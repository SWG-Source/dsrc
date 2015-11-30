package script.quest.task.ground.util;

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

public class quest_giver_object extends script.base_script
{
    public quest_giver_object()
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
    public static final string_id NO_FURTHER_INTEREST = new string_id("quest/groundquests", "retrieve_item_already_used");
    public static final String DATATABLE = "datatables/quest/ground/quest_giver_object.iff";
    public static final String QUEST_NAME = "quest_name";
    public static final String PRE_QUEST_NAME = "pre_quest_name";
    public static final String QUEST_OFFER_TEXT = "quest_offer_text";
    public static final String QUEST_IS_REPEATABLE = "quest_is_repeatable";
    public static final String DO_NOT_DESTROY = "do_not_destroy";
    public static final String IMPERIAL_ONLY = "imperial_only";
    public static final String REBEL_ONLY = "rebel_only";
    public static final String[] TRADER_PROFESSION_QUEST_STARTERS = 
    {
        "object/tangible/quest/quest_start/profession_trader_10.iff",
        "object/tangible/quest/quest_start/profession_trader_20.iff",
        "object/tangible/quest/quest_start/profession_trader_30.iff"
    };
    public static final String[] ENT_PROFESSION_QUEST_STARTERS = 
    {
        "object/tangible/quest/quest_start/profession_entertainer_10.iff",
        "object/tangible/quest/quest_start/profession_entertainer_20.iff",
        "object/tangible/quest/quest_start/profession_entertainer_30.iff"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String template = getTemplateName(self);
        if (template.endsWith("_30.iff"))
        {
            utils.replaceSnowflakeItem(self, template);
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < TRADER_PROFESSION_QUEST_STARTERS.length; ++i)
        {
            if (template.equals(TRADER_PROFESSION_QUEST_STARTERS[i]))
            {
                utils.replaceSnowflakeItem(self, template);
                return SCRIPT_CONTINUE;
            }
            if (template.equals(ENT_PROFESSION_QUEST_STARTERS[i]))
            {
                utils.replaceSnowflakeItem(self, template);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
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
            String myTemplate = getTemplateName(self);
            dictionary objectQuestData = dataTableGetRow(DATATABLE, myTemplate);
            if (objectQuestData != null)
            {
                String questName = objectQuestData.getString(QUEST_NAME);
                if (questName == null || questName.length() <= 0 || !groundquests.isValidQuestName(questName))
                {
                    if (isGod(player))
                    {
                        String errorMsg = "GODMODE MSG: questName provided for " + getTemplateName(self) + " in " + DATATABLE + " is invalid.";
                        sendSystemMessage(player, errorMsg, "");
                    }
                    return SCRIPT_CONTINUE;
                }
                int questIsRepeatable = objectQuestData.getInt(QUEST_IS_REPEATABLE);
                if (groundquests.isQuestActiveOrComplete(player, questName) && questIsRepeatable <= 0)
                {
                    sendSystemMessage(player, NO_FURTHER_INTEREST);
                    return SCRIPT_CONTINUE;
                }
                if (utils.hasScriptVar(player, "questGiver.openSui"))
                {
                    int oldSui = utils.getIntScriptVar(player, "questGiver.openSui");
                    utils.removeScriptVar(player, "questGiver.openSui");
                    if (oldSui > -1)
                    {
                        forceCloseSUIPage(oldSui);
                    }
                }
                string_id sid_offerText = OFFER_QUEST_MSG;
                String newOfferText = objectQuestData.getString(QUEST_OFFER_TEXT);
                if (newOfferText != null && newOfferText.length() > 0)
                {
                    sid_offerText = new string_id("quest/ground/util/quest_giver_object", newOfferText);
                }
                String title = utils.packStringId(SUI_TITLE);
                String testMsg = utils.packStringId(sid_offerText);
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
        if (utils.hasScriptVar(player, "questGiver.openSui"))
        {
            utils.removeScriptVar(player, "questGiver.openSui");
        }
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
                if (!groundquests.hasCompletedQuest(player, questName) || questIsRepeatable > 0)
                {
                    if (!groundquests.isQuestActive(player, questName))
                    {
                        switch (bp)
                        {
                            case sui.BP_OK:
                            String preQuestName = objectQuestData.getString(PRE_QUEST_NAME);
                            if (preQuestName != null && preQuestName.length() > 0)
                            {
                                if (!groundquests.hasCompletedQuest(player, preQuestName))
                                {
                                    if (!groundquests.isQuestActive(player, preQuestName))
                                    {
                                        groundquests.grantQuest(player, preQuestName);
                                        checkForDestroy(self, player, objectQuestData);
                                        break;
                                    }
                                    else 
                                    {
                                        sendSystemMessage(player, ALREADY_HAS_QUEST);
                                        break;
                                    }
                                }
                            }
                            groundquests.grantQuest(player, questName);
                            checkForDestroy(self, player, objectQuestData);
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
                    sendSystemMessage(player, ALREADY_COMPLETED_QUEST);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void checkForDestroy(obj_id self, obj_id player, dictionary objectQuestData) throws InterruptedException
    {
        int doNotDestroy = objectQuestData.getInt(DO_NOT_DESTROY);
        if (doNotDestroy < 1)
        {
            sendSystemMessage(player, OBJECT_UPLOADED);
            destroyObject(self);
        }
        return;
    }
}
