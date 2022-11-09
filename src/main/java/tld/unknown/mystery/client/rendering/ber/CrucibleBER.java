package tld.unknown.mystery.client.rendering.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import tld.unknown.mystery.blocks.entities.CrucibleBlockEntity;
import tld.unknown.mystery.client.rendering.RenderHelper;
import tld.unknown.mystery.util.FluidHelper;
import tld.unknown.mystery.util.simple.SimpleBER;

public class CrucibleBER extends SimpleBER<CrucibleBlockEntity> {

    public CrucibleBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CrucibleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();

        int heat = pBlockEntity.getHeat();
        renderNametag(pPoseStack, pBufferSource, 1, FluidHelper.serializeTankStatus(pBlockEntity), pPackedLight);
        renderNametag(pPoseStack, pBufferSource, .75F, String.format("Heat: %d/200 [%s]", heat, heat >= 150 ? "o" : "x"), pPackedLight);
        pPoseStack.translate(0, 1, 0);
        pPoseStack.mulPose(Quaternion.fromXYZ((float)Math.toRadians(-90), 0, 0));
        pPoseStack.scale(2, 2, 2);
        RenderHelper.renderFluidTexture(pPoseStack, pBufferSource, pBlockEntity.getFluidInTank(0), 1, 1, 1, pPackedLight);

        pPoseStack.popPose();
    }
}
