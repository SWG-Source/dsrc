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
import script.library.utils;

public class hutt_informant extends script.base_script
{
    public hutt_informant()
    {
    }
    public static String c_stringFile = "conversation/hutt_informant";
    public boolean hutt_informant_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean hutt_informant_condition_HasItem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "bestine.mess"))
        {
            if (utils.playerHasItemByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff"))
            {
                return true;
            }
        }
        return false;
    }
    public void hutt_informant_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void hutt_informant_action_destroyDisk(obj_id player, obj_id npc) throws InterruptedException
    {
        if (isIdValid(player))
        {
            obj_id disk = utils.getItemPlayerHasByTemplate(player, "object/tangible/loot/quest/sean_history_disk.iff");
            if (isIdValid(disk))
            {
                destroyObject(disk);
                obj_id playerInv = getObjectInSlot(player, "inventory");
                if (isIdValid(playerInv))
                {
                    obj_id item = createObject("object/weapon/ranged/grenade/grenade_cryoban_loot_medium.iff", playerInv, "");
                    if (isIdValid(item))
                    {
                        setObjVar(player, "bestine.hutt", true);
                        removeObjVar(player, "bestine.mess");
                        if (hasObjVar(player, "bestine.contactWaypoint"))
                        {
                            obj_id waypoint = getObjIdObjVar(player, "bestine.contactWaypoint");
                            if (isIdValid(waypoint))
                            {
                                destroyWaypointInDatapad(waypoint, player);
                            }
                            removeObjVar(player, "bestine.contactWaypoint");
                        }
                    }
                }
            }
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.hutt_informant");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        if (!hasScript(self, "city.bestine.hutt_informant_approach"))
        {
            attachScript(self, "city.bestine.hutt_informant_approach");
        }
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
        detachScript(self, "npc.conversation.hutt_informant");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (hutt_informant_condition_HasItem(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_fb7697b2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (hutt_informant_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f660c800");
                }
                setObjVar(player, "conversation.hutt_informant.branchId", 1);
                npcStartConversation(player, self, "hutt_informant", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (hutt_informant_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_7b45b993");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("hutt_informant"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.hutt_informant.branchId");
        if (branchId == 1 && response.equals("s_f660c800"))
        {
            if (hutt_informant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_dfffcfb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (hutt_informant_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (hutt_informant_condition_HasItem(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (hutt_informant_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_139f41b9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54b54bd0");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4ddc07ed");
                    }
                    setObjVar(player, "conversation.hutt_informant.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.hutt_informant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I can take care of this... mess for you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_139f41b9"))
        {
            if (hutt_informant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_70142360");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (hutt_informant_condition_HasItem(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54b54bd0");
                    }
                    setObjVar(player, "conversation.hutt_informant.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.hutt_informant.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why do the dirty work of a politician when I can do it for you? Let me take that disk off your hands. In fact, I know I may have something with which I can pay you for your... generosity.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_54b54bd0"))
        {
            if (hutt_informant_condition__defaultCondition(player, self))
            {
                hutt_informant_action_destroyDisk(player, self);
                string_id message = new string_id(c_stringFile, "s_53f349b7");
                removeObjVar(player, "conversation.hutt_informant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why do the dirty work of a politician when I can do it for you? Let me take that disk off your hands. In fact, I know I may have something with which I can pay you for your... generosity.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_4ddc07ed"))
        {
            if (hutt_informant_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_eed9dce1");
                removeObjVar(player, "conversation.hutt_informant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why do the dirty work of a politician when I can do it for you? Let me take that disk off your hands. In fact, I know I may have something with which I can pay you for your... generosity.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_54b54bd0"))
        {
            if (hutt_informant_condition__defaultCondition(player, self))
            {
                hutt_informant_action_destroyDisk(player, self);
                string_id message = new string_id(c_stringFile, "s_53f349b7");
                removeObjVar(player, "conversation.hutt_informant.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why wouldn't I? It contains pertinent information regarding a highly-regarded politician and I'm one of Jabba's informants. Again, I ask... why wouldn't I want it?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.hutt_informant.branchId");
        return SCRIPT_CONTINUE;
    }
}
