package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import tld.unknown.mystery.Chaumtraft;

public final class RenderHelper {

    public static final CuboidRenderer CUBOID_RENDERER = new CuboidRenderer();

    public static TextureAtlasSprite getFluidSprite(FluidStack b) {
        ResourceLocation texture = IClientFluidTypeExtensions.of(b.getFluid()).getStillTexture(b);
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
    }

    public static int getFluidTint(FluidStack b) {
        return IClientFluidTypeExtensions.of(b.getFluid()).getTintColor();
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

    public static void drawFace(Direction dir, VertexConsumer consumer, Matrix4f modelMatrix, Vector3f min, Vector3f max, int colour, float minU, float minV, float maxU, float maxV, boolean applyLight, int light, boolean applyOverlay, int overlay) {
        switch(dir) {
            case UP -> {
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), max.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
            }
            case DOWN -> {
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), max.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
            }
            case NORTH -> {
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
            }
            case SOUTH -> {
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), max.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), max.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
            }
            case EAST -> {
                fillVertex(consumer, modelMatrix, max.x(), min.y(), max.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), min.y(), min.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), min.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, max.x(), max.y(), max.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
            }
            case WEST -> {
                fillVertex(consumer, modelMatrix, min.x(), min.y(), max.z(), colour, maxU, minV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), max.z(), colour, maxU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), max.y(), min.z(), colour, minU, maxV, applyLight, light, applyOverlay, overlay);
                fillVertex(consumer, modelMatrix, min.x(), min.y(), min.z(), colour, minU, minV, applyLight, light, applyOverlay, overlay);
            }
        }
    }

    public static boolean debugIsLookingAtBlock(BlockPos pos) {
        if(Chaumtraft.isDev() && Minecraft.getInstance().player.isCrouching() && Minecraft.getInstance().hitResult instanceof BlockHitResult hit) {
            return hit.getBlockPos().equals(pos);
        }
        return false;
    }

    private static void fillVertex(VertexConsumer consumer, Matrix4f modelMatrix, float x, float y, float z, int colour, float u, float v, boolean applyLight, int packedLight, boolean applyOverlay, int overlay) {
        consumer.vertex(modelMatrix, x, y, z).color((colour >> 16) & 0xFF, (colour >> 8), colour & 0xFF, (colour >> 24)).uv(u, v);
        if(applyOverlay) {
            consumer.overlayCoords(overlay);
        }
        if(applyLight) {
            consumer.uv2(packedLight);
        }
        consumer.normal(0, 0, 1).endVertex();
    }
}
