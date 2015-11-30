package script.systems.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.city;
import script.library.prose;
import script.library.utils;
import script.library.player_structure;
import script.library.regions;

public class city_furniture extends script.base_script
{
    public city_furniture()
    {
    }
    public static final string_id SID_PLACE = new string_id("city/city", "place");
    public static final string_id SID_MT_REMOVE = new string_id("city/city", "mt_remove");
    public static final string_id SID_MT_REMOVED = new string_id("city/city", "mt_removed");
    public static final string_id SID_DECO_TOO_CLOSE = new string_id("city/city", "deco_too_close");
    public static final string_id SID_NO_MORE_DECOS = new string_id("city/city", "no_more_decos");
    public static final string_id SID_ALIGN = new string_id("city/city", "align");
    public static final string_id SID_NORTH = new string_id("city/city", "north");
    public static final string_id SID_SOUTH = new string_id("city/city", "south");
    public static final string_id SID_EAST = new string_id("city/city", "east");
    public static final string_id SID_WEST = new string_id("city/city", "west");
    public static final string_id NO_SKILL_DECO = new string_id("city/city", "no_skill_deco");
    public static final String CITY_DECORATIONS = "datatables/city/decorations.iff";
    public static final string_id SID_CIVIC_ONLY = new string_id("city/city", "civic_only");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        LOG("sissynoid", "Initializing City Decoration ");
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (city_id <= 0)
        {
            LOG("sissynoid", "Initializing City Decoration " + self + " isInWorldCell(self) = " + isInWorldCell(self) + " getContainer(self) = " + getContainedBy(self) + " hasObjVar(self, 'city_id') = " + hasObjVar(self, "city_id"));
            if (isInWorldCell(self))
            {
                LOG("sissynoid", "City Decorations: OnInitialize: City ID is invalid for Decoration - Requesting Destruction of City Decoration(" + self + " : " + getTemplateName(self) + ")");
                CustomerServiceLog("player_city_transfer", "City Decorations: OnInitialize: City ID is invalid for Decoration - Requesting Destruction of City Decoration(" + self + " : " + getTemplateName(self) + ")");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                return SCRIPT_CONTINUE;
            }
        }
        obj_id currentMayor = cityGetLeader(city_id);
        obj_id decorationOwner = getOwner(self);
        if (!isIdValid(currentMayor))
        {
            LOG("sissynoid", "City Decorations: OnInitialize: Invalid Mayor ID(" + currentMayor + ") for City(" + city_id + ") when Initializing City Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing out until next initialization.");
            CustomerServiceLog("player_city_transfer", "City Decorations: OnInitialize: Invalid Mayor ID(" + currentMayor + ") for City(" + city_id + ") when Initializing City Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing out until next initialization.");
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "returnObjectToOwner"))
        {
            LOG("sissynoid", "Initializing City Decoration: Found Decoration to Send to Owner");
            if (currentMayor != decorationOwner)
            {
                LOG("sissynoid", "Initializing City Decoration: Owner != Mayor - Moving Item.");
                if (moveCityDecorationToOwnerInventory(decorationOwner, self))
                {
                    LOG("sissynoid", "Successfully Moved Decoration - Removing from City Decor List");
                    city.removeDecoration(self);
                }
            }
        }
        else 
        {
            LOG("sissynoid", "Initializing City Decoration: Not a Move-To-Inventory Decoration");
            setOwner(self, currentMayor);
            city.validateSpecialStructure(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        city.removeDecoration(self);
        return SCRIPT_CONTINUE;
    }
    public int requestDestroy(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "returnObjectToOwner"))
        {
            obj_id decorationOwner = getOwner(self);
            if (!isIdValid(decorationOwner))
            {
                LOG("sissynoid", "City Decorations: requestDestroy: Invalid Decoration Owner(" + decorationOwner + ") for City Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing out until next initialization.");
                CustomerServiceLog("player_city_transfer", "City Decorations: requestDestroy: Invalid Decoration Owner(" + decorationOwner + ") for City Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing out until next initialization.");
                return SCRIPT_CONTINUE;
            }
            if (moveCityDecorationToOwnerInventory(decorationOwner, self))
            {
                LOG("sissynoid", "Successfully sent Object (" + self + ") to Player(" + decorationOwner + ")");
                city.removeDecoration(self);
            }
        }
        else 
        {
            LOG("sissynoid", "Destroying object - not a 'return-to-owner' object/decoration");
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int city_id = getCityAtLocation(getLocation(player), 0);
        boolean isMayor = city.isTheCityMayor(player, city_id);
        if (!isMayor)
        {
            return SCRIPT_CONTINUE;
        }
        region[] rgnTest = getRegionsWithBuildableAtPoint(getLocation(player), regions.BUILD_FALSE);
        if (rgnTest != null)
        {
            if (getIntObjVar(self, "city_id") != 0)
            {
                obj_id pinv = utils.getInventoryContainer(player);
                if (putIn(self, pinv))
                {
                    removalCleanup(self, player, true);
                }
            }
            return SCRIPT_CONTINUE;
        }
        if (0 == getIntObjVar(self, "city_id"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU1, SID_PLACE);
        }
        else 
        {
            if (getOwner(self) == player)
            {
                mi.addRootMenu(menu_info_types.ITEM_PICKUP, SID_MT_REMOVE);
            }
            else if (isMayor)
            {
                mi.addRootMenu(menu_info_types.SERVER_MENU2, SID_MT_REMOVE);
            }
            int menu = mi.addRootMenu(menu_info_types.SERVER_MENU3, SID_ALIGN);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU4, SID_NORTH);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU5, SID_SOUTH);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU6, SID_EAST);
            mi.addSubMenu(menu, menu_info_types.SERVER_MENU7, SID_WEST);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        sendDirtyObjectMenuNotification(self);
        int city_id = city.checkMayorCity(player, false);
        if (city_id == 0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU1)
        {
            placeDecoration(city_id, player, self);
        }
        else if (item == menu_info_types.SERVER_MENU2 || item == menu_info_types.ITEM_PICKUP)
        {
            obj_id pinv = utils.getInventoryContainer(player);
            if (putIn(self, pinv))
            {
                removalCleanup(self, player, true);
            }
        }
        else if (item == menu_info_types.SERVER_MENU4)
        {
            setYaw(self, 0);
        }
        else if (item == menu_info_types.SERVER_MENU5)
        {
            setYaw(self, 180);
        }
        else if (item == menu_info_types.SERVER_MENU6)
        {
            setYaw(self, 90);
        }
        else if (item == menu_info_types.SERVER_MENU7)
        {
            setYaw(self, -90);
        }
        return SCRIPT_CONTINUE;
    }
    public void placeDecoration(int city_id, obj_id player, obj_id self) throws InterruptedException
    {
        String command = null;
        String[] templates = dataTableGetStringColumn(CITY_DECORATIONS, "TEMPLATE");
        for (int i = 0; i < templates.length; i++)
        {
            if (templates[i].equals(getTemplateName(self)))
            {
                command = dataTableGetString(CITY_DECORATIONS, i, "COMMAND");
                break;
            }
        }
        if (command == null)
        {
            return;
        }
        if (!hasCommand(player, command))
        {
            sendSystemMessage(player, NO_SKILL_DECO);
            return;
        }
        location loc = getLocation(player);
        int maxd = city.getMaxDecorationCount(city_id);
        int curd = city.getDecorationCount(city_id);
        if (curd + 1 > maxd)
        {
            sendSystemMessage(player, SID_NO_MORE_DECOS);
            return;
        }
        obj_id structure = getTopMostContainer(player);
        if ((!isIdValid(structure)) && (structure != player))
        {
            if (!player_structure.isCivic(structure))
            {
                sendSystemMessage(player, SID_CIVIC_ONLY);
                return;
            }
        }
        String name = getTemplateName(self);
        if (name.indexOf("streetlamp") < 0)
        {
            obj_id[] structures = cityGetStructureIds(city_id);
            for (int i = 0; i < structures.length; i++)
            {
                if (city.isSkillTrainer(city_id, structures[i]) || city.isMissionTerminal(city_id, structures[i]) || city.isDecoration(city_id, structures[i]))
                {
                    continue;
                }
                location oloc = getLocation(structures[i]);
                float dist = utils.getDistance2D(loc, oloc);
                if ((dist < 25) && (dist > 0))
                {
                    prose_package pp = prose.getPackage(SID_DECO_TOO_CLOSE, localize(getNameStringId(structures[i])));
                    sendSystemMessageProse(player, pp);
                    return;
                }
            }
        }
        setLocation(self, loc);
        setYaw(self, getYaw(player));
        city.addDecoration(city_id, player, self);
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        obj_id player = transferer;
        if (isIdValid(player))
        {
            removalCleanup(self, player, false);
        }
        return SCRIPT_CONTINUE;
    }
    public void removalCleanup(obj_id object, obj_id player, boolean spam) throws InterruptedException
    {
        removeObjVar(object, "city_id");
        city.removeDecoration(object);
        if (spam)
        {
            sendSystemMessage(player, SID_MT_REMOVED);
        }
    }
    public int setNewMayor(obj_id self, dictionary params) throws InterruptedException
    {
        LOG("sissynoid", "Entered: Setting New Mayor");
        if (params == null || params.isEmpty())
        {
            LOG("sissynoid", "City Decorations: setNewMayor: Failed to transfer Decoration(" + self + ") to New Mayor due to Empty Params.");
            CustomerServiceLog("player_city_transfer", "City Decorations: setNewMayor: Failed to transfer Decoration(" + self + ") to New Mayor due to Empty Params.");
            return SCRIPT_CONTINUE;
        }
        obj_id new_mayor = params.getObjId("mayor");
        if (!isIdValid(new_mayor) || !isIdValid(self))
        {
            LOG("sissynoid", "City Decorations: setNewMayor: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid Mayor/Self ID.");
            CustomerServiceLog("player_city_transfer", "City Decorations: setNewMayor: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid Mayor/Self ID.");
            return SCRIPT_CONTINUE;
        }
        updateOwnerAndAdmin(self, new_mayor);
        return SCRIPT_CONTINUE;
    }
    public void updateOwnerAndAdmin(obj_id self, obj_id new_mayor) throws InterruptedException
    {
        if (!isIdValid(new_mayor) || !isIdValid(self))
        {
            LOG("sissynoid", "City Decorations: updateOwnerAndAdmin: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid Mayor/Self ID.");
            CustomerServiceLog("player_city_transfer", "City Decorations: updateOwnerAndAdmin: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid Mayor/Self ID.");
            return;
        }
        int city_id = getCityAtLocation(getLocation(self), 0);
        if (city_id <= 0)
        {
            LOG("sissynoid", "City Decorations: updateOwnerAndAdmin: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid City ID (" + city_id + "). Used getCityAtLocation - using Self");
            CustomerServiceLog("player_city_transfer", "City Decorations: updateOwnerAndAdmin: Failed to transfer Structure(" + self + ") to New Mayor (" + new_mayor + ") due to Invalid City ID (" + city_id + "). Used getCityAtLocation - using Self");
            return;
        }
        if (!city.isDecoration(city_id, self))
        {
            LOG("sissynoid", "City Decorations: updateOwnerAndAdmin: Did not transfer Decoration(" + self + ") - Is not a Decoration");
            CustomerServiceLog("player_city_transfer", "City Decorations: updateOwnerAndAdmin: Did not transfer Decoration(" + self + ") - Is not a Decoration");
            return;
        }
        if (hasObjVar(self, "returnObjectToOwner"))
        {
            LOG("sissynoid", "Object Needing transferred to Owner's Inventory (" + self + ")");
            obj_id decorOwner = getOwner(self);
            if (!isIdValid(decorOwner))
            {
                LOG("sissynoid", "City Decorations: updateOwnerAndAdmin: (ObjVar)returnObjectToOwner: Invalid Decoration Owner for Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing until next attempt.");
                CustomerServiceLog("player_city_transfer", "City Decorations: updateOwnerAndAdmin: (ObjVar)returnObjectToOwner: Invalid Decoration Owner for Decoration(" + self + " : " + getTemplateName(self) + ") - Bailing until next attempt.");
                return;
            }
            if (decorOwner != new_mayor)
            {
                LOG("sissynoid", "New Mayor (" + new_mayor + ") does not equal Decor Owner (" + decorOwner + ") Decoration(" + self + ")");
                if (moveCityDecorationToOwnerInventory(decorOwner, self))
                {
                    LOG("sissynoid", "Transfer to Inventory (" + decorOwner + ") Complete: (" + self + ")");
                    city.removeDecoration(self);
                }
            }
        }
        else 
        {
            obj_id decorOwner = getOwner(self);
            LOG("sissynoid", "Setting Owner of | Decoration (" + self + ") | from(" + decorOwner + ") | to(" + new_mayor + ")");
            setOwner(self, new_mayor);
        }
        return;
    }
    public boolean moveCityDecorationToOwnerInventory(obj_id player, obj_id item) throws InterruptedException
    {
        if (!isIdValid(item) || !isIdValid(player))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: Invalid ID: Player(" + player + ") or Item(" + item + ") - Can Not move to player's inventory at this time.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: Invalid ID: Player(" + player + ") or Item(" + item + ") - Can Not move to player's inventory at this time.");
            return false;
        }
        boolean isValidObject = true;
        obj_id objectOwner = getOwner(item);
        if (player != objectOwner)
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: Player(" + player + ") is not the objectOwner(" + objectOwner + ") of Object: City Decoration(" + item + " : " + getTemplateName(item) + ")");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: Player(" + player + ") is not the objectOwner(" + objectOwner + ") of Object: City Decoration(" + item + " : " + getTemplateName(item) + ")");
            isValidObject = false;
        }
        if (!hasObjVar(item, "returnObjectToOwner"))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") does not have the 'returnObjectToOwner' objvar - Not Transfering to player.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") does not have the 'returnObjectToOwner' objvar - Not Transfering to player.");
            isValidObject = false;
        }
        if (!isObjectPersisted(item))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is not a persisted object - not transfering object to player");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is not a persisted object - not transfering object to player");
            isValidObject = false;
        }
        if (isPlayer(item))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a player! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a player! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (isMob(item))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Mob! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Mob! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (hasCondition(item, CONDITION_VENDOR))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Vendor! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Vendor! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (getContainerType(item) != 0)
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Container! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is a Container! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (utils.isNestedWithinAPlayer(item, true))
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is already contained by a player! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is already contained by a player! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (!isInWorld(item) || !isInWorldCell(item) || getTopMostContainer(item) != item)
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is already contained by a ! Not transfering the object to player's inventory.");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is already contained by a player! Not transfering the object to player's inventory.");
            isValidObject = false;
        }
        if (isValidObject)
        {
            LOG("sissynoid", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is being transferred to Owner's Inventory. Owner ID(" + player + ")");
            CustomerServiceLog("player_city_transfer", "City Decorations: moveCityDecorationToOwnerInventory: City Decoration(" + item + " : " + getTemplateName(item) + ") is being transferred to Owner's Inventory. Owner ID(" + player + ")");
            removeObjVar(item, "city_id");
            moveToOfflinePlayerInventoryAndUnload(item, player);
        }
        return isValidObject;
    }
}
