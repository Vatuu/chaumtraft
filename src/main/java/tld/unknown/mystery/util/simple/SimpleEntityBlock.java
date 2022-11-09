package tld.unknown.mystery.util.simple;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public abstract class SimpleEntityBlock<T extends SimpleBlockEntity> extends Block implements BlockEntityType.BlockEntitySupplier<T>, EntityBlock {

    private final BiFunction<BlockPos, BlockState, T> factory;

    public SimpleEntityBlock(Properties pProperties, BiFunction<BlockPos, BlockState, T> factory) {
        super(pProperties);
        this.factory = factory;
    }

    @Override
    public T create(BlockPos pPos, BlockState pState) {
        return factory.apply(pPos, pState);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return create(pPos, pState);
    }

    @SuppressWarnings("unchecked")
    public T getEntity(Level l, BlockPos pos) {
        BlockEntity entity = l.getBlockEntity(pos);
        if(entity == null)
            throw new IllegalStateException(String.format("Unable to find BlockEntity at %s[%d|%d|%d]", l.dimension(), pos.getX(), pos.getY(), pos.getZ()));
        return (T)entity;
    }
}
