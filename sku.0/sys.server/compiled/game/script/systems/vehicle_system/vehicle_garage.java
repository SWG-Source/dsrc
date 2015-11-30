package script.systems.vehicle_system;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.sui;
import script.library.utils;
import script.library.attrib;
import script.library.money;
import script.library.prose;
import script.library.vehicle;
import script.library.city;
import script.library.player_structure;

public class vehicle_garage extends script.base_script
{
    public vehicle_garage()
    {
    }
    public static final String STF_PET = "pet/pet_menu";
    public static final String VOL_GARAGE = "garageRepairEntry";
    public static final string_id SID_PROCESSING_OVERPAYMENT = new string_id(STF_PET, "processing_overpayment");
    public static final string_id SID_REPAIRED_TO_MAX = new string_id(STF_PET, "repaired_to_max");
    public static final string_id SID_REPAIRS_COMPLETE = new string_id(STF_PET, "repairs_complete");
    public static final string_id SID_REPAIR_FAILED_DUE_TO_FUNDS = new string_id(STF_PET, "repair_failed_due_to_funds");
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "planet_map.map_loc"))
        {
            detachScript(self, "planet_map.map_loc");
        }
        if (!hasScript(self, "planet_map.map_loc_both"))
        {
            attachScript(self, "planet_map.map_loc_both");
        }
        createTriggerVolume(VOL_GARAGE, 64f, true);
        setAttributeInterested(self, attrib.VEHICLE);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (volName.equals(VOL_GARAGE) && hasScript(who, "systems.vehicle_system.vehicle_base"))
        {
            enterRepairZone(who);
        }
        return SCRIPT_CONTINUE;
    }
    public void enterRepairZone(obj_id ride) throws InterruptedException
    {
        if (!isIdValid(ride) || utils.hasScriptVar(ride, "inRepairZone"))
        {
            return;
        }
        obj_id owner = getOwner(ride);
        if (!isIdValid(owner))
        {
            return;
        }
        obj_id self = getSelf();
        sendSystemMessage(owner, new string_id(STF_PET, "garage_proximity"));
        utils.setScriptVar(ride, "inRepairZone", self);
    }
    public int OnTriggerVolumeExited(obj_id self, String volName, obj_id who) throws InterruptedException
    {
        if (volName.equals(VOL_GARAGE) && hasScript(who, "systems.vehicle_system.vehicle_base"))
        {
            utils.removeScriptVar(who, "inRepairZone");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRepairConfirm(obj_id self, dictionary params) throws InterruptedException
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
        obj_id vid = utils.getObjIdScriptVar(player, "vehicleRepair.vehicle");
        if (!isGarageInRange(vid))
        {
            sendSystemMessage(player, new string_id(STF_PET, "garage_out_of_range"));
            return SCRIPT_CONTINUE;
        }
        int cost = utils.getIntScriptVar(player, "vehicleRepair.cost");
        if (!isIdValid(vid) || !vid.isLoaded() || cost < 1)
        {
            sendSystemMessage(player, new string_id(STF_PET, "err_repair_data"));
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            return SCRIPT_CONTINUE;
        }
        dictionary d = new dictionary();
        d.put("player", player);
        d.put("vehicle", vid);
        d.put("cost", cost);
        money.requestPayment(player, self, cost, "handleRepairPayment", d, false);
        return SCRIPT_CONTINUE;
    }
    public int handleRepairPayment(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        obj_id vid = params.getObjId("vehicle");
        int cost = params.getInt("cost");
        if (!isIdValid(player) || !isIdValid(vid) || !vid.isLoaded() || cost < 1)
        {
            return SCRIPT_CONTINUE;
        }
        if (params.getInt(money.DICT_CODE) == money.RET_FAIL)
        {
            sendSystemMessage(player, new string_id(STF_PET, "err_repair_fail"));
            String prompt = utils.packStringId(SID_REPAIR_FAILED_DUE_TO_FUNDS);
            sui.msgbox(player, prompt);
            return SCRIPT_CONTINUE;
        }
        int current_cost = vehicle.getVehicleRepairCost(vid);
        if (current_cost > cost)
        {
            prose_package ppConditionChanged = prose.getPackage(new string_id(STF_PET, "repair_condition_changed"), cost);
            sendSystemMessageProse(player, ppConditionChanged);
        }
        int city_take = (int)utils.getFloatScriptVar(vid, "vehicleRepair.city_tax");
        int base_cost = cost - city_take;
        obj_id cityId = utils.getObjIdScriptVar(vid, "vehicleRepair.city_id");
        float repair_rate = vehicle.getVehicleRepairRate(vid);
        int toHeal = Math.round(base_cost / repair_rate);
        int cur_hp = getHitpoints(vid);
        int max_hp = getMaxHitpoints(vid);
        int cur_dam = max_hp - cur_hp;
        int target_hp = cur_hp + toHeal;
        if (cur_dam < toHeal)
        {
            target_hp = max_hp;
            int dam_delta = toHeal - cur_dam;
            int refund = Math.round(dam_delta * repair_rate);
            prose_package ppProcOverpay = prose.getPackage(SID_PROCESSING_OVERPAYMENT);
            prose.setDI(ppProcOverpay, refund);
            sendSystemMessageProse(player, ppProcOverpay);
            transferBankCreditsFromNamedAccount(money.ACCT_VEHICLE_REPAIRS, player, refund, "noHandler", "noHandler", new dictionary());
        }
        obj_id vcd = callable.getCallableCD(vid);
        if (isDisabled(vid))
        {
            clearCondition(vid, CONDITION_DISABLED);
        }
        setHitpoints(vid, target_hp);
        vehicle.storeDamage(vcd, target_hp, 0);
        if (getHitpoints(vid) == getMaxHitpoints(vid))
        {
            sendSystemMessage(player, SID_REPAIRED_TO_MAX);
        }
        else 
        {
            sendSystemMessage(player, SID_REPAIRS_COMPLETE);
        }
        if (isIdValid(cityId))
        {
            transferBankCreditsFromNamedAccount(money.ACCT_VEHICLE_REPAIRS, cityId, city_take, "noHandler", "noHandler", new dictionary());
        }
        transferBankCreditsToNamedAccount(self, money.ACCT_VEHICLE_REPAIRS, cost, "noHandler", "noHandler", new dictionary());
        utils.moneyOutMetric(self, money.ACCT_VEHICLE_REPAIRS, base_cost);
        return SCRIPT_CONTINUE;
    }
    public boolean isGarageInRange(obj_id vehicle) throws InterruptedException
    {
        location loc = getLocation(vehicle);
        float range = 64f;
        obj_id localGarage = null;
        String script = "systems.vehicle_system.vehicle_garage";
        localGarage = getFirstObjectWithScript(loc, range, script);
        if (!isIdValid(localGarage))
        {
            return false;
        }
        return true;
    }
}
