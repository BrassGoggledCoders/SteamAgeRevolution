package xyz.brassgoggledcoders.steamagerevolution.modules.steam.multiblock.boiler.tileentities;

import com.teamacronymcoders.base.multiblock.validation.IMultiblockValidator;

public class TileEntityBoilerGauge extends TileEntityBoilerPart {

    @Override
    public boolean isGoodForFrame(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForSides(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForTop(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForBottom(IMultiblockValidator validatorCallback) {
        return true;
    }

    @Override
    public boolean isGoodForInterior(IMultiblockValidator validatorCallback) {
        return false;
    }
}
