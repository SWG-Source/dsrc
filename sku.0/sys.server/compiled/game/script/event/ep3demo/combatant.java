package script.event.ep3demo;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.utils;
import script.library.ai_lib;

public class combatant extends script.base_script
{
    public combatant()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "checkForNewTargets", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id mom = getObjIdObjVar(self, "ep3demo.mom");
        if (utils.hasScriptVar(self, "ep3demo.victim"))
        {
            messageTo(mom, "spawnAnotherVictim", null, 1, false);
            messageTo(self, "destroyYourself", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(self, "ep3demo.aggressor"))
        {
            messageTo(mom, "spawnAnotherAggressor", null, 1, false);
            messageTo(self, "destroyYourself", null, 5, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (!isGod(objSpeaker))
        {
            return SCRIPT_CONTINUE;
        }
        if ((toLower(strText)).startsWith("stop battle"))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int checkForNewTargets(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            lookForSomeoneToKill(self);
        }
        messageTo(self, "checkForNewTargets", null, 6, false);
        return SCRIPT_CONTINUE;
    }
    public int destroyYourself(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void lookForSomeoneToKill(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id[] objects = getObjectsInRange(here, 32);
        for (int i = 0; i < objects.length; i++)
        {
            if (utils.hasScriptVar(self, "ep3demo.aggressor"))
            {
                if (utils.hasScriptVar(objects[i], "ep3demo.victim"))
                {
                    startCombat(self, objects[i]);
                    return;
                }
            }
            if (utils.hasScriptVar(self, "ep3demo.victim"))
            {
                if (utils.hasScriptVar(objects[i], "ep3demo.aggressor"))
                {
                    startCombat(self, objects[i]);
                    return;
                }
            }
        }
        return;
    }
}
