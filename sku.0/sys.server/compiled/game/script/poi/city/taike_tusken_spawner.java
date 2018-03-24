package script.poi.city;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.ai.ai_combat;

public class taike_tusken_spawner extends script.base_script
{
    public taike_tusken_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "waiting"))
        {
            location here = getStartLocation();
            obj_id tusken1 = create.object("tusken_raider", here);
            here.x = here.x + 2;
            obj_id tusken2 = create.object("tusken_raider", here);
            here = getStartLocation();
            obj_id tusken3 = create.object("tusken_raider", here);
            here.z = here.z + 2;
            obj_id tusken4 = create.object("tusken_raider", here);
            here = getStartLocation();
            obj_id tusken5 = create.object("tusken_raider", here);
            here.x = here.x + 1;
            here.z = here.z + 1;
            obj_id tusken6 = create.object("tusken_raider", here);
            attachScript(tusken1, "toad.tusken");
            attachScript(tusken2, "toad.tusken");
            attachScript(tusken3, "toad.tusken");
            attachScript(tusken4, "toad.tusken");
            attachScript(tusken5, "toad.tusken");
            attachScript(tusken6, "toad.tusken");
            messageTo(tusken1, "goToTheSpot", null, 10, true);
            messageTo(tusken2, "goToTheSpot", null, 10, true);
            messageTo(tusken3, "goToTheSpot", null, 10, true);
            messageTo(tusken4, "goToTheSpot", null, 10, true);
            messageTo(tusken5, "goToTheSpot", null, 10, true);
            messageTo(tusken6, "goToTheSpot", null, 10, true);
            setObjVar(self, "waiting", 1);
            messageTo(self, "resetTuskens", null, rand(300, 1000), true);
        }
        return SCRIPT_CONTINUE;
    }
    public location getStartLocation() throws InterruptedException
    {
        location raid = new location(3802, 13, 2359, "tatooine", null);
        int raidSpot = rand(1, 3);
        switch (raidSpot)
        {
            case 1:
            raid = new location(3802, 13, 2359, "tatooine", null);
            break;
            case 2:
            raid = new location(0, 0, 0, "tatooine", null);
            break;
            case 3:
            raid = new location(0, 0, 0, "tatooine", null);
            break;
        }
        return raid;
    }
    public int resetTuskens(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, "waiting");
        return SCRIPT_CONTINUE;
    }
}
