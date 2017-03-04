package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.hammer;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSteamHammerFrame extends BlockTEBase<TileEntitySteamHammerFrame> {

	public BlockSteamHammerFrame(Material material, String name) {
		super(material, name);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntitySteamHammerFrame.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamHammerFrame();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntitySteamHammerFrame te = getTileEntity(world, pos);
		if(te != null && !player.isSneaking()) {
			if(te.getMultiblockController().getLastError() != null) {
				player.addChatMessage(te.getMultiblockController().getLastError().getChatMessage());
				return true;
			}
		}
		return false;
	}
}
