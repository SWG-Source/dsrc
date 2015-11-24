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

public class story_loot_bestine extends script.base_script
{
    public story_loot_bestine()
    {
    }
    public static String c_stringFile = "conversation/story_loot_bestine";
    public boolean story_loot_bestine_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_bestine_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_bestine_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_bestine_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_bestine_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_bestine_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_bestine_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public String story_loot_bestine_tokenTO_species(obj_id player, obj_id npc) throws InterruptedException
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
            detachScript(self, "npc.conversation.story_loot_bestine");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Riljcha Voruste");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Riljcha Voruste");
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
        detachScript(self, "npc.conversation.story_loot_bestine");
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
        if (story_loot_bestine_condition_checkAll(player, self))
        {
            doAnimationAction(self, "belly_laugh");
            story_loot_bestine_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_f468472d");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "thumbs_down");
            story_loot_bestine_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_49ebaa61");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "tiny");
            story_loot_bestine_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_811e4893");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "hold_nose");
            story_loot_bestine_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_499a8089");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_bestine_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_da2693fc");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkLok(player, self))
        {
            doAnimationAction(self, "rude");
            story_loot_bestine_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_c10227fa");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "shake_head_disgust");
            story_loot_bestine_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_e523caed");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "wave_on_dismissing");
            story_loot_bestine_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_d7e58047");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "thumb_down");
            story_loot_bestine_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_6c3929dd");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_checkRem(player, self))
        {
            doAnimationAction(self, "check_wrist_device");
            string_id message = new string_id(c_stringFile, "s_61e0fe65");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition_hasSpaceExp(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_2e41605f");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_bestine_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_de1ad09e");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_324c1a5a");
                }
                setObjVar(player, "conversation.story_loot_bestine.branchId", 11);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                npcStartConversation(player, self, "story_loot_bestine", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(self);
                pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                chat.chat(self, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_bestine_condition__defaultCondition(player, self))
        {
            string_id message = new string_id(c_stringFile, "s_d4673a");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_bestine"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_bestine.branchId");
        if (branchId == 11 && response.equals("s_de1ad09e"))
        {
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_50394b57");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_bestine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c2e59721");
                    }
                    setObjVar(player, "conversation.story_loot_bestine.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_bestine.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Your aura indicates you are a pilot. Is this true, young %TO?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_324c1a5a"))
        {
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a050b81f");
                removeObjVar(player, "conversation.story_loot_bestine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Your aura indicates you are a pilot. Is this true, young %TO?' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_c2e59721"))
        {
            doAnimationAction(player, "nod_head_once");
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "sigh_deeply");
                string_id message = new string_id(c_stringFile, "s_6d679a15");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_bestine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6128587b");
                    }
                    setObjVar(player, "conversation.story_loot_bestine.branchId", 13);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_bestine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'Why, indeed, it is. I've never noticed him before. Of course, it's a Hutt, you dolt! In fact, *he* is my employer. I highly recommend oweing him the proper respect. He hasn't been fed today.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_6128587b"))
        {
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "heavy_cough_vomit");
                string_id message = new string_id(c_stringFile, "s_66e27b28");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_bestine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_bestine_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (story_loot_bestine_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_c333eea");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_7cbeeeed");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_781f5cfe");
                    }
                    setObjVar(player, "conversation.story_loot_bestine.branchId", 14);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_bestine.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(self);
                    pp.other.set(story_loot_bestine_tokenTO_species(player, self));
                    npcSpeak(player, pp);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'You petty %TO. My employer, Belhata the Hutt--how original--is here to mingle with his... 'friends', the twisted politicians of Bestine. While Belhata is here, he assumed it would be a good idea to employ peons, like yourself, to gather information for him. Unfortunately, to make it worth while, I have to pay you for any type of information you find. My life has turned painfully pitiful.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_c333eea"))
        {
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "backhand_threaten");
                story_loot_bestine_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_c03b2cd7");
                removeObjVar(player, "conversation.story_loot_bestine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't have all day to chatter with you, little %TO. The information can be found in the space systems. Go up there, try not to die *too* often and collect information from the enemies you eliminate. The information can be in the form of a log or even a diary. Go now. Your presence make me nauseous.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_7cbeeeed"))
        {
            doAnimationAction(player, "gesticulate_wildly");
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "giveup");
                string_id message = new string_id(c_stringFile, "s_b05e0d8c");
                removeObjVar(player, "conversation.story_loot_bestine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't have all day to chatter with you, little %TO. The information can be found in the space systems. Go up there, try not to die *too* often and collect information from the enemies you eliminate. The information can be in the form of a log or even a diary. Go now. Your presence make me nauseous.' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_781f5cfe"))
        {
            doAnimationAction(player, "cuckoo");
            if (story_loot_bestine_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_away");
                string_id message = new string_id(c_stringFile, "s_7b7f4a0f");
                removeObjVar(player, "conversation.story_loot_bestine.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch 'I don't have all day to chatter with you, little %TO. The information can be found in the space systems. Go up there, try not to die *too* often and collect information from the enemies you eliminate. The information can be in the form of a log or even a diary. Go now. Your presence make me nauseous.' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_bestine.branchId");
        return SCRIPT_CONTINUE;
    }
}
