package tld.unknown.mystery.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tld.unknown.mystery.registries.ChaumtraftItems;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class TrunkEntity extends Mob implements HasCustomInventoryScreen, OwnableEntity {

    private static final int SIZE_STANDARD = 27;
    public static final int SIZE_UPGRADED = 36;

    private static final EntityDataAccessor<Byte> UPGRADES = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> STAY = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private final SimpleContainer inventory;

    public TrunkEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.inventory = new SimpleContainer(SIZE_STANDARD);
    }

    protected void registerGoals() {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("Contents", inventory.createTag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        inventory.fromTag(pCompound.getList("Contents", ListTag.TAG_COMPOUND));
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        this.openCustomInventoryScreen(pPlayer);
        return InteractionResult.sidedSuccess(this.level.isClientSide);
    }

    @Override
    public void openCustomInventoryScreen(Player pPlayer) {
        if (!this.level.isClientSide()) {
            MenuConstructor menu = (id, inv, p) -> isSizeUpgraded() ? new ChestMenu(MenuType.GENERIC_9x4, id, inv, inventory, 4) : ChestMenu.threeRows(id, inv, inventory);
            pPlayer.getLevel().playSound(null, this, SoundEvents.CHEST_OPEN, SoundSource.NEUTRAL, 1, 1);
            pPlayer.openMenu(new SimpleMenuProvider(menu, getTypeName()));
        }
    }

    @Override
    public Entity getOwner() {
        UUID uuid = this.getOwnerUUID();
        return uuid == null ? null : this.level.getPlayerByUUID(uuid);
    }

    public boolean isSizeUpgraded() {
        return ChaumtraftItems.UPGRADE_CAPACITY.get().isBitSet(getUpgradeByte());
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                             Synced Data Methods                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(UPGRADES, (byte)0);
        getEntityData().define(STAY, false);
        getEntityData().define(OWNER_UUID, Optional.empty());
    }

    public byte getUpgradeByte() {
        return getEntityData().get(UPGRADES);
    }

    @Override
    public UUID getOwnerUUID() {
        return getEntityData().get(OWNER_UUID).orElse(null);
    }

    public boolean shouldSit() {
        return getEntityData().get(STAY);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                             Mob Entity Methods                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Set.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) { }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
