package tld.unknown.mystery.api;

import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.data.aspects.AspectList;

public interface AspectContainerItem {
    AspectList getAspects(ItemStack stack);
}
