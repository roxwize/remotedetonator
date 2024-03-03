package xyz.roxwize.remotedetonator;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTNT;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class Detonator extends ItemTool {
	private boolean armed = false;
	private BlockTNT target;
	private int targetX, targetY, targetZ;

	protected Detonator(String name, int id, ToolMaterial toolMaterial) {
		super(name, id, 1, toolMaterial, null);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		Block block = world.getBlock(blockX, blockY, blockZ);
		if (block instanceof BlockTNT) {
			armed = true;
			target = (BlockTNT) block;
			targetX = blockX;
			targetY = blockY;
			targetZ = blockZ;
			entityplayer.addChatMessage("item.remotedetonator.detonator.message.armed");
			return true;
		}
		if (armed) {
			if (entityplayer.distanceTo(targetX, targetY, targetZ) > 128) {
				entityplayer.addChatMessage("item.remotedetonator.detonator.message.too_far");
				return false;
			}
			target.ignite(world, targetX, targetY, targetZ, entityplayer, true);
			target = null;
			armed = false;
			itemstack.damageItem(1, entityplayer);
			return true;
		}
		entityplayer.addChatMessage("item.remotedetonator.detonator.message.default");
		return false;
	}
}
