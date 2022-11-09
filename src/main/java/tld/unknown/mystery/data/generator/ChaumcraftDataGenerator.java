package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.data.generator.recipes.AlchemyRecipeProvider;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ChaumcraftDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent e) {
        DataGenerator dataGen = e.getGenerator();
        dataGen.addProvider(true, new ResearchCategoryProvider(dataGen));
        dataGen.addProvider(true, new ResearchEntryProvider(dataGen));
        dataGen.addProvider(true, new AspectProvider(dataGen));
        dataGen.addProvider(true, new AspectRegistryProvider(dataGen));

        dataGen.addProvider(true, new AlchemyRecipeProvider(dataGen));

        dataGen.addProvider(true, new BlockDataProvider(dataGen, e.getExistingFileHelper()));
        dataGen.addProvider(true, new TagsProvider(dataGen, e.getExistingFileHelper()));
    }
}
