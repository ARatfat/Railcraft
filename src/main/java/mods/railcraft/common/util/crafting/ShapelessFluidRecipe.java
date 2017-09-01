/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package mods.railcraft.common.util.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A version of {@link net.minecraftforge.oredict.ShapelessOreRecipe shaped ore recipe}
 * that supports fluid.
 */
public class ShapelessFluidRecipe implements IRecipe {
    protected ItemStack output = null;
    protected List<Object> input = new ArrayList<>();

    public ShapelessFluidRecipe(ItemStack result, Object... recipe) {
        output = result.copy();
        for (Object in : recipe) {
            if (in instanceof ItemStack) {
                input.add(((ItemStack) in).copy());
            } else if (in instanceof Item) {
                input.add(new ItemStack((Item) in));
            } else if (in instanceof Block) {
                input.add(new ItemStack((Block) in));
            } else if (in instanceof String) {
                input.add(OreDictionary.getOres((String) in));
            } else if (in instanceof FluidStack) {
                input.add(((FluidStack) in).copy());
            } else {
                StringBuilder ret = new StringBuilder("Invalid shapeless ore recipe: ");
                for (Object tmp : recipe) {
                    ret.append(tmp).append(", ");
                }
                ret.append(output);
                throw new RuntimeException(ret.toString());
            }
        }
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return input.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1) {
        return output.copy();
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    @Override
    public boolean matches(InventoryCrafting var1, World world) {
        List<Object> required = new ArrayList<>(input);

        for (int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack slot = var1.getStackInSlot(i);

            if (slot != null) {
                boolean inRecipe = false;

                for (Iterator<Object> iterator = required.iterator(); iterator.hasNext(); ) {
                    boolean match = false;
                    Object next = iterator.next();

                    if (next instanceof ItemStack) {
                        match = OreDictionary.itemMatches((ItemStack) next, slot, false);
                    } else if (next instanceof List) {
                        Iterator<ItemStack> itr = ((List<ItemStack>) next).iterator();
                        while (itr.hasNext() && !match) {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    } else if (next instanceof FluidStack) {
                        if (slot.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                            IFluidHandler handler = slot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                            FluidStack fluidStack = handler.drain((FluidStack) next, false);
                            if (fluidStack == null || fluidStack.amount != ((FluidStack) next).amount) {
                                continue;
                            }
                            match = true;
                        } else if (slot.getItem() instanceof IFluidContainerItem) {
                            // legacy
                            IFluidContainerItem handler = (IFluidContainerItem) slot.getItem();
                            FluidStack fluidStack = handler.getFluid(slot);
                            if (fluidStack == null || fluidStack.getFluid() != ((FluidStack) next).getFluid() || fluidStack.amount < ((FluidStack) next).amount) {
                                continue;
                            }
                            match = true;
                        } else {
                            continue;
                        }
                    }

                    if (match) {
                        inRecipe = true;
                        iterator.remove();
                        break;
                    }
                }

                if (!inRecipe) {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public List<Object> getInput() {
        return this.input;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}