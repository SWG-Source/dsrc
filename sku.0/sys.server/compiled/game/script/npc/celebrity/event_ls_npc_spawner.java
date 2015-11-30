package script.npc.celebrity;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.create;
import script.library.ai_lib;

public class event_ls_npc_spawner extends script.base_script
{
    public event_ls_npc_spawner()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "event.lost_squadron.num_rewards"))
        {
            setObjVar(self, "event.lost_squadron.num_rewards", 0);
        }
        String setting = getConfigSetting("EventTeam", "lostSquadron");
        if (setting == null || setting.equals(""))
        {
            return SCRIPT_CONTINUE;
        }
        if (setting.equals("true") || setting.equals("1"))
        {
            String spawn = getStringObjVar(self, "spawns");
            obj_id celeb = create.object(spawn, getLocation(self));
            setObjVar(self, "celeb", celeb);
            setObjVar(celeb, "mom", self);
            if (!isIdValid(celeb))
            {
                return SCRIPT_CONTINUE;
            }
            setInvulnerable(celeb, true);
            ai_lib.setDefaultCalmBehavior(celeb, ai_lib.BEHAVIOR_SENTINEL);
            setCondition(celeb, CONDITION_INTERESTING);
            if (hasObjVar(self, "quest_script"))
            {
                String script = getStringObjVar(self, "quest_script");
                attachScript(celeb, script);
            }
            if (hasObjVar(self, "quest_table"))
            {
                String table = getStringObjVar(self, "quest_table");
                setObjVar(celeb, "quest_table", table);
            }
            if (hasObjVar(self, "npc_name"))
            {
                String name = getStringObjVar(self, "npc_name");
                if (name != null)
                {
                    if (!name.equals(""))
                    {
                        setName(celeb, name);
                    }
                }
            }
        }
        else 
        {
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_CONTINUE;
    }
    public int rewardGiven(obj_id self, dictionary params) throws InterruptedException
    {
        int numRewards = getIntObjVar(self, "event.lost_squadron.num_rewards");
        numRewards++;
        setObjVar(self, "event.lost_squadron.num_rewards", numRewards);
        String reward = params.getString("reward");
        obj_id player = params.getObjId("player");
        String playerName = getName(player);
        int playerGuildId = getGuildId(player);
        String guildName = "NONE";
        if (playerGuildId != 0)
        {
            guildName = guildGetName(playerGuildId);
        }
        CustomerServiceLog("EventPerk", "[Lost Squadron Event] WINNER: Rewarding player [%TU] with " + reward + ". Member of PA (" + guildName + ").", player);
        return SCRIPT_CONTINUE;
    }
}
