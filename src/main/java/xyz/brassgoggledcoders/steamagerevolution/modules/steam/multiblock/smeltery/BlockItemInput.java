package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.smeltery;

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
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.TileEntityBoilerController;

public class BlockItemInput extends BlockTEBase<TileEntityBoilerController> {

	public BlockItemInput(Material material, String name) {
		super(material, name);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity te = getTileEntity(world, pos);
		if(te != null && !player.isSneaking()) {
			// SteamAgeRevolution.instance.getGuiHandler().openGui(new GuiTileTarget(te, pos), new NBTTagCompound(),
			// false,
			// player, world);
			return true;
		}
		return false;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityBoilerController.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityBoilerController();
	}

}