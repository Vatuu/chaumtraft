package tld.unknown.mystery.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tld.unknown.mystery.Chaumtraft;

public final class ChaumtraftIDs {

    /**
     * Various identifiers referring to textures which are often reused.
     */
    public static final class Textures {

        public static final ResourceLocation UNKNOWN = Chaumtraft.id("textures/unknown.png");
    }


    public static final class Recipes {

        public static final ResourceLocation TYPE_ALCHEMY = Chaumtraft.id("alchemy");

        public static final ResourceLocation ALCHEMY_DOUBLE_SLIME = Chaumtraft.id("double_slime");
    }

    /**
     * Various constant Identifiers related to the Research system. Mostly internal research triggers and categories.
     */
    public static final class Research {

        public static final String CATEGORY_DEBUG = "debug";
        public static final String CATEGORY_FUNDAMENTALS = "fundamentals";
        public static final String CATEGORY_AUROMANCY = "auromancy";
        public static final String CATEGORY_ALCHEMY = "alchemy";
        public static final String CATEGORY_ARTIFICE = "artifice";
        public static final String CATEGORY_INFUSION = "infusion";
        public static final String CATEGORY_GOLEMANCY = "golemancy";
        public static final String CATEGORY_ELDRITCH = "eldritch";

        public static final ResourceLocation UNLOCK_DEBUG = Chaumtraft.id("internal/unlock_debug");
        public static final ResourceLocation UNLOCK_ARTIFICE = Chaumtraft.id(CATEGORY_FUNDAMENTALS + "/unlock_" + CATEGORY_ALCHEMY);
    }

    /**
     * Identifiers for the various items within the mod.
     */
    public static final class Items {

        public static final ResourceLocation THAUMONOMICON = Chaumtraft.id("thaumonomicon");

        public static final ResourceLocation PHIAL = Chaumtraft.id("phial");
        public static final ResourceLocation VIS_CRYSTAL = Chaumtraft.id("vis_crystal");

        public static final ResourceLocation UPGRADE_SPEED = Chaumtraft.id("upgrade_speed");
        public static final ResourceLocation UPGRADE_CAPACITY = Chaumtraft.id("upgrade_capacity");
        public static final ResourceLocation UPGRADE_RAGE = Chaumtraft.id("upgrade_rage");
        public static final ResourceLocation UPGRADE_EFFICIENCY = Chaumtraft.id("upgrade_efficiency");
    }

    public static final class ItemProperties {

        public static final ResourceLocation ASPECT_HOLDER_PRESENT = Chaumtraft.id("aspect_holder_present");
    }

    public static final class Tags {

        public static final TagKey<Block> CRUCIBLE_HEATER = BlockTags.create(Chaumtraft.id("crucible_heater"));
    }

    public static final class Blocks {

        public static final ResourceLocation ARCANE_WORKBENCH = Chaumtraft.id("arcane_workbench");
        public static final ResourceLocation CRUCIBLE = Chaumtraft.id("crucible");
        public static final ResourceLocation CRYSTAL_COLONY = Chaumtraft.id("crystal_colony");
        public static final ResourceLocation SILVERWOOD = Chaumtraft.id("silverwood");
        public static final ResourceLocation GREATWOOD = Chaumtraft.id("greatwood");
    }

    public static final class Entities {

        public static final ResourceLocation TRAVELING_TRUNK = Chaumtraft.id("traveling_trunk");
    }

    /**
     * Identifiers for the various capabilities used to keep track of data within the mod.
     */
    public static final class Capabilities {

        public static final ResourceLocation RESEARCH = Chaumtraft.id("research");
    }

    /**
     * Identifiers for the "vanilla" aspects of Chaumtraft.
     */
    public static final class Aspects {

        public static final ResourceLocation UNKNOWN = Chaumtraft.id("unknown");

        public static final ResourceLocation ORDER = Chaumtraft.id("ordo");
        public static final ResourceLocation DESTRUCTION = Chaumtraft.id("perditio");
        public static final ResourceLocation EARTH = Chaumtraft.id("terra");
        public static final ResourceLocation AIR = Chaumtraft.id("aer");
        public static final ResourceLocation WATER = Chaumtraft.id("aqua");
        public static final ResourceLocation FIRE = Chaumtraft.id("ignis");

        public static final ResourceLocation EMPTY = Chaumtraft.id("vacuos");
        public static final ResourceLocation LIGHT = Chaumtraft.id("lux");
        public static final ResourceLocation MOVEMENT = Chaumtraft.id("motus");
        public static final ResourceLocation ICE = Chaumtraft.id("gelum");
        public static final ResourceLocation CRYSTAL = Chaumtraft.id("vitreus");
        public static final ResourceLocation METAL = Chaumtraft.id("metallum");
        public static final ResourceLocation LIFE = Chaumtraft.id("victus");
        public static final ResourceLocation DEATH = Chaumtraft.id("mortuus");
        public static final ResourceLocation POWER = Chaumtraft.id("potentia");
        public static final ResourceLocation CHANGE = Chaumtraft.id("permutatio");
        public static final ResourceLocation MAGIC = Chaumtraft.id("praecantatio");
        public static final ResourceLocation AURA = Chaumtraft.id("auram");
        public static final ResourceLocation ALCHEMY = Chaumtraft.id("alkimia");
        public static final ResourceLocation TAINT = Chaumtraft.id("vitium");
        public static final ResourceLocation DARKNESS = Chaumtraft.id("tenebrae");
        public static final ResourceLocation ALIEN = Chaumtraft.id("alienis");
        public static final ResourceLocation FLIGHT = Chaumtraft.id("volatus");
        public static final ResourceLocation PLANT = Chaumtraft.id("herba");
        public static final ResourceLocation TOOL = Chaumtraft.id("instrumentum");
        public static final ResourceLocation CRAFT = Chaumtraft.id("fabrico");
        public static final ResourceLocation MACHINE = Chaumtraft.id("machina");
        public static final ResourceLocation TRAP = Chaumtraft.id("vinculum");
        public static final ResourceLocation SPIRIT = Chaumtraft.id("spiritus");
        public static final ResourceLocation MIND = Chaumtraft.id("cognito");
        public static final ResourceLocation SENSE = Chaumtraft.id("sensus");
        public static final ResourceLocation AVERSION = Chaumtraft.id("aversio");
        public static final ResourceLocation ARMOR = Chaumtraft.id("praemunio");
        public static final ResourceLocation DESIRE = Chaumtraft.id("desiderium");
        public static final ResourceLocation UNDEAD = Chaumtraft.id("exanimis");
        public static final ResourceLocation CREATURE = Chaumtraft.id("bestia");
        public static final ResourceLocation HUMAN = Chaumtraft.id("humanus");
    }
}
