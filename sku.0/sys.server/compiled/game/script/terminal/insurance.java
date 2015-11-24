package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.sui;
import script.library.pclib;
import script.library.money;

public class insurance extends script.terminal.base.base_terminal
{
    public insurance()
    {
    }
    public static final String SCRIPTVAR_CONFIRM_ALL = "confirmInsureAll";
    public static final string_id SID_MNU_INSURE = new string_id("sui", "mnu_insure");
    public static final string_id SID_MNU_INSURE_ALL = new string_id("sui", "mnu_insure_all");
    public static final string_id SID_OBJECT_NOT_ACTIVE = new string_id("error_message", "object_not_active");
    public static final string_id SID_UNABLE_TO_INSURE = new string_id("error_message", "unable_to_insure");
    public static final String STF = "terminal_ui";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        destroyObject(self);
        return super.OnInitialize(self);
    }
}
