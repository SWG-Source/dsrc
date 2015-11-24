package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.factions;
import script.library.utils;
import script.library.attrib;
import script.library.chat;

public class trooper extends script.base_script
{
    public trooper()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String ALERT_VOLUME_NAME = "alertVolume";
    public static final String ACTION_ALERT = "alert";
    public static final String ACTION_THREATEN = "threaten";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "ai.diction"))
        {
            setObjVar(self, "ai.diction", "military");
        }
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(ALERT_VOLUME_NAME, 15.0f, true);
        }
        setObjVar(self, "ai.rangedOnly", true);
        if (!hasObjVar(self, "ai.grenadeType"))
        {
            setObjVar(self, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (breacher == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (isIncapacitated(self) || ai_lib.isAiDead(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (!isPlayer(breacher))
        {
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(breacher, "warren.flaggedImperial"))
        {
            return SCRIPT_CONTINUE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME) && !ai_lib.isInCombat(self))
        {
            if (rand(1, 10) == 1)
            {
                int yourGender = getGender(breacher);
                if (yourGender == GENDER_MALE)
                {
                    chat.chat(self, new string_id(CONVO_FILE, "trooper_greeting_m"));
                }
                else 
                {
                    chat.chat(self, new string_id(CONVO_FILE, "trooper_greeting_f"));
                }
            }
            startCombat(self, breacher);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSawAttack(obj_id self, obj_id defender, obj_id[] attackers) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "disableAICombat") != null)
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isAiDead(self) || isInvulnerable(self))
        {
            setWantSawAttackTriggers(self, false);
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasScript(defender, "theme_park.warren.trooper"))
        {
            startCombat(self, attackers[rand(0, attackers.length - 1)]);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < attackers.length; i++)
        {
            if (hasScript(attackers[i], "theme_park.warren.trooper"))
            {
                startCombat(self, defender);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnMoveMoving(obj_id self) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int OnMovePathComplete(obj_id self) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterWaiting(obj_id self, modifiable_float time) throws InterruptedException
    {
        aiEquipPrimaryWeapon(self);
        return SCRIPT_CONTINUE;
    }
}
