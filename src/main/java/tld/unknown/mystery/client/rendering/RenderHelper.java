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
import net.minecraft.core.Direction;
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
        drawFace(Direction.NORTH, consumer, stack.last().pose(), new Vector3f(0, 0, 0), new Vector3f(1, 1, 0), getFluidTint(fluid), 0, 0, 1, 1, true, packedLight);
    }

    private static void buildQuadVertices(VertexConsumer consumer, Matrix4f modelMatrix, int r, int g, int b, int a, float minU, float minV, float maxU, float maxV, boolean applyLight, int packedLight) {
        fillVertex(consumer, modelMatrix, 0, 0, 0, colour, maxU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, 1, 0, 0, colour, minU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, 1, 1, 0, colour, minU, minV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, 0, 1, 0, colour, maxU, minV, applyLight, packedLight);
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

    public static void drawCuboid(VertexConsumer consumer, Matrix4f modelMatrix, Vector3f min, Vector3f max, int colour, int minU, int minV, int light) {
        drawFace(Direction.NORTH, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.x() - min.x()), minV + (max.y() - min.y()), true, light);
        drawFace(Direction.SOUTH, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.x() - min.x()), minV + (max.y() - min.y()), true, light);
        drawFace(Direction.EAST, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.z() - min.z()), minV + (max.y() - min.y()), true, light);
        drawFace(Direction.WEST, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.z() - min.z()), minV + (max.y() - min.y()), true, light);
        drawFace(Direction.UP, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.x() - min.x()), minV + (max.z() - min.z()), true, light);
        drawFace(Direction.DOWN, consumer, modelMatrix, min, max, colour, minU, minV, minU + (max.x() - min.x()), minV + (max.z() - min.z()), true, light);
    }

    private static void drawFace(Direction dir, VertexConsumer consumer, Matrix4f modelMatrix, Vector3f min, Vector3f max, int colour, float minU, float minV, float maxU, float maxV, boolean applyLight, int light) {
        switch(dir) {
            case UP -> {
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, minU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, minU, minV, applyLight, light);
            }
            case DOWN -> {
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, minU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, minU, minV, applyLight, light);
            }
            case NORTH -> {
                fillVertex(consumer, modelMatrix, min.x(), min.y(), max.z(), colour, minU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), max.z(), colour, minU, maxV, applyLight, light);
            }
            case SOUTH -> {
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, minU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, minU, maxV, applyLight, light);
            }
            case EAST -> {
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, minU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, minU, maxV, applyLight, light);
            }
            case WEST -> {
                fillVertex(consumer, modelMatrix, min.x(), min.y(), max.z(), colour, minU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, maxU, minV, applyLight, light);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, maxU, maxV, applyLight, light);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), max.z(), colour, minU, maxV, applyLight, light);
            }
        }
    }

    private static void fillVertex(VertexConsumer consumer, Matrix4f modelMatrix, float x, float y, float z, int colour, float u, float v, boolean applyLight, int packedLight) {
        consumer.vertex(modelMatrix, x, y, z).color(r, g, b, a).uv(u, v);
        if(applyLight) {
            consumer.uv2(packedLight);
        }
        consumer.normal(0, 0, 1).endVertex();
    }
}
