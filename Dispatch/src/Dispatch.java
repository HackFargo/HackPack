import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Dispatch {

	
	private String callType;
	
	private final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	private Calendar startDate = Calendar.getInstance();
	private Calendar endDate;
	
	private final DateFormat timestampFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	private Calendar lastUpdated; // last time we pulled a GET request
	

	public Dispatch ()
	{
		startDate = Calendar.getInstance();
		startDate.add(Calendar.DAY_OF_MONTH, -15);
		endDate = Calendar.getInstance();
	}
	
	public Dispatch ( String callType )
	{
		this.startDate = Calendar.getInstance();
		this.startDate.add(Calendar.DAY_OF_MONTH, -15);
		this.endDate = Calendar.getInstance();
		
		this.setCallType(callType);
		
	}
	
	public Dispatch ( String startDate, String endDate )
	{
		
	}
	
	public Dispatch ( String callType, String startDate, String endDate )
	{
		
	}
	

	//User doesn't get to say when they last updated..
	private void setLastUpdated ()
	{
		this.lastUpdated = Calendar.getInstance();
	}
	
	//This will be used to 
	private String[] parseDate (String Date)
	{
		String[] units = new String[3];
		
		units[0] = Date.substring(0, 2);
		units[1] = Date.substring(3, 5);
		units[2] = Date.substring(6, 10);

		System.out.println(units[0] + " " + units[1] + " " + units[2]);
		
		return units;
	}
	
	
	public void Update ()
	{
		//redo GET
		this.setLastUpdated();
	}
	
	
	public String getStartDate ()
	{
		this.parseDate("01-01-2014");
		//it is a pretty strong assumtion that this is set.
		return dateFormat.format(this.startDate.getTime());
	}
	
	public String getEndDate ()
	{
		//it is a pretty strong assumtion that this is set.
		return dateFormat.format(this.endDate.getTime());
	}
	
	public String getCallType ()
	{
		//if not set, will return null. should be fine.
		return this.callType;
	}
	
	public String getLastUpdated ()
	{
		return timestampFormat.format(this.lastUpdated.getTime());
	}
	
	public void setStartDate ( int month, int day, int year )
	{
		
	}
	
	public void setEndDate ( int month, int day, int year )
	{
		
	}
	

	
	
	public void setCallType ( String callType )
	{
		//Should probably make sure this doesn't get out of hand..
		this.callType = callType;
	}
	
	
	public String ToString ()
	{
		return MessageFormat.format("Call Type: {0} \nStart Date: {1} \nEnd Date: {2} \nLast Updated: {3}\n", this.getCallType(), this.getStartDate(), this.getEndDate(), this.getLastUpdated() );
	}
	
	
}
