package script.systems.vehicle_system;

import script.dictionary;
import script.library.callable;
import script.library.pet_lib;
import script.library.utils;
import script.library.vehicle;
import script.obj_id;

public class vehicle_ping extends script.base_script
{
    public vehicle_ping()
    {
    }
    public static final String VCDPING_VCD_SCRIPT_NAME = "systems.vehicle_system.vcd_ping_response";
    public static final String VCDPING_SEND_MESSAGE_NUMBER = "vcdping.sendMsgNumber";
    public static final String VCDPING_LAST_ACK_MESSAGE_NUMBER = "vcdping.lastAckNumber";
    public static final String VCDPING_MESSAGE_VEHICLE_ID_NAME = "vehicleId";
    public static final String VCDPING_MESSAGE_MESSAGE_ID_NAME = "messageId";
    public static final String VCDPING_MESSAGE_RIDER_ID_NAME = "riderId";
    public static final String VCDPING_PING_CALLBACK_NAME = "vehiclePingCallback";
    public static final String VCDPING_VCD_MESSAGEHANDLER_NAME = "handleVehiclePing";
    public static final int VCDPING_MAX_UNACKNOWLEDGED_MESSAGE_COUNT = 3;
    public static final int VCDPING_PING_INTERVAL_INITIAL = 10;
    public static final int VCDPING_PING_INTERVAL_STANDARD = 300;
    public static final int VCDPING_PING_INTERVAL_RETRY = 30;
    public static final boolean debug = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.OnAttach(): self = [" + self + "] entered");
        }
        setObjVar(self, VCDPING_SEND_MESSAGE_NUMBER, 0);
        setObjVar(self, VCDPING_LAST_ACK_MESSAGE_NUMBER, 0);
        messageTo(self, VCDPING_PING_CALLBACK_NAME, null, VCDPING_PING_INTERVAL_INITIAL, false);
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.OnAttach(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int vehiclePingCallback(obj_id self, dictionary params) throws InterruptedException
    {
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.vehiclePingCallback(): self = [" + self + "] entered");
        }
        final int previousMessageNumber = getIntObjVar(self, VCDPING_SEND_MESSAGE_NUMBER);
        final int newMessageNumber = previousMessageNumber + 1;
        setObjVar(self, VCDPING_SEND_MESSAGE_NUMBER, newMessageNumber);
        if (vehicle.isBattlefieldVehicle(self))
        {
            return SCRIPT_CONTINUE;
        }
        if (previousMessageNumber == 0)
        {
            ensureVcdHasSisterScript(self);
        }
        final int mostRecentAcknowledgedMessageNumber = getIntObjVar(self, VCDPING_LAST_ACK_MESSAGE_NUMBER);
        final int unacknowledgedGap = previousMessageNumber - mostRecentAcknowledgedMessageNumber;
        if (unacknowledgedGap > VCDPING_MAX_UNACKNOWLEDGED_MESSAGE_COUNT)
        {
            boolean killSuccess = doPingBasedKill(self, "vehicle received no acknowledgement from VCD, killing vehicle with unacknowleged gap=[" + unacknowledgedGap + "]. VCD appears to be unloaded.");
            if (killSuccess)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("mounts-bug", "vehicle_ping.vehiclePingCallback(): doPingBasedKill() failed for vehicle=[" + self + "], continuing ping loop so that we can try to kill it later.");
            }
        }
        final int nextCallbackInterval = (unacknowledgedGap <= 0) ? VCDPING_PING_INTERVAL_STANDARD : VCDPING_PING_INTERVAL_RETRY;
        obj_id controlDevice = callable.getCallableCD(self);
        if (!isIdValid(controlDevice))
        {
            boolean killSuccess = doPingBasedKill(self, "vehicle does not have a valid VCD objvar");
            if (killSuccess)
            {
                return SCRIPT_CONTINUE;
            }
            else 
            {
                LOG("mounts-bug", "vehicle_ping.vehiclePingCallback(): doPingBasedKill() failed for vehicle=[" + self + "], continuing ping loop so that we can try to kill it later.");
            }
        }
        else 
        {
            dictionary messageData = new dictionary();
            messageData.put(VCDPING_MESSAGE_VEHICLE_ID_NAME, self);
            messageData.put(VCDPING_MESSAGE_MESSAGE_ID_NAME, newMessageNumber);
            obj_id riderId = getRiderId(self);
            if (isIdValid(riderId))
            {
                messageData.put(VCDPING_MESSAGE_RIDER_ID_NAME, riderId);
            }
            messageTo(controlDevice, VCDPING_VCD_MESSAGEHANDLER_NAME, messageData, 1, false);
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_ping.vehiclePingCallback(): self = [" + self + "] sent ping message to VCD.");
            }
        }
        messageTo(self, VCDPING_PING_CALLBACK_NAME, null, nextCallbackInterval, false);
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.vehiclePingCallback(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePositiveAcknowledgementFromVcd(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.handlePositiveAcknowledgementFromVcd(): self = [" + self + "] entered");
        }
        final int ackMessageNumber = params.getInt(VCDPING_MESSAGE_MESSAGE_ID_NAME);
        final int mostRecentAckMessageNumber = getIntObjVar(self, VCDPING_LAST_ACK_MESSAGE_NUMBER);
        if (ackMessageNumber > mostRecentAckMessageNumber)
        {
            if(self == null || self.equals(obj_id.NULL_ID) || !isIdValid(self) || !exists(self)){
                String output = "VCD Ping: ";
                try {
                    output += "Script Exception: invalid object found (vehicle_ping).";
                    output += " Unable to update VCDPing Acknowledgement.";
                    output += " Self is " + (isIdValid(self) ? "" : "not") + " valid.";
                    output += " Self = " + self;
                    output += " Self's Name: " + getName(self);
                    output += " Self's Template: " + getTemplateName(self);
                    output += " Self's Owner: " + getOwner(self);
                    output += " Self's Location: " + getLocation(self).toString();
                }
                catch(Exception e){
                    LOG("vcdping-debug", output);
                }
                return SCRIPT_CONTINUE;
            }
            setObjVar(self, VCDPING_LAST_ACK_MESSAGE_NUMBER, ackMessageNumber);
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_ping.handlePositiveAcknowledgementFromVcd(): processed newer positive VCD ack number=[" + ackMessageNumber + "]");
            }
        }
        else 
        {
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_ping.handlePositiveAcknowledgementFromVcd(): discarded older positive VCD ack number=[" + ackMessageNumber + "], highest ack is [" + mostRecentAckMessageNumber + "]");
            }
        }
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.handlePositiveAcknowledgementFromVcd(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleNegativeAcknowledgementFromVcd(obj_id self, dictionary params) throws InterruptedException
    {
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.handleNegativeAcknowledgementFromVcd(): self = [" + self + "] entered");
        }
        final boolean killSuccess = doPingBasedKill(self, "VCD indicated that this vehicle should not exist");
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.handleNegativeAcknowledgementFromVcd(): self = [" + self + "] attempted to kill vehicle per VCD request.  killSuccess=[" + killSuccess + "]");
        }
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.handleNegativeAcknowledgementFromVcd(): self = [" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public boolean doPingBasedKill(obj_id vehicle, String reason) throws InterruptedException
    {
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.doPingBasedKill(): vehicle = [" + vehicle + "] entered");
        }
        LOG("vcdping-kill", "Killing vehicle id=[" + vehicle + "]: reason=[" + reason + "]");
        obj_id rider = getRiderId(vehicle);
        if (isIdValid(rider))
        {
            boolean dismountSuccess = pet_lib.doDismountNow(rider);
            if (!dismountSuccess)
            {
                LOG("mounts-bug", "vehicle_ping.doPingBasedKill(): creature [" + vehicle + "], rider [" + rider + "] failed to dismount.  Will still try to kill the vehicle.");
            }
        }
        obj_id vehicleControlDevice = callable.getCallableCD(vehicle);
        pet_lib.savePetInfo(vehicle, vehicleControlDevice);
        utils.setScriptVar(vehicle, "stored", true);
        boolean killSuccess = destroyObject(vehicle);
        if (!killSuccess)
        {
            LOG("vcdping-kill", "failed to destroy vehicle=[" + vehicle + "].");
        }
        if (debug)
        {
            LOG("vcdping-debug", "vehicle_ping.doPingBasedKill(): vehicle = [" + vehicle + "] exited");
        }
        return killSuccess;
    }
    public void ensureVcdHasSisterScript(obj_id vehicle) throws InterruptedException
    {
        obj_id controlDevice = callable.getCallableCD(vehicle);
        if (isIdValid(controlDevice) && !hasScript(controlDevice, VCDPING_VCD_SCRIPT_NAME))
        {
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_ping.ensureVcdHasSisterScript(): attaching script [" + VCDPING_VCD_SCRIPT_NAME + "] to VCD id=[" + controlDevice + "]");
            }
            attachScript(controlDevice, VCDPING_VCD_SCRIPT_NAME);
        }
        else 
        {
            if (debug)
            {
                LOG("vcdping-debug", "vehicle_ping.ensureVcdHasSisterScript(): couldn't attach script [" + VCDPING_VCD_SCRIPT_NAME + "] to VCD because didn't have objvar");
            }
        }
    }
}
