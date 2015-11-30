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

public class lifeday04b extends script.base_script
{
    public lifeday04b()
    {
    }
    public static String c_stringFile = "conversation/lifeday04b";
    public boolean lifeday04b_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lifeday04b_condition_hasNotStarted(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!hasObjVar(player, "lifeday04.convTracker") && !hasObjVar(player, "lifeday04.rewarded"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04b_condition_hasBeenRewarded(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "lifeday04.rewarded"))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04b_condition_hasTalkedToCityGuy(obj_id player, obj_id npc) throws InterruptedException
    {
        int convTracker = getIntObjVar(player, "lifeday04.convTracker");
        if (convTracker >= 0 && convTracker < 15)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04b_condition_spokeWithEveryone(obj_id player, obj_id npc) throws InterruptedException
    {
        int convTracker = getIntObjVar(player, "lifeday04.convTracker");
        if (convTracker == 15)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04b_condition_isOldEnoughNotaWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        int timeData = getPlayerBirthDate(player);
        int rightNow = getCurrentBirthDate();
        int delta = rightNow - timeData;
        int species = getSpecies(player);
        if (species != SPECIES_WOOKIEE)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean lifeday04b_condition_isOldEnoughIsaWookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        int timeData = getPlayerBirthDate(player);
        int rightNow = getCurrentBirthDate();
        int delta = rightNow - timeData;
        int species = getSpecies(player);
        if (species == SPECIES_WOOKIEE)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public void lifeday04b_action_giveRandomGift(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        String[] reward = 
        {
            "object/tangible/loot/quest/lifeday_orb.iff",
            "object/tangible/painting/painting_wookiee_m.iff",
            "object/tangible/painting/painting_wookiee_f.iff",
            "object/tangible/painting/painting_trees_s01.iff"
        };
        int roll = rand(0, 3);
        obj_id createdObject = createObject(reward[roll], playerInventory, "");
        if (isIdValid(createdObject))
        {
            setObjVar(player, "lifeday04.rewarded", 1);
            removeObjVar(player, "lifeday04.convTracker");
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public void lifeday04b_action_giveLifeDayRobe(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id playerInventory = utils.getInventoryContainer(player);
        obj_id createdObject = createObject("object/tangible/wearables/wookiee/wke_lifeday_robe.iff", playerInventory, "");
        if (isIdValid(createdObject))
        {
            setObjVar(player, "lifeday04.rewarded", 1);
            removeObjVar(player, "lifeday04.convTracker");
        }
        else 
        {
            sendSystemMessage(player, new string_id("quest/lifeday/lifeday", "full_inv"));
        }
    }
    public void lifeday04b_action_setSpokeWithElder(obj_id player, obj_id npc) throws InterruptedException
    {
        int convTracker = getIntObjVar(player, "lifeday04.convTracker");
        if (convTracker == 0)
        {
            setObjVar(player, "lifeday04.convTracker", 1);
        }
    }
    public void lifeday04b_action_notOldEnoughBoned(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "lifeday04.rewarded", 1);
        removeObjVar(player, "lifeday04.convTracker");
    }
    public int lifeday04b_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_8a712dbc"))
        {
            lifeday04b_action_setSpokeWithElder(player, npc);
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_d8cde9a");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_6cd2786a"))
        {
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_feeecf48");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04b_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_ebbd31aa"))
        {
            if (lifeday04b_condition_isOldEnoughNotaWookiee(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_af9f8883");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday04b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_afabf3ac");
                    }
                    utils.setScriptVar(player, "conversation.lifeday04b.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (lifeday04b_condition_isOldEnoughIsaWookiee(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_f0f674e2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday04b_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday04b_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_352585ec");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52645cdc");
                    }
                    utils.setScriptVar(player, "conversation.lifeday04b.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                lifeday04b_action_notOldEnoughBoned(player, npc);
                string_id message = new string_id(c_stringFile, "s_b85028af");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04b_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_afabf3ac"))
        {
            lifeday04b_action_giveRandomGift(player, npc);
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_66b5743f");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday04b_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_352585ec"))
        {
            lifeday04b_action_giveLifeDayRobe(player, npc);
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de3b1306");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52645cdc"))
        {
            lifeday04b_action_giveRandomGift(player, npc);
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_de3b1306");
                utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.lifeday04b");
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
        detachScript(self, "conversation.lifeday04b");
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
        if (lifeday04b_condition_hasNotStarted(player, npc))
        {
            doAnimationAction(npc, "refuse_offer_affection");
            string_id message = new string_id(c_stringFile, "s_5bd07d67");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lifeday04b_condition_hasBeenRewarded(player, npc))
        {
            doAnimationAction(npc, "bow2");
            doAnimationAction(player, "nod_head_once");
            string_id message = new string_id(c_stringFile, "s_fe83540d");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lifeday04b_condition_hasTalkedToCityGuy(player, npc))
        {
            doAnimationAction(npc, "beckon");
            string_id message = new string_id(c_stringFile, "s_f0c65663");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday04b_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday04b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8a712dbc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6cd2786a");
                }
                utils.setScriptVar(player, "conversation.lifeday04b.branchId", 3);
                npcStartConversation(player, npc, "lifeday04b", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday04b_condition_spokeWithEveryone(player, npc))
        {
            doAnimationAction(npc, "explain");
            string_id message = new string_id(c_stringFile, "s_b874dc09");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday04b_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ebbd31aa");
                }
                utils.setScriptVar(player, "conversation.lifeday04b.branchId", 6);
                npcStartConversation(player, npc, "lifeday04b", message, responses);
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
        if (!conversationId.equals("lifeday04b"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lifeday04b.branchId");
        if (branchId == 3 && lifeday04b_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && lifeday04b_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lifeday04b_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && lifeday04b_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lifeday04b.branchId");
        return SCRIPT_CONTINUE;
    }
}
