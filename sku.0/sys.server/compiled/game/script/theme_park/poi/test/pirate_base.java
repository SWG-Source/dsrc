package script.theme_park.poi.test;

import script.dictionary;
import script.library.create;
import script.location;
import script.obj_id;

public class pirate_base extends script.theme_park.poi.base
{
    public pirate_base()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("pirate"))
        {
            obj_id base = poiCreateObject("object/building/military/pirate_outpost.iff", 1, 1);
            obj_id room1 = getCellId(base, "room1");
            obj_id room2 = getCellId(base, "room2");
            obj_id room3 = getCellId(base, "room3");
            obj_id foyer = getCellId(base, "foyer");
            obj_id mainh = getCellId(base, "mainhall");
            location pirate1 = new location(4.0f, 2.0f, 1.87f, "tatooine", room1);
            location pirate2 = new location(4.23f, 2.0f, -3.65f, "tatooine", room2);
            location pirate3 = new location(-3.72f, 2.0f, -3.68f, "tatooine", room3);
            location pirate4 = new location(-3.78f, 2.0f, 1.37f, "tatooine", foyer);
            location pirate5 = new location(-0.05f, 2.0f, -5.6f, "tatooine", mainh);
            poiSetDestroyMessage(self, "imDead");
            obj_id p1 = create.object("pirate", pirate1);
            setObjVar(self, "p1", p1);
            setObjVar(p1, "base", self);
            attachScript(p1, "theme_park.poi.test.pirate_death");
            setName(p1, "Pirate Leader");
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
        return SCRIPT_CONTINUE;
    }
    public int imDead(obj_id self, dictionary params) throws InterruptedException
    {
        poiComplete(POI_SUCCESS);
        return SCRIPT_CONTINUE;
    }
}
