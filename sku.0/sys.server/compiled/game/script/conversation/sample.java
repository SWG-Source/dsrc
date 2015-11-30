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
import script.library.cmd;
import script.library.colors;
import script.library.combat;

public class sample extends script.base_script
{
    public sample()
    {
    }
    public static String c_stringFile = "conversation/sample";
    public boolean sample_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean sample_condition_knowsPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(player, "asommers_test.playerLevel") == 0;
    }
    public boolean sample_condition_likesPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(player, "asommers_test.playerLevel") == 1;
    }
    public boolean sample_condition_dislikesPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        return getIntObjVar(player, "asommers_test.playerLevel") == 2;
    }
    public void sample_action__defaultAction(obj_id player, obj_id npc) throws InterruptedException
    {
    }
    public void sample_action_cheer(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "asommers_test.playerLevel", 1);
        doAnimationAction(npc, anims.PLAYER_APPLAUSE_POLITE);
    }
    public void sample_action_greet(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, anims.PLAYER_BOW2);
    }
    public void sample_action_clear(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "asommers_test.playerLevel", 0);
    }
    public void sample_action_impress(obj_id player, obj_id npc) throws InterruptedException
    {
        setObjVar(player, "asommers_test.playerLevel", 1);
        doAnimationAction(npc, anims.PLAYER_FLEX_BICEPS);
        playMusic(player, "sound/music_event_danger.snd");
        location loc = getLocation(npc);
        if (loc != null)
        {
            playClientEffectLoc(player, "clienteffect/entertainer_color_lights_level_3.cef", loc, 0.f);
        }
    }
    public void sample_action_shakeFist(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, anims.PLAYER_GESTICULATE_WILDLY);
    }
    public void sample_action_scratchHead(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, anims.PLAYER_SCRATCH_HEAD);
        setObjVar(player, "asommers_test.playerLevel", 2);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.sample");
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
        detachScript(self, "npc.conversation.sample");
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        if (ai_lib.isInCombat(self) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (sample_condition_knowsPlayer(player, self))
        {
            sample_action_greet(player, self);
            string_id message = new string_id(c_stringFile, "s_a23c5071");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_9f39d936");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_8516f9af");
                }
                setObjVar(player, "conversation.sample.branchId", 1);
                npcStartConversation(player, self, "sample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sample_condition_likesPlayer(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d3feb8e7");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (sample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_b6a01563");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_58132054");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a1f212c");
                }
                setObjVar(player, "conversation.sample.branchId", 4);
                npcStartConversation(player, self, "sample", message, responses);
            }
            else 
            {
                chat.chat(self, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (sample_condition_dislikesPlayer(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_e5afcb3a");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (sample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (sample_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (sample_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_5c203407");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_59da9c5a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_4a1f212c");
                }
                setObjVar(player, "conversation.sample.branchId", 9);
                npcStartConversation(player, self, "sample", message, responses);
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
        if (!conversationId.equals("sample"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.sample.branchId");
        if (branchId == 1 && response.equals("s_9f39d936"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_scratchHead(player, self);
                string_id message = new string_id(c_stringFile, "s_d574fa56");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hi there.  I don't believe I know you!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 1 && response.equals("s_8516f9af"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_impress(player, self);
                string_id message = new string_id(c_stringFile, "s_27dd59b6");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hi there.  I don't believe I know you!' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_b6a01563"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_5967ef4d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sample_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2280847e");
                    }
                    setObjVar(player, "conversation.sample.branchId", 5);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sample.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome back!  What would you like to see?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_58132054"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_scratchHead(player, self);
                string_id message = new string_id(c_stringFile, "s_ae4f086a");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome back!  What would you like to see?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && response.equals("s_4a1f212c"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_clear(player, self);
                string_id message = new string_id(c_stringFile, "s_551ee684");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Welcome back!  What would you like to see?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 5 && response.equals("s_2280847e"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_impress(player, self);
                string_id message = new string_id(c_stringFile, "s_33ee1807");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I can only show you the first trick I have ever shown you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_5c203407"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_3f9aded7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (sample_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (sample_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_ef420789");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_bc51d16b");
                    }
                    setObjVar(player, "conversation.sample.branchId", 10);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.sample.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why are you bothering me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_59da9c5a"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_scratchHead(player, self);
                string_id message = new string_id(c_stringFile, "s_914765d8");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why are you bothering me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && response.equals("s_4a1f212c"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_clear(player, self);
                string_id message = new string_id(c_stringFile, "s_551ee684");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why are you bothering me?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_ef420789"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_cheer(player, self);
                string_id message = new string_id(c_stringFile, "s_b75b171f");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Do you mean it?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 10 && response.equals("s_bc51d16b"))
        {
            if (sample_condition__defaultCondition(player, self))
            {
                sample_action_shakeFist(player, self);
                string_id message = new string_id(c_stringFile, "s_629c5d0b");
                removeObjVar(player, "conversation.sample.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Do you mean it?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.sample.branchId");
        return SCRIPT_CONTINUE;
    }
}
