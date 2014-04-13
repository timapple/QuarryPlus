package buildcraft.customevents;

import org.bukkit.block.Block;

public class BC4MiningWellMineBlockEvent extends BC4CancellableEvent {
	
	private Block miningwell;
	private String miningwellowner;
	private Block block;
	public BC4MiningWellMineBlockEvent(Block miningwell, Block block, String miningwellowner) {
		this.miningwell = miningwell;
		this.block = block;
		this.miningwellowner = miningwellowner;
	}
	
	public Block getMiningWell() {
		return miningwell;
	}
	
	public String getMiningWellOwnerName() {
		return miningwellowner;
	}
	
	public Block getBlock() {
		return block;
	}

}
