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

public class story_loot_moenia extends script.base_script
{
    public story_loot_moenia()
    {
    }
    public static String c_stringFile = "conversation/story_loot_moenia";
    public boolean story_loot_moenia_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean story_loot_moenia_condition_checkAll(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkTatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkNaboo(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkCorellia(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkLok(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkDantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkYavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkEndor(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_checkDathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public boolean story_loot_moenia_condition_hasSpaceExp(obj_id player, obj_id npc) throws InterruptedException
    {
        return features.isSpaceEdition(player);
    }
    public boolean story_loot_moenia_condition_checkRem(obj_id player, obj_id npc) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "story_loot.rem"))
        {
            return true;
        }
        return false;
    }
    public void story_loot_moenia_action_Tatooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Naboo(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Corellia(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Dantooine(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Lok(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Yavin4(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Endor(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_Dathomir(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_All(obj_id player, obj_id npc) throws InterruptedException
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
    public void story_loot_moenia_action_animBow(obj_id player, obj_id npc) throws InterruptedException
    {
        doAnimationAction(npc, "bow");
    }
    public void story_loot_moenia_action_storyLoot(obj_id player, obj_id npc) throws InterruptedException
    {
        utils.setScriptVar(player, "story_loot.rem", true);
    }
    public String story_loot_moenia_tokenTO_species(obj_id player, obj_id npc) throws InterruptedException
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
            detachScript(self, "npc.conversation.story_loot_moenia");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Droijack-dyn");
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setInvulnerable(self, true);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        setName(self, "Droijack-dyn");
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
        detachScript(self, "npc.conversation.story_loot_moenia");
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
        if (story_loot_moenia_condition_checkAll(player, self))
        {
            doAnimationAction(self, "point_away");
            doAnimationAction(player, "scared");
            story_loot_moenia_action_All(player, self);
            string_id message = new string_id(c_stringFile, "s_aa0a2be5");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkCorellia(player, self))
        {
            doAnimationAction(self, "fakepunch");
            doAnimationAction(player, "pet_high");
            story_loot_moenia_action_Corellia(player, self);
            string_id message = new string_id(c_stringFile, "s_8a1e08c");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkDantooine(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            doAnimationAction(player, "dream");
            story_loot_moenia_action_Dantooine(player, self);
            string_id message = new string_id(c_stringFile, "s_4f8f72e2");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkDathomir(player, self))
        {
            doAnimationAction(self, "flipcoin");
            doAnimationAction(player, "airguitar");
            story_loot_moenia_action_Dathomir(player, self);
            string_id message = new string_id(c_stringFile, "s_b953d944");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkEndor(player, self))
        {
            doAnimationAction(self, "flipcoin");
            story_loot_moenia_action_Endor(player, self);
            string_id message = new string_id(c_stringFile, "s_e9574203");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkLok(player, self))
        {
            doAnimationAction(self, "backhand_threaten");
            doAnimationAction(player, "shrug_shoulders");
            story_loot_moenia_action_Lok(player, self);
            string_id message = new string_id(c_stringFile, "s_61cc04c8");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkNaboo(player, self))
        {
            doAnimationAction(self, "flipcoin");
            doAnimationAction(player, "expect_tip");
            story_loot_moenia_action_Naboo(player, self);
            string_id message = new string_id(c_stringFile, "s_d701ec31");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkTatooine(player, self))
        {
            doAnimationAction(self, "flipcoin");
            story_loot_moenia_action_Tatooine(player, self);
            string_id message = new string_id(c_stringFile, "s_d768ce2d");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkYavin4(player, self))
        {
            doAnimationAction(self, "nod_head_once");
            story_loot_moenia_action_Yavin4(player, self);
            string_id message = new string_id(c_stringFile, "s_5b01a6db");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_checkRem(player, self))
        {
            doAnimationAction(self, "wave1");
            doAnimationAction(player, "shrug_shoulders");
            string_id message = new string_id(c_stringFile, "s_d70926bc");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition_hasSpaceExp(player, self))
        {
            doAnimationAction(self, "wave1");
            story_loot_moenia_action_animBow(player, self);
            string_id message = new string_id(c_stringFile, "s_5b29fb2");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (story_loot_moenia_condition__defaultCondition(player, self))
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
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45d96c97");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_579e662c");
                }
                setObjVar(player, "conversation.story_loot_moenia.branchId", 11);
                npcStartConversation(player, self, "story_loot_moenia", message, responses);
            }
            else 
            {
                chat.chat(self, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (story_loot_moenia_condition__defaultCondition(player, self))
        {
            doAnimationAction(self, "wave_on_dismissing");
            string_id message = new string_id(c_stringFile, "s_308c2df6");
            chat.chat(self, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("story_loot_moenia"))
        {
            return SCRIPT_CONTINUE;
        }
        int branchId = getIntObjVar(player, "conversation.story_loot_moenia.branchId");
        if (branchId == 11 && response.equals("s_45d96c97"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "point_up");
                string_id message = new string_id(c_stringFile, "s_c9179ad6");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_fb58660c");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_6d9c4154");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d3466494");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 12);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Droijack-dyn the Ishi Tib tries to get your attention by spouting off what sounds like chipring. Ah, yes. The Ishi Tib, an avian race whose language consists of bird-like sounds and squawks. You try to understand what it's saying.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 11 && response.equals("s_579e662c"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "backhand_threaten");
                doAnimationAction(player, "wtf");
                string_id message = new string_id(c_stringFile, "s_43728039");
                removeObjVar(player, "conversation.story_loot_moenia.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Droijack-dyn the Ishi Tib tries to get your attention by spouting off what sounds like chipring. Ah, yes. The Ishi Tib, an avian race whose language consists of bird-like sounds and squawks. You try to understand what it's saying.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_fb58660c"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "shake_head_no");
                string_id message = new string_id(c_stringFile, "s_8b12a5dd");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_92154c00");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 13);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Droijack-dyn the Ishi Tib nods. It, although you've kinda figured out it's male by it's appearance, points to the sky. Space. Obviously, he wants something from the space systems. But, what, you can't tell. You start to guess.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_6d9c4154"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(player, "pose_proudly");
                string_id message = new string_id(c_stringFile, "s_57df7ab9");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5b7b08d1");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 18);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Droijack-dyn the Ishi Tib nods. It, although you've kinda figured out it's male by it's appearance, points to the sky. Space. Obviously, he wants something from the space systems. But, what, you can't tell. You start to guess.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && response.equals("s_d3466494"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "threaten_combat");
                doAnimationAction(player, "scared");
                string_id message = new string_id(c_stringFile, "s_7f89f5d3");
                removeObjVar(player, "conversation.story_loot_moenia.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Droijack-dyn the Ishi Tib nods. It, although you've kinda figured out it's male by it's appearance, points to the sky. Space. Obviously, he wants something from the space systems. But, what, you can't tell. You start to guess.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && response.equals("s_92154c00"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_f09b75ae");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_f93d75ff");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 14);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[You crinkle your nose. Where did that come from? Well, he is a bird, isn't he? Droijack-dyn the Ishi Tib shakes his head. Of course that's not what he wanted! You try some more guesses.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 14 && response.equals("s_f93d75ff"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_a8aa7613");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_d57d668a");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_5c453f12");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 15);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Would you stop that? It seems Droijack-dyn really needs some help. Get serious, here. You try again.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_d57d668a"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(player, "shrug_shoulders");
                story_loot_moenia_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_56dc8579");
                removeObjVar(player, "conversation.story_loot_moenia.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Finally, Droijack-dyn nods and pulls out two objects from his backpack. One looks like a log of some sort, the other, a diary--you can tell because 'diary' is written across the front of it. Pretty tricky. Droijack-dyn points up. You begin to piece together that you can find objects like this in space and that the bird-man needs them. Will you help?]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 15 && response.equals("s_5c453f12"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "strut");
                string_id message = new string_id(c_stringFile, "s_81fa3514");
                removeObjVar(player, "conversation.story_loot_moenia.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Finally, Droijack-dyn nods and pulls out two objects from his backpack. One looks like a log of some sort, the other, a diary--you can tell because 'diary' is written across the front of it. Pretty tricky. Droijack-dyn points up. You begin to piece together that you can find objects like this in space and that the bird-man needs them. Will you help?]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 18 && response.equals("s_5b7b08d1"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                string_id message = new string_id(c_stringFile, "s_aa1b838f");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (story_loot_moenia_condition__defaultCondition(player, self))
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
                        responses[responseIndex++] = new string_id(c_stringFile, "s_34e97c89");
                    }
                    setObjVar(player, "conversation.story_loot_moenia.branchId", 19);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    removeObjVar(player, "conversation.story_loot_moenia.branchId");
                    npcSpeak(player, message);
                    npcEndConversation(player);
                }
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[Right on the first try! Looks like you have all of your kreetles in one lair and definitely the sharpest stone knife in the drawer. The bird-man pulls out several objects, looking to contain some pretty neat information. You start to piece together that he wants these objects, you can get them from space and from the enemies you defeat. Wow, your intellect is amazing. You puff up, trying to look all impressive.]' were false.");
            return SCRIPT_CONTINUE;
        }
        if (branchId == 19 && response.equals("s_34e97c89"))
        {
            if (story_loot_moenia_condition__defaultCondition(player, self))
            {
                doAnimationAction(self, "nod_head_once");
                story_loot_moenia_action_storyLoot(player, self);
                string_id message = new string_id(c_stringFile, "s_b3731646");
                removeObjVar(player, "conversation.story_loot_moenia.branchId");
                npcSpeak(player, message);
                npcEndConversation(player);
                return SCRIPT_CONTINUE;
            }
            chat.chat(self, "Error:  All conditions for OnNpcConversationResponse for branch '[You look closer at the objects Droijack-dyn is holding. The objects appear to be disks, and a datapad of some sort. Huh. One of them has 'diary' written on the front of it. Looks like you'll be hunting enemies for their diaries and logs of information. Just your line of work. Droijack-dyn pulls out some credits from his backpack, either gloating he has way more money than you or that he'll pay you for the information you get him.]' were false.");
            return SCRIPT_CONTINUE;
        }
        chat.chat(self, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        removeObjVar(player, "conversation.story_loot_moenia.branchId");
        return SCRIPT_CONTINUE;
    }
}
