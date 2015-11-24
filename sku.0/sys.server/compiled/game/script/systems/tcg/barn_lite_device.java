package script.systems.tcg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.beast_lib;
import script.library.callable;
import script.library.groundquests;
import script.library.prose;
import script.library.space_utils;
import script.library.spawning;
import script.library.structure;
import script.library.sui;
import script.library.tcg;
import script.library.utils;

public class barn_lite_device extends script.base_script
{
    public barn_lite_device()
    {
    }
    public static final int MENU_ROOT_OPTION = menu_info_types.SERVER_MENU1;
    public static final int MENU_STORE_BEAST_OPTION = menu_info_types.SERVER_MENU2;
    public static final int MENU_RECLAIM_BEAST_OPTION = menu_info_types.SERVER_MENU3;
    public static final int MENU_DISPLAY_BEAST_OPTION = menu_info_types.SERVER_MENU4;
    public static final int MENU_REBOOT_DEVICE_OPTION = menu_info_types.SERVER_MENU5;
    public static final int MENU_TESTING_DEACTIVATE_OPTION = menu_info_types.SERVER_MENU6;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupBarnLiteDevice", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setupBarnLiteDevice", null, 2, false);
        messageTo(self, "showRoamingBeastsSoon", null, 5, false);
        return SCRIPT_CONTINUE;
    }
    public int setupBarnLiteDevice(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "barnLite.isBarnLite"))
        {
            setObjVar(self, "barnLite.isBarnLite", true);
        }
        if (!utils.isNestedWithinAPlayer(self))
        {
            location here = getLocation(self);
            if (getContainedBy(self) == here.cell)
            {
                obj_id building = getTopMostContainer(self);
                if (isIdValid(building))
                {
                    if (isGameObjectTypeOf(building, GOT_building_player) || (isGameObjectTypeOf(building, GOT_ship_fighter) && space_utils.isShipWithInterior(building)))
                    {
                        utils.setScriptVar(building, "barnLite.hasBarnLiteDevice", self);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnUnPack(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "setupBarnLiteDevice", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnShipUnpack(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "setupBarnLiteDevice", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        int max_beasts = tcg.getMaxBarnLiteBeasts(self);
        names[idx] = "max_beasts";
        attribs[idx] = "" + max_beasts;
        idx++;
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id building = getTopMostContainer(self);
        if (utils.isNestedWithinAPlayer(destContainer))
        {
            if (isIdValid(building))
            {
                if (tcg.getTotalBarnStoredBeasts(self) > 0)
                {
                    if (isIdValid(transferer))
                    {
                        sendSystemMessage(transferer, new string_id("tcg", "barn_lite_must_reclaim_before_taking"));
                    }
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    if (utils.hasScriptVar(building, "barnLite.hasBarnLiteDevice"))
                    {
                        obj_id buildingBarnLiteDevice = utils.getObjIdScriptVar(building, "barnLite.hasBarnLiteDevice");
                        if (isIdValid(buildingBarnLiteDevice) && buildingBarnLiteDevice == self)
                        {
                            utils.removeScriptVar(building, "barnLite.hasBarnLiteDevice");
                        }
                    }
                }
            }
        }
        else if ((getTemplateName(destContainer)).equals(structure.TEMPLATE_CELL))
        {
            if (!isIdValid(building))
            {
                if (isIdValid(transferer))
                {
                    sendSystemMessage(transferer, new string_id("tcg", "barn_lite_invalid_building"));
                }
                return SCRIPT_OVERRIDE;
            }
            if (utils.hasScriptVar(building, "barnLite.hasBarnLiteDevice"))
            {
                obj_id buildingBarnLiteDevice = utils.getObjIdScriptVar(building, "barnLite.hasBarnLiteDevice");
                if (isIdValid(buildingBarnLiteDevice) && buildingBarnLiteDevice != self)
                {
                    if (isIdValid(transferer))
                    {
                        sendSystemMessage(transferer, new string_id("tcg", "barn_lite_already_one_in_building"));
                    }
                    return SCRIPT_OVERRIDE;
                }
            }
            else 
            {
                utils.setScriptVar(building, "barnLite.hasBarnLiteDevice", self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int totalStoredBeasts = tcg.getTotalBarnStoredBeasts(self);
        if (canUseActiveBarnLite(self, player))
        {
            int rootMenu = mi.addRootMenu(MENU_ROOT_OPTION, new string_id("tcg", "barn_lite_menu_root"));
            if (beast_lib.getTotalBeastControlDevices(player) > 0)
            {
                mi.addSubMenu(rootMenu, MENU_STORE_BEAST_OPTION, new string_id("tcg", "barn_lite_menu_store"));
            }
            if (totalStoredBeasts > 0)
            {
                mi.addSubMenu(rootMenu, MENU_RECLAIM_BEAST_OPTION, new string_id("tcg", "barn_lite_menu_reclaim"));
                mi.addSubMenu(rootMenu, MENU_DISPLAY_BEAST_OPTION, new string_id("tcg", "barn_lite_menu_display"));
            }
            if (isGod(player) && hasObjVar(player, "barnLite_testing"))
            {
                mi.addSubMenu(rootMenu, MENU_TESTING_DEACTIVATE_OPTION, new string_id("tcg", "barn_lite_menu_testing_deactivate"));
            }
        }
        else if (canUseInActiveBarnLite(self, player))
        {
            int rootMenu = mi.addRootMenu(MENU_ROOT_OPTION, new string_id("tcg", "barn_lite_menu_root"));
            mi.addSubMenu(rootMenu, MENU_REBOOT_DEVICE_OPTION, new string_id("tcg", "barn_lite_menu_activate"));
            if (totalStoredBeasts > 0)
            {
                mi.addSubMenu(rootMenu, MENU_RECLAIM_BEAST_OPTION, new string_id("tcg", "barn_lite_menu_reclaim"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        obj_id biolink = getBioLink(self);
        if (isValidId(biolink) && biolink == utils.OBJ_ID_BIO_LINK_PENDING)
        {
            sendSystemMessage(player, new string_id("base_player", "must_biolink_to_use_from_inventory"));
            return SCRIPT_CONTINUE;
        }
        if (isValidId(biolink) && biolink != player)
        {
            sendSystemMessage(player, new string_id("base_player", "wrong_player_per_biolink"));
            return SCRIPT_CONTINUE;
        }
        if (canUseActiveBarnLite(self, player))
        {
            if (item == MENU_ROOT_OPTION)
            {
            }
            else if (item == MENU_STORE_BEAST_OPTION)
            {
                if (tcg.getTotalBarnStoredBeasts(self) >= tcg.getMaxBarnLiteBeasts(self))
                {
                    sendSystemMessage(player, new string_id("tcg", "barn_lite_device_full"));
                }
                else 
                {
                    tcg.barnStoreBeastPrompt(player, self);
                }
            }
            else if (item == MENU_RECLAIM_BEAST_OPTION)
            {
                if (tcg.getTotalBarnStoredBeasts(self) > 0 && callable.hasMaxStoredCombatPets(player))
                {
                    sendSystemMessage(player, new string_id("tcg", "barn_lite_player_full"));
                }
                else 
                {
                    tcg.barnReclaimBeastPrompt(player, self, self);
                }
            }
            else if (item == MENU_DISPLAY_BEAST_OPTION)
            {
                if (tcg.getTotalBarnStoredBeasts(self) > 0)
                {
                    tcg.barnDisplayBeastPrompt(player, self, self);
                }
            }
            else if (item == MENU_TESTING_DEACTIVATE_OPTION)
            {
                if (isGod(player) && hasObjVar(player, "barnLite_testing"))
                {
                    obj_id building = getTopMostContainer(self);
                    if (isIdValid(building))
                    {
                        utils.removeScriptVar(building, "barnLite.hasBarnLiteDevice");
                        sendSystemMessage(player, "TESTING OPTION: Beast Storage Device has been deactivated.", "");
                        sendDirtyObjectMenuNotification(self);
                    }
                }
            }
        }
        else if (canUseInActiveBarnLite(self, player))
        {
            if (item == MENU_RECLAIM_BEAST_OPTION)
            {
                if (tcg.getTotalBarnStoredBeasts(self) > 0 && callable.hasMaxStoredCombatPets(player))
                {
                    sendSystemMessage(player, new string_id("tcg", "barn_lite_player_full"));
                }
                else 
                {
                    tcg.barnReclaimBeastPrompt(player, self, self);
                }
            }
            else if (item == MENU_REBOOT_DEVICE_OPTION)
            {
                if (!utils.isNestedWithinAPlayer(self))
                {
                    location here = getLocation(self);
                    if (getContainedBy(self) == here.cell)
                    {
                        obj_id building = getTopMostContainer(self);
                        if (isIdValid(building))
                        {
                            if (isGameObjectTypeOf(building, GOT_building_player) || (isGameObjectTypeOf(building, GOT_ship_fighter) && space_utils.isShipWithInterior(building)))
                            {
                                if (!utils.hasScriptVar(building, "barnLite.hasBarnLiteDevice"))
                                {
                                    utils.setScriptVar(building, "barnLite.hasBarnLiteDevice", self);
                                    sendSystemMessage(player, new string_id("tcg", "barn_lite_actived"));
                                    sendDirtyObjectMenuNotification(self);
                                }
                                else 
                                {
                                    obj_id activeBarnLite = utils.getObjIdScriptVar(building, "barnLite.hasBarnLiteDevice");
                                    if (!isIdValid(activeBarnLite))
                                    {
                                        utils.setScriptVar(building, "barnLite.hasBarnLiteDevice", self);
                                        sendSystemMessage(player, new string_id("tcg", "barn_lite_actived"));
                                        sendDirtyObjectMenuNotification(self);
                                    }
                                    else if (activeBarnLite == self)
                                    {
                                        sendSystemMessage(player, new string_id("tcg", "barn_lite_already_active"));
                                    }
                                    else 
                                    {
                                        sendSystemMessage(player, new string_id("tcg", "barn_lite_already_one_in_building"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean canUseInActiveBarnLite(obj_id self, obj_id player) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            obj_id building = getTopMostContainer(self);
            if (isIdValid(building))
            {
                if (isGameObjectTypeOf(building, GOT_building_player) || (isGameObjectTypeOf(building, GOT_ship_fighter) && space_utils.isShipWithInterior(building)))
                {
                    location here = getLocation(self);
                    if (getContainedBy(self) == here.cell)
                    {
                        obj_id buildingOwner = getOwner(building);
                        if (isIdValid(buildingOwner) && buildingOwner == player)
                        {
                            obj_id myOwner = getOwner(self);
                            if (isIdValid(myOwner) && myOwner == player)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean canUseActiveBarnLite(obj_id self, obj_id player) throws InterruptedException
    {
        if (canUseInActiveBarnLite(self, player))
        {
            obj_id building = getTopMostContainer(self);
            if (isIdValid(building))
            {
                if (utils.hasScriptVar(building, "barnLite.hasBarnLiteDevice"))
                {
                    obj_id currentBarnLiteDevice = utils.getObjIdScriptVar(building, "barnLite.hasBarnLiteDevice");
                    if (currentBarnLiteDevice == self)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int handleBarnBeastStorage(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player))
        {
            location myLoc = getLocation(self);
            location playerLoc = getLocation(player);
            if (myLoc.cell != playerLoc.cell)
            {
                sendSystemMessage(player, new string_id("tcg", "barn_lite_not_in_same_cell"));
            }
            else 
            {
                int button = sui.getIntButtonPressed(params);
                if (button == sui.BP_CANCEL)
                {
                }
                else 
                {
                    int beast_selected = sui.getListboxSelectedRow(params);
                    if (beast_selected > -1)
                    {
                        obj_id[] beastControlDevices = utils.getObjIdArrayScriptVar(player, "barnRanchHand.handleBarnBeastStorage");
                        if (beastControlDevices != null && beastControlDevices.length > 0)
                        {
                            if (beast_selected < beastControlDevices.length)
                            {
                                obj_id selectedBeastControlDevice = beastControlDevices[beast_selected];
                                if (isIdValid(selectedBeastControlDevice))
                                {
                                    string_id message = new string_id("tcg", "barn_lite_beast_storage_failed");
                                    String petName = beast_lib.getBCDBeastName(selectedBeastControlDevice);
                                    if (tcg.transferBeastControlDeviceToBarn(selectedBeastControlDevice, player, self))
                                    {
                                        message = new string_id("tcg", "barn_lite_beast_storage_success");
                                        CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " successfully stored beast control device in a beast storage device: " + self + " located at: " + getLocation(self) + ".");
                                        sendDirtyObjectMenuNotification(self);
                                    }
                                    prose_package pp = prose.getPackage(message, player, player);
                                    prose.setTO(pp, petName);
                                    sendSystemMessageProse(player, pp);
                                    if (beast_lib.getTotalBeastControlDevices(player) > 0 && tcg.getTotalBarnStoredBeasts(self) <= tcg.getMaxBarnLiteBeasts(self))
                                    {
                                        tcg.barnStoreBeastPrompt(player, self);
                                        return SCRIPT_CONTINUE;
                                    }
                                }
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("tcg", "barn_beast_transfer_failed_invalid"));
                        }
                    }
                }
            }
            if (utils.hasScriptVar(player, "barnRanchHand.handleBarnBeastStorage"))
            {
                utils.removeScriptVar(player, "barnRanchHand.handleBarnBeastStorage");
            }
            if (utils.hasScriptVar(player, "barnRanchHand.pid"))
            {
                utils.removeScriptVar(player, "barnRanchHand.pid");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBarnBeastReclaim(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player))
        {
            location myLoc = getLocation(self);
            location playerLoc = getLocation(player);
            if (myLoc.cell != playerLoc.cell)
            {
                sendSystemMessage(player, new string_id("tcg", "barn_lite_not_in_same_cell"));
            }
            else 
            {
                int button = sui.getIntButtonPressed(params);
                if (button == sui.BP_CANCEL)
                {
                }
                else 
                {
                    int beast_selected = sui.getListboxSelectedRow(params);
                    if (beast_selected > -1)
                    {
                        String[] beastStorageSlots = utils.getStringArrayScriptVar(player, "barnRanchHand.handleBarnBeastReclaim");
                        if (beastStorageSlots != null && beastStorageSlots.length > 0)
                        {
                            if (beast_selected < beastStorageSlots.length)
                            {
                                String selectedBeastStorageSlot = beastStorageSlots[beast_selected];
                                if (selectedBeastStorageSlot != null && selectedBeastStorageSlot.length() > 0)
                                {
                                    string_id message = new string_id("tcg", "barn_beast_reclaimed_failed");
                                    String petName = "Unknown";
                                    if (hasObjVar(self, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName"))
                                    {
                                        petName = getStringObjVar(self, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName");
                                    }
                                    else if (hasObjVar(self, "barnStorage." + selectedBeastStorageSlot + ".tempName"))
                                    {
                                        petName = getStringObjVar(self, "barnStorage." + selectedBeastStorageSlot + ".tempName");
                                    }
                                    obj_id transferedBCD = tcg.transferBeastControlDeviceFromBarn(selectedBeastStorageSlot, player, self);
                                    if (isIdValid(transferedBCD))
                                    {
                                        message = new string_id("tcg", "barn_beast_reclaimed_to_you");
                                        petName = beast_lib.getBCDBeastName(transferedBCD);
                                        CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " successfully claimed beast control device: " + transferedBCD + " from beast storage device: " + self + " located at: " + getLocation(self) + ".");
                                        sendDirtyObjectMenuNotification(self);
                                    }
                                    prose_package pp = prose.getPackage(message, player, player);
                                    prose.setTO(pp, petName);
                                    sendSystemMessageProse(player, pp);
                                    if (tcg.getTotalBarnStoredBeasts(self) > 0 && !callable.hasMaxStoredCombatPets(player))
                                    {
                                        tcg.barnReclaimBeastPrompt(player, self, self);
                                        return SCRIPT_CONTINUE;
                                    }
                                }
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("tcg", "barn_beast_transfer_failed_invalid"));
                        }
                    }
                }
            }
            if (utils.hasScriptVar(player, "barnRanchHand.handleBarnBeastReclaim"))
            {
                utils.removeScriptVar(player, "barnRanchHand.handleBarnBeastReclaim");
            }
            if (utils.hasScriptVar(player, "barnRanchHand.pid"))
            {
                utils.removeScriptVar(player, "barnRanchHand.pid");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleBarnBeastDisplay(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = sui.getPlayerId(params);
        if (isIdValid(player))
        {
            location myLoc = getLocation(self);
            location playerLoc = getLocation(player);
            if (myLoc.cell != playerLoc.cell)
            {
                sendSystemMessage(player, new string_id("tcg", "barn_ranchhand_not_in_same_cell"));
            }
            else 
            {
                int button = sui.getIntButtonPressed(params);
                if (button == sui.BP_CANCEL)
                {
                }
                else 
                {
                    int beast_selected = sui.getListboxSelectedRow(params);
                    if (beast_selected > -1)
                    {
                        String[] beastStorageSlots = utils.getStringArrayScriptVar(player, "barnRanchHand.handleBarnBeastDisplay");
                        if (beastStorageSlots != null && beastStorageSlots.length > 0)
                        {
                            if (beast_selected < beastStorageSlots.length)
                            {
                                String selectedBeastStorageSlot = beastStorageSlots[beast_selected];
                                if (selectedBeastStorageSlot != null && selectedBeastStorageSlot.length() > 0)
                                {
                                    String petName = getPetNameForDisplayMessage(self, selectedBeastStorageSlot);
                                    if (hasObjVar(self, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING))
                                    {
                                        storeDisplayedBeast(self, player, selectedBeastStorageSlot);
                                    }
                                    else 
                                    {
                                        String[] storageList = tcg.getBarnBeastStorageSlots(self);
                                        if (storageList != null || storageList.length > 0)
                                        {
                                            for (int i = 0; i < storageList.length; i++)
                                            {
                                                String bcdSlot = storageList[i];
                                                if (hasObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING))
                                                {
                                                    storeDisplayedBeast(self, player, bcdSlot);
                                                }
                                            }
                                        }
                                        string_id message = new string_id("tcg", "barn_lite_beast_display_failed");
                                        location here = getLocation(self);
                                        location spawnLoc = spawning.getRandomLocationAtDistance(getLocation(self), 2.0f);
                                        obj_id beastToDisplay = tcg.barnDisplayBeast(self, selectedBeastStorageSlot, self, spawnLoc);
                                        if (isIdValid(beastToDisplay))
                                        {
                                            CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has created a roaming/displayed representation of their barn beast: " + beastToDisplay + " in barn: " + self + " located at: " + getLocation(self) + ".");
                                            message = new string_id("tcg", "barn_lite_beast_display_success");
                                        }
                                        else 
                                        {
                                            CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has FAILED to create a roaming/displayed representation of their barn beast slot: " + selectedBeastStorageSlot + " in barn: " + self + " located at: " + getLocation(self) + ".");
                                            message = new string_id("tcg", "barn_lite_beast_display_failed");
                                        }
                                        prose_package pp = prose.getPackage(message, player, player);
                                        prose.setTO(pp, petName);
                                        sendSystemMessageProse(player, pp);
                                    }
                                }
                            }
                        }
                        else 
                        {
                            sendSystemMessage(player, new string_id("tcg", "barn_beast_display_failed_invalid"));
                        }
                    }
                }
            }
            if (utils.hasScriptVar(player, "barnRanchHand.handleBarnBeastDisplay"))
            {
                utils.removeScriptVar(player, "barnRanchHand.handleBarnBeastDisplay");
            }
            if (utils.hasScriptVar(player, "barnRanchHand.pid"))
            {
                utils.removeScriptVar(player, "barnRanchHand.pid");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void storeDisplayedBeast(obj_id self, obj_id player, String bcdSlot) throws InterruptedException
    {
        string_id message = new string_id("tcg", "barn_beast_display_storage_failed");
        if (hasObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING))
        {
            obj_id displayedBeast = getObjIdObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING);
            if (isIdValid(displayedBeast) && exists(displayedBeast))
            {
                destroyObject(displayedBeast);
                removeObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING);
                message = new string_id("tcg", "barn_lite_beast_display_stored");
                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has stored a previously roaming/displayed beast: " + displayedBeast + " in a beast storage device: " + self + " located at: " + getLocation(self) + ".");
            }
            else if (isIdValid(displayedBeast) && !exists(displayedBeast))
            {
                removeObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING);
                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " stored a barn beast " + (displayedBeast) + " that used to be roaming/displayed but for some reason is no longer valid.  The issue was corrected and the beast can now roam/be displayed roaming. Beast Storage Device: " + self + " located at: " + getLocation(self) + ".");
            }
            else 
            {
                removeObjVar(self, "barnStorage." + bcdSlot + "." + tcg.BEAST_ROAMING);
                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " could not store a roming displayed barn beast. The beast OID was invalid in the beast storage device: " + self + " located at: " + getLocation(self) + ".");
            }
            String petName = getPetNameForDisplayMessage(self, bcdSlot);
            prose_package pp = prose.getPackage(message, player, player);
            prose.setTO(pp, petName);
            sendSystemMessageProse(player, pp);
        }
        return;
    }
    public String getPetNameForDisplayMessage(obj_id self, String bcdSlot) throws InterruptedException
    {
        String petName = "Unknown";
        if (hasObjVar(self, "barnStorage." + bcdSlot + ".beast.beastName"))
        {
            petName = getStringObjVar(self, "barnStorage." + bcdSlot + ".beast.beastName");
        }
        else if (hasObjVar(self, "barnStorage." + bcdSlot + ".tempName"))
        {
            petName = getStringObjVar(self, "barnStorage." + bcdSlot + ".tempName");
        }
        return petName;
    }
    public int showRoamingBeastsSoon(obj_id self, dictionary params) throws InterruptedException
    {
        tcg.showRoamingBeasts(self, self);
        return SCRIPT_CONTINUE;
    }
}
