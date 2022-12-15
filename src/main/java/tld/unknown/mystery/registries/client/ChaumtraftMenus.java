package tld.unknown.mystery.registries.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.menus.ArcaneWorkbenchMenu;
import tld.unknown.mystery.menus.TrunkMenu;

public final class ChaumtraftMenus {

    private static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static final RegistryObject<MenuType<TrunkMenu>> TRUNK_MENU_SMALL = register(Chaumtraft.id("trunk_menu"), (id, inv) -> TrunkMenu.create(id, inv, false));
    public static final RegistryObject<MenuType<TrunkMenu>> TRUNK_MENU_BIG = register(Chaumtraft.id("trunk_menu_big"), (id, inv) -> TrunkMenu.create(id, inv, true));
    public static final RegistryObject<MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH = register(Chaumtraft.id("arcane_workbench"), (id, inv) -> new ArcaneWorkbenchMenu(id));

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY.register(bus);
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(ResourceLocation id, MenuType.MenuSupplier<T> factory) {
        return REGISTRY.register(id.getPath(), () -> new MenuType<>(factory));
    }

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(ResourceLocation id, IContainerFactory<T> factory) {
        return REGISTRY.register(id.getPath(), () -> IForgeMenuType.create(factory));
    }
}
