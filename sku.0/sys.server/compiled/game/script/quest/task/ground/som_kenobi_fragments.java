package script.quest.task.ground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.sui;
import script.library.utils;

public class som_kenobi_fragments extends script.base_script
{
    public som_kenobi_fragments()
    {
    }
    public static final string_id RADIAL_INSPECT = new string_id("quest/ground/util/quest_giver_object", "radial_inspect");
    public static final string_id OFFER_QUEST_MSG = new string_id("quest/ground/util/quest_giver_object", "offer_quest");
    public static final string_id SUI_TITLE = new string_id("quest/ground/util/quest_giver_object", "sui_title");
    public static final string_id BUTTON_DECLINE = new string_id("quest/ground/util/quest_giver_object", "button_decline");
    public static final string_id BUTTON_ACCEPT = new string_id("quest/ground/util/quest_giver_object", "button_accept");
    public static final string_id ALREADY_COMPLETED_QUEST = new string_id("quest/ground/util/quest_giver_object", "already_completed_quest");
    public static final string_id ALREADY_HAS_QUEST = new string_id("quest/ground/util/quest_giver_object", "already_has_quest");
    public static final string_id DECLINED_QUEST = new string_id("quest/ground/util/quest_giver_object", "declined_quest");
    public static final string_id OBJECT_UPLOADED = new string_id("quest/ground/util/quest_giver_object", "object_uploaded");
    public static final string_id IMPERIAL_FACTION_REQ = new string_id("quest/ground/util/quest_giver_object", "imperial_faction_required");
    public static final string_id REBEL_FACTION_REQ = new string_id("quest/ground/util/quest_giver_object", "rebel_faction_required");
    public static final String DATATABLE = "datatables/quest/ground/quest_giver_object.iff";
    public static final String QUEST_NAME = "quest_name";
    public static final String QUEST_OFFER_TEXT = "quest_offer_text";
    public static final String QUEST_IS_REPEATABLE = "quest_is_repeatable";
    public static final String DO_NOT_DESTROY = "do_not_destroy";
    public static final String IMPERIAL_ONLY = "imperial_only";
    public static final String REBEL_ONLY = "rebel_only";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        String myTemplate = getTemplateName(self);
        dictionary objectQuestData = dataTableGetRow(DATATABLE, myTemplate);
        if (objectQuestData != null)
        {
            String questName = objectQuestData.getString(QUEST_NAME);
            if (questName != null && questName.length() > 0)
            {
                if (groundquests.isValidQuestName(questName))
                {
                    int menuOption = mi.addRootMenu(menu_info_types.ITEM_USE, RADIAL_INSPECT);
                }
                else 
                {
                    if (isGod(player))
                    {
                        String errorMsg = "GODMODE MSG: questName provided for " + getTemplateName(self) + " in " + DATATABLE + " is invalid.";
                        sendSystemMessageTestingOnly(player, errorMsg);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (utils.hasScriptVar(player, "questGiver.openSui"))
            {
                int oldSui = utils.getIntScriptVar(player, "questGiver.openSui");
                utils.removeScriptVar(player, "questGiver.openSui");
                if (oldSui > -1)
                {
                    forceCloseSUIPage(oldSui);
                }
            }
            string_id sid_offerText = SUI_TITLE;
            String myTemplate = getTemplateName(self);
            dictionary objectQuestData = dataTableGetRow(DATATABLE, myTemplate);
            if (objectQuestData != null)
            {
                String newOfferText = objectQuestData.getString(QUEST_OFFER_TEXT);
                if (newOfferText != null && newOfferText.length() > 0)
                {
                    sid_offerText = new string_id("quest/ground/util/quest_giver_object", newOfferText);
                }
            }
            String title = utils.packStringId(sid_offerText);
            String testMsg = utils.packStringId(OFFER_QUEST_MSG);
            String ok_button = utils.packStringId(BUTTON_ACCEPT);
            String cancel_button = utils.packStringId(BUTTON_DECLINE);
            int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleQuestOfferResponse");
            setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
            setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, testMsg);
            sui.msgboxButtonSetup(pid, sui.YES_NO);
            setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, ok_button);
            setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, cancel_button);
            utils.setScriptVar(player, "questGiver.openSui", pid);
            sui.showSUIPage(pid);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestOfferResponse(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        String myTemplate = getTemplateName(self);
        dictionary objectQuestData = dataTableGetRow(DATATABLE, myTemplate);
        if (objectQuestData != null)
        {
            String questName = objectQuestData.getString(QUEST_NAME);
            if (questName != null && questName.length() > 0)
            {
                int bp = sui.getIntButtonPressed(params);
                int imperialOnly = objectQuestData.getInt(IMPERIAL_ONLY);
                int rebelOnly = objectQuestData.getInt(REBEL_ONLY);
                int playerFaction = pvpGetAlignedFaction(player);
                if (imperialOnly > 0)
                {
                    if (playerFaction != (-615855020))
                    {
                        sendSystemMessage(player, IMPERIAL_FACTION_REQ);
                        return SCRIPT_CONTINUE;
                    }
                }
                else if (rebelOnly > 0)
                {
                    if (playerFaction != (370444368))
                    {
                        sendSystemMessage(player, REBEL_FACTION_REQ);
                        return SCRIPT_CONTINUE;
                    }
                }
                int questIsRepeatable = objectQuestData.getInt(QUEST_IS_REPEATABLE);
                if (!groundquests.hasCompletedQuest(player, "som_kenobi_reunite_shard_3"))
                {
                    if (!groundquests.isQuestActive(player, "som_kenobi_reunite_shard_1"))
                    {
                        if (!groundquests.isQuestActive(player, "som_kenobi_reunite_shard_2"))
                        {
                            if (!groundquests.isQuestActive(player, "som_kenobi_reunite_shard_3"))
                            {
                                switch (bp)
                                {
                                    case sui.BP_OK:
                                    groundquests.grantQuest(player, questName);
                                    int doNotDestroy = objectQuestData.getInt(DO_NOT_DESTROY);
                                    if (doNotDestroy < 1)
                                    {
                                        sendSystemMessage(player, OBJECT_UPLOADED);
                                        destroyObject(self);
                                    }
                                    break;
                                    case sui.BP_CANCEL:
                                    sendSystemMessage(player, DECLINED_QUEST);
                                    break;
                                }
                            }
                            else 
                            {
                                sendSystemMessage(player, ALREADY_HAS_QUEST);
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, ALREADY_HAS_QUEST);
                        }
                    }
                    else 
                    {
                        sendSystemMessage(player, ALREADY_HAS_QUEST);
                    }
                }
                else 
                {
                    sendSystemMessage(player, ALREADY_COMPLETED_QUEST);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
