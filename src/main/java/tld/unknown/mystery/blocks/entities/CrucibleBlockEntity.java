package tld.unknown.mystery.blocks.entities;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.api.IResearchCapability;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;
import tld.unknown.mystery.capabilities.ResearchCapability;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.data.aspects.AspectList;
import tld.unknown.mystery.data.recipes.AlchemyRecipe;
import tld.unknown.mystery.registries.ChaumtraftBlocks;
import tld.unknown.mystery.util.CraftingUtils;
import tld.unknown.mystery.util.FluidHelper;
import tld.unknown.mystery.util.simple.SimpleBlockEntity;

import java.util.Optional;

//TODO: Visuals and Sound
public class CrucibleBlockEntity extends SimpleBlockEntity implements IFluidHandler {

    private static final int MAX_ESSENTIA = 500;
    private static final int HEAT_THRESHOLD = 150;
    private static final int HEAT_MAX = 200;

    private final FluidTank waterTank;
    @Getter
    private final AspectList aspects;

    @Getter
    private int heat;

    public CrucibleBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ChaumtraftBlocks.CRUCIBLE.entityType(), pPos, pBlockState);
        this.waterTank = new FluidTank(FluidType.BUCKET_VOLUME);
        this.aspects = new AspectList();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        CrucibleBlockEntity be = (CrucibleBlockEntity)entity;
        int prevHeat = be.heat;
        if(!level.isClientSide()) {
            if(!FluidHelper.isTankEmpty(be) ) {
                if(level.getBlockState(pos.below()).getTags().anyMatch(tag -> tag == ChaumtraftIDs.Tags.CRUCIBLE_HEATER)) {
                    be.heat += be.heat < HEAT_MAX ? 1 : 0;
                    if(prevHeat < HEAT_THRESHOLD && be.heat >= HEAT_THRESHOLD) {
                        be.sync();
                    }
                } else if(be.heat > 0) {
                    be.heat--;
                    if(be.heat < HEAT_THRESHOLD) {
                        be.sync();
                    }
                }
            } else if(be.heat > 0) {
                be.heat--;
                if(be.heat < HEAT_THRESHOLD) {
                    be.sync();
                }
            }
        }
    }

    @Override
    protected void readNbt(CompoundTag nbt) {
        this.aspects.deserializeNBT(nbt.getCompound("Aspects"));
        this.waterTank.readFromNBT(nbt.getCompound("Water"));
        this.heat = nbt.getShort("Heat");
    }

    @Override
    protected void writeNbt(CompoundTag nbt) {
        nbt.put("Aspects", aspects.serializeNBT());
        nbt.put("Water", waterTank.writeToNBT(new CompoundTag()));
        nbt.putShort("Heat", (short)heat);
    }

    // TODO: Flux Pollution
    public void emptyCrucible() {
        if(!FluidHelper.isTankEmpty(this)) {
            waterTank.setFluid(FluidStack.EMPTY);
            aspects.clear();
            float randomPitch = 1.0F + (getLevel().getRandom().nextFloat() - getLevel().getRandom().nextFloat()) * .3F;
            getLevel().playSound(null, getBlockPos().getX() + .5D, getBlockPos().getY() + .5D, getBlockPos().getZ() + .5D, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, .33F, randomPitch);
            sync();
        }
    }

    // TODO: Crafting Event
    public boolean processInput(ItemStack stack, Player player) {
        boolean crafted = false, consumed = false;
        for(int i = 0; i < stack.getCount(); i++) {
            IResearchCapability research = player != null ? player.getCapability(ChaumtraftCapabilities.RESEARCH).orElse(new ResearchCapability()) : new ResearchCapability();
            Optional<AlchemyRecipe> recipe = CraftingUtils.findRecipe(getLevel(), aspects, stack, research);
            if(recipe.isPresent()) {
                ItemStack result = recipe.get().getResultItem().copy();
                this.aspects.remove(recipe.get().getAspects());
                drain(50, FluidAction.EXECUTE);
                spitItem(result);
                stack.shrink(1);
                crafted = true;
            } else {
                AspectList list = ChaumtraftData.ASPECT_REGISTRY.getAspects(stack);
                if(!list.isEmpty()) {
                    if(aspects.size() + list.size() > MAX_ESSENTIA) {
                        aspects.merge(list.drain(MAX_ESSENTIA - aspects.size()));
                        //TODO Vent list as flux.
                        Chaumtraft.debug("Spilled %d as flux.", list.size());
                    } else {
                        aspects.merge(list);
                    }

                    if(player == null || !player.isCreative()) {
                        stack.shrink(1);
                    }
                    consumed = true;
                }
            }
        }

        if(crafted) {
            Chaumtraft.debug("Alchemy successful.");
        } else if(consumed) {
            Chaumtraft.debug("Item melted down.");
        } else {
            Chaumtraft.debug("Unable to process Item.");
        }

        sync();

        return crafted || consumed;
    }

    public float getFluidPercentage() {
        return FluidHelper.isTankEmpty(this) ? 0 : (float)waterTank.getFluidAmount() / waterTank.getCapacity();
    }

    public float getAspectPercentage() {
        return aspects.isEmpty() ? 0 : (float)aspects.size() / MAX_ESSENTIA;
    }

    public boolean isCooking() {
        return heat >= HEAT_THRESHOLD;
    }

    private void spitItem(ItemStack items) {
        boolean repeatDrop = false;
        while(!items.isEmpty()) {
            ItemStack copy = items.copy();
            if(copy.getCount() > copy.getMaxStackSize())
                copy.setCount(copy.getMaxStackSize());
            items.shrink(copy.getCount());
            double hVel = repeatDrop ? (getLevel().getRandom().nextFloat() - getLevel().getRandom().nextFloat()) * .01F : 0F;
            getLevel().addFreshEntity(new ItemEntity(getLevel(),
                    getBlockPos().getX() + .5F,
                    getBlockPos().getY() + .5F,
                    getBlockPos().getZ() + .5F,
                    copy,
                    hVel, .075D, hVel));
            repeatDrop = true;
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /*                                              Fluid Tank Methods                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Override
    public int getTanks() {
        return waterTank.getTanks();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return waterTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return waterTank.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.fill(resource, action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.drain(maxDrain, action);
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        setChanged();
        return waterTank.drain(resource, action);
    }
}
