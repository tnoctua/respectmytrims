package me.tnoctua.respectmytrims.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

    @Inject(method = "isWearingPiglinSafeArmor", at = @At("RETURN"), cancellable = true)
    private static void isWearingPiglinSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        // Gold trims pacify piglins
        for (EquipmentSlot equipmentSlot : AttributeModifierSlot.ARMOR) {
            ArmorTrim components = entity.getEquippedStack(equipmentSlot).getComponents().get(DataComponentTypes.TRIM);
            if (components != null) {
                RegistryEntry<ArmorTrimMaterial> trim = components.material();
                if (trim != null && trim.getIdAsString().equals("minecraft:gold")) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
