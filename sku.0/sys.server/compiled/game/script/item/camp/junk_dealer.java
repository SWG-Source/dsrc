package script.item.camp;

import script.dictionary;
import script.library.create;
import script.obj_id;

public class junk_dealer extends script.base_script
{
    public junk_dealer()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleSpawnJunkDealer", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id dealer = getObjIdObjVar(self, "dealer");
        if (isIdValid(dealer))
        {
            destroyObject(dealer);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSpawnJunkDealer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id parent = getObjIdObjVar(self, "theater.parent");
        if (!isIdValid(parent))
        {
            return SCRIPT_CONTINUE;
        }
        float yaw = getYaw(self);
        obj_id dealer = create.object("junk_dealer", getLocation(self));
        if (!isIdValid(dealer))
        {
            return SCRIPT_CONTINUE;
        }
        setYaw(dealer, yaw);
        int money = (int)getFloatObjVar(parent, "modules.junk_dealer");
        setObjVar(dealer, "money_limit", money);
        setObjVar(self, "dealer", dealer);
        return SCRIPT_CONTINUE;
    }
}
