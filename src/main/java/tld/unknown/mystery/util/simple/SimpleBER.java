package tld.unknown.mystery.util.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

@AllArgsConstructor
public abstract class SimpleBER<T extends BlockEntity> implements BlockEntityRenderer<T> {

    protected final BlockEntityRendererProvider.Context context;

    protected void renderNametag(PoseStack stack, MultiBufferSource source, float y, String text, int light) {
        stack.pushPose();

        stack.translate(.5D, .5D + y, .5D);
        stack.mulPose(context.getEntityRenderer().cameraOrientation());
        stack.scale(-.025F, -.025F, .025F);

        int bgColor = (int)(Minecraft.getInstance().options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
        Matrix4f matrix4f = stack.last().pose();
        Font font = context.getFont();
        float centerOffset = (float)(-font.width(text) / 2);
        font.drawInBatch(text, centerOffset, 0, 553648127, false, matrix4f, source, true, bgColor, light);
        font.drawInBatch(text, centerOffset, 0, -1, false, matrix4f, source, false, 0, light);

        stack.popPose();
    }
}
