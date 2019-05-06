package xyz.brassgoggledcoders.steamagerevolution.modules.mining;

import java.util.List;

import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.client.models.IHasModel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemPickaxeBase extends ItemPickaxe implements IHasModel {
    protected String texturePath;
    protected String name;
    private IBaseMod mod;

    public ItemPickaxeBase(ToolMaterial material, String name) {
        this(material, "", name);
    }

    public ItemPickaxeBase(ToolMaterial material, String texturePath, String name) {
        super(material);
        this.name = name;
        this.texturePath = texturePath;
        if (!texturePath.isEmpty() && !texturePath.endsWith("/"))
            this.texturePath += "/";
        this.setTranslationKey(name);
    }

    @Override
    public List<String> getModelNames(List<String> modelNames) {
        modelNames.add(texturePath + name);
        return modelNames;
    }

    @Override
    public List<ItemStack> getAllSubItems(List<ItemStack> itemStacks) {
        itemStacks.add(new ItemStack(this, 1));
        return itemStacks;
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public IBaseMod getMod() {
        return this.mod;
    }

    @Override
    public void setMod(IBaseMod mod) {
        this.mod = mod;
    }
}
