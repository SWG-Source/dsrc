package script.city;

import script.dictionary;
import script.obj_id;

public class interior_2_convo extends script.city.interior_convo_base
{
    public interior_2_convo()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        spawnGuy(self, "guy1");
        spawnGuy(self, "guy2");
        messageTo(self, "checkForScripts", null, 5, false);
        messageTo(self, "handleChatting", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int handleChatting(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id guy1 = getObjIdObjVar(self, "guy1");
        obj_id guy2 = getObjIdObjVar(self, "guy2");
        if (!exists(guy1) || !exists(guy2))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(guy1, guy2);
        faceTo(guy2, guy1);
        setAnimationMood(guy1, "conversation");
        setAnimationMood(guy2, "conversation");
        return SCRIPT_CONTINUE;
    }
}
