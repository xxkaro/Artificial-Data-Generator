package PersonalDataGenerator;

import java.time.LocalDate;
import java.util.Date;

public class Person {
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String idNumber;
    private String pesel;
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;

    // constructor
    public Person(String name, String surname, LocalDate birthDate, String pesel, String idNumber,
                  String postalCodeAndCity, String street, String houseNumber, Gender gender) {
        this.gender = gender;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.idNumber = idNumber;
        this.street = street;
        String[] splitedPostalCodeAndCity = postalCodeAndCity.split("\\s", 2);
        this.city = splitedPostalCodeAndCity[1];
        this.postalCode = splitedPostalCodeAndCity[0];
        this.houseNumber = houseNumber;
    }

    // toString
    @Override
    public String toString() {
        return "PersonalDataGenerator.Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", idNumber='" + idNumber + '\'' +
                ", pesel='" + pesel + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", house number='" + houseNumber + '\'' +
                '}';
    }

    // getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }
}