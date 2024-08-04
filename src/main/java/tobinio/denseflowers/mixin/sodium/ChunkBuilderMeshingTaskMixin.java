package tobinio.denseflowers.mixin.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderCache;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import me.jellysquid.mods.sodium.client.util.task.CancellationToken;
import net.minecraft.block.FlowerBlock;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.denseflowers.OffsetGenerator;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (value = ChunkBuilderMeshingTask.class, priority = 995)
public class ChunkBuilderMeshingTaskMixin {
    @Inject (method = "execute(Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lme/jellysquid/mods/sodium/client/util/task/CancellationToken;)Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;", at = @At (value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext;Lme/jellysquid/mods/sodium/client/render/chunk/compile/ChunkBuildBuffers;)V", remap = false), remap = false)
    private void execute(ChunkBuildContext buildContext, CancellationToken cancellationToken,
            CallbackInfoReturnable<ChunkBuildOutput> cir, @Local BlockRenderContext ctx,
            @Local ChunkBuildBuffers buffers, @Local BlockRenderCache cache) {
        if (ctx.state().getBlock() instanceof FlowerBlock) {
            Vector3fc origin = ctx.origin();
            var baseOffset = ctx.state().getModelOffset(ctx.world(), ctx.pos());

            var context = (BlockRenderContextAccessor) ctx;

            for (Vec3d flowerOffset : OffsetGenerator.getFlowerOffsets(ctx.state(), ctx.world(), ctx.pos())) {

                var newOrigen = new Vector3f(origin);
                newOrigen.sub((float) baseOffset.getX(), (float) baseOffset.getY(), (float) baseOffset.getZ());
                newOrigen.add((float) flowerOffset.getX(), (float) flowerOffset.getY(), (float) flowerOffset.getZ());

                context.setOrigin(newOrigen);

                cache.getBlockRenderer().renderModel((BlockRenderContext) context, buffers);
            }

            context.setOrigin((Vector3f) origin);
        }
    }
}
