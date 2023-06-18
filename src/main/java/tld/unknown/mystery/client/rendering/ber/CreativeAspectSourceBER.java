package tld.unknown.mystery.client.rendering.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.blocks.entities.CreativeAspectSourceBlockEntity;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.util.simple.SimpleBER;

public class CreativeAspectSourceBER extends SimpleBER<CreativeAspectSourceBlockEntity> {

    public CreativeAspectSourceBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CreativeAspectSourceBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getAspect() != null) {
            pPoseStack.pushPose();
            pPoseStack.translate(.5F, .5F, .5F);
            for(Direction dir : Direction.values()) {
                if(dir.getAxis() == Direction.Axis.Y) {
                    continue;
                }
                int axis = dir.getAxisDirection().getStep();
                pPoseStack.pushPose();
                if(dir.getAxis() == Direction.Axis.X) {
                    pPoseStack.translate((.5F + (1 / 16F / 8)) * (axis * -1), 0, 0);
                    pPoseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0, 1, 0, 90 * axis));
                } else {
                    pPoseStack.translate(0, 0, (.5F + (1 / 16F / 8)) * axis);
                    pPoseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0, 1, 0, dir == Direction.NORTH ? 0 : 180));
                }
                pPoseStack.scale(.5F, .5F, .5F);
                Matrix4f mat = pPoseStack.last().pose();
                Aspect aspect = ChaumtraftData.ASPECTS.getOptional(pBlockEntity.getAspect()).orElse(Aspect.UNKNOWN);
                VertexConsumer consumer = pBufferSource.getBuffer(RenderType.text(Aspect.getTexture(pBlockEntity.getAspect(), false)));
                int color = (255 << 24) | (aspect.getColor().getValue() & 0x00FFFFFF);
                int light = LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().relative(dir));
                consumer.vertex(mat, (float)0.5, (float)-0.5, (float) 0).color(color).uv(0, 1).uv2(light).endVertex();
                consumer.vertex(mat, (float)-0.5, (float)-0.5, (float) 0).color(color).uv(1, 1).uv2(light).endVertex();
                consumer.vertex(mat, (float)-0.5, (float)0.5, (float) 0).color(color).uv(1, 0).uv2(light).endVertex();
                consumer.vertex(mat, (float)0.5, (float)0.5, (float) 0).color(color).uv(0, 0).uv2(light).endVertex();
                pPoseStack.popPose();
            }
            pPoseStack.popPose();
        }
    }
}
