package xyz.brassgoggledcoders.steamagerevolution.modules.vanity;

import com.teamacronymcoders.base.items.ItemBase;

public class ItemWatch extends ItemBase {
	public ItemWatch() {
		super("vanity", "watch");
		setMaxStackSize(1);
	}

	// @Override
	// public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World
	// worldIn, EntityPlayer playerIn,
	// EnumHand hand) {
	// if(!worldIn.isRemote) {
	// /*
	// * final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	// * String mcTimeMessage =
	// StatCollector.translateToLocal("desc.item.watch.mcTime") + " " +
	// * worldIn.getWorldTime()
	// * + "\n" + StatCollector.translateToLocal("desc.item.watch.rwTime") + " "
	// * + sdf.format(Calendar.getInstance().getTime());
	// * ChatComponentText mcTimeComponent = new ChatComponentText(mcTimeMessage);
	// * mcTimeComponent.getChatStyle().setColor(EnumChatFormatting.GREEN);
	// * ChatUtils.sendNoSpamClient(mcTimeComponent);
	// */
	// }
	//
	// return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	// }
}
