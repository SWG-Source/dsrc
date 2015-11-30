package script.working.dosgood;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.space_dungeon;
import script.library.npe;

public class test extends script.base_script
{
    public test()
    {
    }
    
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(text);
        String arg = st.nextToken();
        if (arg.equals("testing"))
        {
            sendSystemMessageTestingOnly(self, "1... 2... 3...");
        }
        if (arg.equals("path"))
        {
            float x = Float.parseFloat(st.nextToken());
            float y = Float.parseFloat(st.nextToken());
            float z = Float.parseFloat(st.nextToken());
            location destination = new location(x, y, z);
            createClientPathAdvanced(self, getLocation(self), destination, "default");
        }
        return SCRIPT_CONTINUE;
    }

}
