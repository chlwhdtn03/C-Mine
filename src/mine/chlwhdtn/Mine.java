package mine.chlwhdtn;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import economy.chlwhdtn.Economy;
import net.md_5.bungee.api.ChatColor;
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
		StatFileManager.reloadConfig();

		Bukkit.getPluginManager().registerEvents(this, this);

		getCommand("광산").setExecutor(this);
	}

	@Override
	public void onDisable() {
		MineFileManager.saveConfig();
		StatFileManager.saveConfig();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(StatManager.getMaps().containsKey(event.getPlayer().getName()) == false) {
			StatManager.setStat(event.getPlayer().getName(), new MinePlayerStat());
			StatFileManager.saveConfig();
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent event) {
		if (MineManager.isMinedBlock(event.getBlock().getLocation())) {
			MinePlayerStat chance = StatManager.getStat(event.getPlayer().getName());
			CUtil.addScore(event.getPlayer(), "mining", "채굴", 1);
			
			if(CUtil.getScore(event.getPlayer(), "mining") % 250 == 0) { // 250회 채굴할때마다 랜덤 강화 
			
				int rand =  new Random().nextInt(52); // 0~51
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				if(rand < 2) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§b다이아몬드 " 
							+ chance.DIAMOND_CHANCE*100 + "% > §2§l" + (chance.DIAMOND_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 5) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§b청금석 " 
							+ chance.LAPIS_CHANCE*100 + "% > §2§l" + (chance.LAPIS_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 10) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§c레드스톤 " 
							+ chance.REDSTONE_CHANCE*100 + "% > §2§l" + (chance.REDSTONE_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 15) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§6금 " 
							+ chance.GOLD_CHANCE*100 + "% > §2§l" + (chance.GOLD_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 30) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§f철 " 
							+ chance.IRON_CHANCE*100 + "% > §2§l" + (chance.IRON_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 50) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§7석탄 " 
							+ chance.COAL_CHANCE*100 + "% > §2§l" + (chance.COAL_CHANCE += 0.001F)*100+"%", 10,70,20);
				} else if(rand < 52) {
					event.getPlayer().sendTitle("§a채굴 향상!", "§7고대 잔해 " 
							+ chance.ANCIENT_CHANCE*100 + "% > §2§l" + (chance.ANCIENT_CHANCE += 0.001F)*100+"%", 10,70,20);
				}
				StatFileManager.saveConfig();
				
			}
			float random = new Random().nextFloat();
			Material nextBlock;
			
			if(random < chance.DIAMOND_CHANCE) {
				nextBlock = Material.DIAMOND_ORE;
				random = new Random().nextFloat();
			} else if(random < chance.ANCIENT_CHANCE) {
				nextBlock = Material.ANCIENT_DEBRIS;
				random = new Random().nextFloat();
			} else if(random < chance.LAPIS_CHANCE) {
				nextBlock = Material.LAPIS_ORE;
				random = new Random().nextFloat();
			}  else if(random < chance.GOLD_CHANCE) {
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
		if (args.length == 0) {
			MinePlayerStat stat = StatManager.getStat(sender.getName());
			sender.sendMessage(ChatColor.GREEN + "== [ 채굴 능력 ] ==");
			sender.sendMessage(ChatColor.AQUA + "다이아몬드 " + stat.DIAMOND_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.DARK_PURPLE + "고대 잔해 " + stat.ANCIENT_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.AQUA + "청금석 " + stat.LAPIS_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.RED + "레드스톤 " + stat.REDSTONE_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.GOLD + "금 " + stat.GOLD_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.WHITE + "철 " + stat.IRON_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.GRAY + "석탄 " + stat.COAL_CHANCE*100 + "%");
			return true;
		}
		
		if (args.length == 1) {
			if(StatManager.getMaps().containsKey(args[0]) == false) {
				sender.sendMessage(ChatColor.RED + "존재하지 않는 플레이어입니다.");
				return false;
			}
			MinePlayerStat stat = StatManager.getStat(args[0]);
			sender.sendMessage(ChatColor.GREEN + "== [ " + args[0] +"의 채굴 능력 ] ==");
			sender.sendMessage(ChatColor.AQUA + "다이아몬드 " + stat.DIAMOND_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.DARK_PURPLE + "고대 잔해 " + stat.ANCIENT_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.AQUA + "청금석 " + stat.LAPIS_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.RED + "레드스톤 " + stat.REDSTONE_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.GOLD + "금 " + stat.GOLD_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.WHITE + "철 " + stat.IRON_CHANCE*100 + "%");
			sender.sendMessage(ChatColor.GRAY + "석탄 " + stat.COAL_CHANCE*100 + "%");
			return true;
		}
		
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
