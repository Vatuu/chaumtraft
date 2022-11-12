package tld.unknown.mystery.registries;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tld.unknown.mystery.Chaumtraft;
import tld.unknown.mystery.api.ChaumtraftIDs;
import tld.unknown.mystery.entities.TrunkEntity;
import tld.unknown.mystery.util.ReflectionUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Chaumtraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ChaumtraftEntities {

    private static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Chaumtraft.MOD_ID);

    /* -------------------------------------------------------------------------------------------------------------- */

    public static LivingEntityObject<TrunkEntity> LIVING_TRUNK = registerLiving(ChaumtraftIDs.Entities.TRAVELING_TRUNK, TrunkEntity::new, MobCategory.MISC, .875F, .875F,
            s -> { },
            mobAttributes(a -> a.add(Attributes.MAX_HEALTH, 50)));

    /* -------------------------------------------------------------------------------------------------------------- */

    public static void init(IEventBus bus) { REGISTRY.register(bus); }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(ResourceLocation id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, Consumer<EntityType.Builder<T>> builder) {
        Supplier<EntityType<T>> supplier = () -> {
            EntityType.Builder<T> b = EntityType.Builder.of(factory, category).sized(width, height);
            builder.accept(b);
            return b.build(id.getPath());
        };
        return REGISTRY.register(id.getPath(), supplier);
    }

    private static <T extends LivingEntity> LivingEntityObject<T> registerLiving(ResourceLocation id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, Consumer<EntityType.Builder<T>> builder, Supplier<AttributeSupplier> attributes) {
        Supplier<EntityType<T>> supplier = () -> {
            EntityType.Builder<T> b = EntityType.Builder.of(factory, category).sized(width, height);
            builder.accept(b);
            return b.build(id.getPath());
        };
        return new LivingEntityObject<>(REGISTRY.register(id.getPath(), supplier), attributes);
    }

    private static Supplier<AttributeSupplier> livingAttributes(Consumer<AttributeSupplier.Builder> additionalAttributes) {
        return () -> {
            AttributeSupplier.Builder supplier = LivingEntity.createLivingAttributes();
            additionalAttributes.accept(supplier);
            return supplier.build();
        };
    }

    private static Supplier<AttributeSupplier> mobAttributes(Consumer<AttributeSupplier.Builder> additionalAttributes) {
        return () -> {
            AttributeSupplier.Builder supplier = Mob.createMobAttributes();
            additionalAttributes.accept(supplier);
            return supplier.build();
        };
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent e) {
        ReflectionUtils.getAllStaticsOfType(ChaumtraftEntities.class, LivingEntityObject.class).forEach(obj -> e.put(obj.entityType(), obj.getAttributes()));
    }

    @RequiredArgsConstructor
    public static class EntityObject<T extends Entity> {

        private final RegistryObject<EntityType<T>> typeObject;

        public EntityType<T> entityType() {
            return typeObject.get();
        }
    }

    public static class LivingEntityObject<T extends LivingEntity> extends EntityObject<T> {

        private final Supplier<AttributeSupplier> attributes;

        public LivingEntityObject(RegistryObject<EntityType<T>> typeObject, Supplier<AttributeSupplier> attributes) {
            super(typeObject);
            this.attributes = attributes;
        }

        public AttributeSupplier getAttributes() {
            return attributes.get();
        }
    }
}
