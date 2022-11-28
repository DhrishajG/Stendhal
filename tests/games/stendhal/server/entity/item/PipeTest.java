package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class PipeTest{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}
	
	//tests that pipe is created and throws error if not
	@Test
	public void testPipe()
	 throws Exception {
		String name = "pipe";
		String clazz = "";
		String subclass = "";
		Map<String, String> attributes = new HashMap<String, String>();
		new Pipe(name, clazz, subclass, attributes);

	}
	
	//tests that pipe is held
	@Test
	public void testPipeHeld() throws Exception {
		final Player user = PlayerTestHelper.createPlayer("bob");
		
		String name = "pipe";
		String clazz = "";
		String subclass = "";
		Map<String, String> attributes = new HashMap<String, String>();
		Pipe newPipe = new Pipe(name, clazz, subclass, attributes);
		
		//checks that if pipe is not held in either hand returns this message
		user.equip("bag", newPipe);
		newPipe.onUsed(user);
		assertEquals("You should hold the pipe in either hand in order to use it.", PlayerTestHelper.getPrivateReply(user));
		
		//checks that if pipe is held in right hand returns this message
		user.equip("rhand", newPipe);
		newPipe.onUsed(user);
		assertEquals("Your holding pipe in hand.", PlayerTestHelper.getPrivateReply(user));
		
		//checks that if pipe is held in left hand returns this message
		user.drop(newPipe);
		user.equip("lhand", newPipe);
		newPipe.onUsed(user);
		assertEquals("Your holding pipe in hand.", PlayerTestHelper.getPrivateReply(user));
	}
	
	//tests the player state when holding pipe
	@Test
	public void testPipeState() throws Exception {
		final Player user = PlayerTestHelper.createPlayer("bob");
			
		String name = "pipe";
		String clazz = "";
		String subclass = "";
		Map<String, String> attributes = new HashMap<String, String>();
		Pipe newPipe = new Pipe(name, clazz, subclass, attributes);
			
		//checks that if pipe is not held state is false
		user.equip("bag", newPipe);
		newPipe.onUsed(user);
		assertFalse(newPipe.onUsed(user));
			
		//checks that if pipe is held state is true
		user.equip("rhand", newPipe);
		newPipe.onUsed(user);
		assertTrue(newPipe.onUsed(user));
	}
	
	//code stub for tests
	private static class Pipe extends Item {

		public Pipe(final String name, final String clazz,
	            final String subclass, final Map<String, String> attributes) {
	        super(name, clazz, subclass, attributes);
	    }
		
		// determines whether pipe can be used and sets the state
		@Override
		public boolean onUsed(final RPEntity user) {
			
			return false;
	    	
		}

	}
	
}