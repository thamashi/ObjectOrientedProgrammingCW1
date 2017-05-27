import java.util.Date;

public abstract class Vehicle {
	protected String idplate;
	protected String brand;
	protected Date entryTime;
	protected Date exitTime;

	abstract String getVehicleType();

	public Vehicle() {
		super();
	}

	public Vehicle(String idplate, String brand, Date entryTime, Date exitTime) { //to make the program efficient
		super();
		this.idplate = idplate;
		this.brand = brand;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
	}

	public String getIdplate() { //to access private variables(getters/setters)
		return idplate;
	}

	public void setIdplate(String idplate) {
		this.idplate = idplate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	
	@Override
	public String toString() {
		return "Vehicle [idplate=" + idplate + ", brand=" + brand + ", entryTime=" + entryTime + ", exitTime="
				+ exitTime + "]";
	}


}
