package App;

import DataImporter.Importer;
import DataSaver.Saver;
import PersonalDataGenerator.Gender;
import PersonalDataGenerator.Generator;
import PersonalDataGenerator.Person;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the application for generating personal data.
 */
public class GeneratorApp {
    boolean isGenerated = false; // Flag to check if data is generated
    private JTable dataTable; // Table to display the data
    private DefaultTableModel tableModel;

    /**
     * Constructor for the GeneratorApp class.
     * It initializes the GUI components and sets up the event handlers.
     */
    public GeneratorApp() {
        // Setting up the main frame
        JFrame frame = new JFrame("Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //LOADING PANEL
        JPanel loadingPanel = new JPanel();
        loadingPanel.add(new JLabel("Waiting for data..."));
        frame.add(loadingPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        //DATA LOADING
        Generator generator = new Generator();
        Importer importer = new Importer();
        Saver saver = new Saver();
        importer.Import(generator);
        List<Person> personList = new ArrayList<>();

        //ACTION PANEL
        loadingPanel.removeAll();
        loadingPanel.repaint();

        //MENUBAR
        JMenuBar menuBar = new JMenuBar();
        JMenu menuExport = new JMenu("Export");
        JMenuItem menuItemExportToCsv = new JMenuItem("Export to CSV");
        JMenuItem menuItemExportToXlsx = new JMenuItem("Export to XLSX");
        menuExport.add(menuItemExportToCsv);
        menuExport.add(menuItemExportToXlsx);
        menuBar.add(menuExport);
        frame.setJMenuBar(menuBar);

        // Action listeners for menu items
        menuItemExportToCsv.addActionListener(e -> {
            if (isGenerated) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify location to save CSV file");
                fileChooser.showSaveDialog(null);
                File file = fileChooser.getSelectedFile();
                saver.saveToCsv(personList, file.getAbsolutePath() + ".csv");
            } else {
                JOptionPane.showMessageDialog(frame, "Please generate data first");
            }

        });

        menuItemExportToXlsx.addActionListener(e -> {
            if (isGenerated) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify location to save XLSX file");
                fileChooser.showSaveDialog(null);
                File file = fileChooser.getSelectedFile();
                saver.saveToXLSX(personList, file.getAbsolutePath() + ".xlsx");
            } else {
                JOptionPane.showMessageDialog(frame, "Please generate data first");
            }
        });

        //input field for number of people to generate
        JPanel fieldPanel = new JPanel();
        JLabel numberOfPeopleLabel = new JLabel("Number of people to generate: ");
        JTextField numberOfPeopleInputField = new JTextField();
        numberOfPeopleInputField.setColumns(10);
        numberOfPeopleInputField.setText("10000");
        numberOfPeopleInputField.setEditable(true);
        fieldPanel.add(numberOfPeopleLabel);
        fieldPanel.add(numberOfPeopleInputField);

        //LABEL
        JLabel dataGeneratedLabel = new JLabel("");
        JPanel labelPanel = new JPanel();
        labelPanel.add(dataGeneratedLabel);

        //CHECKBOX OVER 18
        JCheckBox over18CheckBox;
        over18CheckBox = new JCheckBox("Generate over 18 only");

        // TABLE PANEL
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("ID Number");
        tableModel.addColumn("Birth Date");
        tableModel.addColumn("PESEL");
        tableModel.addColumn("City");
        tableModel.addColumn("Postal Code");
        tableModel.addColumn("Street");
        tableModel.addColumn("House Number");

        dataTable = new JTable(tableModel);
        dataTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create GridBagConstraints for vertical stacking - options panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);


        // BUTTONS
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton button = new JButton("Generate");

        //DROPDOWN
        String[] genderOptions = {"Both", "Only Male", "Only Female"};
        JComboBox<String> genderDropdown = new JComboBox<>(genderOptions);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Select Gender");
        genderDropdown.setBorder(titledBorder);

        // Action listener for the generate button
        button.addActionListener(e -> {

            personList.clear();
            dataGeneratedLabel.setText("Generating data...");

            button.setEnabled(false);
            over18CheckBox.setEnabled(false);
            genderDropdown.setEnabled(false);
            numberOfPeopleInputField.setEnabled(false);

            try {
                int numPeople = Integer.parseInt(numberOfPeopleInputField.getText());
                boolean over18 = over18CheckBox.isSelected();
                Gender gender = getSelectedGender(genderDropdown.getSelectedItem().toString());
                if (numPeople < 0) {

                    throw new NumberFormatException();
                }
                for (int i = 0; i < numPeople; i++) {
                    Person person = generator.GenerateRandomPerson(over18, gender);
                    personList.add(person);
                }

                updateTable(personList);
                isGenerated = true;
                dataGeneratedLabel.setText("Data generated, download it from the menu bar");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number");
            }
            button.setEnabled(true);
            over18CheckBox.setEnabled(true);
            genderDropdown.setEnabled(true);
            numberOfPeopleInputField.setEnabled(true);


        });

        // Add components to the button panel
        buttonPanel.add(over18CheckBox, gbc);
        buttonPanel.add(genderDropdown, gbc);
        button.setBackground(Color.ORANGE);
        buttonPanel.add(button, gbc);


        // frame layout
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(fieldPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(labelPanel, BorderLayout.SOUTH);
        frame.revalidate();
    }

    /**
     * Method to update the table with new data.
     *
     * @param personList List of Person objects to be displayed in the table.
     */
    private void updateTable(List<Person> personList) {
        // Clear existing data in the table
        tableModel.setRowCount(0);

        // Add the new data to the table
        for (Person person : personList) {
            tableModel.addRow(new Object[]{person.getName(), person.getSurname(), person.getIdNumber(), person.getBirthDate(), person.getPesel(), person.getCity(), person.getPostalCode(), person.getStreet(), person.getHouseNumber()});
        }
    }

    /**
     * Method to get the selected gender from the dropdown.
     *
     * @param selectedItem Selected item from the dropdown.
     * @return Gender enum corresponding to the selected item.
     */
    private Gender getSelectedGender(String selectedItem) {
        switch (selectedItem) {
            case "Only Male":
                return Gender.MALE;
            case "Only Female":
                return Gender.FEMALE;
            default:
                return null; // Both or unknown option
        }
    }

    /**
     * Main method to run the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new GeneratorApp();
    }


}
