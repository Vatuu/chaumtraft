package tld.unknown.mystery.networking.clientbound;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkEvent;
import tld.unknown.mystery.api.capabilities.IResearchCapability;
import tld.unknown.mystery.capabilities.ChaumtraftCapabilities;
import tld.unknown.mystery.networking.Packet;

@RequiredArgsConstructor
public abstract class SyncCapabilityPacket<T extends Tag, C extends INBTSerializable<T>> implements Packet.ClientboundPlay {

    private final Capability<C> capability;
    private T content;

    public SyncCapabilityPacket(Player p, Capability<C> cap) {
        this.capability = cap;
        this.content = p.getCapability(cap).orElseThrow(() -> new IllegalStateException("What?")).serializeNBT();
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        tag.put("content", content);
        buffer.writeNbt(tag);
    }

    @Override
    public void decode(FriendlyByteBuf buffer) {
        this.content = (T)buffer.readNbt().get("content");
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        Player p = Minecraft.getInstance().player;
        p.getCapability(capability).ifPresent(c -> c.deserializeNBT(content));
    }

    public static class Research extends SyncCapabilityPacket<CompoundTag, IResearchCapability> {
        public Research() { super(ChaumtraftCapabilities.RESEARCH); }
        public Research(Player p) { super(p, ChaumtraftCapabilities.RESEARCH); }
    }
}
