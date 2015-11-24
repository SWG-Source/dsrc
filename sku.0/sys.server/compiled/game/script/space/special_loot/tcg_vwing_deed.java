package script.space.special_loot;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.utils;

public class tcg_vwing_deed extends script.base_script
{
    public tcg_vwing_deed()
    {
    }
    public static final String LOGGING_CATEGORY = "vwing";
    public static final boolean LOGGING_ON = false;
    public static final string_id MNU_CREATE_VEHICLE = new string_id("sui", "create_vehicle");
    public static final string_id SID_ALREADY_HAVE_VEHICLE = new string_id("sui", "have_ship");
    public static final String SHIP_PCD = "vwing";
    public static final float SHIP_HP = 3000f;
    public static final float SHIP_CURRENT_HP = 3000f;
    public static final float SHIP_MASS = 100000f;
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        names[idx] = "reb_pilot_cert_required";
        attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_rebel_navy_starships_02"));
        idx++;
        names[idx] = "imp_pilot_cert_required";
        attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_imperial_navy_starships_02"));
        idx++;
        names[idx] = "neu_pilot_cert_required";
        attribs[idx] = utils.packStringId(new string_id("skl_n", "pilot_neutral_starships_02"));
        idx++;
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        blog("tcg_vwing_deed.OnObjectMenuRequest: init");
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_CREATE_VEHICLE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        blog("tcg_vwing_deed.OnObjectMenuSelect: init");
        if (!utils.isNestedWithin(self, player))
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.SERVER_MENU1)
        {
            return SCRIPT_CONTINUE;
        }
        blog("tcg_vwing_deed.OnObjectMenuSelect: is SERVER_MENU1");
        obj_id newShip = space_crafting.createChassisFromDeed(player, self, SHIP_HP, SHIP_CURRENT_HP, SHIP_MASS, SHIP_PCD);
        if (!isValidId(newShip) || !exists(newShip))
        {
            CustomerServiceLog("ship_deed", "PLAYER: " + player + "(" + getPlayerName(player) + ") attempted and FAILED to create a ship from DEED:" + self + " which provided the PCD: " + SHIP_PCD + " MASS: " + SHIP_MASS + " MAX CHASSIS HP: " + SHIP_HP + " and CURRENT CHASSIS HP: " + SHIP_CURRENT_HP);
            blog("tcg_vwing_deed.OnObjectMenuSelect: SHIP IS INVALID. ABORTING");
            return SCRIPT_CONTINUE;
        }
        blog("tcg_vwing_deed.OnObjectMenuSelect: SHIP CREATION SUCCESS");
        CustomerServiceLog("ship_deed", "PLAYER: " + player + "(" + getPlayerName(player) + ") created SHIP: " + newShip + " from DEED:" + self + " which provided the PCD: " + SHIP_PCD + " MASS: " + SHIP_MASS + " MAX CHASSIS HP: " + SHIP_HP + " and CURRENT CHASSIS HP: " + SHIP_CURRENT_HP);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(LOGGING_CATEGORY, msg);
        }
        return true;
    }
}
