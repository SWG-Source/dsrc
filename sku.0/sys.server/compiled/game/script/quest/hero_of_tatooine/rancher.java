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

public class rancher extends script.base_script
{
    public rancher()
    {
    }
    public static final String STF_FILE = "quest/hero_of_tatooine/rancher";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        detachScript(self, "ai.ai_combat");
        setInvulnerable(self, true);
        setName(self, "Roric Goldenfield (a rancher)");
        messageTo(self, "action01", null, 1f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        if (name.equals("wifeLoc") && !hasObjVar(self, "wifeReached"))
        {
            setObjVar(self, "wifeReached", 1);
            chat.chat(self, new string_id(STF_FILE, "you_okay"));
            obj_id building = getTopMostContainer(self);
            obj_id wife = getObjIdObjVar(building, "wife");
            chat.chat(wife, new string_id(STF_FILE, "glad_here"));
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
        location wifeTarget = new location(-6.53f, 0.03f, -3.21f, planet, wife);
        ai_lib.aiPathTo(self, wifeTarget);
        addLocationTarget("wifeLoc", wifeTarget, 1);
        return SCRIPT_CONTINUE;
    }
}
