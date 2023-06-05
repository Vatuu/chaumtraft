package tld.unknown.mystery.blocks.entities;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.PedestalBlock;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;

public class PedestalBlockEntity extends SimpleBlockEntity {

    @Getter @Setter
    private ItemStack itemStack;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ChaumtraftBlockEntities.PEDESTAL.entityType(), pPos, pBlockState);
        this.itemStack = ItemStack.EMPTY;
    }

    @Override
    protected void readNbt(CompoundTag nbt) {
        this.itemStack = ItemStack.of(nbt.getCompound("item"));
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        nbt.put("item", itemStack.serializeNBT());
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos().above()).inflate(.25).move(0, .25F, 0);
    }

    private PedestalBlock.Type getPedestalType() {
        return ((PedestalBlock)getBlockState().getBlock()).getType();
    }
}
