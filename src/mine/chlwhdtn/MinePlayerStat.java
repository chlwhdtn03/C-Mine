package mine.chlwhdtn;

import org.bukkit.entity.Player;

public class MinePlayerStat {
	
	public float DIAMOND_CHANCE = 0.001F;
	public float GOLD_CHANCE = 0.01F;
	public float LAPIS_CHANCE = 0.01F;
	public float IRON_CHANCE = 0.05F;
	public float COAL_CHANCE = 0.1F;
	public float REDSTONE_CHANCE = 0.07F;
	public float ANCIENT_CHANCE = 0.001F;
	
	public MinePlayerStat() {
		
	}
	
	public MinePlayerStat(float dIAMOND_CHANCE, float gOLD_CHANCE, float lAPIS_CHANCE, float iRON_CHANCE,
			float cOAL_CHANCE, float rEDSTONE_CHANCE, float aNCIENT_CHANT) {
		DIAMOND_CHANCE = dIAMOND_CHANCE;
		GOLD_CHANCE = gOLD_CHANCE;
		LAPIS_CHANCE = lAPIS_CHANCE;
		IRON_CHANCE = iRON_CHANCE;
		COAL_CHANCE = cOAL_CHANCE;
		REDSTONE_CHANCE = rEDSTONE_CHANCE;
		ANCIENT_CHANCE = aNCIENT_CHANT;
	}
	
}
