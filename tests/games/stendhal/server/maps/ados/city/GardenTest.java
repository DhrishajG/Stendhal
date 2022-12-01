package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Seed;
import games.stendhal.server.entity.mapstuff.area.FertileGround;
import games.stendhal.server.entity.mapstuff.area.Garden;
import games.stendhal.server.entity.mapstuff.spawner.FlowerGrower;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.GrowingPassiveEntityRespawnPointTestHelper;

	/**
	 * Test planting seed on garden.
	 * @author Kimi Goyal and Jungwoo Koo
	 */
	public class GardenTest{
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			MockStendlRPWorld.get();
			GrowingPassiveEntityRespawnPointTestHelper.generateRPClasses();
		}

	
		/**
		 * Tests that default garden state is tilled.
		 */
		@Test
		public void testDefaultGardenState() {
			Garden g = new Garden();
			assertEquals(g.getLandState(), "tilled");
		}
		
		/**
		 * Tests that default garden owner is Ross.
		 */
		@Test
		public void testDefaultGardenOwner() {
			Garden g = new Garden();
			assertEquals(g.getOwner(), "Ross");
		}
		
		/**
		 * Tests that garden can be planted.
		 */
		@Test
		public void testPlantedGardenState() {
			
			final Player player = PlayerTestHelper.createPlayer("bob");
			assertNotNull(player);
			
			final StendhalRPZone zone = new StendhalRPZone("zone");
			SingletonRepository.getRPWorld().addRPZone(zone);
			zone.add(player);
			
			
			// configure the zone as garden.
			Garden g = new Garden();
			final Map<String, String> attribs = new HashMap<String, String>();
			attribs.put("x", "1");
			attribs.put("y", "1");
			attribs.put("width", "3");
			attribs.put("height", "3");

			g.configureZone(zone, attribs);
			
			assertFalse(0 + ":" + 0,
					zone.getEntityAt(0, 0) instanceof FertileGround);
			
			// check garden is fertile ground
			for (int x = 1; x < 4; x++) {
				for (int y = 1; y < 4; y++) {
					assertTrue(x + ":" + y,
							zone.getEntityAt(x, y) instanceof FertileGround);
					
					// check that seed can grow on garden
					final Seed seed = (Seed) SingletonRepository.getEntityManager().getItem("seed");
					assertNotNull(seed);
					zone.add(seed);
					seed.setPosition(x,y);
					player.setPosition(x, y);
					assertTrue(seed.onUsed(player,g));
					
					final FlowerGrower fl = new FlowerGrower();
					zone.add(fl);
					fl.setPosition(x, y);
					fl.setToFullGrowth();
					fl.onUsed(player);
					assertTrue(player.isEquipped("lilia"));
				}
			}
			
			// check that land state is planted
			assertEquals(g.getLandState(), "planted");
		}
	

		

}
