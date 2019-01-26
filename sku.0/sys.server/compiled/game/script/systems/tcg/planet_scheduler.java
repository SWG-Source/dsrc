package script.systems.tcg;

import script.dictionary;
import script.library.scheduled_drop;
import script.obj_id;

public class planet_scheduler extends script.base_script
{
    public planet_scheduler()
    {
    }
    public int clearPromotions(obj_id self, dictionary params) throws InterruptedException
    {
        String[] promotions = scheduled_drop.getSchedulerPromotions();
        for (String promotion : promotions) {
            removeObjVar(self, "tcg." + promotion + ".count");
        }
        scheduled_drop.removeClusterPromotions();
        scheduled_drop.setLastClusterUpdateTime(getCalendarTime());
        return SCRIPT_CONTINUE;
    }
    public int setPromotion(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String promotion = params.getString("promotion");
        int promotionCount = params.getInt("promotionMaxDrops");
        if (!hasObjVar(self, "tcg." + promotion + ".count"))
        {
            setObjVar(self, "tcg." + promotion + ".count", promotionCount);
        }
        scheduled_drop.setLastClusterUpdateTime(getCalendarTime());
        return SCRIPT_CONTINUE;
    }
    public int reducePromotion(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        String promotion = params.getString("promotionName");
        if (hasObjVar(self, "tcg." + promotion + ".count"))
        {
            int promotionCount = getIntObjVar(self, "tcg." + promotion + ".count");
            if (promotionCount > 0)
            {
                promotionCount--;
                setObjVar(self, "tcg." + promotion + ".count", promotionCount);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
