/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.cart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.actions.move.PushAction;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPClass;
import marauroa.common.game.SlotIsFullException;
import utilities.PlayerTestHelper;

public class CartTest{
	@Before
	public void setUp() throws Exception {
		if (!RPClass.hasRPClass("entity")) {
			Entity.generateRPClass();
		}

		if (!RPClass.hasRPClass("cart")) {
			Cart.generateRPClass();
		}
	}

	
	@After
	public void tearDown() throws Exception {
	}
	//Player should be able to push cart
	@Test
	public final void pushable() {
		Cart cart = new Cart();
		Player player = PlayerTestHelper.createPlayer("bob");
		AssertEquals(player.canPush(cart),true);
	}
	
	@Test
	public final void testOpen() {
		final Cart cart = new Cart();
		assertFalse(cart.isOpen());
		cart.open();

		assertTrue(cart.isOpen());
		cart.close();
	}
}
