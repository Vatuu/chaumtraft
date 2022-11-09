package tld.unknown.mystery.data.generator;

import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import tld.unknown.mystery.api.Aspect;
import tld.unknown.mystery.util.codec.data.CodecDataProvider;

import java.util.Arrays;

import static tld.unknown.mystery.api.ChaumtraftIDs.Aspects;

public class AspectProvider extends CodecDataProvider<Aspect> {

    public AspectProvider(DataGenerator generator) {
        super(generator, "Aspects", "aspects", Aspect.CODEC);
    }

    @Override
    protected void createEntries() {
        registerAspect(Aspects.EMPTY, "#888888", Aspects.AIR, Aspects.DESTRUCTION);
        registerAspect(Aspects.LIGHT, "#FFFFC0", Aspects.AIR, Aspects.FIRE);
        registerAspect(Aspects.MOVEMENT, "#CDCCF4", Aspects.AIR, Aspects.ORDER);
        registerAspect(Aspects.ICE, "#E1FFFF", Aspects.FIRE, Aspects.DESTRUCTION);
        registerAspect(Aspects.CRYSTAL, "#80FFFF", Aspects.EARTH, Aspects.AIR);
        registerAspect(Aspects.METAL, "#B5B5CD", Aspects.EARTH, Aspects.ORDER);
        registerAspect(Aspects.LIFE, "#DE0005", Aspects.EARTH, Aspects.WATER);
        registerAspect(Aspects.DEATH, "#6A0005", Aspects.WATER, Aspects.DESTRUCTION);
        registerAspect(Aspects.POWER, "#C0FFFF", Aspects.ORDER, Aspects.FIRE);
        registerAspect(Aspects.CHANGE, "#578357", Aspects.DESTRUCTION, Aspects.ORDER);

        registerAspect(Aspects.MAGIC, "#CF00FF", Aspects.POWER, Aspects.AIR);
        registerAspect(Aspects.AURA, "#FFC0FF", Aspects.MAGIC, Aspects.AIR);
        registerAspect(Aspects.ALCHEMY, "#23AC9D", Aspects.MAGIC, Aspects.WATER);
        registerAspect(Aspects.FLUX, "#800080", Aspects.DESTRUCTION, Aspects.MAGIC);

        registerAspect(Aspects.DARKNESS, "#222222", Aspects.EMPTY, Aspects.LIGHT);
        registerAspect(Aspects.ALIEN, "#805080", Aspects.EMPTY, Aspects.DARKNESS);
        registerAspect(Aspects.FLIGHT, "#E7E7D7", Aspects.AIR, Aspects.MOVEMENT);
        registerAspect(Aspects.PLANT, "#1AC00", Aspects.LIFE, Aspects.EARTH);

        registerAspect(Aspects.TOOL, "#4040EE", Aspects.METAL, Aspects.POWER);
        registerAspect(Aspects.CRAFT, "#809D80", Aspects.CHANGE, Aspects.TOOL);
        registerAspect(Aspects.MACHINE, "#8080A0", Aspects.MOVEMENT, Aspects.TOOL);
        registerAspect(Aspects.TRAP, "#9A8080", Aspects.MOVEMENT, Aspects.DESTRUCTION);

        registerAspect(Aspects.SPIRIT, "#EBEBFB", Aspects.LIFE, Aspects.DEATH);
        registerAspect(Aspects.MIND, "#F9967F", Aspects.FIRE, Aspects.SPIRIT);
        registerAspect(Aspects.SENSE, "#C0FFC0", Aspects.AIR, Aspects.SPIRIT);
        registerAspect(Aspects.AVERSION, "#C05050", Aspects.SPIRIT, Aspects.DESTRUCTION);
        registerAspect(Aspects.ARMOR, "#C0C0", Aspects.SPIRIT, Aspects.EARTH);
        registerAspect(Aspects.DESIRE, "#E6BE44", Aspects.SPIRIT, Aspects.EMPTY);

        registerAspect(Aspects.UNDEAD, "#3A4000", Aspects.MOVEMENT, Aspects.DEATH);
        registerAspect(Aspects.CREATURE, "#9F6409", Aspects.MOVEMENT, Aspects.LIFE);
        registerAspect(Aspects.HUMAN, "#FFD7C0", Aspects.SPIRIT, Aspects.LIFE);
    }

    private void registerAspect(ResourceLocation id, String color, ResourceLocation... origin) {
        register(id, new Aspect(TextColor.parseColor(color), Arrays.asList(origin)));
    }
}
