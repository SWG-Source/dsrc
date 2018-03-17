package script.systems.collections.publish_gift;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.collection;
import script.library.locations;
import script.library.groundquests;
import script.library.mustafar;
import script.library.sui;
import script.library.utils;

public class chapter_8_quest_object extends script.base_script
{
    public chapter_8_quest_object()
    {
    }
    public static final string_id RADIAL_INSPECT = new string_id("quest/ground/util/quest_giver_object", "radial_inspect");
    public static final string_id OFFER_QUEST_MSG = new string_id("quest/ground/util/quest_giver_object", "offer_quest");
    public static final string_id SUI_TITLE = new string_id("quest/ground/util/quest_giver_object", "sui_title");
    public static final string_id BUTTON_DECLINE = new string_id("quest/ground/util/quest_giver_object", "button_decline");
    public static final string_id BUTTON_ACCEPT = new string_id("quest/ground/util/quest_giver_object", "button_accept");
    public static final string_id DECLINED_QUEST = new string_id("quest/ground/util/quest_giver_object", "declined_quest");
    public static final string_id NOT_WHILE_INCAPPED = new string_id("quest/ground/util/quest_giver_object", "not_while_incapped");
    public static final string_id SID_NOT_WHILE_IN_COMBAT = new string_id("base_player", "not_while_in_combat");
    public static final String ACTIVATION_SLOT_NAME = "chapter_collection_activation";
    public static final String PID_NAME = "chapter8_starter_quest_pid";
    public static final String QUEST_NAME = "chapter8_publish_gift_activation";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menuOption = mi.addRootMenu(menu_info_types.ITEM_USE, RADIAL_INSPECT);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (getState(player, STATE_COMBAT) > 0)
            {
                sendSystemMessage(player, SID_NOT_WHILE_IN_COMBAT);
                return SCRIPT_CONTINUE;
            }
            if (isDead(player) || isIncapacitated(player))
            {
                sendSystemMessage(player, NOT_WHILE_INCAPPED);
                return SCRIPT_CONTINUE;
            }
            if (!groundquests.isQuestActive(player, "chapter8_publish_gift_activation"))
            {
                mustafar.activateQuestAcceptSUI(player, self);
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
        if (!isIdValid(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (hasSkill(player, "pilot_neutral_novice") || hasSkill(player, "pilot_rebel_navy_novice") || hasSkill(player, "pilot_imperial_navy_novice"))
        {
            switch (bp)
            {
                case sui.BP_OK:
                groundquests.grantQuest(player, "chapter8_publish_gift_is_pilot");
                modifyCollectionSlotValue(player, ACTIVATION_SLOT_NAME, 1);
                destroySelf(self);
                break;
                case sui.BP_CANCEL:
                sendSystemMessage(player, DECLINED_QUEST);
                break;
            }
        }
        else 
        {
            switch (bp)
            {
                case sui.BP_OK:
                groundquests.grantQuest(player, "chapter8_publish_gift_activation");
                modifyCollectionSlotValue(player, ACTIVATION_SLOT_NAME, 1);
                destroySelf(self);
                break;
                case sui.BP_CANCEL:
                sendSystemMessage(player, DECLINED_QUEST);
                break;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void destroySelf(obj_id self) throws InterruptedException
    {
        detachScript(self, "item.special.nodestroy");
        destroyObject(self);
    }
}
