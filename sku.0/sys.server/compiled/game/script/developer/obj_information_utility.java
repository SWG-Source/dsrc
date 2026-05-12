package script.developer;

import script.*;
import script.library.*;

import java.util.*;

public class obj_information_utility extends script.base_script {


    //Dictionary Constants
    public static final String DICT_OBJ_ID = "ObjectId";
    public static final String DICT_OBJ_TYPE = "ObjectType";
    public static final String DICT_OBJ_NAME = "DisplayName";
    public static final String DICT_OBJ_SERVER_TEMPLATE_NAME = "ServerTemplateName";
    public static final String DICT_OBJ_SHARED_TEMPLATE_NAME = "SharedTemplateName";
    public static final String DICT_OBJ_CRC = "CRC";

    //Object Type Constants
    public static final int OBJ_TYPE_UNKNOWN    = -1;
    public static final int OBJ_TYPE_OBJECT     = 0;
    public static final int OBJ_TYPE_PLAYER     = 2;

    public obj_information_utility()
    {
    }


    //Returns the target if it is valid, self if target is invalid and
    //self is valid, otherwise a obj_id.NULL_ID
    //self: obj_id of self
    //target: obj_id of self's target
    public static obj_id getTargetOrSelf(obj_id self, obj_id target) throws InterruptedException      
    {
        
        if (isValidId(target)) 
        {
            return target;
        }

        if (isValidId(self))
        {
            return self;
        }

        return obj_id.NULL_ID;       
    }

    //Gets information on the object and stores it in the returned dictionary
    //obj: The obj_id to get information for
    public static dictionary getObjectInformation(obj_id obj) throws InterruptedException
    {
        dictionary dict = new dictionary();
        getObjectInformation(obj, dict);
        return dict;
    }

    //Gets information on the object and stores it in the provided dictionary
    //obj: The obj_id to get information for
    //dict: The dictionary to the data in
    public static void getObjectInformation(obj_id obj, dictionary dict) throws InterruptedException
    {
        boolean isPlayer = false;

        dict.put(DICT_OBJ_ID, obj);
        dict.put(DICT_OBJ_NAME, getName(obj));

        if (isPlayer(obj))
        {
            dict.put(DICT_OBJ_TYPE, OBJ_TYPE_PLAYER);
        }
        else
        {

            String sharedTemplateName = getSharedObjectTemplateName(obj);

            dict.put(DICT_OBJ_TYPE, OBJ_TYPE_OBJECT);
            dict.put(DICT_OBJ_SERVER_TEMPLATE_NAME, getTemplateName(obj));
            dict.put(DICT_OBJ_SHARED_TEMPLATE_NAME, sharedTemplateName);
            dict.put(DICT_OBJ_CRC, getStringCrc(sharedTemplateName));
        }
    }

    public int cmdObjectInfo(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            sendSystemMessage(self, "\\#ff0000You do not authorized to use this script.\\#ffffff", null);
        }

        obj_id infoTarget = null;
        
        StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String objIdString = "";

        if (st.hasMoreTokens())
        {
            objIdString = st.nextToken();
        }

        if (objIdString != null && objIdString.length() > 0)
        {
            try
            {
                infoTarget = obj_id.getObjId(Long.parseLong(objIdString));
            }
            catch(Exception ex)
            {
                infoTarget = self;
            }
        }

        if (!isValidId(infoTarget))
        {
            infoTarget = self;
        }

        //now pull the data

        StringBuilder sb = new StringBuilder();
        
        GetPlayerInformation(infoTarget, sb);
        GetObjectInformation(infoTarget, sb);
        GetObjVars(infoTarget, sb);
        GetScriptVars(infoTarget, sb);
        GetScripts(infoTarget, sb);

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Object Information", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);

        return SCRIPT_CONTINUE;
    }


    private static void GetObjVars(obj_id target, StringBuilder sb) throws InterruptedException      
    {

        String packedObjVars = getPackedObjvars(target);        
        sb.append("\\#FFFFFF======Obj Vars======\\#FFFFFF\n");

        String[] strSplit = split(packedObjVars, '|');
        if (strSplit.length > 2)
        {
            for (int intI = 0; intI < strSplit.length - 2; intI++)
            {
                sb.append("\\#00FF00");
                sb.append(strSplit[intI]);
                sb.append("=");
                sb.append("\\#FFFFFF");
                sb.append(strSplit[intI + 2]);
                sb.append("\n\r");
                intI = intI + 2;
            }
        }
    }

    private static void GetScriptVars(obj_id target, StringBuilder sb) throws InterruptedException      
    {
        deltadictionary scriptVars = target.getScriptVars();
        Enumeration keys = scriptVars.keys();
        sb.append("\\#FFFFFF=====Script Vars=====\\#FFFFFF\n");

        while (keys.hasMoreElements())
        {
            String key = (String)keys.nextElement();
            sb.append("\\#00FF00");
            sb.append(key);
            sb.append("=");
            sb.append("\\#FFFFFF");
            sb.append(scriptVars.getObject(key).toString());
            sb.append("\n");
        }
    }

    private static void GetScripts(obj_id target, StringBuilder sb) throws InterruptedException      
    {
        sb.append("\\#FFFFFF=======Scripts=======\\#FFFFFF\n");
        String[] scriptList = getScriptList(target);

        for (String script : scriptList)
        {
            sb.append("\\#00FF00");
            sb.append(script);
            sb.append("\\#FFFFFF");
            sb.append("\n");
        }
    }

    private static void GetPlayerInformation(obj_id target, StringBuilder sb) 
    {
        if (isPlayer(target))
        {
            sb.append("\\#00FFFFPlayer: \\#FFFFFF");
            sb.append(getName(target) );
            sb.append("\\#00FF00 (");
            sb.append(target.toString());
            sb.append("\\)\\#FFFFFF");            
            sb.append("\n");
        }
    }

    private static void GetObjectInformation(obj_id target, StringBuilder sb) 
    {
        if (!isPlayer(target))
        {
            sb.append("\\#00FFFFDetails for object id: \\#FFFFFF");
            sb.append(target.toString());
            sb.append("\n");
            String serverTemplateName = getTemplateName(target);
            String sharedTemplateName = getSharedObjectTemplateName(target);
            int crc = getStringCrc(sharedTemplateName);
            String displayName = getName(target);

            sb.append("\\#00FFFFServer Template: \\#FFFFFF");
            sb.append(serverTemplateName);
            sb.append("\n"); 
            sb.append("\\#00FFFFShared Template: \\#FFFFFF");
            sb.append(sharedTemplateName);
            sb.append("\n");
            sb.append("\\#00FFFFDisplay Name: \\#FFFFFF");    
            sb.append(displayName);
            sb.append("\n");
            sb.append("\\#00FFFFGame Object Type: \\#FFFFFF");
            sb.append(getGameObjectTypeName(getGameObjectType(target)));
            sb.append("\n");
            sb.append("\\#00FFFFCRC: \\#FFFFFF");
            sb.append(Integer.toString(crc));
            sb.append("\n");
        }
    }



}
