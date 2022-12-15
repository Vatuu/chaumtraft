package tld.unknown.mystery.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.items.VisCrystalItem;
import tld.unknown.mystery.items.PhialItem;
import tld.unknown.mystery.items.UpgradeItem;

import java.util.function.Supplier;

import static tld.unknown.mystery.api.ChaumtraftIDs.Items;

public final class ChaumtraftItems {

    private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static final RegistryObject<UpgradeItem> UPGRADE_SPEED = register(Items.UPGRADE_SPEED, () -> new UpgradeItem((byte)0b00000001));
    public static final RegistryObject<UpgradeItem> UPGRADE_CAPACITY = register(Items.UPGRADE_CAPACITY, () -> new UpgradeItem((byte)0b00000010));
    public static final RegistryObject<UpgradeItem> UPGRADE_RAGE = register(Items.UPGRADE_RAGE, () -> new UpgradeItem((byte)0b00000100));
    public static final RegistryObject<UpgradeItem> UPGRADE_EFFICIENCY = register(Items.UPGRADE_EFFICIENCY, () -> new UpgradeItem((byte)0b00001000));

    public static final RegistryObject<Item> PHIAL = register(Items.PHIAL, PhialItem::new);
    public static final RegistryObject<Item> VIS_CRYSTAL = register(Items.VIS_CRYSTAL, VisCrystalItem::new);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) { REGISTRY.register(bus); }

    private static <T extends Item> RegistryObject<T> register(ResourceLocation location, Supplier<T> instance) {
        return REGISTRY.register(location.getPath(), instance);
    }
}
