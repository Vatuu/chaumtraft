package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.util.UncommonTags;
import tld.unknown.mystery.util.codec.data.CodecDataProvider;

import java.util.Arrays;

import static tld.unknown.mystery.api.ChaumtraftIDs.Aspects;

public class AspectRegistryProvider extends CodecDataProvider<AspectList> {

    public AspectRegistryProvider(DataGenerator generator) {
        super(generator, "AspectRegistry", "aspect_registry", AspectList.CODEC);
    }

    @Override
    protected void createEntries() {
        vanilla();
    }

    private void vanilla() {
        //------------------------------------------------------[BLOCKS]------------------------------------------------------------------------
        //STONES
        bothTag(Tags.Blocks.STONE, new AspectList().add(Aspects.CRYSTAL, 6));
        bothTag(Tags.Items.END_STONES, new AspectList().add(Aspects.EARTH, 5).add(Aspects.DARKNESS, 5));
        //GLASS
        bothTag(Tags.Blocks.GLASS, new AspectList().add(Aspects.CRYSTAL, 6));
        bothTag(Tags.Blocks.GLASS_PANES, new AspectList().add(Aspects.CRYSTAL, 1));
        bothTag(Tags.Blocks.GLASS_TINTED, new AspectList().add(Aspects.DARKNESS, 3));
        bothTag(Tags.Blocks.GLASS_SILICA, new AspectList().add(Aspects.CRAFT, 3));
        //ORES
        bothTag(Tags.Blocks.ORES, new AspectList().add(Aspects.EARTH, 5));
        bothTag(Tags.Blocks.ORES_LAPIS, new AspectList().add(Aspects.SENSE, 15));
        bothTag(Tags.Blocks.ORES_DIAMOND, new AspectList().add(Aspects.DESIRE, 15).add(Aspects.CRYSTAL, 15));
        bothTag(Tags.Blocks.ORES_REDSTONE, new AspectList().add(Aspects.POWER, 15));
        bothTag(Tags.Blocks.ORES_EMERALD, new AspectList().add(Aspects.DESIRE, 10).add(Aspects.CRYSTAL, 15));
        bothTag(Tags.Blocks.ORES_QUARTZ, new AspectList().add(Aspects.CRYSTAL, 10));
        bothTag(Tags.Blocks.ORES_IRON, new AspectList().add(Aspects.METAL, 15));
        bothTag(Tags.Blocks.ORES_COAL, new AspectList().add(Aspects.POWER, 15).add(Aspects.FIRE, 15));
        bothTag(Tags.Blocks.ORES_GOLD, new AspectList().add(Aspects.DESIRE, 10).add(Aspects.METAL, 10));
        bothTag(Tags.Blocks.ORES_COPPER, new AspectList().add(Aspects.METAL, 10).add(Aspects.CHANGE, 5));
        bothTag(UncommonTags.ORES_TIN, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.CRYSTAL, 5));
        bothTag(UncommonTags.ORES_SILVER, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.DESIRE, 5));
        bothTag(UncommonTags.ORES_LEAD, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.ORDER, 5));
        bothTag(UncommonTags.ORES_BRONZE, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5));
        bothTag(UncommonTags.ORES_URANIUM, new AspectList().add(Aspects.METAL, 10).add(Aspects.DEATH, 5).add(Aspects.POWER, 10));
        //ORGANICS
        bothTag(Tags.Items.SEEDS, new AspectList().add(Aspects.PLANT, 5).add(Aspects.LIFE, 1));//SAND - LIKE
        bothTag(Tags.Items.SAND, new AspectList().add(Aspects.EARTH, 5).add(Aspects.DESTRUCTION, 5));
        bothTag(Tags.Items.GRAVEL, new AspectList().add(Aspects.EARTH, 5).add(Aspects.DESTRUCTION, 2));
        bothTag(Tags.Items.SANDSTONE, new AspectList().add(Aspects.ORDER, 2));
        //COBBLESTONE - LIKE
        bothTag(Tags.Items.COBBLESTONE, new AspectList().add(Aspects.ORDER, 2).add(Aspects.DESTRUCTION, 2));
        bothTag(Tags.Items.COBBLESTONE_DEEPSLATE, new AspectList().add(Aspects.ORDER, 2).add(Aspects.DARKNESS, 2));
        bothTag(Tags.Items.COBBLESTONE_INFESTED, new AspectList().add(Aspects.ORDER, 2).add(Aspects.CREATURE, 2));
        bothTag(Tags.Items.COBBLESTONE_MOSSY, new AspectList().add(Aspects.EARTH, 2).add(Aspects.PLANT, 2).add(Aspects.DESTRUCTION, 2));
        //NETHER - LIKE
        bothTag(Tags.Items.NETHERRACK, new AspectList().add(Aspects.EARTH, 2).add(Aspects.FIRE, 2));
        bothTag(Tags.Items.OBSIDIAN, new AspectList().add(Aspects.EARTH, 5).add(Aspects.FIRE, 5).add(Aspects.DARKNESS, 5));




        //------------------------------------------------------[NON-TAGGED]------------------------------------------------------------------------
        //NETHER
        both(new AspectList().add(Aspects.EARTH, 3).add(Aspects.TRAP, 2).add(Aspects.SPIRIT, 4), Blocks.SOUL_SAND);



        //STONES
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.STONE_BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.CRACKED_STONE_BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.PLANT, 2), Blocks.MOSSY_STONE_BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.CHISELED_STONE_BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.STONE);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.STONE_BRICKS);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.GRANITE);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.ANDESITE);
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.DESTRUCTION, 2), Blocks.COBBLESTONE);
        both(new AspectList().add(Aspects.DARKNESS, 25).add(Aspects.DESTRUCTION, 25).add(Aspects.EMPTY, 25).add(Aspects.EARTH, 5), Blocks.BEDROCK);
        //DIRTS
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.DIRT);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.DIRT_PATH);
        both(new AspectList().add(Aspects.EARTH, 5), Blocks.COARSE_DIRT);
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.PLANT, 2), Blocks.ROOTED_DIRT);
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.PLANT, 1), Blocks.GRASS);
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.PLANT, 1), Blocks.FARMLAND);
        //COOKABLE-CLAY-LIKE
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BLACK_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BLUE_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BROWN_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.CYAN_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.GRAY_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.GREEN_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIGHT_BLUE_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIGHT_GRAY_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIME_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.MAGENTA_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.ORANGE_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.PINK_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.PURPLE_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.RED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.WHITE_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.YELLOW_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BLACK_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BLUE_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.BROWN_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.CYAN_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.GRAY_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.GREEN_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.LIME_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.MAGENTA_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.ORANGE_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.PINK_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.PURPLE_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.RED_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.WHITE_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.FIRE, 5).add(Aspects.SENSE, 1), Blocks.YELLOW_GLAZED_TERRACOTTA);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BLACK_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BLUE_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BROWN_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.CYAN_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.GRAY_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.GREEN_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIGHT_BLUE_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIGHT_GRAY_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIME_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.MAGENTA_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.ORANGE_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.PINK_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.PURPLE_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.RED_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.WHITE_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.YELLOW_CONCRETE);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BLACK_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BLUE_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.BROWN_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.CYAN_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.GRAY_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.GREEN_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIGHT_BLUE_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIGHT_GRAY_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.LIME_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.MAGENTA_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.ORANGE_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.PINK_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.PURPLE_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.RED_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.WHITE_CONCRETE_POWDER);
        both(new AspectList().add(Aspects.ORDER, 5).add(Aspects.WATER, 1), Blocks.YELLOW_CONCRETE_POWDER);
        //ORGANIC?
        both(new AspectList().add(Aspects.EARTH, 5).add(Aspects.PLANT, 1).add(Aspects.FLUX, 3), Blocks.MYCELIUM);
        both(new AspectList().add(Aspects.POWER, 10).add(Aspects.FIRE, 10), Blocks.CLAY);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.ACACIA_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.BIRCH_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.DARK_OAK_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.JUNGLE_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.OAK_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.SPRUCE_SAPLING);
        both(new AspectList().add(Aspects.LIFE, 5).add(Aspects.PLANT, 15), Blocks.MANGROVE_PROPAGULE);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.ACACIA_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.BIRCH_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.DARK_OAK_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.JUNGLE_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.OAK_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.SPRUCE_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.MANGROVE_LEAVES);
        both(new AspectList().add(Aspects.PLANT, 5), Blocks.MANGROVE_ROOTS);

        //COAL
        item(new AspectList().add(Aspects.POWER, 10).add(Aspects.FIRE, 10), Items.COAL);
        both(new AspectList().add(Aspects.POWER, 90).add(Aspects.FIRE, 90), Blocks.COAL_BLOCK);
        item(new AspectList().add(Aspects.POWER, 10).add(Aspects.FIRE, 10), Items.CHARCOAL);
        both(new AspectList().add(Aspects.SENSE, 10).add(Aspects.LIGHT, 10), Blocks.GLOWSTONE);
        //WOODS
        item(new AspectList().add(Aspects.PLANT, 20), Items.OAK_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.SPRUCE_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.BIRCH_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.JUNGLE_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.ACACIA_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.DARK_OAK_LOG);
        item(new AspectList().add(Aspects.PLANT, 20), Items.MANGROVE_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_OAK_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_SPRUCE_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_BIRCH_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_JUNGLE_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_ACACIA_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_DARK_OAK_LOG);
        item(new AspectList().add(Aspects.PLANT, 15).add(Aspects.CHANGE, 5), Items.STRIPPED_MANGROVE_LOG);





        //------------------------------------------------------[ITEMS]------------------------------------------------------------------------
        //PLANTS
        bothTag(Tags.Items.DYES, new AspectList().add(Aspects.SENSE, 5).add(Aspects.CHANGE, 5).add(Aspects.LIFE, 5));

        //GEMS
        itemTag(Tags.Items.GEMS, new AspectList().add(Aspects.CRYSTAL, 5));
        itemTag(Tags.Items.GEMS_DIAMOND, new AspectList().add(Aspects.CRYSTAL, 10).add(Aspects.DESIRE, 15));
        itemTag(Tags.Items.GEMS_EMERALD, new AspectList().add(Aspects.CRYSTAL, 10).add(Aspects.DESIRE, 10));
        itemTag(Tags.Items.GEMS_QUARTZ, new AspectList().add(Aspects.CRYSTAL, 0)); // 0 Because the value is already 5
        itemTag(Tags.Items.GEMS_LAPIS, new AspectList().add(Aspects.SENSE, 15));
        itemTag(UncommonTags.GEMS_RUBY, new AspectList().add(Aspects.CRYSTAL, 10).add(Aspects.DESIRE, 10));
        itemTag(UncommonTags.GEMS_SAPPHIRE, new AspectList().add(Aspects.CRYSTAL, 10).add(Aspects.DESIRE, 10));
        itemTag(UncommonTags.GEMS_GREEN_SAPPHIRE, new AspectList().add(Aspects.CRYSTAL, 10).add(Aspects.DESIRE, 10));
        //DUSTS
        itemTag(Tags.Items.DUSTS_REDSTONE, new AspectList().add(Aspects.POWER, 10));
        itemTag(Tags.Items.DUSTS_GLOWSTONE, new AspectList().add(Aspects.SENSE, 5).add(Aspects.LIGHT, 10));
        itemTag(UncommonTags.DUSTS_IRON, new AspectList().add(Aspects.METAL, 15).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_GOLD, new AspectList().add(Aspects.METAL, 10).add(Aspects.DESIRE, 10).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_COPPER, new AspectList().add(Aspects.METAL, 10).add(Aspects.CHANGE, 5).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_TIN, new AspectList().add(Aspects.METAL, 10).add(Aspects.CRYSTAL, 5).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_SILVER, new AspectList().add(Aspects.METAL, 10).add(Aspects.DESIRE, 5).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_LEAD, new AspectList().add(Aspects.METAL, 10).add(Aspects.ORDER, 5).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_BRONZE, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5).add(Aspects.DESTRUCTION, 1));
        itemTag(UncommonTags.DUSTS_BRASS, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5).add(Aspects.DESTRUCTION, 1));
        //CLUSTERS
        itemTag(UncommonTags.CLUSTERS_IRON, new AspectList().add(Aspects.METAL, 15).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_GOLD, new AspectList().add(Aspects.METAL, 10).add(Aspects.DESIRE, 10).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_COPPER, new AspectList().add(Aspects.METAL, 10).add(Aspects.CHANGE, 10));
        itemTag(UncommonTags.CLUSTERS_TIN, new AspectList().add(Aspects.METAL, 10).add(Aspects.CRYSTAL, 5).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_SILVER, new AspectList().add(Aspects.METAL, 10).add(Aspects.DESIRE, 5).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_LEAD, new AspectList().add(Aspects.METAL, 10).add(Aspects.ORDER, 5).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_BRONZE, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5).add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.CLUSTERS_BRASS, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5).add(Aspects.CHANGE, 5));
        //INGOTS
        itemTag(Tags.Items.INGOTS, new AspectList().add(Aspects.METAL, 10)); // INGOTS
        itemTag(Tags.Items.INGOTS_IRON, new AspectList().add(Aspects.METAL, 5));
        itemTag(Tags.Items.INGOTS_GOLD, new AspectList().add(Aspects.DESIRE, 10));
        itemTag(Tags.Items.INGOTS_COPPER, new AspectList().add(Aspects.CHANGE, 5));
        itemTag(UncommonTags.INGOTS_TIN, new AspectList().add(Aspects.METAL, 10).add(Aspects.CRYSTAL, 5));
        itemTag(UncommonTags.INGOTS_SILVER, new AspectList().add(Aspects.METAL, 10).add(Aspects.DESIRE, 5));
        itemTag(UncommonTags.INGOTS_LEAD, new AspectList().add(Aspects.METAL, 10).add(Aspects.ORDER, 5));
        itemTag(UncommonTags.INGOTS_BRASS, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5));
        itemTag(UncommonTags.INGOTS_URANIUM, new AspectList().add(Aspects.METAL, 10).add(Aspects.DEATH, 5).add(Aspects.POWER, 5));
        itemTag(UncommonTags.INGOTS_STEEL, new AspectList().add(Aspects.METAL, 11).add(Aspects.ORDER, 5));
        //MISC ITEMS
        itemTag(Tags.Items.SLIMEBALLS, new AspectList().add(Aspects.WATER, 5).add(Aspects.LIFE, 5).add(Aspects.ALCHEMY, 1));
        itemTag(UncommonTags.ITEM_RUBBER, new AspectList().add(Aspects.WATER, 5).add(Aspects.MOVEMENT, 5).add(Aspects.TOOL, 5));
    }

    private void item(AspectList list, Item... item) {
        for (Item i : item) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(i);
            if (itemId == null)
                continue;
            ResourceLocation loc = new ResourceLocation(itemId.getNamespace(), "items/" + itemId.getPath());
            register(loc, list);
        }
    }

    private void block(AspectList list, Block... blocks) {
        for (Block b : blocks) {
            ResourceLocation blockID = ForgeRegistries.BLOCKS.getKey(b);
            if (blockID == null)
                continue;
            ResourceLocation loc = new ResourceLocation(blockID.getNamespace(), "blocks/" + blockID.getPath());
            register(loc, list);
        }
    }

    private void both(AspectList list, Block... blocks) {
        block(list, blocks);
        item(list, Arrays.stream(blocks).map(Block::asItem).distinct().toArray(Item[]::new));
    }

    private void itemTag(TagKey<?> tag, AspectList list) {
        ResourceLocation loc = new ResourceLocation(tag.location().getNamespace(), "items/tags/" + tag.location().getPath());
        register(loc, list);
    }

    private void blockTag(TagKey<?> tag, AspectList list) {
        ResourceLocation loc = new ResourceLocation(tag.location().getNamespace(), "blocks/tags/" + tag.location().getPath());
        register(loc, list);
    }

    private void bothTag(TagKey<?> tags, AspectList list) {
        blockTag(tags, list);
        itemTag(tags, list);
    }
}
