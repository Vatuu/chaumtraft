package tld.unknown.mystery.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface Packet {

    void encode(FriendlyByteBuf buffer);
    void decode(FriendlyByteBuf buffer);

    void handle(NetworkEvent.Context context);

    interface ClientboundLogin extends Packet { }
    interface ClientboundPlay extends Packet { }
    interface ServerboundLogin extends Packet { }
    interface ServerboundPlay extends Packet { }
}
