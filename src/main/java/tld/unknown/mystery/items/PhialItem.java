package tld.unknown.mystery.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.registries.ChaumtraftItems;

public class PhialItem extends AbstractAspectItem {

    public PhialItem() {
        super(new Properties().stacksTo(64), true);
    }

    @Override
    public AspectList getAspects(ItemStack stack) {
        return hasContent(stack) ? new AspectList().add(getContent(stack), 10) : new AspectList();
    }
}
