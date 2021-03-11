package vitals.bms;

import java.util.function.Predicate;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public final class BMSCheck {

    private BMSCheck() {
    }

    public static final String TEMPERATURE_BELOW_THRESHOLD = "TEMPERATURE_BELOW_THRESHOLD";
    public static final String TEMPERATURE_ABOVE_THRESHOLD = "TEMPERATURE_ABOVE_THRESHOLD";
    public static final String TEMPERATURE_ABOVE_THRESHOLD_WARNING = "TEMPERATURE_ABOVE_THRESHOLD_WARNING";
    public static final String TEMPERATURE_BELOW_THRESHOLD_WARNING = "TEMPERATURE_BELOW_THRESHOLD_WARNING";
    public static final String STATE_OF_CHARGE_ABOVE_THRESHOLD = "STATE_OF_CHARGE_ABOVE_THRESHOLD";
    public static final String STATE_OF_CHARGE_BELOW_THRESHOLD = "STATE_OF_CHARGE_BELOW_THRESHOLD";
    public static final String STATE_OF_CHARGE_ABOVE_THRESHOLD_WARNING = "STATE_OF_CHARGE_ABOVE_THRESHOLD_WARNING";
    public static final String STATE_OF_CHARGE_BELOW_THRESHOLD_WARNING = "STATE_OF_CHARGE_BELOW_THRESHOLD_WARNING";
    public static final String CHARGING_RATE_ABOVE_THRESHOLD = "CHARGING_RATE_ABOVE_THRESHOLD";
    public static final String CHARGING_RATE_ABOVE_THRESHOLD_WARNING = "CHARGING_RATE_ABOVE_THRESHOLD_WARNING";

    public static Predicate<Float> isAboveThreshold(float maxThreshold) {
        return value -> value > maxThreshold;
    }

    public static Predicate<Float> isBelowThreshold(float minThreshold) {
        return value -> value < minThreshold;
    }

    public static Predicate<Float> isApproachingBelowThreshold(
          float minThreshold,
          float warningThreshold
    )
    {
        return soc -> soc >= minThreshold && soc <= warningThreshold;
    }

    public static Predicate<Float> isApproachingAboveThreshold(
          float maxThreshold,
          float warningThreshold
    )
    {
        return soc -> soc <= maxThreshold && soc >= warningThreshold;
    }
}
