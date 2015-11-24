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

public class quest_hero_of_tatooine_box extends script.base_script
{
    public quest_hero_of_tatooine_box()
    {
    }
    public static String c_stringFile = "conversation/quest_hero_of_tatooine_box";
    public boolean quest_hero_of_tatooine_box_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean quest_hero_of_tatooine_box_condition_Noquest(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id building = getTopMostContainer(npc);
        if (hasObjVar(building, "quest.hero_of_tatooine.progress"))
        {
            obj_id guy = getObjIdObjVar(building, "quest.hero_of_tatooine.progress");
            if (player == guy)
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_box_condition_Intercom(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "quest.hero_of_tatooine.honor"))
        {
            if (hasObjVar(player, "quest.hero_of_tatooine.intercom"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean quest_hero_of_tatooine_box_condition_DistractObj(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "quest.hero_of_tatooine.distract"))
        {
            return true;
        }
        return false;
    }
    public void quest_hero_of_tatooine_box_action_Distract(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "quest.hero_of_tatooine.distract", true);
        removeObjVar(player, "quest.hero_of_tatooine.intercom");
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.quest_hero_of_tatooine_box");
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
        detachScript(self, "npc.conversation.quest_hero_of_tatooine_box");
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
        if (quest_hero_of_tatooine_box_condition_DistractObj(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e606ff8b");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46d11795");
                }
                setObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId", 1);
                npcStartConversation(player, self, "quest_hero_of_tatooine_box", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_box_condition_Intercom(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_cf46030c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_46ea9e29");
                }
                setObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId", 3);
                npcStartConversation(player, self, "quest_hero_of_tatooine_box", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_31845b51");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("quest_hero_of_tatooine_box"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
        if (branchId == 1 && response.equals("s_46d11795"))
        {
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8ce1bb85");
                removeObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Is that you again? What do you want?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_46ea9e29"))
        {
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_83c7eb4e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19cc687c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d22d9b89");
                    }
                    setObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hey! Is anyone out there? Who's that!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_19cc687c"))
        {
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
            {
                quest_hero_of_tatooine_box_action_Distract(player, self);
                string_id message = new string_id(c_stringFile, "s_e601f570");
                removeObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Who sent you? That wife?! She's no good. She tricked us and is starving us in here! C'mon. Help us out of here. We can't do it alone. Our means of escape will cause too much of a ruckuss and she'll just catch us again! Go distract her and we'll do our thing.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_d22d9b89"))
        {
            if (quest_hero_of_tatooine_box_condition__defaultCondition(player, self))
            {
                quest_hero_of_tatooine_box_action_Distract(player, self);
                string_id message = new string_id(c_stringFile, "s_68f8b129");
                removeObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Who sent you? That wife?! She's no good. She tricked us and is starving us in here! C'mon. Help us out of here. We can't do it alone. Our means of escape will cause too much of a ruckuss and she'll just catch us again! Go distract her and we'll do our thing.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.quest_hero_of_tatooine_box.branchId");
        return SCRIPT_CONTINUE;
    }
}
