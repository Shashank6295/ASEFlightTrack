/*Author: Dennish K.C
 * Date: 08/03/2022
 * Utils.java contains utilities for parsing of latitude and longitude based on DMS format
 * 
 */
package utils;
import java.util.regex.Pattern;

// Utilities for parsing of latitude and longitude 
public class Utils {
     //Regex for latitude and longitude in Degree,Minutes,Seconds (DMS) format
    public static final Pattern DMSLATPATTERN =
            Pattern.compile("(-?)([0-9]{1,3})°([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([NS])");
    public static final Pattern DMSLNGPATTERN =
            Pattern.compile("(-?)([0-9]{1,3})°([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([EW])");
}
