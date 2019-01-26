package script.working;

import script.dictionary;
import script.library.*;
import script.location;
import script.obj_id;
import script.string_id;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

public class jbenjtest extends script.base_script
{
    public jbenjtest()
    {
    }
    public static final String PID_NAME = "hateList";
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        obj_id player = self;
        obj_id pInv = utils.getInventoryContainer(player);
        obj_id target = getIntendedTarget(self);
        obj_id tInv = utils.getInventoryContainer(target);
        if (isGod(player))
        {
            StringTokenizer st = new java.util.StringTokenizer(text);
            int tokens = st.countTokens();
            String command = null;
            if (st.hasMoreTokens())
            {
                command = st.nextToken();
            }
            if (command.equals("setSpaceFlag"))
            {
                if (st.hasMoreTokens())
                {
                    command = st.nextToken();
                    space_flags.setSpaceFlag(self, command, 1);
                    return SCRIPT_CONTINUE;
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("tweakRespecValues"))
            {
                int combatLevel = -1;
                int entLevel = -1;
                int traderLevel = -1;
                if (st.hasMoreTokens())
                {
                    command = st.nextToken();
                    combatLevel = utils.stringToInt(command);
                    if (st.hasMoreTokens())
                    {
                        command = st.nextToken();
                        entLevel = utils.stringToInt(command);
                        if (st.hasMoreTokens())
                        {
                            command = st.nextToken();
                            traderLevel = utils.stringToInt(command);
                        }
                    }
                }
                int[] recpecLevelsNewValue = 
                {
                    combatLevel,
                    entLevel,
                    traderLevel
                };
                setObjVar(player, respec.PROF_LEVEL_ARRAY, recpecLevelsNewValue);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("respecFix"))
            {
                if (hasObjVar(player, respec.PROF_LEVEL_ARRAY))
                {
                    String skillTemplate = getSkillTemplate(player);
                    int[] recpecLevelsValue = getIntArrayObjVar(player, respec.PROF_LEVEL_ARRAY);
                    int currentLevel = getLevel(player);
                    if (!skillTemplate.startsWith("entertainer") && !skillTemplate.startsWith("trader"))
                    {
                        int combatLevel = recpecLevelsValue[respec.PROF_LEVEL_COMBAT];
                        if (currentLevel < combatLevel)
                        {
                            respec.autoLevelPlayer(player, combatLevel, true);
                        }
                    }
                    else if (skillTemplate.startsWith("entertainer"))
                    {
                        debugSpeakMsg(self, "you should be a entertainer dude");
                        int entLevel = recpecLevelsValue[respec.PROF_LEVEL_ENT];
                        debugSpeakMsg(self, "entLevel " + entLevel);
                        if (currentLevel < entLevel)
                        {
                            respec.autoLevelPlayer(player, entLevel, true);
                        }
                    }
                    else if (skillTemplate.startsWith("trader"))
                    {
                        debugSpeakMsg(self, "you should be a trader dude");
                        int traderLevel = recpecLevelsValue[respec.PROF_LEVEL_TRADER];
                        debugSpeakMsg(self, "traderLevel " + traderLevel);
                        if (currentLevel < traderLevel)
                        {
                            respec.autoLevelPlayer(player, traderLevel, true);
                        }
                    }
                }
            }
            if (command.equals("sendTestMail"))
            {
                utils.sendMail(new string_id("spam", "test_mail_title"), new string_id("spam", "test_mail_title"), self, "Galactic Vacant Building Demolishing Movement");
                return SCRIPT_CONTINUE;
            }
            if (command.equals("hasScriptVar"))
            {
                if (st.hasMoreTokens())
                {
                    command = st.nextToken();
                    debugSpeakMsg(self, "" + utils.hasObjIdBatchScriptVar(target, command));
                    return SCRIPT_CONTINUE;
                }
            }
            if (command.equals("getAttackerList"))
            {
                obj_id[] attackerList = utils.getObjIdBatchScriptVar(target, "creditForKills.attackerList.attackers");
                String list = utils.getStringScriptVar(target, "creditForKills.attackerList");
                if (attackerList == null)
                {
                    debugSpeakMsg(self, "attackerList was null");
                    debugSpeakMsg(self, "list was " + list);
                    return SCRIPT_CONTINUE;
                }
                for (int i = 0; i < attackerList.length; ++i)
                {
                    debugSpeakMsg(self, "attackerList[" + i + "] " + attackerList[i]);
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("testMonthly"))
            {
                sendSystemMessageTestingOnly(self, "" + getCalendarTimeStringGMT(getIntObjVar(self, respec.OBJVAR_RESPEC_DECAY_TIME)));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("clearInstanceQueue"))
            {
                instance.setRequestResolved(self);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("setRespecDecay"))
            {
                sendSystemMessageTestingOnly(self, "setting respec decay to end in 10 seconds. You need to log out fully to test this, or say 'testRespecDecay'.");
                int now = getCalendarTime();
                int then = now + 10;
                setObjVar(player, respec.OBJVAR_RESPEC_DECAY_TIME, then);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("testRespecDecay"))
            {
                respec.checkRespecDecay(self);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("get_tat"))
            {
                obj_id planetId = getPlanetByName("tatooine");
                sendSystemMessageTestingOnly(self, "" + planetId);
            }
            if (command.equals("sendTestHeroicComplete"))
            {
                dictionary dict = new dictionary();
                dict.put("tokenIndex", 5);
                dict.put("tokenCount", 9);
                messageTo(self, "handleAwardtoken", dict, 1, false);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getHealthRate"))
            {
                float fltHealthRegen = getHealthRegenRate(target);
                debugSpeakMsg(self, "" + fltHealthRegen);
            }
            if (command.equals("testSuiCountDown"))
            {
                int flags = 0;
                flags |= sui.CD_EVENT_INCAPACITATE;
                flags |= sui.CD_EVENT_DAMAGED;
                int startTime = 0;
                int endTime = 6;
                String displayGroup = "bm_creature_knowledge";
                string_id prompt = new string_id("beast", "ability_activate_creature_knowledge");
                String handler = "testSuiCountDown";
                float maxRange = 32;
                int pid = sui.smartCountdownTimerSUI(self, self, displayGroup, prompt, startTime, endTime, handler, maxRange, flags);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getFirstName"))
            {
                debugSpeakMsg(self, "" + getFirstName(self));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("setStatic"))
            {
                setCreatureStatic(target, true);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("clearInspiration"))
            {
                utils.removeScriptVarTree(self, "performance");
                return SCRIPT_CONTINUE;
            }
            if (command.equals("removeScriptVar"))
            {
                if (st.hasMoreTokens())
                {
                    command = st.nextToken();
                    utils.removeScriptVarTree(self, command);
                    return SCRIPT_CONTINUE;
                }
            }
            if (command.equals("displayScriptVarDictionary"))
            {
                dictionary dict = new dictionary();
                dict.put("myInt", 1);
                dict.put("myString", "Mikkel");
                debugSpeakMsg(self, "dictionary is " + dict.toString());
                return SCRIPT_CONTINUE;
            }
            if (command.equals("createCameraPcd"))
            {
                obj_id datapad = utils.getPlayerDatapad(self);
                obj_id petControlDevice = createObject(pet_lib.PET_CTRL_DEVICE_TEMPLATE, datapad, "");
                attachScript(petControlDevice, "ai.pcd_ping_response");
                attachScript(petControlDevice, "ai.pet_control_device");
                setObjVar(petControlDevice, "ai.pet.command.0", "f");
                setObjVar(petControlDevice, "ai.pet.command.1", "st");
                setObjVar(petControlDevice, "ai.pet.command.10", "tran");
                setObjVar(petControlDevice, "ai.pet.command.11", "s");
                setObjVar(petControlDevice, "ai.pet.command.12", "t1");
                setObjVar(petControlDevice, "ai.pet.command.13", "t2");
                setObjVar(petControlDevice, "ai.pet.command.1563288080", "fa");
                setObjVar(petControlDevice, "ai.pet.command.16", "g");
                setObjVar(petControlDevice, "ai.pet.command.17", "ft");
                setObjVar(petControlDevice, "ai.pet.command.2", "gu");
                setObjVar(petControlDevice, "ai.pet.command.20", "r");
                setObjVar(petControlDevice, "ai.pet.command.3", "bef");
                setObjVar(petControlDevice, "ai.pet.command.4", "a");
                setObjVar(petControlDevice, "ai.pet.command.5", "p");
                setObjVar(petControlDevice, "ai.pet.command.6", "gp");
                setObjVar(petControlDevice, "ai.pet.command.7", "cp");
                setObjVar(petControlDevice, "ai.pet.command.8", "w");
                setObjVar(petControlDevice, "ai.pet.command.9", "c");
                setObjVar(petControlDevice, "ai.pet.currentStats", "0:0:0:0:0:0:");
                setObjVar(petControlDevice, "ai.pet.palvar.vars./private/index_color_1", 19);
                setObjVar(petControlDevice, "ai.pet.type", 1);
                setObjVar(petControlDevice, "ai.petAbility.abilityList", "1319491216:1563288080:");
                setObjVar(petControlDevice, "ai.petAbility.available", 2);
                setObjVar(petControlDevice, "ai.petAbility.toBeEarned", 1);
                setObjVar(petControlDevice, "ai.petAbility.trainingPts", 24);
                setObjVar(petControlDevice, "ai.petAdvance.cannotBeMount", 1);
                setObjVar(petControlDevice, "ai.petAdvance.growthStage", 5);
                setObjVar(petControlDevice, "ai.petAdvance.xpEarned", 6223);
                setObjVar(petControlDevice, "pet.creatureName", "noxious_vrelt_scavenger");
                setObjVar(petControlDevice, "pet.timeStored", 65802667);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getExpertiseAllocation"))
            {
                String[] skills = expertise.getExpertiseAllocation(self);
                if (skills == null || skills.length <= 0)
                {
                    debugSpeakMsg(self, "No Expertise Skills");
                    return SCRIPT_CONTINUE;
                }
                for (String skill : skills) {
                    debugSpeakMsg(self, "" + skill);
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getStandingON"))
            {
                debugSpeakMsg(self, "Standing On " + getStandingOn(self));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getTargetYaw"))
            {
                debugSpeakMsg(self, "yaw is  " + getYaw(target));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("clearHouseId"))
            {
                setHouseId(self, obj_id.NULL_ID);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("destroyTarget"))
            {
                destroyObject(target);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getTargetHateList"))
            {
                if (!isIdValid(target) || !exists(target))
                {
                    return SCRIPT_CONTINUE;
                }
                String enemyNames = generateStringHateList(target);
                if (enemyNames != null && !enemyNames.equals(""))
                {
                    int pid = sui.msgbox(self, self, enemyNames, sui.OK_ONLY, "Hate List", "onHateListSuiUpdate");
                    messageTo(self, "refreshSuiHateList", null, 1, false);
                    sui.setPid(self, pid, PID_NAME);
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("freezeAi"))
            {
                stop(target);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("testAddHate"))
            {
                debugSpeakMsg(self, "beginingHate is " + getHate(target, self));
                addHateDot(target, self, 50, 25);
                debugSpeakMsg(self, "hateDot is " + getHate(target, self));
                float currentHate = getHate(target, self);
                addHate(target, self, currentHate + 500);
                debugSpeakMsg(self, "SecondHate is " + getHate(target, self));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("incrementSlot"))
            {
                if (st.hasMoreTokens())
                {
                    command = st.nextToken();
                    if (st.hasMoreTokens())
                    {
                        int number = utils.stringToInt(st.nextToken());
                        modifyCollectionSlotValue(player, command, number);
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("showDroids"))
            {
                obj_id[] droidIds = pet_lib.getPcdsForType(player, pet_lib.PET_TYPE_DROID);
                Vector resizAbleDroidIds = new Vector(Arrays.asList(droidIds));
                Vector droidNames = new Vector();
                droidNames.setSize(0);
                for (obj_id droidId : droidIds) {
                    if (hasObjVar(droidId, "module_data.dancing_droid")) {
                        utils.removeElement(resizAbleDroidIds, droidId);
                        continue;
                    }
                    String droidName = getName(droidId);
                    String[] splitName = split(droidName, '/');
                    if (splitName.length > 1) {
                        utils.addElement(droidNames, "@" + droidName);
                    } else {
                        utils.addElement(droidNames, droidName);
                    }
                }
                utils.setScriptVar(player, "dancing_droid.ids", droidIds);
                int pid = sui.listbox(self, self, "prompt", sui.OK_CANCEL, "title", droidNames, "onDanceDroidLoaded", true, true);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getMemberIds"))
            {
                int guildId = getGuildId(self);
                obj_id[] members = guildGetMemberIds(guildId);
                debugSpeakMsg(self, "members.length = " + members.length);
                debugSpeakMsg(self, "guildId = " + guildId);
                for (int i = 0; i < members.length; ++i)
                {
                    if (!isIdValid(members[i]))
                    {
                        debugSpeakMsg(self, "members[" + i + "] was Invalid");
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("gc"))
            {
                debugSpeakMsg(self, "telling Java to do garbage collection");
                System.gc();
            }
            if (command.equals("getGameTime"))
            {
                debugSpeakMsg(self, "" + getGameTime());
                return SCRIPT_CONTINUE;
            }
            if (command.equals("clearGuildPid"))
            {
                guild.removeWindowPid(self);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("giveNewbShip"))
            {
                space_quest.grantNewbieShip(self, "neutral");
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getGuildId"))
            {
                int guildId = getGuildId(target);
                debugSpeakMsg(self, "guildId " + guildId);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("maxDailyPackup"))
            {
                setObjVar(self, "dailyHousePackup", player_structure.MAX_PACKUP_PER_DAY);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getMasterGuildObj"))
            {
                obj_id masterGuildObj = getMasterGuildObject();
                debugSpeakMsg(self, "masterGuildObj " + masterGuildObj);
            }
            if (command.equals("getGameTime"))
            {
                debugSpeakMsg(self, "Game Time is " + getGameTime());
                return SCRIPT_CONTINUE;
            }
            if (command.equals("mountable"))
            {
                debugSpeakMsg(self, "couldPetBeMadeMountable " + couldPetBeMadeMountable(target));
            }
            if (command.equals("getMyCityId"))
            {
                debugSpeakMsg(self, "My city is " + getCitizenOfCityId(self));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("updateScriptvar"))
            {
                if (st.hasMoreTokens())
                {
                    String scriptVarName = st.nextToken();
                    if (st.hasMoreTokens())
                    {
                        String scriptVarValue = st.nextToken();
                        utils.setScriptVar(target, scriptVarName, utils.stringToInt(scriptVarValue));
                        return SCRIPT_CONTINUE;
                    }
                    else 
                    {
                        debugSpeakMsg(self, "parameters are updateScriptvar <scriptVarName> <scriptVarValue>");
                    }
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    debugSpeakMsg(self, "parameters are updateScriptvar <scriptVarName> <scriptVarValue>");
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getAppearance"))
            {
                String targetAppearance = getAppearance(target);
                String[] splitAppearance = split(targetAppearance, '/');
                String[] finalAppearance = split(splitAppearance[1], '.');
                int row = dataTableSearchColumnForString(finalAppearance[0], "start_template", "datatables/beast/incubator_templates.iff");
                debugSpeakMsg(self, "finalAppearance = " + finalAppearance[0]);
                debugSpeakMsg(self, "row = " + row);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getRawAppearance"))
            {
                String targetAppearance = getAppearance(target);
                debugSpeakMsg(self, "targetAppearance = " + targetAppearance);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getDna"))
            {
                obj_id dnaContainer = incubator.extractDna(self, target);
                debugSpeakMsg(self, "dnaContainer = " + dnaContainer);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("validateExpertise"))
            {
                skill.validateExpertise(self);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("newbArmor"))
            {
                npe.grantNewbArmor(self);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getTemplate"))
            {
                String skillTemplate = getSkillTemplate(self);
                debugSpeakMsg(self, "Skill Template is " + skillTemplate);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("setBlankTemplate"))
            {
                setSkillTemplate(self, "a");
                String skillTemplate = getSkillTemplate(self);
                debugSpeakMsg(self, "Skill Template is " + skillTemplate);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("startMarket"))
            {
                debugSpeakMsg(self, "target is " + target);
                messageTo(target, "spawnMarketplaceNpcs", null, 1, false);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("nukeWorld"))
            {
                obj_id[] objObjects = getObjectsInRange(self, 32000);
                sendSystemMessageTestingOnly(self, "Destroying world");
                for (obj_id objObject : objObjects) {
                    if (!isPlayer(objObject)) {
                        setObjVar(objObject, "intCleaningUp", 1);
                        destroyObject(objObject);
                    }
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("topMostContainer"))
            {
                debugSpeakMsg(self, "topmost is" + getTopMostContainer(self));
                return SCRIPT_CONTINUE;
            }
            if (command.equals("getScene"))
            {
                String planet = getCurrentSceneName();
                debugSpeakMsg(self, "planet is " + planet);
                return SCRIPT_CONTINUE;
            }
            if (command.equals("faceMe"))
            {
                faceTo(target, self);
            }
            if (command.equals("addNightSisterVendor"))
            {
                applySkillStatisticModifier(self, "vendor_nightsister", 1);
            }
            if (command.equals("addSkillMod"))
            {
                if (st.hasMoreTokens())
                {
                    String modName = st.nextToken();
                    if (st.hasMoreTokens())
                    {
                        int amount = utils.stringToInt(st.nextToken());
                        applySkillStatisticModifier(self, modName, amount);
                        messageTo(self, "setDisplayOnlyDefensiveMods", trial.getSessionDict(self, "displayDefensiveMods"), 0, false);
                    }
                }
            }
            if (command.equals("callTravelUI"))
            {
                String planet = getCurrentSceneName();
                boolean uiCalled = enterClientTicketPurchaseMode(self, planet, "Dathomir Outpost", true);
                debugSpeakMsg(self, "enterClientTicketPurchaseMode " + uiCalled);
            }
            if (command.equals("zoneAurillia"))
            {
                transition.zonePlayerNoGate("aurillia_township", self, true);
            }
            if (command.equals("removeNightSisterVendor"))
            {
                applySkillStatisticModifier(self, "vendor_nightsister", -1);
            }
            if (command.equals("getResistance"))
            {
                int resistance_mod = getEnhancedSkillStatisticModifier(self, "resistance_poison");
                float resist_scale = (0.4f / ((100.0f + resistance_mod) / 200.0f));
                float to_hit_chance = 100 * resist_scale;
                debugSpeakMsg(self, "My resistance mod is " + resistance_mod);
                debugSpeakMsg(self, "My resist_scale is " + resist_scale);
                debugSpeakMsg(self, "To hit Chance for spy fang is " + to_hit_chance);
                to_hit_chance = 360 * resist_scale;
                debugSpeakMsg(self, "To hit Chance for artery strike is " + to_hit_chance);
                to_hit_chance = 120 * resist_scale;
                debugSpeakMsg(self, "To hit Chance for malady is " + to_hit_chance);
            }
            if (command.equals("spawnCommoner"))
            {
                location myLoc = getLocation(self);
                create.object("commoner", myLoc, false);
            }
            if (command.equals("incrementHappiness"))
            {
                obj_id beast = beast_lib.getBeastOnPlayer(self);
                obj_id bcd = beast_lib.getBeastBCD(beast);
                int currentHappiness = utils.getIntScriptVar(bcd, beast_lib.PET_HAPPINESS_SCRIPTVAR);
                int updatedHappiness = currentHappiness + 5;
                utils.setScriptVar(bcd, beast_lib.PET_HAPPINESS_SCRIPTVAR, updatedHappiness);
                beast_lib.updateBeastStats(bcd, beast);
            }
            if (command.equals("decrementHappiness"))
            {
                obj_id beast = beast_lib.getBeastOnPlayer(self);
                obj_id bcd = beast_lib.getBeastBCD(beast);
                int currentHappiness = utils.getIntScriptVar(bcd, beast_lib.PET_HAPPINESS_SCRIPTVAR);
                int updatedHappiness = currentHappiness - 5;
                utils.setScriptVar(bcd, beast_lib.PET_HAPPINESS_SCRIPTVAR, updatedHappiness);
                beast_lib.updateBeastStats(bcd, beast);
            }
            if (command.equals("testCrystal"))
            {
                obj_id createdObject = createObjectInInventoryAllowOverload("object/tangible/item/quest/force_sensitive/fs_buff_item.iff", self);
                if (!isIdNull(createdObject))
                {
                    setObjVar(createdObject, "createdFor", self);
                    setObjVar(createdObject, "item.time.reuse_time", 345600);
                    setObjVar(createdObject, "item.buff.type", 6);
                    setObjVar(createdObject, "item.buff.value", 900);
                    setObjVar(createdObject, "item.buff.duration", 5400);
                }
                return SCRIPT_CONTINUE;
            }
            if (command.equals("giveTargetBuff"))
            {
                if (st.hasMoreTokens())
                {
                    String buffName = st.nextToken();
                    buff.applyBuff(target, buffName);
                }
            }
            if (command.equals("giveMidlithe"))
            {
                if (st.hasMoreTokens())
                {
                    String count = st.nextToken();
                    obj_id lootItem = static_item.createNewItemFunction(space_combat.NOVA_ORION_RESOURCE, utils.getInventoryContainer(self));
                    setCount(lootItem, utils.stringToInt(count));
                }
            }
            if (command.equals("nukeTarget"))
            {
                destroyObject(target);
                return SCRIPT_OVERRIDE;
            }
            if (command.equals("getPosture"))
            {
                int myPosture = getPosture(target);
                switch (myPosture)
                {
                    case POSTURE_NONE:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_NONE");
                    break;
                    case POSTURE_UPRIGHT:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_UPRIGHT");
                    break;
                    case POSTURE_CROUCHED:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_CROUCHED");
                    break;
                    case POSTURE_PRONE:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_PRONE");
                    break;
                    case POSTURE_SNEAKING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_SNEAKING");
                    break;
                    case POSTURE_BLOCKING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_BLOCKING");
                    break;
                    case POSTURE_CLIMBING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_CLIMBING");
                    break;
                    case POSTURE_FLYING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_FLYING");
                    break;
                    case POSTURE_LYING_DOWN:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_LYING_DOWN");
                    break;
                    case POSTURE_SITTING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_SITTING");
                    break;
                    case POSTURE_SKILL_ANIMATING:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_SKILL_ANIMATING");
                    break;
                    case POSTURE_DRIVING_VEHICLE:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_DRIVING_VEHICLE");
                    break;
                    case POSTURE_RIDING_CREATURE:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_RIDING_CREATURE");
                    break;
                    case POSTURE_KNOCKED_DOWN:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_KNOCKED_DOWN");
                    break;
                    case POSTURE_INCAPACITATED:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_INCAPACITATED");
                    break;
                    case POSTURE_DEAD:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_DEAD");
                    break;
                    case POSTURE_COUNT:
                    sendSystemMessageTestingOnly(self, "my Posuture is POSTURE_COUNT");
                    break;
                    default:
                    sendSystemMessageTestingOnly(self, "my Posuture is default");
                    break;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int testSuiCountDown(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "params is " + params);
        return SCRIPT_CONTINUE;
    }
    public int onHateListSuiUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL || bp == sui.BP_OK)
        {
            sui.removePid(self, PID_NAME);
            return SCRIPT_CONTINUE;
        }
        if (!sui.hasPid(self, PID_NAME))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int refreshSuiHateList(obj_id self, dictionary params) throws InterruptedException
    {
        if (sui.hasPid(self, PID_NAME))
        {
            int pid = sui.getPid(self, PID_NAME);
            obj_id target = getIntendedTarget(self);
            if (!isIdValid(target) || !exists(target))
            {
                return SCRIPT_CONTINUE;
            }
            String enemyNames = generateStringHateList(target);
            if (enemyNames != null && !enemyNames.equals(""))
            {
                setSUIProperty(pid, sui.MSGBOX_PROMPT, sui.PROP_TEXT, enemyNames);
                flushSUIPage(pid);
                messageTo(self, "refreshSuiHateList", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int getStructureGuildId(obj_id self) throws InterruptedException
    {
        int guildId = 0;
        obj_id structure = player_structure.getStructure(self);
        if (isIdValid(structure) && hasObjVar(structure, "player_structure.owner") && hasObjVar(structure, "guildId"))
        {
            guildId = getIntObjVar(structure, "guildId");
            if (guildId != 0 && !guildExists(guildId))
            {
                removeObjVar(structure, "guildId");
                guildId = 0;
            }
        }
        if (guildId == 0 && hasObjVar(self, "guildId"))
        {
            guildId = getIntObjVar(self, "guildId");
            if (guildId != 0 && !guildExists(guildId))
            {
                removeObjVar(self, "guildId");
                guildId = 0;
            }
        }
        return guildId;
    }
    public String generateStringHateList(obj_id target) throws InterruptedException
    {
        obj_id[] enemies = getHateList(target);
        if (enemies == null || enemies.length == 0)
        {
            return null;
        }
        String enemyNames = "\n";
        for (obj_id enemy : enemies) {
            String enemyStringName = "";
            if (!isPlayer(enemy)) {
                enemyStringName = "NotAPlayer ObjID(" + enemy + ")";
            } else {
                enemyStringName = getPlayerName(enemy);
            }
            enemyNames += enemyStringName + "\t\t" + (int) getHate(target, enemy) + "\n";
        }
        return enemyNames;
    }
}
