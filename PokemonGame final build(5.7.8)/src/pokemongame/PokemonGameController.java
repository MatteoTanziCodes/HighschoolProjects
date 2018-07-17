/*
 * Programer: John,Zain,Matteo
 * class: PokemonGameController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: main hub of game
 */
package pokemongame;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Zain
 */
public class PokemonGameController implements Initializable {

  //Set MovesList
  public static ArrayList<Moves> movesList;
  //Set Count
  private int count = 0;

  //Set Saves
  private int saves;

  
//Labels
  @FXML
  private Label begin_label;
  @FXML
  private Label label;
  @FXML
  private Label label1;
  @FXML
  private Label label2;

  //Buttons
  @FXML
  private Button btn1;
  @FXML
  private Button btn2;
  @FXML
  private Button btn3;
  @FXML
  private Button begin_button;
  @FXML
  private Button load_button;
  
  //TextFeild
  @FXML
  private TextField home_textField;

   //toggle storage for button command
  private int count2 = 0;
  private int count3 = 0;
  
    private SceneHandler scene = new SceneHandler("PokemonGame.fxml");
  /**
   * method handleBeginAction - the starts "story of game"
   * player picks starter pokemon
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleBeginAction(ActionEvent event) throws IOException {
    switch (count) {

      //goes down cases , as much level select
      case 0:
        begin_label.setText("Please enter one of the following pokemon:");
        begin_button.setText("Continue");
        count++;
        break;
      case 1:
        begin_label.setText("-> Charmander -> Squirtle -> Bulbasaur");
        begin_button.setText("Submit");
        count++;
        break;
      case 2:
          
          if(home_textField.getText().equalsIgnoreCase("Charmander")
                  ||home_textField.getText().equalsIgnoreCase("Squirtle")
                  ||home_textField.getText().equalsIgnoreCase("Bulbsaur"))
          {
            //Re write the original file
            FileWriter fwrite = new FileWriter("player\\Party.txt", false);
            try (PrintWriter partyText = new PrintWriter(fwrite)) {
              partyText.print(home_textField.getText() + ";");
            }
            begin_label.setText("Go to battle!");
            count++;
          }
          else
          {
              begin_label.setText("Error that is not an option");
          }
        break;
      default:
        break;
    }

  }

  /**
   * method save - saves all player's stats for future game use after game is
   * closed
   *
   * @param event
   * @throws FileNotFoundException
   * @throws IOException
   */
  @FXML
  private void save(ActionEvent event) throws FileNotFoundException, IOException {
//declaring and initalizing player info files
    File file2 = new File("player//Party.txt");
    String[] tokens2;
    try (Scanner inputFile = new Scanner(file2)) {
      tokens2 = inputFile.nextLine().split(";");
    }

    File file3 = new File("player//Player.txt");
    String[] tokens3;
    try (Scanner inputFile = new Scanner(file3)) {
      tokens3 = inputFile.nextLine().split(";");
    }

    if (count3 == 0) {
      //label for save file # to save as new player save 
      begin_label.setText("Enter save file number(1/2/3/4)");
      count++;
    } else if (count3 == 1 && (home_textField.getText().equals("1"))
        || (home_textField.getText().equals("2"))
        || (home_textField.getText().equals("3"))
        || (home_textField.getText().equals("4"))) {
      saves = Integer.parseInt(home_textField.getText());
      
        FileWriter fwrite2 = new FileWriter("saves//save" + saves+".txt", false);
        try (final PrintWriter partyText = new PrintWriter(fwrite2)) {
          partyText.print("");
          partyText.close();
        }
      
      
      for (String tokens21 : tokens2) {
        FileWriter fwrite = new FileWriter("saves//save" + saves+".txt", true);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
          partyText.print(tokens21 + ";");
          partyText.close();
        }
      }
      for (String tokens31 : tokens3) {
        FileWriter fwrite = new FileWriter("saves//save" + saves+".txt", true);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
          partyText.print("\n" + tokens31);
          partyText.close();
        }
      }
    }
  }

  /**
   * method load - loads previously made save file
   * @param event
   * @throws FileNotFoundException
   * @throws IOException
   */
  @FXML
  private void load(ActionEvent event) throws FileNotFoundException, IOException {

    int num;

    //asks user which load file they would like to load
    if (count2 == 0) {
      restart(event);
      begin_label.setText("Enter which save:1,2,3,4");
      load_button.setText("Submit");
      count2++;
    } else if (count2 == 1 && ((home_textField.getText().equals("1"))
        || (home_textField.getText().equals("2"))
        || (home_textField.getText().equals("3"))
        || (home_textField.getText().equals("4")))) {

      saves = Integer.parseInt(home_textField.getText());
      
      File file = new File("saves//save" + saves + ".txt");
      String[] tokens;

      try (Scanner inputFile = new Scanner(file)) {
        tokens = inputFile.nextLine().split(";");
        inputFile.close();
      }
      for (String token : tokens) {
        //Re write the original file
        FileWriter fwrite = new FileWriter("player\\Party.txt", true);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
          partyText.print(token + ";");
          partyText.close();
        }
      }
      try (Scanner inputFile = new Scanner(file)) {
        inputFile.nextLine();
        num = inputFile.nextInt();
        inputFile.close();
      }
      
        FileWriter fwrite = new FileWriter("player\\Player.txt", false);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
          partyText.print(num);
          partyText.close();
        }
      
        begin_label.setText("Complete");
    } else {

    }
  }

  /**
   * method restart - starts a new game
   *
   * @param event
   * @throws IOException
   */
  @FXML
  private void restart(ActionEvent event) throws IOException {

    for (int i = 0; i < 1; i++) {

      // intialize filewriter 
      FileWriter fwrite = new FileWriter("player\\Party.txt", false);
      try (final PrintWriter partyText = new PrintWriter(fwrite)) {
        partyText.print("");
      }
    }
    
    for (int i = 0; i < 1; i++) {
      // intialize filewriter 
      FileWriter fwrite = new FileWriter("player\\Player.txt", false);
      try (final PrintWriter partyText = new PrintWriter(fwrite)) {
        partyText.print("0");
      }
    }

    //verification message
    begin_label.setText("You have started a new game!");
  }

  /**
   * method shopMenu - changes to shopMenu scene
   * @param event
   * @throws IOException 
   */
  @FXML
  private void shopMenu(ActionEvent event) throws IOException {
    scene.changeScene(event, "Shop.fxml");
  }

 
  /**
   * method geneticSequence - changes to genetics scene
   * @param event
   * @throws IOException 
   */
  @FXML
  private void geneticSequence(ActionEvent event) throws IOException {
    Parent main_page = FXMLLoader.load(getClass().getResource("GeneticSequence.fxml"));
    Scene main_scene = new Scene(main_page);
    Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    main_stage.setScene(main_scene);
    main_stage.show();
  }

  /**
   * method battleMode - changes to battle mode level select scene
   * @param event
   * @throws IOException 
   */
  @FXML
  private void battleMode(ActionEvent event) throws IOException {
    scene.changeScene(event, "LvlSelect.fxml");
  }

  /**
   * method evolutionSequence - changes to evolution scene
   * @param event
   * @throws IOException 
   */
  @FXML
  private void evolutionSequence(ActionEvent event) throws IOException {
    scene.changeScene(event, "Evolution.fxml");
  }

  /**
   * method party - changes to party scene
   * @param event
   * @throws IOException 
   */
  @FXML
  private void party(ActionEvent event) throws IOException {
    scene.changeScene(event, "Party.fxml");
  }

  /**
   * method initialize - creates a global masterlist of moves available to be changed
   * 
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO

    //intializes move database file for reading
    File file = new File("resources\\Moves.txt");

    Scanner inputFile = null;
    try {
      inputFile = new Scanner(file);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(PokemonGameController.class.getName()).log(Level.SEVERE, null, ex);
    }

    //initialize the arraylist of move objects
    movesList = new ArrayList<>();

    String[] tokens;

    //skips info line in text file
    String line = inputFile.nextLine();

    while (inputFile.hasNextLine()) {
      //inputs line from file into string variable
      line = inputFile.nextLine();

      //tokenizes string
      tokens = line.split(";");

      //adds moves object to arraylisit
      movesList.add(new Moves(tokens));

    }
  }

}
