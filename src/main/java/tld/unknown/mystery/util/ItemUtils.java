package tld.unknown.mystery.util;

import net.minecraft.world.item.ItemStack;

public final class ItemUtils {

    public static boolean isSimple(ItemStack is) {
        return !is.hasTag() && !is.isDamaged() && is.getCount() == 1;
    }
}
