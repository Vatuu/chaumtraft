package tld.unknown.mystery.registries;

import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.util.ReflectionUtils;
import tld.unknown.mystery.util.simple.SimpleCreativeTab;

public final class ChaumtraftTabs {

    public static final SimpleCreativeTab MAIN = new SimpleCreativeTab(ChaumtraftIDs.CreativeTabs.MAIN, ChaumtraftIDs.Blocks.ARCANE_WORKBENCH);

    public static void init(IEventBus bus) {
        bus.register(ChaumtraftTabs.class);
    }

    @SubscribeEvent
    public static void registerCreativeTabs(CreativeModeTabEvent.Register e) {
        ReflectionUtils.getAllStaticsOfType(ChaumtraftTabs.class, SimpleCreativeTab.class).forEach(t -> t.construct(e));
    }

    @SubscribeEvent
    public static void populateCreativeTabs(CreativeModeTabEvent.BuildContents e) {
        ReflectionUtils.getAllStaticsOfType(ChaumtraftTabs.class, SimpleCreativeTab.class).forEach(t -> t.populate(e));
    }
}
