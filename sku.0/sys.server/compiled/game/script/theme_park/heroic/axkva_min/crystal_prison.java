package script.theme_park.heroic.axkva_min;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.ai_lib;
import script.library.buff;
import script.library.trial;

public class crystal_prison extends script.base_script
{
    public crystal_prison()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateTarget", null, 1.0f, false);
        messageTo(self, "cleanupSelf", null, 60.0f, false);
        setupSelf(self);
        trial.markAsTempObject(self, true);
        return SCRIPT_CONTINUE;
    }
    public void setupSelf(obj_id self) throws InterruptedException
    {
        setMaxHitpoints(self, 50000);
        setHitpoints(self, 50000);
        setInvulnerable(self, false);
    }
    public int OnObjectDisabled(obj_id self, obj_id killer) throws InterruptedException
    {
        location death = getLocation(self);
        playClientEffectObj(killer, "clienteffect/combat_explosion_lair_large.cef", self, "");
        playClientEffectLoc(killer, "clienteffect/combat_explosion_lair_large.cef", death, 0);
        setInvulnerable(self, true);
        obj_id[] enemies = getWhoIsTargetingMe(self);
        if (enemies != null && enemies.length > 1)
        {
            for (int i = 0; i < enemies.length; i++)
            {
                if (isPlayer(enemies[i]))
                {
                    setTarget(enemies[i], null);
                    setCombatTarget(enemies[i], null);
                }
            }
        }
        obj_id player = getObjIdObjVar(self, "player");
        if (isIdValid(player) && exists(player) && !isDead(player))
        {
            buff.removeBuff(player, "axkva_crystalize");
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
    public int validateTarget(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isIdValid(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id player = getObjIdObjVar(self, "player");
        if (!isIdValid(player) || !exists(player) || isDead(player))
        {
            messageTo(self, "cleanupSelf", null, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        obj_id axkva = trial.getParent(self);
        if (!isIdValid(axkva) || !exists(axkva) || isDead(axkva) || !ai_lib.isInCombat(axkva))
        {
            messageTo(self, "cleanupSelf", null, 0.0f, false);
            return SCRIPT_CONTINUE;
        }
        queueCommand(axkva, (-673246850), player, "", COMMAND_PRIORITY_DEFAULT);
        messageTo(self, "validateTarget", null, 2.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int cleanupSelf(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = getObjIdObjVar(self, "player");
        if (isIdValid(player) && exists(player) && !isDead(player))
        {
            buff.removeBuff(player, "axkva_crystalize");
        }
        trial.cleanupObject(self);
        return SCRIPT_CONTINUE;
    }
}
