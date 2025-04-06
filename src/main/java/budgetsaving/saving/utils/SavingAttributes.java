package budgetsaving.saving.utils;

import budgetsaving.budget.utils.BudgetAttributes; // for using findNextIdentifier
import budgetsaving.saving.exceptions.SavingAttributeException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private boolean identifierIsInOrder(int iPos, int nPos, int aPos, int dPos) {
        List<Map.Entry<String, Integer>> orderedIdentifiers = new ArrayList<>();
        if (iPos != -1) orderedIdentifiers.add(Map.entry(INDEX_IDENTIFIER, iPos));
        if (nPos != -1) orderedIdentifiers.add(Map.entry(NAME_IDENTIFIER, nPos));
        if (aPos != -1) orderedIdentifiers.add(Map.entry(AMOUNT_IDENTIFIER, aPos));
        if (dPos != -1) orderedIdentifiers.add(Map.entry(DEADLINE_IDENTIFIER, dPos));

        for (int i = 0; i < orderedIdentifiers.size() - 1; i++) {
            int currentPos = orderedIdentifiers.get(i).getValue();
            int nextPos = orderedIdentifiers.get(i + 1).getValue();
            if (currentPos >= nextPos) {
                return false;
            }
        }
        return true;
    }

    public SavingAttributes(String input) throws SavingAttributeException {
        // Check for repeated identifiers.
        String[] identifiers = { INDEX_IDENTIFIER, NAME_IDENTIFIER, AMOUNT_IDENTIFIER, DEADLINE_IDENTIFIER };
        for (String id : identifiers) {
            int firstOccurrence = input.indexOf(id);
            int lastOccurrence = input.lastIndexOf(id);
            if (firstOccurrence != -1 && firstOccurrence != lastOccurrence) {
                throw new SavingAttributeException("Identifier '" + id + "' is repeated in the input.");
            }
        }

        // Determine positions of each identifier.
        int iPos = input.indexOf(INDEX_IDENTIFIER);
        int nPos = input.indexOf(NAME_IDENTIFIER);
        int aPos = input.indexOf(AMOUNT_IDENTIFIER);
        int dPos = input.indexOf(DEADLINE_IDENTIFIER);
        if (!identifierIsInOrder(iPos, nPos, aPos, dPos)) {
            throw new SavingAttributeException("Identifiers are not in the correct order.");
        }

        // Extract index (convert to 0-index)
        if (iPos != -1) {
            int end = BudgetAttributes.findNextIdentifier(input, iPos, nPos, aPos, dPos);
            String indexStr = input.substring(iPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.index = Integer.parseInt(indexStr) - 1;
            } catch(NumberFormatException ex) {
                throw new SavingAttributeException("Invalid index value: " + indexStr);
            }
        } else {
            this.index = -1;
        }

        // Extract name
        if (nPos != -1) {
            int end = BudgetAttributes.findNextIdentifier(input, nPos, aPos, dPos);
            this.name = input.substring(nPos + IDENTIFIER_OFFSET, end).trim();
            if (this.name.isEmpty()) {
                throw new SavingAttributeException("Name cannot be empty after specifying the identifier.");
            }
        } else {
            this.name = null;
        }

        // Extract amount
        if (aPos != -1) {
            int end = BudgetAttributes.findNextIdentifier(input, aPos, dPos);
            String amountStr = input.substring(aPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.amount = Double.parseDouble(amountStr);
            } catch(NumberFormatException ex) {
                throw new SavingAttributeException("Invalid amount value: " + amountStr);
            }
        } else {
            this.amount = -1;
        }

        // Extract deadline (as a LocalDate)
        if (dPos != -1) {
            String deadlineStr = input.substring(dPos + IDENTIFIER_OFFSET).trim();
            try {
                this.deadline = LocalDate.parse(deadlineStr);
            } catch (DateTimeParseException ex) {
                throw new SavingAttributeException("Your deadline input is not a valid format of YYYY-MM-DD.");
            }
        } else {
            this.deadline = null;
        }
    }

    // Getters
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
