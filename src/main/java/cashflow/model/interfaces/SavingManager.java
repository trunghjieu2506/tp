package cashflow.model.interfaces;

import utils.money.Money;

import java.time.LocalDate;

public interface SavingManager {
    String getCurrency();
    void setCurrency(String currency);

    String setNewSaving(String name, Money amount, LocalDate deadline);
    String contributeToSaving(String name, Money amount);
    String contributeToSaving(int index, Money amount);
    String listGoals();
    void checkOneGoal(int index);
    void modifySaving(int index, Money amount, LocalDate deadline);
}
