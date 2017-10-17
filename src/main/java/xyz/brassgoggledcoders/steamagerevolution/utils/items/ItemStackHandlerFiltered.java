package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.crucible.CrucibleRecipe;

public abstract class ItemStackHandlerFiltered extends ItemStackHandler {

	public ItemStackHandlerFiltered(int size) {
		super(size);
	}

	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if(!canInsertItem(slot, stack))
			return stack;
		return super.insertItem(slot, stack, simulate);
	}

	protected abstract boolean canInsertItem(int slot, ItemStack stack);

	public static class ItemStackHandlerFuel extends ItemStackHandlerFiltered {
		public ItemStackHandlerFuel(int size) {
			super(size);
		}

		@Override
		protected boolean canInsertItem(int slot, ItemStack stack) {
			return TileEntityFurnace.isItemFuel(stack);
		}
	}

	public static class ItemStackHandlerCrucible extends ItemStackHandlerFiltered {

		public ItemStackHandlerCrucible() {
			super(1);
		}

		@Override
		protected boolean canInsertItem(int slot, ItemStack stack) {
			return CrucibleRecipe.getRecipe(stack) != null;
		}

	}
}