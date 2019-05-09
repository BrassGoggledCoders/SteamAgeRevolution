package xyz.brassgoggledcoders.steamagerevolution.modules.mining.entities;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.MiningUtils;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.ModuleMining;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.ContainerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.HandlerForceStack;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;

public class EntityMinecartCarrier extends EntityMinecartInventory<InventoryRecipeMachine>  {
	
	public static final DataParameter<ItemStack> CONTENTS = EntityDataManager.createKey(EntityMinecartCarrier.class, DataSerializers.ITEM_STACK);
	
	public EntityMinecartCarrier(World world) {
		super(world);
		this.setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new HandlerForceStack(this, 8), MiningUtils.getGUIPositionGrid(53, 31, 4, 2)), null, null, null, null));
	}
	
	@Override
	protected void entityInit()
    {
		this.dataManager.register(CONTENTS, ItemStack.EMPTY);
		super.entityInit();
    }

	@SideOnly(Side.CLIENT)
	@Override
	public Gui getGui(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new GuiInventory(entityPlayer, this, new ContainerForceStack(entityPlayer, this), "carrier_cart");
	}

	@Override
	public Container getContainer(EntityPlayer entityPlayer, World world, BlockPos blockPos) {
		return new ContainerForceStack(entityPlayer, this);
	}

	@Override
	public ItemMinecart getItem() {
		return ModuleMining.minecart_carrier;
	}
	
	@Override
	public void markDirty() {
		for(int i = 0 ; i < this.getInventory().getInputHandler().getSlots(); i++) {
			ItemStack stack = this.getInventory().getInputHandler().getStackInSlot(i);
			if(!stack.isEmpty()) {
				this.getDataManager().set(CONTENTS, stack);
				break;
			}
		}
		super.markDirty();
	}
}