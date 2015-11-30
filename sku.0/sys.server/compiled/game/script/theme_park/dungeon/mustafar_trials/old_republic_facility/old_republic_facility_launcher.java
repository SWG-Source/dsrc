package script.theme_park.dungeon.mustafar_trials.old_republic_facility;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.space_dungeon;
import script.library.space_dungeon_data;
import script.library.utils;
import script.library.sui;
import script.library.prose;
import script.library.pet_lib;
import script.library.instance;

public class old_republic_facility_launcher extends script.base_script
{
    public old_republic_facility_launcher()
    {
    }
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_REQUEST_TRAVEL = new string_id("dungeon/space_dungeon", "request_travel");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDIN = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public static final string_id SID_UPLINK_CAVE = new string_id("travel/zone_transition", "old_republic_facility");
    public static final string_id SID_NO_PERMISSION = new string_id("travel/zone_transition", "default_no_access");
    public static final string_id SID_PLAYER_DEAD = new string_id("dungeon/space_dungeon", "player_dead");
    public static final string_id SID_PLAYER_INCAP = new string_id("dungeon/space_dungeon", "player_incap");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (getDistance(player, self) > 6.0f)
        {
            return SCRIPT_CONTINUE;
        }
        item.addRootMenu(menu_info_types.ITEM_USE, SID_UPLINK_CAVE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            doBackflagging(player);
            instance.requestInstanceMovement(player, "old_republic_facility");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doBackflagging(obj_id player) throws InterruptedException
    {
        if (instance.isFlaggedForInstance(player, "old_republic_facility"))
        {
            return true;
        }
        boolean doFlagging = false;
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_one_03", "mustafar_uplink_two"))
        {
            doFlagging = true;
        }
        if (groundquests.hasCompletedTask(player, "som_story_arc_chapter_one_03", "mustafar_uplink_two"))
        {
            doFlagging = true;
        }
        if (groundquests.hasCompletedQuest(player, "som_story_arc_chapter_one_03"))
        {
            doFlagging = true;
        }
        if (groundquests.isQuestActive(player, "som_kenobi_historian_1"))
        {
            doFlagging = true;
        }
        if (groundquests.isQuestActive(player, "som_kenobi_historian_smuggler"))
        {
            doFlagging = true;
        }
        if (groundquests.isQuestActive(player, "som_kenobi_reunite_shard_3"))
        {
            doFlagging = true;
        }
        if (doFlagging)
        {
            instance.flagPlayerForInstance(player, "old_republic_facility");
        }
        return doFlagging;
    }
}
