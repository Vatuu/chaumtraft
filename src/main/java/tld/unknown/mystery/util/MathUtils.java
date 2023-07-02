package tld.unknown.mystery.util;

import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

public final class MathUtils {

    public static <E extends Enum<E>> int create8Bitfield(Set<E> values) {
        byte value = 0b00000000;
        for(E item : values) {
            if(item.ordinal() >= 8) {
                continue;
            }
            value |= 1 << item.ordinal();
        }
        return value;
    }

    public static <E extends Enum<E>> Set<E> read8Bitfield(Class<E> enumList, int value) {
        Set<E> set = new HashSet<>();
        for(int i = 0; i < 8 && i < enumList.getEnumConstants().length; i++) {
            E e = enumList.getEnumConstants()[i];
            if((value & 1 << e.ordinal()) != 0) {
                set.add(e);
            }
        }
        return set;
    }

    public static float px(float amount) {
        return 1F / 16 * amount;
    }

    public static Vector3f pxVector3f(float x, float y, float z) {
        return new Vector3f(px(x), px(y), px(z));
    }
}
