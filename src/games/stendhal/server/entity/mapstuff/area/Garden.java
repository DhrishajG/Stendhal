package games.stendhal.server.entity.mapstuff.area;
import games.stendhal.server.maps.semos.city.FertileGrounds;
public class Garden extends FertileGrounds{
	
	private String landState = "tilled";
	private String owner = "Ross";

	// return land state
	public String getLandState() {
		return landState;
	}
	
	// return owner
	public String getOwner() {
		return owner;
	}
	
	// set land state
	public void setState(String state) {
		landState = state;
	}

}
