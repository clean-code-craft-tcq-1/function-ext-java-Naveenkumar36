package vitals;

import java.util.function.Function;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public enum TemperatureScale {
    C(returnSameValue()),
    K(convertKelvinToCelsius()),
    F(convertFahrenheitToCelsius());
    private final Function<Float, Float> toCelsius;

    TemperatureScale(Function<Float, Float> toCelsius) {
        this.toCelsius = toCelsius;
    }

    public Function<Float, Float> toCelsius() {
        return toCelsius;
    }

    static Function<Float, Float> returnSameValue() {
        return Function.identity();
    }

    static Function<Float, Float> convertKelvinToCelsius() {
        return aFloat -> aFloat - 273.15f;
    }

    static Function<Float, Float> convertFahrenheitToCelsius() {
        return aFloat -> (aFloat - 32f) * (5f / 9f);
    }
}
