///////////////////////////////////////////////////////////////////////////////
// Author:            Carson Murillo
// Email:             cemurillo@ucsd.edu
// Class:             CSE 11 Winter 2026
// Instructor's Name: Ben Ochoa
///////////////////////////////////////////////////////////////////////////////

/**
 * Exception to be thrown when a creature reaches a mass of 0.0
 *
 * Bugs: none known
 *
 * @author Carson Murillo
 */
public class CreatureDeathException extends Exception
{
    /**
    * constructor for the exception
    *
    * @param message the message that is to be printed when the exception is 
    * thrown
    */
    public CreatureDeathException(String message){
        super(message);
    }
}