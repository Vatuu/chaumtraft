package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public final class RenderHelper {

    public static TextureAtlasSprite getFluidSprite(FluidStack b) {
        ResourceLocation texture = IClientFluidTypeExtensions.of(b.getFluid()).getStillTexture(b);
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
    }

    public static void renderFluidTexture(PoseStack stack, MultiBufferSource source, FluidStack fluid, int r, int g, int b, int packedLight) {
        TextureAtlasSprite sprite = getFluidSprite(fluid);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        VertexConsumer consumer = source.getBuffer(RenderType.translucent());
        buildQuadVertices(consumer, stack.last().pose(), r, g, b, sprite.getU0(), sprite.getV1(), sprite.getU1(), sprite.getV1(), true, packedLight);
    }

    private static void buildQuadVertices(VertexConsumer consumer, Matrix4f modelMatrix, int r, int g, int b, float minU, float minV, float maxU, float maxV, boolean applyLight, int packedLight) {
        fillVertex(consumer, modelMatrix, new Vector3f(0, 0, 0), r, g, b, maxU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(1, 0, 0), r, g, b, minU, maxV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(1, 1, 0), r, g, b, minU, minV, applyLight, packedLight);
        fillVertex(consumer, modelMatrix, new Vector3f(0, 1, 0), r, g, b, maxU, minV, applyLight, packedLight);
    }

    private static void fillVertex(VertexConsumer consumer, Matrix4f modelMatrix, Vector3f pos,  int r, int g, int b, float u, float v, boolean applyLight, int packedLight) {
        consumer.vertex(modelMatrix, pos.x(), pos.y(), pos.z()).color(r, g, b, 1).uv(u, v);
        if(applyLight) {
            consumer.uv2(packedLight);
        }
        consumer.normal(0, 0, 1).endVertex();
    }
}
