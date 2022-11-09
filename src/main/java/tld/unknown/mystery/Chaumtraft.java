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
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.registries.ChaumtraftItems;
import tld.unknown.mystery.networking.ChaumtraftNetworking;
import tld.unknown.mystery.registries.ChaumtraftRecipes;

@Mod(Chaumtraft.MOD_ID)
public class Chaumtraft {

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

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ChaumtraftItems.init(modEventBus);
        ChaumtraftBlocks.init(modEventBus);
        ChaumtraftRecipes.init(modEventBus);

        ChaumtraftNetworking.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    public static boolean isDev() {
        return FMLLoader.isProduction();
    }

    public static void info(String format, Object... args) {
        LOGGER.info(String.format(format, args));
    }

    public static void error(String format, Object... args) {
        LOGGER.error(String.format(format, args));
    }

    public static void error(Throwable exception, String format, Object... args) {
        error(format, args);
        error("\t%s%s", exception.getClass().getSimpleName(), exception.getMessage() != null ? ": " + exception.getMessage() : "");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MinecraftForge.EVENT_BUS.addListener(ChaumtraftKeybinds::clientTick);
        }
    }
}
