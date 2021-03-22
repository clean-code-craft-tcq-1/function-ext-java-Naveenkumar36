package vitals.report;

import java.util.List;

import vitals.battery.LiIonBattery;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class ConsoleReporter implements Reporter {

    private static final String SUCCESS = "Battery is in good condition";

    public void printBatteryStatusReport(
          float temperature,
          float soc,
          float chargeRate
    )
    {
        List<String> data = LiIonBattery.getBatteryStatus(temperature, soc, chargeRate);
        if (!data.isEmpty()) {
            data.forEach(System.out::println);
        } else {
            System.out.println(SUCCESS);
        }
    }
}
