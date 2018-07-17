/*
 * Programer:  Zain,Matteo
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Zain
 */
public class LvlSelectController implements Initializable 
{
    
    @FXML
    private ComboBox party_Box;
    @FXML
    private ComboBox enemy_Box;
    
    @FXML
    private Text pokemon_txt;
    @FXML
    private Text enemy_txt;
    
    @FXML
    private Button start_btn;
    @FXML
    private Button btn_Lvl;
    
    public static Player player;
    @FXML
    private Text msg;
    
    @FXML 
    private ObservableList<String> party = FXCollections.observableArrayList();
    @FXML 
    private ObservableList<String> level = FXCollections.observableArrayList();
    
    //boolean variables
    private boolean player_Set;
    private boolean enemy_Set;
    
    // handles scene change
    private SceneHandler scene = new SceneHandler("PokemonGame.fxml");
    
    //arraylist of pokemon and enemy pokemon
    private ArrayList<Pokemon> partyFile = new ArrayList<>();
    private ArrayList<Pokemon> enemyFile = new ArrayList<>();
    
    private int boxToggle = 0;
    private String enemyLocation = "enemies//Level.txt"; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        File prty_File = new File("player//Party.txt");
        File plyr_File = new File("player//Player.txt");

        try {
            player = new Player(plyr_File, prty_File);
        } catch (IOException ex) {
            Logger.getLogger(LvlSelectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            read();
        } catch (IOException ex) {
            Logger.getLogger(LvlSelectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        boxToggle++;
    }    
    
    /**
     *  method sets enemy and player pokemon
     * @param event
     * @throws IOException 
     */
    public void set(ActionEvent event) throws IOException
    {
        
        if(enemy_Box.getValue() == null || party_Box.getValue()==null)
        {
            msg.setText("Error: Pokemon not selected");
            
            player_Set = false;
            enemy_Set = false;
        }
        else
        {
            player_Set = true;
            enemy_Set = true;
            
            pokemon_txt.setText(((String)party_Box.getValue()).split(" ")[0]);
            enemy_txt.setText(((String)enemy_Box.getValue()).split(" ")[0]);
            
            start_btn.setOpacity(1);
            start_btn.setOnAction(test);
        }
    }
    EventHandler<ActionEvent> test = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) 
        {
            try {
                start(event);
            } catch (IOException ex) {
                Logger.getLogger(LvlSelectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    /**
     * start method - starts battle sequence
     * @param event
     * @throws IOException 
     */
    public void start(ActionEvent event) throws IOException
    {
        String[] e,p;
        if(player_Set && enemy_Set)
        {
            e = ((String)enemy_Box.getValue()).split(" ");
            p = ((String)party_Box.getValue()).split(" ");
            String enemyFileName = "mew",pokemonFileName="charmander";
            for(int i = 0; i<partyFile.size(); i++)
            {
                if(partyFile.get(i).getName().equalsIgnoreCase(p[0]))
                {
                    pokemonFileName = partyFile.get(i).getFileName();
                }
            }
            for(int i = 0; i<enemyFile.size(); i++)
            {
                if(enemyFile.get(i).getName().equalsIgnoreCase(e[0]))
                {
                    enemyFileName = enemyFile.get(i).getFileName();
                }
            }
            player.setEnemy(enemyFileName);
            player.setPlayer(pokemonFileName);
            
            //changes to battle scene
            scene.changeScene(event,"BattleMode.fxml");
        }
        else
        {
            msg.setText("Error: Pokemon not selected");
        }
    }
    
    
    /**
     * method back - returns to main scene
     * @param event
     * @throws IOException 
     */
    public void back(ActionEvent event) throws IOException
    {
        scene.changeScene(event);
    }
    
    /**
     * method read - reads the pokemon into battle 
     * @throws IOException 
     */
    public void read() throws IOException
    {
        //Read party file
        File file = new File("player//Party.txt");
        Scanner inputFile = new Scanner(file);
        
        String [] tokens = inputFile.nextLine().split(";");
        
        //Create a pokemon object for each pokemon in the party
        for(String p: tokens)
        {
            partyFile.add(new Pokemon(new File("pokemon//"+p+".txt")));
        }
        
        for(Pokemon p: partyFile)
        {
            party.add(p.printStats());
        }
       party_Box.setItems(party);
   
       
       inputFile.close();
       
       //Read enemy file
        File fileE = new File(enemyLocation);
        Scanner inputFileE = new Scanner(fileE);
        
        String [] tokensE = inputFileE.nextLine().split(";");
        
        //Create a pokemon object for each pokemon in the party
        for(String p: tokensE)
        {
            if(boxToggle == 0)
            {
                enemyFile.add(new Pokemon(new File("enemies//"+p+".txt")));
            }
            else if (boxToggle > 0)
            {
                enemyFile.add(new Pokemon(new File("pokemon//"+p+".txt")));
            }
        }
        
        for(Pokemon p: enemyFile)
        {
            level.add(p.printStats());
        }
        enemy_Box.setItems(level);
       
       inputFileE.close();
    }
    
    /**
     * box method -switches the enemy list
     * @param event
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void box (ActionEvent event) throws FileNotFoundException, IOException
    {
        
        switch(boxToggle)
        {
            case 0:
            {
                enemyLocation ="enemies//Level.txt";
                btn_Lvl.setText("Enemies");
                update();
                boxToggle++;
                break;
            }
            case 1:
            {
                enemyLocation ="saves//box1.txt";
                btn_Lvl.setText("Box1");
                update();
                boxToggle++;
                break;
            }
            case 2:
            {
                enemyLocation ="saves//box2.txt";
                btn_Lvl.setText("Box2");
                update();
                boxToggle++;
                break;
            }
            case 3:
            {
                enemyLocation ="saves//box3.txt";
                btn_Lvl.setText("Box3");
                update();
                boxToggle = 0;
                break;
            }
            default:
            {
                enemyLocation ="enemies//Level.txt";
                btn_Lvl.setText("Enemies");
                boxToggle = 0;
                break;
            }
        }
    }
    /**
     * Updates the enemy box
     * @throws FileNotFoundException 
     */
    public void update() throws FileNotFoundException, IOException
    {
        enemyFile.clear();
        level.clear();
        read();
    }
}
