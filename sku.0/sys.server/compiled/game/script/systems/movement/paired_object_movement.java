package script.systems.movement;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.instance;
import script.library.trial;
import script.library.utils;
import script.library.vehicle;

public class paired_object_movement extends script.base_script
{
    public paired_object_movement()
    {
    }
    public static final int STATUS_NONE = 0;
    public static final int STATUS_ONE = 1;
    public static final int STATUS_MANY = 2;
    public static final int[] MULTI_MENU = 
    {
        menu_info_types.SERVER_MENU1,
        menu_info_types.SERVER_MENU2,
        menu_info_types.SERVER_MENU3,
        menu_info_types.SERVER_MENU4,
        menu_info_types.SERVER_MENU5
    };
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        if (isIncapacitated(player) || isDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] validTransitions = getPairedObjects(self);
        if (validTransitions == null || validTransitions.length == 0)
        {
            utils.setScriptVar(self, "status", STATUS_NONE);
            item.addRootMenu(menu_info_types.ITEM_USE, getFailMessage(self));
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "validTransition", validTransitions);
        if (validTransitions.length == 1)
        {
            utils.setScriptVar(self, "status", STATUS_ONE);
            item.addRootMenu(menu_info_types.ITEM_USE, getUniqueId(validTransitions[0]));
            return SCRIPT_CONTINUE;
        }
        int opt_menu = item.addRootMenu(menu_info_types.ITEM_USE, new string_id("movement", "paired_object_menu_multi"));
        for (int i = 0; i < validTransitions.length && i < 5; i++)
        {
            utils.setScriptVar(self, "status", STATUS_MANY);
            item.addSubMenu(opt_menu, MULTI_MENU[i], getUniqueId(validTransitions[i]));
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!wasValidSelection(item))
        {
            return SCRIPT_CONTINUE;
        }
        int status = utils.getIntScriptVar(self, "status");
        obj_id[] transitions = utils.getObjIdArrayScriptVar(self, "validTransition");
        switch (status)
        {
            case STATUS_NONE:
            break;
            case STATUS_ONE:
            doPairedTransition(player, transitions[0]);
            break;
            case STATUS_MANY:
            obj_id chosenItem = getObjectSelected(transitions, item);
            doPairedTransition(player, chosenItem);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public String getTransitionKey(obj_id self) throws InterruptedException
    {
        return hasObjVar(self, "transition_key") ? getStringObjVar(self, "transition_key") : "none";
    }
    public string_id getUniqueId(obj_id self) throws InterruptedException
    {
        String message = hasObjVar(self, "unique_id") ? getStringObjVar(self, "unique_id") : "none";
        return new string_id("movement", message);
    }
    public string_id getFailMessage(obj_id self) throws InterruptedException
    {
        string_id failMessage = new string_id("movement", "paired_object_default_fail_menu");
        if (hasObjVar(self, "custom_string"))
        {
            failMessage = new string_id("movement", getStringObjVar(self, "custom_string"));
        }
        return failMessage;
    }
    public obj_id[] getPairedObjects(obj_id self) throws InterruptedException
    {
        obj_id[] allObjects = new obj_id[0];
        if (instance.isInInstanceArea(self))
        {
            allObjects = utils.getAllObjectsInBuildoutArea(self);
        }
        else 
        {
            allObjects = getObjectsInRange(trial.getTop(self), 1000.0f);
        }
        if (allObjects == null || allObjects.length == 0)
        {
            clog("Found zero objects in this buildout area");
            return null;
        }
        obj_id[] pairedObjects = trial.getObjectsInListWithObjVar(allObjects, "transition_key");
        if (pairedObjects == null || pairedObjects.length == 0)
        {
            clog("Found no paired objects. List length = " + allObjects.length);
            return null;
        }
        String testKey = getTransitionKey(self);
        Vector matches = new Vector();
        matches.setSize(0);
        for (int i = 0; i < pairedObjects.length; i++)
        {
            if (!testKey.equals(getTransitionKey(pairedObjects[i])))
            {
                continue;
            }
            if (pairedObjects[i] == self)
            {
                continue;
            }
            matches.add(pairedObjects[i]);
        }
        if (matches == null || matches.size() == 0)
        {
            clog("found no matches");
            return null;
        }
        if (matches != null)
        {
            allObjects = new obj_id[matches.size()];
            matches.toArray(allObjects);

        }
        return allObjects;
    }
    public boolean wasValidSelection(int selection) throws InterruptedException
    {
        boolean wasValid = false;
        if (selection == menu_info_types.ITEM_USE)
        {
            return true;
        }
        for (int i = 0; i < MULTI_MENU.length; i++)
        {
            if (selection == MULTI_MENU[i])
            {
                return true;
            }
        }
        return false;
    }
    public obj_id getObjectSelected(obj_id[] selections, int menu_item) throws InterruptedException
    {
        for (int i = 0; i < MULTI_MENU.length; i++)
        {
            if (menu_item == MULTI_MENU[i])
            {
                return selections[i];
            }
        }
        return null;
    }
    public void doPairedTransition(obj_id player, obj_id warpToObject) throws InterruptedException
    {
        vehicle.checkForMountAndDismountPlayer(player);
        location destLoc = getLocation(warpToObject);
        boolean isInterior = isIdValid(destLoc.cell);
        if (isInterior)
        {
            location topLoc = getLocation(trial.getTop(warpToObject));
            warpPlayer(player, destLoc.area, topLoc.x, topLoc.y, topLoc.z, destLoc.cell, destLoc.x, destLoc.y, destLoc.z, "nullCallback", false);
        }
        else 
        {
            setLocation(player, getLocation(warpToObject));
        }
    }
    public void clog(String message) throws InterruptedException
    {
        LOG("doLogging", message);
    }
}
