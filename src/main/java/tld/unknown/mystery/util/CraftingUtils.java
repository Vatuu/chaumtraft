package tld.unknown.mystery.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tld.unknown.mystery.api.capabilities.IResearchCapability;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.data.recipes.AlchemyRecipe;
import tld.unknown.mystery.data.recipes.ArcaneCraftingRecipe;
import tld.unknown.mystery.registries.ChaumtraftRecipes;

import java.util.Optional;

public final class CraftingUtils {

    public static Optional<AlchemyRecipe> findAlchemyRecipe(Level p, AspectList list, ItemStack stack, IResearchCapability research) {
        return p.getRecipeManager().getAllRecipesFor(ChaumtraftRecipes.ALCHEMY.type()).stream()
                .filter(r -> r.isValid(list, stack, research))
                .findFirst();
    }

    public static Optional<ArcaneCraftingRecipe> findArcaneCraftingRecipe(Level p, SimpleContainer craftingSlots, IResearchCapability research) {
        return p.getRecipeManager().getAllRecipesFor(ChaumtraftRecipes.ARCANE_CRAFTING.type()).stream()
                .filter(r -> r.isValidPattern(craftingSlots))
                .findFirst();
    }
}
