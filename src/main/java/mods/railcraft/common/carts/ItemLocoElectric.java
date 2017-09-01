/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.common.carts;

import mods.railcraft.api.carts.locomotive.LocomotiveRenderType;
import mods.railcraft.common.blocks.charge.BlockChargeBattery;
import mods.railcraft.common.items.ItemCharge;
import mods.railcraft.common.items.ItemGear;
import mods.railcraft.common.items.Metal;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.plugins.color.EnumColor;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by CovertJaguar on 8/30/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class ItemLocoElectric extends ItemLocomotive {
    public ItemLocoElectric(IRailcraftCartContainer cart) {
        super(cart, LocomotiveRenderType.ELECTRIC, EnumColor.YELLOW, EnumColor.BLACK);
    }

    @Override
    public void defineRecipes() {
        super.defineRecipes();
        //TODO: Change to battery
        CraftingPlugin.addRecipe(getStack(),
                "LT ",
                "DBD",
                "GMG",
                'L', Blocks.REDSTONE_LAMP,
                'D', RailcraftItems.CHARGE, ItemCharge.EnumCharge.MOTOR,
                'B', BlockChargeBattery.RECHARGEABLE_BATTERY_ORE_TAG,
                'M', Items.MINECART,
                'G', RailcraftItems.GEAR, ItemGear.EnumGear.STEEL,
                'T', RailcraftItems.PLATE, Metal.STEEL);
    }
}
