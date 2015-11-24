package script.quest.hero_of_tatooine;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.chat;
import script.library.utils;
import script.library.ai_lib;
import script.library.utils;
import script.library.anims;
import script.library.create;
import script.ai.ai_combat;

public class pirate_captured_01 extends script.base_script
{
    public pirate_captured_01()
    {
    }
    public static final String STF_FILE = "quest/pirates";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        setName(self, "Itr Fairla (a captured pirate)");
        messageTo(self, "action01", null, 5f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("wifeLoc") && !hasObjVar(self, "wifeReached"))
        {
            obj_id building = getTopMostContainer(self);
            obj_id wife = getObjIdObjVar(building, "wife");
            setObjVar(self, "wifeReached", 1);
            doAnimationAction(self, "wave_finger_warning");
            chat.chat(self, new string_id(STF_FILE, "ruin_life"));
            messageTo(self, "action02", null, 1f, false);
            chat.chat(wife, "AHH! Keep him away from me!");
            obj_id auth01 = getObjIdObjVar(building, "auth01");
            chat.chat(auth01, "Keep going!");
        }
        if (name.equals("door") && !hasObjVar(self, "doorReached"))
        {
            setObjVar(self, "doorReached", 1);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int action01(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id building = getTopMostContainer(self);
        obj_id wife = getCellId(building, "livingroom");
        location here = getLocation(self);
        String planet = here.area;
        location wifeTarget = new location(-8.07f, 0.31f, -3.29f, planet, wife);
        ai_lib.aiPathTo(self, wifeTarget);
        addLocationTarget("wifeLoc", wifeTarget, 1);
        return SCRIPT_CONTINUE;
    }
    public int action02(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        if (!isIdValid(structure))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id building = getTopMostContainer(self);
        obj_id door = getCellId(building, "hall1");
        location here = getLocation(self);
        String planet = here.area;
        location doorTarget = new location(-6.20f, 0.57f, 10.92f, planet, door);
        ai_lib.aiPathTo(self, doorTarget);
        addLocationTarget("door", doorTarget, 1);
        return SCRIPT_CONTINUE;
    }
}
