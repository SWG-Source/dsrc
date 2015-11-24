package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class player_spice extends script.base_script
{
    public player_spice()
    {
    }
    public int OnAttribModDone(obj_id self, String modName, boolean isDead) throws InterruptedException
    {
        String name = utils.getStringScriptVar(self, "spice.name");
        if (modName.startsWith("spice." + name))
        {
            int[] mods = utils.getIntArrayScriptVar(self, "spice.mods");
            int dur = utils.getIntScriptVar(self, "spice.duration");
            float downTimeReduction = utils.getFloatScriptVar(self, "spice.downTimeReduction");
            if (modName.endsWith(".up"))
            {
                dur = (dur / 3);
                if (downTimeReduction > 0)
                {
                    dur *= downTimeReduction;
                }
                boolean buffIcon = false;
                for (int i = 0; i < mods.length; i++)
                {
                    if (mods[i] != 0)
                    {
                        int val = mods[i];
                        if (val > 0)
                        {
                            val *= -1;
                        }
                        attrib_mod newMod;
                        if (!buffIcon)
                        {
                            newMod = new attrib_mod("spice." + name + ".down", i, val, dur, 0, 0, false, true, true);
                            buffIcon = true;
                        }
                        else 
                        {
                            newMod = new attrib_mod(null, i, val, dur, 0, 0, false, false, false);
                        }
                        addAttribModifier(self, newMod);
                    }
                }
                int puketime = 30 + rand(0, 30);
                messageTo(self, "spicePuke", null, puketime, false);
                string_id cmsg = new string_id("spice/spice", name + "_downer");
                sendSystemMessage(self, cmsg);
            }
            else 
            {
                string_id cmsg = new string_id("spice/spice", name + "_done");
                sendSystemMessage(self, cmsg);
                utils.setScriptVar(self, "numPukes", 100);
                utils.removeScriptVarTree(self, "spice");
                detachScript(self, "player.player_spice");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int spicePuke(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasAttribModifier(self, "spice"))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (1780871594), null, "puke", COMMAND_PRIORITY_DEFAULT);
        int numPukes = 0;
        if (utils.hasScriptVar(self, "numPukes"))
        {
            numPukes = utils.getIntScriptVar(self, "numPukes");
        }
        if (numPukes < 5)
        {
            int puketime = 40 + rand(0, 30);
            messageTo(self, "spicePuke", null, puketime, false);
            numPukes++;
            utils.setScriptVar(self, "numPukes", numPukes);
        }
        return SCRIPT_CONTINUE;
    }
}
