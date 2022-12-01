package games.stendhal.server.entity.mapstuff.cart;

//import java.awt.geom.Rectangle2D;


//import org.apache.log4j.Logger;

import games.stendhal.common.Direction;

//import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.ZoneEnterExitListener;
import games.stendhal.server.entity.ActiveEntity;
//import games.stendhal.server.entity.mapstuff.block.Block;

import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;

/**
 * A hand cart is a movable container. It can be opened and closed. While it is
 * open, every player can put items in and take them out later. A player can
 * take out items that another player put in. It can be used by players to store
 * more items for their quest when their pockets get full. 
 * 
 * @author Dhrishaj Garg, Oliver Quinn
 */

public class HandCart extends Chest implements ZoneEnterExitListener,
MovementListener, TurnListener {
	
	private int startX;
	private int startY;
	private boolean multi;


	private boolean resetBlock = true;
	private boolean wasMoved = false;
	
	static final int RESET_AGAIN_DELAY = 10;
	
	public HandCart(boolean multiPush) {
		super();
	}
	
	public void reset() {
	}
	
	//executes whenever player attempts to push the cart
	public void push(Player p, Direction d) { 
	}
	
	public int getYAfterPush(Direction d) {
		return 0;
	}

	public int getXAfterPush(Direction d) {
		return 0;
	}

	private boolean wasPushed() {
		return false;
	}

	private boolean mayBePushed(Direction d) {
		return false;
	}

	@Override
	public void onTurnReached(int currentTurn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onAdded(StendhalRPZone zone) {

	}

	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntered(RPObject object, StendhalRPZone zone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExited(RPObject object, StendhalRPZone zone) {
		resetInPlayerlessZone(zone, object);
	}
	
	private void resetInPlayerlessZone(StendhalRPZone zone, RPObject object) {

	}
	
	private void resetIfInitialPositionFree() {

	}

	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}
}