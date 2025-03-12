package loanbook;

import loanbook.loan.Loan;
import loanbook.loan.SimpleLoan;
import money.Money;
import people.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public class LoanManager {
    protected ArrayList<Loan> loans;

    public LoanManager() {
        loans = new ArrayList<>();
    }

    public LoanManager(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(String description, Person lender, Person borrower, Money money, LocalDate deadline) {
        loans.add(new SimpleLoan(description, lender, borrower, money, deadline));
    }

    public ArrayList<Loan> findOutgoingLoan(Person borrower) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBorrower() == borrower) {
                found.add(loan);
            }
        }
        return found;
    }

    public ArrayList<Loan> findIncomingLoan(Person lender) {
        ArrayList<Loan> found = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getLender() == lender) {
                found.add(loan);
            }
        }
        return found;
    }
}
