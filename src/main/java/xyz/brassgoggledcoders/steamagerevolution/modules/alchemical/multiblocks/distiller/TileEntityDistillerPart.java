package xyz.brassgoggledcoders.steamagerevolution.modules.alchemical.multiblocks.distiller;

import java.util.function.Function;

import com.teamacronymcoders.base.multiblock.MultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockControllerBase;
import com.teamacronymcoders.base.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

import net.minecraft.world.World;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockControllerInfo;
import xyz.brassgoggledcoders.steamagerevolution.utils.multiblock.IMultiblockTileInfo;

public abstract class TileEntityDistillerPart<T extends RectangularMultiblockControllerBase>
		extends RectangularMultiblockTileEntityBase<T> implements IMultiblockTileInfo {

	private Class<T> controllerClass;
	private Function<World, MultiblockControllerBase> controllerCreator;

	public TileEntityDistillerPart(Class<T> controllerClass, Function<World, MultiblockControllerBase> controllerCreator) {
		this.controllerClass = controllerClass;
		this.controllerCreator = controllerCreator;
	}

	@Override
	public Class<T> getMultiblockControllerType() {
		return controllerClass;
	}

	@Override
	public IMultiblockControllerInfo getControllerInfo() {
		return (IMultiblockControllerInfo) controllerCreator.apply(null);
	}

	@Override
	public boolean[] getValidPositions() {
		return new boolean[] {isGoodForFrame(null), isGoodForSides(null), isGoodForTop(null), isGoodForBottom(null),
				isGoodForInterior(null)};
	}

	@Override
	public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
		return false;
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return controllerCreator.apply(getWorld());
	}

}
