package script.npe;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.create;
import script.library.utils;
import script.library.groundquests;

public class npe_droid_trigger_volumes extends script.base_script
{
    public npe_droid_trigger_volumes()
    {
    }
    public int OnCreatureDamaged(obj_id self, obj_id player, obj_id weapon, int[] damage) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "droidVolumeBreach"))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        String myname = getName(self);
        String guard = "npe_rakqua_warrior";
        dictionary webster = new dictionary();
        webster.put("player", player);
        if (myname != null && !myname.equals(""))
        {
            utils.setScriptVar(self, "droidVolumeBreach", 1);
            if (myname.equals("mob/creature_names:npe_droid_rescind"))
            {
                playExplosionEffect(player, self, -400);
            }
            else if (myname.equals("mob/creature_names:npe_k3i_buzzer"))
            {
                playExplosionEffect(player, self, -250);
            }
            else if (myname.equals("mob/creature_names:npe_4warning"))
            {
                webster.put("guard", guard);
                messageTo(self, "spawnGuards", webster, 5, false);
            }
            else if (myname.equals("mob/creature_names:npe_i10_probe"))
            {
                guard = "npe_rakqua_shaman";
                webster.put("guard", guard);
                messageTo(self, "spawnGuards", webster, 5, false);
            }
            else if (myname.equals("npc_name:typho"))
            {
                playExplosionEffect(player, self, -150);
            }
            else if (myname.equals("mob/creature_names:npe_smuggler_trap"))
            {
                if (groundquests.isTaskActive(player, "npe_smuggler_try", "droidAlert"))
                {
                    debugSpeakMsg(player, "You have the quest");
                    groundquests.sendSignal(player, "gotYou");
                    guard = "npe_rakqua_warrior";
                    webster.put("guard", guard);
                    messageTo(self, "spawnGuards", webster, 5, false);
                }
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void playExplosionEffect(obj_id player, obj_id self, int damageAmount) throws InterruptedException
    {
        playClientEffectLoc(player, "clienteffect/combat_grenade_small_01.cef", getLocation(self), 1f);
        addAttribModifier(player, HEALTH, damageAmount, 0f, 0f, MOD_POOL);
        kill(self);
        messageTo(self, "getRidOfMe", null, 1, true);
        return;
    }
    public int spawnGuards(obj_id self, dictionary params) throws InterruptedException
    {
        String guard = params.getString("guard");
        obj_id player = params.getObjId("player");
        playClientEffectObj(self, "clienteffect/survey_tool_gas.cef", self, "");
        location me = getLocation(self);
        me.x = me.x + 1;
        if (guard != null && !guard.equals(""))
        {
            create.object(guard, me);
            me.y = me.y + 1;
            create.object(guard, me);
        }
        chat.chat(self, "INTRUDER ALERT! INTRUDER ALERT!");
        detachScript(self, "npe.npe_droid_trigger_volumes");
        return SCRIPT_CONTINUE;
    }
    public int getRidOfMe(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
