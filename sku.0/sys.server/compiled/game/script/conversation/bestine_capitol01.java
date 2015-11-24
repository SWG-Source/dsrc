package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.chat;

public class bestine_capitol01 extends script.base_script
{
    public bestine_capitol01()
    {
    }
    public static String c_stringFile = "conversation/bestine_capitol01";
    public boolean bestine_capitol01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean bestine_capitol01_condition_nonoffice(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasObjVar(npc, "bestine.electionEnded");
    }
    public void bestine_capitol01_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.bestine_capitol01");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "npc.conversation.bestine_capitol01");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (bestine_capitol01_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_da5842cb");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (!bestine_capitol01_condition_nonoffice(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_f9dda042");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (bestine_capitol01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (bestine_capitol01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_c82e9a2f");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_90ce1297");
                }
                setObjVar(player, "conversation.bestine_capitol01.branchId", 2);
                npcStartConversation(player, self, "bestine_capitol01", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("bestine_capitol01"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.bestine_capitol01.branchId");
        if (branchId == 2 && response.equals("s_c82e9a2f"))
        {
            if (bestine_capitol01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8819f19f");
                removeObjVar(player, "conversation.bestine_capitol01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You look a little lost. Or have you come to the capitol to vote in the upcoming election? Confused? You haven't heard of the changes to Bestine? I thought everyone had heard of it! Must be an outsider. Oh well! Do you want to hear about it?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_90ce1297"))
        {
            if (bestine_capitol01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_edaf838a");
                removeObjVar(player, "conversation.bestine_capitol01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You look a little lost. Or have you come to the capitol to vote in the upcoming election? Confused? You haven't heard of the changes to Bestine? I thought everyone had heard of it! Must be an outsider. Oh well! Do you want to hear about it?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.bestine_capitol01.branchId");
        return SCRIPT_CONTINUE;
    }
}
