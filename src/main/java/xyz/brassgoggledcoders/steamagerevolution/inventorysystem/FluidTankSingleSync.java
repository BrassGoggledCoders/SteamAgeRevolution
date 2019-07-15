package xyz.brassgoggledcoders.steamagerevolution.inventorysystem;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankSingleSync extends FluidTankSync {

	private String fluidName;

	public FluidTankSingleSync(String name, int capacity, String fluidName, IHasInventory<?> container) {
		super(name, capacity, container);
		this.fluidName = fluidName;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return getFluidName().equals(FluidRegistry.getFluidName(fluid)) ? canFill() : false;
	}

	public String getFluidName() {
		return fluidName;
	}

}
