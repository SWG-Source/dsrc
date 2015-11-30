package script.theme_park.tatooine.bestine_pilots_club;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.hue;
import script.library.colors;
import script.library.ai_lib;
import script.library.factions;
import script.ai.ai;

public class pilot3 extends script.base_script
{
    public pilot3()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "dressed"))
        {
            
        }
        
        {
            obj_id suit = createObject("object/tangible/wearables/bodysuit/bodysuit_tie_fighter.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/boots/boots_s22.iff", self, "");
            hue.hueObject(self);
            pvpSetAlignedFaction(self, (-615855020));
            pvpMakeDeclared(self);
            String hair_table = "datatables/npc_customization/human_male_hair.iff";
            int numHair = dataTableGetNumRows(hair_table);
            int hairCol = dataTableGetNumColumns(hair_table);
            hairCol = hairCol - 1;
            numHair = numHair - 1;
            hairCol = rand(1, hairCol);
            numHair = rand(1, numHair);
            String hair = dataTableGetString(hair_table, numHair, hairCol);
            obj_id hairStyle = createObject(hair, self, "");
            hue.hueObject(hairStyle);
            setObjVar(self, "dressed", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id club = getObjIdObjVar(self, "club");
        messageTo(club, "pilot3Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
