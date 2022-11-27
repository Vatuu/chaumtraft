package tld.unknown.mystery.items;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.ChaumtraftData;

public abstract class AbstractAspectItem extends Item {

    private static final String ASPECT_TAG = "Aspect";
    private static final String NAME_FILLED_SUFFIX = ".filled";

    public static ClampedItemPropertyFunction HAS_ASPECT_GETTER = (stack, world, entity, seed) -> hasAspect(stack) ? 1F : 0F;

    public AbstractAspectItem(Properties pProperties) {
        super(pProperties);
    }

    public static boolean hasAspect(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(ASPECT_TAG, Tag.TAG_STRING);
    }

    public static ResourceLocation getEssentiaAspect(ItemStack stack) {
        return ResourceLocation.tryParse(stack.getTag().getString(ASPECT_TAG));
    }

    @Override
    public Component getName(ItemStack pStack) {
        if(hasAspect(pStack)) {
            ResourceLocation id = getEssentiaAspect(pStack);
            return Component.translatable(getDescriptionId() + NAME_FILLED_SUFFIX, Aspect.getName(ChaumtraftData.ASPECTS.entryPresent(id) ? id : ChaumtraftIDs.Aspects.UNKNOWN, false, true));
        } else {
            return super.getName(pStack);
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        pItems.add(new ItemStack(this));
        ChaumtraftData.ASPECTS.getKeys().forEach(a -> {
            if(a != ChaumtraftIDs.Aspects.UNKNOWN) {
                ItemStack stack = new ItemStack(this);
                stack.getOrCreateTag().putString(ASPECT_TAG, a.toString());
                pItems.add(stack);
            }
        });
    }
}
