package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
//import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.core.engine.StendhalRPZone;


public class CandleCheckAction implements ChatAction{

		public CandleCheckAction(StendhalRPZone zone) {
			Item candle = SingletonRepository.getEntityManager().getItem("candle");
			if (candle != null) {
				if (candle.getQuantity()>0) {
					//zone.remove(candle);
					//candle.removeOne();
					SingletonRepository.getEntityManager().getItem("candle").removeFromWorld();
				}
			}
		}

		
		@Override
		public void fire(Player player, Sentence sentence, EventRaiser npc) {
			// TODO Auto-generated method stub
			
		}
}
