package script.theme_park.heroic.exar_kun;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.chat;
import script.library.trial;

public class ct_guard extends script.base_script
{
    public ct_guard()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        trial.setHp(self, 65000);
        setName(self, "a Caretaker Protector");
        return SCRIPT_CONTINUE;
    }
    public int start_boss_buff(obj_id self, dictionary params) throws InterruptedException
    {
        messageTo(self, "buff_boss", trial.getSessionDict(self, "buff"), 0.0f, false);
        buff.applyBuff(self, "caretaker_guard_buff");
        return SCRIPT_CONTINUE;
    }
    public int buff_boss(obj_id self, dictionary params) throws InterruptedException
    {
        if (!trial.verifySession(self, params, "buff"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id boss = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.caretaker");
        if (!isIdValid(boss))
        {
            return SCRIPT_CONTINUE;
        }
        queueCommand(self, (867597929), boss, "", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "buff_boss", trial.getSessionDict(self, "buff"), rand(12.0f, 30.0f), false);
        return SCRIPT_CONTINUE;
    }
    public int activateGuard(obj_id self, dictionary params) throws InterruptedException
    {
        trial.bumpSession(self, "buff");
        buff.removeBuff(self, "caretaker_guard_buff");
        setInvulnerable(self, false);
        obj_id prisoner = getFirstObjectWithScript(getLocation(self), 200.0f, "theme_park.heroic.exar_kun.ct_prisoner");
        faceTo(self, prisoner);
        chat.chat(self, "Do not interfere!");
        kill(prisoner);
        return SCRIPT_CONTINUE;
    }
    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        messageTo(self, "cleanup", null, 3.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanup(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
