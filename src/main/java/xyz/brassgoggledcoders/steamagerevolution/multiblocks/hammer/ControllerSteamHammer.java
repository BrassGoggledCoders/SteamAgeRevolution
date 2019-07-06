package xyz.brassgoggledcoders.steamagerevolution.multiblocks.hammer;

import com.teamacronymcoders.base.multiblock.IMultiblockPart;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.brassgoggledcoders.steamagerevolution.SteamAgeRevolution;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.InventoryRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.SARMultiblockRecipe;
import xyz.brassgoggledcoders.steamagerevolution.inventorysystem.pieces.InventoryPieceProgressBar;

public class ControllerSteamHammer extends SARMultiblockRecipe<InventoryRecipe> {

	public String dieType = "";
	BlockPos center = null;
	AxisAlignedBB interior = null;

	public ControllerSteamHammer(World world) {
		super(world);
		// TODO Investigate if possible to have same handler for input and output
		setInventory(new InventoryRecipe(this).addItemInput("itemInput", new int[] { 35 }, new int[] { 32 })
				.addItemOutput("itemOutput", new int[] { 138 }, new int[] { 32 }).setSteamTank(9, 11)
				.setProgressBar(new InventoryPieceProgressBar(103, 31)));
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		dieType = data.getString("dieType");
		super.onAttachedPartWithMultiblockData(part, data);
	}

	@Override
	protected void onMachineAssembled() {
		center = getReferenceCoord().up().east().south();
		interior = new AxisAlignedBB(center).expand(1, 2, 1);
		WORLD.markBlockRangeForRenderUpdate(getMinimumCoord(), getMaximumCoord());
		super.onMachineAssembled();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;// 3*3*4-10 Shielding is optional, always hollow.
	}

	@Override
	public int getMaximumXSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getMaximumZSize() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getMaximumYSize() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getMinimumXSize() {
		return 3;
	}

	@Override
	public int getMinimumZSize() {
		return 3;
	}

	@Override
	public int getMinimumYSize() {
		return 4;
	}

	@Override
	protected void onTick() {
		for(EntityItem item : WORLD.getEntitiesWithinAABB(EntityItem.class, interior)) {
			if(ItemHandlerHelper
					.insertItem(this.getInventory().getItemPiece("itemInput").getHandler(), item.getItem(), true)
					.isEmpty()) {
				ItemHandlerHelper.insertItem(this.getInventory().getItemPiece("itemInput").getHandler(), item.getItem(),
						false);
				item.setDead();
			}
		}
		super.onTick();
	}

	@Override
	public void onFinish() {
		super.onFinish();
		WORLD.playSound(null, center.getX() + .5F, center.getY(), center.getZ() + .5F, SoundEvents.BLOCK_ANVIL_LAND,
				SoundCategory.BLOCKS, 1F, 1F);
		SteamAgeRevolution.proxy.spawnFX(EnumParticleTypes.FLAME, center);
		WORLD.getEntitiesWithinAABB(EntityLivingBase.class, interior).forEach(
				entity -> entity.attackEntityFrom(SteamAgeRevolution.damageSourceHammer, entity.getMaxHealth()));
	}

	@Override
	protected boolean isBlockGoodForSides(World world, int x, int y, int z, IMultiblockValidator validatorCallback) {
		return world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public void writeToDisk(NBTTagCompound data) {
		data.setString("dieType", dieType);
		super.writeToDisk(data);
	}

	@Override
	public String getName() {
		return "Steam Hammer";
	}
}
