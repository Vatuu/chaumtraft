package tld.unknown.mystery.registries;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.ArcaneWorkbenchBlock;
import tld.unknown.mystery.blocks.CrucibleBlock;
import tld.unknown.mystery.blocks.CrystalBlock;
import tld.unknown.mystery.blocks.entities.ArcaneWorkbenchBlockEntity;
import tld.unknown.mystery.blocks.entities.CrucibleBlockEntity;
import tld.unknown.mystery.items.blocks.CrystalBlockItem;
import tld.unknown.mystery.util.better.BetterSign;

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

    public static final BlockObject<CrystalBlock> CRYSTAL_COLONY = registerBlock(Blocks.CRYSTAL_COLONY, CrystalBlock::new, CrystalBlockItem::new);

    public static final WoodBlockSet GREATWOOD = registerWoodType(Blocks.GREATWOOD);
    public static final WoodBlockSet SILVERWOOD = registerWoodType(Blocks.SILVERWOOD);

    public static final BlockEntityObject<ArcaneWorkbenchBlock, ArcaneWorkbenchBlockEntity> ARCANE_WORKBENCH = registerBlockEntity(Blocks.ARCANE_WORKBENCH, ArcaneWorkbenchBlock::new);
    public static final BlockEntityObject<CrucibleBlock, CrucibleBlockEntity> CRUCIBLE = registerBlockEntity(Blocks.CRUCIBLE, CrucibleBlock::new);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY_BLOCKS.register(bus);
        REGISTRY_BLOCK_ENTITIES.register(bus);
        REGISTRY_ITEM.register(bus);
    }

    private static <B extends Block> BlockObject<B> registerBlock(ResourceLocation id, Supplier<B> block) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), () -> new BlockItem(blockObject.get(), new Item.Properties().stacksTo(64).tab(Chaumtraft.CREATIVE_TAB)));
        return new BlockObject<>(blockObject, itemObject);
    }

    private static <B extends Block> BlockObject<B> registerBlock(ResourceLocation id, Supplier<B> block, Supplier<Item>blockItem) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), blockItem);
        return new BlockObject<>(blockObject, itemObject);
    }

    //TODO Tree Grower
    private static WoodBlockSet registerWoodType(ResourceLocation id) {
        final BlockBehaviour.Properties props = BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD);
        BlockObject<Block> planks = registerBlock(Chaumtraft.id(id.getPath() + "_planks"), () -> new Block(props));
        BlockObject<RotatedPillarBlock> log = registerBlock(Chaumtraft.id(id.getPath() + "_log"), () -> new RotatedPillarBlock(props));
        BlockObject<RotatedPillarBlock> wood = registerBlock(Chaumtraft.id(id.getPath() + "_wood"), () -> new RotatedPillarBlock(props));
        BlockObject<RotatedPillarBlock> logStripped = registerBlock(Chaumtraft.id("stripped_" + id.getPath() + "_log"), () -> new RotatedPillarBlock(props));
        BlockObject<RotatedPillarBlock> woodStripped = registerBlock(Chaumtraft.id("stripped_" + id.getPath() + "_wood"), () -> new RotatedPillarBlock(props));

        BlockObject<LeavesBlock> leaves = registerBlock(Chaumtraft.id(id.getPath() + "_leaves"), () -> new LeavesBlock(
                BlockBehaviour.Properties.of(Material.LEAVES)
                .strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
                .isValidSpawn((state, level, pos, type) -> type == EntityType.PARROT)
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)));
        BlockObject<SaplingBlock> sapling = registerBlock(Chaumtraft.id(id.getPath() + "_sapling"), () -> new SaplingBlock(
                null,
                BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

        BlockObject<StairBlock> stairs = registerBlock(Chaumtraft.id(id.getPath() + "_stairs"), () -> new StairBlock(() -> planks.block().defaultBlockState(), props));
        BlockObject<SlabBlock> slab = registerBlock(Chaumtraft.id(id.getPath() + "_slab"), () -> new SlabBlock(props));

        BlockObject<FenceBlock> fence = registerBlock(Chaumtraft.id(id.getPath() + "_fence"), () -> new FenceBlock(props));
        BlockObject<FenceGateBlock> fenceGate = registerBlock(Chaumtraft.id(id.getPath() + "_fence_gate"), () -> new FenceGateBlock(props));

        BlockObject<PressurePlateBlock> pressurePlate = registerBlock(Chaumtraft.id(id.getPath() + "_pressure_plate"), () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, props));
        BlockObject<WoodButtonBlock> button = registerBlock(Chaumtraft.id(id.getPath() + "_button"), () -> new WoodButtonBlock(props));

        return new WoodBlockSet(planks, log, wood, logStripped, woodStripped, leaves, sapling, stairs, slab,fence, fenceGate, pressurePlate, button);
    }

    private static BetterSign.SignObject registerSign(ResourceLocation id) {
        BlockBehaviour.Properties signProps = BlockBehaviour.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD);
        WoodType type = WoodType.create(id.getPath());
        RegistryObject<StandingSignBlock> standingSign = REGISTRY_BLOCKS.register(id.getPath() + "_sign", () -> new BetterSign.BetterStandingSignBlock(signProps, type));
        RegistryObject<WallSignBlock> wallSign = REGISTRY_BLOCKS.register(id.getPath() + "_wall_sign", () -> new BetterSign.BetterWallSignBlock(signProps, type));
        RegistryObject<SignItem> signItem = REGISTRY_ITEM.register(id.getPath() + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(Chaumtraft.CREATIVE_TAB), standingSign.get(), wallSign.get()));
        BetterSign.SignObject obj = new BetterSign.SignObject(type, standingSign, wallSign, signItem);
        BetterSign.addSign(obj);
        return obj;
    }

    private static <B extends Block, K, I extends Item> MultiItemBlockObject<B, K, I> registerMultiItemBlock(ResourceLocation id, Supplier<B> block, Set<K> itemKeys, Function<K, I> factory) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        Map<K, RegistryObject<I>> items = Maps.newHashMap();
        itemKeys.forEach(k -> items.put(k, REGISTRY_ITEM.register(id.getPath() + "_" + k.toString().toLowerCase(), () -> factory.apply(k))));
        return new MultiItemBlockObject<>(blockObject, ImmutableMap.copyOf(items));
    }

    private static <B extends Block & BlockEntityType.BlockEntitySupplier<E>, E extends BlockEntity> BlockEntityObject<B, E> registerBlockEntity(ResourceLocation id, Supplier<B> block) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), () -> new BlockItem(blockObject.get(), new Item.Properties().stacksTo(64).tab(Chaumtraft.CREATIVE_TAB)));
        RegistryObject<BlockEntityType<E>> blockEntityType = REGISTRY_BLOCK_ENTITIES.register(id.getPath(), () -> BlockEntityType.Builder.of(blockObject.get(), blockObject.get()).build(null));
        return new BlockEntityObject<>(blockObject, itemObject, blockEntityType);
    }

    private static <B extends Block & BlockEntityType.BlockEntitySupplier<E>, E extends BlockEntity> BlockEntityObject<B, E> registerBlockEntity(ResourceLocation id, Supplier<B> block, Supplier<Item> blockItem) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<Item> itemObject = REGISTRY_ITEM.register(id.getPath(), blockItem);
        RegistryObject<BlockEntityType<E>> blockEntityType = REGISTRY_BLOCK_ENTITIES.register(id.getPath(), () -> BlockEntityType.Builder.of(blockObject.get(), blockObject.get()).build(null));
        return new BlockEntityObject<>(blockObject, itemObject, blockEntityType);
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

    @Getter
    @RequiredArgsConstructor
    public static class WoodBlockSet {

        private final BlockObject<Block> planks;
        private final BlockObject<RotatedPillarBlock> log, wood, strippedLog, strippedWood;

        private final BlockObject<LeavesBlock> leaves;
        private final BlockObject<SaplingBlock> sapling;

        private final BlockObject<StairBlock> stairs;
        private final BlockObject<SlabBlock> slab;

        private final BlockObject<FenceBlock> fence;
        private final BlockObject<FenceGateBlock> fenceGate;

        private final BlockObject<PressurePlateBlock> pressurePlate;
        private final BlockObject<WoodButtonBlock> button;

    }

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

        public Item items(K key) {
            return items.get(key).get();
        }

        public Set<I> itemSet() {
            return items.values().stream().map(RegistryObject::get).collect(Collectors.toSet());
        }

        public RegistryObject<I> itemsObject(K key) {
            return items.get(key);
        }
    }

    public static class BlockEntityObject<B extends Block & BlockEntityType.BlockEntitySupplier<E>, E extends BlockEntity> extends BlockObject<B> {

        private final RegistryObject<BlockEntityType<E>> entityType;

        public BlockEntityObject(RegistryObject<B> block, RegistryObject<Item> item, RegistryObject<BlockEntityType<E>> entityType) {
            super(block, item);
            this.entityType = entityType;
        }

        public BlockEntityType<E> entityType() {
            return entityType.get();
        }

        public RegistryObject<BlockEntityType<E>> entityTypeObject() {
            return entityType;
        }
    }
}
