/*Author: Shashank A.V
 * Date: 01/03/2022
 * Airport.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes
 * 
 */
package core;

public class Airport {

    private String airportName;
    private String airportCode;
    private ControlTower controlTower;

    public String getairportName() {
        return airportName;
    }
  //to assign  airport name
    public void setairportName(String name) {
        this.airportName = name;
    }
// returns airportcode
    public String getairportCode() {
        return airportCode;
    }
  //to assign new airport code
    public void setairportCode(String code) {
        this.airportCode = code;
    }

    public ControlTower getControlTower() {
        return controlTower;//returns departure airport controltower
    }

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }
}
