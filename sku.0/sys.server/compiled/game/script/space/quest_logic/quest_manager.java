package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_quest;
import script.library.space_utils;
import script.library.utils;

public class quest_manager extends script.base_script
{
    public quest_manager()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        registerNamedObject(space_quest.QUEST_MANAGER, self);
        return SCRIPT_CONTINUE;
    }
    public int registerQuestLocation(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id new_point = params.getObjId("point");
        String new_point_name = getStringObjVar(new_point, "nav_name");
        String type = params.getString("type");
        obj_id[] points = utils.getObjIdArrayScriptVar(self, type + "_list");
        if (points == null)
        {
            points = new obj_id[1];
            points[0] = new_point;
            utils.setScriptVar(self, type + "_list", points);
        }
        else 
        {
            obj_id[] updated_points = new obj_id[points.length + 1];
            for (int i = 0; i < points.length; i++)
            {
                if (points[i] == new_point)
                {
                    debugServerConsoleMsg(self, "Quest Manager: Ignoring a duplicate registerQuestLocation request for object " + new_point);
                    return SCRIPT_CONTINUE;
                }
                String point_name = getStringObjVar(points[i], "nav_name");
                if ((point_name != null) && point_name.equals(new_point_name))
                {
                    debugServerConsoleMsg(self, "Quest Manager: Tried to register a quest point with a duplicate name. " + new_point + " " + points[i]);
                    return SCRIPT_CONTINUE;
                }
                updated_points[i] = points[i];
            }
            updated_points[points.length] = new_point;
            utils.setScriptVar(self, type + "_list", updated_points);
        }
        return SCRIPT_CONTINUE;
    }
    public int createWaypointToSpawner(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id quest = params.getObjId("quest");
        obj_id player = params.getObjId("player");
        String name = params.getString("name");
        String spawner = params.getString("spawner");
        int taskId = params.getInt("taskId");
        String questName = params.getString("questName");
        int questId = params.getInt("questId");
        obj_id[] navs = utils.getObjIdArrayScriptVar(self, "spawner_list");
        if (navs == null)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < navs.length; i++)
        {
            String curSpawnName = getStringObjVar(navs[i], "strSpawnerName");
            if ((curSpawnName != null) && curSpawnName.equals(spawner))
            {
                location loc = getLocation(navs[i]);
                obj_id waypoint = createWaypointInDatapad(player, loc);
                setWaypointVisible(waypoint, true);
                setWaypointActive(waypoint, true);
                setWaypointName(waypoint, name);
                setWaypointColor(waypoint, "space");
                questActivateTask(questId, taskId, player);
                questSetQuestTaskLocation(player, questName, taskId, loc);
                dictionary outparams = new dictionary();
                outparams.put("waypoint", waypoint);
                messageTo(quest, "createdWaypointToSpawner", outparams, 1.f, false);
                return SCRIPT_CONTINUE;
            }
        }
        if (navs != null)
        {
            sendSystemMessageTestingOnly(player, "Error: Failed to find spawner '" + spawner + "'.  " + navs.length + " spawners were searched.  The nav point object may not exist or your server may need to specify 'createZoneObjects=1'");
        }
        else 
        {
            sendSystemMessageTestingOnly(player, "Error: Failed to find spawner '" + spawner + "'.  No spawners were registered!  The nav point object may not exist or your server may need to specify 'createZoneObjects=1'");
        }
        return SCRIPT_CONTINUE;
    }
}
