package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.locations;
import script.library.loot;
import script.library.npe;
import script.library.static_item;
import script.library.vehicle;
import script.library.armor;
import script.library.chat;
import script.library.space_quest;
import script.library.anims;
import script.library.sequencer;
import script.library.sui;
import script.library.gcw;
import script.library.space_pilot_command;
import script.library.factions;
import script.library.healing;
import script.library.planetary_map;
import script.library.player_structure;
import script.library.space_battlefield;
import script.library.space_utils;
import script.library.ship_ai;
import script.library.space_combat;
import script.library.space_transition;
import script.library.space_crafting;
import script.library.datatable;
import script.library.battlefield;
import script.library.create;
import script.library.utils;
import script.library.skill;
import script.library.pclib;
import script.library.regions;
import script.library.dot;
import script.library.pet_lib;
import script.library.space_create;
import script.library.trial;

public class dantest extends script.base_script
{
    public dantest()
    {
    }
    public static final int SPAWN_TEMPLATE_CHECK_DISTANCE = 128;
    public static final int SPAWN_CHECK_TEMPLATE_LIMIT = 140;
    public static final int SPAWN_THEATER_CHECK_DISTANCE = 200;
    public static final int SPAWN_CHECK_DISTANCE = 64;
    public static final int SPAWN_CHECK_LIMIT = 15;
    public static final int SPAWN_CHECK_THEATER_LIMIT = 1;
    public String getLevelString(int intLevel) throws InterruptedException
    {
        if (intLevel <= 10)
        {
            return "1_10";
        }
        if (intLevel <= 20)
        {
            return "11_20";
        }
        if (intLevel <= 30)
        {
            return "21_30";
        }
        if (intLevel <= 40)
        {
            return "31_40";
        }
        if (intLevel <= 50)
        {
            return "41_50";
        }
        if (intLevel <= 60)
        {
            return "51_60";
        }
        if (intLevel <= 70)
        {
            return "61_70";
        }
        if (intLevel <= 80)
        {
            return "71_80";
        }
        if (intLevel <= 90)
        {
            return "81_90";
        }
        return "81_90";
    }
    public int checkDifficulty(obj_id self, dictionary params) throws InterruptedException
    {
        int intRawDifficulty = getLevel(self);
        sendSystemMessageTestingOnly(self, "getLevel returned " + intRawDifficulty);
        if (intRawDifficulty == 0)
        {
            sendSystemMessageTestingOnly(self, "you lost your difficulty!");
            deltadictionary dctScriptVars = self.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        messageTo(self, "checkDifficulty", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public void setupJediTrainer(obj_id self) throws InterruptedException
    {
        final String[] TRAINER_TYPES = 
        {
            "trainer_brawler"
        };
        String strPrimaryCategory = "trainer";
        String strSecondaryCategory = TRAINER_TYPES[rand(0, TRAINER_TYPES.length - 1)];
        map_location[] mapLocations = getPlanetaryMapLocations(strPrimaryCategory, strSecondaryCategory);
        if ((mapLocations == null) || (mapLocations.length == 0))
        {
            location locTest = getLocation(self);
            LOG("DESIGNER_FATAL", "JEDI : For planet " + locTest.area + ", primary category : " + strPrimaryCategory + " and secondary category " + strSecondaryCategory + " No planetary map location was found");
            return;
        }
        int intRoll = rand(0, mapLocations.length - 1);
        location locHome = getLocation(self);
        location locTest = new location();
        locTest.x = mapLocations[intRoll].getX();
        locTest.z = mapLocations[intRoll].getY();
        locTest.area = locHome.area;
        setObjVar(self, "jedi.locTrainerLocation", locTest);
        return;
    }
    public int OnSpeaking(obj_id self, String strText) throws InterruptedException
    {
        String[] strCommands = split(strText, ' ');
        if (strCommands[0].equalsIgnoreCase("march"))
        {
            play2dNonLoopingMusic(self, "sound/mus_imperial_march_excerpt.snd");
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equalsIgnoreCase("hangar"))
        {
            location myLoc = new location(getLocation(self));
            obj_id bldg = getTopMostContainer(self);
            warpPlayer(self, "space_light1", 0, 0, 0, bldg, "elevator_e3_down", -74.5f, 204.9f, 330.8f);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("exception"))
        {
            obj_id objTest = null;
            space_utils.notifyObject(objTest, "fixit", null);
        }
        if (strCommands[0].equals("shipStatus"))
        {
            obj_id objShip = getPilotedShip(self);
            int[] intSlots = space_crafting.getShipInstalledSlots(objShip);
            for (int intI = 0; intI < intSlots.length; intI++)
            {
                String strName = ship_chassis_slot_type.names[intSlots[intI]];
                debugConsoleMsg(self, "Item: " + strName);
                debugConsoleMsg(self, "General Efficiency: " + getShipComponentEfficiencyGeneral(objShip, intSlots[intI]));
                debugConsoleMsg(self, "Energy Efficiency: " + getShipComponentEfficiencyEnergy(objShip, intSlots[intI]));
                debugConsoleMsg(self, "Current Hitpoints: " + getShipComponentHitpointsCurrent(objShip, intSlots[intI]));
                debugConsoleMsg(self, "Maximum Hitpoints: " + getShipComponentHitpointsMaximum(objShip, intSlots[intI]));
            }
        }
        if (strCommands[0].equals("validateCharacter"))
        {
            String strObject = strCommands[1];
            sendSystemMessageTestingOnly(self, "strCommands[1] is " + strObject);
            obj_id objTarget = utils.stringToObjId(strObject);
            obj_id objWeapon = getDefaultWeapon(objTarget);
            sendSystemMessageTestingOnly(self, "Weapon Speed is " + getWeaponAttackSpeed(objWeapon));
            sendSystemMessageTestingOnly(self, "template is " + getTemplateName(objWeapon));
            sendSystemMessageTestingOnly(self, "Run Speed of target is " + getRunSpeed(objTarget));
        }
        if (strCommands[0].equals("moveTestOn"))
        {
            applySkillStatisticModifier(self, "slope_move", 30);
            sendSystemMessageTestingOnly(self, "on");
        }
        if (strCommands[0].equals("moveTestOff"))
        {
            applySkillStatisticModifier(self, "slope_move", -10);
            sendSystemMessageTestingOnly(self, "off");
        }
        if (strCommands[0].equals("gcwCrap"))
        {
            gcw.incrementGCWStanding(self, self);
            sendSystemMessageTestingOnly(self, "YEAH");
        }
        if (strCommands[0].equals("unarmedSpeed"))
        {
            obj_id objWeapon = getDefaultWeapon(self);
            sendSystemMessageTestingOnly(self, "Weapon Speed s " + getWeaponAttackSpeed(objWeapon));
            sendSystemMessageTestingOnly(self, "template is " + getTemplateName(objWeapon));
        }
        if (strCommands[0].equals("setVehicleSpeed"))
        {
            obj_id objVehicle = getMountId(self);
            vehicle.initializeVehicle(objVehicle, self);
            sendSystemMessageTestingOnly(self, "= " + getTemplateName(objVehicle));
        }
        if (strCommands[0].equals("testTarget"))
        {
            setCombatTarget(self, getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "asdsa");
        }
        if (strCommands[0].equals("show3po"))
        {
            string_id strSpam = new string_id("beta", "blank_line");
            npe.commTutorialPlayer(self, self, 20, strSpam, "", "object/mobile/c_3po.iff");
            sendSystemMessageTestingOnly(self, "asdadasd");
        }
        if (strCommands[0].equals("testStatup"))
        {
            skill.recalcPlayerPools(self, true);
            sendSystemMessageTestingOnly(self, "asdsa");
        }
        if (strCommands[0].equals("checkReimburse"))
        {
            String[] strArray = new String[2];
            strArray[0] = "object/weapon/ranged/rifle/rifle_dlt20.iff";
            strArray[1] = "object/weapon/ranged/rifle/rifle_t21.iff";
            setObjVar(self, "item_reimbursement_list", strArray);
            sendSystemMessageTestingOnly(self, "Set");
        }
        if (strCommands[0].equals("checkStringId"))
        {
            string_id strSpam = new string_id("faction/faction_hq/faction_hq_response", "terminal_response23");
            sendSystemMessage(self, strSpam);
            String strTest = getString(strSpam);
            sendSystemMessageTestingOnly(self, "strTest is " + strTest);
            strSpam = new string_id("gcw", "newsnet_extra");
            strTest = getString(strSpam);
            sendSystemMessageTestingOnly(self, "strTest is " + strTest);
        }
        if (strCommands[0].equals("countMobs"))
        {
            int intMobs = 0;
            obj_id[] objObjects = getObjectsInRange(getLocation(self), 320000);
            for (int intI = 0; intI < objObjects.length; intI++)
            {
                if (isMob(objObjects[intI]))
                {
                    intMobs++;
                }
                else 
                {
                    obj_id[] objContents = utils.getContents(objObjects[intI], true);
                    if (objContents != null)
                    {
                        for (int intJ = 0; intJ < objContents.length; intJ++)
                        {
                            if (isMob(objContents[intJ]))
                            {
                                sendSystemMessageTestingOnly(self, "Found  mob in " + objContents[intI]);
                                intMobs++;
                            }
                        }
                    }
                }
            }
            sendSystemMessageTestingOnly(self, "Found " + intMobs);
        }
        if (strCommands[0].equals("findMobs"))
        {
            obj_id[] objMobs = getAllObjectsWithObjVar(getLocation(self), 32000f, "ai");
            int intRoll = rand(0, objMobs.length - 1);
            sendSystemMessageTestingOnly(self, "Try ");
            int intTest = 0;
            String strTest = "";
            Vector objDungeons = new Vector();
            objDungeons.setSize(0);
            Vector intCounts = new Vector();
            intCounts.setSize(0);
            for (int intI = 0; intI < objMobs.length; intI++)
            {
                if (hasCondition(objMobs[intI], CONDITION_HIBERNATING))
                {
                    intTest++;
                }
                obj_id objTest = getTopMostContainer(objMobs[intI]);
                int intIndex = utils.getElementPositionInArray(objDungeons, objTest);
                if (intIndex < 0)
                {
                    objDungeons = utils.addElement(objDungeons, objTest);
                    intCounts = utils.addElement(intCounts, 0);
                }
                else 
                {
                    int intCount = ((Integer)intCounts.get(intIndex)).intValue();
                    intCount++;
                    intCounts.set(intIndex, new Integer(intCount));
                }
            }
            for (int intI = 0; intI < objDungeons.size(); intI++)
            {
                strTest += "For " + getTemplateName(((obj_id)objDungeons.get(intI))) + " NPC Count is " + ((Integer)intCounts.get(intI)).intValue() + "\r\n";
                sendSystemMessageTestingOnly(self, "For " + getTemplateName(((obj_id)objDungeons.get(intI))) + " NPC Count is " + ((Integer)intCounts.get(intI)).intValue());
                LOG("npe", "For " + getTemplateName(((obj_id)objDungeons.get(intI))) + " NPC Count is " + ((Integer)intCounts.get(intI)).intValue());
            }
            sendSystemMessageTestingOnly(self, "Total Hibernating Mob Count is " + intTest + " from " + objMobs.length);
            strTest += "Total Hibernating Mob Count is " + intTest + " from " + objMobs.length;
            saveTextOnClient(self, "dungeon1.txt", strTest);
        }
        if (strCommands[0].equals("dumpLocations"))
        {
            String strTest = "";
            obj_id objBuilding = getTopMostContainer(self);
            obj_id[] objContents = getShipContents(objBuilding);
            for (int intI = 0; intI < objContents.length; intI++)
            {
                if (hasScript(objContents[intI], "systems.spawning.spawner_area"))
                {
                    String strName = getStringObjVar(objContents[intI], "strName");
                    strTest += getDumpString(objContents[intI], strName);
                }
                else if (!hasScript(objContents[intI], "content_tools.sequencer_object"))
                {
                    if (!hasScript(objContents[intI], "systems.spawning.spawned_tracker"))
                    {
                        String strName = getEncodedName(objContents[intI]);
                        if (strName == null)
                        {
                            strName = getName(objContents[intI]);
                        }
                        int intIndex = strName.indexOf("@");
                        if (intIndex > -1)
                        {
                            strName = getString(utils.unpackString(strName));
                        }
                        strTest += getDumpString(objContents[intI], strName);
                    }
                }
            }
            saveTextOnClient(self, "everything.txt", strTest);
            sendSystemMessageTestingOnly(self, "length was " + objContents.length);
        }
        if (strCommands[0].equals("checkForceRegen"))
        {
            int baseRegenRate = getSkillStatisticModifier(self, "jedi_force_power_regen");
            float regenRate = (float)baseRegenRate;
            sendSystemMessageTestingOnly(self, "regen Rate base is " + regenRate);
            float fltActualRegen = getForcePowerRegenRate(self);
            sendSystemMessageTestingOnly(self, "Actual Modified Regen is " + fltActualRegen);
        }
        if (strCommands[0].equals("checkRate"))
        {
            obj_id objTarget = getLookAtTarget(self);
            int intRate = player_structure.getMaintenanceRate(objTarget);
            sendSystemMessageTestingOnly(self, "rate is " + intRate);
        }
        if (strCommands[0].equals("goOnLeave"))
        {
            factions.goOnLeaveWithDelay(self, 1.0f);
            sendSystemMessageTestingOnly(self, "GOING");
        }
        if (strCommands[0].equals("startFlash"))
        {
            newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.volume.0", true);
            sendSystemMessageTestingOnly(self, "ON");
        }
        if (strCommands[0].equals("stopFlash"))
        {
            newbieTutorialHighlightUIElement(self, "/GroundHUD.Toolbar.volume.0", false);
            sendSystemMessageTestingOnly(self, "OFF");
        }
        if (strCommands[0].equals("fixLots"))
        {
            adjustLotCount(self, 10);
            sendSystemMessageTestingOnly(self, "fixed");
        }
        if (strCommands[0].equals("startLoop"))
        {
            messageTo(getLookAtTarget(self), "updateGCWInfo", null, 30, false);
            sendSystemMessageTestingOnly(self, "Looping");
        }
        if (strCommands[0].equals("setHyperspaceLoc"))
        {
            setObjVar(self, "trHyperspace", getTransform_o2p(space_transition.getContainingShip(self)));
            sendSystemMessageTestingOnly(self, "Set Transform");
        }
        if (strCommands[0].equals("fakeHyperspace"))
        {
            transform trTest = getTransformObjVar(self, "trHyperspace");
            obj_id objTest = space_create.createShipHyperspace("imp_tie_bomber_tier1", trTest, null);
            setLookAtTarget(self, objTest);
            sendSystemMessageTestingOnly(self, "Made " + objTest + " at " + getLocation(objTest) + " and you are at " + getLocation(space_transition.getContainingShip(self)));
        }
        if (strCommands[0].equals("hitMe"))
        {
            startCombat(getLookAtTarget(self), self);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("welcomeAboard"))
        {
            obj_id objTarget = getLookAtTarget(self);
            chat.chat(objTarget, "Welcome aboard, Sir. Lord Vader is waiting for you on the bridge.");
            doAnimationAction(objTarget, anims.PLAYER_SALUTE2);
            return SCRIPT_OVERRIDE;
        }
        if (strCommands[0].equals("resetStormtroopers"))
        {
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 2000, "object/mobile/stormtrooper.iff");
            for (int intI = 0; intI < objTestObjects.length; intI++)
            {
                detachScript(objTestObjects[intI], "e3demo.e3_stormtrooper");
                attachScript(objTestObjects[intI], "e3demo.e3_stormtrooper");
            }
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("makeStormtroopers"))
        {
            location locTest = getLocation(self);
            locTest.x = 30;
            locTest.y = 205;
            locTest.z = 318;
            for (int intI = 0; intI < 6; intI++)
            {
                obj_id objTest = createObject("object/mobile/stormtrooper.iff", locTest);
                attachScript(objTest, "e3demo.e3_stormtrooper");
                locTest.z = locTest.z - 1.5f;
            }
            locTest.x = 28;
            locTest.y = 205;
            locTest.z = 318;
            for (int intI = 0; intI < 6; intI++)
            {
                obj_id objTest = createObject("object/mobile/stormtrooper.iff", locTest);
                attachScript(objTest, "e3demo.e3_stormtrooper");
                locTest.z = locTest.z - 1.5f;
            }
        }
        if (strCommands[0].equals("lifeDay"))
        {
            location locTest = getLocation(self);
            final String[] strItems = 
            {
                "object/tangible/furniture/decorative/event_chewbacca_toy.iff",
                "object/tangible/furniture/decorative/event_lifeday05_painting_01.iff",
                "object/tangible/furniture/decorative/event_lifeday05_painting_02.iff",
                "object/tangible/furniture/decorative/event_lifeday05_painting_03.iff",
                "object/tangible/furniture/decorative/event_wookiee_rifle_display.iff",
                "object/tangible/furniture/decorative/event_wookiee_shield.iff",
                "object/tangible/furniture/decorative/event_wroshyr_tree.iff",
                "object/tangible/painting/painting_wookiee_m.iff",
                "object/tangible/painting/painting_wookiee_f.iff",
                "object/tangible/painting/painting_trees_s01.iff",
                "object/tangible/loot/quest/lifeday_orb.iff",
                "object/tangible/wearables/wookiee/wke_lifeday_robe.iff"
            };
            for (int intI = 0; intI < strItems.length; intI++)
            {
                locTest.x = locTest.x + 3;
                obj_id objTest = createObject(strItems[intI], locTest);
            }
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("decrementScore"))
        {
            setObjVar(self, "faction", strCommands[1]);
            gcw.decrementGCWScore(self);
            sendSystemMessageTestingOnly(self, "Decrementing");
        }
        if (strCommands[0].equals("steal"))
        {
            obj_id myPack = utils.getInventoryContainer(self);
            loot.generateTheftLoot(myPack, getLookAtTarget(self), 0, 1);
        }
        if (strCommands[0].equals("incrementScore"))
        {
            setObjVar(self, "faction", strCommands[1]);
            gcw.incrementGCWScore(self);
            sendSystemMessageTestingOnly(self, "Incrementing");
        }
        if (strCommands[0].equals("forceSpawn"))
        {
            messageTo(getLookAtTarget(self), "doSpawnEvent", null, 1, false);
            sendSystemMessageTestingOnly(self, "Doing spawny thing");
        }
        if (strCommands[0].equals("forceMaintenance"))
        {
            obj_id objTarget = getLookAtTarget(self);
            int intLoops = 5;
            int intTimeStamp = intLoops * 1800;
            int intTime = getGameTime();
            intTime = intTime - intTimeStamp;
            setObjVar(objTarget, player_structure.VAR_LAST_MAINTANENCE, intTimeStamp);
            dictionary dctParams = new dictionary();
            dctParams.put("objSender", self);
            messageTo(objTarget, "OnMaintenanceLoop", dctParams, 0, false);
            sendSystemMessageTestingOnly(self, "Sent maintenance loop request to " + objTarget);
        }
        if (strCommands[0].equals("shootTest"))
        {
            setState(self, STATE_COMBAT, true);
            setCombatTarget(self, getLookAtTarget(self));
            sequencer.doCombatAnimation(self, getLookAtTarget(self), "fire_1_single_light");
            sendSystemMessageTestingOnly(self, "s");
        }
        if (strCommands[0].equals("twiddle"))
        {
            doAnimationAction(self, anims.PLAYER_USE_GENERIC_OBJECT_MEDIUM);
        }
        if (strCommands[0].equals("validateSequencer"))
        {
            obj_id master = sequencer.getSequenceObject(strCommands[1]);
            sendSystemMessageTestingOnly(self, "" + master);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("forceMaintenanceLong"))
        {
            obj_id objTarget = getLookAtTarget(self);
            int intLoops = 45;
            int intTimeStamp = intLoops * 1800;
            int intTime = getGameTime();
            intTime = intTime - intTimeStamp;
            setObjVar(objTarget, player_structure.VAR_LAST_MAINTANENCE, intTimeStamp);
            messageTo(objTarget, "OnMaintenanceLoop", null, 0, false);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("sellItemTest"))
        {
            obj_id objItem = createObject("object/weapon/ranged/rifle/rifle_t21.iff", getLocation(self));
            setObjVar(objItem, "junkDealer.intPrice", 100);
            attachScript(objItem, "object.autostack");
        }
        if (strCommands[0].equals("verboseDebug"))
        {
            setObjVar(self, "verboseDebug", 1);
            sendSystemMessageTestingOnly(self, "Debug on");
        }
        if (strCommands[0].equals("makeGuy"))
        {
            location here = getLocation(self);
            obj_id npc = create.object("commoner", here);
            attachScript(npc, "systems.missions.dynamic.mission_deliver_npc");
        }
        if (strCommands[0].equals("checkMissions"))
        {
            obj_id[] objMissions = getMissionObjects(self);
            for (int intI = 0; intI < objMissions.length; intI++)
            {
                location locTest = getMissionStartLocation(objMissions[intI]);
                sendSystemMessageTestingOnly(self, "Start: " + locTest);
                float dist = utils.getDistance2D(getLocation(self), locTest);
                sendSystemMessageTestingOnly(self, "dist is " + dist);
                locTest = getMissionEndLocation(objMissions[intI]);
                sendSystemMessageTestingOnly(self, "End: " + locTest);
                dist = utils.getDistance2D(getLocation(self), locTest);
                sendSystemMessageTestingOnly(self, "dist is " + dist);
            }
        }
        if (strCommands[0].equals("verboseDebug"))
        {
            setObjVar(self, "verboseDebug", 1);
            sendSystemMessageTestingOnly(self, "Debug on");
        }
        if (strCommands[0].equals("makeGuy"))
        {
            location here = getLocation(self);
            obj_id npc = create.object("commoner", here);
            attachScript(npc, "systems.missions.dynamic.mission_deliver_npc");
        }
        if (strCommands[0].equals("checkMissions"))
        {
            obj_id[] objMissions = getMissionObjects(self);
            for (int intI = 0; intI < objMissions.length; intI++)
            {
                location locTest = getMissionStartLocation(objMissions[intI]);
                sendSystemMessageTestingOnly(self, "Start: " + locTest);
                float dist = utils.getDistance2D(getLocation(self), locTest);
                sendSystemMessageTestingOnly(self, "dist is " + dist);
                locTest = getMissionEndLocation(objMissions[intI]);
                sendSystemMessageTestingOnly(self, "End: " + locTest);
                dist = utils.getDistance2D(getLocation(self), locTest);
                sendSystemMessageTestingOnly(self, "dist is " + dist);
            }
        }
        if (strCommands[0].equals("testFaction"))
        {
            int intFaction = pvpGetAlignedFaction(self);
            sendSystemMessageTestingOnly(self, "I am " + intFaction);
            obj_id objTarget = getLookAtTarget(self);
            intFaction = pvpGetAlignedFaction(objTarget);
            sendSystemMessageTestingOnly(self, "They are " + intFaction);
        }
        if (strCommands[0].equals("setupRebelFaction"))
        {
            factions.addFactionStanding(self, factions.FACTION_REBEL, 30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("setupImperialFaction"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("negativeImperial"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, -30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("negativeRebel"))
        {
            factions.addFactionStanding(self, factions.FACTION_REBEL, -30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("negativeImperial"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, -30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("negativeRebel"))
        {
            factions.addFactionStanding(self, factions.FACTION_REBEL, -30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("setupImperialFaction"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, 30000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("logTest"))
        {
            LOG("space", "WOBBLE");
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("testReload"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            int[] slots = space_crafting.getShipInstalledSlots(ship);
            for (int i = 0; i < slots.length; i++)
            {
                sendSystemMessageTestingOnly(self, "checking slot " + slots[i]);
                if (space_crafting.isMissileSlot(ship, slots[i]))
                {
                    space_pilot_command.weaponLauncherSpaceReload(ship, self, slots[i]);
                }
                else if (space_crafting.isCounterMeasureSlot(ship, slots[i]))
                {
                    sendSystemMessageTestingOnly(self, "aweads");
                    space_pilot_command.countermeasureLauncherSpaceReload(ship, self, slots[i]);
                }
            }
            sendSystemMessageTestingOnly(self, "WACHSD");
        }
        if (strCommands[0].equals("registerRecruiter"))
        {
            obj_id objTarget = getLookAtTarget(self);
            String strMapName = "pilot_recruiter";
            String strPrimaryCategory = "space";
            String strSecondaryCategory = "space_recruiter";
            string_id strMapNameId = new string_id("map_loc_type_n", strMapName);
            obj_id objContainer = getTopMostContainer(objTarget);
            location locTest = getLocation(objTarget);
            if (isIdValid(objContainer))
            {
                locTest = getLocation(objContainer);
            }
            addPlanetaryMapLocation(objTarget, utils.packStringId(strMapNameId), (int)locTest.x, (int)locTest.z, strPrimaryCategory, strSecondaryCategory, MLT_STATIC, planetary_map.NO_FLAG);
            sendSystemMessageTestingOnly(self, "Registered the whozit");
        }
        if (strCommands[0].equals("countShips"))
        {
            obj_id[] objStuff = getAllObjectsWithScript(getLocation(space_transition.getContainingShip(self)), 32000, "space.combat.combat_ship");
            sendSystemMessageTestingOnly(self, "objStuff length is " + objStuff.length);
        }
        if (strCommands[0].equals("countLair"))
        {
            obj_id[] objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/creature/player/bothan_male.iff");
            sendSystemMessageTestingOnly(self, "objStuff length is " + objStuff.length);
            int intCount = 0;
            objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/location/location_32.iff");
            intCount = intCount + objStuff.length;
            objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/location/location_48.iff");
            intCount = intCount + objStuff.length;
            objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/location/location_64.iff");
            intCount = intCount + objStuff.length;
            objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/location/location_80.iff");
            intCount = intCount + objStuff.length;
            objStuff = getAllObjectsWithTemplate(getLocation(self), 32000, "object/tangible/location/location_96.iff");
            intCount = intCount + objStuff.length;
            sendSystemMessageTestingOnly(self, "intCount is " + intCount);
        }
        if (strCommands[0].equals("clearImperialFaction"))
        {
            factions.addFactionStanding(self, factions.FACTION_IMPERIAL, -1000);
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("zoneStatus"))
        {
            obj_id objShip = getPilotedShip(self);
            obj_id[] objSpawnedShips = getAllObjectsWithObjVar(getLocation(objShip), 32000, "objParent");
            obj_id[] objAllShips = getAllObjectsWithScript(getLocation(objShip), 32000, "space.combat.combat_ship");
            sendSystemMessageTestingOnly(self, "Ships From Spawners: " + objSpawnedShips.length);
            sendSystemMessageTestingOnly(self, "Ships From Spawners and Missions: " + objAllShips.length);
            obj_id[] objSpawners = getAllObjectsWithTemplate(getLocation(objShip), 32000, "object/tangible/space/content_infrastructure/basic_spawner.iff");
            for (int intI = 0; intI < objSpawners.length; intI++)
            {
                obj_id objParent = objSpawners[intI];
                LOG("space", "Checking " + objParent);
                int intSpawnCount = getIntObjVar(objParent, "intSpawnCount");
                int intActualCount = 0;
                for (int intJ = 0; intJ < objSpawnedShips.length; intJ++)
                {
                    obj_id objSpawnParent = getObjIdObjVar(objSpawnedShips[intJ], "objParent");
                    LOG("space", "Checking spawn parent of " + objSpawnParent);
                    if (objSpawnParent == objParent)
                    {
                        intActualCount = intActualCount + 1;
                    }
                }
                sendSystemMessageTestingOnly(self, objParent + " Set count is :" + intSpawnCount + " and spawned count is: " + intActualCount);
                if (intActualCount > intSpawnCount)
                {
                    sendSystemMessageTestingOnly(self, "WARNING! WARNING! WARNING! SPAWN COUNT IS OVER SET COUNT!@!@");
                }
            }
        }
        if (strCommands[0].equals("youDead"))
        {
            attachScript(getLookAtTarget(self), "e3demo.e3_deadguy");
        }
        if (strCommands[0].equals("ownYou"))
        {
            obj_id objTest = getTopMostContainer(self);
            setOwner(objTest, self);
            sendSystemMessageTestingOnly(self, "Owned");
        }
        if (strCommands[0].equals("makeShip"))
        {
            obj_id objTest = space_transition.getContainingShip(self);
            if (!isIdValid(objTest))
            {
                objTest = self;
            }
            if (strCommands.length > 1)
            {
                obj_id objShip = createObject(strCommands[1], getLocation(objTest));
                setOwner(objShip, self);
            }
        }
        if (strCommands[0].equals("whereAmI"))
        {
            obj_id objTest = space_transition.getContainingShip(self);
            sendSystemMessageTestingOnly(self, "I am at " + getLocation(objTest));
        }
        if (strCommands[0].equals("trainerTest"))
        {
            space_utils.openCommChannelAfterLoad(getPilotedShip(self), getPilotedShip(self));
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("fixChassis"))
        {
            obj_id objTarget = getLookAtTarget(self);
            setShipMaximumChassisHitPoints(objTarget, 10000);
            setShipCurrentChassisHitPoints(objTarget, 10000);
        }
        if (strCommands[0].equals("addBooster"))
        {
            boolean boolTest = shipPseudoInstallComponent(space_transition.getContainingShip(self), space_crafting.BOOSTER, (1521210513));
            sendSystemMessageTestingOnly(self, "Added booster, " + boolTest);
        }
        if (strCommands[0].equals("radialCrash"))
        {
            for (int intI = 0; intI < 100000; intI++)
            {
                menu_info mi = new menu_info();
                string_id strSpam = new string_id("space/foo", "spam");
                mi.addRootMenu(menu_info_types.CRAFT_START, strSpam);
                Object[] newParams = new Object[3];
                newParams[0] = getLookAtTarget(self);
                newParams[1] = self;
                newParams[2] = mi;
                space_utils.callTrigger("OnObjectMenuRequest", newParams);
                newParams[0] = getLookAtTarget(self);
                newParams[1] = self;
                Integer intTest = new Integer(menu_info_types.CRAFT_START);
                newParams[2] = intTest;
                space_utils.callTrigger("OnObjectMenuSelect", newParams);
                queueCommand(self, (1283441724), getLookAtTarget(self), "", COMMAND_PRIORITY_IMMEDIATE);
            }
            sendSystemMessageTestingOnly(self, "DONE DONE DONE");
            sendSystemMessageTestingOnly(self, "DONE DONE DONE");
            sendSystemMessageTestingOnly(self, "DONE DONE DONE");
            sendSystemMessageTestingOnly(self, "DONE DONE DONE");
        }
        if (strText.equals("shotTest"))
        {
            obj_id objMonsters[] = getAllObjectsWithObjVar(getLocation(self), 320000, "ai");
            obj_id objTarget = objMonsters[0];
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
            queueCommand(self, (2027179676), objTarget, "", COMMAND_PRIORITY_FRONT);
        }
        if (strText.equals("healClone"))
        {
            healing.healClone(self, true);
            sendSystemMessageTestingOnly(self, "f");
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("mapTest"))
        {
            String[] strTest = planetary_map.getUniquePlanetaryMapCategories(null);
            for (int intI = 0; intI < strTest.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "strTest " + intI + " is " + strTest[intI]);
            }
            strTest = planetary_map.getUniquePlanetaryMapCategories("space");
            for (int intI = 0; intI < strTest.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "strTest " + intI + " is " + strTest[intI]);
            }
        }
        if (strText.equals("testRegen"))
        {
            sendSystemMessageTestingOnly(self, "regen is " + getHealthRegenRate(self));
            setAttrib(self, HEALTH, 1);
            setAttrib(self, ACTION, 1);
            setAttrib(self, MIND, 1);
        }
        if (strText.equals("lootTest"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            String strTable = "npc/generic_1_10";
            loot.makeLootInContainer(objInventory, strTable, 10, 10);
            sendSystemMessageTestingOnly(self, "Loot");
        }
        if (strText.equals("asd"))
        {
            String strSource = "datatables/mob/creatures.iff";
            String[] strLootTables = dataTableGetStringColumn(strSource, "lootList");
            String[] strNames = dataTableGetStringColumn(strSource, "creatureName");
            for (int intI = 0; intI < strLootTables.length; intI++)
            {
                int intIndex = strNames[intI].indexOf("ep3");
                if (intIndex > -1)
                {
                    if (!strLootTables[intI].equals(""))
                    {
                        sendSystemMessageTestingOnly(self, "loot table is " + strLootTables[intI]);
                    }
                }
            }
        }
        if (strText.equals("redoLoot"))
        {
            String strSource = "datatables/mob/creatures.iff";
            String[] strLootTables = dataTableGetStringColumn(strSource, "lootTable");
            String[] strOldLootTables = dataTableGetStringColumn(strSource, "lootList");
            int[] intNiches = dataTableGetIntColumn(strSource, "niche");
            String[] strMeatTypes = dataTableGetStringColumn(strSource, "meatType");
            int[] intLevels = dataTableGetIntColumn(strSource, "BaseLevel");
            String strTableToWrite = "datatables/loot/test_rewrite.tab";
            String[] strHeaders = new String[1];
            strHeaders[0] = "strTest";
            String[] strTypes = new String[1];
            strTypes[0] = "s";
            boolean boolTest = datatable.createDataTable(strTableToWrite, strHeaders, strTypes);
            for (int intI = 0; intI < strLootTables.length; intI++)
            {
                String strTest = "";
                if (strLootTables[intI].equals(""))
                {
                    String strLevelString = getLevelString(intLevels[intI]);
                    if ((intNiches[intI] == 3) || (intNiches[intI] == 10))
                    {
                        strTest = "droid/droid_" + strLevelString;
                    }
                    else if (intNiches[intI] == 5)
                    {
                        strTest = "npc/npc_" + strLevelString;
                    }
                    else 
                    {
                        int intIndex = strMeatTypes[intI].indexOf("insect");
                        if (intIndex > -1)
                        {
                            strTest = "creature/insect_" + strLevelString;
                        }
                        else 
                        {
                            strTest = "creature/creature_" + strLevelString;
                        }
                    }
                }
                else 
                {
                    strTest = strLootTables[intI];
                }
                dictionary dctTest = new dictionary();
                dctTest.put("strTest", strTest);
                datatable.serverDataTableAddRow(strTableToWrite, dctTest);
            }
        }
        if (strText.equals("makeDynamicWeapon"))
        {
            static_item.makeDynamicObject("dynamic_weapon_basic_all", utils.getInventoryContainer(self), rand(5, 60));
            sendSystemMessageTestingOnly(self, "woof");
        }
        if (strText.equals("makeStaticWeapon"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id lootItem = static_item.createNewItemFunction("weapon_tow_pistol_02_01", objInventory);
            sendSystemMessageTestingOnly(self, "made " + lootItem);
        }
        if (strText.equals("checkStats"))
        {
            skill.recalcPlayerPools(self, false);
            sendSystemMessageTestingOnly(self, "Action Max is " + getMaxAttrib(self, ACTION));
            sendSystemMessageTestingOnly(self, "Health Max is " + getMaxAttrib(self, HEALTH));
        }
        if (strText.equals("weaponUpdates"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id[] objContents = utils.getContents(self, true);
            for (int intI = 0; intI < objContents.length; intI++)
            {
                if (hasScript(objContents[intI], "systems.combat.combat_weapon"))
                {
                    setWeaponAttackSpeed(objContents[intI], .25f);
                    setWeaponMinDamage(objContents[intI], 5);
                    setWeaponMaxDamage(objContents[intI], 10);
                }
            }
            sendSystemMessageTestingOnly(self, "Done length was " + objContents.length);
        }
        if (strCommands[0].equals("setupMonster"))
        {
            obj_id objMonsters[] = getAllObjectsWithObjVar(getLocation(self), 320000, "ai");
            for (int intI = 0; intI < objMonsters.length; intI++)
            {
                setMaxAttrib(objMonsters[intI], HEALTH, 50);
                setAttrib(objMonsters[intI], HEALTH, 50);
                debugSpeakMsg(objMonsters[intI], "SET HEALTH AND STUFF");
            }
            sendSystemMessageTestingOnly(self, "as");
        }
        if (strCommands[0].equals("engineTesting"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id objItem = createObject("object/tangible/ship/components/engine/eng_gallofree_jx2.iff", objInventory, "");
            setName(objItem, "Tier 1");
            objItem = createObject("object/tangible/ship/components/engine/eng_kse_a5x.iff", objInventory, "");
            setName(objItem, "Tier 2");
            objItem = createObject("object/tangible/ship/components/engine/eng_kse_a7x.iff", objInventory, "");
            setName(objItem, "Tier 3");
            objItem = createObject("object/tangible/ship/components/engine/eng_novaldex_x7.iff", objInventory, "");
            setName(objItem, "Tier 4");
            objItem = createObject("object/tangible/ship/components/engine/eng_kse_advanced.iff", objInventory, "");
            setName(objItem, "Tier 5");
            sendSystemMessageTestingOnly(self, "Made stuff");
        }
        if (strCommands[0].equals("chassisRepairTest"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            for (int intI = 0; intI < 5; intI++)
            {
                obj_id objKit = createObject("object/tangible/ship/crafted/repair/repair_kit_chassis.iff", objInventory, "");
                setCount(objKit, 400);
            }
            obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
            obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
            setShipCurrentChassisHitPoints(objShip, 10);
        }
        if (strCommands[0].equals("getShipIds"))
        {
            obj_id[] objControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            for (int intI = 0; intI < objControlDevices.length; intI++)
            {
                obj_id objTestShip = space_transition.getShipFromShipControlDevice(objControlDevices[intI]);
                sendSystemMessageTestingOnly(self, "Ship from PCD " + objControlDevices[intI] + " is " + objTestShip);
            }
        }
        if (strCommands[0].equals("woozle"))
        {
            space_quest.grantNewbieShip(self, "neutral");
            sendSystemMessageTestingOnly(self, "asd");
        }
        if (strCommands[0].equals("zeroShip"))
        {
            obj_id objDefender = getLookAtTarget(self);
            int[] intRawSlots = getShipChassisSlots(objDefender);
            for (int intI = 0; intI < intRawSlots.length; intI++)
            {
                if (isShipSlotInstalled(objDefender, intRawSlots[intI]))
                {
                    setShipComponentArmorHitpointsCurrent(objDefender, intRawSlots[intI], 0);
                    setShipComponentHitpointsCurrent(objDefender, intRawSlots[intI], 0);
                }
            }
            setShipCurrentChassisHitPoints(objDefender, 0);
            sendSystemMessageTestingOnly(self, "Zeroed");
        }
        if (strCommands[0].equals("weaponRepairKits"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id objKit = createObject("object/tangible/ship/crafted/repair/repair_kit_weapon.iff", objInventory, "");
            setCount(objKit, 10000);
            sendSystemMessageTestingOnly(self, "Kitted");
        }
        if (strCommands[0].equals("engineRepairKits"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id objKit = createObject("object/tangible/ship/crafted/repair/repair_kit_engine.iff", objInventory, "");
            setCount(objKit, 10000);
            sendSystemMessageTestingOnly(self, "Kitted");
        }
        if (strCommands[0].equals("checkEngineValue"))
        {
            dictionary dctParams = new dictionary();
            dctParams.put("intLoops", 90);
            messageTo(self, "checkEngineValue", dctParams, .25f, false);
            sendSystemMessageTestingOnly(self, "checking");
        }
        if (strCommands[0].equals("checkImmune"))
        {
            space_transition.doAIImmunityCheck(space_transition.getContainingShip(self));
            sendSystemMessageTestingOnly(self, "Chassis is " + getShipChassisType(space_transition.getContainingShip(self)));
            playMusic(self, "sound/ship_hyperspace_countdown.snd");
        }
        if (strCommands[0].equals("raiseMass"))
        {
            obj_id[] objControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            int intCount = 0;
            for (int intI = 0; intI < objControlDevices.length; intI++)
            {
                obj_id objTestShip = space_transition.getShipFromShipControlDevice(objControlDevices[intI]);
                setChassisComponentMassMaximum(objTestShip, 500000f);
                sendSystemMessageTestingOnly(self, "massed ");
            }
        }
        if (strCommands[0].equals("forceMaintenance"))
        {
            obj_id objTarget = getLookAtTarget(self);
            int intLoops = 100;
            int intTimeStamp = intLoops * 1800;
            int intTime = getGameTime();
            intTime = intTime - intTimeStamp;
            setObjVar(objTarget, player_structure.VAR_LAST_MAINTANENCE, intTime);
            dictionary dctParams = new dictionary();
            dctParams.put("objSender", self);
            messageTo(objTarget, "OnMaintenanceLoop", dctParams, 0, false);
            sendSystemMessageTestingOnly(self, "Sent maintenance loop request to " + objTarget);
        }
        if (strCommands[0].equals("setHitpoints"))
        {
            obj_id objTarget = getLookAtTarget(self);
            setHitpoints(objTarget, 1000);
            sendSystemMessageTestingOnly(self, "for " + objTarget + " hitpoints are " + getHitpoints(objTarget));
            setInvulnerableHitpoints(objTarget, 2000);
            sendSystemMessageTestingOnly(self, "for " + objTarget + " hitpoints are " + getHitpoints(objTarget));
            setInvulnerableHitpoints(objTarget, 0);
            sendSystemMessageTestingOnly(self, "for " + objTarget + " hitpoints are " + getHitpoints(objTarget));
            setInvulnerableHitpoints(objTarget, 3500);
            sendSystemMessageTestingOnly(self, "for " + objTarget + " hitpoints are " + getHitpoints(objTarget));
        }
        if (strCommands[0].equals("forceMaintenanceRaw"))
        {
            obj_id objTarget = getLookAtTarget(self);
            dictionary dctParams = new dictionary();
            dctParams.put("objSender", self);
            messageTo(objTarget, "OnMaintenanceLoop", dctParams, 0, false);
            sendSystemMessageTestingOnly(self, "Sent maintenance loop request to " + objTarget);
        }
        if (strCommands[0].equals("powerDebug"))
        {
            obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
            obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
            objShip = space_transition.getContainingShip(self);
            setShipReactorEnergyGenerationRate(objShip, 500);
            setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.SHIELD_GENERATOR, 200);
            setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.ENGINE, 100);
            setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.WEAPON_0, 100);
            setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.WEAPON_1, 100);
            setShipComponentEnergyMaintenanceRequirement(objShip, space_crafting.CAPACITOR, 100);
            sendSystemMessageTestingOnly(self, "whacked ");
        }
        if (strCommands[0].equals("emptyInventory"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id[] objContents = getContents(objInventory);
            for (int intI = 0; intI < objContents.length; intI++)
            {
                destroyObject(objContents[intI]);
            }
            sendSystemMessageTestingOnly(self, "WHOOM");
        }
        if (strCommands[0].equals("validateConduits"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            LOG("space", "Fixing all conduits");
            if (utils.hasScriptVar(objShip, "objPlasmaConduits"))
            {
                Vector objPlasmaConduits = utils.getResizeableObjIdArrayScriptVar(objShip, "objPlasmaConduits");
                for (int intI = 0; intI < objPlasmaConduits.size(); intI++)
                {
                    if (hasCondition(((obj_id)objPlasmaConduits.get(intI)), CONDITION_ON))
                    {
                        Vector locBrokenComponents = getResizeableLocationArrayObjVar(objShip, "locBrokenComponents");
                        location locTest = getLocation(((obj_id)objPlasmaConduits.get(intI)));
                        int intIndex = utils.getElementPositionInArray(locBrokenComponents, locTest);
                        if (intIndex > -1)
                        {
                            sendSystemMessageTestingOnly(self, ((obj_id)objPlasmaConduits.get(intI)) + " is in the list");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, ((obj_id)objPlasmaConduits.get(intI)) + " is NOT IN THE LIST");
                        }
                    }
                }
            }
        }
        if (strCommands[0].equals("fixConduits"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            space_crafting.fixAllPlasmaConduits(objShip);
            sendSystemMessageTestingOnly(self, "Fixed");
        }
        if (strCommands[0].equals("fillInventory"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            for (int intI = 0; intI < 60; intI++)
            {
                obj_id objItem = createObject("object/tangible/ship/components/engine/eng_kse_a5x.iff", objInventory, "");
            }
            sendSystemMessageTestingOnly(self, "Filled");
        }
        if (strCommands[0].equals("bridge1"))
        {
            attachScript(getLookAtTarget(self), "e3demo.e3_bridge_worker_1");
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("setLaunchLocation"))
        {
            obj_id[] objTestObjects = getAllObjectsWithTemplate(getLocation(self), 320000, "object/ship/player/player_star_destroyer.iff");
            obj_id objShip = objTestObjects[0];
            obj_id objMe = space_transition.getContainingShip(self);
            setObjVar(objShip, "trLaunchLocation", getTransform_o2p(objMe));
            setObjVar(objShip, "locLaunchLocation", getLocation(objMe));
            sendSystemMessageTestingOnly(self, "Done");
        }
        if (strCommands[0].equals("testExit"))
        {
        }
        if (strCommands[0].equals("destroyerUp"))
        {
            location locTest = new location();
            obj_id objShip = space_transition.getContainingShip(self);
            obj_id objCell = getCellId(objShip, "elevator05");
            locTest.cell = objCell;
            setLocation(self, locTest);
            playClientEffectObj(self, "clienteffect/elevator_rise.cef", self, null);
            elevatorMove(self, 1);
        }
        if (strCommands[0].equals("destroyerDown"))
        {
            location locTest = new location();
            obj_id objShip = space_transition.getContainingShip(self);
            obj_id objCell = getCellId(objShip, "elevator_e3");
            locTest.cell = objCell;
            setLocation(self, locTest);
        }
        if (strCommands[0].equals("equipClothes"))
        {
        }
        if (strCommands[0].equals("breakShipInDatapad"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
            obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
            int intSlot = space_crafting.ENGINE;
            setShipComponentHitpointsCurrent(objShip, intSlot, 0);
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, objShip);
            intSlot = space_crafting.WEAPON_0;
            setShipComponentHitpointsCurrent(objShip, intSlot, 0);
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, objShip);
            intSlot = space_crafting.REACTOR;
            setShipComponentHitpointsCurrent(objShip, intSlot, 0);
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, objShip);
            intSlot = space_crafting.CAPACITOR;
            setShipComponentHitpointsCurrent(objShip, intSlot, 0);
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, objShip);
            intSlot = space_crafting.DROID_INTERFACE;
            setShipComponentHitpointsCurrent(objShip, intSlot, 0);
            setShipComponentArmorHitpointsCurrent(objShip, intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, objShip);
            sendSystemMessageTestingOnly(self, "whacked " + getShipComponentName(objShip, intSlot));
        }
        if (strCommands[0].equals("setBackShields"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            int intSide = space_combat.BACK;
            space_combat.setShieldHitPoints(objShip, intSide, 5.0f);
        }
        if (strCommands[0].equals("damageShip"))
        {
            obj_id objAttacker = space_transition.getContainingShip(self);
            float fltDamage = 200;
            int intSide = 1;
            int intTargetedComponent = 112;
            int intWeaponSlot = space_crafting.WEAPON_0;
            space_combat.doShieldDamage(objAttacker, objAttacker, intWeaponSlot, fltDamage, intSide);
            space_combat.doArmorDamage(objAttacker, objAttacker, intWeaponSlot, fltDamage, intSide);
            space_combat.doComponentDamage(objAttacker, objAttacker, intWeaponSlot, intTargetedComponent, fltDamage, intSide);
        }
        if (strCommands[0].equals("registerRecruiter"))
        {
            obj_id objTarget = getLookAtTarget(self);
            String strMapName = "pilot_recruiter";
            String strPrimaryCategory = "space";
            String strSecondaryCategory = "recruiter";
            string_id strMapNameId = new string_id("map_loc_type_n", strMapName);
            obj_id objContainer = getTopMostContainer(objTarget);
            location locTest = getLocation(objTarget);
            if (isIdValid(objContainer))
            {
                locTest = getLocation(objContainer);
            }
            addPlanetaryMapLocation(objTarget, utils.packStringId(strMapNameId), (int)locTest.x, (int)locTest.z, strPrimaryCategory, strSecondaryCategory, MLT_STATIC, planetary_map.NO_FLAG);
            sendSystemMessageTestingOnly(self, "Registered the whozit");
        }
        if (strCommands[0].equals("fixChassis"))
        {
            obj_id[] objPcds = space_transition.findShipControlDevicesForPlayer(self);
            obj_id objShip = space_transition.getShipFromShipControlDevice(objPcds[0]);
            dictionary dctParams = new dictionary();
            dctParams.put("strSlot", "" + space_crafting.CHASSIS);
            dctParams.put("objShip", objShip);
            space_utils.notifyObject(self, "fixit", dctParams);
        }
        if (strCommands[0].equals("damageReactor"))
        {
            obj_id objShip = getPilotedShip(self);
            float fltCurrentHitPoints = getShipComponentHitpointsCurrent(objShip, ship_chassis_slot_type.SCST_reactor);
            sendSystemMessageTestingOnly(self, "changing hp from " + fltCurrentHitPoints + " to " + fltCurrentHitPoints / 2);
            fltCurrentHitPoints = fltCurrentHitPoints / 2;
            setShipComponentHitpointsCurrent(objShip, ship_chassis_slot_type.SCST_reactor, fltCurrentHitPoints);
            space_combat.recalculateEfficiency(ship_chassis_slot_type.SCST_reactor, objShip);
        }
        if (strCommands[0].equals("tauntMe"))
        {
            string_id strSpam = new string_id("space/space_interaction", "aborting_reactor_pump");
            space_utils.tauntPlayer(self, getLookAtTarget(self), strSpam);
        }
        if (strCommands[0].equals("checkKessel"))
        {
            obj_id objKesselObject = getPlanetByName("space_light1");
            obj_id objKesselManager = utils.getObjIdScriptVar(objKesselObject, "objKesselManager");
            sendSystemMessageTestingOnly(self, "on planet " + objKesselObject + " got " + objKesselManager);
        }
        if (strCommands[0].equals("damageUp"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            setShipCapacitorEnergyMaximum(objShip, 300000);
            setShipCapacitorEnergyRechargeRate(objShip, 1200000);
            setShipWeaponDamageMaximum(objShip, space_crafting.WEAPON_0, 2500000);
        }
        if (strCommands[0].equals("hasReactor"))
        {
            boolean boolTest = isShipSlotInstalled(getLookAtTarget(self), 2);
            sendSystemMessageTestingOnly(self, "boolTest is " + boolTest);
            int intChassisSlot = space_crafting.getComponentSlotInt("reactor");
            sendSystemMessageTestingOnly(self, "slot is " + intChassisSlot);
        }
        if (strCommands[0].equals("resetSpaceFaction"))
        {
            int spaceFaction = space_transition.getPlayerSpaceFaction(self);
            shipSetSpaceFaction(space_transition.getContainingShip(self), spaceFaction);
            sendSystemMessageTestingOnly(self, "Reset faction");
        }
        if (strCommands[0].equals("checkTargetFaction"))
        {
            obj_id objTarget = getLookAtTarget(self);
            String strFaction = "rebel";
            sendSystemMessageTestingOnly(self, "set " + objTarget + " to Faction " + strFaction);
        }
        if (strCommands[0].equals("getShip"))
        {
        }
        if (strCommands[0].equals("updateShipFaction"))
        {
            space_transition.updateShipFaction(space_transition.getContainingShip(self), self);
            sendSystemMessageTestingOnly(self, "updated faction ");
            int intTest = (-615855020);
            sendSystemMessageTestingOnly(self, "Imperial : " + intTest);
            intTest = (370444368);
            sendSystemMessageTestingOnly(self, "Rebel : " + intTest);
        }
        if (strCommands[0].equals("testSM"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            string_id strSpam = new string_id("fpp", "fooo");
            space_utils.sendSystemMessageShip(objShip, strSpam, true, false, true, true);
            space_utils.sendSystemMessageToOperations(objShip, strSpam);
            obj_id objOfficer = space_utils.getOperationsOfficer(objShip);
            sendSystemMessageTestingOnly(self, "objOfficer is " + objOfficer);
        }
        if (strCommands[0].equals("makeShipImperial"))
        {
            int intTest = (-615855020);
            obj_id objShip = space_transition.getContainingShip(self);
            shipSetSpaceFaction(objShip, intTest);
            sendSystemMessageTestingOnly(self, "Set Ship Faction to Imperial");
        }
        if (strCommands[0].equals("makeShipRebel"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            int intTest = (370444368);
            shipSetSpaceFaction(objShip, intTest);
            sendSystemMessageTestingOnly(self, "Set Ship Faction to Rebel");
        }
        if (strCommands[0].equals("clearOvert"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            space_transition.clearOvertStatus(objShip);
            space_transition.updatePVPStatus(objShip);
        }
        if (strCommands[0].equals("checkOvert"))
        {
            if (pvpGetType(self) == PVPTYPE_DECLARED)
            {
                sendSystemMessageTestingOnly(self, "Overt");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "covert");
            }
        }
        if (strCommands[0].equals("asd"))
        {
            factions.goCovert(self);
        }
        if (strCommands[0].equals("checkMe"))
        {
            sendSystemMessageTestingOnly(self, "Faction " + space_transition.getPlayerSpaceFaction(self));
            obj_id objShip = space_transition.getContainingShip(self);
            sendSystemMessageTestingOnly(self, "ship Faction " + shipGetSpaceFaction(objShip));
            int intTest = (-615855020);
            sendSystemMessageTestingOnly(self, "Imperial : " + intTest);
            intTest = (370444368);
            sendSystemMessageTestingOnly(self, "Rebel : " + intTest);
        }
        if (strCommands[0].equals("deckOut"))
        {
            space_combat.setupCapitalShipFromTurretDefinition(getLookAtTarget(self), "star_destroyer");
            sendSystemMessageTestingOnly(self, "DECKED");
        }
        if (strCommands[0].equals("testFoo"))
        {
            space_crafting.setEngineMaximumPitch(self, 100);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("lookAtTarget"))
        {
            obj_id objTarget = getLookAtTarget(self);
            sendSystemMessageTestingOnly(self, "lookAtTarget is " + objTarget);
        }
        if (strCommands[0].equals("myScriptVars"))
        {
            deltadictionary dctScriptVars = self.getScriptVars();
            sendSystemMessageTestingOnly(self, "Script Vars are :" + dctScriptVars.toString());
        }
        if (strCommands[0].equals("nameShip"))
        {
            obj_id shipControlDevice = space_transition.findEmptyShipControlDeviceForPlayer(self);
            obj_id objShip = getPilotedShip(self);
            space_transition.setShipName(objShip, self, shipControlDevice);
            sendSystemMessageTestingOnly(self, "asd");
        }
        if (strCommands[0].equals("dumpShip"))
        {
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            obj_id shipControlDevice = shipControlDevices[0];
            obj_id ship = space_transition.getShipFromShipControlDevice(shipControlDevice);
            location x = getLocation(self);
            setLocation(ship, x);
            debugSpeakMsg(self, "Ship ID = " + ship);
            return SCRIPT_CONTINUE;
        }
        if (strCommands[0].equals("yourScriptVars"))
        {
            obj_id objTest = getLookAtTarget(self);
            deltadictionary dctScriptVars = objTest.getScriptVars();
            sendSystemMessageTestingOnly(self, "Script Vars are :" + dctScriptVars.toString());
        }
        if (strCommands[0].equals("checkLocation"))
        {
            sendSystemMessageTestingOnly(self, "i am at " + getLocation(self));
        }
        if (strCommands[0].equals("resendMail"))
        {
            obj_id objTarget = getLookAtTarget(self);
            removeObjVar(objTarget, player_structure.VAR_LAST_MESSAGE);
            player_structure.sendMaintenanceMail(objTarget);
            sendSystemMessageTestingOnly(self, "Resetn");
        }
        if (strCommands[0].equals("checkMail"))
        {
            player_structure.sendCondemnedMail(getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "sentMial");
        }
        if (strCommands[0].equals("sendNewbieMail"))
        {
            string_id strSubject = new string_id("space/space_interaction", "email_subject_" + strCommands[1]);
            string_id strBody = new string_id("space/space_interaction", "email_body_" + strCommands[1]);
            string_id strSender = new string_id("space/space_interaction", "email_sender_" + strCommands[1]);
            String strSenderName = utils.packStringId(strSender);
            sendSystemMessageTestingOnly(self, "sending mail " + strCommands[1]);
            utils.sendMail(strSubject, strBody, self, strSenderName);
        }
        if (strCommands[0].equals("sendNewbieMailMessage"))
        {
            if (strCommands[1].equals("1"))
            {
                dictionary dctParams = new dictionary();
                dctParams.put("intMail", 1);
                messageTo(self, "sendNewbieMails", dctParams, rand(10, 11), true);
                sendSystemMessageTestingOnly(self, "Email 1");
            }
            if (strCommands[1].equals("2"))
            {
                dictionary dctParams = new dictionary();
                dctParams.put("intMail", 2);
                messageTo(self, "sendNewbieMails", dctParams, rand(10, 11), true);
                sendSystemMessageTestingOnly(self, "Email 2");
            }
            if (strCommands[1].equals("3"))
            {
                dictionary dctParams = new dictionary();
                dctParams.put("intMail", 3);
                messageTo(self, "sendNewbieMails", dctParams, rand(10, 11), true);
                sendSystemMessageTestingOnly(self, "Email 2");
            }
        }
        if (strCommands[0].equals("testItem1"))
        {
            obj_id objInventory = utils.getInventoryContainer(self);
            obj_id objItem = createObjectOverloaded("object/tangible/ship/components/shield_generator/shd_seinar_experimental_a1.iff", objInventory);
            sendSystemMessageTestingOnly(self, "made item " + objItem + " in " + objInventory);
        }
        if (strCommands[0].equals("testItem2"))
        {
            obj_id objItem = createObject("object/tangible/ship/components/weapon/wpn_armek_sw4.iff", getLocation(self));
            sendSystemMessageTestingOnly(self, "made item " + objItem);
        }
        if (strCommands[0].equals("hitMe"))
        {
            obj_id objTarget = getLookAtTarget(self);
            obj_id objShip = getPilotedShip(self);
            dictionary dctParams = new dictionary();
            dctParams.put("objShip", objTarget);
            space_utils.notifyObject(objShip, "megaDamage", dctParams);
            sendSystemMessageTestingOnly(self, "objTarget of " + objTarget + " is attacking " + objShip);
        }
        if (strCommands[0].equals("findStationEggs"))
        {
            obj_id objStationEggs[] = getAllObjectsWithObjVar(getLocation(self), 320000, "intStationRespawner");
            for (int intI = 0; intI < objStationEggs.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "Egg is " + objStationEggs[intI]);
            }
        }
        if (strCommands[0].equals("destroyerPhase"))
        {
            obj_id objManager = space_battlefield.getManagerObject();
            space_battlefield.doDestroyerPhase(objManager);
            sendSystemMessageTestingOnly(self, "DESTROYER");
        }
        if (strCommands[0].equals("makeSquad"))
        {
            space_create.createSquad(self, "squad_test", getTransform_o2p(self), 10, null);
            sendSystemMessageTestingOnly(self, "spawned");
        }
        if (strCommands[0].equals("breakThing1"))
        {
            obj_id objTest = getLookAtTarget(self);
            setInvulnerableHitpoints(objTest, 1);
            sendSystemMessageTestingOnly(self, "Hitpoints are " + getHitpoints(objTest));
        }
        if (strCommands[0].equals("breakThing50"))
        {
            obj_id objTest = getLookAtTarget(self);
            setInvulnerableHitpoints(objTest, 50);
            sendSystemMessageTestingOnly(self, "Hitpoints are " + getHitpoints(objTest));
        }
        if (strCommands[0].equals("breakThing100"))
        {
            obj_id objTest = getLookAtTarget(self);
            setInvulnerableHitpoints(objTest, 100);
            sendSystemMessageTestingOnly(self, "Hitpoints are " + getHitpoints(objTest));
        }
        if (strCommands[0].equals("breakThing500"))
        {
            obj_id objTest = getLookAtTarget(self);
            setInvulnerableHitpoints(objTest, 500);
            sendSystemMessageTestingOnly(self, "Hitpoints are " + getHitpoints(objTest));
        }
        if (strCommands[0].equals("fixThing"))
        {
            obj_id objTest = getLookAtTarget(self);
            setInvulnerableHitpoints(objTest, 1000);
            sendSystemMessageTestingOnly(self, "Hitpoints are " + getHitpoints(objTest));
        }
        if (strCommands[0].equals("setDifficulty"))
        {
            int intRawDifficulty = getLevel(self);
        }
        if (strCommands[0].equals("testDirectDamage"))
        {
            int intSlot = space_crafting.ENGINE;
            float fltPercent = utils.stringToFloat(strCommands[1]);
            fltPercent = fltPercent / 100;
            dictionary dctParams = new dictionary();
            dctParams.put("intSlot", intSlot);
            fltPercent = 1000 * fltPercent;
            dctParams.put("fltCurrentHitpoints", fltPercent);
            LOG("space", "Put");
            dctParams.put("fltMaximumHitpoints", 1000f);
            LOG("space", "DctParams is " + dctParams);
            space_utils.notifyObject(space_transition.getContainingShip(self), "componentEfficiencyChanged", dctParams);
            sendSystemMessageTestingOnly(self, "set " + intSlot + " to " + fltPercent);
        }
        if (strCommands[0].equals("bustEngine"))
        {
            int intSlot = space_crafting.ENGINE;
            setShipComponentHitpointsCurrent(getTopMostContainer(self), intSlot, 0);
            setShipComponentArmorHitpointsCurrent(getTopMostContainer(self), intSlot, 0);
            space_combat.recalculateEfficiency(intSlot, getTopMostContainer(self));
            sendSystemMessageTestingOnly(self, "WHACk!@");
        }
        if (strCommands[0].equals("breakHull"))
        {
            obj_id objTest = getLookAtTarget(self);
            obj_id objShip = space_transition.getContainingShip(self);
            space_crafting.breakHullPanel(objTest, objShip, getLocation(objTest), true);
            sendSystemMessageTestingOnly(self, "broke");
        }
        if (strCommands[0].equals("fixHull"))
        {
            obj_id objTest = getLookAtTarget(self);
            obj_id objShip = space_transition.getContainingShip(self);
            space_crafting.fixHullPanel(objTest, objShip, getLocation(objTest));
            sendSystemMessageTestingOnly(self, "fix ");
        }
        if (strCommands[0].equals("checkItemCount"))
        {
            int intCount = player_structure.getStructureNumItems(space_transition.getContainingShip(self));
            sendSystemMessageTestingOnly(self, "intCount is " + intCount);
        }
        if (strCommands[0].equals("checkTrainer"))
        {
            String strPrimary = "trainer";
            String strSub = "trainer_pilot_privateer";
            map_location[] map_locs = getPlanetaryMapLocations(strPrimary, strSub);
            if (map_locs.length == 0)
            {
                sendSystemMessageTestingOnly(self, "000000000");
            }
            sendSystemMessageTestingOnly(self, "map_locs x is " + map_locs[0].getX() + " y is " + map_locs[0].getY());
        }
        if (strCommands[0].equals("fixEngine"))
        {
            int intSlot = space_crafting.ENGINE;
            setShipComponentHitpointsCurrent(getTopMostContainer(self), intSlot, 100);
            space_combat.recalculateEfficiency(intSlot, getTopMostContainer(self));
            sendSystemMessageTestingOnly(self, "WHACk!@");
        }
        if (strCommands[0].equals("testCells"))
        {
            location locTest = getLocation(self);
            obj_id objCell = locTest.cell;
            obj_id objBuilding = getContainedBy(objCell);
            String[] strCells = getCellNames(objBuilding);
            if (strCells == null)
            {
                sendSystemMessageTestingOnly(self, "objCell is " + objCell + " building i s" + objBuilding);
            }
        }
        if (strCommands[0].equals("testItemCreation"))
        {
            obj_id objFoo = createObject("object/weapon/ranged/rifle/rifle_dlt20.iff", getTransform_o2p(self), null);
            sendSystemMessageTestingOnly(self, "Made object type " + objFoo);
        }
        if (strCommands[0].equals("setupShip"))
        {
            Vector objSparkers = new Vector();
            objSparkers.setSize(0);
            location locTest = getLocation(self);
            obj_id objObject = getContainedBy(locTest.cell);
            Vector objContents = new Vector();
            objContents.setSize(0);
            obj_id[] objCells = getContents(objObject);
            for (int intI = 0; intI < objCells.length; intI++)
            {
                obj_id[] objTestContents = getContents(objCells[intI]);
                if ((objTestContents != null) && (objTestContents.length > 0))
                {
                    for (int intJ = 0; intJ < objTestContents.length; intJ++)
                    {
                        String strTest = getTemplateName(objTestContents[intJ]);
                        if (strTest.equals("object/tangible/space/content_infrastructure/generic_egg_small.iff"))
                        {
                            objSparkers = utils.addElement(objSparkers, objTestContents[intJ]);
                        }
                    }
                }
            }
            utils.setScriptVar(objObject, "objSparkers", objSparkers);
            sendSystemMessageTestingOnly(self, "set sparkers of length " + objSparkers + " on " + objObject);
        }
        if (strCommands[0].equals("locationTesting"))
        {
            obj_id objTarget = getLookAtTarget(self);
            location locTest = getLocation(objTarget);
            Vector locTests = new Vector();
            locTests.setSize(0);
            locTests = utils.addElement(locTests, locTest);
            setObjVar(self, "locTest2", locTest);
        }
        if (strCommands[0].equals("validateLocations"))
        {
            obj_id objTarget = getLookAtTarget(self);
            location locTest = getLocation(objTarget);
            Vector locTests = getResizeableLocationArrayObjVar(self, "locTest");
            for (int intI = 0; intI < locTests.size(); intI++)
            {
                sendSystemMessageTestingOnly(self, "locTest is " + locTest);
                sendSystemMessageTestingOnly(self, "locTests[" + intI + "] is " + ((location)locTests.get(intI)));
                if (locTest.equals(((location)locTests.get(intI))))
                {
                    sendSystemMessageTestingOnly(self, "Equality comparison is true");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "Equality comparison is false");
                }
            }
        }
        if (strCommands[0].equals("fixConduit"))
        {
            space_crafting.fixPlasmaConduit(getLookAtTarget(self), space_transition.getContainingShip(self), getLocation(getLookAtTarget(self)));
            sendSystemMessageTestingOnly(self, "Fixing " + getLookAtTarget(self));
        }
        if (strCommands[0].equals("fixCombat"))
        {
            setState(self, STATE_COMBAT, false);
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("breakConduit"))
        {
            space_crafting.breakPlasmaConduit(getLookAtTarget(self), space_transition.getContainingShip(self), getLocation(getLookAtTarget(self)), true);
            sendSystemMessageTestingOnly(self, "BROKE");
        }
        if (strCommands[0].equals("checkCondition"))
        {
            sendSystemMessageTestingOnly(self, "Condition is " + hasCondition(getLookAtTarget(self), CONDITION_ON));
        }
        if (strCommands[0].equals("closestStation"))
        {
            obj_id objClosestStation = space_combat.getClosestSpaceStation(self);
            sendSystemMessageTestingOnly(self, "Found " + objClosestStation);
        }
        if (strCommands[0].equals("getSpawnerData"))
        {
            space_utils.notifyObject(getLookAtTarget(self), "getSpawnerData", null);
            sendSystemMessageTestingOnly(self, "weh");
        }
        if (strCommands[0].equals("checkStations"))
        {
            obj_id objManager = space_battlefield.getManagerObject();
            obj_id objStationEggs[] = getAllObjectsWithObjVar(getLocation(objManager), 320000, "intStationRespawner");
            if (objStationEggs != null)
            {
                for (int intI = 0; intI < objStationEggs.length; intI++)
                {
                    String strObjVars = utils.getStringLocalVar(objStationEggs[intI], "strObjVars");
                    String strScripts = utils.getStringLocalVar(objStationEggs[intI], "strScripts");
                    String strTemplate = utils.getStringLocalVar(objStationEggs[intI], "strTemplate");
                    LOG("space", "making " + strTemplate + " from object " + objStationEggs[intI]);
                    transform trTest = getTransform_o2p(objStationEggs[intI]);
                }
            }
        }
        if (strCommands[0].equals("getShipId"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            sendSystemMessageTestingOnly(self, "I am in " + objShip);
        }
        if (strCommands[0].equals("pingObject"))
        {
            String strObject = strCommands[1];
            sendSystemMessageTestingOnly(self, "strCommands[1] is " + strObject);
            obj_id objTarget = utils.stringToObjId(strObject);
            dictionary dctParams = new dictionary();
            dctParams.put("objSender", self);
            messageTo(objTarget, "pingObject", dctParams, 0, false);
            sendSystemMessageTestingOnly(self, "Sent message to " + objTarget + " with ping message");
        }
        if (strCommands[0].equals("doSpawns"))
        {
            messageTo(space_transition.getContainingShip(self), "delayedSpawn", null, 1, false);
            sendSystemMessageTestingOnly(self, "going");
        }
        if (strCommands[0].equals("damageParticleTest"))
        {
            int intDamageType = 0;
            if (strCommands[1].equals("chassis"))
            {
                intDamageType = 1;
            }
            else if (strCommands[1].equals("component"))
            {
                intDamageType = 2;
            }
            else if (strCommands[1].equals("shield"))
            {
                intDamageType = 3;
            }
            else if (strCommands[1].equals("armor"))
            {
                intDamageType = 4;
            }
            int intDamageIntensity = 0;
            if (strCommands[2].equals("light"))
            {
                intDamageIntensity = 1;
            }
            if (strCommands[2].equals("heavy"))
            {
                intDamageIntensity = 2;
            }
            dictionary dctParams = new dictionary();
            dctParams.put("intDamageType", intDamageType);
            dctParams.put("intDamageIntensity", intDamageIntensity);
            location locTest = getLocation(self);
            obj_id objCell = locTest.cell;
            obj_id objShip = getContainedBy(objCell);
            space_utils.notifyObject(objShip, "interiorDamageNotification", dctParams);
            sendSystemMessageTestingOnly(self, "text : " + strText + " sent to " + objShip + "type is " + intDamageType + " and intensity is " + intDamageIntensity);
        }
        if (strCommands[0].equals("checkDroid"))
        {
            obj_id[] shipControlDevices = space_transition.findShipControlDevicesForPlayer(self);
            if (shipControlDevices != null)
            {
                for (int i = 0; i < shipControlDevices.length; ++i)
                {
                    sendSystemMessageTestingOnly(self, "objPcd is " + shipControlDevices[i]);
                    obj_id objShip = space_transition.getShipFromShipControlDevice(shipControlDevices[i]);
                    sendSystemMessageTestingOnly(self, "objShip is " + objShip);
                    obj_id objDroid = getDroidControlDeviceForShip(objShip);
                    sendSystemMessageTestingOnly(self, "objDroid is " + objDroid);
                }
            }
        }
        if (strCommands[0].equals("reset world sure"))
        {
            obj_id[] objObjects = getObjectsInRange(self, 32000);
            sendSystemMessageTestingOnly(self, "Destroying world");
            for (int intI = 0; intI < objObjects.length; intI++)
            {
                if (!isPlayer(objObjects[intI]))
                {
                    if (hasObjVar(objObjects[intI], "intNoDump"))
                    {
                        destroyObject(objObjects[intI]);
                    }
                }
            }
        }
        if (strCommands[0].equals("destroyObject"))
        {
            destroyObject(getLookAtTarget(self));
            sendSystemMessageTestingOnly(self, "KABOOM");
        }
        if (strCommands[0].equals("nukeworld"))
        {
            if (strCommands.length > 1)
            {
                if (strCommands[1].equals("sure"))
                {
                    obj_id[] objObjects = getObjectsInRange(self, 32000);
                    sendSystemMessageTestingOnly(self, "Destroying world");
                    for (int intI = 0; intI < objObjects.length; intI++)
                    {
                        if (isIdValid(objObjects[intI]) && !isPlayer(objObjects[intI]))
                        {
                            setObjVar(objObjects[intI], "intCleaningUp", 1);
                            trial.cleanupObject(objObjects[intI]);
                        }
                    }
                }
            }
        }
        if (strCommands[0].equals("stationTest"))
        {
            obj_id objStation = space_combat.getClosestSpaceStation(getPilotedShip(self));
            sendSystemMessageTestingOnly(self, "found " + objStation);
        }
        if (strCommands[0].equals("pilotShip"))
        {
            obj_id objShip = getLookAtTarget(self);
            if (isIdValid(objShip))
            {
                boolean boolTest = pilotShip(self, objShip);
                sendSystemMessageTestingOnly(self, "You are FLYING " + objShip + " and boolTest is " + boolTest);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "You have no ship targeted");
            }
        }
        if (strCommands[0].equals("checkShields"))
        {
            obj_id objShip = getPilotedShip(self);
            if (isShipSlotInstalled(objShip, ship_chassis_slot_type.SCST_shield_0))
            {
                sendSystemMessageTestingOnly(self, "shield generator");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No shield generator");
            }
        }
        if (strCommands[0].equals("unpilotShip"))
        {
            unpilotShip(self);
            sendSystemMessageTestingOnly(self, "NO MORE FLYING!");
        }
        if (strCommands[0].equals("checkOwner"))
        {
            obj_id objShip = getLookAtTarget(self);
            obj_id objOwner = getOwner(objShip);
            sendSystemMessageTestingOnly(self, "objOwner is " + objOwner);
        }
        if (strCommands[0].equals("setOwner"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            setOwner(objShip, self);
            sendSystemMessageTestingOnly(self, "setOwner to " + getOwner(objShip));
        }
        if (strCommands[0].equals("getWeaponEnergy"))
        {
        }
        if (strCommands[0].equals("setWeaponEnergy"))
        {
        }
        if (strCommands[0].equals("dataTableTest"))
        {
            String[] strHeaders = new String[3];
            String[] strHeaderTypes = new String[3];
            for (int intI = 0; intI < 3; intI++)
            {
                strHeaders[intI] = "hello" + intI;
                strHeaderTypes[intI] = "i";
            }
            boolean boolTest = datatable.createDataTable("test.tab", strHeaders, strHeaderTypes);
            sendSystemMessageTestingOnly(self, "return value is " + boolTest);
        }
        if (strCommands[0].equals("checkTargets"))
        {
            obj_id[] objTargets = pvpGetTargetsInRange(self, self, 32f);
            sendSystemMessageTestingOnly(self, "Found " + objTargets.length + " pvp targets in range");
        }
        if (strCommands[0].equals("jediTrainer"))
        {
            setupJediTrainer(self);
            sendSystemMessageTestingOnly(self, "setup jedi trainer");
        }
        if (strCommands[0].equals("changeLocation"))
        {
            location locTest = getLocationObjVar(self, "jedi.locTrainerLocation");
            locTest.area = "naboo";
            setObjVar(self, "jedi.locTrainerLocation", locTest);
            sendSystemMessageTestingOnly(self, "updated");
        }
        if (strCommands[0].equals("checkDifficultyLoop"))
        {
            messageTo(self, "checkDifficulty", null, 3, false);
        }
        if (strCommands[0].equals("jackVisibility"))
        {
            setJediVisibility(self, 4000);
            sendSystemMessageTestingOnly(self, "set");
        }
        if (strCommands[0].equals("damageHealth"))
        {
            setHealth(self, 1);
        }
        if (strCommands[0].equals("drainHealth"))
        {
            attrib_mod[] attrTest = new attrib_mod[1];
            attrTest[0] = utils.createAttribMod(HEALTH, -1 * 500, 0, 0, 15.0f);
            addAttribModifiers(self, attrTest);
        }
        if (strCommands[0].equals("incapMe"))
        {
            setHealth(self, -100);
        }
        if (strCommands[0].equals("checkVisibility"))
        {
            int intVisibility = getJediVisibility(self);
            sendSystemMessageTestingOnly(self, "visibility is " + intVisibility);
        }
        if (strCommands[0].equals("clearGallop"))
        {
            obj_id objMount = getMountId(self);
            obj_id objControlDevice = callable.getCallableCD(objMount);
            utils.removeScriptVar(objControlDevice, "mount.intGalloping");
            utils.removeScriptVar(objControlDevice, "mount.intTired");
            sendSystemMessageTestingOnly(self, "cleared");
        }
        if (strCommands[0].equals("goFast"))
        {
            obj_id objMount = getMountId(self);
            setBaseRunSpeed(objMount, 20.0f);
            sendSystemMessageTestingOnly(self, "Running");
        }
        if (strCommands[0].equals("goSlow"))
        {
            obj_id objMount = getMountId(self);
            setBaseRunSpeed(objMount, 6.0f);
            sendSystemMessageTestingOnly(self, "Jogging");
        }
        if (strCommands[0].equals("jediRevive"))
        {
            setForcePower(self, 9000);
            sendSystemMessageTestingOnly(self, "force power maxed");
        }
        if (strCommands[0].equals("bleedMe"))
        {
            dot.applyBleedingEffect(self, "foo", HEALTH, 100, 120);
        }
        if (strCommands[0].equals("diseaseMe"))
        {
            dot.applyDotEffect(self, dot.DOT_DISEASE, "foo2", 100, 120);
        }
        if (strCommands[0].equals("slowMe"))
        {
            setBaseRunSpeed(self, 1.0f);
        }
        if (strCommands[0].equals("fastMe"))
        {
            setBaseRunSpeed(self, 6.0f);
        }
        if (strCommands[0].equals("jediSetup"))
        {
            grantSkill(self, "jedi_padawan_novice");
            queueCommand(self, (-1292820740), null, "metal", COMMAND_PRIORITY_IMMEDIATE);
            queueCommand(self, (-1292820740), null, "mineral", COMMAND_PRIORITY_IMMEDIATE);
            queueCommand(self, (-1292820740), null, "chemical", COMMAND_PRIORITY_IMMEDIATE);
            queueCommand(self, (-1292820740), null, "gemstone", COMMAND_PRIORITY_IMMEDIATE);
            queueCommand(self, (-1292820740), null, "gas", COMMAND_PRIORITY_IMMEDIATE);
        }
        if (strCommands[0].equals("forcePower"))
        {
            setMaxForcePower(self, 100);
            sendSystemMessageTestingOnly(self, "Force power set to 100");
            int intTest = getMaxForcePower(self);
            sendSystemMessageTestingOnly(self, "Force power regotten is " + intTest);
        }
        if (strCommands[0].equals("reloadForcePower"))
        {
            int intMaxPower = getSkillStatisticModifier(self, "jedi_force_power_max");
            setMaxForcePower(self, intMaxPower);
            LOG("jedi", "Setting force power to " + intMaxPower);
            int intRegenRate = getSkillStatisticModifier(self, "jedi_force_power_regen");
            float fltRate = (float)intRegenRate;
            fltRate = fltRate / 10;
            setForcePowerRegenRate(self, fltRate);
        }
        if (strCommands[0].equals("getScriptVars"))
        {
            deltadictionary dctScriptVars = self.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        if (strCommands[0].equals("rawDifficulty"))
        {
            int intRawDifficulty = getLevel(self);
            sendSystemMessageTestingOnly(self, "getLevel returned " + intRawDifficulty);
        }
        if (strCommands[0].equals("skillDifficulty"))
        {
            int intCombatDifficulty = getLevel(self);
            sendSystemMessageTestingOnly(self, "combat difficulty is " + intCombatDifficulty);
        }
        if (strCommands[0].equals("getMissions"))
        {
            obj_id[] objMissions = getMissionObjects(self);
            for (int intI = 0; intI < objMissions.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "Mission is " + objMissions[intI]);
            }
        }
        if (strCommands[0].equals("getNewbieSpawnerScriptVars"))
        {
            obj_id[] objFoo = getAllObjectsWithTemplate(getLocation(self), 10, "object/tangible/creature_spawner/tatooine_spawner_hard.iff");
            if (objFoo != null)
            {
                for (int intI = 0; intI < objFoo.length; intI++)
                {
                    deltadictionary dctScriptVars = objFoo[intI].getScriptVars();
                    sendSystemMessageTestingOnly(self, "scriptvars for " + objFoo[intI] + " are " + dctScriptVars.toString());
                }
            }
        }
        if (strCommands[0].equals("getMissionData"))
        {
            obj_id[] objMissions = getMissionObjects(self);
            for (int intI = 0; intI < objMissions.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "Mission is " + objMissions[intI]);
                obj_id objMissionData = objMissions[intI];
                if (objMissionData == null)
                {
                    sendSystemMessageTestingOnly(self, " missionData is null");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "missionData is " + objMissionData);
                }
            }
        }
        if (strCommands[0].equals("checkDynamicRegion"))
        {
            region[] rgnRegions = getRegionsWithGeographicalAtPoint(getLocation(self), 600);
            if (rgnRegions == null)
            {
                sendSystemMessageTestingOnly(self, "NULL, No regions of type 600 found");
            }
            else if (rgnRegions.length == 0)
            {
                sendSystemMessageTestingOnly(self, "0 LENGTH, No regions of type 600 found");
            }
            else 
            {
                for (int intI = 0; intI < rgnRegions.length; intI++)
                {
                    sendSystemMessageTestingOnly(self, "region name is " + rgnRegions[intI].getName());
                }
            }
        }
        if (strCommands[0].equals("goodLocationSlope"))
        {
            location locTest = locations.getGoodLocationAroundLocation(getLocation(self), 1, 1, 3, 3, false, true);
            if (locTest == null)
            {
                sendSystemMessageTestingOnly(self, "good location is null");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "good location is " + locTest);
            }
        }
        else if (strCommands[0].equals("goodLocationNoSlope"))
        {
            location locTest = locations.getGoodLocationAroundLocation(getLocation(self), 5, 5, 10, 10, false, true);
            if (locTest == null)
            {
                sendSystemMessageTestingOnly(self, "good location is null");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "good location is " + locTest);
            }
        }
        else if (strCommands[0].equals("object"))
        {
            location locTest = getLocation(self);
            locTest.x = locTest.x + 10;
            obj_id objTest = createObject("object/weapon/ranged/pistol/pistol_dl44.iff", locTest);
            locTest.z = locTest.z + 10;
            objTest = createObject("object/weapon/ranged/pistol/pistol_dl44.iff", locTest);
            obj_id[] objFoo = getObjectsInRange(locTest, 10);
            sendSystemMessageTestingOnly(self, "objFoo length is " + objFoo.length);
            for (int intI = 0; intI < objFoo.length; intI++)
            {
                destroyObject(objFoo[intI]);
            }
        }
        else if (strCommands[0].equals("npcLairCheck"))
        {
        }
        else if (strCommands[0].equals("objectWithTemplate"))
        {
            location locTest = getLocation(self);
            locTest.x = locTest.x + 10;
            obj_id objTest = createObject("object/weapon/ranged/pistol/pistol_dl44.iff", locTest);
            locTest.z = locTest.z + 10;
            objTest = createObject("object/weapon/ranged/pistol/pistol_dl44.iff", locTest);
            obj_id[] objFoo = getAllObjectsWithTemplate(locTest, 10, "object/weapon/ranged/pistol/pistol_dl44.iff");
            sendSystemMessageTestingOnly(self, "objFoo length is " + objFoo.length);
            for (int intI = 0; intI < objFoo.length; intI++)
            {
                destroyObject(objFoo[intI]);
            }
        }
        else if (strCommands[0].equals("lairs"))
        {
            float fltDistance = rand(120, 150);
            location locHome = getLocation(self);
            obj_id[] objLairs = getAllObjectsWithTemplate(locHome, fltDistance, "object/tangible/lair/npc_lair.iff");
            sendSystemMessageTestingOnly(self, "length of lairs is " + objLairs.length + " at distance " + fltDistance);
        }
        else if (strCommands[0].equals("theaters"))
        {
            location locHome = getLocation(self);
            obj_id[] objLairs = getAllObjectsWithTemplate(locHome, 200, "object/tangible/lair/npc_lair.iff");
            int intCount = utils.countSubStringObjVars(objLairs, "spawing.lairType", "theater");
            sendSystemMessageTestingOnly(self, "intCount is " + intCount);
        }
        if (strCommands[0].equals("alarmOn"))
        {
            space_crafting.turnOnInteriorAlarms(space_transition.getContainingShip(self));
        }
        if (strCommands[0].equals("alarmOff"))
        {
            space_crafting.turnOffInteriorAlarms(space_transition.getContainingShip(self));
        }
        else if (strCommands[0].equals("getStats"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (objTarget != null)
            {
                int intConstitution = getConst(objTarget);
                int intHealth = getHealth(objTarget);
                sendSystemMessageTestingOnly(self, "Const=" + intConstitution + " Health=" + intHealth);
                int intStamina = getStam(objTarget);
                int intAction = getAction(objTarget);
                sendSystemMessageTestingOnly(self, "Stamina=" + intStamina + " Action=" + intAction);
                int intWillPower = getWill(objTarget);
                int intMind = getMind(objTarget);
                sendSystemMessageTestingOnly(self, "WillPower=" + intWillPower + " Mind=" + intMind);
            }
        }
        if (strCommands[0].equals("jediFull"))
        {
            String[] skillsNeeded = getStringArrayObjVar(self, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
            if (skillsNeeded != null)
            {
                for (int i = 0; i < skillsNeeded.length; ++i)
                {
                    skill.grantSkillToPlayer(self, skillsNeeded[i]);
                }
            }
        }
        if (strCommands[0].equals("whoAreYou"))
        {
            obj_id objTarget = getLookAtTarget(self);
            obj_id objPilot = getPilotId(objTarget);
            sendSystemMessageTestingOnly(self, "objPilot is " + objPilot);
        }
        if (strCommands[0].equals("getInCellTest"))
        {
            obj_id objShip = getLookAtTarget(self);
            if (!isIdValid(objShip))
            {
                sendSystemMessageTestingOnly(self, "No look at target");
                return SCRIPT_CONTINUE;
            }
            obj_id[] objCells = getContents(objShip);
            if ((objCells == null) || (objCells.length == 0))
            {
                sendSystemMessageTestingOnly(self, "ship  doesn't have an interior");
                return SCRIPT_CONTINUE;
            }
            obj_id objCell = objCells[0];
            location locTest = new location();
            locTest.cell = objCell;
        }
        if (strCommands[0].equals("getOutTest"))
        {
        }
        if (strCommands[0].equals("spawnEvent"))
        {
            obj_id objTarget = getLookAtTarget(self);
            messageTo(objTarget, "doSpawnEvent", null, 0, false);
            sendSystemMessageTestingOnly(self, "done");
        }
        if (strCommands[0].equals("jedi5"))
        {
            String[] skillsNeeded = getStringArrayObjVar(self, pclib.OBJVAR_JEDI_SKILL_REQUIREMENTS);
            if (skillsNeeded != null)
            {
                for (int i = 0; i < 5; ++i)
                {
                    skill.grantSkillToPlayer(self, skillsNeeded[i]);
                }
            }
        }
        if (strCommands[0].equals("setJediTime"))
        {
            int intTime = getGameTime();
            sendSystemMessageTestingOnly(self, "set time stamp");
            setObjVar(self, "jedi.timeStamp", intTime);
        }
        if (strCommands[0].equals("damage"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (objTarget != null)
            {
                obj_id objWeapon = getCurrentWeapon(self);
                hit_result cbtHitData = new hit_result();
                cbtHitData.success = true;
                cbtHitData.baseRoll = 50;
                cbtHitData.finalRoll = 100;
                cbtHitData.hitLocation = rand(0, 5);
                cbtHitData.canSee = true;
                cbtHitData.damage = 200;
                cbtHitData.bleedingChance = 1;
                doDamage(self, objTarget, objWeapon, cbtHitData);
                sendSystemMessageTestingOnly(self, "200 damage done to target");
                debugSpeakMsg(objTarget, "ow!");
            }
        }
        if (strCommands[0].equals("soundTest"))
        {
            playMusic(self, "sound/sys_comm_rebel.snd");
        }
        if (strCommands[0].equals("makeJedi"))
        {
            removeObjVar(self, "jedi.timeStamp");
            addJediSlot(self);
            playMusic(self, "sound/music_amb_underwater_b.snd");
            setObjVar(self, "jedi.enabled", 1);
            string_id strSpam = new string_id("jedi_spam", "force_sensitive");
            sendSystemMessage(self, strSpam);
        }
        if (strCommands[0].equals("music"))
        {
            playMusic(self, "sound/music_int_complete_rebel.snd");
        }
        if (strCommands[0].equals("badWeapon"))
        {
            obj_id objWeapon = createObject("object/weapon/ranged/rifle/rifle_dlt20a.iff", getLocation(self));
            setWeaponAttackSpeed(objWeapon, 1.5f);
        }
        if (strCommands[0].equals("checkFlags"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (isIdValid(objTarget))
            {
                sendSystemMessageTestingOnly(self, "NPC is: " + objTarget);
                sendSystemMessageTestingOnly(self, "InvulnerableFlag is: " + isInvulnerable(objTarget));
                sendSystemMessageTestingOnly(self, "pvpCanAttack is: " + pvpCanAttack(self, objTarget));
                sendSystemMessageTestingOnly(self, "pvpCanHelp is " + pvpCanHelp(self, objTarget));
                sendSystemMessageTestingOnly(self, "Your PVPType is " + pvpGetType(self));
                sendSystemMessageTestingOnly(self, "Target PVPType is " + pvpGetType(objTarget));
                sendSystemMessageTestingOnly(self, "Your Faction is " + pvpGetAlignedFaction(self));
                sendSystemMessageTestingOnly(self, "Target Faction is " + pvpGetAlignedFaction(objTarget));
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No target");
            }
        }
        if (strCommands[0].equals("checkAreaFlags"))
        {
            obj_id[] objStuff = getObjectsInRange(getLocation(self), 40);
            sendSystemMessageTestingOnly(self, "length is " + objStuff.length);
            for (int intI = 0; intI < objStuff.length; intI++)
            {
                sendSystemMessageTestingOnly(self, "NPC is: " + objStuff[intI]);
                sendSystemMessageTestingOnly(self, "InvulnerableFlag is: " + isInvulnerable(objStuff[intI]));
                sendSystemMessageTestingOnly(self, "pvpCanAttack is: " + pvpCanAttack(self, objStuff[intI]));
            }
        }
        if (strCommands[0].equals("burySomeHouses"))
        {
            sendSystemMessageTestingOnly(self, "burying");
            location locSpawnLocation = getLocation(self);
            locSpawnLocation.x = 1988;
            locSpawnLocation.z = -1996;
            location locGoodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, 72, 72, 75, 75, false, true);
            if (locGoodLocation != null)
            {
                sendSystemMessageTestingOnly(self, "location is " + locGoodLocation);
                String strLairType = "tatooine_dim_u_church_neutral_large_theater";
                int intMinDifficulty = 10;
                int intMaxDifficulty = 20;
                int intPlayerDifficulty = rand(intMinDifficulty, intMaxDifficulty);
                int intNumToSpawn = 0;
                String strTemplate = "object/tangible/lair/npc_lair.iff";
                String strTemplateToSpawn = "";
                String strBuildingType = "";
                obj_id objCreatedTemplate = createObject(strTemplate, locGoodLocation);
                if (!(objCreatedTemplate == null))
                {
                    setObjVar(objCreatedTemplate, "spawning.intDifficultyLevel", intPlayerDifficulty);
                    String strDifficulty = create.getLairDifficulty(intMinDifficulty, intMaxDifficulty, intPlayerDifficulty);
                    setObjVar(objCreatedTemplate, "spawning.lairDifficulty", strDifficulty);
                    setObjVar(objCreatedTemplate, "spawning.lairType", strLairType);
                    setObjVar(objCreatedTemplate, "spawning.buildingTrackingType", strBuildingType);
                    if (!strBuildingType.equals(""))
                    {
                        setObjVar(objCreatedTemplate, "spawning.buildingType", strBuildingType);
                    }
                    messageTo(objCreatedTemplate, "handle_Spawn_Setup_Complete", null, 0, false);
                }
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "null");
            }
        }
        if (strCommands[0].equals("commTest"))
        {
            dictionary dctParams = new dictionary();
            dctParams.put("objStation", getLookAtTarget(self));
            messageTo(space_transition.getContainingShip(self), "openComm", dctParams, 0, false);
        }
        if (strCommands[0].equals("ringCalc"))
        {
            location locSpawnLocation = getLocation(self);
            locSpawnLocation.x = 1988;
            locSpawnLocation.z = -1996;
            location locTest = utils.getRandomLocationInRing(locSpawnLocation, 100, 110);
            sendSystemMessageTestingOnly(self, "location is " + locTest);
        }
        if (strCommands[0].equals("missionLocation"))
        {
            location locTest = getGoodMissionLocation(getLocation(self), self);
            sendSystemMessageTestingOnly(self, "location found is " + locTest);
        }
        if (strCommands[0].equals("isInMissionCity"))
        {
            location locTest = getLocation(self);
            boolean boolTest = locations.isInMissionCity(locTest);
            sendSystemMessageTestingOnly(self, "at location " + locTest + " isInMissionCity is " + boolTest);
        }
        if (strCommands[0].equals("whatCity"))
        {
            region[] rgnCities = getRegionsWithMunicipalAtPoint(getLocation(self), regions.MUNI_TRUE);
            if (rgnCities != null)
            {
                sendSystemMessageTestingOnly(self, "name is " + rgnCities[0].getName());
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "nul");
            }
            rgnCities = getRegionsWithGeographicalAtPoint(getLocation(self), regions.GEO_CITY);
            if (rgnCities != null)
            {
                sendSystemMessageTestingOnly(self, "name is " + rgnCities[0].getName());
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "nul");
            }
        }
        if (strCommands[0].equals("spawnCheckWithoutCreation"))
        {
            for (int intM = 0; intM < 2; intM++)
            {
                float fltMinSpawnDistance = 30 + (rand(10, 30) / 2);
                if (fltMinSpawnDistance > 100)
                {
                    fltMinSpawnDistance = 100;
                }
                float fltMaxSpawnDistance = 200;
                location locSpawnLocation = new location();
                int intI = 0;
                Vector locSpawnLocations = new Vector();
                locSpawnLocations.setSize(0);
                while (intI < 3)
                {
                    final float[] QUADRANT_START_ANGLE = 
                    {
                        0,
                        47,
                        -47
                    };
                    final float[] QUADRANT_SIZE = 
                    {
                        12,
                        20,
                        20
                    };
                    float fltSpawnDistance = rand(fltMinSpawnDistance, fltMaxSpawnDistance);
                    locSpawnLocation = utils.getLocationInArc(self, QUADRANT_START_ANGLE[intI], QUADRANT_SIZE[intI], fltSpawnDistance);
                    int intSize = rand(30, 72);
                    location goodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, intSize, intSize, 75, 75, false, true);
                    if (goodLocation == null)
                    {
                        sendSystemMessageTestingOnly(self, "Null location");
                    }
                    else 
                    {
                        float fltY = getHeightAtLocation(goodLocation.x, goodLocation.z);
                        goodLocation.y = fltY;
                        locSpawnLocations = utils.addElement(locSpawnLocations, goodLocation);
                    }
                    intI = intI + 1;
                }
                intI = 0;
                boolean boolCheckForTheaters = true;
                location locCurrentLocation = getLocation(self);
                while (intI < locSpawnLocations.size())
                {
                    locSpawnLocation = ((location)locSpawnLocations.get(intI));
                    String strLairType = "tatooine_dim_u_church_neutral_large_theater";
                    if (canSpawn(self, locSpawnLocation, true, strLairType))
                    {
                        sendSystemMessageTestingOnly(self, "spawnLocation is " + locSpawnLocation);
                    }
                    intI = intI + 1;
                }
            }
        }
        if (strCommands[0].equals("components"))
        {
            obj_id objShip = space_transition.getContainingShip(self);
            for (int intI = 0; intI < 10; intI++)
            {
                obj_id objManager = utils.getObjIdLocalVar(objShip, "objInteriorManager" + intI);
                sendSystemMessageTestingOnly(self, "manager slot " + intI + " is " + objManager);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public location getGoodMissionLocation(location locSpawnLocation, obj_id objPlayer) throws InterruptedException
    {
        int intSize = 72;
        location locGoodLocation = locations.getGoodLocationAroundLocation(locSpawnLocation, intSize, intSize, 300, 300, false, true);
        if (locGoodLocation == null)
        {
            locGoodLocation = locations.getGoodLocationAroundLocation(getLocation(objPlayer), intSize, intSize, 400, 400, false, true);
            if (locGoodLocation == null)
            {
                return null;
            }
        }
        if (locations.isInMissionCity(locGoodLocation))
        {
            locGoodLocation = locations.getGoodLocationAroundLocation(getLocation(objPlayer), intSize, intSize, 400, 400, false, true);
            if (locGoodLocation == null)
            {
                return null;
            }
            if (locations.isInMissionCity(locGoodLocation))
            {
                return null;
            }
        }
        region rgnBattlefield = battlefield.getBattlefield(locGoodLocation);
        if (rgnBattlefield != null)
        {
            return null;
        }
        if (locations.isInMissionCity(locGoodLocation))
        {
            return null;
        }
        return locGoodLocation;
    }
    public boolean checkTemplatesInRange(obj_id objPlayer, location locHome, boolean boolCheckForTheaters, String strObjectType) throws InterruptedException
    {
        boolean boolLogFailures = true;
        boolean boolVerboseMode = true;
        float fltDistance = rand(120, 200);
        obj_id[] objTemplates = getObjectsInRange(locHome, fltDistance);
        if (objTemplates == null)
        {
            return true;
        }
        else 
        {
            if (boolCheckForTheaters)
            {
                obj_id[] objLairs = getAllObjectsWithTemplate(locHome, fltDistance, "object/tangible/lair/npc_lair.iff");
                if (objLairs != null)
                {
                    sendSystemMessageTestingOnly(objPlayer, "Non null objLairs.. length is " + objLairs.length);
                    if (objLairs.length > 0)
                    {
                        String strSpam = objPlayer + " Too many lairs in the area, kicking out, area was " + locHome + " number of lairs is " + objLairs.length;
                        sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                        return false;
                    }
                }
                if (strObjectType != null)
                {
                    int intIndex = strObjectType.indexOf("theater");
                    if (intIndex > -1)
                    {
                        objLairs = getAllObjectsWithTemplate(locHome, 200, "object/creature/planet/bothan_male.iff");
                        int intCount = utils.countSubStringObjVars(objLairs, "spawing.lairType", "theater");
                        if (intCount > 0)
                        {
                            String strSpam = objPlayer + " theaters in range is " + intCount + " too many at location " + locHome;
                            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                            return false;
                        }
                    }
                }
                obj_id[] objTheaters = getAllObjectsWithObjVar(locHome, 200, "isScenario");
                if (objTheaters != null)
                {
                    if (objTheaters.length > 0)
                    {
                        String strSpam = objPlayer + " too many scenarios neat " + locHome + " count was " + objTheaters.length;
                        sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                        return false;
                    }
                }
            }
            if (objTemplates.length > SPAWN_CHECK_TEMPLATE_LIMIT)
            {
                if (boolLogFailures)
                {
                    String strSpam = objPlayer + " found too many normal templates in range of " + locHome + " count was " + objTemplates.length;
                    sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                }
                return false;
            }
        }
        return true;
    }
    public boolean canSpawn(obj_id objPlayer, location locSpawnLocation, boolean boolCheckForTheaters, String strObjectType) throws InterruptedException
    {
        boolean boolLogFailures = true;
        boolean boolVerboseMode = true;
        if (!isSpawningAllowed(locSpawnLocation))
        {
            String strSpam = objPlayer + " spawning not allowed on " + locSpawnLocation.area;
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        int intServerSpawnLimit = 0;
        String strSpam = "";
        location locCurrentLocation = getLocation(objPlayer);
        if (locCurrentLocation.area == "tutorial")
        {
            strSpam = objPlayer + " canSpawn failed: spawning disabled in tutorial";
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        region[] rgnCities = getRegionsWithMunicipalAtPoint(locCurrentLocation, regions.MUNI_TRUE);
        if (rgnCities != null)
        {
            strSpam = objPlayer + " canSpawn failed: spawning not allowed in cities";
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        region rgnBattlefield = battlefield.getBattlefield(locCurrentLocation);
        if (rgnBattlefield != null)
        {
            strSpam = objPlayer + " canSpawn failed: spawning disabled in battlefields";
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        rgnCities = getRegionsWithGeographicalAtPoint(locCurrentLocation, regions.GEO_CITY);
        if (rgnCities != null)
        {
            strSpam = objPlayer + " canSpawn failed: spawning not allowed in cities";
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        obj_id objMasterSpawner = getPlanetByName(locCurrentLocation.area);
        if (hasObjVar(objMasterSpawner, "boolSpawnerIsOn"))
        {
            boolean boolSpawnerIsOn;
            boolSpawnerIsOn = getBooleanObjVar(objMasterSpawner, "boolSpawnerIsOn");
            if (boolSpawnerIsOn == false)
            {
                strSpam = objPlayer + " canSpawn failed: spawning is disabled";
                sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                return boolSpawnerIsOn;
            }
        }
        if (locCurrentLocation.cell != obj_id.NULL_ID)
        {
            strSpam = objPlayer + " canSpawn failed: spawnig is disabled in cells";
            sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
            return false;
        }
        if (hasObjVar(objPlayer, "spawning.locSpawnLocation"))
        {
            int intSpawnedTemplates = 0;
            if (hasObjVar(objPlayer, "spawning.intSpawnedTemplates"))
            {
                intSpawnedTemplates = getIntObjVar(objPlayer, "spawning.intSpawnedTemplates");
            }
            if (intSpawnedTemplates >= SPAWN_CHECK_LIMIT)
            {
                location locLastSpawnLocation = getLocationObjVar(objPlayer, "spawning.locSpawnLocation");
                float fltDistance = utils.getDistance(locLastSpawnLocation, locCurrentLocation);
                if (fltDistance < SPAWN_CHECK_DISTANCE)
                {
                    strSpam = objPlayer + " You need to move before we spawn more stuff";
                    sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                    strSpam = objPlayer + " The minimum check distance is: " + SPAWN_CHECK_DISTANCE;
                    sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                    strSpam = objPlayer + " Your distance is: " + fltDistance;
                    sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                    return false;
                }
            }
        }
        if (locSpawnLocation != null)
        {
            rgnCities = getRegionsWithMunicipalAtPoint(locSpawnLocation, regions.MUNI_TRUE);
            if (rgnCities != null)
            {
                strSpam = " spawn location is in a city, returning false from canspawn";
                sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                return false;
            }
            rgnCities = getRegionsWithGeographicalAtPoint(locSpawnLocation, regions.GEO_CITY);
            if (rgnCities != null)
            {
                strSpam = "location is in a city2, returning false from canspawn";
                sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                return false;
            }
            rgnBattlefield = battlefield.getBattlefield(locSpawnLocation);
            if (rgnBattlefield != null)
            {
                strSpam = "location is in a battlefield, returning false from canspawn";
                sendSpawnSpam(objPlayer, boolLogFailures, boolVerboseMode, strSpam);
                return false;
            }
            if (!checkTemplatesInRange(objPlayer, locSpawnLocation, boolCheckForTheaters, strObjectType))
            {
                return false;
            }
        }
        return true;
    }
    public void sendSpawnSpam(obj_id self, boolean boolLogFailures, boolean boolVerboseMode, String strSpam) throws InterruptedException
    {
        sendSystemMessageTestingOnly(self, strSpam);
        return;
    }
    public boolean isSpawningAllowed(location locTest) throws InterruptedException
    {
        return true;
    }
    public int gmCreateSpecificResource(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String strClass = params;
        if ((strClass == null) || (strClass.equals("")))
        {
            sendSystemMessageTestingOnly(self, "Bad class passed in, syntax is /gmCreateClassResource class");
            return SCRIPT_CONTINUE;
        }
        obj_id rtype = pickRandomNonDepeletedResource(strClass);
        if (!isIdValid(rtype))
        {
            sendSystemMessageTestingOnly(self, "No id found");
            sendSystemMessageTestingOnly(self, "Type was " + strClass);
            return SCRIPT_CONTINUE;
        }
        String crateTemplate = getResourceContainerForType(rtype);
        if (!crateTemplate.equals(""))
        {
            obj_id pInv = utils.getInventoryContainer(self);
            if (pInv != null)
            {
                obj_id crate = createObject(crateTemplate, pInv, "");
                if (addResourceToContainer(crate, rtype, 100000, self))
                {
                    sendSystemMessageTestingOnly(self, "Resource of class " + params + " added");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int gmCreateClassResource(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        String strClass = params;
        if ((strClass == null) || (strClass.equals("")))
        {
            sendSystemMessageTestingOnly(self, "Bad class passed in, syntax is /gmCreateClassResource class");
            return SCRIPT_CONTINUE;
        }
        obj_id rtype = pickRandomNonDepeletedResource(strClass);
        if (!isIdValid(rtype))
        {
            sendSystemMessageTestingOnly(self, "Bad class passed in, syntax is /gmCreateClassResource class");
            return SCRIPT_CONTINUE;
        }
        String crateTemplate = getResourceContainerForType(rtype);
        if (!crateTemplate.equals(""))
        {
            obj_id pInv = utils.getInventoryContainer(self);
            if (pInv != null)
            {
                obj_id crate = createObject(crateTemplate, pInv, "");
                if (addResourceToContainer(crate, rtype, 100000, self))
                {
                    sendSystemMessageTestingOnly(self, "Resource of class " + params + " added");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnShipWasHit(obj_id self, obj_id objAttacker, int weaponIndex, boolean isMissile, int missileType, int intTargetedComponent, boolean fromPlayerAutoTurret, float hitLocationX_o, float hitLocationY_o, float hitLocationZ_o) throws InterruptedException
    {
        int intRoll = rand(1, 10);
        if (intRoll > 6)
        {
            obj_id[] objContents = getShipContents(self);
            if ((objContents != null) && (objContents.length > 0))
            {
                for (int intI = 0; intI < objContents.length; intI++)
                {
                    if (isPlayer(objContents[intI]))
                    {
                        sendSystemMessageTestingOnly(objContents[intI], "playing effect");
                        playClientEffectLoc(objContents[intI], "clienteffect/int_camshake_light.cef", getLocation(objContents[intI]), 0);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id[] getShipContents(obj_id objObject) throws InterruptedException
    {
        Vector objContents = new Vector();
        objContents.setSize(0);
        obj_id[] objCells = getContents(objObject);
        for (int intI = 0; intI < objCells.length; intI++)
        {
            obj_id[] objTestContents = getContents(objCells[intI]);
            if ((objTestContents != null) && (objTestContents.length > 0))
            {
                for (int intJ = 0; intJ < objTestContents.length; intJ++)
                {
                    objContents = utils.addElement(objContents, objTestContents[intJ]);
                }
            }
        }
        obj_id[] _objContents = new obj_id[0];
        if (objContents != null)
        {
            _objContents = new obj_id[objContents.size()];
            objContents.toArray(_objContents);
        }
        return _objContents;
    }
    public int checkEngineValue(obj_id self, dictionary params) throws InterruptedException
    {
        int intLoops = params.getInt("intLoops");
        intLoops = intLoops - 1;
        if (intLoops < 1)
        {
            LOG("space", "DONE CHECKING");
            sendSystemMessageTestingOnly(self, "DONE CHECKING");
            return SCRIPT_CONTINUE;
        }
        obj_id objShip = space_transition.getContainingShip(self);
        sendSystemMessageTestingOnly(self, "Current Ship Speed: " + getShipCurrentSpeed(objShip) + " Actual Pitch: " + getShipActualPitchRateMaximumDegrees(objShip));
        LOG("space", "===============================================================");
        LOG("space", "Loops left: " + intLoops);
        LOG("space", "Current Ship Speed: " + getShipCurrentSpeed(objShip));
        LOG("space", "Actual Yaw: " + getShipActualYawRateMaximumDegrees(objShip));
        LOG("space", "Actual Pitch: " + getShipActualPitchRateMaximumDegrees(objShip));
        LOG("space", "Actual Roll: " + getShipActualRollRateMaximumDegrees(objShip));
        params.put("intLoops", intLoops);
        messageTo(self, "checkEngineValue", params, .25f, false);
        return SCRIPT_CONTINUE;
    }
    public String getDumpString(obj_id objTest, String strName) throws InterruptedException
    {
        String strTest = "";
        location locTest = getWorldLocation(objTest);
        location locFoo = getLocation(objTest);
        String strCell = getCellName(locFoo.cell);
        strTest = strName + "	" + (int)locTest.x + "	" + (int)locTest.y + "	" + (int)locTest.z + "	" + strCell + "\r\n";
        return strTest;
    }
    public int pingResponse(obj_id self, dictionary params) throws InterruptedException
    {
        location locTest = params.getLocation("locTest");
        obj_id objSender = params.getObjId("objSender");
        sendSystemMessageTestingOnly(self, "Response from " + objSender + " they are at location " + locTest);
        obj_id objTopMost = params.getObjId("objTopMost");
        if (isIdValid(objTopMost))
        {
            location locTopMost = params.getLocation("locTopMost");
            sendSystemMessageTestingOnly(self, "Top Most Container is " + objTopMost + " at " + locTopMost);
        }
        else 
        {
            sendSystemMessageTestingOnly(self, "Target is in the world, not top most");
        }
        return SCRIPT_CONTINUE;
    }
}
