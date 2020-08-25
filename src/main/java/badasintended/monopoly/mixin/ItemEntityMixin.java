package badasintended.monopoly.mixin;

import badasintended.monopoly.Monopoly;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {

    @ModifyVariable(method = "setStack", at = @At("HEAD"), argsOnly = true)
    private ItemStack unifyStack(ItemStack from) {
        return Monopoly.getInstance().unify(world, from);
    }

}
