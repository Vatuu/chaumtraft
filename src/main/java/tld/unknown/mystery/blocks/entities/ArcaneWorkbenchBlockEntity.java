package tld.unknown.mystery.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;

public class ArcaneWorkbenchBlockEntity extends SimpleBlockEntity {

    private static final int SLOT_ORDER = 0;
    private static final int SLOT_WATER = 1;
    private static final int SLOT_AIR = 2;
    private static final int SLOT_DESTRUCTION = 3;
    private static final int SLOT_EARTH = 4;
    private static final int SLOT_FIRE = 5;

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
