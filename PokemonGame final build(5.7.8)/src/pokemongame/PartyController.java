/*
 * Programer: Zain
 * class: PartyController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: deals with the aspects of party(6 battle pokemon) that player uses
 */
package pokemongame;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Zain
 */
public class PartyController implements Initializable {

    //text
    @FXML
    private Text txt_Prty;
    @FXML
    private Text txt_Box;
    @FXML
    private Text txt_Msg;

    //Observable Lists
    @FXML
    private ObservableList<String> prty = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> box = FXCollections.observableArrayList();

    //Combobox
    @FXML
    private ComboBox box_Prty;
    @FXML
    private ComboBox box_Box;

    private int boxNum = 1;

    private SceneHandler scene = new SceneHandler("PokemonGame.fxml");
    private Player player;

    private String pokemonPrty;
    private String pokemonBox;

    private boolean set = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //declare tokenize array
        String[] tokens_Box;

        //declare and initalize files party(pokemon squad), player (money), saves (all pokemon)
        File prty_File = new File("player//Party.txt");
        File plyr_File = new File("player//Player.txt");
        File box_File = new File("saves//box" + boxNum + ".txt");
        try {
            //player pokemon object created using player files 
            player = new Player(plyr_File, prty_File);
        } catch (IOException ex) {
            Logger.getLogger(PartyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Scanner box_Input = new Scanner(box_File);

            //
            tokens_Box = box_Input.nextLine().split(";");
            for (String p : tokens_Box) {
                box.add(p);
            }
            box_Input.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PartyController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //add pokemon to party
        for (String p : player.getPartyArray()) {
            prty.add(p);
        }

        //Set combobox
        box_Prty.setItems(prty);
        box_Box.setItems(box);

    }

    /**
     * method Remove - removes a pokemon from box
     *
     * @param event
     * @throws FileNotFoundException
     * @throws IOException
     */
    @FXML
    public void Remove(ActionEvent event) throws FileNotFoundException, IOException {
        if (set) {
            if (box_Prty.getValue() == null) {

                //user error message
                txt_Msg.setText("Error pokemon not seletect");
            } else {
                txt_Msg.setText(player.removePokemon(pokemonPrty));
            }
        }
        update();
    }

    /**
     *
     * @param event
     * @throws FileNotFoundException
     * @throws IOException
     */
    @FXML
    public void Add(ActionEvent event) throws FileNotFoundException, IOException {
        if (set) {
            if (box_Box.getValue() == null) {

                //error message
                txt_Msg.setText("Error pokemon from box not seletect");
            } else {
                player.addParty(pokemonBox);

                //verification message
                txt_Msg.setText(pokemonBox + " Has been added");
            }
        }
        update();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Back(ActionEvent event) throws IOException {
        scene.changeScene(event);
    }

    /**
     * method BoxSwitch
     *
     * @param event
     * @throws FileNotFoundException
     * @throws IOException
     */
    @FXML
    public void BoxSwitch(ActionEvent event) throws FileNotFoundException, IOException {
        update();

        if (boxNum < 3) {
            boxNum++;
        } else {
            boxNum = 1;
        }
    }

    /**
     * method Set
     *
     * @param event
     * @throws FileNotFoundException
     * @throws IOException
     */
    @FXML
    public void Set(ActionEvent event) throws FileNotFoundException, IOException {

        // errror/ verification messages for user to tell them what is needed to proceed
        if (box_Prty.getValue() == null && box_Box.getValue() == null) {
            txt_Msg.setText("No pokemon have been set");
            set = false;
        } else if (box_Box.getValue() == null) {
            pokemonPrty = (String) box_Prty.getValue();
            txt_Msg.setText("Pokemon " + pokemonPrty + " has been set");
            set = true;
        } else if (box_Prty.getValue() == null) {
            pokemonBox = (String) box_Box.getValue();
            txt_Msg.setText("Pokemon " + pokemonBox + " has been set");
            set = true;
        } else {
            pokemonPrty = (String) box_Prty.getValue();
            pokemonBox = (String) box_Box.getValue();
            txt_Msg.setText("Both pokemon have been set");
            set = true;
        }
    }
//    

    /**
     * method update -
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void update() throws FileNotFoundException, IOException {
        String[] tokens_Box;

        //Read files for box and split to tokens
        File box_File = new File("saves//box" + boxNum + ".txt");
        Scanner box_Input = new Scanner(box_File);
        tokens_Box = box_Input.nextLine().split(";");

        //Clear user box arraylists
        prty.clear();
        box.clear();

        //Set set to false
        set = false;

        //Update arrays
        for (String p : player.getPartyArray()) {
            prty.add(p);
        }

        for (String p : tokens_Box) {
            box.add(p);
        }

        //Set combobox
        box_Prty.setItems(prty);
        box_Box.setItems(box);

        //Close box file
        box_Input.close();

        //saves player party
        player.saveParty();

    }
}
