package script.working.rdelashmit;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.guild;

public class guild_test extends script.base_script
{
    public guild_test()
    {
    }
    public obj_id getPlayerByName(obj_id actor, String name) throws InterruptedException
    {
        java.util.StringTokenizer st = new java.util.StringTokenizer(name);
        String compareName = toLower(st.nextToken());
        obj_id[] players = getPlayerCreaturesInRange(actor, 128.0f);
        if (players != null)
        {
            for (int i = 0; i < players.length; ++i)
            {
                java.util.StringTokenizer st2 = new java.util.StringTokenizer(getName(players[i]));
                String playerName = toLower(st2.nextToken());
                if (compareName.equals(playerName))
                {
                    return players[i];
                }
            }
        }
        return obj_id.NULL_ID;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        debugSpeakMsg(self, "guild test script attached.");
        return SCRIPT_CONTINUE;
    }
    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (speaker != self)
        {
            return SCRIPT_CONTINUE;
        }
        if (text.startsWith("guild "))
        {
            java.util.StringTokenizer st = new java.util.StringTokenizer(text);
            st.nextToken();
            int guildId = getGuildId(speaker);
            String tok = st.nextToken();
            if (tok.equals("create"))
            {
                guild.create(speaker, st.nextToken(), st.nextToken());
            }
            else if (tok.equals("disband"))
            {
                guild.disband(guildId, speaker);
            }
            else if (tok.equals("info"))
            {
                guild.dumpInfo(guildId, speaker);
            }
            else if (tok.equals("accept"))
            {
                guild.accept(guildId, speaker, st.nextToken());
            }
            else if (tok.equals("kick"))
            {
                guild.kick(guildId, speaker, st.nextToken());
            }
            else if (tok.equals("title"))
            {
                guild.title(guildId, speaker, st.nextToken(), st.nextToken());
            }
            else if (tok.equals("namechange"))
            {
                guild.nameChange(guildId, speaker, st.nextToken(), st.nextToken());
            }
            else if (tok.equals("allegiance"))
            {
                guild.allegiance(speaker, st.nextToken());
            }
            else if (tok.equals("war"))
            {
                guild.war(guildId, speaker, st.nextToken());
            }
            else if (tok.equals("peace"))
            {
                guild.peace(guildId, speaker, st.nextToken());
            }
            else if (tok.equals("maintain_update"))
            {
                messageTo(getMasterGuildObject(), "forceUpdateGuilds", null, 0, false);
            }
            else if (tok.equals("maintain_namechange"))
            {
                messageTo(getMasterGuildObject(), "forceUpdateGuildNameChanges", null, 0, false);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
