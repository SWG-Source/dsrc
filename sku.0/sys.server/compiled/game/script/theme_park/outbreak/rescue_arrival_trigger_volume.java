package script.theme_park.outbreak;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.attrib;
import script.library.groundquests;

public class rescue_arrival_trigger_volume extends script.base_script
{
    public rescue_arrival_trigger_volume()
    {
    }
    public static final boolean LOGGING_ON = true;
    public static final String SCRIPT_LOG = "outbreak_pathing";
    public static final String TRIGGER_NAME_PREFIX = "rescue_arrival_trigger_";
    public static final String UPDATE_SIGNAL = "update_signal";
    public static final float TRIGGER_RADIUS = 6f;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setAttributeInterested(self, attrib.OUTBREAK_SURVIVOR);
        createTriggerVolume(TRIGGER_NAME_PREFIX + self, TRIGGER_RADIUS, true);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("OnTriggerVolumeEntered INIT: " + whoTriggeredMe);
        if (isPlayer(whoTriggeredMe))
        {
            return SCRIPT_CONTINUE;
        }
        if (hasObjVar(whoTriggeredMe, "sci"))
        {
            CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() whoTriggeredMe: " + whoTriggeredMe + " is a Scientist Survivor. Destroying.");
            messageTo(whoTriggeredMe, "cleanUpNpcTimer", null, 1, false);
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(whoTriggeredMe, "myEscort"))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnTriggerVolumeEntered: NPC has myEscort");
        messageTo(whoTriggeredMe, "creditPlayerRescue", null, 40, false);
        attachScript(whoTriggeredMe, "conversation.survivor_02");
        setInvulnerable(whoTriggeredMe, true);
        setCondition(whoTriggeredMe, CONDITION_CONVERSABLE);
        obj_id player = getObjIdObjVar(whoTriggeredMe, "myEscort");
        if (!isValidId(player))
        {
            CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() Player: " + player + " OID was invalid so the player could not receive the fail signal.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.startSurvivorPathing() the NPC is now invulnerable and waiting for player " + player);
        String signalUpdate = getStringObjVar(whoTriggeredMe, UPDATE_SIGNAL);
        if (signalUpdate == null || signalUpdate.length() <= 0)
        {
            CustomerServiceLog("outbreak_themepark", "survivor_pathing.OnMovePathComplete() The Rescued NPC did not have the appropriate signal to update the players quest for Player: " + player + " (" + getPlayerName(player) + "). The player may not receive credit for the rescue.");
            return SCRIPT_CONTINUE;
        }
        CustomerServiceLog("outbreak_themepark", "survivor_pathing.startSurvivorPathing() the NPC update signal is: " + signalUpdate);
        groundquests.sendSignal(player, signalUpdate);
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeExited(obj_id self, String volumeName, obj_id whoTriggeredMe) throws InterruptedException
    {
        blog("Exited trigger volume: " + whoTriggeredMe);
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (LOGGING_ON)
        {
            LOG(SCRIPT_LOG, msg);
        }
        return true;
    }
}
