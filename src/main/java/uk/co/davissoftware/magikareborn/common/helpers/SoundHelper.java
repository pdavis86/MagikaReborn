package uk.co.davissoftware.magikareborn.common.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundHelper {

    public static void playSoundForAll(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.getEntityWorld().playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), sound, entity.getSoundCategory(), volume, pitch);
    }

    public static void playSoundForAll(World world, BlockPos blockPos, SoundEvent sound, SoundCategory soundCategory, float volume, float pitch) {
        world.playSound(null, blockPos, sound, soundCategory, volume, pitch);
    }

    public static void playSoundForPlayer(Entity entity, SoundEvent sound, float volume, float pitch) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entity;
            if (player.connection != null) {
                player.connection.sendPacket(new SPlaySoundEffectPacket(sound, entity.getSoundCategory(), entity.getPosX(), entity.getPosY(), entity.getPosZ(), volume, pitch));
            }
        }
    }
}
