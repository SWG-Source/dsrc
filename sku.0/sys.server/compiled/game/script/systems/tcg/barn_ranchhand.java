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
import script.library.player_structure;
import script.library.prose;
import script.library.sui;
import script.library.tcg;
import script.library.utils;

public class barn_ranchhand extends script.base_script
{
    public barn_ranchhand()
    {
    }
    public static final boolean LOGGING_ON = false;
    public static final String LOGGING_CATEGORY = "tcg_barn";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setName(self, "a ranch hand");
        messageTo(self, "handleSetRanchandName", null, 2, false);
        messageTo(self, "showRoamingBeastsSoon", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleSetRanchandName(obj_id self, dictionary params) throws InterruptedException
    {
        String ranchhandName = "a ranch hand";
        obj_id building = getTopMostContainer(self);
        if (isIdValid(building))
        {
            String buildingOwnerName = getStringObjVar(building, player_structure.VAR_OWNER);
            if (buildingOwnerName != null && buildingOwnerName.length() > 0)
            {
                ranchhandName = "" + toUpper(buildingOwnerName, 0) + "'s ranch hand";
            }
        }
        setName(self, ranchhandName);
        setObjVar(self, "unmoveable", 1);
        setObjVar(self, "noEject", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id destContainerContainer = getContainedBy(destContainer);
        String destContainerContainerTemplate = getTemplateName(destContainerContainer);
        if (destContainerContainerTemplate.indexOf("barn_no_planet_restriction") > -1)
        {
            return SCRIPT_CONTINUE;
        }
        sendSystemMessage(transferer, new string_id("tcg", "barn_ranchhand_cannot_transfer"));
        return SCRIPT_OVERRIDE;
    }
    public int OnPack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id barnCell = getObjIdObjVar(self, tcg.RANCHHAND_CELL);
        if (isValidId(barnCell))
        {
            removeObjVar(barnCell, tcg.RANCHHAND_CELLCHECK);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
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
                        obj_id[] beastControlDevices = utils.getObjIdArrayScriptVar(player, "barnRanchHand.handleBarnBeastStorage");
                        if (beastControlDevices != null && beastControlDevices.length > 0)
                        {
                            if (beast_selected < beastControlDevices.length)
                            {
                                obj_id selectedBeastControlDevice = beastControlDevices[beast_selected];
                                if (isIdValid(selectedBeastControlDevice))
                                {
                                    obj_id barn = getTopMostContainer(self);
                                    if (isIdValid(barn))
                                    {
                                        string_id message = new string_id("tcg", "barn_beast_storage_failed");
                                        String petName = beast_lib.getBCDBeastName(selectedBeastControlDevice);
                                        if (tcg.transferBeastControlDeviceToBarn(selectedBeastControlDevice, player, barn))
                                        {
                                            message = new string_id("tcg", "barn_beast_stored_in_barn");
                                            CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " successfully stored beast control device in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                        }
                                        prose_package pp = prose.getPackage(message, player, player);
                                        prose.setTO(pp, petName);
                                        sendSystemMessageProse(player, pp);
                                        if (beast_lib.getTotalBeastControlDevices(player) > 0 && tcg.getTotalBarnStoredBeastsFromRanchhand(self) <= 40)
                                        {
                                            tcg.barnStoreBeastPrompt(player, self);
                                            return SCRIPT_CONTINUE;
                                        }
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
                        String[] beastStorageSlots = utils.getStringArrayScriptVar(player, "barnRanchHand.handleBarnBeastReclaim");
                        if (beastStorageSlots != null && beastStorageSlots.length > 0)
                        {
                            if (beast_selected < beastStorageSlots.length)
                            {
                                String selectedBeastStorageSlot = beastStorageSlots[beast_selected];
                                if (selectedBeastStorageSlot != null && selectedBeastStorageSlot.length() > 0)
                                {
                                    obj_id barn = getTopMostContainer(self);
                                    if (isIdValid(barn))
                                    {
                                        string_id message = new string_id("tcg", "barn_beast_reclaimed_failed");
                                        String petName = "Unknown";
                                        if (hasObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName"))
                                        {
                                            petName = getStringObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName");
                                        }
                                        else if (hasObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".tempName"))
                                        {
                                            petName = getStringObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".tempName");
                                        }
                                        obj_id transferedBCD = tcg.transferBeastControlDeviceFromBarn(selectedBeastStorageSlot, player, barn);
                                        if (isIdValid(transferedBCD))
                                        {
                                            message = new string_id("tcg", "barn_beast_reclaimed_to_you");
                                            petName = beast_lib.getBCDBeastName(transferedBCD);
                                            CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " successfully claimed beast control device: " + transferedBCD + " from barn: " + barn + " located at: " + getLocation(barn) + ".");
                                        }
                                        prose_package pp = prose.getPackage(message, player, player);
                                        prose.setTO(pp, petName);
                                        sendSystemMessageProse(player, pp);
                                        if (tcg.getTotalBarnStoredBeastsFromRanchhand(self) > 0 && !callable.hasMaxStoredCombatPets(player))
                                        {
                                            tcg.barnReclaimBeastPrompt(player, barn, self);
                                            return SCRIPT_CONTINUE;
                                        }
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
                                    obj_id barn = getTopMostContainer(self);
                                    if (isIdValid(barn))
                                    {
                                        string_id message = new string_id("tcg", "barn_beast_display_failed");
                                        String petName = "Unknown";
                                        if (hasObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName"))
                                        {
                                            petName = getStringObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".beast.beastName");
                                        }
                                        else if (hasObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".tempName"))
                                        {
                                            petName = getStringObjVar(barn, "barnStorage." + selectedBeastStorageSlot + ".tempName");
                                        }
                                        if (hasObjVar(barn, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING))
                                        {
                                            obj_id displayedBeast = utils.getObjIdObjVar(barn, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING);
                                            if (isIdValid(displayedBeast) && exists(displayedBeast))
                                            {
                                                blog("barn_ranchhand = handleBarnBeastDisplay ---- Destroying beast: " + displayedBeast);
                                                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has stored a previously roaming/displayed beast: " + displayedBeast + " in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                                destroyObject(displayedBeast);
                                                removeObjVar(barn, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING);
                                                message = new string_id("tcg", "barn_beast_display_stored");
                                            }
                                            else if (isIdValid(displayedBeast) && !exists(displayedBeast))
                                            {
                                                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " stored a barn beast " + (displayedBeast) + " that used to be roaming/displayed in the barn but for some reason is no longer valid.  The issue was corrected and the beast can now roam/be displayed roaming in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                                blog("barn_ranchhand = handleBarnBeastDisplay ---- Destroying beast OBJVAR ONLY: " + displayedBeast);
                                                removeObjVar(barn, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING);
                                            }
                                            else 
                                            {
                                                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " could not store a roming displayed barn beast. The beast OID was invalid in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                                message = new string_id("tcg", "barn_beast_display_storage_failed");
                                                removeObjVar(barn, "barnStorage." + selectedBeastStorageSlot + "." + tcg.BEAST_ROAMING);
                                            }
                                        }
                                        else 
                                        {
                                            obj_id beastToDisplay = tcg.barnDisplayBeast(self, selectedBeastStorageSlot, barn);
                                            if (isIdValid(beastToDisplay))
                                            {
                                                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has created a roaming/displayed representation of their barn beast: " + beastToDisplay + " in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                                message = new string_id("tcg", "barn_beast_display_success");
                                            }
                                            else 
                                            {
                                                CustomerServiceLog("playerStructure", "Player: " + player + " " + getPlayerName(player) + " has FAILED to create a roaming/displayed representation of their barn beast slot: " + selectedBeastStorageSlot + " in barn: " + barn + " located at: " + getLocation(barn) + ".");
                                                message = new string_id("tcg", "barn_beast_display_failed");
                                            }
                                        }
                                        prose_package pp = prose.getPackage(message, player, player);
                                        prose.setTO(pp, petName);
                                        sendSystemMessageProse(player, pp);
                                        if (tcg.getTotalBarnStoredBeastsFromRanchhand(self) > 0)
                                        {
                                            tcg.barnDisplayBeastPrompt(player, barn, self);
                                            return SCRIPT_CONTINUE;
                                        }
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
    public int showRoamingBeastsSoon(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id barn = getTopMostContainer(self);
        if (!isIdValid(barn) || !exists(barn))
        {
            return SCRIPT_CONTINUE;
        }
        if (!tcg.showRoamingBeasts(barn, self))
        {
            CustomerServiceLog("playerStructure", "Ranchhand: " + self + " " + getPlayerName(self) + " has FAILED to create a roaming/displayed representation in its barn: " + barn + " located at: " + getLocation(barn) + ".");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON && msg != null && !msg.equals(""))
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
