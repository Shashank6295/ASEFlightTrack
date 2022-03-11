/*Author: Ankitha Cherian
 * Date: 04/03/2022
 * FlightPlan.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes. 
 */
package core;
import java.util.List;

public class FlightPlan {

	// List to retrieve all airport data 
    private List<Airport> airports;

    public FlightPlan() {
    }

    public FlightPlan(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
