package script.test;

import script.dictionary;
import script.library.qa;
import script.library.sui;
import script.library.utils;
import script.location;
import script.obj_id;

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
                6495.0f,
            -5552.0f,
            -568.0f,
            -2530.0f,
            -1850.0f,
            -1132.0f,
            -1127.0f,
                3998.0f,
                4000.0f,
                3227.0f,
            -5524.0f,
            -3002.0f,
            -6966.0f,
            -115.0f,
                3690.0f,
                3082.0f
        },
        
        {
            0.0f,
            -7065.0f,
            0.0f,
            0.0f,
            0.0f,
            13.32f,
                15.0f,
                37.0f,
                37.0f,
                24.0f,
                29.0f,
                4.0f,
                73.0f,
                18.0f,
                96.0f,
                301.0f
        },
        
        {
                4490.0f,
            -5121.0f,
            -100.0f,
                1650.0f,
                820.0f,
            -3542.0f,
            -3589.0f,
            -6195.0f,
            -6196.0f,
            -3436.0f,
                4618.0f,
                2201.0f,
            -5660,
            -1579.0f,
            -6463.0f,
            -5203.0f
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
        for (obj_id waypoint : waypoints) {
            destroyWaypointInDatapad(waypoint, self);
        }
        return true;
    }
    public boolean addOnSelection(obj_id self, String previousSelection) throws InterruptedException
    {
        for (String addOnDatapadMenu : ADD_ON_DATAPAD_MENU) {
            if (previousSelection.equals(addOnDatapadMenu)) {
                return true;
            }
        }
        return false;
    }
}
