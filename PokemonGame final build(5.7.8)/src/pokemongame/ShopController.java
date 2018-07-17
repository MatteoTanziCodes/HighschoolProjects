/*
 * Programer: Matteo
 * class: ShopController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Handles Evolution sequence - Evolves pokemons
 */
package pokemongame;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author annettetanzi
 */
public class ShopController implements Initializable {

    public ArrayList<String> party = new ArrayList<>();

    public ArrayList<String> bag = new ArrayList<>();

    private int cost = 0;

    private Player player;

    //Label
    @FXML
    private Label checkout_label;
    @FXML
    private Label pokelabel_label1;
    @FXML
    private Label pokelabel_label2;
    @FXML
    private Label pokelabel_label3;
    @FXML
    private Label pokelabel_label4;
    @FXML
    private Label pokelabel_label5;
    @FXML
    private Label pokelabel_label6;
    @FXML
    private Label pokelabel_label7;
    @FXML
    private Label pokelabel_label8;

    //CheckBox
    @FXML
    private CheckBox pokeCheck_CheckBox1;
    @FXML
    private CheckBox pokeCheck_CheckBox2;
    @FXML
    private CheckBox pokeCheck_CheckBox3;
    @FXML
    private CheckBox pokeCheck_CheckBox4;
    @FXML
    private CheckBox pokeCheck_CheckBox5;
    @FXML
    private CheckBox pokeCheck_CheckBox6;
    @FXML
    private CheckBox pokeCheck_CheckBox7;
    @FXML
    private CheckBox pokeCheck_CheckBox8;

    //GridPane
    @FXML
    private GridPane grid_layout;
    private GridPane grid_layout2;

    //Scene handler
    private SceneHandler scene = new SceneHandler("PokemonGame.fxml");

    //Set Boolean
    boolean remove = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //declares and intializes player stat info files 
        File prty_File = new File("player//Party.txt");
        File item_File = new File("player//Items.txt");
        File plyr_File = new File("player//Player.txt");

        try {
            player = new Player(plyr_File, prty_File);
        } catch (IOException ex) {
            Logger.getLogger(ShopController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        checkout_label.setText("Money: " + player.getPlayerMoney());
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        scene.changeScene(event); // change scene
    }

    /**
     * purchasePokemon method - check which pokemon item is selected by player
     * for purchase
     *
     * @return
     */
    private int purchasePokemon() {

        for (int i = 0; i < grid_layout.getChildren().size(); i++) {
            Node v = grid_layout.getChildren().get(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isSelected()) {
                    cost += 40;

                } else {
                }
            }
        }

        return cost;
    }

    /**
     * purchaseItem method - check which items player selected for purchase
     *
     * @return
     */
    private int purchaseItems() {

        for (int i = 0; i < grid_layout2.getChildren().size(); i++) {
            Node v = grid_layout2.getChildren().get(i);
            if (v instanceof CheckBox) {
                if (((CheckBox) v).isSelected()) {
                    cost += 30;
                } else {

                }
            }
        }

        return cost;
    }

    /**
     * method Purchase
     *
     * @throws IOException
     */
    @FXML
    private void purchase() throws IOException {

        if (pokeCheck_CheckBox1.isSelected() 
                || pokeCheck_CheckBox8.isSelected()
                || pokeCheck_CheckBox2.isSelected() 
                || pokeCheck_CheckBox3.isSelected()
                || pokeCheck_CheckBox4.isSelected() 
                || pokeCheck_CheckBox5.isSelected()
                || pokeCheck_CheckBox6.isSelected() 
                || pokeCheck_CheckBox7.isSelected()
                || pokeCheck_CheckBox8.isSelected()) {

            if (cost <= player.getPlayerMoney()) {
                if (player.getPartySize() < 6) {
                    transaction();
                    saveParty();
                } else {
                //error message to user if user has more than 6 pokemon
                    checkout_label.setText("Party size too large");
                    partySize();
                }

            } else {
        //error message if user does not have enough money for items chosen
                checkout_label.setText("Insufficient Funds");
            }

        } else {
            //asks user to choose items that they'd like to buy
            checkout_label.setText("Please Select Items");
        }

    }

    /**
     * method transaction
     *
     * @throws IOException
     */
    private void transaction() throws IOException {

        //Set Funds title
        checkout_label.setText("Current Funds: " + player.getPlayerMoney());
        //Check boxes that are selected
        if (pokeCheck_CheckBox1.isSelected()) {
            if (partySize() && calCost(100)) {
                player.addParty(pokelabel_label1.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox2.isSelected()) {
            if (partySize() && calCost(150)) {
                player.addParty(pokelabel_label2.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox3.isSelected()) {
            if (partySize() && calCost(200)) {
                player.addParty(pokelabel_label3.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox4.isSelected()) {
            if (partySize() && calCost(300)) {
                player.addParty(pokelabel_label4.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox5.isSelected()) {
            if (partySize() && calCost(400)) {
                player.addParty(pokelabel_label5.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox6.isSelected()) {
            if (partySize() && calCost(500)) {
                player.addParty(pokelabel_label6.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox7.isSelected()) {
            if (partySize() && calCost(1000)) {
                player.addParty(pokelabel_label7.getText().split(":")[0]);
            }
        }
        if (pokeCheck_CheckBox8.isSelected()) {
            if (partySize() && calCost(2000)) {
                player.addParty(pokelabel_label8.getText().split(":")[0]);
            }
        }

        player.savePLR(); //Save the player
        checkout_label.setText("Money: " + player.getPlayerMoney());
    }

    private boolean calCost(int n) {
        if (player.getPlayerMoney() < n) {

            //error message , not enough money 
            checkout_label.setText("Insufficient Funds");
            return false;
        } else {
            //verification message
            checkout_label.setText("Complete");

            //removes player 
            player.removePlayerMoney(n);
            return true;
        }
    }

    /**
     * method partySize - returns boolean false or true depending on if party is
     * full or not
     *
     * @return
     * @throws IOException
     */
    private boolean partySize() throws IOException {

        if (party.size() >= 6) {
            // error messgae, not enough space , only 6 pokemon
            checkout_label.setText("Sorry too many pokemon in party");
            return false;
        } else {
            return true;
        }

    }

    //saves player's party to file
    private void saveParty() throws IOException {
        player.saveParty();
    }

}
