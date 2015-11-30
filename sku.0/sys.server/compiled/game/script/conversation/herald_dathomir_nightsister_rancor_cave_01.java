package script.conversation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.anims;
import script.library.chat;
import script.library.utils;

public class herald_dathomir_nightsister_rancor_cave_01 extends script.base_script
{
    public herald_dathomir_nightsister_rancor_cave_01()
    {
    }
    public static String c_stringFile = "conversation/herald_dathomir_nightsister_rancor_cave_01";
    public boolean herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean herald_dathomir_nightsister_rancor_cave_01_condition_hasWayPoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location loc;
        obj_id[] waypoints = getWaypointsInDatapad(player);
        for (int x = 0; x < waypoints.length; x++)
        {
            loc = getWaypointLocation(waypoints[x]);
            if (loc.x == 609f && loc.z == 3072f)
            {
                return true;
            }
        }
        return false;
    }
    public void herald_dathomir_nightsister_rancor_cave_01_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void herald_dathomir_nightsister_rancor_cave_01_action_grantWayPoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location explorer = new location(609, 0, 3072, "dathomir", null);
        obj_id waypoint = createWaypointInDatapad(player, explorer);
        setWaypointName(waypoint, "Explorer");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void herald_dathomir_nightsister_rancor_cave_01_action_faceto(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.herald_dathomir_nightsister_rancor_cave_01");
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
        detachScript(self, "npc.conversation.herald_dathomir_nightsister_rancor_cave_01");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (herald_dathomir_nightsister_rancor_cave_01_condition_hasWayPoint(player, self))
        {
            herald_dathomir_nightsister_rancor_cave_01_action_faceto(player, self);
            string_id message = new string_id(c_stringFile, "s_44b81b29");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
        {
            herald_dathomir_nightsister_rancor_cave_01_action_faceto(player, self);
            string_id message = new string_id(c_stringFile, "s_f299b046");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_10831e4f");
                }
                setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 2);
                npcStartConversation(player, self, "herald_dathomir_nightsister_rancor_cave_01", message, responses);
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
        if (!conversationId.equals("herald_dathomir_nightsister_rancor_cave_01"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
        if (branchId == 2 && response.equals("s_10831e4f"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_15e3dca2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7c31deb0");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_3e4ab6fb");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yeah...what do you want? Everyone is either trying to gloat or commiserate with me lately and frankly I don't need either.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_7c31deb0"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_624bf4fd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2092ed4c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6c129947");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh so you are just one of those people who like gabbing at complete strangers. Oh well, who am I to judge. So what do you want to talk about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_3e4ab6fb"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_6bb0ffaa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1317e44d");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh so you are just one of those people who like gabbing at complete strangers. Oh well, who am I to judge. So what do you want to talk about?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_2092ed4c"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5ad34c57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a48fb925");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let see. I spent two weeks on a damp, desolate, sorry excuse for a planet just to do a report on an explorer. Wrote my story, which was a really good piece...exciting. Had my story dumped the night before it was going to run. Oh and then I was fired. How are you doing?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_6c129947"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_adb7dc7c");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let see. I spent two weeks on a damp, desolate, sorry excuse for a planet just to do a report on an explorer. Wrote my story, which was a really good piece...exciting. Had my story dumped the night before it was going to run. Oh and then I was fired. How are you doing?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_a48fb925"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_95e33f0f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb269296");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5fda628");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They claimed it was because of lack of performance but I think someone way up high stepped in. I got the feeling that they were not impressed that my report was about an explore lost on Dathomir.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_bb269296"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                herald_dathomir_nightsister_rancor_cave_01_action_grantWayPoint(player, self);
                string_id message = new string_id(c_stringFile, "s_c5366b0f");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I wrote my story once and got fired for it. There is no way I am going to retell it...who knows what would happen then. But if you want I can direct you to the explorer and he can tell you his tale.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_c5fda628"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3ad6472e");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I wrote my story once and got fired for it. There is no way I am going to retell it...who knows what would happen then. But if you want I can direct you to the explorer and he can tell you his tale.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_1317e44d"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d04c7627");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_95c31ac4");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'The weather? You stopped me to talk about the weather? Hmmmm...it is much nicer here then on Dathomir, but...then again, what place isn't.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_95c31ac4"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_7d06d35f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bb269296");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5fda628");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yes, I am...er....was a reporter for the Corellian Times until I was fired. I did a story on an explorer of sorts who was lost in the wilds of Dathomir and managed to survive. The Times cut my story and then fired me.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_bb269296"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                herald_dathomir_nightsister_rancor_cave_01_action_grantWayPoint(player, self);
                string_id message = new string_id(c_stringFile, "s_c5366b0f");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I already got fired once for just trying to report on the subject and I don't need any more troubles in my life. If you want to talk to the explorer yourself I am willing to tell you where he was when I left him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_c5fda628"))
        {
            if (herald_dathomir_nightsister_rancor_cave_01_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3ad6472e");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I already got fired once for just trying to report on the subject and I don't need any more troubles in my life. If you want to talk to the explorer yourself I am willing to tell you where he was when I left him.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_01.branchId");
        return SCRIPT_CONTINUE;
    }
}
