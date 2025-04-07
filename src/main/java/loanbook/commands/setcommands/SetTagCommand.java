package loanbook.commands.setcommands;

import loanbook.LoanManager;
import utils.io.IOHandler;

import java.util.Scanner;

import static utils.textcolour.TextColour.GREEN;

public class SetTagCommand extends SetCommand {
    Scanner scanner;

    public SetTagCommand(LoanManager loanManager, Scanner scanner, int index) {
        super(loanManager, index);
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Editing the tags for the following loan:");
        System.out.println(loan.basicInfo());
        System.out.println("""
                Add a tag by entering "add [your new tag]".
                Delete a tag by entering "delete [tag]".
                Finish by entering "finish".
                """);
        while (true) {
            System.out.print("Enter your command:\n> ");
            String input = scanner.nextLine().trim();
            if (input.startsWith("add")) {
                String tag = input.replace("add", "").trim();
                loanManager.addTag(index, tag);
                System.out.println("Added tag: [" + tag + "]");
            } else if (input.startsWith("delete")) {
                String tag = input.replace("delete", "").trim();
                loanManager.deleteTag(index, tag);
                System.out.println("Deleted tag: [" + tag + "]");
            } else if (input.equalsIgnoreCase("finish")) {
                IOHandler.writeOutputWithColour("Finished editing tags.", GREEN);
                System.out.println("Here are the update details of this loan:");
                System.out.println(loan.showDetails());
                break;
            }
        }
    }
}
