package tld.unknown.mystery.blocks.entities;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.FluidHelper;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;
import tld.unknown.mystery.util.simple.SimpleTickable;

public class CrucibleBlockEntity extends SimpleBlockEntity implements IFluidHandler {

    private static final int MAX_ESSENTIA = 500;
    private static final int HEAT_THRESHOLD = 150;
    private static final int HEAT_MAX = 200;

    private final FluidTank waterTank;
    private final AspectList aspects;

    @Getter
    private int heat;

    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ChaumtraftBlocks.CRUCIBLE.entityType(), pPos, pBlockState);
        this.waterTank = new FluidTank(FluidType.BUCKET_VOLUME);
        this.aspects = new AspectList();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        CrucibleBlockEntity be = (CrucibleBlockEntity)entity;
        int prevHeat = be.heat;
        if(level.isClientSide()) {
            if(!FluidHelper.isTankEmpty(be) ) {
                if(level.getBlockState(pos.below()).getTags().anyMatch(tag -> tag == ChaumtraftIDs.Tags.CRUCIBLE_HEATER)) {
                    be.heat += be.heat < HEAT_MAX ? 1 : 0;
                    if(prevHeat < HEAT_THRESHOLD && be.heat >= HEAT_THRESHOLD) {
                        be.sync();
                    }
                } else if(be.heat > 0) {
                    be.heat--;
                    if(be.heat < HEAT_THRESHOLD) {
                        be.sync();
                    }
                }
            } else if(be.heat > 0) {
                be.heat --;
            }
        }
    }

    @Override
    protected void readNbt(CompoundTag nbt) {
        this.aspects.fromNBT(nbt.getCompound("Aspects"));
        this.waterTank.readFromNBT(nbt.getCompound("Water"));
        this.heat = nbt.getByte("Heat");
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        nbt.put("Aspects", aspects.toNBT());
        nbt.put("Water", waterTank.writeToNBT(new CompoundTag()));
        nbt.putByte("Heat", (byte)heat);
    }

    // TODO: Flux Pollution
    public void emptyCrucible() {
        if(!FluidHelper.isTankEmpty(this)) {
            waterTank.setFluid(FluidStack.EMPTY);
            float randomPitch = 1.0F + (getLevel().getRandom().nextFloat() - getLevel().getRandom().nextFloat()) * .3F;
            getLevel().playSound(null, getBlockPos().getX() + .5D, getBlockPos().getY() + .5D, getBlockPos().getZ() + .5D, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, .33F, randomPitch);
            sync();
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                              Fluid Tank Methods                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public int getTanks() {
        return waterTank.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return waterTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return waterTank.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.fill(resource, action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.drain(maxDrain, action);
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.drain(resource, action);
    }
}
