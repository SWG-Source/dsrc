package script.systems.camping.template;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.camping;

public class camp_spit extends script.base_script
{
    public camp_spit()
    {
    }
    public static final String VAR_CHILDREN = "myChildren";
    public static final String VAR_CAMP_LOGS = VAR_CHILDREN + ".logs";
    public static final String VAR_CAMP_CAMPFIRE = VAR_CHILDREN + ".campfire";
    public static final String TEMPLATE_LOGS_FRESH = "object/tangible/camp/campfire_logs_fresh.iff";
    public static final String TEMPLATE_LOGS_BURNT = "object/tangible/camp/campfire_logs_burnt.iff";
    public static final String TEMPLATE_LOGS_SMOLDERING = "object/tangible/camp/campfire_logs_smoldering.iff";
    public static final String TEMPLATE_LOGS_ASH = "object/tangible/camp/campfire_logs_ash.iff";
    public static final String TEMPLATE_CAMPFIRE = "object/static/particle/particle_campfire_style_1.iff";
    public static final String TEMPLATE_CAMPFIRE_SMOLDER = "object/static/particle/particle_smoke.iff";
    public int OnUnloadedFromMemory(obj_id self) throws InterruptedException
    {
        destroyChildren(self);
        return SCRIPT_CONTINUE;
    }
    public int handleSetStatus(obj_id self, dictionary params) throws InterruptedException
    {
        if ((params == null) || (params.isEmpty()))
        {
            return SCRIPT_CONTINUE;
        }
        int status = params.getInt(camping.DICT_NEW_STATUS);
        switch (status)
        {
            case camping.STATUS_NEW:
            setLogs(self, TEMPLATE_LOGS_FRESH);
            setFire(self, TEMPLATE_CAMPFIRE);
            break;
            case camping.STATUS_CREATION:
            break;
            case camping.STATUS_MAINTAIN:
            setLogs(self, TEMPLATE_LOGS_BURNT);
            setFire(self, TEMPLATE_CAMPFIRE);
            break;
            case camping.STATUS_ABANDONED:
            default:
            setLogs(self, TEMPLATE_LOGS_SMOLDERING);
            setFire(self, TEMPLATE_CAMPFIRE_SMOLDER);
            break;
        }
        return SCRIPT_CONTINUE;
    }
    public void setLogs(obj_id self, String tpf) throws InterruptedException
    {
        obj_id item = getObjIdObjVar(self, VAR_CAMP_LOGS);
        if ((item == null) || (item == obj_id.NULL_ID))
        {
        }
        else 
        {
            String itemTemplate = getTemplateName(item);
            if (itemTemplate.equals(tpf))
            {
                return;
            }
            destroyObject(item);
        }
        location here = getLocation(self);
        item = createObject(tpf, here);
        setObjVar(self, VAR_CAMP_LOGS, item);
    }
    public void setFire(obj_id self, String tpf) throws InterruptedException
    {
        obj_id item = getObjIdObjVar(self, VAR_CAMP_CAMPFIRE);
        if ((item == null) || (item == obj_id.NULL_ID))
        {
        }
        else 
        {
            String itemTemplate = getTemplateName(item);
            if (itemTemplate.equals(tpf))
            {
                return;
            }
            destroyObject(item);
        }
        location here = getLocation(self);
        item = createObject(tpf, here);
        setObjVar(self, VAR_CAMP_CAMPFIRE, item);
    }
    public void destroyChildren(obj_id self) throws InterruptedException
    {
        obj_id logs = getObjIdObjVar(self, VAR_CAMP_LOGS);
        if ((logs == null) || (logs == obj_id.NULL_ID))
        {
        }
        else 
        {
            destroyObject(logs);
        }
        obj_id campfire = getObjIdObjVar(self, VAR_CAMP_CAMPFIRE);
        if ((campfire == null) || (campfire == obj_id.NULL_ID))
        {
        }
        else 
        {
            destroyObject(campfire);
        }
    }
}
