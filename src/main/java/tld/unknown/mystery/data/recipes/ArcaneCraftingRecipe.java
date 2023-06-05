package tld.unknown.mystery.data.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.api.capabilities.IResearchCapability;
import tld.unknown.mystery.registries.ChaumtraftRecipes;
import tld.unknown.mystery.util.codec.Codecs;
import tld.unknown.mystery.util.codec.EnumCodec;
import tld.unknown.mystery.util.codec.recipes.CodecRecipe;
import tld.unknown.mystery.util.codec.recipes.CodecRecipeSerializer;

import java.util.Map;

@Getter
public class ArcaneCraftingRecipe extends CodecRecipe<ArcaneCraftingRecipe> {

    private static final ResourceLocation RESEARCH_NONE = Chaumtraft.id("none");

    private final ResourceLocation requiredResearch;
    private final CodecRecipeSerializer.CraftingGrid grid;
    private final Map<Aspect.Primal, Integer> crystals;
    private final int visAmount;
    private final ItemStack result;


    public ArcaneCraftingRecipe(ResourceLocation requiredResearch, CodecRecipeSerializer.CraftingGrid grid, Map<Aspect.Primal, Integer> crystals, int visAmount, ItemStack result) {
        super(ChaumtraftRecipes.ARCANE_CRAFTING, result);
        this.requiredResearch = requiredResearch;
        this.grid = grid;
        this.crystals = crystals;
        this.visAmount = visAmount;
        this.result = result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(Container pContainer) {
        return super.getRemainingItems(pContainer);
    }

    public boolean isValidPattern(SimpleContainer craftingSlot) {
        return grid.verify(craftingSlot, 6) && crystals.keySet().stream().allMatch(p -> !craftingSlot.getItem(p.ordinal()).isEmpty() && craftingSlot.getItem(p.ordinal()).getCount() >= crystals.get(p));
    }

    public boolean playerKnowsResearch(IResearchCapability cap) {
        return this.requiredResearch.equals(RESEARCH_NONE) || cap.hasResearch(this.requiredResearch);
    }

    public static final Codec<ArcaneCraftingRecipe> CODEC = RecordCodecBuilder.create(i -> i.group(
            ResourceLocation.CODEC.fieldOf("requiredResearch").forGetter(ArcaneCraftingRecipe::getRequiredResearch),
            CodecRecipeSerializer.CraftingGrid.dimensionedCodec(3, 3).fieldOf("grid").forGetter(ArcaneCraftingRecipe::getGrid),
            Codec.unboundedMap(new EnumCodec<>(Aspect.Primal.class), Codec.INT).fieldOf("crystals").forGetter(ArcaneCraftingRecipe::getCrystals),
            Codec.INT.fieldOf("visCost").forGetter(ArcaneCraftingRecipe::getVisAmount),
            Codecs.ITEM_STACK.fieldOf("result").forGetter(ArcaneCraftingRecipe::getResult)
    ).apply(i, ArcaneCraftingRecipe::new));
}
