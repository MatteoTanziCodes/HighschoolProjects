/*
 * Programer: Zain
 * class: GeneteicSequenceController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Handles genetic sequence - breeds new pokemon
 */
package pokemongame;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Zain
 */
public class GeneticSequenceController implements Initializable {

    //declares and initalizes pokemon box used for future use
    private String box = "Box1";
    private int box_num = 1;

    // declares genetic variables
    private ArrayList<Pokemon> stored_Pokemon = new ArrayList<>();
    private boolean mom_Set, dad_Set, child_Set;

    //Pokemon
    Pokemon mom_Pokemon;
    Pokemon dad_Pokemon;
    Pokemon child_Pokemon;

    //Displayed Arrays 
    private ArrayList<String> moves = new ArrayList<>();
    @FXML
    ObservableList<String> storage;
    @FXML
    ObservableList<String> storage_Male
            = FXCollections.observableArrayList();
    @FXML
    ObservableList<String> storage_Female
            = FXCollections.observableArrayList();

    //Labels
    @FXML
    private Label lbl_Mom;
    @FXML
    private Label lbl_Dad;
    @FXML
    private Label lbl_Child;

    //Combo boxes
    @FXML
    private ComboBox box_Female;
    @FXML
    private ComboBox box_Male;

    //Buttons
    @FXML
    private Button btn_Box;

    //Text fields
    @FXML
    private TextField txt_Child;

    //Text 
    @FXML
    private Text msg_Text;

    private SceneHandler scene
            = new SceneHandler("PokemonGame.fxml");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initalize variables
        int count = 0; // Index for each pokmon in the array list

        //Initalize text for current storage box
        btn_Box.setText(box);

        //Read pokmon from storage
        try {
            storage = FXCollections.observableArrayList(read(box));
        } catch (IOException ex) {
            Logger.getLogger(GeneticSequenceController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        //Create a pokemon object for each pokemon in the file
        for (String n : storage) {
            File file = new File("pokemon//" + n + ".txt");
            try {
                stored_Pokemon.add(new Pokemon(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GeneticSequenceController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            //Test gender of pokemon and place in appropriate box
            if (stored_Pokemon.get(count).getGender()
                    .equalsIgnoreCase("Male")) {

                storage_Male.add(stored_Pokemon.get(count).getName());
            } else {
                storage_Female.add(stored_Pokemon.get(count).getName());
            }
            //Go to next index in array list of pokemon
            count++;
        }

        //update menue box with appropriate pokemon
        box_Female.setItems(storage_Female);
        box_Male.setItems(storage_Male);
    }

    @FXML
    /**
     * setMom - FMXL button to set the selected pokemon as the mom
     */
    private void setMom(ActionEvent event) throws IOException {

        //If no pokemon detected, give error
        if (box_Female.getValue() == null
                || ((String) box_Female.getValue()).equals("")) {
            mom_Set = false;
            msg_Text.setText("Error, no value detected");
        } //Else create pokemon object for the slected dad
        else {
            mom_Set = true;

            //display verification of mom to user
            msg_Text.setText("Mom set as: " + box_Female.getValue());
            lbl_Mom.setText((String) box_Female.getValue());

            File file = new File("pokemon//"
                    + (String) box_Female.getValue() + ".txt");
            mom_Pokemon = new Pokemon(file);
        }
    }

    @FXML
    /**
     * setDad - FXML button to set the selected pokemon as the dad
     */
    private void setDad(ActionEvent event) throws IOException {
        //If no pokemon detected, give error
        if (box_Male.getValue() == null
                || ((String) box_Male.getValue()).equals("")) {
            dad_Set = false;
            msg_Text.setText("Error, no value detected");

        } //Else create pokemon object for the selected dad
        else {
            dad_Set = true;

            msg_Text.setText("Dad set as: " + box_Male.getValue());
            lbl_Dad.setText((String) box_Male.getValue());

            File file = new File("pokemon//"
                    + (String) box_Male.getValue() + ".txt");
            dad_Pokemon = new Pokemon(file);
        }
    }

    @FXML
    /**
     * setChild - FXML button to set the child name
     */
    private void setChild(ActionEvent event) {
        //Display error if no name is detected
        if (txt_Child.getText().charAt(0) == (' ')) {
            dad_Set = false;
            msg_Text.setText("Error, no value detected");
        } else {
            //Set the child name to the inputed value
            child_Set = true;
            msg_Text.setText("child_Pokemon set "
                    + txt_Child.getText());

            //Change label to selected name
            lbl_Child.setText(txt_Child.getText());

        }
    }

    @FXML
    /**
     * geneticSequence - FXML button which creates the child
     */
    private void geneticSequence(ActionEvent event) throws IOException {
        //Check to see if all feilds are set
        if (mom_Set && dad_Set && child_Set) {
            //Save new pokemon into box file
            FileWriter fWrite = new FileWriter("saves//" + box
                    + ".txt", false);
            PrintWriter saver = new PrintWriter(fWrite);

            FileWriter fWrite2 = new FileWriter("player//Party.txt", true);
            PrintWriter saver2 = new PrintWriter(fWrite2);

            //print new pokemon onto file 
            for (String p : storage) {
                saver.print(p + ";");
            }

            //Add child_Pokemon to box
            saver.print(txt_Child.getText() + ";");
            saver2.print(txt_Child.getText() + ";");

            //Dsiplay message 
            msg_Text.setText("Something Special Occured");
            child_Pokemon = new Pokemon(txt_Child.getText(),
                    mom_Pokemon, dad_Pokemon);
            saver.close();
            saver2.close();

            //Save the file for the child pokemon
            child_Pokemon.save();

            //Clear Feilds
            mom_Set = false;
            dad_Set = false;
            child_Set = false;

            //reset lables 
            lbl_Mom.setText("Mom Name");
            lbl_Dad.setText("Dad Name");
            lbl_Child.setText("Child Name");
            txt_Child.setText(null);

            //Update the drop down boxes
            update();

        } //Display appropriate error messages
        else if ((!mom_Set) && (dad_Set && child_Set)) {
            msg_Text.setText("Error, mom_Pokemon not set");
        } else if ((!dad_Set) && (mom_Set && child_Set)) {
            msg_Text.setText("Error, dad_Pokemon not set");
        } else if ((!child_Set) && (mom_Set && dad_Set)) {
            msg_Text.setText("Error, child_Pokemon not set");
        } else if (!(mom_Set && dad_Set && child_Set)) {
            msg_Text.setText("Error, parents and child_Pokemon not set");
        }
    }

    @FXML
    /**
     * back - FXML button to return to the main page
     */
    private void back(ActionEvent event) throws IOException {
        scene.changeScene(event);
    }

    @FXML
    /**
     * setBox - FXML button to change storage box
     */
    private void setBox(ActionEvent event) throws IOException {
        //Change box nunmber
        if (box_num < 3) {
            box_num++;
        } else {
            box_num = 1;
        }

        //use box with apprpriate number
        box = "Box" + box_num;

        btn_Box.setText(box);

        //Update drop down boxes 
        update();
    }

    @FXML
    /**
     * update - updates the drop down boxes with the appropriate pokemon
     */
    private void update() throws IOException {
        //Initalize variables 
        int count = 0;//Stores index location for array list

        //Store the pokemon name from the desired box
        storage = FXCollections.observableArrayList(read(box));

        //Clear storage boxes
        storage_Male.clear();
        storage_Female.clear();

        //Clear pokemon array
        stored_Pokemon.clear();

        //Create a pokemon object for each pokemon in the box
        for (String n : storage) {
            //Read the pokemon file
            File file = new File("pokemon//" + n + ".txt");

            //Add the pokemon to the array list
            stored_Pokemon.add(new Pokemon(file));

            //Place the pokemon in the correct drop down box based off gender
            if (stored_Pokemon.get(count).getGender()
                    .equalsIgnoreCase("Male")) {
                storage_Male.add(stored_Pokemon.get(count).getName());
            } else {
                storage_Female.add(stored_Pokemon.get(count).getName());
            }
            count++;
        }

        //Update the drop down boxes
        box_Female.setItems(storage_Female);
        box_Male.setItems(storage_Male);
    }

    /**
     * read - reads the pokemon storage box return return an array list of all
     * the pokemon in the box
     */
    private String[] read(String box) throws IOException {
        //Read specified storage box
        File file = new File("saves//" + box + ".txt");
        Scanner inputFile = new Scanner(file);

        //Split each name into tokens
        String[] tokens = inputFile.nextLine().split(";");

        //Close file
        inputFile.close();

        //Return the tokens
        return tokens;

    }
}
