package script.item.container;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class factory_crate extends script.item.container.remove_only
{
    public factory_crate()
    {
    }
    public static final String SCRIPT_FACTORY_CRATE = "item.container.factory_crate";
    public static final String VAR_TO_ATTACH = "to_attach";
    public static final String VAR_ITEMS_LEFT = "items_left";
    public int OnAboutToLoseItem(obj_id self, obj_id destContainer, obj_id transferer, obj_id item) throws InterruptedException
    {
        return SCRIPT_OVERRIDE;
    }
    public int handleFactoryCrate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_var_list ovl = getObjVarList(self, VAR_TO_ATTACH);
        if (ovl != null)
        {
            for (int i = 0; i < ovl.getNumItems(); i++)
            {
                obj_var ov = ovl.getObjVar(i);
                String ovName = ov.getName();
                java.util.StringTokenizer st = new java.util.StringTokenizer(ovName, ".");
                st.nextToken();
                String ovPath = st.nextToken();
                while (st.hasMoreTokens())
                {
                    ovPath += "." + st.nextToken();
                }
            }
        }
        int itemsLeft = getIntObjVar(self, VAR_ITEMS_LEFT);
        setObjVar(self, VAR_ITEMS_LEFT, itemsLeft);
        return SCRIPT_CONTINUE;
    }
}
