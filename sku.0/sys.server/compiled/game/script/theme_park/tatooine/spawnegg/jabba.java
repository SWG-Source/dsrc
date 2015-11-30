package script.theme_park.tatooine.spawnegg;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class jabba extends script.base_script
{
    public jabba()
    {
    }
    public static final String SCRIPTPATH = "theme_park.tatooine.";
    public static final String TEMPLATE = "object/creature/npc/theme_park/jabba_the_hutt.iff";
    public static final String[] SCRIPTS = 
    {
        "jabba_adv_quest_1.jabba",
        "jabba_advanced_quest_2.jaq2_jabba_convo",
        "jabba_advanced_quest_3.jabba_quest3_jabba",
        "jabba_combat_1.jvcm1_jabba_convo",
        "jabba_combat_2.jvcm2_jabba_convo",
        "jabba_veteran_crafting_1.jvc1_jabba_convo",
        "jabba_veteran_crafting_2.jvc2_jabba_convo"
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
