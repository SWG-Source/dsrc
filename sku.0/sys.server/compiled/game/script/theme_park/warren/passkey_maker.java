package script.theme_park.warren;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class passkey_maker extends script.base_script
{
    public passkey_maker()
    {
    }
    public static final String PASSKEYCODE = "object/tangible/mission/quest_item/warren_passkey_s02.iff";
    public static final String SYSTEM_MESSAGES = "theme_park/warren/warren_system_messages";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "warren.passkeyNumber"))
        {
            debugSpeakMsg(self, "WARNING!  I SHOULD HAVE THE warren.passkeyNumber OBJVAR SET ON ME BUT I DON'T");
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, "messageSent", true);
        messageTo(self, "respawnKey", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public void spawnKey(obj_id container) throws InterruptedException
    {
        obj_id[] contents = getContents(container);
        if (contents != null)
        {
            for (int i = 0; i < contents.length; i++)
            {
                if (hasObjVar(contents[i], "warren.passkeyNumber"))
                {
                    for (int j = i + 1; j < contents.length; ++j)
                    {
                        destroyObject(contents[j]);
                    }
                    return;
                }
            }
        }
        obj_id passKey = createObject(PASSKEYCODE, container, "");
        int passKeyNumber = getIntObjVar(container, "warren.passkeyNumber");
        setObjVar(passKey, "warren.passkeyNumber", passKeyNumber);
        setName(passKey, "");
        setName(passKey, new string_id(SYSTEM_MESSAGES, "passkey" + passKeyNumber + "_name"));
    }
    public int respawnKey(obj_id self, dictionary params) throws InterruptedException
    {
        utils.removeScriptVar(self, "messageSent");
        spawnKey(self);
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
                if (hasObjVar(contents[i], "warren.passkeyNumber"))
                {
                    return SCRIPT_CONTINUE;
                }
            }
        }
        messageTo(self, "respawnKey", null, 900, false);
        utils.setScriptVar(self, "messageSent", true);
        return SCRIPT_CONTINUE;
    }
    public int OnOpenedContainer(obj_id self, obj_id player) throws InterruptedException
    {
        if (!hasObjVar(self, "warren.passkeyNumber"))
        {
            debugSpeakMsg(self, "WARNING!  I SHOULD HAVE THE warren.passkeyNumber OBJVAR SET ON ME BUT I DON'T");
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
