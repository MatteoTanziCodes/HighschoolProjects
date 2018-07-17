/*
 * Programer: Zain,Matteo
 * class: Player
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: player object is made for save and data for player pokemon, 
 * and player info
 */
package pokemongame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author zain
 */
public class Player {

    private int playerMoney;
    String pokemon;
    String enemy;
    private ArrayList<String> party = new ArrayList<String>();

    /**
     * Default Constructor - to set enemy and player pokemon
     */
    public Player() {
        pokemon = "Charmander";
        enemy = "Blaziken";
    }

    /**
     * Constructor - to set enemies
     *
     * @param m
     * @param e
     */
    public Player(String m, String e) {
        pokemon = m;
        enemy = e;
    }

    /**
     * Constructor -
     *
     * @param plyr file - money info file
     * @param prty file - pokemon info file
     * @throws IOException
     */
    public Player(File plyr, File prty) throws IOException {
        String[] tokensP;
        Scanner plyr_File = new Scanner(plyr);
        Scanner prty_File = new Scanner(prty);

        //reads in player party file and tokenizes
        tokensP = prty_File.nextLine().split(";");

        for (String p : tokensP) {
            party.add(p);
        }

        //  sets players money 
        playerMoney = plyr_File.nextInt();
    }

    /**
     * method addParty - adds pokemon to user's party if party is greater than 6
     * do nothing
     *
     * @param name
     */
    public void addParty(String name) {
        if (party.size() <= 6) {
            party.add(name);
        }
    }

    /**
     * method getPartySize
     *
     * @return - the integer of party size
     */
    public int getPartySize() {
        return party.size();
    }

    /**
     * method removePokemon- removes pokemon from party
     *
     * @param n - pokemon user wishes to remove
     * @return - error message or success message
     * @throws IOException
     */
    public String removePokemon(String n) throws IOException {
        for (int i = 0; i < party.size(); i++) {
            if (party.get(i).equalsIgnoreCase(n)) {
                party.remove(i);
                return "Remove: " + n + " success";
            }
        }
        savePLR();
        return "Remove: " + n + " failed";

    }

    /**
     * setParty - Sets party using a newer party file
     *
     * @param p - party file
     * @throws IOException
     */
    public void setParty(File p) throws IOException {

        String[] tokensp;

        Scanner inputFileP = new Scanner(p);

        tokensp = inputFileP.nextLine().split(";");
        for (int i = 0; i < tokensp.length; i++) {
            party.add(tokensp[i]);
        }

    }

    /**
     * method setPlayerMoney - sets the amount of cash player has
     *
     * @param x - integer of cash
     */
    public void setPlayerMoney(int x) {

        playerMoney = x;

    }

    /**
     * method getPlayerMoney - gets amount of cash player has
     *
     * @return
     */
    public int getPlayerMoney() {

        return playerMoney;
    }

    /**
     * method getParty
     *
     * @return party pokemon names string
     */
    public String getParty() {

        String str = "";
        for (String m : party) {
            str += "\n " + m;
        }
        return str;
    }

    /**
     * getPartyArray - returns arraylist of party pokemon
     *
     * @return - arrayList string
     */
    public ArrayList<String> getPartyArray() {
        return party;
    }

    /**
     * setPlayer method - sets main pokemon for player
     *
     * @param m
     */
    public void setPlayer(String m) {
        pokemon = m;
    }

    /**
     * getEnemy method
     *
     * @returns the name of enemy pokemon
     */
    public String getEnemy() {
        return enemy;
    }

    /**
     * setEnemy method - sets the name of enemy pokemon
     *
     * @param m
     */
    public void setEnemy(String m) {
        enemy = m;
    }

    /**
     * getPokemon method
     *
     * @return pokemon - string name
     */
    public String getPokemon() {

        return pokemon;
    }

    /**
     * addPlayerMoney method will add xp to player
     *
     * @param x
     * @throws IOException
     */
    public void addPlayerMoney(int x) throws IOException {

        playerMoney = playerMoney + x;
        savePLR();
    }

    /**
     * removePlayerMoney method will remove money from player
     *
     * @param x
     */
    public void removePlayerMoney(int x) {
        playerMoney = playerMoney - x;
    }

    /**
     * savePLR - saves info on player's money value
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void savePLR() throws FileNotFoundException, IOException {

        FileWriter fwrite = new FileWriter("player\\Player.txt", false);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
            //writes the value of player money to Player text file
            partyText.print(playerMoney);
            partyText.close();
        }

    }

    /**
     * saveParty - saves info on player's party values
     *
     * @throws IOException
     */
    public void saveParty() throws IOException {
        FileWriter fwrite = new FileWriter("player\\Party.txt", false);
        try (final PrintWriter partyText = new PrintWriter(fwrite)) {
            for (String m : party) {
                //writes the names of pokemon into Party text file
                partyText.print(m + ";");
            }
            partyText.close();
        }
    }

}
