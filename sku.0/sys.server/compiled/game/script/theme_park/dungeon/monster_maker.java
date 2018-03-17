package script.theme_park.dungeon;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;

public class monster_maker extends script.base_script
{
    public monster_maker()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume("kwi_spawner", 10.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        if (!isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        else 
        {
            if (hasObjVar(self, "already_spawned"))
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                messageTo(self, "makeMonster", null, 1, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int makeMonster(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "already_spawned", 1);
        String makes = getStringObjVar(self, "makes");
        if (makes == null || makes.equals(""))
        {
            makes = "thug";
        }
        obj_id creature = create.object(makes, getLocation(self));
        if (isIdValid(creature))
        {
            attachScript(creature, "theme_park.dungeon.monster_maker_tracker");
            setObjVar(creature, "parent", self);
        }
        return SCRIPT_CONTINUE;
    }
    public int removeMonsterBlock(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "already_spawned"))
        {
            removeObjVar(self, "already_spawned");
        }
        return SCRIPT_CONTINUE;
    }
}
