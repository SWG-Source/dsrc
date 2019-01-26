package script.space.ship;

import script.dictionary;
import script.library.dot;
import script.library.utils;
import script.obj_id;
import script.string_id;

public class ship_cell_manager extends script.base_script
{
    public ship_cell_manager()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int incrementDotDamage(obj_id self, dictionary params) throws InterruptedException
    {
        int intSlot = params.getInt("intSlot");
        int intDamage = utils.getIntScriptVar(self, "intDamage");
        intDamage = intDamage + 100;
        obj_id[] objContents = getContents(self);
        utils.setScriptVar(self, "intDamage", intDamage);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                if (isPlayer(objContent)) {
                    if (dot.hasDotId(objContent, "plasma_conduit")) {
                        int intStrength = dot.getDotStrength(objContent, "plasma_conduit");
                        intStrength = intDamage;
                        dot.applyDotEffect(objContent, self, dot.DOT_FIRE, "plasma_conduit", HEALTH, -1, intDamage, 10000000, false, null);
                    } else {
                        string_id strSpam = new string_id("space/space_interaction", "plasma_leak_begin");
                        sendSystemMessage(objContent, strSpam);
                        dot.applyDotEffect(objContent, self, dot.DOT_FIRE, "plasma_conduit", HEALTH, -1, intDamage, 10000000, true, null);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int decrementDotDamage(obj_id self, dictionary params) throws InterruptedException
    {
        int intSlot = params.getInt("intSlot");
        int intDamage = utils.getIntScriptVar(self, "intDamage");
        intDamage = intDamage - 100;
        LOG("space", "Decredmenting damage.. new damage i s" + intDamage);
        obj_id[] objContents = getContents(self);
        utils.setScriptVar(self, "intDamage", intDamage);
        if ((objContents != null) && (objContents.length > 0))
        {
            for (obj_id objContent : objContents) {
                if (isPlayer(objContent)) {
                    if (intDamage > 0) {
                        if (dot.hasDotId(objContent, "plasma_conduit")) {
                            int intStrength = dot.getDotStrength(objContent, "plasma_conduit");
                            intStrength = intDamage;
                            dot.applyDotEffect(objContent, null, dot.DOT_FIRE, "plasma_conduit", HEALTH, -1, intDamage, 10000000, false, null);
                        } else {
                            dot.applyDotEffect(objContent, null, dot.DOT_FIRE, "plasma_conduit", HEALTH, -1, intDamage, 10000000, true, null);
                        }
                    } else {
                        string_id strSpam = new string_id("space/space_interaction", "plasma_leak_end");
                        sendSystemMessage(objContent, strSpam);
                        dot.removeDotEffect(objContent, "plasma_conduit", true);
                        detachScript(self, "space.ship.ship_cell_manager");
                        setState(objContent, STATE_ON_FIRE, false);
                        LOG("space", "Detaching script");
                    }
                }
            }
        }
        if (intDamage <= 0)
        {
            detachScript(self, "space.ship.ship_cell_manager");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        obj_id[] objContents = getContents(self);
        if (objContents != null)
        {
            for (obj_id objContent : objContents) {
                if (isPlayer(objContent)) {
                    if (dot.hasDotId(objContent, "plasma_conduit")) {
                        string_id strSpam = new string_id("space/space_interaction", "plasma_leak_end");
                        sendSystemMessage(objContent, strSpam);
                        dot.removeDotEffect(objContent, "plasma_conduit", true);
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnReceivedItem(obj_id self, obj_id objDestContainer, obj_id objTransferer, obj_id objItem) throws InterruptedException
    {
        if (isPlayer(objItem))
        {
            int intDamage = utils.getIntScriptVar(self, "intDamage");
            if (intDamage > 0)
            {
                string_id strSpam = new string_id("space/space_interaction", "plasma_leak_begin");
                sendSystemMessage(objItem, strSpam);
                dot.applyDotEffect(objItem, null, dot.DOT_FIRE, "plasma_conduit", HEALTH, -1, intDamage, 10000000, false, null);
            }
            else 
            {
                detachScript(self, "space.ship.ship_cell_manager");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnLostItem(obj_id self, obj_id objDestContainer, obj_id objTransferer, obj_id objItem) throws InterruptedException
    {
        if (isPlayer(objItem))
        {
            if (dot.hasDotId(objItem, "plasma_conduit"))
            {
                string_id strSpam = new string_id("space/space_interaction", "plasma_leak_end");
                sendSystemMessage(objItem, strSpam);
                dot.removeDotEffect(objItem, "plasma_conduit", true);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
