/*Author: Shashank A.V
 * Date: 28/02/2022
 * App.java aims at creating GUI interface using SWING and AWT toolkits for the flight tracking system which
 * displays the flight details from FlightFile.txt and accordingly calculates 
 * total distance covered, time taken, fuel consumption and co2 emission for the
 * flight selected
 * 
 */

import core.Airport;
import core.Flight;
import exception.DataNotFoundException;
import utils.DataExtractor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class App extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
    private DataExtractor fileManager;
    private JTable flightTable;
    private JTable flightPlanTable;
    private JScrollPane scrollPane;
    private int width;
    private int height;
    private String[][] data;


    private App() {
        super("Flights Tracking System"); 
        fileManager = new DataExtractor();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.width;
        height = screenSize.height;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //creating panel
        JPanel jPanel = new JPanel();
        data = getFlights();
         //creating new component table with flight details
        flightTable = new JTable(data, getColumnNames());

        scrollPane = new JScrollPane(flightTable);
        scrollPane.setPreferredSize(new Dimension((width * 69)/100, (height * 45)/100));
        flightTable.setFillsViewportHeight(true);
         //creating and setting size of GridBagLayout 
        panel = new JPanel(new GridBagLayout());
        panel.add(scrollPane);
        panel.setPreferredSize(new Dimension((width * 70)/100, height/2));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "Flights", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("times new roman", Font.PLAIN, 20)));
        jPanel.add(panel);

        updateFlightPlanTable(data[0][0], jPanel);
         //Adding labels for Distance, Time,Fuel consumption,Co2 emission
        JLabel distanceLabel = new JLabel("Distance (km) :");
        JLabel timeLabel = new JLabel("Time (hr):");
        JLabel fuelConsumptionLabel = new JLabel("Fuel Consumption (litre):");
        JLabel co2EmissionLabel = new JLabel("CO2 (kg):");
        
         //Creating and setting of text area for calculation of Distance, Time,Fuel consumption,Co2 emission based on flight selected
        JTextArea distanceTextArea = new JTextArea(1,5);
        distanceTextArea.setFont(new Font("times new roman", Font.PLAIN, 18));
        distanceTextArea.setEditable(false);
        distanceTextArea.setText(getDistance(getFlights()[0][0]));//display of total distance travelled from getDistance() based on flight selected

        JTextArea timeTextArea = new JTextArea(1,5);
        timeTextArea.setFont(new Font("times new roman", Font.PLAIN, 18));
        timeTextArea.setEditable(false);
        timeTextArea.setText(getTime(getFlights()[0][0])); //display of total time taken for travel from getTime() based on flight selected

        JTextArea fuelTextArea = new JTextArea(1,5);
        fuelTextArea.setFont(new Font("times new roman", Font.PLAIN, 18));
        fuelTextArea.setEditable(false);
        fuelTextArea.setText(getFuelConsumption(getFlights()[0][0]));//display of calculated fuel consumption value from getFuelConsumption() based on flight selected

        JTextArea co2EmissionTextArea = new JTextArea(1,5);
        co2EmissionTextArea.setFont(new Font("times new roman", Font.PLAIN, 18));
        co2EmissionTextArea.setEditable(false);
        co2EmissionTextArea.setText(getCO2Emession(getFlights()[0][0]));//display of calculated co2 emission value from getCO2Emession() based on flight selected
         
        //creating new panel and setting dimension for gridlayout to display calculated values
        JPanel jPanel1 = new JPanel(new GridLayout(8, 2));
        jPanel1.setPreferredSize(new Dimension((width * 11)/100, (height * 45)/100));
        jPanel1.add(distanceLabel);
        jPanel1.add(distanceTextArea);
        jPanel1.add(timeLabel);
        jPanel1.add(timeTextArea);
        jPanel1.add(fuelConsumptionLabel);
        jPanel1.add(fuelTextArea);
        jPanel1.add(co2EmissionLabel);
        jPanel1.add(co2EmissionTextArea);
        panel = new JPanel(new GridBagLayout());
        panel.add(jPanel1);
        panel.setPreferredSize(new Dimension((width * 12)/100, height/2));
        jPanel.add(panel);

        add(jPanel);

        ListSelectionModel model = flightTable.getSelectionModel();

        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!model.getValueIsAdjusting()){
                    String flightCode = (String) flightTable.getValueAt(flightTable.getSelectedRow(), 0);
                    String[][] flightPlan = getFlightPlan(flightCode);
                    flightPlanTable.setModel(new DefaultTableModel(flightPlan, new String[]{ ""}));

                    distanceTextArea.setText(getDistance(flightCode));
                    timeTextArea.setText(getTime(flightCode));
                    co2EmissionTextArea.setText(getCO2Emession(flightCode));
                    fuelTextArea.setText(getFuelConsumption(flightCode));
                }
            }
        });
        setVisible(true);
    }
  // Displaying flightplan based on flight selected
    private void updateFlightPlanTable(String flightCode, JPanel jPanel){
        flightPlanTable = new JTable(getFlightPlan(flightCode), new String[]{ ""});
        flightPlanTable.repaint();
        scrollPane = new JScrollPane(flightPlanTable);
        scrollPane.setPreferredSize(new Dimension((width * 14)/100, (height * 45)/100));
        flightPlanTable.setFillsViewportHeight(true);
        //adding new panel for flightplan and setting dimension for gridbag layout 
        panel = new JPanel(new GridBagLayout());
        panel.add(scrollPane);
        panel.setPreferredSize(new Dimension((width * 15)/100, height/2));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "Flight Plan", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("times new roman", Font.PLAIN, 20)));
        jPanel.add(panel);
    }

    private String[][] getFlights() {
        fileManager = new DataExtractor();
        try {
            List<Flight> flights = fileManager.getFlights();
            Flight[] flights1 = new Flight[flights.size()];
            data = new String[flights.size()][];
            flights.toArray(flights1);
            for (int i = 0; i < flights1.length; i++) {
                String[] flightData = new String[]{
                        flights1[i].getId(),
                        flights1[i].getPlaneType().getModel(),
                        flights1[i].getairportDeparture().getairportCode(),
                        flights1[i].getairportDestination().getairportCode(),
                        flights1[i].getDepartureDate().toString(),
                        flights1[i].getDepartureTime().toString(),
                        null
                };
                data[i] = flightData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
// adding column names to Jtable for Plane details
    String[] getColumnNames() {
        return new String[]{
                "Flight",
                "Plane",
                "Departure",
                "Destination",
                "Date",
                "Time"
        };
    }

    private String[][] getFlightPlan(String flightCode) {
        Flight flight = getFlight(flightCode);
        List<Airport> airports = flight.getFlightPlan().getAirports();
        data = new String[airports.size()][];
        Airport[] ports = new Airport[airports.size()];
        airports.toArray(ports);
        for (int i = 0; i < ports.length; i++) {
            String[] airportData = new String[]{
                    ports[i].getairportCode(),
            };
            data[i] = airportData;
        }
//        System.out.println(Arrays.deepToString(data));
        return data;
    }

    private Flight getFlight(String code){
        Optional<Flight> optionalFlight;
        Flight flight = null;
        try {
            optionalFlight = fileManager.getFlights()
                    .stream()
                    .filter(flight1 -> flight1.getId().equalsIgnoreCase(code))
                    .findFirst();
            if (!optionalFlight.isPresent())
                throw new DataNotFoundException("Flight not found.");
            flight = optionalFlight.get();
        } catch (IOException | DataNotFoundException e) {
            e.printStackTrace();
        }
        return flight;
    }

    private String getDistance(String flightCode) {
        String num = null;
        try {
            Flight flight = getFlight(flightCode);
            DecimalFormat df = new DecimalFormat("###.##");//setting decimal format for distance
            num = df.format(flight.calculateDistance());
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    private String getTime(String flightCode) {
        String num = null;
        try {
            Flight flight = getFlight(flightCode);
            DecimalFormat df = new DecimalFormat("###.##");
            num = df.format(flight.timeTaken());
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    private String getCO2Emession(String flightCode){
        String num = null;
        try {
            Flight flight = getFlight(flightCode);
            DecimalFormat df = new DecimalFormat("###.##"); //setting decimal format for co2emission value
            num = df.format(flight.CO2_emission());
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    private String getFuelConsumption(String flightCode){
        String num = null;
        try {
            Flight flight = getFlight(flightCode);
            DecimalFormat df = new DecimalFormat("###.##");//setting decimal format for fuelconsumption value
            num = df.format(flight.fuelConsumption());
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    public static void main(String[] args) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
		        App app = new App();
		        app.setVisible(true);
//		        
			}
		});

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
