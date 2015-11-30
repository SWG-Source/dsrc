package script.systems.gcw.static_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.utils;

public class master extends script.base_script
{
    public master()
    {
    }
    public static final int NO_CONTROL = 0;
    public static final int IMPERIAL_CONTROL = 1;
    public static final int REBEL_CONTROL = 2;
    public static final String VAR_TERMINAL_IDS = "gcw.static_base.terminal_ids";
    public static final String VAR_TERMINAL_STATUS = "gcw.static_base.terminal_status";
    public static final String VAR_MASTER = "gcw.static_base.master";
    public static final String VAR_BASE_STATUS = "gcw.static_base.base_status";
    public static final String VAR_BASE_LAST_CAPTURE = "gcw.static_base.last_capture";
    public static final String SCRIPT_VAR_VALIDATION = "gcw.static_base.validation";
    public static final String TABLE_TERMINAL_SPAWN = "datatables/gcw/static_base/terminal_spawn.iff";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
