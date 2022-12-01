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


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;
//import games.stendhal.server.actions.move.PushAction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Corpse;
//import games.stendhal.server.entity.item.Corpse;
//import games.stendhal.server.entity.mapstuff.block.Block;
//import games.stendhal.server.entity.mapstuff.chest.Chest;
//import games.stendhal.server.entity.PassiveEntity;
//import games.stendhal.server.entity.RPEntity;
//import games.stendhal.server.entity.item.Corpse;
//import games.stendhal.server.entity.mapstuff.cart.Cart;
//import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import marauroa.common.game.SlotIsFullException;
//import marauroa.common.game.SlotIsFullException;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;

public class HandCartTest{
	

	@BeforeClass
	public static void beforeClass() {
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
	    MockStendlRPWorld.get();
	}
	@Before
	public void setUp() throws Exception {
		if (!RPClass.hasRPClass("entity")) {
			Entity.generateRPClass();
		}

		if (!RPClass.hasRPClass("hand cart")) {
			HandCart.generateRPClass();
		}
	}

	
	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Tests for size.
	 */
	@Test(expected = SlotIsFullException.class)
	public final void testSize() {
		final HandCart ch = new HandCart(true);
		assertEquals(0, ch.size());
		for (int i = 0; i < 30; i++) {
			ch.add(new PassiveEntity() {
			});
		}
		assertEquals(30, ch.size());
		ch.add(new PassiveEntity() {
		});
	}

	/**
	 * Tests for open.
	 */
	@Test
	public final void testOpen() {
		final HandCart ch = new HandCart(true);
		assertFalse(ch.isOpen());
		ch.open();

		assertTrue(ch.isOpen());
		ch.close();
		assertFalse(ch.isOpen());
	}

	/**
	 * Tests for onUsed.
	 */
	@Test
	public final void testOnUsed() {
		final HandCart ch = new HandCart(true);
		assertFalse(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});

		assertTrue(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});
		assertFalse(ch.isOpen());
	}

	
	@Test
	public void testPush() {
		HandCart b = new HandCart(true);
		b.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");
		z.add(b);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.LEFT);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));

		b.push(p, Direction.DOWN);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(1)));

		b.push(p, Direction.UP);
		assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(0)));
	}
	
}