package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.trace;
import script.library.utils;
import script.library.chat;

public class fs_commander_rescue_ai extends script.base_script
{
    public fs_commander_rescue_ai()
    {
    }
    public static final float MAX_LIFE_TIME = 300;
    public static final float MIN_SPOUT_OFF = 20;
    public static final float MAX_SPOUT_OFF = 60;
    public static final int PERCENT_SPOUT_OFF = 5;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "msgBiteTheDust", null, MAX_LIFE_TIME, false);
        messageTo(self, "msgSayItDontSprayIt", null, rand(MIN_SPOUT_OFF, MAX_SPOUT_OFF), false);
        obj_id owner = null;
        owner = getObjIdObjVar(self, "fs_cs.target");
        setMovementPercent(self, 150.0f);
        setMovementRun(self);
        ai_lib.aiFollow(self, owner);
        return SCRIPT_CONTINUE;
    }
    public int msgSayItDontSprayIt(obj_id self, dictionary params) throws InterruptedException
    {
        if (rand(1, 100) >= 100 - PERCENT_SPOUT_OFF)
        {
            chat.chat(self, new string_id("fs_quest_village", "rescue" + rand(1, 10)));
        }
        messageTo(self, "msgSayItDontSprayIt", null, rand(MIN_SPOUT_OFF, MAX_SPOUT_OFF), false);
        return SCRIPT_CONTINUE;
    }
    public int msgBiteTheDust(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
