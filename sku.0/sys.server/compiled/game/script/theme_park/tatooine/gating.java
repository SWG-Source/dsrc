package script.theme_park.tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.quests;
import script.library.factions;

public class gating extends script.base_script
{
    public gating()
    {
    }
    public static final String BIB_QUEST = "bibNewbieQuest1";
    public static final String BIB_QUEST2 = "bibNewbieQuest2";
    public static final String EPHANT_MON_QUEST = "ephantMonQuest1";
    public static final String MAX_REBO_QUEST = "maxReboQuest1";
    public static final String BARADA_QUEST = "baradaQuest1";
    public static final String BIB_QUEST3 = "bibIntermediateQuest3";
    public static final String JABBA_QUEST = "jabbaAdvQuest1";
    public static final String JABBA_QUEST2 = "jabbaAdvQuest2";
    public static final String JABBA_QUEST3 = "jabbaAdvQuest3";
    public static final String JABBA_CRAFTING_1 = "jabbaCrafting1";
    public static final String JABBA_CRAFTING_2 = "jabbaCrafting2";
    public static final String JABBA_COMBAT_1 = "jabbaCombat1";
    public static final String JABBA_COMBAT_2 = "jabbaCombat2";
    public static final String DERA_QUEST_1 = "deraQuest1";
    public static final String DERA_CRAFTING_1 = "deraCrafting1";
    public static final String DERA_CRAFTING_2 = "deraCrafting2";
    public static final String DERA_COMBAT_1 = "deraCombat1";
    public static final String DERA_COMBAT_2 = "deraCombat2";
    public static final String TALMONT_QUEST_1 = "talmontQuest1";
    public static final String TALMONT_CRAFTING_1 = "talmontCrafting1";
    public static final String TALMONT_CRAFTING_2 = "talmontCrafting2";
    public static final String TALMONT_COMBAT_1 = "talmontCombat1";
    public static final String TALMONT_COMBAT_2 = "talmontCombat2";
    public static boolean canTakeQuest(obj_id player, String questId) throws InterruptedException
    {
        if (quests.hasCompletedQuest(player, questId))
        {
            return false;
        }
        if (questId.equals(DERA_CRAFTING_1) || questId.equals(DERA_CRAFTING_2) || questId.equals(DERA_COMBAT_1) || questId.equals(DERA_COMBAT_2))
        {
            return quests.hasCompletedQuest(player, DERA_QUEST_1);
        }
        if (questId.equals(TALMONT_CRAFTING_1) || questId.equals(TALMONT_CRAFTING_2) || questId.equals(TALMONT_COMBAT_1) || questId.equals(TALMONT_COMBAT_2))
        {
            return quests.hasCompletedQuest(player, TALMONT_QUEST_1);
        }
        if (factions.getFactionStanding(player, factions.FACTION_HUTT) < 500f)
        {
            if (quests.canInfiltrate(player, DERA_QUEST_1) == false && quests.canInfiltrate(player, TALMONT_QUEST_1) == false)
            {
                return false;
            }
        }
        if (questId.equals(JABBA_CRAFTING_1) || questId.equals(JABBA_CRAFTING_2) || questId.equals(JABBA_COMBAT_1) || questId.equals(JABBA_COMBAT_2))
        {
            return quests.hasCompletedQuest(player, JABBA_QUEST3);
        }
        if (questId.equals(BIB_QUEST))
        {
            return true;
        }
        if (questId.equals(BIB_QUEST2))
        {
            return quests.hasCompletedQuest(player, BIB_QUEST);
        }
        if (questId.equals(EPHANT_MON_QUEST) || questId.equals(MAX_REBO_QUEST) || questId.equals(BARADA_QUEST))
        {
            return quests.hasCompletedQuest(player, BIB_QUEST2);
        }
        if (questId.equals(BIB_QUEST3))
        {
            if (quests.hasCompletedQuest(player, EPHANT_MON_QUEST) && quests.hasCompletedQuest(player, MAX_REBO_QUEST) && quests.hasCompletedQuest(player, BARADA_QUEST))
            {
                return true;
            }
            else 
            {
                return false;
            }
        }
        if (questId.equals(JABBA_QUEST))
        {
            return quests.hasCompletedQuest(player, BIB_QUEST3);
        }
        if (questId.equals(JABBA_QUEST2))
        {
            return quests.hasCompletedQuest(player, JABBA_QUEST);
        }
        if (questId.equals(JABBA_QUEST3))
        {
            return quests.hasCompletedQuest(player, JABBA_QUEST2);
        }
        debugServerConsoleMsg(player, "Error:  Don't know WTF " + questId + " quest is!");
        return false;
    }
}
