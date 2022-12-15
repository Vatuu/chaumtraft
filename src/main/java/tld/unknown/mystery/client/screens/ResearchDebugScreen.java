package tld.unknown.mystery.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.client.rendering.RenderTypes;
import tld.unknown.mystery.client.screens.widgets.DataIndexWidget;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.data.research.ResearchCategory;
import tld.unknown.mystery.data.research.ResearchEntry;

import java.awt.*;

public class ResearchDebugScreen extends Screen {

    private static final int LIST_WIDTH = 200;

    private DataIndexWidget<ResearchCategory> categories;
    private DataIndexWidget<ResearchEntry> entries;

    private ResearchCategory category;

    public ResearchDebugScreen() {
        super(Component.literal("debug_research"));
    }

    @Override
    protected void init() {
        this.categories = addWidget(new DataIndexWidget<>(ChaumtraftData.RESEARCH_CATEGORY, width / 2 - (int)(LIST_WIDTH * 1.5F) - 100, 75, LIST_WIDTH, height - 125, 20));
        this.entries = addWidget(new DataIndexWidget<>(ChaumtraftData.RESEARCH_ENTRIES, width / 2 - (int)(LIST_WIDTH * 0.5F) - 100, 75, LIST_WIDTH, height - 125, 20));

        this.categories.update(id -> true, (id, c) -> ResearchCategory.getName(id), (id, c) -> c.icon());
    }

    @Override
    public void tick() {
        if(category != categories.getCurrent()) {
            category = categories.getCurrent();
            if(category != null) {
                ResourceLocation loc = ChaumtraftData.RESEARCH_CATEGORY.getIdentifier(category);
                entries.update(id -> id.getPath().startsWith(loc.getPath()), (id, c) -> ResearchEntry.getName(id), (id, c) -> c.displayProperties().icons().get(0));
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        categories.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        entries.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }
}
