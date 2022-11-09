package tld.unknown.mystery.data.aspects;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Map;

public class AspectList {

    private final Map<ResourceLocation, Short> aspects;

    public AspectList() {
        aspects = Maps.newHashMap();
    }

    public AspectList(Map<ResourceLocation, Short> aspects) {
        this.aspects = aspects;
    }

    public void merge(AspectList list) {
        list.forEachAspect((aspect, amount, i) -> {
            aspects.computeIfPresent(aspect, (rl, a) -> (short)(a + amount));
            aspects.putIfAbsent(aspect, amount);
        });
    }

    public AspectList add(ResourceLocation aspect, int amount) {
        aspects.compute(aspect, (rl, a) -> a == null ? (short)amount : (short)(amount + a));
        return this;
    }

    public AspectList remove(ResourceLocation aspect) {
        aspects.remove(aspect);
        return this;
    }

    public AspectList remove(ResourceLocation aspect, int amount) {
        aspects.computeIfPresent(aspect, (rl, a) -> a <= amount ? null : (short)(a - amount));
        return this;
    }

    public int aspectTypeCount() {
        return aspects.size();
    }

    public void forEachAspect(TriConsumer<ResourceLocation, Short, Integer> consumer) {
        int i = 0;
        for(Map.Entry<ResourceLocation, Short> entry : aspects.entrySet()) {
            consumer.accept(entry.getKey(), entry.getValue(), i++);
        }
    }

    public boolean isEmpty() {
        return aspects.isEmpty();
    }

    public void clear() {
        aspects.clear();
    }

    public AspectList clone() {
        return new AspectList(aspects);
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        forEachAspect((rl, s, i) -> tag.putShort(rl.toString(), s));
        return tag;
    }

    public AspectList fromNBT(CompoundTag tag) {
        clear();
        tag.getAllKeys().forEach(s -> add(ResourceLocation.tryParse(s), tag.getShort(s)));
        return this;
    }

    public static final Codec<AspectList> CODEC = Codec.unboundedMap(ResourceLocation.CODEC, Codec.SHORT).xmap(AspectList::new, al -> al.aspects);
}
