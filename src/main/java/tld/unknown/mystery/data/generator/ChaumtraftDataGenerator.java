package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.data.generator.providers.BlockDataProvider;
import tld.unknown.mystery.data.generator.providers.AspectProvider;
import tld.unknown.mystery.data.generator.providers.AspectRegistryProvider;
import tld.unknown.mystery.data.generator.providers.ResearchCategoryProvider;
import tld.unknown.mystery.data.generator.providers.ResearchEntryProvider;
import tld.unknown.mystery.data.generator.recipes.AlchemyRecipeProvider;
import tld.unknown.mystery.data.generator.recipes.ArcaneCraftingRecipeProvider;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ChaumtraftDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator dataGen = e.getGenerator();
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) ResearchCategoryProvider::new);
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) ResearchEntryProvider::new);
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) AspectProvider::new);
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) AspectRegistryProvider::new);

        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) AlchemyRecipeProvider::new);
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) ArcaneCraftingRecipeProvider::new);

        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) out -> new BlockDataProvider(out, e.getExistingFileHelper()));
        dataGen.addProvider(true, (DataProvider.Factory<DataProvider>) out -> new TagsProvider(out, e.getLookupProvider(), e.getExistingFileHelper()));
    }
}
