/**
 * Programmer: Zain
 * Class: Pokemon
 * Project: File manipulation
 * Date: December 26 2017
 * Description: A file that can read and manipulate the Pokemon information
 */
package pokemongame;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Pokemon {

    //Main stats_File for a pokemon
    private String name, fileName;
    private boolean fainted = false;
    private int health, xp, lvl, evolution, cooldown;
    private double power, defence, accuracy;

    private int[] moves = new int[4];
    private int[] movesPP = new int[4];

    //Genetic stats_File for a pokemon
    private String type, r_type, gender, r_gender, resistance, r_resistance;
    private int b_power, r_power, b_accuracy, r_accuracy, b_defence, r_defence;

    //Randomizer
    private Random r = new Random();

    /**
     * Constructor
     *
     * @param stats_File
     * @param m
     * @param geneticTraits_File
     * @param geneticStats_File
     */
    public Pokemon(File file) throws FileNotFoundException {
        //Read file for pokemon
        Scanner inputFile = new Scanner(file);

        //Store Each line of the file into the appropriate variable
        String stats_File, moves_File, geneticTraits_File, geneticStats_File;

        //Stats are line 2
        inputFile.nextLine();
        stats_File = inputFile.nextLine();

        //Moves are line 3
        inputFile.nextLine();
        moves_File = inputFile.nextLine();

        //Skip 6 lines
        for (int i = 0; i < 6; i++) {
            inputFile.nextLine();
        }

        //Genetic Traits are line 7
        geneticTraits_File = inputFile.nextLine();

        //Genetic Stats are line 8
        inputFile.nextLine();
        geneticStats_File = inputFile.nextLine();

        //Close File
        inputFile.close();

        //Split each line of the file into tokens
        String[] t_stats = stats_File.split(";");
        String[] t_moves = moves_File.split(";");
        String[] t_genetics = geneticTraits_File.split(";");
        String[] t_SGenetics = geneticStats_File.split(";");

        //Define name
        name = t_stats[1];

        //Define file location
        fileName = t_stats[21];

        //Define Stats
        health = Integer.parseInt(t_stats[3]);
        xp = Integer.parseInt(t_stats[5]);
        lvl = Integer.parseInt(t_stats[7]);
        evolution = Integer.parseInt(t_stats[9]);
        cooldown = Integer.parseInt(t_stats[11]);
        power = Double.parseDouble(t_stats[13]);
        defence = Double.parseDouble(t_stats[15]);
        accuracy = Double.parseDouble(t_stats[17]);
        fainted = Boolean.parseBoolean(t_stats[19]);

        //Define moves
        moves[0] = Integer.parseInt(t_moves[0]);
        moves[1] = Integer.parseInt(t_moves[2]);
        moves[2] = Integer.parseInt(t_moves[4]);
        moves[3] = Integer.parseInt(t_moves[6]);

        //Define movesPP
        movesPP[0] = Integer.parseInt(t_moves[1]);
        movesPP[1] = Integer.parseInt(t_moves[3]);
        movesPP[2] = Integer.parseInt(t_moves[5]);
        movesPP[3] = Integer.parseInt(t_moves[7]);

        //Define Genetic Strings
        type = t_genetics[1];
        r_type = t_genetics[3];
        gender = t_genetics[5];
        r_gender = t_genetics[7];
        resistance = t_genetics[9];
        r_resistance = t_genetics[11];

        //Define geneticTraits_File stats_File 
        b_power = Integer.parseInt(t_SGenetics[1]);
        r_power = Integer.parseInt(t_SGenetics[3]);
        b_accuracy = Integer.parseInt(t_SGenetics[5]);
        r_accuracy = Integer.parseInt(t_SGenetics[7]);
        b_defence = Integer.parseInt(t_SGenetics[9]);
        r_defence = Integer.parseInt(t_SGenetics[11]);
    }

    /**
     * Constructor - Breeding mechanic to make new Pokemon
     *
     * @param n
     * @param mom
     * @param dad
     */
    public Pokemon(String n, Pokemon mom, Pokemon dad) {
        //Define name
        name = n;
        //Define file location
        fileName = n;

        //Define Stats
        health = 100;
        xp = 0;
        lvl = 0;
        evolution = 0;
        cooldown = 0;
        power = 1;
        defence = 1;
        accuracy = 1;

        //Define moves
        moves = mom.getMoves();
        movesPP = mom.getAllMovesPP();

        /**
         * Add if statements that will change the base moves the 4 base moves
         * should be different for different types
         */
        //Choose child stats randomly from 4 option 
        switch (r.nextInt(4)) {
            /*
            *Case 0
            * Child will receive dominant trait from mom's dominant trait
            * Child will receive recessive trait from dad's dominant trait
             */
            case 0: {
                //type
                type = mom.getType();
                r_type = dad.getType();

                //gender
                gender = mom.getGender();
                r_gender = dad.getGender();

                //resitance
                resistance = mom.getResistance();
                r_resistance = dad.getResistance();

                //power
                b_power = mom.getB_Power();
                r_power = dad.getB_Power();

                //accuracy
                b_accuracy = mom.getB_Accuracy();
                r_accuracy = dad.getB_Accuracy();

                //defence
                b_defence = mom.getB_Defence();
                r_defence = dad.getB_Defence();
            }
            /*
            *Case 1
            * Child will receive dominant trait from mom's dominant trait
            * Child will receive recessive trait from dad's recessive trait
             */
            case 1: {
                //Type
                type = mom.getType();
                r_type = dad.getR_Type();

                //gender
                gender = mom.getGender();
                r_gender = dad.getR_Gender();

                //resitance
                resistance = mom.getResistance();
                r_resistance = dad.getR_Resistance();

                //power
                b_power = mom.getB_Power();
                r_power = dad.getR_Power();

                //accuracy
                b_accuracy = mom.getB_Accuracy();
                r_accuracy = dad.getR_Accuracy();

                //defence
                b_defence = mom.getB_Defence();
                r_defence = dad.getR_Defence();
            }
            /*
            *Case 2
            * Child will receive dominant trait from dad's dominant trait
            * Child will receive recessive trait from mom's recessive trait
             */
            case 2: {
                type = dad.getType();
                r_type = mom.getR_Type();

                //gender
                gender = dad.getGender();
                r_gender = mom.getR_Gender();

                //resitance
                resistance = dad.getResistance();
                r_resistance = mom.getR_Resistance();

                //power
                b_power = dad.getB_Power();
                r_power = mom.getR_Power();

                //accuracy
                b_accuracy = dad.getB_Accuracy();
                r_accuracy = mom.getR_Accuracy();

                //defence
                b_defence = dad.getB_Defence();
                r_defence = mom.getR_Defence();
            }
            /*
            *Case 3
            * Child will receive dominant trait from dad's recessive trait
            * Child will receive recessive trait from mom's recessive trait
             */
            case 3: {
                type = dad.getR_Type();
                r_type = mom.getR_Type();

                //gender
                gender = dad.getR_Gender();
                r_gender = mom.getR_Gender();

                //resitance
                resistance = dad.getR_Resistance();
                r_resistance = mom.getR_Resistance();

                //power
                b_power = dad.getR_Power();
                r_power = mom.getR_Power();

                //accuracy
                b_accuracy = dad.getR_Accuracy();
                r_accuracy = mom.getR_Accuracy();

                //defence
                b_defence = dad.getR_Defence();
                r_defence = mom.getR_Defence();
            }
        }

    }

    /**
     * Save - saves the Pokemon information into a file
     *
     * @throws IOException
     */
    public void save() throws IOException {
        //Re write the the original file
        FileWriter fwrite = new FileWriter("pokemon//" + fileName + ".txt", false);
        PrintWriter pokemon = new PrintWriter(fwrite);

        //Saving information for the stats_File
        pokemon.println("//Stats");
        pokemon.println("Name;" + name + ";Health;" + health + ";Xp;" + xp + ""
                + ";Lvl;" + lvl + ";Evolution;" + evolution + ";Cooldown;" + cooldown + ";Power;" + power
                + ";Defense;" + defence + ";Accuracy;" + accuracy + ";Fainted;" + fainted + ";fileName;" + fileName);

        //Saving Moves
        pokemon.println("//Moves");
        pokemon.println(moves[0] + ";" + movesPP[0] + ";" + moves[1] + ";" + movesPP[1]
                + ";" + moves[2] + ";" + movesPP[2]
                + ";" + moves[3] + ";" + movesPP[3]);
        pokemon.println();

        //Printing outline
        pokemon.println("//Outline");
        pokemon.println("Name Health Xp Lvl Evolution Cooldown Power Defense Accuracy Fainted FileName");
        pokemon.println("move1 move1PP move2 move2PP move3 move3PP move4 move4PP");
        pokemon.println();

        //Saving Genetic Strings
        pokemon.println("//Genetic Strings");
        pokemon.println("B_Type;" + type + ";R_Type;" + r_type + ";Gender;" + gender
                + ";R_Gender;" + r_gender + ";Resistance;" + resistance
                + ";R_Resistance;" + r_resistance);

        //Saving Genetic Stats
        pokemon.println("//Genetic Stats");
        pokemon.println("B_Power;" + b_power + ";R_Power;" + r_power
                + ";B_Accuracy;" + b_accuracy + ";R_Accuracy;" + r_accuracy
                + ";B_Defence;" + b_defence + ";R_Defence;" + r_defence);
        pokemon.println();

        //Printing outline
        pokemon.println("//Outline");
        pokemon.println("B_Type R_Type Gender R_Gender Resistance R_Resistance");
        pokemon.println("B_Power R_power B_Accuracy "
                + "R_Accuracy B_Defence R_Defence");

        pokemon.close();
    }
    public void saveNew() throws IOException {
        //Re write the the original file
        FileWriter fwrite = new FileWriter("pokemon//" + name + ".txt", false);
        PrintWriter pokemon = new PrintWriter(fwrite);

        //Saving information for the stats_File
        pokemon.println("//Stats");
        pokemon.println("Name;" + name + ";Health;" + health + ";Xp;" + xp + ""
                + ";Lvl;" + lvl + ";Evolution;" + evolution + ";Cooldown;" + cooldown + ";Power;" + power
                + ";Defense;" + defence + ";Accuracy;" + accuracy + ";Fainted;" + fainted + ";fileName;" + fileName);

        //Saving Moves
        pokemon.println("//Moves");
        pokemon.println(moves[0] + ";" + movesPP[0] + ";" + moves[1] + ";" + movesPP[1]
                + ";" + moves[2] + ";" + movesPP[2]
                + ";" + moves[3] + ";" + movesPP[3]);
        pokemon.println();

        //Printing outline
        pokemon.println("//Outline");
        pokemon.println("Name Health Xp Lvl Evolution Cooldown Power Defense Accuracy Fainted FileName");
        pokemon.println("move1 move1PP move2 move2PP move3 move3PP move4 move4PP");
        pokemon.println();

        //Saving Genetic Strings
        pokemon.println("//Genetic Strings");
        pokemon.println("B_Type;" + type + ";R_Type;" + r_type + ";Gender;" + gender
                + ";R_Gender;" + r_gender + ";Resistance;" + resistance
                + ";R_Resistance;" + r_resistance);

        //Saving Genetic Stats
        pokemon.println("//Genetic Stats");
        pokemon.println("B_Power;" + b_power + ";R_Power;" + r_power
                + ";B_Accuracy;" + b_accuracy + ";R_Accuracy;" + r_accuracy
                + ";B_Defence;" + b_defence + ";R_Defence;" + r_defence);
        pokemon.println();

        //Printing outline
        pokemon.println("//Outline");
        pokemon.println("B_Type R_Type Gender R_Gender Resistance R_Resistance");
        pokemon.println("B_Power R_power B_Accuracy "
                + "R_Accuracy B_Defence R_Defence");

        pokemon.close();
    }

    /**
     * getName - return pokemon's name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setName - change the pokemon's name
     *
     * @param n
     */
    public void setName(String n) {
        name = n;
    }

    /**
     * getFileName - return the file location for the pokemon
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * setHealth - Updates the pokemon's health
     *
     * @param h
     */
    public void setHealth(int h) {
        health = h;
    }

    /**
     * setXp - changes the pokemon's xp
     *
     * @param x
     */
    public void setXp(int x) {
        xp = x;
        if (xp > 10) {
            xp = 0;
            lvl += 1;
        }
    }

    /**
     * AddXp - adds addtional xp
     *
     * @param x
     */
    public void AddXp(int x) {
        xp += x;
        if (xp > 50) {
            xp = 0;
            lvl += 1;
            power += 0.1;
            defence += 0.1;
            accuracy += 0.1;
            health += 10;
        }
    }

    /**
     * setLevel - Change the pokemon's level
     *
     * @param l
     */
    public void setLevel(int l) {
        lvl = l;
    }

    /**
     * addLevel - add the pokemon's level
     *
     * @param l
     */
    public void addLevel(int l) {
        lvl += l;
    }

    /**
     * setEvolution - change the pokemon's current evolution stage
     *
     * @param e
     */
    public void setEvolution(int e) {
        evolution = e;
    }

    /**
     * setCooldown - change pokemon's cooldown for breeding
     *
     * @param c
     */
    public void setCooldown(int c) {
        cooldown = c;
    }

    /**
     * setPower - change the pokemon's power multiplier
     *
     * @param p
     */
    public void setPower(double p) {
        power = p;
    }

    /**
     * setDefence - change the pokemon's defence multiplier
     *
     * @param d
     */
    public void setDefence(double d) {
        defence = d;
    }

    /**
     * setAccuracy - change the pokemon's accuracy multiplier
     *
     * @param a
     */
    public void setAccuracy(double a) {
        accuracy = a;
    }

    /**
     * setMove1 - change move1 for pokemon
     *
     * @param m
     */
    public void setMove1(int m) {
        moves[0] = m;
    }

    /**
     * setMove2 - change move2 for pokemon
     *
     * @param m
     */
    public void setMove2(int m) {
        moves[1] = m;
    }

    /**
     * setMove3 - change move3 for the pokemon
     *
     * @param m
     */
    public void setMove3(int m) {
        moves[2] = m;
    }

    /**
     * setMove4 - change move4 for the pokemon
     *
     * @param m
     */
    public void setMove4(int m) {
        moves[3] = m;
    }

    /**
     * setMove1 - change the move and pp for move 1
     *
     * @param p
     */
    public void setMovePP1(int p) {
        movesPP[0] = p;
    }

    /**
     * setMove2 - change the move and pp for move 2
     *
     * @param p
     */
    public void setMovePP2(int p) {
        movesPP[1] = p;
    }

    /**
     * setMove3 - change the move and pp for move 3
     *
     * @param p
     */
    public void setMovePP3(int p) {
        movesPP[2] = p;
    }

    /**
     * setMove4 - change the move and pp for move 4
     *
     * @param p
     */
    public void setMovePP4(int p) {
        movesPP[3] = p;
    }

    /**
     * setMove1 - change the move and pp for move 1
     *
     * @param p
     */
    public int getMovePP1() {
        return movesPP[0];
    }

    /**
     * setMove2 - change the move and pp for move 2
     *
     * @param m
     * @param p
     */
    public int getMovePP2() {
        return movesPP[1];
    }

    /**
     * setMove3 - change the move and pp for move 3
     *
     * @param m
     * @param p
     */
    public int getMovePP3() {
        return movesPP[2];
    }

    /**
     * setMove4 - change the move and pp for move 4
     *
     * @param m
     * @param p
     */
    public int getMovePP4() {
        return movesPP[3];
    }

    /**
     * getAllMovesPP - return the array of the movesPP
     *
     * @return
     */
    public int[] getAllMovesPP() {
        return movesPP;
    }

    /**
     * getHealth - Return the pokemon's current health
     *
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * getLevel - return pokemon's level
     *
     * @return
     */
    public int getLevel() {
        return lvl;
    }

    /**
     * getEvolution - return the pokemon's level
     *
     * @return
     */
    public int getEvolution() {
        return evolution;
    }

    /**
     * getCooldown - return pokemon's breeding cool down timer
     *
     * @return
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * getPower - return power multiplier
     *
     * @return
     */
    public double getPower() {
        return power;
    }

    /**
     * getDefence - return defence multiplier
     *
     * @return
     */
    public double getDefence() {
        return defence;
    }

    /**
     * getAccuracy - return accuracy multiplier
     *
     * @return
     */
    public double getAccuracy() {
        return accuracy;
    }

    /**
     * getMoves - return array of all the pokemon moves
     *
     * @return
     */
    public int[] getMoves() {
        return moves;
    }

    /**
     * getMove1 - return the number location for the first move
     *
     * @return
     */
    public int getMove1() {
        return moves[0];
    }

    /**
     * getMove2 - return the number location for the second move
     *
     * @return
     */
    public int getMove2() {
        return moves[1];
    }

    /**
     * getMove3 - return the number location for the third move
     *
     * @return
     */
    public int getMove3() {
        return moves[2];
    }

    /**
     * getMove4 - return the number location for the fourth move
     *
     * @return
     */
    public int getMove4() {
        return moves[3];
    }

    /**
     * getTupe - return the type for the pokemon
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * getR_Type - return the recursive type for the pokemon
     *
     * @return
     */
    public String getR_Type() {
        return r_type;
    }

    /**
     * getGender - return the gender of the pokemon
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * getR_Gender - return the recursive gender of the pokemon
     *
     * @return
     */
    public String getR_Gender() {
        return r_gender;
    }

    /**
     * getResistane() - return the resistance for the pokemon
     *
     * @return
     */
    public String getResistance() {
        return resistance;
    }

    /**
     * getR_Resistance - return the recursive resistance for the pokemon
     *
     * @return
     */
    public String getR_Resistance() {
        return r_resistance;
    }

    /**
     * getB_Power - return the base power for the pokemon
     *
     * @return
     */
    public int getB_Power() {
        return b_power;
    }

    /**
     * getR_Power - return the recursive power for the pokemon
     *
     * @return
     */
    public int getR_Power() {
        return r_power;
    }

    /**
     * getB_Accuracy - return the base accuracy for the pokemon
     *
     * @return
     */
    public int getB_Accuracy() {
        return b_accuracy;
    }

    /**
     *
     * @return
     */
    public int getR_Accuracy() {
        return r_accuracy;
    }

    /**
     * getB_Defence - return base defence
     *
     * @return
     */
    public int getB_Defence() {
        return b_defence;
    }

    /**
     * getR_Defence - return the recursive defence
     *
     * @return
     */
    public int getR_Defence() {
        return r_defence;
    }
    /**
     * Print out all the information for the pokemon
     * @return 
     */
    public String printStats()
    {
        String str;
        str = (name+" "
             +"\nHealth: "+health+" Level: "+lvl
            +" Evolution: "+evolution+"\n"
            +"Power: "+power+" defence: "+defence+" Accuracy: "+accuracy);
        return str;
    }

    /**
     * Print out all the information for the pokemon
     *
     * @return
     */
    public String toString() {
        String str;
        str = ("Name " + name + " Health " + health + " xp " + xp + " Level " + lvl
                + " Evolution " + evolution + " Cooldown " + cooldown + "\n"
                + "Power " + power + " defence " + defence + "Accuracy" + accuracy
                + "\n Move 1 " + moves[0]
                + "\n Move 2 " + moves[1]
                + "\n Move 3 " + moves[2]
                + "\n Move 4 " + moves[3]
                + "\n Move 1PP " + movesPP[0]
                + "\n Move 2PP " + movesPP[1]
                + "\n Move 3PP " + movesPP[2]
                + "\n Move 4PP " + movesPP[3]);
        return str;
    }
}
