package tld.unknown.mystery.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@AutoRegisterCapability
public interface IResearchCapability extends ICapabilitySerializable<CompoundTag> {

    boolean hasResearch(ResourceLocation entry);
    ResearchState.Completion getEntryCompletion(ResourceLocation entry);
    void forgetResearch(ResourceLocation entry);
    void completeResearch(ResourceLocation location, boolean addenda);

    void progressStage(ResourceLocation entry);
    void updateEntryStage(ResourceLocation entry, int stage);
    int getEntryStage(ResourceLocation entry);

    record ResearchState(Completion completion, int stage) {
        public enum Completion {
            UNKNOWN,
            IN_PROGRESS,
            COMPLETED
        }
    }
}
