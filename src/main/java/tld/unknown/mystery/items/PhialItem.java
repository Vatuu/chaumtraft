package tld.unknown.mystery.items;

import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.data.aspects.AspectList;

public class PhialItem extends AbstractAspectItem {

    public PhialItem() {
        super(new Properties().stacksTo(64).tab(Chaumtraft.CREATIVE_TAB), true);
    }

    @Override
    public AspectList getAspects(ItemStack stack) {
        return hasContent(stack) ? new AspectList().add(getContent(stack), 10) : new AspectList();
    }
}
