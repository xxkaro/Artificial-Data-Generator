package PersonalDataGenerator;

import java.time.LocalDate;
import java.util.Random;

import static DataStructures.Config.maxAge;

/**
 * Provides methods to generate a random birthdate.
 */
public class BirthDateGenerator {
    /**
     * Generates a random birthdate.
     * @param over18 if true, generated date will be for a person over 18 years old
     * @return LocalDate object with generated date
     */
    public static LocalDate generateRandomDate(boolean over18) {
        Random random = new Random();
        int birthYear = generateRandomYearOfBirth(over18);
        int birthMonth = random.nextInt(12) + 1;
        int maxDay = LocalDate.of(birthYear, birthMonth, 1).lengthOfMonth();
        int birthDay = random.nextInt(maxDay) + 1;

        LocalDate generatedDate = LocalDate.of(birthYear, birthMonth, birthDay);

        //double check if person is over 18
        if (over18) {
            LocalDate currentDate = LocalDate.now();
            LocalDate minimumDate = currentDate.minusYears(18);
            if (generatedDate.isAfter(minimumDate)) {
                return generateRandomDate(true);
            }
        }
        return generatedDate;
    }
    /**
     * Generates a random birth year with realistic probability.
     * @param over18 if true, generated year will be for a person over 18 years old
     * @return int with generated year
     */
    private static int generateRandomYearOfBirth(boolean over18) {
        Random random = new Random();
        int ageGroup;
        int currentYear = LocalDate.now().getYear();

        if (over18) {
            ageGroup = random.nextInt(83) + 18;
        } else {
            ageGroup = random.nextInt(100);
        }

        if (ageGroup < 18) {
            return currentYear - (random.nextInt(18));
        } else if (ageGroup < 35) {
            return currentYear - (random.nextInt(12) + 18);
        } else if (ageGroup < 53) {
            return currentYear - (random.nextInt(10) + 30);
        } else if (ageGroup < 69) {
            return currentYear - (random.nextInt(10) + 40);
        } else if (ageGroup < 85) {
            return currentYear - (random.nextInt(10) + 50);
        } else if (ageGroup < 100) {
            return currentYear - (random.nextInt(maxAge-60) + 60);
        } else {
            return currentYear - maxAge;
        }
    }
}