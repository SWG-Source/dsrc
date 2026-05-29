package script.library.conversation_manager;

import script.base_class;
import script.dictionary;
import script.obj_id;
import script.string_id;
import script.library.chat;
import script.library.groundquests;
import script.library.static_item;
import script.library.utils;
import script.library.conversation_manager.ConversationManagerData.Condition;
import script.library.conversation_manager.ConversationManagerData.ConditionGroup;
import script.library.conversation_manager.ConversationManagerData.ConditionSet;
import script.library.conversation_manager.ConversationManagerData.ParameterArray;
import script.library.conversation_manager.ConversationManagerData.ParameterObject;
import script.library.conversation_manager.ConversationManagerData.ParameterValue;
import script.library.conversation_manager.ConversationManagerData.ResponseAction;
import script.library.conversation_manager.ConversationManagerData.ResponseState;

import java.util.Enumeration;
import java.util.Vector;

public class ConversationManagerProcessor
{
    public static final String LOGICAL_AND = "and";
    public static final String LOGICAL_ALL = "all";
    public static final String LOGICAL_OR = "or";
    public static final String LOGICAL_ANY = "any";
    public static final String CONDITION_DEFAULT = Condition.TYPE_DEFAULT;
    public static final String REWARD_TYPE_OBJECT = "object";
    public static final String REWARD_TYPE_STATIC_ITEM = "static_item";
    public static final String REWARD_TYPE_MESSAGE = "message";
    public static final String MESSAGE_TARGET_PLAYER = "player";
    public static final String MESSAGE_TARGET_NPC = "npc";

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
        return executeResponseActions(directory, baseName, state, player, npc, null, null, null);
    }

    public static int executeResponseActions(String directory, String baseName, ResponseState state, obj_id player, obj_id npc, String branchScriptVar, String conversationName, String stringFile) throws InterruptedException
    {
        ResponseAction[] actions = getResponseActions(directory, baseName, state);
        for (int i = 0; i < actions.length; i++)
        {
            executeResponseAction(actions[i], player, npc, branchScriptVar, conversationName, stringFile);
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
            return isQuestActive(player, condition.parameters);
        }
        if (Condition.TYPE_IS_QUEST_COMPLETED.equals(condition.type))
        {
            return isQuestCompleted(player, condition.parameters);
        }
        if (Condition.TYPE_IS_ANY_QUEST_ACTIVE.equals(condition.type))
        {
            return isAnyQuestActive(player, condition.parameters);
        }
        if (Condition.TYPE_PLAYER_HAS_OBJVAR.equals(condition.type))
        {
            return playerHasObjVar(player, condition.parameters);
        }
        if (Condition.TYPE_PLAYER_HAS_SCRIPT_VAR.equals(condition.type))
        {
            return playerHasScriptVar(player, condition.parameters);
        }
        if (Condition.TYPE_NPC_HAS_OBJVAR.equals(condition.type))
        {
            return npcHasObjVar(npc, condition.parameters);
        }
        if (Condition.TYPE_NPC_HAS_SCRIPT_VAR.equals(condition.type))
        {
            return npcHasScriptVar(npc, condition.parameters);
        }
        if (Condition.TYPE_DEFAULT.equals(condition.type))
        {
            return defaultCondition();
        }

        return false;
    }

    private static boolean isQuestActive(obj_id player, String parameters) throws InterruptedException
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

    private static boolean isQuestCompleted(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String questName = params.getString("quest_name");
        if (questName == null || questName.length() == 0)
        {
            return false;
        }
        return groundquests.hasCompletedQuest(player, questName);
    }

    private static boolean isAnyQuestActive(obj_id player, String parameters) throws InterruptedException
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

    private static boolean playerHasObjVar(obj_id player, String parameters) throws InterruptedException
    {
        return hasObjVarCondition(player, parameters);
    }

    private static boolean playerHasScriptVar(obj_id player, String parameters) throws InterruptedException
    {
        return hasScriptVarCondition(player, parameters);
    }

    private static boolean npcHasObjVar(obj_id npc, String parameters) throws InterruptedException
    {
        return hasObjVarCondition(npc, parameters);
    }

    private static boolean npcHasScriptVar(obj_id npc, String parameters) throws InterruptedException
    {
        return hasScriptVarCondition(npc, parameters);
    }

    private static boolean defaultCondition()
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

    private static void executeResponseAction(ResponseAction action, obj_id player, obj_id npc, String branchScriptVar, String conversationName, String stringFile) throws InterruptedException
    {
        if (action == null || action.action == null)
        {
            return;
        }
        if (ResponseAction.ACTION_SIGNAL.equals(action.action))
        {
            signal(player, action.parameters);
        }
        else if (ResponseAction.ACTION_CONVERSATION.equals(action.action))
        {
            conversation(player, npc, action.parameters, branchScriptVar, conversationName, stringFile);
        }
        else if (ResponseAction.ACTION_SPEAK.equals(action.action))
        {
            speak(player, npc, action.parameters, branchScriptVar, stringFile);
        }
        else if (ResponseAction.ACTION_ANIMATION.equals(action.action))
        {
            animation(npc, action.parameters);
        }
        else if (ResponseAction.ACTION_COMPLETE_QUEST.equals(action.action))
        {
            completeQuest(player, action.parameters);
        }
        else if (ResponseAction.ACTION_GRANT_QUEST.equals(action.action))
        {
            grantQuest(player, action.parameters);
        }
        else if (ResponseAction.ACTION_GRANT_REWARD.equals(action.action))
        {
            grantReward(player, npc, action.parameters);
        }
        else if (ResponseAction.ACTION_SET_OBJECT_VAR.equals(action.action))
        {
            setObjectVar(player, action.parameters);
        }
        else if (ResponseAction.ACTION_REMOVE_OBJECT_VAR.equals(action.action))
        {
            removeObjectVar(player, action.parameters);
        }
        else if (ResponseAction.ACTION_SET_SCRIPT_VAR.equals(action.action))
        {
            setScriptVar(player, action.parameters);
        }
        else if (ResponseAction.ACTION_REMOVE_SCRIPT_VAR.equals(action.action))
        {
            removeScriptVar(player, action.parameters);
        }
    }

    private static void signal(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String signalName = params.getString("signal_name");
        if (signalName == null || signalName.length() == 0)
        {
            return;
        }
        groundquests.sendSignal(player, signalName);
    }

    private static void conversation(obj_id player, obj_id npc, String parameters, String branchScriptVar, String conversationName, String stringFile) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        boolean isActiveConversation = branchScriptVar != null && branchScriptVar.length() > 0 && utils.hasScriptVar(player, branchScriptVar);
        if (branchScriptVar != null && branchScriptVar.length() > 0)
        {
            utils.setScriptVar(player, branchScriptVar, params.getInt("branch"));
        }
        if (conversationName == null || conversationName.length() == 0 || stringFile == null || stringFile.length() == 0)
        {
            return;
        }
        String npcReply = params.getString("npc_reply");
        if (npcReply == null || npcReply.length() == 0)
        {
            return;
        }
        ParameterArray playerResponses = params.getArray("player_responses");
        if (playerResponses == null || playerResponses.size() < 1)
        {
            base_class.npcStartConversation(player, npc, conversationName, new string_id(stringFile, npcReply), new string_id[0]);
            return;
        }
        string_id[] responses = new string_id[playerResponses.size()];
        for (int i = 0; i < playerResponses.size(); i++)
        {
            responses[i] = new string_id(stringFile, playerResponses.getString(i));
        }
        if (isActiveConversation)
        {
            base_class.npcSpeak(player, new string_id(stringFile, npcReply));
            base_class.npcSetConversationResponses(player, responses);
            return;
        }
        base_class.npcStartConversation(player, npc, conversationName, new string_id(stringFile, npcReply), responses);
    }

    private static void speak(obj_id player, obj_id npc, String parameters, String branchScriptVar, String stringFile) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String npcReply = params.getString("npc_reply");
        if (npcReply == null || npcReply.length() == 0)
        {
            return;
        }
        if (branchScriptVar != null && branchScriptVar.length() > 0 && utils.hasScriptVar(player, branchScriptVar))
        {
            utils.removeScriptVar(player, branchScriptVar);
            if (stringFile != null && stringFile.length() > 0)
            {
                base_class.npcEndConversationWithMessage(player, new string_id(stringFile, npcReply));
            }
            return;
        }
        if (stringFile != null && stringFile.length() > 0)
        {
            chat.chat(npc, player, new string_id(stringFile, npcReply));
        }
    }

    private static void animation(obj_id npc, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String animationName = params.getString("animation_name");
        if (animationName == null || animationName.length() == 0)
        {
            return;
        }
        base_class.doAnimationAction(npc, animationName);
    }

    private static void completeQuest(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String questName = params.getString("quest_name");
        if (questName == null || questName.length() == 0)
        {
            return;
        }
        groundquests.completeQuest(player, questName);
    }

    private static void grantQuest(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String questName = params.getString("quest_name");
        if (questName == null || questName.length() == 0)
        {
            return;
        }
        groundquests.grantQuest(player, questName);
    }

    private static void grantReward(obj_id player, obj_id npc, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String type = params.getString("type");
        if (type == null || type.length() == 0)
        {
            return;
        }
        if (REWARD_TYPE_OBJECT.equals(type))
        {
            grantObjectReward(player, params);
        }
        else if (REWARD_TYPE_STATIC_ITEM.equals(type))
        {
            grantStaticItemReward(player, params);
        }
        else if (REWARD_TYPE_MESSAGE.equals(type))
        {
            grantMessageReward(player, npc, params);
        }
    }

    private static void grantObjectReward(obj_id player, ParameterObject params) throws InterruptedException
    {
        String template = params.getString("template");
        if (template == null || template.length() == 0)
        {
            return;
        }
        int count = getCount(params);
        for (int i = 0; i < count; i++)
        {
            base_class.createObjectInInventoryAllowOverload(template, player);
        }
    }

    private static void grantStaticItemReward(obj_id player, ParameterObject params) throws InterruptedException
    {
        String itemName = params.getString("item_name");
        if (itemName == null || itemName.length() == 0)
        {
            return;
        }
        int count = getCount(params);
        int charges = params.getInt("charges");
        for (int i = 0; i < count; i++)
        {
            if (charges > 0)
            {
                static_item.createNewItemFunction(itemName, player, charges);
            }
            else
            {
                static_item.createNewItemFunction(itemName, player);
            }
        }
    }

    private static void grantMessageReward(obj_id player, obj_id npc, ParameterObject params) throws InterruptedException
    {
        String message = params.getString("message");
        if (message == null || message.length() == 0)
        {
            return;
        }
        obj_id target = getMessageTarget(player, npc, params.getString("target"));
        if (target == null || !base_class.isIdValid(target))
        {
            return;
        }
        String script = params.getString("script");
        if (script != null && script.length() > 0 && !base_class.hasScript(target, script))
        {
            return;
        }

        dictionary messageParams = new dictionary();
        ParameterObject data = params.getObject("data");
        if (data != null)
        {
            copyParameterObjectToDictionary(data, messageParams);
        }
        messageParams.put("player", player);
        messageParams.put("npc", npc);
        base_class.messageTo(target, message, messageParams, params.getInt("delay"), false);
    }

    private static obj_id getMessageTarget(obj_id player, obj_id npc, String target)
    {
        if (MESSAGE_TARGET_PLAYER.equals(target))
        {
            return player;
        }
        if (MESSAGE_TARGET_NPC.equals(target))
        {
            return npc;
        }
        return null;
    }

    private static int getCount(ParameterObject params)
    {
        int count = params.getInt("count");
        if (count < 1)
        {
            return 1;
        }
        return count;
    }

    private static void copyParameterObjectToDictionary(ParameterObject params, dictionary target)
    {
        Enumeration keys = params.values.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            Object value = toDictionaryValue(params.get(key));
            if (value != null)
            {
                target.put(key, value);
            }
        }
    }

    private static Object toDictionaryValue(ParameterValue value)
    {
        if (value == null)
        {
            return null;
        }
        if (value.isString())
        {
            return value.getString();
        }
        if (value.isInt())
        {
            return Integer.valueOf(value.getInt());
        }
        if (value.isObject())
        {
            dictionary result = new dictionary();
            copyParameterObjectToDictionary(value.getObject(), result);
            return result;
        }
        if (value.isArray())
        {
            return toDictionaryVector(value.getArray());
        }
        return null;
    }

    private static Vector toDictionaryVector(ParameterArray array)
    {
        Vector result = new Vector();
        for (int i = 0; i < array.size(); i++)
        {
            Object value = toDictionaryValue(array.get(i));
            if (value != null)
            {
                result.add(value);
            }
        }
        return result;
    }

    private static void setObjectVar(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (name == null || name.length() == 0)
        {
            return;
        }
        utils.setObjVar(player, name, params.getString("value"));
    }

    private static void removeObjectVar(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (name == null || name.length() == 0)
        {
            return;
        }
        utils.removeObjVar(player, name);
    }

    private static void setScriptVar(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (name == null || name.length() == 0)
        {
            return;
        }
        utils.setScriptVar(player, name, params.getString("value"));
    }

    private static void removeScriptVar(obj_id player, String parameters) throws InterruptedException
    {
        ParameterObject params = ParameterParser.parseObject(parameters);
        String name = params.getString("name");
        if (name == null || name.length() == 0)
        {
            return;
        }
        utils.removeScriptVar(player, name);
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
