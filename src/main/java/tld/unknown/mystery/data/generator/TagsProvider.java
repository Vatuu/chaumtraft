package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tld.unknown.mystery.Chaumtraft;

import static tld.unknown.mystery.api.ChaumtraftIDs.Tags;

public class TagsProvider extends BlockTagsProvider {

    public TagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Chaumtraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(Tags.CRUCIBLE_HEATER).add(Blocks.LAVA, Blocks.MAGMA_BLOCK, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);
    }
}
