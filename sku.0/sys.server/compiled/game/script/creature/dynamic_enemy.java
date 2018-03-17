package script.creature;

import script.dictionary;
import script.library.chat;
import script.library.groundquests;
import script.location;
import script.obj_id;
import script.string_id;

public class dynamic_enemy extends script.base_script
{
    public dynamic_enemy()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "clientEffect", null, 400, true);
        messageTo(self, "attackPlayer", null, 1, false);
        messageTo(self, "barkAttack", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitateTarget(obj_id self, obj_id victim) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        if (player != victim)
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "expression", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int OnLoiterMoving(obj_id self) throws InterruptedException
    {
        messageTo(self, "clientEffect", null, 3, true);
        return SCRIPT_CONTINUE;
    }
    public int clientEffect(obj_id self, dictionary params) throws InterruptedException
    {
        if (!hasObjVar(self, "clientEffect"))
        {
            messageTo(self, "cleanUp", null, 0, true);
        }
        else 
        {
            playClientEffectObj(self, getStringObjVar(self, "clientEffect"), self, "");
            messageTo(self, "cleanUp", null, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int cleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int expression(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        faceTo(self, player);
        messageTo(self, "barkDefeat", null, 1, false);
        pathTo(self, getLocation(player));
        messageTo(self, "runAwayThenCleanUp", null, 4, false);
        return SCRIPT_CONTINUE;
    }
    public int runAwayThenCleanUp(obj_id self, dictionary params) throws InterruptedException
    {
        pathTo(self, groundquests.getRandom2DLocationAroundPlayer(self, 20, 50));
        messageTo(self, "clientEffect", null, 6, true);
        return SCRIPT_CONTINUE;
    }
    public int attackPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (!isValidId(player) || !exists(player))
        {
            return SCRIPT_CONTINUE;
        }
        startCombat(self, player);
        return SCRIPT_CONTINUE;
    }
    public int barkAttack(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (!hasObjVar(self, "phrase_string_file"))
        {
            return SCRIPT_OVERRIDE;
        }
        String attackString = getStringObjVar(self, "attack_phrase");
        if ((attackString == null) || (attackString.equals("")))
        {
            return SCRIPT_OVERRIDE;
        }
        chat.chat(self, chat.CHAT_SHOUT, chat.MOOD_ANGRY, new string_id(getStringObjVar(self, "phrase_string_file"), attackString));
        return SCRIPT_CONTINUE;
    }
    public int barkDefeat(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null)
        {
            return SCRIPT_CONTINUE;
        }
        else if (!hasObjVar(self, "phrase_string_file"))
        {
            return SCRIPT_OVERRIDE;
        }
        String defeatString = getStringObjVar(self, "defeat_phrase");
        if ((defeatString == null) || (defeatString.equals("")))
        {
            return SCRIPT_OVERRIDE;
        }
        chat.chat(self, chat.CHAT_SHOUT, chat.MOOD_ANGRY, new string_id(getStringObjVar(self, "phrase_string_file"), defeatString));
        return SCRIPT_CONTINUE;
    }
}
