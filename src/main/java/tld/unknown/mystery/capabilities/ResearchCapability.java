package tld.unknown.mystery.capabilities;

import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tld.unknown.mystery.api.capabilities.IResearchCapability;
import tld.unknown.mystery.data.ChaumtraftData;
import tld.unknown.mystery.data.research.ResearchEntry;

import java.util.Map;
import java.util.Optional;

public class ResearchCapability implements IResearchCapability {

    private final LazyOptional<IResearchCapability> HOLDER;

    private Map<ResourceLocation, ResearchState> stateMap = Maps.newHashMap();

    public ResearchCapability() {
        HOLDER = LazyOptional.of(() -> this);
    }

    @Override
    public boolean hasResearch(ResourceLocation entry) {
        return getEntryCompletion(entry) == ResearchState.Completion.COMPLETED;
    }

    @Override
    public void completeResearch(ResourceLocation entry, boolean addenda) {
        //TODO Update nodes, revoke recipes etc
        getEntry(entry).ifPresent(e -> {
            int stage = addenda ? e.getMaxStages() : e.getCompleteStages();
            stateMap.compute(entry, (rl, state) -> new ResearchState(ResearchState.Completion.COMPLETED, stage));
        });
    }

    @Override
    public ResearchState.Completion getEntryCompletion(ResourceLocation entry) {
        if(!stateMap.containsKey(entry))
            return ResearchState.Completion.UNKNOWN;
        return stateMap.get(entry).completion();
    }

    @Override
    public void forgetResearch(ResourceLocation entry) {
        stateMap.remove(entry);
        //TODO Update nodes, revoke recipes etc
    }

    @Override
    public void progressStage(ResourceLocation entry) {
        //TODO Update nodes, revoke recipes etc
        getEntry(entry).ifPresent(e -> {
            int completion = e.getCompleteStages();
            int max = e.getMaxStages();
            stateMap.compute(entry, (rl, state) -> {
                if(state != null) {
                    int n = state.stage() + 1;
                    ResearchState.Completion c = n >= completion ? ResearchState.Completion.COMPLETED : ResearchState.Completion.IN_PROGRESS;
                    return new ResearchState(c, Math.min(n, max));
                }
                return new ResearchState(ResearchState.Completion.IN_PROGRESS, 0);
            });
        });
    }

    @Override
    public void updateEntryStage(ResourceLocation entry, int stage) {
        // TODO Get entry and max stage to check completion
        //TODO Update nodes, grant recipes etc
        stateMap.compute(entry, (k, v) -> new ResearchState(ResearchState.Completion.IN_PROGRESS, stage));
    }

    @Override
    public int getEntryStage(ResourceLocation entry) {
        if(!stateMap.containsKey(entry))
            return 0;
        return stateMap.get(entry).stage();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag root = new CompoundTag();
        // States
        CompoundTag stateTag = new CompoundTag();
        stateMap.forEach((k, v) -> {
            if(v.completion() != ResearchState.Completion.UNKNOWN) {
                CompoundTag tag = new CompoundTag();
                tag.put("completion", ByteTag.valueOf((byte)v.completion().ordinal()));
                tag.put("stage", ByteTag.valueOf((byte)v.stage()));
                stateTag.put(k.toString(), tag);
            }
        });
        root.put("research", stateTag);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Map<ResourceLocation, ResearchState> state = Maps.newHashMap();
        CompoundTag stateTag = nbt.getCompound("research");
        stateTag.getAllKeys().forEach(k -> {
            CompoundTag tag = stateTag.getCompound(k);
            ResearchState.Completion c = ResearchState.Completion.values()[tag.getByte("completion")];
            if(c != ResearchState.Completion.UNKNOWN) {
                int stage = tag.getInt("stage");
                state.put(ResourceLocation.tryParse(k), new ResearchState(c, stage));
            }
        });
        this.stateMap = state;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ChaumtraftCapabilities.RESEARCH.orEmpty(cap, HOLDER.cast());
    }

    private Optional<ResearchEntry> getEntry(ResourceLocation location) {
        return ChaumtraftData.RESEARCH_ENTRIES.getOptional(location);
    }
}
