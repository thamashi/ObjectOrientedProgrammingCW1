import java.util.Date;

public class Van extends Vehicle {
	private double cargoVolume;

	public double getCargoVolume() {
		return cargoVolume;
	}

	public Van() {
		super();
	}

	public Van(double cargoVolume, String idplate, String brand, Date entryTime, Date exitTime) {
		super(idplate, brand, entryTime, exitTime);
		this.cargoVolume = cargoVolume;
	}

	public Van(double cargoVolume) {
		super();
		this.cargoVolume = cargoVolume;
	}

	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	@Override
	public String toString() {
		DateTimeConfig dtc = new DateTimeConfig();
		String exittime = null;
		
		if (exitTime != null) {
			exittime = dtc.getFormattedDateTime(exitTime, "MM/dd/yyyy HH:mm:ss");
		}
		return "Van" + "," + " Cargo volume= " + cargoVolume + "," + " Id plate= " + idplate + "," + " Brand= " + brand
				+ "," + " Entry dateTime= " + dtc.getFormattedDateTime(entryTime, "MM/dd/yyyy HH:mm:ss") + ","
				+ " Exit datetime= " + exittime;
	}
	
	@Override
	String getVehicleType() {
		return "Van";
	}

}
