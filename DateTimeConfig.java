import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateTimeConfig implements Comparable<Object> {
	
	protected Date entryTime;

	public String getFormattedDateTime(Date datetimein,String dateFormat) {
		String formattedD = null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat); //("MM/dd/yyyy HH:mm:ss");
		formattedD = format.format(datetimein);
		return formattedD;
	}

	public Date stringtoDate(String indatetime,String dateFormat) {
		Date indate = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);//("MM/dd/yyyy HH:mm:ss");
			indate = sdf.parse(indatetime);
			this.entryTime = indate;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return indate;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getTimeDifference(Date inTime, Date exTime) {
		String dif = null;
		
		try {
			long diff = exTime.getTime() - inTime.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds. ");

			dif = "Days - " + diffDays + ",Hours - " + diffHours + ",Minutes - "+ diffMinutes + ",Seconds - " + diffSeconds;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dif;
	}
	
	@Override
	public int compareTo(Object o) { //to get the date in ascending order
		Date date1 = null;
		Date date2 = null;
		
		try {
			date1 = this.entryTime;
			DateTimeConfig other = (DateTimeConfig) o;
			date2 = other.entryTime;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return date1.compareTo(date2);
	}

	@Override
	public String toString() {
		return "entryTime=" + entryTime;
	}
	
	public String DateValidate(String entrydate, Scanner sc) { //validating date
		boolean validate = false;

		while (!validate) {
			String newEntryDay = entrydate.split(" ")[0];
			String month = newEntryDay.split("/")[0];
			String day = newEntryDay.split("/")[1];
			//String year = newEntryDay.split("/")[2];

			int month1 = Integer.parseInt(month);
			int day1 = Integer.parseInt(day);
			
			if (month1 > 12) {
				System.out.println("invalid month try again: ");
				entrydate = sc.nextLine();
			} else if (day1 > 31 || day1 < 1) {
				System.out.println("invalid date try again: ");
				entrydate = sc.nextLine();
			} else {
				validate = true;
			}
		}
		return entrydate;
	}
	
	public String TimeValidate(String entrytime,Scanner sc){ //validating date
		boolean isValid = false;
		
		while(!isValid){
			String newEntryTime = entrytime.split(" ")[1];
			String hour = newEntryTime.split(":")[0];
			String minutes = newEntryTime.split(":")[1];
			String seconds = newEntryTime.split(":")[2];
			
			int h = Integer.parseInt(hour);
			int m = Integer.parseInt(minutes);
			int s = Integer.parseInt(seconds);
			
			if(h>24){
				System.out.println("Invalid hours.Try again!: ");
				entrytime = sc.nextLine();
			}else if(m>60){
				System.out.println("Invalid minutes.Try again!: ");
				entrytime = sc.nextLine();
			}else if(s>60){
				System.out.println("Invalid seconds.Try again!: ");
				entrytime = sc.nextLine();
			}else{
				isValid = true;
			}
		}
		return entrytime;
	}
}
