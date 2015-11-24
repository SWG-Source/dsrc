package script.event.housepackup;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.cts;
import script.library.utils;

public class cts_refugee extends script.base_script
{
    public cts_refugee()
    {
    }
    public static final boolean BLOGGING_ON = true;
    public static final String BLOG_CATEGORY = "CharacterTransfer";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnInitialize.cts_refugee validation completed");
        if (!hasObjVar(self, cts.CURRENT_PLAYER_OID))
        {
            cts.initializeCtsObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!utils.isNestedWithinAPlayer(self))
        {
            return SCRIPT_CONTINUE;
        }
        blog("OnTransferred.cts_refugee validation completed");
        if (!hasObjVar(self, cts.CURRENT_PLAYER_OID))
        {
            cts.initializeCtsObject(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int idx = utils.getValidAttributeIndex(names);
        if (idx == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isValidId(self) || !exists(self))
        {
            return SCRIPT_CONTINUE;
        }
        String inscriptionString = cts.getCtsInscription(self);
        if (inscriptionString != null && !inscriptionString.equals(""))
        {
            names[idx] = "inscription";
            attribs[idx] = inscriptionString;
            idx++;
        }
        return SCRIPT_CONTINUE;
    }
    public boolean blog(String msg) throws InterruptedException
    {
        if (BLOGGING_ON)
        {
            LOG(BLOG_CATEGORY, msg);
        }
        return true;
    }
}
