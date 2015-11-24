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

public class herald_dathomir_nightsister_rancor_cave_02 extends script.base_script
{
    public herald_dathomir_nightsister_rancor_cave_02()
    {
    }
    public static String c_stringFile = "conversation/herald_dathomir_nightsister_rancor_cave_02";
    public boolean herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean herald_dathomir_nightsister_rancor_cave_02_condition_hasWayPoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location loc;
        obj_id[] waypoints = getWaypointsInDatapad(player);
        for (int x = 0; x < waypoints.length; x++)
        {
            loc = getWaypointLocation(waypoints[x]);
            if (loc.x == -4010f && loc.z == -43f)
            {
                return true;
            }
        }
        return false;
    }
    public void herald_dathomir_nightsister_rancor_cave_02_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void herald_dathomir_nightsister_rancor_cave_02_action_grantWayPoint(obj_id player, obj_id npc) throws InterruptedException
    {
        location cave = new location(-4010, 0, -43, "dathomir", null);
        obj_id waypoint = createWaypointInDatapad(player, cave);
        setWaypointName(waypoint, "Rancor Cave");
        setWaypointColor(waypoint, "blue");
        setWaypointVisible(waypoint, true);
        setWaypointActive(waypoint, true);
    }
    public void herald_dathomir_nightsister_rancor_cave_02_action_faceto(obj_id player, obj_id npc) throws InterruptedException
    {
        faceToBehavior(npc, player);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.herald_dathomir_nightsister_rancor_cave_02");
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
        detachScript(self, "npc.conversation.herald_dathomir_nightsister_rancor_cave_02");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (herald_dathomir_nightsister_rancor_cave_02_condition_hasWayPoint(player, self))
        {
            herald_dathomir_nightsister_rancor_cave_02_action_faceto(player, self);
            string_id message = new string_id(c_stringFile, "s_5b51e4ff");
            chat.chat(self, message);
            return SCRIPT_CONTINUE;
        }
        if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
        {
            herald_dathomir_nightsister_rancor_cave_02_action_faceto(player, self);
            string_id message = new string_id(c_stringFile, "s_b790a5fa");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_fa7218cd");
                }
                setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 2);
                npcStartConversation(player, self, "herald_dathomir_nightsister_rancor_cave_02", message, responses);
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
        if (!conversationId.equals("herald_dathomir_nightsister_rancor_cave_02"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
        if (branchId == 2 && response.equals("s_fa7218cd"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a049b0c2");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fa7b107c");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 3);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ahhhhhh...er...sorry about that I was letting my mind drift and you looked like one of those witches for a moment. You will have to excuse me I haven't been myself since I had my incident.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 3 && response.equals("s_fa7b107c"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_ace86f45");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc7d951b");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 4);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Bah...it is this planet. Something is wrong with it and all that live on it. And let me tell you, I should know, I have been lost out in that jungle. No one should ever have to see the things that I saw.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_bc7d951b"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_de0973b1");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15ce71c7");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You sound like that nosey reporter. But I suppose that since I was willing to tell him I can do the same for you. Hmmmm....where to begin.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_15ce71c7"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f1cf80ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6f6a022");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 6);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'At the beginning eh? Very well, it all started about seventy-five years ago when my da met my mum. They fell in love, got married, and then I showed up. At about eight I started school at the academy for.....' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 6 && response.equals("s_6f6a022"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f5169a8f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_30a1b3c3");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 7);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was just seeing if you were paying attention. Well, me and my partner came to Dathomir because we heard that there were riches to be found in antiquities from this place. That was our first mistake.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && response.equals("s_30a1b3c3"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9b56973b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_81490116");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 8);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'That really isn't important. Like I said it was a mistake, there isn't anything on this rock worth spit. Me and my partner set out in our speeder and traveled around for several days dodging all sorts of nasties. Let me tell you this place has nasties in spades.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && response.equals("s_81490116"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9f7894ec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_84c686b2");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 9);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'We were quite a ways out when our speeder decides that it doesn't want to work anymore. So what do we do...turn on the emergency becon and hope for help to arrive...nope. We set off on foot. That was mistake number two.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_84c686b2"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f8b65700");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_622c6840");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I was getting to that. We walked for a couple days, well ran is more like it. Dathomir's fauna seems to consider us a good source of protein. Any ways a few days after we broke down is when we ran into those witches. Nastiest of the nasties.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_622c6840"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_fcac14c6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_15885220");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 11);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'They call themselves the sisters of the night or the nightsisters or something like that. You'll never meet anything meaner then those...er...women? We appartently had walked to close to that sorry bunch of shacks and huts that they call a stronghold.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_15885220"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_91ddecec");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_50ea3acc");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You could say that. So those pale skinned freaks showed up and let me tell you we didn't waste anytime high tailing it out of there. I managed to elude them but my partner wasn't so lucky.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_50ea3acc"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_d2615d77");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8cd440d3");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yeah he was nabbed by them witches. I followed them back to the outskirts of their stronghold where they drug him down into a cave. That place was ripe with the scent of rancors and had a real forboding feel to it. You know what I mean?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_8cd440d3"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c6cfb65f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_8e266a60");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ab4f8cd8");
                    }
                    setObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't know for sure but I can guess. The sounds that were coming out of that cave will haunt me for the rest of my life. It is a shame to, he was a good man.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_8e266a60"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                herald_dathomir_nightsister_rancor_cave_02_action_grantWayPoint(player, self);
                string_id message = new string_id(c_stringFile, "s_75346db8");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you want to know where it is I can give you the location but there is no way that I am ever going to set foot back into that jungle. Just as soon as I can get enough credits together I am getting off of this rock.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_ab4f8cd8"))
        {
            if (herald_dathomir_nightsister_rancor_cave_02_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_b4961c75");
                removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'If you want to know where it is I can give you the location but there is no way that I am ever going to set foot back into that jungle. Just as soon as I can get enough credits together I am getting off of this rock.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.herald_dathomir_nightsister_rancor_cave_02.branchId");
        return SCRIPT_CONTINUE;
    }
}
