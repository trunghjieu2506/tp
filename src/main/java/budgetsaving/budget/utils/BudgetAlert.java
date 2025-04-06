package budgetsaving.budget.utils;

import utils.textcolour.TextColour;

public class BudgetAlert {
    public static final String BUDGET_ALERT = TextColour.RED +
            "You have exceeded the budget! \n" +
            "Please manage your budget wisely or type 'suggestion' for financial advises."
             + TextColour.RESET;

    public static void exceedBudgetAlert(){
        System.out.println(BUDGET_ALERT);
    }
}
