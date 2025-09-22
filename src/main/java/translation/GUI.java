package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        SwingUtilities.invokeLater(() -> {


            JPanel languagePanel = new JPanel();
            JComboBox<String> languageComboBox = new JComboBox<>();
            languagePanel.add(new JLabel("Language:"));
            for(String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            String[] items = new String[translator.getCountryCodes().size()];

            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryConverter.fromCountryCode(countryCode);
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(scrollPane, 1);



            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    String language = languageConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                    String country = countryConverter.fromCountry(list.getSelectedValue()).toLowerCase();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    System.out.println(country);
                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }
            });
            // add listener for when an item is selected.
            languageComboBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {



                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String language = languageConverter.fromLanguage(languageComboBox.getSelectedItem().toString());
                        String country = countryConverter.fromCountry(list.getSelectedValue()).toLowerCase();

                        // for now, just using our simple translator, but
                        // we'll need to use the real JSON version later.
                        System.out.println(country);
                        String result = translator.translate(country, language);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);
                   }
                }


            });


            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);


            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
