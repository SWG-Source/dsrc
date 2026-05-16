package script.systems.conversation_manager;

public class conversation_definition
{
    public String conversationId;
    public String table;
    public String stringFile;
    public conversation_node[] nodes;
    public conversation_response[] responses;
    public conversation_action[] actions;

    public conversation_definition()
    {
        nodes = new conversation_node[0];
        responses = new conversation_response[0];
        actions = new conversation_action[0];
    }
}
