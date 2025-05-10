package nerdhub.infinityfix.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

  @Inject(method = "getProjectileType", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;abilities:Lnet/minecraft/entity/player/PlayerAbilities;"), cancellable = true)
  private void getProjectileType(ItemStack weapon, CallbackInfoReturnable<ItemStack> cir) {
    if (EnchantmentHelper.getEnchantments(weapon).getEnchantmentEntries().stream()
        .anyMatch((entry) -> entry.getKey().matches(Enchantments.INFINITY::equals))) {
      cir.setReturnValue(new ItemStack(Items.ARROW));
    }
  }
}
