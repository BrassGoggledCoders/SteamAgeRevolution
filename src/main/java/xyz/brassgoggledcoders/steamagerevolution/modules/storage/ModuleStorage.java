package xyz.brassgoggledcoders.steamagerevolution.modules.storage;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.base.registry.ItemRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockBasicFluidTank;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockFluidIO;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.blocks.BlockTrunk;
import xyz.brassgoggledcoders.steamagerevolution.modules.storage.items.ItemCanister;

@Module(value = SteamAgeRevolution.MODID)
public class ModuleStorage extends ModuleBase {

	public static Block trunk, fluidIO, basicFluidTank;
	public static Item canister;

	@Override
	public String getName() {
		return "Storage";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
		trunk = new BlockTrunk(Material.WOOD, "trunk");
		blockRegistry.register(trunk);
		fluidIO = new BlockFluidIO();
		blockRegistry.register(fluidIO);
		basicFluidTank = new BlockBasicFluidTank(Material.IRON, "basic_fluid_tank");
		blockRegistry.register(basicFluidTank);
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		canister = new ItemCanister("canister", 10000);
		itemRegistry.register(canister);
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.storage.ClientProxy";
	}
}
