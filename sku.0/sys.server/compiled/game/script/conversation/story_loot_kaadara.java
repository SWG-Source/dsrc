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

public class story_loot_kaadara extends script.base_script
{
    public story_loot_kaadara()
    {
    }
    public static String c_stringFile = "conversation/story_loot_kaadara";
    public boolean story_loot_kaadara_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_kaadara_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_kaadara_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_kaadara_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_kaadara_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_kaadara_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_kaadara_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public String story_loot_kaadara_tokenTO_species(obj_id player, obj_id npc) throws InterruptedException
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
            detachScript(self, "npc.conversation.story_loot_kaadara");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Model 5-D23");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Model 5-D23");
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
        detachScript(self, "npc.conversation.story_loot_kaadara");
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
        if (story_loot_kaadara_condition_checkAll(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_kaadara_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_8102799c");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "curtsey");
            story_loot_kaadara_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_da89aca1");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_df3acc86");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_b73343f");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_c3034c17");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkLok(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_f377335c");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_ffeed2dd");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_b467075c");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_kaadara_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_15849cf7");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_checkRem(player, self))
        {
            doAnimationAction(self, "explain");
            string_id message = new string_id(c_stringFile, "s_17719eaa");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            pp.other.set(story_loot_kaadara_tokenTO_species(player, self));
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition_hasSpaceExp(player, self))
        {
            doAnimationAction(self, "manipulate_medium");
            string_id message = new string_id(c_stringFile, "s_35e1b3d3");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_kaadara_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_d6e4c4f4");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_579e662c");
                }
                setObjVar(player, "conversation.story_loot_kaadara.branchId", 11);
                npcStartConversation(player, self, "story_loot_kaadara", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_kaadara_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2c1ae38f");
            prose_package pp = new prose_package();
            pp.stringId = message;
            pp.actor.set(player);
            pp.target.set(self);
            pp.other.set(story_loot_kaadara_tokenTO_species(player, self));
            chat.chat(self, player, null, null, pp);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_kaadara"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_kaadara.branchId");
        if (branchId == 11 && response.equals("s_d6e4c4f4"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_9f9d56f7");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_kaadara_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_700de710");
                    }
                    setObjVar(player, "conversation.story_loot_kaadara.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Scanning.. .. Scanning complete. Confimed. You're a pilot. Mobel number 5-D23 asks for your assistance. Roger, roger.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_579e662c"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8f76b872");
                removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Scanning.. .. Scanning complete. Confimed. You're a pilot. Mobel number 5-D23 asks for your assistance. Roger, roger.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_700de710"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                string_id message = new string_id(c_stringFile, "s_3a806939");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_kaadara_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_61ab006");
                    }
                    setObjVar(player, "conversation.story_loot_kaadara.branchId", 13);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_kaadara_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_kaadara_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Clone Wars. Attack. Does not compute. Model number 5-D23 is programmed to collect information. Roger, roger.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_61ab006"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_89fa7715");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_kaadara_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_kaadara_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_9e214a10");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_348fdf07");
                    }
                    setObjVar(player, "conversation.story_loot_kaadara.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Roger, roger. Model 5-D23 is requesting that little %TO gather information that can be found of enemies in space systems.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_9e214a10"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                story_loot_kaadara_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_9fea5784");
                removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Scanning.. .. Look for logs, diaries. Roger, roger. Model 5-D23 is authorized to pay monetary value for information.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_348fdf07"))
        {
            if (story_loot_kaadara_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_8f76b872");
                removeObjVar(player, "conversation.story_loot_kaadara.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Scanning.. .. Look for logs, diaries. Roger, roger. Model 5-D23 is authorized to pay monetary value for information.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_kaadara.branchId");
        return SCRIPT_CONTINUE;
    }
}
