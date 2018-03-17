package script.conversation;

import script.library.ai_lib;
import script.library.chat;
import script.library.groundquests;
import script.library.utils;
import script.*;

public class barada extends script.conversation.base.conversation_base
{
    public String conversation = "conversation.barada";
    public String c_stringFile = "conversation/barada";

    public barada()
    {
        super.scriptName = "barada";
        super.conversation = conversation;
        super.c_stringFile = c_stringFile;
    }
    private boolean barada_condition_completedBarada(obj_id player) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest/jabba_barada_v2");
    }
    private boolean barada_condition_completedPorcellus(obj_id player) throws InterruptedException
    {
        return groundquests.hasCompletedQuest(player, "quest/jabba_porcellus");
    }
    private boolean barada_condition_findingParts(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/jabba_barada_v2", "suspiciousParts");
    }
    private boolean barada_condition_killingCompetition(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/jabba_barada_v2", "PodRaceVillainy");
    }
    private boolean barada_condition_readyToReturn(obj_id player) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "quest/jabba_barada_v2", "readyToReturn");
    }
    private void barada_action_grantBaradaQuest(obj_id player) throws InterruptedException
    {
        groundquests.requestGrantQuest(player, "quest/jabba_barada_v2");
    }
    private void barada_action_sendBaradaDoneSignal(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "doneWithBarada");
        groundquests.grantQuest(player, "pointer_bib_fortuna");
    }
    private void barada_action_clearPointer(obj_id player, obj_id npc) throws InterruptedException
    {
        faceTo(npc, player);
        groundquests.sendSignal(player, "found_barada");
    }
    private int barada_handleBranch5(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            return craft_response(new String[] {"s_15", "s_17", "s_36"}, 6, player);
        }
        if (response.equals("s_40"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_42"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int barada_handleBranch6(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            return craft_response(new String[] {"s_24", "s_26", "s_34"}, 7, player);
        }
        if (response.equals("s_36"))
        {
            barada_action_grantBaradaQuest(player);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_38"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int barada_handleBranch7(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_26"))
        {
            return craft_response(new String[] {"s_28", "s_30", "s_32"}, 8, player);
        }
        if (response.equals("s_34"))
        {
            barada_action_grantBaradaQuest(player);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_38"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    private int barada_handleBranch8(obj_id player, string_id response) throws InterruptedException
    {
        if (response.equals("s_30"))
        {
            barada_action_grantBaradaQuest(player);
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_38"));
            return SCRIPT_CONTINUE;
        }
        if (response.equals("s_32"))
        {
            utils.removeScriptVar(player, conversation + ".branchId");
            npcEndConversationWithMessage(player, new string_id(c_stringFile, "s_42"));
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_INTERESTING);
        return super.OnInitialize(self);
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_INTERESTING);
        return super.OnAttach(self);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        faceTo(self, player);
        if (barada_condition_completedBarada(player))
        {
            chat.chat(self, player, new string_id(c_stringFile, "s_4"));
            return SCRIPT_CONTINUE;
        }
        if (barada_condition_readyToReturn(player))
        {
            barada_action_sendBaradaDoneSignal(player, self);
            chat.chat(self, player, new string_id(c_stringFile, "s_23"));
            return SCRIPT_CONTINUE;
        }
        if (barada_condition_killingCompetition(player))
        {
            chat.chat(self, player, new string_id(c_stringFile, "s_20"));
            return SCRIPT_CONTINUE;
        }
        if (barada_condition_findingParts(player))
        {
            chat.chat(self, player, new string_id(c_stringFile, "s_19"));
            return SCRIPT_CONTINUE;
        }
        if (barada_condition_completedPorcellus(player))
        {
            barada_action_clearPointer(player, self);
            return craft_repeater(new String[] {"s_11", "s_13", "s_40"}, 5, player, self);
        }
        chat.chat(self, player, new string_id(c_stringFile, "s_44"));
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("barada"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = utils.getIntScriptVar(player, conversation + ".branchId");
        if (branchId == 5 && barada_handleBranch5(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && barada_handleBranch6(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && barada_handleBranch7(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && barada_handleBranch8(player, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, conversation + ".branchId");
        return SCRIPT_CONTINUE;
    }
}
