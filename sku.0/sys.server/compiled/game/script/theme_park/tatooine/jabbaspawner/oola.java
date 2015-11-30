package script.theme_park.tatooine.jabbaspawner;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class oola extends script.base_script
{
    public oola()
    {
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id palace = getObjIdObjVar(self, "palace");
        messageTo(palace, "oolaDied", null, 20, true);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        grantSkill(self, "social_dancer_novice");
        obj_id max = getObjIdObjVar(self, "max");
        obj_id sy = getObjIdObjVar(self, "sy");
        obj_id droopy = getObjIdObjVar(self, "droopy");
        messageTo(self, "startDancing", null, 10, true);
        return SCRIPT_CONTINUE;
    }
    public int startDancing(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id max = getObjIdObjVar(self, "max");
        obj_id sy = getObjIdObjVar(self, "sy");
        obj_id droopy = getObjIdObjVar(self, "droopy");
        ai_lib.setMood(self, "themepark_oola");
        return SCRIPT_CONTINUE;
    }
}
