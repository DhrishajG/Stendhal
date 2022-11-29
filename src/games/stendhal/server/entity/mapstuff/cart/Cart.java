package games.stendhal.server.entity.mapstuff.cart;

import java.util.Iterator;

import games.stendhal.common.grammar.Grammar;
//import java.util.Iterator;
//import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.entity.PassiveEntity;
//import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.slot.ChestSlot;
//import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.entity.slot.ChestSlot;
//import marauroa.common.game.Definition.Type;
//import marauroa.common.game.RPClass;
//import marauroa.common.game.RPObject;
//import marauroa.common.game.RPSlot;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;
import marauroa.common.game.Definition.Type;

public class Cart extends Block implements UseListener{
	private static final String CART_RPCLASS_NAME = "block";
	private static final String CHEST_RPCLASS_NAME = "chest";
	private static final String Z_ORDER = "z";

	
	private Chest cartChest;
	
	private boolean open;

	public Cart() {
		super(true,"block"); 
		cartChest = new Chest();
		setRPClass(CART_RPCLASS_NAME);
		put("type", CART_RPCLASS_NAME);
		setRPClass(CHEST_RPCLASS_NAME);
		put("type", CHEST_RPCLASS_NAME);
		open = false;

		final RPSlot slot = new ChestSlot(cartChest);
		addSlot(slot);
	}
	
	public Cart(boolean multiPush) {
		super(multiPush);
		cartChest = new Chest();
		setRPClass(CART_RPCLASS_NAME);
		put("type", CART_RPCLASS_NAME);
	}
	
	public static void generateRPClass() {
		RPClass clazz = new RPClass("block");
		clazz.isA("area");
		// z order to control client side drawing
		clazz.addAttribute(Z_ORDER, Type.INT);
		clazz.addAttribute("class", Type.STRING);
		clazz.addAttribute("shape", Type.STRING);
		
		clazz.isA("entity");
		clazz.addAttribute("open",Type.FLAG);
		clazz.addRPSlot("content",30);
	}
	
	//UseListener
	//@Override
//	public boolean onUsed(final RPEntity user) {
//		return cartChest.onUsed(user);
//	}
//	
//	public boolean isOpen() {
//		return cartChest.isOpen();
//	}
//	
//	public void open() {
//		cartChest.open();
//	}
//
//	public void close() {
//		cartChest.close();
//	}
//	
	@Override
    public String getDescriptionName(final boolean definite) {
	    return Grammar.article_noun(CHEST_RPCLASS_NAME, definite);
    }

	@Override
	public void update() {
		super.update();
		open = false;
		if (has("open")) {
			open = true;
		}
	}

	/**
	 * Open the chest.
	 */
	public void open() {
		this.open = true;
		put("open", "");
	}

	/**
	 * Close the chest.
	 */
	public void close() {
		this.open = false;

		if (has("open")) {
			remove("open");
		}
	}

	/**
	 * Determine if the chest is open.
	 *
	 * @return <code>true</code> if the chest is open.
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * Adds a passive entity (like an item) to the chest.
	 *
	 * @param entity
	 *            entity to add
	 */
	public void add(final PassiveEntity entity) {
		final RPSlot content = getSlot("content");
		content.add(entity);
	}

	@Override
	public int size() {
		return getSlot("content").size();
	}

	/**
	 * Returns the content.
	 *
	 * @return iterator for the content
	 */
	public Iterator<RPObject> getContent() {
		final RPSlot content = getSlot("content");
		return content.iterator();
	}

	//
	// UseListener
	//

	@Override
	public boolean onUsed(final RPEntity user) {
		if (user.nextTo(this)) {
			if (isOpen()) {
				close();
			} else {
				open();
			}

			notifyWorldAboutChanges();
			return true;
		}
		if (user instanceof Player) {
			final Player player = (Player) user;
			player.sendPrivateText("You cannot reach the chest from there.");
		}
		return false;
	}

	//
	// Entity
	//

	@Override
	public String describe() {
		String text = "You see a chest.";

		if (hasDescription()) {
			text = getDescription();
		}

		if (isOpen()) {
			text += " It is open.";
			text += " You can right click and inspect this item to see its contents.";
		} else {
			text += " It is closed.";
		}

		return (text);
	}
	
}