package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.*;

public final class RenderHelper {

    public static TextureAtlasSprite getFluidSprite(FluidStack b) {
        ResourceLocation texture = IClientFluidTypeExtensions.of(b.getFluid()).getStillTexture(b);
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
    }

    public static int getFluidTint(FluidStack b) {
        return IClientFluidTypeExtensions.of(b.getFluid()).getTintColor();
    }

    public static void renderFluidTexture(PoseStack stack, MultiBufferSource source, FluidStack fluid, int r, int g, int b, int a, int packedLight) {
        TextureAtlasSprite sprite = getFluidSprite(fluid);
        Color tint = new Color(getFluidTint(fluid), true);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        VertexConsumer consumer = source.getBuffer(RenderType.cutout());
        buildQuadVertices(consumer, stack.last().pose(), r * tint.getRed(), g * tint.getGreen(), b * tint.getBlue(), a * tint.getAlpha(), sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), true, packedLight);
    }

    private static void buildQuadVertices(VertexConsumer consumer, Matrix4f modelMatrix, int r, int g, int b, int a, float minU, float minV, float maxU, float maxV, boolean applyLight, int packedLight) {
        fillVertex(consumer, modelMatrix, new Vector3f(0, 0, 0), r, g, b, a, maxU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(1, 0, 0), r, g, b, a, minU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(1, 1, 0), r, g, b, a, minU, minV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(0, 1, 0), r, g, b, a, maxU, minV, applyLight, packedLight);
    }

    private static void fillVertex(VertexConsumer consumer, Matrix4f modelMatrix, Vector3f pos, int r, int g, int b, int a, float u, float v, boolean applyLight, int packedLight) {
        consumer.vertex(modelMatrix, pos.x(), pos.y(), pos.z()).color(r, g, b, a).uv(u, v);
        if(applyLight) {
            consumer.uv2(packedLight);
        }
        consumer.normal(0, 0, 1).endVertex();
    }

    public static void drawOutlineFont(PoseStack stack, int x, int y, String text, TextColor color, TextColor outlineColor) {
        Font font = Minecraft.getInstance().font;
        stack.pushPose();
        font.draw(stack, text, x + 1, y, outlineColor.getValue());
        font.draw(stack, text, x - 1, y, outlineColor.getValue());
        font.draw(stack, text, x, y + 1, outlineColor.getValue());
        font.draw(stack, text, x, y - 1, outlineColor.getValue());
        font.draw(stack, text, x, y, color.getValue());
        stack.popPose();
    }
}
