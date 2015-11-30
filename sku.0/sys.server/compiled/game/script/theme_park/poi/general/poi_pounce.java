package script.theme_park.poi.general;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;

public class poi_pounce extends script.theme_park.poi.base
{
    public poi_pounce()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        doSpawning(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        doSpawning(self);
        return SCRIPT_CONTINUE;
    }
    public void doSpawning(obj_id self) throws InterruptedException
    {
        String planet = getCurrentSceneName();
        String dangerous = "datatables/herd/dangerous_creatures.iff";
        String[] creatureRows = dataTableGetStringColumnNoDefaults(dangerous, planet);
        String randomAnimal = creatureRows[rand(0, creatureRows.length - 1)];
        String thing = "object/creature/monster/" + randomAnimal + "_large.iff";
        obj_id monster = poiCreateObject(thing, 3, 3);
        obj_id vic1 = poiCreateNpc("townperson", 5, 5);
        obj_id vic2 = poiCreateNpc("townperson", 6, 5);
        obj_id vic3 = poiCreateNpc("townperson", 5, 6);
        ai_lib.setDefaultCalmBehavior(monster, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmBehavior(vic1, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmBehavior(vic2, ai_lib.BEHAVIOR_SENTINEL);
        ai_lib.setDefaultCalmBehavior(vic3, ai_lib.BEHAVIOR_SENTINEL);
        faceTo(monster, vic1);
        faceTo(vic1, monster);
        faceTo(vic2, monster);
        faceTo(vic3, monster);
        setObjVar(monster, "vic1", vic1);
        setObjVar(monster, "vic2", vic2);
        setObjVar(monster, "vic3", vic3);
        setObjVar(vic3, "leader", 1);
        attachScript(vic1, "theme_park.poi.general.behavior.terrified_dialog");
        attachScript(vic2, "theme_park.poi.general.behavior.terrified_dialog");
        attachScript(vic3, "theme_park.poi.general.behavior.terrified_dialog");
        attachScript(monster, "theme_park.poi.general.behavior.pounce_creature");
        setAnimationMood(monster, "threaten");
        return;
    }
}
