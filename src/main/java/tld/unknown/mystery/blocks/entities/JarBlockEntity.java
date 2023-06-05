package tld.unknown.mystery.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.api.aspects.Aspect;
import tld.unknown.mystery.api.aspects.AspectContainer;
import tld.unknown.mystery.api.capabilities.IEssentiaCapability;
import tld.unknown.mystery.blocks.JarBlock;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;

public class JarBlockEntity extends SimpleBlockEntity implements AspectContainer, IEssentiaCapability {

    private static final int MAX_ESSENTIA = 250;

    private ResourceLocation label, currentAspect;
    private int amount;

    public JarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ChaumtraftBlockEntities.JAR.entityType(), pPos, pBlockState);
        this.label = null;
        this.currentAspect = null;
        this.amount = 0;
    }

    @Override
    protected void readNbt(CompoundTag nbt) {
        if(nbt.contains("content")) {
            CompoundTag content = nbt.getCompound("content");
            this.currentAspect = ResourceLocation.tryParse(content.getString("aspect"));
            this.amount = content.getInt("amount");
        }
        if(nbt.contains("label")) {
            this.label = ResourceLocation.tryParse(nbt.getString("label"));
        }
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        if(amount > 0) {
            CompoundTag content = new CompoundTag();
            content.putString("aspect", this.currentAspect.toString());
            content.putInt("amount", this.amount);
            nbt.put("content", content);
        }
        if(label != null) {
            nbt.putString("label", this.label.toString());
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ChaumtraftCapabilities.ESSENTIA.orEmpty(cap, side == Direction.UP ? LazyOptional.of(() -> this) : LazyOptional.empty());
    }

    private boolean isVoid() {
        return ((JarBlock)getBlockState().getBlock()).isVoid();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                            Aspect Container Methods                                            */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public int getAspectCount(ResourceLocation aspect) {
        return aspect.equals(currentAspect) ? amount : 0;
    }

    @Override
    public AspectList getAspectList() {
        return new AspectList().add(currentAspect, amount);
    }

    @Override
    public int drainAspect(ResourceLocation aspect) {
        int essentia = this.amount;
        this.amount = 0;
        return essentia;
    }

    @Override
    public int drainAspect(ResourceLocation aspect, int amount) {
        if(aspect.equals(currentAspect)) {
            int currentAmount = this.amount;
            if(currentAmount <= amount) {
                this.amount = 0;
                this.currentAspect = ChaumtraftIDs.Aspects.ANY;
                return currentAmount;
            }
            this.amount -= amount;
            return amount;
        }
        return 0;
    }

    //TODO: Void Effect & Flux
    @Override
    public int addAspect(ResourceLocation aspect, int amount) {
        if(this.currentAspect == null && this.amount == 0) {
            this.currentAspect = aspect;
            this.amount = Math.min(MAX_ESSENTIA, amount);
            return this.amount;
        } else if(aspect.equals(currentAspect)) {
            int addAmount = Math.min(MAX_ESSENTIA - this.amount, amount);
            this.amount += addAmount;
            return addAmount;
        }
        return 0;
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                         Essentia Capability Methods                                            */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public SideStatus getSideStatus(Direction dir) {
        return SideStatus.INPUT_OUTPUT;
    }

    @Override
    public int getEssentia(Direction dir) {
        return amount;
    }

    @Override
    public ResourceLocation getEssentiaType(Direction dir) {
        return label != null ? label : currentAspect;
    }

    @Override
    public int getMinimumSuction(Direction dir) {
        if(isVoid()) {
            return label == null ? 48 : 32;
        } else {
            return label == null ? 64 : 32;
        }
    }

    @Override
    public ResourceLocation getSuctionType(Direction dir) {
        return getEssentiaType(dir);
    }

    @Override
    public int getSuction(Direction dir) {
        if(isVoid()) {
            return label == null ? 48 : 32;
        } else {
            if(amount >= MAX_ESSENTIA) {
                return 0;
            }
            return label == null ? 64 : 32;
        }
    }

    @Override
    public int drainAspect(ResourceLocation aspect, int amount, Direction dir) {
        return dir == Direction.UP ? drainAspect(aspect, amount) : 0;
    }

    @Override
    public int fillAspect(ResourceLocation aspect, int amount, Direction dir) {
        return dir == Direction.UP ? addAspect(aspect, amount) : 0;
    }

    @Override
    public boolean compliesToAspect(ResourceLocation aspect, Direction dir) {
        return dir == Direction.UP && (label == null || aspect.equals(label)) && (currentAspect == null || aspect.equals(currentAspect));
    }
}
