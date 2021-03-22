package vitals;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author {@literal Jayaram Naveenkumar (jayaram.naveenkumar@in.bosch.com)}
 */
public class ValidateBMSVariants {

    private final Predicate<Float> variant;
    private final String message;
    private final Float value;

    public ValidateBMSVariants(
          Predicate<Float> variant,
          Float value,
          String message
    )
    {
        this.value = value;
        this.variant = variant;
        this.message = message;
    }

    public boolean isNotValid()
    {
        return variant.test(value);
    }

    public String getMessage()

    {
        return message;
    }

    public static List<String> check(List<ValidateBMSVariants> validateBMSVariants) {
        return validateBMSVariants.stream()
              .filter(ValidateBMSVariants::isNotValid)
              .map(ValidateBMSVariants::getMessage)
              .collect(Collectors.toList());
    }
}
