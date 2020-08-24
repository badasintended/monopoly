package badasintended.monopoly.mixin;

import badasintended.monopoly.Monopoly;
import badasintended.monopoly.UnifyConfig;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {

    @ModifyVariable(method = "setStack", at = @At("HEAD"), argsOnly = true)
    private ItemStack unifyStack(ItemStack from) {
        return Monopoly.getInstance().unify(world, from);
    }

}
