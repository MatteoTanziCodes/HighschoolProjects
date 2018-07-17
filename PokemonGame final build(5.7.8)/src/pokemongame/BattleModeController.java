/*
 * Programer: John
 * class: BattleModeController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Handles battle sequence 
 */
package pokemongame;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Zain
 */
public class BattleModeController implements Initializable {

    //Stores FXML to easly change scene
    private SceneHandler scene = new SceneHandler("PokemonGame.fxml");

//FXML Buttons
    @FXML
    private Button btn_Move1;
    @FXML
    private Button btn_Move2;
    @FXML
    private Button btn_Move3;
    @FXML
    private Button btn_Move4;
    @FXML
    private Button btn_Back;

//FXML ProgressBars
    @FXML
    private ProgressBar bar_Plyr = new ProgressBar();
    @FXML
    private ProgressBar bar_Boss = new ProgressBar();

//FXML TextAreas
    @FXML
    private TextArea txt_Plyr = new TextArea();
    @FXML
    private TextArea txt_Boss = new TextArea();

//Text Labels
    @FXML
    private Text battle_label;

//FXML Labels
    @FXML
    private Label lbl_PlyrHealth;
    @FXML
    private Label lbl_BossHealth;
    @FXML
    private Label lbl_Plyr;
    @FXML
    private Label lbl_Boss;

//passes Array list for master list of moves
    private ArrayList<Moves> movesList = PokemonGameController.movesList;

//Random object for calculating move accuracy
    private Random random = new Random();

//Pokemon variables
    private Pokemon plyrPokemon;
    private Pokemon bossPokemon;

//Player
    private Player player;

//Game status variables
    private boolean win = false;
    private boolean battle = true;

//Original Health - Used for progress bar
    private int originalHealthPlyr = 100, originalHealthBoss = 100;

    /**
     * back - Return to the main scene
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void back(ActionEvent event) throws IOException {
        scene.changeScene(event);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //reads in party file (player pokemon squad)
        File prty_File = new File("player//Party.txt");

        //reads in player file (money)
        File plyr_File = new File("player//Player.txt");

        try {
            player = new Player(plyr_File, prty_File);
        } catch (IOException ex) {
            Logger.getLogger(BattleModeController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        try {
            //creates player pokemon object from player pokemon file
            plyrPokemon = new Pokemon(new File("pokemon"
                    + "//" + LvlSelectController.player.getPokemon()
                    + ".txt"));

            //creates enemy pokemon object from enemy pokemon file
            bossPokemon = new Pokemon(new File("enemies"
                    + "//" + LvlSelectController.player.getEnemy()
                    + ".txt"));

            originalHealthPlyr = plyrPokemon.getHealth();
            originalHealthBoss = bossPokemon.getHealth();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BattleModeController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        //sets player pokemon label as pokemon name of user 
        lbl_Plyr.setText(plyrPokemon.getName());

        //set button names to moves of player pokemon
        btn_Move1.setText(movesList.get(plyrPokemon.getMove1()).getName());
        btn_Move2.setText(movesList.get(plyrPokemon.getMove2()).getName());
        btn_Move3.setText(movesList.get(plyrPokemon.getMove3()).getName());
        btn_Move4.setText(movesList.get(plyrPokemon.getMove4()).getName());

        bar_Plyr.setProgress((double) plyrPokemon.getHealth()
                / originalHealthPlyr);

        bar_Boss.setProgress((double) bossPokemon.getHealth()
                / originalHealthBoss);
    }

    /**
     * Move1 - Will attack enemy with move 1
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Move1(ActionEvent event) throws IOException {
        update();
        if (battle) {
            playerTurn(0);
            EnemyTurn();
        } else {
            txt_Plyr.setText("Battle ended");
        }

    }

    /**
     * Move2 - Will attack enemy with move 2
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Move2(ActionEvent event) throws IOException {
        update();
        if (battle) {
            playerTurn(1);
            EnemyTurn();
        } else {
            txt_Plyr.setText("Battle ended");
        }
    }

    /**
     * Move3 - Will attack enemy with move 3
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Move3(ActionEvent event) throws IOException {
        update();
        if (battle) {
            playerTurn(2);
            EnemyTurn();
        } else {
            txt_Plyr.setText("Battle ended");
        }
    }

    /**
     * Move4 - Will attack enemy with move 4
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void Move4(ActionEvent event) throws IOException {
        update();
        if (battle) {
            playerTurn(3);
            EnemyTurn();
        } else {
            txt_Plyr.setText("Battle ended");
        }
    }

    /**
     * Calculates and applies the attack the player chooses
     *
     * @param moveUsed
     * @throws IOException
     */
    public void playerTurn(int moveUsed) throws IOException {
        if (plyrPokemon.getHealth() > 0) {
            boolean hasPP = true;

            int totalDam; // Stores total damage the pokemon does
            int totalAcc; // Sotres the accuracy a pokemon has
            int accChance; //Chance a pokemon might miss

            int[] move = plyrPokemon.getMoves();
            do {
                //verifies pp of move selected - VERIFY PP 
                if ((moveUsed == 0 && plyrPokemon.getMovePP1() <= 0)
                        || (moveUsed == 1 && plyrPokemon.getMovePP2() <= 0)
                        || (moveUsed == 2 && plyrPokemon.getMovePP3() <= 0)
                        || (moveUsed == 3 && plyrPokemon.getMovePP4() <= 0)) {
                    //either text or make button grayed out
                    txt_Plyr.setText("PP has run out... "
                            + "Please select a different move");
                } else if (moveUsed > 4 || moveUsed < 0) {
                    //user error prompt
                    txt_Plyr.setText("Error! Please Try Again");
                } else {
                    hasPP = false;
                }

            } while (hasPP);

            //finds total values for player pokemon stats when attacking
            //- DAMAGE/ACCURACY CALCULATIONS
            totalDam = (int) Math.round(plyrPokemon.getB_Power()
                    + movesList.get(move[moveUsed]).getDamage());
            totalAcc = (int) Math.round(plyrPokemon.getB_Accuracy()
                    * movesList.get(move[moveUsed]).getAccuracy());

            //checks if enemy pokemon is resistant and decreases attack 
            //damage if true - RESISTANCE CALCULATIONS
            if (plyrPokemon.getType().equals(bossPokemon.getResistance())) {
                totalDam = (int) Math.round(totalDam * 0.90);
            }

            //displays output of user move chosen
            txt_Plyr.setText(plyrPokemon.getName() + " used "
                    + movesList.get(move[moveUsed]).getName()
                    + ".\n*" + plyrPokemon.getName() + " : "
                    + movesList.get(move[moveUsed]).getText() + "*\n");

            //decides if pokemon can land a hit - ACCURACY CALCULATIONS
            accChance = random.nextInt(10) + 1;

            if (accChance <= (4 - totalAcc)) {
                txt_Plyr.setText(plyrPokemon.getName() + " missed!!!");
                totalDam = 0;
            }

            //decreases pp of move selected - PP MOVE DECREASE
            if (moveUsed == 1) {
                plyrPokemon.setMovePP1(plyrPokemon.getMovePP1() - 1);
            } else if (moveUsed == 2) {
                plyrPokemon.setMovePP2(plyrPokemon.getMovePP2() - 1);
            } else if (moveUsed == 3) {
                plyrPokemon.setMovePP3(plyrPokemon.getMovePP3() - 1);
            } else {
                plyrPokemon.setMovePP4(plyrPokemon.getMovePP4() - 1);
            }

            // damage is taken by enemy 
            bossPokemon.setHealth(bossPokemon.getHealth() - totalDam);

            //if  enemy pokemon has no more health battle ends , player wins
            if (bossPokemon.getHealth() < 0) {
                battle = false;
                win = true;
            }
        }//end If 
        else {
            battle = false;
            win = false;
        }
        update();
    }

    /**
     * EnemyTurn - Calculates and applies the damage the player takes
     *
     * @throws IOException
     */
    public void EnemyTurn() throws IOException {
        if (bossPokemon.getHealth() > 0) {
            boolean ppNoneCpu = true;

            int totalDam; // Store total damage for the enmey
            int totalAcc; //Store the total accuracy for the player
            int accChance; // Chance pokemon might miss
            int totalPP; // Store total pp for the pokemon
            int cpuNum; // The move the pokemon will attack with

            //Store move number and pp left for the pokemon
            int[] cpuMovePP = cpuMovePP = bossPokemon.getAllMovesPP();
            int[] cpuMove = bossPokemon.getMoves();

            //random number is generated to choose a value 
            //for enemy pokemon to attack with
            cpuNum = random.nextInt(3);

            //adds up all pp to check if no pp left - VERIFY ENEMY PP
            totalPP = 0;

            for (int i = 0; i < 4; i++) {
                totalPP += cpuMovePP[i];
            }

            //if no pp left in move, new move must be chosen
            if (totalPP == 0) {
                txt_Boss.setText(bossPokemon.getName()
                        + "is unable to use any more moves");
                txt_Boss.setText(bossPokemon.getName()
                        + "flees from battle!!");
            }

            //displays output of enemy move chosen
            txt_Boss.setText(bossPokemon.getName()
                    + " used " + movesList.get(cpuMove[cpuNum]).getName()
                    + ".\n*" + bossPokemon.getName()
                    + " : " + movesList.get(cpuMove[cpuNum]).getText() + "*");

            //finds total values for all pokemon stats when using move
            totalDam = (int) Math.round(bossPokemon.getB_Power()
                    + movesList.get(cpuMove[cpuNum]).getDamage());
            totalAcc = (int) Math.round(bossPokemon.getB_Accuracy()
                    * movesList.get(cpuMove[cpuNum]).getAccuracy());

            //checks resistance and decreases if enemy pokemon is resistant
            if (bossPokemon.getType().equals(plyrPokemon.getResistance())) {
                totalDam = (int) Math.round(totalDam * 0.90);
            }

            //decides if pokemon can land a hit
            accChance = random.nextInt(10) + 1;

            if (accChance == totalAcc) {
                txt_Boss.setText(bossPokemon.getName() + " missed!!!");
            }

            if (cpuNum == 1) {
                bossPokemon.setMovePP1(bossPokemon.getMovePP1() - 1);
            } else if (cpuNum == 2) {
                bossPokemon.setMovePP2(bossPokemon.getMovePP2() - 1);
            } else if (cpuNum == 3) {
                bossPokemon.setMovePP3(bossPokemon.getMovePP3() - 1);
            } else {
                bossPokemon.setMovePP4(bossPokemon.getMovePP4() - 1);
            }

            // damage is taken by player
            plyrPokemon.setHealth(plyrPokemon.getHealth() - totalDam);

            // if player pokemon has no more health battle ends, player loses
            if (plyrPokemon.getHealth() < 0) {
                battle = false;
                win = false;
            }
        }//end If 
        else {
            battle = false;
            win = true;
        }

        //updates battle pokemon's in game stats
        update();

    }//end enemyTurn

    /**
     * Updates visuals from fxml Displays health bar Give and calculate player
     * xp
     *
     * @throws IOException
     */
    public void update() throws IOException {

        int xpGained;// ammount of xp a player will receive
        int moneyGained; //ammount of money gained
        if (battle) {
            bar_Plyr.setProgress((double) plyrPokemon.getHealth()
                    / originalHealthPlyr);
            lbl_PlyrHealth.setText("PlayerHealth: "
                    + plyrPokemon.getHealth());

            // updates enemy pokemon's healthbar
            bar_Boss.setProgress((double) bossPokemon.getHealth()
                    / originalHealthBoss);
            lbl_BossHealth.setText("BossHealth: "
                    + bossPokemon.getHealth());
        } else if (!win) {
            //IF PLAYER LOSES

            //updates enemy pokemon's healthbar
            bar_Plyr.setProgress(0);
            plyrPokemon.setHealth(0);
            lbl_PlyrHealth.setText("PlayerHealth: "
                    + plyrPokemon.getHealth());
            txt_Plyr.setText("You lose :(");

            plyrPokemon.setHealth(originalHealthPlyr);
            xpGained = random.nextInt(10) + 1;
            plyrPokemon.AddXp(xpGained);

            //player pokemon xp gain is displayed.
            txt_Plyr.setText("Player gained " + xpGained + " xp.");

            btn_Back.setText("Exit");
        } else {

            //IF PLAYER WINS
            //enemy healthbar is updated
            bar_Boss.setProgress(0);
            lbl_BossHealth.setText("BossHealth: " + 0);

            //congratulation message
            txt_Plyr.setText("You win :)");
            plyrPokemon.setHealth(originalHealthPlyr);

            //calculates player xp and money gain
            xpGained = random.nextInt(bossPokemon.getLevel()) + 10;
            moneyGained = random.nextInt(bossPokemon.getLevel()) + 20;

            //add pokemon 
            plyrPokemon.AddXp(xpGained);
            player.addPlayerMoney(moneyGained);

            //player pokemon xp gain is displayed.
            txt_Plyr.setText("Player gained " + xpGained + " xp.");

            //player cash gain is displayed
            txt_Plyr.setText("Player gained $" + moneyGained + ".");

            //player saves
            plyrPokemon.save();
            btn_Back.setText("Exit");
        }
    }
}
