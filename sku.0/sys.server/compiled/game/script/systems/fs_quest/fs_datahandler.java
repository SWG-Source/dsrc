package script.systems.fs_quest;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.trace;
import script.library.fs_dyn_village;
import script.library.utils;
import java.util.Enumeration;

public class fs_datahandler extends script.base_script
{
    public fs_datahandler()
    {
    }
    public void tryDetachScript() throws InterruptedException
    {
        obj_id selph = getSelf();
        deltadictionary dd = selph.getScriptVars();
        Enumeration keys = dd.keys();
        while (keys.hasMoreElements())
        {
            String key = (String)(keys.nextElement());
            if (key.startsWith(fs_dyn_village.SCRIPT_VAR_DATA_REQUEST))
            {
                return;
            }
        }
        detachScript(selph, fs_dyn_village.SCRIPT_FS_DATAHANDLER);
        return;
    }
    public int OnLogin(obj_id self) throws InterruptedException
    {
        tryDetachScript();
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        tryDetachScript();
        return SCRIPT_CONTINUE;
    }
    public int OnClusterWideDataResponse(obj_id self, String manager_name, String dataset_name, int request_id, String[] element_name_list, dictionary[] cluster_data, int lock_key) throws InterruptedException
    {
        if (!manager_name.equals(fs_dyn_village.DYNVILLAGE_CLUSTER_NAME))
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_datahandler::OnClusterWideDataResponse: -> " + self + " Ignoring cluster manager name " + manager_name, self, trace.TL_ERROR_LOG);
            return SCRIPT_CONTINUE;
        }
        int data_mode = -1;
        if (utils.hasScriptVar(self, fs_dyn_village.SCRIPT_VAR_DATA_REQUEST + "." + request_id))
        {
            data_mode = utils.getIntScriptVar(self, fs_dyn_village.SCRIPT_VAR_DATA_REQUEST + "." + request_id);
            utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_DATA_REQUEST + "." + request_id);
        }
        else 
        {
            trace.log(fs_dyn_village.LOG_CHAN, "fs_datahandler::OnClusterWideDataResponse: -> cannot find a data_mode for request_id " + request_id + " on " + self, self, trace.TL_ERROR_LOG);
            releaseClusterWideDataLock(manager_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        String str_params = null;
        if (utils.hasScriptVar(self, fs_dyn_village.SCRIPT_VAR_PARAMS_REQUEST + "." + request_id))
        {
            str_params = utils.getStringScriptVar(self, fs_dyn_village.SCRIPT_VAR_PARAMS_REQUEST + "." + request_id);
            utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_PARAMS_REQUEST + "." + request_id);
        }
        String handler = null;
        if (utils.hasScriptVar(self, fs_dyn_village.SCRIPT_VAR_HANDLER_REQ + "." + request_id))
        {
            handler = utils.getStringScriptVar(self, fs_dyn_village.SCRIPT_VAR_HANDLER_REQ + "." + request_id);
            utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_HANDLER_REQ + "." + request_id);
        }
        if (data_mode == -1 || str_params == null)
        {
            releaseClusterWideDataLock(manager_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        if (element_name_list == null || element_name_list.length < 1)
        {
            if (handler != null && handler.length() > 0)
            {
                dictionary result = new dictionary();
                result.put("success", false);
                messageTo(self, handler, result, 0.0f, false);
            }
            releaseClusterWideDataLock(manager_name, lock_key);
            return SCRIPT_CONTINUE;
        }
        switch (data_mode)
        {
            case fs_dyn_village.DATAMODE_ADD_OBJID:
            
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(str_params, "|");
                if (st.countTokens() < 2)
                {
                    releaseClusterWideDataLock(manager_name, lock_key);
                    return SCRIPT_CONTINUE;
                }
                String obj_id_key = st.nextToken();
                obj_id id = utils.stringToObjId(st.nextToken());
                if (!isIdValid(id))
                {
                    releaseClusterWideDataLock(manager_name, lock_key);
                    return SCRIPT_CONTINUE;
                }
                dictionary result = new dictionary();
                result.put("success", true);
                result.put("registeredId", id);
                result.put("key", obj_id_key);
                dictionary currentdata = cluster_data[0];
                currentdata.put(obj_id_key, id);
                replaceClusterWideData(fs_dyn_village.DYNVILLAGE_CLUSTER_NAME, element_name_list[0], currentdata, false, lock_key);
                releaseClusterWideDataLock(manager_name, lock_key);
                if (handler != null && handler.length() > 0)
                {
                    messageTo(self, handler, result, 0.0f, false);
                }
                break;
            }
            case fs_dyn_village.DATAMODE_GET_OBJID:
            
            {
                dictionary dgo = new dictionary();
                dgo.put("key", str_params);
                if (cluster_data[0].containsKey(str_params))
                {
                    dgo.put("success", true);
                    dgo.put(str_params, cluster_data[0].getObjId(str_params));
                }
                else 
                {
                    dgo.put("success", false);
                    obj_id nullid = obj_id.NULL_ID;
                    dgo.put(str_params, nullid);
                }
                messageTo(self, handler, dgo, 0.0f, false);
                break;
            }
            case fs_dyn_village.DATAMODE_ADD_INT:
            
            {
                java.util.StringTokenizer st = new java.util.StringTokenizer(str_params, "|");
                if (st.countTokens() < 2)
                {
                    releaseClusterWideDataLock(manager_name, lock_key);
                    return SCRIPT_CONTINUE;
                }
                String int_key = st.nextToken();
                int val = utils.stringToInt(st.nextToken());
                dictionary dai = new dictionary();
                dai.put("success", true);
                dai.put("registeredInt", val);
                dai.put("key", int_key);
                dictionary currentData = cluster_data[0];
                currentData.put(int_key, val);
                replaceClusterWideData(fs_dyn_village.DYNVILLAGE_CLUSTER_NAME, element_name_list[0], currentData, false, lock_key);
                releaseClusterWideDataLock(manager_name, lock_key);
                messageTo(self, handler, dai, 0.0f, false);
                break;
            }
            case fs_dyn_village.DATAMODE_GET_INT:
            
            {
                dictionary dgi = new dictionary();
                dgi.put("key", str_params);
                if (cluster_data[0].containsKey(str_params))
                {
                    dgi.put("success", true);
                    dgi.put(str_params, cluster_data[0].getInt(str_params));
                }
                else 
                {
                    dgi.put("success", false);
                    dgi.put(str_params, -1);
                }
                messageTo(self, handler, dgi, 0.0f, false);
                break;
            }
            default:
            
            {
                trace.log(fs_dyn_village.LOG_CHAN, "fs_datahandler::OnClusterWideDataResponse: -> invalid data_mode value of " + data_mode + " on " + self, self, trace.TL_ERROR_LOG);
                releaseClusterWideDataLock(manager_name, lock_key);
                break;
            }
        }
        utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_DATA_REQUEST + "." + request_id);
        utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_PARAMS_REQUEST + "." + request_id);
        utils.removeScriptVar(self, fs_dyn_village.SCRIPT_VAR_HANDLER_REQ + "." + request_id);
        tryDetachScript();
        return SCRIPT_CONTINUE;
    }
}
