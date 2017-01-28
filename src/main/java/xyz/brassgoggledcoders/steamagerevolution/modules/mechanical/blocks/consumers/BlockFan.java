package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.blocks.consumers;

import javax.annotation.Nullable;

import com.teamacronymcoders.base.api.BaseAPI;
import com.teamacronymcoders.base.blocks.BlockTEBase;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.mechanical.tileentities.consumers.TileEntityFan;

public class BlockFan extends BlockTEBase<TileEntityFan> {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockFan() {
		super(Material.ANVIL, "fan");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(ItemStackUtils.isItemNonNull(heldItem) && heldItem.hasCapability(BaseAPI.TOOL_CAPABILITY, side)) {
			world.setBlockState(pos, state.cycleProperty(FACING));
			return true;
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityFan.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityFan();
	}

}