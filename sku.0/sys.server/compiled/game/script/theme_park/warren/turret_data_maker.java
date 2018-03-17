package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class turret_data_maker extends script.base_script
{
    public turret_data_maker()
    {
    }
    public static final String TURRET_DATA_TEMPLATE = "object/tangible/mission/quest_item/warren_turret_sequence.iff";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(self, "messageSent", true);
        messageTo(self, "respawnTurretData", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnTurretData(obj_id container) throws InterruptedException
    {
        obj_id[] contents = getContents(container);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], "warren.turretData"))
                {
                    for (int j = i + 1; j < contents.length; ++j)
                    {
                        destroyObject(contents[j]);
                    }
                    return;
                }
            }
        }
        obj_id turretData = createObject(TURRET_DATA_TEMPLATE, container, "");
        attachScript(turretData, "theme_park.warren.turret_data");
        setObjVar(turretData, "warren.turretData", true);
        setName(turretData, "");
        setName(turretData, new string_id(SYSTEM_MESSAGES, "turret_data_name"));
    }
    public int respawnTurretData(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "messageSent");
        spawnTurretData(self);
        return SCRIPT_CONTINUE;
    }
    public int OnClosedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "messageSent"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id[] contents = getContents(self);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], "warren.turretData"))
                {
                    removeObjVar(contents[i], "warren.turretCodeSequence");
                    return SCRIPT_CONTINUE;
                }
            }
        }
        messageTo(self, "respawnTurretData", null, 900, false);
        utils.setScriptVar(self, "messageSent", true);
        return SCRIPT_CONTINUE;
    }
}
