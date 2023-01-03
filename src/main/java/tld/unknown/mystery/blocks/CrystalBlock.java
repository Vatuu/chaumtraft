package tld.unknown.mystery.blocks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tld.unknown.mystery.api.ChaumtraftIDs;

import java.util.Arrays;
import java.util.Optional;

//TODO: Crystal material
public class CrystalBlock extends DirectionalBlock {

    public static final EnumProperty<CrystalAspect> ASPECT = EnumProperty.create("aspect", CrystalAspect.class);
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 3);

    private static final VoxelShape COLLISION = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public CrystalBlock() {
        super(Properties.of(Material.GLASS, s -> MaterialColor.COLOR_CYAN));
        registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.UP)
                .setValue(ASPECT, CrystalAspect.ORDER)
                .setValue(SIZE, 0));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return COLLISION;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ASPECT, SIZE);
    }

    @AllArgsConstructor
    public enum CrystalAspect implements StringRepresentable {
        ORDER(ChaumtraftIDs.Aspects.ORDER, MaterialColor.COLOR_LIGHT_GRAY),
        DESTRUCTION(ChaumtraftIDs.Aspects.CHAOS, MaterialColor.COLOR_GRAY),
        EARTH(ChaumtraftIDs.Aspects.EARTH, MaterialColor.COLOR_GREEN),
        WATER(ChaumtraftIDs.Aspects.WATER, MaterialColor.COLOR_CYAN),
        AIR(ChaumtraftIDs.Aspects.AIR, MaterialColor.COLOR_YELLOW),
        FIRE(ChaumtraftIDs.Aspects.FIRE, MaterialColor.COLOR_RED),
        TAINT(ChaumtraftIDs.Aspects.TAINT, MaterialColor.COLOR_PURPLE);

        @Getter
        private final ResourceLocation id;
        private final MaterialColor color;

        @Override
        public String getSerializedName() {
            return id.getPath();
        }

        public static Optional<CrystalAspect> getFromId(ResourceLocation id) {
            return Arrays.stream(values()).filter(a -> a.id == id).findFirst();
        }
    }
}
