package script.item.special;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class officer_drop_item extends script.base_script
{
    public officer_drop_item()
    {
    }
    public static final float LIFESPAN = 18000.0f;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float rightNow = getGameTime();
        setObjVar(self, "item.temporary.time_stamp", rightNow);
        float dieTime = getDieTime(LIFESPAN, self);
        if (dieTime < 1)
        {
            dieTime = 1.0f;
        }
        messageTo(self, "cleanUp", null, dieTime, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        float dieTime = getDieTime(LIFESPAN, self);
        if (dieTime < 1)
        {
            dieTime = 1.0f;
        }
        messageTo(self, "cleanUp", null, dieTime, false);
        return SCRIPT_CONTINUE;
    }
    public float getDieTime(float lifeSpan, obj_id tempObject) throws InterruptedException
    {
        float timeStamp = getFloatObjVar(tempObject, "item.temporary.time_stamp");
        float deathStamp = timeStamp + LIFESPAN;
        float rightNow = getGameTime();
        float dieTime = deathStamp - rightNow;
        return dieTime;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        if (self.isBeingDestroyed())
        {
            return SCRIPT_CONTINUE;
        }
        float dieTime = getDieTime(LIFESPAN, self);
        if (dieTime < 1)
        {
            destroyObject(self);
        }
        else 
        {
            messageTo(self, "cleanUp", null, dieTime, false);
        }
        return SCRIPT_CONTINUE;
    }
}
