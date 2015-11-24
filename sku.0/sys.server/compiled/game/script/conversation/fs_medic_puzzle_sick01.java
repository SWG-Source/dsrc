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
import script.library.quests;
import script.library.utils;

public class fs_medic_puzzle_sick01 extends script.base_script
{
    public fs_medic_puzzle_sick01()
    {
    }
    public static String c_stringFile = "conversation/fs_medic_puzzle_sick01";
    public boolean fs_medic_puzzle_sick01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean fs_medic_puzzle_sick01_condition_onHealingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.isActive("fs_medic_puzzle_quest_01", player) || quests.isActive("fs_medic_puzzle_quest_02", player) || quests.isActive("fs_medic_puzzle_quest_03", player))
        {
            result = true;
        }
        if (quests.isActive("fs_combat_healing_1", player) || quests.isActive("fs_combat_healing_2", player))
        {
            result = true;
        }
        return result;
    }
    public boolean fs_medic_puzzle_sick01_condition_hasSymptoms(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + npc))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    public boolean fs_medic_puzzle_sick01_condition_onCombatHealingQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        boolean result = false;
        if (quests.isActive("fs_combat_healing_1", player) || quests.isActive("fs_combat_healing_2", player))
        {
            result = true;
        }
        return result;
    }
    public void fs_medic_puzzle_sick01_action_giveSymptoms(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("fs_quest", "Medic Puzzle -- player is invalid.");
        }
        int numberOfSymptoms = rand(6, MAX_NUM_OF_SYMPTOMS - 3);
        boolean[] symptomList = new boolean[MAX_NUM_OF_SYMPTOMS];
        Vector symptomListVector = new Vector();
        for (int j = 0; j < 12; j++)
        {
            symptomListVector.add(new Integer(j));
        }
        while (numberOfSymptoms > 0)
        {
            int index = rand(0, symptomListVector.size() - 1);
            Integer symptom = (Integer)symptomListVector.get(index);
            symptomList[symptom.intValue()] = true;
            symptomListVector.remove(index);
            numberOfSymptoms--;
        }
        for (int i = 0; i < numberOfSymptoms; i++)
        {
            int symptom = rand(0, MAX_NUM_OF_SYMPTOMS - 1);
            if (symptomList[symptom] == true)
            {
                i--;
            }
            symptomList[symptom] = true;
        }
        utils.setScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + npc, symptomList);
    }
    public String fs_medic_puzzle_sick01_tokenTO_getSymptoms(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("fs_quest", "Medic Puzzle -- player is invalid.");
            return new String();
        }
        boolean[] symptomList = utils.getBooleanArrayScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + npc);
        int lastSymptom = 0;
        int numberOfSymptoms = 0;
        for (int i = 0; i < symptomList.length; i++)
        {
            if (symptomList[i])
            {
                lastSymptom = i;
                numberOfSymptoms++;
            }
        }
        String symptomString = "";
        if (numberOfSymptoms == 1)
        {
            string_id cure = new string_id("quest/force_sensitive/fs_medic", "symptom" + lastSymptom);
            symptomString = symptomString + localize(cure);
            return symptomString;
        }
        else 
        {
            utils.setScriptVar(player, SCRIPT_VAR + ONE_SYMPTOM_LEFT + npc, false);
        }
        for (int j = 0; j < symptomList.length; j++)
        {
            if (j != lastSymptom && symptomList[j])
            {
                string_id cure = new string_id("quest/force_sensitive/fs_medic", "symptom" + j);
                symptomString = symptomString + localize(cure) + ", ";
            }
            if (j == lastSymptom)
            {
                string_id cure = new string_id("quest/force_sensitive/fs_medic", "symptom" + j);
                symptomString = symptomString + "and " + localize(cure);
            }
        }
        return symptomString;
    }
    public String fs_medic_puzzle_sick01_tokenTO_numberHealed(obj_id player, obj_id npc) throws InterruptedException
    {
        int totalhealed = 0;
        if (hasObjVar(player, "quest.fs_combat_healing.totalhealed"))
        {
            totalhealed = getIntObjVar(player, "quest.fs_combat_healing.totalhealed");
        }
        return " " + totalhealed;
    }
    public static final boolean DEBUGGING = false;
    public static final int MAX_NUM_OF_SYMPTOMS = 12;
    public static final String MEDIC_QUEST_NAME = "fs_medic_puzzle_quest";
    public static final String SCRIPT_VAR = "fs_quest.";
    public static final String SYMPTOM_LIST = "symptom_list";
    public static final String ONE_SYMPTOM_LEFT = "one_symptom_left";
    public static final String CRAFT_VAR = "crafting_components.";
    public static final String CURE_SYMPTOM_ONE = "cureSymptomOne";
    public static final String CURE_SYMPTOM_TWO = "cureSymptomTwo";
    public static final String GIVE_SYMPTOM_ONE = "giveSymptomOne";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.fs_medic_puzzle_sick01");
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
        detachScript(self, "npc.conversation.fs_medic_puzzle_sick01");
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            LOG("fs_quest", "Medic Puzzle -- player is invalid.");
            return SCRIPT_OVERRIDE;
        }
        if (utils.hasScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + self))
        {
            boolean[] symptomList = utils.getBooleanArrayScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + self);
            boolean onLastSymptom = false;
            if (utils.hasScriptVar(player, SCRIPT_VAR + ONE_SYMPTOM_LEFT + self))
            {
                onLastSymptom = utils.getBooleanScriptVar(player, SCRIPT_VAR + ONE_SYMPTOM_LEFT + self);
            }
            if (utils.hasObjVar(item, CRAFT_VAR + CURE_SYMPTOM_ONE))
            {
                if (DEBUGGING)
                {
                    sendSystemMessageTestingOnly(player, "Changing Symptoms yo");
                }
                int cureSymptomOne = (int)utils.getFloatObjVar(item, CRAFT_VAR + CURE_SYMPTOM_ONE);
                int cureSymptomTwo = (int)utils.getFloatObjVar(item, CRAFT_VAR + CURE_SYMPTOM_TWO);
                int giveSymptomOne = (int)utils.getFloatObjVar(item, CRAFT_VAR + GIVE_SYMPTOM_ONE);
                symptomList[giveSymptomOne] = true;
                symptomList[cureSymptomOne] = false;
                symptomList[cureSymptomTwo] = false;
                utils.setScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + self, symptomList);
                destroyObject(item);
                string_id adminMeds = new string_id("quest/force_sensitive/fs_medic", "admin_meds");
                chat.chat(self, adminMeds);
                int lastSymptom = 0;
                int numberOfSymptoms = 0;
                for (int i = 0; i < symptomList.length; i++)
                {
                    if (symptomList[i])
                    {
                        lastSymptom = i;
                        numberOfSymptoms++;
                    }
                }
                if (numberOfSymptoms == 0)
                {
                    string_id npcCured = new string_id("quest/force_sensitive/fs_medic", "npc_cured");
                    chat.chat(self, npcCured);
                    utils.removeScriptVar(player, SCRIPT_VAR + ONE_SYMPTOM_LEFT + self);
                    utils.removeScriptVar(player, SCRIPT_VAR + SYMPTOM_LIST + self);
                    destroyObject(item);
                    if (utils.hasObjVar(player, "fs.numberHealed"))
                    {
                        int peopleHealed = utils.getIntObjVar(player, "fs.numberHealed");
                        peopleHealed++;
                        utils.setObjVar(player, "fs.numberHealed", peopleHealed);
                    }
                    else 
                    {
                        utils.setObjVar(player, "fs.numberHealed", 1);
                    }
                    return SCRIPT_CONTINUE;
                }
            }
        }
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
        if (!fs_medic_puzzle_sick01_condition_onHealingQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_bf4eec78");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId", 1);
                npcStartConversation(player, self, "fs_medic_puzzle_sick01", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_sick01_condition_onCombatHealingQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_fa8f35d7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId", 3);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_sick01_tokenTO_numberHealed(player, self));
                npcStartConversation(player, self, "fs_medic_puzzle_sick01", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_sick01_tokenTO_numberHealed(player, self));
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_sick01_condition_hasSymptoms(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_3904c58");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_67e6df55");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId", 5);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_sick01_tokenTO_getSymptoms(player, self));
                npcStartConversation(player, self, "fs_medic_puzzle_sick01", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_sick01_tokenTO_getSymptoms(player, self));
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_95bc25f3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_12ec68a8");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a4d284b1");
                }
                setObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId", 7);
                npcStartConversation(player, self, "fs_medic_puzzle_sick01", message, responses);
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
        if (!conversationId.equals("fs_medic_puzzle_sick01"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
        if (branchId == 1 && response.equals("s_67e6df55"))
        {
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_95a8553f");
                removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I am sorry, but only authorized medical personnel have access to my functions.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_67e6df55"))
        {
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b9b27823");
                removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Accessing Medical Records...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_67e6df55"))
        {
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b9b27823");
                removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'This patient is currently suffering from %TO. If you have any treatments you would like to administer to him, please give the treatment to me so I can administer them.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_12ec68a8"))
        {
            fs_medic_puzzle_sick01_action_giveSymptoms(player, self);
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3904c58");
                removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(fs_medic_puzzle_sick01_tokenTO_getSymptoms(player, self));
                npcSpeak(player, pp);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings, I am the 2-1B Medical droid in charge of this patient. How may I assist you?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_a4d284b1"))
        {
            if (fs_medic_puzzle_sick01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1d567ce1");
                removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetings, I am the 2-1B Medical droid in charge of this patient. How may I assist you?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.fs_medic_puzzle_sick01.branchId");
        return SCRIPT_CONTINUE;
    }
}
