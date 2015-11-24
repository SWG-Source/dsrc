package script.working.asommers;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.create;
import script.library.ship_ai;
import script.library.utils;

public class debug extends script.base_script
{
    public debug()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        location playerLocation = getLocation(self);
        if (text.equals("newbieZoomCamera"))
        {
            boolean result = newbieTutorialRequest(self, "zoomCamera");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieOpenInventory"))
        {
            boolean result = newbieTutorialRequest(self, "openInventory");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieCloseInventory"))
        {
            boolean result = newbieTutorialRequest(self, "closeInventory");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieOpenCharacterSheet"))
        {
            boolean result = newbieTutorialRequest(self, "openCharacterSheet");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieCloseCharacterSheet"))
        {
            boolean result = newbieTutorialRequest(self, "closeCharacterSheet");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieChangeMouseMode"))
        {
            boolean result = newbieTutorialRequest(self, "changeMouseMode");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieEquipToolbar"))
        {
            boolean result = newbieTutorialRequest(self, "equipToolbar");
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieEnableRadar"))
        {
            boolean result = newbieTutorialEnableHudElement(self, "radar", true, 5);
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("newbieDisableRadar"))
        {
            boolean result = newbieTutorialEnableHudElement(self, "radar", false, 0);
            if (result)
            {
                debugSpeakMsg(self, "success");
            }
            else 
            {
                debugSpeakMsg(self, "fail");
            }
        }
        if (text.equals("suitest"))
        {
            int temp = sui.msgbox(self, text);
        }
        if (text.equals("warptest"))
        {
            warpPlayer(self, playerLocation.area, playerLocation.x, playerLocation.y, playerLocation.z + 100, null, 0, 0, 0);
        }
        if (text.equals("weather0"))
        {
            boolean set = setWeatherData(0, 1.0f, 0.0f);
            if (set)
            {
                debugSpeakMsg(self, text + "set");
            }
            else 
            {
                debugSpeakMsg(self, text + "not set");
            }
        }
        if (text.equals("weather1"))
        {
            boolean set = setWeatherData(1, 0.0f, 1.0f);
            if (set)
            {
                debugSpeakMsg(self, text + "set");
            }
            else 
            {
                debugSpeakMsg(self, text + "not set");
            }
        }
        if (text.equals("weather2"))
        {
            boolean set = setWeatherData(2, -1.0f, 0.0f);
            if (set)
            {
                debugSpeakMsg(self, text + "set");
            }
            else 
            {
                debugSpeakMsg(self, text + "not set");
            }
        }
        if (text.equals("weather3"))
        {
            boolean set = setWeatherData(3, 0.0f, -1.0f);
            if (set)
            {
                debugSpeakMsg(self, text + "set");
            }
            else 
            {
                debugSpeakMsg(self, text + "not set");
            }
        }
        if (text.equals("addTravelPoints"))
        {
            String planet = getCurrentSceneName();
            location location = getLocation(self);
            location.x -= 50;
            obj_id object1 = createObject("object/installation/mining_ore/mining_ore_harvester_style_2.iff", location);
            boolean result = addPlanetTravelPoint(planet, "point 1", location, 100, true, TPT_PC_Shuttleport);
            if (result)
            {
                debugSpeakMsg(self, "addPlanetTravelPoint point1 succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "addPlanetTravelPoint point1 failed");
            }
            location.x += 100;
            obj_id object2 = createObject("object/installation/mining_ore/mining_ore_harvester_style_2.iff", location);
            result = addPlanetTravelPoint(planet, "point2", location, 50, true, TPT_PC_Shuttleport);
            if (result)
            {
                debugSpeakMsg(self, "addPlanetTravelPoint point2 succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "addPlanetTravelPoint point2 failed");
            }
        }
        if (text.equals("purchaseTicket1"))
        {
            String planet = getCurrentSceneName();
            boolean result = enterClientTicketPurchaseMode(self, planet, "point 1", false);
            if (result)
            {
                debugSpeakMsg(self, "enterClientTicketPurchaseMode succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "enterClientTicketPurchaseMode failed");
            }
        }
        if (text.equals("purchaseTicket2"))
        {
            String planet = getCurrentSceneName();
            boolean result = enterClientTicketPurchaseMode(self, planet, "point2", false);
            if (result)
            {
                debugSpeakMsg(self, "enterClientTicketPurchaseMode succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "enterClientTicketPurchaseMode failed");
            }
        }
        if (text.equals("place"))
        {
            boolean placed = enterClientStructurePlacementMode(self, self, "object/installation/mining_ore/mining_ore_harvester_style_2.iff");
            if (!placed)
            {
                debugSpeakMsg(self, "OnSpeaking: enterClientStructurePlacementMode failed");
            }
            else 
            {
                debugSpeakMsg(self, "OnSpeaking: enterClientStructurePlacementMode succeeded");
            }
        }
        if (text.equals("poiTest"))
        {
            debugSpeakMsg(self, "Making lair at your location");
            location locHome = getLocation(self);
            String strLairDifficulty = create.getLairDifficulty(4, 40, 20);
            obj_id objLair = createObject("object/tangible/lair/npc_lair.iff", locHome);
            setObjVar(objLair, "spawning.intDifficultyLevel", 20);
            setObjVar(objLair, "spawning.lairDifficulty", strLairDifficulty);
            setObjVar(objLair, "spawning.lairType", "evil_tatooine_village");
            messageTo(objLair, "handle_Spawn_Setup_Complete", null, 0, false);
        }
        if (text.equals("heightTest"))
        {
            int i;
            for (i = 0; i < 9; ++i)
            {
                location position = utils.getRandomLocationInRing(playerLocation, 1, 20);
                float height = getHeightAtLocation(position.x, position.z);
                debugSpeakMsg(self, "height = " + height);
            }
        }
        if (text.equals("zeroLots"))
        {
            adjustLotCount(self, -getAccountNumLots(self));
        }
        if (text.equals("requestLocation"))
        {
            if (requestLocation(self, "locationId", playerLocation, 200, 32, true, true))
            {
                debugSpeakMsg(self, "requestLocation succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "requestLocation failed");
            }
        }
        if (text.equals("enemy_xwing"))
        {
            obj_id ship = createObject("object/ship/xwing.iff", playerLocation);
            ship_ai.unitAddDamageTaken(ship, self, 10000.0f);
        }
        if (text.equals("enemy_tiefighter"))
        {
            obj_id ship = createObject("object/ship/tiefighter.iff", playerLocation);
            ship_ai.unitAddDamageTaken(ship, self, 10000.0f);
        }
        if (text.equals("deleteObject"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                destroyObject(target);
                debugSpeakMsg(self, "destroyObject " + target + " succeeded");
            }
        }
        if (text.equals("createTestObjects"))
        {
            for (int i = -3; i < 3; ++i)
            {
                for (int j = -3; j < 3; ++j)
                {
                    location location = getLocation(self);
                    location.x -= 5 * i;
                    location.z -= 5 * j;
                    obj_id object = createObject("object/tangible/furniture/space/frn_chair_falcon_passenger_s01.iff", location);
                }
            }
        }
        if (text.equals("addMissionCriticalObject"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                if (addMissionCriticalObject(self, target))
                {
                    debugSpeakMsg(self, "addMissionCriticalObject" + target + " succeeded");
                }
            }
            else 
            {
                debugSpeakMsg(self, "no target");
            }
        }
        if (text.equals("removeMissionCriticalObject"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                if (removeMissionCriticalObject(self, target))
                {
                    debugSpeakMsg(self, "removeMissionCriticalObject" + target + " succeeded");
                }
            }
            else 
            {
                debugSpeakMsg(self, "no target");
            }
        }
        if (text.equals("isMissionCriticalObject"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                if (isMissionCriticalObject(self, target))
                {
                    debugSpeakMsg(self, "isMissionCriticalObject" + target + " yes");
                }
                else 
                {
                    debugSpeakMsg(self, "isMissionCriticalObject" + target + " no");
                }
            }
        }
        if (text.equals("clearMissionCriticalObjects"))
        {
            if (clearMissionCriticalObjects(self))
            {
                debugSpeakMsg(self, "clearMissionCriticalObjects succeeded");
            }
            else 
            {
                debugSpeakMsg(self, "clearMissionCriticalObjects failed");
            }
        }
        if (text.equals("conditionTest1"))
        {
            obj_id object = createObject("object/tangible/ship/interior_components/alarm_interior.iff", playerLocation);
        }
        if (text.equals("conditionTest2"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                setCondition(target, CONDITION_ON);
            }
        }
        if (text.equals("getConditionOnOff"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null && target != obj_id.NULL_ID)
            {
                if (hasCondition(target, CONDITION_ON))
                {
                    debugSpeakMsg(target, "on");
                }
                else 
                {
                    debugSpeakMsg(target, "off");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnPlaceStructure(obj_id self, obj_id player, obj_id deed, location position, int rotation) throws InterruptedException
    {
        debugSpeakMsg(self, "OnPlaceStructure: entered");
        obj_id structure = createObject("object/installation/mining_ore/mining_ore_harvester_style_2.iff", position);
        if (structure == null || structure == obj_id.NULL_ID)
        {
            debugSpeakMsg(self, "OnPlaceStructure: createObject failed");
            return SCRIPT_OVERRIDE;
        }
        setYaw(structure, (float)(90 * rotation));
        return SCRIPT_CONTINUE;
    }
    public int OnPurchaseTicket(obj_id self, obj_id player, String departPlanetName, String departTravelPointName, String arrivePlanetName, String arriveTravelPointName, boolean roundTrip) throws InterruptedException
    {
        debugSpeakMsg(self, "OnPurchaseTicket: entered");
        debugSpeakMsg(self, departPlanetName);
        debugSpeakMsg(self, departTravelPointName);
        debugSpeakMsg(self, arrivePlanetName);
        debugSpeakMsg(self, arriveTravelPointName);
        debugSpeakMsg(self, roundTrip ? "roundtrip" : "not roundtrip");
        return SCRIPT_CONTINUE;
    }
    public int OnNewbieTutorialResponse(obj_id self, String response) throws InterruptedException
    {
        debugSpeakMsg(self, "Newbie tutorial: " + response);
        return SCRIPT_CONTINUE;
    }
    public int OnLocationReceived(obj_id self, String locationId, obj_id locationObject, location locationLocation, float locationRadius) throws InterruptedException
    {
        debugSpeakMsg(self, "OnLocationReceived: " + locationId);
        if (locationObject == null || locationObject == obj_id.NULL_ID)
        {
            debugSpeakMsg(self, "OnLocationReceived: could not find a valid location");
        }
        else 
        {
            obj_id object = createObject("object/installation/mining_ore/mining_ore_harvester_style_2.iff", locationLocation);
            if (object == null || object == obj_id.NULL_ID)
            {
                debugSpeakMsg(self, "OnLocationReceived: createObject failed");
            }
            if (!destroyObject(locationObject))
            {
                debugSpeakMsg(self, "OnLocationReceived: failed to destroy locationObject");
            }
        }
        return SCRIPT_CONTINUE;
    }
}
