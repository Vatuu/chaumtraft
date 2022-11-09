package tld.unknown.mystery.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;

import java.util.function.Supplier;

public final class ChaumtraftItems {

    private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    //public static RegistryObject<Item> THAUMONOMICON = register(ChaumtraftIDs.Items.THAUMONOMICON, ThaumonomiconItem::new);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) { REGISTRY.register(bus); }

    private static RegistryObject<Item> register(ResourceLocation location, Supplier<? extends Item> instance) {
        return REGISTRY.register(location.getPath(), instance);
    }
}
