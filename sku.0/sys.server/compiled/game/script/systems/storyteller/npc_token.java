package script.systems.storyteller;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.player_structure;
import script.library.static_item;
import script.library.storyteller;
import script.library.sui;
import script.library.trial;
import script.library.utils;

public class npc_token extends script.base_script
{
    public npc_token()
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
        int menuPlacement = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("storyteller", "place_npc"));
        int numCharges = getCount(self);
        String placeMultiMenuLabel = "place_20_npcs";
        if (numCharges <= 20)
        {
            placeMultiMenuLabel = "place_all_npcs";
        }
        obj_id whatAmIStandingOn = getStandingOn(player);
        if (isIdValid(whatAmIStandingOn) && player_structure.isBuilding(whatAmIStandingOn))
        {
            mi.addRootMenu(menu_info_types.ITEM_USE_OTHER, new string_id("storyteller", "set_npc_level"));
        }
        else 
        {
            mi.addSubMenu(menuPlacement, menu_info_types.SERVER_MENU1, new string_id("storyteller", placeMultiMenuLabel));
            mi.addRootMenu(menu_info_types.ITEM_USE_OTHER, new string_id("storyteller", "set_npc_level"));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
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
        if (item == menu_info_types.ITEM_USE_OTHER)
        {
            String prompt = utils.packStringId(new string_id("storyteller", "npc_combat_level_prompt"));
            sui.inputbox(self, player, prompt, "handleStorytellerNpcLevelSelect");
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
        if (menuItem == menu_info_types.ITEM_USE)
        {
            if (storyteller.createNpcAtLocation(self, player, newLoc, yaw) == null)
            {
                sendSystemMessage(player, new string_id("storyteller", "failed_to_create_npc"));
            }
            else 
            {
                storyteller.handleTokenUsage(self);
            }
        }
        else if (menuItem == menu_info_types.SERVER_MENU1)
        {
            sendDirtyObjectMenuNotification(self);
            location startLoc = newLoc;
            location columnPosition = startLoc;
            int numToSpawn = getCount(self);
            if (numToSpawn > 20)
            {
                numToSpawn = 20;
            }
            int numSpawned = 1;
            int columnNum = 1;
            int columnSize = 3;
            if (numToSpawn == 1)
            {
                columnSize = 1;
            }
            else if (numToSpawn <= 8)
            {
                columnSize = numToSpawn / 2;
            }
            else if (numToSpawn > 8 && numToSpawn <= 15)
            {
                columnSize = numToSpawn / 3;
            }
            else if (numToSpawn > 15)
            {
                columnSize = numToSpawn / 4;
            }
            if (columnSize > 5)
            {
                columnSize = 5;
            }
            while (numSpawned <= numToSpawn && getCount(self) > 0)
            {
                location tempLoc = columnPosition;
                obj_id npc = storyteller.createNpcAtLocation(self, player, columnPosition, yaw);
                if (isGod(player))
                {
                    utils.setScriptVar(player, "storyteller.godModeStopOverrideMessages", true);
                }
                float spacing = 2.0f;
                dictionary itemData = dataTableGetRow(storyteller.STORYTELLER_DATATABLE, getStaticItemName(self));
                float tempSpacing = itemData.getFloat("placement_spacing");
                if (tempSpacing > 0)
                {
                    spacing = tempSpacing;
                }
                columnPosition.x = columnPosition.x + spacing;
                if (numSpawned >= columnSize && numSpawned % columnSize == 0)
                {
                    columnPosition.x = startLoc.x;
                    columnPosition.z = startLoc.z + (spacing * columnNum);
                    tempLoc = startLoc;
                    columnNum++;
                }
                if (yaw != 0)
                {
                    columnPosition = utils.rotatePointXZ(tempLoc, columnPosition, yaw);
                }
                if (!isIdValid(npc))
                {
                    sendSystemMessage(player, new string_id("storyteller", "failed_to_create_npc"));
                    break;
                }
                else 
                {
                    storyteller.handleTokenUsage(self);
                    numSpawned++;
                }
            }
            if (utils.hasScriptVar(player, "storyteller.godModeStopOverrideMessages"))
            {
                utils.removeScriptVar(player, "storyteller.godModeStopOverrideMessages");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleStorytellerNpcLevelSelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = sui.getPlayerId(params);
        String text = sui.getInputBoxText(params);
        int level = utils.stringToInt(text);
        if (level < 1 || level > 90)
        {
            sendSystemMessage(player, new string_id("storyteller", "npc_combat_level_invalid"));
        }
        else 
        {
            sendDirtyObjectMenuNotification(self);
            setObjVar(self, "storytellerNpcCombatLevel", level);
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
        if (hasObjVar(self, "storytellerNpcCombatLevel"))
        {
            int storytellerNpcLevel = getIntObjVar(self, "storytellerNpcCombatLevel");
            names[idx] = "storyteller_combat_level";
            attribs[idx] = "" + storytellerNpcLevel;
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
        if (storyteller.allowTokenPlacementOnRoofs(self))
        {
            names[idx] = "storyteller_allow_roof_deploy";
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
