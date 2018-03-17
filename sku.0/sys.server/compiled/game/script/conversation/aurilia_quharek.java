package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.*;

public class aurilia_quharek extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.aurilia_quharek";
    public static String c_stringFile = "conversation/aurilia_quharek";

    public aurilia_quharek()
    {
        super.scriptName = "aurilia_quharek";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean aurilia_quharek_condition__defaultCondition() throws InterruptedException
    {
        return true;
    }
    private boolean aurilia_quharek_condition_axkva_min_intro_01(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "axkva_min_intro", "axkva_min_intro_01");
    }
    private int aurilia_quharek_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (aurilia_quharek_condition__defaultCondition())
            {
                doAnimationAction(npc, "laugh");
                utils.removeScriptVar(player, conversation + ".branchId");
                npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_8"));
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (aurilia_quharek_condition__defaultCondition())
        {
            string_id message = new string_id(c_stringFile, "s_4");
            if (aurilia_quharek_condition_axkva_min_intro_01(player))
            {
                string_id responses[] = new string_id[1];
                responses[0] = new string_id(c_stringFile, "s_6");
                utils.setScriptVar(player, conversation + ".branchId", 1);
                npcStartConversation(player, self, "aurilia_quharek", new string_id(c_stringFile, "s_4"), responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("aurilia_quharek"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 1 && aurilia_quharek_handleBranch1(player, self, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}
