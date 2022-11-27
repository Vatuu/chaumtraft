package tld.unknown.mystery.client.rendering;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.client.rendering.ber.CrucibleBER;
import tld.unknown.mystery.client.rendering.entity.TrunkEntityRenderer;
import tld.unknown.mystery.client.rendering.ui.AspectTooltip;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.items.AbstractAspectItem;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.registries.ChaumtraftEntities;
import tld.unknown.mystery.registries.ChaumtraftItems;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RenderingEventHandlers {

    @SubscribeEvent
    public static void onTooltipGather(RenderTooltipEvent.GatherComponents e) {
        //TODO Actual restrictions, equipment, thaumonometer etc
        boolean shift = Minecraft.getInstance().options.keyShift.isDown();
        if(ChaumtraftData.ASPECT_REGISTRY.hasAspects(e.getItemStack())) {
            e.getTooltipElements().add(Either.right(new AspectTooltip.Data(ChaumtraftData.ASPECT_REGISTRY.getAspects(e.getItemStack()))));
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModRenderingEventHandlers {

        @SubscribeEvent
        public static void onTooltipComponentRegister(RegisterClientTooltipComponentFactoriesEvent e) {
            e.register(AspectTooltip.Data.class, d -> new AspectTooltip(d.aspects()));
        }

        @SubscribeEvent
        public static void onBlockEntityRendererRegister(EntityRenderersEvent.RegisterRenderers e) {
            e.registerBlockEntityRenderer(ChaumtraftBlocks.CRUCIBLE.entityType(), CrucibleBER::new);

            e.registerEntityRenderer(ChaumtraftEntities.LIVING_TRUNK.entityType(), TrunkEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onColorTinting(RegisterColorHandlersEvent.Item e) {
            e.register((stack, tintIndex) -> tintIndex == 1 ? ChaumtraftData.ASPECTS.getOptional(AbstractAspectItem.getEssentiaAspect(stack)).orElse(Aspect.UNKNOWN).getColor().getValue() : -1, ChaumtraftItems.PHIAL.get());
        }
    }
}
