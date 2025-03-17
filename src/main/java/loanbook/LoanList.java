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

    public void delete(int index) throws IndexOutOfBoundsException {
        Loan loan = loans.get(index);
        ArrayList<String> loanTags = loan.getTagsList();
        for (String tag : loanTags) {
            tags.removeMap(tag, loan);
        }
        loans.remove(index);
    }

    public Loan get(int index) {
        return loans.get(index - 1);
    }

    public ArrayList<Loan> findIncomingLoan(Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.borrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }

    public ArrayList<Loan> findOutgoingLoan(Person lender) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.lender() == lender) {
                found.add(loan);
            }
        }
        return found;
    }

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

    public String simpleFulList() {
        StringBuilder output = new StringBuilder();
        int i = 1;
        for (Loan loan : loans) {
            output.append("[" + i + "] ").append(loan.basicInfo()).append('\n');
            i++;
        }
        return output.toString();
    }

    public String showDetail(int index) {
        Loan loan = loans.get(index - 1);
        return "[" + index + "]\n" + loan.showDetails();
    }

    public String toSave() {
        StringBuilder save = new StringBuilder();
        for (Loan loan : loans) {
            save.append(loan.forSave()).append('\n');
        }
        return save.toString();
    }
}
