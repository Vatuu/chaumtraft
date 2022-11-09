package tld.unknown.mystery.registries;

import lombok.AllArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.blocks.CrucibleBlock;
import tld.unknown.mystery.blocks.entities.CrucibleBlockEntity;

import java.util.function.Supplier;

import static tld.unknown.mystery.api.ChaumtraftIDs.Blocks;

public final class ChaumtraftBlocks {

    private static final DeferredRegister<Block> REGISTRY_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Chaumtraft.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> REGISTRY_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Chaumtraft.MOD_ID);
    private static final DeferredRegister<Item> REGISTRY_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static BlockEntityObject<CrucibleBlock, CrucibleBlockEntity> CRUCIBLE = registerBlockEntity(Blocks.CRUCIBLE, CrucibleBlock::new);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY_BLOCKS.register(bus);
        REGISTRY_BLOCK_ENTITIES.register(bus);
        REGISTRY_ITEM.register(bus);
    }

    private static <B extends Block & BlockEntityType.BlockEntitySupplier<E>, E extends BlockEntity> BlockEntityObject<B, E> registerBlockEntity(ResourceLocation id, Supplier<B> block) {
        RegistryObject<B> blockObject = REGISTRY_BLOCKS.register(id.getPath(), block);
        RegistryObject<BlockItem> itemObject = REGISTRY_ITEM.register(id.getPath(), () -> new BlockItem(blockObject.get(), new Item.Properties().stacksTo(64).tab(Chaumtraft.CREATIVE_TAB)));
        RegistryObject<BlockEntityType<E>> blockEntityType = REGISTRY_BLOCK_ENTITIES.register(id.getPath(), () -> BlockEntityType.Builder.of(blockObject.get(), blockObject.get()).build(null));
        return new BlockEntityObject<>(blockObject, itemObject, blockEntityType);
    }

    @AllArgsConstructor
    public static final class BlockEntityObject<B extends Block & BlockEntityType.BlockEntitySupplier<E>, E extends BlockEntity> {

        private final RegistryObject<B> block;
        private final RegistryObject<BlockItem> item;
        private final RegistryObject<BlockEntityType<E>> entityType;

        public B block() {
            return block.get();
        }

        public RegistryObject<B> blockObject() {
            return block;
        }

        public BlockItem item() {
            return item.get();
        }

        public RegistryObject<BlockItem> itemObject() {
            return item;
        }

        public BlockEntityType<E> entityType() {
            return entityType.get();
        }

        public RegistryObject<BlockEntityType<E>> entityTypeObject() {
            return entityType;
        }
    }
}
