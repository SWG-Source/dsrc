package script.space.special_loot;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.scenario;
import script.library.space_quest;
import script.library.space_create;
import script.library.ship_ai;
import script.library.utils;

public class encoded_document extends script.base_script
{
    public encoded_document()
    {
    }
    public static final string_id SID_USE = new string_id("space/encoded_doc", "use");
    public static final string_id SID_READ = new string_id("space/encoded_doc", "read");
    public static final string_id STORE_WAYPOINT = new string_id("space/encoded_doc", "stored_waypoint");
    public static final string_id WAYPOINT_EXISTS = new string_id("space/encoded_doc", "waypoint_exists");
    public static final string_id NOT_IN_INV = new string_id("space/encoded_doc", "not_in_inv");
    public static final string_id WRONG_ZONE = new string_id("space/encoded_doc", "wrong_planet");
    public static final string_id ACTION_WRONG_ZONE = new string_id("space/encoded_doc", "action_wrong_planet");
    public static final string_id NEED_WAYPOINT = new string_id("space/encoded_doc", "need_waypoint");
    public static final String SID_STORE_WAYPOINT = "@space/encoded_doc:store_waypoint";
    public static final String SID_CLOSE = "@space/encoded_doc:close";
    public static final String EVENT_TABLE = "datatables/spacequest/encoded_doc.iff";
    public static final String VAR_INDEX = "idx";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        dictionary eventEntry = scenario.getRandomScenario(EVENT_TABLE);
        if ((eventEntry == null) || (eventEntry.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = eventEntry.getInt(scenario.DICT_IDX);
        setObjVar(self, VAR_INDEX, idx);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        dictionary eventEntry = dataTableGetRow(EVENT_TABLE, getIntObjVar(self, VAR_INDEX));
        int mnu = mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_USE);
        mi.addSubMenu(mnu, menu_info_types.SERVER_MENU2, SID_READ);
        mi.addSubMenu(mnu, menu_info_types.SERVER_MENU3, new string_id("space/encoded_doc", eventEntry.getString("useOption")));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.SERVER_MENU2)
        {
            displayDialog(self, player);
        }
        else if (item == menu_info_types.SERVER_MENU3)
        {
            doAction(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getWaypoint(obj_id self, obj_id player) throws InterruptedException
    {
        location eventLoc = getLocationObjVar(self, "eventLoc");
        obj_id[] data = getWaypointsInDatapad(player);
        if (data != null)
        {
            for (int i = 0; i < data.length; i++)
            {
                if (data[i] == null)
                {
                    continue;
                }
                location waypointLoc = getWaypointLocation(data[i]);
                if ((waypointLoc != null) && (waypointLoc.equals(eventLoc)))
                {
                    return data[i];
                }
            }
        }
        return null;
    }
    public void displayDialog(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (!contains(inventory, self))
        {
            sendSystemMessage(player, NOT_IN_INV);
            return;
        }
        if (!dataTableOpen(EVENT_TABLE))
        {
            return;
        }
        dictionary eventEntry = dataTableGetRow(EVENT_TABLE, getIntObjVar(self, VAR_INDEX));
        String entryName = eventEntry.getString("eventName");
        String text = "@space/encoded_doc:text_" + entryName;
        String title = "@space/encoded_doc:title_" + entryName;
        if (hasObjVar(self, "map_text"))
        {
            text = "@space/encoded_doc:" + getStringObjVar(self, "map_text");
        }
        if (hasObjVar(self, "map_title"))
        {
            title = "@space/encoded_doc:" + getStringObjVar(self, "map_title");
        }
        createDialog(self, player, text, title);
    }
    public int createDialog(obj_id self, obj_id player, String text, String title) throws InterruptedException
    {
        if (player == null)
        {
            return -1;
        }
        int pid = sui.createSUIPage(sui.SUI_MSGBOX, self, player, "handleDialogInput");
        sui.setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, text);
        sui.setSUIProperty(pid, sui.MSGBOX_TITLE, sui.PROP_TEXT, title);
        sui.msgboxButtonSetup(pid, sui.OK_CANCEL);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_CANCEL, sui.PROP_TEXT, SID_STORE_WAYPOINT);
        sui.setSUIProperty(pid, sui.MSGBOX_BTN_OK, sui.PROP_TEXT, SID_CLOSE);
        sui.showSUIPage(pid);
        return pid;
    }
    public int handleDialogInput(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        int bp = sui.getIntButtonPressed(params);
        switch (bp)
        {
            case sui.BP_CANCEL:
            storeWaypoint(self, player);
            return SCRIPT_CONTINUE;
            case sui.BP_OK:
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void storeWaypoint(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary eventEntry = dataTableGetRow(EVENT_TABLE, getIntObjVar(self, VAR_INDEX));
        String scene = getCurrentSceneName();
        String docScene = eventEntry.getString("eventZone");
        if ((docScene == null) || !docScene.equals(scene))
        {
            sendSystemMessage(player, WRONG_ZONE);
            return;
        }
        location eventLoc;
        if (!hasObjVar(self, "storedLoc"))
        {
            int locx, locy, locz;
            if (rand() > 0.5)
            {
                locx = rand(0, 7000);
            }
            else 
            {
                locx = rand(0, 7000);
            }
            if (rand() > 0.5)
            {
                locy = rand(0, 7000);
            }
            else 
            {
                locy = rand(0, 7000);
            }
            if (rand() > 0.5)
            {
                locz = rand(0, 7000);
            }
            else 
            {
                locz = rand(0, 7000);
            }
            eventLoc = new location(locx, locy, locz);
            setObjVar(self, "storedLoc", eventLoc);
        }
        else 
        {
            eventLoc = getLocationObjVar(self, "storedLoc");
        }
        if (getWaypoint(self, player) != null)
        {
            sendSystemMessage(player, WAYPOINT_EXISTS);
            return;
        }
        obj_id waypoint = createWaypointInDatapad(player, eventLoc);
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, "Document Coordinates");
            setWaypointColor(waypoint, "space");
            sendSystemMessage(player, STORE_WAYPOINT);
        }
    }
    public void doAction(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary eventEntry = dataTableGetRow(EVENT_TABLE, getIntObjVar(self, VAR_INDEX));
        String entryName = eventEntry.getString("eventName");
        String scene = getCurrentSceneName();
        String docScene = eventEntry.getString("eventZone");
        if ((docScene == null) || !docScene.equals(scene))
        {
            sendSystemMessage(player, ACTION_WRONG_ZONE);
            return;
        }
        if (!hasObjVar(self, "storedLoc"))
        {
            sendSystemMessage(player, NEED_WAYPOINT);
            return;
        }
        location storedLoc = getLocationObjVar(self, "storedLoc");
        location myLoc = getLocation(player);
        float dist = getDistance(storedLoc, myLoc);
        if (dist > 300)
        {
            sendSystemMessage(player, new string_id("space/encoded_doc", "toofar_" + entryName));
            return;
        }
        sendSystemMessage(player, new string_id("space/encoded_doc", "trigger_" + entryName));
        dictionary outparams = new dictionary();
        outparams.put("player", player);
        messageTo(self, "timerDone", outparams, 30 + rand(30, 120), false);
    }
    public int timerDone(obj_id self, dictionary params) throws InterruptedException
    {
        dictionary eventEntry = dataTableGetRow(EVENT_TABLE, getIntObjVar(self, VAR_INDEX));
        String entryName = eventEntry.getString("eventName");
        String scene = getCurrentSceneName();
        String docScene = eventEntry.getString("eventZone");
        if ((docScene == null) || !docScene.equals(scene))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        transform myt = getTransform_o2w(player);
        transform attackorigin = space_quest.getRandomPositionInSphere(myt, 500, 600);
        String spawnList = eventEntry.getString("spawnList");
        String[] shipTypes = dataTableGetStringColumn("datatables/spacequest/attacklists/" + spawnList + ".iff", "shipType");
        int[] shipCounts = dataTableGetIntColumn("datatables/spacequest/attacklists/" + spawnList + ".iff", "shipCount");
        int[] shipBehaviors = dataTableGetIntColumn("datatables/spacequest/attacklists/" + spawnList + ".iff", "behavior");
        String[] patrolLists = dataTableGetStringColumn("datatables/spacequest/attacklists/" + spawnList + ".iff", "patrollist");
        for (int i = 0; i < shipTypes.length; i++)
        {
            for (int j = 0; j < shipCounts[i]; j++)
            {
                transform spawnLoc = space_quest.getRandomPositionInSphere(attackorigin, 50, 100);
                obj_id newship = space_create.createShip(shipTypes[i], spawnLoc, null);
                switch (shipBehaviors[i])
                {
                    case 0:
                    ship_ai.spaceAttack(newship, getPilotedShip(player));
                    break;
                    case 1:
                    break;
                    case 2:
                    String[] navPoints = dataTableGetStringColumn("datatables/spacequest/navlists/" + patrolLists[i] + ".iff", "navPoints");
                    transform[] epts = getEnemyPatrolTransforms(self, navPoints);
                    ship_ai.spacePatrol(newship, epts);
                    break;
                }
            }
        }
        sendSystemMessage(player, new string_id("space/encoded_doc", "event_" + entryName));
        return SCRIPT_CONTINUE;
    }
    public transform[] getEnemyPatrolTransforms(obj_id self, String[] navPoints) throws InterruptedException
    {
        obj_id questManager = getNamedObject(space_quest.QUEST_MANAGER);
        if (questManager == null)
        {
            return null;
        }
        obj_id[] points = utils.getObjIdArrayScriptVar(questManager, "nav_list");
        transform[] translist = new transform[navPoints.length];
        for (int j = 0; j < navPoints.length; j++)
        {
            for (int i = 0; i < points.length; i++)
            {
                String pointName = getStringObjVar(points[i], "nav_name");
                String nName = navPoints[j];
                java.util.StringTokenizer st = new java.util.StringTokenizer(nName, ":");
                String scene = st.nextToken();
                nName = st.nextToken();
                if ((pointName != null) && pointName.equals(nName))
                {
                    translist[j] = getTransform_o2w(points[i]);
                    break;
                }
            }
        }
        return translist;
    }
}
