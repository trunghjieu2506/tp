package budgetsaving.budget.utils;

import budgetsaving.budget.exceptions.BudgetAttributeException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BudgetAttributes {
    public static final String INDEX_IDENTIFIER = "i/";
    public static final String NAME_IDENTIFIER = "n/";
    public static final String AMOUNT_IDENTIFIER = "a/";
    public static final String END_DATE_IDENTIFIER = "e/";
    public static final String CATEGORY_IDENTIFIER = "c/";
    public static final int IDENTIFIER_OFFSET = 2;

    private int index;
    private String name;
    private double amount;
    private LocalDate endDate;
    private String category;

    //this constructor needs to ensure that:
    //INDEX is an int
    //NAME is a non empty String
    //AMOUNT is a real number
    //endDate is a date
    public BudgetAttributes(String input) throws BudgetAttributeException {
        // Additional check for repeated identifiers.
        String[] identifiers = { INDEX_IDENTIFIER, NAME_IDENTIFIER,
                AMOUNT_IDENTIFIER, END_DATE_IDENTIFIER, CATEGORY_IDENTIFIER };
        for (String id : identifiers) {
            int firstOccurrence = input.indexOf(id);
            int lastOccurrence = input.lastIndexOf(id);
            if (firstOccurrence != -1 && firstOccurrence != lastOccurrence) {
                throw new BudgetAttributeException("Identifier '" + id + "' is repeated in the input.");
            }
        }

        int iPos = input.indexOf(INDEX_IDENTIFIER);
        int nPos = input.indexOf(NAME_IDENTIFIER);
        int aPos = input.indexOf(AMOUNT_IDENTIFIER);
        int ePos = input.indexOf(END_DATE_IDENTIFIER);
        int cPos = input.indexOf(CATEGORY_IDENTIFIER);
        if (!identifierIsInOrder(iPos, nPos, aPos, ePos, cPos)) {
            throw new BudgetAttributeException("Identifiers are not in the correct order.");
        }

        // Extract index (convert to 0-index)
        if (iPos != -1) {
            int end = findNextIdentifier(input, iPos, nPos, aPos, ePos, cPos);
            String indexStr = input.substring(iPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.index = Integer.parseInt(indexStr) - 1;
            } catch(NumberFormatException ex) {
                throw new BudgetAttributeException("Index input is not an integer.");
            }
        } else {
            this.index = -1;
        }

        // Extract name
        if (nPos != -1) {
            int end = findNextIdentifier(input, nPos, aPos, ePos, cPos);
            this.name = input.substring(nPos + IDENTIFIER_OFFSET, end).trim();
            if (name.isEmpty()) {
                throw new BudgetAttributeException("Name cannot be empty after specifying the identifier.");
            }
        } else {
            this.name = null;
        }

        // Extract amount
        if (aPos != -1) {
            int end = findNextIdentifier(input, aPos, ePos, cPos);
            String amountStr = input.substring(aPos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.amount = Double.parseDouble(amountStr);
            } catch(NumberFormatException ex) {
                throw new BudgetAttributeException("The amount must be a number.");
            }
        } else {
            this.amount = -1;
        }

        // Extract end date
        if (ePos != -1) {
            int end = findNextIdentifier(input, ePos, cPos);
            String endDateStr = input.substring(ePos + IDENTIFIER_OFFSET, end).trim();
            try {
                this.endDate = LocalDate.parse(endDateStr);
            } catch(Exception e) {
                throw new BudgetAttributeException("Your date input is not a valid format of YYYY-MM-DD.");
            }
            if (endDate.isAfter(LocalDate.of(2049, 12, 31))) {
                throw new BudgetAttributeException(
                        "You budget has a very long duration, which is not supported.");
            }
        } else {
            this.endDate = null;
        }

        // Extract category (till end of string)
        if (cPos != -1) {
            this.category = input.substring(cPos + IDENTIFIER_OFFSET).trim();
            if (category.isEmpty()){
                throw new BudgetAttributeException("The category cannot be empty.");
            }
        } else {
            this.category = null;
        }
    }

    private boolean identifierIsInOrder(int iPos, int nPos, int aPos, int ePos, int cPos){
        // Check order of identifiers
        List<Map.Entry<String, Integer>> orderedIdentifiers = new ArrayList<>();
        if (iPos != -1) {
            orderedIdentifiers.add(Map.entry("i/", iPos));
        }
        if (nPos != -1) {
            orderedIdentifiers.add(Map.entry("n/", nPos));
        }
        if (aPos != -1) {
            orderedIdentifiers.add(Map.entry("a/", aPos));
        }
        if (ePos != -1) {
            orderedIdentifiers.add(Map.entry("e/", ePos));
        }
        if (cPos != -1) {
            orderedIdentifiers.add(Map.entry("c/", cPos));
        }

        for (int i = 0; i < orderedIdentifiers.size() - 1; i++) {
            int currentPos = orderedIdentifiers.get(i).getValue();
            int nextPos = orderedIdentifiers.get(i + 1).getValue();
            if (currentPos >= nextPos) {
                return false;
            }
        }
        return true;
    }
    // Helper method to find the next identifier position after 'current'
    // among a variable number of identifier positions.
    public static int findNextIdentifier(String input, int current, int... positions) {
        int next = input.length();
        for (int pos : positions) {
            if (pos != -1 && pos > current && pos < next) {
                next = pos;
            }
        }
        return next;
    }

    // Getters
    public int getIndex(){
        return this.index;
    }
    public String getName(){
        return this.name;
    }
    public double getAmount(){
        return this.amount;
    }
    public LocalDate getEndDate(){
        return this.endDate;
    }
    public String getCategory(){
        return this.category;
    }
}
