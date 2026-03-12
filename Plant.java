///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * abstract class that provides structure for future Crowberry and Willow
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public abstract class Plant extends Creature
{
    private final double weeklyGrowthRate;
    /**
    * no arg constructor for plant initializes all data fields to 0.0
    */
    protected Plant(){
        super();
        weeklyGrowthRate = 0.0;
    }
    /**
    * constructor for animal
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    * @param weeklyGrowthRate sets weeklyGrowthRate
    */
    protected Plant(double mass, double health, double locX, double locY, 
        double weeklyGrowthRate){
        super(mass, health, locX, locY);
        this.weeklyGrowthRate = weeklyGrowthRate;
    }
    /**
    * getter for weeklyGrowthRate
    *
    * @return weeklyGrowthRate
    */
    public double getWeeklyGrowthRate(){
        return weeklyGrowthRate;
    }
}