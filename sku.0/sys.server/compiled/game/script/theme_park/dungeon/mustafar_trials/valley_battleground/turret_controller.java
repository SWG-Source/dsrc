package script.theme_park.dungeon.mustafar_trials.valley_battleground;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;

public class turret_controller extends script.base_script
{
    public turret_controller()
    {
    }
    public static final String STF = "mustafar/valley_battlefield";
    public static final string_id fire = new string_id(STF, "fire");
    public static final string_id assemble = new string_id(STF, "assemble_turret");
    public static final string_id pickup = new string_id(STF, "pick_up_control_device");
    public static final String[] turretList = 
    {
        "object/tangible/dungeon/mustafar/valley_battlefield/turret_plasma.iff",
        "object/tangible/dungeon/mustafar/valley_battlefield/turret_concussion.iff",
        "object/tangible/dungeon/mustafar/valley_battlefield/turret_energy_burst.iff",
        "object/tangible/dungeon/mustafar/valley_battlefield/turret_null_energy.iff"
    };
    public static final string_id[] turretOptions = 
    {
        new string_id(STF, "plasma_turret"),
        new string_id(STF, "concussion_turret"),
        new string_id(STF, "energy_burst"),
        new string_id(STF, "null_energy")
    };
    public static final string_id setSUIInterest_d = new string_id(STF, "set_interest_d");
    public static final string_id setSUIInterest_t = new string_id(STF, "set_interest_t");
    public static final string_id showTargetSUI_d = new string_id(STF, "show_targets_d");
    public static final string_id showTargetSUI_t = new string_id(STF, "show_targets_t");
    public static final string_id buildingPlasma = new string_id(STF, "building_plasma");
    public static final string_id buildingConcussion = new string_id(STF, "building_concussion");
    public static final string_id buildingEnergy = new string_id(STF, "building_energy");
    public static final string_id buildingNullEnergy = new string_id(STF, "building_null_energy");
    public static final String controllerObject = "object/tangible/dungeon/mustafar/valley_battlefield/turret_controller_object.iff";
    public static final String turretID = "activeTurret";
    public static final String constructorID = "constructionDroid";
    public static final boolean doLogging = false;
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        int root_menu = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF, "start"));
        int menu = mi.addRootMenu(menu_info_types.ITEM_USE_SELF, new string_id(STF, "reset"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            obj_id top = getObjIdObjVar(self, "top");
            dictionary dict = new dictionary();
            dict.put("command", "startEvent");
            messageTo(top, "transmitCommand", dict, 0, false);
        }
        if (item == menu_info_types.ITEM_USE_SELF)
        {
            obj_id top = getObjIdObjVar(self, "top");
            dictionary dict = new dictionary();
            dict.put("command", "reset");
            messageTo(top, "transmitCommand", dict, 0, false);
        }
        return SCRIPT_CONTINUE;
    }
    public boolean hasBeenPickedUp(obj_id self) throws InterruptedException
    {
        if (hasObjVar(self, "inWorld"))
        {
            return false;
        }
        return true;
    }
    public boolean isTurretActive(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, turretID))
        {
            return false;
        }
        obj_id activeTurret = getObjIdObjVar(self, turretID);
        if (isIdValid(activeTurret))
        {
            return true;
        }
        return false;
    }
    public obj_id getTurret(obj_id player, obj_id controller) throws InterruptedException
    {
        if (!isTurretActive(controller))
        {
            presentAssembleSUI(player, controller);
        }
        obj_id activeTurret = getObjIdObjVar(controller, turretID);
        if (!isIdValid(activeTurret))
        {
            if (isTurretDisabled(controller))
            {
                destroyObject(controller);
                return obj_id.NULL_ID;
            }
        }
        return activeTurret;
    }
    public boolean isTurretDisabled(obj_id controller) throws InterruptedException
    {
        if (hasObjVar(controller, "turretDisabled"))
        {
            return true;
        }
        return false;
    }
    public int turretDisabled(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "turretDisabled", true);
        return SCRIPT_CONTINUE;
    }
    public int builtTurret(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id turret = params.getObjId("turretId");
        if (!isIdValid(turret))
        {
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, turretID, turret);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void regenerateInPlayerInventory(obj_id player, obj_id self) throws InterruptedException
    {
        obj_id turretController = createObjectInInventoryAllowOverload(controllerObject, player);
        if (!isIdValid(turretController))
        {
            doLogging("regenerateInPlayerInventory", "Failed to create controller in player inventory");
            return;
        }
        attachScript(turretController, "theme_park.dungeon.mustafar_trials.valley_battleground.turret_controller");
        utils.verifyLocationBasedDestructionAnchor(turretController, getLocation(player), 500);
        copyObjVar(self, turretController, constructorID);
        destroyObject(self);
    }
    public void fireTurret(obj_id player, obj_id controller) throws InterruptedException
    {
        obj_id target = getTarget(player);
        if (!isIdValid(target))
        {
            presentTargetingSUI(player, controller);
            return;
        }
        if (!isMob(target))
        {
            presentTargetingSUI(player, controller);
            return;
        }
        if (isTurretDisabled(controller))
        {
            doLogging("fireTurret", "Call to fire occured but the turret was disabled");
            return;
        }
        obj_id turret = getTurret(player, controller);
        if (!isIdValid(turret))
        {
            doLogging("fireTurret", "Call to fire occured but the turret Id was invalid");
            return;
        }
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("controller", controller);
        dict.put("target", target);
        doLogging("fireTurret", "Sending command to direct fire to turret(" + turret + "): Player(" + getName(player) + ") from controller(" + controller + ") at target(" + getName(target) + ")");
        messageTo(turret, "doFireAction", dict, 0, false);
    }
    public void presentAssembleSUI(obj_id player, obj_id self) throws InterruptedException
    {
        String[] turretTypes = new String[4];
        turretTypes[0] = "@" + turretOptions[0];
        turretTypes[1] = "@" + turretOptions[1];
        turretTypes[2] = "@" + turretOptions[2];
        turretTypes[3] = "@" + turretOptions[3];
        sui.listbox(self, player, "@" + setSUIInterest_d, sui.OK_CANCEL, "@" + setSUIInterest_t, turretTypes, "handleAssembleTurret", true);
    }
    public int handleAssembleTurret(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        obj_id constructorDroid = getConstructionDroid(self);
        location buildLoc = getLocation(player);
        if (!isIdValid(constructorDroid))
        {
            return SCRIPT_CONTINUE;
        }
        doLogging("handleAssembleTurret", "Idx precheck: " + idx);
        dictionary dict = new dictionary();
        dict.put("turretController", self);
        dict.put("buildLoc", buildLoc);
        if (!isIdValid(constructorDroid))
        {
            return SCRIPT_CONTINUE;
        }
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        if (idx == 0)
        {
            sendSystemMessage(player, buildingPlasma);
            dict.put("turretType", "plasma");
            messageTo(constructorDroid, "buildTurret", dict, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (idx == 1)
        {
            sendSystemMessage(player, buildingConcussion);
            dict.put("turretType", "concussion");
            messageTo(constructorDroid, "buildTurret", dict, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (idx == 2)
        {
            sendSystemMessage(player, buildingEnergy);
            dict.put("turretType", "energy_burst");
            messageTo(constructorDroid, "buildTurret", dict, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (idx == 3)
        {
            sendSystemMessage(player, buildingNullEnergy);
            dict.put("turretType", "null_energy");
            messageTo(constructorDroid, "buildTurret", dict, 1, false);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public obj_id getConstructionDroid(obj_id controller) throws InterruptedException
    {
        if (!hasObjVar(controller, constructorID))
        {
            destroyObject(controller);
            return obj_id.NULL_ID;
        }
        obj_id constructor = getObjIdObjVar(controller, constructorID);
        if (!isIdValid(constructor))
        {
            destroyObject(controller);
            return obj_id.NULL_ID;
        }
        return constructor;
    }
    public void presentTargetingSUI(obj_id player, obj_id controller) throws InterruptedException
    {
        obj_id turret = getTurret(player, controller);
        if (isTurretDisabled(controller))
        {
            doLogging("presentTargetingSUI", "Cannot querey targets because turret is diabled");
            return;
        }
        if (!isIdValid(turret))
        {
            doLogging("presentTargetingSUI", "Cannot querey targets because turret is invalid");
            return;
        }
        obj_id[] targets = getCreaturesInRange(turret, 40);
        Vector validTargets = new Vector();
        validTargets.setSize(0);
        for (int i = 0; i < targets.length; i++)
        {
            if (!isPlayer(targets[i]))
            {
                if (!isDead(targets[i]))
                {
                    if (canSee(turret, targets[i]))
                    {
                        utils.addElement(validTargets, targets[i]);
                    }
                }
            }
        }
        if (validTargets.size() == 0)
        {
            doLogging("presentTargetingSUI", "No valid targets were available");
            return;
        }
        obj_id[] goodTargets = new obj_id[0];
        if (validTargets != null)
        {
            goodTargets = new obj_id[validTargets.size()];
            validTargets.toArray(goodTargets);
        }
        utils.setScriptVar(turret, "goodTargets", goodTargets);
        String[] targetNames = new String[goodTargets.length];
        for (int k = 0; k < goodTargets.length; k++)
        {
            targetNames[k] = getEncodedName(goodTargets[k]);
        }
        if (targetNames == null || targetNames.length == 0)
        {
            doLogging("presetnTArgetingSUI", "No list of names for valid targets could be generated");
            return;
        }
        sui.listbox(controller, player, "@" + showTargetSUI_d, sui.OK_CANCEL, "@" + showTargetSUI_t, targetNames, "handleChooseTarget", true);
    }
    public int handleChooseTarget(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        obj_id player = sui.getPlayerId(params);
        obj_id turret = getTurret(player, self);
        if (!isIdValid(turret))
        {
            doLogging("handleChooseTarget", "Turret id was invalid");
            return SCRIPT_CONTINUE;
        }
        dictionary dict = new dictionary();
        dict.put("player", player);
        dict.put("controller", self);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            doLogging("handleChooseTarget", "Cancel button was pressed");
            return SCRIPT_CONTINUE;
        }
        obj_id[] targetList = utils.getObjIdArrayScriptVar(turret, "goodTargets");
        if (targetList == null || targetList.length == 0)
        {
            doLogging("handleChooseTarget", "Target list was null");
            return SCRIPT_CONTINUE;
        }
        int maxIdx = targetList.length - 1;
        if (idx > maxIdx)
        {
            doLogging("handleChooseTarget", "idx is greater than max idx(" + idx + " > " + maxIdx + ")");
        }
        obj_id target = targetList[idx];
        if (!isIdValid(target))
        {
            doLogging("handleChooseTarget", "TargetId was invalid");
            return SCRIPT_CONTINUE;
        }
        dict.put("target", target);
        messageTo(turret, "doFireAction", dict, 0, false);
        return SCRIPT_CONTINUE;
    }
    public void doLogging(String section, String message) throws InterruptedException
    {
        if (doLogging)
        {
            LOG("debug/turret_controller/" + section, message);
        }
    }
}
