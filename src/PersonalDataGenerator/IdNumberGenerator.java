package PersonalDataGenerator;

import java.util.Random;

/**
 * Generates random ID numbers with control digits.
 */
public class IdNumberGenerator {

    /**
     * Generates a complete random ID number with control digits.
     * @return String representing the generated ID number
     */
    public static String generate() {

        String id = "";
        id = generateSerialNumber();
        String idNumbers3 = generateIdNumbers3();
        String idNumbers2 = generateIdNumbers2();
        char controlNumber = generateControlNumber(id, idNumbers3, idNumbers2);

        id += idNumbers3;
        id += controlNumber;
        id += idNumbers2;

        return id;
    }

    /**
     * Generates a random serial number.
     * @return String representing the generated serial number
     */
    private static String generateSerialNumber() {
        StringBuilder serialNumber = new StringBuilder(3);

        Random random = new Random();

        for (int i = 0; i < 3 ; i++) {
            char randomLetter = (char) (random.nextInt(26) + 'A');
            serialNumber.append(randomLetter);
        }
        return serialNumber.toString();
    }

    /**
     * Generates a random three-digit number.
     * @return String representing the generated three-digit number
     */
    private static String generateIdNumbers3() {
        Random random = new Random();
        int number = random.nextInt(1000); // Range from 0 to 999 - we want 3 digits
        return String.format("%03d", number);
    }

    /**
     * Generates a random two-digit number.
     * @return String representing the generated two-digit number
     */
    private static String generateIdNumbers2() {
        Random random = new Random();
        int number = random.nextInt(100); // Range from 0 to 99 - we want 2 digits
        return String.format("%02d", number);
    }

    /**
     * Generates a control number based on the generated ID components.
     * @param id the complete ID without the control digit
     * @param idNumbers3 the generated three-digit number
     * @param idNumbers2 the generated two-digit number
     * @return char representing the calculated control number
     */
    private static char generateControlNumber(String id, String idNumbers3, String idNumbers2) {
        int checkSum = 0;
        int controlNum;

        char[] idArray = id.toCharArray();
        checkSum += 7 * ((int) idArray[0] - 55);
        checkSum += 3 * ((int) idArray[1] - 55);
        checkSum += 1 * ((int) idArray[2] - 55);

        char[] idArray3 = idNumbers3.toCharArray();
        checkSum += 9 * Character.getNumericValue(idArray3[0]);
        checkSum += 7 * Character.getNumericValue(idArray3[1]);
        checkSum += 3 * Character.getNumericValue(idArray3[2]);

        char[] idArray2 = idNumbers2.toCharArray();
        checkSum += 7 * Character.getNumericValue(idArray2[0]);
        checkSum += 3 * Character.getNumericValue(idArray2[1]);

        if (checkSum % 10 == 0) {
            controlNum = 0;
        } else {
            controlNum = 10 - (checkSum % 10);
        }

        return (char) (controlNum + (int)'0');
    }
}
