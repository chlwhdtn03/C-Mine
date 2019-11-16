package mine.chlwhdtn;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import economy.chlwhdtn.Economy;

public class MineFileManager {
	private static FileConfiguration MineConfig = null;
	private static File MineConfigFile = null;

	public static void reloadConfig() {
		if (MineConfigFile == null) {
			MineConfigFile = new File(Economy.getInstance().getDataFolder(), "Mine.yml");
		}
		MineConfig = YamlConfiguration.loadConfiguration(MineConfigFile);
		if (!MineConfig.contains("Mine"))
			return;
		for (String name : MineConfig.getConfigurationSection("Mine").getKeys(false)) {
			MineManager.LoadLand(name, new MineData(MineConfig.getInt("Mine." + name + ".x"),
					MineConfig.getInt("Mine." + name + ".y"), MineConfig.getInt("Mine." + name + ".z")));
		}
	}

	public static FileConfiguration getConfig() {
		if (MineConfig == null) {
			reloadConfig();
		}
		return MineConfig;
	}

	public static void saveConfig() {
		if (MineConfig == null || MineConfigFile == null) {
			return;
		}
		try {
			for (String name : MineManager.getMineMap().keySet()) {
				MineConfig.set("Mine." + name + ".x", MineManager.getMineMap().get(name).x);
				MineConfig.set("Mine." + name + ".y", MineManager.getMineMap().get(name).y);
				MineConfig.set("Mine." + name + ".z", MineManager.getMineMap().get(name).z);
			}
			getConfig().save(MineConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
