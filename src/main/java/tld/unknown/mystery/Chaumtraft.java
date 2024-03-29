package tld.unknown.mystery;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;
import tld.unknown.mystery.client.ChaumtraftKeybinds;
import tld.unknown.mystery.client.screens.ArcaneWorkbenchScreen;
import tld.unknown.mystery.registries.*;
import tld.unknown.mystery.networking.ChaumtraftNetworking;
import tld.unknown.mystery.registries.client.ChaumtraftItemProperties;
import tld.unknown.mystery.registries.client.ChaumtraftMenus;
import tld.unknown.mystery.util.better.BetterSign;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@Mod(Chaumtraft.MOD_ID)
@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Chaumtraft {

    public static final String MOD_ID = "chaumtraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String value) {
        return new ResourceLocation(MOD_ID, value);
    }

    public Chaumtraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);

        BetterSign.init(MOD_ID, modEventBus);

        ChaumtraftItems.init(modEventBus);
        ChaumtraftBlocks.init(modEventBus);

        ChaumtraftEntities.init(modEventBus);
        ChaumtraftBlockEntities.init(modEventBus);

        ChaumtraftTabs.init(modEventBus);
        ChaumtraftRecipes.init(modEventBus);
        ChaumtraftMenus.init(modEventBus);

        ChaumtraftNetworking.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(ChaumtraftKeybinds::clientTick);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ChaumtraftMenus.init(modEventBus);
        ChaumtraftItemProperties.init(event);

        event.enqueueWork(() -> {
            MenuScreens.register(ChaumtraftMenus.ARCANE_WORKBENCH.get(), ArcaneWorkbenchScreen::new);
        });
    }

    public static boolean isDev() {
        return !FMLLoader.isProduction();
    }

    public static void info(String format, Object... args) {
        LOGGER.info(String.format(format, args));
    }

    public static void debug(String format, Object... args) {
        LOGGER.debug(String.format(format, args));
    }

    public static void error(String format, Object... args) {
        LOGGER.error(String.format(format, args));
    }

    public static void error(Throwable exception, String format, Object... args) {
        error(format, args);
        error("\t%s%s", exception.getClass().getSimpleName(), exception.getMessage() != null ? ": " + exception.getMessage() : "");
    }
}
