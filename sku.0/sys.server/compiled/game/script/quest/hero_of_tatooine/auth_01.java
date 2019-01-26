package script.quest.hero_of_tatooine;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.location;
import script.obj_id;
import script.string_id;

public class auth_01 extends script.base_script
{
    public auth_01()
    {
    }
    public static final String STF_FILE = "quest/hero_of_tatooine/auth_chat";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        messageTo(self, "action01", null, 10.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("pirateLoc") && !hasObjVar(self, "pirateReached"))
        {
            setObjVar(self, "pirateReached", 1);
            chat.chat(self, new string_id(STF_FILE, "auth_01"));
            obj_id building = getTopMostContainer(self);
            messageTo(building, "piratesCaptured", null, 1.0f, false);
            messageTo(self, "action02", null, 5.0f, false);
        }
        if (name.equals("wifeLoc") && !hasObjVar(self, "wifeReached"))
        {
            obj_id building = getTopMostContainer(self);
            obj_id wife = getObjIdObjVar(building, "wife");
            setObjVar(self, "wifeReached", 1);
            messageTo(self, "action03", null, 2.0f, false);
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
        location pirateTarget = new location(-7.86f, -3.97f, -8.18f, planet, pirate);
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
        obj_id wife = getCellId(building, "livingroom");
        location here = getLocation(self);
        String planet = here.area;
        location wifeTarget = new location(-6.53f, 0.03f, -3.21f, planet, wife);
        ai_lib.aiPathTo(self, wifeTarget);
        addLocationTarget("wifeLoc", wifeTarget, 1);
        return SCRIPT_CONTINUE;
    }
    public int action03(obj_id self, dictionary params) throws InterruptedException
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
