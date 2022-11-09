package tld.unknown.mystery.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.data.aspects.AspectRegistryManager;
import tld.unknown.mystery.data.aspects.PrimalAspect;
import tld.unknown.mystery.networking.ChaumtraftNetworking;
import tld.unknown.mystery.data.research.ResearchCategory;
import tld.unknown.mystery.data.research.ResearchEntry;
import tld.unknown.mystery.util.codec.data.CodecDataManager;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ChaumtraftData {

    public static final AspectRegistryManager ASPECT_REGISTRY = new AspectRegistryManager();
    public static final CodecDataManager<Aspect> ASPECTS = new CodecDataManager<>(Aspect.CODEC,
            "Aspects", "aspects", true, PrimalAspect.ALL, id -> !id.getPath().contains("/"));
    public static final CodecDataManager<ResearchCategory> RESEARCH_CATEGORY = new CodecDataManager<>(ResearchCategory.CODEC,
            "ResearchCategory", "research", true, null, id -> !id.getPath().contains("/"));
    public static final CodecDataManager<ResearchEntry> RESEARCH_ENTRIES = new CodecDataManager<>(ResearchEntry.CODEC,
            "ResearchEntry", "research", true, null, id -> {
                if(!id.getPath().contains("/"))
                    return false;
                ResourceLocation category = new ResourceLocation(id.getNamespace(), id.getPath().split("/")[0]);
                return RESEARCH_CATEGORY.entryPresent(category);
            });

    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent e) {
        e.addListener(RESEARCH_CATEGORY);
        e.addListener(RESEARCH_ENTRIES);
        e.addListener(ASPECTS);
        e.addListener(ASPECT_REGISTRY);
    }

    @SubscribeEvent
    public static void registerDataSync(OnDatapackSyncEvent e) {
        if(e.getPlayer() != null) {
            ChaumtraftNetworking.sendToPlayer(RESEARCH_CATEGORY, e.getPlayer());
            ChaumtraftNetworking.sendToPlayer(RESEARCH_ENTRIES, e.getPlayer());
            ChaumtraftNetworking.sendToPlayer(ASPECTS, e.getPlayer());
            ChaumtraftNetworking.sendToPlayer(ASPECT_REGISTRY, e.getPlayer());
        } else {
            ChaumtraftNetworking.sendToAll(RESEARCH_CATEGORY);
            ChaumtraftNetworking.sendToAll(RESEARCH_ENTRIES);
            ChaumtraftNetworking.sendToAll(ASPECTS);
            ChaumtraftNetworking.sendToAll(ASPECT_REGISTRY);
        }
    }
}
