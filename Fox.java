///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Random;

/**
 * class for Fox which will act as the apex predator in the simulation
 * (unable to eat willow)
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class Fox extends Animal
{
    private ArrayList<Fox> offspring;
    private static final double GROWNLOSSRATE = 0.025;
    private static final double BABYLOSSRATE = 0.05;
    private static final double DEFAULTAR = 1000.0;
    private static final double HEALTHSUBTRACTION = 0.05;
    private static final double THIRTY = 30.0;
    private static final double ZERO2 = 0.2;
    private static final double ZERO4 = 0.4;
    private static final double ZERO1 = 0.1;
    private static final double ZERO3 = 0.3;
    private static final double ZERO7 = 0.7;
    private static final int THREE = 3;
    private static final double ZERO5 = 0.5;
    /**
    * no arg constructor for fox, sets superclass datafields to 0.0 and 
    * offspring to an empty arraylist
    */
    public Fox(){
        super();
        offspring = new ArrayList<>();
    }
    /**
    * constructor for fox
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    * @param isOffspring sets isOffspring
    */
    public Fox(double mass, double health, double locX, double locY, 
        boolean isOffspring){
        super(mass, health, locX, locY, 
            isOffspring ? BABYLOSSRATE : GROWNLOSSRATE, DEFAULTAR);
        offspring = new ArrayList<>();
    }
    /**
    * advances the week by 1 in the simulation, reduces mass every week
    */
    @Override
    public void advanceWeek() throws CreatureDeathException{
        setMass(getMass() * (1.0 - getWeeklyLossRate()) - THIRTY);
        if (getMass() <= 0.0){
            throw new CreatureDeathException("A fox died");
        }
        
        double newHealth = getHealth() - HEALTHSUBTRACTION;
        if (newHealth < 0.0){
            newHealth = 0.0;
        }
        setHealth(newHealth);

        for (int i = offspring.size() - 1; i >= 0; i--){
            try{
                offspring.get(i).advanceWeek();
            }
            catch (CreatureDeathException ex){
                offspring.remove(i);
            }
        }
    }
    /**
    * randomly decides if a fox creates an offspring
    *
    * @return true if fox will have offspring, otherwise false
    */
    @Override
    public boolean triggerReproduce(){
        if (offspring.size() >= THREE || getHealth() < ZERO5){
            return false;
        }
        Random random = new Random();
        double num = random.nextDouble();
        if (num <= ZERO1){
            return true;
        }
        return false;
    }
    /**
    * creates new offspring and adds it to the offspring arraylist
    */
    public void giveBirth(){
        Fox baby = new Fox(ZERO2 * getMass(), getHealth(), getLocX(), 
            getLocY(), true);
        setMass(getMass() - (ZERO2 * getMass()));
        offspring.add(baby);
    }
    /**
    * randomly decides if a lemming or crowberry is eaten
    *
    * @param other the creature to be preyed on
    * @return true and calls feedOn() if eats other, otherwise false
    */
    @Override
    public boolean preyOn(Creature other){
        if (!(other instanceof Lemming || other instanceof Crowberry)){
            return false;
        }
        if (!(other.isAlive())){
            return false;
        }

        double successRate;
        if (other instanceof Lemming){
            successRate = ZERO4 + ZERO2 * this.compareTo(other);
        }
        else {
            successRate = ZERO1 + ZERO1 * other.compareTo(this);
        }

        Random random = new Random();
        double num = random.nextDouble();

        if (num > successRate){
            return false;
        }
        else{
            feedOn(other);
            return true;
        }
    }
    /**
    * consumes some mass of a another creature
    *
    * @param other the creature to be eaten/lose mass
    */
    @Override
    public void feedOn(Creature other){
        double reducedMass;
        if (other instanceof Lemming){
            reducedMass = other.getMass();
            other.setMass(0.0);
        }
        else{
            reducedMass = other.getMass() * (1 - ZERO7);
            other.setMass(other.getMass() * (1 - ZERO3));
            other.setHealth(other.getHealth() * (1 - ZERO3));
        }

        if (offspring.size() == 0){
            setMass(getMass() + (reducedMass * ZERO4));
        }
        else{
            setMass(getMass() + (reducedMass * ZERO2));
            int numOffspring = offspring.size();
            double massPerOffspring = (reducedMass * ZERO2) / numOffspring;
            for (int i = 0; i < offspring.size(); i++){
                offspring.get(i).setMass(offspring.get(i).getMass() + 
                    massPerOffspring);
            }
        }

        setHealth(getHealth() + ZERO2);
        if (getHealth() > 1.0){
            setHealth(1.0);
        }
    }
    /**
    * adds a offspring to offspring arraylist
    *
    * @param other fox object to be added to list
    */
    public void addOffspring(Fox other){
        offspring.add(other);
    }
    /**
    * getter for offspring arraylist
    *
    * @return a copy of the offspring arraylist
    */
    public ArrayList<Fox> getAllOffspring(){
        return new ArrayList<>(offspring);
    }
}
