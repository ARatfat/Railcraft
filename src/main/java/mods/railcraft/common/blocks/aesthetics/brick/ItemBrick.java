/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 * http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 ******************************************************************************/
package mods.railcraft.common.blocks.aesthetics.brick;

import mods.railcraft.common.blocks.ISubtypedBlock;
import mods.railcraft.common.blocks.ItemBlockRailcraftSubtyped;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;


public class ItemBrick extends ItemBlockRailcraftSubtyped<BrickVariant> {

    public <T extends Block & ISubtypedBlock<BrickVariant>> ItemBrick(T block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + BrickVariant.fromOrdinal(stack.getItemDamage());
    }

}
