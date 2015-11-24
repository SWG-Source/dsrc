package script.space.quest_logic;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class delivery_no_pickup extends script.space.quest_logic.delivery
{
    public delivery_no_pickup()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, "delivering", 1);
        setObjVar(self, "delivery_nopickup", 1);
        return SCRIPT_CONTINUE;
    }
    public int deliveryNoPickupAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_OVERRIDE;
        }
        int attackPeriod = getIntObjVar(self, "attackPeriod");
        if (attackPeriod > 0)
        {
            messageTo(self, "launchAttack", null, attackPeriod, false);
        }
        return SCRIPT_OVERRIDE;
    }
}
