package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.pet_lib;
import script.library.utils;

public class pet_ping extends script.base_script
{
    public pet_ping()
    {
    }
    public static final String PCDPING_PCD_SCRIPT_NAME = "ai.pcd_ping_response";
    public static final String PCDPING_SEND_MESSAGE_NUMBER = "pcdping.sendMsgNumber";
    public static final String PCDPING_LAST_ACK_MESSAGE_NUMBER = "pcdping.lastAckNumber";
    public static final String PCDPING_MESSAGE_PET_ID_NAME = "petId";
    public static final String PCDPING_MESSAGE_MESSAGE_ID_NAME = "messageId";
    public static final String PCDPING_MESSAGE_RIDER_ID_NAME = "riderId";
    public static final String PCDPING_PING_CALLBACK_NAME = "petPingCallback";
    public static final String PCDPING_PCD_MESSAGEHANDLER_NAME = "handlePetPing";
    public static final int PCDPING_MAX_UNACKNOWLEDGED_MESSAGE_COUNT = 3;
    public static final int PCDPING_PING_INTERVAL_INITIAL = 1;
    public static final int PCDPING_PING_INTERVAL_STANDARD = 300;
    public static final int PCDPING_PING_INTERVAL_RETRY = 30;
    public static final boolean debug = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.OnAttach(): self = [" + self + "] entered");
        }
        setObjVar(self, PCDPING_SEND_MESSAGE_NUMBER, 0);
        setObjVar(self, PCDPING_LAST_ACK_MESSAGE_NUMBER, 0);
        messageTo(self, PCDPING_PING_CALLBACK_NAME, null, PCDPING_PING_INTERVAL_INITIAL, false);
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.OnAttach(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int petPingCallback(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.petPingCallback(): self = [" + self + "] entered");
        }
        obj_id player = getRiderId(self);
        if (!isValidId(player) && pet_lib.isMount(self))
        {
            pet_lib.storePet(self);
            return SCRIPT_CONTINUE;
        }
        final int previousMessageNumber = getIntObjVar(self, PCDPING_SEND_MESSAGE_NUMBER);
        final int newMessageNumber = previousMessageNumber + 1;
        setObjVar(self, PCDPING_SEND_MESSAGE_NUMBER, newMessageNumber);
        if (previousMessageNumber == 0)
        {
            ensurePcdHasSisterScript(self);
        }
        final int mostRecentAcknowledgedMessageNumber = getIntObjVar(self, PCDPING_LAST_ACK_MESSAGE_NUMBER);
        final int unacknowledgedGap = previousMessageNumber - mostRecentAcknowledgedMessageNumber;
        if (unacknowledgedGap > PCDPING_MAX_UNACKNOWLEDGED_MESSAGE_COUNT)
        {
            boolean killSuccess = doPingBasedKill(self, "pet received no acknowledgement from PCD, killing pet with unacknowleged gap=[" + unacknowledgedGap + "]. PCD appears to be unloaded.");
            if (killSuccess)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("mounts-bug", "pet_ping.petPingCallback(): doPingBasedKill() failed for pet=[" + self + "], continuing ping loop so that we can try to kill it later.");
            }
        }
        final int nextCallbackInterval = (unacknowledgedGap <= 0) ? PCDPING_PING_INTERVAL_STANDARD : PCDPING_PING_INTERVAL_RETRY;
        obj_id controlDevice = callable.getCallableCD(self);
        final boolean hasValidPcd = isIdValid(controlDevice);
        if (!hasValidPcd)
        {
            boolean killSuccess = doPingBasedKill(self, "pet does not have a valid PCD objvar");
            if (killSuccess)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("mounts-bug", "pet_ping.petPingCallback(): doPingBasedKill() failed for pet=[" + self + "], continuing ping loop so that we can try to kill it later.");
            }
        }
        else 
        {
            dictionary messageData = new dictionary();
            messageData.put(PCDPING_MESSAGE_PET_ID_NAME, self);
            messageData.put(PCDPING_MESSAGE_MESSAGE_ID_NAME, newMessageNumber);
            obj_id riderId = getRiderId(self);
            if (isIdValid(riderId))
            {
                messageData.put(PCDPING_MESSAGE_RIDER_ID_NAME, riderId);
            }
            messageTo(controlDevice, PCDPING_PCD_MESSAGEHANDLER_NAME, messageData, 1, false);
            if (debug)
            {
                LOG("pcdping-debug", "pet_ping.petPingCallback(): self = [" + self + "] sent ping message to PCD.");
            }
        }
        messageTo(self, PCDPING_PING_CALLBACK_NAME, null, nextCallbackInterval, false);
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.petPingCallback(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePositiveAcknowledgementFromPcd(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.handlePositiveAcknowledgementFromPcd(): self = [" + self + "] entered");
        }
        final int ackMessageNumber = params.getInt(PCDPING_MESSAGE_MESSAGE_ID_NAME);
        final int mostRecentAckMessageNumber = getIntObjVar(self, PCDPING_LAST_ACK_MESSAGE_NUMBER);
        if (ackMessageNumber > mostRecentAckMessageNumber)
        {
            setObjVar(self, PCDPING_LAST_ACK_MESSAGE_NUMBER, ackMessageNumber);
            if (debug)
            {
                LOG("pcdping-debug", "pet_ping.handlePositiveAcknowledgementFromPcd(): processed newer positive PCD ack number=[" + ackMessageNumber + "]");
            }
        }
        else 
        {
            if (debug)
            {
                LOG("pcdping-debug", "pet_ping.handlePositiveAcknowledgementFromPcd(): discarded older positive PCD ack number=[" + ackMessageNumber + "], highest ack is [" + mostRecentAckMessageNumber + "]");
            }
        }
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.handlePositiveAcknowledgementFromPcd(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNegativeAcknowledgementFromPcd(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.handleNegativeAcknowledgementFromPcd(): self = [" + self + "] entered");
        }
        final boolean killSuccess = doPingBasedKill(self, "PCD indicated that this pet should not exist");
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.handleNegativeAcknowledgementFromPcd(): self = [" + self + "] attempted to kill pet per PCD request.  killSuccess=[" + killSuccess + "]");
        }
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.handleNegativeAcknowledgementFromPcd(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doPingBasedKill(obj_id pet, String reason) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.doPingBasedKill(): pet = [" + pet + "] entered");
        }
        LOG("pcdping-kill", "Killing pet id=[" + pet + "]: reason=[" + reason + "]");
        obj_id rider = getRiderId(pet);
        if (isIdValid(rider))
        {
            boolean dismountSuccess = pet_lib.doDismountNow(rider);
            if (!dismountSuccess)
            {
                LOG("mounts-bug", "pet_ping.doPingBasedKill(): creature [" + pet + "], rider [" + rider + "] failed to dismount.  Will still try to kill the pet.");
            }
        }
        obj_id petControlDevice = callable.getCallableCD(pet);
        pet_lib.savePetInfo(pet, petControlDevice);
        utils.setScriptVar(pet, "stored", true);
        boolean killSuccess = destroyObject(pet);
        if (!killSuccess)
        {
            LOG("pcdping-kill", "failed to destroy pet=[" + pet + "].");
        }
        if (debug)
        {
            LOG("pcdping-debug", "pet_ping.doPingBasedKill(): pet = [" + pet + "] exited");
        }
        return killSuccess;
    }
    public void ensurePcdHasSisterScript(obj_id pet) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(pet);
        if (isIdValid(controlDevice) && !hasScript(controlDevice, PCDPING_PCD_SCRIPT_NAME))
        {
            if (debug)
            {
                LOG("pcdping-debug", "pet_ping.ensurePcdHasSisterScript(): attaching script [" + PCDPING_PCD_SCRIPT_NAME + "] to PCD id=[" + controlDevice + "]");
            }
            attachScript(controlDevice, PCDPING_PCD_SCRIPT_NAME);
        }
        else 
        {
            if (debug)
            {
                LOG("pcdping-debug", "pet_ping.ensurePcdHasSisterScript(): couldn't attach script [" + PCDPING_PCD_SCRIPT_NAME + "] to PCD because didn't have objvar");
            }
        }
    }
}
