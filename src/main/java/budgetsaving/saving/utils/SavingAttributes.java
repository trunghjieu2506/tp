package budgetsaving.saving.utils;

import java.time.LocalDate;

import static budgetsaving.budget.utils.BudgetAttributes.findNextIdentifier;

public class SavingAttributes {
    public static final String INDEX_IDENTIFIER = "i/";
    public static final String NAME_IDENTIFIER = "n/";
    public static final String AMOUNT_IDENTIFIER = "a/";
    public static final String DEADLINE_IDENTIFIER = "b/";
    public static final int IDENTIFIER_OFFSET = 2;

    private int index;
    private String name;
    private double amount;
    private LocalDate deadline;

    public SavingAttributes(String input) {
        int iPos = input.indexOf(INDEX_IDENTIFIER);
        int nPos = input.indexOf(NAME_IDENTIFIER);
        int aPos = input.indexOf(AMOUNT_IDENTIFIER);
        int dPos = input.indexOf(DEADLINE_IDENTIFIER);

        // Extract index (convert to 0-index)
        if (iPos != -1) {
            int end = findNextIdentifier(input, iPos, nPos, aPos, dPos);
            String indexStr = input.substring(iPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.index = Integer.parseInt(indexStr) - 1;
            } catch(NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid index value: " + indexStr, ex);
            }
        } else {
            this.index = -1;
        }

        // Extract name
        if (nPos != -1) {
            int end = findNextIdentifier(input, nPos, aPos, dPos);
            this.name = input.substring(nPos + IDENTIFIER_OFFSET, end).trim();
        } else {
            this.name = null;
        }

        // Extract amount
        if (aPos != -1) {
            int end = findNextIdentifier(input, aPos, dPos);
            String amountStr = input.substring(aPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.amount = Double.parseDouble(amountStr);
            } catch(NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid amount value: " + amountStr, ex);
            }
        } else {
            this.amount = -1;
        }

        if (dPos != -1) {
            String deadlineStr = input.substring(dPos + IDENTIFIER_OFFSET).trim();
            this.deadline = LocalDate.parse(deadlineStr);
        } else {
            this.deadline = null;
        }
    }

    public int getIndex() {
        return this.index;
    }
    public String getName() {
        return this.name;
    }
    public double getAmount() {
        return this.amount;
    }
    public LocalDate getDeadline() {
        return this.deadline;
    }
}
