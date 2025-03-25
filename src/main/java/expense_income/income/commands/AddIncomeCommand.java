package expense_income.income.commands;

import expense_income.income.IncomeManager;
import java.time.LocalDate;

public class AddIncomeCommand extends IncomeCommand {
    private String source;
    private double amount;
    private LocalDate date;
    private String category;

    public AddIncomeCommand(String source, double amount, LocalDate date, String category) {
        assert source != null && !source.trim().isEmpty() : "Income description cannot be empty.";
        assert amount > 0 : "Income amount must be greater than zero.";
        assert date != null : "Income date cannot be null.";
        assert category != null : "Income category cannot be null.";

        this.source = source;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    @Override
    public void execute(IncomeManager manager) {
        assert manager != null : "IncomeManager instance cannot be null.";

        manager.addIncome(source, amount, date, category);
    }
}
