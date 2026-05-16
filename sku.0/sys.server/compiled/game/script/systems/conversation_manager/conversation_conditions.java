package script.systems.conversation_manager;

import script.library.features;
import script.library.factions;
import script.library.groundquests;
import script.obj_id;

public class conversation_conditions extends script.base_script
{
    public conversation_conditions()
    {
    }

    public boolean passes(conversation_context ctx, String conditionType, String arg1, String arg2) throws InterruptedException
    {
        if (conditionType == null || conditionType.equals("") || conditionType.equals("always"))
        {
            return true;
        }
        if (conditionType.equals("questActive"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, groundquests.isQuestActive(ctx.player, arg1));
        }
        if (conditionType.equals("questComplete"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, groundquests.hasCompletedQuest(ctx.player, arg1));
        }
        if (conditionType.equals("questActiveOrComplete"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, groundquests.isQuestActiveOrComplete(ctx.player, arg1));
        }
        if (conditionType.equals("taskActive"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, groundquests.isTaskActive(ctx.player, arg1, arg2));
        }
        if (conditionType.equals("taskComplete"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, groundquests.hasCompletedTask(ctx.player, arg1, arg2));
        }
        if (conditionType.equals("hasObjvar"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, hasObjVar(ctx.player, arg1));
        }
        if (conditionType.equals("notHasObjvar"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, !hasObjVar(ctx.player, arg1));
        }
        if (conditionType.equals("objvarEquals"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, hasObjVar(ctx.player, arg1) && getStringObjVar(ctx.player, arg1).equals(arg2));
        }
        if (conditionType.equals("faction"))
        {
            String playerFaction = factions.getFaction(ctx.player);
            return debugResult(ctx, conditionType, arg1, arg2, playerFaction != null && playerFaction.equals(arg1));
        }
        if (conditionType.equals("hasSkill"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, hasSkill(ctx.player, arg1));
        }
        if (conditionType.equals("hasEp3"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, features.hasEpisode3Expansion(ctx.player));
        }
        if (conditionType.equals("isGm"))
        {
            return debugResult(ctx, conditionType, arg1, arg2, hasObjVar(ctx.player, "gm"));
        }
        LOG("conversation_manager", "Unknown condition type '" + conditionType + "' for conversation '" + ctx.conversationId + "'");
        debug(ctx, "unknown condition type=" + conditionType + " arg1=" + arg1 + " arg2=" + arg2);
        return false;
    }

    public boolean passesNode(conversation_context ctx, conversation_node node) throws InterruptedException
    {
        return passes(ctx, node.conditionType, node.conditionArg1, node.conditionArg2);
    }

    public boolean passesResponse(conversation_context ctx, conversation_response response) throws InterruptedException
    {
        return passes(ctx, response.conditionType, response.conditionArg1, response.conditionArg2);
    }

    public boolean debugResult(conversation_context ctx, String conditionType, String arg1, String arg2, boolean result) throws InterruptedException
    {
        debug(ctx, "condition type=" + conditionType + " arg1=" + arg1 + " arg2=" + arg2 + " result=" + result);
        return result;
    }

    public void debug(conversation_context ctx, String message) throws InterruptedException
    {
        if (ctx == null || !isIdValid(ctx.npc) || !isIdValid(ctx.player))
        {
            return;
        }
        if (!hasObjVar(ctx.npc, "conversation_manager.debug"))
        {
            return;
        }
        if (!getBooleanObjVar(ctx.npc, "conversation_manager.debug"))
        {
            return;
        }
        sendSystemMessage(ctx.player, "[conversation_manager] " + message, null);
    }
}
