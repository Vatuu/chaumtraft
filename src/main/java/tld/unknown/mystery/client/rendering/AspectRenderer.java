package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.util.RenderUtils;

public final class AspectRenderer {

    public static void renderAspectOverlaySDF(PoseStack stack, ResourceLocation aspectId, int x, int y, int size, int amount) {
        stack.pushPose();
        Matrix4f pMatrix = stack.last().pose();
        Aspect aspect = ChaumtraftData.ASPECTS.getOptional(aspectId).orElse(Aspect.UNKNOWN);
        RenderSystem.setShader(() -> RenderTypes.bindSdf(Aspect.getTexture(aspectId, true)));
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(pMatrix, (float) x, (float) y + size, (float) 0).color(aspect.getColor().getValue()).uv(0, 1).endVertex();
        bufferbuilder.vertex(pMatrix, (float) x + size, (float) y + size, (float) 0).color(aspect.getColor().getValue()).uv(1, 1).endVertex();
        bufferbuilder.vertex(pMatrix, (float) x + size, (float) y, (float) 0).color(aspect.getColor().getValue()).uv(1, 0).endVertex();
        bufferbuilder.vertex(pMatrix, (float) x, (float) y, (float) 0).color(aspect.getColor().getValue()).uv(0, 0).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        stack.popPose();

        if(amount > 1) {
            stack.pushPose();
            stack.translate(0, 0, 400);
            stack.scale(.5F, .5F, 1);
            String text = String.valueOf(amount);
            int xOffset = size - Minecraft.getInstance().font.width(text) / 2;
            int yOffset = size - Minecraft.getInstance().font.lineHeight / 2;
            RenderUtils.drawOutlineFont(stack, (x + xOffset) * 2, (y + yOffset) * 2, text, TextColor.fromRgb(0xFFFFFF), TextColor.fromRgb(0x000000));
            stack.popPose();
        }
    }
}
