package tld.unknown.mystery.util.simple;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public abstract class SimpleEntityBlock<T extends BlockEntity> extends Block implements EntityBlock {

    protected final RegistryObject<BlockEntityType<T>> beType;

    public SimpleEntityBlock(Properties pProperties, RegistryObject<BlockEntityType<T>> factory) {
        super(pProperties);
        this.beType = factory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return beType.get().create(pPos, pState);
    }

    @SuppressWarnings("unchecked")
    public T getEntity(Level l, BlockPos pos) {
        BlockEntity entity = l.getBlockEntity(pos);
        if(entity == null)
            throw new IllegalStateException(String.format("Unable to find BlockEntity at %s[%d|%d|%d]", l.dimension(), pos.getX(), pos.getY(), pos.getZ()));
        return (T)entity;
    }
}
