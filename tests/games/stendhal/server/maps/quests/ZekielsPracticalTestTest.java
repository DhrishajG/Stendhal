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
package games.stendhal.server.maps.quests;
import static org.junit.Assert.assertEquals;
//import static utilities.SpeakerNPCTestHelper.getReply;

//import java.util.Arrays;

//import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;
//import games.stendhal.common.constants.Actions;
/*import games.stendhal.common.constants.Actions;
import games.stendhal.server.actions.UseAction;*/
import games.stendhal.server.core.engine.SingletonRepository; //NEED TO CHANGE SOME OF THESE TO CORRECT CONTEXT
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.item.Item;
/*import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;*/
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.EquipItemAction;
//import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.TeleportAction;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
/*import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;*/
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.semos.wizardstower.WizardsGuardStatueNPC;
import games.stendhal.server.maps.semos.wizardstower.WizardsGuardStatueSpireNPC;
//import marauroa.common.game.RPAction;
//import marauroa.common.game.RPAction;
/*import games.stendhal.server.maps.fado.tavern.MaidNPC;*/
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class ZekielsPracticalTestTest {

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine  en = null;
	private StendhalRPZone zone = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		ItemTestHelper.generateRPClasses();
	}

	@Before
	public void setUp() {
		zone = new StendhalRPZone("int_semos_wizards_tower_basement");
		MockStendlRPWorld.get().addRPZone(zone);
		//Zekiel the guardian
		new WizardsGuardStatueNPC().configureZone(zone,null);
		//Zekiel
		new WizardsGuardStatueSpireNPC().configureZone(zone,null);


		AbstractQuest quest = new ZekielsPracticalTestQuest();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("bob");
	}

	@Test
	public void testQuest() {

		npc = SingletonRepository.getNPCList().get("Zekiel the guardian");
		en = npc.getEngine();
		player.setLevel(30);
		
		new SetQuestAction("zekiels_practical_test", "candles_done");
		new TeleportAction("int_semos_wizards_tower_1", 15, 15, Direction.DOWN);
		
		/*
		 * en.step(player, "send"); //Should teleport to level 1
		 * assertEquals(player.isQuestInState("zekiels_practical_test",
		 * "first_state"),true); //Checks quest state
		 * 
		 * //Puts candle on floor then leaves Item candle =
		 * SingletonRepository.getEntityManager().getItem("candle"); zone.add(candle);
		 * RPAction action = new RPAction(); action.put(Actions.TARGET_PATH,
		 * Arrays.asList(Integer.toString(candle.getID().getObjectID())));
		 */// FROM UseActionTest should put candle on floor
		
		new EquipItemAction("candle",1);
		assertEquals("player has item <1 candle>",new PlayerHasItemWithHimCondition("candle").toString());
		
		new DropItemAction("candle",1);
		
		assertEquals("player has item <1 candle>",new PlayerHasItemWithHimCondition("candle").toString());
		
		//Teleport to basement
		/*
		 * new TeleportAction("int_semos_wizards_tower_basement", 15, 15,
		 * Direction.DOWN); //Re-enter through Zekiel en.step(player, "hello");
		 * assertEquals("Greetings! You have so far failed the practical test. Tell me, if you want me to #send you on it again right now, or if there is anything you want to #learn about it first."
		 * ,getReply(npc)); en.step(player, "send"); //Should teleport to level 1
		 * //Check no candle on floor candle =
		 * SingletonRepository.getEntityManager().getItem("candle"); assertEquals(0,
		 * candle.getQuantity());
		 */

		//Win
		
		
	}
}
