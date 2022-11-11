package tld.unknown.mystery.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class TrunkEntity extends Mob implements HasCustomInventoryScreen, OwnableEntity {

    private final EntityDataAccessor<Byte> UPGRADES = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.BYTE);
    private final EntityDataAccessor<Boolean> STAY = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.BOOLEAN);
    private final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(TrunkEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public TrunkEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {

    }

    @Override
    public void openCustomInventoryScreen(Player pPlayer) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public Entity getOwner() {
        UUID uuid = this.getOwnerUUID();
        return uuid == null ? null : this.level.getPlayerByUUID(uuid);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                             Synced Data Methods                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(UPGRADES, (byte)0);
        getEntityData().define(STAY, false);
    }

    public byte getUpgradeByte() {
        return getEntityData().get(UPGRADES);
    }

    @Override
    public UUID getOwnerUUID() {
        return getEntityData().get(OWNER_UUID).orElse(null);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                           Living Entity Methods                                                */
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
