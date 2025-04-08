package loanbook.commands.setcommands;

import loanbook.LoanManager;
import utils.datetime.DateParser;
import utils.datetime.ReturnDateException;
import utils.io.IOHandler;

import java.time.LocalDate;
import java.util.Scanner;

import static utils.textcolour.TextColour.RED;

public class SetReturnDateCommand extends SetCommand {
    protected Scanner scanner;
    protected boolean allowNull;

    public SetReturnDateCommand(LoanManager loanManager, int index, Scanner scanner, boolean allowNull) {
        super(loanManager, index);
        this.scanner = scanner;
        this.allowNull = allowNull;
    }

    @Override
    public void execute() {
        while (true) {
            try {
                LocalDate date = DateParser.handleLocalDateUI(scanner, "Key in the new return date:",
                        allowNull);
                loan.setReturnDate(date);
                loanManager.storeLoans();
                break;
            } catch (ReturnDateException e) {
                IOHandler.writeOutputWithColour(e.getMessage(), RED);
            }
        }
        System.out.println("The return date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
