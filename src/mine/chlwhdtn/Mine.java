package mine.chlwhdtn;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
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
import util.chlwhdtn.CUtil;

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
		StatFileManager.saveConfig();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent event) {
		if (MineManager.isMinedBlock(event.getBlock().getLocation())) {
			MinePlayerStat chance = StatManager.getStat(event.getPlayer().getName());
			CUtil.addScore(event.getPlayer(), "mining", "채굴", 1);
			float random = new Random().nextFloat();
			Material nextBlock;
			
			if(random < chance.DIAMOND_CHANCE) {
				System.out.println(random  + " " + chance.DIAMOND_CHANCE);
				nextBlock = Material.DIAMOND_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.LAPIS_CHANCE) {
				nextBlock = Material.LAPIS_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.GOLD_CHANCE) {
				nextBlock = Material.GOLD_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.IRON_CHANCE) {
				nextBlock = Material.IRON_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.REDSTONE_CHANCE) {
				nextBlock = Material.REDSTONE_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.COAL_CHANCE) {
				nextBlock = Material.COAL_ORE;
				random = new Random().nextFloat();
			} else {
				nextBlock = Material.STONE;
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
