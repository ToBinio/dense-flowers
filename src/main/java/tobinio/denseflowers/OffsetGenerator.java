package tobinio.denseflowers;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import tobinio.denseflowers.mixin.AbstractBlockAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
public class OffsetGenerator {

    public static List<Vec3d> getFlowerOffsets(BlockState blockState, BlockView world, BlockPos flowerPos) {
        var baseLocation = blockState.getModelOffset(flowerPos);
        AbstractBlockAccessor flower = (AbstractBlockAccessor) blockState.getBlock();

        var allLocations = new ArrayList<Vec3d>();
        allLocations.add(baseLocation);

        var locations = new ArrayList<Vec3d>();

        outer:
        for (int i = 0; i < getNumberOfSurroundingFlowers(world, flowerPos); i++) {
            var newLocation = blockState.getModelOffset(flowerPos.add((i + 1) * 5, 0, (i + 1) * 3))
                    .multiply(0.45 / flower.callGetMaxHorizontalModelOffset());


            for (Vec3d location : allLocations) {
                double rotation = Math.atan2(newLocation.x - location.x, newLocation.z - location.z);

                if (location.distanceTo(newLocation) <= 0.4 || is45Degrees(rotation)) {
                    continue outer;
                }
            }

            allLocations.add(newLocation);
            locations.add(newLocation);
        }

        return locations;
    }

    private static int getNumberOfSurroundingFlowers(BlockView world, BlockPos flowerPos) {
        var count = 0;
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        for (Direction dir : directions) {
            if (world.getBlockState(flowerPos.add(dir.getOffsetX(), 0, dir.getOffsetZ()))
                    .getBlock() instanceof FlowerBlock) {
                count++;
            }
        }

        return count;
    }

    public static boolean is45Degrees(double angle) {
        double[] targetAngles = {Math.PI / 4, 3 * Math.PI / 4, 5 * Math.PI / 4, 7 * Math.PI / 4};

        double normalizedAngle = angle % (2 * Math.PI);
        if (normalizedAngle < 0) {
            normalizedAngle += 2 * Math.PI;
        }

        for (double targetAngle : targetAngles) {
            //check + rounding error
            if (Math.abs(normalizedAngle - targetAngle) < 0.01) {
                return true;
            }
        }

        return false;
    }

}
