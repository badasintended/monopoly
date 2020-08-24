package badasintended.monopoly.mixin;

import badasintended.monopoly.Config;
import badasintended.monopoly.Monopoly;
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

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {

    @Shadow
    public abstract UUID getThrower();

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract void setStack(ItemStack stack);

    @Unique
    private boolean monopoly$already = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void unifyStack(CallbackInfo ci) {
        if (!world.isClient && !monopoly$already) {
            monopoly$already = true;
            ItemStack stack = getStack();
            for (Identifier id : Monopoly.getConfig().getUnify().keySet()) {
                Tag<Item> tag = world.getTagManager().getItems().getTag(id);
                if (tag == null) continue;
                if (tag.contains(stack.getItem())) {
                    Config.Unify unify = Monopoly.getConfig().getUnify().getOrDefault(id, null);
                    if (unify == null) continue;

                    if (!unify.isThrown() && getThrower() != null) continue;
                    if (!unify.isNbt() && stack.getTag() != null) continue;

                    Item item = Registry.ITEM.get(unify.getTarget());
                    if (item == Items.AIR) continue;

                    ItemStack unified = new ItemStack(item, stack.getCount());

                    unified.setTag(stack.getTag());

                    setStack(unified);
                }
            }
        }
    }

}
