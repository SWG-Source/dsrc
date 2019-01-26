package script.test;

import script.*;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class mboudreaux_test extends script.base.remote_object_requester
{
    public mboudreaux_test()
    {
    }
    public static final String FINISH_PLANET = "tatooine";
    public static final float FINISH_X = 3528.0f;
    public static final float FINISH_Z = -4804.0f;
    public static final String PGC_QUEST_CONTROL_DEVICE = "object/intangible/saga_system/sage_intangible_holocron.iff";
    public static final int PQ_TASK_FINISHED = 2;
    public static final int PQ_TASK_INACTIVE = 4;
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (true)
        {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
            if (tok.hasMoreTokens())
            {
                String command = tok.nextToken();
                if (command.equalsIgnoreCase("mb_performance"))
                {
                    if (tok.hasMoreTokens())
                    {
                        int rounds = 50;
                        float totalCanSee = 0;
                        float totalCached = 0;
                        long frequency = queryPerformanceCounterFrequency();
                        if (frequency == 0L)
                        {
                            frequency = 1L;
                        }
                        dictionary localDict = new dictionary();
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        obj_id target = getObjIdWithNull(lValue);
                        int iterations = 1000;
                        if (tok.hasMoreTokens())
                        {
                            value = tok.nextToken();
                            iterations = Integer.parseInt(value);
                        }
                        for (int j = 0; j < rounds; ++j)
                        {
                            long startTimeCanSee = queryPerformanceCounter();
                            for (int i = 0; i < iterations; ++i)
                            {
                                boolean ret = canSee(self, target);
                            }
                            long endTimeCanSee = queryPerformanceCounter();
                            long startTimeLocalCache = queryPerformanceCounter();
                            boolean cacheValue = canSee(self, target);
                            localDict.put("cachedCanSee", cacheValue);
                            for (int i = 0; i < iterations; ++i)
                            {
                                boolean cache = localDict.getBoolean("cachedCanSee");
                                localDict.remove("cachedCanSee");
                                localDict.put("cachedCanSee", cache);
                            }
                            long endTimeLocalCache = queryPerformanceCounter();
                            totalCanSee += (float)(endTimeCanSee - startTimeCanSee) / frequency;
                            totalCached += (float)(endTimeLocalCache - startTimeLocalCache) / frequency;
                        }
                        debugConsoleMsg(self, "Average Times for " + rounds + " of " + iterations + " iterations each were: canSee (" + totalCanSee + "), Java Cache (" + totalCached + ")");
                    }
                    return SCRIPT_CONTINUE;
                }
                if (command.equalsIgnoreCase("mb_addObjectEffect"))
                {
                    vector vec = new vector(0.0f, 0.0f, 0.0f);
                    if (tok.hasMoreTokens())
                    {
                        String object = tok.nextToken();
                        String filename = tok.nextToken();
                        String scale = tok.nextToken();
                        String label = tok.nextToken();
                        float scaleFloat = Float.parseFloat(scale);
                        long object_id = Long.parseLong(object);
                        addObjectEffect(getObjIdWithNull(object_id), filename, "head", vec, scaleFloat, label);
                    }
                    else 
                    {
                        addObjectEffect(self, "appearance/pt_green_fire_base.prt", "head", vec, 0.25f, "TestGreenFire");
                    }
                    return SCRIPT_CONTINUE;
                }
                if (command.equalsIgnoreCase("mb_removeObjectEffect"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String object = tok.nextToken();
                        String label = tok.nextToken();
                        long object_id = Long.parseLong(object);
                        removeObjectEffect(getObjIdWithNull(object_id), label);
                    }
                    else 
                    {
                        removeObjectEffect(self, "TestGreenFire");
                    }
                    return SCRIPT_CONTINUE;
                }
                if (command.equalsIgnoreCase("mb_hasObjectEffect"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String object = tok.nextToken();
                        String label = tok.nextToken();
                        long object_id = Long.parseLong(object);
                        debugConsoleMsg(self, "Has Effect = " + hasObjectEffect(getObjIdWithNull(object_id), label));
                    }
                    else 
                    {
                        debugConsoleMsg(self, "Has Effect = " + hasObjectEffect(self, "TestGreenFire"));
                    }
                    return SCRIPT_CONTINUE;
                }
                if (command.equalsIgnoreCase("mb_stand"))
                {
                    debugConsoleMsg(self, "hit mb_stand");
                    setPosture(self, POSTURE_UPRIGHT);
                }
                if (command.equalsIgnoreCase("mb_warp"))
                {
                    debugConsoleMsg(self, "hit mb_warp");
                    warpPlayer(self, FINISH_PLANET, FINISH_X, 0, FINISH_Z, null, 0.0f, 0.0f, 0.0f, null, false);
                }
                if (command.equalsIgnoreCase("mb_goodloc"))
                {
                    debugConsoleMsg(self, "hit mb_goodloc");
                    location locLowerLeft = new location();
                    locLowerLeft.x = 200 + 20;
                    locLowerLeft.z = 200 + 20;
                    location locUpperRight = new location();
                    locUpperRight.x = 200 + 40;
                    locUpperRight.z = 200 + 40;
                    location goodLoc = getGoodLocationAvoidCollidables(15.0f, 15.0f, locLowerLeft, locUpperRight, false, false, 5.0f);
                    if (goodLoc == null)
                    {
                        debugConsoleMsg(self, "did NOT find goodLocation");
                    }
                    else 
                    {
                        debugConsoleMsg(self, "did find goodlocation");
                    }
                }
                if (command.equalsIgnoreCase("mb_acklay"))
                {
                    debugConsoleMsg(self, "hit mb_acklay");
                    debugConsoleMsg(self, "It's morphing time!");
                    setObjectAppearance(self, "object/mobile/shared_mutant_acklay.iff");
                }
                if (command.equalsIgnoreCase("mb_rancor"))
                {
                    debugConsoleMsg(self, "hit mb_rancor");
                    debugConsoleMsg(self, "It's morphing time!");
                    setObjectAppearance(self, "object/mobile/shared_mutant_rancor.iff");
                }
                if (command.equalsIgnoreCase("mb_boba"))
                {
                    debugConsoleMsg(self, "hit mb_boba");
                    debugConsoleMsg(self, "It's morphing time!");
                    setObjectAppearance(self, "object/mobile/shared_boba_fett.iff");
                }
                if (command.equalsIgnoreCase("mb_revert"))
                {
                    debugConsoleMsg(self, "hit mb_revert");
                    revertObjectAppearance(self);
                }
                if (command.equalsIgnoreCase("mb_states"))
                {
                    debugConsoleMsg(self, "hit mb_states");
                    debugConsoleMsg(self, "Disguised State: " + getState(self, 35));
                    debugConsoleMsg(self, "Acid Burned State: " + getState(self, 38));
                    debugConsoleMsg(self, "Energy Burned State: " + getState(self, 39));
                }
                if (command.equalsIgnoreCase("mb_acidBurned"))
                {
                    debugConsoleMsg(self, "hit mb_acidBurned");
                    int active = getState(self, 38);
                    if (active == 0)
                    {
                        setState(self, 38, true);
                    }
                    else 
                    {
                        setState(self, 38, false);
                    }
                }
                if (command.equalsIgnoreCase("mb_energyBurned"))
                {
                    debugConsoleMsg(self, "hit mb_energyBurned");
                    int active = getState(self, 39);
                    if (active == 0)
                    {
                        setState(self, 39, true);
                    }
                    else 
                    {
                        setState(self, 39, false);
                    }
                }
                if (command.equalsIgnoreCase("mb_helmetbackpack"))
                {
                    debugConsoleMsg(self, "hit mb_helmetbackpack");
                    debugConsoleMsg(self, "Helmet: " + isPlayerHelmetHidden(self));
                    debugConsoleMsg(self, "Backpack: " + isPlayerBackpackHidden(self));
                }
                else if (command.equalsIgnoreCase("mb_ticket"))
                {
                    enterClientTicketPurchaseMode(self, "tatooine", "Starport", false);
                    return SCRIPT_CONTINUE;
                }
                else if (command.equalsIgnoreCase("mb_clientProjectileObjToObj"))
                {
                    debugConsoleMsg(self, "hit mb_clientProjectileObjToObj");
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        createClientProjectileObjectToObject(self, "object/weapon/ranged/pistol/shared_pistol_dl44.iff", self, "hold_r", target, "hold_l", 30.0f, 4.0f, true, 255, 0, 0, 255);
                    }
                }
                else if (command.equalsIgnoreCase("mb_clientProjectileLocToObj"))
                {
                    debugConsoleMsg(self, "hit mb_clientProjectileLocToObj");
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        createClientProjectileLocationToObject(self, "object/weapon/ranged/pistol/shared_pistol_dl44.iff", getLocation(self), target, "hold_l", 30.0f, 4.0f, true, 255, 0, 0, 255);
                    }
                }
                else if (command.equalsIgnoreCase("mb_clientProjectileObjToLoc"))
                {
                    debugConsoleMsg(self, "hit mb_clientProjectileObjToLoc");
                    final obj_id target = getIntendedTarget(self);
                    if (isValidId(target))
                    {
                        createClientProjectileObjectToLocation(self, "object/weapon/ranged/pistol/shared_pistol_dl44.iff", self, "hold_r", getLocation(target), 30.0f, 4.0f, true, 255, 0, 0, 255);
                    }
                }
                else if (command.equalsIgnoreCase("mb_override"))
                {
                    debugConsoleMsg(self, "hit mb_override");
                    if (tok.hasMoreTokens())
                    {
                        String action = tok.nextToken();
                        overrideDefaultAttack(self, action);
                    }
                }
                else if (command.equalsIgnoreCase("mb_clearoverride"))
                {
                    debugConsoleMsg(self, "hit mb_clearoverride");
                    removeDefaultAttackOverride(self);
                }
                else if (command.equalsIgnoreCase("mb_getoverride"))
                {
                    debugConsoleMsg(self, "Current override: " + getDefaultAttackOverrideActionName(self));
                }
                else if (command.equalsIgnoreCase("mb_setMC"))
                {
                    debugConsoleMsg(self, "Hit mb_setMC");
                    final obj_id target = getIntendedTarget(self);
                    addMissionCriticalObject(self, target);
                }
                else if (command.equalsIgnoreCase("mb_triggerRadius"))
                {
                    debugConsoleMsg(self, "Hit mb_triggerRadius");
                    final obj_id target = getIntendedTarget(self);
                    updateNetworkTriggerVolume(target, 512.0f);
                }
                else if (command.equalsIgnoreCase("mb_hologram"))
                {
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int intValue = Integer.parseInt(value);
                        obj_id target = getIntendedTarget(self);
                        setHologramType(target, intValue);
                    }
                }
                else if (command.equalsIgnoreCase("mb_isContainedByPA"))
                {
                    obj_id target = null;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        target = getObjIdWithNull(lValue);
                    }
                    if (!isIdValid(target))
                    {
                        return SCRIPT_CONTINUE;
                    }
                    debugConsoleMsg(self, "isContainedByPlayerAppearanceInv: " + isContainedByPlayerAppearanceInventory(self, target));
                }
                else if (command.equalsIgnoreCase("mb_getAllPAItems"))
                {
                    obj_id[] allItems = getAllItemsFromAppearanceInventory(self);
                    debugConsoleMsg(self, "Found " + allItems.length + " total items.");
                    for (int i = 0; i < allItems.length; ++i)
                    {
                        debugConsoleMsg(self, "Item [" + i + "] " + allItems[i]);
                    }
                }
                else if (command.equalsIgnoreCase("mb_animate"))
                {
                    obj_id target = getIntendedTarget(self);
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        doAnimationAction(target, value);
                    }
                }
                else if (command.equalsIgnoreCase("mb_custom"))
                {
                    obj_id target = null;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        target = getObjIdWithNull(lValue);
                    }
                    custom_var[] allVars = getAllCustomVars(target);
                    debugConsoleMsg(self, "Var length = " + allVars.length);
                    if (allVars.length == 1)
                    {
                        openCustomizationWindow(self, target, allVars[0].getVarName(), -1, -1, "", -1, -1, "", -1, -1, "", -1, -1);
                    }
                    else if (allVars.length == 2)
                    {
                        openCustomizationWindow(self, target, allVars[0].getVarName(), 0, 10, allVars[1].getVarName(), -1, -1, "", -1, -1, "", -1, -1);
                    }
                    else if (allVars.length == 3)
                    {
                        openCustomizationWindow(self, target, allVars[0].getVarName(), -1, -1, allVars[1].getVarName(), -1, -1, allVars[2].getVarName(), -1, -1, "", -1, -1);
                    }
                    else if (allVars.length == 4)
                    {
                        openCustomizationWindow(self, target, allVars[0].getVarName(), -1, -1, allVars[1].getVarName(), -1, -1, allVars[2].getVarName(), -1, -1, allVars[3].getVarName(), -1, -1);
                    }
                }
                else if (command.equalsIgnoreCase("mb_table"))
                {
                    String type = "";
                    if (tok.hasMoreTokens())
                    {
                        type = tok.nextToken();
                    }
                    if (type.equalsIgnoreCase("column"))
                    {
                        String title = "My Guild";
                        String prompt = "This is my prompt. Text is going in here!";
                        String[] colTitles = 
                        {
                            "Name",
                            "Guild Rank",
                            "PvP Kills",
                            "Last Login Date"
                        };
                        String[] colTypes = 
                        {
                            "text",
                            "text",
                            "integer",
                            "text"
                        };
                        String[][] data = 
                        {
                            
                            {
                                "PlayerA",
                                "PlayerB",
                                "PlayerC",
                                "PlayerD",
                                "PlayerE"
                            },
                            
                            {
                                "Awesome Dude",
                                "Great Dude",
                                "Okay Dude",
                                "Meh Dude",
                                "Poor Dude"
                            },
                            
                            {
                                "200",
                                "150",
                                "100",
                                "50",
                                "0"
                            },
                            
                            {
                                "01/04/2009",
                                "01/03/2009",
                                "01/02/2009",
                                "01/01/2009",
                                "01/05/2009"
                            }
                        };
                        int id = sui.table(self, self, sui.OK_CANCEL, title, "tableHandler", prompt, colTitles, colTypes, data, false, true);
                        debugConsoleMsg(self, "Table PID is " + id);
                    }
                    else if (type.equalsIgnoreCase("row"))
                    {
                        String title = "My Table";
                        String[] colTitles = 
                        {
                            "Name",
                            "Weight",
                            "BMI",
                            "BMI"
                        };
                        String[] colTypes = 
                        {
                            "text",
                            "integer",
                            "percent",
                            "text"
                        };
                        String[][] data = 
                        {
                            
                            {
                                "John",
                                "180",
                                "12",
                                "12"
                            },
                            
                            {
                                "Steve",
                                "190",
                                "20",
                                "12"
                            },
                            
                            {
                                "Andy",
                                "230",
                                "40",
                                "12"
                            }
                        };
                        int id = sui.table(self, self, sui.OK_CANCEL, title, "tableHandler", null, colTitles, colTypes, data, true, false);
                        debugConsoleMsg(self, "Table PID is " + id);
                    }
                }
                else if (command.equalsIgnoreCase("mb_holiday"))
                {
                    obj_id target = getIntendedTarget(self);
                    if (isIdValid(target))
                    {
                        setCondition(target, CONDITION_HOLIDAY_INTERESTING);
                    }
                }
                else if (command.equalsIgnoreCase("mb_addAccess"))
                {
                    obj_id target = getIntendedTarget(self);
                    obj_id user = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        user = getObjIdWithNull(lValue);
                    }
                    if (isIdValid(target))
                    {
                        addUserToAccessList(target, user);
                    }
                }
                else if (command.equalsIgnoreCase("mb_addGuildAccess"))
                {
                    debugConsoleMsg(self, "Hit mb_addGuildAccess");
                    obj_id target = getIntendedTarget(self);
                    int lValue = 0;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        lValue = Integer.parseInt(value);
                        debugConsoleMsg(self, "Parsed out guild value of " + lValue);
                    }
                    if (isIdValid(target))
                    {
                        debugConsoleMsg(self, "Adding Guild " + lValue + " to object " + target);
                        addGuildToAccessList(target, lValue);
                    }
                }
                else if (command.equalsIgnoreCase("mb_removeAccess"))
                {
                    obj_id target = getIntendedTarget(self);
                    obj_id user = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        user = getObjIdWithNull(lValue);
                    }
                    if (isIdValid(target))
                    {
                        removeUserFromAccessList(target, user);
                    }
                }
                else if (command.equalsIgnoreCase("mb_clearAccess"))
                {
                    obj_id target = getIntendedTarget(self);
                    if (isIdValid(target))
                    {
                        clearUserAccessList(target);
                    }
                }
                else if (command.equalsIgnoreCase("mb_getUserAccess"))
                {
                    obj_id[] items = getUserAccessList(getIntendedTarget(self));
                    if (items != null && items.length > 0)
                    {
                        for (obj_id item : items) {
                            debugConsoleMsg(self, "User: " + item);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_getGuildAccess"))
                {
                    int[] items = getGuildAccessList(getIntendedTarget(self));
                    if (items != null && items.length > 0)
                    {
                        for (int item : items) {
                            debugConsoleMsg(self, "Guild: " + item);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_getObjects"))
                {
                    obj_id[] items = getObjectsInRange(self, 64.0f);
                    if (items != null && items.length > 0)
                    {
                        for (obj_id item : items) {
                            debugConsoleMsg(self, "Object in range: " + item);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_getCreatureObjects"))
                {
                    obj_id[] items = getCreaturesInRange(self, 64.0f);
                    if (items != null && items.length > 0)
                    {
                        for (obj_id item : items) {
                            debugConsoleMsg(self, "Creature Object in range: " + item);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_loc"))
                {
                    obj_id user = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        user = getObjIdWithNull(lValue);
                    }
                    location myLoc = getLocation(user);
                    debugConsoleMsg(self, "Location Info: " + myLoc);
                }
                else if (command.equalsIgnoreCase("mb_dynamic"))
                {
                    location myLoc = getLocation(self);
                    debugConsoleMsg(self, "Attempting to create dynamic spawn at " + myLoc);
                    obj_id newRegion = createCircleRegionWithSpawn(myLoc, 50.0f, "mboudreaux2", 0, 0, 0, 0, 10, 20, 1, 0, true, false, "datatables/dynamic_region_spawns/banthatest.iff", 3);
                    debugConsoleMsg(self, "New Region = " + newRegion);
                }
                else if (command.equalsIgnoreCase("mb_removedynamic"))
                {
                    region[] regionsHere = getRegionsAtPoint(getLocation(self));
                    if (regionsHere != null && regionsHere.length > 0)
                    {
                        for (region currentRegion : regionsHere) {
                            String regionName = currentRegion.getName();
                            if (regionName.equals("mboudreaux2")) {
                                debugConsoleMsg(self, "Found region, removing it");
                                deleteRegion(currentRegion);
                            }
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_pq"))
                {
                    obj_id entry = createPlayerQuestObjectInInventory(self);
                    if (isIdValid(entry))
                    {
                        setPlayerQuestTitle(entry, "Matt's Super Kill Extravaganza");
                        setPlayerQuestDescription(entry, "Kill lots of stuff. For fun!");
                        setPlayerQuestDifficulty(entry, 90);
                    }
                    debugConsoleMsg(self, "PQ OID: " + entry);
                }
                else if (command.equalsIgnoreCase("mb_addTask"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    boolean returnVal = false;
                    if (tok.hasMoreTokens())
                    {
                        returnVal = addPlayerQuestTask(pq, "Matt's Kill Task", "I hate kreetles! Go kill 10 of them!", 10, getLocation(self));
                    }
                    else 
                    {
                        returnVal = addPlayerQuestTask(pq, "Matt's Kill Task", "I hate kreetles! Go kill 10 of them!", 10, null);
                    }
                    debugConsoleMsg(self, "Added task = " + returnVal);
                }
                else if (command.equalsIgnoreCase("mb_getTasks"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    String[] tasks = getPlayerQuestTasks(pq);
                    if (tasks != null && tasks.length > 0)
                    {
                        for (int i = 0; i < tasks.length; ++i)
                        {
                            debugConsoleMsg(self, "Task " + i + ":" + tasks[i]);
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_taskStatus"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int iValue = Integer.parseInt(value);
                        debugConsoleMsg(self, "Status for Index " + iValue + " is " + getPlayerQuestTaskStatus(pq, iValue));
                    }
                    else 
                    {
                        int[] status = getAllPlayerQuestTaskStatus(pq);
                        if (status != null && status.length > 0)
                        {
                            for (int i = 0; i < status.length; ++i)
                            {
                                debugConsoleMsg(self, "Task " + i + " status = " + status[i]);
                            }
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqLevel"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int iValue = Integer.parseInt(value);
                        setPlayerQuestDifficulty(pq, iValue);
                        debugConsoleMsg(self, "Level for PQ " + pq + " is now " + iValue);
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqActivate"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                    obj_id datapad = utils.getPlayerDatapad(self);
                    if (isIdValid(datapad))
                    {
                        obj_id controlDevice = createObject(PGC_QUEST_CONTROL_DEVICE, datapad, "");
                        if (isIdValid(controlDevice))
                        {
                            putIn(pq, controlDevice, self);
                            debugConsoleMsg(self, "PQ Activated");
                        }
                        else 
                        {
                            debugConsoleMsg(self, "Failed to create PQ control device.");
                        }
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqCounter"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int index = Integer.parseInt(value);
                        value = tok.nextToken();
                        int iValue = Integer.parseInt(value);
                        setPlayerQuestTaskCounter(pq, index, iValue);
                        debugConsoleMsg(self, "Level for PQ " + pq + " is now " + iValue);
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqWaypoint"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int index = Integer.parseInt(value);
                        debugConsoleMsg(self, "Waypoint for index " + index + " is " + getPlayerQuestWaypoint(pq, index));
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqComplete"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int index = Integer.parseInt(value);
                        debugConsoleMsg(self, "Marking PQ Task " + index + " as completed.");
                        setPlayerQuestTaskStatus(pq, index, PQ_TASK_FINISHED);
                    }
                }
                else if (command.equalsIgnoreCase("mb_pqInactive"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        int index = Integer.parseInt(value);
                        debugConsoleMsg(self, "Marking PQ Task " + index + " as inactive.");
                        setPlayerQuestTaskStatus(pq, index, PQ_TASK_INACTIVE);
                    }
                }
                else if (command.equalsIgnoreCase("mb_rating"))
                {
                    openRatingWindow(self, "Some Title", "Some Description!");
                }
                else if (command.equalsIgnoreCase("mb_recipe"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                    setPlayerQuestRecipe(pq, true);
                    debugConsoleMsg(self, "PQ Recipe set.");
                }
                else if (command.equalsIgnoreCase("mb_addRecipeData"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                    }
                    else 
                    {
                        return SCRIPT_CONTINUE;
                    }
                    if (tok.hasMoreTokens())
                    {
                        String data = tok.nextToken();
                        addPlayerQuestTaskRecipeData(pq, data);
                        debugConsoleMsg(self, "Recipe data added.");
                    }
                }
                else if (command.equalsIgnoreCase("mb_filter"))
                {
                    debugConsoleMsg(self, filterText(text));
                }
                else if (command.equalsIgnoreCase("mb_editRecipe"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                        openPlayerQuestRecipe(self, pq);
                    }
                }
                else if (command.equalsIgnoreCase("mb_resetRecipe"))
                {
                    obj_id pq = self;
                    if (tok.hasMoreTokens())
                    {
                        String value = tok.nextToken();
                        long lValue = Long.parseLong(value);
                        pq = getObjIdWithNull(lValue);
                        resetAllPlayerQuestData(pq);
                        setPlayerQuestTitle(pq, "Matt's Super Kill Extravaganza");
                        setPlayerQuestDescription(pq, "Kill lots of stuff. For fun!");
                        setPlayerQuestDifficulty(pq, 90);
                        addPlayerQuestTask(pq, "Matt's Kill Task", "I hate kreetles! Go kill 10 of them!", 10, null);
                        setPlayerQuestCreator(pq, self);
                    }
                }
                else if (command.equalsIgnoreCase("mb_commTest"))
                {
                    String message_string = "\\#FF0000Hi %NT also known as %TT";
                    prose_package pp = new prose_package();
                    string_id message_base = new string_id("saga_system", "holocron_string_message");
                    pp = prose.getPackage(message_base, self, self);
                    prose.setTO(pp, message_string);
                    prose.setTT(pp, self);
                    String appearance = "object/mobile/boba_fett.iff";
                    commPlayerQuest(self, self, pp, appearance);
                }
                else if (command.equalsIgnoreCase("mb_gots"))
                {
                    debugConsoleMsg(self, "GOT_chronicles_relic = " + GOT_chronicles_relic);
                    debugConsoleMsg(self, "GOT_chronicles_chronicle = " + GOT_chronicles_chronicle);
                    debugConsoleMsg(self, "GOT_chronicles_quest_holocron = " + GOT_chronicles_quest_holocron);
                    debugConsoleMsg(self, "GOT_chornicles_quest_holocron_recipe = " + GOT_chronicles_quest_holocron_recipe);
                    debugConsoleMsg(self, "GOT_chronicles_relic_fragment = " + GOT_chronicles_relic_fragment);
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int tableHandler(obj_id self, dictionary params) throws InterruptedException
    {
        debugConsoleMsg(self, "handler hit. Params = " + params);
        debugConsoleMsg(self, "Visual Index = " + sui.getTableSelectedRow(params) + " Logic Index = " + sui.getTableLogicalIndex(params));
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!isGod(self) || getGodLevel(self) < 50 || !isPlayer(self)) {
            detachScript(self, "test.mboudreaux_test");
        }
        return SCRIPT_CONTINUE;
    }
    public int startPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int stopPerform(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnCreateSaga(obj_id self, dictionary params) throws InterruptedException
    {
        debugConsoleMsg(self, "Params = " + params.toString());
        return SCRIPT_CONTINUE;
    }
    public int OnRatingFinished(obj_id self, int rating) throws InterruptedException
    {
        debugConsoleMsg(self, "OnRatingFinished: Params = " + rating);
        return SCRIPT_CONTINUE;
    }
    public int OnAbandonPlayerQuest(obj_id self, obj_id playerQuest) throws InterruptedException
    {
        debugConsoleMsg(self, "OnAbandonPlayerQuest: Params = " + playerQuest);
        return SCRIPT_CONTINUE;
    }
}
