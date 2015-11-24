package script.theme_park.dungeon.mustafar_trials.obiwan_finale;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.groundquests;
import script.library.mustafar;
import script.library.prose;
import script.library.utils;

public class obiwan_quest_monitor extends script.base_script
{
    public obiwan_quest_monitor()
    {
    }
    public static final String TRIGGER_QUEST = "som_prelude_obiwan_check";
    public static final boolean CONST_FLAG_DO_LOGGING = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "didMustafarCrystalLair"))
        {
            if (!isGod(self))
            {
                messageTo(self, "removeMonitor", null, 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "waitingOnObiwan"))
        {
            messageTo(self, "recallObiwanDelay", null, 180, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "waitingOnObiwan"))
        {
            removeObjVar(self, "waitingOnObiwan");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "didMustafarCrystalLair"))
        {
            if (!isGod(self))
            {
                messageTo(self, "removeMonitor", null, 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "didMustafarCrystalLair"))
        {
            if (!isGod(self))
            {
                messageTo(self, "removeMonitor", null, 1, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "waitingOnObiwan"))
        {
            if (!utils.hasScriptVar(self, "waitingOnObiwanLoop"))
            {
                utils.setScriptVar(self, "waitingOnObiwanLoop", 1);
                messageTo(self, "recallObiwanDelay", null, 180, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnQuestCompleted(obj_id self, int questNameCrc) throws InterruptedException
    {
        String questName = questGetQuestName(questNameCrc);
        int questNameChecksum = questName.indexOf("som_kenobi");
        if (questNameChecksum > -1)
        {
            String planetName = getCurrentSceneName();
            if (!planetName.startsWith("mustafar"))
            {
                return SCRIPT_CONTINUE;
            }
            if (mustafar.hasCompletedTrials(self))
            {
                if (mustafar.canCallObiwan(self))
                {
                    messageTo(self, "callObiWanNow", null, 5f, false);
                }
                else 
                {
                    setObjVar(self, "waitingOnObiwan", 1);
                    dictionary params = new dictionary();
                    params.put("appearanceComment", 1);
                    messageTo(self, "recallObiwanDelay", params, 180, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSomeTaskActivated(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        int appearanceComment = 0;
        if (groundquests.isTaskActive(self, "som_kenobi_main_quest_killed", "talkKenobi1") || groundquests.isTaskActive(self, "som_kenobi_main_quest_spared", "talkKenobi1"))
        {
            appearanceComment = 2;
        }
        else if (groundquests.isTaskActive(self, "som_kenobi_main_quest_3", "talkKenobi2") || groundquests.isTaskActive(self, "som_kenobi_main_quest_3_b", "talkKenobi2"))
        {
            appearanceComment = 3;
        }
        if (appearanceComment > 0)
        {
            if (mustafar.canCallObiwan(self))
            {
                messageTo(self, "callObiWanNow", null, 5f, false);
            }
            else 
            {
                setObjVar(self, "waitingOnObiwan", appearanceComment);
                dictionary params = new dictionary();
                params.put("appearanceComment", appearanceComment);
                messageTo(self, "recallObiwanDelay", params, 10, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSomeTaskCompleted(obj_id self, int questCrc, int taskId) throws InterruptedException
    {
        location currentLoc = getLocation(self);
        obj_id obiwanObject = getFirstObjectWithObjVar(currentLoc, 100f, "mustafar_obiwan");
        if (isIdValid(obiwanObject))
        {
            messageTo(obiwanObject, "despawnObiwanDelay", null, 10, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void debugLogging(String section, String message) throws InterruptedException
    {
        if (CONST_FLAG_DO_LOGGING)
        {
            LOG("debug/obiwan_quest_monitor/" + section, message);
        }
    }
    public int recallObiwanDelay(obj_id self, dictionary params) throws InterruptedException
    {
        debugLogging("recallObiwanDelay: ", " entered.");
        obj_id obiwan = null;
        if (hasObjVar(self, "waitingOnObiwan"))
        {
            if (mustafar.canCallObiwan(self))
            {
                obiwan = mustafar.callObiwan(self, true);
            }
            messageTo(self, "recallObiwanDelay", null, 360, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int callObiWanNow(obj_id self, dictionary params) throws InterruptedException
    {
        mustafar.callObiwan(self, true, 1);
        return SCRIPT_CONTINUE;
    }
    public int removeMonitor(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "waitingOnObiwan");
        removeObjVar(self, "waitingOnObiwanLoop");
        removeObjVar(self, "sawObiwanAtLauncher");
        groundquests.completeQuest(self, "som_kenobi_main_quest_3");
        groundquests.completeQuest(self, "som_kenobi_main_quest_3_b");
        detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor");
        return SCRIPT_CONTINUE;
    }
}
