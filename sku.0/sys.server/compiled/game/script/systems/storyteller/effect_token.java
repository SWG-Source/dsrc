package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.static_item;
import script.library.storyteller;
import script.library.create;
import script.library.trial;

public class effect_token extends script.base_script
{
    public effect_token()
    {
    }
    public static final String EFFECT_CONTROL_SCRIPT = "systems.storyteller.effect_controller";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        storyteller.resetTokenDailyCount(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        storyteller.resetTokenDailyCount(self);
        return SCRIPT_CONTINUE;
    }
    public int storytellerEffectTokenDailyAlarm(obj_id self, dictionary params) throws InterruptedException
    {
        storyteller.resetTokenDailyCount(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        string_id deployEffectPrompt = new string_id("storyteller", "deploy_effect");
        if (storyteller.isStaticEffect(self))
        {
            deployEffectPrompt = new string_id("storyteller", "deploy_static_effect");
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, deployEffectPrompt);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        obj_id effectTarget = getStorytellerEffectTarget(player);
        if (item == menu_info_types.ITEM_USE)
        {
            if (storyteller.isStaticEffect(self))
            {
                if (isIdValid(effectTarget))
                {
                    playEffectOnTarget(self, effectTarget, player);
                }
                else 
                {
                    sendSystemMessage(player, new string_id("storyteller", "persisted_effect_invalid_target"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGroundTargetLoc(obj_id self, obj_id player, int menuItem, float x, float y, float z) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        location playerLoc = getLocation(player);
        location newLoc = new location(x, y, z, playerLoc.area, playerLoc.cell);
        if (isIdValid(playerLoc.cell))
        {
            newLoc = playerLoc;
        }
        obj_id effectTarget = getStorytellerEffectTarget(player);
        if (menuItem == menu_info_types.ITEM_USE)
        {
            if (storyteller.isStaticEffect(self))
            {
                if (isIdValid(effectTarget))
                {
                    playEffectOnTarget(self, effectTarget, player);
                    return SCRIPT_CONTINUE;
                }
                sendSystemMessage(player, new string_id("storyteller", "persisted_effect_invalid_target"));
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (isIdValid(effectTarget))
                {
                    playEffectOnTarget(self, effectTarget, player);
                }
                else 
                {
                    playEffectAtLocation(self, player, newLoc);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getStorytellerEffectTarget(obj_id player) throws InterruptedException
    {
        obj_id target = null;
        obj_id intendedTarget = getIntendedTarget(player);
        if (isIdValid(intendedTarget))
        {
            target = intendedTarget;
        }
        else 
        {
            target = getLookAtTarget(player);
        }
        return target;
    }
    public boolean playEffectOnTarget(obj_id token, obj_id target, obj_id player) throws InterruptedException
    {
        if (isTargetMyStorytellerObject(player, target))
        {
            setObjVar(target, storyteller.EFFECT_TOKEN_NAME, getStaticItemName(token));
            if (hasScript(target, "systems.storyteller.effect_controller"))
            {
                messageTo(target, "handlePlayNewStorytellerEffect", null, 0, false);
            }
            else 
            {
                attachScript(target, "systems.storyteller.effect_controller");
            }
            storyteller.handleTokenUsage(token);
            String logMsg = "(" + player + ")" + getName(player) + " is deploying a static fx storyteller token: " + getStaticItemName(token) + " on " + target;
            CustomerServiceLog("storyteller", logMsg);
        }
        return true;
    }
    public boolean playEffectAtLocation(obj_id token, obj_id player, location here) throws InterruptedException
    {
        if (storyteller.canDeployStorytellerToken(player, here, token))
        {
            obj_id effectController = createObject("object/tangible/theme_park/invisible_object.iff", here);
            setObjVar(effectController, storyteller.EFFECT_TOKEN_NAME, getStaticItemName(token));
            if (utils.hasScriptVar(player, "storytellerAssistant"))
            {
                obj_id storytellerId = utils.getObjIdScriptVar(player, "storytellerAssistant");
                String storytellerName = utils.getStringScriptVar(player, "storytellerAssistantName");
                setObjVar(effectController, "storytellerid", storytellerId);
                setObjVar(effectController, "storytellerName", storytellerName);
            }
            else 
            {
                setObjVar(effectController, "storytellerid", player);
                setObjVar(effectController, "storytellerName", getName(player));
            }
            attachScript(effectController, "systems.storyteller.effect_controller");
            storyteller.handleTokenUsage(token);
            String logMsg = "(" + player + ")" + getName(player) + " is deploying a static fx storyteller token: " + getStaticItemName(token) + " at " + here;
            CustomerServiceLog("storyteller", logMsg);
            return true;
        }
        return false;
    }
    public boolean isTargetMyStorytellerObject(obj_id player, obj_id target) throws InterruptedException
    {
        if (storyteller.isAnyStorytellerItem(target))
        {
            if (hasObjVar(target, "storytellerid"))
            {
                obj_id storytellerId = getObjIdObjVar(target, "storytellerid");
                if (storytellerId == player || storytellerId == utils.getObjIdScriptVar(player, "storytellerAssistant"))
                {
                    return true;
                }
            }
        }
        sendSystemMessage(player, new string_id("storyteller", "persisted_effect_cannot_use"));
        return false;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (storyteller.isTokenFlaggedWithDailyUsage(self))
        {
            int dailyUsesLeft = storyteller.getTokenDailyUsageAmount(self);
            if (hasObjVar(self, storyteller.STORYTELLER_DAILY_COUNT_OBJVAR))
            {
                dailyUsesLeft = dailyUsesLeft - getIntObjVar(self, storyteller.STORYTELLER_DAILY_COUNT_OBJVAR);
            }
            names[idx] = "storyteller_daily_uses";
            attribs[idx] = "" + dailyUsesLeft;
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
