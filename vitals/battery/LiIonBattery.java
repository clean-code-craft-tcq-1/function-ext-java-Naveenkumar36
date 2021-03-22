package vitals.battery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import vitals.TemperatureScale;
import vitals.ValidateBMSVariants;
import vitals.bms.BMS;
import vitals.language.Language;

import static vitals.bms.BMSCheck.*;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class LiIonBattery {

    static final BiFunction<Float, Float, Float> calculatePercentageValue =
          (percentageValue, value) -> (percentageValue / 100) * value;
    private Map<BMS, Float> input;

    public LiIonBattery(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        saveInput(temperature, soc, chargeRate);
    }

    private void saveInput(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        input = new TreeMap<>();
        input.put(BMS.TEMPERATURE, temperature);
        input.put(BMS.SOC, soc);
        input.put(BMS.CHARGINGRATE, chargeRate);
    }

    private List<ValidateBMSVariants> variants() {
        List<ValidateBMSVariants> validateBMSVariants = new ArrayList<>();
        validateBMSVariants.add(new ValidateBMSVariants(
              isApproachingAboveThreshold(
                    Temperature.MAX_TEMPERATURE_THRESHOLD,
                    Temperature.MAX_WARNING_TEMPERATURE_THRESHOLD
              ),
              input.get(BMS.TEMPERATURE),
              Language.getSelectedLanguage().getString(TEMPERATURE_ABOVE_THRESHOLD_WARNING)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isApproachingBelowThreshold(
                    Temperature.MIN_TEMPERATURE_THRESHOLD,
                    Temperature.MIN_WARNING_TEMPERATURE_THRESHOLD
              ),
              input.get(BMS.TEMPERATURE),
              Language.getSelectedLanguage().getString(TEMPERATURE_BELOW_THRESHOLD_WARNING)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isBelowThreshold(Temperature.MIN_TEMPERATURE_THRESHOLD),
              input.get(BMS.TEMPERATURE),
              Language.getSelectedLanguage().getString(TEMPERATURE_BELOW_THRESHOLD)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isAboveThreshold(Temperature.MAX_TEMPERATURE_THRESHOLD),
              input.get(BMS.TEMPERATURE),
              Language.getSelectedLanguage().getString(TEMPERATURE_ABOVE_THRESHOLD)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isApproachingBelowThreshold(StateOfCharge.MIN_SOC, StateOfCharge.MIN_WARNING_SOC),
              input.get(BMS.SOC),
              Language.getSelectedLanguage().getString(STATE_OF_CHARGE_BELOW_THRESHOLD_WARNING)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isApproachingAboveThreshold(StateOfCharge.MAX_SOC, StateOfCharge.MAX_WARNING_SOC),
              input.get(BMS.SOC),
              Language.getSelectedLanguage().getString(STATE_OF_CHARGE_ABOVE_THRESHOLD_WARNING)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isBelowThreshold(StateOfCharge.MIN_SOC),
              input.get(BMS.SOC), Language.getSelectedLanguage().getString(STATE_OF_CHARGE_BELOW_THRESHOLD)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(isAboveThreshold(StateOfCharge.MAX_SOC),
              input.get(BMS.SOC), Language.getSelectedLanguage().getString(STATE_OF_CHARGE_ABOVE_THRESHOLD)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isApproachingAboveThreshold(ChargingRate.MAX_CHARGING_RATE, ChargingRate.MAX_WARNING_CHARGING_RATE),
              input.get(BMS.CHARGINGRATE),
              Language.getSelectedLanguage().getString(CHARGING_RATE_ABOVE_THRESHOLD_WARNING)
        ));
        validateBMSVariants.add(new ValidateBMSVariants(
              isAboveThreshold(ChargingRate.MAX_CHARGING_RATE),
              input.get(BMS.CHARGINGRATE),
              Language.getSelectedLanguage().getString(CHARGING_RATE_ABOVE_THRESHOLD)
        ));
        return validateBMSVariants;
    }

    public static boolean isBatteryOk(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        List<String> hasError = getBatteryStatus(temperature, soc, chargeRate);
        if (!hasError.isEmpty()) {
            System.out.println(hasError);
            return false;
        }
        return true;
    }

    public static List<String> getBatteryStatus(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        LiIonBattery liIonBattery = new LiIonBattery(temperature, soc, chargeRate);
        List<String> batteryStatus = ValidateBMSVariants.check(liIonBattery.variants());
        return batteryStatus;
    }

    public static boolean isBatteryOkWithInputInTemperatureScale(
          String temperature,
          float soc,
          float chargeRate
    )
    {
        return isBatteryOk(
              isTempValidReturnFunction(temperature)
                    .apply(Float.parseFloat(temperature.substring(0, temperature.length() - 1))),
              soc,
              chargeRate
        );
    }

    private static Function<Float, Float> isTempValidReturnFunction(String temperature) {
        return Arrays.stream(TemperatureScale.values())
              .filter(tempUnit -> temperature.toUpperCase().contains(tempUnit.name()))
              .findFirst()
              .map(TemperatureScale::toCelsius)
              .orElseThrow(() -> new IllegalArgumentException("Provide proper temperature scale"));
    }

    public static final class Temperature {

        private static final float WARNING_PERCENTAGE = 5;
        private static final float MAX_TEMPERATURE_THRESHOLD = 45;
        private static final float MIN_TEMPERATURE_THRESHOLD = 0;
        private static final float MAX_WARNING_TEMPERATURE_THRESHOLD =
              MAX_TEMPERATURE_THRESHOLD - calculatePercentageValue.apply(WARNING_PERCENTAGE, MAX_TEMPERATURE_THRESHOLD);
        private static final float MIN_WARNING_TEMPERATURE_THRESHOLD =
              MIN_TEMPERATURE_THRESHOLD + calculatePercentageValue.apply(WARNING_PERCENTAGE, MIN_TEMPERATURE_THRESHOLD);

        private Temperature() {
        }
    }

    public static final class StateOfCharge {

        private static final float MAX_SOC = 80;
        private static final float MIN_SOC = 20;
        private static final float WARNING_PERCENTAGE = 5;
        private static final float MAX_WARNING_SOC =
              MAX_SOC - calculatePercentageValue.apply(WARNING_PERCENTAGE, MAX_SOC);
        private static final float MIN_WARNING_SOC =
              MIN_SOC + calculatePercentageValue.apply(WARNING_PERCENTAGE, MIN_SOC);

        private StateOfCharge() {
        }
    }

    public static final class ChargingRate {

        private static final float MAX_CHARGING_RATE = 0.8f;
        private static final float WARNING_PERCENTAGE = 5;
        private static final float MAX_WARNING_CHARGING_RATE =
              MAX_CHARGING_RATE - calculatePercentageValue.apply(WARNING_PERCENTAGE, MAX_CHARGING_RATE);

        private ChargingRate() {
        }
    }
}
