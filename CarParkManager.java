import java.util.Date;

public interface CarParkManager {
	
	public abstract void addVehicle(Vehicle v);

	public abstract Vehicle deleteVehicle(String IDPlate, Date exitTime);

	public abstract void displayAllVehicles();

	public abstract void displayVehicles(Date datetime);

	public abstract void statisticsVehicles();

	public abstract Vehicle displaylongtimeVehicle();

	public abstract Vehicle displaylastInVehicle();

	public abstract boolean processParking();

	public abstract void displayParkingCharges(String IDPlate, Date exitTime);

}
