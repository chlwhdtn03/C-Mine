package mine.chlwhdtn;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import economy.chlwhdtn.Economy;

public class Mine extends JavaPlugin implements Listener, CommandExecutor {
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("C-Economy") == null) {
			System.out.println("필수 플러그인이 존재하지 않습니다.");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			Economy.online(this);
		}

		MineFileManager.reloadConfig();

		Bukkit.getPluginManager().registerEvents(this, this);

		getCommand("광산").setExecutor(this);
	}

	@Override
	public void onDisable() {
		MineFileManager.saveConfig();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent event) {
		if (MineManager.isMinedBlock(event.getBlock().getLocation())) {
			int random = new Random().nextInt(1000); // 0 ~ 999 : 1000가지 경우
			Material nextBlock;
			switch (random) {

			case 0: // 0.1% 확률
				nextBlock = Material.DIAMOND_ORE;
				break;
			default:
				random = new Random().nextInt(100); // 0 ~ 99 : 100가지 경우
				switch (random) {
				case 0: // 1% 확률
					nextBlock = Material.GOLD_ORE;
					break;
				case 1: // 1% 확률
					nextBlock = Material.LAPIS_ORE;
					break;
				default:
					random = new Random().nextInt(20); // 0 ~ 19 : 20가지 경우
					switch (random) {
					case 0: // 5% 확률
						nextBlock = Material.IRON_ORE;
						break;
					case 1: case 2: // 10% 확률
						nextBlock = Material.COAL_ORE;
						break;
					case 3: // 5% 확률
						nextBlock = Material.REDSTONE_ORE;
						break;
					default: // 85% 확률
						nextBlock = Material.STONE;
						break;
					}
					break;
				}
				break;

			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(Economy.getInstance(), new Runnable() {

				@Override
				public void run() {
					event.getBlock().setType(nextBlock);
				}
			}, 5L);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.isOp() == false)
			return false;
		if (args.length == 3) {
			// 광산 x y z
			MineManager.addMine(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			return true;
		} else {
			sender.sendMessage("§c/광산 <x> <y> <z> - 해당 좌표값에 블럭이 재생성되게 합니다.");
			return true;
		}
	}

}
