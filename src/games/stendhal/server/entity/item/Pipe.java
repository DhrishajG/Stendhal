package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;

public class Pipe extends Item {
	
	public Pipe(final String name, final String clazz,
            final String subclass, final Map<String, String> attributes) {
        super(name, clazz, subclass, attributes);
    }
	
	// determines whether pipe can be used and sets the state
	@Override
	public boolean onUsed(final RPEntity user) {
				
		final String slotName = getContainerSlot().getName();
				
		/* is it in a hand? */
		if (!slotName.endsWith("hand")) {
			user.sendPrivateText("You should hold the pipe in either hand in order to use it.");
		    return false;
		}
		else {
		    user.sendPrivateText("Your holding pipe in hand.");
		    return true;
		}		    	

		}
	

}
