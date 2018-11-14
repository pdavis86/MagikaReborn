package magikareborn.helpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundHelper {

    public static void playSoundForAll(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.getEntityWorld().playSound(null, entity.getPosition(), sound, entity.getSoundCategory(), volume, pitch);
    }

    public static void playSoundForAll(World world, BlockPos blockPos, SoundEvent sound, SoundCategory soundCategory, float volume, float pitch) {
        world.playSound(null, blockPos, sound, soundCategory, volume, pitch);
    }

    public static void playSoundForPlayer(Entity entity, SoundEvent sound, float volume, float pitch) {
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            if (player.connection != null) {
                ((EntityPlayerMP) entity).connection.sendPacket(new SPacketSoundEffect(sound, entity.getSoundCategory(), entity.posX, entity.posY, entity.posZ, volume, pitch));
            }
        }
    }
}
