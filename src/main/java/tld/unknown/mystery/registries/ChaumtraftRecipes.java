package tld.unknown.mystery.registries;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.recipes.AlchemyRecipe;
import tld.unknown.mystery.util.codec.recipes.CodecRecipeSerializer;

import java.util.function.Supplier;

public final class ChaumtraftRecipes {

    private static final DeferredRegister<RecipeType<?>> REGISTRY_TYPE = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Chaumtraft.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> REGISTRY_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static final RecipeObject<AlchemyRecipe> ALCHEMY = register(ChaumtraftIDs.Recipes.TYPE_ALCHEMY, () -> new CodecRecipeSerializer<>(AlchemyRecipe.CODEC));

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY_TYPE.register(bus);
        REGISTRY_SERIALIZER.register(bus);
    }

    private static <T extends Recipe<?>> RecipeObject<T> register(ResourceLocation location, Supplier<RecipeSerializer<T>> serializer) {
        RegistryObject<RecipeType<T>> type = REGISTRY_TYPE.register(location.getPath(), () -> RecipeType.simple(location));
        RegistryObject<RecipeSerializer<T>> serial = REGISTRY_SERIALIZER.register(location.getPath(), serializer);
        return new RecipeObject<>(type, serial);
    }

    @RequiredArgsConstructor
    public static class RecipeObject<T extends Recipe<?>> {

        private final RegistryObject<RecipeType<T>> typeObject;
        private final RegistryObject<RecipeSerializer<T>> serializerObject;

        public RecipeType<T> type() {
            return typeObject.get();
        }

        public RecipeSerializer<T> serializer() {
            return serializerObject.get();
        }
    }
}
