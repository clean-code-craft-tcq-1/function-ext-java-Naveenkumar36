package vitals;

import vitals.battery.LiIonBattery;
import vitals.language.Language;

public final class Main {

    private Main() {

    }

    public static void main(String[] args) throws IllegalArgumentException {
        Language.setPreference("German");
        assert (LiIonBattery.isBatteryOk(25, 70, 0.7f) == true);
        assert (LiIonBattery.isBatteryOk(0, 85, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(44, 85, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(50, 85, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(-1, 85, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(25, 79, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(25, 21, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(25, 85, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(25, 15, 0.0f) == false);
        assert (LiIonBattery.isBatteryOk(25, 25, 0.76f) == false);
        assert (LiIonBattery.isBatteryOk(25, 25, 0.9f) == false);
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("50C", 70, 0.7f) == false);
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("323K", 70, 0.7f) == false);
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("122F", 70, 0.7f) == false);
    }
}
