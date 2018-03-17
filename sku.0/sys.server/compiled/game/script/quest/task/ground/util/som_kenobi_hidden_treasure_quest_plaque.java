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

public class som_kenobi_hidden_treasure_quest_plaque extends script.base_script
{
    public som_kenobi_hidden_treasure_quest_plaque()
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
    public static final String QUEST_NAME_OBJVAR = "questGiver.questName";
    public static final String QUEST_IS_REPEATABLE = "questGiver.questRepeatable";
    public static final String NO_DELETE_OBJVAR = "questGiver.noDelete";
    public static final String OFFER_TEXT_OBJVAR = "questGiver.offerText";
    public static final String IMPERIAL_ONLY_OBJVAR = "questGiver.imperialOnly";
    public static final String REBEL_ONLY_OBJVAR = "questGiver.rebelOnly";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (groundquests.isQuestActive(player, "som_kenobi_hidden_treasure_2") || groundquests.hasCompletedQuest(player, "som_kenobi_hidden_treasure_2"))
        {
            return SCRIPT_OVERRIDE;
        }
        else if (hasObjVar(self, QUEST_NAME_OBJVAR))
        {
            String questName = getStringObjVar(self, QUEST_NAME_OBJVAR);
            if (isGod(player))
            {
                sendSystemMessageTestingOnly(player, "GODMODE MSG: quest name provided with questGiver.questName objvar is invalid.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
