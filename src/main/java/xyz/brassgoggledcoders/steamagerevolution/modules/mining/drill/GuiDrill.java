package xyz.brassgoggledcoders.steamagerevolution.modules.mining.drill;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.steamagerevolution.api.crushedmaterial.ICrushedHandler;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryCrushed;
import xyz.brassgoggledcoders.steamagerevolution.modules.mining.InventoryPieceCrushed;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.GuiInventory;
import xyz.brassgoggledcoders.steamagerevolution.utils.inventory.IHasInventory;

@SideOnly(Side.CLIENT)
public class GuiDrill extends GuiInventory {
	
	public GuiDrill(EntityPlayer player, IHasInventory<InventoryCrushed> holder) {
		super(player, holder);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		InventoryPieceCrushed inventoryPiece = ((InventoryCrushed) holder.getInventory()).ore;
		ICrushedHandler oreHandler = inventoryPiece.getHandler();
		if(oreHandler.getHolders()[0].getCrushed() != null) {
		if(isPointInRegion(inventoryPiece.getX(0), inventoryPiece.getY(0),
				8, 55, mouseX, mouseY)) {
			if(oreHandler.getHolders()[0] != null) {
				List<String> tooltip = Lists.newArrayList();
				int capacity = oreHandler.getHolders()[0].getHolderCapacity();
				if(oreHandler.getHolders()[0].getAmount() > 0) {
					tooltip.add(oreHandler.getHolders()[0].getAmount() + "/" + capacity);
					tooltip.add(oreHandler.getHolders()[0].getCrushed().getMaterial().getTranslationKey());
				}
				else {
					// TODO Localization
					tooltip.add("Empty");
				}
				this.drawHoveringText(tooltip, mouseX, mouseY, fontRenderer);
			}
		}
		}
	}	

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		InventoryPieceCrushed inventoryPiece = ((InventoryCrushed) holder.getInventory()).ore;
		ICrushedHandler oreHandler = inventoryPiece.getHandler();
		if(oreHandler.getHolders()[0].getCrushed() != null) {
			mc.renderEngine.bindTexture(guiTexture);
			this.drawTexturedModalRect(guiLeft + inventoryPiece.getX(0), guiTop + inventoryPiece.getY(0), 176, 100, 8, 55);
		}
	}
}
