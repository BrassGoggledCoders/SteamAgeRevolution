package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical;

import com.teamacronymcoders.base.modulesystem.proxies.IModuleProxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.MultiblockVatTankRenderer;
import xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.vat.TileEntityVatFrame;

public class ClientProxy implements IModuleProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {}

	@Override
	public void init(FMLInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVatFrame.class, new MultiblockVatTankRenderer());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}
}