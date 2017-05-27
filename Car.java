import java.util.Date;

public class Car extends Vehicle {
	
	@Override
	public String toString() {
		DateTimeConfig dtc=new DateTimeConfig();
		String exittime=null;
		
		if (exitTime!=null) {
		exittime=dtc.getFormattedDateTime(exitTime,"MM/dd/yyyy HH:mm:ss");	
		}
		return "Car"+ ","+" no.of doors= "+noofdoors +","+" colour= " +color+","+" Id plate= "+idplate + "," +" Brand= "+brand + ","
				+" Entry dateTime= "+dtc.getFormattedDateTime(entryTime,"MM/dd/yyyy HH:mm:ss") + "," +" Exit datetime= "+exittime ;
	}

	private int noofdoors;
	private String color;

	public Car(int noofdoors, String color) {
		super();
		this.noofdoors = noofdoors;
		this.color = color;
	}
	public Car(int noofdoors, String color,String idplate,String brand,	Date entryTime,	Date exitTime) {
		super(idplate, brand, entryTime, exitTime);
		this.noofdoors = noofdoors;
		this.color = color;
	}
	public Car() {}

	public int getNoofdoors() {
		return noofdoors;
	}

	public void setNoofdoors(int noofdoors) {
		this.noofdoors = noofdoors;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	String getVehicleType() {
		return "Car";
	}

}
