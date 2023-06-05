package tld.unknown.mystery.registries;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.*;
import tld.unknown.mystery.items.blocks.CrystalBlockItem;
import tld.unknown.mystery.util.better.BetterSign;
import tld.unknown.mystery.util.simple.SimpleCreativeTab;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static tld.unknown.mystery.api.ChaumtraftIDs.Blocks;

public final class ChaumtraftBlocks {

    private static final DeferredRegister<Block> REGISTRY_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Chaumtraft.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> REGISTRY_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Chaumtraft.MOD_ID);
    private static final DeferredRegister<Item> REGISTRY_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static final BlockObject<CrystalBlock> CRYSTAL_COLONY = registerBlock(Blocks.CRYSTAL_COLONY, CrystalBlock::new, CrystalBlockItem::new, ChaumtraftTabs.MAIN);

    public static final WoodBlockSet GREATWOOD = registerWoodType(Blocks.GREATWOOD, ChaumtraftTabs.MAIN);
    public static final WoodBlockSet SILVERWOOD = registerWoodType(Blocks.SILVERWOOD, ChaumtraftTabs.MAIN);

    public static final BlockObject<ArcaneWorkbenchBlock> ARCANE_WORKBENCH = registerBlock(Blocks.ARCANE_WORKBENCH, ArcaneWorkbenchBlock::new, ChaumtraftTabs.MAIN);
    public static final BlockObject<CrucibleBlock> CRUCIBLE = registerBlock(Blocks.CRUCIBLE, CrucibleBlock::new, ChaumtraftTabs.MAIN);
    public static final BlockObject<RunicMatrixBlock> RUNIC_MATRIX = registerBlock(Blocks.RUNIC_MATRIX, RunicMatrixBlock::new, ChaumtraftTabs.MAIN);
    public static final BlockObject<PedestalBlock> ARCANE_PEDESTAL = registerBlock(Blocks.ARCANE_PEDESTAL, () -> new PedestalBlock(PedestalBlock.Type.ARCANE), ChaumtraftTabs.MAIN);
    public static final BlockObject<PedestalBlock> ANCIENT_PEDESTAL = registerBlock(Blocks.ANCIENT_PEDESTAL, () -> new PedestalBlock(PedestalBlock.Type.ANCIENT), ChaumtraftTabs.MAIN);
    public static final BlockObject<PedestalBlock> ELDRITCH_PEDESTAL = registerBlock(Blocks.ELDRITCH_PEDESTAL, () -> new PedestalBlock(PedestalBlock.Type.ELDRITCH), ChaumtraftTabs.MAIN);
    public static final BlockObject<JarBlock> WARDED_JAR = registerBlock(Blocks.WARDED_JAR, () -> new JarBlock(false), ChaumtraftTabs.MAIN);
    public static final BlockObject<JarBlock> VOID_JAR = registerBlock(Blocks.VOID_JAR, () -> new JarBlock(true), ChaumtraftTabs.MAIN);

    public static final BlockObject<TubeBlock> TUBE = registerBlock(Blocks.TUBE, TubeBlock::new, ChaumtraftTabs.MAIN);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY_BLOCKS.register(bus);
        REGISTRY_BLOCK_ENTITIES.register(bus);
        REGISTRY_ITEM.register(bus);
    }

    private static <B extends Block> BlockObject<B> registerBlock(ResourceLocation id, Supplier<B> block, SimpleCreativeTab tab) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), () -> new BlockItem(blockObject.get(), new Item.Properties().stacksTo(64)));
        if(tab != null) {
            tab.register(itemObject);
        }
        return new BlockObject<>(blockObject, itemObject);
    }

    private static <B extends Block> BlockObject<B> registerBlock(ResourceLocation id, Supplier<B> block, Supplier<Item> blockItem, SimpleCreativeTab tab) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), blockItem);
        if(tab != null) {
            tab.register(itemObject);
        }
        return new BlockObject<>(blockObject, itemObject);
    }

    //TODO Tree Grower, BlockSetType, Button property
    private static WoodBlockSet registerWoodType(ResourceLocation id, SimpleCreativeTab tab) {
        final BlockBehaviour.Properties props = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD);
        WoodType type = WoodType.register(new WoodType(id.getPath(), BlockSetType.DARK_OAK));
        BlockObject<Block> planks = registerBlock(Chaumtraft.id(id.getPath() + "_planks"), () -> new Block(props), tab);
        BlockObject<RotatedPillarBlock> log = registerBlock(Chaumtraft.id(id.getPath() + "_log"), () -> new RotatedPillarBlock(props), tab);
        BlockObject<RotatedPillarBlock> wood = registerBlock(Chaumtraft.id(id.getPath() + "_wood"), () -> new RotatedPillarBlock(props), tab);
        BlockObject<RotatedPillarBlock> logStripped = registerBlock(Chaumtraft.id("stripped_" + id.getPath() + "_log"), () -> new RotatedPillarBlock(props), tab);
        BlockObject<RotatedPillarBlock> woodStripped = registerBlock(Chaumtraft.id("stripped_" + id.getPath() + "_wood"), () -> new RotatedPillarBlock(props), tab);

        BlockObject<LeavesBlock> leaves = registerBlock(Chaumtraft.id(id.getPath() + "_leaves"), () -> new LeavesBlock(
                BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
                .isValidSpawn((state, level, pos, a) -> a == EntityType.PARROT)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)), tab);
        BlockObject<SaplingBlock> sapling = registerBlock(Chaumtraft.id(id.getPath() + "_sapling"), () -> new SaplingBlock(
                null,
                BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), tab);

        BlockObject<StairBlock> stairs = registerBlock(Chaumtraft.id(id.getPath() + "_stairs"), () -> new StairBlock(() -> planks.block().defaultBlockState(), props), tab);
        BlockObject<SlabBlock> slab = registerBlock(Chaumtraft.id(id.getPath() + "_slab"), () -> new SlabBlock(props), tab);

        BlockObject<FenceBlock> fence = registerBlock(Chaumtraft.id(id.getPath() + "_fence"), () -> new FenceBlock(props), tab);
        BlockObject<FenceGateBlock> fenceGate = registerBlock(Chaumtraft.id(id.getPath() + "_fence_gate"), () -> new FenceGateBlock(props, type), tab);

        BlockObject<PressurePlateBlock> pressurePlate = registerBlock(Chaumtraft.id(id.getPath() + "_pressure_plate"), () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props, type.setType()), tab);
        BlockObject<ButtonBlock> button = registerBlock(Chaumtraft.id(id.getPath() + "_button"), () -> new ButtonBlock(props, type.setType(), 60, false), tab);

        return new WoodBlockSet(type, planks, log, wood, logStripped, woodStripped, leaves, sapling, stairs, slab,fence, fenceGate, pressurePlate, button);
    }

    private static BetterSign.SignObject registerSign(ResourceLocation id, WoodType type, SimpleCreativeTab tab) {
        BlockBehaviour.Properties signProps = BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD);
        RegistryObject<StandingSignBlock> standingSign = REGISTRY_BLOCKS.register(id.getPath() + "_sign", () -> new BetterSign.BetterStandingSignBlock(signProps, type));
        RegistryObject<WallSignBlock> wallSign = REGISTRY_BLOCKS.register(id.getPath() + "_wall_sign", () -> new BetterSign.BetterWallSignBlock(signProps, type));
        RegistryObject<SignItem> signItem = REGISTRY_ITEM.register(id.getPath() + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), standingSign.get(), wallSign.get()));
        if(tab != null) {
            tab.register(signItem);
        }
        BetterSign.SignObject obj = new BetterSign.SignObject(type, standingSign, wallSign, signItem);
        BetterSign.addSign(obj);
        return obj;
    }

    private static <B extends Block, K, I extends Item> MultiItemBlockObject<B, K, I> registerMultiItemBlock(ResourceLocation id, Supplier<B> block, Set<K> itemKeys, Function<K, I> factory, SimpleCreativeTab tab) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        Map<K, RegistryObject<I>> items = Maps.newHashMap();
        itemKeys.forEach(k -> {
            RegistryObject<I> item = REGISTRY_ITEM.register(id.getPath() + "_" + k.toString().toLowerCase(), () -> factory.apply(k));
            items.put(k, item);
            tab.register(item);
        });
        return new MultiItemBlockObject<>(blockObject, ImmutableMap.copyOf(items));
    }

    @RequiredArgsConstructor
    public static class BlockObject<B extends Block> {

        private final RegistryObject<B> block;
        private final RegistryObject<Item> item;

        public B block() {
            return block.get();
        }

        public RegistryObject<B> blockObject() {
            return block;
        }

        public Item item() {
            return item.get();
        }

        public RegistryObject<Item> itemObject() {
            return item;
        }
    }

    public record WoodBlockSet(WoodType type,
                               BlockObject<Block> planks,
                               BlockObject<RotatedPillarBlock> log, BlockObject<RotatedPillarBlock> wood,
                               BlockObject<RotatedPillarBlock> strippedLog, BlockObject<RotatedPillarBlock> strippedWood,
                               BlockObject<LeavesBlock> leaves, BlockObject<SaplingBlock> sapling,
                               BlockObject<StairBlock> stairs, BlockObject<SlabBlock> slab,
                               BlockObject<FenceBlock> fence, BlockObject<FenceGateBlock> fenceGate,
                               BlockObject<PressurePlateBlock> pressurePlate, BlockObject<ButtonBlock> button) { }

    @RequiredArgsConstructor
    public static class MultiItemBlockObject<B extends Block, K, I extends Item> {

        private final RegistryObject<B> block;
        private final Map<K, RegistryObject<I>> items;

        public B block() {
            return block.get();
        }

        public RegistryObject<B> blockObject() {
            return block;
        }

        public Item item(K key) {
            return items.get(key).get();
        }

        public Set<I> itemSet() {
            return items.values().stream().map(RegistryObject::get).collect(Collectors.toSet());
        }

        public RegistryObject<I> itemObject(K key) {
            return items.get(key);
        }
    }
}
