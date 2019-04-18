package script.working.rdelashmit;

import script.library.guild;
import script.obj_id;

import java.util.StringTokenizer;

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
            for (obj_id player : players) {
                StringTokenizer st2 = new StringTokenizer(getName(player));
                String playerName = toLower(st2.nextToken());
                if (compareName.equals(playerName)) {
                    return player;
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
            switch (tok) {
                case "create":
                    guild.create(speaker, st.nextToken(), st.nextToken());
                    break;
                case "disband":
                    guild.disband(guildId, speaker);
                    break;
                case "info":
                    guild.dumpInfo(guildId, speaker);
                    break;
                case "accept":
                    guild.accept(guildId, speaker, st.nextToken());
                    break;
                case "kick":
                    guild.kick(guildId, speaker, st.nextToken());
                    break;
                case "title":
                    guild.title(guildId, speaker, st.nextToken(), st.nextToken());
                    break;
                case "namechange":
                    guild.nameChange(guildId, speaker, st.nextToken(), st.nextToken());
                    break;
                case "allegiance":
                    guild.allegiance(speaker, st.nextToken());
                    break;
                case "war":
                    guild.war(guildId, speaker, st.nextToken());
                    break;
                case "peace":
                    guild.peace(guildId, speaker, st.nextToken());
                    break;
                case "maintain_update":
                    messageTo(getMasterGuildObject(), "forceUpdateGuilds", null, 0, false);
                    break;
                case "maintain_namechange":
                    messageTo(getMasterGuildObject(), "forceUpdateGuildNameChanges", null, 0, false);
                    break;
            }
        }
        return SCRIPT_CONTINUE;
    }
}
