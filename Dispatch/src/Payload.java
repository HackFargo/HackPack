import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Scanner;

/*Notes:
 * I am aware the redeclaring a new URL is more expensive than updating an existing one,
 * however, the only set methods in the java.net.URL library take more than 5 parameters.
 * Hoping to create a more expressive tool, this is a trade off.
 */

public class Payload {

	private URL target;
	private String source;
	
	/** CONSTRUCTORS 
	 * 
	 */
	public Payload() {}
	
	public Payload(String target)
	{
		this.setTarget(target);
		
	} // exit Payload method.
	
	
	/////////////////
	//TARGET ANALYSIS
	/////////////////
	public boolean isSet()
	{
		return this.target != null;
	} // exit isSet method.
	
	public URL getTarget()
	{
		return this.target;
	} // exit getTarget method.
	
	public void setTarget(String target)
	{
		try 
		{
			this.target = new URL(target);
		} 
		
		catch (MalformedURLException e) 
		{
			System.err.print(e.getLocalizedMessage());
			e.printStackTrace();
		}
	} // exit setTarget method.

	
	/////////////////
	//SOURCE ANALYSIS
	/////////////////
	public boolean isEmpty()
	{
		return this.source == null;
	} // exit isEmpty method.
	
	
	public String getSource()
	{
		return this.source;
	} // exit getSource method.
	
	
	private void setSource(InputStream inputStream)
	{
		Scanner sc = new Scanner(inputStream);
		while (sc.hasNextLine())
		{
			this.source += sc.nextLine();
		}
	} // exit getSource method.
	
	///////////
	//EXECUTION
	///////////
	private boolean isReachable()
	{
		boolean result = false;
		
		//Only analyze response validity and response time if we have a target.
		if (this.isSet())
		{
				try {
					String host = this.getTarget().getHost();
					InetAddress destination = InetAddress.getByName(host);
					destination.isReachable(5000);
					result = true;
				} 
				//Address not resolvable.
				catch (UnknownHostException e) {
					System.err.print(e.getMessage());
				} 
				//Timeout has occurred.
				catch (IOException e) {
					System.err.print(e.getMessage());
				}
		}
		
		else
		{
			System.err.print("Payload target is undefined.");
		}
			
		
		return result;
		
	} // exit isReachable method.
	
	public void GET()
	{		
		//ONLY if URL target is set and validated, proceed.
		if (this.isSet())
		{
			try {
				HttpURLConnection pipe = (HttpURLConnection) this.getTarget().openConnection();
				pipe.setRequestMethod("GET");
				pipe.setRequestProperty("Accept", "application/json");
				
				if (pipe.getResponseCode() != 200)
				{
					throw new RuntimeException("Request Failed." + pipe.getResponseCode() + pipe.getResponseMessage());
				}

				this.setSource(pipe.getInputStream());
				pipe.disconnect(); //break connection from web server
				
			} 
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.err.print("Payload target is undefined.");
		}
		
	} // exit GET method.
	
	////////////////////////
	//EXPECTED JAVA-Y THINGS
	////////////////////////
	public String toString()
	{
		return MessageFormat.format("Target: {0} Source: {1}", this.getTarget(), this.isEmpty());
	}
	
} // exit Payload class.