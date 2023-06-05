package tld.unknown.mystery.client.rendering.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AllArgsConstructor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import tld.unknown.mystery.client.rendering.AspectRenderer;
import tld.unknown.mystery.data.aspects.AspectList;

@AllArgsConstructor
public class AspectTooltip implements ClientTooltipComponent {

    private static final int SIZE = 16;
    private static final int SPACING = 2;

    private final AspectList aspects;

    @Override
    public int getHeight() {
        return SIZE + 3;
    }

    @Override
    public int getWidth(Font pFont) {
        return SIZE * aspects.aspectCount() + SPACING * Math.max(aspects.aspectCount() - 1, 0);
    }

    @Override
    public void renderImage(Font font, int mouseX, int mouseY, PoseStack poseStack, ItemRenderer renderer) {
        aspects.indexedForEach((aspect, amount, index) -> {
            int offset = index * (SIZE + SPACING);
            AspectRenderer.renderAspectOverlay(poseStack, aspect, mouseX + offset, mouseY + 1, SIZE, amount, false);
        });
    }

    public record Data(AspectList aspects) implements TooltipComponent { }
}
