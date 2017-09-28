package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.utils.TextUtils;

@ObjectHolder(SteamAgeRevolution.MODID)
public class CategoryTransportStorage {

	public static final Block fluid_hopper = null;
	public static final Item canister = null;

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.transportstorage.";

		List<IPage> canisterEntry = new ArrayList<IPage>();
		canisterEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "canister.info")));
		canisterEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "canister")));
		// TODO Recipe handling
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "canister_entry"),
				new EntryItemStack(canisterEntry, keyBase + "canister", new ItemStack(canister)));

		List<IPage> fluidHopperEntry = new ArrayList<IPage>();
		fluidHopperEntry.addAll(PageHelper.pagesForLongText(TextUtils.localize(keyBase + "fluid_hopper.info")));
		fluidHopperEntry.add(PageIRecipe.fromJson(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_hopper")));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "fluid_hopper_entry"),
				new EntryItemStack(fluidHopperEntry, keyBase + "fluid_hopper", new ItemStack(fluid_hopper)));

		for(EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
