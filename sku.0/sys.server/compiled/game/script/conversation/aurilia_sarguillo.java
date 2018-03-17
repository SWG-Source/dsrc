package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.*;

public class aurilia_sarguillo extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_sarguillo";
    public String c_stringFile = "conversation/aurilia_sarguillo";

    public aurilia_sarguillo()
    {
        super.scriptName = "aurilia_sarguillo";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean aurilia_sarguillo_condition_axkva_min_intro_01(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_01");
    }
    private int aurilia_sarguillo_handleBranch1(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_5"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_6"));
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
        string_id message = new string_id(c_stringFile, "s_4");
        if (aurilia_sarguillo_condition_axkva_min_intro_01(player))
		{
			string_id responses[] = new string_id[1];
			responses[0] = new string_id(c_stringFile, "s_5");
			utils.setScriptVar(player, conversation + ".branchId", 1);
			npcStartConversation(player, self, "aurilia_sarguillo", message, responses);
		}
		else
		{
			chat.chat(self, player, message);
		}
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("aurilia_sarguillo"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 1 && aurilia_sarguillo_handleBranch1(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}
