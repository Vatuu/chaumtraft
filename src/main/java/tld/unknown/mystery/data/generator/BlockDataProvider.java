package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.registries.ChaumtraftBlocks;

public class BlockDataProvider extends BlockStateProvider {

    public BlockDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Chaumtraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelBuilder<?> builder = models().withExistingParent("crucible", new ResourceLocation("minecraft", "block/cauldron"))
                .texture("particle", Chaumtraft.id("block/crucible_inner"))
                .texture("top", Chaumtraft.id("block/crucible_top"))
                .texture("bottom", Chaumtraft.id("block/crucible_bottom"))
                .texture("side", Chaumtraft.id("block/crucible_side"))
                .texture("inside", Chaumtraft.id("block/crucible_inner"));
        simpleBlock(ChaumtraftBlocks.CRUCIBLE.block(), builder);
    }
}
