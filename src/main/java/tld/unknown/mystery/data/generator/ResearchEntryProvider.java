package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.data.research.DisplayProperties;
import tld.unknown.mystery.data.research.ResearchEntry;
import tld.unknown.mystery.data.research.ResearchStage;
import tld.unknown.mystery.util.codec.data.CodecDataProvider;

public class ResearchEntryProvider extends CodecDataProvider<ResearchEntry> {

    public ResearchEntryProvider(DataGenerator generator) {
        super(generator, "ResearchEntries", "research", ResearchEntry.CODEC);
    }

    @Override
    protected void createEntries() {
        register(Chaumtraft.id(ChaumtraftIDs.Research.CATEGORY_DEBUG + "/origin"), ResearchEntry.builder(
                DisplayProperties.builder(0, 0).build(),
                ResearchStage.builder()
                        .setRecipeUnlocks(Chaumtraft.id("test_recipe"))
                        .build()
                ).build());
    }
}
