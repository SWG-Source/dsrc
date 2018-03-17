package script.systems.veteran_reward;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.static_item;
import script.library.utils;

public class som_order_rewards extends script.base_script
{
    public som_order_rewards()
    {
    }
    public static final String STF = "som/som_item";
    public static final string_id EXAMINE_MOUNT = new string_id(STF, "generate_lava_flea");
    public static final string_id EXAMINE_VEHICLE = new string_id(STF, "generate_skiff");
    public static final string_id ALREADY = new string_id(STF, "");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleDeedCreate", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDeedCreate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = utils.getContainingPlayer(self);
        if (hasObjVar(self, "reward_type1"))
        {
            String object1 = getStringObjVar(self, "reward_type1");
            obj_id mustFlea = static_item.createNewItemFunction(object1, player);
            obj_id pInv = utils.getInventoryContainer(player);
            obj_id mustHouse = createObjectOverloaded("object/tangible/deed/player_house_deed/mustafar_house_lg.iff", pInv);
            CustomerServiceLog("ToOWRetailReward: ", "(" + player + ")" + getFirstName(player) + " recieved " + "(" + mustFlea + ")" + " Lava Flea and " + "(" + mustHouse + ")" + " Mustafar Underground Bunker");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(self, "reward_type"))
        {
            String object = getStringObjVar(self, "reward_type");
            obj_id TransportSkiff = static_item.createNewItemFunction(object, player);
            CustomerServiceLog("ToOWPreOrderReward: ", "(" + player + ")" + getFirstName(player) + " recieved " + "(" + TransportSkiff + ")" + " Transport Skiff");
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
