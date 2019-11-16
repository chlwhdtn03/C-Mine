package mine.chlwhdtn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

public class MineManager {

	private static HashMap<String, MineData> hashmap = new HashMap<String, MineData>();
	public static MineData addMine(int x, int y, int z) {
		MineData data =  new MineData(x, y, z);
		hashmap.put("Mine-" + (hashmap.size()+1), data);
		return data;
	}
	public static MineData getLand(String string) {
		return hashmap.get(string);
	}
	public static void LoadLand(String key, MineData data) {
		hashmap.put(key, data);
	}
	public static boolean isMinedBlock(Location loc) {
		MineData target = new MineData(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		for(MineData data : hashmap.values()) {
			if(target.x != data.x)
				continue;
			if(target.y != data.y)
				continue;
			if(target.z != data.z)
				continue;
			return true;
		}
		return false;
	}

	public static HashMap<String, MineData> getMineMap() {
		return hashmap;
	}
}
