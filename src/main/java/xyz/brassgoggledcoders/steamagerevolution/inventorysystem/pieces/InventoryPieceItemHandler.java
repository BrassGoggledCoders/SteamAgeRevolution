package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import net.minecraftforge.items.ItemStackHandler;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;

public class InventoryPieceItemHandler extends InventoryPieceHandler<ItemStackHandler> {

	int[] slotXs;
	int[] slotYs;

	public InventoryPieceItemHandler(String name, InventoryBasic parent, ItemStackHandlerSync handler, int[] slotXs,
			int[] slotYs) {
		this(name, parent, null, handler, slotXs, slotYs);
	}

	public InventoryPieceItemHandler(String name, InventoryBasic parent, IOType type, ItemStackHandlerSync handler,
			int slotXs[], int[] slotYs) {
		super(name, parent, type, handler, slotXs[0], slotYs[0]);
		this.slotXs = slotXs;
		this.slotYs = slotYs;
	}

	public int getSlotPositionX(int slot) {
		return slotXs[slot];
	}

	public int getSlotPositionY(int slot) {
		return slotYs[slot];
	}
}