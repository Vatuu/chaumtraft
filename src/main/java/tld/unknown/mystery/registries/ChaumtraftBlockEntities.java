package tld.unknown.mystery.registries;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.blocks.RunicMatrixBlock;
import tld.unknown.mystery.blocks.entities.*;

import java.util.Arrays;

import static tld.unknown.mystery.api.ChaumtraftIDs.BlockEntities;

public final class ChaumtraftBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> REGISTRY_BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static final BlockEntityObject<CrucibleBlockEntity> CRUCIBLE = register(BlockEntities.CRUCIBLE,
            CrucibleBlockEntity::new,
            ChaumtraftBlocks.CRUCIBLE);

    public static final BlockEntityObject<ArcaneWorkbenchBlockEntity> ARCANE_WORKBENCH = register(BlockEntities.ARCANE_WORKBENCH,
            ArcaneWorkbenchBlockEntity::new,
            ChaumtraftBlocks.ARCANE_WORKBENCH);

    public static final BlockEntityObject<RunicMatrixBlockEntity> RUNIC_MATRIX = register(BlockEntities.RUNIC_MATRIX,
            RunicMatrixBlockEntity::new,
            ChaumtraftBlocks.RUNIC_MATRIX);

    public static final BlockEntityObject<PedestalBlockEntity> PEDESTAL = register(BlockEntities.PEDESTAL,
            PedestalBlockEntity::new,
            ChaumtraftBlocks.ARCANE_PEDESTAL, ChaumtraftBlocks.ANCIENT_PEDESTAL, ChaumtraftBlocks.ELDRITCH_PEDESTAL);

    public static final BlockEntityObject<JarBlockEntity> JAR = register(BlockEntities.JAR,
            JarBlockEntity::new,
            ChaumtraftBlocks.WARDED_JAR, ChaumtraftBlocks.VOID_JAR);

    public static final BlockEntityObject<TubeBlockEntity> TUBE = register(BlockEntities.TUBE,
            TubeBlockEntity::new,
            ChaumtraftBlocks.TUBE);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) {
        REGISTRY_BLOCK_ENTITIES.register(bus);
    }

    @SafeVarargs
    private static <E extends BlockEntity> BlockEntityObject<E> register(ResourceLocation id, BlockEntityType.BlockEntitySupplier<E> supplier, ChaumtraftBlocks.BlockObject<? extends Block>... validBlocks) {
        return new BlockEntityObject<>(REGISTRY_BLOCK_ENTITIES.register(id.getPath(), () -> {
            Block[] blocks = Arrays.stream(validBlocks).map(ChaumtraftBlocks.BlockObject::block).toArray(Block[]::new);
            return BlockEntityType.Builder.of(supplier, blocks).build(null);
        }));
    }

    @RequiredArgsConstructor
    public static class BlockEntityObject<E extends BlockEntity> {

        private final RegistryObject<BlockEntityType<E>> type;

        public BlockEntityType<E> entityType() {
            return type.get();
        }

        public RegistryObject<BlockEntityType<E>> entityTypeObject() {
            return type;
        }
    }
}
