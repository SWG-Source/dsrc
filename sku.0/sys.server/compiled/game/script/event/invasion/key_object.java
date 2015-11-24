package script.event.invasion;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class key_object extends script.base_script
{
    public key_object()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        float expirationTime = getFloatObjVar(self, "event.invasion.keyObject.expirationTime");
        messageTo(self, "cleanUp", null, expirationTime, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public void checkTimeLimit(obj_id self) throws InterruptedException
    {
        float expirationTime = getFloatObjVar(self, "event.invasion.keyObject.expirationTime");
        float timeStamp = getFloatObjVar(self, "event.invasion.keyObject.timeStamp");
        float rightNow = getGameTime();
        float delta = rightNow - timeStamp;
        if (delta > expirationTime)
        {
            destroyObject(self);
        }
        return;
    }
    public int OnAddedToWorld(obj_id self) throws InterruptedException
    {
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id objSpeaker, String strText) throws InterruptedException
    {
        obj_id coordinator = getObjIdObjVar(self, "event.invasion.coordinator");
        if (objSpeaker == coordinator && strText.equals("abortEventNow"))
        {
            messageTo(self, "cleanUp", null, 1, false);
        }
        checkTimeLimit(self);
        return SCRIPT_CONTINUE;
    }
}
