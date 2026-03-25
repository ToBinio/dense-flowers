package tobinio.denseflowers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import tobinio.denseflowers.mixin.BlockBehaviourAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
public class OffsetGenerator {

    public static List<Vec3> getFlowerOffsets(BlockState blockState, BlockGetter world, BlockPos flowerPos) {
        var baseLocation = blockState.getOffset(flowerPos);
        BlockBehaviourAccessor flower = (BlockBehaviourAccessor) blockState.getBlock();

        var allLocations = new ArrayList<Vec3>();
        allLocations.add(baseLocation);

        var locations = new ArrayList<Vec3>();

        outer:
        for (int i = 0; i < getNumberOfSurroundingFlowers(world, flowerPos); i++) {
            var newLocation = blockState.getOffset(flowerPos.offset((i + 1) * 5, 0, (i + 1) * 3))
                    .scale(0.45 / flower.callGetMaxHorizontalOffset());


            for (Vec3 location : allLocations) {
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

    private static int getNumberOfSurroundingFlowers(BlockGetter world, BlockPos flowerPos) {
        var count = 0;
        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        for (Direction dir : directions) {
            if (world.getBlockState(flowerPos.offset(dir.getStepX(), 0, dir.getStepZ()))
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
