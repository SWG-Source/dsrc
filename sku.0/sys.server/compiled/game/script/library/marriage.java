package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class marriage extends script.base_script
{
    public marriage()
    {
    }
    public static final float PROPOSAL_RANGE = 5f;
    public static final float PROPOSAL_TIME = 120f;
    public static final String SCRIPT_RING_BASE = "item.ring.base";
    public static final String SCRIPT_RING_UNITY = "item.ring.wedding";
    public static final String SCRIPT_UNITY = "player.unity";
    public static final String VAR_MARRIAGE_BASE = "marriage";
    public static final String VAR_SPOUSE_ID = VAR_MARRIAGE_BASE + ".spouseId";
    public static final String VAR_SPOUSE_NAME = VAR_MARRIAGE_BASE + ".spouseName";
    public static final String VAR_RING = VAR_MARRIAGE_BASE + ".ringId";
    public static final String VAR_PROPOSAL_BASE = VAR_MARRIAGE_BASE + ".proposal";
    public static final String VAR_PROPOSAL_TARGET = VAR_PROPOSAL_BASE + ".targetId";
    public static final String VAR_PROPOSAL_NAME = VAR_PROPOSAL_BASE + ".targetName";
    public static final String VAR_PROPOSAL_RING = VAR_PROPOSAL_BASE + ".ringId";
    public static final String VAR_PROPOSEDTO_BASE = VAR_MARRIAGE_BASE + ".proposedTo";
    public static final String VAR_PROPOSEDTO_TARGET = VAR_PROPOSEDTO_BASE + ".targetId";
    public static final String VAR_PROPOSEDTO_NAME = VAR_PROPOSEDTO_BASE + ".targetName";
    public static final String VAR_PROPOSEDTO_RING = VAR_PROPOSEDTO_BASE + ".ringId";
    public static final String VAR_ACCEPT_BASE = VAR_MARRIAGE_BASE + ".accept";
    public static final String VAR_ACCEPT_SUI = VAR_ACCEPT_BASE + ".sui";
    public static final String VAR_ACCEPT_SUI_OPTIONS = VAR_ACCEPT_BASE + ".sui_options";
    public static final String VAR_ACCEPT_RING = VAR_ACCEPT_BASE + ".ringId";
    public static final String HANDLER_PROPOSED_TO = "handleProposedTo";
    public static final String HANDLER_PROPOSAL_ACCEPTED = "handleProposalAccepted";
    public static final String HANDLER_PROPOSAL_EXPIRE = "handleProposalExpire";
    public static final String HANDLER_ACCEPT_FAIL = "handleAcceptFail";
    public static final String HANDLER_PROPOSAL_DECLINED = "handleProposalDeclined";
    public static final String HANDLER_RING_EXCHANGE = "handleRingExchange";
    public static final String HANDLER_RING_SETUP = "handleRingSetup";
    public static final String HANDLER_WED_COMPLETE = "handleWedComplete";
    public static final String HANDLER_WED_ERROR = "handleWedError";
    public static final String HANDLER_DIVORCE = "handleDivorce";
    public static final String STF = "unity";
    public static final string_id MNU_PROPOSE = new string_id(STF, "mnu_propose");
    public static final string_id MNU_DIVORCE = new string_id(STF, "mnu_divorce");
    public static final string_id SID_BAD_TARGET = new string_id(STF, "bad_target");
    public static final string_id SID_BAD_FACING = new string_id(STF, "bad_facing");
    public static final string_id SID_OUT_OF_RANGE = new string_id(STF, "out_of_range");
    public static final string_id PROSE_PROPOSE = new string_id(STF, "prose_propose");
    public static final string_id PROSE_ALREADY_PROPOSED = new string_id(STF, "prose_already_proposed");
    public static final string_id PROSE_ALREADY_MARRIED = new string_id(STF, "prose_already_married");
    public static final string_id SID_TARGET_NOT_PLAYER = new string_id(STF, "target_not_player");
    public static final string_id SID_TARGET_MARRIED = new string_id(STF, "target_married");
    public static final string_id SID_TARGET_PROPOSED = new string_id(STF, "target_proposed");
    public static final string_id SID_NO_RING = new string_id(STF, "no_ring");
    public static final string_id SID_ACCEPT_FAIL = new string_id(STF, "accept_fail");
    public static final string_id PROSE_ACCEPT_FAIL = new string_id(STF, "prose_accept_fail");
    public static final string_id SID_DECLINE = new string_id(STF, "decline");
    public static final string_id SID_DECLINED = new string_id(STF, "declined");
    public static final string_id PROSE_DECLINED = new string_id(STF, "prose_declined");
    public static final string_id SID_EXPIRED_PLAYER = new string_id(STF, "expired_player");
    public static final string_id SID_EXPIRED_TARGET = new string_id(STF, "expired_target");
    public static final string_id PROSE_EXPIRE_PLAYER = new string_id(STF, "prose_expire_player");
    public static final string_id PROSE_EXPIRE_TARGET = new string_id(STF, "prose_expire_target");
    public static final string_id PROSE_WED_COMPLETE = new string_id(STF, "prose_wed_complete");
    public static final string_id SID_WED_ERROR = new string_id(STF, "wed_error");
    public static final string_id SID_WED_OOR = new string_id(STF, "wed_oor");
    public static final string_id SID_YOU_EXCHANGE_RINGS = new string_id(STF, "you_exchange_rings");
    public static final string_id PROSE_END_UNITY = new string_id(STF, "prose_end_unity");
    public static boolean isMarried(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            return false;
        }
        return hasObjVar(target, VAR_MARRIAGE_BASE);
    }
    public static boolean hasProposed(obj_id target) throws InterruptedException
    {
        if (!isIdValid(target) || !isPlayer(target))
        {
            return false;
        }
        return utils.hasScriptVar(target, VAR_PROPOSAL_NAME);
    }
    public static void propose(obj_id player, obj_id target, obj_id ring) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target) || !isIdValid(ring))
        {
            return;
        }
        if (!isPlayer(player))
        {
            return;
        }
        if (!isPlayer(target))
        {
            sendSystemMessage(player, SID_TARGET_NOT_PLAYER);
            return;
        }
        location here = getLocation(player);
        location there = getLocation(target);
        float distance = getDistance(here, there);
        if (distance > PROPOSAL_RANGE)
        {
            sendSystemMessage(player, SID_OUT_OF_RANGE);
            return;
        }
        if (!isFacing(player, there))
        {
            sendSystemMessage(player, SID_BAD_FACING);
            return;
        }
        if (isMarried(target))
        {
            sendSystemMessage(player, SID_TARGET_MARRIED);
            return;
        }
        if (hasProposed(target))
        {
            sendSystemMessage(player, SID_TARGET_PROPOSED);
            return;
        }
        if (utils.hasScriptVar(player, VAR_PROPOSAL_NAME))
        {
            String proposedTarget = getStringObjVar(player, VAR_PROPOSAL_NAME);
            if (proposedTarget != null && !proposedTarget.equals(""))
            {
                prose_package ppAlreadyProposed = prose.getPackage(PROSE_ALREADY_PROPOSED, proposedTarget);
                sendSystemMessageProse(player, ppAlreadyProposed);
                return;
            }
        }
        if (!hasScript(player, SCRIPT_UNITY))
        {
            attachScript(player, SCRIPT_UNITY);
        }
        if (!hasScript(target, SCRIPT_UNITY))
        {
            attachScript(target, SCRIPT_UNITY);
        }
        String targetName = getAssignedName(target);
        utils.setScriptVar(player, VAR_PROPOSAL_TARGET, target);
        utils.setScriptVar(player, VAR_PROPOSAL_NAME, targetName);
        utils.setScriptVar(player, VAR_PROPOSAL_RING, ring);
        queueCommand(player, (28609318), null, "", COMMAND_PRIORITY_DEFAULT);
        dictionary params = new dictionary();
        params.put("proposer", player);
        params.put("proposerName", getAssignedName(player));
        utils.setScriptVar(player, VAR_PROPOSAL_RING, ring);
        prose_package ppPropose = prose.getPackage(PROSE_PROPOSE, targetName);
        sendSystemMessageProse(player, ppPropose);
        messageTo(target, HANDLER_PROPOSED_TO, params, 2, false);
    }
    public static void cleanupProposal(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        utils.removeScriptVar(player, VAR_PROPOSAL_TARGET);
        utils.removeScriptVar(player, VAR_PROPOSAL_NAME);
        utils.removeScriptVar(player, VAR_PROPOSAL_RING);
    }
    public static void cleanupProposedTo(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        utils.removeScriptVar(player, VAR_PROPOSEDTO_TARGET);
        utils.removeScriptVar(player, VAR_PROPOSEDTO_NAME);
        utils.removeScriptVar(player, VAR_PROPOSEDTO_RING);
    }
    public static void cleanupAcceptance(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (utils.hasScriptVar(player, VAR_ACCEPT_SUI))
        {
            int pid = utils.getIntScriptVar(player, VAR_ACCEPT_SUI);
            forceCloseSUIPage(pid);
            utils.removeScriptVar(player, VAR_ACCEPT_SUI);
        }
        utils.removeScriptVar(player, VAR_ACCEPT_SUI_OPTIONS);
        utils.removeScriptVar(player, VAR_ACCEPT_RING);
    }
    public static obj_id[] getRings(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return null;
        }
        obj_id inv = utils.getInventoryContainer(player);
        if (!isIdValid(inv))
        {
            return null;
        }
        Vector vEq = new Vector();
        obj_id[] eqRings = utils.getContainedGOTObjects(player, GOT_jewelry_ring, false);
        if (eqRings != null && eqRings.length > 0)
        {
            vEq = new Vector(Arrays.asList(eqRings));
        }
        Vector vInv = new Vector();
        obj_id[] invRings = utils.getContainedGOTObjects(inv, GOT_jewelry_ring, true);
        if (invRings != null && invRings.length > 0)
        {
            vInv = new Vector(Arrays.asList(invRings));
        }
        Vector rings = utils.concatArrays(vEq, vInv);
        if (rings == null || rings.size() == 0)
        {
            return null;
        }
        return utils.toStaticObjIdArray(rings);
    }
    public static void wed(obj_id player, obj_id target) throws InterruptedException
    {
        if (!isIdValid(player) || !isIdValid(target))
        {
            return;
        }
        if (!isPlayer(player) || !isPlayer(target))
        {
            return;
        }
        location pLoc = getLocation(player);
        location tLoc = getLocation(target);
        if (getDistance(pLoc, tLoc) > PROPOSAL_RANGE)
        {
            sendSystemMessage(player, SID_WED_OOR);
            sendSystemMessage(target, SID_WED_OOR);
            detachScript(player, SCRIPT_UNITY);
            detachScript(target, SCRIPT_UNITY);
            return;
        }
        obj_id pRing = utils.getObjIdScriptVar(player, VAR_PROPOSAL_RING);
        obj_id tRing = utils.getObjIdScriptVar(target, VAR_ACCEPT_RING);
        if (!isIdValid(pRing) || !isIdValid(tRing))
        {
            weddingError(player, target);
            return;
        }
        obj_id pSlot = getObjectInSlot(player, "ring_l");
        obj_id pInv = utils.getInventoryContainer(player);
        if (isIdValid(pSlot) && isIdValid(pInv))
        {
            putInOverloaded(pSlot, pInv);
        }
        putInOverloaded(pRing, pInv);
        obj_id tSlot = getObjectInSlot(player, "ring_l");
        obj_id tInv = utils.getInventoryContainer(target);
        if (isIdValid(tSlot) && isIdValid(tInv))
        {
            putInOverloaded(tSlot, tInv);
        }
        putInOverloaded(tRing, tInv);
        dictionary toPlayer = new dictionary();
        toPlayer.put("other", target);
        toPlayer.put("ring", pRing);
        dictionary toTarget = new dictionary();
        toTarget.put("other", player);
        toTarget.put("ring", tRing);
        messageTo(player, HANDLER_RING_SETUP, toPlayer, 0, false);
        messageTo(target, HANDLER_RING_SETUP, toTarget, 0, false);
    }
    public static void weddingError(obj_id player, obj_id target) throws InterruptedException
    {
        sendSystemMessage(player, SID_WED_ERROR);
        sendSystemMessage(target, SID_WED_ERROR);
        detachScript(player, SCRIPT_UNITY);
        detachScript(target, SCRIPT_UNITY);
    }
    public static void weddingError(obj_id player) throws InterruptedException
    {
        obj_id target = null;
        if (utils.hasScriptVar(player, VAR_PROPOSAL_TARGET))
        {
            target = utils.getObjIdScriptVar(player, VAR_PROPOSAL_TARGET);
        }
        if (utils.hasScriptVar(player, VAR_PROPOSEDTO_TARGET))
        {
            target = utils.getObjIdScriptVar(player, VAR_PROPOSEDTO_TARGET);
        }
        if (isIdValid(target))
        {
            weddingError(player, target);
        }
        else 
        {
            sendSystemMessage(player, SID_WED_ERROR);
            detachScript(player, SCRIPT_UNITY);
        }
    }
    public static void divorce(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        if (!isMarried(player))
        {
            return;
        }
        obj_id spouse = getObjIdObjVar(player, VAR_SPOUSE_ID);
        if (isIdValid(spouse))
        {
            dictionary params = new dictionary();
            params.put("self", player);
            messageTo(spouse, HANDLER_DIVORCE, params, 0, true);
        }
        endUnity(player);
    }
    public static void declineProposal(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id proposer = utils.getObjIdScriptVar(player, VAR_PROPOSEDTO_TARGET);
        if (isIdValid(proposer))
        {
            messageTo(proposer, HANDLER_PROPOSAL_DECLINED, null, 0, false);
        }
        sendSystemMessage(player, SID_DECLINE);
        detachScript(player, SCRIPT_UNITY);
    }
    public static void cannotAccept(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        obj_id proposer = utils.getObjIdScriptVar(player, VAR_PROPOSEDTO_TARGET);
        if (isIdValid(proposer))
        {
            messageTo(proposer, HANDLER_ACCEPT_FAIL, null, 0, false);
        }
        sendSystemMessage(player, SID_NO_RING);
        detachScript(player, SCRIPT_UNITY);
    }
    public static void endUnity(obj_id player) throws InterruptedException
    {
        if (!isIdValid(player))
        {
            return;
        }
        String spouseName = getStringObjVar(player, VAR_SPOUSE_NAME);
        obj_id ring = getObjIdObjVar(player, VAR_RING);
        if (!isIdValid(ring))
        {
            ring = getObjectInSlot(player, "ring_l");
        }
        if (isIdValid(ring))
        {
            detachScript(ring, SCRIPT_RING_UNITY);
        }
        prose_package ppEndUnity = prose.getPackage(PROSE_END_UNITY, spouseName);
        sendSystemMessageProse(player, ppEndUnity);
        removeObjVar(player, VAR_MARRIAGE_BASE);
    }
}
