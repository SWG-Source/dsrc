package script.npc.vendor;

import script.dictionary;
import script.library.trial;
import script.library.utils;
import script.obj_id;

public class space_duty_vendor extends script.base_script
{
    public space_duty_vendor()
    {
    }
    public int buySpaceDutyItem(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = params.getObjId("player");
        int level = params.getInt("level");
        String component = params.getString("component");
        processItemPurchase(player, level, component);
        return SCRIPT_CONTINUE;
    }
    public void processItemPurchase(obj_id player, int level, String component) throws InterruptedException
    {
        if (level < 1 || level > 10 || !isIdValid(player) || !exists(player) || component == null || component.length() <= 0)
        {
            return;
        }
        int price = trial.getSpaceDutyTokenPrice(level);
        obj_id inventory = utils.getInventoryContainer(player);
        String table = "datatables/item/vendor/space_duty/" + component + ".iff";
        String col = "level" + level;
        String[] compList = dataTableGetStringColumn(table, col);
        if (compList == null)
        {
            return;
        }
        int realLength = 0;
        for (String s : compList) {
            if (s != null && s.length() > 0) {
                realLength++;
            }
        }
        if (price > trial.getTokenTotal(player, trial.SPACE_DUTY_TOKEN))
        {
            sendSystemMessage(player, "Not enough tokens detected.", null);
            return;
        }
        int comp = rand(0, realLength - 1);
        obj_id item = createObject(compList[comp], inventory, "");
        if (!isIdValid(item))
        {
            return;
        }
        if (!trial.purchaseTokenItem(player, price, trial.SPACE_DUTY_TOKEN))
        {
            destroyObject(item);
        }
    }
}
