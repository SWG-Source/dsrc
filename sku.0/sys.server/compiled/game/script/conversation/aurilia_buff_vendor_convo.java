package script.conversation;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.obj_id;
import script.string_id;

public class aurilia_buff_vendor_convo extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_buff_vendor_convo";
    public String c_stringFile = "conversation/aurilia_buff_vendor_convo";

    public aurilia_buff_vendor_convo()
    {
        super.scriptName = "aurilia_buff_vendor_convo";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private void aurilia_buff_vendor_convo_action_showTokenVendorUI(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(npc, "showInventorySUI", d, 0, false);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return super.OnInitialize(self);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return super.OnAttach(self);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        aurilia_buff_vendor_convo_action_showTokenVendorUI(player, self);
        chat.chat(self, player, new string_id(c_stringFile, "s_4"));
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("aurilia_buff_vendor_convo"))
        {
            return SCRIPT_CONTINUE;
        }
        return super.OnNpcConversationResponse(self, conversationId, player, response);
    }
}
