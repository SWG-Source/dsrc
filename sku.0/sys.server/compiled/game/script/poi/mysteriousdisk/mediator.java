package script.poi.mysteriousdisk;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.npc;
import script.library.poi;
import script.library.scenario;
import script.library.ai_lib;
import script.library.utils;
import script.ai.ai_combat;

public class mediator extends script.poi.base.scenario_actor
{
    public mediator()
    {
    }
    public static final String SCRIPT_CONVERSE = "npc.converse.npc_converse_menu";
    public static final String LOG_NAME = "poiMysteriousDisk Mediator";
    public static final String ALERT_VOLUME_NAME = "alertTriggerVolume";
    public static final int CONV_GREET = 0;
    public static final int CONV_TALKMEDIATOR = 1;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        attachScript(self, SCRIPT_CONVERSE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(poiMaster, scenario.HANDLER_ACTOR_DEATH, null, 0, false);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        if (hasObjVar(breacher, "gm"))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (breacher == self)
        {
            return SCRIPT_OVERRIDE;
        }
        if (isIncapacitated(self))
        {
            return SCRIPT_OVERRIDE;
        }
        if (!isMob(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (ai_lib.isMonster(breacher))
        {
            return SCRIPT_OVERRIDE;
        }
        if (volumeName.equals(ALERT_VOLUME_NAME))
        {
            if (hasObjVar(self, "ranForHelp"))
            {
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, "ranForHelp", true);
            setObjVar(self, "breacher", breacher);
            faceTo(self, breacher);
            queueCommand(self, (1780871594), breacher, "hail", COMMAND_PRIORITY_DEFAULT);
            messageTo(self, "runToTarget", null, 6, false);
            return SCRIPT_OVERRIDE;
        }
        return SCRIPT_OVERRIDE;
    }
    public int runToTarget(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.aiFollow(self, getObjIdObjVar(self, "breacher"));
        poi.quickSay(self, "m_helpme");
        messageTo(self, "tryToGetHelp1", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int tryToGetHelp1(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "inChat"))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, "m_pleasehelpme");
        messageTo(self, "tryToGetHelp2", null, 10, false);
        return SCRIPT_CONTINUE;
    }
    public int tryToGetHelp2(obj_id self, dictionary params) throws InterruptedException
    {
        if (hasObjVar(self, "inChat"))
        {
            return SCRIPT_CONTINUE;
        }
        poi.quickSay(self, "m_desperatehelpme");
        ai_lib.aiStopFollowing(self);
        messageTo(self, "begForHelp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int begForHelp(obj_id self, dictionary params) throws InterruptedException
    {
        queueCommand(self, (1780871594), null, "grovel", COMMAND_PRIORITY_DEFAULT);
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            return SCRIPT_CONTINUE;
        }
        messageTo(poiMaster, "startFight", null, 20, false);
        return SCRIPT_CONTINUE;
    }
    public int OnStartNpcConversation(obj_id self, obj_id speaker) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        string_id msg = new string_id(convo, "m_greet_0");
        Vector responses = new Vector();
        responses.setSize(0);
        ai_lib.aiStopFollowing(self);
        setObjVar(self, "inChat", true);
        if (progress == CONV_GREET)
        {
            msg = new string_id(convo, "m_greet");
            responses = utils.addElement(responses, new string_id(convo, "r_m_greet_who"));
            npcStartConversation(speaker, self, convo, msg, responses);
        }
        else 
        {
            npcEndConversation(speaker);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String convoName, obj_id speaker, string_id response) throws InterruptedException
    {
        if (ai_lib.isInCombat(self))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id poiMaster = poi.getBaseObject(self);
        if ((poiMaster == null) || (poiMaster == obj_id.NULL_ID))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        String convo = scenario.getConvo();
        if (convo.equals(""))
        {
            npcEndConversation(speaker);
            return SCRIPT_CONTINUE;
        }
        if (!convoName.equals(convo))
        {
            LOG(LOG_NAME, "Convo files do not match!!");
            return SCRIPT_CONTINUE;
        }
        int progress = scenario.getPlayerProgress(speaker);
        String aId = response.getAsciiId();
        string_id msg = new string_id();
        string_id[] responses = new string_id[0];
        npcSetConversationResponses(speaker, responses);
        if ((aId.equals("r_m_greet_who")))
        {
            scenario.setPlayerProgress(speaker, CONV_TALKMEDIATOR);
            obj_id inventory = getObjectInSlot(speaker, "inventory");
            String diskItem = "object/tangible/mission/mission_datadisk.iff";
            obj_id disk = createObject(diskItem, inventory, "");
            setName(disk, "Encrypted Datadisk");
            sendSystemMessage(speaker, "You received an encrypted datadisk!", null);
            npcSpeak(speaker, new string_id(convo, "m_theycome"));
            setObjVar(self, "gaveDisk", true);
            messageTo(poiMaster, "startFight", null, 3, false);
        }
        return SCRIPT_CONTINUE;
    }
}
