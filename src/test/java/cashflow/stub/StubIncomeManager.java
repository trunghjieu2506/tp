package cashflow.stub;

import cashflow.model.interfaces.Finance;
import expenseincome.income.IncomeManager;

import java.time.LocalDate;
import java.util.ArrayList;

public class StubIncomeManager extends IncomeManager {

    @Override
    public ArrayList<Finance> getIncomeList() {
        ArrayList<Finance> list = new ArrayList<>();

        // Suppose we have two incomes in April 2025
        list.add(new Finance() {
            @Override
            public String getType() {
                return "income";
            }

            @Override
            public double getAmount() {
                return 200.0;
            }

            @Override
            public LocalDate getDate() {
                return LocalDate.of(2025, 4, 1);
            }
        });

        list.add(new Finance() {
            @Override
            public String getType() {
                return "income";
            }

            @Override
            public double getAmount() {
                return 100.0;
            }

            @Override
            public LocalDate getDate() {
                return LocalDate.of(2025, 4, 15);
            }
        });
        return list;
    }
}
