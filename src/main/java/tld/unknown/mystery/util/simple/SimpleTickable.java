package tld.unknown.mystery.util.simple;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface SimpleTickable {

    void tick(Level level, BlockPos pos, BlockState state);
}
