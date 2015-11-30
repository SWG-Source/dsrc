package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.qa;
import script.library.sui;

public class qadatapad extends script.base_script
{
    public qadatapad()
    {
    }
    public static final String SCRIPTVAR = "qadatapad";
    public static final String DATAPAD_TOOL_TITLE = "QA DATAPAD TOOL";
    public static final String DATAPAD_TOOL_PROMPT = "This tool allows the tester to view, warp, export and import into the datapad";
    public static final int FILL_WAYPOINTS = 0;
    public static final int CONTROL_DEVICES = 1;
    public static final String[] DATAPAD_TOOL_MENU = 
    {
        "Warp to Waypoints",
        "Export Waypoints"
    };
    public static final String[] ADD_ON_DATAPAD_MENU = 
    {
        "FILL WAYPOINTS",
        "CLEAR ALL WAYPOINTS"
    };
    public static final String[][] WAYPOINT_STRING_MULTIARRAY = 
    {
        
        {
            "yavin4",
            "space_yavin4",
            "kashyyyk_main",
            "mustafar",
            "mustafar",
            "tatooine",
            "tatooine",
            "yavin4",
            "yavin4",
            "endor",
            "naboo",
            "tatooine",
            "yavin4",
            "dathomir",
            "rori",
            "corellia"
        },
        
        {
            "Beach",
            "Yavin 4 Station",
            "Kachirho",
            "New Mining Facility",
            "Old Mining Facility",
            "Lt. Akal Colzet (Imperial Pilot Trainer)",
            "Commander Oberhaur",
            "Field Commander Alozen",
            "Captain Denner",
            "Admiral Kilnstrider",
            "Grand Admiral Nial Declann",
            "Commander Da'la Socuna",
            "Major Eker",
            "Arnecio Ulvaw'op",
            "General Ufwol",
            "Admiral Wilham Burke"
        }
    };
    public static final float[][] WAYPOINT_FLOAT_MULTIARRAY = 
    {
        
        {
            6495f,
            -5552f,
            -568f,
            -2530f,
            -1850f,
            -1132f,
            -1127f,
            3998f,
            4000f,
            3227f,
            -5524f,
            -3002f,
            -6966f,
            -115f,
            3690f,
            3082f
        },
        
        {
            0.0f,
            -7065f,
            0.0f,
            0.0f,
            0.0f,
            13.32f,
            15f,
            37f,
            37f,
            24f,
            29f,
            4f,
            73f,
            18f,
            96f,
            301f
        },
        
        {
            4490f,
            -5121f,
            -100f,
            1650f,
            820f,
            -3542f,
            -3589f,
            -6195f,
            -6196f,
            -3436f,
            4618f,
            2201f,
            -5660,
            -1579f,
            -6463f,
            -5203f
        }
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qadatapad");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qadatapad");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals(SCRIPTVAR))
            {
                toolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleWarpScriptOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, SCRIPTVAR + ".pid"))
            {
                qa.checkParams(params, SCRIPTVAR);
                obj_id player = sui.getPlayerId(params);
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    qa.removePlayer(player, SCRIPTVAR, "");
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    qa.qaToolMainMenu(self);
                    utils.removeScriptVarTree(self, SCRIPTVAR);
                    return SCRIPT_CONTINUE;
                }
                String previousMainMenuArray[] = utils.getStringArrayScriptVar(self, SCRIPTVAR + ".warpMenu");
                String previousSelection = previousMainMenuArray[idx];
                if (utils.hasScriptVar(self, SCRIPTVAR + ".warpPoints") && !addOnSelection(self, previousSelection))
                {
                    location waypointWarpLocationArray[] = utils.getLocationArrayScriptVar(self, SCRIPTVAR + ".warpPoints");
                    location warpSelection = waypointWarpLocationArray[idx];
                    goWarpLocation(player, warpSelection);
                    CustomerServiceLog("qaTool", "User: (" + self + ") " + getName(self) + " has warped to  (" + warpSelection + ") using a QA Datapad Tool.");
                    qa.removePlayer(player, SCRIPTVAR, "");
                }
                else 
                {
                    if (previousSelection.equals(ADD_ON_DATAPAD_MENU[0]))
                    {
                        testWaypointLocations(self);
                        toolMainMenu(self);
                    }
                    else if (previousSelection.equals(ADD_ON_DATAPAD_MENU[1]))
                    {
                        deleteAllWaypoints(self);
                        toolMainMenu(self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void toolMainMenu(obj_id self) throws InterruptedException
    {
        obj_id[] waypointArray = qa.getAllValidWaypoints(self);
        String[] waypointMenu = qa.getMenuList(self, waypointArray, "waypoint menu");
        if (waypointMenu.length > 0)
        {
            location[] waypointWarpLocations = qa.getLocationList(self, waypointArray);
            utils.setScriptVar(self, SCRIPTVAR + ".warpPoints", waypointWarpLocations);
            String[] combinedMenu = new String[waypointMenu.length + ADD_ON_DATAPAD_MENU.length];
            System.arraycopy(waypointMenu, 0, combinedMenu, 0, waypointMenu.length);
            System.arraycopy(ADD_ON_DATAPAD_MENU, 0, combinedMenu, waypointMenu.length, ADD_ON_DATAPAD_MENU.length);
            qa.refreshMenu(self, DATAPAD_TOOL_PROMPT, DATAPAD_TOOL_TITLE, combinedMenu, "handleWarpScriptOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".warpMenu", sui.OK_CANCEL_REFRESH);
        }
        else 
        {
            qa.refreshMenu(self, DATAPAD_TOOL_PROMPT, DATAPAD_TOOL_TITLE, ADD_ON_DATAPAD_MENU, "handleWarpScriptOptions", SCRIPTVAR + ".pid", SCRIPTVAR + ".warpMenu", sui.OK_CANCEL_REFRESH);
        }
    }
    public void goWarpLocation(obj_id player, location selectedLocation) throws InterruptedException
    {
        sendSystemMessageTestingOnly(player, "Waypoint data received, warping now.");
        warpPlayer(player, selectedLocation.area, selectedLocation.x, selectedLocation.y, selectedLocation.z, null, 0.0f, 0.0f, 0.0f);
    }
    public boolean createWaypoint(obj_id self, String planetArea, float locationX, float locationY, float locationZ, String waypointName) throws InterruptedException
    {
        location locationVar = new location();
        locationVar.area = planetArea;
        locationVar.x = locationX;
        locationVar.y = locationY;
        locationVar.z = locationZ;
        String waypName = waypointName;
        qa.createAQaWaypointInDataPad(self, locationVar, waypName);
        return true;
    }
    public void testWaypointLocations(obj_id self) throws InterruptedException
    {
        for (int i = 0; i < WAYPOINT_STRING_MULTIARRAY[0].length; i++)
        {
            String planetArea = WAYPOINT_STRING_MULTIARRAY[0][i];
            String waypointName = WAYPOINT_STRING_MULTIARRAY[1][i];
            float locationX = WAYPOINT_FLOAT_MULTIARRAY[0][i];
            float locationY = WAYPOINT_FLOAT_MULTIARRAY[1][i];
            float locationZ = WAYPOINT_FLOAT_MULTIARRAY[2][i];
            boolean response = createWaypoint(self, planetArea, locationX, locationY, locationZ, waypointName);
        }
    }
    public boolean deleteAllWaypoints(obj_id self) throws InterruptedException
    {
        obj_id[] waypoints = getWaypointsInDatapad(self);
        for (int i = 0; i < waypoints.length; i++)
        {
            destroyWaypointInDatapad(waypoints[i], self);
        }
        return true;
    }
    public boolean addOnSelection(obj_id self, String previousSelection) throws InterruptedException
    {
        for (int i = 0; i < ADD_ON_DATAPAD_MENU.length; i++)
        {
            if (previousSelection.equals(ADD_ON_DATAPAD_MENU[i]))
            {
                return true;
            }
        }
        return false;
    }
}
