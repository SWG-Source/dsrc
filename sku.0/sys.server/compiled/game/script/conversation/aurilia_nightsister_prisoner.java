package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.*;

public class aurilia_nightsister_prisoner extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_nightsister_prisoner";
    public String c_stringFile = "conversation/aurilia_nightsister_prisoner";
    public aurilia_nightsister_prisoner()
    {
        super.scriptName = "aurilia_nightsister_prisoner";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean aurilia_nightsister_prisoner_condition_onAxkvaMinQuest(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_01");
    }
    private void aurilia_nightsister_prisoner_action_axkvaMinSignal(obj_id player) throws InterruptedException
    {
        groundquests.sendSignal(player, "axkva_min_intro_01");
    }
    private int aurilia_nightsister_prisoner_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7"))
        {
            doAnimationAction(npc, "scared");
            aurilia_nightsister_prisoner_action_axkvaMinSignal(player);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_9"));
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
        doAnimationAction(self, "scare");
        string_id message = new string_id(c_stringFile, "s_6");
        if (aurilia_nightsister_prisoner_condition_onAxkvaMinQuest(player))
		{
			string_id responses[] = new string_id[1];
			responses[0] = new string_id(c_stringFile, "s_7");
			utils.setScriptVar(player, conversation + ".branchId", 1);
			npcStartConversation(player, self, scriptName, message, responses);
		}
		else
		{
			chat.chat(self, player, message);
		}
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("aurilia_nightsister_prisoner"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 1 && aurilia_nightsister_prisoner_handleBranch1(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}
