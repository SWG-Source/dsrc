package script.systems.respec;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.respec;
import script.library.utils;

public class grant_respec_on_login extends script.base_script
{
    public grant_respec_on_login()
    {
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        detachScript(self, respec.SCRIPT_GRANT_ON_LOGIN);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        detachScript(self, respec.SCRIPT_GRANT_ON_LOGIN);
        return SCRIPT_CONTINUE;
    }
}
