package script.space.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_transition;
import script.library.utils;

public class interdiction_terminal extends script.base_script
{
    public interdiction_terminal()
    {
    }
    public static final string_id SID_ACTIVATE_FALSE = new string_id("space/space_terminal", "activate_unencoded");
    public static final string_id SID_ACTIVATE_WAYPOINT = new string_id("space/space_terminal", "create_waypoint");
    public static final string_id SID_ACTIVATE_EVENT = new string_id("space/space_terminal", "activate_event");
    public static final string_id SID_NO_USE = new string_id("space/space_terminal", "no_use");
    public static final String[] VALID_REGIONS = 
    {
        "space_tatooine",
        "space_corellia",
        "space_dantooine",
        "space_dathomir",
        "space_endor",
        "space_lok",
        "space_naboo",
        "space_yavin4"
    };
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "waypoint"))
        {
            if (isIdValid(getObjIdObjVar(self, "waypoint")))
            {
                obj_id player = utils.getContainingPlayer(self);
                obj_id waypoint = getObjIdObjVar(self, "waypoint");
                if (isIdValid(player) && exists(player) && (isIdValid(waypoint)))
                {
                    destroyWaypointInDatapad(waypoint, player);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        boolean isNested = true;
        if (!utils.isNestedWithin(self, player))
        {
            isNested = false;
        }
        boolean readyForEvent = false;
        if (hasObjVar(self, "region") && hasObjVar(self, "location") && hasObjVar(self, "difficulty"))
        {
            if (!isRegionValid(self))
            {
                return SCRIPT_CONTINUE;
            }
            readyForEvent = true;
        }
        boolean inSpaceShip = false;
        obj_id ship = space_transition.getContainingShip(player);
        if (isSpaceScene() && isIdValid(ship))
        {
            obj_id pilot = getPilotId(ship);
            if (player == pilot)
            {
                inSpaceShip = true;
            }
        }
        if (isNested == false)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_FALSE);
            return SCRIPT_CONTINUE;
        }
        if (readyForEvent == false)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_FALSE);
        }
        if (readyForEvent == true && inSpaceShip == false)
        {
            mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_WAYPOINT);
        }
        if (readyForEvent == true && inSpaceShip == true)
        {
            if (atLocation(self, player))
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_EVENT);
            }
            else 
            {
                mi.addRootMenu(menu_info_types.ITEM_USE, SID_ACTIVATE_WAYPOINT);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self) || item <= 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, "The Burst Generator must be used from your inventory.", null);
            return SCRIPT_CONTINUE;
        }
        boolean inSpaceShip = false;
        obj_id ship = space_transition.getContainingShip(player);
        if (isSpaceScene() && isIdValid(ship))
        {
            obj_id pilot = getPilotId(ship);
            if (player == pilot)
            {
                inSpaceShip = true;
            }
        }
        if (item == menu_info_types.ITEM_USE && !hasObjVar(self, "difficulty"))
        {
            sendSystemMessage(player, "You cannot use the Burst Generator until it has been encoded with a convoy flight plan.", null);
        }
        if (item == menu_info_types.ITEM_USE && hasObjVar(self, "difficulty") && !atLocation(self, player))
        {
            LOG("mikkel-space", "calling create WP");
            createWaypoint(self, player);
        }
        if (item == menu_info_types.ITEM_USE && atLocation(self, player))
        {
            if (utils.hasScriptVar(player, "interdiction_beacon"))
            {
                obj_id oldBeacon = utils.getObjIdScriptVar(self, "interdiction_beacon");
                if (isIdValid(oldBeacon) && exists(oldBeacon))
                {
                    return SCRIPT_CONTINUE;
                }
                utils.removeScriptVar(self, "interdiction_beacon");
            }
            startEvent(self, player);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (hasObjVar(self, "difficulty"))
        {
            names[idx] = "difficulty";
            int difficulty = getIntObjVar(self, "difficulty");
            attribs[idx] = " " + difficulty;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "region"))
        {
            names[idx] = "region";
            String region = getStringObjVar(self, "region");
            string_id planetName = new string_id("planet_n", region);
            attribs[idx] = " " + localize(planetName);
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, "faction"))
        {
            names[idx] = "faction";
            String faction = getStringObjVar(self, "faction");
            attribs[idx] = " " + faction;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (idx >= names.length)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void createWaypoint(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return;
        }
        if (hasObjVar(self, "waypoint"))
        {
            obj_id waypoint = getObjIdObjVar(self, "waypoint");
            if (isIdValid(waypoint))
            {
                destroyWaypointInDatapad(waypoint, player);
            }
        }
        String region = getStringObjVar(self, "region");
        if (region == null || region.length() <= 0 || !isRegionValid(self))
        {
            return;
        }
        location loc = getLocationObjVar(self, "location");
        obj_id waypoint = createWaypointInDatapad(player, loc);
        if (isIdValid(waypoint))
        {
            setWaypointVisible(waypoint, true);
            setWaypointActive(waypoint, true);
            setWaypointName(waypoint, "Interdiction Location");
            setWaypointColor(waypoint, "space");
            setObjVar(self, "waypoint", waypoint);
        }
        return;
    }
    public boolean isRegionValid(obj_id self) throws InterruptedException
    {
        if (!isIdValid(self))
        {
            return false;
        }
        String region = getStringObjVar(self, "region");
        boolean isRegionValid = false;
        for (int i = 0; i < VALID_REGIONS.length; i++)
        {
            if (region.equals(VALID_REGIONS[i]))
            {
                return true;
            }
        }
        return false;
    }
    public boolean atLocation(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return false;
        }
        obj_id ship = space_transition.getContainingShip(player);
        location eventLoc = getLocationObjVar(self, "location");
        float fltDistance = getDistance(getLocation(ship), eventLoc);
        if (fltDistance < 200 && fltDistance >= 0)
        {
            return true;
        }
        return false;
    }
    public void startEvent(obj_id self, obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(self))
        {
            return;
        }
        String region = getStringObjVar(self, "region");
        if (getCurrentSceneName() == region || !atLocation(self, player))
        {
            return;
        }
        int difficulty = getIntObjVar(self, "difficulty");
        if (difficulty <= 0)
        {
            return;
        }
        transform gloc = getTransform_o2w(space_transition.getContainingShip(player));
        float dist = rand(50.f, 100.f);
        vector n = ((gloc.getLocalFrameK_p()).normalize()).multiply(dist);
        gloc = gloc.move_p(n);
        vector vi = ((gloc.getLocalFrameI_p()).normalize()).multiply(rand(-20.f, 20.f));
        vector vj = ((gloc.getLocalFrameJ_p()).normalize()).multiply(rand(-20.f, 20.f));
        vector vd = vi.add(vj);
        gloc = gloc.move_p(vd);
        obj_id spaceBeacon = createObject("object/tangible/space/mission_objects/interdiction_beacon.iff", gloc, null);
        setObjVar(spaceBeacon, "player", player);
        setObjVar(spaceBeacon, "difficulty", difficulty);
        attachScript(spaceBeacon, "space.quest_logic.piracy");
        LOG("mikkel-space", "created bacon, OID is: " + spaceBeacon);
        utils.setScriptVar(player, "interdiction_beacon", spaceBeacon);
        messageTo(spaceBeacon, "startBeacon", null, 1.f, false);
        destroyObject(self);
    }
}
