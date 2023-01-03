package tld.unknown.mystery.menus;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.api.IResearchCapability;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;
import tld.unknown.mystery.capabilities.ResearchCapability;
import tld.unknown.mystery.data.recipes.ArcaneCraftingRecipe;
import tld.unknown.mystery.items.AbstractAspectItem;
import tld.unknown.mystery.registries.client.ChaumtraftMenus;
import tld.unknown.mystery.util.CraftingUtils;
import tld.unknown.mystery.util.PredicateSlot;

import java.util.Optional;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu {

    private static final int SLOT_CHAOS = 0;
    private static final int SLOT_ORDER = 1;
    private static final int SLOT_WATER = 2;
    private static final int SLOT_AIR = 3;
    private static final int SLOT_FIRE = 4;
    private static final int SLOT_EARTH = 5;

    private final CraftingContainer craftSlots = new CraftingContainer(this, 3, 3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final SimpleContainer crystalSlots;

    private final Player player;
    private final ContainerLevelAccess access;

    public ArcaneWorkbenchMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, new SimpleContainer(6), playerInv.player, ContainerLevelAccess.NULL);
    }

    public ArcaneWorkbenchMenu(int pContainerId, Inventory playerInv, SimpleContainer crystals, Player player, ContainerLevelAccess access) {
        super(ChaumtraftMenus.ARCANE_WORKBENCH.get(), pContainerId);
        this.player = player;
        this.access = access;
        this.crystalSlots = crystals;

        populateCrystalSlots();

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 3; ++x) {
                this.addSlot(new Slot(this.craftSlots, x + y * 3, 33 + x * 24, 6 + y * 24));
            }
        }
        this.addSlot(new ResultSlot(player, this.craftSlots, this.resultSlots, 0, 153, 30));

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 9 + x * 18, 117 + y * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInv, i, 9 + i * 18, 175));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(pIndex);

        if (quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();
            if (pIndex == 0) {
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                this.slots.get(pIndex).onQuickCraft(rawStack, quickMovedStack);
            } else if (pIndex >= 5 && pIndex < 41) {
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    if (pIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }

            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }

            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void slotsChanged(Container pInventory) {
        this.access.execute((level, pos) -> slotChangedCraftingGrid(level, pos, this.player));
    }

    private void slotChangedCraftingGrid(Level pLevel, BlockPos pos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            ServerPlayer serverplayer = (ServerPlayer)pPlayer;
            ItemStack itemstack = ItemStack.EMPTY;
            IResearchCapability cap = player.getCapability(ChaumtraftCapabilities.RESEARCH).orElse(new ResearchCapability());
            Optional<ArcaneCraftingRecipe> optional = CraftingUtils.findArcaneCraftingRecipe(pLevel, this.craftSlots, this.crystalSlots, cap);
            if (optional.isPresent()) {
                ArcaneCraftingRecipe recipe = optional.get();
                if (this.resultSlots.setRecipeUsed(pLevel, serverplayer, recipe)) {
                    itemstack = recipe.getResult();
                }
            }

            this.resultSlots.setItem(0, itemstack);
            setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(containerId, incrementStateId(), 0, itemstack));
        }
    }

    private void populateCrystalSlots() {
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_CHAOS, 57, 81, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.CHAOS)));
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_ORDER, 105, 1, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.ORDER)));
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_WATER, 105, 59, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.WATER)));
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_AIR, 57, -21, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.AIR)));
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_FIRE, 10, 1, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.FIRE)));
        addSlot(new PredicateSlot(this.crystalSlots, SLOT_EARTH, 10, 59, i -> AbstractAspectItem.hasAspect(i, ChaumtraftIDs.Aspects.EARTH)));
    }
}
