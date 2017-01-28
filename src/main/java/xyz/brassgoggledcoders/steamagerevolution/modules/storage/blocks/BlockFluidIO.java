package xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.guisystem.target.GuiTileTarget;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.tileentities.TileEntityFluidIO;

public class BlockFluidIO extends BlockTEBase<TileEntityFluidIO> {

	public BlockFluidIO() {
		super(Material.IRON, "fluid_io");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFluidIO.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFluidIO();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tile = getTileEntity(world, pos);
		if(tile != null && !player.isSneaking()) {
			SteamAgeRevolution.instance.getGuiHandler().openGui(new GuiTileTarget(tile, pos), new NBTTagCompound(),
					false, player, world);
			return true;
		}
		return false;
	}

}