package tld.unknown.mystery.util.better;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class BetterCreativeTab extends CreativeModeTab {

    private final RegistryObject<Item> icon;

    public BetterCreativeTab(String label, RegistryObject<Item> icon) {
        super(label);
        this.icon = icon;
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(icon.get());
    }
}
