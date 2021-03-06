package xyz.brassgoggledcoders.steamagerevolution.compat.guideapi;

import java.util.*;

import com.teamacronymcoders.base.util.OreDictUtils;

import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.entry.EntryItemStack;
import amerifrance.guideapi.page.PageFurnaceRecipe;
import amerifrance.guideapi.page.PageIRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;

public class CategoryBasics {

	public static Map<ResourceLocation, EntryAbstract> buildCategory() {
		Map<ResourceLocation, EntryAbstract> entries = new LinkedHashMap<ResourceLocation, EntryAbstract>();
		String keyBase = "guide." + SteamAgeRevolution.MODID + ".entry.basics.";

		List<IPage> hammerEntry = new ArrayList<IPage>();
		hammerEntry.addAll(PageHelper.pagesForLongText(I18n.translateToLocalFormatted(keyBase + "hammer.info")));
		// hammerEntry.add(new PageJsonRecipe(new
		// ResourceLocation(SteamAgeRevolution.MODID, "hammer")));
		hammerEntry.add(new PageIRecipe(new ShapelessOreRecipe(null, OreDictUtils.getPreferredItemStack("dustCopper"),
				OreDictUtils.getPreferredItemStack("oreCopper"), BookObjectHolder.hammer)));
		hammerEntry.add(new PageIRecipe(new ShapelessOreRecipe(null, OreDictUtils.getPreferredItemStack("plateCopper"),
				OreDictUtils.getPreferredItemStack("ingotCopper"), BookObjectHolder.hammer)));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "hammer_entry"),
				new EntryItemStack(hammerEntry, keyBase + "hammer", new ItemStack(BookObjectHolder.hammer)));

		BookUtils.addBasicEntry(entries, keyBase, "ore", OreDictUtils.getPreferredItemStack("oreZinc"), false);

		List<IPage> alloyEntry = new ArrayList<IPage>();
		alloyEntry.addAll(PageHelper.pagesForLongText(I18n.translateToLocalFormatted(keyBase + "alloy.info",
				OreDictUtils.getPreferredItemStack("blockBrass"))));
		alloyEntry.add(new PageFurnaceRecipe("dustBrass"));
		entries.put(new ResourceLocation(SteamAgeRevolution.MODID, "alloy_entry"),
				new EntryItemStack(alloyEntry, keyBase + "alloy", OreDictUtils.getPreferredItemStack("ingotBrass")));

		for (EntryAbstract entry : entries.values()) {
			PageHelper.setPagesToUnicode(entry.pageList);
		}

		return entries;
	}

}
