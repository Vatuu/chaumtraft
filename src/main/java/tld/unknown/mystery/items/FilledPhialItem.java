package tld.unknown.mystery.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.api.ChaumtraftIDs;

public class FilledPhialItem extends AbstractAspectItem {

    public FilledPhialItem() {
        super(new Properties().stacksTo(64).tab(Chaumtraft.CREATIVE_TAB));
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.translatable(getDescriptionId(), Aspect.getName(hasAspect(pStack) ? getEssentiaAspect(pStack) : ChaumtraftIDs.Aspects.UNKNOWN, false, true));
    }
}
