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
import script.library.features;
import script.library.skill;
import script.library.space_flags;
import script.library.space_quest;
import script.library.space_skill;
import script.library.utils;

public class generic_newbie_pilot_info_jabbas_palace extends script.base_script
{
    public generic_newbie_pilot_info_jabbas_palace()
    {
    }
    public static String c_stringFile = "conversation/generic_newbie_pilot_info_jabbas_palace";
    public boolean generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_remembersPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return (utils.hasScriptVar(player, "metNewbiePilot"));
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_hasAccessDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (pInv == null)
        {
            return false;
        }
        obj_id[] items = getContents(pInv);
        if (items == null)
        {
            return false;
        }
        for (int i = 0; i < items.length; i++)
        {
            String name = getTemplateName(items[i]);
            if (name.equals("object/tangible/space/mission_objects/palace_access_doc.iff"))
            {
                utils.setScriptVar(player, "auth", items[i]);
                return true;
            }
        }
        return false;
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_hasIncorrectDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id auth = utils.getObjIdScriptVar(player, "auth");
        if (!isIdValid(auth))
        {
            return true;
        }
        String sign = getStringObjVar(auth, "signature");
        String name = getName(player);
        if (sign.equals(name))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_isImperialPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_imperial_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_isRebelPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return (hasSkill(player, "pilot_rebel_navy_novice"));
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_hasUnsignedDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id auth = utils.getObjIdScriptVar(player, "auth");
        if (!isIdValid(auth))
        {
            return true;
        }
        String sign = getStringObjVar(auth, "signature");
        if (sign == null)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_notCorrectPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!space_flags.isSpaceTrack(player, space_flags.PRIVATEER_TATOOINE))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_isPrivateerPilot(obj_id player, obj_id npc) throws InterruptedException
    {
        return hasSkill(player, "pilot_neutral_novice");
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_hasSpaceExpansion(obj_id player, obj_id npc) throws InterruptedException
    {
        return (features.isSpaceEdition(player));
    }
    public boolean generic_newbie_pilot_info_jabbas_palace_condition_hasSpaceShip(obj_id player, obj_id npc) throws InterruptedException
    {
        return (space_quest.hasShip(player));
    }
    public void generic_newbie_pilot_info_jabbas_palace_action_rememberPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "metNewbiePilot", true);
    }
    public void generic_newbie_pilot_info_jabbas_palace_action_removeAccessDocuments(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id pInv = utils.getInventoryContainer(player);
        if (pInv == null)
        {
            return;
        }
        obj_id[] items = getContents(pInv);
        if (items == null)
        {
            return;
        }
        for (int i = 0; i < items.length; i++)
        {
            String name = getTemplateName(items[i]);
            if (name.equals("object/tangible/space/mission_objects/palace_access_doc.iff"))
            {
                obj_id transferauth = items[i];
                if (destroyObject(transferauth))
                {
                    setObjVar(player, "space_access_jabba", 30);
                }
            }
        }
        return;
    }
    public void generic_newbie_pilot_info_jabbas_palace_action_grantQuestOne(obj_id player, obj_id npc) throws InterruptedException
    {
        space_quest.grantQuest(player, "delivery", "tatooine_newbie_1");
        space_quest.grantNewbieShip(player, "rebel");
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_daa8544b"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9bd20141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (!generic_newbie_pilot_info_jabbas_palace_condition_hasAccessDocuments(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition_hasAccessDocuments(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c484c74");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f97d453c");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_b8b50032"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_8583811b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d94b37db");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_bc4e70bf"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_29430ed4");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c484c74"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_6fa59967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9e4fd02");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_f97d453c"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition_notCorrectPilot(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_3385ded9");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (generic_newbie_pilot_info_jabbas_palace_condition_hasUnsignedDocuments(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_2b167dbd");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (generic_newbie_pilot_info_jabbas_palace_condition_hasIncorrectDocuments(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                string_id message = new string_id(c_stringFile, "s_f7d7d0fe");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "rub_chin_thoughtful");
                generic_newbie_pilot_info_jabbas_palace_action_removeAccessDocuments(player, npc);
                string_id message = new string_id(c_stringFile, "s_51bcd889");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d06b92dc");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9e4fd02"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_d11dc1bf");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cba77fff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebf8ee6f");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cba77fff"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_6d6fc618");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ebf8ee6f"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8aeca88d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d038a005"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9dd79035");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch14(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d06b92dc"))
        {
            doAnimationAction(player, "refuse_offer_affection");
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smell_air");
                string_id message = new string_id(c_stringFile, "s_6f8371ee");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d94b37db"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "wave_finger_warning");
                string_id message = new string_id(c_stringFile, "s_aefb75dd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_80f90f12");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_80f90f12"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "smack_self");
                string_id message = new string_id(c_stringFile, "s_6b059369");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_daa8544b");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch18(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_daa8544b"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_9bd20141");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c484c74");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch19(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_5c484c74"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "shrug_hands");
                string_id message = new string_id(c_stringFile, "s_6fa59967");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a9e4fd02");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 20);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch20(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a9e4fd02"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "explain");
                string_id message = new string_id(c_stringFile, "s_37388d12");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_cba77fff");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ebf8ee6f");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch21(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_cba77fff"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_6d6fc618");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d038a005");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 22);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_ebf8ee6f"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_8aeca88d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 25);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch22(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_d038a005"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_9dd79035");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2c017871");
                    }
                    utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 23);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch23(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int generic_newbie_pilot_info_jabbas_palace_handleBranch25(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_2c017871"))
        {
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "goodbye");
                string_id message = new string_id(c_stringFile, "s_bc324892");
                utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
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
            detachScript(self, "conversation.generic_newbie_pilot_info_jabbas_palace");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Smuggler Pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Smuggler Pilot");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        faceTo(self, player);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.generic_newbie_pilot_info_jabbas_palace");
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
        if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "greet");
            string_id message = new string_id(c_stringFile, "s_b543ec60");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (generic_newbie_pilot_info_jabbas_palace_condition_hasSpaceShip(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (generic_newbie_pilot_info_jabbas_palace_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_daa8544b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b8b50032");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bc4e70bf");
                }
                utils.setScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId", 1);
                npcStartConversation(player, npc, "generic_newbie_pilot_info_jabbas_palace", message, responses);
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
        if (!conversationId.equals("generic_newbie_pilot_info_jabbas_palace"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
        if (branchId == 1 && generic_newbie_pilot_info_jabbas_palace_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && generic_newbie_pilot_info_jabbas_palace_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && generic_newbie_pilot_info_jabbas_palace_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && generic_newbie_pilot_info_jabbas_palace_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && generic_newbie_pilot_info_jabbas_palace_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && generic_newbie_pilot_info_jabbas_palace_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && generic_newbie_pilot_info_jabbas_palace_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && generic_newbie_pilot_info_jabbas_palace_handleBranch14(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && generic_newbie_pilot_info_jabbas_palace_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && generic_newbie_pilot_info_jabbas_palace_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && generic_newbie_pilot_info_jabbas_palace_handleBranch18(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && generic_newbie_pilot_info_jabbas_palace_handleBranch19(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 20 && generic_newbie_pilot_info_jabbas_palace_handleBranch20(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && generic_newbie_pilot_info_jabbas_palace_handleBranch21(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 22 && generic_newbie_pilot_info_jabbas_palace_handleBranch22(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 23 && generic_newbie_pilot_info_jabbas_palace_handleBranch23(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 25 && generic_newbie_pilot_info_jabbas_palace_handleBranch25(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.generic_newbie_pilot_info_jabbas_palace.branchId");
        return SCRIPT_CONTINUE;
    }
}
