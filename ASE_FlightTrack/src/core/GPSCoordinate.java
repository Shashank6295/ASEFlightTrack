/*Author: Dennish K.C
 * Date: 08/03/2022
 * GPSCoordinate.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes
 * The class contains method getLngInDegree(),getLatInDegree() to get latitude and longitude in Degree,minute,second(DMS) format.
 *  convertToDouble() was used to convert latitude and longitude in DMS format to double
 */
package core;
import utils.Utils;
import java.util.regex.Matcher;

public class GPSCoordinate {

    private String latitude;
    private String longitude;

  //to assign latitude and longitude attributes.
    public GPSCoordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GPSCoordinate() {
    }

    public String getLatitude() {
        return latitude;// to return the latitude value
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude; // to assign new Latitude
    }

    public String getLongitude() {
        return longitude;//to return the longitude value
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude; //to assign new longitude value
    }
  //to get longitude in Degree,minute,second format. DMS pattern is given in Utils.java
    public Double getLngInDegree(){
        Matcher matcher = Utils.DMSLNGPATTERN.matcher(this.longitude.trim());
        if (matcher.matches()){
            double longitude = convertToDouble(matcher);
            if ((Math.abs(longitude) > 180)) {
                throw new NumberFormatException("Invalid longitude");
            }
            return longitude;
        }else {
            throw new NumberFormatException("Wrong DMS coordiniates");
        }
    }

    //to get latitude in Degree,minute,second format. DMS pattern is given in Utils.java
    public Double getLatInDegree(){
    	//System.out.println(this.latitude.trim());
        Matcher matcher = Utils.DMSLATPATTERN.matcher(this.latitude.trim());
       // System.out.println(matcher.matches());
        if (matcher.matches()){

            double latitude = convertToDouble(matcher);
            if ((Math.abs(latitude) > 180)) {
                throw new NumberFormatException("Invalid latitude");
            }
            return latitude;
        }else {
            throw new NumberFormatException("Wrrong DMS coordiniates");
        }
    }
    //TO convert latitude and longitude to double
    private Double convertToDouble(Matcher matcher){
        int sign = "".equals(matcher.group(1)) ? 1 : -1;
        double degrees = Double.parseDouble(matcher.group(2));
        double minutes = Double.parseDouble(matcher.group(3));
        double seconds = Double.parseDouble(matcher.group(4));
        int direction = "NE".contains(matcher.group(5)) ? 1 : -1;

        return sign * direction * (degrees + minutes / 60 + seconds / 3600 );
    }
}
