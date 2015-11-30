package script.player;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.sui;
import script.library.utils;
import script.library.prose;
import script.library.marriage;
import script.library.static_item;

public class unity extends script.base_script
{
    public unity()
    {
    }
    public static final string_id SID_SUI_PROPOSE_PROMPT = new string_id("unity", "sui_propose_prompt");
    public static final string_id SID_SUI_RING_SPEC = new string_id("unity", "sui_ring_spec");
    public static final string_id SID_SUI_RING_EQUIP = new string_id("unity", "sui_ring_equip");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        messageTo(self, marriage.HANDLER_PROPOSAL_EXPIRE, null, marriage.PROPOSAL_TIME, true);
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSAL_TARGET))
        {
            if (getPosture(self) == POSTURE_CROUCHED)
            {
                queueCommand(self, (-1465754503), null, "", COMMAND_PRIORITY_DEFAULT);
            }
            marriage.cleanupProposal(self);
        }
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET))
        {
            marriage.cleanupProposedTo(self);
            marriage.cleanupAcceptance(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLogout(obj_id self) throws InterruptedException
    {
        obj_id target = null;
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSAL_TARGET))
        {
            target = utils.getObjIdScriptVar(self, marriage.VAR_PROPOSAL_TARGET);
        }
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET))
        {
            target = utils.getObjIdScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET);
        }
        if (isIdValid(target))
        {
            messageTo(target, marriage.HANDLER_PROPOSAL_EXPIRE, null, 0, true);
        }
        messageTo(self, marriage.HANDLER_PROPOSAL_EXPIRE, null, 0, true);
        return SCRIPT_CONTINUE;
    }
    public int handleProposedTo(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            detachScript(self, marriage.SCRIPT_UNITY);
            return SCRIPT_CONTINUE;
        }
        obj_id proposer = params.getObjId("proposer");
        if (!isIdValid(proposer))
        {
            detachScript(self, marriage.SCRIPT_UNITY);
            return SCRIPT_CONTINUE;
        }
        String proposerName = params.getString("proposerName");
        if (proposerName == null || proposerName.equals(""))
        {
            detachScript(self, marriage.SCRIPT_UNITY);
            return SCRIPT_CONTINUE;
        }
        utils.setScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET, proposer);
        utils.setScriptVar(self, marriage.VAR_PROPOSEDTO_NAME, proposerName);
        prose_package ppPropose = prose.getPackage(SID_SUI_PROPOSE_PROMPT);
        prose.setTT(ppPropose, proposerName);
        String prompt = " \0" + packOutOfBandProsePackage(null, ppPropose);
        String title = "@" + marriage.STF + ":accept_title";
        int pid = sui.msgbox(self, self, prompt, sui.YES_NO, title, "handleProposalSui");
        if (pid > -1)
        {
            utils.setScriptVar(self, marriage.VAR_ACCEPT_SUI, pid);
        }
        else 
        {
            detachScript(self, marriage.SCRIPT_UNITY);
            detachScript(proposer, marriage.SCRIPT_UNITY);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleProposalSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            marriage.declineProposal(self);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            marriage.declineProposal(self);
            return SCRIPT_CONTINUE;
        }
        obj_id[] rings = marriage.getRings(self);
        if (rings == null || rings.length == 0)
        {
            marriage.cannotAccept(self);
            return SCRIPT_CONTINUE;
        }
        Vector entries = new Vector();
        entries.setSize(0);
        for (int i = 0; i < rings.length; i++)
        {
            String entry = null;
            if (static_item.isStaticItem(rings[i]))
            {
                if (!static_item.canEquip(self, rings[i]))
                {
                    continue;
                }
            }
            prose_package ppSpec = prose.getPackage(SID_SUI_RING_EQUIP);
            prose.setTT(ppSpec, getNameStringId(rings[i]));
            entry = " \0" + packOutOfBandProsePackage(null, ppSpec);
            entries = utils.addElement(entries, entry);
        }
        String prompt = "@" + marriage.STF + ":ring_prompt";
        String title = "@" + marriage.STF + ":ring_title";
        int pid = sui.listbox(self, self, prompt, sui.OK_CANCEL, title, entries, "handleRingSelectionSui");
        if (pid > -1)
        {
            utils.setScriptVar(self, marriage.VAR_ACCEPT_SUI, pid);
            utils.setBatchScriptVar(self, marriage.VAR_ACCEPT_SUI_OPTIONS, rings);
        }
        else 
        {
            obj_id proposer = utils.getObjIdScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET);
            if (isIdValid(proposer))
            {
                messageTo(proposer, marriage.HANDLER_PROPOSAL_EXPIRE, null, 0, true);
            }
            messageTo(self, marriage.HANDLER_PROPOSAL_EXPIRE, null, 0, true);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRingSelectionSui(obj_id self, dictionary params) throws InterruptedException
    {
        if (params == null || params.isEmpty())
        {
            marriage.declineProposal(self);
            return SCRIPT_CONTINUE;
        }
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL)
        {
            marriage.declineProposal(self);
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1)
        {
            marriage.declineProposal(self);
            return SCRIPT_CONTINUE;
        }
        obj_id[] rings = marriage.getRings(self);
        if (rings == null || rings.length == 0)
        {
            marriage.cannotAccept(self);
            return SCRIPT_CONTINUE;
        }
        obj_id[] ringOptions = utils.getObjIdBatchScriptVar(self, marriage.VAR_ACCEPT_SUI_OPTIONS);
        if (ringOptions == null || ringOptions.length == 0)
        {
            marriage.cannotAccept(self);
            return SCRIPT_CONTINUE;
        }
        if (idx >= ringOptions.length)
        {
            marriage.cannotAccept(self);
            return SCRIPT_CONTINUE;
        }
        obj_id retRing = ringOptions[idx];
        if (utils.getElementPositionInArray(rings, retRing) == -1)
        {
            marriage.cannotAccept(self);
            return SCRIPT_CONTINUE;
        }
        obj_id proposer = utils.getObjIdScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET);
        if (!isIdValid(proposer))
        {
            messageTo(self, marriage.HANDLER_PROPOSAL_EXPIRE, null, 0, true);
        }
        marriage.cleanupAcceptance(self);
        utils.setScriptVar(self, marriage.VAR_ACCEPT_RING, retRing);
        marriage.wed(proposer, self);
        return SCRIPT_CONTINUE;
    }
    public int handleProposalExpire(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSAL_TARGET))
        {
            String proposee = utils.getStringScriptVar(self, marriage.VAR_PROPOSAL_NAME);
            if (proposee != null && !proposee.equals(""))
            {
                prose_package ppExpirePlayer = prose.getPackage(marriage.PROSE_EXPIRE_PLAYER, proposee);
                sendSystemMessageProse(self, ppExpirePlayer);
            }
            else 
            {
                sendSystemMessage(self, marriage.SID_EXPIRED_PLAYER);
            }
        }
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSEDTO_TARGET))
        {
            String proposer = utils.getStringScriptVar(self, marriage.VAR_PROPOSEDTO_NAME);
            if (proposer != null && !proposer.equals(""))
            {
                prose_package ppExpireTarget = prose.getPackage(marriage.PROSE_EXPIRE_TARGET, proposer);
                sendSystemMessageProse(self, ppExpireTarget);
            }
            else 
            {
                sendSystemMessage(self, marriage.SID_EXPIRED_TARGET);
            }
        }
        detachScript(self, marriage.SCRIPT_UNITY);
        return SCRIPT_CONTINUE;
    }
    public int handleAcceptFail(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSAL_TARGET))
        {
            String proposee = utils.getStringScriptVar(self, marriage.VAR_PROPOSAL_NAME);
            if (proposee != null && !proposee.equals(""))
            {
                prose_package ppAcceptFail = prose.getPackage(marriage.PROSE_ACCEPT_FAIL, proposee);
                sendSystemMessageProse(self, ppAcceptFail);
            }
            else 
            {
                sendSystemMessage(self, marriage.SID_ACCEPT_FAIL);
            }
        }
        detachScript(self, marriage.SCRIPT_UNITY);
        return SCRIPT_CONTINUE;
    }
    public int handleProposalDeclined(obj_id self, dictionary params) throws InterruptedException
    {
        if (utils.hasScriptVar(self, marriage.VAR_PROPOSAL_TARGET))
        {
            String proposee = utils.getStringScriptVar(self, marriage.VAR_PROPOSAL_NAME);
            if (proposee != null && !proposee.equals(""))
            {
                prose_package ppDeclined = prose.getPackage(marriage.PROSE_DECLINED, proposee);
                sendSystemMessageProse(self, ppDeclined);
            }
            else 
            {
                sendSystemMessage(self, marriage.SID_DECLINED);
            }
        }
        detachScript(self, marriage.SCRIPT_UNITY);
        return SCRIPT_CONTINUE;
    }
    public int handleRingExchange(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id other = params.getObjId("other");
        obj_id ring = params.getObjId("ring");
        obj_id inv = params.getObjId("inv");
        if (isIdValid(other) && isIdValid(ring) && isIdValid(inv))
        {
            putInOverloaded(ring, inv);
            params.put("other", self);
            params.remove("inv");
            prose_package ppExchangeRings = prose.getPackage(marriage.SID_YOU_EXCHANGE_RINGS);
            prose.setTT(ppExchangeRings, getAssignedName(other));
            sendSystemMessageProse(self, ppExchangeRings);
            messageTo(other, marriage.HANDLER_RING_SETUP, params, 1, false);
        }
        else 
        {
            marriage.weddingError(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleRingSetup(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id spouse = params.getObjId("other");
        obj_id ring = params.getObjId("ring");
        if (isIdValid(ring) && isIdValid(spouse))
        {
            obj_id inv = utils.getInventoryContainer(self);
            obj_id ring_l = getObjectInSlot(self, "ring_l");
            if (isIdValid(inv) && isIdValid(ring_l))
            {
                putInOverloaded(ring_l, inv);
            }
            if (equip(ring, self, "ring_l"))
            {
                attachScript(ring, marriage.SCRIPT_RING_UNITY);
                String spouseName = getAssignedName(spouse);
                setObjVar(self, marriage.VAR_SPOUSE_ID, spouse);
                setObjVar(self, marriage.VAR_SPOUSE_NAME, spouseName);
                setObjVar(self, marriage.VAR_RING, ring);
                prose_package pp = prose.getPackage(marriage.PROSE_WED_COMPLETE, spouse);
                sendSystemMessageProse(self, pp);
                detachScript(self, marriage.SCRIPT_UNITY);
                return SCRIPT_CONTINUE;
            }
            else 
            {
            }
        }
        marriage.weddingError(self);
        return SCRIPT_CONTINUE;
    }
}
