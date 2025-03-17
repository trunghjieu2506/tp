package loanbook;

import loanbook.loan.Loan;
import people.*;
import tags.TagList;

import java.util.ArrayList;

/**
 * Stores the list of loans and related operations.
 */
public class LoanList {
    protected ArrayList<Loan> loans;
    protected TagList<Loan> tags;

    public LoanList() {
        loans = new ArrayList<>();
    }

    public LoanList(ArrayList<Loan> loans) {
        this.loans = loans;
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
        ArrayList<String> loanTags = loan.getTagsList();
        if (loanTags != null) {
            for (String tag : loanTags) {
                tags.removeMap(tag, loan);
            }
        }
        loans.remove(index);
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
     * Finds all loans lent by <code>borrower</code>.
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

    public ArrayList<Loan> findLoanWithTag(String tag) {
        return tags.findWithTag(tag);
    }

    /**
     * Shows the full list of loans. Only the lenders, borrowers and money are shown.
     * @return a ready-to-print <code>String</code> displaying all loans.
     */
    public String simpleFulList() {
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (Loan loan : loans) {
            output.append("[" + i + "] ").append(loan.basicInfo()).append('\n');
            i++;
        }
        return output.toString();
    }

    /**
     * Convert an <code>ArrayList</code> of <code>Loan</code>s to a ready-to-print <code>String</code>.
     * @param loans the <code>ArrayList</code> of <code>Loans</code> to be printed.
     * @return the converted <code>String</code>.
     */
    public String forPrint(ArrayList<Loan> loans) {
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (Loan loan : loans) {
            output.append("[" + i + "] ").append(loan.basicInfo()).append('\n');
            i++;
        }
        return output.toString();
    }

    /**
     * Shows every information of the <code>index</code>th loan.
     * @param index the index of the loan in the ArrayList.
     * @return a ready-to-print <code>String</code> containing all information of the loan.
     */
    public String showDetail(int index) {
        Loan loan = loans.get(index - 1);
        String[] lines = loan.showDetails().split("\n");

        for (int i = 1; i < lines.length; i++) {
            lines[i] = "    " + lines[i];
        }

        return "[" + index + "] " + String.join("\n", lines);
    }

    public String toSave() {
        StringBuilder save = new StringBuilder();
        for (Loan loan : loans) {
            save.append(loan.forSave()).append('\n');
        }
        return save.toString();
    }
}
