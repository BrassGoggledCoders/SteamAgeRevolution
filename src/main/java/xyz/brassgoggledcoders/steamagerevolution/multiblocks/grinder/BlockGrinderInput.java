package xyz.brassgoggledcoders.steamagerevolution.multiblocks.grinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.BlockMultiblockBase;

public class BlockGrinderInput extends BlockMultiblockBase<TileEntityGrinderInput> {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
	
	public BlockGrinderInput() {
		super(Material.IRON, "grinder_input");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityGrinderInput.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState blockState) {
		return new TileEntityGrinderInput();
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		float damage = 3F;
		if(this.getTileEntity(worldIn, pos).isPresent() && this.getTileEntity(worldIn, pos).get().isConnected()) {
			ControllerGrinder controller = this.getTileEntity(worldIn, pos).get().getMultiblockController();
			if(controller.getCurrentProgress() > 0) {
				damage = 20F;
			}
			if(entityIn instanceof EntityItem) {
				EntityItem item = (EntityItem) entityIn;
				ItemStack stack = item.getItem();
				if(ItemHandlerHelper.insertItem(controller.getInventory().getInputHandler(), stack, true).isEmpty()) {
					ItemHandlerHelper.insertItem(controller.getInventory().getInputHandler(), stack, false);
					item.setDead();
				}
			}
		}
		entityIn.attackEntityFrom(SteamAgeRevolution.damageSourceGrinder, damage);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB;
    }

}