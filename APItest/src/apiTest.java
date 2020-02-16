
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random; 

public class apiTest {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://localhost:3030/flights?date=2020-02-15&origin=DFW";
	
	private static final String MAP_URL1 = "https://maps.googleapis.com/maps/api/distancematrix/json?";//origins=33.2305,-97.1467&destination=32.9222,-97.0409
	private static final String MAP_URL2 = "&key=AIzaSyDA9VXX0pYrU3czm5XmictKcLJU5jGzXSw";
	
	private static Flight sendGET() throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//our code----------------------------------------------------------------------------
			int number_of_flights = 100; //roughly how many flights to take into consideration
			int flight_size = 16;//how many elements in a flight split on {}
			String flight_data = response.toString(); //put all flights into a str
			//linked list
			Flight head = new Flight();
			Flight current_flight = head;
			
			flight_data = flight_data.substring(2, (561*number_of_flights)); //reduce to the number we need
			
			String flight_split[] = flight_data.split("[{}]"); //splits it up
			
			int max_data_elem = 14; //num of data elements read in each flight
			//loop for all flights
			for(int j =0; j<number_of_flights && flight_split.length>(j*max_data_elem); j++) {
				int current_data = 0;
				
				//make nextflight
				current_flight.next = new Flight();
				//set current to the new one
				current_flight = current_flight.next;
					
				//get the flight num
				String temp_arr[] = flight_split[j*flight_size+current_data].split("\"");//split on quote
				current_flight.setF_num(temp_arr[3]); //get data
				current_data++; //next data
				
				//get origin
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				String temp_data_arr[] = new String[5];
				temp_data_arr[0]=temp_arr[3];
				temp_data_arr[1]=temp_arr[7];
				current_data++;
				//weird split on comma
				temp_arr = flight_split[j*flight_size+current_data].split("[\",]"); 
				temp_data_arr[2]=temp_arr[2].substring(1);
				temp_data_arr[3]=temp_arr[5].substring(1);
				current_data++;
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				temp_data_arr[4]=temp_arr[3];
				current_flight.setOrigin(temp_data_arr); //set origin
				current_data++;
				
				//get destination
				current_data++; //skip word destination
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				temp_data_arr = new String[5];
				temp_data_arr[0]=temp_arr[3];
				temp_data_arr[1]=temp_arr[7];
				current_data++;
				//weird split on comma
				temp_arr = flight_split[j*flight_size+current_data].split("[\",]"); 
				temp_data_arr[2]=temp_arr[2].substring(1);
				temp_data_arr[3]=temp_arr[5].substring(1);
				current_data++;
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				temp_data_arr[4]=temp_arr[3];
				current_flight.setDest(temp_data_arr); //set origin
				current_data++;
				
				//get distance
				//weird split on comma
				temp_arr = flight_split[j*flight_size+current_data].split("[\",]"); 
				current_flight.setDistance(temp_arr[3].substring(1));
				current_data++;
				
				//get duration
				//weird split on comma
				temp_arr = flight_split[j*flight_size+current_data].split("[\",]"); 
				temp_data_arr = new String[3];
				temp_data_arr[0] = temp_arr[3];
				temp_data_arr[1] = temp_arr[7].substring(1);
				temp_data_arr[2] = temp_arr[10].substring(1);
				current_flight.setDuration(temp_data_arr); //set duration
				current_data++;
				
				//get departure and arrival
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				current_flight.setDepart(temp_arr[3]); //set depart
				current_flight.setArrive(temp_arr[7]); //set arrive
				current_data++;
				
				//get aircraft
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				temp_data_arr = new String[5];
				temp_data_arr[0] = temp_arr[3];
				current_data++;
				//weird split on comma
				temp_arr = flight_split[j*flight_size+current_data].split("[\",]"); 
				temp_data_arr[1] = temp_arr[2].substring(1);
				temp_data_arr[2] = temp_arr[5].substring(1);
				temp_data_arr[3] = temp_arr[8].substring(1);
				current_data++;
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				temp_data_arr[4] = temp_arr[2].substring(1);
				current_flight.setAircraft(temp_data_arr); //set aricraft
				current_data++;
				
				//get cost
				temp_arr = flight_split[j*flight_size+current_data].split("\"");
				current_flight.setCost(temp_arr[2].substring(1));
				
			}
			/*
			Flight test = head;
			while(test != null) {
				test = test.next;
				System.out.println(test.getF_num());
			}*/
			return head;
			//System.out.println(flight_data);
			//------------------------------------------------------------------------------------
		} else {
			System.out.println("GET request not worked");
			return new Flight();
		}

	}
	
	//origin and dest in form: lat,long
	private static String getTravelDistance(String origin, String destination, boolean toll, boolean highway, boolean ferries) throws IOException{
		String avoid ="";
		//check for avoidance
		if(toll || highway || ferries) {
			avoid += "&avoid=";
			if(toll) {
				avoid+="tolls|";
			}
			if(highway) {
				avoid+="highways|";
			}
			if(ferries) {
				avoid+="ferries|";
			}
			avoid = avoid.substring(0,avoid.length()-1);
		}
		
		String MAP_URL = MAP_URL1 + "origins=" + origin + "&destinations=" + destination + avoid + MAP_URL2;
		
		//System.out.println(MAP_URL);
		URL obj = new URL(MAP_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		//System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//our code----------------------------------------------------------------------------
			String travel_data = response.toString();//get everything
			
			String split_travel_data[] = travel_data.split("\"");//split on "
			
			String travel_time = split_travel_data[28];// get the travel time
			
			travel_time = travel_time.split("\\s")[2];// split on space to just get the number
			
			return travel_time; //return time in sec
			//------------------------------------------------------------------------------------
		} else {
			//System.out.println("GET request not worked");
			return "";
		}
	}
	
	private static Flight findFlight(Flight flightlist, String flight_number) {
		
		Flight iterator = flightlist;
		if(iterator != null && iterator.next!=null) {//make sure theyre not null
			while(iterator.next.getF_num()!=null){//loop while not found and not end of list
				iterator=iterator.next;
				if(iterator.getF_num().equals(flight_number)) {
					//flight found
					return iterator;
				}
				
			}
		}
		//flight not found
		return null;
	}
	
	public static int calcPassengers(Flight flights, Flight your_flight) {
		int totalPassengers = 0;
		Flight iterator = flights.next;
		Random rand = new Random();
		while(iterator != null){
			if(your_flight.getDepart().equals(iterator.getDepart())) {//checks depart time
				totalPassengers += rand.nextInt(Integer.parseInt(iterator.getAircraft()[1]) - 1) + 1; //add random amount of passengers
			}
			iterator = iterator.next;
		}
		return totalPassengers;
	}
	
	public static void main(String[] args) throws IOException {
		Flight flights;
		String travelTime;
		String flight_number = "0352";	
		int time_to_leave;
		
		flights = sendGET();  //creates linked list of all flights
		Flight your_flight = findFlight(flights,flight_number); //finds the flight youre on
		
		//System.out.println("GET DONE");
		//origin = 33.2305,-97.1467
		//dest = 32.9222,-97.0409
		//https://maps.googleapis.com/maps/api/distancematrix/json?origins=33.2305,-97.1467&destination=32.9222,-97.0409&key=AIzaSyDA9VXX0pYrU3czm5XmictKcLJU5jGzXSw
		
		//finds the traveltime from you to airport in seconds
		travelTime = getTravelDistance("33.230593,-97.146718",your_flight.getOrigin()[2]+","+your_flight.getOrigin()[3],true,true,true); //gets the travel time in seconds
		
		//finds departure time of the flight number
		//converts it to seconds
		if(your_flight != null) {
			String leave_time = your_flight.getDepart();
			String split_leave_time[] = leave_time.split("[T:\\.-]");
			//  (hours*3600+min*60+sec) - (travelTime) - (30*60)
			time_to_leave =((Integer.parseInt(split_leave_time[3])*3600	//hours
					+ Integer.parseInt(split_leave_time[4])*60		//min
					+ Integer.parseInt(split_leave_time[5]))		//sec
					- Integer.parseInt(travelTime)					//traveltime
					- (30*60));										//30min buffer
			//   vvv CONVERSION vvvv
			//hour = min/60, min = totalsec/60, sec = totalsec%60
			leave_time = (time_to_leave/3600)+":"+((time_to_leave%3600)/60)+":"+(time_to_leave%60);
			
			//calculate lobby wait time
			final int NUMBER_KIOSKS = 5;
			final int NUMBER_TSA_LINES = 5;
			final int NUMBER_CHECK_IN_DESKS = 5;
			
			int totalPass = calcPassengers(flights, your_flight);
			int n = (int) (totalPass * .47);
			int c = (int) (totalPass * .13);
			int b = (int) (totalPass * .3);
			int nTime = (n + c + b) * 5 / NUMBER_TSA_LINES;
			int cTime = ((c) * 4 / NUMBER_KIOSKS) + ((n + c + b) * 5 / NUMBER_TSA_LINES);
			int bTime = ((b) * 5 / NUMBER_CHECK_IN_DESKS) + ((n + c + b) * 5 / NUMBER_TSA_LINES);
			System.out.println("nTime = " + nTime);
			System.out.println("cTime = " + cTime);
			System.out.println("bTime = " + bTime);
		}
		else {
			System.out.println("Flight not found");
		}
		
		//System.out.println(travelTime);
	}

	


}