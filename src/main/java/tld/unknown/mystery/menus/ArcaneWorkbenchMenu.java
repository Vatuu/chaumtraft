package tld.unknown.mystery.menus;

import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import tld.unknown.mystery.registries.client.ChaumtraftMenus;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu {

    public ArcaneWorkbenchMenu(int pContainerId) {
        super(ChaumtraftMenus.ARCANE_WORKBENCH.get(), pContainerId);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
