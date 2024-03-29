package tld.unknown.mystery.blocks;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tld.unknown.mystery.blocks.entities.JarBlockEntity;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.items.AbstractAspectItem;
import tld.unknown.mystery.items.PhialItem;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
import tld.unknown.mystery.registries.ChaumtraftItems;
import tld.unknown.mystery.util.CapabilityUtils;
import tld.unknown.mystery.util.simple.SimpleEntityBlock;

public class JarBlock extends SimpleEntityBlock<JarBlockEntity> {

    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    public static final BooleanProperty BRACED = BooleanProperty.create("braced");

    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 12, 13);

    @Getter
    private final boolean isVoid;

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public JarBlock(boolean isVoid) {
        super(Properties.of(Material.GLASS), ChaumtraftBlockEntities.JAR.entityTypeObject());
        this.isVoid = isVoid;
        registerDefaultState(this.getStateDefinition().any()
                .setValue(CONNECTED, false)
                .setValue(BRACED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CONNECTED).add(BRACED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }
        JarBlockEntity jar = getEntity(pLevel, pPos);
        ItemStack handItem = pPlayer.getMainHandItem();
        if(handItem != ItemStack.EMPTY) {
            if(handItem.getItem().equals(ChaumtraftItems.JAR_BRACE.get()) && !pState.getValue(BRACED)) {
                if(!pLevel.isClientSide()) {
                    if(!pPlayer.isCreative()) {
                        handItem.shrink(1);
                    }
                    pLevel.setBlock(pPos, pState.setValue(BRACED, true), 1 | 2);
                    //TODO: Play Sound
                    return InteractionResult.sidedSuccess(false);
                } else {
                    return InteractionResult.sidedSuccess(true);
                }
            } else if(handItem.getItem().equals(ChaumtraftItems.PHIAL.get())) {
                if(AbstractAspectItem.hasContent(handItem)) {
                    ResourceLocation aspect = ChaumtraftItems.PHIAL.get().getAspects(handItem).aspects().get(0);
                    if(jar.canFit(aspect, 10, Direction.UP)) {
                        if(!pLevel.isClientSide()) {
                            //TODO: Sound
                            jar.fillAspect(aspect, 10, Direction.UP);
                            jar.sync();
                            if(!pPlayer.isCreative()) {
                                handItem.shrink(1);
                                pPlayer.addItem(new ItemStack(ChaumtraftItems.PHIAL.get()));
                            }
                            return InteractionResult.sidedSuccess(false);
                        } else {
                            return InteractionResult.sidedSuccess(true);
                        }
                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    if(jar.contains(null, 10, Direction.UP)) {
                        if(!pLevel.isClientSide()) {
                            //TODO: Sound
                            ResourceLocation aspect = jar.getEssentiaType(Direction.UP);
                            jar.drainAspect(aspect, 10, Direction.UP);
                            jar.sync();
                            if(!pPlayer.isCreative()) {
                                handItem.shrink(1);
                                pPlayer.addItem(ChaumtraftItems.PHIAL.get().create(aspect));
                            }
                            return InteractionResult.sidedSuccess(false);
                        } else {
                            return InteractionResult.sidedSuccess(true);
                        }
                    } else {
                        return InteractionResult.FAIL;
                    }
                }
            }
        } else {
            //TODO: Empty contents
            return InteractionResult.FAIL;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if(pPos.above() != pFromPos) {
            return;
        }
        if(CapabilityUtils.findEssentiaCapability(pLevel, pPos, Direction.UP).isPresent()) {
            pLevel.setBlock(pPos, pState.setValue(CONNECTED, true), 2);
        } else {
            pLevel.setBlock(pPos, pState.setValue(CONNECTED, false), 2);
        }
    }
}
