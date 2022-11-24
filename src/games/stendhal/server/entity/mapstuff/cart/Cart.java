package games.stendhal.server.entity.mapstuff.cart;

import java.util.Iterator;
import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.slot.ChestSlot;
import marauroa.common.game.Definition.Type;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

public class Cart extends Entity{
	private static final String CART_RPCLASS_NAME = "cart";
	
	public Cart() {
		setRPClass(CART_RPCLASS_NAME);
		put("type", CART_RPCLASS_NAME);
	}
	
	public static void generateRPClass() {
//		if (!RPClass.hasRPClass(CHEST_RPCLASS_NAME)) {
//			final RPClass chest = new RPClass(CHEST_RPCLASS_NAME);
//			chest.isA("entity");
//			chest.addAttribute("open", Type.FLAG);
//			chest.addRPSlot("content", 30);
//		}
	}
	
}