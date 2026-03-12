///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * abstract class that provides structure for future Fox and Lemming
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public abstract class Animal extends Creature
{
    private final double weeklyLossRate;
    private final double actionRadius;
    /**
    * no arg constructor for Animal, initializes all variables to 0.0
    *
    */
    protected Animal(){
        super();
        weeklyLossRate = 0.0;
        actionRadius = 0.0;
    }
    /**
    * constructor for animal
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    * @param weeklyLossRate sets weeklyLossRate
    * @param actionRadius sets actionRadius
    */
    protected Animal(double mass, double health, double locX, double locY, 
        double weeklyLossRate, double actionRadius){
        super(mass, health, locX, locY);
        this.weeklyLossRate = weeklyLossRate;
        this.actionRadius = actionRadius;
    }
    /**
    * getter for weeklyLossRate
    *
    * @return weeklyLossRate
    */
    public double getWeeklyLossRate(){
        return weeklyLossRate;
    }
    /**
    * getter for actionRadius
    *
    * @return actionRadius
    */
    public double getActionRadius(){
        return actionRadius;
    }
    /**
    * abstract method for reproduction
    * 
    * @return a boolean
    */
    public abstract boolean triggerReproduce();
    /**
    * abstract method for possibly eating another creature
    * 
    * @param other a creature
    * @return a boolean
    */
    public abstract boolean preyOn(Creature other);
    /**
    * abstract method for getting mass from another creature
    * 
    * @param other a creature
    */    
    public abstract void feedOn(Creature other);
}