// Given: Sample Solution for required Interfaces and Classes from Lab 4
interface SmartDevice {
    boolean turnOn();
    boolean turnOff();
    String performFunction();
}

interface Controllable {
    boolean increaseSetting();
    boolean decreaseSetting();
    void displaySetting();
}

abstract class Device implements SmartDevice {


    protected String name;
    protected boolean isOn;


    Device(String name) {
        this.name = name;
        this.isOn = false;
    }

    public String getName() {
        return this.name;
    }

    public boolean isOn() {
        return this.isOn;
    }

    // Given setter functions below
    @Override
    public boolean turnOn() {
        if (isOn) return false;
        else {
            isOn = true;
            return true;
        }
    }

    @Override
    public boolean turnOff() {
        if (!isOn) return false;
        else {
            isOn = false;
            return true;
        }
    }
}

class SmartThermostat extends Device implements Controllable {
    
    double temperature;
    double min_temperature = 18.0;
    double max_temperature = 25.0;
    double incremement_temperature = 0.5;

    public SmartThermostat(String name) {
        super(name);
        this.temperature = 20.0;
    }

    @Override
    public String performFunction() {
        return (this + " is " + (this.isOn ? ("set to " + temperature + "C"):"off"));
    }

    @Override
    public String toString() {
        return "Thermostat: " + name;
    }

    @Override
    public void displaySetting() {
        performFunction();
    }

    @Override
    public boolean increaseSetting() {
        if (this.isOn && this.temperature < max_temperature) {
            this.temperature += incremement_temperature;
            return true;
        } else return false;
    }

    @Override
    public boolean decreaseSetting() {
        if (this.isOn && this.temperature > min_temperature) {
            this.temperature -= incremement_temperature;
            return true;
        } else return false;
    }

}

class SmartLight extends Device implements Controllable {
    
    int brightness;
    int min_brightness = 1;
    int max_brightness = 10;
    int incremement_brightness = 1;

    public SmartLight(String name) {
        super(name);
        this.brightness = 5;
    }

    @Override
    public String performFunction() {
        return (this + " is " + (this.isOn ? ("set to " + brightness + " level"):"off"));
    }

    @Override
    public String toString() {
        return "Light: " + name;
    }

    @Override
    public void displaySetting() {
        performFunction();
    }

    @Override
    public boolean increaseSetting() {
        if (this.isOn && this.brightness < max_brightness) {
            this.brightness += incremement_brightness;
            return true;
        } else return false;
    }

    @Override
    public boolean decreaseSetting() {
        if (this.isOn && this.brightness > min_brightness) {
            this.brightness -= incremement_brightness;
            return true;
        } else return false;
    }
    
}
