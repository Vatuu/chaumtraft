package tld.unknown.mystery.data.generator.models;

import net.minecraft.client.renderer.block.model.ItemTransforms;
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
        builder.transforms()
                .transform(ItemTransforms.TransformType.GUI).rotation(30F, 45F, 0F).scale(.6F).end()
                .transform(ItemTransforms.TransformType.FIXED).translation(0F, 0F, 4F).scale(.6F).end()
                .transform(ItemTransforms.TransformType.GROUND).translation(0F, 3F, 0F).scale(.2F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND).rotation(0F, 45F, 0F).scale(.4F).end()
                .transform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND).rotation(0F, 225F, 0F).scale(.4F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND).translation(0F, 2.5F, 0F).rotation(75F, 45F, 0F).scale(.3F).end()
                .transform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND).translation(0F, 2.5F, 0F).rotation(75F, 45F, 0F).scale(.3F).end();
        simpleBlock(ChaumtraftBlocks.CRUCIBLE.block(), builder);
        simpleBlockItem(ChaumtraftBlocks.CRUCIBLE.block(), builder);
    }
}
