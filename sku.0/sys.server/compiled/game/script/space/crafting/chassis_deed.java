package script.space.crafting;

import script.library.space_crafting;
import script.library.space_transition;
import script.library.space_utils;
import script.library.utils;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class chassis_deed extends script.base_script
{
    public chassis_deed()
    {
    }
    public static final string_id MNU_CREATE_VEHICLE = new string_id("sui", "create_vehicle");
    public static final String STF = "chassis_npc";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        float hp = getFloatObjVar(self, "ship_chassis.hp");
        if (hp < 0)
        {
            return SCRIPT_CONTINUE;
        }
        float mass = getFloatObjVar(self, "ship_chassis.mass");
        if (hp < 0)
        {
            return SCRIPT_CONTINUE;
        }
        float currentHp = hp;
        if (hasObjVar(self, "ship_chassis.currentHp"))
        {
            float newHp = getFloatObjVar(self, "ship_chassis.currentHp");
            if (newHp > 0)
            {
                currentHp = newHp;
            }
        }
        String type = getStringObjVar(self, "ship_chassis.type");
        if (type == null || type.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "ship_chassis.multifactional") && getBooleanObjVar(self, "ship_chassis.multifactional"))
        {
            if (type.equals("vwing"))
            {
                names[idx] = "reb_pilot_cert_required";
                attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_rebel_navy_starships_02"));
                idx++;
                names[idx] = "imp_pilot_cert_required";
                attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_imperial_navy_starships_02"));
                idx++;
                names[idx] = "neu_pilot_cert_required";
                attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_neutral_starships_02"));
                idx++;
            }
        }
        else 
        {
            names[idx] = "pilotSkillRequired";
            attribs[idx] = space_utils.getSkillRequiredForShip(type);
            idx++;
        }
        names[idx] = "chassisHitpoints";
        attribs[idx] = Float.toString(currentHp) + "/" + Float.toString(hp);
        idx++;
        names[idx] = "chassisMass";
        attribs[idx] = Float.toString(mass);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_CREATE_VEHICLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }

        if (item == menu_info_types.SERVER_MENU1)
        {
            obj_id datapad = utils.getDatapad(player);
            if (utils.getIntScriptVar(self, "chassis_deed.inUse") == 1)
            {
                return SCRIPT_CONTINUE;
            }
            if (isIdValid(datapad))
            {
                utils.setScriptVar(self, "chassis_deed.inUse", 1);
                String type = getStringObjVar(self, "ship_chassis.type");
                float hp = getFloatObjVar(self, "ship_chassis.hp");
                float currentHp = hp;
                if (hasObjVar(self, "ship_chassis.currentHp"))
                {
                    currentHp = getFloatObjVar(self, "ship_chassis.currentHp");
                }
                float mass = getFloatObjVar(self, "ship_chassis.mass");
                obj_id newShip = space_crafting.createChassisFromDeed(player, self, hp, currentHp, mass, type);
                if (!isIdValid(newShip))
                {
                    CustomerServiceLog("ship_deed", "PLAYER: " + player + "(" + getPlayerName(player) + ") attempted and FAILED to create a ship from DEED:" + self + " which provided the Ship Type: " + type + " MASS: " + mass + " MAX CHASSIS HP: " + hp + " and CURRENT CHASSIS HP: " + currentHp);
                    utils.removeScriptVar(self, "chassis_deed.inUse");
                    return SCRIPT_CONTINUE;
                }
                CustomerServiceLog("ship_deed", "PLAYER: " + player + "(" + getPlayerName(player) + ") created SHIP: " + newShip + " from DEED:" + self + " which provided the Ship Type: " + type + " MASS: " + mass + " MAX CHASSIS HP: " + hp + " and CURRENT CHASSIS HP: " + currentHp);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                utils.removeScriptVar(self, "chassis_deed.inUse");
            }
            space_transition.handlePotentialSceneChange(player);
        }
        return SCRIPT_CONTINUE;
    }
}
