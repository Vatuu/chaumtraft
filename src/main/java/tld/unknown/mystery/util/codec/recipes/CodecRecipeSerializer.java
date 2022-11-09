package tld.unknown.mystery.util.codec.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import lombok.AllArgsConstructor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class CodecRecipeSerializer<T extends CodecRecipe<?>> implements RecipeSerializer<T> {

    private final Codec<T> codec;

    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return get(pRecipeId, JsonOps.INSTANCE, pSerializedRecipe);
    }

    @Override
    public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        CompoundTag tag = pBuffer.readAnySizeNbt();
        return get(pRecipeId, NbtOps.INSTANCE, tag);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        DataResult<Tag> tag = codec.encode(pRecipe, NbtOps.INSTANCE, null);
        if(tag.error().isPresent()) {
            throw new JsonParseException("Unable to serialize recipe to JSON: " + tag.error().get().message());
        } else {
            pBuffer.writeNbt((CompoundTag) tag.get().left().get());
        }
    }

    private <U> T get(ResourceLocation recipeId, DynamicOps<U> ops, U input) {
        DataResult<Pair<T, U>> result = codec.decode(ops, input);
        if(result.error().isPresent()) {
            throw new JsonParseException("Unable to parse recipe from JSON: " + result.error().get().message());
        } else {
            T recipe = result.result().get().getFirst();
            recipe.setId(recipeId);
            return recipe;
        }
    }
}
