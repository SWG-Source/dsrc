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
import script.library.money;
import script.library.prose;
import script.library.utils;

public class story_loot_talus extends script.base_script
{
    public story_loot_talus()
    {
    }
    public static String c_stringFile = "conversation/story_loot_talus";
    public boolean story_loot_talus_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_talus_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("all"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("tatooine"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("naboo"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("corellia"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("lok"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("dantooine"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("yavin4"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("endor"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.planet"))
        {
            String planet = utils.getStringScriptVar(player, "story_loot.planet");
            if (planet.equals("dathomir"))
            {
                return true;
            }
        }
        return false;
    }
    public boolean story_loot_talus_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_talus_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_talus_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 1500;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 1500;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 1500;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 3000;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Lok(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 3000;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 3000;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Endor(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 4000;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 4000;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_All(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id item = utils.getObjIdScriptVar(player, "story_loot.item");
        if (!isIdValid(item))
        {
            return;
        }
        destroyObject(item);
        utils.removeScriptVar(player, "story_loot.planet");
        utils.removeScriptVar(player, "story_loot.item");
        int amount = 150;
        prose_package pp = prose.getPackage(new string_id("space/story_loot_d", "systemmessage"), amount);
        sendSystemMessageProse(player, pp);
        money.systemPayout(money.ACCT_RELIC_DEALER, player, amount, "noHandler", null);
        return;
    }
    public void story_loot_talus_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_talus_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.story_loot_talus");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Ebony Ves'Ser");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Ebony Ves'Ser");
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
        detachScript(self, "npc.conversation.story_loot_talus");
        return SCRIPT_CONTINUE;
    }
    public int OnGiveItem(obj_id self, obj_id item, obj_id player) throws InterruptedException
    {
        if (hasObjVar(item, "story_loot.planet"))
        {
            String planet = getStringObjVar(item, "story_loot.planet");
            utils.setScriptVar(player, "story_loot.planet", planet);
            utils.setScriptVar(player, "story_loot.item", item);
            OnStartNpcConversation(self, player);
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
        if (story_loot_talus_condition_checkAll(player, self))
        {
            story_loot_talus_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_d446e3df");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkCorellia(player, self))
        {
            story_loot_talus_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_a5556cbd");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_talus_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_9208410d");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkDathomir(player, self))
        {
            story_loot_talus_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_dd1eea6d");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "nod_head_multiple");
            story_loot_talus_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_e45caf5a");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkLok(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_talus_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_9208410d");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkNaboo(player, self))
        {
            story_loot_talus_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_383cdae");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_talus_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_ece79bc1");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_talus_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_c4f7d1f");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_checkRem(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_244d9ea1");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition_hasSpaceExp(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9208410d");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_talus_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_a10eda57");
                }
                setObjVar(player, "conversation.story_loot_talus.branchId", 11);
                npcStartConversation(player, self, "story_loot_talus", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_talus_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_9b37d148");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_talus"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_talus.branchId");
        if (branchId == 11 && response.equals("s_a10eda57"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a3d8ff73");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_1eaeb942");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b7dfba8d");
                    }
                    setObjVar(player, "conversation.story_loot_talus.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_talus.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '...' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_1eaeb942"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9208410d");
                removeObjVar(player, "conversation.story_loot_talus.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '... I am a Marauder, a race of reptilian humanoidsss, sssurviving as best we can on Endor. We lure sssilly humanoidsss like you to their death and ssteal their technology sso we can ssurvive. Now, what iss it that you want?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_b7dfba8d"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c4dd562d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_82270559");
                    }
                    setObjVar(player, "conversation.story_loot_talus.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_talus.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '... I am a Marauder, a race of reptilian humanoidsss, sssurviving as best we can on Endor. We lure sssilly humanoidsss like you to their death and ssteal their technology sso we can ssurvive. Now, what iss it that you want?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_82270559"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8696779b");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_435d9064");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6236530");
                    }
                    setObjVar(player, "conversation.story_loot_talus.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_talus.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ssstupid human. I am here to gather information for my people.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_435d9064"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c42087aa");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_talus_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_2e907efd");
                    }
                    setObjVar(player, "conversation.story_loot_talus.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_talus.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It doesss not concern you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_6236530"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9208410d");
                removeObjVar(player, "conversation.story_loot_talus.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'It doesss not concern you.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_2e907efd"))
        {
            if (story_loot_talus_condition__defaultCondition(player, self))
            {
                story_loot_talus_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_9208410d");
                removeObjVar(player, "conversation.story_loot_talus.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '... You ssseem to be a pilot. I sssuppose I can implore you to help me. I am ssseeking information from the ssspace sssystemss. When you find it, you will know what I am looking for. Bring them to me.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_talus.branchId");
        return SCRIPT_CONTINUE;
    }
}
