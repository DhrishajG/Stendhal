/***************************************************************************
 *                   (C) Copyright 2003-2022 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.item;

import static games.stendhal.common.constants.Actions.INVISIBLE;

import java.util.Map;

import games.stendhal.server.core.engine.GameEvent;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;


/**
 * A ring that protects from XP loss.
 */
public class BlackHelmet extends Item {
	public BlackHelmet(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item copied item
	 */
	public BlackHelmet(final BlackHelmet item) {
		super(item);
	}

	/**
	 * Create a RingOfLife.
	 */
	public BlackHelmet() {
		super("black helmet", "helmet", "black-helmet", null);
		put("amount", 1);
	}

	/**
	 * If the player puts the helmet in the head slot, then they become invisible
	 * this method returns true if the player was not already invisible, and false otherwise
	 * This as per the onEqipped definition to return whether the method 'indicate whether it made any change'
	 */
	@Override
	public boolean onEquipped(final RPEntity entity, final String slot) {
		boolean already_invisible = false;
		if (slot.equals("head") && entity instanceof Player) {	
			if (((Player)entity).isInvisibleToCreatures()) {
				already_invisible = true;
			}
			((Player) entity).setInvisible(true);
			new GameEvent(((Player)entity).getName(), INVISIBLE, "on").raise();
		}
		return !already_invisible;
	}
}
