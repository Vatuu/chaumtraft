package tld.unknown.mystery.data.aspects;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.api.ChaumtraftIDs;

@Getter
public class PrimalAspect extends Aspect {

    public static final ImmutableMap<ResourceLocation, Aspect> ALL = new ImmutableMap.Builder<ResourceLocation, Aspect>()
            .put(ChaumtraftIDs.Aspects.ORDER, new PrimalAspect("#D5D4EC", ChatFormatting.GRAY))
            .put(ChaumtraftIDs.Aspects.DESTRUCTION, new PrimalAspect("#404040", ChatFormatting.DARK_GRAY))
            .put(ChaumtraftIDs.Aspects.EARTH, new PrimalAspect("#56C000", ChatFormatting.DARK_GREEN))
            .put(ChaumtraftIDs.Aspects.WATER, new PrimalAspect("#3CD4FC", ChatFormatting.DARK_AQUA))
            .put(ChaumtraftIDs.Aspects.AIR, new PrimalAspect("#FFFF7E", ChatFormatting.YELLOW))
            .put(ChaumtraftIDs.Aspects.FIRE, new PrimalAspect("#FF5A01", ChatFormatting.RED)).build();

    private final ChatFormatting formatting;

    private PrimalAspect(String color, ChatFormatting formatting) {
        super(TextColor.parseColor(color), Lists.newArrayList());
        this.formatting = formatting;
    }
}
