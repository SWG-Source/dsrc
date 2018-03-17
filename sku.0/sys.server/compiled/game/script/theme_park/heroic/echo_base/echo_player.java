package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.badge;
import script.library.factions;
import script.library.groundquests;
import script.library.instance;
import script.library.locations;
import script.library.utils;
import script.library.vehicle;

public class echo_player extends script.base_script
{
    public echo_player()
    {
    }
    public static final String BADGE_PLANET_HOTH = "bdg_hoth_planet";
    public static String[] QUEST_TO_CLEAR = 
    {
        "imperial_phase_1",
        "rebel_phase_1",
        "heroic_echo_imperial_phase_2",
        "heroic_echo_rebel_phase_2",
        "heroic_echo_rebel_phase_2_v2",
        "heroic_echo_rebel_evac",
        "heroic_echo_imperial_evac"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        npcEndConversation(self);
        if (utils.hasScriptVar(self, "echo_player_setup"))
        {
            utils.removeScriptVar(self, "echo_player_setup");
        }
        messageTo(self, "handleEchoPlayerInitialSetup", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "echo_player_setup"))
        {
            utils.removeScriptVar(self, "echo_player_setup");
        }
        messageTo(self, "handleEchoPlayerInitialSetup", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int handleEchoPlayerInitialSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "echoBaseDeath"))
        {
            utils.removeScriptVar(self, "echoBaseDeath");
        }
        if (!utils.hasScriptVar(self, "echo_player_setup"))
        {
            utils.setScriptVar(self, "echo_player_setup", true);
            messageTo(self, "checkForActiveHothTrackerQuest", null, 59, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        String area = locations.getBuildoutAreaName(self);
        if (area == null || area.equals(""))
        {
            utils.removeScriptVar(self, factions.IN_ADHOC_PVP_AREA);
            detachScript(self, "theme_park.heroic.echo_base.echo_player");
            return SCRIPT_CONTINUE;
        }
        if (!area.equals("echo_base"))
        {
            utils.removeScriptVar(self, factions.IN_ADHOC_PVP_AREA);
            pvpMakeCovert(self);
            detachScript(self, "theme_park.heroic.echo_base.echo_player");
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "echoBaseDeath"))
        {
            clearCurrentEchoQuest(self);
            messageTo(self, "update_quest", null, 10.0f, false);
        }
        else 
        {
            utils.removeScriptVar(self, "echoBaseDeath");
        }
        obj_id[] waypoints = getWaypointsInDatapad(self);
        utils.setScriptVar(self, factions.IN_ADHOC_PVP_AREA, true);
        pvpMakeDeclared(self);
        if (waypoints != null && waypoints.length > 0)
        {
            for (int i = 0; i < waypoints.length; i++)
            {
                location wpLoc = getWaypointLocation(waypoints[i]);
                if (wpLoc.area.equals("adventure2"))
                {
                    destroyWaypointInDatapad(waypoints[i], self);
                }
            }
        }
        grantPlanetHothBadge(self);
        messageTo(self, "checkForHothMusic", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String action) throws InterruptedException
    {
        if (action.equals("clientReady"))
        {
            location here = getLocation(self);
            if (here.area.equals("adventure2"))
            {
            }
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnRecapacitated(obj_id self) throws InterruptedException
    {
        pvpMakeDeclared(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "echoBaseDeath"))
        {
            utils.removeScriptVar(self, "echoBaseDeath");
        }
        checkForBattlefieldVehicleAllowedZones(self);
        clearCurrentEchoQuest(self);
        obj_id[] waypoints = getWaypointsInDatapad(self);
        if (waypoints != null && waypoints.length > 0)
        {
            for (int i = 0; i < waypoints.length; i++)
            {
                location wpLoc = getWaypointLocation(waypoints[i]);
                if (wpLoc.area.equals("adventure2"))
                {
                    destroyWaypointInDatapad(waypoints[i], self);
                }
            }
        }
        utils.removeScriptVar(self, factions.IN_ADHOC_PVP_AREA);
        pvpMakeCovert(self);
        return SCRIPT_CONTINUE;
    }
    public void checkForBattlefieldVehicleAllowedZones(obj_id self) throws InterruptedException
    {
        obj_id playerCurrentMount = getMountId(self);
        if (isIdValid(playerCurrentMount))
        {
            if (vehicle.isBattlefieldVehicle(playerCurrentMount))
            {
                messageTo(playerCurrentMount, "handleCheckForAllowedZones", null, 0, false);
            }
            return;
        }
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        utils.setScriptVar(self, "echoBaseDeath", true);
        return SCRIPT_CONTINUE;
    }
    public int checkForActiveHothTrackerQuest(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        if (here.area.equals("adventure2"))
        {
            boolean questFound = false;
            for (int i = 0; i < QUEST_TO_CLEAR.length; ++i)
            {
                if (groundquests.isQuestActive(self, QUEST_TO_CLEAR[i]))
                {
                    questFound = true;
                }
            }
            if (!questFound)
            {
                messageTo(self, "update_quest", null, 1.0f, false);
            }
        }
        utils.removeScriptVar(self, "echo_player_setup");
        messageTo(self, "checkForActiveHothTrackerQuest", null, 19, false);
        return SCRIPT_CONTINUE;
    }
    public int checkForHothMusic(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id instanceController = instance.getAreaInstanceController(self);
        if (isIdValid(instanceController))
        {
            if (utils.hasScriptVar(instanceController, "instance_persistedMusic"))
            {
                String music = utils.getStringScriptVar(instanceController, "instance_persistedMusic");
                if (music != null && music.length() > 0)
                {
                    playMusic(self, self, music, 0, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int update_quest(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "echoBaseDeath"))
        {
            utils.removeScriptVar(self, "echoBaseDeath");
        }
        dictionary dict = new dictionary();
        dict.put("player", self);
        obj_id[] allObj = utils.getAllObjectsInBuildoutArea(self);
        obj_id quest_manager = obj_id.NULL_ID;
        if (allObj == null || allObj.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        for (int i = 0; i < allObj.length; i++)
        {
            if (hasScript(allObj[i], "theme_park.heroic.echo_base.echo_quest_tracker"))
            {
                quest_manager = allObj[i];
                break;
            }
        }
        if (!isIdValid(quest_manager))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(quest_manager, "requestUpdatePlayer", dict, 0.0f, false);
        return SCRIPT_CONTINUE;
    }
    public void clearCurrentEchoQuest(obj_id self) throws InterruptedException
    {
        for (int i = 0; i < QUEST_TO_CLEAR.length; ++i)
        {
            if (groundquests.isQuestActive(self, QUEST_TO_CLEAR[i]))
            {
                groundquests.clearQuest(self, QUEST_TO_CLEAR[i]);
            }
        }
    }
    public void grantPlanetHothBadge(obj_id player) throws InterruptedException
    {
        if (isIdValid(player) && exists(player) && !badge.hasBadge(player, BADGE_PLANET_HOTH))
        {
            dictionary dict = new dictionary();
            dict.put("player", player);
            messageTo(player, "delayGrantPlanetHothBadge", dict, 20.0f, false);
        }
    }
    public int delayGrantPlanetHothBadge(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        if (isIdValid(player) && exists(player) && !badge.hasBadge(player, BADGE_PLANET_HOTH))
        {
            badge.grantBadge(player, BADGE_PLANET_HOTH);
        }
        return SCRIPT_CONTINUE;
    }
    public int dismountHothPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        boolean isRidingMount = (getState(self, STATE_RIDING_MOUNT) > 0);
        obj_id mountId = (isRidingMount ? getMountId(self) : null);
        String creature_name = getName(mountId);
        if (isRidingMount)
        {
            if (vehicle.isBattlefieldVehicle(mountId))
            {
                queueCommand(self, (1988230683), self, creature_name, COMMAND_PRIORITY_FRONT);
                setYaw(mountId, getYaw(mountId) + 180.0f);
            }
            else 
            {
                queueCommand(self, (117012717), self, creature_name, COMMAND_PRIORITY_FRONT);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
