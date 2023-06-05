package tld.unknown.mystery.items;

import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import tld.unknown.mystery.Chaumtraft;

public class UpgradeItem extends Item {

    @Getter
    private final byte upgradeBit;

    public UpgradeItem(byte upgradeBit) {
        super(new Properties().rarity(Rarity.RARE).stacksTo(1));
        this.upgradeBit = upgradeBit;
    }

    public boolean isBitSet(byte b) {
        return (b & upgradeBit) != 0;
    }
}
