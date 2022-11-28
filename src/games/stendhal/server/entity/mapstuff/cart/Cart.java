package games.stendhal.server.entity.mapstuff.cart;

//import java.util.Iterator;
//import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.events.UseListener;
//import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.mapstuff.chest.Chest;
//import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.entity.slot.ChestSlot;
//import marauroa.common.game.Definition.Type;
//import marauroa.common.game.RPClass;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPSlot;
import marauroa.common.game.RPClass;
import marauroa.common.game.Definition.Type;

public class Cart extends Block implements UseListener{
	private static final String CART_RPCLASS_NAME = "cart";
	private static final String Z_ORDER = "z";

	
	private Chest cartChest;
	
	public Cart() {
		super(true,"hay_cart"); 
		cartChest = new Chest();
		setRPClass(CART_RPCLASS_NAME);
		put("type", CART_RPCLASS_NAME);
	}
	
	public Cart(boolean multiPush) {
		super(multiPush);
	}
	
	public static void generateRPClass() {
		RPClass clazz = new RPClass("cart");
		clazz.isA("area");
		// z order to control client side drawing
		clazz.addAttribute(Z_ORDER, Type.INT);
		clazz.addAttribute("class", Type.STRING);
		clazz.addAttribute("shape", Type.STRING);
	}
	
	//UseListener
	@Override
	public boolean onUsed(final RPEntity user) {
		return cartChest.onUsed(user);
	}
	
	public boolean isOpen() {
		return cartChest.isOpen();
	}
	
	public void open() {
		cartChest.open();
	}

	public void close() {
		cartChest.close();
	}
	
}