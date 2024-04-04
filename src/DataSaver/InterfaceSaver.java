package DataSaver;

import PersonalDataGenerator.Person;

import java.util.List;

/**
 * interface providing the methods for Save class
 */
public interface InterfaceSaver {


    /**
     * Saves the given list of Person objects to a CSV file at the specified file path.
     *
     * @param list The list of Person objects to be saved.
     * @param filePath The path of the CSV file where the data will be saved.
     */
    void saveToCsv(List<Person> list, String filePath);

    /**
     * Saves the given list of Person objects to an XLSX file at the specified file path.
     *
     * @param list The list of Person objects to be saved.
     * @param filePath The path of the XLSX file where the data will be saved.
     */
    void saveToXLSX(List<Person> list, String filePath);
}
