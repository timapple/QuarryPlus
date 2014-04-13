package buildcraft.customevents;

import org.bukkit.block.Block;

public class BC4QuarryBuildFrameEvent extends BC4CancellableEvent {
	
	private Block quarry;
	private String quarryowner;
	private Block mined;
	public BC4QuarryBuildFrameEvent(Block quarry, Block mined, String quarryowner) {
		this.quarry = quarry;
		this.mined = mined;
		this.quarryowner = quarryowner;
	}
	
	public Block getQuarry() {
		return quarry;
	}
	
	public String getQuarryOwnerName() {
		return quarryowner;
	}
	
	public Block getBlock() {
		return mined;
	}

}
