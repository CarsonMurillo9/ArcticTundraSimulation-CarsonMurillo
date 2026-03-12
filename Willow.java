///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * class for willow which can be prey for lemming
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class Willow extends Plant implements Cloneable
{
    private static final double WEEKLYGROWTHRATE = 0.6;
    private static final double TEN = 10.0;
    private static final double MAXMASS = 160.0;
    private static final double ZERO6 = 0.6;
    /**
    * no arg constructor for willow
    */
    public Willow(){
        super();
    }
    /**
    * constructor for animal
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    */
    public Willow(double mass, double health, double locX, double locY){
        super(mass, health, locX, locY, WEEKLYGROWTHRATE);
    }
    /**
    * clones a willow 
    * @return cloned willow object
    */
    @Override
    public Willow clone(){
        return new Willow(getMass(), getHealth(), getLocX(), getLocY());
    }
    /**
    * advances week for willow object increases mass and health
    */
    @Override
    public void advanceWeek() throws CreatureDeathException{
        setMass(getMass() * (1.0 + WEEKLYGROWTHRATE) + TEN);
        if (getMass() > MAXMASS){
            setMass(MAXMASS);
        }

        if (getMass() <= 0.0){
            throw new CreatureDeathException("A willow died");
        }
        setHealth(getHealth() + ZERO6);
        if (getHealth() > 1.0){
            setHealth(1.0);
        }
    }
}