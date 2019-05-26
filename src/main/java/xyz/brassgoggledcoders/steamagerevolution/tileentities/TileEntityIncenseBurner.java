package xyz.brassgoggledcoders.steamagerevolution.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.SARObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryPiece.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryRecipeMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.SARMachineTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityIncenseBurner extends SARMachineTileEntity {

	public TileEntityIncenseBurner() {
		setInventory(new InventoryRecipeMachine(new InventoryPieceItem(new ItemStackHandlerExtractSpecific(1), 109, 34), null,
				null, null, null));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
	}

	// TODO Automatic siding
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory.getInputHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Incense Burner";
	}

	@Override
	protected boolean canRun() {
		return getWorld().isAirBlock(getPos().up());
	}

	@Override
	protected void onActiveTick() {
		getWorld().setBlockState(getPos().up(), SARObjectHolder.incense.getDefaultState());
		getWorld().setBlockState(getPos().up(2), SARObjectHolder.incense_block.getDefaultState());
	}

}