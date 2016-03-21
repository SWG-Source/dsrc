package script.city.bestine;

import script.dictionary;
import script.location;
import script.obj_id;

public class marketplace_grandparent extends script.base_script
{
    public marketplace_grandparent()
    {
    }
    public static final String MASTER_OBJECT = "object/tangible/poi/tatooine/bestine/bestine_marketplace_spawner_object.iff";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "bootStrap", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int bootStrap(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] objMarketplaceSpawners = getAllObjectsWithTemplate(getLocation(self), 10, MASTER_OBJECT);
        if ((objMarketplaceSpawners != null) && (objMarketplaceSpawners.length != 0))
        {
            setObjVar(self, "objMarketplaceSpawner", objMarketplaceSpawners[0]);
        }
        else 
        {
            location locTest = getLocation(self);
            locTest.x = locTest.x + 1;
            obj_id objMarketplaceSpawner = createObject(MASTER_OBJECT, locTest);
            persistObject(objMarketplaceSpawner);

            setObjVar(self, "objMarketplaceSpawner", objMarketplaceSpawner);

            String strDataTable = "datatables/city/bestine_marketplace.iff";
            setObjVar(objMarketplaceSpawner, "strDataTable", strDataTable);
        }
        return SCRIPT_CONTINUE;
    }
}
