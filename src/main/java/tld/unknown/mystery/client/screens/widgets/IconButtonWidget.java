package tld.unknown.mystery.client.screens.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import tld.unknown.mystery.util.IconTexture;

import java.util.function.Supplier;

public class IconButtonWidget extends Button {

    private final IconTexture icon;

    public IconButtonWidget(int pX, int pY, int pWidth, int pHeight, IconTexture icon, Component pMessage, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, Supplier::get);
        this.icon = icon;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pPoseStack, pMouseX, pMouseY, pPartialTick);
        int yOffset = (height - 16) / 2;
        icon.render(pPoseStack, getX() + 4, getY() + yOffset, 32, 32, .5F);
    }
}
