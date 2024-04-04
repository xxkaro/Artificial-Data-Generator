package DataSaver;
import PersonalDataGenerator.Person;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * provides methods to save a list of Person objects to a CSV or XLSX file.
 */
public class Saver implements InterfaceSaver{

    /**
     * Helper method to write a row of data to a given row in a sheet.
     * @param row The row in the sheet where the data will be written.
     * @param data The data to be written to the row.
     */
    private static void writeRow(Row row, String[] data) {
        for (int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(data[i]);
        }
    }

    /**
     * Function to save data to a CSV file.
     * @param list The list of Person objects to be saved.
     * @param filePath The path of the CSV file where the data will be saved.
     */
    @Override
    public void saveToCsv(List<Person> list, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // write header
            String[] header = {"Name", "Surname", "Birth Date", "ID Number", "PESEL", "City", "Postal Code", "Street " +
                    "Name", "House Number"};
            writer.writeNext(header);

            // write data
            for (Person person : list) {
                String[] data = {person.getName(),
                        person.getSurname(),
                        String.valueOf(person.getBirthDate()),
                        String.valueOf(person.getIdNumber()),
                        String.valueOf(person.getPesel()),
                        String.valueOf(person.getCity()),
                        String.valueOf(person.getPostalCode()),
                        String.valueOf(person.getStreet()),
                        String.valueOf(person.getHouseNumber())};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to save data to an XLSX file.
     * @param list The list of Person objects to be saved.
     * @param filePath The path of the XLSX file where the data will be saved.
     */
    @Override
    public void saveToXLSX(List<Person> list, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PersonalDataGenerator.Persons");
            Row headerRow = sheet.createRow(0);
            String[] header = {"Name", "Surname", "Birth Date", "ID Number", "PESEL", "City", "Postal Code", "Street " +
                    "Name", "House Number"};
            writeRow(headerRow, header);

            int rowNum = 1;
            for (Person person : list) {
                Row row = sheet.createRow(rowNum++);
                String[] data = {person.getName(),
                        person.getSurname(),
                        String.valueOf(person.getBirthDate()),
                        String.valueOf(person.getIdNumber()),
                        String.valueOf(person.getPesel()),
                        String.valueOf(person.getCity()),
                        String.valueOf(person.getPostalCode()),
                        String.valueOf(person.getStreet()),
                        String.valueOf(person.getHouseNumber())};
                writeRow(row, data);
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
