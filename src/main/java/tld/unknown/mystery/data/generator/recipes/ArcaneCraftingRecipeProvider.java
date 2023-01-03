package tld.unknown.mystery.data.generator.recipes;

import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.data.recipes.AlchemyRecipe;
import tld.unknown.mystery.data.recipes.ArcaneCraftingRecipe;
import tld.unknown.mystery.util.codec.data.CodecDataProvider;
import tld.unknown.mystery.util.codec.recipes.CodecRecipeSerializer;

import java.util.List;
import java.util.Map;

public class ArcaneCraftingRecipeProvider extends CodecDataProvider<ArcaneCraftingRecipe> {

    public ArcaneCraftingRecipeProvider(DataGenerator generator) {
        super(generator, "ArcaneCraftingRecipes", "recipes/arcane_crafting", ArcaneCraftingRecipe.CODEC);
    }

    @Override
    protected void createEntries() {
        recipe(Chaumtraft.id("debug"), new RecipeBuilder(new ItemStack(Items.DIAMOND, 2))
                .setPattern(
                        "#+#",
                        "+ +",
                        "#+#",
                        Map.of('#', Ingredient.of(new ItemStack(Items.STICK)), '+', Ingredient.of(new ItemStack(Items.GOLD_INGOT))))
                .setCrystalCost(Map.of(Aspect.Primal.AIR, 1))
                .setVisCost(20)
                .setRequiredResearch(ChaumtraftIDs.Research.UNLOCK_DEBUG));
    }

    @Override
    protected void processJson(JsonElement element) {
        element.getAsJsonObject().addProperty("type", ChaumtraftIDs.Recipes.TYPE_ARCANE_CRAFTING.toString());
    }

    private void recipe(ResourceLocation id, RecipeBuilder builder) {
        register(id, builder.build());
    }

    private static final class RecipeBuilder {

        private ItemStack result;
        private ResourceLocation requiredResearch = Chaumtraft.id("none");
        private CodecRecipeSerializer.CraftingGrid grid;
        private Map<Aspect.Primal, Integer> crystals;
        private int visCost;

        public RecipeBuilder(ItemStack result) {
            this.result = result;
        }

        public RecipeBuilder setRequiredResearch(ResourceLocation id) {
            requiredResearch = id;
            return this;
        }

        public RecipeBuilder setPattern(String one, String two, String three, Map<Character, Ingredient> keys) {
            grid = new CodecRecipeSerializer.CraftingGrid(3, 3, List.of(one, two, three), keys);
            return this;
        }

        public RecipeBuilder setCrystalCost(Map<Aspect.Primal, Integer> crystalCost) {
            crystals = crystalCost;
            return this;
        }

        public RecipeBuilder setVisCost(int cost) {
            this.visCost = cost;
            return this;
        }

        public ArcaneCraftingRecipe build() {
            return new ArcaneCraftingRecipe(requiredResearch, grid, crystals, visCost, result);
        }
    }
}