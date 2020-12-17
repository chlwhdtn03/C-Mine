package mine.chlwhdtn;

import java.util.HashMap;


public class StatManager {

	private static HashMap<String, MinePlayerStat> hashmap = new HashMap<String, MinePlayerStat>();
	
	public static void setStat(String player, MinePlayerStat mps) {
		hashmap.put(player, mps);
	}
	
	public static MinePlayerStat getStat(String player) {
		if(!hashmap.containsKey(player))
			return new MinePlayerStat();
		return hashmap.get(player);
	}
	
	public static HashMap<String, MinePlayerStat> getMaps() {
		return hashmap;
	}
}
