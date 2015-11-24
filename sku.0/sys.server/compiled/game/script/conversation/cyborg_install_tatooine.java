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
import script.library.conversation;
import script.library.cybernetic;
import script.library.sui;
import script.library.utils;

public class cyborg_install_tatooine extends script.base_script
{
    public cyborg_install_tatooine()
    {
    }
    public static String c_stringFile = "conversation/cyborg_install_tatooine";
    public boolean cyborg_install_tatooine_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean cyborg_install_tatooine_condition_tooManyInstalled(obj_id player, obj_id npc) throws InterruptedException
    {
        return cybernetic.hasMaxInstalled(player);
    }
    public boolean cyborg_install_tatooine_condition_hasCyberneticOnHand(obj_id player, obj_id npc) throws InterruptedException
    {
        return cybernetic.hasCyberneticItemInInventory(player);
    }
    public boolean cyborg_install_tatooine_condition_hasCyberneticInstalled(obj_id player, obj_id npc) throws InterruptedException
    {
        return cybernetic.hasCyberneticItem(player);
    }
    public boolean cyborg_install_tatooine_condition_canGetRepairs(obj_id player, obj_id npc) throws InterruptedException
    {
        return cybernetic.hasCyberneticsToRepair(player);
    }
    public void cyborg_install_tatooine_action_listCyberneticsToInstall(obj_id player, obj_id npc) throws InterruptedException
    {
        cybernetic.installCybernetics(player, npc);
    }
    public int handleInstallCybernetics(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx == -1 || bp == -1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objLootToSell = utils.getObjIdArrayScriptVar(objPlayer, "cyberneticToInstall");
        if (objLootToSell == null || objLootToSell.length <= 0)
        {
            LOG("space", "No loot");
            return SCRIPT_CONTINUE;
        }
        if (idx > objLootToSell.length - 1)
        {
            LOG("space", "really bad index");
            return SCRIPT_CONTINUE;
        }
        obj_id objItem = objLootToSell[idx];
        if (isIdValid(objItem))
        {
            cybernetic.installCyberneticItem(objPlayer, self, objItem);
        }
        return SCRIPT_CONTINUE;
    }
    public void cyborg_install_tatooine_action_listCyberneticsToRemove(obj_id player, obj_id npc) throws InterruptedException
    {
        cybernetic.unInstallCybernetics(player, npc);
    }
    public int handleUnInstallCybernetics(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx == -1 || bp == -1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objLootToSell = utils.getObjIdArrayScriptVar(objPlayer, "cyberneticToRemove");
        if (objLootToSell == null || objLootToSell.length <= 0)
        {
            LOG("space", "No loot");
            return SCRIPT_CONTINUE;
        }
        if (idx > objLootToSell.length - 1)
        {
            LOG("space", "really bad index");
            return SCRIPT_CONTINUE;
        }
        obj_id objItem = objLootToSell[idx];
        if (isIdValid(objItem))
        {
            cybernetic.unInstallCyberneticItem(objPlayer, self, objItem);
        }
        return SCRIPT_CONTINUE;
    }
    public void cyborg_install_tatooine_action_listCyberneticsToRepair(obj_id player, obj_id npc) throws InterruptedException
    {
        cybernetic.repairCybernetics(player, npc);
    }
    public int OnCyberneticChangeRequest(obj_id self, obj_id player, int changeType, obj_id cyberneticPiece) throws InterruptedException
    {
        if (isIdValid(player) && isIdValid(cyberneticPiece))
        {
            if (changeType == CYBERNETICS_UI_CHANGETYPE_INSTALL)
            {
                cybernetic.installCyberneticItem(player, self, cyberneticPiece);
            }
            else if (changeType == CYBERNETICS_UI_CHANGETYPE_UNINSTALL)
            {
                cybernetic.unInstallCyberneticItem(player, self, cyberneticPiece);
            }
            else if (changeType == CYBERNETICS_UI_CHANGETYPE_REPAIR)
            {
                cybernetic.repairCyberneticItem(player, self, cyberneticPiece);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepairCybernetics(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id objPlayer = sui.getPlayerId(params);
        if (!isIdValid(objPlayer))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        int bp = sui.getIntButtonPressed(params);
        if (idx == -1 || bp == -1)
        {
            return SCRIPT_CONTINUE;
        }
        else if (bp != sui.BP_OK)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] objLootToSell = utils.getObjIdArrayScriptVar(objPlayer, "cyberneticToRepair");
        if (objLootToSell == null || objLootToSell.length <= 0)
        {
            LOG("space", "No loot");
            return SCRIPT_CONTINUE;
        }
        if (idx > objLootToSell.length - 1)
        {
            LOG("space", "really bad index");
            return SCRIPT_CONTINUE;
        }
        obj_id objItem = objLootToSell[idx];
        if (isIdValid(objItem))
        {
            cybernetic.repairCyberneticItem(objPlayer, self, objItem);
        }
        return SCRIPT_CONTINUE;
    }
    public void cyborg_install_tatooine_action_trackAppearanceTabCyb(obj_id player, obj_id npc) throws InterruptedException
    {
        LOG("sissynoid", "Entered: trackAppearanceTabCyb - Setting Appearance ScriptVar");
        utils.setScriptVar(player, "cybernetic.appearance_install", 1);
    }
    public void cyborg_install_tatooine_action_resetAppearanceTabTracker(obj_id player, obj_id npc) throws InterruptedException
    {
        LOG("sissynoid", "Entered: resetAppearanceTabTracker");
        if (utils.hasScriptVar(player, "cybernetic.appearance_install"))
        {
            LOG("sissynoid", "Removing: resetAppearanceTabTracker ScriptVar!");
            utils.removeScriptVar(player, "cybernetic.appearance_install");
        }
    }
    public int cyborg_install_tatooine_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_41"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_43");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_51"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_26");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_54"))
        {
            cyborg_install_tatooine_action_trackAppearanceTabCyb(player, npc);
            if (!cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToInstall(player, npc);
                string_id message = new string_id(c_stringFile, "s_55");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_57");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_58"))
        {
            cyborg_install_tatooine_action_listCyberneticsToRemove(player, npc);
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToRemove(player, npc);
                string_id message = new string_id(c_stringFile, "s_60");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToRepair(player, npc);
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_43"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_46");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_47"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_48");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_49"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_50");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_62"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                cyborg_install_tatooine_action_resetAppearanceTabTracker(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (cyborg_install_tatooine_condition_hasCyberneticOnHand(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (cyborg_install_tatooine_condition_hasCyberneticOnHand(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (cyborg_install_tatooine_condition_hasCyberneticInstalled(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (cyborg_install_tatooine_condition_canGetRepairs(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 1);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_27"))
        {
            if (!cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToInstall(player, npc);
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_29");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_31");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_32");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_35");
                    }
                    utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int cyborg_install_tatooine_handleBranch10(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_32"))
        {
            cyborg_install_tatooine_action_trackAppearanceTabCyb(player, npc);
            if (!cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToInstall(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_35"))
        {
            if (!cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                cyborg_install_tatooine_action_listCyberneticsToInstall(player, npc);
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (cyborg_install_tatooine_condition_tooManyInstalled(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.cyborg_install_tatooine");
        }
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        stop(self);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setInvulnerable(self, true);
        setCondition(self, CONDITION_CONVERSABLE);
        stop(self);
        setName(self, "");
        setName(self, new string_id("ep3/cybernetic", "corellia_npc"));
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
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
        detachScript(self, "conversation.cyborg_install_tatooine");
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
        if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
        {
            cyborg_install_tatooine_action_resetAppearanceTabTracker(player, npc);
            string_id message = new string_id(c_stringFile, "s_40");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (cyborg_install_tatooine_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (cyborg_install_tatooine_condition_hasCyberneticOnHand(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (cyborg_install_tatooine_condition_hasCyberneticOnHand(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (cyborg_install_tatooine_condition_hasCyberneticInstalled(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (cyborg_install_tatooine_condition_canGetRepairs(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse4 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_41");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_51");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_54");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.cyborg_install_tatooine.branchId", 1);
                npcStartConversation(player, npc, "cyborg_install_tatooine", message, responses);
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
        if (!conversationId.equals("cyborg_install_tatooine"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
        if (branchId == 1 && cyborg_install_tatooine_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && cyborg_install_tatooine_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && cyborg_install_tatooine_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && cyborg_install_tatooine_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && cyborg_install_tatooine_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && cyborg_install_tatooine_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && cyborg_install_tatooine_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && cyborg_install_tatooine_handleBranch10(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.cyborg_install_tatooine.branchId");
        return SCRIPT_CONTINUE;
    }
}
