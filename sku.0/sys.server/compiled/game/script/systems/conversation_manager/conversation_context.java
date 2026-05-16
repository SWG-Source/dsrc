package script.systems.conversation_manager;

import script.obj_id;

public class conversation_context
{
    public obj_id npc;
    public obj_id player;
    public String conversationId;
    public String table;
    public String stringFile;

    public conversation_context(obj_id npc, obj_id player, conversation_definition definition)
    {
        this.npc = npc;
        this.player = player;
        this.conversationId = definition.conversationId;
        this.table = definition.table;
        this.stringFile = definition.stringFile;
    }
}
