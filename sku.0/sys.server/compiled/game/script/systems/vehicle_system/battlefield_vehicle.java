package script.systems.vehicle_system;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.armor;
import script.library.callable;
import script.library.combat;
import script.library.pet_lib;
import script.library.attrib;
import script.library.vehicle;
import script.library.buff;
import script.library.utils;
import script.library.trial;
import script.library.factions;
import script.library.stealth;

public class battlefield_vehicle extends script.base_script
{
    public battlefield_vehicle()
    {
    }
    public static final String MENU_FILE = "pet/pet_menu";
    public static final String TABLE = "datatables/vehicle/battlefield_vehicle.iff";
    public static final boolean debug = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        checkForAllowedZones(self);
        setAttributeAttained(self, attrib.VEHICLE);
        detachScript(self, "systems.vehicle_system.vehicle_base");
        detachScript(self, "systems.vehicle_system.vehicle_ping");
        callable.setCallableCD(self, self);
        makePetMountable(self);
        trial.setHp(self, dataTableGetInt(TABLE, getVehicleType(self), "default_hp"));
        setArmor(self, dataTableGetInt(TABLE, getVehicleType(self), "default_armor"));
        establishFaction(self);
        vehicle.initializeVehicle(self);
        return SCRIPT_CONTINUE;
    }
    public void setArmor(obj_id target, int amount) throws InterruptedException
    {
        if (!isIdValid(target))
        {
            return;
        }
        if (amount < 0)
        {
            return;
        }
        applySkillStatisticModifier(target, "expertise_innate_protection_all", amount);
        armor.recalculateArmorForMob(target);
        return;
    }
    public void establishFaction(obj_id self) throws InterruptedException
    {
        String vehicleFaction = dataTableGetString(TABLE, getVehicleType(self), "faction");
        if (vehicleFaction.equals("none"))
        {
            return;
        }
        factions.setFaction(self, vehicleFaction);
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        checkForAllowedZones(self);
        if (ai_lib.aiIsDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (isDisabled(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasInvisibleBuff(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (factions.getFaction(self) != null)
        {
            if ((factions.getFaction(self)).equals("Imperial") && !factions.isImperial(player))
            {
                return SCRIPT_CONTINUE;
            }
            else if ((factions.getFaction(self)).equals("Rebel") && !factions.isRebel(player))
            {
                return SCRIPT_CONTINUE;
            }
        }
        boolean isMountedOn = isMountedOnCreatureQueried(self, player);
        boolean isOwnedByPlayer = player == getMaster(self);
        if (!isMountedOn)
        {
            if (!doesMountHaveRoom(self))
            {
                return SCRIPT_CONTINUE;
            }
        }
        if (getMovementPercent(player) == 0.0f)
        {
            return SCRIPT_CONTINUE;
        }
        mi.addRootMenu(menu_info_types.SERVER_MENU10, new string_id(MENU_FILE, "menu_enter_exit"));
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        checkForAllowedZones(self);
        if (isDisabled(self))
        {
            return SCRIPT_CONTINUE;
        }
        boolean isMountedOn = isMountedOnCreatureQueried(self, player);
        if ((item != menu_info_types.SERVER_MENU10))
        {
            return SCRIPT_CONTINUE;
        }
        if (stealth.hasInvisibleBuff(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (ai_lib.isAiDead(self) || isDead(player))
        {
            return SCRIPT_CONTINUE;
        }
        String creature_name = getName(self);
        if (getMovementPercent(player) == 0.0f)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.SERVER_MENU10)
        {
            if (isMountedOn)
            {
                queueCommand(player, (1988230683), self, creature_name, COMMAND_PRIORITY_FRONT);
            }
            else 
            {
                if (!utils.hasScriptVar(self, "battlefield_vehicle.aboutToBeDestroyed"))
                {
                    queueCommand(player, (-2070021488), self, creature_name, COMMAND_PRIORITY_FRONT);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isMountedOnCreatureQueried(obj_id pet, obj_id player) throws InterruptedException
    {
        if (!isIdValid(pet))
        {
            return false;
        }
        if (!isIdValid(player))
        {
            return false;
        }
        obj_id playerCurrentMount = getMountId(player);
        if (!isIdValid(playerCurrentMount))
        {
            return false;
        }
        if (playerCurrentMount != pet)
        {
            return false;
        }
        return true;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        removePlayersFromVehicleAndDestroySelf(self, 5.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        removePlayersFromVehicle(self);
        return SCRIPT_CONTINUE;
    }
    public void removePlayersFromVehicleAndDestroySelf(obj_id self, float delay) throws InterruptedException
    {
        utils.setScriptVar(self, "battlefield_vehicle.aboutToBeDestroyed", true);
        removePlayersFromVehicle(self);
        if (buff.hasBuff(self, "vehicle_passenger"))
        {
            buff.removeBuff(self, "vehicle_passenger");
        }
        kill(self);
        messageTo(self, "destroyNow", null, delay, false);
    }
    public void removePlayersFromVehicle(obj_id self) throws InterruptedException
    {
        obj_id rider = getRiderId(self);
        if (isIdValid(rider))
        {
            String creature_name = getName(self);
            queueCommand(rider, (1988230683), self, creature_name, COMMAND_PRIORITY_FRONT);
            int vehicleBuff = buff.getBuffOnTargetFromGroup(rider, "vehicle");
            if (vehicleBuff != 0)
            {
                buff.removeBuff(rider, vehicleBuff);
            }
        }
        checkForPassengers(self);
        return;
    }
    public void areaDebugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(self), 5.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessage(players[i], message, "");
            }
        }
    }
    public int handleCheckForAllowedZones(obj_id self, dictionary params) throws InterruptedException
    {
        checkForAllowedZones(self);
        return SCRIPT_OVERRIDE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        checkForAllowedZones(self);
        return SCRIPT_OVERRIDE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        checkForAllowedZones(self);
        return SCRIPT_OVERRIDE;
    }
    public void debugMessaging(obj_id self, String message) throws InterruptedException
    {
        obj_id[] players = getAllPlayers(getLocation(self), 10.0f);
        if (players != null && players.length > 0)
        {
            for (int i = 0; i < players.length; i++)
            {
                sendSystemMessage(players[i], message, "");
            }
        }
    }
    public void checkForAllowedZones(obj_id self) throws InterruptedException
    {
        String allowedZonesString = "all";
        boolean allowedHere = false;
        if (hasObjVar(self, "battlefield_vehicle.overrideAllowedZones"))
        {
            allowedZonesString = getStringObjVar(self, "battlefield_vehicle.overrideAllowedZones");
        }
        else if (utils.hasScriptVar(self, "battlefield_vehicle.allowedZones"))
        {
            allowedZonesString = utils.getStringScriptVar(self, "battlefield_vehicle.allowedZones");
        }
        else 
        {
            allowedZonesString = dataTableGetString(TABLE, getVehicleType(self), "allowed_zones");
            utils.setScriptVar(self, "battlefield_vehicle.allowedZones", allowedZonesString);
        }
        if (allowedZonesString != null && allowedZonesString.length() > 0)
        {
            if (!allowedZonesString.equals("all"))
            {
                location here = getLocation(self);
                String zone = here.area;
                String[] allowedZones = split(allowedZonesString, ',');
                for (int j = 0; j < allowedZones.length; j++)
                {
                    if (allowedZones[j].equals(zone))
                    {
                        allowedHere = true;
                    }
                }
            }
            else 
            {
                allowedHere = true;
            }
        }
        else 
        {
            allowedHere = true;
        }
        if (!allowedHere)
        {
            removePlayersFromVehicleAndDestroySelf(self, 1.0f);
        }
        return;
    }
    public int destroyNow(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_OVERRIDE;
    }
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id player) throws InterruptedException
    {
        checkForAllowedZones(self);
        callable.storeCallables(player);
        String trunc_name = getVehicleType(self);
        dictionary dict = dataTableGetRow(TABLE, trunc_name);
        if (dict == null || dict.isEmpty())
        {
            debugSpeakMsg(player, "Egads, this vehicle could not find itself in the BF vehicle table");
            return SCRIPT_OVERRIDE;
        }
        utils.setScriptVar(player, combat.DAMAGE_REDIRECT, self);
        obj_id driver = getRiderId(self);
        boolean isDriver = isIdValid(driver) && exists(driver) && driver == player;
        String overrideAttack = getOverrideAttack(dict, isDriver);
        String[] abilities = getVehicleCommands(dict, isDriver);
        if (!overrideAttack.equals("none"))
        {
            overrideDefaultAttack(player, overrideAttack);
        }
        if (!isDriver && isIdValid(driver))
        {
            buff.applyBuff(self, "vehicle_passenger");
        }
        String driverBuff = dict.getString("driver_buff");
        if (driverBuff != null && driverBuff.length() > 0 && !driverBuff.equals("none"))
        {
            if (isDriver)
            {
                buff.applyBuff(player, self, driverBuff);
            }
        }
        setBeastmasterPet(player, self);
        setBeastmasterPetCommands(player, abilities);
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id player) throws InterruptedException
    {
        setBeastmasterPet(player, null);
        removeDefaultAttackOverride(player);
        utils.removeScriptVar(player, combat.DAMAGE_REDIRECT);
        checkForPassengers(self);
        checkForAllowedZones(self);
        return SCRIPT_CONTINUE;
    }
    public void checkForPassengers(obj_id self) throws InterruptedException
    {
        int numPlayers = 0;
        obj_id[] stuff = getContents(self);
        if (stuff != null && stuff.length > 0)
        {
            for (int i = 0; i < stuff.length; i++)
            {
                if (isPlayer(stuff[i]))
                {
                    ++numPlayers;
                }
            }
        }
        if (numPlayers <= 1 && buff.hasBuff(self, "vehicle_passenger"))
        {
            buff.removeBuff(self, "vehicle_passenger");
        }
        return;
    }
    public String getOverrideAttack(dictionary dict, boolean isDriver) throws InterruptedException
    {
        if (isDriver)
        {
            return dict.getString("driver_default_attack");
        }
        else 
        {
            return dict.getString("gunner_default_attack");
        }
    }
    public String[] getVehicleCommands(dictionary dict, boolean isDriver) throws InterruptedException
    {
        String commandString = "none";
        if (isDriver)
        {
            commandString = dict.getString("driver_special_list");
        }
        else 
        {
            commandString = dict.getString("gunner_special_list");
        }
        String[] commandList = 
        {
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        };
        if (commandString.equals("none"))
        {
            return commandList;
        }
        String[] parse = split(commandString, ',');
        for (int i = 0; i < parse.length && i < 8; i++)
        {
            commandList[i] = parse[i];
        }
        return commandList;
    }
    public String getVehicleType(obj_id vehicle) throws InterruptedException
    {
        return utils.getTemplateFilenameNoPath(vehicle);
    }
}
