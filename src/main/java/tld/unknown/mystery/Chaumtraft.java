package tld.unknown.mystery;

import com.mojang.logging.LogUtils;
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
import tld.unknown.mystery.registries.*;
import tld.unknown.mystery.networking.ChaumtraftNetworking;

@Mod(Chaumtraft.MOD_ID)
@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Chaumtraft {

    public static final String MOD_ID = "chaumtraft";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ChaumtraftBlocks.CRUCIBLE.item());
        }
    };

    public static ResourceLocation id(String value) {
        return new ResourceLocation(MOD_ID, value);
    }

    public Chaumtraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);

        ChaumtraftItems.init(modEventBus);
        ChaumtraftBlocks.init(modEventBus);
        ChaumtraftRecipes.init(modEventBus);
        ChaumtraftEntities.init(modEventBus);

        ChaumtraftNetworking.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(ChaumtraftKeybinds::clientTick);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ChaumtraftMenus.init(modEventBus);
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
