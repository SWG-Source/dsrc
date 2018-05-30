package script.working;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.battlefield;
import script.library.bio_engineer;
import script.library.buff;
import script.library.create;
import script.library.dot;
import script.library.features;
import script.library.hq;
import script.library.locations;
import script.library.mustafar;
import script.library.pclib;
import script.library.pet_lib;
import script.library.prose;
import script.library.player_structure;
import script.library.regions;
import script.library.skill;
import script.library.utils;
import script.library.space_crafting;
import script.library.space_transition;

public class reecetest extends script.base_script
{
    public reecetest()
    {
    }
    public static final int SPAWN_TEMPLATE_CHECK_DISTANCE = 128;
    public static final int SPAWN_CHECK_TEMPLATE_LIMIT = 140;
    public static final int SPAWN_THEATER_CHECK_DISTANCE = 200;
    public static final int SPAWN_CHECK_DISTANCE = 64;
    public static final int SPAWN_CHECK_LIMIT = 15;
    public static final int SPAWN_CHECK_THEATER_LIMIT = 1;
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
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        if (objSpeaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (strText.equals("name"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                String creatureName = getCreatureName(target);
                sendSystemMessageTestingOnly(self, "creatureName = " + creatureName);
            }
        }
        if (strText.equals("level"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                ai_lib.creatureLevelUp(target, self);
            }
        }
        if (strText.equals("stats"))
        {
            sendSystemMessageTestingOnly(self, "Action = " + getMaxAttrib(self, ACTION) + " Mind = " + getMaxAttrib(self, MIND));
            sendSystemMessageTestingOnly(self, "Stamina = " + getMaxAttrib(self, STAMINA) + " Constitution = " + getMaxAttrib(self, CONSTITUTION));
        }
        if (strText.equals("cef1"))
        {
            playClientEffectObj(self, "clienteffect/mustafar/som_force_crystal_drain.cef", self, "");
            sendSystemMessageTestingOnly(self, "CEF 1 !!!!!!!!!");
        }
        if (strText.equals("cef2"))
        {
            playClientEffectObj(self, "clienteffect/mustafar/som_force_crystal_buff.cef", self, "");
            sendSystemMessageTestingOnly(self, "CEF 2 !!!!!!!!!");
        }
        if (strText.equals("cef3"))
        {
            playClientEffectObj(self, "clienteffect/mustafar/som_force_crystal_destruction.cef", self, "");
            sendSystemMessageTestingOnly(self, "CEF 3 !!!!!!!!!");
        }
        if (strText.equals("cef4"))
        {
            playClientEffectObj(self, "clienteffect/mustafar/som_dark_jedi_laugh.cef", self, "");
            sendSystemMessageTestingOnly(self, "CEF 4 !!!!!!!!!");
        }
        if (strText.equals("test"))
        {
            obj_id dungeon = getTopMostContainer(self);
            obj_id crystal = utils.getObjIdScriptVar(dungeon, "crystal");
            messageTo(crystal, "crystalTest1", null, 1, false);
            sendSystemMessageTestingOnly(self, "TEST  !!!!!!!!!");
        }
        if (strText.equals("test2"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                messageTo(target, "crystalTest2", null, 1, false);
            }
            sendSystemMessageTestingOnly(self, "TEST2  !!!!!!!!!");
        }
        if (strText.equals("attach"))
        {
            attachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor");
            if (hasScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor"))
            {
                sendSystemMessageTestingOnly(self, "script attached");
            }
        }
        if (strText.equals("detach"))
        {
            if (detachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor"))
            {
                sendSystemMessageTestingOnly(self, "script detatched");
            }
        }
        if (strText.equals("fireone"))
        {
            attachScript(self, "theme_park.dungeon.mustafar_trials.obiwan_finale.obiwan_quest_monitor");
            sendSystemMessageTestingOnly(self, "fireone");
            if (mustafar.canCallObiwan(self))
            {
                mustafar.callObiwan(self, true);
                sendSystemMessageTestingOnly(self, "callingObiwan");
            }
            else 
            {
                setObjVar(self, "waitingOnObiwan", 1);
                messageTo(self, "recallObiwanDelay", null, 180, false);
                sendSystemMessageTestingOnly(self, "recallObiwanDelay");
            }
            sendSystemMessageTestingOnly(self, "firetwo");
        }
        if (strText.equals("firetwo"))
        {
            sendSystemMessageTestingOnly(self, "firetwo");
            utils.setScriptVar(self, "sawObiwanAtLauncher", 1);
        }
        if (strText.equals("hittem"))
        {
            sendSystemMessageTestingOnly(self, "hittem");
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                obj_id weapon = getCurrentWeapon(self);
                hit_result cbtHitData = new hit_result();
                cbtHitData.success = true;
                cbtHitData.baseRoll = 50;
                cbtHitData.finalRoll = 100;
                cbtHitData.hitLocation = rand(0, 5);
                cbtHitData.canSee = true;
                cbtHitData.damage = 5000;
                cbtHitData.bleedingChance = 0;
                doDamage(self, target, weapon, cbtHitData);
            }
        }
        if (strText.equals("cbuff"))
        {
            buff.applyBuff(self, "crystal_buff");
            sendSystemMessageTestingOnly(self, "I C'BUFFED YA!");
        }
        if (strText.equals("dbuff"))
        {
            buff.removeAllBuffs(self);
            sendSystemMessageTestingOnly(self, "I D'BUFFED YA!");
        }
        if (strText.equals("reforce"))
        {
            sendSystemMessageTestingOnly(self, "reforce start");
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                messageTo(target, "specialForcePowerAttackWindup", null, rand(10, 20), false);
            }
            sendSystemMessageTestingOnly(self, "reforce done");
        }
        if (strText.equals("bossman"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                if (utils.hasScriptVar(target, "dungeon"))
                {
                    sendSystemMessageTestingOnly(self, "got dungeon objid. going to spawn BOSS.");
                    obj_id dungeon = utils.getObjIdScriptVar(target, "dungeon");
                    spawnContents(dungeon, "boss", 1);
                }
            }
        }
        if (strText.equals("currentScene"))
        {
            String scene = getCurrentSceneName();
            sendSystemMessageTestingOnly(self, "current scene name is: " + scene);
        }
        if (strText.equals("locations"))
        {
            for (int i = 0; i < 4; i++)
            {
                location here = getLocation(self);
                location spawnLoc = locations.getGoodLocationAroundLocation(here, 1f, 1f, 4f, 4f);
                if (spawnLoc == null)
                {
                    sendSystemMessageTestingOnly(self, " spawnLoc was NULL ");
                    spawnLoc = here;
                }
                sendSystemMessageTestingOnly(self, "found location #" + i + " and it was at: " + spawnLoc);
            }
        }
        if (strText.equals("expansion"))
        {
            if (features.hasTrialsOfObiwanExpansion(self))
            {
                sendSystemMessageTestingOnly(self, " YEE HAW! GOT MUSTAFAR");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No mustafar expansion detected");
            }
            if (features.hasEpisode3PreOrderDigitalDownload(self))
            {
                sendSystemMessageTestingOnly(self, " YEE HAW! EP3 Preorder");
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No EP3 pre-order expansion detected");
            }
        }
        if (strText.equals("petcount"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                int petcount = pet_lib.getMaxStoredPets(target);
                sendSystemMessageTestingOnly(self, " Got pet count. It was: " + petcount);
            }
        }
        if (strText.equals("patroller"))
        {
            location here = getLocation(self);
            obj_id spawn = create.object("fbase_stormtrooper", here);
            obj_id base = player_structure.getStructure(self);
            String[] base_cells = getCellNames(base);
            location[] locs = new location[base_cells.length];
            for (int i = 0; i < base_cells.length; i++)
            {
                locs[i] = getGoodLocation(base, base_cells[i]);
            }
            ai_lib.setPatrolPath(spawn, locs);
        }
        if (strText.equals("conditionon"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                hq.activateHackAlarms(target, true);
                hq.activateDestructAlarms(target, true);
                sendSystemMessageTestingOnly(self, "alarm!");
            }
        }
        if (strText.equals("conditionoff"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                hq.activateHackAlarms(target, false);
                hq.activateDestructAlarms(target, false);
                sendSystemMessageTestingOnly(self, "alarm! off");
            }
        }
        if (strText.equals("respawnalarms"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                hq.cleanupBaseAlarmUnits(target);
                hq.spawnAlarmUnits(target);
                sendSystemMessageTestingOnly(self, "alarm! RESPAWN!");
            }
        }
        if (strText.equals("disableobj"))
        {
            obj_id target = getLookAtTarget(self);
            sendSystemMessageTestingOnly(self, "disabled objective! target is: " + target);
            if (target != null)
            {
                hq.disableObjective(target);
                sendSystemMessageTestingOnly(self, "disabled objective!");
            }
        }
        if (strText.equals("startcountdown"))
        {
            obj_id target = getLookAtTarget(self);
            sendSystemMessageTestingOnly(self, "startcountdown detected! target is: " + target);
            if (target != null)
            {
                float delay = 300f + 300f * 1;
                int minutes = Math.round(delay / 60f);
                obj_id[] players = player_structure.getPlayersInBuilding(getTopMostContainer(target));
                if (players != null && players.length > 0)
                {
                    for (int i = 0; i < players.length; i++)
                    {
                        sendSystemMessageProse(players[i], prose.getPackage(new string_id("faction/faction_hq/faction_hq_response", "terminal_response40"), minutes));
                    }
                }
                int stamp = getGameTime() + Math.round(delay);
                utils.setScriptVar(target, "countdownInProgress", stamp);
                dictionary d = new dictionary();
                d.put("player", self);
                d.put("cnt", minutes);
                messageTo(target, "handleCountdown", d, 10f, false);
            }
        }
        if (strText.equals("accountCheck"))
        {
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                int pBits = getSubscriptionFeatureBits(target);
                sendSystemMessageTestingOnly(self, "CHECKING ACCOUNT TYPE Got account bits. They came back as: " + pBits);
                if ((utils.isFreeTrial(target)))
                {
                    sendSystemMessageTestingOnly(self, "It would appear that this IS a free trial account, per results from utils.isFreeTrial");
                }
                else 
                {
                    sendSystemMessageTestingOnly(self, "It would appear that this IS NOT a free trial account, per results from utils.isFreeTrial");
                }
                obj_id playerObject = getPlayerObject(target);
                if (playerObject != null)
                {
                    pBits = getSubscriptionFeatureBits(playerObject);
                    sendSystemMessageTestingOnly(self, "Running same checks on PLAYER ACCOUNT OBJECT. Got account bits came back as: " + pBits);
                    if ((utils.isFreeTrial(playerObject)))
                    {
                        sendSystemMessageTestingOnly(self, "testing PLAYER ACCOUNT OBJ, this IS a free trial account, per results from utils.isFreeTrial");
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "testing PLAYER ACCOUNT OBJ, this IS NOT a free trial account, per results from utils.isFreeTrial");
                    }
                }
            }
        }
        if (strText.equals("reloadtest"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            if (!isIdValid(ship))
            {
                return SCRIPT_CONTINUE;
            }
            int[] slots = space_crafting.getShipInstalledSlots(ship);
            for (int i = 0; i < slots.length; i++)
            {
                if (space_crafting.isMissileSlot(ship, slots[i]))
                {
                    setShipWeaponAmmoCurrent(ship, slots[i], 1);
                }
                else if (space_crafting.isCounterMeasureSlot(ship, slots[i]))
                {
                    setShipWeaponAmmoCurrent(ship, slots[i], 1);
                }
            }
            sendSystemMessageTestingOnly(self, "unloaded!");
        }
        if (strText.equals("zaprear"))
        {
            obj_id ship = space_transition.getContainingShip(self);
            if (!isIdValid(ship))
            {
                return SCRIPT_CONTINUE;
            }
            setShipShieldHitpointsBackCurrent(ship, 5.0f);
            sendSystemMessageTestingOnly(self, "BANG! Rear Shields ZAPPED! Should be at 5.0f units");
        }
        if (strText.equals("gettime"))
        {
            long now = System.currentTimeMillis();
            long curTime = ((now / 1000) - 1072224000);
            int currentTime = (int)curTime + 3600;
            sendSystemMessageTestingOnly(self, "Set timestamp to: " + currentTime);
        }
        if (strText.equals("getlots"))
        {
            sendSystemMessageTestingOnly(self, "Got getlots command");
            obj_id playerObject = getPlayerObject(self);
            int lots = getAccountNumLots(playerObject);
            sendSystemMessageTestingOnly(self, "Current Lots Count is " + lots);
        }
        if (strText.equals("getcell"))
        {
            String currentCell = getCellName(self);
            sendSystemMessageTestingOnly(self, "DEBUG - currentCell is: " + currentCell);
            location locTest = getLocation(self);
            if (isIdValid(locTest.cell))
            {
                sendSystemMessageTestingOnly(self, "DEBUG - current cell information via locTest IS: " + locTest.cell);
                currentCell = getCellName(locTest.cell);
                sendSystemMessageTestingOnly(self, "DEBUG - currentCell via second method IS: " + currentCell);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "DEBUG - current cell information via locTest IS NOT VALID ");
            }
        }
        if (strText.equals("goSlow"))
        {
            obj_id objMount = getMountId(self);
            setBaseRunSpeed(objMount, 6.0f);
            sendSystemMessageTestingOnly(self, "Jogging");
        }
        if (strText.equals("slowMe"))
        {
            setBaseRunSpeed(self, 1.0f);
        }
        if (strText.equals("fastMe"))
        {
            setBaseRunSpeed(self, 6.0f);
        }
        if (strText.equals("getScriptVars"))
        {
            deltadictionary dctScriptVars = self.getScriptVars();
            sendSystemMessageTestingOnly(self, "Scriptvars are " + dctScriptVars.toString());
        }
        if (strText.equals("basereset"))
        {
            sendSystemMessageTestingOnly(self, "Received baseReset command.");
            obj_id target = getLookAtTarget(self);
            if (target != null)
            {
                sendSystemMessageTestingOnly(self, "Sending message to target. Obj_id target is: " + target);
                messageTo(target, "handleManualSpawnResetTest", null, 3, false);
            }
        }
        else if (strText.equals("getStats"))
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
        if (strText.equals("damage"))
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
        if (strText.equals("music"))
        {
            playMusic(self, "sound/music_int_complete_rebel.snd");
        }
        if (strText.equals("badWeapon"))
        {
            obj_id objWeapon = createObject("object/weapon/ranged/rifle/rifle_dlt20a.iff", getLocation(self));
            setWeaponAttackSpeed(objWeapon, 1.5f);
        }
        if (strText.equals("checkFlags"))
        {
            obj_id objTarget = getLookAtTarget(self);
            if (isIdValid(objTarget))
            {
                sendSystemMessageTestingOnly(self, "NPC is: " + objTarget);
                sendSystemMessageTestingOnly(self, "InvulnerableFlag is: " + isInvulnerable(objTarget));
                sendSystemMessageTestingOnly(self, "pvpCanAttack is: " + pvpCanAttack(self, objTarget));
            }
        }
        if (strText.equals("combatr2"))
        {
            debugServerConsoleMsg(self, "entered combatr2 module");
            obj_id inventoryContainer = getObjectInSlot(self, "inventory");
            if (!isIdValid(inventoryContainer))
            {
                debugServerConsoleMsg(self, "looks like the objid of the player inventory is invalid");
            }
            obj_id deed = createObject("object/tangible/deed/pet_deed/deed_r2_basic.iff", inventoryContainer, "SLOT_INVENTORY");
            if (!isIdValid(deed))
            {
                debugServerConsoleMsg(self, "failed to create the deed object");
            }
            int powerLevel = 50;
            final String CREATURE_NAME = "r2_crafted";
            dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, "r2_crafted");
            setObjVar(deed, "creature_attribs.type", "r2_crafted");
            setObjVar(deed, "creature_attribs.level", 30);
            setObjVar(deed, "creature_attribs.maxHealth", 4000);
            setObjVar(deed, "creature_attribs.maxConstitution", 0);
            setObjVar(deed, "creature_attribs.general_protection", 2000);
            setObjVar(deed, "creature_attribs.toHitChance", 81);
            setObjVar(deed, "creature_attribs.defenseValue", 81);
            setObjVar(deed, "creature_attribs.minDamage", 61);
            setObjVar(deed, "creature_attribs.maxDamage", 284);
            setObjVar(deed, "creature_attribs.aggroBonus", 0.0f);
            setObjVar(deed, "creature_attribs.critChance", 0.0f);
            setObjVar(deed, "creature_attribs.critSave", 0.0f);
            setObjVar(deed, "creature_attribs.scale", 1.0f);
            setObjVar(deed, "creature_attribs.stateResist", 0.0f);
            setObjVar(deed, "crafting_components.cmbt_module", 600.0f);
            setObjVar(deed, "dataModuleRating", 12);
            setObjVar(deed, "ai.pet.hasContainer", 12);
            setObjVar(deed, "ai.pet.isRepairDroid", true);
            setObjVar(deed, "craftingStationSpace", true);
            setObjVar(deed, "craftingStationWeapon", true);
            setObjVar(deed, "craftingStationFood", true);
        }
        if (strText.equals("combatprobot"))
        {
            debugServerConsoleMsg(self, "entered probot module");
            obj_id inventoryContainer = getObjectInSlot(self, "inventory");
            if (!isIdValid(inventoryContainer))
            {
                debugServerConsoleMsg(self, "looks like the objid of the player inventory is invalid");
            }
            obj_id object = createObject("object/tangible/deed/pet_deed/deed_probot_basic.iff", inventoryContainer, "SLOT_INVENTORY");
            if (!isIdValid(object))
            {
                debugServerConsoleMsg(self, "failed to create the deed object");
            }
            int powerLevel = 50;
            final String CREATURE_NAME = "probot";
            dictionary creatureDict = dataTableGetRow(create.CREATURE_TABLE, "r2_crafted");
            setObjVar(object, "creature_attribs.type", "probot");
            for (int attribNum = HEALTH; attribNum <= WILLPOWER; attribNum++)
            {
                int minAttrib = creatureDict.getInt(create.MINATTRIBNAMES[attribNum]);
                int maxAttrib = creatureDict.getInt(create.MAXATTRIBNAMES[attribNum]);
                int attribRange = maxAttrib - minAttrib;
                int newAttribValue = (int)((attribRange * powerLevel) / 1000) + minAttrib;
                setObjVar(object, "creature_attribs." + create.MAXATTRIBNAMES[attribNum], newAttribValue);
            }
            int toHitChance = creatureDict.getInt("toHitChance");
            float minToHit = toHitChance + (toHitChance * bio_engineer.CREATURE_MIN_TO_HIT_MOD);
            float maxToHit = toHitChance + (toHitChance * bio_engineer.CREATURE_MAX_TO_HIT_MOD);
            setObjVar(object, "creature_attribs.toHitChance", toHitChance);
            obj_id creatureWeapon = getCurrentWeapon(object);
            if (!isIdValid(creatureWeapon))
            {
                debugServerConsoleMsg(self, "failed to get the obj_id of the creature's current weapon. Uh oh!");
            }
            float wpnSpeed = (float)creatureDict.getInt("attackSpeed");
            float minWpnSpeed = wpnSpeed + (wpnSpeed * bio_engineer.CREATURE_MIN_WEAPON_SPEED_MOD);
            float maxWpnSpeed = wpnSpeed + (wpnSpeed * bio_engineer.CREATURE_MAX_WEAPON_SPEED_MOD);
            setObjVar(object, "creature_attribs.attackSpeed", wpnSpeed);
            int minDamage = creatureDict.getInt("minDamage");
            int maxDamage = creatureDict.getInt("maxDamage");
            float minMinDamage = minDamage + (minDamage * bio_engineer.CREATURE_MIN_DAMAGE_MOD);
            float maxMinDamage = minDamage + (minDamage * bio_engineer.CREATURE_MAX_DAMAGE_MOD);
            float minMaxDamage = maxDamage + (maxDamage * bio_engineer.CREATURE_MIN_DAMAGE_MOD);
            float maxMaxDamage = maxDamage + (maxDamage * bio_engineer.CREATURE_MAX_DAMAGE_MOD);
            minDamage = (int)(minMinDamage + (((maxMinDamage - minMinDamage) * powerLevel) / 1000));
            maxDamage = (int)(minMaxDamage + (((maxMaxDamage - minMaxDamage) * powerLevel) / 1000));
            setObjVar(object, "creature_attribs.minDamage", minDamage);
            setObjVar(object, "creature_attribs.maxDamage", maxDamage);
            setObjVar(object, "crafting_components.cmbt_module", 600.0f);
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
        int intServerSpawnLimit = getServerSpawnLimit();
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
    public void spawnContents(obj_id dungeon, String spawnType, int numberOfCopies) throws InterruptedException
    {
        obj_id self = getSelf();
        sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> entered. SpawnType requested was: " + spawnType);
        String[] spawnTypeColumnData = dataTableGetStringColumnNoDefaults(mustafar.CONST_TBL_EVENT_DATA, "dataType");
        if (spawnTypeColumnData.length == 0)
        {
            return;
        }
        for (int i = 0; i < spawnTypeColumnData.length; i++)
        {
            if (spawnTypeColumnData[i].equals(spawnType))
            {
                dictionary dict = dataTableGetRow(mustafar.CONST_TBL_EVENT_DATA, i);
                sendSystemMessageTestingOnly(self, "spawnContents:Retrieving info for row(" + i + ")");
                int locx = dict.getInt("locx");
                int locy = dict.getInt("locy");
                int locz = dict.getInt("locz");
                location myself = getLocation(dungeon);
                String scene = myself.area;
                obj_id cell = getCellId(dungeon, "mainroom");
                float yaw = dict.getFloat("yaw");
                String script = dict.getString("script");
                location spawnLoc = new location(locx, locy, locz, scene, cell);
                String object = dict.getString("object");
                if ((dict.getString("dataType")).equals("setpiece"))
                {
                    sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> Going to try to spawn a 'setpiece'");
                    obj_id item = createObjectInCell(object, dungeon, "mainroom", spawnLoc);
                    if (!isIdValid(item))
                    {
                        sendSystemMessageTestingOnly(self, "spawnContents: Tried to create invalid item(" + object + ")");
                        return;
                    }
                    setYaw(item, yaw);
                    if (!script.equals("none"))
                    {
                        attachScript(item, script);
                    }
                    utils.setScriptVar(item, "dungeon", dungeon);
                    sendSystemMessageTestingOnly(self, "spawnContents: Created object(" + object + "/" + item + ") with script(" + script);
                }
                else if (!(dict.getString("dataType")).equals("location") && !(dict.getString("dataType")).equals("forcePowerAttack"))
                {
                    sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> Going to try to spawn a creature of some kind");
                    sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> Going to try to spawn a creature. SERIOUSLY!");
                    if (numberOfCopies < 1)
                    {
                        numberOfCopies = 1;
                    }
                    sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> numberOfCopies is: " + numberOfCopies);
                    for (int j = 0; j < numberOfCopies; j++)
                    {
                        sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> spawn loop 1");
                        obj_id creature = create.object(object, spawnLoc);
                        sendSystemMessageTestingOnly(self, "//***// spawnContents: ////>>>> creature spawned objId is: " + creature);
                        if (!isIdValid(creature))
                        {
                            sendSystemMessageTestingOnly(self, "spawnContents: Tried to create invalid creature(" + object + ")");
                            return;
                        }
                        if (!script.equals("none"))
                        {
                            attachScript(creature, script);
                        }
                        utils.setScriptVar(creature, "dungeon", dungeon);
                        sendSystemMessageTestingOnly(self, "spawnContents: Created object(" + object + "/" + creature + ") with script(" + script);
                    }
                }
            }
        }
    }
}
