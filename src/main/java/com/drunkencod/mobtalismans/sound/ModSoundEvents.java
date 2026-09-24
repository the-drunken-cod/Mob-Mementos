package com.drunkencod.mobtalismans.sound;

import com.drunkencod.mobtalismans.MobTalismans;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(
            BuiltInRegistries.SOUND_EVENT, MobTalismans.MOD_ID);

    // #region talismans

    public static final DeferredHolder<SoundEvent, SoundEvent> CONDUIT_TALISMAN_TRIGGERED = createSoundEvent(
            "item.conduit_talisman.triggered");

    public static final DeferredHolder<SoundEvent, SoundEvent> CREEPER_TALISMAN_TRIGGERED = createSoundEvent(
            "item.creeper_talisman.triggered");

    public static final DeferredHolder<SoundEvent, SoundEvent> ENDERMAN_TALISMAN_TRIGGERED = createSoundEvent(
            "item.enderman_talisman.triggered");

    public static final DeferredHolder<SoundEvent, SoundEvent> SILVERFISH_TALISMAN_TRIGGERED = createSoundEvent(
            "item.silverfish_talisman.triggered");

    // #region register

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    static DeferredHolder<SoundEvent, SoundEvent> createSoundEvent(String registryName) {
        return SOUND_EVENTS.register(registryName, () -> SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(MobTalismans.MOD_ID, registryName)));
    }
}
