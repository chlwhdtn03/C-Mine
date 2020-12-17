package mine.chlwhdtn;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import economy.chlwhdtn.Economy;

public class StatFileManager {
	private static FileConfiguration Config = null;
	private static File ConfigFile = null;

	public static void reloadConfig() {
		if (ConfigFile == null) {
			ConfigFile = new File(Economy.getInstance().getDataFolder(), "MineChance.yml");
		}
		Config = YamlConfiguration.loadConfiguration(ConfigFile);
		if (!Config.contains("chance"))
			return;
		for (String name : Config.getConfigurationSection("chance").getKeys(false)) {
			StatManager.setStat(name, new MinePlayerStat(
					(float)Config.getDouble("chance."+name+".diamond"), 
					(float)Config.getDouble("chance."+name+".gold"), 
					(float)Config.getDouble("chance."+name+".lapis"), 
					(float)Config.getDouble("chance."+name+".iron"), 
					(float)Config.getDouble("chance."+name+".redstone"), 
					(float)Config.getDouble("chance."+name+".coal")
					));
		}
	}

	public static FileConfiguration getConfig() {
		if (Config == null) {
			reloadConfig();
		}
		return Config;
	}

	public static void saveConfig() {
		if (Config == null || ConfigFile == null) {
			return;
		}
		try {
			MinePlayerStat temp;
			for (String name : StatManager.getMaps().keySet()) {
				temp = StatManager.getStat(name);
				Config.set("chance." + name + ".diamond", temp.DIAMOND_CHANCE);
				Config.set("chance" + name + ".gold", temp.GOLD_CHANCE);
				Config.set("chance." + name + ".lapis", temp.LAPIS_CHANCE);
				Config.set("chance." + name + ".iron", temp.IRON_CHANCE);
				Config.set("chance." + name + ".redstone", temp.REDSTONE_CHANCE);
				Config.set("chance" + name + ".coal", temp.COAL_CHANCE);
			}
			getConfig().save(ConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}