///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
 * Brings all other classes together to make a simulation of an arctic 
 * environment. Advances weeks which updates the amount of creatures in the 
 * environment and their mass. Ultimately, is able to put these simulation 
 * outputs into files.
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class ArcticTundra
{
    private ArrayList<Creature> allCreatures;
    private int currentWeek;
    private Random rand;
    private static final double MAX_LOC_X = 5000.0;
    private static final double MAX_LOC_Y = 5000.0;
    private static final double FOX_STANDARD_MASS = 3000.0;
    private static final double LEMMING_STANDARD_MASS = 50.0;
    private static final double CROWBERRY_STANDARD_MASS = 1000.0;
    private static final double WILLOW_STANDARD_MASS = 80.0;
    private static final double CLONED_WILLOW_MASS = 10.0;
    private static final int TWO = 2;
    /**
    * getter for currentWeek
    * 
    * @return currentWeek
    */
    public int getCurrentWeek(){
        return currentWeek;
    }
    /**
    * setter for currentWeek
    *
    * @param currentWeek sets currentWeek
    */
    public void setCurrentWeek(int currentWeek){
        this.currentWeek = currentWeek;
    }
    /**
    * no arg constructor for arctic tundra makes empty arraylist, currentWeek =
    * 0, and random object 
    */
    public ArcticTundra(){
        allCreatures = new ArrayList<Creature>();
        currentWeek = 0;
        rand = new Random();
    }
    /**
    * constructor for arctic tundra
    *
    * @param numFox sets number of foxes in simulation
    * @param numLemming sets number of lemmings in simulation
    * @param numCrowberry sets number of crowberries in simulation
    * @param numWillow sets number of willows in simulation
    */
    public ArcticTundra(int numFox, int numLemming, int numCrowberry, 
        int numWillow){
        allCreatures = new ArrayList<Creature>();
        currentWeek = 0;
        rand = new Random();

        //adding default foxes
        for (int i = 0; i < numFox; i++){
            double randomX = rand.nextDouble() * MAX_LOC_X;
            double randomY = rand.nextDouble() * MAX_LOC_Y;
            allCreatures.add(new Fox(FOX_STANDARD_MASS, 1.0, randomX, 
                randomY, false));
        }

        //adding default lemmings
        for (int i = 0; i < numLemming; i++){
            double randomX = rand.nextDouble() * MAX_LOC_X;
            double randomY = rand.nextDouble() * MAX_LOC_Y;
            allCreatures.add(new Lemming(LEMMING_STANDARD_MASS, 1.0, randomX, 
                randomY));
        }

        //adding default crowberries
        for (int i = 0; i < numCrowberry; i++){
            double randomX = rand.nextDouble() * MAX_LOC_X;
            double randomY = rand.nextDouble() * MAX_LOC_Y;
            allCreatures.add(new Crowberry(CROWBERRY_STANDARD_MASS, 1.0, 
                randomX, randomY));
        }

        //adding default willows
        for (int i = 0; i < numWillow; i++){
            double randomX = rand.nextDouble() * MAX_LOC_X;
            double randomY = rand.nextDouble() * MAX_LOC_Y;
            allCreatures.add(new Willow(WILLOW_STANDARD_MASS, 1.0, randomX, 
                randomY));
        }
    }
    /**
    * returns total mass for fox objects  
    * 
    * @return all fox and fox offspring mass
    */
    public double getTotalFoxMass(){
        double totalMass = 0.0;
        for (Creature creature : allCreatures){
            if (creature instanceof Fox){
                Fox fox = (Fox) creature;
                totalMass += fox.getMass();
                for (Fox offspring : fox.getAllOffspring()){
                    totalMass += offspring.getMass();
                }
            }
        }
        return totalMass;
    }
    /**
    * returns total mass for lemming objects
    * 
    * @return all lemming mass
    */
    public double getTotalLemmingMass(){
        double totalMass = 0.0;
        for (Creature creature : allCreatures){
            if (creature instanceof Lemming){
                Lemming lemming = (Lemming) creature;
                totalMass += lemming.getMass();
            }
        }
        return totalMass;
    }
    /**
    * returns total mass for crowberry objects
    * 
    * @return all crowberry mass
    */
    public double getTotalCrowberryMass(){
        double totalMass = 0.0;
        for (Creature creature : allCreatures){
            if (creature instanceof Crowberry){
                Crowberry crowberry = (Crowberry) creature;
                totalMass += crowberry.getMass();
            }
        }
        return totalMass;
    }
    /**
    * returns total mass for willow objects
    * 
    * @return all willow mass
    */
    public double getTotalWillowMass(){
        double totalMass = 0.0;
        for (Creature creature : allCreatures){
            if (creature instanceof Willow){
                Willow willow = (Willow) creature;
                totalMass += willow.getMass();
            }
        }
        return totalMass;
    }
    /**
    * advances the week by a given amount for all objects in the allCreatures 
    * arrayList
    * 
    * @param n number of weeks to advance
    */
    public void advanceNWeeks(int n){
        for (int i = 0; i < n; i++){
            ArrayList<Creature> copyList = new ArrayList<>(allCreatures);
            for (Creature creature : copyList){
                try{
                    creature.advanceWeek();
                }
                catch (CreatureDeathException ex){
                    allCreatures.remove(creature);
                    continue;
                }
                if (creature instanceof Fox){
                    Fox fox = (Fox) creature;
                    if (fox.triggerReproduce()){
                        fox.giveBirth();
                    }
                }
                if (creature instanceof Lemming){
                    Lemming lemming = (Lemming) creature;
                    if (lemming.triggerReproduce()){
                        allCreatures.add(lemming.clone());
                    }
                }
                ArrayList<Creature> copyList1 = new ArrayList<>(allCreatures);
                if (creature instanceof Animal){
                    Animal animal = (Animal) creature;
                    for (Creature other : copyList1){
                        if (animal.distanceTo(other) < 
                            animal.getActionRadius()){
                            animal.preyOn(other);
                            if (!other.isAlive() && other instanceof Lemming){
                                allCreatures.remove(other);
                            }
                            if (!other.isAlive() && other instanceof Willow){
                                Willow willow = (Willow) other;
                                Willow clonedWillow = willow.clone();
                                clonedWillow.setMass(CLONED_WILLOW_MASS);
                                allCreatures.remove(other);
                                allCreatures.add(clonedWillow);
                            }
                        }
                    }
                }
            }
            setCurrentWeek(getCurrentWeek() + 1);
        }
    }
    /**
    * creates a file of all creatures in format:
    * type mass health locX locY massOffspring1 massOffspring2 massOffspring3
    * 
    * @param filePath the name of the file to be saved to
    */
    public void saveToFile(String filePath){
        File file = new File(filePath);
        
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Creature creature : allCreatures){
                double offspring1 = -1.0;
                double offspring2 = -1.0;
                double offspring3 = -1.0;

                if (creature instanceof Fox){
                    Fox fox = (Fox) creature;
                    ArrayList<Fox> offspring = fox.getAllOffspring();

                    if (offspring.size() > 0){
                        offspring1 = offspring.get(0).getMass();
                    }
                    if (offspring.size() > 1){
                        offspring2 = offspring.get(1).getMass();
                    }
                    if (offspring.size() > TWO){
                        offspring3 = offspring.get(TWO).getMass();
                    }
                }
                writer.println(creature.getClass().getSimpleName() + " " + 
                    creature.getMass() + " " +
                    creature.getHealth() + " " + 
                    creature.getLocX() + " " +
                    creature.getLocY() + " " + 
                    offspring1 + " " + offspring2 + " " + offspring3);
            }
        }
        catch (FileNotFoundException ex){}
    }
    /**
    * constructor for arctic tundra using a file to create it
    * 
    * @param filePath the file that will be read to make new arctic tundra
    */
    public ArcticTundra(String filePath){
        allCreatures = new ArrayList<Creature>();
        currentWeek = 0;
        rand = new Random();

        File file = new File(filePath);
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);

                String species = lineScanner.next();
                double mass = lineScanner.nextDouble();
                double health = lineScanner.nextDouble();
                double locX = lineScanner.nextDouble();
                double locY = lineScanner.nextDouble();

                double offspring1 = lineScanner.nextDouble();
                double offspring2 = lineScanner.nextDouble();
                double offspring3 = lineScanner.nextDouble();

                Creature creature = null;

                if (species.equals("Fox")) {

                    Fox fox = new Fox(mass, health, locX, locY, false);

                    if (offspring1 != -1.0) {
                        Fox child = new Fox(offspring1, 0.0, locX, locY, true);
                        fox.addOffspring(child);
                    }

                    if (offspring2 != -1.0) {
                        Fox child = new Fox(offspring2, 0.0, locX, locY, true);
                        fox.addOffspring(child);
                    }

                    if (offspring3 != -1.0) {
                        Fox child = new Fox(offspring3, 0.0, locX, locY, true);
                        fox.addOffspring(child);
                    }

                    creature = fox;
                }
                else if (species.equals("Lemming")) {
                    creature = new Lemming(mass, health, locX, locY);
                }

                else if (species.equals("Crowberry")) {
                    creature = new Crowberry(mass, health, locX, locY);
                }

                else if (species.equals("Willow")) {
                    creature = new Willow(mass, health, locX, locY);
                }

                if (creature != null) {
                    allCreatures.add(creature);
                }

                lineScanner.close();
            }

        } 
        catch (FileNotFoundException ex) {}
    }
    /**
    * tests all methods
    * 
    * @return true
    */
    @SuppressWarnings("checkstyle:MagicNumber")
    public static boolean unitTests(){
        //test1
        ArcticTundra tundra1 = new ArcticTundra(10, 200, 0, 0);
        tundra1.advanceNWeeks(2);
        System.out.println("Tundra1 fox Mass: " + tundra1.getTotalFoxMass());
        System.out.println("Tundra1 lemming Mass: " + 
            tundra1.getTotalLemmingMass());
        tundra1.advanceNWeeks(2);
        System.out.println("Tundra1 fox Mass: " + tundra1.getTotalFoxMass());
        System.out.println("Tundra1 lemming Mass: " + 
            tundra1.getTotalLemmingMass());
        tundra1.advanceNWeeks(2);
        tundra1.saveToFile("tundra1.txt");
        ArcticTundra loadedTundra1 = new ArcticTundra("tundra1.txt");
        loadedTundra1.saveToFile("loadedTundra1.txt");

        //test2
        ArcticTundra tundra2 = new ArcticTundra();
        tundra2.advanceNWeeks(10);
        tundra2.saveToFile("tundra2.txt");
        ArcticTundra loadedTundra2 = new ArcticTundra("tundra2.txt");
        loadedTundra2.saveToFile("loadedTundra2.txt");


        //test3
        ArcticTundra tundra3 = new ArcticTundra(6, 70, 50, 200);
        tundra3.advanceNWeeks(5);
        System.out.println("Tundra3 fox Mass: " + tundra3.getTotalFoxMass());
        System.out.println("Tundra3 lemming Mass: " + 
            tundra3.getTotalLemmingMass());
        System.out.println("Tundra3 crowberry Mass: " + 
            tundra3.getTotalCrowberryMass());
        System.out.println("Tundra3 willow Mass: " + 
            tundra3.getTotalWillowMass());
        tundra3.advanceNWeeks(5);
        tundra3.saveToFile("tundra3.txt");
        ArcticTundra loadedTundra3 = new ArcticTundra("tundra3.txt");
        loadedTundra3.saveToFile("loadedTundra3.txt");
        return true;
    }
    /**
    * runs unitests
    * 
    * @param args something
    */
    public static void main(String[] args){
        boolean tf = unitTests();
        if (tf){
            System.out.println("All tests passed");
        }
        else{
            System.out.println("Test failed");
        }
    }
}
