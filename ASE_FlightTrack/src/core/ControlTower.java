/*Author: Dennish K.C
 * Date: 04/03/2022
 * ControlTower.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes. 
 * distanceBetweenGPS() method is implemented to calculate distance between 2 GPS locations.
 * 
 */
package core;
import exception.DataNotFoundException;

public class ControlTower {
    private GPSCoordinate gpsCoordinate;

    public ControlTower() {
    }

    //to assign control tower GPscoordinate
    public ControlTower(GPSCoordinate gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public GPSCoordinate getGpsCoordinate() {
        return gpsCoordinate; //returns GPscoordinate
    }

    public void setGpsCoordinate(GPSCoordinate gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }
    
    //method to calculate distance between 2 GPS coordinates
    public double distanceBetweenGPS(ControlTower controlTower) throws DataNotFoundException {
        GPSCoordinate gpsCoordinate = this.gpsCoordinate;
        
        double latInRadian = Math.toRadians(gpsCoordinate.getLatInDegree());//latitude conversion to radian from degree
        double lngInRadian = Math.toRadians(gpsCoordinate.getLngInDegree()); //longitude conversion to radian from degree
        GPSCoordinate gpsCoords = controlTower.gpsCoordinate;
        if (gpsCoords == null){
            throw new DataNotFoundException("GPS coordinates are missing.");
        }
        double otherLatInRadian = Math.toRadians(gpsCoords.getLatInDegree());
        double otherLngInRadian = Math.toRadians(gpsCoords.getLngInDegree());
        double deltaLng = otherLngInRadian - lngInRadian;
        double deltaLat = otherLatInRadian - latInRadian;
        double trig = Math.pow(Math.sin(deltaLat / 2), 2.0) + Math.cos(latInRadian)
                * Math.cos(otherLatInRadian) + Math.pow(Math.sin(deltaLng / 2), 2.0);

        double sqrt = Math.sqrt(trig);

        if(sqrt >= 1) {
            sqrt -=1;
        }

        return  2 * 6371.00 * Math.asin(sqrt);
    }
}
