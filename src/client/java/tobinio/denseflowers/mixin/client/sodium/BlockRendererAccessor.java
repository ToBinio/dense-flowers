package tobinio.denseflowers.mixin.client.sodium;

import me.jellysquid.mods.sodium.client.model.color.ColorProvider;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import me.jellysquid.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockRenderer.class)
public interface BlockRendererAccessor {
    @Invoker
    void callRenderQuadList(BlockRenderContext ctx, Material material, LightPipeline lighter,
            ColorProvider<BlockState> colorizer, Vec3d offset,
            ChunkModelBuilder builder, List<BakedQuad> quads, Direction cullFace);
}
