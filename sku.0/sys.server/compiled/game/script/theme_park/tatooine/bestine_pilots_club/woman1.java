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

public class woman1 extends script.base_script
{
    public woman1()
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
            // obj_id suit = createObject("object/tangible/wearables/dress/dress_s30.iff", self, "");
            obj_id boots = createObject("object/tangible/wearables/shoes/shoes_s01.iff", self, "");
            hue.hueObject(self);
            pvpSetAlignedFaction(self, (-615855020));
            pvpMakeDeclared(self);
            String hair_table = "datatables/tangible/wearable/hair/hair_human_female.iff";
            int numHair = dataTableGetNumRows(hair_table);
            numHair = numHair - 1;
            numHair = rand(1, numHair);
            String hair = dataTableGetString(hair_table, numHair, 1);
            obj_id hairStyle = createObject(hair, self, "");
            hue.hueObject(hairStyle);
            setObjVar(self, "dressed", 1);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id club = getObjIdObjVar(self, "club");
        messageTo(club, "woman1Died", null, 20, true);
        return SCRIPT_CONTINUE;
    }
}
