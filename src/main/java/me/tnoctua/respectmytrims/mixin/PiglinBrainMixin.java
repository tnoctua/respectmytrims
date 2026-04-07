package me.tnoctua.respectmytrims.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.equipment.trim.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinBrainMixin {

    @Inject(method = "isWearingSafeArmor", at = @At("RETURN"), cancellable = true)
    private static void isWearingPiglinSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        // Gold trims (and snout patterns) pacify piglins
        for (EquipmentSlot equipmentSlot : EquipmentSlotGroup.ARMOR) {
            ArmorTrim components = entity.getItemBySlot(equipmentSlot).getComponents().get(DataComponents.TRIM);
            if (components != null) {
                Holder<TrimMaterial> trim = components.material();
                Holder<TrimPattern> pattern = components.pattern();
                if (trim.is(TrimMaterials.GOLD) || pattern.is(TrimPatterns.SNOUT)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
