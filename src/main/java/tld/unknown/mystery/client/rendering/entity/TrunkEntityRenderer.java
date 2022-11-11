package tld.unknown.mystery.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.client.rendering.ChaumtraftModelLayers;
import tld.unknown.mystery.client.rendering.entity.models.TrunkModel;
import tld.unknown.mystery.entities.TrunkEntity;
import tld.unknown.mystery.registries.ChaumtraftItems;

public class TrunkEntityRenderer extends LivingEntityRenderer<TrunkEntity, TrunkModel> {

    private static final ResourceLocation TEXTURE_NORMAL = Chaumtraft.id("textures/entity/trunk/trunk.png");
    private static final ResourceLocation TEXTURE_RAGE = Chaumtraft.id("textures/entity/trunk/trunk_angry.png");
    private static final ResourceLocation TEXTURE_CAPACITY = Chaumtraft.id("textures/entity/trunk/trunk_big.png");
    private static final ResourceLocation TEXTURE_EFFICIENCY = Chaumtraft.id("textures/entity/trunk/trunk_greedy.png");

    public TrunkEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TrunkModel(pContext.bakeLayer(ChaumtraftModelLayers.TRUNK)), .6F);
    }

    @Override
    public ResourceLocation getTextureLocation(TrunkEntity pEntity) {
        byte upgrades = pEntity.getUpgradeByte();
        if(ChaumtraftItems.UPGRADE_RAGE.get().isBitSet(upgrades)) {
            return TEXTURE_RAGE;
        } else if(ChaumtraftItems.UPGRADE_EFFICIENCY.get().isBitSet(upgrades)) {
            return TEXTURE_EFFICIENCY;
        } else if(ChaumtraftItems.UPGRADE_CAPACITY.get().isBitSet(upgrades)) {
            return TEXTURE_CAPACITY;
        } else {
            return TEXTURE_NORMAL;
        }
    }
}
