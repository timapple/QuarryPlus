package org.yogpstop.qp;

import net.minecraft.tileentity.TileEntity;
import buildcraft.customevents.BC4CancellableEvent;
import buildcraft.customevents.BC4MiningWellMineBlockEvent;
import buildcraft.customevents.BC4QuarryBuildFrameEvent;
import buildcraft.customevents.BC4QuarryMineBlockEvent;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
	
	@Override
	public boolean eventQuarryMakeFrame( TileEntity e, int x, int y, int z ) {
		//prepare variables
		org.bukkit.World 		world = org.bukkit.Bukkit.getWorld(e.worldObj.getWorldInfo().getWorldName());
		org.bukkit.block.Block 	block = world.getBlockAt(x, y, z);
		org.bukkit.block.Block 	quarry = world.getBlockAt(e.xCoord, e.yCoord, e.zCoord);
		String ownername = "[BuildCraft]";
		//call event
		BC4CancellableEvent event = new BC4QuarryBuildFrameEvent(quarry, block, ownername);
		org.bukkit.Bukkit.getPluginManager().callEvent(event);
		//process blocks only if event was null or not cancelled
		return (event != null && event.isCancelled());
	}
	
	@Override
	public boolean eventQuarryBreakBlock( TileEntity e, int x, int y, int z ) {
		//prepare variables
		org.bukkit.World 		world = org.bukkit.Bukkit.getWorld(e.worldObj.getWorldInfo().getWorldName());
		org.bukkit.block.Block 	block = world.getBlockAt(x, y, z);
		org.bukkit.block.Block 	quarry = world.getBlockAt(e.xCoord, e.yCoord, e.zCoord);
		String ownername = "[BuildCraft]";
		//call event
		BC4CancellableEvent event = new BC4QuarryMineBlockEvent(quarry, block, ownername);
		org.bukkit.Bukkit.getPluginManager().callEvent(event);
		//process blocks only if event was null or not cancelled
		return (event != null && event.isCancelled());
	}
	
	@Override
	public boolean eventMiningWellBreakBlock( TileEntity e, int x, int y, int z ) {
		//prepare variables
		org.bukkit.World 		world = org.bukkit.Bukkit.getWorld(e.worldObj.getWorldInfo().getWorldName());
		org.bukkit.block.Block 	block = world.getBlockAt(x, y, z);
		org.bukkit.block.Block 	mWell = world.getBlockAt(e.xCoord, e.yCoord, e.zCoord);
		String ownername = "[BuildCraft]";
		//call event
		BC4CancellableEvent event = new BC4MiningWellMineBlockEvent(mWell, block, ownername);
		org.bukkit.Bukkit.getPluginManager().callEvent(event);
		//process blocks only if event was null or not cancelled
		return (event != null && event.isCancelled());
	}
}
