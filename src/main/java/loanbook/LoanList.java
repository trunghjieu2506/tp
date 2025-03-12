package loanbook;

import loanbook.loan.AdvancedLoan;
import loanbook.loan.Loan;
import loanbook.loan.SimpleLoan;
import money.Money;
import people.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * Stores the list of loans and related operations.
 */
public class LoanList {
    protected ArrayList<Loan> loans;

    public LoanList() {
        loans = new ArrayList<>();
    }

    public LoanList(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public void add(String description, Person lender, Person borrower, Money money) {
        loans.add(new SimpleLoan(description, lender, borrower, money));
    }

    public void add(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        loans.add(new SimpleLoan(description, lender, borrower, money, deadline));
    }

    public void add(String description, Person lender, Person borrower, Money money, Period period, double interest) {
        loans.add(new AdvancedLoan(description, lender, borrower, money, period, interest));
    }

    public void add(String description, Person lender, Person borrower, Money money, LocalDate deadline, Period period, double interest) {
        loans.add(new AdvancedLoan(description, lender, borrower, money, deadline, period, interest));
    }

    public void delete(int index) {
        loans.remove(index);
    }

    public ArrayList<Loan> findIncomingLoan(Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBorrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }

    public ArrayList<Loan> findOutgoingLoan(Person lender) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getLender() == lender) {
                found.add(loan);
            }
        }
        return found;
    }

    public ArrayList<Loan> findLoan(Person lender, Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getLender() == lender && loan.getBorrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }
}
