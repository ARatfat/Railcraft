package mods.railcraft.common.items.potion;

import mods.railcraft.common.util.misc.RailcraftDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * The creosote potion.
 */
public class PotionCreosote extends PotionRailcraft {
    public PotionCreosote() {
        super(false, 0xcca300);
        setIconIndex(0, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
            entityLivingBaseIn.attackEntityFrom(RailcraftDamageSource.CREOSOTE, (float) Math.pow(1.05D, amplifier));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int t = 25 >> amplifier;
        return t == 0 || duration % t == 0;
    }

    @Override
    public void finalizeDefinition() {
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent
            public void onEntityAttacked(LivingAttackEvent event) {
                if (event.getSource() instanceof EntityDamageSource) {
                    EntityDamageSource source = (EntityDamageSource) event.getSource();
                    if (source.getEntity() instanceof EntityLivingBase) {
                        EntityLivingBase entity = (EntityLivingBase) source.getEntity();
                        PotionEffect effect = event.getEntityLiving().getActivePotionEffect(PotionCreosote.this);
                        if (effect != null && entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
                            entity.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() / 2, effect.getAmplifier()));
                        }
                    }
                }
            }
        });
    }
}
