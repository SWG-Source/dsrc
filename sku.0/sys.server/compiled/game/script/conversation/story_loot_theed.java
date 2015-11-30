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

public class story_loot_theed extends script.base_script
{
    public story_loot_theed()
    {
    }
    public static String c_stringFile = "conversation/story_loot_theed";
    public boolean story_loot_theed_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_theed_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_theed_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_theed_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_theed_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_theed_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_theed_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public String story_loot_theed_tokenTO_species(obj_id player, obj_id npc) throws InterruptedException
    {
        int speciesNum = getSpecies(player);
        String speciesName = "name_human";
        switch (speciesNum)
        {
            case SPECIES_HUMAN:
            speciesName = "name_human";
            break;
            case SPECIES_ZABRAK:
            speciesName = "name_zabrak";
            break;
            case SPECIES_WOOKIEE:
            speciesName = "name_wookie";
            break;
            case SPECIES_TWILEK:
            speciesName = "name_twilek";
            break;
            case SPECIES_TRANDOSHAN:
            speciesName = "name_trandoshan";
            break;
            case SPECIES_ITHORIAN:
            speciesName = "name_ithorian";
            break;
            case SPECIES_SULLUSTAN:
            speciesName = "name_sullustan";
            break;
            case SPECIES_MON_CALAMARI:
            speciesName = "name_moncal";
            break;
            case SPECIES_RODIAN:
            speciesName = "name_rodian";
            break;
            case SPECIES_BOTHAN:
            speciesName = "name_bothan";
            break;
        }
        return new String(utils.packStringId(new string_id("player_species", speciesName)));
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isMob(self)) || (isPlayer(self)))
        {
            detachScript(self, "npc.conversation.story_loot_theed");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Meeck");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Meeck");
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
        detachScript(self, "npc.conversation.story_loot_theed");
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
        if (story_loot_theed_condition_checkAll(player, self))
        {
            doAnimationAction(self, "point_away");
            story_loot_theed_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_b5402a0f");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            pp.other.set(story_loot_theed_tokenTO_species(player, self));
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_theed_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_26df7294");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_theed_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_c416ff05");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "applause_excited");
            story_loot_theed_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_b21c6039");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "applause_excited");
            story_loot_theed_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_64f35d26");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkLok(player, self))
        {
            story_loot_theed_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_a3b5a049");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "applause_excited");
            story_loot_theed_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_37b0ec80");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "nervous");
            story_loot_theed_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_c7c399e0");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_theed_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_91b60e5f");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_checkRem(player, self))
        {
            doAnimationAction(self, "applause_excited");
            string_id message = new string_id(c_stringFile, "s_20931802");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition_hasSpaceExp(player, self))
        {
            story_loot_theed_action_animBow(player, self);
            string_id message = new string_id(c_stringFile, "s_5d39c47e");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (story_loot_theed_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_2b8acda7");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_48d5fb5a");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_579e662c");
                }
                setObjVar(player, "conversation.story_loot_theed.branchId", 11);
                npcStartConversation(player, self, "story_loot_theed", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_theed_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_afaf698");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_theed"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_theed.branchId");
        if (branchId == 11 && response.equals("s_2b8acda7"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_f37c7b85");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c5da1302");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d89d872a");
                    }
                    setObjVar(player, "conversation.story_loot_theed.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_theed.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ooie booie! Yousa a pilot! Mesa need yousa hep with somethin'. Okieday? Will yousa hep mesa? Mesa need some information from da space systems above usan. Yousa have a maxibig maccaneks, okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_48d5fb5a"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_236d3d10");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_18833216");
                    }
                    setObjVar(player, "conversation.story_loot_theed.branchId", 16);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_theed.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ooie booie! Yousa a pilot! Mesa need yousa hep with somethin'. Okieday? Will yousa hep mesa? Mesa need some information from da space systems above usan. Yousa have a maxibig maccaneks, okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_579e662c"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_b59591d7");
                removeObjVar(player, "conversation.story_loot_theed.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ooie booie! Yousa a pilot! Mesa need yousa hep with somethin'. Okieday? Will yousa hep mesa? Mesa need some information from da space systems above usan. Yousa have a maxibig maccaneks, okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_c5da1302"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_c8d95e9d");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
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
                    setObjVar(player, "conversation.story_loot_theed.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_theed.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Okieday. Mesa need information for mesa clan. Wesa really interested in what goes on in da space systems. Yousa enemies have a chance of droppin' logs or diaries when yousa defeat them. If yousa don't want what yousa find, yousa can bring them to mesa and mesa pay yousa for them. Okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_d89d872a"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_253fa7ea");
                removeObjVar(player, "conversation.story_loot_theed.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Okieday. Mesa need information for mesa clan. Wesa really interested in what goes on in da space systems. Yousa enemies have a chance of droppin' logs or diaries when yousa defeat them. If yousa don't want what yousa find, yousa can bring them to mesa and mesa pay yousa for them. Okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_5a59fcdc"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                story_loot_theed_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_7cabd9ea");
                removeObjVar(player, "conversation.story_loot_theed.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Ooie booie! Mesa glad! Whensa yousa find anythin', just come back and drag de object on mesa and mesa will pay yousa for it. Okieday?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && response.equals("s_18833216"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_c8984072");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9a173443");
                    }
                    setObjVar(player, "conversation.story_loot_theed.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_theed.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Maccaneks..? Yousa basic folks say 'ship'? Flyin' in da space?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && response.equals("s_9a173443"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_11bab004");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_theed_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_618244d1");
                    }
                    setObjVar(player, "conversation.story_loot_theed.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_theed.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Okieday. Enemies drop logs and diaries? Yousa get. Bring to mesa. Mesa give moola.. er--credits?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_618244d1"))
        {
            if (story_loot_theed_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "applause_excited");
                story_loot_theed_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_8bac8381");
                removeObjVar(player, "conversation.story_loot_theed.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Yousa hep mesa?' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_theed.branchId");
        return SCRIPT_CONTINUE;
    }
}
