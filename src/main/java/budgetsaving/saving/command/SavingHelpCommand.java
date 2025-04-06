package budgetsaving.saving.command;

import cashflow.ui.command.Command;
import utils.io.IOHandler;

import static budgetsaving.saving.command.SavingGeneralCommand.SAVING_COMMANDS;

public class SavingHelpCommand implements Command {


    @Override
    public void execute() {
        IOHandler.writeOutputNoLn(SAVING_COMMANDS);
    }
}
