package tld.unknown.mystery.data.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.util.codec.EnumCodec;

public record ResearchKnowledge(
        Type type,
        ResourceLocation category,
        int amount) {

    public static ResearchKnowledge observation(ResourceLocation type, int amount) {
        return new ResearchKnowledge(Type.OBSERVATION, type, amount);
    }

    public static ResearchKnowledge theory(ResourceLocation type, int amount) {
        return new ResearchKnowledge(Type.THEORY, type, amount);
    }

    public static final Codec<ResearchKnowledge> CODEC = RecordCodecBuilder.create(i -> i.group(
            new EnumCodec<>(ResearchKnowledge.Type.class).fieldOf("type").forGetter(ResearchKnowledge::type),
            ResourceLocation.CODEC.fieldOf("category").forGetter(ResearchKnowledge::category),
            Codec.INT.fieldOf("amount").forGetter(ResearchKnowledge::amount)
    ).apply(i, ResearchKnowledge::new));

    public enum Type implements EnumCodec.Values {
        OBSERVATION("observation"),
        THEORY("theory");

        private final String serialized;

        Type(String serialized) {
            this.serialized = serialized;
        }

        @Override
        public String getSerializedName() {
            return serialized;
        }
    }
}
