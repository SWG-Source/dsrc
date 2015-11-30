package script.theme_park.recruitment;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.ai.ai_combat;
import script.library.quests;
import script.library.create;
import script.theme_park.tatooine.gating;
import script.library.locations;

public class hutt_quest2 extends script.base_script
{
    public hutt_quest2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location center = new location(-5685, 0, -5915, "tatooine", null);
        location thugs = locations.getGoodLocationAroundLocation(center, 10f, 10f, 367f, 345f);
        setObjVar(self, "hutt.thugsLoc", thugs);
        quests.addQuestLocationTarget(self, "hutt", "thugs", thugs, 15);
        addLocationTarget("spawnThugs", thugs, 100f);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        location thugLoc = getLocationObjVar(self, "hutt.thugsLoc");
        obj_id quester = self;
        if (name.equals("spawnThugs"))
        {
            obj_id thug1 = create.object("thug", thugLoc);
            thugLoc.x = thugLoc.x + 2;
            obj_id thug2 = create.object("thug", thugLoc);
            attachScript(thug2, "theme_park.recruitment.enemy_dead");
            setObjVar(thug2, "killer", self);
            removeLocationTarget("spawnThugs");
            startCombat(thug1, self);
            startCombat(thug2, self);
            return SCRIPT_CONTINUE;
        }
        else if (name.equals("thugs"))
        {
            removeObjVar(self, "hutt.thugsLoc");
            quests.clearQuestLocationTarget(self, "hutt", "thugs");
            return SCRIPT_CONTINUE;
        }
        else if (name.equals("hutt"))
        {
            quests.clearQuestLocationTarget(self, "hutt", "hutt");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int backToHutt(obj_id self, dictionary params) throws InterruptedException
    {
        location palace = new location(-5860, 90, -6176, "tatooine", null);
        quests.addQuestLocationTarget(self, "hutt", "hutt", palace, 20);
        setObjVar(self, "hutt.hutt-two", 1);
        return SCRIPT_CONTINUE;
    }
}
