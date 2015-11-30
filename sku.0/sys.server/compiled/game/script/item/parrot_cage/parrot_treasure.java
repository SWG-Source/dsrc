package script.item.parrot_cage;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.debug;

public class parrot_treasure extends script.base_script
{
    public parrot_treasure()
    {
    }
    public static final int TREASURE_RADIUS = 100;
    public static final String PARROT_TREASURE_SCRIPT = "item.parrot_cage.parrot_treasure";
    public static final String TREASURE_PARTICLE_TEMPLATE = "object/static/particle/particle_magic_sparks.iff";
    public static final string_id TREASURE_FOUND_MESSAGE = new string_id("item_n", "bird_treasure_found");
    public static final String ITEM_ONE = "object/tangible/component/item/electronic_control_unit.iff";
    public static final String ITEM_TWO = "object/tangible/component/item/electronic_energy_distributor.iff";
    public static final String ITEM_THREE = "object/tangible/component/item/electronic_power_conditioner.iff";
    public static final String ITEM_FOUR = "object/tangible/component/item/electronics_gp_module.iff";
    public static final String ITEM_FIVE = "object/tangible/component/item/electronics_memory_module.iff";
    public static final String[] ITEMS = 
    {
        ITEM_ONE,
        ITEM_TWO,
        ITEM_THREE,
        ITEM_FOUR,
        ITEM_FIVE
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############Parrot_treasure script attached############");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############Parrot_treasure script initialized############");
        return SCRIPT_CONTINUE;
    }
    public int createTreasureLocation(obj_id self, dictionary params) throws InterruptedException
    {
        location treasureLoc = (location)params.get("specialLoc");
        addLocationTarget("parrot_treasure", treasureLoc, TREASURE_RADIUS);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String locName) throws InterruptedException
    {
        debug.debugAllMsg("DEBUG", self, "#############TREASURE FOUND!!!############");
        obj_id sparks = createObject(TREASURE_PARTICLE_TEMPLATE, getLocation(self));
        sendSystemMessage(self, TREASURE_FOUND_MESSAGE);
        dictionary params = new dictionary();
        params.put("sparks", sparks);
        messageTo(self, "cleanUp", params, 10, true);
        receiveLoot(self);
        return SCRIPT_CONTINUE;
    }
    public void receiveLoot(obj_id player) throws InterruptedException
    {
        int index = rand(0, 4);
        obj_id inventory = getObjectInSlot(player, "inventory");
        if (inventory == null)
        {
            debug.debugAllMsg("DEBUG", player, "#############Inventory Is NULL!############");
            return;
        }
        createObject(ITEMS[index], inventory, "");
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id sparks = (obj_id)params.get("sparks");
        destroyObject(sparks);
        detachScript(self, PARROT_TREASURE_SCRIPT);
        return SCRIPT_CONTINUE;
    }
}
