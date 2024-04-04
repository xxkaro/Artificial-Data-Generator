package DataStructures;

public class fileNameMapper {
    public static String mapFileNameToFilePath(fileName fileName)
    {
        switch (fileName) {
            case maleNames -> {
                return Config.maleNamesPath;
            }
            case maleSurnames -> {
                return Config.maleSurnamesPath;
            }
            case femaleNames -> {
                return Config.femaleNamesPath;
            }
            case femaleSurnames -> {
                return Config.femaleSurnamesPath;
            }
            case postalCodesAndCities -> {
                return Config.postalCodesAndCitiesPath;
            }
            case streets -> {
                return  Config.streetsPath;
            }
        }
        return null;
    }
}