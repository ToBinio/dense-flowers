package tobinio.denseflowers.mixin.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildContext;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.caffeinemc.mods.sodium.client.util.task.CancellationToken;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.denseflowers.OffsetGenerator;
import tobinio.denseflowers.util.OffsetStorage;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
// priority change so this mixins gets applied before Iris
@Mixin (value = ChunkBuilderMeshingTask.class, priority = 1020)
public class ChunkBuilderMeshingTaskMixin {

    @Inject (method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At (value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lnet/minecraft/client/renderer/block/model/BlockStateModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V"))
    private void denseflowers$onRenderModel(ChunkBuildContext buildContext, CancellationToken cancellationToken,
            CallbackInfoReturnable<ChunkBuildOutput> cir, @Local BlockRenderer blockRenderer, @Local LevelSlice slice,
            @Local (ordinal = 0) BlockPos.MutableBlockPos pos, @Local (ordinal = 1) BlockPos.MutableBlockPos modelOffset,
            @Local BlockState blockState, @Local BlockStateModel model) {
        if (blockState.getBlock() instanceof FlowerBlock) {
            for (Vec3 flowerOffset : OffsetGenerator.getFlowerOffsets(blockState, slice, pos)) {

                OffsetStorage.offsets.put(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), flowerOffset);
                blockRenderer.renderModel(model, blockState, pos, modelOffset);
                OffsetStorage.offsets.remove(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
    }
}
