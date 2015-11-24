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
import script.library.player_structure;
import script.library.utils;

public class death_watch_technician extends script.base_script
{
    public death_watch_technician()
    {
    }
    public static String c_stringFile = "conversation/death_watch_technician";
    public boolean death_watch_technician_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean death_watch_technician_condition_ventOff(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (hasObjVar(player, "death_watch.air_vent_fail") || hasObjVar(player, "death_watch.air_vent_success"))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.air_vent_on") && !hasObjVar(structure, "death_watch.missionTaker"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_ventOn(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (hasObjVar(structure, "death_watch.air_vent_on") && !hasObjVar(player, "death_watch.air_vent_success"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_ventOffCompleted(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.missionTaker"))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.air_vent_on") && hasObjVar(player, "death_watch.air_vent_success"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_ventOffNewPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.missionTaker"))
        {
            return false;
        }
        obj_id taker = getObjIdObjVar(structure, "death_watch.missionTaker");
        if (!hasObjVar(structure, "death_watch.air_vent_on") && taker != player)
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_ventFailure(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (hasObjVar(structure, "death_watch.missionTaker"))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.air_vent_on") && hasObjVar(player, "death_watch.air_vent_fail"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_ventOnOldPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (hasObjVar(structure, "death_watch.air_vent_on") && hasObjVar(player, "death_watch.air_vent_success"))
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_toSoon(obj_id player, obj_id npc) throws InterruptedException
    {
        int oldTime = getIntObjVar(player, "death_watch.air_vent_fail");
        int refreshTime = oldTime + 1800;
        int curTime = getGameTime();
        if (curTime < refreshTime)
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_readyToGoAgain(obj_id player, obj_id npc) throws InterruptedException
    {
        int oldTime = getIntObjVar(player, "death_watch.air_vent_fail");
        int refreshTime = oldTime + 1800;
        int curTime = getGameTime();
        if (curTime > refreshTime)
        {
            return true;
        }
        return false;
    }
    public boolean death_watch_technician_condition_goProtectDroid(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return false;
        }
        if (!hasObjVar(structure, "death_watch.missionTaker"))
        {
            return false;
        }
        obj_id taker = getObjIdObjVar(structure, "death_watch.missionTaker");
        if (!hasObjVar(structure, "death_watch.air_vent_on") && taker == player)
        {
            return true;
        }
        return false;
    }
    public void death_watch_technician_action_startEvent(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(npc);
        if (!isIdValid(structure))
        {
            return;
        }
        setObjVar(structure, "death_watch.missionTaker", player);
        float xCoord = -20.25f;
        float yCoord = -52f;
        float zCoord = -162.3f;
        location myself = getLocation(npc);
        String planet = myself.area;
        obj_id room = getCellId(structure, "lab64");
        location spawnPoint = new location(xCoord, yCoord, zCoord, planet, room);
        obj_id spawnedCreature = create.object("mand_bunker_repair_droid", spawnPoint);
        attachScript(spawnedCreature, "theme_park.dungeon.death_watch_bunker.repair_droid");
    }
    public int death_watch_technician_handleBranch1(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bb1676fc"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_f7d4b8be");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1cc2ceff");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 2);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_d7a232dc"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25e933d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4446dd7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_1cc2ceff"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_25e933d3");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c4446dd7");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_24");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch3(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_c4446dd7"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_45ae332c");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a0c1a32f");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_24"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8fb8b64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5476c4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406a4ae2");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_a0c1a32f"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_a8fb8b64");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bd5476c4");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_406a4ae2");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch5(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_bd5476c4"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c74f6e65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17aa7854");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a05844b");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_406a4ae2"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_bb18b90f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_20");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_72f8ec3a");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch6(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_17aa7854"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                death_watch_technician_action_startEvent(player, npc);
                string_id message = new string_id(c_stringFile, "s_fe84832a");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_1a05844b"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_89b80057");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_20"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_c74f6e65");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (death_watch_technician_condition__defaultCondition(player, npc))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_17aa7854");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1a05844b");
                    }
                    utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_72f8ec3a"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_3f1a842c");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch11(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_7d875e15"))
        {
            if (death_watch_technician_condition_toSoon(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_32b29b58");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
            if (death_watch_technician_condition_readyToGoAgain(player, npc))
            {
                death_watch_technician_action_startEvent(player, npc);
                string_id message = new string_id(c_stringFile, "s_30");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_3bc0260c"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_fc578c95");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int death_watch_technician_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_373533eb"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                death_watch_technician_action_startEvent(player, npc);
                string_id message = new string_id(c_stringFile, "s_9a8c12bc");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_cbf95857"))
        {
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_aad4d52");
                utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
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
            detachScript(self, "conversation.death_watch_technician");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setInvulnerable(self, true);
        setName(self, "Labsol Renuffi (a technician)");
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
        detachScript(self, "conversation.death_watch_technician");
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
        if (death_watch_technician_condition_ventOff(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_8b67f49d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_bb1676fc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d7a232dc");
                }
                utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 1);
                npcStartConversation(player, npc, "death_watch_technician", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition_ventFailure(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_bd52a08a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_7d875e15");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3bc0260c");
                }
                utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 11);
                npcStartConversation(player, npc, "death_watch_technician", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition_ventOn(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_ebfed4db");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition_ventOffNewPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_1fb416ce");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition_ventOffCompleted(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_3cab69e3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (death_watch_technician_condition__defaultCondition(player, npc))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_373533eb");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_cbf95857");
                }
                utils.setScriptVar(player, "conversation.death_watch_technician.branchId", 17);
                npcStartConversation(player, npc, "death_watch_technician", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition_ventOnOldPlayer(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_33381eda");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (death_watch_technician_condition__defaultCondition(player, npc))
        {
            string_id message = new string_id(c_stringFile, "s_cba682eb");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("death_watch_technician"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.death_watch_technician.branchId");
        if (branchId == 1 && death_watch_technician_handleBranch1(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 2 && death_watch_technician_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && death_watch_technician_handleBranch3(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && death_watch_technician_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && death_watch_technician_handleBranch5(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && death_watch_technician_handleBranch6(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && death_watch_technician_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && death_watch_technician_handleBranch11(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && death_watch_technician_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.death_watch_technician.branchId");
        return SCRIPT_CONTINUE;
    }
}
