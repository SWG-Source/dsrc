package script.ai.imperial_presence;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.ai_lib;
import script.library.chat;

public class harass_backup extends script.base_script
{
    public harass_backup()
    {
    }
    public static final String STF = "imperial_presence/contraband_search";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "goingToFight", null, 1, false);
        int x = rand(300, 600);
        messageTo(self, "cleanUp", null, x, false);
        return SCRIPT_CONTINUE;
    }
    public int goingToFight(obj_id self, dictionary params) throws InterruptedException
    {
        location goFight = getLocationObjVar(self, "whereToFight");
        goFight.x = goFight.x + (rand(-5, 5));
        ai_lib.aiPathTo(self, goFight);
        setMovementRun(self);
        addLocationTarget("fight", goFight, 1);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("fight"))
        {
            chat.setAngryMood(self);
            if (hasObjVar(self, "leader"))
            {
                if (hasObjVar(self, "jedi"))
                {
                    chat.chat(self, new string_id(STF, "containment_team_jedi_" + getStringObjVar(self, "faction")));
                }
                else 
                {
                    chat.chat(self, new string_id(STF, "containment_team_" + getStringObjVar(self, "faction")));
                }
            }
            obj_id attacker = getObjIdObjVar(self, "whoToFight");
            startCombat(self, attacker);
        }
        if (name.equals("done"))
        {
            messageTo(self, "leaveWorld", null, 7, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        location home = getLocationObjVar(self, "home");
        ai_lib.aiPathTo(self, home);
        addLocationTarget("done", home, 1);
        messageTo(self, "handleBadLeaving", null, 120, false);
        return SCRIPT_CONTINUE;
    }
    public int leaveWorld(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int handleBadLeaving(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "leaveWorld", null, 7, false);
        return SCRIPT_CONTINUE;
    }
}
