import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WestminsterCarParkManager implements CarParkManager {
	
	private ArrayList<Vehicle> vehicleList;
	
	private int parkingLots;
	private int parkingavailableLots = 20;//number of available parking lots

	public WestminsterCarParkManager(int ListLength) {
		this.parkingLots = ListLength;
		vehicleList = new ArrayList<Vehicle>();
	}

	public void addVehicle(Vehicle v) {// adding a vehicle
		vehicleList.add(v);
	}

	public Vehicle deleteVehicle(String IDPlate, Date exitTime) {// delete a vehicle
		Vehicle v = null;

		for (int i = 0; i < vehicleList.size(); i++) {
			if (vehicleList.get(i).getIdplate().trim().equalsIgnoreCase(IDPlate)) {
				v = vehicleList.get(i);
				v.setExitTime(exitTime);
				vehicleList.remove(i);
				break;
			}
		}

		System.out.println("Deleted Vehicle Type: " + v.getVehicleType());
		switch (v.getVehicleType()) {// resetting parking lots according to the vehicle type
		case "Car":
		case "Motorbike":
			resetLots(1); //resetting lots for cars and motorbikes
			break;
		case "Van":
			resetLots(2); //resetting lots for vans
			break;
		default:
			break;
		}
		return v;
	}

	public void displayAllVehicles() {// displaying all vehicles currently parked
										
		for (int i = 0; i < vehicleList.size(); i++) {

			System.out.println(vehicleList.get(i).toString());

		}

	}

	public void displayVehicles(Date datetime) { // display vehicles on a specific day
												
		try {
			ArrayList<Vehicle> savedvlist = readsavedFile();
			DateTimeConfig dtc = new DateTimeConfig();

			for (Vehicle vehicle : savedvlist) {

				String entryD = dtc.getFormattedDateTime(vehicle.getEntryTime(), "MM/dd/yyyy");
				Date entryD1 = dtc.stringtoDate(entryD, "MM/dd/yyyy");

				if (entryD1.equals(datetime)) {
					System.out.println(vehicle.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void statisticsVehicles() {// percentages of parked vehicles
		int c = 0, v = 0, m = 0, tot = 0;
		double percentageCar = 0.0, percentageVan = 0.0, percentageM = 0.0;
		tot = vehicleList.size();

		for (int i = 0; i < vehicleList.size(); i++) {
			switch (vehicleList.get(i).getVehicleType().toUpperCase()) {
			case "CAR":
				c++;
				break;
			case "VAN":
				v++;
				break;
			case "MOTORBIKE":
				m++;
				break;
			default:
				break;
			}
		}
		percentageCar = ((double) c / (double) tot) * 100; // percentage of cars
		percentageVan = ((double) v / (double) tot) * 100;// percentage of vans
		percentageM = ((double) m / (double) tot) * 100;// percentage of motor bikes
		System.out.println("Percentage of Cars: " + percentageCar + "%");
		System.out.println("Percentage of Vans: " + percentageVan + "%");
		System.out.println("Percentage of Motorbikes: " + percentageM + "%");

	}

	public Vehicle displaylongtimeVehicle() {// longest parked vehicle
		Vehicle v = null;
		List<DateTimeConfig> list = new ArrayList<DateTimeConfig>();

		for (int i = 0; i < vehicleList.size(); i++) {
			DateTimeConfig dtc = new DateTimeConfig();
			dtc.setEntryTime(vehicleList.get(i).getEntryTime());
			list.add(dtc);
		}
		Collections.sort(list); // sorting the list
		Date longT = null;
		
		for (DateTimeConfig d : list) { 
			System.out.println(d);
			longT = d.getEntryTime();
			break;
		}
		for (int i = 0; i < vehicleList.size(); i++) {
			if (vehicleList.get(i).getEntryTime().equals(longT)) {
				v = vehicleList.get(i);
				break;
			}
		}
		return v;
	}

	public Vehicle displaylastInVehicle() {// displaying last entered vehicle
		Vehicle v = null;
		List<DateTimeConfig> list = new ArrayList<DateTimeConfig>();

		for (int i = 0; i < vehicleList.size(); i++) { //adding the vehicle list
			DateTimeConfig dtc = new DateTimeConfig();
			dtc.setEntryTime(vehicleList.get(i).getEntryTime());
			list.add(dtc);
		}
		Collections.sort(list);
		Date longT = null;
		int difference = 0;
		for (DateTimeConfig d : list) { //getting the longest parked vehicle
			int j = list.size() - 1;
			
			if (difference == j) {
				System.out.println(d);
				longT = d.getEntryTime();
				break;
			} else {
				difference++;
			}

		}
		for (int i = 0; i < vehicleList.size(); i++) {
			if (vehicleList.get(i).getEntryTime().equals(longT)) {
				v = vehicleList.get(i);
				break;
			}
		}
		return v;

	}

	public boolean CheckAvailability(int vehicleType) {// check available no of parking lots
														 
		boolean isAvailable = false;

		try {
			switch (vehicleType) {
			case 1: // car

			case 3: // motor bike
				if (parkingavailableLots >= 1) {
					System.out.println("parking available");
					isAvailable = true;
				}
				break;

			case 2: // van
				if (parkingavailableLots >= 2) {
					System.out.println("parking available");
					isAvailable = true;
				}
				break;

			default:
				System.out.println("parking not available");
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isAvailable;
	}

	public boolean processParking() { //the procedures
		boolean exit = false;

		System.out.println("******** Welcome To the Carpark Management System ********");
		
		System.out.println("\nAvailable lots: " + parkingavailableLots);
		
		System.out.println("\nselect a procedure: "); //selecting a procedure to start
		
		System.out.println("A - Add vehicle\n" + "B - Delete vehicle\n" + "C - List of vehicles currently parked\n"
				+ "D - Percentages of the vehicles parked\n" + "E - Vehicle parked for the longest time\n"
				+ "F - Last parked vehicle\n" + "G - Display vehicles on the given date\n" + "H - Parking charges \n"
				+ "X - Exit\n");
		
		Scanner s = new Scanner(System.in);
		String choice = s.next().toUpperCase();

		switch (choice) { //selecting the vehicle type
		case "A":
			System.out.println("Press 1 if you want to add a Car");
			System.out.println("Press 2 if you want to add a Van");
			System.out.println("Press 3 if you want to add a Motorbike");
			int choice1 = s.nextInt();

			if (CheckAvailability(choice1)) { //checking availability for entered vehicle
				s.nextLine();
				System.out.println("Enter ID-Plate No.(XX-9999): ");
				String idplate = s.nextLine();
				
				while(!idValidation(idplate)){ //id validation
					idplate = s.next();
				}
				
				System.out.println("What is the brand?");
				String brand = s.nextLine();
				
				switch (choice1) {
				case 1:
					// if the choice is car
					int door = 0;
					System.out.println("Enter the number of doors: ");
					door = s.nextInt();
					while (door > 5) { //validating number of doors
						System.out.println("Invalid number try again");
						door = s.nextInt();
					}
					s.nextLine();
					System.out.println("Enter the colour: ");
					String color = s.nextLine();
					
					while(!colourValidation(color)){ //validating color
						color = s.nextLine();
					}
					//enter the entry date and time
					System.out.println("Enter the entry Date & Time (MM/dd/yyyy HH:mm:ss): ");
					String entryT = s.nextLine();
					
					DateTimeConfig dtm = new DateTimeConfig();
					entryT = dtm.DateValidate(entryT,s);//validating the date
					entryT = dtm.TimeValidate(entryT, s);//validating time
					
					
					Date entryTime = dtm.stringtoDate(entryT, "MM/dd/yyyy HH:mm:ss");

					Vehicle v = new Car(door, color, idplate, brand, entryTime, null);
					this.addVehicle(v);
					System.out.println(v.toString());
					storeparkedData(v);
					this.LotAllocation(1);
					break;
				case 2:
					// if the choice is van
					double cargoVolume = 0;
					System.out.println("Enter the Cargo volume: ");
					cargoVolume = s.nextDouble();
					s.nextLine();

					System.out.println("Enter the entry Date & Time (MM/dd/yyyy HH:mm:ss): ");
					String entryT1 = s.nextLine();
					
					DateTimeConfig dtm1 = new DateTimeConfig();
					entryT1 = dtm1.DateValidate(entryT1,s);
					entryT1 = dtm1.TimeValidate(entryT1, s);
					
					Date entryTime1 = dtm1.stringtoDate(entryT1, "MM/dd/yyyy HH:mm:ss");

					Vehicle v1 = new Van(cargoVolume, idplate, brand, entryTime1, null);
					this.addVehicle(v1);
					System.out.println(v1.toString());
					storeparkedData(v1);
					this.LotAllocation(2);
					break;
				case 3:
					int engineSize = 0;
					System.out.println("Enter the Engine size: ");
					engineSize = s.nextInt();

					s.nextLine();

					System.out.println("Enter the entry Date & Time (MM/dd/yyyy HH:mm:ss): ");
					String entryT2 = s.nextLine();
					
					DateTimeConfig dtm2 = new DateTimeConfig();
					entryT2 = dtm2.DateValidate(entryT2,s);
					entryT2 = dtm2.TimeValidate(entryT2, s);
					
					
					Date entryTime2 = dtm2.stringtoDate(entryT2, "MM/dd/yyyy HH:mm:ss");

					Vehicle v2 = new Motorbike(engineSize, idplate, brand, entryTime2, null);
					this.addVehicle(v2);
					System.out.println(v2.toString());
					storeparkedData(v2);
					this.LotAllocation(1);
					break;
				}
			}
			break;
		case "B":
			if (vehicleList.size() > 0) {
				s.nextLine();
				System.out.println("Enter ID-Plate No: ");
				String idplate = s.nextLine();
				System.out.println("Enter the exit Date & Time (MM/dd/yyyy HH:mm:ss): ");
				String exitT = s.nextLine();
	
				DateTimeConfig dtm2 = new DateTimeConfig();
				Date exitTm = dtm2.stringtoDate(exitT, "MM/dd/yyyy HH:mm:ss");
				Vehicle v = deleteVehicle(idplate.trim(), exitTm);
				ArrayList<Vehicle> savedvlist = readsavedFile();
				for (int i = 0; i < savedvlist.size(); i++) {
					if (savedvlist.get(i).getIdplate().trim().equalsIgnoreCase(idplate)) {
						savedvlist.remove(savedvlist.get(i));
						break;
					}
				}

				savedvlist.add(v);
				updateFileData(savedvlist);
				System.out.println("Deleted Vehicle is: " + v.toString());
			} else {
				System.out.println("no vehicles added, try again!!");
			}
			break;

		case "C":
			if (vehicleList.size() > 0) {
				displayAllVehicles();
			} else {
				System.out.println("no vehicles added,try again!!");
			}
			break;
		case "D":
			if (vehicleList.size() > 0) {
				statisticsVehicles();
			} else {
				System.out.println("no vehicles added,try again!!");
			}

			break;
		case "E":
			if (vehicleList.size() > 0) {
				System.out.println("Long time parked vehicle is : " + displaylongtimeVehicle().toString());
			} else {
				System.out.println("no vehicles added,try again!!");
			}
			break;
		case "F":
			if (vehicleList.size() > 0) {
				System.out.println("Last parked vehicle is : " + displaylastInVehicle().toString());
			} else {
				System.out.println("no vehicles added,try again!!");
			}
			break;
		case "G":
			s.nextLine(); //displaying vehicles on a certain date.
			System.out.print("Enter Month(MM): ");
			String month = s.next();
			System.out.print("Enter Day(dd): ");
			String day = s.next();
			System.out.print("Enter Year(yyyy): ");
			String year = s.next();

			String date3 = month + "/" + day + "/" + year;
			DateTimeConfig dtc = new DateTimeConfig();
			Date date4 = dtc.stringtoDate(date3, "MM/dd/yyyy");
			displayVehicles(date4);
			break;
		case "H":
			if (vehicleList.size() > 0) { //calculation for the duration of the parking
				s.nextLine();
				System.out.println("Enter ID-Plate No: ");
				String idplate1 = s.nextLine();
				System.out.println("Enter the exit Date & Time (MM/dd/yyyy HH:mm:ss): ");
				String exitT1 = s.nextLine();
				
				DateTimeConfig dtm3 = new DateTimeConfig();
				Date exitTm1 = dtm3.stringtoDate(exitT1, "MM/dd/yyyy HH:mm:ss");
				displayParkingCharges(idplate1, exitTm1);
			} else {
				System.out.println("no vehicles added,try again!!");
			}
			break;
		case "X":
			System.exit(0);
			break;

		default:
			System.out.println("Invalid Procedure! Try Again");
			break;
		}
		return exit;
	}

	public void LotAllocation(int lotNum) {
		try {
			parkingavailableLots = parkingavailableLots - lotNum;
			if (parkingavailableLots == 0) {
				System.out.println("All the lots are allocated");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetLots(int lotNum) {
		try {
			parkingavailableLots = parkingavailableLots + lotNum;
			if (parkingavailableLots == 20) {
				System.out.println("All the lots are available");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void storeparkedData(Vehicle v) { //writing the parked vehicles to a text file
		try {
			File parkedVehicles = new File("parkedVehicles.txt");
			if (!parkedVehicles.exists()) {
				parkedVehicles.createNewFile();
			}
			
			BufferedWriter Bwriter = new BufferedWriter(new FileWriter(parkedVehicles, true));
			Bwriter.append(v.toString()); //enter details to the existing line
			Bwriter.newLine();

			Bwriter.flush();
			Bwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Vehicle> readsavedFile() {
		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
		try {

			File parkedVehicles = new File("parkedVehicles.txt");
			FileInputStream in = new FileInputStream(parkedVehicles);
			BufferedReader read = new BufferedReader(new InputStreamReader(in));

			String line = read.readLine().trim();
			Vehicle v = null;
			DateTimeConfig dtc = new DateTimeConfig();
			Date exitT = null;

			while (line != null) { //reading the saved data
				if (line.isEmpty()) {
					System.out.println("Line is empty....");
					line = read.readLine();
				} else {
					String inline1 = line;
					switch (inline1.split(",")[0].trim().toUpperCase()) {
					case "CAR":
						String exittime = inline1.split(",")[6].trim();
						if (exittime.equals(null)) {//the exit times which are not null are taken and edited as exited
							exitT = dtc.stringtoDate(inline1.split(",")[6].trim(), "MM/dd/yyyy HH:mm:ss");
						}
						v = new Car(Integer.parseInt(inline1.split(",")[1].split("=")[1].trim()),
								inline1.split(",")[2].split("=")[1].trim(), inline1.split(",")[3].split("=")[1].trim(),
								inline1.split(",")[4].split("=")[1].trim(),
								dtc.stringtoDate(inline1.split(",")[5].split("=")[1].trim(), "MM/dd/yyyy HH:mm:ss"),
								exitT);
						vehicleList.add(v); //adding the vehicles to a temporary list to update the file
						line = read.readLine();
						break;
					case "VAN":
						String exittime1 = inline1.split(",")[5].trim();
						if (exittime1.equals(null)) {
							exitT = dtc.stringtoDate(inline1.split(",")[5].trim(), "MM/dd/yyyy HH:mm:ss");
						}
						v = new Van(Double.parseDouble(inline1.split(",")[1].split("=")[1].trim()),
								inline1.split(",")[2].split("=")[1].trim(), inline1.split(",")[3].split("=")[1].trim(),
								dtc.stringtoDate(inline1.split(",")[4].split("=")[1].trim(), "MM/dd/yyyy HH:mm:ss"),
								exitT);
						vehicleList.add(v);
						line = read.readLine();
						break;

					case "MOTORBIKE":
						String exittime2 = inline1.split(",")[5].trim();
						if (exittime2.equals(null)) {
							exitT = dtc.stringtoDate(inline1.split(",")[5].trim(), "MM/dd/yyyy HH:mm:ss");
						}
						v = new Motorbike(Integer.parseInt(inline1.split(",")[1].split("=")[1].trim()),
								inline1.split(",")[2].split("=")[1].trim(), inline1.split(",")[3].split("=")[1].trim(),
								dtc.stringtoDate(inline1.split(",")[4].split("=")[1].trim(), "MM/dd/yyyy HH:mm:ss"),
								exitT);
						vehicleList.add(v);
						line = read.readLine();
						break;
					default:
						break;

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vehicleList;
	}

	public void updateFileData(ArrayList<Vehicle> vehiclesList) {
		try {
			File parkedVehicles = new File("parkedVehicles.txt");

			if (parkedVehicles.exists()) { //checking for the file
				FileWriter fwOb = new FileWriter("parkedVehicles.txt", false); 
				PrintWriter pwOb = new PrintWriter(fwOb, false); //flushing the data
				pwOb.flush();
				pwOb.close();
				fwOb.close();
			} else {
				if (parkedVehicles.createNewFile()) {
					System.out.println("File Created!..."); //if the file doesn't exist to create a file
				}
			}

			BufferedWriter Bwriter = new BufferedWriter(new FileWriter(parkedVehicles, true));
			for (Vehicle vehicle : vehiclesList) { //writing the updated data to the new vehicle list from the 
													//existing vehicle list 
				Bwriter.write(vehicle.toString());
				Bwriter.newLine();
			}

			Bwriter.flush();
			Bwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		CarParkManager cpm = new WestminsterCarParkManager(20);
		boolean exit = false;
		
		while (!exit) {
			exit = cpm.processParking();
		}
	}

	@Override
    public void displayParkingCharges(String IDPlate, Date exitTime) {
		
        Date inT = null;
        
        for (int i = 0; i < vehicleList.size(); i++) {
            if (vehicleList.get(i).getIdplate().trim().equalsIgnoreCase(IDPlate)) {
                inT = vehicleList.get(i).getEntryTime();
                break;
            }
        }
        DateTimeConfig dtc = new DateTimeConfig();
        String timediff = dtc.getTimeDifference(inT, exitTime);
        
        System.out.println(timediff);
        
        String timediffdays = timediff.split(",")[0].split("-")[1].trim();
        String timediffhrs = timediff.split(",")[1].split("-")[1].trim();
        String timediffmins = timediff.split(",")[2].split("-")[1].trim();
        int numdays = Integer.parseInt(timediffdays);
        int numhrs = Integer.parseInt(timediffhrs);
        int nummins = Integer.parseInt(timediffmins);
        
        double valueph = 3.0, valueadiph = 1.0, max24h = 30.0, totVal = 0.0;
        
        if (numhrs == 0 && nummins>0 ) {
            totVal = valueph;
        }
        if (numhrs > 0 && numhrs <= 3) {
            totVal += (valueph * numhrs);
        }
        if (nummins > 0) {
            totVal += 3;
        }
        if (numhrs > 3 && numhrs < 24) {
            totVal += (3 * 3);
            totVal = totVal + (valueadiph * numhrs);
        } 
        if (numdays>0) {
            totVal += (max24h*numdays);
        }
        System.out.println("Vehicle ID-Plate Is: " + IDPlate+ " Final Price Is: " + totVal);
    }

	public boolean idValidation(String idplate) {

		boolean isValid = false;

		Matcher m = Pattern.compile("[A-Z][A-Z]-[0-9][0-9][0-9][0-9]").matcher(idplate);
		if (m.find()) {
			isValid = true;
		} else {
			System.out.print(idplate + " is not a valid number plate.Try again: ");

		}

		return isValid;
	}
	
	public boolean colourValidation(String colour){
		boolean isValid = false;
		
		Matcher m1 = Pattern.compile("[a-z]").matcher(colour);
		if(!m1.find()){
			System.out.println("Invalid colour.Try again!: ");
		}else{
			isValid = true;
		}
		return isValid;
	}
	

}
