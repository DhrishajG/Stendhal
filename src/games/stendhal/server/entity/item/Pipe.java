package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

public class Pipe extends Item {
	
	public Pipe(final String name, final String clazz,
            final String subclass, final Map<String, String> attributes) {
        super(name, clazz, subclass, attributes);
    }
	public Pipe(final Pipe item) {
		super(item);
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
		    
		    Player player = (Player) user;
		    player.setCharming(true);
		    
		    return true;
		}		    	
	}
	
	@Override
	public boolean onUnequipped() {
		RPEntity unequipper = (RPEntity) this.getContainerOwner();
		
		if (unequipper instanceof Player) {
			Player player = (Player) unequipper;
			if (player.isCharming()) {
				unequipper.sendPrivateText("You stopped playing the pipe");
			}
			
			player.setCharming(false);
		}
		
		return true;
	}

}
