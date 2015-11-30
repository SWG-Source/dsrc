package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.callable;
import script.library.pet_lib;

public class hand_sampling extends script.base_script
{
    public hand_sampling()
    {
    }
    public static final int BUFF_TICK_TIME = 20;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlerApplySamplerBuff", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handlerApplySamplerBuff", null, 3, false);
        return SCRIPT_CONTINUE;
    }
    public int handlerApplySamplerBuff(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id petControlDevice = callable.getCallableCD(self);
        int powerLevel = getIntObjVar(petControlDevice, "ai.pet.powerLevel");
        if (powerLevel >= pet_lib.OUT_OF_POWER)
        {
            messageTo(self, "handlerApplySamplerBuff", null, BUFF_TICK_TIME, false);
            return SCRIPT_CONTINUE;
        }
        obj_id master = getMaster(self);
        int buffValue = getIntObjVar(self, "module_data.sampling_power");
        buff.applyBuff(master, "droid_hand_sample", 30, buffValue);
        messageTo(self, "handlerApplySamplerBuff", null, BUFF_TICK_TIME, false);
        return SCRIPT_CONTINUE;
    }
}
