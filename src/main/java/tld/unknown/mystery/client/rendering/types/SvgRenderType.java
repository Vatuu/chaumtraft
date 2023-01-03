package tld.unknown.mystery.client.rendering.types;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.nanovg.NanoSVG;
import tld.unknown.mystery.Chaumtraft;

public class SvgRenderType extends RenderType {

    public SvgRenderType(Runnable pSetupState, Runnable pClearState) {
        super(Chaumtraft.MOD_ID + "_svg", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true, pSetupState, pClearState);
    }

    public static class SvgTextureShard extends RenderStateShard.EmptyTextureStateShard {

        private final ResourceLocation texture;

        public SvgTextureShard(ResourceLocation texture) {
            super(SETUP, CLEANUP);
            this.texture = texture;
        }

        private static final Runnable SETUP = () -> {

        };

        private static final Runnable CLEANUP = () -> {

        };

        private void createTexture(float scaleX, float scaleY) {
            long rasterizer = NanoSVG.nsvgCreateRasterizer();

        }
    }
}
