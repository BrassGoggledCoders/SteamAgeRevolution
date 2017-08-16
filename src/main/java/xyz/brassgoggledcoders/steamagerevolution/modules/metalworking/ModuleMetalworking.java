package xyz.brassgoggledcoders.steamagerevolution.modules.metalworking;

import java.util.ArrayList;
import java.util.List;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.OreDictUtils;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.items.ItemDie;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.AlloyFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnacePrimaryFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.alloyfurnace.blocks.BlockAlloyFurnaceSecondaryFluidInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.hammer.SteamHammerRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.BlockSteelworksCarbonInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.BlockSteelworksFrame;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.BlockSteelworksIronInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.BlockSteelworksSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.multiblock.steelworks.BlockSteelworksSteelOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.processing.multiblock.furnace.SteamFurnaceRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.MoltenMetalRecipe;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.blocks.BlockCrucibleCasing;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.blocks.BlockCrucibleFluidOutput;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.blocks.BlockCrucibleItemInput;
import xyz.brassgoggledcoders.steamagerevolution.modules.smelting.multiblock.crucible.blocks.BlockCrucibleSteamInput;
import xyz.brassgoggledcoders.steamagerevolution.utils.RecipesIngotToPlate;
import xyz.brassgoggledcoders.steamagerevolution.utils.RecipesOreToDust;

@Module(value = SteamAgeRevolution.MODID)
@ObjectHolder(SteamAgeRevolution.MODID)
public class ModuleMetalworking extends ModuleBase {

	public static final Item charcoal_powder = null;

	public static List<String> knownMetalTypes = new ArrayList<String>();

	public static DamageSource hammer =
			new DamageSource("hammer").setDifficultyScaled().setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		for(ItemStack cooked : OreDictionary.getOres("listAllmeatcooked")) {
			SteamFurnaceRecipe.addSteamFurnaceRecipe(cooked, new ItemStack(charcoal_powder));
		}

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.STONE), new ItemStack(Blocks.COBBLESTONE));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.GRAVEL));
		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND));

		SteamHammerRecipe.addSteamHammerRecipe(new ItemStack(Blocks.DIRT), new ItemStack(Items.DIAMOND), "test");

		AlloyFurnaceRecipe.addAlloyFurnaceRecipe(
				FluidRegistry.getFluidStack("copper", TileEntityCastingBench.VALUE_INGOT),
				FluidRegistry.getFluidStack("zinc", TileEntityCastingBench.VALUE_INGOT),
				FluidRegistry.getFluidStack("brass", TileEntityCastingBench.VALUE_INGOT));
		super.init(event);
	}

	@Override
	public String getClientProxyPath() {
		return "xyz.brassgoggledcoders.steamagerevolution.modules.metalworking.ClientProxy";
	}

	@Override
	public String getName() {
		return "Metalworking";
	}

	@Override
	public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {

		// new BlockAlloyFurnaceController(Material.ANVIL, "alloy_furnace_controller");
		// blockRegistry.register(alloyFurnaceController);
		blockRegistry.register(new BlockAlloyFurnaceFrame(Material.ROCK, "alloy_furnace_frame"));
		blockRegistry
				.register(new BlockAlloyFurnacePrimaryFluidInput(Material.ROCK, "alloy_furnace_primary_fluid_input"));
		blockRegistry.register(
				new BlockAlloyFurnaceSecondaryFluidInput(Material.ROCK, "alloy_furnace_secondary_fluid_input"));
		blockRegistry.register(new BlockAlloyFurnaceFluidOutput(Material.ROCK, "alloy_furnace_fluid_output"));

		blockRegistry.register(new BlockSteelworksFrame(Material.ROCK, "steelworks_frame"));
		blockRegistry.register(new BlockSteelworksIronInput(Material.ROCK, "steelworks_iron_input"));
		blockRegistry.register(new BlockSteelworksCarbonInput(Material.ROCK, "steelworks_carbon_input"));
		blockRegistry.register(new BlockSteelworksSteamInput(Material.ROCK, "steelworks_steam_input"));
		blockRegistry.register(new BlockSteelworksSteelOutput(Material.ROCK, "steelworks_steel_output"));

		blockRegistry.register(new BlockCastingBench(Material.ANVIL, "casting_bench"));

		blockRegistry.register(new BlockCrucibleCasing(Material.IRON, "crucible_casing"));
		blockRegistry.register(new BlockCrucibleItemInput(Material.IRON, "crucible_item_input"));
		blockRegistry.register(new BlockCrucibleSteamInput(Material.IRON, "crucible_steam_input"));
		blockRegistry.register(new BlockCrucibleFluidOutput(Material.IRON, "crucible_fluid_output"));
	}

	@Override
	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(new ItemDie());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		knownMetalTypes.add("Iron");
		knownMetalTypes.add("Gold");
		for(String metal : knownMetalTypes) {
			if(FluidRegistry.isFluidRegistered(metal)) {
				for(ItemStack metalBlock : OreDictionary.getOres("block" + metal, false)) {
					MoltenMetalRecipe.addMelting(metalBlock, FluidRegistry.getFluid(metal.toLowerCase()));
				}
			}
			for(ItemStack ingot : OreDictionary.getOres("ingot" + metal, false)) {
				if(OreDictionary.doesOreNameExist("plate" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ingot, OreDictUtils.getPreferredItemStack("plate" + metal));
				}
				if(OreDictionary.doesOreNameExist("gear" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ingot, OreDictUtils.getPreferredItemStack("gear" + metal),
							"gear");
				}
			}
			for(ItemStack ore : OreDictionary.getOres("ore" + metal, false)) {
				if(OreDictionary.doesOreNameExist("dust" + metal)) {
					SteamHammerRecipe.addSteamHammerRecipe(ore, OreDictUtils.getPreferredItemStack("dust" + metal));
				}
			}
		}
		super.postInit(event);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(
				new RecipesOreToDust().setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ore_to_dust")));
		event.getRegistry().register(new RecipesIngotToPlate()
				.setRegistryName(new ResourceLocation(SteamAgeRevolution.MODID, "ingot_to_plate")));
	}

	@SubscribeEvent
	public void onOreRegistered(OreDictionary.OreRegisterEvent event) {
		String name = event.getName();
		String[] splitName = name.split("(?=[A-Z])");
		if(splitName.length == 2) {
			if(splitName[0].equals("ingot")) {
				String metalType = splitName[1];
				if(!knownMetalTypes.contains(metalType)) {
					knownMetalTypes.add(metalType);
					SteamAgeRevolution.instance.getLogger().devInfo("Metal type detected: " + metalType);
				}
			}
		}
	}

}