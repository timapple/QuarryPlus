/*
 * Copyright (C) 2012,2013 yogpstop
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the
 * GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.yogpstop.qp;

import java.util.ArrayList;

import buildcraft.api.tools.IToolWrench;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockQuarry extends BlockContainer {
	Icon texTop, texFrontOn, texFrontOff, texBB, texNNB, texMF;

	public BlockQuarry(int i) {
		super(i, Material.iron);
		setHardness(1.5F);
		setResistance(10F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(QuarryPlus.ct);
		setUnlocalizedName("QuarryPlus");
	}

	private final ArrayList<ItemStack> drop = new ArrayList<>();

	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		this.drop.clear();
		TileQuarry tile = (TileQuarry) world.getBlockTileEntity(x, y, z);
		if (world.isRemote || tile == null) return;
		int count = quantityDropped(meta, 0, world.rand);
		int id1 = idDropped(meta, world.rand, 0);
		if (id1 > 0) {
			for (int i = 0; i < count; i++) {
				ItemStack is = new ItemStack(id1, 1, damageDropped(meta));
				EnchantmentHelper.enchantmentToIS(tile, is);
				this.drop.add(is);
			}
		}
		super.breakBlock(world, x, y, z, id, meta);
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		return this.drop;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess ba, int x, int y, int z, int side) {
		TileEntity t = ba.getBlockTileEntity(x, y, z);
		if (t.isInvalid() || !(t instanceof TileQuarry)) return null;
		TileQuarry tq = (TileQuarry) t;
		
		if (side == 1) { // UP

			switch (tq.G_getNow()) {
			case TileQuarry.BREAKBLOCK:
			case TileQuarry.MOVEHEAD:
				return this.texBB;
			case TileQuarry.MAKEFRAME:
				return this.texMF;
			case TileQuarry.NOTNEEDBREAK:
				return this.texNNB;
			default:
				return this.texTop;
			}
		}
		
		int rotate = ba.getBlockMetadata(x, y, z);
		if ((rotate == side && rotate > 1)  	// Front can't be top or bottom.
			|| (rotate == 0 && side == 3)) {	// If no metadata is set, then this is an icon. 
			//front
			return tq.isActive() ? this.texFrontOn : this.texFrontOff;
		}
		// side
		return this.blockIcon; 
	}

	/**
     * called from Block.getBlockTexture by default
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) { // enum {bottom=0, top=1, north=2, south=3, west=4, east=5}

			switch (side) {
			case 3:
				return this.texFrontOn;
			case 1:
				return this.texTop;
			default:
				return this.blockIcon; 
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon 		= par1IconRegister.registerIcon("yogpstop_qp:quarry");
		this.texTop 		= par1IconRegister.registerIcon("yogpstop_qp:quarry_top");
		//this.textureFront 	= par1IconRegister.registerIcon("yogpstop_qp:quarry_front");
		this.texFrontOn 	= par1IconRegister.registerIcon("yogpstop_qp:quarry_front_on");
		this.texFrontOff 	= par1IconRegister.registerIcon("yogpstop_qp:quarry_front_off");
		this.texBB 			= par1IconRegister.registerIcon("yogpstop_qp:quarry_top_bb");
		this.texNNB 		= par1IconRegister.registerIcon("yogpstop_qp:quarry_top_nnb");
		this.texMF 			= par1IconRegister.registerIcon("yogpstop_qp:quarry_top_mf");
		//this.texNEE 		= par1IconRegister.registerIcon("yogpstop_qp:quarry_top_nee");
	}
	
	@Override
	public TileEntity createNewTileEntity(World w) {
		return new TileQuarry();
	}

	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase el, ItemStack is) {
		super.onBlockPlacedBy(w, x, y, z, el, is);
		ForgeDirection orientation = get2dOrientation(el.posX, el.posZ, x, z);
		w.setBlockMetadataWithNotify(x, y, z, orientation.getOpposite().ordinal(), 1);
		TileQuarry t = (TileQuarry) w.getBlockTileEntity(x, y, z);
		//t.placedBy = (EntityPlayer) el;
		if (el instanceof EntityPlayer)
			t.owner = ((EntityPlayer) el).username;
		EnchantmentHelper.init( (IEnchantableTile)t, is );
		t.requestTicket();
	}

	private static ForgeDirection get2dOrientation(double x1, double z1, double x2, double z2) {
		double Dx = x1 - x2;
		double Dz = z1 - z2;
		double angle = Math.atan2(Dz, Dx) / Math.PI * 180 + 180;

		if (angle < 45 || angle > 315) return ForgeDirection.EAST;
		else if (angle < 135) return ForgeDirection.SOUTH;
		else if (angle < 225) return ForgeDirection.WEST;
		else return ForgeDirection.NORTH;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9) {
		Item equipped = ep.getCurrentEquippedItem() != null ? ep.getCurrentEquippedItem().getItem() : null;
		if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(ep, x, y, z)) {
			((TileQuarry) world.getBlockTileEntity(x, y, z)).G_reinit();
			((IToolWrench) equipped).wrenchUsed(ep, x, y, z);
			return true;
		}
		if (equipped instanceof ItemTool && ep.getCurrentEquippedItem().getItemDamage() == 0) {
			TileEntity te = world.getBlockTileEntity(x, y, z);
			
			if (world.isRemote) {
				if (te instanceof TileQuarry) {
					//ep.addChatMessage(String.format("C: now = %d, nee = %b", ((TileQuarry)te).G_getNow(), ((TileQuarry)te).isNotEnoughEnergy() ));
				}
				return true;
			}
			
			ChatMessageComponent m = new ChatMessageComponent();
			if (te instanceof TileQuarry) {
				//m.addText( String.format("S: now = %d, nee = %b", ((TileQuarry)te).G_getNow(), ((TileQuarry)te).isNotEnoughEnergy()) );
			}
			for (String s : EnchantmentHelper.getEnchantmentsChat((IEnchantableTile)te))
				m.addText("\n"+s);
			PacketDispatcher.sendPacketToPlayer(new Packet3Chat(m), (Player) ep);
			return true;
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		if (!world.isRemote) ((TileMiningCore) world.getBlockTileEntity(x, y, z)).G_renew_powerConfigure();
	}

}