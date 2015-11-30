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

public class story_loot_coronet extends script.base_script
{
    public story_loot_coronet()
    {
    }
    public static String c_stringFile = "conversation/story_loot_coronet";
    public boolean story_loot_coronet_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_coronet_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_coronet_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_coronet_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_coronet_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_coronet_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_coronet_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.story_loot_coronet");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Orztia Sayildi");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Orztia Sayildi");
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
        detachScript(self, "npc.conversation.story_loot_coronet");
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
        if (story_loot_coronet_condition_checkAll(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_coronet_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_491794fd");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_coronet_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_4670569c");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "curtsey1");
            story_loot_coronet_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_fb2d2773");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "applause_excited");
            story_loot_coronet_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_a0c8586a");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "bow3");
            story_loot_coronet_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_9e4796e9");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkLok(player, self))
        {
            doAnimationAction(self, "kiss");
            story_loot_coronet_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_8894e226");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "rub_chin_thoughtful");
            story_loot_coronet_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_d50da00e");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "celebrate");
            story_loot_coronet_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_86775be0");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_coronet_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_d38cbaeb");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_checkRem(player, self))
        {
            doAnimationAction(self, "applause_excited");
            string_id message = new string_id(c_stringFile, "s_8794aec1");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition_hasSpaceExp(player, self))
        {
            story_loot_coronet_action_animBow(player, self);
            string_id message = new string_id(c_stringFile, "s_480f9afe");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_coronet_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_6de6d29b");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_579e662c");
                }
                setObjVar(player, "conversation.story_loot_coronet.branchId", 11);
                npcStartConversation(player, self, "story_loot_coronet", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_coronet_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_8e14e452");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_coronet"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_coronet.branchId");
        if (branchId == 11 && response.equals("s_6de6d29b"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_1b7e5efd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_coronet_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_coronet_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (story_loot_coronet_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_eeb5ec9");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d89d872a");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b02ea236");
                    }
                    setObjVar(player, "conversation.story_loot_coronet.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_coronet.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetin's! Aren't you a lil' young to be walkin' 'round here all by your lonesome? Oh, hon. Don't be like that. I was just givin' you a hard time. Listen.. are you a pilot? You look to be. I need your help with somethin'. What do you think?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_579e662c"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a050b81f");
                removeObjVar(player, "conversation.story_loot_coronet.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Greetin's! Aren't you a lil' young to be walkin' 'round here all by your lonesome? Oh, hon. Don't be like that. I was just givin' you a hard time. Listen.. are you a pilot? You look to be. I need your help with somethin'. What do you think?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_eeb5ec9"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_43fa3ec7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_coronet_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5a59fcdc");
                    }
                    setObjVar(player, "conversation.story_loot_coronet.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_coronet.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hm. Sounds fair. I'm a part of the 'News Now!' Information Guild, specializing in the news of the galaxy. Here's where you come in.. I'm afraid of heights, you see. I can't fly and often get motion sickness up there when I try. You need to go up there and gather information for me. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_d89d872a"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4b465cc3");
                removeObjVar(player, "conversation.story_loot_coronet.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hm. Sounds fair. I'm a part of the 'News Now!' Information Guild, specializing in the news of the galaxy. Here's where you come in.. I'm afraid of heights, you see. I can't fly and often get motion sickness up there when I try. You need to go up there and gather information for me. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_b02ea236"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_cde666f4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_coronet_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5a59fcdc");
                    }
                    setObjVar(player, "conversation.story_loot_coronet.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_coronet.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Hm. Sounds fair. I'm a part of the 'News Now!' Information Guild, specializing in the news of the galaxy. Here's where you come in.. I'm afraid of heights, you see. I can't fly and often get motion sickness up there when I try. You need to go up there and gather information for me. Are you interested?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_5a59fcdc"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                story_loot_coronet_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_d2e79392");
                removeObjVar(player, "conversation.story_loot_coronet.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Alright! I'm so excited! In space, there's a chance your enemy will drop a bit of information be it 'a log', or 'a diary', or what have you. If you bring them to me, I'll pay you handsomely for them. Granted, some will be worth more than others. If you find some, just hand them to me, okay?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_5a59fcdc"))
        {
            if (story_loot_coronet_condition__defaultCondition(player, self))
            {
                story_loot_coronet_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_d2e79392");
                removeObjVar(player, "conversation.story_loot_coronet.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Let me see. Oh! Do you find yourself in a lot of dogfights? If you do, there's a chance your enemy will drop a bit of information be it 'a log', or 'a diary', or what have you. If you bring them to me, I'll pay you handsomely for them. Granted, some will be worth more than others. If you find some, just hand them to me.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_coronet.branchId");
        return SCRIPT_CONTINUE;
    }
}
