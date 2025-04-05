package budgetsaving.budget.command;

import cashflow.ui.command.Command;
import utils.io.IOHandler;

import static budgetsaving.budget.command.BudgetGeneralCommand.BUDGET_COMMANDS;

public class BudgetHelpCommand implements Command {


    public BudgetHelpCommand() {

    }

    public void execute(){
        IOHandler.writeOutput(BUDGET_COMMANDS);
    }
}
