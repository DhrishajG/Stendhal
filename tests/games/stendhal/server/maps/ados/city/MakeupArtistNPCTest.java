package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
//import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.maps.ados.city.MakeupArtistNPC;
//import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class MakeupArtistNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "testzone";
	
	private Player player;
	private SpeakerNPC makeupartistNpc;
	private Engine makeupartistEngine;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME, new MakeupArtistNPC());
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		makeupartistNpc = SingletonRepository.getNPCList().get("Fidorea");
		makeupartistEngine = makeupartistNpc.getEngine();
	}
	
	public MakeupArtistNPCTest() {
		super(ZONE_NAME, "Fidorea");
	}

	@Test
	public void testDialogue() {
		// Tests for helping and greeting comment
		startConversation();
		checkReply("help", "I sell masks. If you don't like your mask, you can #return and I will remove it, or you can just wait 5 hours, until it wears off.");
		checkReply("return", "I can't remember that I gave you anything.");
		endConversation();
		
		// Tests for offering comment
		startConversation();
		checkReply("offer", "You can #buy mask.");
		endConversation();
		
		// Tests for asking job comment
		startConversation();
		checkReply("job", "I am a makeup artist.");
		endConversation();
		
		// Tests for asking quest comment
		startConversation();
		checkReply("quest", "Are you looking for toys for Anna? She loves my costumes, perhaps she'd like a #dress to try on. If you already got her one, I guess she'll have to wait till I make more costumes!");
		checkReply("dress", "I read stories that goblins wear a dress as a kind of armor! If you're scared of goblins, like me, maybe you can buy a dress somewhere. ");
		endConversation();
		
		// Tests for returning mask comment
		startConversation();
		checkReply("return", "I can't remember that I gave you anything.");
		endConversation();
		
		// Tests for selecting mask and say no
		startConversation();
		checkReply("buy", "To buy a mask will cost 20. Do you want to buy it?");
		checkReply("no", "Ok, how else may I help you?");
		endConversation();
		
		// Tests for selecting mask and say yes with no money
		startConversation();
		checkReply("buy", "To buy a mask will cost 20. Do you want to buy it?");
		checkReply("yes", "You can choose brown mouse, green frog, black bird, brown monkey, brown white fox, brown blue eye. Type the name of mask.");
		checkReply("brown mouse", "Sorry, you don't have enough money!");
		endConversation();
		
		// Tests for selecting mask and say yes with money
		equipWithMoney(player, 20);
//		final StackableItem item = (StackableItem) SingletonRepository.getEntityManager().getItem("money");
//		item.setQuantity(20);
//		player.equipToInventoryOnly(item);
		startConversation();
		checkReply("buy", "To buy a mask will cost 20. Do you want to buy it?");
		checkReply("yes", "You can choose brown mouse, green frog, black bird, brown monkey, brown white fox, brown blue eye. Type the name of mask.");
		checkReply("brown mouse", "Thanks, and please don't forget to #return it when you don't need it anymore!");
		checkReply("return", "Thank you!");
		endConversation();
	}

	private void startConversation() {
		makeupartistEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(makeupartistNpc.isTalking());
		assertEquals("Hi, there. Do you need #help with anything?", getReply(makeupartistNpc));
	}
	
	private void endConversation() {
		makeupartistEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(makeupartistNpc.isTalking());
		assertEquals("Bye, come back soon.", getReply(makeupartistNpc));
	}
	
	private void checkReply(String question, String expectedReply) {
		makeupartistEngine.step(player, question);
		assertTrue(makeupartistNpc.isTalking());
		assertEquals(expectedReply, getReply(makeupartistNpc));
	}
}
