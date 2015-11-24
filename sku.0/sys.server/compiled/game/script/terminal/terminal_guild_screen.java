package script.terminal;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.guild;
import script.library.player_structure;
import script.library.sui;
import script.library.utils;

public class terminal_guild_screen extends script.base_script
{
    public terminal_guild_screen()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "validateScreen", null, 1, false);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (exists(self))
        {
            int guildId = getIntObjVar(self, guild.REGISTERED_GUILD);
            if (guildId > 0)
            {
                String guildName = guildGetName(guildId);
                if (!guildName.equals(""))
                {
                    names[idx] = "guild_name";
                    attribs[idx] = guildName;
                    idx++;
                }
                String guildAbbrev = guildGetAbbrev(guildId);
                if (!guildName.equals(""))
                {
                    names[idx] = "guild_abbrev";
                    attribs[idx] = guildAbbrev;
                    idx++;
                }
                obj_id guildLeader = guildGetLeader(guildId);
                if (isValidId(guildLeader))
                {
                    names[idx] = "guild_leader";
                    attribs[idx] = guildGetMemberName(guildId, guildLeader);
                    idx++;
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
    {
        obj_id leaderPDA = getObjIdObjVar(self, guild.GUILD_SCREEN_ID);
        if (isIdValid(leaderPDA))
        {
            dictionary dict = new dictionary();
            dict.put("screenId", self);
            messageTo(leaderPDA, "handlerGuildScreenDestroyed", dict, 1, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handlerGuildNewLeader(obj_id self, dictionary params) throws InterruptedException
    {
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
    public int validateScreen(obj_id self, dictionary params) throws InterruptedException
    {
        int guildId = getIntObjVar(self, guild.REGISTERED_GUILD);
        if (!guildExists(guildId))
        {
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
