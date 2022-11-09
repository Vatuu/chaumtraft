package tld.unknown.mystery.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.client.screens.ResearchDebugScreen;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ChaumtraftKeybinds {

    private static final KeyMapping RESEARCH_DEBUG_SCREEN = new KeyMapping("key.chaumtraft.debug.research",
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_O,
            "key.chaumtraft.category.debug");

    @SubscribeEvent
    public static void keyRegisterEvent(RegisterKeyMappingsEvent e) {
        if(Chaumtraft.isDev())
            registerDebugKeys(e);
    }

    public static void clientTick(TickEvent.ClientTickEvent e) {
        Minecraft client = Minecraft.getInstance();
        if(RESEARCH_DEBUG_SCREEN.consumeClick()) {
            client.setScreen(new ResearchDebugScreen());
        }
    }

    private static void registerDebugKeys(RegisterKeyMappingsEvent e) {
        e.register(RESEARCH_DEBUG_SCREEN);
    }
}
