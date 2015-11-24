package script.systems.buff_builder;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.buff;
import script.library.money;
import script.library.performance;
import script.library.prose;
import script.library.scheduled_drop;
import script.library.utils;

public class buff_builder_response extends script.base_script
{
    public buff_builder_response()
    {
    }
    public static final String DATATABLE_BUFF_BUILDER = "datatables/buff/buff_builder.iff";
    public static final String SCRIPT_BUFF_BUILDER_RESPONSE = "systems.buff_builder.buff_builder_response";
    public static final String SCRIPT_BUFF_BUILDER_CANCEL = "systems.buff_builder.buff_builder_cancel";
    public static final String BUILDABUFF_NAME = "buildabuff_inspiration";
    public static final float BUFF_BUILDER_RANGE = 8.0f;
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_BUFF_BUILDER_RESPONSE))
        {
            detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_BUFF_BUILDER_RESPONSE))
        {
            detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnImmediateLogout(obj_id self) throws InterruptedException
    {
        if (hasScript(self, SCRIPT_BUFF_BUILDER_RESPONSE))
        {
            detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnBuffBuilderValidate(obj_id self, obj_id bufferId, obj_id recipientId, int startingTime, int bufferRequiredCredits, int recipientPaidCredits, boolean accepted, String[] buffComponentKeys, int[] buffComponentValues) throws InterruptedException
    {
        if (!accepted)
        {
            detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
            return SCRIPT_CONTINUE;
        }
        float distance = (getLocation(self)).distance(getLocation(recipientId));
        if (distance > BUFF_BUILDER_RANGE)
        {
            detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
            return SCRIPT_CONTINUE;
        }
        buffBuilderValidated(bufferId, recipientId, startingTime, bufferRequiredCredits, recipientPaidCredits, accepted, buffComponentKeys, buffComponentValues);
        return SCRIPT_CONTINUE;
    }
    public int OnBuffBuilderCompleted(obj_id self, obj_id bufferId, obj_id recipientId, int startingTime, int bufferRequiredCredits, int recipientPaidCredits, boolean accepted, String[] buffComponentKeys, int[] buffComponentValues) throws InterruptedException
    {
        if (bufferRequiredCredits > 0 && !money.pay(recipientId, bufferId, bufferRequiredCredits, "", null))
        {
            sendSystemMessage(recipientId, new string_id("spam", "buildabuff_nsf_buffee"));
            sendSystemMessage(bufferId, new string_id("spam", "buildabuff_nsf_buffer"));
            return SCRIPT_CONTINUE;
        }
        if (buffComponentKeys.length > 0 && buffComponentValues.length > 0)
        {
            utils.setScriptVar(recipientId, "performance.buildabuff.buffComponentKeys", buffComponentKeys);
            utils.setScriptVar(recipientId, "performance.buildabuff.buffComponentValues", buffComponentValues);
            utils.setScriptVar(recipientId, "performance.buildabuff.bufferId", bufferId);
            float currentBuffTime = 30f;
            if (utils.hasScriptVar(recipientId, performance.VAR_PERFORM_INSPIRATION))
            {
                currentBuffTime = utils.getFloatScriptVar(recipientId, performance.VAR_PERFORM_INSPIRATION);
            }
            if (buff.hasBuff(recipientId, "buildabuff_inspiration"))
            {
                buff.removeBuff(recipientId, "buildabuff_inspiration");
            }
            if (bufferId == recipientId)
            {
                currentBuffTime = performance.inspireGetMaxDuration(bufferId);
            }
            buff.applyBuff(recipientId, "buildabuff_inspiration", currentBuffTime);
            if (utils.hasScriptVar(recipientId, "performance.inspireMaxReached"))
            {
                utils.removeScriptVar(recipientId, "performance.inspireMaxReached");
            }
            if (isIdValid(bufferId))
            {
                obj_id inv = utils.getInventoryContainer(bufferId);
                boolean canDrop = scheduled_drop.canDropCard(scheduled_drop.SYSTEM_ENTERTAINER);
                boolean hasDelay = scheduled_drop.hasCardDelay(bufferId, scheduled_drop.SYSTEM_ENTERTAINER);
                if (isGod(bufferId) && hasObjVar(bufferId, "qa_tcg_always_drop"))
                {
                    canDrop = true;
                    hasDelay = false;
                }
                if (isIdValid(inv) && canDrop && !hasDelay && isPlayerActive(bufferId))
                {
                    obj_id card = scheduled_drop.dropCard(scheduled_drop.SYSTEM_ENTERTAINER, inv);
                    if (isIdValid(card))
                    {
                        String[] cardNameList = split(getName(card), ':');
                        if (cardNameList != null && cardNameList.length > 1)
                        {
                            string_id cardName = new string_id(cardNameList[0], cardNameList[1]);
                            String name = getString(cardName);
                            prose_package pp = new prose_package();
                            pp = prose.setStringId(pp, new string_id("spam", "tcg_space_loot"));
                            pp = prose.setTU(pp, name);
                            sendSystemMessageProse(bufferId, pp);
                        }
                    }
                }
                else 
                {
                    if (isGod(bufferId) && hasObjVar(bufferId, "qa_tcg"))
                    {
                        sendSystemMessageTestingOnly(bufferId, "QA TCG ENTERTAINER NOT DROPPED.  Random chance passed? " + canDrop + " Has Card Delay? " + hasDelay);
                    }
                }
                utils.setScriptVar(bufferId, scheduled_drop.PLAYER_SCRIPTVAR_DROP_TIME, getGameTime());
            }
        }
        else 
        {
            if (hasScript(recipientId, SCRIPT_BUFF_BUILDER_CANCEL))
            {
                detachScript(recipientId, SCRIPT_BUFF_BUILDER_CANCEL);
            }
        }
        detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        return SCRIPT_CONTINUE;
    }
    public int OnBuffBuilderCanceled(obj_id self) throws InterruptedException
    {
        detachScript(self, SCRIPT_BUFF_BUILDER_RESPONSE);
        return SCRIPT_CONTINUE;
    }
}
