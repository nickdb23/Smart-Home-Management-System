import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class SmartHome {

    //Given: Member data fields
    String name;
    int thermostatCount;
    int lightCount;
    ArrayList<Device> smartThings;
    static final String TYPES = "SmartLight;SmartThermostat;";

    //Given: Constructors
    public SmartHome() {
        this.name = "SmartHome";
        this.thermostatCount = 0;
        this.lightCount = 0;
        this.smartThings = new ArrayList<Device>();
    }

    public SmartHome(String name) {
        this.name = name;
        this.thermostatCount = 0;
        this.lightCount = 0;
        this.smartThings = new ArrayList<Device>();

        // JUST FOR EXAMPLE
        this.smartThings.add(new SmartLight("L1"));
        this.smartThings.add(new SmartLight("L2"));
        this.smartThings.add(new SmartThermostat("T1"));
        this.smartThings.add(new SmartThermostat("T2"));
    }

    // Given: Method to get devices
    public ArrayList<Device> getSmartDevices() {
        return this.smartThings;
    }

    //TODO: Method to add SmartThermostat or SmartLight to ArrayList
    //Parameters: type (what kind of device), name (name of device)
    //Return: void
    //Modifies: creates new SmartLight or SmartThermostat and adds it to smartThings
    //Exceptions: *must* throw Exception if type is not "SmartLight" or "SmartThermostat", 
    //             or name is already used
    public void addDevice(String type, String name) throws Exception {
        // HINT: Check if name is already a device in smartThings
        boolean foundName = false;
        for (Object d: smartThings) {
            if (((Device)d).getName().equals(name)) {
                foundName = true;
                break;
            }
        }
        if (foundName) throw new Exception("Device " + name + " already exists");
        if (type == "SmartLight") {
            smartThings.add(new SmartLight(name));
        } else if (type == "SmartThermostat") {
            smartThings.add(new SmartThermostat(name));
        } else throw new Exception("Invalid device type: " + type);
    }

    //TODO: Method to controlDevice given name
    // Parameters: name (of device to control), controlCommand ('1' for on, '0' for off, '+' for increase, '-' for decrease)
    // Return: true if command changes Device setting, false otherwise
    // Modifies: changes setting of device "name" based on controlCommand, calling appropriate Controllable or Device method
    //Exceptions: *must* throw Exception if name is not a device in smartThings or if controlCommand is invalid
    public boolean controlDevice(String deviceName, char controlCommand) throws Exception {

        for (Device d: this.smartThings) {
            if (d.getName().equals(deviceName)) {
                switch (controlCommand) {
                    case '1': 
                        return d.turnOn();
                    case '0': 
                        return d.turnOff();
                    case '+':
                            return ((Controllable) d).increaseSetting();
                    case '-':
                            return ((Controllable) d).decreaseSetting();
                    default:
                        throw new Exception("Invalid control command: " + controlCommand);
                }
            }
        }
        throw new Exception("INVALID");
    }
}

//TODO: Create public class SmartHomeGUI which inherits from JFrame
public class SmartHomeGUI extends JFrame {

    private SmartHome myHome;

    public SmartHomeGUI() {

        super("Lab 5 DNB");
        myHome = new SmartHome("LAB 5");
        buildGUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //pack();
        setSize(300, 300);
        setVisible(true);
        
    }

    private void buildGUI() {

        System.out.println("Here");

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu dMan = new JMenu("Device Management");
        menuBar.add(dMan);

        JMenuItem clearTextAreaItem = new JMenuItem("Clear text area");
        dMan.add(clearTextAreaItem);

        dMan.addSeparator();

        JMenu addDevicesItem = new JMenu("Add device...");
        dMan.add(addDevicesItem);

        JMenuItem addSmartLightItem = new JMenuItem("Add SmartLight");
        JMenuItem addSmartThermostatItem = new JMenuItem("Add SmartThermostat");
        addDevicesItem.add(addSmartLightItem);
        addDevicesItem.add(addSmartThermostatItem);

        /*
        Set up GridBagLayout and create a GridBagConstraints object
        */

        Container ct = getContentPane();
        ct.setLayout(new GridBagLayout());
        
        GridBagConstraints c =
            new GridBagConstraints();
        
        c.fill = GridBagConstraints.BOTH;

        /*Initialize, populate and place the JComboBox deviceSelector
        */
        // THIS LINE OF CODE IS USEFUL FOR THE ACTUAL LAB...
        JComboBox deviceSelector = new JComboBox(myHome.getSmartDevices().toArray());
        
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 20; c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        ct.add(deviceSelector, c);

        /*Initialize statusArea to be an appropriate size, ensure it is not editable by users, and place within GUI*/
        JTextArea statusArea = new JTextArea(30,20);
        statusArea.setEditable(false);
        c.gridx = 0; c.gridy = 3;
        c.gridwidth = 20; c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        ct.add(statusArea, c);

        /*Initialize and place all Jbutton widgets*/
        JButton onButton = new JButton("On");
        c.gridx = 0; c.gridy = 1;
        c.gridwidth = 10; c.gridheight = 1;
        ct.add(onButton, c);

        JButton offButton = new JButton("Off");
        c.gridx = 0; c.gridy = 2;
        c.gridwidth = 10; c.gridheight = 1;
        ct.add(offButton, c);

        JButton increaseButton = new JButton("+");
        c.gridx = 11; c.gridy = 1;
        c.gridwidth = 9; c.gridheight = 1;
        ct.add(increaseButton, c);

        JButton decreaseButton = new JButton("-");
        c.gridx = 11; c.gridy = 2;
        c.gridwidth = 9; c.gridheight = 1;
        ct.add(decreaseButton, c); 

        /*Add listeners to buttons, menu either the "old" way*/
        ButtonListener addButtonListenerOn = new ButtonListener(statusArea, deviceSelector, myHome, '1');
        ButtonListener addButtonListenerOff = new ButtonListener(statusArea, deviceSelector, myHome, '0');
        ButtonListener addButtonListenerIncrease = new ButtonListener(statusArea, deviceSelector, myHome, '+');
        ButtonListener addButtonListenerDecrease = new ButtonListener(statusArea, deviceSelector, myHome, '-');
    
        
        onButton.addActionListener(addButtonListenerOn);
        offButton.addActionListener(addButtonListenerOff);
        increaseButton.addActionListener(addButtonListenerIncrease);
        decreaseButton.addActionListener(addButtonListenerDecrease);


        DeviceManageListener addLightListener = new DeviceManageListener(myHome, deviceSelector, statusArea, "SmartLight");
        addSmartLightItem.addActionListener(addLightListener);
        DeviceManageListener addThermostatListener = new DeviceManageListener(myHome, deviceSelector, statusArea, "SmartThermostat");
        addSmartThermostatItem.addActionListener(addThermostatListener);

        /*Call updateStatus() to update the text in statusArea */
        updateStatus("", statusArea);

    }

    // Get selected device name
    private String selectedName() {
        return (String) deviceSelector.getSelectedItem();
    }

    //Control the selected device with given command
    private void controlSelectedDevice(char command) {
        try {
            myHome.controlDevice(selectedName(), command);
            System.out.println("word");
            updateStatus("");
        } catch (Exception ex) {
            updateStatus(ex.getMessage());
        }
    }

    

    class DeviceManageListener implements ActionListener {

        // If a listener interacts with/changes/reads/etc other components, 
        // you should make member variables of those types in the listener class 
        // and assign them values in the listener constructor
        private SmartHome myHome;
        private JComboBox deviceSelector;
        private JTextArea statusArea;
        private String type;

        public DeviceManageListener(SmartHome myHome, JComboBox deviceSelector, JTextArea statusArea, String type) {
            this.myHome = myHome;
            this.deviceSelector = deviceSelector;
            this.statusArea = statusArea;
            this.type = type;
        }

        // Hint: Change this so it prompts the user for both type and name
        // Then use those to add the device
        public void actionPerformed(ActionEvent e) {
            // Open a dialog box to prompt name
            String newName = JOptionPane.showInputDialog("Please enter name of new device");
            
            // Take new name and make a new SmartLight (or SmartThermostat) in myHome
            try {            
                myHome.addDevice(type, newName); // Calls method from SmartHome
                for (Object d: this.myHome.smartThings) {
                    if (((Device)d).getName().equals(newName))
                        deviceSelector.addItem(d); // Add new option to JComboBox
                }
                updateStatus(null, statusArea);
            } catch (Exception ex) {
                updateStatus(ex.getMessage(), statusArea);
            }
        }
    }

    class ButtonListener implements ActionListener {

        private JTextArea statusArea;
        private SmartHome myHome;
        private JComboBox deviceSelector;
        private char command;

        public ButtonListener(JTextArea statusArea, JComboBox deviceSelector, SmartHome myHome, char command) {
            this.statusArea = statusArea;
            this.deviceSelector = deviceSelector;
            this.myHome = myHome;
            this.command = command;
        }

        public void actionPerformed(ActionEvent e) {
            // Turn selected SmartDevice on
            String err = new String();
            try {     
                if (!myHome.controlDevice(((Device)deviceSelector.getSelectedItem()).getName(), command)) {
                    switch(command) {
                        case '1':
                            err = "**" + ((Device)deviceSelector.getSelectedItem()).getName() + " is on";
                            break;
                        case '0':
                            err = "**" + ((Device)deviceSelector.getSelectedItem()).getName() + " is off";
                            break;
                        case '+':
                            err = "**" + ((Device)deviceSelector.getSelectedItem()).getName() + " is at max";
                            break;
                        case '-':
                            err = "**" + ((Device)deviceSelector.getSelectedItem()).getName() + " is at min";
                            break;
                    }
                }
                updateStatus(err, statusArea);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
        }
    }

    private void updateStatus(String errorMsg, JTextArea statusArea) {
        StringBuilder sb = new StringBuilder("Device Status:\n");
        for (Device d : myHome.getSmartDevices()) sb.append(d.performFunction()).append("\n");
        if (errorMsg != null) sb.append(errorMsg).append("\n");
        statusArea.setText(sb.toString());
    }
    public static void main(String[] args) {

        SmartHomeGUI mainGUI = new SmartHomeGUI();
        
    }

}
