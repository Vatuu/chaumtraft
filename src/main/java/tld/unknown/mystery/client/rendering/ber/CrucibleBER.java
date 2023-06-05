package tld.unknown.mystery.client.rendering.ber;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Quaternionf;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.entities.CrucibleBlockEntity;
import tld.unknown.mystery.client.rendering.RenderHelper;
import tld.unknown.mystery.util.FluidHelper;
import tld.unknown.mystery.util.simple.SimpleBER;

public class CrucibleBER extends SimpleBER<CrucibleBlockEntity> {

    private static final float FLUID_START = 1 / 16F * 4;
    private static final float FLUID_HEIGHT = 1 / 16F * 9;
    private static final float ASPECT_HEIGHT = 1 / 16F * 3;

    private static final Quaternionf QUATERNION = Axis.YN.rotationDegrees(90);

    public CrucibleBER(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(CrucibleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        if(Chaumtraft.isDev()) {
            renderNametag(pPoseStack, pBufferSource, 1, FluidHelper.serializeTankStatus(pBlockEntity), pPackedLight);
            renderNametag(pPoseStack, pBufferSource, .75F, pBlockEntity.isCooking() ? "Hot" : "Cool", pPackedLight);
        }
        float fluidHeight = pBlockEntity.getFluidPercentage();
        if(fluidHeight > 0) {
            float aspectHeight = pBlockEntity.getAspectPercentage();
            pPoseStack.translate(0, FLUID_START + (FLUID_HEIGHT * fluidHeight) + (ASPECT_HEIGHT * aspectHeight) + (fluidHeight + aspectHeight >= 2 ? 0.0001 : 0), 1);
            pPoseStack.mulPose(QUATERNION);
            RenderHelper.renderFluidTexture(pPoseStack, pBufferSource, pBlockEntity.getFluidInTank(0), 1, 1, 1, 1, pPackedLight);
        }
        pPoseStack.popPose();
    }
}
