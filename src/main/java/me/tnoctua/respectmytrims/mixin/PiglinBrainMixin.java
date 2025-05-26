package me.tnoctua.respectmytrims.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

    @Inject(method = "wearsGoldArmor", at = @At("RETURN"), cancellable = true)
    private static void isWearingPiglinSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        // Gold trims pacify piglins
        DynamicRegistryManager registries = entity.getWorld().getRegistryManager();
        for (ItemStack stack : entity.getArmorItems()) {
            Optional<ArmorTrim> optionalTrim = ArmorTrim.getTrim(registries, stack, true);
            optionalTrim.ifPresent(trim -> {
                RegistryEntry<ArmorTrimMaterial> materialEntry = trim.getMaterial();
                materialEntry.getKey().ifPresent(key -> {
                    ArmorTrimMaterial material = registries.get(RegistryKeys.TRIM_MATERIAL).get(key);
                    if (material != null && material.assetName().equals("gold")) {
                        cir.setReturnValue(true);
                    }
                });
            });
        }
    }

}
