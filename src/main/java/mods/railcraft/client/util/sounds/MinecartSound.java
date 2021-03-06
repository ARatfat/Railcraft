/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 * http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 ******************************************************************************/

package mods.railcraft.client.util.sounds;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

/**
 * Created by CovertJaguar on 5/25/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class MinecartSound extends MovingSound {
    protected final EntityMinecart cart;

    public MinecartSound(SoundEvent soundEvent, SoundCategory category, EntityMinecart cart) {
        super(soundEvent, category);
        this.cart = cart;
        this.volume = 1f;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if (cart.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) cart.posX;
            this.yPosF = (float) cart.posY;
            this.zPosF = (float) cart.posZ;
        }
    }
}
