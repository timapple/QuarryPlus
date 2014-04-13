package org.yogpstop.qp;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.command.CommandBase;
//import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

public class SampleCommand extends CommandBase {

	private List<String> aliases;

	public SampleCommand()
	{
		this.aliases = new ArrayList<String>();
		//this.aliases.add("quarryplus");
		this.aliases.add("qps");
	}

	@Override
	public String getCommandName()
	{
		return "quarryplus";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "qps [min-count]";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		int minCount = 1;
		if (astring.length == 0 || astring.length > 2)
		{
			ChatMessageComponent cmc = ChatMessageComponent.createFromText("Invalid arguments");
			icommandsender.sendChatToPlayer(cmc);
			return;
		}
		
		if (astring.length >= 2)
			minCount = parseIntWithMin(icommandsender, astring[1], 1);
		
		//HashMap<String, Integer> map = com.google.common.collect.Maps.newHashMap();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();  //if (map==null) return;
		
		World w = icommandsender.getEntityWorld(); if (w==null) return;
		@SuppressWarnings("unchecked")
		List<TileEntity> tes = w.loadedTileEntityList; if (tes==null) return;
		Iterator<TileEntity> iterator = tes.iterator(); if (iterator==null) return;
        while (iterator.hasNext()) {
            TileEntity te = (TileEntity)iterator.next(); if (te==null) return;

            if (!te.isInvalid() && te.hasWorldObj() && w.blockExists(te.xCoord, te.yCoord, te.zCoord)
            		&& (te instanceof TileQuarry))
            {
            	TileQuarry tq = (TileQuarry) te;
            	if (map.get(tq.owner) >= minCount) {
	            	if (map.containsKey(tq.owner)) {
	            		map.put(tq.owner, map.get(tq.owner)+1);
	            	}
	            	else {
	            		map.put(tq.owner, 1);
	            	}
            	}	
            }
        }
        
        ChatMessageComponent cmc = ChatMessageComponent.createFromText("quarryplus: [>=" + minCount + "]:\n");
        
        for ( Entry<String, Integer> e: map.entrySet()) {
        	cmc.addFormatted("    %10s: %d\n", e.getKey(), e.getValue());
        }
        icommandsender.sendChatToPlayer(cmc);
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

}
