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
import script.library.groundquests;
import script.library.utils;

public class corellia_coronet_meatlump_distress extends script.base_script
{
    public corellia_coronet_meatlump_distress()
    {
    }
    public static String c_stringFile = "conversation/corellia_coronet_meatlump_distress";
    public boolean corellia_coronet_meatlump_distress_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean corellia_coronet_meatlump_distress_condition_meatlumpLieutenant(obj_id player, obj_id npc) throws InterruptedException
    {
        if (groundquests.isTaskActive(player, "corellia_coronet_meatlump_act1_part3", "meatlumpsAct1_meatlumpLieutenant"))
        {
            int questNum = groundquests.getQuestIdFromString("corellia_coronet_meatlump_act1_part3");
            int taskNum = groundquests.getTaskId(questNum, "meatlumpsAct1_meatlumpLieutenant");
            String questBaseObjVar = groundquests.getBaseObjVar(player, "encounter", "quest/corellia_coronet_meatlump_act1_part3", taskNum);
            String spawnListObjVarName = questBaseObjVar + ".spawnList";
            if (hasObjVar(player, spawnListObjVarName))
            {
                obj_id[] spawnList = getObjIdArrayObjVar(player, spawnListObjVarName);
                if (spawnList != null)
                {
                    for (int k = 0; k < spawnList.length; ++k)
                    {
                        obj_id spawn = spawnList[k];
                        if (spawn == npc)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean corellia_coronet_meatlump_distress_condition_gotPassword(obj_id player, obj_id npc) throws InterruptedException
    {
        return groundquests.isTaskActive(player, "corellia_coronet_meatlump_act1_end", "meatlumpsAct1_gotPassword");
    }
    public boolean corellia_coronet_meatlump_distress_condition_code1(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 1;
    }
    public boolean corellia_coronet_meatlump_distress_condition_code2(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 2;
    }
    public boolean corellia_coronet_meatlump_distress_condition_code3(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 3;
    }
    public boolean corellia_coronet_meatlump_distress_condition_code4(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 4;
    }
    public boolean corellia_coronet_meatlump_distress_condition_code5(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 5;
    }
    public boolean corellia_coronet_meatlump_distress_condition_code6(obj_id player, obj_id npc) throws InterruptedException
    {
        return utils.getIntScriptVar(player, "meatlumpAct1.codeNumber") == 6;
    }
    public void corellia_coronet_meatlump_distress_action_pickCorrectCode(obj_id player, obj_id npc) throws InterruptedException
    {
        int species = getSpecies(player);
        int codeNum = rand(1, 6);
        switch (species)
        {
            case SPECIES_HUMAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 1;
                break;
                case 3:
                case 4:
                codeNum = 2;
                break;
                case 5:
                case 6:
                codeNum = 3;
                break;
            }
            break;
            case SPECIES_RODIAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 4;
                break;
                case 3:
                case 4:
                codeNum = 5;
                break;
                case 5:
                case 6:
                codeNum = 6;
                break;
            }
            break;
            case SPECIES_TRANDOSHAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 2;
                break;
                case 3:
                case 4:
                codeNum = 4;
                break;
                case 5:
                case 6:
                codeNum = 6;
                break;
            }
            break;
            case SPECIES_MON_CALAMARI:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 1;
                break;
                case 3:
                case 4:
                codeNum = 3;
                break;
                case 5:
                case 6:
                codeNum = 5;
                break;
            }
            break;
            case SPECIES_WOOKIEE:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 1;
                break;
                case 3:
                case 4:
                codeNum = 2;
                break;
                case 5:
                case 6:
                codeNum = 4;
                break;
            }
            break;
            case SPECIES_BOTHAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 3;
                break;
                case 3:
                case 4:
                codeNum = 5;
                break;
                case 5:
                case 6:
                codeNum = 6;
                break;
            }
            break;
            case SPECIES_TWILEK:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 1;
                break;
                case 3:
                case 4:
                codeNum = 4;
                break;
                case 5:
                case 6:
                codeNum = 5;
                break;
            }
            break;
            case SPECIES_ZABRAK:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 2;
                break;
                case 3:
                case 4:
                codeNum = 3;
                break;
                case 5:
                case 6:
                codeNum = 6;
                break;
            }
            break;
            case SPECIES_ITHORIAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 1;
                break;
                case 3:
                case 4:
                codeNum = 5;
                break;
                case 5:
                case 6:
                codeNum = 6;
                break;
            }
            break;
            case SPECIES_SULLUSTAN:
            switch (codeNum)
            {
                case 1:
                case 2:
                codeNum = 2;
                break;
                case 3:
                case 4:
                codeNum = 3;
                break;
                case 5:
                case 6:
                codeNum = 4;
                break;
            }
            break;
            default:
            break;
        }
        utils.setScriptVar(player, "meatlumpAct1.codeNumber", codeNum);
    }
    public void corellia_coronet_meatlump_distress_action_attack(obj_id player, obj_id npc) throws InterruptedException
    {
        setInvulnerable(npc, false);
        startCombat(npc, player);
        clearCondition(npc, CONDITION_CONVERSABLE);
        detachScript(npc, "conversation.corellia_coronet_meatlump_distress");
    }
    public void corellia_coronet_meatlump_distress_action_givePassword(obj_id player, obj_id npc) throws InterruptedException
    {
        groundquests.sendSignal(player, "meatlumpsAct1_spokenPassword");
    }
    public int corellia_coronet_meatlump_distress_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_13"))
        {
            if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                boolean hasResponse4 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse4 = true;
                }
                boolean hasResponse5 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse5 = true;
                }
                boolean hasResponse6 = false;
                if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse6 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_19");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_21");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_23");
                    }
                    if (hasResponse4)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_27");
                    }
                    if (hasResponse5)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_36");
                    }
                    if (hasResponse6)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_48");
                    }
                    utils.setScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                    chat.chat(npc, player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int corellia_coronet_meatlump_distress_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17"))
        {
            if (corellia_coronet_meatlump_distress_condition_code1(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code1(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_37");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_19"))
        {
            if (corellia_coronet_meatlump_distress_condition_code2(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_31");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code2(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_38");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_21"))
        {
            if (corellia_coronet_meatlump_distress_condition_code3(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_32");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code3(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_39");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_23"))
        {
            if (corellia_coronet_meatlump_distress_condition_code4(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_33");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code4(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_40");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_27"))
        {
            if (corellia_coronet_meatlump_distress_condition_code5(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_34");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code5(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_41");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_36"))
        {
            if (corellia_coronet_meatlump_distress_condition_code6(player, npc))
            {
                corellia_coronet_meatlump_distress_action_givePassword(player, npc);
                string_id message = new string_id(c_stringFile, "s_43");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            if (!corellia_coronet_meatlump_distress_condition_code6(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_46");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
                chat.chat(npc, player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_48"))
        {
            if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
            {
                corellia_coronet_meatlump_distress_action_attack(player, npc);
                string_id message = new string_id(c_stringFile, "s_50");
                utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
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
            detachScript(self, "conversation.corellia_coronet_meatlump_distress");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        if (!ai_lib.isInCombat(self))
        {
            stop(self);
            int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
            menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
            menuInfoData.setServerNotify(false);
            setCondition(self, CONDITION_CONVERSABLE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.corellia_coronet_meatlump_distress");
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
        if (corellia_coronet_meatlump_distress_condition_gotPassword(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_45");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_meatlump_distress_condition_meatlumpLieutenant(player, npc))
        {
            corellia_coronet_meatlump_distress_action_pickCorrectCode(player, npc);
            string_id message = new string_id(c_stringFile, "s_12");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_13");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                utils.setScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId", 2);
                npcStartConversation(player, npc, "corellia_coronet_meatlump_distress", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (corellia_coronet_meatlump_distress_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_56");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("corellia_coronet_meatlump_distress"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
        if (branchId == 2 && corellia_coronet_meatlump_distress_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && corellia_coronet_meatlump_distress_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.corellia_coronet_meatlump_distress.branchId");
        return SCRIPT_CONTINUE;
    }
}
