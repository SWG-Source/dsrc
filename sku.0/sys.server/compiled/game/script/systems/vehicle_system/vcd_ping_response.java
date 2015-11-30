package script.systems.vehicle_system;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.utils;

public class vcd_ping_response extends script.base_script
{
    public vcd_ping_response()
    {
    }
    public static final String VCDPING_MESSAGE_VCD_ID_NAME = "vcdId";
    public static final String VCDPING_MESSAGE_VEHICLE_ID_NAME = "vehicleId";
    public static final String VCDPING_MESSAGE_MESSAGE_ID_NAME = "messageId";
    public static final String VCDPING_MESSAGE_RIDER_ID_NAME = "riderId";
    public static final String VCDPING_VEHICLE_MESSAGEHANDLER_POSITIVE_ACK_NAME = "handlePositiveAcknowledgementFromVcd";
    public static final String VCDPING_VEHICLE_MESSAGEHANDLER_NEGATIVE_ACK_NAME = "handleNegativeAcknowledgementFromVcd";
    public static final String VCDPING_VEHICLE_MESSAGEHANDLER_PACK_REQUEST_NAME = "handlePackRequest";
    public static final boolean debug = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            LOG("vcdping-debug", "vcd_ping_response.OnAttach(): self=[" + self + "] entered");
        }
        if (debug)
        {
            LOG("vcdping-debug", "vcd_ping_response.OnAttach(): self=[" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handleVehiclePing(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id pingVehicleId = params.getObjId(VCDPING_MESSAGE_VEHICLE_ID_NAME);
        final obj_id riderId = params.getObjId(VCDPING_MESSAGE_RIDER_ID_NAME);
        final int pingMessageNumber = params.getInt(VCDPING_MESSAGE_MESSAGE_ID_NAME);
        obj_id currentVehicleId = callable.getCDCallable(self);
        if (isIdValid(currentVehicleId))
        {
            if (pingVehicleId == currentVehicleId)
            {
                sendPositiveAcknowledgement(self, pingVehicleId, pingMessageNumber);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (isIdValid(riderId) && isRiderOkayForVcd(self, riderId))
                {
                    LOG("vcd-bug", "VCD id=[" + self + "]: received ping from vehicle id=[" + pingVehicleId + "] with rider id=[" + riderId + "]: VCD thinks vehicle id=[" + currentVehicleId + "] is out.  Reassigning VCD vehicle due to on-board rider and packing vehicle id=[" + currentVehicleId + "].");
                    messageTo(currentVehicleId, VCDPING_VEHICLE_MESSAGEHANDLER_PACK_REQUEST_NAME, null, 1, false);
                    callable.setCDCallable(self, pingVehicleId);
                    sendPositiveAcknowledgement(self, pingVehicleId, pingMessageNumber);
                }
                else 
                {
                    sendNegativeAcknowledgement(self, pingVehicleId, pingMessageNumber, "VCD does not expect vehicle=[" + pingVehicleId + "] to be the called vehicle.  It thinks vehicle=[" + currentVehicleId + "] is called.");
                }
            }
        }
        else 
        {
            if (isIdValid(riderId) && isRiderOkayForVcd(self, riderId))
            {
                LOG("vcd-bug", "VCD id=[" + self + "]: received ping from vehicle id=[" + pingVehicleId + "] with rider id=[" + riderId + "]: VCD thinks no vehicle is out.  Reassigning VCD vehicle due to on-board rider.");
                callable.setCDCallable(self, pingVehicleId);
                sendPositiveAcknowledgement(self, pingVehicleId, pingMessageNumber);
            }
            else 
            {
                sendNegativeAcknowledgement(self, pingVehicleId, pingMessageNumber, "VCD does not expecting its vehicle to exist");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary createResponseDictionary(obj_id vcdId, int messageNumber) throws InterruptedException
    {
        dictionary messageData = new dictionary();
        messageData.put(VCDPING_MESSAGE_VCD_ID_NAME, vcdId);
        messageData.put(VCDPING_MESSAGE_MESSAGE_ID_NAME, messageNumber);
        return messageData;
    }
    public void sendPositiveAcknowledgement(obj_id vcdId, obj_id vehicleId, int messageNumber) throws InterruptedException
    {
        dictionary messageData = createResponseDictionary(vcdId, messageNumber);
        if (debug)
        {
            LOG("vcdping-debug", "vcd_ping_response.sendPositiveAcknowledgement(): vcdId=[" + vcdId + "], vehicleId=[" + vehicleId + "], messageNumber=[" + messageNumber + "]: sending positive ack now.");
        }
        messageTo(vehicleId, VCDPING_VEHICLE_MESSAGEHANDLER_POSITIVE_ACK_NAME, messageData, 1, false);
    }
    public void sendNegativeAcknowledgement(obj_id vcdId, obj_id vehicleId, int messageNumber, String reason) throws InterruptedException
    {
        dictionary messageData = createResponseDictionary(vcdId, messageNumber);
        LOG("vcdping-vcd", "vcd_ping_response.sendNegativeAcknowledgement(): vcdId=[" + vcdId + "], vehicleId=[" + vehicleId + "], messageNumber=[" + messageNumber + "]: sending negative ack now: reason=[" + reason + "].");
        messageTo(vehicleId, VCDPING_VEHICLE_MESSAGEHANDLER_NEGATIVE_ACK_NAME, messageData, 1, false);
    }
    public boolean isRiderOkayForVcd(obj_id vcdId, obj_id riderId) throws InterruptedException
    {
        if (!isIdValid(vcdId) || !isIdValid(riderId))
        {
            return false;
        }
        for (obj_id testObjectId = getContainedBy(vcdId); isIdValid(testObjectId); testObjectId = getContainedBy(testObjectId))
        {
            if (testObjectId == riderId)
            {
                return true;
            }
        }
        return false;
    }
}
