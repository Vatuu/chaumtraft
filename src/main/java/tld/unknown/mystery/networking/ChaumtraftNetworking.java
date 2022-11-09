package tld.unknown.mystery.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.networking.clientbound.SyncCapabilityPacket;
import tld.unknown.mystery.networking.clientbound.UpdateResearchPacket;

import java.util.Optional;
import java.util.function.Supplier;

public class ChaumtraftNetworking {

    private static final String PROTOCOL = "0.1";
    private static final ResourceLocation CHANNEL_ID = Chaumtraft.id("network");

    private static int packetId = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(CHANNEL_ID)
            .networkProtocolVersion(() -> PROTOCOL)
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .simpleChannel();

    public static void init() {
        registerPacket(UpdateResearchPacket.class, UpdateResearchPacket::new);
        registerPacket(SyncCapabilityPacket.Research.class, SyncCapabilityPacket.Research::new);
    }

    public static void sendToServer(Packet p) {
        Optional<NetworkDirection> dir = determineDirection(p.getClass());
        if(dir.isPresent() && (dir.get() == NetworkDirection.PLAY_TO_SERVER || dir.get() == NetworkDirection.LOGIN_TO_SERVER))
            CHANNEL.sendToServer(p);
    }

    public static void sendToPlayer(Packet p, Player player) {
        Optional<NetworkDirection> dir = determineDirection(p.getClass());
        if(dir.isPresent() && (dir.get() == NetworkDirection.PLAY_TO_CLIENT || dir.get() == NetworkDirection.LOGIN_TO_CLIENT))
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), p);
    }

    public static void sendToAll(Packet p) {
        Optional<NetworkDirection> dir = determineDirection(p.getClass());
        if(dir.isPresent() && (dir.get() == NetworkDirection.PLAY_TO_CLIENT || dir.get() == NetworkDirection.LOGIN_TO_CLIENT))
            CHANNEL.send(PacketDistributor.ALL.noArg(), p);
    }

    public static <T extends Packet> void registerPacket(Class<T> clazz, Supplier<T> factory) {
        Optional<NetworkDirection> dir = determineDirection(clazz);
        boolean isClient = dir.isPresent() && (dir.get() == NetworkDirection.PLAY_TO_CLIENT || dir.get() == NetworkDirection.LOGIN_TO_CLIENT);
        CHANNEL.registerMessage(packetId++, clazz,
                Packet::encode,
                buffer -> {
                    T packet = factory.get();
                    packet.decode(buffer);
                    return packet;
                },
                isClient ? (packet, ctx) -> {
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> packet.handle(ctx.get()));
                    ctx.get().setPacketHandled(true);
                } : (packet, ctx) -> {
                    ctx.get().enqueueWork(() -> packet.handle(ctx.get()));
                    ctx.get().setPacketHandled(true);
                }, dir);
    }

    private static <T extends Packet> Optional<NetworkDirection> determineDirection(Class<T> clazz) {
        if(clazz.isAssignableFrom(Packet.ClientboundLogin.class))
            return Optional.of(NetworkDirection.LOGIN_TO_CLIENT);
        else if(clazz.isAssignableFrom(Packet.ClientboundPlay.class))
            return Optional.of(NetworkDirection.PLAY_TO_CLIENT);
        else if(clazz.isAssignableFrom(Packet.ServerboundLogin.class))
            return Optional.of(NetworkDirection.LOGIN_TO_SERVER);
        else if(clazz.isAssignableFrom(Packet.ServerboundPlay.class))
            return Optional.of(NetworkDirection.PLAY_TO_SERVER);
        else
            return Optional.empty();
    }
}
