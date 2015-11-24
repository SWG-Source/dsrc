package script.city.bestine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class marketplace_npc extends script.base_script
{
    public marketplace_npc()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id marketplaceSpawner = getObjIdObjVar(self, "marketplaceSpawner");
        if (isIdValid(marketplaceSpawner))
        {
            listenToMessage(marketplaceSpawner, "handleRemoveMarketplaceNpcs");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRemoveMarketplaceNpcs(obj_id self, dictionary params) throws InterruptedException
    {
        if (isIdValid(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
