package loanbook;

import cashflow.model.interfaces.Finance;
import cashflow.model.interfaces.LoanDataManager;
import loanbook.loan.Loan;
import loanbook.save.LoanSaveManager;
import utils.contacts.ContactsList;
import utils.contacts.Person;
import utils.tags.TagList;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Stores the list of loans and related operations. Each instance requires a <code>String</code> username.
 */
public class LoanManager implements LoanDataManager {
    protected String user;
    protected ArrayList<Loan> loans;
    protected ContactsList contactsList;
    protected TagList<Loan> tags;

    public LoanManager(String user) {
        this.user = user;
        loans = new ArrayList<>();
        contactsList = new ContactsList(user);
        tags = new TagList<>();
    }

    public LoanManager(String user, ArrayList<Loan> loans, ContactsList contactsList) {
        this.user = user;
        this.loans = loans;
        this.contactsList = contactsList;
        initialiseTags();
    }

    public ContactsList getContactsList() {
        return contactsList;
    }

    public void setContactsList(ContactsList contactsList) {
        this.contactsList = contactsList;
    }

    public String getUser() {
        return user;
    }

    public void add(Loan loan) {
        loans.add(loan);
    }

    public void add(Loan loan, String tag) {
        loan.addTag(tag);
        loans.add(loan);
        tags.addMap(tag, loan);
    }

    /**
     * Deletes the <code>index</code>th loan from the list and remove all tag mappings to the loan.
     * @param index the index of the loan, starts from 1.
     * @throws IndexOutOfBoundsException when the <code>index</code>th loan does not exist.
     */
    public void delete(int index) throws IndexOutOfBoundsException {
        Loan loan = loans.get(index - 1);
        ArrayList<String> loanTags = loan.getTagList();
        if (loanTags != null) {
            for (String tag : loanTags) {
                tags.removeMap(tag, loan);
            }
        }
        loans.remove(index - 1);
        try {
            LoanSaveManager.saveLoanList(this);
        } catch (IOException e) {
            System.out.println("Unable to update save file");
        }
    }

    public void addTag(int index, String tag) {
        get(index).addTag(tag);
        tags.addMap(tag, get(index));
    }

    public void deleteTag(int index, String tag) {
        get(index).removeTag(tag);
    }

    /**
     * Get the <code>index</code>th loan in the list. Starts from 1.
     * @param index the index of the loan.
     * @return The <code>index</code>th <code>Loan</code> in the list.
     */
    public Loan get(int index) {
        return loans.get(index - 1);
    }

    /**
     * Finds all loans borrowed by <code>borrower</code>.
     * @param borrower the borrower.
     * @return an <code>ArrayList</code> of all <code>Loan</code>s found.
     */
    public ArrayList<Loan> findIncomingLoan(Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.borrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }

    /**
     * Finds all loans lent by <code>lender</code>.
     * @param lender the lender.
     * @return an <code>ArrayList</code> of all <code>Loan</code>s found.
     */
    public ArrayList<Loan> findOutgoingLoan(Person lender) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.lender() == lender) {
                found.add(loan);
            }
        }
        return found;
    }

    /**
     * Finds all loans lent or borrowed by a person.
     * @param person the name of the person.
     * @return an <code>ArrayList</code> of all <code>Loan</code>s found.
     */
    public ArrayList<Loan> findAssociatedLoan(Person person) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.lender() == person || loan.borrower() == person) {
                found.add(loan);
            }
        }
        return found;
    }

    public ArrayList<Loan> findOverdueLoan() {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.isOverdue()) {
                found.add(loan);
            }
        }
        return found;
    }

    public BigDecimal getTotalLoanFrom(String name) {
        Person borrower = contactsList.findName(name);
        if (borrower == null) {
            return null;
        }
        BigDecimal amount = BigDecimal.valueOf(0);
        for (Loan loan : loans) {
            if (loan.borrower() == borrower) {
                amount = amount.add(loan.getBalance().getAmount());
            }
        }
        return amount;
    }

    /**
     * Finds all the loans lent by <code>lender</code> and borrowed by <code>borrower</code>.
     * @param lender the lender.
     * @param borrower the borrower.
     * @return an <code>ArrayList</code> of all <code>Loan</code>s found.
     */
    public ArrayList<Loan> findLoan(Person lender, Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.lender() == lender && loan.borrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }

    /**
     * Finds all loans in this LoanManager that contains the <code>tag</code>.
     * @param tag the <codee>String</codee> tag that each loan in the output list contains.
     * @return an <code>ArrayList</code> of <code>Loan</code>s that contains the <code>tag</code>.
     */
    public ArrayList<Loan> findLoanWithTag(String tag) {
        return tags.findWithTag(tag);
    }

    /**
     * Reads every tag from every <code>Loan</code> and create respective mappings in the <code>tags</code>
     *     for easy search. This method should not be called often. Only when reading the storage file, or when
     *     the tag list is messed up.
     */
    public void initialiseTags() {
        tags = new TagList<>();
        for (Loan loan : loans) {
            ArrayList<String> loanTags = loan.getTagList();
            if (loanTags != null) {
                for (String tag : loanTags) {
                    tags.addMap(tag, loan);
                }
            }
        }
    }

    /**
     * A method to show the full list of loans. Only the basic information of the loans are shown by calling
     *     <code>basicInfo()</code> of every <code>Loan</code>.
     * @return a ready-to-print <code>String</code> displaying all loans.
     */
    public String simpleFulList() {
        if (loans.isEmpty()) {
            return "List is empty";
        }
        StringBuilder output = new StringBuilder();
        output.append("Here are all recorded loans:\n");
        int i = 1;
        for (Loan loan : loans) {
            output.append("[" + i + "] ").append(loan.basicInfo()).append('\n');
            i++;
        }
        return output.toString();
    }

    /**
     * A method to show every information of the <code>index</code>th loan.
     * @param index the index of the loan in the ArrayList.
     * @return a ready-to-print <code>String</code> containing all information of the loan.
     */
    public String showDetail(int index) throws IndexOutOfBoundsException {
        Loan loan = loans.get(index - 1);
        if (loan == null) {
            return "Invalid loan index";
        }
        String[] lines = loan.showDetails().split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = "    " + lines[i];
        }

        return "[" + index + "] " + String.join("\n", lines);
    }

    public String toSave() {
        StringBuilder save = new StringBuilder();
        for (Loan loan : loans) {
            save.append(loan.forSave()).append('\n').append(LoanSaveManager.LOAN_SEPARATOR);
        }
        return save.toString();
    }

    @Override
    public ArrayList<Finance> getLoanList() {
        return new ArrayList<>(loans);
    }
}
