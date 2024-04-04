package DataImporter;
import DataStructures.*;
import java.io.FileInputStream;
import java.io.IOException;
import DataStructures.fileName;
import PersonalDataGenerator.Generator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static java.lang.Math.round;

/**
 * provides methods to import data for generating Person objects from XLSX files
 */

public class Importer implements InterfaceImporter{
    /**
     *  Reads data from an Excel file, processes rows based on the file type
     *  and adds relevant information to a data generator.
     *
     * @param fileName Name of the file to read from; variable has assigned values
     *                 (for more check DataStructures > Config)
     * @param generator Generator object to which we add imported data.
     */
    public void importExcelFile(fileName fileName, Generator generator) {
        try {
            FileInputStream file = new FileInputStream(getFullPath(fileNameMapper.mapFileNameToFilePath(fileName)));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);


            for (Row row : sheet) {
                switch (fileName) {
                    case maleNames:
                    case femaleNames:
                    case maleSurnames:
                    case femaleSurnames:
                        if (row.getCell(0) != null && row.getCell(1) != null) {
                            for(int i = 0; i < round(row.getCell(1).getNumericCellValue() / 10); i++) {
                                generator.AddData(fileName, row.getCell(0).getStringCellValue());
                            }
                        }
                        break;
                    case postalCodesAndCities:
                        if (row.getCell(0) != null && row.getCell(1) != null) {
                            generator.AddData(fileName, row.getCell(0).getStringCellValue() + " " + row.getCell(1).getStringCellValue());
                        }
                        break;
                    case streets:
                        if (row.getCell(0) != null && row.getCell(1) != null) {
                            if(row.getCell(3) == null) {
                                generator.AddData(fileName, row.getCell(0).getStringCellValue() + row.getCell(1).getStringCellValue());
                            } else {
                                generator.AddData(fileName, row.getCell(0).getStringCellValue() + " " + row.getCell(1).getStringCellValue());
                            }
                        }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds full path to the files we want to read
     *
     * @param fileName Name of the file to read from; variable has assigned values
     *                 (for more check DataStructures > Config)
     */
    public static String getFullPath(String fileName) throws IOException {
        String path = new java.io.File(".").getCanonicalPath();
        return path + Config.inputDataFolder + fileName;
    }

    /**
     * Imports all the data from provided files to generator.
     *
     * @param generator Generator object to which we add imported data.
     *
     */
    public void Import(Generator generator) {
        importExcelFile(fileName.maleNames, generator);
        importExcelFile(fileName.femaleNames, generator);
        importExcelFile(fileName.femaleSurnames, generator);
        importExcelFile(fileName.maleSurnames, generator);
        importExcelFile(fileName.postalCodesAndCities, generator);
        importExcelFile(fileName.streets, generator);
    }
}
