package tobinio.denseflowers.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tobinio.denseflowers.util.OffsetStorage;

/**
 * Created: 01.04.25
 *
 * @author Tobias Frischmann
 */
@Mixin (net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext.class)
public class TerrainRenderContext {

    @Shadow private MatrixStack matrixStack;

    @Inject(method = "bufferModel", at= @At (value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;peek()Lnet/minecraft/client/util/math/MatrixStack$Entry;"))
    private void betterSnowCoverage$setOffset(BlockStateModel model, BlockState blockState, BlockPos blockPos,
            CallbackInfo ci, @Local Vec3d modelOffset) {

        Vec3d offset = OffsetStorage.offsets.get(blockPos);

        if (offset != null) {
            this.matrixStack.translate(-modelOffset.getX(), -modelOffset.getY(), -modelOffset.getZ());
            this.matrixStack.translate(offset.getX(), offset.getY(), offset.getZ());
        }
    }
}
