/*Author: Akash Palanivel
 * Date: 02/03/2022
 * Airline.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes
 * 
 */
package core;

public class Airline {
     //declaration of airline class attributes
    private String airlineCode;
    private String airlineName;

    public Airline() {
    }

  //assigning new airline code and name
    public Airline(String airlineCode, String airlineName) {
        this.airlineCode = airlineCode; 
        this.airlineName = airlineName;
    }

    public String getairlineName() {
        return airlineName;
    }
public void setairlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getairlineCode() {
        return airlineCode;
    }

    public void setairlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }
}
