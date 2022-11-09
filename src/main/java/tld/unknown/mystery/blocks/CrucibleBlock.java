package tld.unknown.mystery.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.entities.CrucibleBlockEntity;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.FluidHelper;
import tld.unknown.mystery.util.simple.SimpleEntityBlock;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class CrucibleBlock extends SimpleEntityBlock<CrucibleBlockEntity> {

    private static final int SIDE_THICKNESS = 2;
    private static final int LEG_WIDTH = 4;
    private static final int LEG_HEIGHT = 3;
    private static final int LEG_DEPTH = 2;
    private static final int FLOOR_LEVEL = 4;
    private static final VoxelShape INSIDE = box(SIDE_THICKNESS, FLOOR_LEVEL, SIDE_THICKNESS, SIDE_THICKNESS + 12, FLOOR_LEVEL + 12, SIDE_THICKNESS + 12);
    private static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(
                    box(0, 0, LEG_WIDTH, 16.0D, LEG_HEIGHT, 12.0D),
                    box(LEG_WIDTH, 0.0D, 0.0D, 16 - LEG_WIDTH, LEG_HEIGHT, 16.0D),
                    box(LEG_DEPTH, 0.0D, LEG_DEPTH, 16 - LEG_DEPTH, LEG_HEIGHT, 16 - LEG_DEPTH),
                    INSIDE), BooleanOp.ONLY_FIRST);

    public CrucibleBlock() {
        super(Properties.of(Material.METAL, MaterialColor.STONE), CrucibleBlockEntity::new);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return INSIDE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ChaumtraftBlocks.CRUCIBLE.entityType() ? CrucibleBlockEntity::tick : null;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide) {
            Optional<FluidStack> stack = FluidUtil.getFluidContained(pPlayer.getItemInHand(pHand));
            if(stack.isPresent() && stack.get().containsFluid(new FluidStack(Fluids.WATER, 1000))) {
                CrucibleBlockEntity be = getEntity(pLevel, pPos);
                if(!FluidHelper.isTankFull(be) && FluidUtil.interactWithFluidHandler(pPlayer, pHand, be)) {
                    float randomPitch = 1.0F + (pLevel.getRandom().nextFloat() - pLevel.getRandom().nextFloat()) * .3F;
                    pLevel.playSound(null, pPos.getX() + .5D, pPos.getY() + .5D, pPos.getZ() + .5D, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, .33F, randomPitch);
                    be.sync();
                }
                return InteractionResult.SUCCESS;
            } else if(pPlayer.getMainHandItem().isEmpty() && pPlayer.isCrouching()) {
                getEntity(pLevel, pPos).emptyCrucible();
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
