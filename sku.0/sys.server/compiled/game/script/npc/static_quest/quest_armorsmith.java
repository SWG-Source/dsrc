package script.npc.static_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.prose;
import script.library.money;
import script.library.chat;

public class quest_armorsmith extends script.base_script
{
    public quest_armorsmith()
    {
    }
    public static final String SCRIPT_QUEST_ARMORSMITH = "npc.static_quest.quest_armorsmith";
    public static final String DATATABLE_ARMORSMITH_ITEMS = "datatables/npc/static_quest/armorsmith.iff";
    public static final int QUEST_COST = 50000;
    public static final String VAR_QUEST_ROOT_ARMORSMITH = "profession_quest_status.armorsmith";
    public static final String CONVERSE_ARMORSMITH_QUEST = "armorsmith_quest";
    public static final string_id SID_GREETING = new string_id("quest_armorsmith", "greeting");
    public static final string_id SID_GREETING_COMPLETE = new string_id("quest_armorsmith", "greeting");
    public static final string_id SID_QUEST_1_START = new string_id("quest_armorsmith", "quest_1_start");
    public static final string_id SID_QUEST_1_START_NO = new string_id("quest_armorsmith", "quest_1_start_no");
    public static final string_id SID_QUEST_1_START_YES = new string_id("quest_armorsmith", "quest_1_start_yes");
    public static final string_id SID_QUEST_1_DESCRIPTION = new string_id("quest_armorsmith", "quest_1_description");
    public static final string_id SID_QUEST_2_QUERY = new string_id("quest_armorsmith", "quest_2_query");
    public static final string_id SID_QUEST_2_COMPLETE = new string_id("quest_armorsmith", "quest_2_complete");
    public static final string_id SID_QUEST_2_INCOMPLETE = new string_id("quest_armorsmith", "quest_2_incomplete");
    public static final string_id SID_QUEST_3_START = new string_id("quest_armorsmith", "quest_3_start");
    public static final string_id SID_QUEST_3_START_NO = new string_id("quest_armorsmith", "quest_3_start_no");
    public static final string_id SID_QUEST_4_QUERY = new string_id("quest_armorsmith", "quest_4_query");
    public static final string_id SID_QUEST_4_COMPLETE = new string_id("quest_armorsmith", "quest_4_complete");
    public static final string_id SID_QUEST_4_INCOMPLETE = new string_id("quest_armorsmith", "quest_4_incomplete");
    public static final string_id SID_ILLEGAL_RESPONSE = new string_id("quest_armorsmith", "illegal_response");
    public static final string_id SID_QUEST_5_START = new string_id("quest_armorsmith", "quest_5_start");
    public static final string_id SID_QUEST_5_START_NO = new string_id("quest_armorsmith", "quest_5_start_no");
    public static final string_id SID_QUEST_6_QUERY = new string_id("quest_armorsmith", "quest_6_query");
    public static final string_id SID_QUEST_6_COMPLETE = new string_id("quest_armorsmith", "quest_6_complete");
    public static final string_id SID_QUEST_6_INCOMPLETE = new string_id("quest_armorsmith", "quest_6_incomplete");
    public static final string_id SID_NOT_ENOUGH_MONEY = new string_id("quest_armorsmith", "not_enough_money");
    public static final string_id SID_CONVERSION = new string_id("quest_armorsmith", "conversion_done");
    public static final string_id SID_YES_TO_QUEST_1_START = new string_id("quest_armorsmith", "yes_to_quest_1_start");
    public static final string_id SID_NO_TO_QUEST_1_START = new string_id("quest_armorsmith", "no_to_quest_1_start");
    public static final string_id SID_YES_TO_QUEST_2_QUERY = new string_id("quest_armorsmith", "yes_to_quest_2_query");
    public static final string_id SID_NO_TO_QUEST_2_QUERY = new string_id("quest_armorsmith", "no_to_quest_2_query");
    public static final string_id SID_YES_TO_QUEST_3_START = new string_id("quest_armorsmith", "yes_to_quest_3_start");
    public static final string_id SID_NO_TO_QUEST_3_START = new string_id("quest_armorsmith", "no_to_quest_3_start");
    public static final string_id SID_YES_TO_QUEST_4_QUERY = new string_id("quest_armorsmith", "yes_to_quest_4_query");
    public static final string_id SID_NO_TO_QUEST_4_QUERY = new string_id("quest_armorsmith", "no_to_quest_4_query");
    public static final string_id SID_YES_TO_QUEST_5_START = new string_id("quest_armorsmith", "yes_to_quest_5_start");
    public static final string_id SID_NO_TO_QUEST_5_START = new string_id("quest_armorsmith", "no_to_quest_5_start");
    public static final string_id SID_YES_TO_QUEST_6_QUERY = new string_id("quest_armorsmith", "yes_to_quest_6_query");
    public static final string_id SID_NO_TO_QUEST_6_QUERY = new string_id("quest_armorsmith", "no_to_quest_6_query");
    public static final string_id SID_YES = new string_id("quest_armorsmith", "yes");
    public static final string_id SID_NO = new string_id("quest_armorsmith", "no");
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        detachScript(self, SCRIPT_QUEST_ARMORSMITH);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int mnu = mi.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data mdata = mi.getMenuItemById(mnu);
        mdata.setServerNotify(false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        string_id[] valid_responses = new string_id[0];
        prose_package[] prose_responses = new prose_package[0];
        int stage = getArmorsmithQuestStage(speaker);
        prose_package pp;
        String name;
        if (hasOldRisBoot(speaker))
        {
            if (hasOldRisChest(speaker))
            {
                fullSchematicConversion(speaker);
            }
            else 
            {
                bootSchematicConversion(speaker);
            }
            chat.chat(self, speaker, SID_CONVERSION);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        switch (stage)
        {
            case 0:
            pp = prose.getPackage(SID_QUEST_1_START, QUEST_COST);
            valid_responses = getConversationResponses(speaker, self, 1);
            npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, null, pp, valid_responses);
            break;
            case 1:
            npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, SID_QUEST_1_DESCRIPTION, valid_responses);
            grantSchematic(speaker, "object/draft_schematic/armor/armor_segment_ris.iff");
            setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 2);
            break;
            case 2:
            name = getArmorsmithQuestItemName(stage);
            if (name != null)
            {
                pp = prose.getPackage(SID_QUEST_2_QUERY, name);
                valid_responses = getConversationResponses(speaker, self, 3);
                npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, null, pp, valid_responses);
            }
            break;
            case 3:
            npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, SID_QUEST_3_START, valid_responses);
            setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 4);
            grantSchematic(speaker, "object/draft_schematic/armor/component/armor_layer_ris.iff");
            break;
            case 4:
            name = getArmorsmithQuestItemName(stage);
            if (name != null)
            {
                pp = prose.getPackage(SID_QUEST_4_QUERY, name);
                valid_responses = getConversationResponses(speaker, self, 5);
                npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, null, pp, valid_responses);
            }
            break;
            case 5:
            setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 6);
            npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, SID_QUEST_5_START, valid_responses);
            grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_boots.iff");
            break;
            case 6:
            name = getArmorsmithQuestItemName(stage);
            if (name != null)
            {
                pp = prose.getPackage(SID_QUEST_6_QUERY, name);
                valid_responses = getConversationResponses(speaker, self, 7);
                npcStartConversation(speaker, self, CONVERSE_ARMORSMITH_QUEST, null, pp, valid_responses);
            }
            break;
            case 7:
            npcSpeak(speaker, SID_GREETING_COMPLETE);
            npcEndConversation(speaker);
            default:
            chat.chat(self, speaker, SID_GREETING);
            npcEndConversation(speaker);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        string_id[] valid_responses = new string_id[0];
        prose_package[] prose_responses = new prose_package[0];
        int stage = getArmorsmithQuestStage(speaker);
        prose_package pp;
        LOG("LOG_CHANNEL", "response ->" + response);
        if (stage == -1)
        {
            npcSpeak(speaker, SID_GREETING);
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (response.equals(SID_NO_TO_QUEST_1_START))
        {
            npcSpeak(speaker, SID_QUEST_1_START_NO);
            npcEndConversation(speaker);
        }
        else if (response.equals(SID_YES_TO_QUEST_1_START))
        {
            if (stage == 0)
            {
                dictionary d = new dictionary();
                int total_money = getTotalMoney(speaker);
                if (total_money < QUEST_COST)
                {
                    npcSpeak(speaker, SID_NOT_ENOUGH_MONEY);
                }
                else 
                {
                    d.put("player", speaker);
                    money.requestPayment(speaker, self, QUEST_COST, "armorsmithQuestPayment", d, true);
                }
                npcEndConversation(speaker);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES))
        {
            if (stage == 1)
            {
                grantSchematic(speaker, "object/draft_schematic/armor/armor_segment_ris.iff");
                setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 2);
                npcSpeak(speaker, SID_QUEST_1_DESCRIPTION);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES_TO_QUEST_2_QUERY))
        {
            if (stage == 2)
            {
                obj_id segment = verifyQuestItem(speaker);
                if (isIdValid(segment))
                {
                    destroyObject(segment);
                    setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 3);
                    npcSpeak(speaker, SID_QUEST_2_COMPLETE);
                    valid_responses = getConversationResponses(speaker, self, 4);
                    npcSetConversationResponses(speaker, valid_responses);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if (response.equals(SID_NO_TO_QUEST_2_QUERY))
        {
            if (stage == 2)
            {
                npcSpeak(speaker, SID_QUEST_2_INCOMPLETE);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES_TO_QUEST_3_START))
        {
            if (stage == 3)
            {
                grantSchematic(speaker, "object/draft_schematic/armor/component/armor_layer_ris.iff");
                setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 4);
                npcSpeak(speaker, SID_QUEST_3_START);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_NO_TO_QUEST_3_START))
        {
            if (stage == 3)
            {
                npcSpeak(speaker, SID_QUEST_3_START_NO);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES_TO_QUEST_4_QUERY))
        {
            if (stage == 4)
            {
                obj_id segment = verifyQuestItem(speaker);
                if (isIdValid(segment))
                {
                    destroyObject(segment);
                    setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 5);
                    npcSpeak(speaker, SID_QUEST_4_COMPLETE);
                    valid_responses = getConversationResponses(speaker, self, 6);
                    npcSetConversationResponses(speaker, valid_responses);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if (response.equals(SID_NO_TO_QUEST_4_QUERY))
        {
            if (stage == 4)
            {
                npcSpeak(speaker, SID_QUEST_4_INCOMPLETE);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES_TO_QUEST_5_START))
        {
            if (stage == 5)
            {
                grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_boots.iff");
                setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 6);
                npcSpeak(speaker, SID_QUEST_5_START);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_NO_TO_QUEST_5_START))
        {
            if (stage == 5)
            {
                npcSpeak(speaker, SID_QUEST_5_START_NO);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        else if (response.equals(SID_YES_TO_QUEST_6_QUERY))
        {
            if (stage == 6)
            {
                obj_id segment = verifyQuestItem(speaker);
                if (isIdValid(segment))
                {
                    destroyObject(segment);
                    revokeSchematic(speaker, "object/draft_schematic/armor/component/armor_layer_ris.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_enhancement_feather.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bicep_l.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bicep_r.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bracer_l.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bracer_r.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_chest.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_gloves.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_helmet.iff");
                    grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_leggings.iff");
                    setObjVar(speaker, VAR_QUEST_ROOT_ARMORSMITH, 7);
                    npcSpeak(speaker, SID_QUEST_6_COMPLETE);
                    npcSetConversationResponses(speaker, valid_responses);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        else if (response.equals(SID_NO_TO_QUEST_6_QUERY))
        {
            if (stage == 6)
            {
                npcSpeak(speaker, SID_QUEST_6_INCOMPLETE);
                npcSetConversationResponses(speaker, valid_responses);
                return SCRIPT_CONTINUE;
            }
        }
        npcSpeak(speaker, SID_ILLEGAL_RESPONSE);
        npcEndConversation(speaker);
        return SCRIPT_CONTINUE;
    }
    public int armorsmithQuestPayment(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (params.containsKey(money.DICT_CODE))
        {
            int return_code = params.getInt(money.DICT_CODE);
            if (return_code == money.RET_FAIL)
            {
                chat.chat(self, player, SID_NOT_ENOUGH_MONEY);
                return SCRIPT_CONTINUE;
            }
        }
        LOG("LOG_CHANNEL", "player ->" + player);
        if (!isIdValid(player))
        {
            LOG("LOG_CHANNEL", "quest_armorsmith::handlePayment -- player is not valid");
            return SCRIPT_CONTINUE;
        }
        setObjVar(player, VAR_QUEST_ROOT_ARMORSMITH, 1);
        string_id[] valid_responses = getConversationResponses(player, self, 2);
        npcStartConversation(player, self, CONVERSE_ARMORSMITH_QUEST, SID_QUEST_1_START_YES, valid_responses);
        money.bankTo(self, money.ACCT_SKILL_TRAINING, QUEST_COST);
        return SCRIPT_CONTINUE;
    }
    public string_id[] getConversationResponses(obj_id player, obj_id npc, int progress) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "faction_recruiter::getConversationResponses -- " + progress);
        Vector responses = new Vector();
        responses.setSize(0);
        switch (progress)
        {
            case 1:
            responses = utils.addElement(responses, SID_YES_TO_QUEST_1_START);
            responses = utils.addElement(responses, SID_NO_TO_QUEST_1_START);
            break;
            case 2:
            responses = utils.addElement(responses, SID_YES);
            break;
            case 3:
            if (verifyQuestItem(player) != null)
            {
                responses = utils.addElement(responses, SID_YES_TO_QUEST_2_QUERY);
            }
            responses = utils.addElement(responses, SID_NO_TO_QUEST_2_QUERY);
            break;
            case 4:
            responses = utils.addElement(responses, SID_YES_TO_QUEST_3_START);
            responses = utils.addElement(responses, SID_NO_TO_QUEST_3_START);
            break;
            case 5:
            if (verifyQuestItem(player) != null)
            {
                responses = utils.addElement(responses, SID_YES_TO_QUEST_4_QUERY);
            }
            responses = utils.addElement(responses, SID_NO_TO_QUEST_4_QUERY);
            break;
            case 6:
            responses = utils.addElement(responses, SID_YES_TO_QUEST_5_START);
            responses = utils.addElement(responses, SID_NO_TO_QUEST_5_START);
            break;
            case 7:
            if (verifyQuestItem(player) != null)
            {
                responses = utils.addElement(responses, SID_YES_TO_QUEST_6_QUERY);
            }
            responses = utils.addElement(responses, SID_NO_TO_QUEST_6_QUERY);
            break;
            default:
        }
        string_id[] _responses = new string_id[0];
        if (responses != null)
        {
            _responses = new string_id[responses.size()];
            responses.toArray(_responses);
        }
        return _responses;
    }
    public prose_package[] getProseConversationResponses(obj_id player, obj_id npc, String prose_string, int prose_digit, int progress) throws InterruptedException
    {
        LOG("LOG_CHANNEL", "faction_recruiter::getProseConversationResponses -- " + progress);
        prose_package[] responses = new prose_package[0];
        prose_package pp;
        int stage = getArmorsmithQuestStage(player);
        switch (progress)
        {
            case 3:
            LOG("LOG_CHANNEL", "name ->" + prose_string);
            pp = prose.getPackage(SID_YES_TO_QUEST_2_QUERY, prose_string);
            responses = assemblePackageArray(responses, pp);
            pp = prose.getPackage(SID_NO_TO_QUEST_2_QUERY, prose_string);
            responses = assemblePackageArray(responses, pp);
            break;
        }
        LOG("LOG_CHANNEL", "responses ->" + responses.length);
        return responses;
    }
    public prose_package[] assemblePackageArray(prose_package[] array, prose_package element) throws InterruptedException
    {
        if (element == null)
        {
            return new prose_package[0];
        }
        if (array == null)
        {
            return assemblePackageArray(new prose_package[0], element);
        }
        Vector vector = new Vector(Arrays.asList(array));
        vector.add(element);
        array = new prose_package[vector.size()];
        vector.toArray(array);
        return array;
    }
    public int getArmorsmithQuestStage(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return -1;
        }
        if (!hasSkill(player, "class_munitions_phase4_master"))
        {
            return -1;
        }
        if (hasObjVar(player, VAR_QUEST_ROOT_ARMORSMITH))
        {
            return getIntObjVar(player, VAR_QUEST_ROOT_ARMORSMITH);
        }
        else 
        {
            return 0;
        }
    }
    public String getArmorsmithQuestItem(int stage) throws InterruptedException
    {
        if (stage < 0)
        {
            return null;
        }
        int idx = dataTableSearchColumnForInt(stage, "stage", DATATABLE_ARMORSMITH_ITEMS);
        if (idx == -1)
        {
            return null;
        }
        else 
        {
            return dataTableGetString(DATATABLE_ARMORSMITH_ITEMS, idx, "template");
        }
    }
    public String getArmorsmithQuestItemName(int stage) throws InterruptedException
    {
        if (stage < 0)
        {
            return null;
        }
        int idx = dataTableSearchColumnForInt(stage, "stage", DATATABLE_ARMORSMITH_ITEMS);
        if (idx == -1)
        {
            return null;
        }
        else 
        {
            String name_str = dataTableGetString(DATATABLE_ARMORSMITH_ITEMS, idx, "name");
            if (name_str != null)
            {
                string_id name_str_id = utils.unpackString(name_str);
                return localize(name_str_id);
            }
        }
        return null;
    }
    public obj_id verifyQuestItem(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        int stage = getArmorsmithQuestStage(player);
        if (stage == -1)
        {
            return null;
        }
        String template = getArmorsmithQuestItem(stage);
        if (template == null)
        {
            return null;
        }
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (inventory == null)
        {
            LOG("LOG_CHANNEL", "healing::findHealWoundMedicine -- " + player + "'s inventory is null.");
            return null;
        }
        obj_id[] inv_contents = utils.getContents(inventory, false);
        for (int i = 0; i < inv_contents.length; i++)
        {
            if (template.equals(getTemplateName(inv_contents[i])))
            {
                obj_id creator = getCrafter(inv_contents[i]);
                if (isIdValid(creator))
                {
                    if (creator == player)
                    {
                        return inv_contents[i];
                    }
                }
            }
        }
        return null;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "Mol Ni'mai");
        attachScript(self, "npc.converse.npc_converse_menu");
        setCondition(self, CONDITION_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public boolean hasOldRisChest(obj_id player) throws InterruptedException
    {
        return (hasSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_chest_plate.iff"));
    }
    public boolean hasOldRisBoot(obj_id player) throws InterruptedException
    {
        return (hasSchematic(player, "object/draft_schematic/clothing/clothing_armor_ris_boots.iff"));
    }
    public void fullSchematicConversion(obj_id speaker) throws InterruptedException
    {
        revokeSchematic(speaker, "object/draft_schematic/armor/component/armor_layer_ris.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_bicep_l.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_bicep_r.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_bracer_l.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_bracer_r.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_chest_plate.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_gloves.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_helmet.iff");
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_leggings.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_enhancement_feather.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bicep_l.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bicep_r.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bracer_l.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_bracer_r.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_chest.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_gloves.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_helmet.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_leggings.iff");
        return;
    }
    public void bootSchematicConversion(obj_id speaker) throws InterruptedException
    {
        revokeSchematic(speaker, "object/draft_schematic/clothing/clothing_armor_ris_boots.iff");
        grantSchematic(speaker, "object/draft_schematic/armor/armor_appearance_ris_boots.iff");
        return;
    }
}
