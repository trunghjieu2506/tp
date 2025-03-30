package loanbook.interest;

import java.time.Period;

/**
 * A class to store information about a certain interest.
 * <code>description</code> is optional and describes what this interest is about.
 * <code>rate</code> is the percentage that the outstanding balance increases at the end of each <code>period</code>.
 * <code>type</code> can be either SIMPLE or COMPOUND. SIMPLE interests increases the outstanding balance
 * by <code>rate</code>% of the original principal, while COMPOUND interests increase the outstanding balance
 * by <code>rate</code>% of the outstanding balance.
 */
public class Interest {
    protected String description;
    protected double rate;
    protected InterestType type;
    protected Period period;

    /**
     * Instantiates an Interest class with necessary information.
     * @param rate is the percentage that the outstanding balance increases at the end of each <code>period</code>.
     * @param type can be either SIMPLE or COMPOUND.
     * @param period is the time between each time the interest is applied.
     */
    public Interest(double rate, InterestType type, Period period) {
        this.rate = rate;
        this.type = type;
        this.period = period;
    }

    /**
     * Instantiates an Interest class with necessary information.
     * @param rate is the percentage that the outstanding balance increases at the end of each <code>period</code>.
     * @param type can be either SIMPLE or COMPOUND.
     * @param period is the time between each time the interest is applied.
     * @param description describes what the interest is about.
     */
    public Interest(double rate, InterestType type, Period period, String description) {
        this.rate = rate;
        this.type = type;
        this.period = period;
        this.description = description;
    }

    /**
     * @return the interest rate in percentage.
     */
    public double rate() {
        return rate;
    }

    /**
     * @return the interest period.
     */
    public Period period() {
        return period;
    }

    /**
     * @return either SIMPLE or COMPOUND.
     */
    public InterestType type() {
        return type;
    }

    /**
     * @return the description of this interest.
     */
    public String description() {
        return description;
    }

    /**
     * @param description updates the description of this interest.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        StringBuilder periodString = new StringBuilder();
        if (period.getYears() != 0) {
            periodString.append(period.getYears()).append(" Year").append(period.getYears() == 1 ? "" : "s");
        }
        if (period.getMonths() != 0) {
            periodString.append(period.getMonths()).append(" Month").append(period.getMonths() == 1 ? "" : "s");
        }
        if (period.getDays() != 0) {
            periodString.append(period.getDays()).append(" Day").append(period.getDays() == 1 ? "" : "s");
        }
        return (type == InterestType.SIMPLE ? "Simple Interest " : "Compound Interest ")
                + rate + "% Per "
                + periodString.toString().trim();
    }

    public String forSave() {
        StringBuilder periodString = new StringBuilder();
        if (period.getYears() != 0) {
            periodString.append(period.getYears()).append(" Year").append(period.getYears() == 1 ? "" : "s");
        }
        if (period.getMonths() != 0) {
            periodString.append(period.getMonths()).append(" Month").append(period.getMonths() == 1 ? "" : "s");
        }
        if (period.getDays() != 0) {
            periodString.append(period.getDays()).append(" Day").append(period.getDays() == 1 ? "" : "s");
        }
        return (type == InterestType.SIMPLE ? "SIMPLE " : "COMPOUND ")
                + rate + "% Per "
                + periodString.toString().trim();
    }
}
