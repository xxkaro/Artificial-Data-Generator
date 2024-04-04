package PersonalDataGenerator;
import java.util.Random;
import java.time.LocalDate;

/**
 * Provides methods to generate a PESEL number based on a given birthdate and gender.
 */

public class PeselGenerator {

    /**
     * Main method to generate a PESEL number.
     * @param birthDate
     * @param gender
     * @return
     */
    public static String generatePesel(LocalDate birthDate, Gender gender) {
        // first 6 digits of PESEL number based on birthdate
        int birthDateYear = birthDate.getYear();
        int birthDateMonth = birthDate.getMonthValue();
        int birthDayDay = birthDate.getDayOfMonth();

        // check for criteria - month digits for 21st century
        if (birthDateYear >= 2000 && birthDateYear <= 2099) {
            birthDateMonth += 20;
        }

        //next 4 digits of PESEL number based on gender
        Random random = new Random();
        int num1;
        num1 = random.nextInt(9000) + 1000;
        // check for criteria - odd number for female, even for male
        if (gender == Gender.FEMALE && num1 % 2 == 0) {
            num1++;
        } else if (gender == Gender.MALE && num1 % 2 != 0) {
            num1--;
        }

        //build PESEL base
        StringBuilder peselBuilder = new StringBuilder();
        peselBuilder.append(String.format("%02d", birthDateYear % 100));
        peselBuilder.append(String.format("%02d", birthDateMonth));
        peselBuilder.append(String.format("%02d", birthDayDay));
        peselBuilder.append(num1);

        // Calculate control number
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            int number = Character.getNumericValue(peselBuilder.charAt(i));
            sum += (number * weights[i]) % 10;
        }

        int controlNumber = ((10 - (sum % 10)) % 10);
        peselBuilder.append(controlNumber);

        return peselBuilder.toString();
    }
}