package tld.unknown.mystery.blocks;

import lombok.AllArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import tld.unknown.mystery.api.ChaumtraftIDs;

//TODO: Crystal material
public class CrystalBlock extends DirectionalBlock {

    public static final EnumProperty<Aspect> ASPECT = EnumProperty.create("aspect", Aspect.class);
    public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 3);

    protected CrystalBlock() {
        super(Properties.of(Material.GLASS, s -> s.getValue(ASPECT).color));
    }

    @AllArgsConstructor
    public enum Aspect implements StringRepresentable {
        ORDER(ChaumtraftIDs.Aspects.ORDER, MaterialColor.COLOR_LIGHT_GRAY),
        DESTRUCTION(ChaumtraftIDs.Aspects.DESTRUCTION, MaterialColor.COLOR_GRAY),
        EARTH(ChaumtraftIDs.Aspects.EARTH, MaterialColor.COLOR_GREEN),
        WATER(ChaumtraftIDs.Aspects.WATER, MaterialColor.COLOR_CYAN),
        AIR(ChaumtraftIDs.Aspects.AIR, MaterialColor.COLOR_YELLOW),
        FIRE(ChaumtraftIDs.Aspects.FIRE, MaterialColor.COLOR_RED),
        TAINT(ChaumtraftIDs.Aspects.TAINT, MaterialColor.COLOR_PURPLE);

        private final ResourceLocation id;
        private final MaterialColor color;

        @Override
        public String getSerializedName() {
            return id.getPath();
        }
    }
}
