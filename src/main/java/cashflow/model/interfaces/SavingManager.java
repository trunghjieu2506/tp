package cashflow.model.interfaces;

import budgetsaving.saving.exceptions.SavingRuntimeException;
import utils.money.Money;

import java.time.LocalDate;

public interface SavingManager {

    String getCurrency();
    void setCurrency(String currency);

    String setNewSaving(String name, Money amount, LocalDate deadline) throws SavingRuntimeException;
    String contributeToSaving(int index, Money amount) throws SavingRuntimeException;
    String listSavings();
    void modifySaving(int index, Money amount, LocalDate deadline) throws SavingRuntimeException;
    void checkSaving(int index) throws SavingRuntimeException;
    void deleteSaving(int index) throws SavingRuntimeException;
    void deleteContribution(int SavingIndex, int ContributionIndex)throws SavingRuntimeException;
    String getSavingsSummary();
}
