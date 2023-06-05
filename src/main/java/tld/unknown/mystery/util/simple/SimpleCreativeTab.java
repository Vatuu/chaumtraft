package tld.unknown.mystery.util.simple;

import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;

import java.util.ArrayList;
import java.util.List;

public final class SimpleCreativeTab {

    private final ResourceLocation id, icon;

    private final List<RegistryObject<? extends ItemLike>> contents;

    private CreativeModeTab tab;

    public SimpleCreativeTab(ResourceLocation id, ResourceLocation icon) {
        this.id = id;
        this.icon = icon;
        this.contents = new ArrayList<>();
    }

    public void construct(CreativeModeTabEvent.Register e) {
        Item i = ForgeRegistries.ITEMS.getValue(this.icon);
        this.tab = e.registerCreativeModeTab(id, b -> b
                .title(Component.translatable("itemGroup." + id.getNamespace() + "." + id.getPath()))
                .icon(() -> i != null ? i.getDefaultInstance() : Items.SEA_PICKLE.getDefaultInstance()));
    }

    public void populate(CreativeModeTabEvent.BuildContents e) {
        if(e.getTab() != tab) {
            return;
        }

        contents.forEach(ro -> {
            if(ro.get().asItem() instanceof MultipleRegistrar m) {
                NonNullList<ItemStack> stacks = NonNullList.create();
                m.getCreativeTabEntries(stacks);
                e.acceptAll(stacks, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            } else {
                e.accept(ro, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        });
    }

    public void register(RegistryObject<? extends ItemLike> object) {
        this.contents.add(object);
    }

    public interface MultipleRegistrar {
        void getCreativeTabEntries(NonNullList<ItemStack> items);
    }
}
