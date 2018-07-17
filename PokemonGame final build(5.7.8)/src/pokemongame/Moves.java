/*
 * Programer: John
 * class: BattleModeController
 * Project: PokemonGame
 * Date: January 19 2018
 * Description: Stores information for each move read from a file
 */
package pokemongame;

/**
 *
 * @author johnc
 */
public class Moves {

    //intiialize fields
    private String name;
    private String type;
    private int damage;
    private int pp;
    private String text;
    private double acc;

    public Moves() {
        name = "N/A";
        type = "N/A";
        damage = 0;
        pp = 0;
        acc = 0.0;
        text = "N/A";

    }

    public Moves(String[] tokens) {
        name = tokens[0];
        type = tokens[1];
        damage = Integer.parseInt(tokens[2]);
        pp = Integer.parseInt(tokens[3]);
        acc = Double.parseDouble(tokens[4]);
        text = tokens[5];

    }

    /**
     * getName method
     *
     * @return name - pokemon name
     */
    public String getName() {
        return name;
    }

    /**
     * getType method
     *
     * @return type - pokemon type
     */
    public String getType() {
        return type;
    }

    /**
     * getDamage method
     * 
     * @return - Damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * getPP method
     * 
     * @return - PP
     */
    public int getPP() {
        return pp;
    }

    /**
     * getAccuracy method
     * 
     * @return - Accuracy
     */
    public double getAccuracy() {
        return acc;
    }

    /**
     * getTet method
     * 
     * @return - Text
     */
    public String getText() {
        return text;
    }

    @Override
    /**
     * 
     * toString method
     */
    public String toString() {
        String line = "Name: " + name
                + " \nType: " + type
                + "\nDamage: " + damage
                + "\nPP: " + pp;

        return line;
    }
}
