package script.systems.conversation_manager;

import script.dictionary;
import script.obj_id;

public class conversation_loader extends script.base_script
{
    public conversation_loader()
    {
    }

    public static final String TABLE_OBJVAR = "conversation_manager.table";
    public static final String TABLE_ROOT = "datatables/conversation_manager/";

    public conversation_definition load(obj_id npc) throws InterruptedException
    {
        String tableName = getStringObjVar(npc, TABLE_OBJVAR);
        conversation_definition definition = new conversation_definition();
        definition.table = tableName;
        definition.conversationId = tableName;
        if (tableName == null || tableName.equals(""))
        {
            LOG("conversation_manager", "Missing objvar '" + TABLE_OBJVAR + "' on npc " + npc);
            return definition;
        }
        String table = TABLE_ROOT + tableName + ".iff";
        definition.stringFile = getStringCell(table, 0, "stringFile");
        if (definition.stringFile == null || definition.stringFile.equals(""))
        {
            definition.stringFile = "conversation/" + tableName;
        }
        definition.nodes = loadNodes(table);
        definition.responses = loadResponses(table);
        definition.actions = loadActions(table);
        return definition;
    }

    public conversation_node[] loadNodes(String table) throws InterruptedException
    {
        String[] nodeIds = dataTableGetStringColumn(table, "nodeId");
        if (nodeIds == null)
        {
            return new conversation_node[0];
        }
        int count = 0;
        for (String nodeId : nodeIds)
        {
            if (nodeId != null && !nodeId.equals(""))
            {
                ++count;
            }
        }
        conversation_node[] nodes = new conversation_node[count];
        int index = 0;
        for (int i = 0; i < nodeIds.length; ++i)
        {
            if (nodeIds[i] == null || nodeIds[i].equals(""))
            {
                continue;
            }
            dictionary row = dataTableGetRow(table, i);
            conversation_node node = new conversation_node();
            node.nodeId = getString(row, "nodeId");
            node.textKey = getString(row, "nodeText");
            node.conditionType = getString(row, "nodeCondition");
            node.conditionArg1 = getString(row, "conditionArg1");
            node.conditionArg2 = getString(row, "conditionArg2");
            node.priority = getInt(row, "priority");
            node.startNode = getBoolean(row, "startNode");
            node.endNode = getBoolean(row, "endNode");
            node.enterActionSet = getString(row, "enterActionSet");
            nodes[index++] = node;
        }
        return nodes;
    }

    public conversation_response[] loadResponses(String table) throws InterruptedException
    {
        String[] responseIds = dataTableGetStringColumn(table, "responseId");
        if (responseIds == null)
        {
            return new conversation_response[0];
        }
        int count = 0;
        for (String responseId : responseIds)
        {
            if (responseId != null && !responseId.equals(""))
            {
                ++count;
            }
        }
        conversation_response[] responses = new conversation_response[count];
        int index = 0;
        for (int i = 0; i < responseIds.length; ++i)
        {
            if (responseIds[i] == null || responseIds[i].equals(""))
            {
                continue;
            }
            dictionary row = dataTableGetRow(table, i);
            conversation_response response = new conversation_response();
            response.responseId = getString(row, "responseId");
            response.nodeId = getString(row, "responseNode");
            response.textKey = getString(row, "responseText");
            response.conditionType = getString(row, "responseCondition");
            response.conditionArg1 = getString(row, "responseConditionArg1");
            response.conditionArg2 = getString(row, "responseConditionArg2");
            response.actionSet = getString(row, "actionSet");
            response.nextNodeId = getString(row, "nextNode");
            response.endConversation = getBoolean(row, "endConversation");
            response.endTextKey = getString(row, "endText");
            responses[index++] = response;
        }
        return responses;
    }

    public conversation_action[] loadActions(String table) throws InterruptedException
    {
        String[] actionSets = dataTableGetStringColumn(table, "actionSetName");
        if (actionSets == null)
        {
            return new conversation_action[0];
        }
        int count = 0;
        for (String actionSet : actionSets)
        {
            if (actionSet != null && !actionSet.equals(""))
            {
                ++count;
            }
        }
        conversation_action[] actions = new conversation_action[count];
        int index = 0;
        for (int i = 0; i < actionSets.length; ++i)
        {
            if (actionSets[i] == null || actionSets[i].equals(""))
            {
                continue;
            }
            dictionary row = dataTableGetRow(table, i);
            conversation_action action = new conversation_action();
            action.actionSet = getString(row, "actionSetName");
            action.order = getInt(row, "actionOrder");
            action.actionType = getString(row, "actionType");
            action.arg1 = getString(row, "actionArg1");
            action.arg2 = getString(row, "actionArg2");
            action.arg3 = getString(row, "actionArg3");
            actions[index++] = action;
        }
        return actions;
    }

    public String getStringCell(String table, int row, String column) throws InterruptedException
    {
        String value = dataTableGetString(table, row, column);
        return value == null ? "" : value;
    }

    public String getString(dictionary row, String key) throws InterruptedException
    {
        String value = row.getString(key);
        return value == null ? "" : value;
    }

    public int getInt(dictionary row, String key) throws InterruptedException
    {
        return row.getInt(key);
    }

    public boolean getBoolean(dictionary row, String key) throws InterruptedException
    {
        String value = getString(row, key);
        return value.equals("1") || value.equals("true") || value.equals("yes");
    }
}
