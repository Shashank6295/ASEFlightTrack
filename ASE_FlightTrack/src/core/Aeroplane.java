/*Author: Akash Palanivel
 * Date: 01/03/2022
 * Aeroplane.java contains declaration of the attributes along with getter 
 * and setter methods to enable to be accessed by other classes
 * 
 */
package core;

public class Aeroplane {

    private String model;
    private Double speed;
    private String Manufacturer;
    private Double fuelConsumption;

    public String getModel() {
        return model; //returns plane model
    }

    public void setModel(String model) {
        this.model = model; //to assign new plane model
    }

    public Double getSpeed() {
        return speed;// returns aeroplane speed
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;//to assign new fuel assumption for a plane
    }
}
