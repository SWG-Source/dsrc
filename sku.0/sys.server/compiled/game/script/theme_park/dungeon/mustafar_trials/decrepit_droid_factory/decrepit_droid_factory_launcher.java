package script.theme_park.dungeon.mustafar_trials.decrepit_droid_factory;

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

public class decrepit_droid_factory_launcher extends script.base_script
{
    public decrepit_droid_factory_launcher()
    {
    }
    public static final string_id SID_UNABLE_TO_FIND_DUNGEON = new string_id("dungeon/space_dungeon", "unable_to_find_dungeon");
    public static final string_id SID_NO_TICKET = new string_id("dungeon/space_dungeon", "no_ticket");
    public static final string_id SID_REQUEST_TRAVEL = new string_id("dungeon/space_dungeon", "request_travel");
    public static final string_id SID_REQUEST_TRAVEL_OUTSTANDIN = new string_id("dungeon/space_dungeon", "request_travel_outstanding");
    public static final string_id SID_UPLINK_CAVE = new string_id("travel/zone_transition", "decrepit_droid_factory");
    public static final string_id SID_NO_PERMISSION = new string_id("travel/zone_transition", "default_no_access");
    public static final string_id SID_MUST_CHOOSE = new string_id("som/som_quest", "need_to_choose_destination");
    public static final string_id ACCESS = new string_id("som/som_quest", "access_destination");
    public static final string_id SID_PART1 = new string_id("som/som_quest", "decrepit_droid_factory");
    public static final string_id SID_PART2 = new string_id("som/som_quest", "working_droid_factory");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE, ACCESS);
        if (canEnterDecrepit(player))
        {
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU1, SID_PART1);
        }
        if (canEnterWorking(player))
        {
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU2, SID_PART2);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU1)
        {
            doBackflagging(player);
            instance.requestInstanceMovement(player, "decrepit_droid_factory");
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU2)
        {
            doBackflagging(player);
            instance.requestInstanceMovement(player, "working_droid_factory");
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE && hasCorrectQuests(player))
        {
            sendSystemMessage(player, SID_MUST_CHOOSE);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            sendSystemMessage(player, SID_NO_PERMISSION);
            return SCRIPT_CONTINUE;
        }
    }
    public boolean hasCorrectQuests(obj_id player) throws InterruptedException
    {
        boolean hasCorrectQuests = false;
        if (groundquests.isQuestActive(player, "som_story_arc_chapter_two_01") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_two_01"))
        {
            hasCorrectQuests = true;
        }
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_droidfactory_two") || groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_milo_report") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_01"))
        {
            hasCorrectQuests = true;
        }
        return hasCorrectQuests;
    }
    public boolean canEnterDecrepit(obj_id player) throws InterruptedException
    {
        return instance.isFlaggedForInstance(player, "decrepit_droid_factory") || groundquests.isQuestActive(player, "som_story_arc_chapter_two_01") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_two_01");
    }
    public boolean canEnterWorking(obj_id player) throws InterruptedException
    {
        return instance.isFlaggedForInstance(player, "working_droid_factory") || groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_droidfactory_two") || groundquests.isTaskActive(player, "som_story_arc_chapter_three_01", "mustafar_milo_report") || groundquests.hasCompletedQuest(player, "som_story_arc_chapter_three_01");
    }
    public void doBackflagging(obj_id player) throws InterruptedException
    {
        if (canEnterDecrepit(player))
        {
            instance.flagPlayerForInstance(player, "decrepit_droid_factory");
        }
        if (canEnterWorking(player))
        {
            instance.flagPlayerForInstance(player, "working_droid_factory");
        }
    }
}
