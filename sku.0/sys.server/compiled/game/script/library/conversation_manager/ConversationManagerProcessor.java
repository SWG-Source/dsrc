package script.library.conversation_manager;

import script.obj_id;
import script.library.groundquests;
import script.library.utils;
import script.library.conversation_manager.ConversationManagerData.Condition;
import script.library.conversation_manager.ConversationManagerData.ConditionGroup;
import script.library.conversation_manager.ConversationManagerData.ConditionSet;
import script.library.conversation_manager.ConversationManagerData.ParameterArray;
import script.library.conversation_manager.ConversationManagerData.ParameterObject;
import script.library.conversation_manager.ConversationManagerData.ResponseAction;
import script.library.conversation_manager.ConversationManagerData.ResponseState;

public class ConversationManagerProcessor
{
    public static final String LOGICAL_AND = "and";
    public static final String LOGICAL_ALL = "all";
    public static final String LOGICAL_OR = "or";
    public static final String LOGICAL_ANY = "any";
    public static final String CONDITION_DEFAULT = Condition.TYPE_DEFAULT;

    public ConversationManagerProcessor()
    {
    }

    public static ResponseState getFirstPassingResponseState(String directory, String baseName, obj_id player, obj_id npc) throws InterruptedException
    {
        String conditionTable = ConditionTable.getTableName(directory, baseName);
        String conditionGroupTable = ConditionGroupTable.getTableName(directory, baseName);
        String conditionSetTable = ConditionSetTable.getTableName(directory, baseName);

        ResponseState[] states = ResponseStateTable.getResponseStates(directory, baseName);
        for (int i = 0; i < states.length; i++)
        {
            if (responseStatePasses(states[i], conditionTable, conditionGroupTable, conditionSetTable, player, npc))
            {
                return states[i];
            }
        }
        return null;
    }

    public static ResponseAction[] getResponseActions(String directory, String baseName, ResponseState state) throws InterruptedException
    {
        if (state == null)
        {
            return new ResponseAction[0];
        }
        return ResponseActionTable.getResponseActionsByStateId(directory, baseName, state.id);
    }

    public static int executeResponseActions(String directory, String baseName, ResponseState state, obj_id player, obj_id npc) throws InterruptedException
    {
        ResponseAction[] actions = getResponseActions(directory, baseName, state);
        for (int i = 0; i < actions.length; i++)
        {
            executeResponseAction(actions[i], player, npc);
        }
        return actions.length;
    }

    public static boolean responseStatePasses(ResponseState state, String conditionTable, String conditionGroupTable, String conditionSetTable, obj_id player, obj_id npc) throws InterruptedException
    {
        if (state == null)
        {
            return false;
        }
        ConditionSet[] conditionSets = ConditionSetTable.getConditionSetsByIdFromTable(conditionSetTable, state.conditionSetId);
        return conditionSetsPass(conditionSets, conditionTable, conditionGroupTable, player, npc);
    }

    private static boolean conditionSetsPass(ConditionSet[] conditionSets, String conditionTable, String conditionGroupTable, obj_id player, obj_id npc) throws InterruptedException
    {
        if (conditionSets == null || conditionSets.length < 1)
        {
            return false;
        }
        for (int i = 0; i < conditionSets.length; i++)
        {
            if (!conditionSetPasses(conditionSets[i], conditionTable, conditionGroupTable, player, npc))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean conditionSetPasses(ConditionSet conditionSet, String conditionTable, String conditionGroupTable, obj_id player, obj_id npc) throws InterruptedException
    {
        if (conditionSet == null)
        {
            return false;
        }
        ConditionGroup[] conditionGroups = ConditionGroupTable.getConditionGroupsByIdFromTable(conditionGroupTable, conditionSet.groupId);
        return conditionGroupPasses(conditionGroups, conditionTable, player, npc);
    }

    private static boolean conditionGroupPasses(ConditionGroup[] conditionGroups, String conditionTable, obj_id player, obj_id npc) throws InterruptedException
    {
        if (conditionGroups == null || conditionGroups.length < 1)
        {
            return false;
        }

        String operation = normalizeLogicalOperation(conditionGroups[0].logicalOperation);
        if (LOGICAL_ALL.equals(operation) || LOGICAL_AND.equals(operation))
        {
            for (int i = 0; i < conditionGroups.length; i++)
            {
                if (!conditionLinkPasses(conditionGroups[i], conditionTable, player, npc))
                {
                    return false;
                }
            }
            return true;
        }

        for (int i = 0; i < conditionGroups.length; i++)
        {
            if (conditionLinkPasses(conditionGroups[i], conditionTable, player, npc))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean conditionLinkPasses(ConditionGroup conditionGroup, String conditionTable, obj_id player, obj_id npc) throws InterruptedException
    {
        if (conditionGroup == null)
        {
            return false;
        }
        Condition condition = ConditionTable.getConditionFromTable(conditionTable, conditionGroup.conditionId);
        return conditionPasses(condition, player, npc);
    }

    private static boolean conditionPasses(Condition condition, obj_id player, obj_id npc) throws InterruptedException
    {
        if (condition == null || condition.type == null)
        {
            return false;
        }
        if (Condition.TYPE_IS_QUEST_ACTIVE.equals(condition.type))
        {
            return isQuestActive(player, npc, condition.parameters);
        }
        if (Condition.TYPE_IS_QUEST_COMPLETED.equals(condition.type))
        {
            return isQuestCompleted(player, npc, condition.parameters);
        }
        if (Condition.TYPE_IS_ANY_QUEST_ACTIVE.equals(condition.type))
        {
            return isAnyQuestActive(player, npc, condition.parameters);
        }
        if (Condition.TYPE_PLAYER_HAS_OBJVAR.equals(condition.type))
        {
            return playerHasObjVar(player, npc, condition.parameters);
        }
        if (Condition.TYPE_PLAYER_HAS_SCRIPT_VAR.equals(condition.type))
        {
            return playerHasScriptVar(player, npc, condition.parameters);
        }
        if (Condition.TYPE_NPC_HAS_OBJVAR.equals(condition.type))
        {
            return npcHasObjVar(player, npc, condition.parameters);
        }
        if (Condition.TYPE_NPC_HAS_SCRIPT_VAR.equals(condition.type))
        {
            return npcHasScriptVar(player, npc, condition.parameters);
        }
        if (Condition.TYPE_DEFAULT.equals(condition.type))
        {
            return defaultCondition(player, npc, condition.parameters);
        }

        return false;
    }

    private static boolean isQuestActive(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String questName = params.getString("quest_name");
        if (questName == null || questName.length() == 0)
        {
            return false;
        }
        if (groundquests.hasCompletedQuest(player, questName))
        {
            return false;
        }
        String step = params.getString("step");
        if (step != null && step.length() > 0)
        {
            return groundquests.isTaskActive(player, questName, step);
        }
        return groundquests.isQuestActive(player, questName);
    }

    private static boolean isQuestCompleted(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String questName = params.getString("quest_name");
        if (questName == null || questName.length() == 0)
        {
            return false;
        }
        return groundquests.hasCompletedQuest(player, questName);
    }

    private static boolean isAnyQuestActive(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        ParameterArray quests = params.getArray("quests");
        if (quests == null || quests.size() < 1)
        {
            return false;
        }
        for (int i = 0; i < quests.size(); i++)
        {
            String questName = quests.getString(i);
            if (questName != null && questName.length() > 0 && !groundquests.hasCompletedQuest(player, questName) && groundquests.isQuestActive(player, questName))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean playerHasObjVar(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        return hasObjVarCondition(player, parameters);
    }

    private static boolean playerHasScriptVar(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        return hasScriptVarCondition(player, parameters);
    }

    private static boolean npcHasObjVar(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        return hasObjVarCondition(npc, parameters);
    }

    private static boolean npcHasScriptVar(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        return hasScriptVarCondition(npc, parameters);
    }

    private static boolean defaultCondition(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        return true;
    }

    private static boolean hasObjVarCondition(obj_id target, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (target == null || name == null || name.length() == 0 || !utils.hasObjVar(target, name))
        {
            return false;
        }
        String value = params.getString("value");
        if (value == null)
        {
            return true;
        }
        String actualValue = utils.getStringObjVar(target, name);
        return value.equals(actualValue);
    }

    private static boolean hasScriptVarCondition(obj_id target, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (target == null || name == null || name.length() == 0 || !utils.hasScriptVar(target, name))
        {
            return false;
        }
        String value = params.getString("value");
        if (value == null)
        {
            return true;
        }
        String actualValue = utils.getStringScriptVar(target, name);
        return value.equals(actualValue);
    }

    private static void executeResponseAction(ResponseAction action, obj_id player, obj_id npc) throws InterruptedException
    {
        // Stub until response action processors are wired in.
    }

    private static String normalizeLogicalOperation(String operation)
    {
        if (operation == null || operation.length() == 0)
        {
            return LOGICAL_ANY;
        }
        return operation.toLowerCase();
    }
}
