package magikareborn.opus;

import java.util.ArrayList;

public class OpusBuilder {

    public static ArrayList<BaseEntry> getOpusEntries() {
        return new ArrayList<BaseEntry>() {
            {
                //add(new MagikaOpusEntry().addChild(new LightSpellEntry()));

                //Utility
                add(new LightSpellEntry());
                // - Dispel
                // - Beckon (merchant)
                // - Rift (gravity grenade)

                //Support
                add(new Ether1Entry().setNext(new Ether2Entry().setNext(new Ether3Entry())));
                // - Heal 1
                // - Summon (mob army)
                // - Leap + Levitate + Fly)
                // - Jump + Blink + Warp

                //Offensive (plus healing if attuned)
                // - Fire 1
                // - Thunder 1
                // - Ice 1
                // - Earth 1
                // - Water 1
                // - Air 1

                //Buff
                // - Regen
                // - Haste
                // - Strength
                // - Shield (boost to max health)

                //Debuff
                // - Poison
                // - Slow
                // - Weakness
                // - Blind


            }
        };
    }

}
