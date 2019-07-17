package xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces;

import com.teamacronymcoders.base.util.GuiHelper;

import net.minecraftforge.fluids.FluidStack;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.gui.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.recipe.InventoryCraftingMachine;

public class InventoryPieceFluidTank extends InventoryPieceHandler<FluidTankSync> {

	public InventoryPieceFluidTank(String name, InventoryBasic parent, IOType type, FluidTankSync handler,
			int xPosition, int yPosition) {
		super(name, parent, type, handler, xPosition, yPosition, 28, 166, 22, 41);
		parent.fluidPieces.put(name, this);
		// TODO
		if(parent instanceof InventoryCraftingMachine) {
			if(type.equals(IOType.INPUT)) {
				((InventoryCraftingMachine) parent).fluidInputPieces.add(this);
			}
			else if(type.equals(IOType.OUTPUT)) {
				((InventoryCraftingMachine) parent).fluidInputPieces.add(this);
			}
		}
	}

	public InventoryPieceFluidTank(String name, InventoryBasic inventoryBasic, FluidTankSync handler, int xPos,
			int yPos) {
		this(name, inventoryBasic, null, handler, xPos, yPos);
	}

	@Override
	public void backgroundLayerCallback(GuiInventory gui, float partialTicks, int mouseX, int mouseY) {
		FluidStack stack = handler.getFluid();
		if(stack != null && stack.getFluid() != null && stack.amount > 0) {
			GuiHelper.renderGuiTank(stack, handler.getCapacity(), stack.amount, gui.guiLeft + this.getX(),
					gui.guiTop + this.getY(), 20, 39);
			gui.mc.renderEngine.bindTexture(GuiInventory.guiTexture);
			gui.drawTexturedModalRect(gui.guiLeft + this.getX(), gui.guiTop + this.getY() + 6, 21, 207, 22, 41);
		}
	}

	@Override
	public String getTooltip() {
		return com.teamacronymcoders.base.util.TextUtils.representTankContents(this.getHandler()).getFormattedText();
	}
}
