package me.tnoctua.respectmytrims.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

    @Inject(method = "wearsGoldArmor", at = @At("RETURN"), cancellable = true)
    private static void isWearingPiglinSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        // Gold trims pacify piglins
        for (ItemStack stack : entity.getArmorItems()) {
            ArmorTrim components = stack.getComponents().get(DataComponentTypes.TRIM);
            if (components != null) {
                RegistryEntry<ArmorTrimMaterial> trim = components.getMaterial();
                if (trim != null && trim.getIdAsString().equals("minecraft:gold")) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
