package PersonalDataGenerator;

import java.util.Random;

/**
 * Generates random house numbers with optional apartment numbers.
 */
public class HouseNumberGenerator {

    private static final Random random = new Random();

    /**
     * Generates a random house number with an optional apartment number.
     * @return String representing the generated house/apartment number
     */
    public static String generate() {

        int houseNumber = random.nextInt(150) + 1;

        boolean includeApartment = random.nextBoolean();
        if (includeApartment) {
            int apartmentNumber = random.nextInt(150) + 1;
            return houseNumber + "/" + apartmentNumber;
        } else {
            return String.valueOf(houseNumber);
        }
    }
}
