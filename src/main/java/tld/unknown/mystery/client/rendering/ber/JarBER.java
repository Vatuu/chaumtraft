package tld.unknown.mystery.client.rendering.ber;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.blocks.entities.JarBlockEntity;
import tld.unknown.mystery.client.rendering.RenderHelper;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.util.simple.SimpleBER;

import java.awt.*;

public class JarBER extends SimpleBER<JarBlockEntity> {

    private static final int FLUID_HEIGHT = 10;
    private static final int FLUID_WIDTH = 8;
    private static final ResourceLocation FILLED_TEXTURE = Chaumtraft.id("block/essentia_fluid");

    public JarBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(JarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(Chaumtraft.isDev()) {
            ResourceLocation type = pBlockEntity.getEssentiaType(Direction.UP);
            String essentiaType = type != null ? type.getPath() : (pBlockEntity.getLabel() != null ? pBlockEntity.getLabel().getPath() : "Undefined");
            renderNametag(pPoseStack, pBufferSource, 1, "[" + essentiaType + "]", pPackedLight);
            renderNametag(pPoseStack, pBufferSource, .75F, pBlockEntity.getEssentia(Direction.UP) + " / 250 [" + String.format("%.0f", pBlockEntity.getFillPercent() * 100) + "%]", pPackedLight);
        }

        if(pBlockEntity.getEssentia(Direction.UP) > 0) {
            VertexConsumer consumer = pBufferSource.getBuffer(RenderType.cutoutMipped());
            Color color = new Color(ChaumtraftData.ASPECTS.getOptional(pBlockEntity.getEssentiaType(Direction.UP)).orElse(Aspect.UNKNOWN).getColor().getValue());
            pPoseStack.pushPose();
            pPoseStack.translate(pixel(4), pixel(1), pixel(4));
            RenderSystem.setShaderTexture(0, FILLED_TEXTURE);
            RenderHelper.drawCuboid(consumer, pPoseStack.last().pose(),
                    new Vector3f(0, 0, 0), new Vector3f(FLUID_WIDTH, FLUID_HEIGHT * pBlockEntity.getFillPercent() , FLUID_WIDTH),
                    color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(),
                    4, 4,
                    pPackedLight);
            pPoseStack.popPose();
        }
    }

    private float pixel(float px) {
        return 1 / 16F * px;
    }
}
