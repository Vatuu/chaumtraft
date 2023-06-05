package tld.unknown.mystery.items;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.api.aspects.AspectContainerItem;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.util.simple.SimpleMetaItem;

import java.util.Set;

public abstract class AbstractAspectItem extends SimpleMetaItem<ResourceLocation> implements AspectContainerItem {

    public AbstractAspectItem(Properties pProperties, boolean includeEmpty) {
        super(pProperties, includeEmpty);
    }

    @Override
    protected ResourceLocation read(String value) {
        return ResourceLocation.tryParse(value);
    }

    @Override
    protected String write(ResourceLocation value) {
        return value.toString();
    }

    @Override
    protected Set<ResourceLocation> getValidValues() {
        Set<ResourceLocation> keys = ChaumtraftData.ASPECTS.getKeys();
        keys.remove(ChaumtraftIDs.Aspects.UNKNOWN);
        return keys;
    }

    @Override
    protected Component getContentNameFiller(ResourceLocation content) {
        return Aspect.getName(content, false, true);
    }

    public static boolean hasAspect(ItemStack stack, ResourceLocation aspect) {
        return stack.getItem() instanceof AbstractAspectItem i && i.getContent(stack).equals(aspect);
    }
}
