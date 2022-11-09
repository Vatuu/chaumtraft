package tld.unknown.mystery.util.codec.data;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.network.NetworkEvent;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.networking.Packet;
import tld.unknown.mystery.networking.ChaumtraftNetworking;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class CodecDataManager<T> extends SimpleJsonResourceReloadListener implements Packet.ClientboundPlay {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Getter
    private final boolean syncable;

    private final String name;
    private final Codec<T> codec;
    private final UnboundedMapCodec<ResourceLocation, T> syncCodec;
    protected final HashBiMap<ResourceLocation, T> values;
    private final ImmutableMap<ResourceLocation, T> defaults;
    private final Predicate<ResourceLocation> test;

    public CodecDataManager(Codec<T> codec, String name, String directory, boolean syncable, ImmutableMap<ResourceLocation, T> defaults, Predicate<ResourceLocation> test) {
        super(GSON, directory);
        this.name = name;
        this.codec = codec;
        this.syncable = syncable;
        this.syncCodec = Codec.unboundedMap(ResourceLocation.CODEC, codec);
        this.values = HashBiMap.create();
        this.test = test;

        if(defaults != null)
            this.defaults = ImmutableMap.copyOf(defaults);
        else
            this.defaults = ImmutableMap.of();

        if(syncable)
            ChaumtraftNetworking.registerPacket(CodecDataManager.class, () -> this);
    }

    public Set<ResourceLocation> getKeys() {
        return values.keySet();
    }

    public T get(ResourceLocation loc) {
        return values.get(loc);
    }

    public Optional<T> getOptional(ResourceLocation loc) {
        if(entryPresent(loc))
            return Optional.of(get(loc));
        return Optional.empty();
    }

    public ResourceLocation getIdentifier(T value) {
        return values.inverse().get(value);
    }

    public boolean entryPresent(ResourceLocation location) {
        return values.containsKey(location);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        values.clear();
        values.putAll(defaults);
        pObject.forEach((id, json) -> {
            if(test.test(id)) {
                codec.decode(JsonOps.INSTANCE, json).get()
                        .ifLeft(result -> values.put(id, result.getFirst()))
                        .ifRight(partial -> Chaumtraft.error("Failed to parse %s for resource \"%s\": %s", this.name, id, partial.message()));
            }
        });
        postApply();
        Chaumtraft.info("Loaded %d entries for %s data.", values.size(), name);
    }

    protected void postApply() { }

    @Override
    public String getName() { return this.name; }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        syncCodec.encodeStart(NbtOps.INSTANCE, values).get()
                .ifLeft(nbt -> {
                    CompoundTag c = new CompoundTag();
                    c.put("values", nbt);
                    buffer.writeNbt(c);
                })
                .ifRight(partial -> Chaumtraft.error("Failed to encode sync data for manager %s: %s", this.name, partial.message()));
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        syncCodec.decode(NbtOps.INSTANCE, buffer.readNbt()).get()
                .ifLeft(nbt -> {
                    values.clear();
                    values.putAll(nbt.getFirst());
                })
                .ifRight(partial -> Chaumtraft.error("Failed to decode sync data for manager %s: %s", this.name, partial.message()));
        postApply();
    }

    @Override
    public void handle(NetworkEvent.Context context) { }

    public void printRegistry() {
        values.forEach((k, v) -> Chaumtraft.info("%s: %s", k, v));
    }
}
