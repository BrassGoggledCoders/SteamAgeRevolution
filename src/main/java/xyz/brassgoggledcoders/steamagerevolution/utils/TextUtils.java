package xyz.brassgoggledcoders.steamagerevolution.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class TextUtils {

	public static String localize(String input, Object... format) {
		return I18n.translateToLocalFormatted(input, format);
	}

	public static TextComponentString representTankContents(FluidTank tank) {
		if(tank.getFluid() != null && tank.getFluidAmount() > 0) {
			return new TextComponentString(tank.getFluid().getLocalizedName() + ": " + tank.getFluidAmount() + "mB/"
					+ tank.getCapacity() + "mB");
		}
		return new TextComponentString("Empty");
	}

	public static TextComponentString representInventoryContents(ItemStackHandler handler) {
		TextComponentString start = new TextComponentString("Inventory: ");
		for(int i = 0; i < handler.getSlots(); i++) {
			ItemStack current = handler.getStackInSlot(i);
			if(current != ItemStack.EMPTY) {
				start.appendText(current.getCount() + " " + current.getDisplayName() + " ");
			}
		}

		return start;
	}
}