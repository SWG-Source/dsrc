package script.library.conversation_manager;

import script.dictionary;

import java.util.Hashtable;
import java.util.Vector;

public class ConversationManagerData
{
    public ConversationManagerData()
    {
    }

    public static class Condition
    {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_PARAMETERS = "parameters";
        public static final String TYPE_IS_QUEST_ACTIVE = "isQuestActive";
        public static final String TYPE_IS_QUEST_COMPLETED = "isQuestCompleted";
        public static final String TYPE_IS_ANY_QUEST_ACTIVE = "isAnyQuestActive";
        public static final String TYPE_PLAYER_HAS_OBJVAR = "playerHasObjVar";
        public static final String TYPE_PLAYER_HAS_SCRIPT_VAR = "playerHasScriptVar";
        public static final String TYPE_NPC_HAS_OBJVAR = "npcHasObjVar";
        public static final String TYPE_NPC_HAS_SCRIPT_VAR = "npcHasScriptVar";
        public static final String TYPE_PLAYER_RESPONSE = "playerResponse";
        public static final String TYPE_DEFAULT = "default";

        public String id = "";
        public String type = "";
        public String parameters = "";

        public Condition()
        {
        }

        public Condition(String id, String type, String parameters)
        {
            this.id = id;
            this.type = type;
            this.parameters = parameters;
        }

        public String getId()
        {
            return id;
        }

        public String getType()
        {
            return type;
        }

        public String getParameters()
        {
            return parameters;
        }

        public static Condition fromDictionary(dictionary row)
        {
            if (row == null)
            {
                return null;
            }
            return new Condition(row.getString(COLUMN_ID), row.getString(COLUMN_TYPE), row.getString(COLUMN_PARAMETERS));
        }
    }

    public static class ConditionGroup
    {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOGICAL_OPERATION = "logical_operation";
        public static final String COLUMN_CONDITION_ID = "condition_id";

        public String id = "";
        public String logicalOperation = "";
        public String conditionId = "";

        public ConditionGroup()
        {
        }

        public ConditionGroup(String id, String logicalOperation, String conditionId)
        {
            this.id = id;
            this.logicalOperation = logicalOperation;
            this.conditionId = conditionId;
        }

        public String getId()
        {
            return id;
        }

        public String getLogicalOperation()
        {
            return logicalOperation;
        }

        public String getConditionId()
        {
            return conditionId;
        }

        public static ConditionGroup fromDictionary(dictionary row)
        {
            if (row == null)
            {
                return null;
            }
            return new ConditionGroup(row.getString(COLUMN_ID), row.getString(COLUMN_LOGICAL_OPERATION), row.getString(COLUMN_CONDITION_ID));
        }
    }

    public static class ConditionSet
    {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_GROUP_ID = "group_id";
        public static final String COLUMN_ORDER = "order";

        public String id = "";
        public String groupId = "";
        public int order = 0;

        public ConditionSet()
        {
        }

        public ConditionSet(String id, String groupId, int order)
        {
            this.id = id;
            this.groupId = groupId;
            this.order = order;
        }

        public String getId()
        {
            return id;
        }

        public String getGroupId()
        {
            return groupId;
        }

        public int getOrder()
        {
            return order;
        }

        public static ConditionSet fromDictionary(dictionary row)
        {
            if (row == null)
            {
                return null;
            }
            return new ConditionSet(row.getString(COLUMN_ID), row.getString(COLUMN_GROUP_ID), row.getInt(COLUMN_ORDER));
        }
    }

    public static class ResponseState
    {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONDITION_SET_ID = "condition_set_id";
        public static final String COLUMN_ORDER = "order";

        public String id = "";
        public String conditionSetId = "";
        public int order = 0;

        public ResponseState()
        {
        }

        public ResponseState(String id, String conditionSetId, int order)
        {
            this.id = id;
            this.conditionSetId = conditionSetId;
            this.order = order;
        }

        public String getId()
        {
            return id;
        }

        public String getConditionSetId()
        {
            return conditionSetId;
        }

        public int getOrder()
        {
            return order;
        }

        public static ResponseState fromDictionary(dictionary row)
        {
            if (row == null)
            {
                return null;
            }
            return new ResponseState(row.getString(COLUMN_ID), row.getString(COLUMN_CONDITION_SET_ID), row.getInt(COLUMN_ORDER));
        }
    }

    public static class ResponseAction
    {
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_RESPONSE_STATE_ID = "response_state_id";
        public static final String COLUMN_ORDER = "order";
        public static final String COLUMN_ACTION = "action";
        public static final String COLUMN_PARAMETERS = "parameters";
        public static final String ACTION_SIGNAL = "signal";
        public static final String ACTION_CONVERSATION = "conversation";
        public static final String ACTION_SPEAK = "speak";
        public static final String ACTION_ANIMATION = "animation";
        public static final String ACTION_COMPLETE_QUEST = "complete_quest";
        public static final String ACTION_GRANT_QUEST = "grant_quest";
        public static final String ACTION_GRANT_REWARD = "grant_reward";
        public static final String ACTION_SET_OBJECT_VAR = "set_object_var";
        public static final String ACTION_REMOVE_OBJECT_VAR = "remove_object_var";
        public static final String ACTION_SET_SCRIPT_VAR = "set_script_var";
        public static final String ACTION_REMOVE_SCRIPT_VAR = "remove_script_var";

        public String id = "";
        public String responseStateId = "";
        public int order = 0;
        public String action = "";
        public String parameters = "";

        public ResponseAction()
        {
        }

        public ResponseAction(String id, String responseStateId, int order, String action, String parameters)
        {
            this.id = id;
            this.responseStateId = responseStateId;
            this.order = order;
            this.action = action;
            this.parameters = parameters;
        }

        public String getId()
        {
            return id;
        }

        public int getOrder()
        {
            return order;
        }

        public String getResponseStateId()
        {
            return responseStateId;
        }

        public String getAction()
        {
            return action;
        }

        public String getParameters()
        {
            return parameters;
        }

        public static ResponseAction fromDictionary(dictionary row)
        {
            if (row == null)
            {
                return null;
            }
            return new ResponseAction(row.getString(COLUMN_ID), row.getString(COLUMN_RESPONSE_STATE_ID), row.getInt(COLUMN_ORDER), row.getString(COLUMN_ACTION), row.getString(COLUMN_PARAMETERS));
        }
    }

    public static class ParameterValue
    {
        public static final int TYPE_STRING = 1;
        public static final int TYPE_INT = 2;
        public static final int TYPE_OBJECT = 3;
        public static final int TYPE_ARRAY = 4;

        public int type = 0;
        public Object value = null;

        public ParameterValue()
        {
        }

        public ParameterValue(int type, Object value)
        {
            this.type = type;
            this.value = value;
        }

        public static ParameterValue stringValue(String value)
        {
            return new ParameterValue(TYPE_STRING, value);
        }

        public static ParameterValue intValue(int value)
        {
            return new ParameterValue(TYPE_INT, Integer.valueOf(value));
        }

        public static ParameterValue objectValue(ParameterObject value)
        {
            return new ParameterValue(TYPE_OBJECT, value);
        }

        public static ParameterValue arrayValue(ParameterArray value)
        {
            return new ParameterValue(TYPE_ARRAY, value);
        }

        public int getType()
        {
            return type;
        }

        public boolean isString()
        {
            return type == TYPE_STRING;
        }

        public boolean isInt()
        {
            return type == TYPE_INT;
        }

        public boolean isObject()
        {
            return type == TYPE_OBJECT;
        }

        public boolean isArray()
        {
            return type == TYPE_ARRAY;
        }

        public String getString()
        {
            if (!isString())
            {
                return null;
            }
            return (String)value;
        }

        public int getInt()
        {
            if (!isInt())
            {
                return 0;
            }
            return ((Integer)value).intValue();
        }

        public ParameterObject getObject()
        {
            if (!isObject())
            {
                return null;
            }
            return (ParameterObject)value;
        }

        public ParameterArray getArray()
        {
            if (!isArray())
            {
                return null;
            }
            return (ParameterArray)value;
        }
    }

    public static class ParameterObject
    {
        public Hashtable values = new Hashtable();

        public ParameterObject()
        {
        }

        public void put(String key, ParameterValue value)
        {
            values.put(key, value);
        }

        public boolean containsKey(String key)
        {
            return values.containsKey(key);
        }

        public ParameterValue get(String key)
        {
            return (ParameterValue)values.get(key);
        }

        public String getString(String key)
        {
            ParameterValue value = get(key);
            if (value == null)
            {
                return null;
            }
            return value.getString();
        }

        public int getInt(String key)
        {
            ParameterValue value = get(key);
            if (value == null)
            {
                return 0;
            }
            return value.getInt();
        }

        public ParameterObject getObject(String key)
        {
            ParameterValue value = get(key);
            if (value == null)
            {
                return null;
            }
            return value.getObject();
        }

        public ParameterArray getArray(String key)
        {
            ParameterValue value = get(key);
            if (value == null)
            {
                return null;
            }
            return value.getArray();
        }

        public String[] getStringArray(String key)
        {
            ParameterArray array = getArray(key);
            if (array == null)
            {
                return null;
            }
            return array.toStringArray();
        }
    }

    public static class ParameterArray
    {
        public Vector values = new Vector();

        public ParameterArray()
        {
        }

        public void add(ParameterValue value)
        {
            values.add(value);
        }

        public int size()
        {
            return values.size();
        }

        public ParameterValue get(int index)
        {
            return (ParameterValue)values.get(index);
        }

        public String getString(int index)
        {
            ParameterValue value = get(index);
            if (value == null)
            {
                return null;
            }
            return value.getString();
        }

        public int getInt(int index)
        {
            ParameterValue value = get(index);
            if (value == null)
            {
                return 0;
            }
            return value.getInt();
        }

        public ParameterObject getObject(int index)
        {
            ParameterValue value = get(index);
            if (value == null)
            {
                return null;
            }
            return value.getObject();
        }

        public ParameterArray getArray(int index)
        {
            ParameterValue value = get(index);
            if (value == null)
            {
                return null;
            }
            return value.getArray();
        }

        public String[] toStringArray()
        {
            String[] result = new String[values.size()];
            for (int i = 0; i < values.size(); i++)
            {
                result[i] = getString(i);
            }
            return result;
        }
    }
}
