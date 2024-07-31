package tobinio.denseflowers.mixin.client.sodium;

import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.render.chunk.compile.ChunkBuildBuffers;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.denseflowers.OffsetGenerator;

import java.util.List;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockRenderer.class)
public class BlockRenderMixin {
    @Inject (method = "renderModel", at = @At (value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderQuadList(Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext;Lme/jellysquid/mods/sodium/client/render/chunk/terrain/material/Material;Lme/jellysquid/mods/sodium/client/model/light/LightPipeline;Lme/jellysquid/mods/sodium/client/model/color/ColorProvider;Lnet/minecraft/util/math/Vec3d;Lme/jellysquid/mods/sodium/client/render/chunk/compile/buffers/ChunkModelBuilder;Ljava/util/List;Lnet/minecraft/util/math/Direction;)V", ordinal = 1))
    private void render(BlockRenderContext ctx, ChunkBuildBuffers buffers, CallbackInfo ci, @Local Material material,
            @Local LightPipeline lighter, @Local ColorProvider<BlockState> colorizer,
            @Local ChunkModelBuilder meshBuilder, @Local List<BakedQuad> all) {

        if (ctx.state().getBlock() instanceof FlowerBlock) {
            var renderer = (BlockRendererAccessor) this;

            for (Vec3d flowerOffset : OffsetGenerator.getFlowerOffsets(ctx.state(), ctx.world(), ctx.pos())) {
                renderer.callRenderQuadList(ctx, material, lighter, colorizer, flowerOffset, meshBuilder, all, null);
            }
        }
    }
}
