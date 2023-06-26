package tld.unknown.mystery.client.rendering.ber;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.blocks.entities.JarBlockEntity;
import tld.unknown.mystery.client.rendering.RenderHelper;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.util.MathUtils;
import tld.unknown.mystery.util.simple.SimpleBER;

public class JarBER extends SimpleBER<JarBlockEntity> {

    private static final float FLUID_HEIGHT = MathUtils.px(10);
    private static final float FLUID_WIDTH = MathUtils.px(8);
    private static final ResourceLocation FILLED_TEXTURE = Chaumtraft.id("block/essentia_fluid.png");

    public JarBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(JarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(Chaumtraft.isDev()) {
            ResourceLocation type = pBlockEntity.getEssentiaType(Direction.UP);
            type = type != null ? type : (pBlockEntity.getLabel() != null ? pBlockEntity.getLabel() : ChaumtraftIDs.Aspects.ANY);
            renderNametag(pPoseStack, pBufferSource, 1, Aspect.getName(type, false, false), pPackedLight);
            renderNametag(pPoseStack, pBufferSource, .75F, pBlockEntity.getEssentia(Direction.UP) + " / 250 [" + String.format("%.0f", pBlockEntity.getFillPercent() * 100) + "%]", pPackedLight);
        }

        if(pBlockEntity.getEssentia(Direction.UP) > 0) {
            pPoseStack.pushPose();
            VertexConsumer consumer = pBufferSource.getBuffer(RenderType.cutoutMipped());
            pPoseStack.translate(MathUtils.px(4), MathUtils.px(1), MathUtils.px(4));
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(FILLED_TEXTURE);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            float fluidHeight = FLUID_HEIGHT * pBlockEntity.getFillPercent();
            RenderHelper.CUBOID_RENDERER.prepare(FLUID_WIDTH, fluidHeight, FLUID_WIDTH, 16, 16, sprite)
                    .setUVs(Direction.Axis.X, 4, 0, 12, fluidHeight)
                    .setUVs(Direction.Axis.Z, 4, 0, 12, fluidHeight)
                    .setUVs(Direction.Axis.Y, 4, 4, 12, 12)
                    .draw(consumer, pPoseStack.last().pose(), /*ChaumtraftData.ASPECTS.getOptional(pBlockEntity.getEssentiaType(Direction.UP)).orElse(Aspect.UNKNOWN).getColor().getValue()*/0xFFFFFFFF, true, pPackedLight);
            pPoseStack.popPose();
        }
    }
}
