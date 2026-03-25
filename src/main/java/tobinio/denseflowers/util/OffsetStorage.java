package tobinio.denseflowers.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created: 23.08.24
 *
 * @author Tobias Frischmann
 */
public class OffsetStorage {
    public static final ConcurrentHashMap<BlockPos, Vec3> offsets = new ConcurrentHashMap<>();
}
