package budget_saving.budget.utils;


public class BudgetAttributes {
    public final int index;
    public final String name;
    public final double amount;

    public String getName(){
        return this.name;
    }
    public int getIndex(){
        return this.index;
    }
    public double getAmount(){
        return this.amount;
    }

    public BudgetAttributes(String input) {
        final String INDEX_IDENTIFIER = "i/";
        final String NAME_IDENTIFIER = "n/";
        final String AMOUNT_IDENTIFIER = "a/";
        final int IDENTIFIER_OFFSET = 2;

        int iPos = input.indexOf(INDEX_IDENTIFIER);
        int nPos = input.indexOf(NAME_IDENTIFIER);
        int aPos = input.indexOf(AMOUNT_IDENTIFIER);

        // Extract index
        if (iPos != -1) {
            int end = findNextIdentifier(iPos, nPos, aPos, input.length());
            String indexStr = input.substring(iPos + IDENTIFIER_OFFSET, end).trim();
            this.index = Integer.parseInt(indexStr) - 1;    //CHANGE THIS INTO 0-INDEX
        } else {
            this.index = -1;
        }

        // Extract name
        if (nPos != -1) {
            int end = findNextIdentifier(nPos, iPos, aPos, input.length());
            this.name = input.substring(nPos + IDENTIFIER_OFFSET, end).trim();
        } else {
            this.name = null;
        }

        // Extract amount
        if (aPos != -1) {
            int end = findNextIdentifier(aPos, iPos, nPos, input.length());
            String amountStr = input.substring(aPos + IDENTIFIER_OFFSET, end).trim();
            this.amount = Double.parseDouble(amountStr);
        } else {
            this.amount = -1;   //it cant be negative so -1
        }
    }

    // Helper method to find the nearest next identifier (or end of string)
    private int findNextIdentifier(int current, int other1, int other2, int defaultEnd) {
        int next = defaultEnd;
        if (other1 > current) next = Math.min(next, other1);
        if (other2 > current) next = Math.min(next, other2);
        return next;
    }
}

