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
import script.library.static_item;
import script.library.utils;

public class mtp_hideout_recon extends script.base_script
{
    public mtp_hideout_recon()
    {
    }
    public static String c_stringFile = "conversation/mtp_hideout_recon";
    public boolean mtp_hideout_recon_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean mtp_hideout_recon_condition_collectionComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasCompletedCollection(player, "col_meatlump_photo_01"))
        {
            return false;
        }
        if (hasCompletedCollectionSlotPrereq(player, "meatlump_photo_corellia") && !hasCompletedCollection(player, "col_meatlump_photo_01"))
        {
            return false;
        }
        return true;
    }
    public boolean mtp_hideout_recon_condition_collectionStillActive(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasCompletedCollection(player, "col_meatlump_photo_01") && hasCompletedCollectionSlotPrereq(player, "meatlump_photo_corellia"))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_recon_condition_returnNoCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id camera = utils.getStaticItemInInventory(player, "meatlump_camera_02_01");
        if (!isIdValid(camera))
        {
            return true;
        }
        return false;
    }
    public boolean mtp_hideout_recon_condition_planetsNotComplete(obj_id player, obj_id npc) throws InterruptedException
    {
        for (int j = 0; j < PHOTO_PLANETS.length; j++)
        {
            if (!hasCompletedCollectionSlot(player, PHOTO_PLANETS[j]))
            {
                return false;
            }
        }
        return true;
    }
    public void mtp_hideout_recon_action_grantMeatlumpCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id camera = static_item.createNewItemFunction("meatlump_camera_02_01", pInv);
        modifyCollectionSlotValue(player, "meatlump_camera_starter_slot", 1);
        if (!isIdValid(camera) || !exists(camera))
        {
        }
    }
    public void mtp_hideout_recon_action_returnTheCamera(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id camera = utils.getStaticItemInInventory(player, "meatlump_camera_02_01");
        if (!isIdValid(camera))
        {
            return;
        }
        if (!hasCompletedCollectionSlotPrereq(player, "meatlump_photo_corellia"))
        {
            return;
        }
        for (int j = 0; j < PHOTO_PLANETS.length; j++)
        {
            if (!hasCompletedCollectionSlot(player, PHOTO_PLANETS[j]))
            {
                return;
            }
        }
        modifyCollectionSlotValue(player, "meatlump_camera_finish_slot", 1);
        if (hasCompletedCollection(player, "col_meatlump_photo_01"))
        {
            detachScript(camera, "item.special.nodestroy");
            destroyObject(camera);
            CustomerServiceLog("Collection: ", "Player " + getFirstName(player) + "(" + player + ") completed the 'meatlump photo' collection. " + "item 'meatlump_camera_02_01'" + "(" + camera + ") " + "was deleted.  *****This deletion is required to finish the collection!*****");
        }
    }
    public int mtp_hideout_recon_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_6"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_recon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_recon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_10");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_26");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_recon.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_30"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_36");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_38"))
        {
            if (mtp_hideout_recon_condition_planetsNotComplete(player, npc))
            {
                mtp_hideout_recon_action_returnTheCamera(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (mtp_hideout_recon_condition_returnNoCamera(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_42");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (!mtp_hideout_recon_condition_planetsNotComplete(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_44");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_recon_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_10"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_recon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_14");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_recon.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_26"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_28");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_recon_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_14"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_16");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (mtp_hideout_recon_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (mtp_hideout_recon_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_22");
                    }
                    utils.setScriptVar(player, "conversation.mtp_hideout_recon.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int mtp_hideout_recon_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_18"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                mtp_hideout_recon_action_grantMeatlumpCamera(player, npc);
                string_id message = new string_id(c_stringFile, "s_20");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_22"))
        {
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_24");
                utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public static final String[] PHOTO_PLANETS = 
    {
        "meatlump_photo_corellia",
        "meatlump_photo_dantooine",
        "meatlump_photo_dathomir",
        "meatlump_photo_endor",
        "meatlump_photo_lok",
        "meatlump_photo_naboo",
        "meatlump_photo_rori",
        "meatlump_photo_talus",
        "meatlump_photo_tatooine",
        "meatlump_photo_yavin4"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.mtp_hideout_recon");
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
        detachScript(self, "conversation.mtp_hideout_recon");
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
        if (mtp_hideout_recon_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_4");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (mtp_hideout_recon_condition_collectionComplete(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (mtp_hideout_recon_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (mtp_hideout_recon_condition_collectionStillActive(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (mtp_hideout_recon_condition_collectionStillActive(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_30");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                }
                utils.setScriptVar(player, "conversation.mtp_hideout_recon.branchId", 1);
                npcStartConversation(player, npc, "mtp_hideout_recon", message, responses);
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
        if (!conversationId.equals("mtp_hideout_recon"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mtp_hideout_recon.branchId");
        if (branchId == 1 && mtp_hideout_recon_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && mtp_hideout_recon_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && mtp_hideout_recon_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && mtp_hideout_recon_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mtp_hideout_recon.branchId");
        return SCRIPT_CONTINUE;
    }
}
