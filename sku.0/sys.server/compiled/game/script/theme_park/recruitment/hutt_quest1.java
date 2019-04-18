package script.theme_park.recruitment;

import script.dictionary;
import script.library.create;
import script.library.locations;
import script.library.quests;
import script.location;
import script.obj_id;

public class hutt_quest1 extends script.base_script
{
    public hutt_quest1()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        location center = new location(-5667, 35, -5863, "tatooine", null);
        location twilek = locations.getGoodLocationAroundLocation(center, 10.0f, 10.0f, 100.0f, 100.0f);
        setObjVar(self, "hutt.twilekLoc", twilek);
        quests.addQuestLocationTarget(self, "hutt", "twilek", twilek, 15);
        addLocationTarget("spawnTwilek", twilek, 100.0f);
        return SCRIPT_CONTINUE;
    }
    public int OnArrivedAtLocation(obj_id self, String name) throws InterruptedException
    {
        location twilek = getLocationObjVar(self, "hutt.twilekLoc");
        obj_id quester = self;
        switch (name) {
            case "spawnTwilek":
                obj_id twilekThug = create.object("thug", twilek);
                twilek.x = twilek.x + 2;
                obj_id tent = createObject("object/tangible/camp/camp_tent_s1.iff", twilek);
                twilek.z = twilek.z + 2;
                obj_id campfire = createObject("object/tangible/camp/campfire_logs_burnt.iff", twilek);
                setObjVar(self, "hutt.campfire", campfire);
                setObjVar(self, "hutt.tent", tent);
                setObjVar(self, "hutt.twilek", twilekThug);
                attachScript(twilekThug, "theme_park.recruitment.enemy_dead");
                setObjVar(twilekThug, "killer", self);
                removeLocationTarget("spawnTwilek");
                debugSpeakMsg(twilekThug, "So, another one of Fortuna's puppets comes calling!");
                startCombat(twilekThug, self);
                return SCRIPT_CONTINUE;
            case "twilek":
                removeObjVar(self, "hutt.twilekLoc");
                quests.clearQuestLocationTarget(self, "hutt", "twilek");
                return SCRIPT_CONTINUE;
            case "hutt":
                quests.clearQuestLocationTarget(self, "hutt", "hutt");
                return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public int backToHutt(obj_id self, dictionary params) throws InterruptedException
    {
        location palace = new location(-5860, 90, -6176, "tatooine", null);
        messageTo(self, "cleanUpCamp", null, 30, true);
        quests.addQuestLocationTarget(self, "hutt", "hutt", palace, 20);
        setObjVar(self, "hutt.hutt-one", 1);
        return SCRIPT_CONTINUE;
    }
    public int cleanUpCamp(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id campfire = getObjIdObjVar(self, "hutt.campfire");
        obj_id tent = getObjIdObjVar(self, "hutt.tent");
        destroyObject(campfire);
        destroyObject(tent);
        removeObjVar(self, "hutt.campfire");
        removeObjVar(self, "hutt.tent");
        return SCRIPT_CONTINUE;
    }
}
