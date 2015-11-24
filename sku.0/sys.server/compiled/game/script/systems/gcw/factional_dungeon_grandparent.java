package script.systems.gcw;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.factions;

public class factional_dungeon_grandparent extends script.base_script
{
    public factional_dungeon_grandparent()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String strType = getStringObjVar(self, "type");
        if (strType != null)
        {
            if ((strType.equals(factions.FACTION_REBEL)) || (strType.equals(factions.FACTION_IMPERIAL)))
            {
                setObjVar(self, "strFaction", strType);
            }
            else 
            {
                setObjVar(self, "strFaction", "Rebel");
            }
        }
        else 
        {
            setObjVar(self, "strFaction", "Rebel");
        }
        messageTo(self, "bootStrap", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int bootStrap(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "objParent"))
        {
            obj_id[] objParents = getAllObjectsWithTemplate(getLocation(self), 250, "object/tangible/poi/spawnegg/factional_dungeon_parent_object.iff");
            if ((objParents != null) && (objParents.length != 0))
            {
                messageTo(objParents[0], "resetDungeon", null, 0, true);
                setObjVar(self, "objParent", objParents[0]);
                setObjVar(objParents[0], "intPoints", getIntObjVar(self, "intPoints"));
            }
            else 
            {
                location locTest = getLocation(self);
                locTest.x = locTest.x + 1;
                obj_id objParent = createObject("object/tangible/poi/spawnegg/factional_dungeon_parent_object.iff", locTest);
                persistObject(objParent);
                String strFaction = getStringObjVar(self, "strFaction");
                setObjVar(objParent, "strFaction", strFaction);
                setObjVar(self, "objParent", objParent);
                int intPoints = getIntObjVar(self, "intPoints");
                setObjVar(objParent, "intPoints", intPoints);
                messageTo(objParent, "resetDungeon", null, 10, true);
                factions.changeFactionPoints(strFaction, intPoints);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
