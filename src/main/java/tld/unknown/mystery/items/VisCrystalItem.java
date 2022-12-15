package tld.unknown.mystery.items;

import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.data.aspects.AspectList;

public class VisCrystalItem extends AbstractAspectItem {

    public VisCrystalItem() {
        super(new Properties().tab(Chaumtraft.CREATIVE_TAB).stacksTo(64), false);
    }

    @Override
    public AspectList getAspects(ItemStack stack) {
        return hasContent(stack) ? new AspectList().add(getContent(stack), 1) : new AspectList();
    }
}
