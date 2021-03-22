package vitals.tests;

import java.util.List;

import vitals.battery.LiIonBattery;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class BatteryStatusTest {

    public static void isBatteryOk() {
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
    }

    public static void withTemperatureScale() {
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("50C", 70, 0.7f) == false);
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("323K", 70, 0.7f) == false);
        assert (LiIonBattery.isBatteryOkWithInputInTemperatureScale("122F", 70, 0.7f) == false);
    }

    public static void accumulatedData() {
        List<String> data = LiIonBattery.getBatteryStatus(0, 85, 0.9f);
        assert (!data.isEmpty());
        assert (data.size() == 3);
    }
}
