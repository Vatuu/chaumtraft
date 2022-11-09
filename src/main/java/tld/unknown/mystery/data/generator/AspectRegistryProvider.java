package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import tld.unknown.mystery.data.aspects.AspectList;
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
        // Ores
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

        itemTag(Tags.Items.SLIMEBALLS, new AspectList().add(Aspects.WATER, 5).add(Aspects.LIFE, 5).add(Aspects.ALCHEMY, 1));
    }

    private void item(AspectList list, Item... item) {
        for(Item i : item) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(i);
            if(itemId == null)
                continue;
            ResourceLocation loc = new ResourceLocation(itemId.getNamespace(), "items/" + itemId.getPath());
            register(loc, list);
        }
    }

    private void block(AspectList list, Block... blocks) {
        for(Block b : blocks) {
            ResourceLocation blockID = ForgeRegistries.BLOCKS.getKey(b);
            if(blockID == null)
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

    private void blockTag( TagKey<?> tag, AspectList list) {
        ResourceLocation loc = new ResourceLocation(tag.location().getNamespace(), "blocks/tags/" + tag.location().getPath());
        register(loc, list);
    }

    private void bothTag(TagKey<?> tags, AspectList list) {
        blockTag(tags, list);
        itemTag(tags, list);
    }
}
