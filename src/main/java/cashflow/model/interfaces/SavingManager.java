package cashflow.model.interfaces;

import utils.money.Money;

import java.time.LocalDate;

public interface SavingManager {

    String getCurrency();
    void setCurrency(String currency);

    String setNewSaving(String name, Money amount, LocalDate deadline);
    String contributeToSaving(int index, Money amount);
    String listSavings();
    void modifySaving(int index, Money amount, LocalDate deadline);
    void checkSaving(int index);
    void deleteSaving(int index);
    void deleteContribution(int SavingIndex, int ContributionIndex);
    String getSavingsSummary();
}
