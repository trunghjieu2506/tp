package cashflow.ui.command;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Available commands:");
        System.out.println("  help     - Display available commands and usage");
        System.out.println("  setup    - Program Configuration");
        System.out.println("  analytic - Enter analytic program");
        System.out.println("  saving   - Enter saving program");
        System.out.println("  budget   - Enter budget program");
        System.out.println("  expense  - Enter expense program");
        System.out.println("  income   - Enter income program");
        System.out.println("  loan     - Enter loan program");
        System.out.println("  exit     - Exit the program");
    }
}
