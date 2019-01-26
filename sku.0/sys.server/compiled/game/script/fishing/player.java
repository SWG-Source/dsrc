package script.fishing;

import script.*;
import script.library.minigame;
import script.library.prose;
import script.library.sui;
import script.library.utils;

public class player extends script.base_script
{
    public player()
    {
    }
    private static final String STF_FISH = "fishing";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        minigame.stopFishing(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        minigame.cleanupFishing(self);
        return SCRIPT_CONTINUE;
    }
    public int OnEnteredCombat(obj_id self) throws InterruptedException
    {
        minigame.stopFishing(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnEnterSwimming(obj_id self) throws InterruptedException
    {
        minigame.stopFishing(self, false);
        return SCRIPT_CONTINUE;
    }
    public int OnChangedPosture(obj_id self, int before, int after) throws InterruptedException
    {
        switch (after)
        {
            case POSTURE_UPRIGHT:
                break;
            default:
                minigame.stopFishing(self, false);
                break;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        if (minigame.isFishing(self))
        {
            obj_id pole = getObjectInSlot(self, "hold_r");
            if (!isIdValid(pole) || !minigame.isFishingPole(pole) || (item == pole))
            {
                minigame.stopFishing(self, false);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFishingSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        if (!minigame.isFishing(self))
        {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            minigame.showFishingSui(self);
            return SCRIPT_CONTINUE;
        }
        int action = idx + 1;
        if (action == minigame.FA_STOP_FISHING)
        {
            minigame.stopFishing(self);
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, minigame.SCRIPTVAR_CAUGHT))
        {
            utils.setScriptVar(self, minigame.SCRIPTVAR_ACTION, action);
            minigame.showFishingSui(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleFishingTick(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("fishing", "** handleFishingTick: entered @ " + getGameTime() + " **");
        int action = utils.getIntScriptVar(self, minigame.SCRIPTVAR_ACTION);
        utils.removeScriptVar(self, minigame.SCRIPTVAR_ACTION);
        LOG("fishing", "handleFishingTick: action = " + action);
        if (action == minigame.FA_STOP_FISHING)
        {
            minigame.stopFishing(self);
            return SCRIPT_CONTINUE;
        }
        boolean hasBait = true;
        if (minigame.getBaitStatus(self) == minigame.BS_NONE)
        {
            hasBait = false;
        }
        LOG("fishing", "handleFishingTick: hasBait = " + hasBait);
        obj_id marker = utils.getObjIdScriptVar(self, minigame.SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            minigame.stopFishing(self);
            return SCRIPT_CONTINUE;
        }
        float dist = getDistance(getLocation(self), getLocation(marker));
        if (dist < 2.0f)
        {
            sendSystemMessage(self, new string_id(STF_FISH, "close_reel_in"));
            minigame.stopFishing(self);
            return SCRIPT_CONTINUE;
        }
        else if (dist > 45)
        {
            sendSystemMessage(self, new string_id(STF_FISH, "far_reel_in"));
            minigame.stopFishing(self);
            return SCRIPT_CONTINUE;
        }
        int status = utils.getIntScriptVar(self, minigame.SCRIPTVAR_STATUS);
        LOG("fishing", "handleFishingTick: status = " + status);
        switch (status)
        {
            case minigame.FS_CAST:
                minigame.setFishingState(self, minigame.FS_WAIT);
                break;
            case minigame.FS_WAIT:
                minigame.defaultFishingUpdate(self, action);
                if (action != minigame.FA_SMALL_REEL && hasBait)
                {
                    minigame.checkForNibble(self);
                }
                break;
            case minigame.FS_NIBBLE:
                int[] biteActions = minigame.getGoodActions();
                if ((biteActions == null) || (biteActions.length == 0))
                {
                    break;
                }
                if (utils.getElementPositionInArray(biteActions, action) > -1)
                {
                    if (minigame.checkForBite(self, 0.1f))
                    {
                        break;
                    }
                }
                else
                {
                    if (rand(0, 100) < 10)
                    {
                        sendSystemMessage(self, new string_id(STF_FISH, "strong_nibble"));
                        if (!isGod(self))
                        {
                            minigame.lostBait(self);
                        }
                        else
                        {
                            debugSpeakMsg(self, "bypassing -minigame.lostBait- due to God Mode!");
                            break;
                        }
                        return SCRIPT_CONTINUE;
                    }
                }
                minigame.defaultFishingUpdate(self, action);
                break;
            case minigame.FS_BITE:
                int[] catchActions = minigame.getGoodActions();
                if ((catchActions == null) || (catchActions.length == 0))
                {
                    break;
                }
                if (utils.getElementPositionInArray(catchActions, action) > -1)
                {
                    if (minigame.checkForCatch(self))
                    {
                        break;
                    }
                }
                else
                {
                    if (rand(0, 100) < 10)
                    {
                        sendSystemMessage(self, new string_id(STF_FISH, "tore_bait"));
                        if (!isGod(self))
                        {
                            minigame.lostBait(self);
                        }
                        else
                        {
                            debugSpeakMsg(self, "bypassing -minigame.lostBait- due to God Mode!");
                            break;
                        }
                        return SCRIPT_CONTINUE;
                    }
                }
                minigame.defaultFishingUpdate(self, action);
                break;
            case minigame.FS_SNAG:
                float snagFactor = utils.getFloatScriptVar(self, minigame.SCRIPTVAR_BONUS);
                float minFree = 0.0f;
                switch (action)
                {
                    case minigame.FA_TUG_UP:
                    case minigame.FA_TUG_RIGHT:
                    case minigame.FA_TUG_LEFT:
                        minFree = 0.2f;
                        break;
                    case minigame.FA_SMALL_REEL:
                        minFree = 0.4f;
                        break;
                }
                float delta = rand(minFree, 1.0f) - snagFactor;
                if (delta > 0)
                {
                    minigame.freeSnaggedLine(self);
                }
                else
                {
                    float snapRoll = rand(0.0f, 1.0f);
                    if (snapRoll < minFree)
                    {
                        minigame.snapFishingLine(self);
                        return SCRIPT_CONTINUE;
                    }
                    if (snapRoll > 0.95f)
                    {
                        minigame.caughtLoot(self);
                    }
                }
                break;
            case minigame.FS_CAUGHT:
            case minigame.FS_LOOT:
                LOG("fishing", "handleFishingTick: caught something -> status = " + status);
                location castLoc = params.getLocation("castLoc");
                if (castLoc != null)
                {
                    LOG("fishing", "handleFishingTick: castLoc = " + castLoc.toString());
                }
                else
                {
                    LOG("fishing", "handleFishingTick: unable to retrieve castLoc from params...");
                    castLoc = utils.getLocationScriptVar(self, minigame.SCRIPTVAR_LOCATION);
                }
                if (castLoc == null)
                {
                    minigame.loseCatch(self);
                    return SCRIPT_CONTINUE;
                }
                minigame.confirmReelIn(self, castLoc);
                minigame.closeFishingSui(self);
                return SCRIPT_CONTINUE;
            case minigame.FS_NONE:
            default:
                return SCRIPT_CONTINUE;
        }
        LOG("fishing", "handleFishingTick: updating bait status...");
        minigame.updateBaitStatus(self);
        LOG("fishing", "handleFishingTick: showing fishing sui...");
        minigame.showFishingSui(self);
        LOG("fishing", "handleFishingTick: remessaging HANDLER_FISHING_TICK...");
        messageTo(self, minigame.HANDLER_FISHING_TICK, params, minigame.FISHING_TICK, false);
        return SCRIPT_CONTINUE;
    }
    public int handlePlayCastSplash(obj_id self, dictionary params) throws InterruptedException
    {
        minigame.playCastSplash(self, params);
        messageTo(self, minigame.HANDLER_FISHING_TICK, params, minigame.FISHING_TICK, false);
        return SCRIPT_CONTINUE;
    }
    public int handleReelIn(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("fishing", "** handleReelIn: entered @ " + getGameTime() + " **");
        if (!minigame.isFishing(self))
        {
            return SCRIPT_CONTINUE;
        }
        if ((params == null) || (params.isEmpty()))
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        obj_id marker = utils.getObjIdScriptVar(self, minigame.SCRIPTVAR_MARKER);
        if (!isIdValid(marker))
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(marker);
        if (loc == null)
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        float delay = rand(3.0f, 10.0f);
        location here = getLocation(self);
        float dist = getDistance(here, loc);
        LOG("fishing", "handleReelIn: dist = " + dist);
        if (dist < 2.0f)
        {
            LOG("fishing", "handleReelIn: messaging HANDLER_CAUGHT_SOMETHING");
            messageTo(self, minigame.HANDLER_CAUGHT_SOMETHING, params, delay, false);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (rand(0, 100) > 95)
            {
                LOG("fishing", "handleReelIn: lost catch on rand 5% chance..");
                if (!isGod(self))
                {
                    minigame.loseCatch(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    debugSpeakMsg(self, "Bypassing -minigame.loseCatch- due to God Mode!");
                }
            }
            int status = utils.getIntScriptVar(self, minigame.SCRIPTVAR_STATUS);
            if (status == minigame.FS_CAUGHT)
            {
                float theta = getYaw(marker);
                float dTheta = rand(-45.0f, 45.0f);
                if (rand(0, 9) > 7)
                {
                    switch (rand(1, 3))
                    {
                        case 1:
                            doAnimationAction(self, "fishing_tug_back");
                            break;
                        case 2:
                            doAnimationAction(self, "fishing_tug_right");
                            break;
                        case 3:
                            doAnimationAction(self, "fishing_tug_left");
                            break;
                    }
                    dTheta += 180.0f;
                }
                else 
                {
                    doAnimationAction(self, "fishing_reel");
                }
                LOG("fishing", "handleReelIn: theta = " + theta + " dTheta = " + dTheta);
                float moveDist = rand(0.0f, 2.0f);
                LOG("fishing", "handleReelIn: moveDist = " + moveDist);
                float uTheta = theta + dTheta;
                LOG("fishing", "handleReelIn: uTheta = " + uTheta);
                if (uTheta < -180.0f)
                {
                    uTheta += 360.0f;
                }
                if (uTheta > 180.0f)
                {
                    uTheta -= 360.0f;
                }
                location updatedLoc = utils.rotatePointXZ(loc, moveDist, uTheta);
                LOG("fishing", "handleReelIn: updatedLoc = " + updatedLoc.toString());
                if (!minigame.isLocationFishable(updatedLoc))
                {
                    updatedLoc = loc;
                }
                location markerLoc = (location)updatedLoc.clone();
                markerLoc.y = getWaterTableHeight(markerLoc);
                setLocation(marker, markerLoc);
                faceTo(marker, self);
                float updatedRange = getDistance(here, updatedLoc);
                LOG("fishing", "handleReelIn: updatedRange = " + updatedRange);
                if (updatedRange > minigame.SPOOL_RANGE)
                {
                    LOG("fishing", "handleReelIn: line spooled...");
                    minigame.spoolFishingLine(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    if (Math.abs(dTheta) > 90.0f)
                    {
                        if (moveDist < 0.25f)
                        {
                            sendSystemMessage(self, minigame.SID_FISH_FIGHT_HARD);
                        }
                        else if (moveDist > 1.0f)
                        {
                            sendSystemMessage(self, minigame.SID_FISH_RUN);
                        }
                        else 
                        {
                            sendSystemMessage(self, minigame.SID_FISH_FIGHT_AWAY);
                        }
                    }
                    else 
                    {
                        if (moveDist < 0.25f)
                        {
                            sendSystemMessage(self, minigame.SID_FISH_FIGHT_EASY);
                        }
                        else if (moveDist > 1.0f)
                        {
                            sendSystemMessage(self, minigame.SID_FISH_CHARGE);
                        }
                        else 
                        {
                            sendSystemMessage(self, minigame.SID_FISH_FIGHT_CLOSER);
                        }
                    }
                }
            }
            else if (status == minigame.FS_LOOT)
            {
                location updatedLoc = getLocationBetweenLocs(here, loc);
                if (updatedLoc == null)
                {
                    sendSystemMessage(self, minigame.SID_LOST_CATCH);
                    return SCRIPT_CONTINUE;
                }
                else if (!minigame.isLocationFishable(updatedLoc))
                {
                    sendSystemMessage(self, minigame.SID_LOOT_BEACHED);
                    messageTo(self, minigame.HANDLER_CAUGHT_SOMETHING, params, 1.0f, false);
                    return SCRIPT_CONTINUE;
                }
                updatedLoc.y = getWaterTableHeight(updatedLoc);
                setLocation(marker, updatedLoc);
                faceTo(marker, self);
                doAnimationAction(self, "fishing_reel");
                sendSystemMessage(self, minigame.SID_REEL_LOOT);
            }
        }
        LOG("fishing", "handleReelIn: messaging HANDLER_REEL_IN again in " + delay + " seconds");
        messageTo(self, minigame.HANDLER_REEL_IN, params, delay, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCaughtSomething(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("fishing", "** handleCaughtSomething: entered...");
        if (!minigame.isFishing(self))
        {
            return SCRIPT_CONTINUE;
        }
        if ((params == null) || (params.isEmpty()))
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        int status = utils.getIntScriptVar(self, minigame.SCRIPTVAR_STATUS);
        if (status != minigame.FS_CAUGHT && status != minigame.FS_LOOT)
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        location castLoc = params.getLocation("castLoc");
        if (castLoc == null)
        {
            minigame.loseCatch(self);
            return SCRIPT_CONTINUE;
        }
        obj_id myCatch = null;
        if (status == minigame.FS_CAUGHT)
        {
            myCatch = minigame.spawnFishingFish(self, castLoc);
        }
        else {
            obj_id[] loot = minigame.spawnFishingLoot(self, castLoc);
            if (loot != null && loot.length > 0)
            {
                myCatch = loot[0];
            }
        }
        if (isIdValid(myCatch))
        {
            prose_package ppCatch = prose.getPackage(minigame.PROSE_NOTIFY_CATCH, myCatch);
            sendSystemMessageProse(self, ppCatch);
        }
        minigame.stopFishing(self);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanupSplash(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id splash = params.getObjId("id");
        if (isIdValid(splash))
        {
            destroyObject(splash);
        }
        return SCRIPT_CONTINUE;
    }
}
