package script.theme_park.poi.general;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class poi_pirate_base extends script.theme_park.poi.base
{
    public poi_pirate_base()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        obj_id room1 = getCellId(self, "room1");
        obj_id room2 = getCellId(self, "room2");
        obj_id room3 = getCellId(self, "room3");
        obj_id foyer = getCellId(self, "foyer");
        obj_id mainh = getCellId(self, "mainhall");
        location pirate1 = new location(4.0f, 2.0f, 1.87f, "tatooine", room1);
        location pirate2 = new location(4.23f, 2.0f, -3.65f, "tatooine", room2);
        location pirate3 = new location(-3.72f, 2.0f, -3.68f, "tatooine", room3);
        location pirate4 = new location(-3.78f, 2.0f, 1.37f, "tatooine", foyer);
        location pirate5 = new location(-0.05f, 2.0f, -5.6f, "tatooine", mainh);
        obj_id base = poiCreateObject(self, "tower", "object/building/military/pirate_outpost.iff", 0, 0);
        obj_id p1 = create.object("pirate", pirate1);
        setObjVar(self, "p1", p1);
        setObjVar(p1, "base", self);
        attachScript(p1, "theme_park.poi.tatooine.behavior.pirate_base");
        obj_id p2 = create.object("pirate", pirate2);
        setObjVar(self, "p2", p2);
        setObjVar(p2, "base", self);
        attachScript(p1, "theme_park.poi.tatooine.behavior.pirate_base");
        obj_id p3 = create.object("pirate", pirate3);
        setObjVar(self, "p3", p3);
        setObjVar(p3, "base", self);
        attachScript(p1, "theme_park.poi.tatooine.behavior.pirate_base");
        obj_id p4 = create.object("pirate", pirate4);
        setObjVar(self, "p4", p4);
        setObjVar(p4, "base", self);
        attachScript(p1, "theme_park.poi.tatooine.behavior.pirate_base");
        obj_id p5 = create.object("pirate", pirate5);
        setObjVar(self, "p5", p5);
        setObjVar(p5, "base", self);
        attachScript(p1, "theme_park.poi.tatooine.behavior.pirate_base");
        return SCRIPT_CONTINUE;
    }
    public int attacked(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id p1 = getObjIdObjVar(self, "p1");
        obj_id p2 = getObjIdObjVar(self, "p2");
        obj_id p3 = getObjIdObjVar(self, "p3");
        obj_id p4 = getObjIdObjVar(self, "p4");
        obj_id p5 = getObjIdObjVar(self, "p5");
        obj_id attacker = params.getObjId("attacker");
        startCombat(p1, attacker);
        startCombat(p2, attacker);
        startCombat(p3, attacker);
        startCombat(p4, attacker);
        startCombat(p5, attacker);
        return SCRIPT_CONTINUE;
    }
}
