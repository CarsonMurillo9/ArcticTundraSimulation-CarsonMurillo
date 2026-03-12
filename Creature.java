///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * abstract class that provides a basic structure for future plants and animals
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public abstract class Creature implements Comparable<Creature>
{
    private double mass;
    private double health;
    private double locX;
    private double locY;
    /**
    * no arg constructor for Creature; initializes all variables to 0.0
    */
    protected Creature(){
        mass = 0.0;
        health = 0.0;
        locX = 0.0;
        locY = 0.0;
    }
    /**
    * constructor for Creature
    *
    * @param mass sets mass
    * @param health sets health
    * @param locX sets locX
    * @param locY sets locY
    */
    protected Creature(double mass, double health, double locX, double locY){
        this.mass = mass;
        this.health = health;
        this.locX = locX;
        this.locY = locY;
    }
    /**
    * getter for mass
    *
    * @return mass
    */
    public double getMass(){
        return mass;
    }
    /**
    * getter for Health
    *
    * @return health
    */
    public double getHealth(){
        return health;
    }
    /**
    * getter for locX
    *
    * @return locX
    */
    public double getLocX(){
        return locX;
    }
    /**
    * getter for locY
    * 
    * @return locY
    */
    public double getLocY(){
        return locY;
    }
    /**
    * setter for mass
    *
    * @param mass sets mass to any double
    */
    public void setMass(double mass){
        this.mass = mass;
    }
    /**
    * setter for health
    *
    * @param health sets health should be in [0, 1.0]
    */
    public void setHealth(double health){
        this.health = health;
    }
    /**
    * setter for locX
    *
    * @param locX sets locX
    */
    public void setLocX(double locX){
        this.locX = locX;
    }
    /**
    * setter for locY
    *
    * @param locY sets locY
    */
    public void setLocY(double locY){
        this.locY = locY;
    }
    /**
    * tells if a creature is alive (has positive mass)
    *
    * @return true if alive, otherwise false
    */
    public boolean isAlive(){
        if (mass > 0){
            return true;
        }
        return false;
    }
    /**
    * compares creatures using health
    *
    * @param other the creature to be compared to
    * @return 1 if health is greater than other, -1 if less, 0 otherwise
    */
    public int compareTo(Creature other){
        if (this.health > other.health){
            return 1;
        }
        else if (this.health < other.health){
            return -1;
        }
        else{
            return 0;
        }
    }
    /**
    * calculates euclidean distance between two creatures
    *
    * @param other creature a certain distance away
    * @return the distance between the two creatures
    */
    public double distanceTo(Creature other){
        double aSquared = (other.locX - this.locX) * (other.locX - this.locX);
        double bSquared = (other.locY - this.locY) * (other.locY - this.locY);
        double cSquared = aSquared + bSquared;
        return Math.sqrt(cSquared);
    }
    /**
    * determines if two creatures are the same species
    *
    * @param other creature to be compared to
    * @return true if same species, otherwise false
    */
    public boolean sameSpecies(Creature other){
        if (this.getClass().getName().equals(other.getClass().getName())){
            return true;
        }
        return false;
    }
    /**
    * puts all attributes of a creature in string form
    *
    * @return creature's attributes in string form
    */
    @Override
    public String toString() {
        return "(" + getClass().getName() + ")" + " mass: " + getMass() +
                "; health: " + getHealth() + "; locX: " + getLocX() + 
                "; locY: " + getLocY();
    }
    /**
    * abstract method for advancing week in simulation
    */
    public abstract void advanceWeek() throws CreatureDeathException;
}