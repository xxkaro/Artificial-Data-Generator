import DataImporter.*;
import DataSaver.Saver;
import PersonalDataGenerator.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        System.out.println("generator");
        Generator generator = new Generator();
        Importer importer = new Importer();
        importer.Import(generator);
        List<Person> personList = new ArrayList<>();

        int numPeople = 20;
        for (int i = 0; i < numPeople; i++) {

            Person person = generator.GenerateRandomPerson(true, Gender.FEMALE);
            personList.add(person);
            System.out.println(person);

        }

    }
}
