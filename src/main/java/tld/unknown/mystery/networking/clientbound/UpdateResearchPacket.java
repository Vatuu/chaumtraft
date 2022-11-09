package tld.unknown.mystery.networking.clientbound;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import tld.unknown.mystery.api.IResearchCapability;
import tld.unknown.mystery.capabilities.ResearchCapability;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;
import tld.unknown.mystery.networking.Packet;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateResearchPacket implements Packet.ClientboundPlay {

    private Action action;
    private ResourceLocation key;
    private int stage;

    public static UpdateResearchPacket update(ResourceLocation loc, int stage) {
        return new UpdateResearchPacket(Action.UPDATE_STAGE, loc, stage);
    }

    public static UpdateResearchPacket forget(ResourceLocation loc) {
        return new UpdateResearchPacket(Action.FORGET, loc, -1);
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeByte(action.ordinal());
        buffer.writeResourceLocation(key);
        if(action == Action.UPDATE_STAGE)
            buffer.writeByte(stage);
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        action = Action.values()[buffer.readByte()];
        key = buffer.readResourceLocation();
        if(action == Action.UPDATE_STAGE)
            stage = buffer.readByte();
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        IResearchCapability cap = Minecraft.getInstance().player.getCapability(ChaumtraftCapabilities.RESEARCH).orElse(new ResearchCapability());
        switch(action) {
            case UPDATE_STAGE -> cap.updateEntryStage(key, stage);
            case FORGET -> cap.forgetResearch(key);
        }
    }

    public enum Action {
        UPDATE_STAGE,
        FORGET
    }
}
