package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class emp_day_spawner extends script.base_script
{
    public emp_day_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String setting = getConfigSetting("EventTeam", "empireDay");
        if (setting == null || setting.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (setting.equals("true") || setting.equals("1"))
        {
            String spawn = getStringObjVar(self, "spawns");
            obj_id celeb = create.object(spawn, getLocation(self));
            setObjVar(self, "celeb", celeb);
            if (!isIdValid(celeb))
            {
                return SCRIPT_CONTINUE;
            }
            setInvulnerable(celeb, true);
            ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
            messageTo(self, "postSpawnScriptDetach", null, 30, false);
            if (hasObjVar(self, "quest_script"))
            {
                String script = getStringObjVar(self, "quest_script");
                attachScript(celeb, script);
            }
            if (hasObjVar(self, "quest_table"))
            {
                String table = getStringObjVar(self, "quest_table");
                setObjVar(celeb, "quest_table", table);
            }
            if (hasObjVar(self, "npc_name"))
            {
                String name = getStringObjVar(self, "npc_name");
                if (name != null)
                {
                    if (!name.equals(""))
                    {
                        setName(celeb, name);
                    }
                }
            }
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int postSpawnScriptDetach(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id celeb = getObjIdObjVar(self, "celeb");
        if (hasScript(celeb, "npc.faction_recruiter.recruiter_setup"))
        {
            detachScript(celeb, "npc.faction_recruiter.recruiter_setup");
        }
        if (hasScript(celeb, "npc.faction_recruiter.faction_recruiter"))
        {
            detachScript(celeb, "npc.faction_recruiter.faction_recruiter");
        }
        if (hasScript(celeb, "conversation.faction_recruiter_imperial"))
        {
            detachScript(celeb, "conversation.faction_recruiter_imperial");
        }
        if (hasScript(celeb, "conversation.faction_recruiter_rebel"))
        {
            detachScript(celeb, "conversation.faction_recruiter_rebel");
        }
        if (hasScript(celeb, "theme_park.imperial.quest_convo"))
        {
            detachScript(celeb, "theme_park.imperial.quest_convo");
        }
        if (hasScript(celeb, "npc.celebrity.han_solo"))
        {
            detachScript(celeb, "npc.celebrity.han_solo");
        }
        if (hasScript(celeb, "theme_park.rebel.quest_convo"))
        {
            detachScript(celeb, "theme_park.rebel.quest_convo");
        }
        return SCRIPT_CONTINUE;
    }
}
