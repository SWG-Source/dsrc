package script.ai;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.callable;
import script.library.utils;

public class pcd_ping_response extends script.base_script
{
    public pcd_ping_response()
    {
    }
    public static final String PCDPING_MESSAGE_PCD_ID_NAME = "pcdId";
    public static final String PCDPING_MESSAGE_PET_ID_NAME = "petId";
    public static final String PCDPING_MESSAGE_MESSAGE_ID_NAME = "messageId";
    public static final String PCDPING_MESSAGE_RIDER_ID_NAME = "riderId";
    public static final String PCDPING_PET_MESSAGEHANDLER_POSITIVE_ACK_NAME = "handlePositiveAcknowledgementFromPcd";
    public static final String PCDPING_PET_MESSAGEHANDLER_NEGATIVE_ACK_NAME = "handleNegativeAcknowledgementFromPcd";
    public static final String PCDPING_PET_MESSAGEHANDLER_PACK_REQUEST_NAME = "handlePackRequest";
    public static final boolean debug = false;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (debug)
        {
            LOG("pcdping-debug", "pcd_ping_response.OnAttach(): self=[" + self + "] entered");
        }
        if (debug)
        {
            LOG("pcdping-debug", "pcd_ping_response.OnAttach(): self=[" + self + "] exited");
        }
        return SCRIPT_CONTINUE;
    }
    public int handlePetPing(obj_id self, dictionary params) throws InterruptedException
    {
        final obj_id pingPetId = params.getObjId(PCDPING_MESSAGE_PET_ID_NAME);
        final obj_id riderId = params.getObjId(PCDPING_MESSAGE_RIDER_ID_NAME);
        final int pingMessageNumber = params.getInt(PCDPING_MESSAGE_MESSAGE_ID_NAME);
        obj_id currentPetId = callable.getCDCallable(self);
        final boolean shouldHavePet = isIdValid(currentPetId);
        if (shouldHavePet)
        {
            if (pingPetId == currentPetId)
            {
                sendPositiveAcknowledgement(self, pingPetId, pingMessageNumber);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                if (isIdValid(riderId) && isRiderOkayForPcd(self, riderId))
                {
                    LOG("pcd-bug", "PCD id=[" + self + "]: received ping from pet id=[" + pingPetId + "] with rider id=[" + riderId + "]: PCD thinks pet id=[" + currentPetId + "] is out.  Reassigning PCD pet due to on-board rider and packing pet id=[" + currentPetId + "].");
                    messageTo(currentPetId, PCDPING_PET_MESSAGEHANDLER_PACK_REQUEST_NAME, null, 1, false);
                    callable.setCDCallable(self, pingPetId);
                    sendPositiveAcknowledgement(self, pingPetId, pingMessageNumber);
                }
                else 
                {
                    sendNegativeAcknowledgement(self, pingPetId, pingMessageNumber, "PCD does not expect pet=[" + pingPetId + "] to be the called pet.  It thinks pet=[" + currentPetId + "] is called.");
                }
            }
        }
        else 
        {
            if (isIdValid(riderId) && isRiderOkayForPcd(self, riderId))
            {
                LOG("pcd-bug", "PCD id=[" + self + "]: received ping from pet id=[" + pingPetId + "] with rider id=[" + riderId + "]: PCD thinks no pet is out.  Reassigning PCD pet due to on-board rider.");
                callable.setCDCallable(self, pingPetId);
                sendPositiveAcknowledgement(self, pingPetId, pingMessageNumber);
            }
            else 
            {
                sendNegativeAcknowledgement(self, pingPetId, pingMessageNumber, "PCD does not expecting its pet to exist");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public dictionary createResponseDictionary(obj_id pcdId, int messageNumber) throws InterruptedException
    {
        dictionary messageData = new dictionary();
        messageData.put(PCDPING_MESSAGE_PCD_ID_NAME, pcdId);
        messageData.put(PCDPING_MESSAGE_MESSAGE_ID_NAME, messageNumber);
        return messageData;
    }
    public void sendPositiveAcknowledgement(obj_id pcdId, obj_id petId, int messageNumber) throws InterruptedException
    {
        dictionary messageData = createResponseDictionary(pcdId, messageNumber);
        if (debug)
        {
            LOG("pcdping-debug", "pcd_ping_response.sendPositiveAcknowledgement(): pcdId=[" + pcdId + "], petId=[" + petId + "], messageNumber=[" + messageNumber + "]: sending positive ack now.");
        }
        messageTo(petId, PCDPING_PET_MESSAGEHANDLER_POSITIVE_ACK_NAME, messageData, 1, false);
    }
    public void sendNegativeAcknowledgement(obj_id pcdId, obj_id petId, int messageNumber, String reason) throws InterruptedException
    {
        dictionary messageData = createResponseDictionary(pcdId, messageNumber);
        LOG("pcdping-pcd", "pcd_ping_response.sendNegativeAcknowledgement(): pcdId=[" + pcdId + "], petId=[" + petId + "], messageNumber=[" + messageNumber + "]: sending negative ack now: reason=[" + reason + "].");
        messageTo(petId, PCDPING_PET_MESSAGEHANDLER_NEGATIVE_ACK_NAME, messageData, 1, false);
    }
    public boolean isRiderOkayForPcd(obj_id pcdId, obj_id riderId) throws InterruptedException
    {
        if (!isIdValid(pcdId) || !isIdValid(riderId))
        {
            return false;
        }
        for (obj_id testObjectId = getContainedBy(pcdId); isIdValid(testObjectId); testObjectId = getContainedBy(testObjectId))
        {
            if (testObjectId == riderId)
            {
                return true;
            }
        }
        return false;
    }
}
