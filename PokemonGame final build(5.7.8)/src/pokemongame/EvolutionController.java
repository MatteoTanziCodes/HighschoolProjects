/*
 * Programer: Matteo
 * class: EvolutionController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Handles Evolution sequence - Evolves pokemons
 */
package pokemongame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static pokemongame.PokemonGameController.movesList;

/**
 * FXML Controller class
 *
 * @author annettetanzi
 */
public class EvolutionController implements Initializable {

  //Set Party
  private File Party = new File("player//Party.txt");

  //Combo Box
  @FXML
  private ComboBox evol_comboBox;

  @FXML
  private ComboBox evol_comboBox2;

  @FXML
  private ComboBox evol_comboBox3;

  //TextField
  @FXML
  private TextField evol_textfield;

  //Label
  @FXML
  private Label info;

  //Object
  private Pokemon poke;

  //Observable lists
  ObservableList<String> storage;

  ObservableList<String> moves_storage;

  ObservableList<String> moves_storage2;

  //Arraylists
  ArrayList<Moves> nums = new ArrayList<>();

  ArrayList<String> pokeNames = new ArrayList<>();

  //Randomizer
  Random rand = new Random();

  //Scene handler
  private SceneHandler scene = new SceneHandler("PokemonGame.fxml");

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    //initialize with charmander
    try {
      File file = new File("pokemon//Charmander.txt");
      poke = new Pokemon(file);
      update();
    } catch (IOException ex) {
      Logger.getLogger(EvolutionController.class.getName())
          .log(Level.SEVERE, null, ex);
    }
  }

  /**
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void back(ActionEvent event) throws IOException {
    scene.changeScene(event); // change scene
  }

  /**
   * ChangeStats - updates the stats of the pokemon
   */
  private void changeStats() {

    //If else which upgrades the statistics of the pokemon
    if (poke.getLevel() < 5) {
      poke.setLevel(1);
      poke.setPower(poke.getPower() + 5);
      poke.setAccuracy(poke.getAccuracy() + 10);
      poke.setMovePP1(poke.getMovePP1() + 15);
      poke.setMovePP2(poke.getMovePP2() + 15);
      poke.setMovePP3(poke.getMovePP3() + 15);
      poke.setMovePP4(poke.getMovePP4() + 15);
      poke.setEvolution(2);
    } else if (poke.getLevel() < 15) {
      poke.setLevel(1);
      poke.setPower(poke.getPower() + 15);
      poke.setAccuracy(poke.getAccuracy() + 20);
      poke.setMovePP1(poke.getMovePP1() + 25);
      poke.setMovePP2(poke.getMovePP2() + 25);
      poke.setMovePP3(poke.getMovePP3() + 25);
      poke.setMovePP4(poke.getMovePP4() + 25);
      poke.setEvolution(3);
    } else {
      poke.setLevel(1);
      poke.setPower(poke.getPower() + 35);
      poke.setAccuracy(poke.getAccuracy() + 40);
      poke.setMovePP1(poke.getMovePP1() + 45);
      poke.setMovePP2(poke.getMovePP2() + 45);
      poke.setMovePP3(poke.getMovePP3() + 45);
      poke.setMovePP4(poke.getMovePP4() + 45);
      poke.setEvolution(4);
    }
  }

  /**
   * ChangeMove - Changes the pokemon's move
   *
   * @throws IOException
   */
  private void changeMove() throws IOException {

    //change pokemon move
    switch (movesList.indexOf(evol_comboBox2.getValue())) {
      case 0:
        poke.setMove1(movesList.indexOf(evol_comboBox3.getValue()));
        break;
      case 1:
        poke.setMove2(movesList.indexOf(evol_comboBox3.getValue()));
        break;
      case 2:
        poke.setMove3(movesList.indexOf(evol_comboBox3.getValue()));
        break;
      default:
        poke.setMove4(movesList.indexOf(evol_comboBox3.getValue()));
        break;
    }

  }

  /**
   * ChangeName - change the pokemons name
   */
  private void changeName() {
    //Sets new name of evolved pokemon
    poke.setName(evol_textfield.getText());
  }

  /**
   * savePoke - saves the pokemon data into the file
   *
   * @throws IOException
   */
  @FXML
  private void savePoke() throws IOException {

    if (evol_comboBox2.getValue() == null
        || evol_comboBox3.getValue() == null) {

      if (poke.getLevel() > 1) {
        evolveMethod();
        
        //sets verification message to player
        info.setText("Your PokeMon has Evolved");
      } else {
        info.setText("Please upgrade your pokemon!");
      }

    } else if (evol_textfield.getText().equals("")) {

      if (poke.getLevel() > 1) {
        evolveMethod();
        
         //sets verification message to player
        info.setText("Your PokeMon has Evolved");
        
      } else {
        info.setText("Please upgrade your pokemon!");
      }

    } else {

      if (poke.getLevel() > 1) {
        evolveMethod();
        
         //sets verification message to player
        info.setText("Your PokeMon has Evolved");
      } else {
        info.setText("Please upgrade your pokemon!");
      }
    }
  }

  /**
   * update - Update FXML visuals
   *
   * @throws IOException
   */
  private void update() throws IOException {

    //Set combobox 1
    storage = FXCollections.observableArrayList(read());
    evol_comboBox.setItems(storage);

    //Set combobox 2
    moves_storage = FXCollections.observableArrayList(read2());
    evol_comboBox3.setItems(moves_storage);

    //Set combobox 3
    moves_storage2 = FXCollections.observableArrayList(read3());
    evol_comboBox2.setItems(moves_storage2);

  }

  /**
   * read - Reads party file , party file stores user's maximum 6 pokemon 
   * for battle use
   *
   * @param box
   * @return
   * @throws IOException
   */
  private String[] read() throws IOException {

    //tokenize pokemon stored in user part
    String[] tokens;
    try (Scanner inputFile = new Scanner(Party)) {
      tokens = inputFile.nextLine().split(";");
    }
    return tokens;

  }

  /**
   * read 2 - reads moves
   *
   * @return @throws IOException
   */
  private ArrayList read2() throws IOException {
      
    File file = new File("resources\\Moves.txt"); //set file
    Scanner inputFile = null;
    try {
      inputFile = new Scanner(file);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(PokemonGameController.class.getName())
          .log(Level.SEVERE, null, ex);
    }

    //initialize the arraylist of move objects 
    movesList = new ArrayList<>();
    
    //tokens array
    String[] tokens;

    //skips info line in text file
    String line = inputFile.nextLine();

    while (inputFile.hasNextLine()) {
      //inputs line from file into string variable
      line = inputFile.nextLine();

      //tokenizes string
      tokens = line.split(";");

      //adds m
      movesList.add(new Moves(tokens));
    }
    
    return movesList;
  }

  /**
   * read3 - Gets the pokemon's moves
   *
   * @return ArrayList of pokemon's moves @throws IOException
   */
  private ArrayList read3() throws IOException {
    
    //clear the moves list
    nums.clear();
    nums.add(movesList.get(poke.getMove1())); //add move 1
    nums.add(movesList.get(poke.getMove2())); //add move 2
    nums.add(movesList.get(poke.getMove3())); //add move 3
    nums.add(movesList.get(poke.getMove4())); //add move 4

    return nums; //return moves arraylist
  }

  /**
   * setPoke - changes the selected pokemon
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void setPoke(ActionEvent event) throws IOException {

    if (evol_comboBox.getValue() == null) {

      //error message for user  if combobox is empty
      info.setText("Error, no value detected");

    } else {
      //create new pokemon
      File file = new File("pokemon//"
          + (String) evol_comboBox.getValue() + ".txt");

      poke = new Pokemon(file);
    }
    update(); //run update method

  }

  private void pokemonSave() throws IOException {

      // intialize filewriter 
      FileWriter fwrite = new FileWriter("player\\Party.txt", true);
      try (final PrintWriter partyText = new PrintWriter(fwrite)) {
        partyText.print(evol_textfield.getText()+";"); //write pokemon in party
      }
    }
  private void evolveMethod() throws IOException{
  
        //Methods that run every evolution
        poke.setLevel(2); //set pokemon level to 2
        changeName(); //change the name
        changeMove(); //change moves
        changeStats(); //update stats
        pokemonSave(); //run method to save to party
        poke.saveNew();//save the pokemon
  }

  }

