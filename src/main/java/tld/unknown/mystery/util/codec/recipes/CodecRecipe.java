package tld.unknown.mystery.util.codec.recipes;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import tld.unknown.mystery.registries.ChaumtraftRecipes;

public abstract class CodecRecipe<T extends CodecRecipe<T>> implements Recipe<Container> {

    @Getter @Setter
    private ResourceLocation id;

    private final ItemStack result;
    private final ChaumtraftRecipes.RecipeObject<T> registryEntry;

    public CodecRecipe(ChaumtraftRecipes.RecipeObject<T> registry, ItemStack result) {
        this.registryEntry = registry;
        this.result = result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return registryEntry.serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return registryEntry.type();
    }
}
