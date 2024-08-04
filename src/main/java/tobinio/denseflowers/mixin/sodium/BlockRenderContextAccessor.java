package tobinio.denseflowers.mixin.sodium;

import me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderContext;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockRenderContext.class)
public interface BlockRenderContextAccessor {
    @Mutable
    @Accessor (remap = false)
    void setOrigin(Vector3f origin);
}
