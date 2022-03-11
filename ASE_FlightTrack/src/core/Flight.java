/*Author: Ankitha Cherian
 * Date: 04/03/2022
 * Flight.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes. 
 * The class containes calculateDistance() to  calculate total distance travelled in km by selected flight.
 * timeTaken() to calculate the total time in hours taken for the travel.
 * fuelConsumption() to calculate the total fuelConsumption in litres depending on distance travelled
 * CO2emission() to calculate CO2 emission in kg depending on fuelConsumption of the selected plane
 * 
 * 
 */
package core;

import exception.DataNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Flight {

	//declaration of all attributes
    private String id;
    private Aeroplane planeType;
    private Airport airportDeparture;
    private Airport airportDestination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private FlightPlan flightPlan;
    private Airline airline;

    public FlightPlan getFlightPlan() {
        return flightPlan; //to return  flight plan
    }

    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan; //to assign new flight plan
    }

    public Airline getAirline() {
        return airline; //to retrieve the airline
    }

    public void setAirline(Airline airline) {
        this.airline = airline; //sets new airline
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aeroplane getPlaneType() {
        return planeType; //to retrieve the plane type
    }

    public void setPlaneType(Aeroplane planeType) {
        this.planeType = planeType;// to set new plane type
    }

    public Airport getairportDeparture() {
        return airportDeparture;// to return departure airport
    }

    public void setairportDeparture(Airport airportDeparture) {
        this.airportDeparture = airportDeparture;
    }

    public Airport getairportDestination() {
        return airportDestination; // to return destination airport
    }

    public void getairportDestination(Airport newairportDestination) {
        this.airportDestination = newairportDestination;// to assign new departure airport
    }

    public LocalDate getDepartureDate() {
        return departureDate; //// to return date of departure
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate; //to set new date of departure
    }

    public LocalTime getDepartureTime() {
        return departureTime; //to return time of departure
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;//to add new time  of departure
    }

    //to calculate total distance travelled by flight
    public Double calculateDistance() throws DataNotFoundException {
        double distance = 0;
        ControlTower controlTower = this.airportDeparture.getControlTower();
        if (controlTower == null) {
            throw new DataNotFoundException("Control tower not found.");
        }
        GPSCoordinate gpsCoord = controlTower.getGpsCoordinate();
        List<ControlTower> controlTowers = this.getFlightPlan()
                .getAirports()
                .stream()
                .map(Airport::getControlTower)
                .collect(Collectors.toList());
        if (controlTowers.isEmpty()) {
            throw new DataNotFoundException("Control towers is empty.");
        }
        double latInRadian = Math.toRadians(gpsCoord.getLatInDegree());
//        System.out.println(latInRadian);
        Double longInRadian = Math.toRadians(gpsCoord.getLngInDegree());
//        System.out.println(longInRadian);

        for (ControlTower cntrlTower : controlTowers) {
            GPSCoordinate gpsCoordinate = cntrlTower.getGpsCoordinate(); //getting control tower gps coordinates
            if (gpsCoordinate == null) {
                throw new DataNotFoundException("GPS coordinates are missing.");
            }
            double otherLatInRadian = Math.toRadians(gpsCoordinate.getLatInDegree());//latitude conversion to radian from degree
            Double otherLngInRadian = Math.toRadians(gpsCoordinate.getLngInDegree());//longitude conversion to radian from degree
            double deltaLongitude = otherLngInRadian - longInRadian;
            Double deltaLatitude = otherLatInRadian - latInRadian;
            double trig = Math.pow(Math.sin(deltaLatitude / 2), 2.0) + Math.cos(latInRadian)
                    * Math.cos(otherLatInRadian) + Math.pow(Math.sin(deltaLongitude / 2), 2.0);


            double sqrt = Math.sqrt(trig);

            if(sqrt >= 1) {
                sqrt -=1;
            }

            distance += 2 * 6371.00 * Math.asin(sqrt);

            latInRadian = otherLatInRadian;
            longInRadian = otherLngInRadian;
        }
        return distance;
    }

  //to calculate total time taken by flight
    public Double timeTaken() throws DataNotFoundException {
        double timeTaken = 0.0;
        Aeroplane aeroplane = this.getPlaneType();
        Double speed = aeroplane.getSpeed();
        FlightPlan flightPlan = this.getFlightPlan();
        ControlTower departureControlTower = this.airportDeparture.getControlTower();
        if (departureControlTower == null){
            throw new DataNotFoundException("Departure airport control tower not found.");
        }
        List<ControlTower> controlTowers = flightPlan.getAirports()
                .stream()
                .map(Airport::getControlTower)
                .collect(Collectors.toList());
        if (controlTowers.isEmpty()) {
            throw new DataNotFoundException("Control towers not found.");
        }
        for (ControlTower controlTower: controlTowers) {
        	
            Double distanceBetweenControlTower = departureControlTower
                    .distanceBetweenGPS(controlTower);
            timeTaken = timeTaken + distanceBetweenControlTower / speed;
            departureControlTower = controlTower;
        }
        return timeTaken;
    }

    ///to calculate fuelconsumption 
    public Double fuelConsumption() throws DataNotFoundException {
        Aeroplane aeroplane = this.getPlaneType();
        Double fuelConsumption = aeroplane.getFuelConsumption();//retrieve fuelconsumption of selected flight
        if (fuelConsumption == null){
            throw new DataNotFoundException("Fuel consumption is null.");
        }
        Double distance = this.calculateDistance(); //retrieve total distance
        return distance * fuelConsumption / 100;
    }

    //to calculate CO2 emission for selected flight
    public Double CO2_emission() throws DataNotFoundException{
        return this.fuelConsumption() * 4.98;//Emission factor is set as constant :4.98
    }

	

}
