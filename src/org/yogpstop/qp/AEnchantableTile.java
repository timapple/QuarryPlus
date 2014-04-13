package org.yogpstop.qp;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public abstract class AEnchantableTile extends APacketRecieverTile implements IEnchantableTile {

	protected byte unbreaking;
	protected byte fortune;
	protected boolean silktouch;
	protected byte efficiency;
	
	public final List<Long> fortuneList = new ArrayList<Long>();
	public final List<Long> silktouchList = new ArrayList<Long>();
	public boolean fortuneInclude, silktouchInclude;
	
	public AEnchantableTile() {
		unbreaking = 0;
		fortune = 0;
		silktouch = false;
		efficiency = 0;
		
		fortuneInclude = false;
		silktouchInclude = false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		super.readFromNBT(nbttc);
		super.readFromNBT(nbttc);
		this.silktouch = nbttc.getBoolean("silktouch");
		this.fortune = nbttc.getByte("fortune");
		this.efficiency = nbttc.getByte("efficiency");
		this.unbreaking = nbttc.getByte("unbreaking");
		this.fortuneInclude = nbttc.getBoolean("fortuneInclude");
		this.silktouchInclude = nbttc.getBoolean("silktouchInclude");
		EnchantmentHelper.readLongCollection(nbttc.getTagList("fortuneList"), this.fortuneList);
		EnchantmentHelper.readLongCollection(nbttc.getTagList("silktouchList"), this.silktouchList);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttc) {
		super.writeToNBT(nbttc);
		nbttc.setBoolean("silktouch", this.silktouch);
		nbttc.setByte("fortune", this.fortune);
		nbttc.setByte("efficiency", this.efficiency);
		nbttc.setByte("unbreaking", this.unbreaking);
		nbttc.setBoolean("fortuneInclude", this.fortuneInclude);
		nbttc.setBoolean("silktouchInclude", this.silktouchInclude);
		nbttc.setTag("fortuneList",   EnchantmentHelper.writeLongCollection(this.fortuneList));
		nbttc.setTag("silktouchList", EnchantmentHelper.writeLongCollection(this.silktouchList));
	}
	
	@Override
	public byte getEfficiency() {
		return this.efficiency;
	}

	@Override
	public byte getFortune() {
		return this.fortune;
	}

	@Override
	public byte getUnbreaking() {
		return this.unbreaking;
	}

	@Override
	public boolean getSilktouch() {
		return this.silktouch;
	}

	@Override
	public void set(byte pefficiency, byte pfortune, byte punbreaking, boolean psilktouch) {
		this.efficiency = pefficiency;
		this.fortune = pfortune;
		this.unbreaking = punbreaking;
		this.silktouch = psilktouch;
	}
	
	@Override
	public void setFortuneConfig(Boolean include, List<Long> list) {
		fortuneInclude = include;
		fortuneList.clear();
		fortuneList.addAll(list);
	}

	@Override
	public void setSilctouchConfig(Boolean include, List<Long> list) {
		silktouchInclude = include;
		silktouchList.clear();
		silktouchList.addAll(list);
	}
	
	@Override
	public List<Long> getFortuneList() {
		return fortuneList;
	}
	
	@Override
	public List<Long> getSilktouchList() {
		return silktouchList;
	}
	
	@Override
	public boolean getFortuneInclude() {
		return fortuneInclude;
	}
	
	@Override
	public boolean getSilktouchInclude() {
		return silktouchInclude;
	}
	
	@Override
	public void G_reinit() {}
	
}
