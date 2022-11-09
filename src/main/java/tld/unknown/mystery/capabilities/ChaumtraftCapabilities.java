package tld.unknown.mystery.capabilities;

import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.IResearchCapability;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.networking.ChaumtraftNetworking;
import tld.unknown.mystery.networking.clientbound.SyncCapabilityPacket;

public class ChaumtraftCapabilities {

    public static final Capability<IResearchCapability> RESEARCH = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        e.getEntity().getCapability(ChaumtraftCapabilities.RESEARCH).ifPresent(r -> {
            if(Chaumtraft.isDev())
                r.completeResearch(ChaumtraftIDs.Research.UNLOCK_DEBUG, true);
            else
                r.forgetResearch(ChaumtraftIDs.Research.UNLOCK_DEBUG);
        });
        ChaumtraftNetworking.sendToPlayer(new SyncCapabilityPacket.Research(e.getEntity()), e.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone e) {
        copyCapability(RESEARCH, e.getOriginal(), e.getEntity());
    }

    @SubscribeEvent
    public static void onCapabilityAttach(AttachCapabilitiesEvent<Entity> e) {
        if(e.getObject() instanceof Player) {
            e.addCapability(ChaumtraftIDs.Capabilities.RESEARCH, new ResearchCapability());
        }
    }

    private static <T extends Tag, C extends INBTSerializable<T>> void copyCapability(Capability<C> capability, Player original, Player p) {
        C old = original.getCapability(capability).orElseThrow(() -> new IllegalStateException("Serializable capability is null!"));
        p.getCapability(capability).orElseThrow(() -> new IllegalStateException("Serializable capability is null!")).deserializeNBT(old.serializeNBT());
    }
}
