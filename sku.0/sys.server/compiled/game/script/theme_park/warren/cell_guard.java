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

public class cell_guard extends script.base_script
{
    public cell_guard()
    {
    }
    public static final String CONVO_FILE = "theme_park/warren/warren";
    public static final String ALERT_VOLUME_NAME = "alertVolume";
    public static final String AGGRO_VOLUME_NAME = "aggroVolume";
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
            createTriggerVolume(AGGRO_VOLUME_NAME, 8.0f, true);
        }
        setObjVar(self, "ai.rangedOnly", true);
        if (!hasObjVar(self, "ai.grenadeType"))
        {
            setObjVar(self, "ai.grenadeType", "object/weapon/ranged/grenade/grenade_fragmentation");
        }
        setName(self, "");
        setName(self, new string_id("theme_park/warren/warren_system_messages", "name_jerrd"));
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (getConfigSetting("GameServer", "disableAITriggerVolumes") == null)
        {
            createTriggerVolume(ALERT_VOLUME_NAME, 15.0f, true);
            createTriggerVolume(AGGRO_VOLUME_NAME, 8.0f, true);
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
        if (!ai_lib.isInCombat(self))
        {
            if (volumeName.equals(ALERT_VOLUME_NAME))
            {
                chat.setAngryMood(self);
                chat.chat(self, new string_id(CONVO_FILE, "guard_warning"));
            }
            else if (volumeName.equals(AGGRO_VOLUME_NAME))
            {
                startCombat(self, breacher);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
