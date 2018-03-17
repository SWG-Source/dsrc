package script.theme_park.naboo;

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
    public static final String KAJA_QUEST_1 = "kajaQuest1";
    public static final String KAJA_QUEST_2 = "kajaQuest2";
    public static final String LOAM_QUEST_1 = "loamQuest1";
    public static final String HETHRIR_QUEST_1 = "hethrirQuest1";
    public static final String VADER_QUEST_1 = "vaderQuest1";
    public static final String VADER_QUEST_2 = "vaderQuest2";
    public static final String EMPEROR_QUEST_1 = "emperorQuest1";
    public static final String EMPEROR_QUEST_2 = "emperorQuest2";
    public static final String EMPEROR_QUEST_3 = "emperorQuest3";
    public static final String BOSS_NASS_INF_QUEST_1 = "nassInfQuest1";
    public static final String GOR_EBELT_INF_QUEST_1 = "gorEbeltInfQuest1";
    public static final String HETH_VET_CRAFTING_1 = "hethVetCrafting1";
    public static final String HETH_VET_COMBAT_1 = "hethVetCombat1";
    public static final String LOAM_VET_CRAFTING_1 = "loamVetCrafting1";
    public static final String EMPEROR_VET_GROUP_1 = "emperorVetGroup1";
    public static final String VADER_VET_GROUP_1 = "vaderVetGroup1";
    public static final String NASS_VET_CRAFTING_1 = "nassVetCrafting1";
    public static final String NASS_VET_CRAFTING_2 = "nassVetCrafting2";
    public static final String NASS_VET_COMBAT_1 = "nassVetCombat1";
    public static final String NASS_VET_COMBAT_2 = "nassVetCombat2";
    public static final String NASS_VET_GROUP_1 = "nassVetGroup1";
    public static final String EBELT_VET_CRAFTING_1 = "ebeltVetCrafting1";
    public static final String EBELT_VET_COMBAT_1 = "ebeltVetCombat1";
    public static final String EBELT_VET_GROUP_1 = "ebeltVetGroup1";
    public static boolean canTakeQuest(obj_id player, String questId) throws InterruptedException
    {
        if (quests.hasCompletedQuest(player, questId))
        {
            return false;
        }
        if (factions.getFactionStanding(player, factions.FACTION_IMPERIAL) < 500f)
        {
            return false;
        }
        if (questId.equals(KAJA_QUEST_1))
        {
            return true;
        }
        if (questId.equals(KAJA_QUEST_2))
        {
            return quests.hasCompletedQuest(player, KAJA_QUEST_1);
        }
        if (questId.equals(LOAM_QUEST_1))
        {
            return quests.hasCompletedQuest(player, KAJA_QUEST_2);
        }
        if (questId.equals(HETHRIR_QUEST_1))
        {
            return quests.hasCompletedQuest(player, LOAM_QUEST_1);
        }
        if (questId.equals(EMPEROR_QUEST_1))
        {
            return quests.hasCompletedQuest(player, HETHRIR_QUEST_1);
        }
        if (questId.equals(EMPEROR_QUEST_2))
        {
            return quests.hasCompletedQuest(player, EMPEROR_QUEST_1);
        }
        if (questId.equals(EMPEROR_QUEST_3))
        {
            return quests.hasCompletedQuest(player, EMPEROR_QUEST_2);
        }
        if (questId.equals(VADER_QUEST_1))
        {
            return quests.hasCompletedQuest(player, EMPEROR_QUEST_3);
        }
        if (questId.equals(VADER_QUEST_2))
        {
            return quests.hasCompletedQuest(player, VADER_QUEST_1);
        }
        debugServerConsoleMsg(player, "Error:  Don't know WTF " + questId + " quest is!");
        return false;
    }
    public static boolean canTakeInfiltrationMission(obj_id player, String questId) throws InterruptedException
    {
        return true;
    }
}
