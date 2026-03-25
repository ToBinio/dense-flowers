package tobinio.denseflowers.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Created: 31.07.24
 *
 * @author Tobias Frischmann
 */
@Mixin (BlockBehaviour.class)
public interface BlockBehaviourAccessor {
    @Invoker
    float callGetMaxHorizontalOffset();
}
