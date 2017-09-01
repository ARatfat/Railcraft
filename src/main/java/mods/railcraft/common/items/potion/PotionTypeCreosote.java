package mods.railcraft.common.items.potion;

import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;

public class PotionTypeCreosote extends PotionTypeRailcraft {
    public PotionTypeCreosote() {
        super("creosote", new PotionEffect(RailcraftPotions.CREOSOTE.get(), 3600, 0));
    }

    @Override
    public void defineRecipes() {
        super.defineRecipes();
        PotionHelper.registerPotionTypeConversion(PotionTypes.AWKWARD, RailcraftItems.BOTTLE_CREOSOTE::isEqual, this);
    }
}
