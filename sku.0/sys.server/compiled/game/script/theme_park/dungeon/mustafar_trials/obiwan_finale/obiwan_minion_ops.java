package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.ai.ai_combat;
import script.library.utils;

public class obiwan_minion_ops extends script.base_script
{
    public obiwan_minion_ops()
    {
    }
    public static final boolean CONST_FLAG_DO_LOGGING = true;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        findTarget(self);
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("OnAttach", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        else 
        {
            if (utils.hasScriptVar(dungeon, "minions"))
            {
                int minionCount = utils.getIntScriptVar(dungeon, "minions");
                utils.setScriptVar(dungeon, "minions", ++minionCount);
                debugLogging("OnAttach", "////>>>> added a minion to the dungeon. Current total count is " + minionCount);
            }
            else 
            {
                utils.setScriptVar(dungeon, "minions", 1);
            }
        }
        setObjVar(self, "minion", 1);
        dictionary params = new dictionary();
        params.put("minion", self);
        messageTo(dungeon, "moveMinionIntoRoom", params, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        debugLogging("OnIncapacitated", "Minion destroyed");
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// OnAttach: ", "////>>>> dungeon obj_id is invalid. BROKEN");
        }
        else 
        {
            if (utils.hasScriptVar(dungeon, "minions"))
            {
                int minionCount = utils.getIntScriptVar(dungeon, "minions");
                if (minionCount == 1)
                {
                    utils.removeScriptVar(dungeon, "minions");
                    messageTo(dungeon, "minionDied", null, 2, false);
                }
                else 
                {
                    utils.setScriptVar(dungeon, "minions", --minionCount);
                }
                debugLogging("OnIncapacitated", "////>>>> Pulled out a minion from the count. Current count is " + minionCount);
            }
        }
        if (!hasObjVar(self, "minion"))
        {
            debugLogging("OnIncapacitated: ", "////>>>> minion didn't have the minion objvar. BROKEN");
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_minion_ops/" + section, message);
        }
    }
    public void findTarget(obj_id self) throws InterruptedException
    {
        obj_id dungeon = getTopMostContainer(self);
        if (!isIdValid(dungeon))
        {
            debugLogging("//***// findTarget: ", "////>>>> dungeon obj_id is STILL invalid.");
            return;
        }
        obj_id contents[] = getContents(dungeon);
        if (contents == null || contents.length == 0)
        {
            debugLogging("findTargets", "Contents list was empty, exiting");
            return;
        }
        obj_id target = utils.getObjIdScriptVar(dungeon, "player");
        dictionary params = new dictionary();
        params.put("target", target);
        messageTo(self, "engageTarget", params, 20, false);
    }
    public int engageTarget(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("engageTarget", "entered");
        obj_id target = params.getObjId("target");
        if (!isIdValid(target))
        {
            debugLogging("engageTarget", "target objId isn't valid. Trying alternate method");
            target = getObjIdObjVar(self, "target");
            if (!isIdValid(target))
            {
                debugLogging("engageTarget", "Still no good target objId. BROKEN.");
            }
        }
        startCombat(self, target);
        debugLogging("engageTarget", "StartedCombat with target: ." + getName(target));
        addHate(self, target, 1000);
        return SCRIPT_CONTINUE;
    }
}
