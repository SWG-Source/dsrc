package script.quest.hero_of_tatooine;

import script.dictionary;
import script.library.ai_lib;
import script.library.chat;
import script.location;
import script.obj_id;
import script.string_id;

public class pirate_03 extends script.base_script
{
    public pirate_03()
    {
    }
    public static final string_id NPC_NAME = new string_id("quest/hero_of_tatooine/npc_names", "pirate_trapped_02");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        setName(self, "");
        setName(self, localize(NPC_NAME));
        messageTo(self, "action01", null, 1.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("statueLoc") && !hasObjVar(self, "statueReached"))
        {
            obj_id building = getTopMostContainer(self);
            obj_id statue = getObjIdObjVar(building, "statue02");
            destroyObject(statue);
            setObjVar(self, "statueReached", 1);
            messageTo(self, "action02", null, 3.0f, false);
            chat.chat(self, "I got me a statue. Teehee!");
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
        obj_id statue = getCellId(building, "livingroom");
        location here = getLocation(self);
        String planet = here.area;
        location statueTarget = new location(-10.62f, 0.30f, -2.10f, planet, statue);
        ai_lib.aiPathTo(self, statueTarget);
        addLocationTarget("statueLoc", statueTarget, 1);
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
