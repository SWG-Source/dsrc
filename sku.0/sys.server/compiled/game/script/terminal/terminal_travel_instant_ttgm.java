package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.pet_lib;
import script.library.prose;
import script.library.static_item;
import script.library.sui;
import script.library.township;
import script.library.utils;

public class terminal_travel_instant_ttgm extends script.base_script
{
    public terminal_travel_instant_ttgm()
    {
    }
    public static final String PID_VAR = "teleportToGroupMember.pid";
    public static final String GROUP_NAMES_LIST_VAR = "teleportToGroupMember.groupieNames";
    public static final String TRAVEL_LOC_VAR = "teleportToGroupMember.groupieLoc";
    public static final String TRAVEL_LOC_REGIONS_VAR = "teleportToGroupMember.groupieRegions";
    public static final String VAR_TREE_NAME = "teleportToGroupMember";
    public static final string_id SID_WHILE_DEAD = new string_id("spam", "while_dead");
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            sendSystemMessage(player, SID_WHILE_DEAD);
            return SCRIPT_CONTINUE;
        }
        obj_id tcg_itv = getSelf();
        mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("reward_sys", "vet_ttgm_use_menu"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (getState(player, STATE_RIDING_MOUNT) == 1)
        {
            pet_lib.doDismountNow(player, true);
        }
        if (item == menu_info_types.ITEM_USE)
        {
            showGroupMemberMenu(self, player);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary getMyItemData(obj_id self) throws InterruptedException
    {
        dictionary itemData = new dictionary();
        String itemName = getStaticItemName(self);
        if (itemName == null || itemName.equals(""))
        {
            CustomerServiceLog("vet_reward", "terminal_travel_instant_ttgm object self: " + self + " Name: " + getName(self) + " had an invalid static item name. Buff object is bailing out early as a result.");
            return null;
        }
        itemData = dataTableGetRow(static_item.ITEM_STAT_BALANCE_TABLE, itemName);
        if (itemData == null)
        {
            CustomerServiceLog("vet_reward", "terminal_travel_instant_ttgm object self: " + self + " Name: " + getStaticItemName(self) + " had invalid item data and as a result the buff object is bailing out early.");
            return null;
        }
        return itemData;
    }
    public void showGroupMemberMenu(obj_id self, obj_id player) throws InterruptedException
    {
        dictionary itemData = getMyItemData(self);
        if (itemData != null)
        {
            String coolDownGroup = itemData.getString("cool_down_group");
            int reuseTime = itemData.getInt("reuse_time");
            String varName = "clickItem." + coolDownGroup;
            int buffTime = getIntObjVar(player, varName);
            if (getGameTime() > buffTime || isGod(player))
            {
                if (getGameTime() < buffTime && isGod(player))
                {
                    sendSystemMessage(player, "You are allowed to override the cooldown because you are in god mode.", null);
                }
                closeOldWindow(self);
                obj_id groupId = getGroupObject(player);
                if (!isIdValid(groupId))
                {
                    sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_not_in_group"));
                }
                else 
                {
                    obj_id[] groupIds = getGroupMemberIds(groupId);
                    String[] fullGroupNames = getGroupMemberNames(groupId);
                    if (fullGroupNames != null && fullGroupNames.length > 0)
                    {
                        String playerName = getName(player);
                        Vector tempGroupieNames = new Vector();
                        tempGroupieNames.setSize(0);
                        for (int i = 0; i < fullGroupNames.length; i++)
                        {
                            String groupieName = fullGroupNames[i];
                            if (!groupieName.equals(playerName))
                            {
                                if (isPlayer(groupIds[i]))
                                {
                                    utils.addElement(tempGroupieNames, groupieName);
                                }
                            }
                        }
                        if (tempGroupieNames != null && tempGroupieNames.size() > 0)
                        {
                            String[] validGroupNames = utils.toStaticStringArray(tempGroupieNames);
                            String prompt = "@reward_sys:vet_ttgm_prompt";
                            String title = "@reward_sys:vet_ttgm_title";
                            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, validGroupNames, "teleportToGroupMemberChoiceHandler", false, false);
                            sui.showSUIPage(pid);
                            utils.setScriptVar(player, PID_VAR, pid);
                            utils.setScriptVar(player, GROUP_NAMES_LIST_VAR, validGroupNames);
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_no_players_in_group"));
                        }
                    }
                }
            }
            else 
            {
                int timeDiff = buffTime - getGameTime();
                prose_package pp = prose.getPackage(new string_id("reward_sys", "not_yet"), utils.formatTimeVerboseNoSpaces(timeDiff));
                sendSystemMessageProse(player, pp);
            }
        }
        return;
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVarTree(player, VAR_TREE_NAME))
        {
            int oldpid = utils.getIntScriptVar(player, PID_VAR);
            forceCloseSUIPage(oldpid);
            utils.removeScriptVarTree(player, VAR_TREE_NAME);
        }
    }
    public int teleportToGroupMemberChoiceHandler(obj_id self, dictionary params) throws InterruptedException
    {
        int button = sui.getIntButtonPressed(params);
        obj_id player = sui.getPlayerId(params);
        int choice = sui.getListboxSelectedRow(params);
        if (button == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            return SCRIPT_CONTINUE;
        }
        String[] groupieNames = utils.getStringArrayScriptVar(player, GROUP_NAMES_LIST_VAR);
        if (groupieNames != null && groupieNames.length > 0)
        {
            String groupieName = groupieNames[choice];
            if (groupieName != null && groupieName.length() > 0)
            {
                java.util.StringTokenizer token = new java.util.StringTokenizer(groupieName);
                if (token.countTokens() > 0)
                {
                    String groupieFirstName = (token.nextToken()).toLowerCase();
                    obj_id groupieTarget = getPlayerIdFromFirstName(groupieFirstName);
                    if (isIdValid(groupieTarget))
                    {
                        dictionary webster = new dictionary();
                        webster.put("terminal", self);
                        webster.put("requester", player);
                        messageTo(groupieTarget, "groupMemberLocationRequestHandler", webster, 0, false);
                    }
                    else 
                    {
                        sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_unable_to_process"));
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int groupMemberLocationResponseHandler(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id requester = params.getObjId("requester");
        location groupieLoc = params.getLocation("groupieLoc");
        if (isIdValid(requester) && groupieLoc != null)
        {
            String groupieName = params.getString("groupieName");
            String prompt = "@reward_sys:vet_ttgm_confirm_prompt \\#0099FF" + groupieName + "\\#. on\\#FFE21A @planet_n:" + groupieLoc.area + " \\#..";
            String title = "@reward_sys:vet_ttgm_confirm_title";
            sui.msgbox(self, requester, prompt, sui.OK_CANCEL, title, sui.MSG_QUESTION, "handleConfirmTravel");
            utils.setScriptVar(requester, TRAVEL_LOC_VAR, groupieLoc);
            if (params.containsKey("groupieRegions"))
            {
                String[] groupieRegions = params.getStringArray("groupieRegions");
                utils.setScriptVar(requester, TRAVEL_LOC_REGIONS_VAR, groupieRegions);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleConfirmTravel(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
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
        if (!terminalIsInRangeOfPlayer(self, player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_out_of_range"));
            return SCRIPT_CONTINUE;
        }
        if (utils.hasScriptVar(player, TRAVEL_LOC_VAR))
        {
            location travelLoc = utils.getLocationScriptVar(player, TRAVEL_LOC_VAR);
            utils.removeScriptVar(player, TRAVEL_LOC_VAR);
            if (travelLoc != null)
            {
                String[] groupieRegions = new String[0];
                if (utils.hasScriptVar(player, TRAVEL_LOC_REGIONS_VAR))
                {
                    groupieRegions = utils.getStringArrayScriptVar(player, TRAVEL_LOC_REGIONS_VAR);
                }
                if (!isValidTravelLocation(player, travelLoc, groupieRegions))
                {
                    return SCRIPT_CONTINUE;
                }
                dictionary itemData = getMyItemData(self);
                if (itemData != null)
                {
                    String coolDownGroup = itemData.getString("cool_down_group");
                    int reuseTime = itemData.getInt("reuse_time");
                    String varName = "clickItem." + coolDownGroup;
                    String destPlanet = travelLoc.area;
                    warpPlayer(player, destPlanet, travelLoc.x, travelLoc.y, travelLoc.z, null, 0, 0, 0, "", false);
                    setObjVar(player, varName, getGameTime() + reuseTime);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isValidTravelLocation(obj_id player, location travelLoc, String[] groupieRegions) throws InterruptedException
    {
        String planet = travelLoc.area;
        if (planet.startsWith("space_"))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_not_to_space"));
            return false;
        }
        if (planet.startsWith("kashyyyk"))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_not_to_kashyyyk"));
            return false;
        }
        if (planet.startsWith("mustafar"))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_not_to_mustafar"));
            return false;
        }
        if (planet.equals("adventure1") || planet.equals("adventure2") || planet.equals("dungeon1"))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_invalid_scene"));
            return false;
        }
        if (isIdValid(travelLoc.cell))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_inside_building"));
            return false;
        }
        if (combat.isInCombat(player))
        {
            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_while_in_combat"));
            return false;
        }
        if (groupieRegions != null && groupieRegions.length > 0)
        {
            String[] restrictedRegions = dataTableGetStringColumn("datatables/item/instant_travel_restricted_regions.iff", "region_name");
            for (int k = 0; k < groupieRegions.length; k++)
            {
                String regionTheyAreIn = groupieRegions[k];
                for (int q = 0; q < restrictedRegions.length; q++)
                {
                    String restrictedRegion = restrictedRegions[q];
                    if (regionTheyAreIn.startsWith(restrictedRegion))
                    {
                        sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_invalid_scene"));
                        return false;
                    }
                    else if (regionTheyAreIn.equals("dathomir_fs_village_unpassable"))
                    {
                        if (!township.isTownshipEligible(player))
                        {
                            sendSystemMessage(player, new string_id("reward_sys", "vet_ttgm_ineligible_aurilia"));
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public boolean isOwner(obj_id object, obj_id player) throws InterruptedException
    {
        return getOwner(object) == player;
    }
    public boolean terminalIsInRangeOfPlayer(obj_id terminal, obj_id player) throws InterruptedException
    {
        if (!isIdValid(terminal) || !isIdValid(player))
        {
            return false;
        }
        if (!utils.isNestedWithin(terminal, player))
        {
            if (utils.getDistance2D(terminal, player) > 40)
            {
                return false;
            }
        }
        return true;
    }
}
