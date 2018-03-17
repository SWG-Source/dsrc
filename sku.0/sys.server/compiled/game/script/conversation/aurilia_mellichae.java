package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.obj_id;
import script.string_id;

public class aurilia_mellichae extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_mellichae";
    public String c_stringFile = "conversation/aurilia_mellichae";

    public aurilia_mellichae()
    {
        super.scriptName = "aurilia_mellichae";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        doAnimationAction(self, "standing_raise_fist");
        chat.chat(self, player, new string_id(c_stringFile, "s_4"));
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals(scriptName))
        {
            return SCRIPT_CONTINUE;
        }
        return super.OnNpcConversationResponse(self, conversationId, player, response);
    }
}
