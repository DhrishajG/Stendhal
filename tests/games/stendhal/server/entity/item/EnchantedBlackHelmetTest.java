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
package games.stendhal.server.entity.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class EnchantedBlackHelmetTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();

		MockStendlRPWorld.get().addRPZone(new StendhalRPZone("int_kalavan_castle", 10, 10));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PlayerTestHelper.removeAllPlayers();
	}
	
	/**
	 * Tests that a player is not invisible after taking the helmet off
	 */
	@Test
	public void testOnUnequip() {
		final StendhalRPZone zone = new StendhalRPZone("zone");
		MockStendlRPWorld.get().addRPZone(zone);
		final Player anna = PlayerTestHelper.createPlayer("anna");
		final Item helmet = new EnchantedBlackHelmet();
		anna.equip("head", helmet);
		helmet.onUnequipped();
		zone.add(anna);
		assertFalse(anna.isInvisibleToCreatures());
	}
	
	/**
	 * Tests that a player is invisible after putting the helmet on
	 */
	@Test
	public void testOnEquip() {
		final StendhalRPZone zone = new StendhalRPZone("zone");
		MockStendlRPWorld.get().addRPZone(zone);
		final Player anna = PlayerTestHelper.createPlayer("anna");
		final Item helmet = new EnchantedBlackHelmet();
		anna.equip("head", helmet);		
		zone.add(anna);
		assertTrue(anna.isInvisibleToCreatures());
	}
}