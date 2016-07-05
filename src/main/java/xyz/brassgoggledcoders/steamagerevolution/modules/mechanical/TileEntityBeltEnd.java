package xyz.brassgoggledcoders.steamagerevolution.modules.mechanical;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import xyz.brassgoggledcoders.steamagerevolution.api.SARAPI;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.ISpinHandler;
import xyz.brassgoggledcoders.steamagerevolution.api.capabilities.SpinHandler;

public class TileEntityBeltEnd extends TileEntityOneWayPair {

	private ISpinHandler handler = new SpinHandler();

	@Override
	public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capObject, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
		if(capObject == SARAPI.SPIN_HANDLER_CAPABILITY) {
			return SARAPI.SPIN_HANDLER_CAPABILITY.cast(handler);
		}

		return super.getCapability(capObject, side);
	}

	@Override
	public void updateTile() {
		if(getWorld().isRemote)
			return;

		if(this.isTilePaired()) {
			if((this.isMaster())) {
				if(this.getPairedTile() != null) {
					TileEntityBeltEnd other_belt = (TileEntityBeltEnd) this.getPairedTile();
					other_belt.handler.fill(1);
				}
				this.mod.getLogger().devInfo("Master at: " + this.getPos().toString());
			}
			else
				this.mod.getLogger().devInfo("Slave at: " + this.getPos().toString());
		}
	}
}
