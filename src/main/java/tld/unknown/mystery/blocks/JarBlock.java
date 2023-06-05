package tld.unknown.mystery.blocks;

import lombok.Getter;
import net.minecraft.world.level.material.Material;
import tld.unknown.mystery.blocks.entities.JarBlockEntity;
import tld.unknown.mystery.registries.ChaumtraftBlockEntities;
import tld.unknown.mystery.util.simple.SimpleEntityBlock;

public class JarBlock extends SimpleEntityBlock<JarBlockEntity> {

    @Getter
    private final boolean isVoid;

    public JarBlock(boolean isVoid) {
        super(Properties.of(Material.GLASS), ChaumtraftBlockEntities.JAR.entityTypeObject());
        this.isVoid = isVoid;
    }
}
