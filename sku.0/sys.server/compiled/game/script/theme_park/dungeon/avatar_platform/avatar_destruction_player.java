package script.theme_park.dungeon.avatar_platform;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_dungeon;
import script.library.pclib;

public class avatar_destruction_player extends script.base_script
{
    public avatar_destruction_player()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        String name = getTemplateName(structure);
        location here = getLocation(self);
        String planet = here.area;
        if (!planet.equals("kashyyyk_pob_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleExplosionEffect", null, 20f, false);
        messageTo(self, "handleAvatarDestructionPlayer", null, 300f, false);
        playClientEffectObj(self, "clienteffect/avatar_overload_alarm.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        location here = getLocation(self);
        String planet = here.area;
        if (!planet.equals("kashyyyk_pob_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleExplosionEffect", null, 20f, false);
        messageTo(self, "handleAvatarDestructionPlayer", null, 300f, false);
        playClientEffectObj(self, "clienteffect/avatar_overload_alarm.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        String name = getTemplateName(structure);
        location here = getLocation(self);
        String planet = here.area;
        if (!planet.equals("kashyyyk_pob_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            return SCRIPT_CONTINUE;
        }
        messageTo(self, "handleExplosionEffect", null, 20f, false);
        messageTo(self, "handleAvatarDestructionPlayer", null, 300f, false);
        playClientEffectObj(self, "clienteffect/avatar_overload_alarm.cef", self, "");
        return SCRIPT_CONTINUE;
    }
    public int handleExplosionEffect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        location here = getLocation(self);
        String planet = here.area;
        if (!planet.equals("kashyyyk_pob_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, "clienteffect/avatar_explosion_01.cef", getLocation(self), 0.0f);
        playClientEffectObj(self, "clienteffect/avatar_overload_alarm.cef", self, "");
        messageTo(self, "handleExplosionEffect", null, 15f, false);
        obj_id currentCell = getContainedBy(self);
        obj_id[] items = getContents(currentCell);
        if (items == null)
        {
            return SCRIPT_CONTINUE;
        }
        int numItems = items.length;
        if (numItems > 0)
        {
            for (int i = 0; i < numItems; i++)
            {
                if (isNpcCreature(items[i]))
                {
                    int deathRoll = rand(1, 10);
                    if (deathRoll < 6)
                    {
                        kill(items[i]);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAvatarDestructionPlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id structure = getTopMostContainer(self);
        location here = getLocation(self);
        String planet = here.area;
        if (!planet.equals("kashyyyk_pob_dungeons"))
        {
            detachScript(self, "theme_park.dungeon.avatar_platform.avatar_destruction_player");
            return SCRIPT_CONTINUE;
        }
        playClientEffectLoc(self, "clienteffect/avatar_explosion_02.cef", getLocation(self), 0.0f);
        killPlayer(self, structure);
        return SCRIPT_CONTINUE;
    }
    public void killPlayer(obj_id player, obj_id killer) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(killer) || (!isPlayer(player)))
        {
            return;
        }
        
        {
            int dam = -(getAttrib(player, HEALTH) + 50);
            addAttribModifier(player, HEALTH, dam, 0f, 0f, MOD_POOL);
        }
        pclib.coupDeGrace(player, killer, false);
        ejectPlayerFromAvatar(player);
        return;
    }
    public void ejectPlayerFromAvatar(obj_id player) throws InterruptedException
    {
        pclib.resurrectPlayer(player, false);
        space_dungeon.ejectPlayerFromDungeon(player);
        return;
    }
}
