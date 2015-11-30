package script.vendor;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.player_structure;
import script.library.trace;
import script.library.turnstile;
import script.library.vendor_lib;
import script.location;

public class vendor_control_device extends script.base_script
{
    public vendor_control_device()
    {
    }
    public static final string_id UNPACK_VENDOR = new string_id("sui", "unpack_vendor");
    public static final string_id SID_ONLY_IN_HOUSES = new string_id("sui", "only_in_houses");
    public static final string_id SID_VENDOR_PUBLIC_ONLY = new string_id("player_structure", "vendor_public_only");
    public static final string_id SID_VENDOR_NOT_IN_SHIP = new string_id("player_structure", "vendor_not_in_ship");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "noTrade", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "noTrade", 1);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id[] contents = getContents(self);
        if (contents == null || contents.length == 0)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id vendor = contents[0];
        obj_id player = utils.getContainingPlayer(self);
        if (hasObjVar(vendor, "vendor_initialized") && !isCommoditiesServerAvailable())
        {
            sui.msgbox(vendor, player, "@player_structure:destroy_vendor_cs_unavailable_d", sui.OK_ONLY, "@player_structure:destroy_vendor_cs_unavailable_t", "noHandler");
        }
        else 
        {
            CustomerServiceLog("vendor", "Vendor destroyed by owner via vendor control device. Vendor: " + vendor + " Location: " + getLocation(vendor));
            destroyObject(vendor);
        }
        return SCRIPT_OVERRIDE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id objPlayer, String[] strNames, String[] strAttribs) throws InterruptedException
    {
        int intIndex = utils.getValidAttributeIndex(strNames);
        if (intIndex == -1)
        {
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        location loc = getLocation(player);
        if (isIdValid(loc.cell))
        {
            obj_id structure = getTopMostContainer(player);
            int got = getGameObjectType(structure);
            if (isGameObjectTypeOf(got, GOT_ship))
            {
                sendSystemMessage(player, SID_VENDOR_NOT_IN_SHIP);
                return SCRIPT_CONTINUE;
            }
            if (isGameObjectTypeOf(got, GOT_building))
            {
                if (!permissionsIsPublic(structure))
                {
                    sendSystemMessage(player, SID_VENDOR_PUBLIC_ONLY);
                    return SCRIPT_CONTINUE;
                }
            }
            else 
            {
                sendSystemMessage(player, SID_ONLY_IN_HOUSES);
                return SCRIPT_CONTINUE;
            }
            if (!player_structure.isAdmin(structure, player) && getOwner(structure) != player)
            {
                sendSystemMessage(player, SID_ONLY_IN_HOUSES);
                return SCRIPT_CONTINUE;
            }
            mi.addRootMenu(menu_info_types.SERVER_MENU9, UNPACK_VENDOR);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDead(player) || isIncapacitated(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU9)
        {
            if (!isCommoditiesServerAvailable())
            {
                return SCRIPT_CONTINUE;
            }
            obj_id[] contents = getContents(self);
            if (contents == null || contents.length != 1)
            {
                trace.log("vendorpackup", "%TU attempted to unpack a vendor, but the VCD did not contain one.", player, trace.TL_CS_LOG | trace.TL_ERROR_LOG);
                return SCRIPT_CONTINUE;
            }
            obj_id vendor = contents[0];
            obj_id cell = getContainedBy(player);
            location loc = getLocation(player);
            if (!isIdNull(cell))
            {
                obj_id structure = getTopMostContainer(player);
                int got = getGameObjectType(structure);
                if (isGameObjectTypeOf(got, GOT_ship))
                {
                    sendSystemMessage(player, SID_VENDOR_NOT_IN_SHIP);
                    return SCRIPT_CONTINUE;
                }
                if (isGameObjectTypeOf(got, GOT_building))
                {
                    if (!permissionsIsPublic(structure))
                    {
                        sendSystemMessage(player, SID_VENDOR_PUBLIC_ONLY);
                        return SCRIPT_CONTINUE;
                    }
                }
                else 
                {
                    sendSystemMessage(player, SID_ONLY_IN_HOUSES);
                    return SCRIPT_CONTINUE;
                }
                if (!player_structure.isAdmin(structure, player) && getOwner(structure) != player)
                {
                    sendSystemMessage(player, SID_ONLY_IN_HOUSES);
                    return SCRIPT_CONTINUE;
                }
                if (getAccountNumLots(getPlayerObject(player)) > player_structure.MAX_LOTS)
                {
                    obj_id lotOverlimitStructure = getObjIdObjVar(player, "lotOverlimit.structure_id");
                    if (isIdValid(lotOverlimitStructure) && (lotOverlimitStructure != structure))
                    {
                        String lotOverlimitStructureName = getStringObjVar(player, "lotOverlimit.structure_name");
                        if (lotOverlimitStructureName == null)
                        {
                            lotOverlimitStructureName = "";
                        }
                        String lotOverlimitStructureLocation = getStringObjVar(player, "lotOverlimit.structure_location");
                        if (lotOverlimitStructureLocation == null)
                        {
                            lotOverlimitStructureLocation = "";
                        }
                        sendSystemMessage(player, "You are currently over the lot limit, and cannot drop any item into (or move around items within) any factory or structure, other than the " + lotOverlimitStructureName + " structure located at " + lotOverlimitStructureLocation + " that caused you to exceed the lot limit.", null);
                        return SCRIPT_CONTINUE;
                    }
                }
                if (putInPosition(vendor, cell, loc))
                {
                    if (!hasObjVar(vendor, vendor_lib.VAR_PACKUP_VERSION))
                    {
                        setObjVar(vendor_lib.getAuctionContainer(vendor), "vendor.needs_old_reinitialize", 1);
                        reinitializeVendor(vendor, player);
                    }
                    else 
                    {
                        updateVendorStatus(vendor, VENDOR_STATUS_FLAG_NONE);
                    }
                    trace.log("vendorpackup", "%TU unpacked their vendor (" + vendor + ") from vendor control device ( " + self + ")", player, trace.TL_CS_LOG);
                    sendDirtyObjectMenuNotification(vendor);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    sendSystemMessage(player, new string_id("player_structure", "drop_npc_vendor_perm"));
                }
            }
            else 
            {
                sendSystemMessage(player, new string_id("player_structure", "drop_npc_vendor_building"));
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToBeTransferred(obj_id self, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
}
