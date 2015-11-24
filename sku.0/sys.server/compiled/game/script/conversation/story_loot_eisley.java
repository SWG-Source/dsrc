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

public class story_loot_eisley extends script.base_script
{
    public story_loot_eisley()
    {
    }
    public static String c_stringFile = "conversation/story_loot_eisley";
    public boolean story_loot_eisley_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_eisley_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_eisley_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_eisley_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_eisley_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_eisley_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_eisley_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public String story_loot_eisley_tokenTO_species(obj_id player, obj_id npc) throws InterruptedException
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
            detachScript(self, "npc.conversation.story_loot_eisley");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Neess Achessa");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Neess Achessa");
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
        detachScript(self, "npc.conversation.story_loot_eisley");
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
        if (story_loot_eisley_condition_checkAll(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_eisley_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_39ab0bf8");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            pp.other.set(story_loot_eisley_tokenTO_species(player, self));
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_eisley_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_7c4f443");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "curtsey1");
            story_loot_eisley_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_a128c839");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "applause_excited");
            story_loot_eisley_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_9f7731a8");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "curtsey1");
            story_loot_eisley_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_ca583977");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkLok(player, self))
        {
            doAnimationAction(self, "kiss");
            story_loot_eisley_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_1938855f");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_eisley_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_6acea54");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "embarrassed");
            story_loot_eisley_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_60682af");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_eisley_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_95de3587");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            pp.other.set(story_loot_eisley_tokenTO_species(player, self));
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_checkRem(player, self))
        {
            doAnimationAction(self, "applause_excited");
            string_id message = new string_id(c_stringFile, "s_db040e63");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition_hasSpaceExp(player, self))
        {
            doAnimationAction(self, "nervous");
            string_id message = new string_id(c_stringFile, "s_5fea04c");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_eisley_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_3e114acc");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_579e662c");
                }
                setObjVar(player, "conversation.story_loot_eisley.branchId", 11);
                npcStartConversation(player, self, "story_loot_eisley", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_eisley_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "curtsey1");
            string_id message = new string_id(c_stringFile, "s_e0574420");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_eisley"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_eisley.branchId");
        if (branchId == 11 && response.equals("s_3e114acc"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_4dcc9c02");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_da79c836");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_4cf73d47");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My master says I can speak with you. You seem to be a pilot and we--or at least, he--could use your help with a small task. If you don't say yes, my master will be very angry at me.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_579e662c"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_249fa88e");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My master says I can speak with you. You seem to be a pilot and we--or at least, he--could use your help with a small task. If you don't say yes, my master will be very angry at me.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_da79c836"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "explain");
                string_id message = new string_id(c_stringFile, "s_8aa2d689");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a5a29e5e");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_a27729d9");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My master is a Chadra-Fan. He does kinda look like a mouse, doesn't he? We came to Mos Eisley because Chadra-Fan are very afraid of drowning. Since there's no water here on Tatooine, it really isn't a problem for him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_4cf73d47"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_a1368bc4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b73ef16b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 21);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'My master is a Chadra-Fan. He does kinda look like a mouse, doesn't he? We came to Mos Eisley because Chadra-Fan are very afraid of drowning. Since there's no water here on Tatooine, it really isn't a problem for him.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_a5a29e5e"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "curtsey1");
                string_id message = new string_id(c_stringFile, "s_914ec8cb");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9c4a22e");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_eisley_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_eisley_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I was orphaned at a very young age and was found by a slave smuggler. He sold me to a Chadra-Fan whom took me to his homeworld on Chad. I met my master, Peraw, soon afterward and he taught me the Chadra-Fan language. I'm a loyal servant who happens to know Basic as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_a27729d9"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_a1368bc4");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b73ef16b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Well, I was orphaned at a very young age and was found by a slave smuggler. He sold me to a Chadra-Fan whom took me to his homeworld on Chad. I met my master, Peraw, soon afterward and he taught me the Chadra-Fan language. I'm a loyal servant who happens to know Basic as well.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_9c4a22e"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "embarrassed");
                string_id message = new string_id(c_stringFile, "s_cbf9648e");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_eisley_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_b73ef16b");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d6695e83");
                    }
                    setObjVar(player, "conversation.story_loot_eisley.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_eisley.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh, he does. You're such a nice %TO.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_b73ef16b"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "applause_excited");
                story_loot_eisley_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_c28b61a1");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. I can get so talkative sometimes. My master doesn't like it so much. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_d6695e83"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_3a570abb");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. I can get so talkative sometimes. My master doesn't like it so much. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_b73ef16b"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                story_loot_eisley_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_c28b61a1");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_d6695e83"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_3a570abb");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_b73ef16b"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "applause_excited");
                story_loot_eisley_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_c28b61a1");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 21 && response.equals("s_d6695e83"))
        {
            if (story_loot_eisley_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "weeping");
                string_id message = new string_id(c_stringFile, "s_3a570abb");
                removeObjVar(player, "conversation.story_loot_eisley.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Oh yeah. Sorry. The task... My master needs information from the.. uh, space systems, I guess it is? I'm not really too smart about those beyond the ground. Anyway, the information can be 'a log', or 'a diary', or anything like that. If you find them, just give them to me. My master trusts me when it comes in his money. I can give you some for the information you bring us.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_eisley.branchId");
        return SCRIPT_CONTINUE;
    }
}
