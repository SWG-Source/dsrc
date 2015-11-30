package script.theme_park.dungeon.mustafar_trials.establish_the_link;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.instance;

public class establish_the_link_launcher extends script.base_script
{
    public establish_the_link_launcher()
    {
    }
    public static final string_id SID_UPLINK_CAVE = new string_id("dungeon/space_dungeon", "uplink_cave");
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
            instance.requestInstanceMovement(player, "uplink_cave");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doBackflagging(obj_id player) throws InterruptedException
    {
        boolean doFlagging = false;
        if (instance.isFlaggedForInstance(player, "uplink_cave"))
        {
            return true;
        }
        if (groundquests.isTaskActive(player, "som_story_arc_chapter_one_03", "mustafar_uplink_one"))
        {
            doFlagging = true;
        }
        if (groundquests.hasCompletedTask(player, "som_story_arc_chapter_one_03", "mustafar_uplink_one"))
        {
            doFlagging = true;
        }
        if (groundquests.hasCompletedQuest(player, "som_story_arc_chapter_one_03"))
        {
            doFlagging = true;
        }
        if (doFlagging)
        {
            instance.flagPlayerForInstance(player, "uplink_cave");
        }
        return doFlagging;
    }
}
