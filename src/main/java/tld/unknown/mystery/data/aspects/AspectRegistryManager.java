package tld.unknown.mystery.data.aspects;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.aspects.AspectContainerItem;
import tld.unknown.mystery.util.codec.data.CodecDataManager;

import java.util.List;
import java.util.Map;

public class AspectRegistryManager extends CodecDataManager<AspectList> {

    private static final List<String> VALID_TYPES = ImmutableList.of("items", "blocks", "entities");

    private static final Map<ResourceLocation, AspectList> ITEM_CACHE = Maps.newHashMap();

    private final Map<TagKey<Item>, AspectList> itemTags = Maps.newHashMap();
    private final Map<TagKey<Block>, AspectList> blockTags = Maps.newHashMap();
    private final Map<ResourceLocation, AspectList> items = Maps.newHashMap();
    private final Map<ResourceLocation, AspectList> blocks = Maps.newHashMap();

    public AspectRegistryManager() {
        super(AspectList.CODEC, "AspectRegistry", "aspect_registry", true, null, rl -> VALID_TYPES.stream().anyMatch(t -> t.equals(rl.getPath().split("/")[0])));
    }

    public boolean hasAspects(ItemStack stack) {
        return !getAspects(stack).isEmpty();
    }

    @Override
    protected void postApply() {
        ITEM_CACHE.clear();
        itemTags.clear();
        blockTags.clear();
        items.clear();
        blocks.clear();
        values.keySet().forEach(rl -> {
            if(rl.getPath().startsWith("items/")) {
                if(rl.getPath().startsWith("items/tags/")) {
                    ResourceLocation tag = new ResourceLocation(rl.getNamespace(), rl.getPath().replace("items/tags/", ""));
                    itemTags.put(TagKey.create(Registries.ITEM, tag), values.get(rl));
                } else {
                    ResourceLocation tag = new ResourceLocation(rl.getNamespace(), rl.getPath().replace("items/", ""));
                    items.put(tag, values.get(rl));
                }
            } else if(rl.getPath().startsWith("blocks/")) {
                if(rl.getPath().startsWith("blocks/tags/")) {
                    ResourceLocation tag = new ResourceLocation(rl.getNamespace(), rl.getPath().replace("blocks/tags/", ""));
                    blockTags.put(TagKey.create(Registries.BLOCK, tag), values.get(rl));
                } else {
                    ResourceLocation tag = new ResourceLocation(rl.getNamespace(), rl.getPath().replace("blocks/", ""));
                    blocks.put(tag, values.get(rl));
                }
            }
        });
    }

    public AspectList getAspects(ItemStack stack) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if(stack.getItem() instanceof AspectContainerItem a) {
            return a.getAspects(stack);
        }else if(ITEM_CACHE.containsKey(itemId)) {
            return ITEM_CACHE.get(itemId).clone();
        } else {
            AspectList list = determineAspects(itemId, stack.getItem());
            ITEM_CACHE.put(itemId, list);
            return list;
        }
    }

    private AspectList determineAspects(ResourceLocation itemId, Item item) {
        AspectList list = new AspectList();

        if(items.containsKey(itemId)) {
            list.merge(items.get(itemId));
        }

        ForgeRegistries.ITEMS.tags().getReverseTag(item).orElseThrow(IllegalStateException::new).getTagKeys().forEach(tag -> {
            if(itemTags.containsKey(tag)) {
                list.merge(itemTags.get(tag));
            }
        });

        if(list.isEmpty()) {
            //TODO: Recipe Walking
        }

        return list;
    }

    @Override
    public void printRegistry() {
        super.printRegistry();
        Chaumtraft.info("Item Tags: ");
        itemTags.forEach((t, al) -> Chaumtraft.info("\t- %s", t.location()));
        Chaumtraft.info("Block Tags: ");
        blockTags.forEach((t, al) -> Chaumtraft.info("\t- %s", t.location()));
        Chaumtraft.info("Items: ");
        items.forEach((t, al) -> Chaumtraft.info("\t- %s", t));
        Chaumtraft.info("Blocks: ");
        blocks.forEach((t, al) -> Chaumtraft.info("\t- %s", t));
    }
}
