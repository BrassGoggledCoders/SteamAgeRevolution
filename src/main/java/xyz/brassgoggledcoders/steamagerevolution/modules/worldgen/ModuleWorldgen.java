package xyz.brassgoggledcoders.steamagerevolution.modules.worldgen;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModuleDependency;
import com.teamacronymcoders.base.registrysystem.config.ConfigEntry;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;

import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.worldgen.OreGenerator.OreEntry;

@ObjectHolder(SteamAgeRevolution.MODID)
@Module(value = SteamAgeRevolution.MODID)
public class ModuleWorldgen extends ModuleBase {

	@Override
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
		SubBlockSystem system = SteamAgeRevolution.instance.getSubBlockSystem();
		this.getConfigRegistry().addEntry(new ConfigEntry("Generation", "doCopperOreGen", Type.BOOLEAN, "true"));
		OreGenerator.oresToGenerate
				.add(new OreEntry("Copper", 16, 64, 8, system.getSubBlock("copper_ore_stone").getBlockState()));
		this.getConfigRegistry().addEntry(new ConfigEntry("Generation", "doZincOreGen", Type.BOOLEAN, "true"));
		OreGenerator.oresToGenerate
				.add(new OreEntry("Zinc", 10, 40, 6, system.getSubBlock("zinc_ore_stone").getBlockState()));
		this.getConfigRegistry().addEntry(new ConfigEntry("Generation", "doSulphurOreGen", Type.BOOLEAN, "true"));
		OreGenerator.oresToGenerate
				.add(new OreEntry("Sulphur", 6, 55, 4, system.getSubBlock("sulphur_ore").getBlockState()));
		super.init(event);
	}

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModuleDependency("Materials"));
		return dependencies;
	}

	@Override
	public String getName() {
		return "Worldgen";
	}
}