package script.object;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class destroy_message extends script.base_script
{
    public destroy_message()
    {
    }
    public int OnIncapacitated(obj_id self, obj_id attacker) throws InterruptedException
    {
        sendMessages(self);
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        sendMessages(self);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        sendMessages(self);
        return SCRIPT_CONTINUE;
    }
    public void sendMessages(obj_id object) throws InterruptedException
    {
        if (!hasObjVar(object, "destroyMessageList"))
        {
            return;
        }
        String[] destroyMessageNames = getStringArrayObjVar(object, "destroyMessageList.handlerNames");
        float[] destroyMessageDelays = getFloatArrayObjVar(object, "destroyMessageList.delays");
        obj_id[] destroyMessageRecipients = getObjIdArrayObjVar(object, "destroyMessageList.recipients");
        removeObjVar(object, "destroyMessageList");
        dictionary parms = new dictionary();
        parms.put("object", object);
        for (int i = 0; i < destroyMessageNames.length; i++)
        {
            messageTo(destroyMessageRecipients[i], destroyMessageNames[i], parms, destroyMessageDelays[i], isObjectPersisted(destroyMessageRecipients[i]));
        }
    }
}
