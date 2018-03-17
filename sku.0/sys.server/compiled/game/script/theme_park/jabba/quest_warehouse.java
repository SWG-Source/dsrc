package script.theme_park.jabba;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.weapons;

public class quest_warehouse extends script.base_script
{
    public quest_warehouse()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "bldgSetup", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int bldgSetup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id room3 = getCellId(self, "room3");
        location chestLoc = new location(-4.5f, 0.13f, -3.23f, "tatooine", room3);
        obj_id thechest = createObject("object/tangible/container/drum/poi_prize_box_off.iff", chestLoc);
        obj_id quester = getObjIdObjVar(self, "player");
        setOwner(thechest, quester);
        setObjVar(thechest, "owner", quester);
        obj_id sword = weapons.createWeapon("object/weapon/melee/sword/sword_rantok.iff", thechest, rand(0.75f, 1.1f));
        attachScript(thechest, "theme_park.jabba.chest_rantok");
        return SCRIPT_CONTINUE;
    }
}
