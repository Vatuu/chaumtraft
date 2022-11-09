package tld.unknown.mystery.client.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;

import java.io.IOException;
import java.util.function.Function;

public final class RenderTypes {

    public static RenderType sdf(ResourceLocation texture) {
        return SdfRenderType.RENDER_TYPE.apply(texture);
    }

    public static ShaderInstance bindSdf(ResourceLocation texture) {
        RenderSystem.enableTexture();
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        texturemanager.getTexture(texture).setFilter(true, false);
        RenderSystem.setShaderTexture(0, texture);
        return SdfRenderType.instance;
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static final class SdfRenderType extends RenderType {

        public static final Function<ResourceLocation, RenderType> RENDER_TYPE = Util.memoize(SdfRenderType::create);

        private static ShaderInstance instance;
        private static final ShaderStateShard SDF_SHADER_STATE = new ShaderStateShard(() -> instance);

        public SdfRenderType(String a, VertexFormat b, VertexFormat.Mode c, int d, boolean e, boolean f, Runnable g, Runnable h) {
            super(a, b, c, d, e, f, g, h);
            throw new IllegalStateException("Render Types should not be constructed.");
        }

        private static RenderType create(ResourceLocation loc) {
            CompositeState state = RenderType.CompositeState.builder()
                    .setShaderState(SDF_SHADER_STATE)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(new RenderStateShard.TextureStateShard(loc, true, false))
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setOverlayState(RenderStateShard.NO_OVERLAY)
                    .createCompositeState(false);
            return create("mystery_sdf", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, false, state);
        }

        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent e) throws IOException {
            e.registerShader(new ShaderInstance(e.getResourceManager(), Chaumtraft.id("rendertype_sdf"), DefaultVertexFormat.POSITION_COLOR_TEX), shaderInstance -> SdfRenderType.instance = shaderInstance);
        }
    }
}
