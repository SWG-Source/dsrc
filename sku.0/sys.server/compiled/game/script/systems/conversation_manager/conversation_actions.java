package script.systems.conversation_manager;

import script.dictionary;
import script.library.armor;
import script.library.factions;
import script.library.groundquests;
import script.library.money;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class conversation_actions extends script.base_script
{
    public conversation_actions()
    {
    }

    public void executeActionSet(conversation_context ctx, conversation_definition definition, String actionSet) throws InterruptedException
    {
        if (actionSet == null || actionSet.equals(""))
        {
            return;
        }
        for (int order = 0; order < 1000; ++order)
        {
            for (conversation_action action : definition.actions)
            {
                if (action.actionSet != null && action.actionSet.equals(actionSet) && action.order == order)
                {
                    debug(ctx, "execute actionSet=" + actionSet + " order=" + action.order + " type=" + action.actionType + " arg1=" + action.arg1);
                    execute(ctx, action);
                }
            }
        }
    }

    public void execute(conversation_context ctx, conversation_action action) throws InterruptedException
    {
        if (action == null || action.actionType == null || action.actionType.equals(""))
        {
            return;
        }
        if (action.actionType.equals("grantQuest"))
        {
            groundquests.grantQuest(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("requestGrantQuest"))
        {
            groundquests.requestGrantQuest(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("clearQuest"))
        {
            groundquests.clearQuest(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("sendQuestSignal"))
        {
            groundquests.sendSignal(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("giveItem"))
        {
            createObjectInInventoryAllowOverload(action.arg1, ctx.player);
            return;
        }
        if (action.actionType.equals("giveArmorItem"))
        {
            obj_id armorItem = createObjectInInventoryAllowOverload(action.arg1, ctx.player);
            if (isIdValid(armorItem) && action.arg2 != null && !action.arg2.equals(""))
            {
                float min = utils.stringToFloat(action.arg2);
                float max = min;
                if (action.arg3 != null && !action.arg3.equals(""))
                {
                    max = utils.stringToFloat(action.arg3);
                }
                if (!isGameObjectTypeOf(armorItem, GOT_armor_foot) && !isGameObjectTypeOf(armorItem, GOT_armor_hand))
                {
                    armor.setArmorDataPercent(armorItem, 2, 1, min, max);
                }
            }
            return;
        }
        if (action.actionType.equals("grantSchematic"))
        {
            grantSchematic(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("rewardFaction"))
        {
            factions.awardFactionStanding(ctx.player, action.arg1, utils.stringToInt(action.arg2));
            return;
        }
        if (action.actionType.equals("giveCredits"))
        {
            money.bankTo(money.ACCT_JABBA, ctx.player, utils.stringToInt(action.arg1));
            return;
        }
        if (action.actionType.equals("setObjvar"))
        {
            setObjVar(ctx.player, action.arg1, action.arg2);
            return;
        }
        if (action.actionType.equals("removeObjvar"))
        {
            removeObjVar(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("playNpcAnimation"))
        {
            doAnimationAction(ctx.npc, action.arg1);
            return;
        }
        if (action.actionType.equals("playPlayerAnimation"))
        {
            doAnimationAction(ctx.player, action.arg1);
            return;
        }
        if (action.actionType.equals("facePlayer"))
        {
            faceTo(ctx.npc, ctx.player);
            return;
        }
        if (action.actionType.equals("sendMessageNpc"))
        {
            dictionary params = new dictionary();
            params.put("player", ctx.player);
            params.put("npc", ctx.npc);
            params.put("arg1", action.arg1);
            params.put("arg2", action.arg2);
            params.put("arg3", action.arg3);
            messageTo(ctx.npc, action.arg1, params, 0, false);
            return;
        }
        if (action.actionType.equals("sendSystemMessage"))
        {
            sendSystemMessage(ctx.player, new string_id(ctx.stringFile, action.arg1));
            return;
        }
        LOG("conversation_manager", "Unknown action type '" + action.actionType + "' for conversation '" + ctx.conversationId + "'");
        debug(ctx, "unknown action type=" + action.actionType);
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
