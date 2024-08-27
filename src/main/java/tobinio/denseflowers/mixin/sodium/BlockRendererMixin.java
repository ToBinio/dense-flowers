package tobinio.denseflowers.mixin.sodium;

import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.denseflowers.util.OffsetStorage;

/**
 * Created: 23.08.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockRenderer.class)
public class BlockRendererMixin {
    @Shadow (remap = false)
    @Final
    private Vector3f posOffset;

    @Inject (method = "renderModel", at = @At (value = "INVOKE", target = "Lorg/joml/Vector3f;add(FFF)Lorg/joml/Vector3f;"), remap = false)
    private void renderModel(BakedModel model, BlockState state, BlockPos pos, BlockPos origin, CallbackInfo ci) {

        Vec3d offset = OffsetStorage.offsets.get(pos);

        if (offset != null) {
            posOffset.add((float) offset.getX(),
                    (float) offset.getY(),
                    (float) offset.getZ());
        }
    }
}
