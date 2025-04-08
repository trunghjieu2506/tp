package loanbook.commands.setcommands;

import loanbook.LoanManager;
import utils.datetime.DateParser;
import utils.datetime.StartDateException;
import utils.io.IOHandler;

import java.time.LocalDate;
import java.util.Scanner;

import static utils.textcolour.TextColour.RED;

public class SetStartDateCommand extends SetCommand {
    protected Scanner scanner;
    protected boolean allowNull;

    public SetStartDateCommand(LoanManager loanManager, int index, Scanner scanner, boolean allowNull) {
        super(loanManager, index);
        this.scanner = scanner;
        this.allowNull = allowNull;
    }

    @Override
    public void execute() {
        while (true) {
            try {
                LocalDate date = DateParser.handleLocalDateUI(scanner, "Key in the new start date:",
                        allowNull);
                loan.setStartDate(date);
                loanManager.storeLoans();
                break;
            } catch (StartDateException e) {
                IOHandler.writeOutputWithColour(e.getMessage(), RED);
            }
        }
        System.out.println("The start date of the following loan is updated:");
        System.out.println(loan.showDetails());
    }
}
