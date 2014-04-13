package buildcraft.customevents;

import org.bukkit.block.Block;

public class BC4QuarryMineBlockEvent extends BC4CancellableEvent {
    
	private Block quarry;
	private String quarryowner;
	private Block mined;
	public BC4QuarryMineBlockEvent(Block quarry, Block mined, String quarryowner) {
		this.quarry = quarry;
		this.mined = mined;
		this.quarryowner = quarryowner;
	}
	
	public String getQuarryOwnerName() {
		return quarryowner;
	}
	
	public Block getQuarry() {
		return quarry;
	}
	
	public Block getBlock() {
		return mined;
	}

}
