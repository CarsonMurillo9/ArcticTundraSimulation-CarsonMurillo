///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

import java.util.Random;

/**
 * class for lemming which will be prey to fox but able to eat crowberry and
 * willow
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class Lemming extends Animal implements Cloneable
{
    private static final double WEEKLYLOSSRATE = 0.1;
    private static final double DEFAULTAR = 500.0;
    private static final double THREE = 3.0;
    private static final double ZERO2 = 0.2;
    private static final double ZERO1 = 0.1;
    private static final double ZERO3 = 0.3;
    private static final double ZERO05 = 0.05;
    private static final double ZERO25 = 0.25;
    private static final double ZERO5 = 0.5;
    /**
    * no arg constructor for lemming, initializes all data fields to 0.0
    */
    public Lemming(){
        super();
    }
    /**
    * constructor for lemming
    * 
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    */
    public Lemming(double mass, double health, double locX, double locY){
        super(mass, health, locX, locY, WEEKLYLOSSRATE, DEFAULTAR);
    }
    /**
    * advances week for lemming in simulation, subtracts mass and removes
    * dead lemmings
    */
    @Override
    public void advanceWeek() throws CreatureDeathException{
        setMass(getMass() * (1.0 - getWeeklyLossRate()) - THREE);

        if (getMass() <= 0.0){
            throw new CreatureDeathException("A lemming died");
        }

        setHealth(getHealth() - ZERO2);
        if (getHealth() < 0.0){
            setHealth(0.0);
        }
    }
    /**
    * randomly decides if a lemming reproduces
    *
    * @return true if should reproduce, otherwise false
    */
    @Override
    public boolean triggerReproduce(){
        if (getHealth() < ZERO5){
            return false;
        }

        Random random = new Random();
        double num = random.nextDouble();
        if (num > ZERO3){
            return false;
        }
        return true;
    }
    /**
    * clones a lemming (used for reproduction)
    *
    * @return new cloned Lemming
    */
    @Override
    public Lemming clone(){
        return new Lemming(getMass(), getHealth(), getLocX(), getLocY());
    }
    /**
    * randomly decides if a lemming will eat a crowberry or willo
    *
    * @param other creature to be fed on
    * @return true and calls feedOn() if lemming eats, otherwise false
    */
    @Override
    public boolean preyOn(Creature other){
        if(!(other instanceof Crowberry || other instanceof Willow)){
            return false;
        }
        if (!other.isAlive()){
            return false;
        }

        double successRate;
        if (other instanceof Crowberry){
            successRate =  ZERO1 + ZERO1 * other.compareTo(this);
        }
        else{
            successRate = ZERO3;
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
    * takes partial mass from a willow or crowberry and gives it to lemming
    *
    * @param other creature to be eaten
    */
    @Override
    public void feedOn(Creature other){
        double reducedMass;
        if (other instanceof Crowberry){
            reducedMass = other.getMass() * ZERO05;
            other.setMass(other.getMass() * (1 - ZERO05));
            other.setHealth(other.getHealth() * (1 - ZERO05));
        }
        else{
            reducedMass = other.getMass();
            other.setMass(0.0);
        }

        setMass(getMass() + reducedMass * ZERO25);
        setHealth(getHealth() + ZERO2);
        if (getHealth() > 1.0){
            setHealth(1.0);
        }
    }
}