package script.conversation;

import script.*;
import script.library.ai_lib;
import script.library.chat;
import script.library.smuggler;
import script.library.utils;

public class aurilia_junk_dealer extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_junk_dealer";
    public String c_stringFile = "conversation/aurilia_junk_dealer";
    public aurilia_junk_dealer()
    {
        super.scriptName = "aurilia_junk_dealer";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean aurilia_junk_dealer_condition_check_inv(obj_id player, obj_id npc) throws InterruptedException
    {
        return smuggler.checkInventory(player, npc);
    }
    private void aurilia_junk_dealer_action_start_dealing(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary params = new dictionary();
        params.put("player", player);
        messageTo(npc, "startDealing", params, 1.0f, false);
    }
    private int aurilia_junk_dealer_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c86eba88"))
        {
            aurilia_junk_dealer_action_start_dealing(player, npc);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_24f30320"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_370a03c"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_df5bd64e"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        faceTo(self, player);
        string_id responses[];
        if (aurilia_junk_dealer_condition_check_inv(player, self))
		{
            responses = new string_id[2];
            responses[0] = new string_id(c_stringFile, "s_c86eba88");
            responses[1] = new string_id(c_stringFile, "s_370a03c");
		}
        else {
            responses = new string_id[1];
            responses[0] = new string_id(c_stringFile, "s_370a03c");
        }
        utils.setScriptVar(player, conversation + ".branchId", 1);
        npcStartConversation(player, self, scriptName, new string_id(c_stringFile, "s_3c06418f"), responses);
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("aurilia_junk_dealer"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 1 && aurilia_junk_dealer_handleBranch1(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}
