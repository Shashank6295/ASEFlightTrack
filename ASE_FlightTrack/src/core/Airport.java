package core;

public class Airport {

    private String airportName;
    private String airportCode;
    private ControlTower controlTower;

    public String getName() {
        return airportName;
    }

    public void setName(String name) {
        this.airportName = name;
    }

    public String getCode() {
        return airportCode;
    }

    public void setCode(String code) {
        this.airportCode = code;
    }

    public ControlTower getControlTower() {
        return controlTower;
    }

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }
}
