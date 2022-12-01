package games.stendhal.server.entity.mapstuff.cart;

import java.awt.geom.Rectangle2D;
import java.util.List;

//import org.apache.log4j.Logger;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
//import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.ZoneEnterExitListener;
import games.stendhal.server.entity.ActiveEntity;
//import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.mapstuff.block.BlockTarget;
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
		this.multi = Boolean.valueOf(multiPush);
		setResistance(100);
	}
	
	public void reset() {
		wasMoved = false;
		List<BlockTarget> blockTargetsAt = this.getZone().getEntitiesAt(getX(), getY(), BlockTarget.class);
		for (BlockTarget blockTarget : blockTargetsAt) {
			blockTarget.untrigger();
		}
		this.setPosition(startX, startY);
		SingletonRepository.getTurnNotifier().dontNotify(this);
		this.notifyWorldAboutChanges();
	}
	
	//executes whenever player attempts to push the cart
	public void push(Player p, Direction d) { 
		if (!this.mayBePushed(d)) {
			return;
		}
		// before push
		List<BlockTarget> blockTargetsAt = this.getZone().getEntitiesAt(getX(), getY(), BlockTarget.class);
		for (BlockTarget blockTarget : blockTargetsAt) {
			blockTarget.untrigger();
		}

		// after push
		int x = getXAfterPush(d);
		int y = getYAfterPush(d);
		this.setPosition(x, y);
		blockTargetsAt = this.getZone().getEntitiesAt(x, y, BlockTarget.class);
//		for (BlockTarget blockTarget : blockTargetsAt) {
//			if (blockTarget.doesTrigger(this, p)) {
//				blockTarget.trigger(this, p);
//			}
//		}
		wasMoved = true;
		this.notifyWorldAboutChanges();
	}
	
	public int getYAfterPush(Direction d) {
		return this.getY() + d.getdy();
	}

	public int getXAfterPush(Direction d) {
		return this.getX() + d.getdx();
	}

	private boolean wasPushed() {
		boolean xChanged = this.getInt("x") != this.startX;
		boolean yChanged = this.getInt("y") != this.startY;
		return xChanged || yChanged;
	}

	private boolean mayBePushed(Direction d) {
		boolean pushed = wasPushed();
		int newX = this.getXAfterPush(d);
		int newY = this.getYAfterPush(d);

		if (!multi && pushed) {
			return false;
		}

		// additional checks: new position must be free
		boolean collision = this.getZone().collides(this, newX, newY);

		return !collision;
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
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
		}
	}
	
	@Override
	public void onAdded(StendhalRPZone zone) {
		super.onAdded(zone);
		this.startX = getX();
		this.startY = getY();
		zone.addMovementListener(this);
		zone.addZoneEnterExitListener(this);
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
		if (!resetBlock || !wasMoved) {
			return;
		}

		// reset to initial position if zone gets empty of players
		final List<Player> playersInZone = zone.getPlayers();
		int numberOfPlayersInZone = playersInZone.size();
		if (numberOfPlayersInZone == 0 || numberOfPlayersInZone == 1 && playersInZone.contains(object)) {
			resetIfInitialPositionFree();
		}
	}
	
	private void resetIfInitialPositionFree() {
		if (!this.getZone().collides(this, this.startX, this.startY)) {
			this.reset();
		} else {
			// try again in a few moments
			SingletonRepository.getTurnNotifier().notifyInSeconds(RESET_AGAIN_DELAY, this);
		}
	}
}