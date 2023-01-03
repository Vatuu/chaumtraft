package tld.unknown.mystery.util.better;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.compress.utils.Lists;
import tld.unknown.mystery.Chaumtraft;

import java.util.List;

public final class BetterSign {

    private static final String ID = "better_sign";
    private static final List<SignObject> OBJECTS = Lists.newArrayList();

    public static RegistryObject<BlockEntityType<BetterSignBlockEntity>> ENTITY_TYPE;

    private static String MOD_ID;

    public static void init(String modId, IEventBus bus) {
        MOD_ID = modId;
        DeferredRegister<BlockEntityType<?>> registry = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modId);
        ENTITY_TYPE = registry.register(ID, () -> {
            List<SignBlock> signs = Lists.newArrayList();
            OBJECTS.forEach(s -> {
                signs.add(s.getStandingSign());
                signs.add(s.getWallSign());
            });
            Chaumtraft.info("Signs: " + signs.toString());
            return BlockEntityType.Builder.of(BetterSignBlockEntity::new, signs.toArray(new SignBlock[0])).build(null);
        });
        registry.register(bus);
        bus.register(ModBusEvents.class);
    }

    public static void addSign(SignObject object) {
        OBJECTS.add(object);
    }

    public static class ModBusEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> OBJECTS.forEach(sign -> Sheets.SIGN_MATERIALS.put(sign.getWoodType(), new Material(Sheets.SIGN_SHEET, new ResourceLocation(MOD_ID, "entity/signs/" + sign.getWoodType().name())))));
        }

        @SubscribeEvent
        public static void onBlockEntityRendererRegister(EntityRenderersEvent.RegisterRenderers e) {
            e.registerBlockEntityRenderer(ENTITY_TYPE.get(), SignRenderer::new);
        }

        @SubscribeEvent
        public static void onLayerBake(EntityRenderersEvent.RegisterLayerDefinitions e) {
            OBJECTS.forEach(sign -> e.registerLayerDefinition(ModelLayers.createSignModelName(sign.getWoodType()), SignRenderer::createSignLayer));
        }
    }

    public static class BetterSignBlockEntity extends SignBlockEntity {

        public BetterSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
            super(pPos, pBlockState);
        }

        @Override
        public BlockEntityType<?> getType() {
            return ENTITY_TYPE.get();
        }
    }

    public static class BetterStandingSignBlock extends StandingSignBlock {

        public BetterStandingSignBlock(Properties pProperties, WoodType pType) {
            super(pProperties, pType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new BetterSignBlockEntity(pPos, pState);
        }
    }

    public static class BetterWallSignBlock extends WallSignBlock {

        public BetterWallSignBlock(Properties pProperties, WoodType pType) {
            super(pProperties, pType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
            return new BetterSignBlockEntity(pPos, pState);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class SignObject {

        private final WoodType woodType;
        private final RegistryObject<StandingSignBlock> standingSign;
        private final RegistryObject<WallSignBlock> wallSign;
        private final RegistryObject<SignItem> item;

        public StandingSignBlock getStandingSign() {
            return standingSign.get();
        }

        public RegistryObject<StandingSignBlock> getStandingSignObject() {
            return standingSign;
        }

        public WallSignBlock getWallSign() {
            return wallSign.get();
        }

        public RegistryObject<WallSignBlock> getWallSignObject() {
            return wallSign;
        }
    }
}
