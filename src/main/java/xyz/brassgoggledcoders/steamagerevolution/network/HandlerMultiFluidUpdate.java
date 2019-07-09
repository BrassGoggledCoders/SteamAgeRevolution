package xyz.brassgoggledcoders.steamagerevolution.network;

import com.teamacronymcoders.base.multiblock.MultiblockTileEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.IHasInventory;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.ISmartTankCallback;

public class HandlerMultiFluidUpdate implements IMessageHandler<PacketMultiFluidUpdate, IMessage> {
	public HandlerMultiFluidUpdate() {

	}

	@Override
	public IMessage onMessage(PacketMultiFluidUpdate message, MessageContext ctx) {
		Minecraft minecraft = Minecraft.getMinecraft();
		final WorldClient worldClient = minecraft.world;
		minecraft.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				processMessage(worldClient, message);
			}
		});
		return null;
	}

	private void processMessage(WorldClient worldClient, PacketMultiFluidUpdate message) {
		TileEntity te = worldClient.getTileEntity(message.pos);
		if (te instanceof IHasInventory) {
			ISmartTankCallback tile = ((IHasInventory) te).getInventory();
			tile.updateFluid(message);
		} else {
			MultiblockTileEntityBase<?> tile = (MultiblockTileEntityBase<?>) te;
			ISmartTankCallback controller = ((IHasInventory) tile.getMultiblockController()).getInventory();
			controller.updateFluid(message);
		}
	}
}
