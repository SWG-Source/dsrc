package script.item.structure_deed.generator_installation;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class power_generator_fusion_deed extends script.base_script
{
    public power_generator_fusion_deed()
    {
    }
    public static final String VERSION = "v1.00.00";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setObjVar(self, "unUsed", 1);
        if (hasObjVar(self, "unUsed"))
        {
        }
        else 
        {
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
        if (mid != null)
        {
            mid.setServerNotify(true);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            if (hasObjVar(self, "usedUp"))
            {
            }
            else 
            {
                location locTest;
                locTest = new location(getLocation(player));
                locTest.x = locTest.x + 5;
                locTest.z = locTest.z + 5;
                String harvesterTemplate = new String("object/installation/generators/power_generator_fusion_style_1.iff");
                obj_id harvesterObject = createObject(harvesterTemplate, locTest);
                setObjVar(self, "usedUp", 1);
                int testInteger;
                if (harvesterObject == null)
                {
                    return SCRIPT_OVERRIDE;
                }
                else 
                {
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
