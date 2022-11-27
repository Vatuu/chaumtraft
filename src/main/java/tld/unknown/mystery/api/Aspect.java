package tld.unknown.mystery.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Data;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.data.aspects.PrimalAspects;

import java.util.List;

@Data
public class Aspect {

    public static final Aspect UNKNOWN = new Aspect(TextColor.parseColor("#FFFFFF"), Lists.newArrayList());

    private final TextColor color;
    private final List<ResourceLocation> origin;

    public static Component getName(ResourceLocation id, boolean pureColor, boolean primalColor) {
        MutableComponent c = Component.translatable("aspect." + id.getNamespace() + "." + id.getPath());
        Aspect a = ChaumtraftData.ASPECTS.getOptional(id).orElse(Aspect.UNKNOWN);
        if(pureColor) {
            return c.setStyle(Style.EMPTY.withColor(a.getColor()));
        } else if(primalColor && a instanceof PrimalAspects pa) {
            return c.withStyle(pa.getFormatting());
        } else {
            return c;
        }
    }

    public static ResourceLocation getTexture(ResourceLocation id, boolean sdf) {
        return new ResourceLocation(id.getNamespace(), "textures/aspects/" + id.getPath() + (sdf ? "_sdf.png" : ".png"));
    }

    public static Codec<Aspect> CODEC = RecordCodecBuilder.create(i -> i.group(
            TextColor.CODEC.fieldOf("color").forGetter(Aspect::getColor),
            ResourceLocation.CODEC.listOf().optionalFieldOf("origin", Lists.newArrayList()).forGetter(Aspect::getOrigin)
    ).apply(i, Aspect::new));
}
