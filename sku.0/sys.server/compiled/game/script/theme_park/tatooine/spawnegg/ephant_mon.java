package script.theme_park.tatooine.spawnegg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class ephant_mon extends script.base_script
{
    public ephant_mon()
    {
    }
    public static final String SCRIPTPATH = "theme_park.tatooine.";
    public static final String TEMPLATE = "object/creature/npc/theme_park/ephant_mon.iff";
    public static final String[] SCRIPTS = 
    {
        "ephant_mon_quest.emiq_ephant_convo",
        "max_rebo_quest_1.ephant_mon"
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
        obj_id myNPC = createObjectAt(TEMPLATE, self);
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
