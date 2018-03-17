package script.turnstile;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.turnstile;

public class turnstile_cleanup extends script.base_script
{
    public turnstile_cleanup()
    {
    }
    public static final String HANDLER_EXPIRED_CLEANUP = "handleExpiredCleanup";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, HANDLER_EXPIRED_CLEANUP, null, turnstile.TURNSTILE_CLEANUP_HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, HANDLER_EXPIRED_CLEANUP, null, turnstile.TURNSTILE_CLEANUP_HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int handleExpiredCleanup(obj_id self, dictionary params) throws InterruptedException
    {
        turnstile.cleanupExpiredPatrons(self);
        messageTo(self, HANDLER_EXPIRED_CLEANUP, null, turnstile.TURNSTILE_CLEANUP_HEARTBEAT, false);
        return SCRIPT_CONTINUE;
    }
}
