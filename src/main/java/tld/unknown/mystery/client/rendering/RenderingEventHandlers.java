package tld.unknown.mystery.client.rendering;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.blocks.CrystalBlock;
import tld.unknown.mystery.client.rendering.ber.CrucibleBER;
import tld.unknown.mystery.client.rendering.ber.PedestalBER;
import tld.unknown.mystery.client.rendering.ber.RunicMatrixBER;
import tld.unknown.mystery.client.rendering.entity.TrunkEntityRenderer;
import tld.unknown.mystery.client.rendering.ui.AspectTooltip;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.items.AbstractAspectItem;
import tld.unknown.mystery.items.blocks.CrystalBlockItem;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
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
            e.registerBlockEntityRenderer(ChaumtraftBlockEntities.CRUCIBLE.entityType(), CrucibleBER::new);
            e.registerBlockEntityRenderer(ChaumtraftBlockEntities.RUNIC_MATRIX.entityType(), RunicMatrixBER::new);
            e.registerBlockEntityRenderer(ChaumtraftBlockEntities.PEDESTAL.entityType(), PedestalBER::new);

            e.registerEntityRenderer(ChaumtraftEntities.LIVING_TRUNK.entityType(), TrunkEntityRenderer::new);
        }

        @SubscribeEvent
        public static void onBlockColorTinting(RegisterColorHandlersEvent.Block e) {
            e.register((bs, level, pos, tintIndex) -> ChaumtraftData.ASPECTS.getOptional(bs.getValue(CrystalBlock.ASPECT).getId()).orElse(Aspect.UNKNOWN).getColor().getValue(), ChaumtraftBlocks.CRYSTAL_COLONY.block());
        }

        @SubscribeEvent
        public static void onItemColorTinting(RegisterColorHandlersEvent.Item e) {
            e.register((stack, tintIndex) -> tintIndex == 1 ? ChaumtraftData.ASPECTS.getOptional(((AbstractAspectItem)stack.getItem()).getContent(stack)).orElse(Aspect.UNKNOWN).getColor().getValue() : -1, ChaumtraftItems.PHIAL.get());
            e.register((stack, tintIndex) -> ChaumtraftData.ASPECTS.getOptional(((AbstractAspectItem)stack.getItem()).getContent(stack)).orElse(Aspect.UNKNOWN).getColor().getValue(), ChaumtraftItems.VIS_CRYSTAL.get());

            e.register((stack, tintIndex) -> {
                CrystalBlock.CrystalAspect aspect = ((CrystalBlockItem)stack.getItem()).getContent(stack);
                if(aspect == null) {
                    return 0xFFFFFFFF;
                }
                return ChaumtraftData.ASPECTS.getOptional(aspect.getId()).orElse(Aspect.UNKNOWN).getColor().getValue();
            }, ChaumtraftBlocks.CRYSTAL_COLONY.item());
        }
    }
}
