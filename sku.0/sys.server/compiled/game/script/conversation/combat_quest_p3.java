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
import script.library.create;
import script.library.fs_counterstrike;
import script.library.fs_dyn_village;
import script.library.fs_quests;
import script.library.quests;
import script.library.trace;
import script.library.utils;

public class combat_quest_p3 extends script.base_script
{
    public combat_quest_p3()
    {
    }
    public static String c_stringFile = "conversation/combat_quest_p3";
    public boolean combat_quest_p3_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean combat_quest_p3_condition_canTurnInFrequencyDp(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id disks[] = utils.getAllItemsPlayerHasByTemplate(player, "object/tangible/loot/quest/force_sensitive/camp_frequency_datapad.iff");
        if (disks == null)
        {
            return false;
        }
        for (int i = 0; i < disks.length; i++)
        {
            if (fs_counterstrike.getPhaseItemPercentDecay(disks[i], 3) < 100)
            {
                return quests.isActive("fs_cs_intro", player);
            }
        }
        return false;
    }
    public boolean combat_quest_p3_condition_isOnQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_counterstrike.isOnCsQuest(player);
    }
    public boolean combat_quest_p3_condition_canTurnInWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id disks[] = utils.getAllItemsPlayerHasByTemplate(player, "object/tangible/loot/quest/force_sensitive/camp_waypoint_datapad.iff");
        if (disks == null)
        {
            return false;
        }
        for (int i = 0; i < disks.length; i++)
        {
            if (fs_counterstrike.getPhaseItemPercentDecay(disks[i], 3) < 100)
            {
                return quests.isActive("fs_cs_intro", player);
            }
        }
        return false;
    }
    public boolean combat_quest_p3_condition_canTalkToNpc(obj_id player, obj_id npc) throws InterruptedException
    {
        if ((fs_quests.isVillageEligible(player) && (!fs_quests.hasQuestAccepted(player) && !fs_quests.hasQuestCompleted(player))) || fs_counterstrike.isOnCsQuest(player))
        {
            return true;
        }
        return false;
    }
    public boolean combat_quest_p3_condition_hasCompeletedQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        return fs_quests.hasQuestCompleted(player);
    }
    public boolean combat_quest_p3_condition_hasAcceptedNonCSQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        LOG("fs_quest", "hasQuestAccepted=" + fs_quests.hasQuestAccepted(player));
        LOG("fs_quest", "hasQuestCompleted=" + fs_quests.hasQuestCompleted(player));
        LOG("fs_quest", "isOnCsQuest=" + fs_counterstrike.isOnCsQuest(player));
        return (fs_quests.hasQuestAccepted(player) && !fs_quests.hasQuestCompleted(player)) && !fs_counterstrike.isOnCsQuest(player);
    }
    public void combat_quest_p3_action_GiveQuest(obj_id player, obj_id npc) throws InterruptedException
    {
        attachScript(player, "systems.fs_quest.fs_cs_player");
        quests.activate("fs_cs_intro", player, npc);
        fs_quests.setQuestAccepted(player);
        sendSystemMessage(player, new string_id("fs_quest_village", "fs_cs_accepted"));
    }
    public void combat_quest_p3_action_giveRemote(obj_id player, obj_id npc) throws InterruptedException
    {
        Vector phaseNames = new Vector();
        obj_id villageMaster = getObjIdObjVar(npc, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT);
        if (hasObjVar(villageMaster, fs_counterstrike.OBJVAR_PHASE_CAMP_NAMES))
        {
            phaseNames = getResizeableStringArrayObjVar(villageMaster, fs_counterstrike.OBJVAR_PHASE_CAMP_NAMES);
        }
        if (phaseNames == null || phaseNames.size() < 1)
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::giveRemote: locations are null or empty.", player, trace.TL_ERROR_LOG);
            return;
        }
        obj_id inv = getObjectInSlot(player, "inventory");
        if (isIdValid(inv))
        {
            obj_id[] objects = utils.getContents(inv, false);
            boolean foundDisk = false;
            for (int i = 0; i < objects.length; i++)
            {
                if ((getTemplateName(objects[i])).equals("object/tangible/loot/quest/force_sensitive/camp_frequency_datapad.iff"))
                {
                    if (fs_counterstrike.getPhaseItemPercentDecay(objects[i], 3) < 100)
                    {
                        destroyObject(objects[i]);
                        foundDisk = true;
                        break;
                    }
                }
            }
            if (!foundDisk)
            {
                sendSystemMessage(player, new string_id("fs_quest_village", "fs_remote_dont_have"));
                return;
            }
            obj_id remote = createObject("object/tangible/loot/quest/force_sensitive/camp_remote.iff", inv, "");
            if (isIdValid(remote))
            {
                String name = (String)phaseNames.get(rand(0, phaseNames.size() - 1));
                setObjVar(remote, fs_counterstrike.OBJVAR_CAMP_NAME, name);
                setName(remote, new string_id("fs_quest_village", "name_rem_" + name));
            }
        }
        return;
    }
    public void combat_quest_p3_action_giveWaypoint(obj_id player, obj_id npc) throws InterruptedException
    {
        Vector phaseLocs = new Vector();
        obj_id villageMaster = getObjIdObjVar(npc, fs_dyn_village.OBJVAR_MY_MASTER_OBJECT);
        if (utils.hasScriptVar(villageMaster, fs_counterstrike.OBJVAR_CREATED_CAMP_LOCS))
        {
            phaseLocs = getResizeableLocationArrayObjVar(villageMaster, fs_counterstrike.OBJVAR_PHASE_CAMP_LOC_HINTS);
        }
        if (phaseLocs == null || phaseLocs.size() < 1)
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_counterstrike::giveWaypoint: camp locs null or empty.", player, trace.TL_ERROR_LOG);
            return;
        }
        obj_id inv = getObjectInSlot(player, "inventory");
        if (isIdValid(inv))
        {
            obj_id[] objects = utils.getContents(inv, false);
            boolean foundDisk = false;
            for (int i = 0; i < objects.length; i++)
            {
                if ((getTemplateName(objects[i])).equals("object/tangible/loot/quest/force_sensitive/camp_waypoint_datapad.iff"))
                {
                    if (fs_counterstrike.getPhaseItemPercentDecay(objects[i], 3) < 100)
                    {
                        destroyObject(objects[i]);
                        foundDisk = true;
                        break;
                    }
                }
            }
            if (!foundDisk)
            {
                sendSystemMessage(player, new string_id("fs_quest_village", "fs_remote_dont_have"));
                return;
            }
            location loc = (location)phaseLocs.get(rand(0, phaseLocs.size() - 1));
            obj_id waypoint = createWaypointInDatapad(player, loc);
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, "Aurilian Enemy");
        }
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.combat_quest_p3");
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
        detachScript(self, "npc.conversation.combat_quest_p3");
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
        if (combat_quest_p3_condition_canTalkToNpc(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_822aed71");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (!combat_quest_p3_condition_isOnQuest(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (!combat_quest_p3_condition_isOnQuest(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (combat_quest_p3_condition_canTurnInFrequencyDp(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (combat_quest_p3_condition_isOnQuest(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            boolean hasResponse4 = false;
            if (combat_quest_p3_condition_canTurnInWaypoint(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_43c7c81c");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_ab0e6060");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d403b674");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_f3864fa4");
                }
                if (hasResponse4)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5d6c3f37");
                }
                setObjVar(player, "conversation.combat_quest_p3.branchId", 1);
                npcStartConversation(player, self, "combat_quest_p3", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (combat_quest_p3_condition_hasAcceptedNonCSQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_476f5295");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (combat_quest_p3_condition_hasCompeletedQuest(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_5ee8eee8");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (combat_quest_p3_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2c9f72e9");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("combat_quest_p3"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.combat_quest_p3.branchId");
        if (branchId == 1 && response.equals("s_43c7c81c"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_1b62f30b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a48b66a0");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5604a3fb");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've gained some critical information about the enemy, thanks to the efforts of you and your friends. The time for our attack has come.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_ab0e6060"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_50cdfefd");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've gained some critical information about the enemy, thanks to the efforts of you and your friends. The time for our attack has come.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_d403b674"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                combat_quest_p3_action_giveRemote(player, self);
                string_id message = new string_id(c_stringFile, "s_c012ae2b");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've gained some critical information about the enemy, thanks to the efforts of you and your friends. The time for our attack has come.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_f3864fa4"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2a5da4e9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de9f3fb3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd673a6");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've gained some critical information about the enemy, thanks to the efforts of you and your friends. The time for our attack has come.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_5d6c3f37"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                combat_quest_p3_action_giveWaypoint(player, self);
                string_id message = new string_id(c_stringFile, "s_51775d2e");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We've gained some critical information about the enemy, thanks to the efforts of you and your friends. The time for our attack has come.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_a48b66a0"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b8a984d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_52917b0d");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6c565018");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Our scouting missions have revealed some very crucial information about possible enemy encampments right here on Dathomir!  We are not certain of the exact locations and our intelligence tells us that the enemy is constantly on the move attempting to elude detection.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && response.equals("s_5604a3fb"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_54db1a8e");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Our scouting missions have revealed some very crucial information about possible enemy encampments right here on Dathomir!  We are not certain of the exact locations and our intelligence tells us that the enemy is constantly on the move attempting to elude detection.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_52917b0d"))
        {
            combat_quest_p3_action_GiveQuest(player, self);
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_e4a148f5");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_527934ce");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm afraid I can't offer much in return, but I can put in a good word with Noldan, so that he might allow you to train Melee Accuracy.  Will you still assist me?  Keep in mind that if you're helping me, you won't be able to do any other work here in the village until later.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_6c565018"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_206a4b9b");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I'm afraid I can't offer much in return, but I can put in a good word with Noldan, so that he might allow you to train Melee Accuracy.  Will you still assist me?  Keep in mind that if you're helping me, you won't be able to do any other work here in the village until later.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_527934ce"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7481097c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_dd3cd5d7");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Excellent!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_dd3cd5d7"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ae1194d7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8767a129");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, not really.  First of all we need to know the exact frequency code for the specific shield you wish to power down in order to configure the remote control device.   Each shield has its own frequency code.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_8767a129"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_2a5da4e9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (combat_quest_p3_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_de9f3fb3");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9bd673a6");
                    }
                    setObjVar(player, "conversation.combat_quest_p3.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.combat_quest_p3.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you return such a data disk to me, I will give you a remote control device configured with the proper frequency to power down the shield.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_de9f3fb3"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3804b036");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you and a friend decide to take this task on together, I will make sure that both of you receive your training as long as you both participate.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_9bd673a6"))
        {
            if (combat_quest_p3_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_97bc1b9");
                removeObjVar(player, "conversation.combat_quest_p3.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you and a friend decide to take this task on together, I will make sure that both of you receive your training as long as you both participate.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.combat_quest_p3.branchId");
        return SCRIPT_CONTINUE;
    }
}
