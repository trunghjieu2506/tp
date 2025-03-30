package loanbook.commands;

import loanbook.LoanList;

public class DeleteLoanCommand extends LoanCommand{
    protected LoanList loans;
    protected int index;

    public DeleteLoanCommand(LoanList loans, int index) {
        this.loans = loans;
        this.index = index;
    }

    @Override
    public void execute() {
        try {
            String deletedLoan = loans.get(index).basicInfo();
            loans.delete(index);
            System.out.println("Successfully deleted the following loan:\n" + deletedLoan + '\n');
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Loan not found");
        }
    }
}
