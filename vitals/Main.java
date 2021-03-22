package vitals;

import vitals.language.Language;
import vitals.report.ConsoleReporter;
import vitals.tests.BatteryStatusTest;

public final class Main {

    private Main() {

    }

    public static void main(String[] args) throws IllegalArgumentException {
        Language.setPreference("German");
        BatteryStatusTest.isBatteryOk();
        BatteryStatusTest.withTemperatureScale();
        BatteryStatusTest.accumulatedData();
        ConsoleReporter consoleReporter = new ConsoleReporter();
        System.out.println("***********************************************");
        consoleReporter.printBatteryStatusReport(0, 85, 0.9f);
        System.out.println("***********************************************");
    }
}
