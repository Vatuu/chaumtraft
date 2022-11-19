package tld.unknown.mystery.entities;

import lombok.Getter;
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
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestLidController;
import tld.unknown.mystery.menus.TrunkMenu;
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
    private static final EntityDataAccessor<Boolean> OPENED = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.BOOLEAN);

    private final ChestLidController lidController;

    @Getter
    private SimpleContainer inventory;
    private short openStatus = 0;

    public TrunkEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.inventory = new SimpleContainer(SIZE_STANDARD);
        this.lidController = new ChestLidController();
    }

    @Override
    protected void registerGoals() {

    }

    @Override
    public void tick() {
        super.tick();
        lidController.shouldBeOpen(isOpen());
        lidController.tickLid();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("Upgrades", getUpgradeByte());
        pCompound.putBoolean("Stay", shouldSit());
        pCompound.put("Contents", inventory.createTag());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setUpgradeByte(pCompound.getByte("Upgrades"));
        setSit(pCompound.getBoolean("Stay"));
        inventory = new SimpleContainer(isSizeUpgraded() ? SIZE_UPGRADED : SIZE_STANDARD);
        inventory.fromTag(pCompound.getList("Contents", ListTag.TAG_COMPOUND));
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        this.openCustomInventoryScreen(pPlayer);
        return InteractionResult.sidedSuccess(this.level.isClientSide);
    }

    @Override
    public void openCustomInventoryScreen(Player pPlayer) {
        if(!this.level.isClientSide()) {
            updateOpenStatus(false);
            MenuConstructor menu = (id, inv, p) -> TrunkMenu.create(id, inv, this);
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

    public float getLidProgress(float pPartialTicks) {
        return lidController.getOpenness(pPartialTicks);
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
        getEntityData().define(OPENED, false);
    }

    public byte getUpgradeByte() {
        return getEntityData().get(UPGRADES);
    }

    public void setUpgradeByte(byte b) {
        getEntityData().set(UPGRADES, b);
    }

    @Override
    public UUID getOwnerUUID() {
        return getEntityData().get(OWNER_UUID).orElse(null);
    }

    public void setSit(boolean stay) {
        getEntityData().set(STAY, stay);
    }

    public boolean shouldSit() {
        return getEntityData().get(STAY);
    }

    public boolean isOpen() {
        return getEntityData().get(OPENED);
    }

    public void updateOpenStatus(boolean close) {
        if(!getLevel().isClientSide()) {
            short newStatus;
            if(close) {
                newStatus = (short) Math.max(openStatus - 1, 0);
                if(newStatus == 0 && openStatus > 0) {
                    getEntityData().set(OPENED, false);
                    getLevel().playSound(null, this, SoundEvents.CHEST_CLOSE, SoundSource.NEUTRAL, 1, 1);
                }
            } else {
                newStatus = (short)(openStatus + 1);
                if(newStatus > 0 && openStatus == 0) {
                    getEntityData().set(OPENED, true);
                    getLevel().playSound(null, this, SoundEvents.CHEST_OPEN, SoundSource.NEUTRAL, 1, 1);
                }
            }
            openStatus = newStatus;
        }
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
