package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import java.lang.ref.WeakReference;

import com.google.common.base.Optional;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;

public class EntityMinecartDrilling extends EntityMinecartInventory<InventoryRecipeMachine> {

	public static final DataParameter<Optional<BlockPos>> MINING_POS = EntityDataManager.<Optional<BlockPos>>createKey(EntityMinecartDrilling.class, DataSerializers.OPTIONAL_BLOCK_POS);
	public static final DataParameter<Float> MINING_PROGRESS = EntityDataManager.<Float>createKey(EntityMinecartDrilling.class, DataSerializers.FLOAT);
	
	public EntityMinecartDrilling(World world) {
		super(world);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new HandlerForceStack(this, 3), MiningUtils.getGUIPositionGrid(62, 31, 3, 1)), null, null, null, null));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this, new ContainerForceStack(entityPlayer, this));
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerForceStack(entityPlayer, this);
	}

	@Override
	public ItemMinecart getItem() {
		return ModuleMining.minecart_drilling;
	}
	
	@Override
	protected void entityInit()
    {
		this.dataManager.register(MINING_POS, Optional.absent());
		this.dataManager.register(MINING_PROGRESS, 0F);
		super.entityInit();
    }

	@Override
	public void onActivatorRailPass(int blockX, int blockY, int blockZ, boolean receivingPower) {
		if (receivingPower) {
			BlockPos pos = new BlockPos(blockX, blockY, blockZ);
			if (BlockRailBase.isRailBlock(this.world, pos)) {
				BlockRailBase rail = (BlockRailBase) this.getEntityWorld().getBlockState(pos).getBlock();
				EnumRailDirection direction = rail.getRailDirection(getEntityWorld(), pos,
						this.getEntityWorld().getBlockState(pos), this);
				EnumFacing cartFacing = this.getAdjustedHorizontalFacing();
				switch (direction) {
				case NORTH_SOUTH:
					if (EnumFacing.NORTH.equals(cartFacing)) {
						this.doMining(EnumFacing.WEST);
					} else {
						this.doMining(EnumFacing.EAST);
					}
					break;
				case EAST_WEST:
					if (EnumFacing.WEST.equals(cartFacing)) {
						this.doMining(EnumFacing.NORTH);
					} else {
						this.doMining(EnumFacing.SOUTH);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void doMining(EnumFacing facingToMine) {
		WeakReference<FakePlayer> fakePlayer = new WeakReference<FakePlayer>(
				FakePlayerFactory.get((WorldServer) world, ModuleMining.profile));
		BlockPos target = this.getPosition().offset(facingToMine);
		if (!world.isAirBlock(target)) {
			if(this.getDataManager().get(MINING_PROGRESS).floatValue() >= 1.0F) {
				MiningUtils.doMining(this.getEntityWorld(), target, this.getInventory().getInputHandler());
				this.getDataManager().set(MINING_POS, Optional.absent());
				this.getDataManager().set(MINING_PROGRESS, 0F);
				return;
			}
			else {
				this.getDataManager().set(MINING_POS, Optional.of(target));
				this.getDataManager().set(MINING_PROGRESS, this.getDataManager().get(MINING_PROGRESS) + world.getBlockState(target).getPlayerRelativeBlockHardness(fakePlayer.get(), world, target));
				return;
			}
		}
		//If we can't mine, reset. TODO Reset on cart move?
		this.getDataManager().set(MINING_PROGRESS, 0F);
	}

}