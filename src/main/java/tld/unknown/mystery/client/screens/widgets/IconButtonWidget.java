package tld.unknown.mystery.client.screens.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import tld.unknown.mystery.util.IconTexture;

public class IconButtonWidget extends Button {

    private final IconTexture icon;

    public IconButtonWidget(int pX, int pY, int pWidth, int pHeight, IconTexture icon, Component pMessage, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress);
        this.icon = icon;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
        int yOffset = (height - 16) / 2;
        icon.render(pPoseStack, this.x + 4, this.y + yOffset, 32, 32, .5F);
    }
}
