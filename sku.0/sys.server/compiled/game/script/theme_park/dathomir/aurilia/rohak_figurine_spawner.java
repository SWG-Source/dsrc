package script.theme_park.dathomir.aurilia;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class rohak_figurine_spawner extends script.base_script
{
    public rohak_figurine_spawner()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnFigurine", null, 9, false);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "spawnFigurine", null, 19, false);
        return SCRIPT_CONTINUE;
    }
    public int spawnFigurine(obj_id self, dictionary params) throws InterruptedException
    {
        if (!utils.hasScriptVar(self, "figurineSpawned"))
        {
            String figurineToSpawn = getStringObjVar(self, "figurineToSpawn");
            String figurineTemplate = "object/tangible/quest/township/" + figurineToSpawn + ".iff";
            transform myTransform = getTransform_o2p(self);
            location locForCell = getLocation(self);
            obj_id figurine = createObject(figurineTemplate, myTransform, locForCell.cell);
            utils.setScriptVar(figurine, "spawner", self);
            utils.setScriptVar(self, "figurineSpawned", true);
        }
        return SCRIPT_CONTINUE;
    }
    public int figurineDestroyed(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "figurineSpawned");
        messageTo(self, "spawnFigurine", null, 2, false);
        return SCRIPT_CONTINUE;
    }
}
