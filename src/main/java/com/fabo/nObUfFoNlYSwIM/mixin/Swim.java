package com.fabo.nObUfFoNlYSwIM.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

@Mixin(value = LivingEntity.class)
public class Swim {
    private Vec3 bump = new Vec3(0, 0.08, 0);

    @Inject(method = "travelRidden", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V", shift = At.Shift.BEFORE))
    private void fakeSwim(Player controllingPlayer, Vec3 movementInput, CallbackInfo ci) {
        if (!((Object) this instanceof AbstractHorse)) {
            return;
        }
        AbstractHorse horseInstance = (AbstractHorse) (Object) this;

        if (horseInstance.getFluidTypeHeight(
                Fluids.WATER.getFluidType()) > (horseInstance.getEyeHeight(Pose.SWIMMING) < 0.4 ? 0.0 : 0.4)) {
            horseInstance.addDeltaMovement(bump);
        }
    }
}
