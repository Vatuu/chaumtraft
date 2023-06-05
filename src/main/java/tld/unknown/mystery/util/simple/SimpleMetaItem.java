package tld.unknown.mystery.util.simple;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Set;

public abstract class SimpleMetaItem<T> extends Item implements SimpleCreativeTab.MultipleRegistrar {

    public static ClampedItemPropertyFunction HAS_META_GETTER = (stack, world, entity, seed) -> hasContent(stack) ? 1F : 0F;

    private static final String NAME_FILLED_SUFFIX = ".filled";
    private static final String CONTENT_KEY = "Content";

    private final boolean registerEmpty;

    public static boolean hasContent(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(CONTENT_KEY, Tag.TAG_STRING);
    }

    public SimpleMetaItem(Properties pProperties, boolean registerEmpty) {
        super(pProperties);
        this.registerEmpty = registerEmpty;
    }

    public T getContent(ItemStack stack) {
        return hasContent(stack) ? read(stack.getTag().getString(CONTENT_KEY)) : null;
    }

    protected abstract T read(String value);

    protected abstract String write(T value);

    protected abstract Set<T> getValidValues();

    protected abstract Component getContentNameFiller(T content);

    @Override
    public Component getName(ItemStack pStack) {
        if(hasContent(pStack)) {
            return Component.translatable(getDescriptionId() + NAME_FILLED_SUFFIX, getContentNameFiller(getContent(pStack)));
        } else {
            return super.getName(pStack);
        }
    }

    @Override
    public void getCreativeTabEntries(NonNullList<ItemStack> items) {
        if(registerEmpty) {
            items.add(new ItemStack(this));
        }

        getValidValues().forEach(s -> {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putString(CONTENT_KEY, write(s));
            items.add(stack);
        });
    }
}
