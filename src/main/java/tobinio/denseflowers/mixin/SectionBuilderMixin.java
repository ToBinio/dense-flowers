package tobinio.denseflowers.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.VertexSorter;
import net.fabricmc.fabric.impl.client.indigo.renderer.accessor.AccessChunkRendererRegion;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.chunk.BlockBufferAllocatorStorage;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.client.render.chunk.SectionBuilder;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.denseflowers.OffsetGenerator;
import tobinio.denseflowers.util.OffsetStorage;

/**
 * Created: 30.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (SectionBuilder.class)
public class SectionBuilderMixin {
    @Shadow
    @Final
    private BlockRenderManager blockRenderManager;

    @Inject (method = "build", at = @At (value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getRenderType()Lnet/minecraft/block/BlockRenderType;"))
    private void betterSnowCoverage$render(ChunkSectionPos sectionPos, ChunkRendererRegion renderRegion,
            VertexSorter vertexSorter,
            BlockBufferAllocatorStorage allocatorStorage, CallbackInfoReturnable<SectionBuilder.RenderData> cir,
            @Local BlockState blockState, @Local (ordinal = 2) BlockPos blockPos) {

        if (blockState.getRenderType() == BlockRenderType.MODEL && blockState.getBlock() instanceof FlowerBlock) {
            var model = this.blockRenderManager.getModel(blockState);

            for (Vec3d flowerOffset : OffsetGenerator.getFlowerOffsets(blockState, renderRegion, blockPos)) {
                OffsetStorage.offsets.put(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()),
                        flowerOffset);
                ((AccessChunkRendererRegion) renderRegion).fabric_getRenderer()
                        .bufferModel(model, blockState, blockPos);
                OffsetStorage.offsets.remove(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
            }
        }
    }

}
