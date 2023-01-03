package tld.unknown.mystery.data.generator.models;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.blocks.CrystalBlock;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.better.BetterSign;

import static tld.unknown.mystery.api.ChaumtraftIDs.*;

public class BlockDataProvider extends BlockStateProvider {

    public BlockDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Chaumtraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ChaumtraftBlocks.CRUCIBLE.block(), models().getExistingFile(Chaumtraft.id("block/crucible")));
        simpleBlockItem(ChaumtraftBlocks.CRUCIBLE.block(), models().getExistingFile(Chaumtraft.id("block/crucible")));
        simpleBlock(ChaumtraftBlocks.ARCANE_WORKBENCH.block(), models().getExistingFile(Chaumtraft.id("block/arcane_workbench")));
        simpleBlockItem(ChaumtraftBlocks.ARCANE_WORKBENCH.block(), models().getExistingFile(Chaumtraft.id("block/arcane_workbench")));

        ModelBuilder<?> filledPhial = itemModels().withExistingParent(Items.PHIAL.getPath() + "_filled", "item/generated")
                .texture("layer0", "item/phial")
                .texture("layer1", "item/phial_overlay");

        ModelFile CRYSTAL_0 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage0"));
        ModelFile CRYSTAL_1 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage1"));
        ModelFile CRYSTAL_2 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage2"));
        ModelFile CRYSTAL_3 = models().getExistingFile(Chaumtraft.id("block/vis_crystals_ground_stage2"));

        getVariantBuilder(ChaumtraftBlocks.CRYSTAL_COLONY.block()).forAllStates((bs) -> getCrystalModels(bs.getValue(CrystalBlock.FACING), switch(bs.getValue(CrystalBlock.SIZE)) {
            case 1 -> CRYSTAL_1;
            case 2 -> CRYSTAL_2;
            case 3 -> CRYSTAL_2;
            default -> CRYSTAL_0;
        }));

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
        registerSimpleBlock(type.getPlanks().blockObject());
        registerSimpleBlock(type.getLeaves().blockObject());

        registerPlant(type.getSapling().blockObject());

        registerLog(type.getLog().blockObject(), type.getWood().blockObject());
        registerLog(type.getStrippedLog().blockObject(), type.getStrippedWood().blockObject());

        registerStairSlab(type.getStairs().blockObject(), type.getSlab().blockObject(), type.getPlanks().blockObject().getId());

        registerFences(type.getFence().blockObject(), type.getFenceGate().blockObject(), type.getPlanks().blockObject().getId());
        registerPressables(type.getPressurePlate().blockObject(), type.getButton().blockObject(), type.getPlanks().blockObject().getId());
    }
}
