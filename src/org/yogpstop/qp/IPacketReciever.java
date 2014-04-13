package org.yogpstop.qp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.io.ByteArrayDataInput;

public interface IPacketReciever {
	void S_recievePacket(byte pattern, ByteArrayDataInput data, EntityPlayer ep);

	void C_recievePacket(byte pattern, ByteArrayDataInput data, EntityPlayer ep);

	Packet getDescriptionPacket();
}
