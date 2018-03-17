package script.library;

import script.location;
import script.obj_id;
import script.string_id;

public class transition extends script.base_script
{
    public transition()
    {
    }
    public static final float distanceMax = 6.0f;
    public static final String dataTable = "datatables/travel/zone_transition.iff";
    public static final String STF = "travel/zone_transition";
    public static final string_id bad_zone_data = new string_id(STF, "bad_zone_data");
    public static final string_id invalid_travel = new string_id(STF, "invalid_travel");
    public static final boolean doLogging = false;
    public static string_id getZoneTransitionString(obj_id gate) throws InterruptedException
    {
        if (!hasObjVar(gate, "zoneLine"))
        {
            return bad_zone_data;
        }
        String zoneLine = getStringObjVar(gate, "zoneLine");
        return new string_id(STF, zoneLine);
    }
    public static void zonePlayer(obj_id gate, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(gate, "zoneLine"))
        {
            sendSystemMessage(player, invalid_travel);
            return;
        }
        String transit = getStringObjVar(gate, "zoneLine");
        if (!hasPermissionForZone(player, transit, "initialRequiredFlag"))
        {
            notifyPlayerOfInvalidPermission(player, transit);
            return;
        }
        String destination = dataTableGetString(dataTable, transit, "destination");
        String[] parse = split(destination, ':');
        callable.storeCallables(player);
        String script = dataTableGetString(dataTable, transit, "script");
        if (!script.equals("none"))
        {
            attachScript(player, script);
        }
        if (parse.length == 4)
        {
            doZoneToWorld(player, parse);
        }
        if (parse[0].equals("instance"))
        {
            doPublicInstanceZone(player, parse[1]);
        }
    }
    public static void zonePlayerNoGate(String transit, obj_id player, boolean forceLoadScreen) throws InterruptedException
    {
        if (!hasPermissionForZone(player, transit, "initialRequiredFlag"))
        {
            notifyPlayerOfInvalidPermission(player, transit);
            return;
        }
        String[] parse = split(dataTableGetString(dataTable, transit, "destination"), ':');
        callable.storeCallables(player);
        String script = dataTableGetString(dataTable, transit, "script");
        if (!script.equals("none"))
        {
            attachScript(player, script);
        }
        if (parse.length == 4)
        {
            doZoneToWorldForceLoadScreen(player, parse, forceLoadScreen);
        }
        if (parse[0].equals("instance"))
        {
            doPublicInstanceZone(player, parse[1]);
        }
    }
    public static void doZoneToWorld(obj_id player, String[] locationParse) throws InterruptedException
    {
        float locX = utils.stringToFloat(locationParse[1]);
        float locY = utils.stringToFloat(locationParse[2]);
        float locZ = utils.stringToFloat(locationParse[3]);
        if (!locationParse[0].equals("offset"))
        {
            String world = locationParse[0];
            cleanupTempAccessFlag(player);
            utils.dismountRiderJetpackCheck(player);
            warpPlayer(player, world, locX, locY, locZ, null, locX, locY, locZ);
        }
        else 
        {
            location portal = getLocation(getSelf());
            String world = portal.area;
            locX = portal.x + locX;
            locY = portal.y + locY;
            locZ = portal.z + locZ;
            cleanupTempAccessFlag(player);
            LOG("debug", "warping player to location:");
            LOG("debug", player + ", " + world + ", " + locX + ", " + locY + ", " + locZ);
            utils.dismountRiderJetpackCheck(player);
            callable.storeCallables(player);
            warpPlayer(player, world, locX, locY, locZ, null, locX, locY, locZ);
        }
    }
    public static void doZoneToWorldForceLoadScreen(obj_id player, String[] locationParse, boolean loadScreen) throws InterruptedException
    {
        float locX = utils.stringToFloat(locationParse[1]);
        float locY = utils.stringToFloat(locationParse[2]);
        float locZ = utils.stringToFloat(locationParse[3]);
        if (!locationParse[0].equals("offset"))
        {
            String world = locationParse[0];
            cleanupTempAccessFlag(player);
            utils.dismountRiderJetpackCheck(player);
            warpPlayer(player, world, locX, locY, locZ, null, locX, locY, locZ);
        }
        else 
        {
            location portal = getLocation(getSelf());
            String world = portal.area;
            locX = portal.x + locX;
            locY = portal.y + locY;
            locZ = portal.z + locZ;
            cleanupTempAccessFlag(player);
            LOG("debug", "warping player to location:");
            LOG("debug", player + ", " + world + ", " + locX + ", " + locY + ", " + locZ);
            utils.dismountRiderJetpackCheck(player);
            callable.storeCallables(player);
            warpPlayer(player, world, locX, locY, locZ, obj_id.NULL_ID, locX, locY, locZ, "noCallBack", loadScreen);
        }
    }
    public static void doPublicInstanceZone(obj_id player, String instance) throws InterruptedException
    {
        getClusterWideData("public_instances", instance + "*", true, getSelf());
        doLogging("doPublicInstanceZone", "Requested clusterwide data for " + instance + "*");
        utils.setScriptVar(getSelf(), "playerId", player);
    }
    public static boolean hasPermissionForZone(obj_id player, String zone, String flagType) throws InterruptedException {
        String requiredFlag = dataTableGetString(dataTable, zone, flagType);
        if (requiredFlag == null || requiredFlag.equals("")) {
            return true;
        }
        String[] parse = split(requiredFlag, ':');
        if (parse.length == 1) {
            if (parse[0].equals("none")) {
                return !flagType.equals("initialRequiredFlag") || hasPermissionForZone(player, zone, "finalRequiredFlag");
            }
            if (hasObjVar(player, parse[0])) {
                return true;
            }
            if (utils.hasScriptVar(player, parse[0])) {
                utils.setScriptVar(player, "tempFlag", parse[0]);
                return true;
            }
            if (parse[0].equals("level")) {
                int level = getLevel(player);
                if (level >= utils.stringToInt(parse[1])) {
                    return true;
                }
            }
        }
        if (parse.length == 2) {
            if (parse[0].equals("won")) {
                if (groundquests.hasCompletedQuest(player, parse[1])) {
                    return true;
                }
            }
            if (parse[0].equals("has")) {
                if (groundquests.isQuestActive(player, parse[1])) {
                    return true;
                }
            }
            if (parse[0].equals("item")) {
                if (utils.playerHasItemByTemplateInInventoryOrEquipped(player, parse[1])) {
                    return true;
                }
                if (utils.playerHasItemWithObjVarInInventoryOrEquipped(player, parse[1])) {
                    return true;
                }
            }
            if (parse[0].equals("level")) {
                int level = getLevel(player);
                if (level >= utils.stringToInt(parse[1])) {
                    return true;
                }
            }
        }
        if (parse.length == 3) {
            if (parse[0].equals("won")) {
                if (space_quest.hasWonQuest(player, parse[1], parse[2])) {
                    return true;
                }
            }
            if (parse[0].equals("has")) {
                if (space_quest.hasQuest(player, parse[1], parse[2])) {
                    return true;
                }
            }
            if (parse[0].equals("task")) {
                if (groundquests.isTaskActive(player, parse[2], parse[3])) {
                    return true;
                }
            }
            if (parse[0].equals("item")) {
                if (utils.playerHasItemByTemplateWithObjVarInInventoryOrEquipped(player, parse[1], parse[2])) {
                    return true;
                }
            }
            if (parse[0].equals("level")) {
                int level = getLevel(player);
                if (level >= utils.stringToInt(parse[1])) {
                    return true;
                }
            }
        }
        if (isGod(player)) {
            sendSystemMessageTestingOnly(player, "You are passing the permissions check because you are in god mode");
            return true;
        }
        return flagType.equals("initialRequiredFlag") && hasPermissionForZone(player, zone, "finalRequiredFlag");
    }
    public static void notifyPlayerOfInvalidPermission(obj_id player, String zone) throws InterruptedException
    {
        String deniedMessage = dataTableGetString(dataTable, zone, "customAccessString");
        sendSystemMessage(player, new string_id(STF, deniedMessage));
    }
    public static void cleanupTempAccessFlag(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(player, "tempFlag"))
        {
            utils.removeScriptVar(player, utils.getStringScriptVar(player, "tempFlag"));
        }
    }
    public static void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/transition.scriptlib/" + section, message);
        }
    }
}
