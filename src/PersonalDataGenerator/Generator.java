package PersonalDataGenerator;
import DataStructures.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class Generator{
    private final ArrayList<String> maleNames = new ArrayList();
    private final ArrayList<String> femaleNames = new ArrayList();
    private final ArrayList<String> maleSurnames = new ArrayList();
    private final ArrayList<String> femaleSurnames = new ArrayList();
    private final ArrayList<String> postalCodesAndCities = new ArrayList();
    private final ArrayList<String> streets = new ArrayList();
    private boolean over18;

    /**
     *  Adds data to declared Arrays.
     *
     * @param fileName Name of the file to read from; variable has assigned values
     *                 (for more check DataStructures > Config)
     * @param data Data to be added to Arrays.
     */
    public void AddData(fileName fileName, String data) {
        switch (fileName) {
            case maleNames -> maleNames.add(data);
            case femaleNames -> femaleNames.add(data);
            case maleSurnames -> maleSurnames.add(data);
            case femaleSurnames -> femaleSurnames.add(data);
            case postalCodesAndCities -> postalCodesAndCities.add(data);
            case streets -> streets.add(data);
        }
    }

    // Generates random city and it's postal code.
    private String GetRandomCityAndCode() {
        return postalCodesAndCities.get(new Random().nextInt(postalCodesAndCities.size()));
    }

    // Generates random street name.
    private String GetRandomStreet() {
        return streets.get(new Random().nextInt(streets.size()));
    }

    private String GetRandomName(Gender c) {
        if (c == Gender.MALE) {
            return maleNames.get(new Random().nextInt(maleNames.size()));
        } else {
            return femaleNames.get(new Random().nextInt(femaleNames.size()));
        }
    }

    /**
     *  Generates random Surname for given sex.
     *
     * @param gender Gender (enum)
     */
    private String GetRandomSurname(Gender gender) {
        if (gender == Gender.MALE) {
            return maleSurnames.get(new Random().nextInt(maleSurnames.size()));
        } else {
            return femaleSurnames.get(new Random().nextInt(femaleSurnames.size()));
        }
    }

    // Generates gender
    private Gender GetRandomGender() {
        return (new Random().nextBoolean()) ? Gender.MALE : Gender.FEMALE;
    }

    /**
     *  Generates random person.
     *
     * @param over18 should the generated Person be over 18
     * @param gender_opt what gender should be the generated Person
     *
     */
    public Person GenerateRandomPerson(boolean over18, Gender gender_opt) {
        var gender = GetRandomGender();
        if (gender_opt != null){
            gender = gender_opt;}
        var name = GetRandomName(gender);
        var surname = GetRandomSurname(gender);
        var birthDate = BirthDateGenerator.generateRandomDate(over18);
        var pesel = PeselGenerator.generatePesel(birthDate, gender);
        var idNumber = IdNumberGenerator.generate();
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 13) {
            idNumber = "";
        } else if (age >= 13 && age < 18) {
            // Clear idNumber with a 50/50 chance
            if (new Random().nextBoolean()) {
                idNumber = "";
            }
        }
        var postalCodeAndCity = GetRandomCityAndCode();
        var street = GetRandomStreet();
        var houseNumber = HouseNumberGenerator.generate();
        return new Person(name, surname, birthDate, pesel, idNumber, postalCodeAndCity, street, houseNumber, gender);
    }

}

