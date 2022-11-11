package tld.unknown.mystery.client.rendering;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.client.rendering.entity.models.TrunkModel;
import tld.unknown.mystery.registries.ChaumtraftEntities;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ChaumtraftModelLayers {

    public static ModelLayerLocation TRUNK = new ModelLayerLocation(ChaumtraftIDs.Entities.TRAVELING_TRUNK, "main");

    @SubscribeEvent
    public static void onLayerBake(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(TRUNK, TrunkModel::createBodyLayer);
    }
}
