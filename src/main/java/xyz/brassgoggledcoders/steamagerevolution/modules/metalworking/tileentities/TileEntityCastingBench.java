package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.tileentities;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ModuleMetalworking;
import xyz.brassgoggledcoders.steamagerevolution.utils.fluids.MultiFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceFluid;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.InventoryMachine.InventoryPieceItem;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.SARMachineTileEntity;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.ItemStackHandlerExtractSpecific;

public class TileEntityCastingBench extends SARMachineTileEntity {

	public static int inputCapacity = ModuleMetalworking.VALUE_BLOCK;

	public TileEntityCastingBench() {
		setInventory(new InventoryMachine(null,
				new InventoryPieceFluid(new MultiFluidTank(TileEntityCastingBench.inputCapacity, this, 1), 51, 11),
				new InventoryPieceItem(new ItemStackHandlerExtractSpecific(1), 109, 34), null, null));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(facing.getAxis() == Axis.Y) {
			return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
		else {
			return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
					|| super.hasCapability(capability, facing);
		}
	}

	// TODO Automatic siding
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.inventory.getInputTank());
		}
		else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory.getOutputHandler());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "Casting Bench";
	}

	@Override
	public InventoryMachine getInventory() {
		return inventory;
	}
}
