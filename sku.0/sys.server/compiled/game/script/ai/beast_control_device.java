package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.hue;
import script.library.space_utils;
import script.library.space_crafting;
import script.library.utils;
import script.library.sui;
import script.library.pet_lib;
import script.library.beast_lib;
import script.library.create;
import script.library.ai_lib;
import script.library.factions;
import script.library.consumable;
import script.library.healing;
import script.library.prose;
import script.library.vehicle;
import script.library.space_transition;
import script.library.buff;
import script.library.group;
import script.library.performance;
import script.library.ai_lib;
import script.library.tcg;

public class beast_control_device extends script.base_script
{
    public beast_control_device()
    {
    }
    public static final string_id SID_CANNOT_TRANSFER_BCD = new string_id("beast", "cannot_transfer_bcd");
    public static final string_id SID_CANNOT_CALL_BEAST_COMBAT = new string_id("beast", "beast_cant_call_combat");
    public static final string_id SID_CANNOT_STORE_BEAST_COMBAT = new string_id("beast", "beast_cant_store");
    public static final string_id SID_STUFF_BEAST = new string_id("beast", "beast_stuff");
    public static final String CALLED_FOR_PET = "player.calledForPet";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id myContainer = getContainedBy(self);
        obj_id yourPad = utils.getPlayerDatapad(player);
        if (myContainer != yourPad)
        {
            if (hasScript(myContainer, "ai.beast_control_device"))
            {
                putIn(self, yourPad);
            }
            return SCRIPT_CONTINUE;
        }
        obj_id calledBeast = beast_lib.getBCDBeastCalled(self);
        if (!beast_lib.isValidBeast(calledBeast))
        {
            if (!isSpaceScene() || getState(player, STATE_SHIP_INTERIOR) == 1)
            {
                setCount(self, 0);
                mi.addRootMenu(menu_info_types.PET_CALL, new string_id(pet_lib.MENU_FILE, "menu_call"));
            }
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_STUFF_BEAST);
        }
        else 
        {
            if (calledBeast.isLoaded() && exists(calledBeast) && !ai_lib.isInCombat(player))
            {
                mi.addRootMenu(menu_info_types.PET_CALL, new string_id(pet_lib.MENU_FILE, "menu_store"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (ai_lib.aiIsDead(player) || getPerformanceType(player) != 0)
        {
            sendSystemMessage(player, new string_id("spam", "cant_do_it_state"));
            return SCRIPT_CONTINUE;
        }
        obj_id calledBeast = beast_lib.getBCDBeastCalled(self);
        if (item == menu_info_types.PET_CALL && !beast_lib.isValidBeast(calledBeast))
        {
            String beastName = beast_lib.getBCDBeastName(self);
            if (beastName == null || beastName.equals(""))
            {
                return SCRIPT_CONTINUE;
            }
            if (isSpaceScene() && getState(player, STATE_SHIP_INTERIOR) != 1)
            {
                string_id strSpam = new string_id("space/space_interaction", "no_action_station");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            if (vehicle.isRidingBattlefieldVehicle(player))
            {
                string_id strSpam = new string_id("beast", "battlefield_vehicle_restriction");
                sendSystemMessage(player, strSpam);
                return SCRIPT_CONTINUE;
            }
            if (!beast_lib.isBeastMaster(player))
            {
                sendSystemMessage(player, beast_lib.SID_NOT_BEAST_MASTER);
                return SCRIPT_CONTINUE;
            }
            if (utils.hasScriptVar(player, CALLED_FOR_PET))
            {
                int timeLeft = pet_lib.getTimeLeft(player, CALLED_FOR_PET, pet_lib.CALL_FINISH);
                if (timeLeft < 0)
                {
                    utils.removeScriptVar(player, CALLED_FOR_PET);
                }
                else 
                {
                    prose_package pp = prose.getPackage(pet_lib.SID_SYS_CALL_DELAY_FINISH_PET, timeLeft);
                    sendSystemMessageProse(player, pp);
                    return SCRIPT_CONTINUE;
                }
            }
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_RIDEABLE))
            {
                obj_id rideable = callable.getCallable(player, callable.CALLABLE_TYPE_RIDEABLE);
                if (isIdValid(rideable) && exists(rideable))
                {
                    callable.storeCallable(player, rideable);
                }
            }
            obj_id currentBeast = null;
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_COMBAT_PET))
            {
                currentBeast = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
                if (beast_lib.isValidBeast(currentBeast))
                {
                    sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    beast_lib.setBCDBeastCalled(self, null);
                }
            }
            if (isIdValid(space_transition.getContainingShip(player)))
            {
                if (space_utils.isInStation(player))
                {
                    sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                    return SCRIPT_CONTINUE;
                }
            }
            if ((getLocation(player).area).startsWith("kashyyyk_pob") && getTopMostContainer(player) == player)
            {
                sendSystemMessage(player, pet_lib.SID_SYS_CANT_CALL);
                return SCRIPT_CONTINUE;
            }
            if (beast_lib.getTotalBeastsCalled(player) > 0)
            {
                sendSystemMessage(player, beast_lib.SID_MAXIMUM_BEASTS);
                return SCRIPT_CONTINUE;
            }
            int beastLevel = beast_lib.getBCDBeastLevel(self);
            if (getLevel(player) < beastLevel - beast_lib.BEAST_LEVEL_MAX_DIFFERENCE && !isGod(player))
            {
                sendSystemMessage(player, beast_lib.SID_BEAST_TOO_HIGH_LEVEL);
                return SCRIPT_CONTINUE;
            }
            obj_id beast = beast_lib.createBeastFromBCD(player, self);
            if (isIdValid(beast))
            {
                beast_lib.checkForFavoriteLocation(self);
            }
            if (group.isGrouped(player))
            {
                messageTo(self, "ownerGrouped", null, 1, false);
            }
            if (performance.checkPlayerEntertained(player, "both"))
            {
                messageTo(self, "ownerEntertained", null, 1, false);
            }
            sendDirtyObjectMenuNotification(self);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.PET_CALL && beast_lib.isValidBeast(calledBeast))
        {
            if (ai_lib.isInCombat(player))
            {
                sendSystemMessage(player, SID_CANNOT_STORE_BEAST_COMBAT);
                return SCRIPT_CONTINUE;
            }
            if (callable.hasCallable(player, callable.CALLABLE_TYPE_COMBAT_PET))
            {
                obj_id currentBeast = callable.getCallable(player, callable.CALLABLE_TYPE_COMBAT_PET);
                if (ai_lib.isInCombat(currentBeast))
                {
                    sendSystemMessage(player, new string_id("beast", "beast_cant_store"));
                    return SCRIPT_CONTINUE;
                }
            }
            beast_lib.storeBeast(self);
            sendDirtyObjectMenuNotification(self);
            return SCRIPT_CONTINUE;
        }
        else if (item == menu_info_types.SERVER_MENU1)
        {
            if (beast_lib.isValidBeast(calledBeast))
            {
                sendSystemMessage(player, new string_id("beast", "beast_cant_stuff"));
                return SCRIPT_CONTINUE;
            }
            String title = utils.packStringId(new string_id("beast", "beast_stuff_really_title"));
            String prompt = getString(new string_id("beast", "beast_stuff_really_prompt"));
            int pid = sui.msgbox(self, player, prompt, sui.YES_NO, title, "handleBeastStuffingConfirm");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBeastStuffingConfirm(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        beast_lib.convertBcdIntoBeastItem(player, self);
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToReceiveItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (hasScript(item, "ai.beast_control_device"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(transferer))
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        beast_lib.storeBeast(self);
        obj_id master = beast_lib.getBCDPlayer(self);
        if (beast_lib.isValidPlayer(master))
        {
            setBeastmasterPet(master, null);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        beast_lib.storeBeast(self);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (!beast_lib.isValidPlayer(player))
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(player, new string_id("beast", "datapad_beast_added"));
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        if (!exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = beast_lib.BEAST_MOOD_TITLE;
        String currentHappiness = utils.packStringId(beast_lib.convertHappinessString(self));
        if (currentHappiness != null)
        {
            attribs[idx] = "" + currentHappiness;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        names[idx] = beast_lib.BEAST_LOYALTY_TITLE;
        String currentLoyalty = utils.packStringId(beast_lib.convertLoyaltyString(self));
        if (currentLoyalty != null)
        {
            attribs[idx] = "" + currentLoyalty;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int level = beast_lib.getBCDBeastLevel(self);
        int trainingPoints = beast_lib.getSingleSkillMaxTrainingPoints(self, level);
        if (trainingPoints > 0)
        {
            names[idx] = "bm_single_max_training_points";
            attribs[idx] = "" + trainingPoints;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int pointsSpent = beast_lib.calculateTrainingPointsSpent(self);
        if (pointsSpent > 0)
        {
            names[idx] = "bm_points_spent";
            attribs[idx] = "" + pointsSpent;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        int maxAvailableTrainingPoints = beast_lib.getAvailableTrainingPoints(self);
        if (maxAvailableTrainingPoints > 0)
        {
            names[idx] = "bm_points_available";
            attribs[idx] = "" + maxAvailableTrainingPoints;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (hasObjVar(self, beast_lib.OBJVAR_BEAST_INCUBATION_BONUSES))
        {
            int[] incubationBonuses = getIntArrayObjVar(self, beast_lib.OBJVAR_BEAST_INCUBATION_BONUSES);
            if (incubationBonuses.length == beast_lib.ARRAY_BEAST_INCUBATION_STATS.length && incubationBonuses.length == beast_lib.DISPLAY_NAMES.length && incubationBonuses.length == beast_lib.DISPLAY_OBJVAR_CONVERSION_RATES.length)
            {
                for (int i = 0; i < incubationBonuses.length; i++)
                {
                    String name = beast_lib.DISPLAY_NAMES[i];
                    if (name.indexOf("_skill") < 0)
                    {
                        if (!name.equals("block_value_bonus"))
                        {
                            names[idx] = beast_lib.DISPLAY_NAMES[i];
                            attribs[idx] = "" + utils.roundFloatByDecimal((float)incubationBonuses[i] * beast_lib.DISPLAY_OBJVAR_CONVERSION_RATES[i]) + "%";
                            idx++;
                        }
                        else 
                        {
                            names[idx] = beast_lib.DISPLAY_NAMES[i];
                            attribs[idx] = "" + incubationBonuses[i];
                            idx++;
                        }
                        continue;
                    }
                    else 
                    {
                        names[idx] = beast_lib.DISPLAY_NAMES[i];
                        attribs[idx] = "" + incubationBonuses[i];
                        idx++;
                        continue;
                    }
                }
            }
        }
        if (exists(self) && hasObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER))
        {
            String creatorName = getStringObjVar(self, beast_lib.OBJVAR_BEAST_ENGINEER);
            names[idx] = "bm_creator";
            attribs[idx] = creatorName;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        setDescriptionStringId(self, new string_id("spam", "bm_train_pet_info"));
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (tcg.isBarnRanchhand(destContainer) || tcg.isBarnRanchhand(getContainedBy(destContainer)))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id bcdPlayer = beast_lib.getBCDPlayer(self);
        if (isIdValid(bcdPlayer) && destContainer == bcdPlayer || getContainedBy(destContainer) == bcdPlayer)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(transferer, SID_CANNOT_TRANSFER_BCD);
        return SCRIPT_OVERRIDE;
    }
    public int handlePetDeathblow(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int beastPing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beast = params.getObjId("beastId");
        int messageCount = params.getInt("messageId");
        if (!beast_lib.isValidBeast(beast))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id beastOnBCD = beast_lib.getBCDBeastCalled(self);
        obj_id player = beast_lib.getBCDPlayer(self);
        if (beastOnBCD != beast || getMaster(beast) != player || beast_lib.getTotalBeastsCalled(player) > 1)
        {
            beast_lib.storeBeast(self);
        }
        messageCount++;
        dictionary messageData = new dictionary();
        messageData.put("messageId", messageCount);
        messageTo(beast, "bcdPingResponse", messageData, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int beastHungry(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id beast = params.getObjId("beastId");
        obj_id beastOnBCD = beast_lib.getBCDBeastCalled(self);
        obj_id player = beast_lib.getBCDPlayer(self);
        if (beastOnBCD != beast || getMaster(beast) != player || beast_lib.getTotalBeastsCalled(player) > 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!beast_lib.hasBCDBeastFood(self))
        {
            LOG("beast_control_device", "Something is wrong. Pet has been fed before or this message shouldn't go off, yet the pet doesn't have the beastmood.beastLastFed");
        }
        int currentTime = getGameTime();
        int[] beastFood = beast_lib.getBCDBeastFood(self);
        if (beast_lib.getBCDBeastLastFedTime(self) > 3600)
        {
            if (beastFood.length != beast_lib.PET_FOOD_MAX_ARRAYS)
            {
                return SCRIPT_CONTINUE;
            }
            int[] autoFeederBuffCrc = buff.getAllBuffsByEffect(beast, "beast_auto_feeder");
            if (autoFeederBuffCrc != null && autoFeederBuffCrc.length > 0)
            {
                String autoFeederBuffName = buff.getBuffNameFromCrc(autoFeederBuffCrc[0]);
                if (autoFeederBuffName != null && autoFeederBuffName.length() > 0)
                {
                    if (beast_lib.feedBeastUsingAutoFeeder(beast, player, autoFeederBuffName))
                    {
                        return SCRIPT_CONTINUE;
                    }
                }
            }
            if (beastFood[beast_lib.PET_WHICH_FOOD] != beast_lib.PET_NO_FOOD)
            {
                beastFood[beast_lib.PET_WHICH_FOOD] = beast_lib.PET_NO_FOOD;
                beast_lib.setBCDBeastFood(self, beastFood);
                beast_lib.decrementBeastLoyalty(self, beast_lib.LOYALTY_FOOD_LOSS);
                beast_lib.updateBeastHappiness(self, beastOnBCD);
            }
            if (!utils.hasScriptVar(self, beast_lib.PET_HUNGRY_MESSAGE_SCRIPTVAR))
            {
                if (!isDead(beast))
                {
                    sendSystemMessage(player, beast_lib.SID_PET_HUNGRY);
                }
                utils.setScriptVar(self, beast_lib.PET_HUNGRY_MESSAGE_SCRIPTVAR, 1);
                messageTo(self, "removeHungryBlock", null, 600, false);
            }
        }
        else 
        {
            LOG("beast_control_device", "Something is wrong. Pet has been fed but their last time fed has not been updated");
        }
        return SCRIPT_CONTINUE;
    }
    public int ownerGrouped(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, beast_lib.PET_FAVORITES_OBJVAR))
        {
            LOG("beast_control_device", "Something is wrong. This BCD does not have the pets favorite activities stored. Setting them up now");
            beast_lib.setupHappinessLoyalty(self);
        }
        else 
        {
            obj_id player = beast_lib.getBCDPlayer(self);
            obj_id beast = beast_lib.getBCDBeastCalled(self);
            int[] beastHappiness = new int[beast_lib.LIKE_DISLIKE_MAX];
            beastHappiness = getIntArrayObjVar(self, beast_lib.PET_FAVORITES_OBJVAR);
            String favoriteActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[2] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            String dislikeActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[3] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            if (favoriteActivity.indexOf("grouped") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_FAVORITE_ACTIVITY);
                messageTo(self, "checkIfStillGrouped", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_FAVORITE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
            else if (dislikeActivity.indexOf("grouped") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_DISLIKE_ACTIVITY);
                messageTo(self, "checkIfStillGrouped", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_DISLIKE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkIfStillGrouped(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = beast_lib.getBCDPlayer(self);
        if (group.isGrouped(player))
        {
            messageTo(self, "checkIfStillGrouped", null, 1800, false);
        }
        else 
        {
            beast_lib.performingActivity(self, beast_lib.PET_NORMAL_ACTIVITY);
        }
        return SCRIPT_CONTINUE;
    }
    public int ownerEntertained(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, beast_lib.PET_FAVORITES_OBJVAR))
        {
            LOG("beast_control_device", "Something is wrong. This BCD does not have the pets favorite activities stored. Setting them up now");
            beast_lib.setupHappinessLoyalty(self);
        }
        else 
        {
            obj_id player = beast_lib.getBCDPlayer(self);
            obj_id beast = beast_lib.getBCDBeastCalled(self);
            int[] beastHappiness = new int[beast_lib.LIKE_DISLIKE_MAX];
            beastHappiness = getIntArrayObjVar(self, beast_lib.PET_FAVORITES_OBJVAR);
            String favoriteActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[2] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            String dislikeActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[3] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            if (favoriteActivity.indexOf("watchingEntertainer") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_FAVORITE_ACTIVITY);
                messageTo(self, "checkIfStillEntertained", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_FAVORITE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
            else if (dislikeActivity.indexOf("watchingEntertainer") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_DISLIKE_ACTIVITY);
                messageTo(self, "checkIfStillEntertained", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_DISLIKE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int checkIfStillEntertained(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = beast_lib.getBCDPlayer(self);
        if (performance.checkPlayerEntertained(player, "both"))
        {
            messageTo(self, "checkIfStillEntertained", null, 1800, false);
        }
        else 
        {
            beast_lib.performingActivity(self, beast_lib.PET_NORMAL_ACTIVITY);
        }
        return SCRIPT_CONTINUE;
    }
    public int beastKilledSomething(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, beast_lib.PET_FAVORITES_OBJVAR))
        {
            LOG("beast_control_device", "Something is wrong. This BCD does not have the pets favorite activities stored. Setting them up now");
            beast_lib.setupHappinessLoyalty(self);
        }
        else 
        {
            obj_id target = params.getObjId("targetId");
            obj_id player = beast_lib.getBCDPlayer(self);
            obj_id beast = beast_lib.getBCDBeastCalled(self);
            int[] beastHappiness = new int[beast_lib.LIKE_DISLIKE_MAX];
            beastHappiness = getIntArrayObjVar(self, beast_lib.PET_FAVORITES_OBJVAR);
            String favoriteActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[2] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            String dislikeActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[3] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            if (ai_lib.isAnimal(target))
            {
                if (favoriteActivity.indexOf("killCreature") > -1)
                {
                    beast_lib.performingActivity(self, beast_lib.PET_FAVORITE_ACTIVITY);
                    messageTo(self, "removeKillBonus", null, 1800, false);
                    if (!utils.hasScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR))
                    {
                        if (isIdValid(beast) && exists(beast) && !isDead(beast))
                        {
                            sendSystemMessage(player, beast_lib.SID_FAVORITE_ACTIVITY);
                        }
                        utils.setScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR, 1);
                    }
                }
                else if (dislikeActivity != null && dislikeActivity.length() > 0 && dislikeActivity.indexOf("killCreature") > -1)
                {
                    if (isIdValid(player) && hasObjVar(player, "qa_beast_hate"))
                    {
                        sendSystemMessageTestingOnly(player, "QA: Beast used to hate combat with creatures.  It would have its happiness lowered here.");
                    }
                }
            }
            if (ai_lib.isNpc(target))
            {
                if (favoriteActivity.indexOf("killNpc") > -1)
                {
                    beast_lib.performingActivity(self, beast_lib.PET_FAVORITE_ACTIVITY);
                    messageTo(self, "removeKillBonus", null, 1800, false);
                    if (!utils.hasScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR))
                    {
                        if (isIdValid(beast) && exists(beast) && !isDead(beast))
                        {
                            sendSystemMessage(player, beast_lib.SID_FAVORITE_ACTIVITY);
                        }
                        utils.setScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR, 1);
                    }
                }
                else if (dislikeActivity != null && dislikeActivity.length() > 0 && dislikeActivity.indexOf("killNpc") > -1)
                {
                    if (isIdValid(player) && hasObjVar(player, "qa_beast_hate"))
                    {
                        sendSystemMessageTestingOnly(player, "QA: Beast used to hate combat with npc's.  It would have its happiness lowered here.");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int removeKillBonus(obj_id self, dictionary params) throws InterruptedException
    {
        beast_lib.performingActivity(self, beast_lib.PET_NORMAL_ACTIVITY);
        if (utils.hasScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR))
        {
            utils.removeScriptVar(self, beast_lib.PET_KILL_MESSAGE_SCRIPTVAR);
        }
        return SCRIPT_CONTINUE;
    }
    public int petDoingTrick(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, beast_lib.PET_FAVORITES_OBJVAR))
        {
            LOG("beast_control_device", "Something is wrong. This BCD does not have the pets favorite activities stored. Setting them up now");
            beast_lib.setupHappinessLoyalty(self);
        }
        else 
        {
            obj_id player = beast_lib.getBCDPlayer(self);
            obj_id beast = beast_lib.getBCDBeastCalled(self);
            int[] beastHappiness = new int[beast_lib.LIKE_DISLIKE_MAX];
            beastHappiness = getIntArrayObjVar(self, beast_lib.PET_FAVORITES_OBJVAR);
            String favoriteActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[2] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            String dislikeActivity = dataTableGetString(beast_lib.DATATABLE_BEAST_FAVORITES, (beastHappiness[3] - 1), beast_lib.DATATABLE_ACTIVITY_COL);
            if (favoriteActivity.indexOf("trick") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_FAVORITE_ACTIVITY);
                messageTo(self, "removeTrickBonus", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_FAVORITE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
            else if (dislikeActivity.indexOf("trick") > -1)
            {
                beast_lib.performingActivity(self, beast_lib.PET_DISLIKE_ACTIVITY);
                messageTo(self, "removeTrickBonus", null, 1800, false);
                if (!utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
                {
                    if (isIdValid(beast) && exists(beast) && !isDead(beast))
                    {
                        sendSystemMessage(player, beast_lib.SID_DISLIKE_ACTIVITY);
                    }
                    utils.setScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR, 1);
                    messageTo(self, "removeActivityBlock", null, 600, false);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int removeTrickBonus(obj_id self, dictionary params) throws InterruptedException
    {
        beast_lib.performingActivity(self, beast_lib.PET_NORMAL_ACTIVITY);
        return SCRIPT_CONTINUE;
    }
    public int removeHungryBlock(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, beast_lib.PET_HUNGRY_MESSAGE_SCRIPTVAR))
        {
            utils.removeScriptVar(self, beast_lib.PET_HUNGRY_MESSAGE_SCRIPTVAR);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeActivityBlock(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR))
        {
            utils.removeScriptVar(self, beast_lib.PET_ACTIVITY_MESSAGE_SCRIPTVAR);
        }
        return SCRIPT_CONTINUE;
    }
}
