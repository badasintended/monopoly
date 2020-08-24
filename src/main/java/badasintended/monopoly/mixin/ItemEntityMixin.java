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
            for (Map.Entry<Identifier, UnifyConfig> entry : Monopoly.getInstance().getConfig().entrySet()) {
                Tag<Item> tag = world.getTagManager().getItems().getTag(entry.getKey());
                if (tag == null) continue;
                if (tag.contains(stack.getItem())) {
                    UnifyConfig unifyConfig = entry.getValue();
                    if (unifyConfig == null) continue;

                    if (!unifyConfig.isThrown() && getThrower() != null) continue;
                    if (!unifyConfig.isNbt() && stack.getTag() != null) continue;

                    Item item = Registry.ITEM.get(unifyConfig.getTarget());
                    if (item == Items.AIR) continue;

                    ItemStack unified = new ItemStack(item, stack.getCount());

                    unified.setTag(stack.getTag());

                    setStack(unified);
                }
            }
        }
    }

}
