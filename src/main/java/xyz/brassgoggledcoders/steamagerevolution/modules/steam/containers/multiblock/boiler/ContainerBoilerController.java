package xyz.brassgoggledcoders.steamagerevolution.modules.steam.containers.multiblock.boiler;

import net.minecraft.entity.player.EntityPlayer;
import xyz.brassgoggledcoders.boilerplate.containers.ContainerBase;
import xyz.brassgoggledcoders.steamagerevolution.modules.steam.tileentities.multiblock.boiler.TileEntityBoilerController;

public class ContainerBoilerController extends ContainerBase {
	public ContainerBoilerController(EntityPlayer player, TileEntityBoilerController tileEntityBoilerController) {

	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}