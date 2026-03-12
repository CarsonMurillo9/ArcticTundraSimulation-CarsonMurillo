///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * class for crowberry which can be prey for either fox or lemming
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class Crowberry extends Plant
{
    private static final double WEEKLYGROWTHRATE = 0.2;
    private static final double TWENTY = 20.0;
    private static final double MAXMASS = 2000.0;
    private static final double ZERO2 = 0.2;
    /**
    * no arg constructor for crowberry
    */
    public Crowberry(){
        super();
    }
    /**
    * constructor for crowberry
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    */
    public Crowberry(double mass, double health, double locX, double locY){
        super(mass, health, locX, locY, WEEKLYGROWTHRATE);
    }
    /**
    * advances week for crowberry object, increases mass and health
    */
    @Override
    public void advanceWeek() throws CreatureDeathException{
        setMass(getMass() * (1.0 + WEEKLYGROWTHRATE) + TWENTY); 
        if (getMass() > MAXMASS){
            setMass(MAXMASS);
        }

        setHealth(getHealth() + ZERO2);
        if (getHealth() > 1.0){
            setHealth(1.0);
        }
    }
}