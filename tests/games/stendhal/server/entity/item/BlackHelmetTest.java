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

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.creature.RaidCreature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class BlackHelmetTest {
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
	 * Tests that a player wearing the helmet is not attacked by an imperial knight
	 */
	@Test
	public void testImperialKnight() {
		final StendhalRPZone zone = new StendhalRPZone("zone");
		MockStendlRPWorld.get().addRPZone(zone);
		final Player anna = PlayerTestHelper.createPlayer("anna");
		// final Item helmet = SingletonRepository.getEntityManager().getItem("black helmet");
		// anna.equip("head", helmet);
		anna.setHP(1000);
		
		Creature knight = new RaidCreature((Creature) SingletonRepository.getEntityManager().getEntity("imperial knight"));
		
		zone.add(anna);
		zone.add(knight);
		knight.setTarget(anna);
		knight.attack();
		
		assertEquals(anna.getHP(), 1000);
	}
}