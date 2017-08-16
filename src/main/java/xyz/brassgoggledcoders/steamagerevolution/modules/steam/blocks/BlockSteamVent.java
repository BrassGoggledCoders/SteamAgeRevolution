package xyz.brassgoggledcoders.steamagerevolution.modules.steam.blocks;

import com.teamacronymcoders.base.Capabilities;
import com.teamacronymcoders.base.blocks.BlockTEBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.TileEntitySteamVent;

public class BlockSteamVent extends BlockTEBase<TileEntitySteamVent> {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockSteamVent(Material material, String name) {
		super(material, name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
		return TileEntitySteamVent.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntitySteamVent();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!playerIn.getHeldItem(hand).isEmpty()
				&& playerIn.getHeldItem(hand).hasCapability(Capabilities.TOOL, facing)) {
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(FACING));
			return true;

		}
		return false;
	}
}
