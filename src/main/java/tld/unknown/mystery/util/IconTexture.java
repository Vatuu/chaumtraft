package tld.unknown.mystery.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import tld.unknown.mystery.api.ChaumtraftIDs;

@Getter
public class IconTexture {

    private final boolean isItem;
    private final ResourceLocation location;
    private ItemStack stack;

    public IconTexture(ResourceLocation location) {
        if(location.getPath().endsWith(".png")) {
            if(Minecraft.getInstance() == null)
                this.location = location;
            else if(Minecraft.getInstance().getResourceManager().getResource(location).isPresent())
                this.location = location;
            else
                this.location = ChaumtraftIDs.Textures.UNKNOWN;
            this.isItem = false;
        } else {
            if(ForgeRegistries.ITEMS.containsKey(location)) {
                this.location = location;
                this.isItem = true;
            } else {
                this.location = ChaumtraftIDs.Textures.UNKNOWN;
                this.isItem = false;
            }
        }
    }

    public void render(PoseStack poseStack, int x, int y, int width, int height, float scale) {
        render(poseStack, x, y, 0, 0, width, height, width, height, width, height, scale);
    }

    public void render(PoseStack poseStack, int x, int y, int u, int v, int uSize, int vSize, int texWidth, int texHeight, int width, int height, float scale) {
        poseStack.pushPose();
        if(isItem) {
            if(stack == null)
                stack = new ItemStack(ForgeRegistries.ITEMS.getValue(location));
            Minecraft mc = Minecraft.getInstance();
            mc.getItemRenderer().renderGuiItem(poseStack, stack, x, y);
        } else {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, location);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            GuiComponent.blit(poseStack, x, y, (int)(width * scale), (int)(height * scale), u, v, uSize, vSize, texWidth, texHeight);
        }
        poseStack.popPose();
    }
}
