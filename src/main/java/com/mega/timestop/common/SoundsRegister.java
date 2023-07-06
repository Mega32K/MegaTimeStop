package com.mega.timestop.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundsRegister {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "megatimestop");
    public static final RegistryObject<SoundEvent> STOP = SOUNDS.register("stop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("megatimestop", "stop")));
    public static final RegistryObject<SoundEvent> THROW = SOUNDS.register("knife_throw", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("megatimestop", "knife_throw")));
    public static final RegistryObject<SoundEvent> KNIFE_HIT = SOUNDS.register("knife_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("megatimestop", "knife_hit")));

}
