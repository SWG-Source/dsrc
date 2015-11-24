package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import java.util.Arrays;
import java.util.Vector;

public class player_version extends script.base_script
{
    public player_version()
    {
    }
    public static boolean updateSkills(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player) || (!isPlayer(player)))
        {
            return false;
        }
        return true;
    }
    public static void zeroPlayerSkillData(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        String[] mods = getSkillStatModListingForPlayer(player);
        if ((mods != null) && (mods.length > 0))
        {
            for (int i = 0; i < mods.length; i++)
            {
                int val = getSkillStatMod(player, mods[i]);
                applySkillStatisticModifier(player, mods[i], -val);
            }
        }
        String[] cmds = getCommandListingForPlayer(player);
        if ((cmds != null) && (cmds.length > 0))
        {
            for (int i = 0; i < cmds.length; i++)
            {
                revokeCommand(player, cmds[i]);
            }
        }
        int[] schematics = getSchematicListingForPlayer(player);
        if ((schematics != null) && (schematics.length > 0))
        {
            for (int i = 0; i < schematics.length; i++)
            {
                revokeSchematic(player, schematics[i]);
            }
        }
    }
    public static String[] orderSkillListForRevoke(String[] skillList) throws InterruptedException
    {
        if ((skillList == null) || (skillList.length == 0))
        {
            return null;
        }
        Vector ret = new Vector();
        for (int i = 0; i < skillList.length; i++)
        {
            LOG("playerVersion", "ordering skill = " + skillList[i]);
            String[] reqs = getSkillPrerequisiteSkills(skillList[i]);
            if ((reqs == null) || (reqs.length == 0))
            {
                LOG("playerVersion", "	reqs = null || length = 0... appending...");
                ret.add(skillList[i]);
            }
            else 
            {
                int idx = ret.size();
                for (int n = 0; n < reqs.length; n++)
                {
                    LOG("playerVersion", "	testing ret for: " + reqs[n]);
                    int pos = ret.indexOf(reqs[n]);
                    if (pos > -1)
                    {
                        LOG("playerVersion", "**	found req(" + reqs[n] + ") at ret idx = " + pos);
                        if (pos < idx)
                        {
                            LOG("playerVersion", "**	updating insert idx to " + pos);
                            idx = pos;
                        }
                    }
                }
                LOG("playerVersion", "	- inserting " + skillList[i] + " @ idx = " + idx);
                ret.add(idx, skillList[i]);
            }
        }
        if ((ret == null) || (ret.size() == 0))
        {
            return null;
        }
        String[] retArray = new String[ret.size()];
        ret.toArray(retArray);
        return retArray;
    }
}
