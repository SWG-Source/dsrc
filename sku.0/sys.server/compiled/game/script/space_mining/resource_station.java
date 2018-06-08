package script.space_mining;

import script.library.money;
import script.library.space_utils;
import script.obj_id;
import script.string_id;

public class resource_station extends script.base_script
{
    public resource_station()
    {
    }
    public static final String SPACE_MINING = "space_mining";
    public int getPricePerUnit(obj_id station, String resourceClassName) throws InterruptedException
    {
        String priceList = getStringObjVar(station, "space_mining.priceList");
        String datatable = "";
        if (!priceList.equals(""))
        {
            datatable = "datatables/space_mining/station_price_list/" + priceList + "_price_list.iff";
        }
        else 
        {
            return 0;
        }
        return dataTableGetInt(datatable, resourceClassName, "price_per_unit");
    }
    public int OnSpaceMiningSellResource(obj_id self, obj_id player, obj_id ship, obj_id station, obj_id resourceId, int amount) throws InterruptedException
    {
        if (getOwner(ship) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (getShipCargoHoldContent(ship, resourceId) >= amount && amount > 0)
        {
            String resourceClassName = getResourceClass(resourceId);
            int pricePerUnit = getPricePerUnit(self, resourceClassName);
            if (pricePerUnit > 0)
            {
                modifyShipCargoHoldContent(ship, resourceId, -amount);
                int cashForPlayer = pricePerUnit * amount;
                money.bankTo("space_resource_sale", player, cashForPlayer);
                space_utils.sendSystemMessageShip(ship, new string_id("space_mining", "sold"), true, true, true, true);
            }
            return SCRIPT_CONTINUE;
        }
        else 
        {
            return SCRIPT_CONTINUE;
        }
    }
}
