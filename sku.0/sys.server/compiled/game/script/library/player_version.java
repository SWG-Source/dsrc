package script.library;

import script.obj_id;

import java.util.Vector;

public class player_version extends script.base_script
{
    public player_version()
    {
    }
    public static boolean updateSkills(obj_id player) throws InterruptedException
    {
        return !(!isIdValid(player) || (!isPlayer(player)));
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
            for (String mod : mods) {
                applySkillStatisticModifier(player, mod, -getSkillStatMod(player, mod));
            }
        }
        String[] cmds = getCommandListingForPlayer(player);
        if ((cmds != null) && (cmds.length > 0))
        {
            for (String cmd : cmds) {
                revokeCommand(player, cmd);
            }
        }
        int[] schematics = getSchematicListingForPlayer(player);
        if ((schematics != null) && (schematics.length > 0))
        {
            for (int schematic : schematics) {
                revokeSchematic(player, schematic);
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
        String[] reqs;
        int idx;
        int pos;

        for (String skill : skillList) {
            LOG("playerVersion", "ordering skill = " + skill);
            reqs = getSkillPrerequisiteSkills(skill);
            if ((reqs == null) || (reqs.length == 0)) {
                LOG("playerVersion", "	reqs = null || length = 0... appending...");
                ret.add(skill);
            } else {
                idx = ret.size();
                for (String req : reqs) {
                    LOG("playerVersion", "	testing ret for: " + req);
                    pos = ret.indexOf(req);
                    if (pos > -1) {
                        LOG("playerVersion", "**	found req(" + req + ") at ret idx = " + pos);
                        if (pos < idx) {
                            LOG("playerVersion", "**	updating insert idx to " + pos);
                            idx = pos;
                        }
                    }
                }
                LOG("playerVersion", "	- inserting " + skill + " @ idx = " + idx);
                ret.add(idx, skill);
            }
        }
        if (ret.size() == 0)
        {
            return null;
        }
        String[] retArray = new String[ret.size()];
        ret.toArray(retArray);
        return retArray;
    }
}
