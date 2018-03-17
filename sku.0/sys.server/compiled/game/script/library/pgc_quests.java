package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.factions;
import script.library.jedi;
import script.library.loot;
import script.library.money;
import script.library.prose;
import script.library.static_item;
import script.library.utils;
import script.library.weapons;
import script.library.xp;
import script.library.gcw;

public class pgc_quests extends script.base_script
{
    public pgc_quests()
    {
    }
    public static final int CHRONICLE_HOLOCRON_VERSION = 0;
    public static final int CHRONICLE_CREATION_XP_BASE = 99;
    public static final int CHRONICLE_SHARED_XP_BASE = 4;
    public static final int CHRONICLES_QUESTOR_GOLD_TOKEN_CHANCE = 5;
    public static final int CHRONICLES_CHRONICLER_GOLD_TOKEN_CHANCE = 5;
    public static final float CHRONICLE_SILVER_TOKENS_FOR_CHRONICLER_MOD = 0.42f;
    public static final float CHRONICLE_SILVER_TOKENS_FOR_QUESTOR_MOD = 1.2f;
    public static final boolean TEXT_FILTER_DISABLED = false;
    public static final String PGC_QUEST_RECIPE_TEMPLATE = "object/player_quest/pgc_quest_recipe.iff";
    public static final String PGC_QUEST_HOLOCRON_TEMPLATE = "object/player_quest/pgc_quest_holocron.iff";
    public static final String PGC_QUEST_SHARED_TEMPLATE = "object/player_quest/pgc_quest_holocron_shared.iff";
    public static final String PGC_QUEST_CONTROL_DEVICE_TEMPLATE = "object/intangible/saga_system/sage_intangible_holocron.iff";
    public static final String PGC_QUEST_HOLOCRON_SCRIPT = "quest.task.pgc.quest_holocron";
    public static final int PGC_QUEST_MAX_NUM_QUESTS = 4;
    public static final int PGC_QUEST_MAX_NUM_TASKS = 12;
    public static final int PGC_QUEST_MAX_NUM_TASKS_PER_PHASE = 4;
    public static final int PGC_NUM_REWARD_PARSE_SLOTS = 5;
    public static final int PGC_MAX_NUM_PLAYER_REWARDS = 4;
    public static final int PQ_TASK_ACTIVE = 0;
    public static final int PQ_TASK_CURRENT = 1;
    public static final int PQ_TASK_COMPLETE = 2;
    public static final int PQ_TASK_FAILED = 3;
    public static final int PQ_TASK_INACTIVE = 4;
    public static final String CATEGORY_NAME_RELIC_DATA = "relic_data";
    public static final int CATERGORY_RELIC_DATA_LEVEL_INDEX = 1;
    public static final int CATERGORY_RELIC_DATA_GROUP_INDEX = 2;
    public static final int CATERGORY_RELIC_DATA_KASHYYYK_INDEX = 3;
    public static final int CATERGORY_RELIC_DATA_MUSTAFAR_INDEX = 4;
    public static final int COMM_TASK_COMPLETION_DELAY = 6;
    public static final int COMPLETED_HOLOCRON_HUE = 6;
    public static final int BOOSTER_PACK_SIZE = 5;
    public static final int STARTER_PACK_SIZE = 12;
    public static final String SAGA_DESTROY_MULTIPLE = "saga_relic_destroy_multiple";
    public static final String SAGA_DESTROY_MULTIPLE_LOOT = "saga_relic_destroy_multiple_loot";
    public static final String SAGA_PERFORM = "saga_relic_perform";
    public static final String SAGA_CRAFT_ITEM = "saga_relic_craft_item";
    public static final String SAGA_COMM_MESSAGE = "saga_relic_comm_message";
    public static final String SAGA_GOTO_LOCATION = "saga_relic_goto_location";
    public static final String SAGA_RETRIEVE_ITEM = "saga_relic_retrieve_item";
    public static final String SAGA_PVP_OBJECTIVE = "saga_relic_pvp_objective";
    public static final String[] CREDIT_FOR_KILL_TASKS = 
    {
        SAGA_DESTROY_MULTIPLE,
        SAGA_DESTROY_MULTIPLE_LOOT
    };
    public static final String[] ALL_PGC_COLLECTION_TASK_NAMES = 
    {
        SAGA_DESTROY_MULTIPLE,
        SAGA_DESTROY_MULTIPLE_LOOT,
        SAGA_PERFORM,
        SAGA_CRAFT_ITEM,
        SAGA_COMM_MESSAGE,
        SAGA_GOTO_LOCATION,
        SAGA_RETRIEVE_ITEM,
        SAGA_PVP_OBJECTIVE
    };
    public static final String PGC_CASH_ITEM_TEMPLATE = "object/tangible/saga_system/pgc_loot_cash.iff";
    public static final String PGC_RELIC_SLOT_GOTO_GENERIC_LOC = "relic_goto_global_generic";
    public static final String PGC_RELIC_OBJ_GOTO_GENERIC_LOC = "saga_relic_goto_global_generic";
    public static final int PGC_CHRONICLE_BASE_FRAGEMENT_CHANCE = 18;
    public static final int PGC_CHRONICLE_BASE_CRAFTING_LOOT_CHANCE = 14;
    public static final int PGC_CHRONICLE_BASE_PVP_LOOT_CHANCE = 14;
    public static final String PGC_CHRONICLES_XP_TYPE = "chronicles";
    public static final String PGC_CHRONICLES_STARTING_SKILL = "class_chronicles_novice";
    public static final String PGC_CHRONICLES_COPPER_TOKEN = "item_pgc_token_01";
    public static final String PGC_CHRONICLES_SILVER_TOKEN = "item_pgc_token_02";
    public static final String PGC_CHRONICLES_GOLD_TOKEN = "item_pgc_token_03";
    public static final String PGC_CHRONICLES_RELIC_FRAGMENT = "item_pgc_relic_fragment";
    public static final String PGC_CHRONICLES_BOOSTER_PACK = "item_pgc_booster_pack";
    public static final String PGC_CHRONICLES_STARTER_KIT = "item_pgc_starter_kit";
    public static final String PGC_SKILLMOD_MAX_TASKS = "chronicles_max_tasks";
    public static final String PGC_SKILLMOD_RELIC_QUALITY = "chronicles_relic_quality";
    public static final String PGC_SKILLMOD_MAX_SHARED_QUESTS = "chronicles_max_quest_shared";
    public static final String PGC_SKILLMOD_RELIC_MASTERY = "chronicles_relic_mastery";
    public static final String PCG_QUEST_CREATOR_ID_OBJVAR = "chronicles.quest_creator_id";
    public static final String PCG_QUEST_CREATOR_NAME_OBJVAR = "chronicles.quest_creator_name";
    public static final String PCG_QUEST_CREATOR_STATIONID_OBJVAR = "chronicles.quest_creator_station_id";
    public static final String PCG_QUEST_VERSION_OBJVAR = "chronicles.version";
    public static final String PCG_QUEST_CUR_QUEST_PHASE_OBJVAR = "chronicles.quest_phase";
    public static final String PCG_QUEST_COMPLETE_OBJVAR = "chronicles.quest_complete";
    public static final String PCG_QUEST_WAS_RATED_OBJVAR = "chronicles.quest_was_rated";
    public static final String PCG_QUEST_SHARED_HOLOCRON_OBJVAR = "chronicles.quest_is_shared_version";
    public static final String PCG_QUEST_CAN_SHARE_OBJVAR = "chronicles.quest_can_be_shared";
    public static final String PCG_QUEST_CAN_SHARE_MAX_OBJVAR = "chronicles.quest_can_be_shared_max";
    public static final String PCG_QUEST_HOLOCRON_SHARED_WITH_LIST = "sharing.sharedWithList";
    public static final String PCG_QUEST_LEVEL_OBJVAR = "chronicles.quest_level";
    public static final String PCG_QUEST_NUM_TASKS_OBJVAR = "chronicles.num_tasks";
    public static final String PCG_QUEST_DIFFICULTY_OBJVAR = "chronicles.quest_difficulty";
    public static final String PCG_QUEST_NEED_KASHYYYK_OBJVAR = "chronicles.quest_requires_kashyyyk";
    public static final String PCG_QUEST_NEED_MUSTAFAR_OBJVAR = "chronicles.quest_requires_mustafar";
    public static final String PCG_QUEST_INVOLVES_PVP_OBJVAR = "chronicles.quest_involves_pvp";
    public static final String PCG_QUEST_INVOLVES_CRAFT_OBJVAR = "chronicles.quest_involves_crafting";
    public static final String PCG_QUEST_INVOLVES_ENTERTAIN_OBJVAR = "chronicles.quest_involves_entertaining";
    public static final String PCG_QUEST_INVOLVES_COMBAT_OBJVAR = "chronicles.quest_involves_combat";
    public static final String PCG_QUEST_WEIGHT_OBJVAR = "chronicles.quest_weight";
    public static final String PCG_QUEST_TASK_TYPE_DIVERSITY = "chronicles.quest_task_type_diversity";
    public static final String PCG_QUEST_RELIC_DIVERSITY = "chronicles.quest_relic_diversity";
    public static final String PCG_QUEST_CREATOR_AT_MAX_LEVEL_OBJVAR = "chronicles.creator_at_max_level";
    public static final String PCG_QUEST_ACTIVATED_TIME = "chronicles.questActivatedTime";
    public static final String PCG_QUEST_COMPLETED_TIME = "chronicles.questCompletedTime";
    public static final String PGC_GRANTED_ROADMAP_REWARDS_OBJVAR = "chroniclesRewards.grantedRoadmapRewards";
    public static final String PGC_RELIC_SLOT_DATA_OBJVAR = "chronicles.slotName";
    public static final int PGC_STORED_CHRONICLE_XP_INDEX = 0;
    public static final int PGC_STORED_CHRONICLE_SILVER_TOKENS_INDEX = 1;
    public static final int PGC_STORED_CHRONICLE_GOLD_TOKENS_INDEX = 2;
    public static final int PGC_NUM_QUESTS_CREATED_ALL_INDEX = 3;
    public static final int PGC_NUM_QUESTS_CREATED_MID_QUALITY_INDEX = 4;
    public static final int PGC_NUM_QUESTS_CREATED_HIGH_QUALITY_INDEX = 5;
    public static final int PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_ALL_INDEX = 6;
    public static final int PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_MID_QUALITY_INDEX = 7;
    public static final int PGC_NUM_YOUR_QUESTS_OTHERS_COMPLETED_HIGH_QUALITY_INDEX = 8;
    public static final int PGC_NUM_QUESTS_YOU_COMPLETED_ALL_INDEX = 9;
    public static final int PGC_NUM_QUESTS_YOU_COMPLETED_MID_QUALITY_INDEX = 10;
    public static final int PGC_NUM_QUESTS_YOU_COMPLETED_HIGH_QUALITY_INDEX = 11;
    public static final float PGC_MIN_MID_QUALITY_QUEST_WEIGHT = 15.0f;
    public static final float PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT = 30.0f;
    public static boolean activateQuestHolocron(obj_id questHolocron, obj_id player) throws InterruptedException
    {
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id questControlDevice = createObject(PGC_QUEST_CONTROL_DEVICE_TEMPLATE, datapad, "");
            if (isIdValid(questControlDevice))
            {
                putInOverloaded(questHolocron, questControlDevice);
                messageTo(questHolocron, "handleQuestHolocronActivated", null, 1, false);
                return true;
            }
        }
        return false;
    }
    public static boolean clearQuestHolocron(obj_id questControlDevice, obj_id player) throws InterruptedException
    {
        obj_id questHolocron = getQuestHolocronFromQCD(questControlDevice);
        if (isIdValid(questHolocron))
        {
            obj_id playerInventory = utils.getInventoryContainer(player);
            if (isIdValid(playerInventory))
            {
                resetQuestHolocron(questHolocron, player);
                sendDirtyObjectMenuNotification(questHolocron);
                putInOverloaded(questHolocron, playerInventory);
                return true;
            }
        }
        return false;
    }
    public static boolean isPgcQuestObject(obj_id object) throws InterruptedException
    {
        int gameObjectType = getGameObjectType(object);
        if (gameObjectType == GOT_chronicles_quest_holocron || gameObjectType == GOT_chronicles_quest_holocron_recipe)
        {
            return true;
        }
        return false;
    }
    public static boolean isPgcRelicObject(obj_id object) throws InterruptedException
    {
        int gameObjectType = getGameObjectType(object);
        if (gameObjectType == GOT_chronicles_relic)
        {
            return true;
        }
        return false;
    }
    public static boolean isSharedPlayerQuestObject(obj_id questHolocron) throws InterruptedException
    {
        return hasObjVar(questHolocron, PCG_QUEST_SHARED_HOLOCRON_OBJVAR);
    }
    public static obj_id getQuestHolocronFromQCD(obj_id questControlDevice) throws InterruptedException
    {
        obj_id[] questItems = getContents(questControlDevice);
        if (questItems != null && questItems.length > 0)
        {
            for (int i = 0; i < questItems.length; i++)
            {
                obj_id questItem = questItems[i];
                if (isPgcQuestObject(questItem))
                {
                    return questItem;
                }
            }
        }
        return obj_id.NULL_ID;
    }
    public static boolean canSharePgcQuest(obj_id questHolocron) throws InterruptedException
    {
        if (isPlayerQuestRecipe(questHolocron))
        {
            return false;
        }
        if (isSharedPlayerQuestObject(questHolocron))
        {
            return false;
        }
        if (!hasObjVar(questHolocron, pgc_quests.PCG_QUEST_CAN_SHARE_OBJVAR))
        {
            return false;
        }
        return true;
    }
    public static dictionary recreateSharedTaskDictionary(obj_id questHolocron) throws InterruptedException
    {
        dictionary webster = recreateQuestTaskDictionary(questHolocron);
        webster.put("isShared", true);
        return webster;
    }
    public static dictionary recreateQuestTaskDictionary(obj_id questHolocron) throws InterruptedException
    {
        dictionary webster = new dictionary();
        webster.put("questName", getPlayerQuestTitle(questHolocron));
        webster.put("questDescription", getPlayerQuestDescription(questHolocron));
        webster.put("recipe", false);
        webster.put("rewards", "0~0~0~0~0");
        webster.put("oldRecipe", obj_id.NULL_ID);
        webster.put("totalTasks", getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_NUM_TASKS_OBJVAR));
        for (int i = 0; i < pgc_quests.PGC_QUEST_MAX_NUM_TASKS; i++)
        {
            String phaseString = pgc_quests.getPhaseObjVarString(i);
            if (hasObjVar(questHolocron, phaseString))
            {
                for (int j = 0; j < pgc_quests.PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; j++)
                {
                    String taskString = pgc_quests.getTaskObjVarString(j);
                    String baseObjVar = pgc_quests.getPgcBaseObjVar(phaseString, taskString);
                    if (hasObjVar(questHolocron, baseObjVar))
                    {
                        String relicName = pgc_quests.getRelicName(questHolocron, phaseString, taskString);
                        String taskTitle = pgc_quests.getTaskName(questHolocron, phaseString, taskString);
                        String taskDescription = pgc_quests.getTaskDescription(questHolocron, phaseString, taskString);
                        String relicType = pgc_quests.getTaskType(questHolocron, phaseString, taskString);
                        String[] relicData = getCollectionSlotCategoryInfo(relicName);
                        String taskData = "";
                        if (relicType.equals(pgc_quests.SAGA_DESTROY_MULTIPLE))
                        {
                            int count = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
                            taskData += "5~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~" + count;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_DESTROY_MULTIPLE_LOOT))
                        {
                            int count = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
                            String message = pgc_quests.getStringPgcTaskData(questHolocron, baseObjVar, "message", relicData);
                            int dropRate = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "drop_rate", relicData);
                            taskData += "7~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~" + count + "~" + message + "~" + dropRate;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_PERFORM))
                        {
                            taskData += "6~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~unneededDummyData~unneededDummyData";
                        }
                        else if (relicType.equals(pgc_quests.SAGA_CRAFT_ITEM))
                        {
                            int count = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
                            taskData += "5~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~" + count;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_COMM_MESSAGE))
                        {
                            String message = pgc_quests.getStringPgcTaskData(questHolocron, baseObjVar, "message", relicData);
                            taskData += "5~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~" + message;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_GOTO_LOCATION))
                        {
                            dictionary waypointData = getWaypointObjVarTaskData(questHolocron, baseObjVar, "waypoint");
                            if (waypointData == null || waypointData.isEmpty())
                            {
                                waypointData = getWaypointRelicData("waypoint", relicData);
                            }
                            String waypointDataString = "";
                            if (waypointData == null || waypointData.isEmpty())
                            {
                                waypointDataString += "0:0:0:tatooine:Unknown";
                            }
                            else 
                            {
                                location waypointLoc = waypointData.getLocation("waypointLoc");
                                String waypointName = waypointData.getString("waypointName");
                                waypointDataString += waypointLoc.x + ":" + waypointLoc.y + ":" + waypointLoc.z + ":" + waypointLoc.area + ":" + waypointName;
                            }
                            taskData += "4~" + relicName + "~" + taskTitle + "~" + taskDescription + "~" + waypointDataString;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_RETRIEVE_ITEM))
                        {
                            int count = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
                            taskData += "5~" + relicName + "~" + taskTitle + "~" + taskDescription + "~unneededDummyData~" + count;
                        }
                        else if (relicType.equals(pgc_quests.SAGA_PVP_OBJECTIVE))
                        {
                            int count = pgc_quests.getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
                            taskData += "5~" + relicName + "~" + taskTitle + "~" + taskDescription + "~" + count + "~unneededDummyData";
                        }
                        webster.put("" + i, taskData);
                    }
                    else 
                    {
                        break;
                    }
                }
            }
            else 
            {
                break;
            }
        }
        return webster;
    }
    public static boolean handleChroniclesRelicCosts(obj_id player, dictionary taskDictionary) throws InterruptedException
    {
        int numTasks = taskDictionary.getInt("totalTasks");
        int maxTasksSkillMod = pgc_quests.getMaxChronicleQuestTasks(player);
        if (numTasks > maxTasksSkillMod)
        {
            numTasks = maxTasksSkillMod;
        }
        dictionary relicNames = getRelicCount(taskDictionary, numTasks);
        if (relicNames == null || relicNames.isEmpty())
        {
            return false;
        }
        if (!canCoverAllChroniclesRelicCosts(player, relicNames, numTasks))
        {
            return false;
        }
        else 
        {
            java.util.Enumeration keys = relicNames.keys();
            while (keys.hasMoreElements())
            {
                String relicName = (String)(keys.nextElement());
                int relicCount = relicNames.getInt(relicName);
                modifyCollectionSlotValue(player, relicName, relicCount * (-1));
            }
        }
        return true;
    }
    public static boolean canCoverAllChroniclesRelicCosts(obj_id player, dictionary relicNames, int numTasks) throws InterruptedException
    {
        boolean result = true;
        java.util.Enumeration keys = relicNames.keys();
        while (keys.hasMoreElements())
        {
            String relicName = (String)(keys.nextElement());
            int relicCount = relicNames.getInt(relicName);
            long relicSlotValue = getCollectionSlotValue(player, relicName);
            if (relicSlotValue < relicCount + 1)
            {
                result = false;
            }
        }
        return result;
    }
    public static dictionary getRelicCount(dictionary taskDictionary, int numTasks) throws InterruptedException
    {
        dictionary relicNames = new dictionary();
        for (int i = 0; i < numTasks; i++)
        {
            String taskName = "" + i;
            String taskData = taskDictionary.getString(taskName);
            String[] parse = split(taskData, '~');
            String relicName = parse[1];
            int relicNameCount = 0;
            if (relicNames.containsKey(relicName))
            {
                relicNameCount = relicNames.getInt(relicName);
            }
            relicNames.put(relicName, relicNameCount + 1);
        }
        return relicNames;
    }
    public static String getQuestName(obj_id questHolocron) throws InterruptedException
    {
        return getPlayerQuestTitle(questHolocron);
    }
    public static String getQuestDescription(obj_id questHolocron) throws InterruptedException
    {
        return getPlayerQuestDescription(questHolocron);
    }
    public static String getTaskName(obj_id questHolocron, int phaseNum, int taskNum) throws InterruptedException
    {
        String phaseString = getPhaseObjVarString(phaseNum);
        String taskString = getTaskObjVarString(taskNum);
        int taskIndex = getIntObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_index");
        return getPlayerQuestTaskTitle(questHolocron, taskIndex);
    }
    public static String getTaskName(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        int taskIndex = getIntObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_index");
        return getPlayerQuestTaskTitle(questHolocron, taskIndex);
    }
    public static String getTaskDescription(obj_id questHolocron, int phaseNum, int taskNum) throws InterruptedException
    {
        String phaseString = getPhaseObjVarString(phaseNum);
        String taskString = getTaskObjVarString(taskNum);
        int taskIndex = getIntObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_index");
        return getPlayerQuestTaskDescription(questHolocron, taskIndex);
    }
    public static String getTaskDescription(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        int taskIndex = getIntObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_index");
        return getPlayerQuestTaskDescription(questHolocron, taskIndex);
    }
    public static String getTaskType(obj_id questHolocron, int phaseNum, int taskNum) throws InterruptedException
    {
        String phaseString = getPhaseObjVarString(phaseNum);
        String taskString = getTaskObjVarString(taskNum);
        return getStringObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_type");
    }
    public static String getTaskType(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        return getStringObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_type");
    }
    public static String getRelicName(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        return getStringObjVarTaskData(questHolocron, getPgcBaseObjVar(phaseString, taskString), "task_relic");
    }
    public static String useFilteredQuestText(String text) throws InterruptedException
    {
        if (TEXT_FILTER_DISABLED)
        {
            return text;
        }
        return filterText(text);
    }
    public static int getMaxChronicleQuestTasks(obj_id player) throws InterruptedException
    {
        int maxTasksSkillmod = getEnhancedSkillStatisticModifierUncapped(player, pgc_quests.PGC_SKILLMOD_MAX_TASKS);
        if (maxTasksSkillmod < 1)
        {
            maxTasksSkillmod = 1;
        }
        if (maxTasksSkillmod > PGC_QUEST_MAX_NUM_TASKS)
        {
            maxTasksSkillmod = PGC_QUEST_MAX_NUM_TASKS;
        }
        return maxTasksSkillmod;
    }
    public static boolean passedQuestHolocronProfanityCheck(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        return true;
    }
    public static int getTaskLevel(String[] relicData) throws InterruptedException
    {
        int level = 1;
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(CATEGORY_NAME_RELIC_DATA))
                {
                    String[] parse = split(relicCategory, ':');
                    level = utils.stringToInt(parse[CATERGORY_RELIC_DATA_LEVEL_INDEX]);
                }
            }
        }
        return level;
    }
    public static String getTaskGroupSetting(String[] relicData) throws InterruptedException
    {
        String groupSetting = "solo";
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(CATEGORY_NAME_RELIC_DATA))
                {
                    String[] parse = split(relicCategory, ':');
                    int data = utils.stringToInt(parse[CATERGORY_RELIC_DATA_GROUP_INDEX]);
                    if (data >= 1)
                    {
                        groupSetting = "group";
                    }
                }
            }
        }
        return groupSetting;
    }
    public static int getTaskDifficultySetting(String[] relicData) throws InterruptedException
    {
        int difficultySetting = 0;
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(CATEGORY_NAME_RELIC_DATA))
                {
                    String[] parse = split(relicCategory, ':');
                    int data = utils.stringToInt(parse[CATERGORY_RELIC_DATA_GROUP_INDEX]);
                    if (data > -1)
                    {
                        difficultySetting = data;
                    }
                }
            }
        }
        return difficultySetting;
    }
    public static boolean getTaskKashyyykSetting(String[] relicData) throws InterruptedException
    {
        boolean requiresKashyyyk = false;
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(CATEGORY_NAME_RELIC_DATA))
                {
                    String[] parse = split(relicCategory, ':');
                    int data = utils.stringToInt(parse[CATERGORY_RELIC_DATA_KASHYYYK_INDEX]);
                    if (data == 1)
                    {
                        requiresKashyyyk = true;
                    }
                }
            }
        }
        return requiresKashyyyk;
    }
    public static boolean getTaskMustafarSetting(String[] relicData) throws InterruptedException
    {
        boolean requiresMustafar = false;
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(CATEGORY_NAME_RELIC_DATA))
                {
                    String[] parse = split(relicCategory, ':');
                    int data = utils.stringToInt(parse[CATERGORY_RELIC_DATA_MUSTAFAR_INDEX]);
                    if (data == 1)
                    {
                        requiresMustafar = true;
                    }
                }
            }
        }
        return requiresMustafar;
    }
    public static obj_id getQuestPlayer(obj_id questHolocron) throws InterruptedException
    {
        return utils.getContainingPlayer(questHolocron);
    }
    public static int getNumActiveQuests(obj_id player) throws InterruptedException
    {
        obj_id[] activeHolocrons = getActivateQuestHolocrons(player);
        if (activeHolocrons == null || activeHolocrons.length < 1)
        {
            return 0;
        }
        return activeHolocrons.length;
    }
    public static boolean hasMaxActivatedQuests(obj_id player) throws InterruptedException
    {
        return getNumActiveQuests(player) >= PGC_QUEST_MAX_NUM_QUESTS;
    }
    public static obj_id[] getActivateQuestHolocrons(obj_id player) throws InterruptedException
    {
        Vector dynamicQuestHolocronList = new Vector();
        dynamicQuestHolocronList.setSize(0);
        obj_id datapad = utils.getPlayerDatapad(player);
        if (isIdValid(datapad))
        {
            obj_id[] dataItems = getContents(datapad);
            if (dataItems != null && dataItems.length > 0)
            {
                for (int i = 0; i < dataItems.length; i++)
                {
                    obj_id dataItem = dataItems[i];
                    if (isIdValid(dataItem) && (getTemplateName(dataItem)).equals(PGC_QUEST_CONTROL_DEVICE_TEMPLATE))
                    {
                        obj_id[] questObjects = getContents(dataItem);
                        for (int j = 0; j < questObjects.length; j++)
                        {
                            obj_id questObject = questObjects[j];
                            if (isIdValid(questObject))
                            {
                                if (isPgcQuestObject(questObject))
                                {
                                    utils.addElement(dynamicQuestHolocronList, questObject);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (dynamicQuestHolocronList != null && dynamicQuestHolocronList.size() > 0)
        {
            return utils.toStaticObjIdArray(dynamicQuestHolocronList);
        }
        return null;
    }
    public static String getTaskObjVarString(int taskNum) throws InterruptedException
    {
        String taskString = "task_0" + taskNum;
        if (taskNum >= 10)
        {
            taskString = "task_" + taskNum;
        }
        return taskString;
    }
    public static String getPgcBaseObjVar(String phaseString, String taskString) throws InterruptedException
    {
        return phaseString + "." + taskString;
    }
    public static boolean phaseHasActiveTaskOfType(obj_id questHolocron, String taskType, String phaseString) throws InterruptedException
    {
        for (int i = 0; i < PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = getTaskObjVarString(i);
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            if (hasObjVar(questHolocron, baseObjVar))
            {
                String phaseTaskCompleteObjVar = baseObjVar + ".task_complete";
                if (!hasObjVar(questHolocron, phaseTaskCompleteObjVar))
                {
                    if ((getActiveTaskTaskType(questHolocron, phaseString, taskString)).equals(taskType))
                    {
                        return true;
                    }
                }
            }
            else 
            {
                break;
            }
        }
        return false;
    }
    public static boolean phaseHasActiveTask(obj_id questHolocron, String phaseString) throws InterruptedException
    {
        for (int i = 0; i < PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = getTaskObjVarString(i);
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            if (hasObjVar(questHolocron, baseObjVar))
            {
                if (!hasObjVar(questHolocron, baseObjVar + ".task_complete"))
                {
                    return true;
                }
            }
            else 
            {
                break;
            }
        }
        return false;
    }
    public static String getActiveTaskTaskType(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        String taskType = "";
        String phaseTaskTypeObjVar = getPgcBaseObjVar(phaseString, taskString) + ".task_type";
        if (hasObjVar(questHolocron, phaseTaskTypeObjVar))
        {
            taskType = getStringObjVar(questHolocron, phaseTaskTypeObjVar);
        }
        return taskType;
    }
    public static int incrementTaskCounter(obj_id questHolocron, String baseObjVar) throws InterruptedException
    {
        int currentCount = 1;
        String currentCountObjVar = baseObjVar + "." + "current_count";
        if (hasObjVar(questHolocron, currentCountObjVar))
        {
            currentCount = getIntObjVar(questHolocron, currentCountObjVar) + 1;
        }
        int taskIndex = getIntObjVarTaskData(questHolocron, baseObjVar, "task_index");
        setPlayerQuestTaskCounter(questHolocron, taskIndex, currentCount);
        setObjVar(questHolocron, currentCountObjVar, currentCount);
        return currentCount;
    }
    public static boolean isTaskComplete(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
        return hasObjVar(questHolocron, baseObjVar + ".task_complete");
    }
    public static int getActivePhaseNum(obj_id questHolocron) throws InterruptedException
    {
        int phaseNum = 0;
        if (hasObjVar(questHolocron, pgc_quests.PCG_QUEST_CUR_QUEST_PHASE_OBJVAR))
        {
            phaseNum = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_CUR_QUEST_PHASE_OBJVAR);
        }
        return phaseNum;
    }
    public static String getPhaseObjVarString(int phaseNum) throws InterruptedException
    {
        String phaseString = "phase_0" + phaseNum;
        if (phaseNum >= 10)
        {
            phaseString = "phase_" + phaseNum;
        }
        return phaseString;
    }
    public static String getActivePhaseObjVarString(obj_id questHolocron) throws InterruptedException
    {
        int phaseNum = getActivePhaseNum(questHolocron);
        String phaseString = getPhaseObjVarString(phaseNum);
        return phaseString;
    }
    public static void setTaskComplete(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        String completedTaskBaseObjVar = getPgcBaseObjVar(phaseString, taskString);
        setObjVar(questHolocron, completedTaskBaseObjVar + ".task_complete", true);
        removePlayerQuestWaypoint(questHolocron, completedTaskBaseObjVar);
        int completedTaskIndex = getIntObjVarTaskData(questHolocron, completedTaskBaseObjVar, "task_index");
        setPlayerQuestTaskStatus(questHolocron, completedTaskIndex, PQ_TASK_COMPLETE);
        if (!phaseHasActiveTask(questHolocron, phaseString))
        {
            int phaseNum = incrementActivePhaseNum(questHolocron);
            if (phaseNum > 0)
            {
                String newPhaseString = getPhaseObjVarString(phaseNum);
                for (int i = 0; i < PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
                {
                    String activatedTaskString = getTaskObjVarString(i);
                    String activatedTaskBaseObjVar = getPgcBaseObjVar(newPhaseString, activatedTaskString);
                    if (hasObjVar(questHolocron, activatedTaskBaseObjVar))
                    {
                        int activatedTaskIndex = getIntObjVarTaskData(questHolocron, activatedTaskBaseObjVar, "task_index");
                        setPlayerQuestTaskStatus(questHolocron, activatedTaskIndex, PQ_TASK_ACTIVE);
                        handleTaskActived(questHolocron, phaseNum, i);
                    }
                    else 
                    {
                        break;
                    }
                }
            }
        }
        return;
    }
    public static void removePlayerQuestWaypoint(obj_id questHolocron, String baseObjVar) throws InterruptedException
    {
        String activeWaypointObjVar = baseObjVar + ".waypointActive";
        if (hasObjVar(questHolocron, activeWaypointObjVar))
        {
            obj_id waypoint = getObjIdObjVar(questHolocron, activeWaypointObjVar);
            obj_id player = getQuestPlayer(questHolocron);
            if (isIdValid(waypoint) && isIdValid(player))
            {
                destroyWaypointInDatapad(waypoint, player);
                removeObjVar(questHolocron, activeWaypointObjVar);
            }
        }
    }
    public static void activatePlayerQuestWaypointFromHolocron(obj_id questHolocron, String baseObjVar) throws InterruptedException
    {
        String activeWaypointObjVar = baseObjVar + ".waypointActive";
        if (hasObjVar(questHolocron, activeWaypointObjVar))
        {
            obj_id waypoint = getObjIdObjVar(questHolocron, activeWaypointObjVar);
            obj_id player = getQuestPlayer(questHolocron);
            activatePlayerQuestWaypoint(player, waypoint);
        }
    }
    public static void activatePlayerQuestWaypoint(obj_id player, obj_id waypoint) throws InterruptedException
    {
        if (isIdValid(waypoint) && isIdValid(player))
        {
            location waypointLoc = getWaypointLocation(waypoint);
            String waypointPlanet = waypointLoc.area;
            location playerLoc = getLocation(player);
            String playerPlanet = playerLoc.area;
            if (playerPlanet.equals(waypointPlanet))
            {
                setWaypointActive(waypoint, true);
            }
        }
    }
    public static int incrementActivePhaseNum(obj_id questHolocron) throws InterruptedException
    {
        int phaseNum = getActivePhaseNum(questHolocron) + 1;
        if (hasObjVar(questHolocron, getPhaseObjVarString(phaseNum)))
        {
            setObjVar(questHolocron, pgc_quests.PCG_QUEST_CUR_QUEST_PHASE_OBJVAR, phaseNum);
        }
        else 
        {
            setQuestComplete(questHolocron);
            phaseNum = -1;
        }
        return phaseNum;
    }
    public static void checkForPhaseComplete(obj_id questHolocron, String phaseString) throws InterruptedException
    {
        if (!phaseHasActiveTask(questHolocron, phaseString))
        {
            int phaseNum = incrementActivePhaseNum(questHolocron);
            if (phaseNum > 0)
            {
                handlePhaseActived(questHolocron, phaseNum);
                return;
            }
        }
        return;
    }
    public static void handlePhaseActived(obj_id questHolocron, int phaseNum) throws InterruptedException
    {
        String phaseString = getPhaseObjVarString(phaseNum);
        for (int i = 0; i < PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; i++)
        {
            String taskString = getTaskObjVarString(i);
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            if (hasObjVar(questHolocron, baseObjVar))
            {
                handleTaskActived(questHolocron, phaseNum, i);
            }
            else 
            {
                break;
            }
        }
        return;
    }
    public static void handleTaskActived(obj_id questHolocron, int phaseNum, int taskNum) throws InterruptedException
    {
        String phaseString = getPhaseObjVarString(phaseNum);
        String taskString = getTaskObjVarString(taskNum);
        obj_id player = getQuestPlayer(questHolocron);
        if (isIdValid(player))
        {
            String taskName = getTaskName(questHolocron, phaseString, taskString);
            if (taskName.length() <= 0)
            {
                taskName = phaseString + " - " + taskString;
            }
            string_id taskActivatedMsg = new string_id("saga_system", "holocron_task_activated");
            prose_package pp_title = prose.getPackage(taskActivatedMsg, player, player);
            prose.setTO(pp_title, taskName);
            sendSystemMessageProse(player, pp_title);
            string_id taskDescMsg = new string_id("saga_system", "holocron_task_active_desc");
            prose_package pp_desc = prose.getPackage(taskDescMsg, player, player);
            prose.setTO(pp_desc, getTaskDescription(questHolocron, phaseString, taskString));
            sendSystemMessageProse(player, pp_desc);
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            String relicName = getRelicName(questHolocron, phaseString, taskString);
            String[] relicData = getCollectionSlotCategoryInfo(relicName);
            dictionary waypointData = getWaypointObjVarTaskData(questHolocron, baseObjVar, "waypoint");
            if (waypointData == null || waypointData.isEmpty())
            {
                waypointData = getWaypointRelicData("waypoint", relicData);
            }
            location waypointLoc = null;
            String waypointName = "";
            if (waypointData != null && !waypointData.isEmpty())
            {
                waypointLoc = waypointData.getLocation("waypointLoc");
                waypointName = waypointData.getString("waypointName");
            }
            if (waypointLoc != null && waypointName.length() > 0)
            {
                obj_id waypoint = createWaypointInDatapad(player, waypointLoc);
                setWaypointName(waypoint, waypointName);
                activatePlayerQuestWaypoint(player, waypoint);
                setObjVar(questHolocron, baseObjVar + ".waypointActive", waypoint);
            }
            String taskType = getTaskType(questHolocron, phaseString, taskString);
            if (taskType != null && taskType.length() > 0)
            {
                if (taskType.equals(SAGA_COMM_MESSAGE))
                {
                    handleCommMessageTaskActivated(questHolocron, phaseString, taskString);
                }
                else if (taskType.equals(SAGA_GOTO_LOCATION))
                {
                    if (waypointLoc != null)
                    {
                        handleGoToLocationTaskActivated(questHolocron, phaseString, taskString, waypointLoc);
                    }
                }
            }
        }
        return;
    }
    public static float getDestroyTaskWeight(int taskLevel, int taskDifficulty, int taskCounterMax) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        int counterMod = 0;
        if (taskDifficulty == 2)
        {
            counterMod = 2;
        }
        else if (taskCounterMax > 4 && taskCounterMax <= 10)
        {
            counterMod = 1;
        }
        else if (taskCounterMax > 10)
        {
            counterMod = 2;
        }
        int difficultyMod = counterMod + taskDifficulty + 1;
        weight = (float)(levelMod + difficultyMod) / 2;
        return weight;
    }
    public static float getDestroyLootTaskWeight(int taskLevel, int taskDifficulty, int taskCounterMax, int dropRate) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        float dropRateFraction = dropRate / 100;
        int percentageBasedTaskCounter = (int)(taskCounterMax / dropRateFraction);
        int counterMod = 0;
        if (taskDifficulty == 2)
        {
            counterMod = 2;
        }
        else if (percentageBasedTaskCounter > 4 && percentageBasedTaskCounter <= 10)
        {
            counterMod = 1;
        }
        else if (percentageBasedTaskCounter > 10)
        {
            counterMod = 2;
        }
        int difficultyMod = counterMod + taskDifficulty + 1;
        weight = (float)(levelMod + difficultyMod) / 2;
        return weight;
    }
    public static float getPerformTaskWeight(int taskLevel) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        weight = levelMod;
        return weight;
    }
    public static float getCraftingTaskWeight(int taskLevel, int taskCounterMax) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        int counterMod = 0;
        if (taskCounterMax > 3 && taskCounterMax <= 6)
        {
            counterMod = 1;
        }
        else if (taskCounterMax > 6 && taskCounterMax <= 10)
        {
            counterMod = 3;
        }
        else if (taskCounterMax > 10)
        {
            counterMod = 5;
        }
        weight = (float)(levelMod + counterMod) / 2;
        return weight;
    }
    public static float getCommTaskWeight(int taskLevel) throws InterruptedException
    {
        float weight = 0.0f;
        return weight;
    }
    public static float getGoToTaskWeight(int taskLevel) throws InterruptedException
    {
        float weight = 0.5f;
        return weight;
    }
    public static float getRetrieveTaskWeight(int taskLevel, int taskCounterMax) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        int counterMod = 0;
        if (taskCounterMax > 4 && taskCounterMax <= 10)
        {
            counterMod = 1;
        }
        else if (taskCounterMax > 10)
        {
            counterMod = 3;
        }
        weight = (float)(levelMod + counterMod) / 2;
        return weight;
    }
    public static float getPvpTaskWeight(int taskLevel, int taskCounterMax) throws InterruptedException
    {
        float weight = 1.0f;
        int levelMod = taskLevel / 20 + 2;
        if (levelMod > 5)
        {
            levelMod = 5;
        }
        int counterMod = 0;
        if (taskCounterMax > 4 && taskCounterMax <= 10)
        {
            counterMod = 1;
        }
        else if (taskCounterMax > 10)
        {
            counterMod = 3;
        }
        weight = (float)(levelMod + counterMod) / 2;
        return weight;
    }
    public static float calculateQuestWeight(float[] taskWeightArray) throws InterruptedException
    {
        int numTasks = taskWeightArray.length;
        Arrays.sort(taskWeightArray);
        int offset = 0;
        if (numTasks > 3 && numTasks <= 7)
        {
            offset = 1;
        }
        else if (numTasks > 7)
        {
            offset = 2;
        }
        float questWeight = 0.0f;
        for (int i = offset; i < taskWeightArray.length; i++)
        {
            questWeight += taskWeightArray[i];
        }
        questWeight = questWeight;
        return questWeight;
    }
    public static float calculateTaskTypeDiversity(dictionary taskTypes, int numTasks) throws InterruptedException
    {
        float questTaskTypeDiversity = 0;
        float numDifferentTaskTypes = taskTypes.size();
        if (numTasks > 0)
        {
            questTaskTypeDiversity = numDifferentTaskTypes / numTasks;
        }
        return questTaskTypeDiversity;
    }
    public static float calculateRelicDiversity(dictionary relicNames, int numTasks) throws InterruptedException
    {
        float questRelicDiversity = 0;
        float numDifferentRelics = relicNames.size();
        int offset = 0;
        if (numTasks > 3 && numTasks <= 7)
        {
            offset = 1;
        }
        else if (numTasks > 7)
        {
            offset = 2;
        }
        int numDiversityTasks = numTasks - offset;
        if (numDiversityTasks > 0)
        {
            questRelicDiversity = numDifferentRelics / numDiversityTasks;
        }
        if (questRelicDiversity > 1)
        {
            questRelicDiversity = 1;
        }
        return questRelicDiversity;
    }
    public static int calculateHolocronCreationChroniclesXp(float questWeight, float questRelicDiversity, float questTaskTypeDiversity) throws InterruptedException
    {
        if (questWeight < 1.0f)
        {
            questWeight = 1.0f;
        }
        int xp = (int)(CHRONICLE_CREATION_XP_BASE * questWeight * questRelicDiversity);
        xp += xp * questTaskTypeDiversity * 0.01;
        return xp;
    }
    public static int calculateHolocronQuestCompletedChroniclesXp(obj_id questHolocron, int rating) throws InterruptedException
    {
        float questWeight = getFloatObjVar(questHolocron, pgc_quests.PCG_QUEST_WEIGHT_OBJVAR);
        float questRelicDiversity = getFloatObjVar(questHolocron, pgc_quests.PCG_QUEST_RELIC_DIVERSITY);
        float questTaskTypeDiversity = getFloatObjVar(questHolocron, pgc_quests.PCG_QUEST_TASK_TYPE_DIVERSITY);
        if (questWeight < 1.0f)
        {
            questWeight = 1.0f;
        }
        int xp = (int)(CHRONICLE_SHARED_XP_BASE * questWeight * questRelicDiversity);
        xp += xp * questTaskTypeDiversity * 0.01;
        xp += xp * (rating / 2.5) * 0.03;
        return xp;
    }
    public static int grantChronicleXp(obj_id player, int xpAmount) throws InterruptedException
    {
        String[] chronicleSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (chronicleSkills != null && chronicleSkills.length > 0)
        {
            String finalChronicleSkill = chronicleSkills[chronicleSkills.length - 1];
            if (hasSkill(player, finalChronicleSkill))
            {
                return 0;
            }
            xpAmount = getConfigModifiedChroniclesXPAmount(xpAmount);
            xpAmount = xp.grant(player, PGC_CHRONICLES_XP_TYPE, xpAmount);
            showPgcXpGrantedMessage(player, PGC_CHRONICLES_XP_TYPE, xpAmount);
            messageTo(player, "handleCheckForGainedChroniclesLevelDelay", null, 1, false);
        }
        return xpAmount;
    }
    public static int getConfigModifiedChroniclesXPAmount(int xpAmount) throws InterruptedException
    {
        String configMod_string = getConfigSetting("GameServer", "chroniclesXpModifier");
        if (configMod_string != null && configMod_string.length() > 0)
        {
            float configMod = utils.stringToFloat(configMod_string);
            if (configMod > 0)
            {
                xpAmount *= configMod;
            }
        }
        return xpAmount;
    }
    public static void checkForGainedChroniclesLevel(obj_id player) throws InterruptedException
    {
        String[] chronicleSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (chronicleSkills != null && chronicleSkills.length > 0)
        {
            String currentChronicleSkill = chronicleSkills[0];
            String nextChronicleSkill = "";
            int nextChronicleSkillIndex = -1;
            for (int i = 0; i < chronicleSkills.length; i++)
            {
                if (hasSkill(player, chronicleSkills[i]))
                {
                    currentChronicleSkill = chronicleSkills[i];
                }
                else 
                {
                    nextChronicleSkill = chronicleSkills[i];
                    nextChronicleSkillIndex = i;
                    break;
                }
            }
            boolean hasRequiredSkills = skill.hasRequiredSkillsForSkillPurchase(player, nextChronicleSkill);
            boolean hasRequiredXp = skill.hasRequiredXpForSkillPurchase(player, nextChronicleSkill);
            reportChronicleXPRequiredForNextSkill(player, nextChronicleSkill);
            if (hasRequiredSkills && hasRequiredXp)
            {
                if (nextChronicleSkill.length() > 0)
                {
                    grantSkill(player, nextChronicleSkill);
                    showFlyText(player, new string_id("cbt_spam", "level_up_chronicles"), 2.5f, colors.GOLDENROD);
                    playClientEffectObj(player, "clienteffect/level_granted_chronicles.cef", player, null);
                    string_id skillGainMsg = new string_id("saga_system", "chronicles_skill_gained");
                    prose_package pp_title = prose.getPackage(skillGainMsg, player, player);
                    prose.setTO(pp_title, new string_id("skl_n", nextChronicleSkill));
                    sendSystemMessageProse(player, pp_title);
                    grantChroniclesRoadmapItem(player, nextChronicleSkill, nextChronicleSkillIndex);
                    pgc_quests.logProgression(player, obj_id.NULL_ID, "Chronicles Profession Level Up! Skill granted: " + nextChronicleSkill);
                }
            }
        }
        return;
    }
    public static void reportChronicleXPRequiredForNextSkill(obj_id player, String skillName) throws InterruptedException
    {
        if (isGod(player) && questGetDebugging())
        {
            dictionary xpReqs = getSkillPrerequisiteExperience(skillName);
            if (xpReqs != null && !xpReqs.isEmpty())
            {
                java.util.Enumeration e = xpReqs.keys();
                while (e.hasMoreElements())
                {
                    String xpType = (String)(e.nextElement());
                    int xpCost = xpReqs.getInt(xpType);
                    int curXP = getExperiencePoints(player, xpType);
                    sendSystemMessage(player, "[God Mode] For xpType = " + xpType + ", player has " + curXP + " of " + xpCost, "");
                }
            }
        }
        return;
    }
    public static boolean grantChroniclesRoadmapItem(obj_id player, String skillName, int skillIndex) throws InterruptedException
    {
        boolean success = false;
        String[] chronicleSkills = skill_template.getSkillTemplateSkillsByTemplateName(pgc_quests.PGC_CHRONICLES_XP_TYPE);
        if (chronicleSkills != null && chronicleSkills.length > 0)
        {
            int[] getGrantedRewardsArray = new int[chronicleSkills.length];
            for (int q = 0; q < chronicleSkills.length; q++)
            {
                getGrantedRewardsArray[q] = -1;
            }
            if (hasObjVar(player, PGC_GRANTED_ROADMAP_REWARDS_OBJVAR))
            {
                getGrantedRewardsArray = getIntArrayObjVar(player, PGC_GRANTED_ROADMAP_REWARDS_OBJVAR);
            }
            int alreadyHasReward = getGrantedRewardsArray[skillIndex];
            if (alreadyHasReward <= -1)
            {
                String skillTemplate = "chronicles";
                String itemGrant = skill_template.getRoadmapItem(player, skillTemplate, skillName);
                if (itemGrant == null || itemGrant.equals(""))
                {
                    getGrantedRewardsArray[skillIndex] = 0;
                    setObjVar(player, PGC_GRANTED_ROADMAP_REWARDS_OBJVAR, getGrantedRewardsArray);
                    return false;
                }
                String[] items = split(itemGrant, ',');
                if (items == null || items.length == 0)
                {
                    return false;
                }
                Vector allNewObjectsResizable = new Vector();
                allNewObjectsResizable.setSize(0);
                for (int i = 0; i < items.length; i++)
                {
                    obj_id newItem = null;
                    if (items[i].endsWith(".iff"))
                    {
                        newItem = createObjectInInventoryAllowOverload(items[i], player);
                    }
                    else 
                    {
                        newItem = static_item.createNewItemFunction(items[i], player);
                    }
                    if (!isIdValid(newItem))
                    {
                        LOG("roadmap", "ERROR - Could not create roadmap item (" + items[i] + ")");
                    }
                    else 
                    {
                        utils.addElement(allNewObjectsResizable, newItem);
                        String newItemName = "";
                        if (static_item.isStaticItem(newItem))
                        {
                            newItemName = getStaticItemName(newItem);
                        }
                        else 
                        {
                            newItemName = getTemplateName(newItem);
                        }
                        pgc_quests.logProgression(player, obj_id.NULL_ID, "Chronicles Roadmap Reward Granted! Item granted to " + player + ": " + newItem + " (" + newItemName + ").");
                    }
                }
                if (allNewObjectsResizable != null && allNewObjectsResizable.size() > 0)
                {
                    getGrantedRewardsArray[skillIndex] = getCalendarTime();
                    string_id itemDesc = utils.unpackString(skill_template.getRoadmapItemDesc(skillTemplate, skillName));
                    prose_package pp = prose.getPackage(new string_id("base_player", "skill_template_item_reward"), itemDesc);
                    sendSystemMessageProse(player, pp);
                    obj_id[] allNewObjects = new obj_id[0];
                    if (allNewObjectsResizable != null)
                    {
                        allNewObjects = new obj_id[allNewObjectsResizable.size()];
                        allNewObjectsResizable.toArray(allNewObjects);
                    }
                    showLootBox(player, allNewObjects);
                    success = true;
                }
            }
            else 
            {
                if (isGod(player))
                {
                    sendSystemMessage(player, "[God Mode]: Already received this reward.", "");
                }
            }
            setObjVar(player, PGC_GRANTED_ROADMAP_REWARDS_OBJVAR, getGrantedRewardsArray);
        }
        return success;
    }
    public static void setQuestComplete(obj_id questHolocron) throws InterruptedException
    {
        obj_id questControlDevice = getContainedBy(questHolocron);
        if (isIdValid(questControlDevice) && (getTemplateName(questControlDevice)).equals(PGC_QUEST_CONTROL_DEVICE_TEMPLATE))
        {
            obj_id player = getQuestPlayer(questHolocron);
            if (isIdValid(player))
            {
                if (clearQuestHolocron(questControlDevice, player))
                {
                    if (isPlayerQuestRecipe(questHolocron))
                    {
                        resetQuestHolocron(questHolocron, player);
                    }
                    else 
                    {
                        setObjVar(questHolocron, PCG_QUEST_COMPLETE_OBJVAR, player);
                        setOwner(questHolocron, player);
                        int chroniclerStationId = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_CREATOR_STATIONID_OBJVAR);
                        if (chroniclerStationId == getPlayerStationId(player))
                        {
                            setObjVar(questHolocron, pgc_quests.PCG_QUEST_WAS_RATED_OBJVAR, -1);
                            sendDirtyObjectMenuNotification(questHolocron);
                        }
                        else 
                        {
                            showQuestRatingUI(questHolocron, player);
                        }
                        String name = getAssignedName(questHolocron);
                        setName(questHolocron, "[Completed] " + name);
                        pgc_quests.grantPgcNonChroniclesQuestXp(player, questHolocron);
                        int tokenCount = pgc_quests.getNumQuestCompleteQuestorRewardTokens(player, questHolocron);
                        obj_id token = pgc_quests.grantSilverChroniclesRewardTokens(player, tokenCount);
                        float questWeight = getFloatObjVar(questHolocron, pgc_quests.PCG_QUEST_WEIGHT_OBJVAR);
                        logReward(player, questHolocron, "Silver Tokens awared (" + tokenCount + ") for completing a quest of weight=" + questWeight);
                        pgcAdjustRatingData(player, getName(player), pgc_quests.PGC_NUM_QUESTS_YOU_COMPLETED_ALL_INDEX, 1);
                        if (questWeight >= pgc_quests.PGC_MIN_MID_QUALITY_QUEST_WEIGHT && questWeight < pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                        {
                            pgcAdjustRatingData(player, getName(player), pgc_quests.PGC_NUM_QUESTS_YOU_COMPLETED_MID_QUALITY_INDEX, 1);
                        }
                        else if (questWeight >= pgc_quests.PGC_MIN_HIGH_QUALITY_QUEST_WEIGHT)
                        {
                            pgcAdjustRatingData(player, getName(player), pgc_quests.PGC_NUM_QUESTS_YOU_COMPLETED_HIGH_QUALITY_INDEX, 1);
                        }
                        if (questWeight >= pgc_quests.PGC_MIN_MID_QUALITY_QUEST_WEIGHT && !isCompletingYourOwnChronicleQuest(player, questHolocron))
                        {
                            int goldTokenChance = CHRONICLES_QUESTOR_GOLD_TOKEN_CHANCE;
                            String configChance_string = getConfigSetting("GameServer", "chroniclesQuestorGoldTokenChanceOverride");
                            if (configChance_string != null && configChance_string.length() > 0)
                            {
                                int configChance = utils.stringToInt(configChance_string);
                                if (configChance > 0)
                                {
                                    goldTokenChance = configChance;
                                }
                            }
                            int goldTokenRoll = rand(1, 100);
                            if (goldTokenRoll <= goldTokenChance)
                            {
                                grantGoldChroniclesRewardTokens(player, 1);
                                showFlyText(player, new string_id("saga_system", "bonus_gold_token"), 2.0f, colors.GOLDENROD);
                                logReward(player, questHolocron, "Gold Token awared for completing a quest of weight=" + questWeight);
                            }
                        }
                    }
                    play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_COMPLETED);
                    String questName = getQuestName(questHolocron);
                    string_id message = new string_id("saga_system", "holocron_quest_complete");
                    prose_package pp = prose.getPackage(message, player, player);
                    prose.setTO(pp, questName);
                    sendSystemMessageProse(player, pp);
                    int currentTime = getCalendarTime();
                    setObjVar(questHolocron, pgc_quests.PCG_QUEST_COMPLETED_TIME, currentTime);
                    String timeToCompleteQuest = utils.formatTimeVerbose(currentTime - getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_ACTIVATED_TIME));
                    pgc_quests.logQuest(player, questHolocron, "Quest completed after " + timeToCompleteQuest + ".");
                    destroyObject(questControlDevice);
                }
            }
        }
    }
    public static void grantPgcNonChroniclesQuestXp(obj_id player, obj_id questHolocron) throws InterruptedException
    {
        int questLevel = getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_LEVEL_OBJVAR);
        obj_id questCreatorObjId = getObjIdObjVar(questHolocron, PCG_QUEST_CREATOR_ID_OBJVAR);
        int questCreatorStationId = getIntObjVar(questHolocron, PCG_QUEST_CREATOR_STATIONID_OBJVAR);
        float questWeight = getFloatObjVar(questHolocron, PCG_QUEST_WEIGHT_OBJVAR);
        pgc_quests.grantPgcNonChroniclesQuestXp(player, questLevel, questWeight, questCreatorObjId, questCreatorStationId);
        return;
    }
    public static void grantPgcNonChroniclesQuestXp(obj_id player, int questLevel, float questWeight, obj_id questCreatorObjId, int questCreatorStationId) throws InterruptedException
    {
        float xpWeight = questWeight * 0.5f;
        if (xpWeight < 5.0f)
        {
            xpWeight = 5.0f;
        }
        else if (xpWeight > 25.0f)
        {
            xpWeight = 25.0f;
        }
        int xpAmount = (int)(groundquests.getQuestExperienceReward(player, questLevel, 1, 0) * xpWeight);
        int playerStationId = getPlayerStationId(player);
        if (playerStationId == questCreatorStationId)
        {
            xpAmount *= 0.5;
        }
        String template = getSkillTemplate(player);
        String xpType = xp.QUEST_COMBAT;
        if (template.startsWith("trader"))
        {
            xpAmount = xp.grantCraftingQuestXp(player, xpAmount);
            xpType = xp.QUEST_CRAFTING;
        }
        else if (template.startsWith("entertainer"))
        {
            xpAmount = xp.grantSocialStyleXp(player, xp.QUEST_SOCIAL, xpAmount);
            xpType = xp.QUEST_SOCIAL;
        }
        else 
        {
            xpAmount = xp.grantCombatStyleXp(player, xp.QUEST_COMBAT, xpAmount);
            xpType = xp.QUEST_COMBAT;
        }
        xp.displayXpFlyText(player, player, xpAmount);
        return;
    }
    public static void showPgcXpGrantedMessage(obj_id player, String xpType, int xpAmount) throws InterruptedException
    {
        float grpMod = 1f;
        if (utils.hasScriptVar(player, "combat.xp.groupBonus"))
        {
            grpMod = utils.getFloatScriptVar(player, "combat.xp.groupBonus");
        }
        float inspMod = xp.getInspirationBuffXpModifier(player, xpType);
        prose_package pp = xp.getXpProsePackage(xpType, xpAmount, grpMod, inspMod);
        if (pp != null)
        {
            sendQuestSystemMessage(player, pp);
        }
        return;
    }
    public static int getNumQuestCompleteChroniclerRewardTokens(obj_id questHolocron) throws InterruptedException
    {
        obj_id questCreatorObjId = getObjIdObjVar(questHolocron, PCG_QUEST_CREATOR_ID_OBJVAR);
        int questCreatorStationId = getIntObjVar(questHolocron, PCG_QUEST_CREATOR_STATIONID_OBJVAR);
        float questWeight = getFloatObjVar(questHolocron, PCG_QUEST_WEIGHT_OBJVAR);
        float relicTypeDiversity = getFloatObjVar(questHolocron, PCG_QUEST_RELIC_DIVERSITY);
        int count = 1;
        count = (int)((float)(count + (questWeight * relicTypeDiversity)) * CHRONICLE_SILVER_TOKENS_FOR_CHRONICLER_MOD);
        String tokenNumMod_string = getConfigSetting("GameServer", "chroniclesChroniclerSilverTokenNumModifier");
        if (tokenNumMod_string != null && tokenNumMod_string.length() > 0)
        {
            float tokenNumMod = utils.stringToFloat(tokenNumMod_string);
            if (tokenNumMod > 0)
            {
                count *= tokenNumMod;
            }
        }
        return count;
    }
    public static int getNumQuestCompleteQuestorRewardTokens(obj_id player, obj_id questHolocron) throws InterruptedException
    {
        if (isCompletingYourOwnChronicleQuest(player, questHolocron))
        {
            return 0;
        }
        float questWeight = getFloatObjVar(questHolocron, PCG_QUEST_WEIGHT_OBJVAR);
        float relicTypeDiversity = getFloatObjVar(questHolocron, PCG_QUEST_RELIC_DIVERSITY);
        int count = 1;
        count = (int)((float)(count + (questWeight * relicTypeDiversity)) * CHRONICLE_SILVER_TOKENS_FOR_QUESTOR_MOD);
        String tokenNumMod_string = getConfigSetting("GameServer", "chroniclesQuestorSilverTokenNumModifier");
        if (tokenNumMod_string != null && tokenNumMod_string.length() > 0)
        {
            float tokenNumMod = utils.stringToFloat(tokenNumMod_string);
            if (tokenNumMod > 0)
            {
                count *= tokenNumMod;
            }
        }
        return count;
    }
    public static obj_id grantSilverChroniclesRewardTokens(obj_id player, int count) throws InterruptedException
    {
        return grantChroniclesRewardTokens(player, count, PGC_CHRONICLES_SILVER_TOKEN);
    }
    public static obj_id grantGoldChroniclesRewardTokens(obj_id player, int count) throws InterruptedException
    {
        return grantChroniclesRewardTokens(player, count, PGC_CHRONICLES_GOLD_TOKEN);
    }
    public static obj_id grantChroniclesRewardTokens(obj_id player, int count, String tokenType) throws InterruptedException
    {
        obj_id token = obj_id.NULL_ID;
        if (count > 0)
        {
            obj_id inventory = utils.getInventoryContainer(player);
            for (int i = count; i > 0; i = i - 500)
            {
                int tempCount = 500;
                if (i < 500)
                {
                    tempCount = i;
                }
                token = static_item.createNewItemFunction(tokenType, inventory, tempCount);
            }
            pgc_quests.sendPlacedInInventorySystemMessage(player, token, count);
        }
        return token;
    }
    public static boolean isCompletingYourOwnChronicleQuest(obj_id player, obj_id questHolocron) throws InterruptedException
    {
        int questCreatorStationId = getIntObjVar(questHolocron, PCG_QUEST_CREATOR_STATIONID_OBJVAR);
        int playerStationId = getPlayerStationId(player);
        if (playerStationId == questCreatorStationId)
        {
            return true;
        }
        return false;
    }
    public static void sendPlacedInInventorySystemMessage(obj_id player, obj_id objectPlaced, int count) throws InterruptedException
    {
        if (isValidId(objectPlaced))
        {
            string_id name = getNameStringId(objectPlaced);
            prose_package pp = prose.getPackage(new string_id("saga_system", "placed_in_inventory_count"), player, player);
            prose.setTO(pp, name);
            prose.setDI(pp, count);
            sendSystemMessageProse(player, pp);
        }
    }
    public static void sendPlacedInInventorySystemMessage(obj_id player, obj_id objectPlaced) throws InterruptedException
    {
        if (isValidId(objectPlaced))
        {
            prose_package pp = null;
            string_id name = getNameStringId(objectPlaced);
            pp = prose.getPackage(new string_id("saga_system", "placed_in_inventory"), name);
            sendQuestSystemMessage(player, pp);
        }
    }
    public static void showQuestRatingUI(obj_id questHolocron, obj_id player) throws InterruptedException
    {
        String questName = getQuestName(questHolocron);
        openRatingWindow(player, "Chronicle Rating", "Please rate this quest: " + questName);
        utils.setScriptVar(player, "chronicles.rating_a_holocron", questHolocron);
        return;
    }
    public static float getCurrentPgcRating(int ratingTotal, int ratingCount) throws InterruptedException
    {
        return getCurrentPgcRating((float)ratingTotal, (float)ratingCount);
    }
    public static float getCurrentPgcRating(float ratingTotal, float ratingCount) throws InterruptedException
    {
        float tempNumber = (ratingTotal / ratingCount) * 10;
        int result = (int)Math.round(tempNumber);
        float finalValue = (float)result / 10;
        return finalValue;
    }
    public static void showChroniclerRatingTable(obj_id player, String name, obj_id[] chroniclers) throws InterruptedException
    {
        if (chroniclers != null && chroniclers.length > 0)
        {
            String[][] data = new String[chroniclers.length][4];
            for (int i = 0; i < chroniclers.length; i++)
            {
                obj_id chroniclerId = chroniclers[i];
                String chroniclerName = pgcGetChroniclerName(chroniclerId);
                if (chroniclerName == null || chroniclerName.length() <= 0)
                {
                    chroniclerName = name;
                }
                data[i][0] = chroniclerName;
                int[] chroniclerRating = pgcGetRating(chroniclerId);
                if (chroniclerRating != null && chroniclerRating.length > 0)
                {
                    int ratingTotal = chroniclerRating[PGC_INFO_INDEX_TOTAL_RATING_VALUE];
                    int ratingCount = chroniclerRating[PGC_INFO_INDEX_TOTAL_RATING_COUNT];
                    int lastRatedTime = chroniclerRating[PGC_INFO_INDEX_MOST_RECENT_RATING_TIME];
                    if (ratingTotal > 0)
                    {
                        data[i][1] = "" + pgc_quests.getCurrentPgcRating(ratingTotal, ratingCount);
                        data[i][2] = "" + ratingCount;
                        data[i][3] = utils.formatTimeVerbose(getCalendarTime() - lastRatedTime);
                    }
                    else 
                    {
                        data[i][1] = "" + 0.0;
                        data[i][2] = "" + 0;
                        data[i][3] = "Not Yet Rated";
                    }
                }
                else 
                {
                    data[i][1] = "" + 0.0;
                    data[i][2] = "" + 0;
                    data[i][3] = "Not Yet Rated";
                }
            }
            String title = name + " Chronicle Ratings";
            String[] colTitles = 
            {
                "Chronicler Name",
                "Rating",
                "Number of Times Rated",
                "Time Since Last Rating"
            };
            String[] colTypes = 
            {
                "text",
                "float",
                "integer",
                "text"
            };
            int id = sui.table(player, player, sui.OK_CANCEL, title, "tableHandler", null, colTitles, colTypes, data, true, false);
        }
        else 
        {
            sendSystemMessage(player, new string_id("saga_system", "pgc_rating_chronicler_not_found"));
        }
        return;
    }
    public static boolean isEligiblePgcReward(obj_id rewardItem) throws InterruptedException
    {
        if (!canTrade(rewardItem))
        {
            return false;
        }
        if (hasScript(rewardItem, "item.special.nomove"))
        {
            return false;
        }
        obj_id biolink = getBioLink(rewardItem);
        if (isIdValid(biolink) && biolink != utils.OBJ_ID_BIO_LINK_PENDING)
        {
            return false;
        }
        if (static_item.isStaticItem(rewardItem))
        {
            if (static_item.isUniqueStaticItem(rewardItem))
            {
                return false;
            }
        }
        if (utils.isContainer(rewardItem))
        {
            obj_id[] itemContents = getContents(rewardItem);
            if (itemContents != null && itemContents.length > 0)
            {
                return false;
            }
        }
        if (jedi.isCrystalTuned(rewardItem))
        {
            return false;
        }
        return true;
    }
    public static void setQuestAbandoned(obj_id questControlDevice, obj_id player) throws InterruptedException
    {
        String questName = "Unknown";
        String timeToCompleteQuest = "Unknown";
        obj_id questHolocron = getQuestHolocronFromQCD(questControlDevice);
        if (isIdValid(questHolocron))
        {
            questName = pgc_quests.getQuestName(questHolocron);
            int currentTime = getCalendarTime();
            timeToCompleteQuest = utils.formatTimeVerbose(currentTime - getIntObjVar(questHolocron, pgc_quests.PCG_QUEST_ACTIVATED_TIME));
            removeObjVar(questHolocron, pgc_quests.PCG_QUEST_ACTIVATED_TIME);
        }
        if (clearQuestHolocron(questControlDevice, player))
        {
            string_id message = new string_id("saga_system", "qcd_quest_abandoned");
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, questName);
            sendSystemMessageProse(player, pp);
            pgc_quests.logQuest(player, questHolocron, "Quest Abandoned after " + timeToCompleteQuest + " time active.");
            destroyObject(questControlDevice);
        }
        return;
    }
    public static void initializeQuestTasksStatus(obj_id questHolocron, int numTasks) throws InterruptedException
    {
        for (int q = 0; q < numTasks; q++)
        {
            int status = pgc_quests.PQ_TASK_INACTIVE;
            if (q == 0)
            {
                status = pgc_quests.PQ_TASK_ACTIVE;
            }
            setPlayerQuestTaskStatus(questHolocron, q, status);
        }
    }
    public static void resetQuestHolocron(obj_id questHolocron, obj_id player) throws InterruptedException
    {
        if (hasObjVar(questHolocron, PCG_QUEST_COMPLETE_OBJVAR))
        {
            return;
        }
        for (int i = 0; i < PGC_QUEST_MAX_NUM_TASKS; i++)
        {
            String phaseString = getPhaseObjVarString(i);
            if (hasObjVar(questHolocron, phaseString))
            {
                for (int j = 0; j < PGC_QUEST_MAX_NUM_TASKS_PER_PHASE; j++)
                {
                    String taskString = getTaskObjVarString(j);
                    String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
                    if (hasObjVar(questHolocron, baseObjVar))
                    {
                        int taskIndex = getIntObjVarTaskData(questHolocron, baseObjVar, "task_index");
                        if (hasObjVar(questHolocron, baseObjVar + ".task_complete"))
                        {
                            removeObjVar(questHolocron, baseObjVar + ".task_complete");
                        }
                        if (hasObjVar(questHolocron, baseObjVar + ".current_count"))
                        {
                            removeObjVar(questHolocron, baseObjVar + ".current_count");
                            setPlayerQuestTaskCounter(questHolocron, taskIndex, 0);
                        }
                        setPlayerQuestTaskStatus(questHolocron, taskIndex, pgc_quests.PQ_TASK_COMPLETE);
                        removePlayerQuestWaypoint(questHolocron, baseObjVar);
                    }
                    else 
                    {
                        break;
                    }
                }
            }
            else 
            {
                break;
            }
        }
        setObjVar(questHolocron, pgc_quests.PCG_QUEST_CUR_QUEST_PHASE_OBJVAR, 0);
        messageTo(questHolocron, "handleQuestHolocronInitializeTaskStatus", null, 1, false);
        return;
    }
    public static void addRelicToQuestBuilder(obj_id player, obj_id relic, boolean addEntireStack) throws InterruptedException
    {
        if (!utils.isNestedWithin(relic, player))
        {
            return;
        }
        String relicData = getStringObjVar(relic, pgc_quests.PGC_RELIC_SLOT_DATA_OBJVAR);
        String[] splitSlotNames = split(relicData, ':');
        String slotName = splitSlotNames[1];
        String collectionName = splitSlotNames[0];
        String staticItemName = getStaticItemName(relic);
        if (!canUseRelic(player, relic, slotName, collectionName, true))
        {
            sendSystemMessage(player, new string_id("saga_system", "relic_consume_fail_lower_level"));
            return;
        }
        if (!hasCompletedCollection(player, collectionName))
        {
            if (!hasCompletedCollectionSlot(player, slotName) && !slotName.equals(""))
            {
                int targetCount = 1;
                int actualCount = 0;
                long relicSlotValue = getCollectionSlotValue(player, slotName);
                int stackCount = getCount(relic);
                if (addEntireStack && stackCount > 0)
                {
                    targetCount = stackCount;
                }
                if (relicSlotValue <= 0)
                {
                    targetCount += 1;
                }
                for (int i = 1; i <= targetCount; i++)
                {
                    if (modifyCollectionSlotValue(player, slotName, 1))
                    {
                        if (relicSlotValue <= 0 && i == 1)
                        {
                        }
                        else 
                        {
                            decrementCount(relic);
                            actualCount += 1;
                        }
                        if (hasCompletedCollectionSlot(player, slotName))
                        {
                            sendSystemMessage(player, new string_id("saga_system", "relic_count_added_to_max"));
                            break;
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("saga_system", "relic_report_consume_item_fail"));
                        pgc_quests.logRelic(player, obj_id.NULL_ID, "Chronicle Relic " + staticItemName + "(slot: " + slotName + ") had bad data and was unable to be add to their relic collection (" + collectionName + ").");
                        return;
                    }
                }
                prose_package pp = prose.getPackage(new string_id("saga_system", "relic_consume_success"), relic, relic);
                prose.setTO(pp, new string_id("collection_n", slotName));
                prose.setDI(pp, actualCount);
                sendSystemMessageProse(player, pp);
                pgc_quests.logRelic(player, obj_id.NULL_ID, "Player added Chronicle Relic " + staticItemName + "(slot: " + slotName + " x " + actualCount + ") to their relic collection (" + collectionName + ").");
            }
            else 
            {
                sendSystemMessage(player, new string_id("saga_system", "relic_count_at_max"));
                pgc_quests.logRelic(player, obj_id.NULL_ID, "Player attempted to add " + staticItemName + "(slot: " + slotName + ") to their relic collection (" + collectionName + "), but that relic is already at max amount.");
            }
        }
        else 
        {
            sendSystemMessage(player, new string_id("saga_system", "relic_count_at_max"));
            pgc_quests.logRelic(player, obj_id.NULL_ID, "Player attempted to add " + staticItemName + "(slot: " + slotName + ") to their relic collection, but they have already maxed out every single available relic of this type, " + collectionName + ".");
        }
        return;
    }
    public static boolean canUseRelic(obj_id player, obj_id relic, String slotName, String collectionName, boolean isVerbose) throws InterruptedException
    {
        int relicQualitySkillmod = getEnhancedSkillStatisticModifierUncapped(player, pgc_quests.PGC_SKILLMOD_RELIC_QUALITY);
        String staticItemName = getStaticItemName(relic);
        dictionary row = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, staticItemName);
        int relic_tier = row.getInt("relic_tier");
        if (relic_tier > relicQualitySkillmod)
        {
            if (isVerbose)
            {
                if (!isGod(player))
                {
                    pgc_quests.logRelic(player, obj_id.NULL_ID, "Player attempted to add " + staticItemName + "(slot: " + slotName + ") to their relic collection (" + collectionName + "), but they were not high enough level (player tier = " + relicQualitySkillmod + "; relic tier = " + relic_tier + ").");
                    return false;
                }
                else 
                {
                    sendSystemMessage(player, "[GOD_MODE] Bypassing relic quality check, which you failed...", "");
                }
            }
        }
        return true;
    }
    public static String getStringPgcTaskData(obj_id questHolocron, String baseObjVar, String category, String[] relicData) throws InterruptedException
    {
        String taskData = getStringObjVarTaskData(questHolocron, baseObjVar, category);
        if (taskData.length() < 1)
        {
            taskData = getStringRelicData(category, relicData);
        }
        return taskData;
    }
    public static String getStringObjVarTaskData(obj_id questHolocron, String baseObjVar, String category) throws InterruptedException
    {
        String taskData = "";
        String dataObjVar = baseObjVar + "." + category;
        if (hasObjVar(questHolocron, dataObjVar))
        {
            taskData = getStringObjVar(questHolocron, dataObjVar);
        }
        return taskData;
    }
    public static String getStringRelicData(String category, String[] relicData) throws InterruptedException
    {
        return parseRelicData(category, relicData);
    }
    public static int getIntPgcTaskData(obj_id questHolocron, String baseObjVar, String category, String[] relicData) throws InterruptedException
    {
        int taskData = getIntObjVarTaskData(questHolocron, baseObjVar, category);
        if (taskData < 0)
        {
            taskData = getIntRelicData(category, relicData);
        }
        return taskData;
    }
    public static int getIntObjVarTaskData(obj_id questHolocron, String baseObjVar, String category) throws InterruptedException
    {
        int taskData = -1;
        String dataObjVar = baseObjVar + "." + category;
        if (hasObjVar(questHolocron, dataObjVar))
        {
            taskData = getIntObjVar(questHolocron, dataObjVar);
        }
        return taskData;
    }
    public static int getIntRelicData(String category, String[] relicData) throws InterruptedException
    {
        return utils.stringToInt(parseRelicData(category, relicData));
    }
    public static dictionary getWaypointObjVarTaskData(obj_id questHolocron, String baseObjVar, String category) throws InterruptedException
    {
        String waypointLocObjVar = baseObjVar + "." + category;
        if (hasObjVar(questHolocron, waypointLocObjVar))
        {
            dictionary waypointData = new dictionary();
            location waypointLoc = getLocationObjVar(questHolocron, waypointLocObjVar);
            String waypointName = "Unknown";
            String waypointNameObjVar = baseObjVar + "." + "waypointName";
            if (hasObjVar(questHolocron, waypointNameObjVar))
            {
                waypointName = getStringObjVar(questHolocron, waypointNameObjVar);
            }
            if (waypointLoc != null)
            {
                waypointData.put("waypointLoc", waypointLoc);
                waypointData.put("waypointName", waypointName);
                return waypointData;
            }
        }
        return null;
    }
    public static dictionary getWaypointRelicData(String category, String[] relicData) throws InterruptedException
    {
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(category))
                {
                    String[] parse = split(relicCategory, ':');
                    String baseData = parse[1];
                    if (baseData != null && baseData.length() > 0)
                    {
                        String[] waypointParse = split(baseData, ',');
                        if (waypointParse.length == 6)
                        {
                            String planet = waypointParse[0];
                            float x = utils.stringToFloat(waypointParse[1]);
                            float y = utils.stringToFloat(waypointParse[2]);
                            float z = utils.stringToFloat(waypointParse[3]);
                            String cellName = waypointParse[4];
                            String waypointName = waypointParse[5];
                            dictionary waypointData = new dictionary();
                            location waypointLoc = new location(x, y, z, planet);
                            waypointData.put("waypointLoc", waypointLoc);
                            waypointData.put("waypointName", waypointName);
                            return waypointData;
                        }
                    }
                }
            }
        }
        return null;
    }
    public static obj_id getObjIdRelicData(String category, String[] relicData) throws InterruptedException
    {
        return utils.stringToObjId(parseRelicData(category, relicData));
    }
    public static string_id getStringIdRelicData(String category, String[] relicData) throws InterruptedException
    {
        String stringData = parseRelicData(category, relicData);
        String[] parse = split(stringData, ',');
        return new string_id(parse[0], parse[1]);
    }
    public static String parseRelicData(String category, String[] relicData) throws InterruptedException
    {
        String data = "";
        if (relicData != null && relicData.length > 0)
        {
            for (int i = 0; i < relicData.length; i++)
            {
                String relicCategory = relicData[i];
                if (relicCategory.startsWith(category))
                {
                    String[] parse = split(relicCategory, ':');
                    data = parse[1];
                    if (parse.length > 2)
                    {
                        data += "," + parse[2];
                    }
                    break;
                }
            }
        }
        return data;
    }
    public static void checkForKillTaskCredit(obj_id questHolocron, String taskType, String phaseString, String taskString, dictionary params) throws InterruptedException
    {
        obj_id player = getQuestPlayer(questHolocron);
        if (!isIdValid(player))
        {
            return;
        }
        String killedCreatureType = params.getString("creatureName");
        String killedCreatureSocialGroup = params.getString("socialGroup");
        location creatureLoc = params.getLocation("location");
        float distance = getDistance(player, creatureLoc);
        if (distance == -1.0f || distance > xp.MAX_DISTANCE)
        {
            return;
        }
        String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
        String relicName = getRelicName(questHolocron, phaseString, taskString);
        String[] relicData = getCollectionSlotCategoryInfo(relicName);
        String targetCreatureNamesList = getStringPgcTaskData(questHolocron, baseObjVar, "creature_name", relicData);
        String targetSocialGroupList = getStringPgcTaskData(questHolocron, baseObjVar, "social_group", relicData);
        int targetCount = getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
        boolean creatureAcceptable = false;
        if (targetCreatureNamesList != null && killedCreatureType != null)
        {
            String[] targetCreatureNamesArray = split(targetCreatureNamesList, ',');
            if (targetCreatureNamesArray != null && targetCreatureNamesArray.length > 0)
            {
                for (int i = 0; i < targetCreatureNamesArray.length; i++)
                {
                    String targetCreatureName = targetCreatureNamesArray[i];
                    if (killedCreatureType.equals(targetCreatureName))
                    {
                        creatureAcceptable = true;
                    }
                }
            }
        }
        if (targetSocialGroupList != null && killedCreatureSocialGroup != null)
        {
            String[] socialGroupsNamesArray = split(targetSocialGroupList, ',');
            if (socialGroupsNamesArray != null && socialGroupsNamesArray.length > 0)
            {
                for (int i = 0; i < socialGroupsNamesArray.length; i++)
                {
                    String targetSocialGroup = socialGroupsNamesArray[i];
                    if (killedCreatureSocialGroup.equals(targetSocialGroup))
                    {
                        creatureAcceptable = true;
                    }
                }
            }
        }
        if (creatureAcceptable)
        {
            if (taskType.equals(SAGA_DESTROY_MULTIPLE))
            {
                handleDestroyMultipleKill(questHolocron, phaseString, taskString, targetCount);
            }
            else if (taskType.equals(SAGA_DESTROY_MULTIPLE_LOOT))
            {
                handleDestroyMultipleLootKill(questHolocron, phaseString, taskString, targetCount, relicData);
            }
        }
        return;
    }
    public static void handleDestroyMultipleKill(obj_id questHolocron, String phaseString, String taskString, int targetCount) throws InterruptedException
    {
        String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
        int currentCount = incrementTaskCounter(questHolocron, baseObjVar);
        obj_id player = getQuestPlayer(questHolocron);
        if (isIdValid(player))
        {
            sendSystemMessage(player, getQuestName(questHolocron) + ": Target killed! " + currentCount + "/" + targetCount, "");
            play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
        }
        if (currentCount >= targetCount)
        {
            setTaskComplete(questHolocron, phaseString, taskString);
        }
        return;
    }
    public static void handleDestroyMultipleLootKill(obj_id questHolocron, String phaseString, String taskString, int targetCount, String[] relicData) throws InterruptedException
    {
        obj_id player = getQuestPlayer(questHolocron);
        if (isIdValid(player))
        {
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            int dropRate = getIntPgcTaskData(questHolocron, baseObjVar, "drop_rate", relicData);
            int roll = rand(1, 100);
            if (roll <= dropRate)
            {
                String message_string = getStringObjVarTaskData(questHolocron, baseObjVar, "message");
                if (message_string != null && message_string.length() > 0)
                {
                    sendSystemMessage(player, message_string, "");
                }
                else 
                {
                    string_id message_sid = getStringIdRelicData("message", relicData);
                    sendSystemMessage(player, message_sid);
                }
                int currentCount = incrementTaskCounter(questHolocron, baseObjVar);
                play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
                if (currentCount >= targetCount)
                {
                    setTaskComplete(questHolocron, phaseString, taskString);
                }
            }
            else 
            {
                sendSystemMessage(player, getQuestName(questHolocron) + ": Nothing Found.", "");
            }
        }
        return;
    }
    public static void handlePvpPlayerKillCredit(obj_id questHolocron, String taskType, String phaseString, String taskString, dictionary params) throws InterruptedException
    {
        obj_id player = getQuestPlayer(questHolocron);
        if (!isIdValid(player))
        {
            return;
        }
        int killedPlayerRank = params.getInt("victimRank");
        String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
        String relicName = getRelicName(questHolocron, phaseString, taskString);
        String[] relicData = getCollectionSlotCategoryInfo(relicName);
        int targetRank = getIntPgcTaskData(questHolocron, baseObjVar, "rank_value", relicData);
        if (killedPlayerRank >= targetRank)
        {
            int targetCount = getIntPgcTaskData(questHolocron, baseObjVar, "count", relicData);
            int currentCount = incrementTaskCounter(questHolocron, baseObjVar);
            sendSystemMessage(player, getQuestName(questHolocron) + ": PvP Target defeated! " + currentCount + "/" + targetCount, "");
            play2dNonLoopingSound(player, groundquests.MUSIC_QUEST_INCREMENT_COUNTER);
            if (currentCount >= targetCount)
            {
                setTaskComplete(questHolocron, phaseString, taskString);
            }
        }
        return;
    }
    public static void handleCommMessageTaskActivated(obj_id questHolocron, String phaseString, String taskString) throws InterruptedException
    {
        obj_id player = getQuestPlayer(questHolocron);
        if (isIdValid(player))
        {
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            String relicName = getRelicName(questHolocron, phaseString, taskString);
            String[] relicData = getCollectionSlotCategoryInfo(relicName);
            String message_string = getStringObjVarTaskData(questHolocron, baseObjVar, "message");
            prose_package pp = new prose_package();
            if (message_string != null && message_string.length() > 0)
            {
                string_id message_base = new string_id("saga_system", "holocron_string_message");
                pp = prose.getPackage(message_base, player, player);
                prose.setTO(pp, message_string);
            }
            else 
            {
                pp.stringId = getStringIdRelicData("message", relicData);
            }
            String voiceOver = getStringRelicData("voice_over", relicData);
            if (voiceOver != null && voiceOver.length() > 0)
            {
                play2dNonLoopingSound(player, voiceOver);
            }
            String appearance = getStringPgcTaskData(questHolocron, baseObjVar, "appearance", relicData);
            if (appearance.equals("message_box"))
            {
                showChroniclesMessageBox(player, questHolocron, pp, phaseString, taskString);
            }
            else 
            {
                commPlayerQuest(questHolocron, player, pp, appearance);
                dictionary webster = new dictionary();
                webster.put("phaseString", phaseString);
                webster.put("taskString", taskString);
                messageTo(questHolocron, "handleCommMessageTaskCompletion", webster, COMM_TASK_COMPLETION_DELAY, false);
            }
        }
        return;
    }
    public static void showChroniclesMessageBox(obj_id player, obj_id questHolocron, prose_package pp, String phaseString, String taskString) throws InterruptedException
    {
        String messageBoxTitle = getTaskName(questHolocron, phaseString, taskString);
        String messageBoxText = "";
        prose.setTT(pp, getName(player));
        messageBoxText = "\0" + packOutOfBandProsePackage(null, pp);
        int messageBoxSizeWidth = 384;
        int messageBoxSizeHeight = 256;
        int messageBoxLocationX = 320;
        int messageBoxLocationY = 256;
        int pageId = sui.createSUIPage(sui.SUI_MSGBOX, questHolocron, player, "ChroniclesMessageBoxCompleted");
        sui.setAutosaveProperty(pageId, false);
        sui.setSizeProperty(pageId, messageBoxSizeWidth, messageBoxSizeHeight);
        sui.setLocationProperty(pageId, messageBoxLocationX, messageBoxLocationY);
        setSUIProperty(pageId, sui.MSGBOX_TITLE, sui.PROP_TEXT, messageBoxTitle);
        setSUIProperty(pageId, sui.MSGBOX_PROMPT, sui.PROP_TEXT, messageBoxText);
        sui.msgboxButtonSetup(pageId, sui.OK_ONLY);
        sui.showSUIPage(pageId);
        utils.setScriptVar(questHolocron, getPhaseStringVarName(pageId, "message_box"), phaseString);
        utils.setScriptVar(questHolocron, getTaskStringVarName(pageId, "message_box"), taskString);
        return;
    }
    public static String getPhaseStringVarName(int pageId, String taskType) throws InterruptedException
    {
        return taskType + ".phaseString." + pageId;
    }
    public static String getTaskStringVarName(int pageId, String taskType) throws InterruptedException
    {
        return taskType + ".taskString." + pageId;
    }
    public static void handleGoToLocationTaskActivated(obj_id questHolocron, String phaseString, String taskString, location targetLoc) throws InterruptedException
    {
        obj_id player = getQuestPlayer(questHolocron);
        if (isIdValid(player))
        {
            String baseObjVar = getPgcBaseObjVar(phaseString, taskString);
            String relicName = getRelicName(questHolocron, phaseString, taskString);
            String[] relicData = getCollectionSlotCategoryInfo(relicName);
            int radius = getIntPgcTaskData(questHolocron, baseObjVar, "radius", relicData);
            dictionary webster = new dictionary();
            webster.put("targetLoc", targetLoc);
            webster.put("radius", radius);
            webster.put("locationTargetName", relicName);
            messageTo(player, "playerQuestSetLocationTarget", webster, 1, false);
        }
        return;
    }
    public static void log(obj_id player, obj_id questHolocron, String category, String text) throws InterruptedException
    {
        if (category.length() > 0 && text.length() > 0)
        {
            String playerInfo = "";
            if (isIdValid(player))
            {
                playerInfo = "player=(" + player + "," + getName(player) + "," + getPlayerStationId(player) + ") ";
            }
            String questInfo = "";
            if (isIdValid(questHolocron))
            {
                obj_id chroniclerId = getObjIdObjVar(questHolocron, PCG_QUEST_CREATOR_ID_OBJVAR);
                String chroniclerName = getStringObjVar(questHolocron, PCG_QUEST_CREATOR_NAME_OBJVAR);
                String questName = getPlayerQuestTitle(questHolocron);
                String holocronType = "Chronicles Holocron";
                if (isPlayerQuestRecipe(questHolocron))
                {
                    holocronType = "Chronicles Draft";
                }
                if (isSharedPlayerQuestObject(questHolocron))
                {
                    holocronType = "Shared Chronicles Holocron";
                }
                questInfo = holocronType + " quest=(" + questHolocron + "," + chroniclerId + "," + chroniclerName + "," + questName + ") ";
            }
            CustomerServiceLog(category, playerInfo + questInfo + text);
        }
    }
    public static void logProgression(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        log(player, questHolocron, "chronicles_progression", text);
    }
    public static void logReward(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        log(player, questHolocron, "chronicles_reward", text);
    }
    public static void logQuest(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        log(player, questHolocron, "chronicles_quest", text);
    }
    public static void logRelic(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        log(player, questHolocron, "chronicles_relic", text);
    }
    public static void logRating(obj_id player, obj_id questHolocron, String text) throws InterruptedException
    {
        log(player, questHolocron, "chronicles_rating", text);
    }
}
