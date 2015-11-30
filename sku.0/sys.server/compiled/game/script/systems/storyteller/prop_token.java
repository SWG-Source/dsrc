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

public class prop_token extends script.base_script
{
    public prop_token()
    {
    }
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
        if (getState(player, STATE_SWIMMING) == 1)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "deploy_prop"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGroundTargetLoc(obj_id self, obj_id player, int menuItem, float x, float y, float z) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_from_inventory_only"));
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_SWIMMING) == 1)
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_not_while_swimming"));
            return SCRIPT_CONTINUE;
        }
        if (isFreeTrialAccount(player))
        {
            sendSystemMessage(player, new string_id("storyteller", "placement_no_trial_accounts"));
            return SCRIPT_CONTINUE;
        }
        float yaw = getYaw(player);
        location playerLoc = getLocation(player);
        location newLoc = new location(x, y, z, playerLoc.area, playerLoc.cell);
        if (isIdValid(playerLoc.cell))
        {
            newLoc = playerLoc;
        }
        if (storyteller.createPropObject(self, player, false, newLoc, yaw) == null)
        {
            sendSystemMessage(player, new string_id("storyteller", "failed_to_place"));
        }
        return SCRIPT_CONTINUE;
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
        if (storyteller.allowTokenPlacementInInteriors(self))
        {
            names[idx] = "storyteller_allow_interior_deploy";
            attribs[idx] = "True";
            idx++;
            if (idx >= names.length)
            {
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
