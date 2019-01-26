package script.quest.hero_of_tatooine;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.location;
import script.obj_id;
import script.string_id;

public class auth_02 extends script.base_script
{
    public auth_02()
    {
    }
    public static final String STF_FILE = "quest/hero_of_tatooine/auth_chat";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        messageTo(self, "action01", null, 7.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("pirateLoc") && !hasObjVar(self, "pirateReached"))
        {
            setObjVar(self, "pirateReached", 1);
            chat.chat(self, new string_id(STF_FILE, "auth_02"));
            messageTo(self, "action02", null, 6.0f, false);
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
        obj_id pirate = getCellId(building, "stair1");
        location here = getLocation(self);
        String planet = here.area;
        location pirateTarget = new location(-5.53f, -3.97f, -8.51f, planet, pirate);
        ai_lib.aiPathTo(self, pirateTarget);
        addLocationTarget("pirateLoc", pirateTarget, 1);
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
