package script.theme_park.tatooine.spawnegg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class dera_darklighter extends script.base_script
{
    public dera_darklighter()
    {
    }
    public static final String SCRIPTPATH = "theme_park.tatooine.";
    public static final String[] SCRIPTS = 
    {
        "dera_quest_1.dera",
        "dera_combat_1.dvcm1_dera_convo",
        "dera_combat_2.dvcm2_dera_convo",
        "dera_crafting_1.dvc1_dera_convo",
        "dera_crafting_2.dvc2_dera_convo"
    };
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "theme_park.spawn_egg_npc"))
        {
            messageTo(self, "makeNPC", null, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int NPCDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "makeNPC", null, 1, true);
        return SCRIPT_CONTINUE;
    }
    public int makeNPC(obj_id self, dictionary params) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id myNPC = create.object("dera_darklighter", here);
        setObjVar(myNPC, "theme_park.spawn_egg", self);
        attachScript(myNPC, "theme_park.spawn_egg.npc_death");
        for (int i = 0; i <= SCRIPTS.length - 1; i++)
        {
            attachScript(myNPC, SCRIPTPATH + SCRIPTS[i]);
        }
        setObjVar(self, "theme_park.spawn_egg_npc", myNPC);
        return SCRIPT_CONTINUE;
    }
}
