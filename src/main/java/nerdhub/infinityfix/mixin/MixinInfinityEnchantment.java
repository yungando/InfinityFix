package nerdhub.infinityfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Enchantment.class)
public class MixinInfinityEnchantment {
  @Shadow
  @Final
  private Text description;

  @Inject(method = "isSupportedItem", at = @At("HEAD"), cancellable = true)
  private void isSupportedItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    if (Objects.equals(this.description, Text.translatable("enchantment.minecraft.infinity"))) {
      cir.setReturnValue(stack.getItem() instanceof RangedWeaponItem);
    }
  }

  @ModifyReturnValue(method = "canBeCombined", at = @At("RETURN"))
  private static boolean canBeCombined(boolean original, RegistryEntry<Enchantment> first,
      RegistryEntry<Enchantment> second) {
    if (first.matchesKey(Enchantments.INFINITY) && second.matchesKey(Enchantments.MENDING)
        || first.matchesKey(Enchantments.MENDING) && second.matchesKey(Enchantments.INFINITY)) {
      return true;
    }
    return original;
  }
}
