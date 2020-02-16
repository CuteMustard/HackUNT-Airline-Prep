
public class Flight {
	private String f_num;
	private String origin[];
	private String dest[];
	private String distance;
	private String duration[];
	private String depart;
	private String arrive;
	private String aircraft[];
	private String cost;
	Flight next;
	
	Flight(){
		f_num="";
		origin = new String[5];
		dest = new String[5];
		distance="";
		duration=new String[3];
		depart="";
		arrive="";
		aircraft = new String[5];
		cost ="";
		//next = null;
	}

	public String getF_num() {
		return f_num;
	}

	public void setF_num(String f_num) {
		this.f_num = f_num;
	}

	public String[] getOrigin() {
		return origin;
	}

	public void setOrigin(String[] origin) {
		this.origin = origin;
	}

	public String[] getDest() {
		return dest;
	}

	public void setDest(String[] dest) {
		this.dest = dest;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String[] getDuration() {
		return duration;
	}

	public void setDuration(String[] duration) {
		this.duration = duration;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public String[] getAircraft() {
		return aircraft;
	}

	public void setAircraft(String[] aircraft) {
		this.aircraft = aircraft;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
	

}
