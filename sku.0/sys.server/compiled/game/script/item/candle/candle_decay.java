package script.item.candle;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;
import script.library.player_structure;
import script.library.sui;

public class candle_decay extends script.base_script
{
    public candle_decay()
    {
    }
    public static final int DECAY_LOOP_TIME = 30 * 60;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        removeObjVar(self, "timeStamp");
        removeObjVar(self, "decayTime");
        String templateName = getTemplateName(self);
        if (isLit(self) || templateName.indexOf("candlestick") >= 0)
        {
            return SCRIPT_CONTINUE;
        }
        templateName = getTemplateName(self);
        int idx = templateName.indexOf("frn_all_");
        if (idx != -1)
        {
            String newTemplateName = templateName.substring(0, idx) + "frn_all_light_" + templateName.substring(idx + 8);
            obj_id newLight = self;
            if (isInHouse(self))
            {
                newLight = createObjectAt(newTemplateName, self);
                if (isIdValid(newLight))
                {
                    setYaw(newLight, getYaw(self));
                }
            }
            else 
            {
                obj_id container = getContainedBy(self);
                if (isIdValid(container))
                {
                    newLight = createObject(newTemplateName, container, "");
                }
                else 
                {
                    newLight = createObjectAt(newTemplateName, self);
                    if (isIdValid(newLight))
                    {
                        setYaw(newLight, getYaw(self));
                    }
                }
            }
            if (!isIdValid(newLight) || newLight == self)
            {
                detachScript(self, "item.candle.candle_decay");
                return SCRIPT_CONTINUE;
            }
            else 
            {
                attachScript(newLight, "item.candle.candle_decay");
                destroyObject(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public boolean isLit(obj_id light) throws InterruptedException
    {
        String templateName = getTemplateName(light);
        int idx = templateName.indexOf("frn_all_light_");
        return (idx != -1);
    }
    public boolean isInHouse(obj_id light) throws InterruptedException
    {
        obj_id container = getContainedBy(light);
        if (!isIdValid(container))
        {
            return false;
        }
        int got = getGameObjectType(container);
        if (isGameObjectTypeOf(got, GOT_misc_container) || isGameObjectTypeOf(got, GOT_misc_container_wearable) || isGameObjectTypeOf(got, GOT_tool) || isGameObjectTypeOf(got, GOT_installation))
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
}
