import java.util.Date;

public class Motorbike extends Vehicle {
	private int engineSize;
	
	public Motorbike() {
		super();
	}

	public Motorbike(int engineSize,String idplate, String brand, Date entryTime, Date exitTime) {
		super(idplate, brand, entryTime, exitTime);
		this.engineSize = engineSize;
	}

	public Motorbike(int engineSize) {
		super();
		this.engineSize = engineSize;
	}

	public int getEngineSize() {
		return engineSize;
	}

	public void setEngineSize(int engineSize) {
		this.engineSize = engineSize;
	}

	@Override
	String getVehicleType() {
		return "Motorbike";
	}

	@Override
	public String toString() {
		
		DateTimeConfig dtc=new DateTimeConfig();
		String exittime=null;
		
		if (exitTime != null) {
			exittime = dtc.getFormattedDateTime(exitTime, "MM/dd/yyyy HH:mm:ss");
		}
		return "Motorbike" + "," + " Engine size= " + engineSize + "," + " Id plate= " + idplate + "," + " Brand= "
				+ brand + "," + " Entry dateTime= " + dtc.getFormattedDateTime(entryTime, "MM/dd/yyyy HH:mm:ss") + ","
				+ " Exit datetime= " + exittime;
	}

}
