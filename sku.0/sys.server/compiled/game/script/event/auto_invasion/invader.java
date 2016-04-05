package script.event.auto_invasion;

import script.dictionary;
import script.library.ai_lib;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class invader extends script.base_script
{
    public invader()
    {
    }
    public static final String DATATABLE = "datatables/event/invasion/ewok_bonus_loot.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "startAttack", null, 1, false);
        messageTo(self, "goDie", null, 3600, false);
        if (hasObjVar(self, "auto_invasion.teh_uber"))
        {
            setScale(self, 5.0f);
            setName(self, utils.packStringId(new string_id("auto_invasion", "ewok_boss_name")));
            int bossBuffLevel = getIntObjVar(self, "auto_invasion.boss_buff_level");
            int buffAmount = bossBuffLevel * bossBuffLevel * 10000;
            addAttribModifier(self, "medical_enhance_health", HEALTH, buffAmount, 10000, 0.0f, 10.0f, true, false, true);
            addAttribModifier(self, "medical_enhance_action", ACTION, buffAmount, 10000, 0.0f, 10.0f, true, false, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int startAttack(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getObjIdObjVar(self, "auto_invasion.target");
        float destinationOffset = getFloatObjVar(target, "auto_invasion.dest_offset");
        ai_lib.aiPathTo(self, utils.getRandomLocationInRing(getLocation(target), destinationOffset, destinationOffset + 10));
        setMovementRun(self);
        return SCRIPT_CONTINUE;
    }
    public int goDie(obj_id self, dictionary params) throws InterruptedException
    {
        if (!ai_lib.isAiDead(self))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        int myNumber = getIntObjVar(self, "auto_invasion.my_number");
        dictionary params = new dictionary();
        params.put("myNumber", myNumber);
        params.put("deadGuy", self);
        obj_id target = getObjIdObjVar(self, "auto_invasion.target");
        removeObjVar(target, "auto_invasion.spawn" + myNumber);
        messageTo(target, "invaderDied", params, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        int chance = rand(1, 200);
        if (hasObjVar(self, "auto_invasion.teh_uber"))
        {
            chance = 200;
        }
        if (chance > 195)
        {
            int roll = rand(1, 100);
            int tableLength = dataTableGetNumRows(DATATABLE);
            if (tableLength < 1)
            {
                return SCRIPT_CONTINUE;
            }
            for (int i = 0; i < tableLength; i++)
            {
                if (dataTableGetInt(DATATABLE, i, "MIN_ROLL") > roll)
                {
                    createObject(dataTableGetString(DATATABLE, i, "LOOT"), utils.getInventoryContainer(self), "");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}
