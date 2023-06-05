package tld.unknown.mystery.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import tld.unknown.mystery.api.capabilities.IEssentiaCapability;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;

import java.util.Optional;

public final class CapabilityUtils {

    public static Optional<IEssentiaCapability> findEssentiaCapability(Level level, BlockPos pos, Direction dir) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be != null) {
            LazyOptional<IEssentiaCapability> cap = be.getCapability(ChaumtraftCapabilities.ESSENTIA, dir);
            return cap.isPresent() ? cap.resolve() : Optional.empty();
        }
        return Optional.empty();
    }
}
