package script.event.ewok_festival;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trial;

public class loveday_disillusion_durni extends script.base_script
{
    public loveday_disillusion_durni()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "handleDurniInitialize", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int handleDurniInitialize(obj_id self, dictionary params) throws InterruptedException
    {
        setObjVar(self, "questFlavorObject", true);
        return SCRIPT_CONTINUE;
    }
    public int handleQuestFlavorObject(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "handleCleanUp", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int handleCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
