package tld.unknown.mystery.data.generator.providers;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.blocks.CrystalBlock;
import tld.unknown.mystery.blocks.TubeBlock;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.better.BetterSign;

import java.util.EnumMap;
import java.util.Map;

import static tld.unknown.mystery.api.ChaumtraftIDs.*;

public class BlockDataProvider extends BlockStateProvider {

    public BlockDataProvider(PackOutput gen, ExistingFileHelper exFileHelper) {
        super(gen, Chaumtraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ChaumtraftBlocks.CRUCIBLE.block(), models().getExistingFile(Chaumtraft.id("block/crucible")));
        simpleBlockItem(ChaumtraftBlocks.CRUCIBLE.block(), models().getExistingFile(Chaumtraft.id("block/crucible")));
        simpleBlock(ChaumtraftBlocks.ARCANE_WORKBENCH.block(), models().getExistingFile(Chaumtraft.id("block/arcane_workbench")));
        simpleBlockItem(ChaumtraftBlocks.ARCANE_WORKBENCH.block(), models().getExistingFile(Chaumtraft.id("block/arcane_workbench")));

        simpleBlock(ChaumtraftBlocks.ARCANE_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_arcane")));
        simpleBlockItem(ChaumtraftBlocks.ARCANE_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_arcane")));
        simpleBlock(ChaumtraftBlocks.ANCIENT_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_ancient")));
        simpleBlockItem(ChaumtraftBlocks.ANCIENT_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_ancient")));
        simpleBlock(ChaumtraftBlocks.ELDRITCH_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_eldritch")));
        simpleBlockItem(ChaumtraftBlocks.ELDRITCH_PEDESTAL.block(), models().getExistingFile(Chaumtraft.id("block/pedestal_eldritch")));

        registerDirectionalMultipart(ChaumtraftBlocks.TUBE.block(), TubeBlock.BY_DIRECTION, Chaumtraft.id("block/tube_generic_center"), Chaumtraft.id("block/tube_generic_side"), true);
        itemModels().basicItem(ChaumtraftBlocks.TUBE.item());

        ModelBuilder<?> filledPhial = itemModels().withExistingParent(Items.PHIAL.getPath() + "_filled", "item/generated")
                .texture("layer0", "item/phial")
                .texture("layer1", "item/phial_overlay");

        ModelFile CRYSTAL_0 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage0"));
        ModelFile CRYSTAL_1 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage1"));
        ModelFile CRYSTAL_2 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage2"));

        getVariantBuilder(ChaumtraftBlocks.CRYSTAL_COLONY.block()).forAllStates((bs) -> getCrystalModels(bs.getValue(CrystalBlock.FACING), switch(bs.getValue(CrystalBlock.SIZE)) {
            case 1 -> CRYSTAL_1;
            case 2 -> CRYSTAL_2;
            default -> CRYSTAL_0;
        }));

        itemModels().basicItem(Items.ESSENTIA_RESONATOR);

        itemModels().basicItem(Items.VIS_CRYSTAL);
        itemModels().basicItem(ChaumtraftIDs.Blocks.CRYSTAL_COLONY);

        itemModels().basicItem(Items.PHIAL)
                .override()
                .predicate(ItemProperties.ASPECT_HOLDER_PRESENT, 1F)
                .model(filledPhial)
                .end();

        registerWoodType(ChaumtraftBlocks.SILVERWOOD);
        registerWoodType(ChaumtraftBlocks.GREATWOOD);
    }

    private ConfiguredModel[] getCrystalModels(Direction dir, ModelFile model) {
        ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(model);
        Direction.Axis axis = dir.getAxis();
        switch(axis) {
            case X -> builder.rotationX(90).rotationY(dir.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 90 : -90);
            case Y -> builder.rotationX(dir.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 180 : 0);
            case Z -> builder.rotationX(dir.getAxisDirection() == Direction.AxisDirection.POSITIVE ? -90 : 90);
        }
        return builder.build();
    }

    private void registerSimpleBlock(RegistryObject<? extends Block> block) {
        simpleBlock(block.get());
        simpleBlockItem(block.get(), itemModels().getExistingFile(new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath())));
    }

    private void registerLog(RegistryObject<? extends RotatedPillarBlock> block, RegistryObject<? extends RotatedPillarBlock> wood) {
        axisBlock(block.get(),
                new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath()),
                new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath() + "_top"));
        simpleBlockItem(block.get(), itemModels().getExistingFile(new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath())));
        axisBlock(wood.get(),
                new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath()),
                new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath()));
        simpleBlockItem(wood.get(), itemModels().getExistingFile(new ResourceLocation(wood.getId().getNamespace(), "block/" + wood.getId().getPath())));
    }

    private void registerStairSlab(RegistryObject<? extends StairBlock> stairs, RegistryObject<? extends SlabBlock> slab, ResourceLocation texture) {
        texture = new ResourceLocation(texture.getNamespace(), "block/" + texture.getPath());
        stairsBlock(stairs.get(), texture);
        simpleBlockItem(stairs.get(), itemModels().getExistingFile(new ResourceLocation(stairs.getId().getNamespace(), "block/" + stairs.getId().getPath())));
        slabBlock(slab.get(), texture, texture);
        simpleBlockItem(slab.get(), itemModels().getExistingFile(new ResourceLocation(slab.getId().getNamespace(), "block/" + slab.getId().getPath())));
    }

    private void registerDoors(RegistryObject<? extends DoorBlock> door, RegistryObject<? extends TrapDoorBlock> trapdoor) {
        doorBlockWithRenderType(door.get(), new ResourceLocation(door.getId().getNamespace(), "block/" + door.getId().getPath() + "_bottom"), new ResourceLocation(door.getId().getNamespace(), "block/" + door.getId().getPath() + "_top"), "translucent");
        itemModels().basicItem(door.get().asItem());
        trapdoorBlockWithRenderType(trapdoor.get(), new ResourceLocation(trapdoor.getId().getNamespace(), "block/" + trapdoor.getId().getPath()), true, "translucent");
        simpleBlockItem(trapdoor.get(), itemModels().getExistingFile(new ResourceLocation(trapdoor.getId().getNamespace(), "block/" + trapdoor.getId().getPath() + "_bottom")));
    }

    private void registerFences(RegistryObject<? extends FenceBlock> fence, RegistryObject<? extends FenceGateBlock> gate, ResourceLocation texture) {
        texture = new ResourceLocation(texture.getNamespace(), "block/" + texture.getPath());
        fenceBlock(fence.get(), texture);
        simpleBlockItem(fence.get(), models().fenceInventory(fence.getId().getPath() + "_inventory", texture));
        fenceGateBlock(gate.get(), texture);
        simpleBlockItem(gate.get(), itemModels().getExistingFile(new ResourceLocation(gate.getId().getNamespace(), "block/" + gate.getId().getPath())));
    }

    private void registerPressables(RegistryObject<? extends PressurePlateBlock> plate, RegistryObject<? extends ButtonBlock> button, ResourceLocation texture) {
        texture = new ResourceLocation(texture.getNamespace(), "block/" + texture.getPath());
        pressurePlateBlock(plate.get(), texture);
        simpleBlockItem(plate.get(), itemModels().getExistingFile(new ResourceLocation(plate.getId().getNamespace(), "block/" + plate.getId().getPath())));
        buttonBlock(button.get(), texture);
        simpleBlockItem(button.get(), models().buttonInventory(button.getId().getPath() + "_inventory", texture));
    }

    private void registerPlant(RegistryObject<? extends Block> block) {
        models().cross(block.getId().getPath(), new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath()));
        simpleBlockItem(block.get(), basicBlockItem(block.getId()));
    }

    private void registerSign(BetterSign.SignObject sign) {
        signBlock(sign.getStandingSign(), sign.getWallSign(), Chaumtraft.id("entity/signs/" + sign.getWoodType().name()));
        itemModels().basicItem(sign.getItem().get());
    }

    public ItemModelBuilder basicBlockItem(ResourceLocation item) {
        return itemModels().getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "block/" + item.getPath()));
    }

    private void registerWoodType(ChaumtraftBlocks.WoodBlockSet type) {
        registerSimpleBlock(type.planks().blockObject());
        registerSimpleBlock(type.leaves().blockObject());

        registerPlant(type.sapling().blockObject());

        registerLog(type.log().blockObject(), type.wood().blockObject());
        registerLog(type.strippedLog().blockObject(), type.strippedWood().blockObject());

        registerStairSlab(type.stairs().blockObject(), type.slab().blockObject(), type.planks().blockObject().getId());

        registerFences(type.fence().blockObject(), type.fenceGate().blockObject(), type.planks().blockObject().getId());
        registerPressables(type.pressurePlate().blockObject(), type.button().blockObject(), type.planks().blockObject().getId());
    }

    private void registerDirectionalMultipart(Block block, Map<Direction, BooleanProperty> dirProperties, ResourceLocation centerPart, ResourceLocation sidePart, boolean hideCenter) {
        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        for(Direction dir : Direction.values()) {
            MultiPartRotation rot = MultiPartRotation.BY_DIRECTION.get(dir);
            multipartBuilder.part()
                    .modelFile(models().getExistingFile(sidePart))
                    .rotationX(rot.xRotation()).rotationY(rot.yRotation())
                    .addModel()
                    .condition(dirProperties.get(dir), true)
                    .end();
        }

        MultiPartBlockStateBuilder.PartBuilder builder = multipartBuilder.part()
                .modelFile(models().getExistingFile(centerPart))
                .addModel();
        if(hideCenter) {
            builder.useOr();
            for(Direction dir : Direction.values()) {
                builder.condition(dirProperties.get(dir), false);
            }
        }
        builder.end();
    }

    private record MultiPartRotation(int xRotation, int yRotation) {
        public static final Map<Direction, MultiPartRotation> BY_DIRECTION = new EnumMap<>(ImmutableMap.of(
                Direction.NORTH, new MultiPartRotation(-90, 0),
                Direction.EAST, new MultiPartRotation(-90, 90),
                Direction.SOUTH, new MultiPartRotation(90, 0),
                Direction.WEST, new MultiPartRotation(90, 90),
                Direction.UP, new MultiPartRotation(180, 0),
                Direction.DOWN, new MultiPartRotation(0, 0)));
    }
}
