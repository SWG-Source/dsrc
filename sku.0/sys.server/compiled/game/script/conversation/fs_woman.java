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
import script.library.fs_quests;
import script.library.quests;

public class fs_woman extends script.base_script
{
    public fs_woman()
    {
    }
    public static String c_stringFile = "conversation/fs_woman";
    public boolean fs_woman_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_woman_condition_questOwner(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id owner = null;
        owner = getObjIdObjVar(npc, "fs_dath_woman.holder");
        if (player == owner)
        {
            return true;
        }
        return false;
    }
    public void fs_woman_action_completeQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!quests.isActive("fs_village_elder", player))
        {
            fs_quests.setStage(player, 10);
            quests.activate("fs_village_elder", player, null);
            messageTo(npc, "cleanUp", null, 180, false);
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_woman");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        obj_id owner = getObjIdObjVar(self, "quest.owner");
        setObjVar(self, "quest.fs_dath_woman_talk.target", owner);
        quests.activate("fs_dath_woman_talk", owner, null);
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
        detachScript(self, "npc.conversation.fs_woman");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (fs_woman_condition_questOwner(player, self))
        {
            fs_woman_action_completeQuest(player, self);
            string_id message = new string_id(c_stringFile, "s_b46f1e5e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_woman_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_827a56fe");
                }
                setObjVar(player, "conversation.fs_woman.branchId", 1);
                npcStartConversation(player, self, "fs_woman", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (!fs_woman_condition_questOwner(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e87eb378");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("fs_woman"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_woman.branchId");
        if (branchId == 1 && response.equals("s_827a56fe"))
        {
            if (fs_woman_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c76aeb53");
                removeObjVar(player, "conversation.fs_woman.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hello, welcome to Dathomir.  I was told you would be looking for me.  Have you spoken to the village elder yet?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_woman.branchId");
        return SCRIPT_CONTINUE;
    }
}
