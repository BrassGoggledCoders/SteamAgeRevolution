package xyz.brassgoggledcoders.steamagerevolution.utils.inventory;

import com.teamacronymcoders.base.containers.ContainerBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;
import xyz.brassgoggledcoders.steamagerevolution.utils.items.SlotHandlerOutput;

public class ContainerInventory extends ContainerBase {
	public ContainerInventory(EntityPlayer player, IHasInventory holder) {
		if(holder.getInventory().itemInput != null) {
			for(int i = 0; i < holder.getInventory().itemInput.getHandler().getSlots(); i++) {
				this.addSlotToContainer(new SlotItemHandler(holder.getInventory().itemInput.getHandler(), i,
						holder.getInventory().itemInput.getX(i), holder.getInventory().itemInput.getY(i)));
			}
		}
		if(holder.getInventory().itemOutput != null) {
			for(int i = 0; i < holder.getInventory().itemOutput.getHandler().getSlots(); i++) {
				this.addSlotToContainer(new SlotHandlerOutput(holder.getInventory().itemOutput.getHandler(), i,
						holder.getInventory().itemOutput.getX(i), holder.getInventory().itemOutput.getY(i)));
			}
		}
		createPlayerSlots(player.inventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
