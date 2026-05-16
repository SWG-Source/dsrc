package script.systems.conversation_manager;

import script.library.ai_lib;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class conversation_engine extends script.base_script
{
    public conversation_engine()
    {
    }

    public static final String CONVERSATION_NAME = "conversationManager";
    public static final String SCRIPT_VAR_PREFIX = "conversation_manager.";

    protected conversation_conditions conditions = new conversation_conditions();
    protected conversation_actions actions = new conversation_actions();

    public int startConversation(obj_id npc, obj_id player, conversation_definition definition) throws InterruptedException
    {
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        conversation_context ctx = new conversation_context(npc, player, definition);
        debug(ctx, "start conversation table=" + definition.table);
        conversation_node startNode = findStartNode(ctx, definition);
        if (startNode == null)
        {
            LOG("conversation_manager", "No valid start node for conversation '" + definition.conversationId + "'");
            debug(ctx, "no valid start node");
            return SCRIPT_OVERRIDE;
        }
        debug(ctx, "start node=" + startNode.nodeId);
        return enterNode(ctx, definition, startNode);
    }

    public int continueConversation(obj_id npc, obj_id player, string_id responseId, conversation_definition definition) throws InterruptedException
    {
        conversation_context ctx = new conversation_context(npc, player, definition);
        String currentNodeId = getCurrentNode(player, definition);
        debug(ctx, "continue conversation currentNode=" + currentNodeId + " response=" + responseId.getAsciiId());
        if (currentNodeId == null || currentNodeId.equals(""))
        {
            LOG("conversation_manager", "No current node for conversation '" + definition.conversationId + "'");
            endConversation(ctx, null);
            return SCRIPT_CONTINUE;
        }
        conversation_response response = findResponse(ctx, definition, currentNodeId, responseId);
        if (response == null)
        {
            LOG("conversation_manager", "No matching response '" + responseId + "' for node '" + currentNodeId + "'");
            debug(ctx, "no matching response for current node");
            endConversation(ctx, null);
            return SCRIPT_CONTINUE;
        }
        debug(ctx, "selected response=" + response.responseId + " actionSet=" + response.actionSet);
        actions.executeActionSet(ctx, definition, response.actionSet);
        if (response.endConversation)
        {
            debug(ctx, "response ends conversation");
            endConversation(ctx, response.endTextKey);
            return SCRIPT_CONTINUE;
        }
        conversation_node nextNode = findNode(definition, response.nextNodeId);
        if (nextNode == null)
        {
            debug(ctx, "no next node, ending conversation");
            endConversation(ctx, response.endTextKey);
            return SCRIPT_CONTINUE;
        }
        debug(ctx, "next node=" + nextNode.nodeId);
        return enterNode(ctx, definition, nextNode);
    }

    public int enterNode(conversation_context ctx, conversation_definition definition, conversation_node node) throws InterruptedException
    {
        debug(ctx, "enter node=" + node.nodeId + " enterActionSet=" + node.enterActionSet);
        actions.executeActionSet(ctx, definition, node.enterActionSet);
        conversation_response[] responses = getValidResponses(ctx, definition, node.nodeId);
        string_id message = new string_id(ctx.stringFile, node.textKey);
        if (node.endNode || responses.length == 0)
        {
            debug(ctx, "node ends conversation responseCount=" + responses.length);
            endConversation(ctx, node.textKey);
            return SCRIPT_CONTINUE;
        }
        string_id[] responseIds = new string_id[responses.length];
        for (int i = 0; i < responses.length; ++i)
        {
            responseIds[i] = new string_id(ctx.stringFile, responses[i].textKey);
        }
        setCurrentNode(ctx.player, definition, node.nodeId);
        debug(ctx, "show node=" + node.nodeId + " responseCount=" + responses.length);
        npcStartConversation(ctx.player, ctx.npc, CONVERSATION_NAME, message, responseIds);
        return SCRIPT_CONTINUE;
    }

    public void endConversation(conversation_context ctx, String textKey) throws InterruptedException
    {
        debug(ctx, "end conversation textKey=" + textKey);
        clearCurrentNode(ctx.player, ctx.conversationId);
        if (textKey != null && !textKey.equals(""))
        {
            npcEndConversationWithMessage(ctx.player, new string_id(ctx.stringFile, textKey));
        }
        else 
        {
            npcEndConversation(ctx.player);
        }
    }

    public conversation_node findStartNode(conversation_context ctx, conversation_definition definition) throws InterruptedException
    {
        conversation_node best = null;
        for (conversation_node node : definition.nodes)
        {
            if (node.startNode && conditions.passesNode(ctx, node))
            {
                if (best == null || node.priority < best.priority)
                {
                    best = node;
                }
            }
        }
        return best;
    }

    public conversation_node findNode(conversation_definition definition, String nodeId) throws InterruptedException
    {
        if (nodeId == null || nodeId.equals(""))
        {
            return null;
        }
        for (conversation_node node : definition.nodes)
        {
            if (node.nodeId != null && node.nodeId.equals(nodeId))
            {
                return node;
            }
        }
        return null;
    }

    public conversation_response findResponse(conversation_context ctx, conversation_definition definition, String nodeId, string_id selectedResponse) throws InterruptedException
    {
        for (conversation_response response : definition.responses)
        {
            if (response.nodeId != null && response.nodeId.equals(nodeId) && response.textKey != null && response.textKey.equals(selectedResponse.getAsciiId()) && conditions.passesResponse(ctx, response))
            {
                return response;
            }
        }
        return null;
    }

    public conversation_response[] getValidResponses(conversation_context ctx, conversation_definition definition, String nodeId) throws InterruptedException
    {
        int count = 0;
        for (conversation_response response : definition.responses)
        {
            if (response.nodeId != null && response.nodeId.equals(nodeId) && conditions.passesResponse(ctx, response))
            {
                ++count;
            }
        }
        conversation_response[] valid = new conversation_response[count];
        int index = 0;
        for (conversation_response response : definition.responses)
        {
            if (response.nodeId != null && response.nodeId.equals(nodeId) && conditions.passesResponse(ctx, response))
            {
                valid[index++] = response;
            }
        }
        return valid;
    }

    public String getCurrentNode(obj_id player, conversation_definition definition) throws InterruptedException
    {
        return utils.getStringScriptVar(player, getCurrentNodeScriptVar(definition.conversationId));
    }

    public void setCurrentNode(obj_id player, conversation_definition definition, String nodeId) throws InterruptedException
    {
        utils.setScriptVar(player, getCurrentNodeScriptVar(definition.conversationId), nodeId);
    }

    public void clearCurrentNode(obj_id player, String conversationId) throws InterruptedException
    {
        utils.removeScriptVar(player, getCurrentNodeScriptVar(conversationId));
    }

    public String getCurrentNodeScriptVar(String conversationId) throws InterruptedException
    {
        return SCRIPT_VAR_PREFIX + conversationId + ".currentNode";
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
