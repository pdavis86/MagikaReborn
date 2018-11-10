package magikareborn;

import magikareborn.Capabilities.IManaCapability;
import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.ManaCapabilitySerializer;
import magikareborn.Capabilities.OpusCapabilityStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class EventHandler {

    /*@SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        player.getCapability(OpusCapabilityStorage.CAPABILITY, null).afterLogin();
    }*/

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone evt) {

        System.out.println("onPlayerClone");

        if (!evt.isWasDeath() || evt.isCanceled()) {
            return;
        }
        if (evt.getOriginal() == null || evt.getEntityPlayer() == null) {
            return;
        }
        /*if(evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) {
            return;
        }*/

        //todo: test this
        System.out.println("You died! But don't worry, you're Opus data is not lost :)");

        IOpusCapability oldCapability = evt.getOriginal().getCapability(OpusCapabilityStorage.CAPABILITY, null);
        IOpusCapability newCapability = evt.getEntityPlayer().getCapability(OpusCapabilityStorage.CAPABILITY, null);
        newCapability.cloneFrom(oldCapability);

        IManaCapability newMana = evt.getEntityPlayer().getCapability(ManaCapabilitySerializer.MANA_CAP, null);
        IManaCapability oldMana = evt.getOriginal().getCapability(ManaCapabilitySerializer.MANA_CAP, null);
        newMana.setMana(oldMana.getMana());
    }

    /*@SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if (player.getEntityWorld().isRemote) return;

        IManaCapability mana = player.getCapability(ManaCapabilitySerializer.MANA_CAP, null);

        mana.fill(50);

        String message = String.format("You refreshed yourself in the bed. You received 50 mana, you have §7%d§r mana left.", (int) mana.getMana());
        player.sendMessage(new TextComponentString(message));
    }

    @SubscribeEvent
    public static void onPlayerFalls(LivingFallEvent event)
    {
        Entity entity = event.getEntity();

        if (entity.getEntityWorld().isRemote || !(entity instanceof EntityPlayerMP) || event.getDistance() < 3) return;

        EntityPlayer player = (EntityPlayer) entity;
        IManaCapability mana = player.getCapability(ManaCapabilitySerializer.MANA_CAP, null);

        float points = mana.getMana();
        float cost = event.getDistance() * 2;

        if (points > cost)
        {
            mana.consume(cost);

            String message = String.format("You absorbed fall damage. It costed §7%d§r mana, you have §7%d§r mana left.", (int) cost, (int) mana.getMana());
            player.sendMessage(new TextComponentString(message));

            event.setCanceled(true);
        }
    }*/
}
