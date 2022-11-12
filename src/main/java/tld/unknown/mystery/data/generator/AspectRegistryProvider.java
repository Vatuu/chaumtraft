package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
        //GLASS
        bothTag(Tags.Blocks.GLASS, new AspectList().add(Aspects.CRYSTAL, 6));
        bothTag(Tags.Blocks.GLASS_PANES, new AspectList().add(Aspects.CRYSTAL, 1));
        bothTag(Tags.Blocks.GLASS_TINTED, new AspectList().add(Aspects.DARKNESS, 3));
        bothTag(Tags.Blocks.GLASS_SILICA, new AspectList().add(Aspects.CRAFT, 3));

        // ORES
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

        //Can I do this?? I Dont Think so
        bothTag(UncommonTags.ORES_GLOWSTONE, new AspectList().add(Aspects.METAL, 10).add(Aspects.CHANGE, 5));
        bothTag(UncommonTags.ORES_TIN, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.CRYSTAL, 5));
        bothTag(UncommonTags.ORES_SILVER, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.DESIRE, 5));
        bothTag(UncommonTags.ORES_LEAD, new AspectList().add(Aspects.METAL, 10).add(Aspects.EARTH, 5).add(Aspects.ORDER, 5));
        bothTag(UncommonTags.ORES_BRONZE, new AspectList().add(Aspects.METAL, 10).add(Aspects.TOOL, 5));
        bothTag(UncommonTags.ORES_URANIUM, new AspectList().add(Aspects.METAL, 10).add(Aspects.DEATH, 5).add(Aspects.POWER, 10));

        //ORGANICS
        bothTag(Tags.Items.SEEDS, new AspectList().add(Aspects.PLANT, 5).add(Aspects.LIFE, 1));


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
        itemTag(UncommonTags.ITEM_COAL, new AspectList().add(Aspects.POWER, 10).add(Aspects.FIRE, 10));

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
