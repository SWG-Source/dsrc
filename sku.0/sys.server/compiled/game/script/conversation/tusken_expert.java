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
import script.library.factions;
import script.library.groundquests;
import script.library.utils;

public class tusken_expert extends script.base_script
{
    public tusken_expert()
    {
    }
    public static String c_stringFile = "conversation/tusken_expert";
    public boolean tusken_expert_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean tusken_expert_condition_starportNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_starport_crew");
    }
    public boolean tusken_expert_condition_wattoNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_watto_crew");
    }
    public boolean tusken_expert_condition_medcenterNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_medcenter_crew");
    }
    public boolean tusken_expert_condition_universityNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_university_crew");
    }
    public boolean tusken_expert_condition_clonecenterNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_cloning_crew");
    }
    public boolean tusken_expert_condition_combathallNeedsCrew(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "heroic_tusken_tracking_02", "heroic_tusken_combat_crew");
    }
    public boolean tusken_expert_condition_allBuildingsHaveCrews(obj_id player, obj_id npc) throws InterruptedException
    {
        return (groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_starport_crew") && groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_watto_crew") && groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_medcenter_crew") && groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_university_crew") && groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_cloning_crew") && groundquests.hasCompletedTask(player, "heroic_tusken_tracking_02", "heroic_tusken_combat_crew"));
    }
    public void tusken_expert_action_goToStarport(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toStarport");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToUniversity(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toUniversity");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToMedicalCenter(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toMedical");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToCommerce(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toCommerce");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToCloning(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toCloning");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToCombat(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toCombat");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public void tusken_expert_action_goToWatto(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary dict = new dictionary();
        dict.put("pathAction", "patrol_path");
        dict.put("pathName", "toWatto");
        messageTo(npc, "doPathAction", dict, 0.0f, false);
    }
    public int tusken_expert_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_22"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToCloning(player, npc);
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_14"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToMedicalCenter(player, npc);
                string_id message = new string_id(c_stringFile, "s_16");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_11"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToUniversity(player, npc);
                string_id message = new string_id(c_stringFile, "s_13");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToCombat(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToWatto(player, npc);
                string_id message = new string_id(c_stringFile, "s_21");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_25"))
        {
            if (tusken_expert_condition__defaultCondition(player, npc))
            {
                tusken_expert_action_goToStarport(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.tusken_expert");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setTriggerVolume(self);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (volumeName.equals("tusken_citizen"))
        {
            if ((factions.getFaction(breacher)).equals("heroic_tusken"))
            {
                addHate(breacher, self, 1000.0f);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void setTriggerVolume(obj_id self) throws InterruptedException
    {
        createTriggerVolume("tusken_citizen", 6.0f, true);
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
        detachScript(self, "conversation.tusken_expert");
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
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (tusken_expert_condition_allBuildingsHaveCrews(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_29");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (tusken_expert_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_5");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (tusken_expert_condition_clonecenterNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (tusken_expert_condition_medcenterNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (tusken_expert_condition_universityNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (tusken_expert_condition_combathallNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (tusken_expert_condition_wattoNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            boolean hasResponse5 = false;
            if (tusken_expert_condition_starportNeedsCrew(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse5 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_11");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                }
                if (hasResponse5)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_25");
                }
                utils.setScriptVar(player, "conversation.tusken_expert.branchId", 2);
                npcStartConversation(player, npc, "tusken_expert", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("tusken_expert"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.tusken_expert.branchId");
        if (branchId == 2 && tusken_expert_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.tusken_expert.branchId");
        return SCRIPT_CONTINUE;
    }
}
