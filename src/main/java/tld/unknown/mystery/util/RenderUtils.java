package tld.unknown.mystery.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TextColor;

public final class RenderUtils {

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
