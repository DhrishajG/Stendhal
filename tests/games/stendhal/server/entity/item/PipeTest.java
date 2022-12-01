package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class PipeTest{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		MockStendlRPWorld.get();
	}
	
	private Player user;
	private Pipe newPipe;
	
	@Before
	public void setUpTests() {
		user = PlayerTestHelper.createPlayer("bob");
		
		String name = "pipe";
		String clazz = "";
		String subclass = "";
		Map<String, String> attributes = new HashMap<String, String>();
		newPipe = new Pipe(name, clazz, subclass, attributes);
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
			
		//checks that if pipe is not held state is false
		user.equip("bag", newPipe);
		newPipe.onUsed(user);
		assertFalse(newPipe.onUsed(user));
			
		//checks that if pipe is held state is true
		user.equip("rhand", newPipe);
		newPipe.onUsed(user);
		assertTrue(newPipe.onUsed(user));
	}
	
	
	
}