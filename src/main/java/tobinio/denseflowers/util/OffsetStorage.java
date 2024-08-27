package tobinio.denseflowers.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created: 23.08.24
 *
 * @author Tobias Frischmann
 */
public class OffsetStorage {
    public static final ConcurrentHashMap<BlockPos, Vec3d> offsets = new ConcurrentHashMap<>();
}
