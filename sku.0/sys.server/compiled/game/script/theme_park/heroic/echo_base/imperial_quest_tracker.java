package script.theme_park.heroic.echo_base;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.groundquests;
import script.library.utils;

public class imperial_quest_tracker extends script.base_script
{
    public imperial_quest_tracker()
    {
    }
    public static final String PHASE = "phase";
    public static final String DEAD_CITIZEN = "citizen";
    public static final String P1_GRANT = "heroic_tusken_tracking_01";
    public static final String P1_COMPLETE = "heroic_tusken_phase1_complete";
    public static final String[] P2_GRANT = 
    {
        "heroic_tusken_tracking_02a",
        "heroic_tusken_tracking_02"
    };
    public static final String P2_COMPLTE = "heroic_tusken_phase2_complete";
    public static final String P3_GRANT = "heroic_tusken_tracking_03";
    public static final String P3_COMPLETE = "heroic_tusken_phase3_complete";
    public static final String[] P1 = 
    {
        "heroic_tusken_cantina",
        "heroic_tusken_starport",
        "heroic_tusken_watto",
        "heroic_tusken_combat",
        "heroic_tusken_university",
        "heroic_tusken_medcenter",
        "heroic_tusken_cloning",
        "heroic_tusken_hotel"
    };
    public static final String[] P2 = 
    {
        "heroic_tusken_starport_crew_01",
        "heroic_tusken_starport_crew_02",
        "heroic_tusken_starport_crew_03",
        "heroic_tusken_watto_crew_01",
        "heroic_tusken_watto_crew_02",
        "heroic_tusken_watto_crew_03",
        "heroic_tusken_combat_crew_01",
        "heroic_tusken_combat_crew_02",
        "heroic_tusken_combat_crew_03",
        "heroic_tusken_university_crew_01",
        "heroic_tusken_university_crew_02",
        "heroic_tusken_university_crew_03",
        "heroic_tusken_medcenter_crew_01",
        "heroic_tusken_medcenter_crew_02",
        "heroic_tusken_medcenter_crew_03",
        "heroic_tusken_cloning_crew_01",
        "heroic_tusken_cloning_crew_02",
        "heroic_tusken_cloning_crew_03"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setObjVar(self, PHASE, 1);
        setObjVar(self, DEAD_CITIZEN, 0);
        setObjVar(self, P3_COMPLETE, 0);
        for (int i = 0; i < P1.length; i++)
        {
            setObjVar(self, P1[i], 0);
        }
        for (int q = 0; q < P2.length; q++)
        {
            setObjVar(self, P2[q], 0);
        }
        return SCRIPT_CONTINUE;
    }
    public int questUpdate(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(getLocation(self), 1000.0f);
        boolean doBroadcast = true;
        if (players == null || players.length == 0)
        {
            doBroadcast = false;
        }
        String update = params.getString("update");
        if (update.equals("citizen_died"))
        {
            handleCitizenDeath(self, players, doBroadcast);
            return SCRIPT_CONTINUE;
        }
        setObjVar(self, update, 1);
        if (doBroadcast)
        {
            groundquests.sendSignal(players, update);
        }
        if (hasCompletedPhaseOne())
        {
            setObjVar(self, PHASE, 2);
            if (doBroadcast)
            {
                groundquests.sendSignal(players, P1_COMPLETE);
                for (int k = 0; k < P2_GRANT.length; k++)
                {
                    groundquests.requestGrantQuest(players, P2_GRANT[k]);
                }
            }
        }
        if (hasCompletedPhaseTwo())
        {
            setObjVar(self, PHASE, 3);
            if (doBroadcast)
            {
                groundquests.sendSignal(players, P2_COMPLTE);
                groundquests.requestGrantQuest(players, P3_GRANT);
            }
        }
        if (hasCompletedPhaseThree())
        {
            setObjVar(self, PHASE, -1);
            if (doBroadcast)
            {
                groundquests.sendSignal(players, P3_COMPLETE);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int getPhase() throws InterruptedException
    {
        return getIntObjVar(getSelf(), PHASE);
    }
    public boolean hasCompletedPhaseOne() throws InterruptedException
    {
        if (getPhase() != 1)
        {
            return false;
        }
        obj_id self = getSelf();
        int total = 0;
        for (int i = 0; i < P1.length; i++)
        {
            total += getIntObjVar(self, P1[i]);
        }
        return total == P1.length;
    }
    public boolean hasCompletedPhaseTwo() throws InterruptedException
    {
        if (getPhase() != 2)
        {
            return false;
        }
        obj_id self = getSelf();
        int total = 0;
        for (int i = 0; i < P2.length; i++)
        {
            total += getIntObjVar(self, P2[i]);
        }
        return total == P2.length;
    }
    public boolean hasCompletedPhaseThree() throws InterruptedException
    {
        if (getPhase() != 3)
        {
            return false;
        }
        obj_id self = getSelf();
        return getIntObjVar(self, P3_COMPLETE) == 1;
    }
    public int requestUpdatePlayer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int phase = getPhase();
        switch (phase)
        {
            case 1:
            groundquests.requestGrantQuest(player, P1_GRANT);
            for (int i = 0; i < P1.length; i++)
            {
                if (getIntObjVar(self, P1[i]) == 1)
                {
                    groundquests.sendSignal(player, P1[i]);
                }
            }
            break;
            case 2:
            for (int i = 0; i < P2_GRANT.length; i++)
            {
                groundquests.requestGrantQuest(player, P2_GRANT[i]);
            }
            for (int q = 0; q < P2.length; q++)
            {
                if (getIntObjVar(self, P2[q]) == 1)
                {
                    groundquests.sendSignal(player, P2[q]);
                }
            }
            int dead_cit = getIntObjVar(self, DEAD_CITIZEN);
            dictionary dict = new dictionary();
            dict.put("creatureName", "heroic_tusken_mos_espa_citizen");
            dict.put("location", getLocation(player));
            dict.put("socialGroup", "espa");
            for (int k = 0; k < dead_cit; k++)
            {
                messageTo(player, "receiveCreditForKill", dict, 0.0f, false);
            }
            break;
            case 3:
            groundquests.requestGrantQuest(player, P3_GRANT);
            if (getIntObjVar(self, P3_COMPLETE) == 1)
            {
                groundquests.sendSignal(player, P3_COMPLETE);
            }
            break;
            case -1:
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void handleCitizenDeath(obj_id self, obj_id[] players, boolean doBroadcast) throws InterruptedException
    {
        int dead = getIntObjVar(self, DEAD_CITIZEN);
        dead++;
        setObjVar(self, DEAD_CITIZEN, dead);
        dictionary dict = new dictionary();
        if (doBroadcast)
        {
            for (int i = 0; i < players.length; i++)
            {
                dict.put("creatureName", "heroic_tusken_mos_espa_citizen");
                dict.put("location", getLocation(players[i]));
                dict.put("socialGroup", "espa");
                messageTo(players[i], "receiveCreditForKill", dict, 0.0f, false);
            }
        }
    }
}
