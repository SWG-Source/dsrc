package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.badge;
import script.library.factions;
import script.library.chat;

public class restuss_event extends script.base_script
{
    public restuss_event()
    {
    }
    public static final String MASTER_PATROL_ARRAY = "master_patrol_point_array";
    public static final String PATROL_POINTS = "patrolPoints";
    public static final String PATROL_TYPE = "patrolType";
    public static final String STAGE_TWO_DATA = "datatables/spawning/restuss_event/invasion_data.iff";
    public static final String IS_STATIC = "isStaticPosition";
    public static final String TRIGGER_NAME = "trigger.name";
    public static final String TRIGGER_OCCURANCE = "trigger.occurance";
    public static final String TRIGGER_DELAY = "trigger.delay";
    public static final String TRIGGER_INTEREST = "trigger.interest";
    public static final String TRIGGER_TYPE = "trigger.type";
    public static final String EFFECT_NAME = "effect.name";
    public static final String EFFECT_DELTA = "effect.duration";
    public static final String EFFECT_VISABILITY = "effect.visability";
    public static final int PATROL_NONE = 0;
    public static final int PATROL = 1;
    public static final int PATROL_ONCE = 2;
    public static final int PATROL_FLIP = 3;
    public static final int PATROL_FLIP_ONCE = 4;
    public static final int PATROL_RANDOM = 5;
    public static final int PATROL_RANDOM_ONCE = 6;
    public static final String MUS_BATTLE_IMPERIAL_INTRO = "";
    public static final String QUEST_TABLE = "datatables/quest/restuss_event/quest_count.iff";
    public static final String PREFIX = "restuss_event_quest.";
    public static final String PVP_REGION_NAME = "restuss_event_pvp_region";
    public static final string_id SID_PVP_ENTER_WARNING = new string_id("restuss_event/object", "pvp_area_approach");
    public static final string_id SID_PVP_EXIT_MESSAGE = new string_id("restuss_event/object", "pvp_area_depart");
    public static final int WINNER_REBEL = 0;
    public static final int WINNER_IMPERIAL = 1;
    public static final int WINNER_NONE = -1;
    public static final String WINNING_FACTION = "winningFaction";
    public static final String TRIG_ONDEATH = "trigger_marks.OnDeath";
    public static final String TRIG_ENTERCOMBAT = "trigger_marks.EnterCombat";
    public static final String TRIG_EXITCOMBAT = "trigger_marks.ExitCombat";
    public static final String TRIG_ARRIVELOCATION = "trigger_marks.OnArrivedAtLocation";
    public static final String TRIG_CUSTOMSIGNAL = "trigger_marks.CustomSignal";
    public static void playMusicInArea(obj_id controller, String music) throws InterruptedException
    {
        playMusicInArea(controller, music, 50.0f);
    }
    public static void playMusicInArea(obj_id controller, String music, float range) throws InterruptedException
    {
    }
    public static void sendAreaSystemMessage(obj_id controller, string_id message) throws InterruptedException
    {
    }
    public static boolean getIsStatic(obj_id subject) throws InterruptedException
    {
        if (utils.hasScriptVar(subject, IS_STATIC))
        {
            return utils.getBooleanScriptVar(subject, IS_STATIC);
        }
        utils.setScriptVar(subject, IS_STATIC, false);
        return false;
    }
    public static void setIsStatic(obj_id subject, boolean state) throws InterruptedException
    {
        utils.setScriptVar(subject, IS_STATIC, state);
    }
    public static int getPhase(obj_id object) throws InterruptedException
    {
        if (hasObjVar(object, "base_builder.current_phase"))
        {
            return getIntObjVar(object, "base_builder.current_phase");
        }
        obj_id parent = trial.getParent(object);
        if (!isIdValid(parent))
        {
            return -1;
        }
        return getPhase(parent);
    }
    public static void incrimentPhase(obj_id object) throws InterruptedException
    {
        if (hasObjVar(object, "base_builder.current_phase"))
        {
            messageTo(object, "incrimentPhase", null, 0, false);
            return;
        }
        obj_id parent = trial.getParent(object);
        if (!isIdValid(parent))
        {
            return;
        }
        messageTo(parent, "incrimentPhase", null, 0, false);
    }
    public static void decrimentPhase(obj_id object) throws InterruptedException
    {
        if (hasObjVar(object, "base_builder.current_phase"))
        {
            messageTo(object, "decrimentPhase", null, 0, false);
            return;
        }
        obj_id parent = trial.getParent(object);
        if (!isIdValid(parent))
        {
            return;
        }
        messageTo(parent, "decrimentPhase", null, 0, false);
    }
    public static int getRequiredQuestNumber(obj_id subject, String questName) throws InterruptedException
    {
        return dataTableGetInt(QUEST_TABLE, questName, "completion_number");
    }
    public static int getCompletedQuestCount(obj_id subject, String questName) throws InterruptedException
    {
        int cQuest = 0;
        obj_id parent = trial.getParent(subject);
        if (hasObjVar(parent, PREFIX + questName))
        {
            cQuest = getIntObjVar(parent, PREFIX + questName);
        }
        return cQuest;
    }
    public static float getCompletedQuestRatio(obj_id subject, String questName) throws InterruptedException
    {
        return (float)getCompletedQuestCount(subject, questName) / getRequiredQuestNumber(subject, questName);
    }
    public static void setCompletedQuestCount(obj_id subject, String questName, int number) throws InterruptedException
    {
        int max = getRequiredQuestNumber(subject, questName);
        if (number > max)
        {
            number = max;
        }
        setObjVar(subject, PREFIX + questName, number);
    }
    public static void incrimentCompletedQuestCount(obj_id subject, String questName) throws InterruptedException
    {
        int current = getCompletedQuestCount(subject, questName);
        current += 1;
        setCompletedQuestCount(trial.getParent(subject), questName, current);
    }
    public static void decrimentCompletedQuestCount(obj_id subject, String questName) throws InterruptedException
    {
        int current = getCompletedQuestCount(subject, questName);
        current = current - 1;
        if (current < 0)
        {
            current = 0;
        }
        setCompletedQuestCount(trial.getParent(subject), questName, current);
    }
    public static boolean isRequiredCountMet(obj_id subject, String questName) throws InterruptedException
    {
        return (getCompletedQuestRatio(subject, questName) == 1);
    }
    public static int getWinningFaction(obj_id self) throws InterruptedException
    {
        obj_id restuss_controller = getFirstObjectWithScript(getLocation(self), 1000.0f, "theme_park.restuss_event.stage_one_watcher");
        if (!isIdValid(restuss_controller))
        {
            return WINNER_NONE;
        }
        if (!hasObjVar(restuss_controller, WINNING_FACTION))
        {
            return WINNER_NONE;
        }
        return getIntObjVar(restuss_controller, WINNING_FACTION);
    }
    public static boolean grantEventBadge(obj_id player) throws InterruptedException
    {
        if (!factions.isRebel(player) && !factions.isImperial(player))
        {
            return false;
        }
        int winner = getWinningFaction(player);
        if (winner == WINNER_NONE)
        {
            return false;
        }
        String[] badgeList = 
        {
            "restuss_rebel_first",
            "restuss_rebel_second",
            "restuss_imperial_first",
            "restuss_imperial_second"
        };
        for (int k = 0; k < badgeList.length; k++)
        {
            if (badge.hasBadge(player, badgeList[k]))
            {
                return false;
            }
        }
        boolean eligable = false;
        String[] quests = dataTableGetStringColumn("datatables/quest/restuss_event/quest_count.iff", "quest_name");
        if (quests == null || quests.length == 0)
        {
            return false;
        }
        for (int i = 0; i < quests.length; i++)
        {
            if (quests[i].indexOf("rebel") > -1)
            {
                if (factions.isRebel(player))
                {
                    if (groundquests.hasCompletedQuest(player, quests[i]))
                    {
                        eligable = true;
                    }
                }
            }
            if (quests[i].indexOf("imperial") > -1)
            {
                if (factions.isImperial(player))
                {
                    if (groundquests.hasCompletedQuest(player, quests[i]))
                    {
                        eligable = true;
                    }
                }
            }
        }
        if (!eligable)
        {
            return false;
        }
        if (winner == WINNER_REBEL)
        {
            if (factions.isRebel(player))
            {
                badge.grantBadge(player, "restuss_rebel_first");
            }
            if (factions.isImperial(player))
            {
                badge.grantBadge(player, "restuss_imperial_second");
            }
            return true;
        }
        if (winner == WINNER_IMPERIAL)
        {
            if (factions.isRebel(player))
            {
                badge.grantBadge(player, "restuss_rebel_second");
            }
            if (factions.isImperial(player))
            {
                badge.grantBadge(player, "restuss_imperial_first");
            }
            return true;
        }
        return false;
    }
    public static boolean isRestussInStageThree(obj_id npc) throws InterruptedException
    {
        obj_id top = trial.getTop(npc);
        obj_id[] controller_id = getAllObjectsWithObjVar(getLocation(top), 1500.0f, "element");
        obj_id restuss_controller = null;
        if (controller_id == null || controller_id.length == 0)
        {
            return false;
        }
        for (int i = 0; i < controller_id.length; i++)
        {
            if ((getStringObjVar(controller_id[i], "element")).equals("ph1_restuss_master"))
            {
                restuss_controller = controller_id[i];
                break;
            }
        }
        if (!isIdValid(restuss_controller))
        {
            return true;
        }
        if (getPhase(restuss_controller) == 2)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public static int getRestussPhase(obj_id npc) throws InterruptedException
    {
        obj_id top = trial.getTop(npc);
        obj_id[] controller_id = getAllObjectsWithObjVar(getLocation(top), 1500.0f, "element");
        obj_id restuss_controller = null;
        if (controller_id == null || controller_id.length == 0)
        {
            return -1;
        }
        for (int i = 0; i < controller_id.length; i++)
        {
            if ((getStringObjVar(controller_id[i], "element")).equals("ph1_restuss_master"))
            {
                restuss_controller = controller_id[i];
                break;
            }
        }
        if (!isIdValid(restuss_controller))
        {
            return -1;
        }
        return getPhase(restuss_controller);
    }
}
