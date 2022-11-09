package tld.unknown.mystery.util.codec.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.Chaumtraft;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class CodecDataProvider<T> implements DataProvider {

    private final String name;
    private final DataGenerator.PathProvider path;
    private final Codec<T> codec;
    private final Map<ResourceLocation, T> entries = new HashMap<>();

    public CodecDataProvider(DataGenerator generator, String name, String path, Codec<T> codec) {
        this.name = name;
        this.path = generator.createPathProvider(DataGenerator.Target.DATA_PACK, path);
        this.codec = codec;
        createEntries();
    }

    protected abstract void createEntries();

    @Override
    public void run(CachedOutput pOutput) {
        entries.forEach((id, obj) -> {
            Path p = path.json(id);
            DataResult<JsonElement> result = JsonOps.INSTANCE.withEncoder(codec).apply(obj);
            result.get().ifLeft(json -> {
                try {
                    processJson(json);
                    Chaumtraft.info("Generating %s for %s", name, id);
                    DataProvider.saveStable(pOutput, json, p);
                } catch(IOException e) {
                    Chaumtraft.error(e, "Failed to save data object!");
                }
            }).ifRight(partial -> Chaumtraft.error("Failed to save data object: " + partial.message()));
        });
    }

    protected void processJson(JsonElement element) { }

    protected void register(ResourceLocation path, T object) {
        if(entries.containsKey(path))
            throw new IllegalStateException("Tried to register duplicate data at " + path + " for data type " + name + "!");
        entries.put(path, object);
    }

    @Override
    public String getName() { return name; }
}
