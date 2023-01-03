package tld.unknown.mystery.blocks.entities;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;

public class ArcaneWorkbenchBlockEntity extends SimpleBlockEntity {

    @Getter
    private final SimpleContainer container;

    public ArcaneWorkbenchBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ChaumtraftBlocks.ARCANE_WORKBENCH.entityType(), pPos, pBlockState);
        this.container = new SimpleContainer(6);
    }

    @Override
    protected void readNbt(CompoundTag nbt) {
        nbt.put("Contents", container.createTag());
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        this.container.fromTag(nbt.getList("Contents", Tag.TAG_COMPOUND));
    }
}
