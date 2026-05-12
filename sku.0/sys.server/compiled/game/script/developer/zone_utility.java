package script.developer;

import script.*;
import script.library.*;

import java.util.*;

public class zone_utility extends script.base_script {

    public static final int LOCATION_UNKNOWN    = -1;
    public static final int LOCATION_CELL       = 0;
    public static final int LOCATION_SPACE      = 1;
    public static final int LOCATION_GROUND     = 2;

    public zone_utility()
    {
    }


    public int OnAttach(obj_id self) throws InterruptedException {

        if (!isGod(self))
        {
            detachScript(self, "developer.zone_utility");
            sendSystemMessage(self, "\\#ff0000You do not authorized to use this script.\\#ffffff", null);
        }

        return SCRIPT_CONTINUE;
    }

    public int cmdZoneUtility(obj_id self, obj_id target, String params, float defaultTime) throws InterruptedException
    {
        if (!isGod(self))
        {
            sendSystemMessage(self, "\\#ff0000You do not authorized to use this script.\\#ffffff", null);
        }


        StringTokenizer st = new java.util.StringTokenizer(params);
        int tokens = st.countTokens();
        String command = "";
        if (st.hasMoreTokens())
        {
            command = st.nextToken();
        }

        if (command != null && command.length() > 0)
        {
            command = command.toLowerCase();
        }

        switch (command)
        {

			case "scene":
			case "getscene":
				showSceneInformation(self);
				break;

            case "cellinfo":
                showCellInformation(self);
                break;


            case "gettransform":
                showTransformInformation(self, target);
                break;

            case "showobjvars":
                showObjVars(self, target);
                break;

            case "showscriptvars":
                showScriptVars(self, target);
                break;

            case "showscripts":
                showScripts(self, target);
                break;

            case "showcrc":

                String stringToConvert = "";
                if (st.hasMoreTokens())
                {
                    stringToConvert = st.nextToken();
                }

                if (stringToConvert != null && stringToConvert.length() > 0)
                {
                    stringToConvert = stringToConvert.toLowerCase();
                    showStringCrc(self, stringToConvert);
                }
                else
                {
                    sendConsoleMessage(self, "\\#ff0000A string to convert to a CRC is required\\#ffffff");
                }

                break;

            case "pcityinfo":


               String cityIdString = "";
               int cityId = 0;

                if (st.hasMoreTokens())
                {
                    cityIdString = st.nextToken();


                    if (cityIdString != null && cityIdString.length() > 0)
                    {
                        try
                        {
                            cityId = Integer.parseInt(cityIdString);
                        }
                        catch(Exception ex)
                        {
                            cityId = 0;
                        }
                    }

                }
                else
                {
                    cityId = getCityAtLocation(getLocation(self), 0);
                }



                if (cityExists(cityId))
                {


                    //fetch city info

                   sendSystemMessage(self, "Player City name is " + cityGetName(cityId) + " (" + cityId + ")", "");
                   obj_id cityHall = cityGetCityHall(cityId);

                   if (hasObjVar(cityHall, city.OBJVAR_DERANK_EXEMPT))
                   {
                       sendSystemMessage(self, "The city IS derank exempt", "");        
                   }
                   else
                   {
                       sendSystemMessage(self, "The city is NOT derank exempt", "");        
                   }

                }
                else
                {
                   sendSystemMessage(self, "There is no player city at your present location. Are you outside?", "");        
                }
                



                break;


            case "help":
                StringBuilder sb = new StringBuilder();
                sb.append("\\#ffff00Command zoneutil help\\#ffffff\n");
				sb.append("\\#ffffffsub command\\#00ffff 'scene' \\#ffff00 Gets name of the current scene\\#ffffff\n");
				sb.append("\\#ffffffsub command\\#00ffff 'getscene' \\#ffff00 Gets name of the current scene\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'cellinfo' \\#ffff00 Gets information about your current cell and the containing structure\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'gettransform' \\#ffff00 Experimental replacement for gettransform\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'showobjvars' \\#ffff00 Show the object variables for self or the target\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'showscriptvars' \\#ffff00 Show the script variables for self or the target\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'showscripts' \\#ffff00 Show the scripts for self or the target\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'showcrc' \\#ffff00 Convert a string to a crc\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'pcityinfo' \\#ffff00 Shows info for the player city at the current location, if there is one\\#ffffff\n");
                sb.append("\\#ffffffsub command\\#00ffff 'pcityinfo <oid>' \\#ffff00 Shows info for the player city with the matching id, if there is one\\#ffffff\n");
                sendConsoleMessage(self, sb.toString());
                break;



            default:
                //"\\#ffff00 ============ buildout_utility spatial chat/speak commands ============ \\#."
                sendConsoleMessage(self, "\\#ff0000Invalid command.\\#ffffff Use \\#00ffff'zoneutil help' \\#ffffff for help");         
                break;
        }

        return SCRIPT_CONTINUE;
    }

	private static void showSceneInformation(obj_id self) throws InterruptedException
	{
		String sceneName = getCurrentSceneName();

		StringBuilder sb = new StringBuilder();
		sb.append("\\#ffffffCurrent Scene: \\#00ff00");
		sb.append(sceneName);
		sb.append("\\#ffffff");

		int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Scene Information", sui.MSG_INFORMATION, "noHandler");
		setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
		setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
		flushSUIPage(pid);
	}

    private static void showStringCrc(obj_id self, String string) throws InterruptedException
    {
        StringBuilder sb = new StringBuilder();

        int crc = getStringCrc(string);

        sb.append("\\#ffffffCRC for \\#00ff00'");
        sb.append(string);
        sb.append("'\\#ffffff is \\#00ff00'");
        sb.append(Integer.toString(crc));
        sb.append("'\\#ffffff");

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "String CRC", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }


    private static void showCellInformation(obj_id self) throws InterruptedException
    {
        location here = getLocation(self);
        obj_id cell = here.cell;
        obj_id building = null;
        boolean inBuilding = false;

        if (!isIdValid(cell))
        {
            sendSystemMessageTestingOnly(self, "You are not in a building!");
            return;
        }

        building = getContainedBy(cell);
        location containerLocation = getLocation(building);
        String buildoutAreaName = getBuildoutAreaName(containerLocation.x, containerLocation.z, containerLocation.area);  
        String[] cellNames = getCellNames(building);

        StringBuilder sb = new StringBuilder();
        
        sb.append("\\#00FF00Building Name: \\#FFFFFF");        
        sb.append(getName(building));
        sb.append("\n");

        sb.append("\\#00FF00Cell Count: \\#FFFFFF");
        sb.append(cellNames.length);
        sb.append("\n");

        sb.append("\\#00FFFFCell Names: \\#FFFFFF\n");

        for(String cellName : cellNames)
        {
            sb.append("\\#00FF00\tCell Name: ");
            sb.append(cellName);
            sb.append("\\#FFFFFF\n");
        }

        sb.append("\\#00FFFFYou are in cell: \\#FFFFFF");
        sb.append(utils.getCellName(building, cell));
        sb.append(", \\#00FFFFIndex: \\#FFFFFF");
        sb.append(getCellIndex(getCurrentSceneName( ), buildoutAreaName, cell));
        sb.append("\n");

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Cell Information", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }

    private static int getCellIndex(String region, String area, obj_id cell)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("datatables/buildout/");
        sb.append(region);
        sb.append("/");
        sb.append(area);
        sb.append(".iff");
    
        String dataTable = sb.toString();

        int row = dataTableSearchColumnForInt((int) cell.getValue(), "objid", dataTable);


        if (row == -1)
        {
            return 0;
        }      

        int index = dataTableGetInt(dataTable, row, "cell_index");          

        return index;
    }

    private static void showObjVars(obj_id self, obj_id target) throws InterruptedException      
    {
        obj_id infoTarget;

        if (isValidId(target)) 
        {
            infoTarget = target;            
        }
        else
        {
            infoTarget = self;
        }

        StringBuilder sb = new StringBuilder();
        String packedObjVars = getPackedObjvars(infoTarget);        
        AppendPlayerInformation(sb, infoTarget);
        AppendObjectInformation(sb, infoTarget);
        sb.append("\\#FFFFFF=====================\\#FFFFFF\n");

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

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Object Variables", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }

    private static void showScriptVars(obj_id self, obj_id target) throws InterruptedException      
    {
        obj_id infoTarget;

        if (isValidId(target)) 
        {
            infoTarget = target;            
        }
        else
        {
            infoTarget = self;
        }

        StringBuilder sb = new StringBuilder();
        deltadictionary scriptVars = infoTarget.getScriptVars();
        Enumeration keys = scriptVars.keys();
        AppendPlayerInformation(sb, infoTarget);
        AppendObjectInformation(sb, infoTarget);
        sb.append("\\#FFFFFF=====================\\#FFFFFF\n");

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

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Script Variables", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }


    private static void showScripts(obj_id self, obj_id target) throws InterruptedException      
    {
        obj_id infoTarget;

        if (isValidId(target)) 
        {
            infoTarget = target;            
        }
        else
        {
            infoTarget = self;
        }

        StringBuilder sb = new StringBuilder();
        AppendPlayerInformation(sb, infoTarget);
        AppendObjectInformation(sb, infoTarget);
        sb.append("\\#FFFFFF=====================\\#FFFFFF\n");

        String[] scriptList = getScriptList(infoTarget);

        for (String script : scriptList)
        {
            sb.append("\\#00FF00");
            sb.append(script);
            sb.append("\\#FFFFFF");
            sb.append("\n");
        }

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Scripts", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }


    private static void showTransformInformation(obj_id self, obj_id target) throws InterruptedException
    {
        StringBuilder sb = new StringBuilder();
        obj_id infoTarget;

        if (isValidId(target)) 
        {
            infoTarget = target;            
        }
        else
        {
            infoTarget = self;
        }
        
        sb.append("\\#00FF00Transform Information\\#FFFFFF\n");
        sb.append("\\#00FF00=====================\\#FFFFFF\n");
        AppendPlayerInformation(sb, infoTarget);
        AppendObjectInformation(sb, infoTarget);
        AppendQuaternion(sb, infoTarget);
        AppendLocationInformation(sb, infoTarget);

        int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "Transform Information", sui.MSG_INFORMATION, "noHandler");
        setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
        flushSUIPage(pid);
    }

    private static void AppendPlayerInformation(StringBuilder sb, obj_id target) 
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

    private static void AppendObjectInformation(StringBuilder sb, obj_id target) 
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

    private static void AppendQuaternion(StringBuilder sb, obj_id target) throws InterruptedException 
    {
        float[] quat = getQuaternion(target);

        sb.append("\\#00FFFFQuaternion: \\#FFFFFF");
        sb.append("\\#00FF00 qX: \\#FFFFFF");                
        sb.append(quat[0]);
        sb.append("\\#00FF00 qY: \\#FFFFFF");                
        sb.append(quat[1]);
        sb.append("\\#00FF00 qZ: \\#FFFFFF");
        sb.append(quat[2]);
        sb.append("\\#00FF00 qW: \\#FFFFFF");
        sb.append(quat[3]);
        sb.append("\n");
    }

    private static void AppendVectorInformation(StringBuilder sb, transform pos) throws InterruptedException
    {
        vector vctJ = pos.getLocalFrameJ_p();
        vector vctK = pos.getLocalFrameK_p();
        vector vctP = pos.getPosition_p();

        sb.append("\\#00FFFFJ vector is: \\#FFFFFF");
        sb.append(vctJ);
        sb.append("\n");
        sb.append("\\#00FFFFK vector is: \\#FFFFFF");
        sb.append(vctK);
        sb.append("\n");
        sb.append("\\#00FFFFP vector is: \\#FFFFFF");
        sb.append(vctP);
        sb.append("\n");
    } 

    private static void AppendLocationInformation(StringBuilder sb, obj_id target) throws InterruptedException 
    {
		location localLocation = getLocation(target);
        int locationType = getLocationType(target);
        String sceneName = getCurrentSceneName();


        sb.append("\\#00FFFFScene Name: \\#FFFFFF");
        sb.append(sceneName);
        sb.append("\n");
        
        switch(locationType)
        {

            case LOCATION_GROUND:
            case LOCATION_SPACE:

                String buildoutAreaName = getBuildoutAreaName(localLocation.x, localLocation.z, localLocation.area);
                final transform pos = getTransform_o2w(target);

                //add the buildout coordinates
                float[] rawBuildoutCoord = getBuildoutAreaSizeAndCenter(localLocation.x, localLocation.z, localLocation.area, false, false);

                float[] extBuildoutCoord = new float[3];
                extBuildoutCoord[0] = localLocation.x -  (rawBuildoutCoord[2] - (rawBuildoutCoord[0] / 2));
                extBuildoutCoord[1] = localLocation.y;
                extBuildoutCoord[2] = (localLocation.z - (rawBuildoutCoord[3] - (rawBuildoutCoord[1] / 2)));

                sb.append("\\#00FFFFBuildout Area Name: \\#FFFFFF");                
                sb.append(buildoutAreaName);
                sb.append("\n");

                sb.append("\\#00FFFFBuildout Coordinates: \\#FFFFFF");
                sb.append("\\#00FF00 X: \\#FFFFFF");

                sb.append(extBuildoutCoord[0]);
                sb.append("\\#00FF00 Y: \\#FFFFFF");
                sb.append(extBuildoutCoord[1]);
                sb.append("\\#00FF00 Z: \\#FFFFFF");
                sb.append(extBuildoutCoord[2]);
                sb.append("\n");

                sb.append("\\#00FFFFObject To World Transform: \\#FFFFFF");                
                sb.append("\n");
                sb.append(pos);
                sb.append("\n");

                AppendVectorInformation(sb, pos);

                break;

            case LOCATION_CELL:                
                obj_id cell = localLocation.cell;
                obj_id containerId = getContainedBy(cell);
                location containerLocation = getLocation(containerId);
                String cellbuildoutAreaName = getBuildoutAreaName(containerLocation.x, containerLocation.z, containerLocation.area);  
                String cellName = utils.getCellName(containerId, cell);
                int cellIndex = getCellIndex(sceneName, cellbuildoutAreaName, cell);        
                String containerName = getName(containerId);
                final transform cellPos = getTransform_o2p(target);

                sb.append("\\#00FFFFBuildout Area Name: \\#FFFFFF");                
                sb.append(cellbuildoutAreaName);
                sb.append("\n");

                sb.append("\\#00FFFFContainer Name: \\#FFFFFF");                
                sb.append(containerName);
                sb.append("\n");

                sb.append("\\#00FFFFCell Name: \\#FFFFFF");
                sb.append(cellName);
                sb.append("\n");

                sb.append("\\#00FFFFCell Index: \\#FFFFFF");
                sb.append(cellIndex);
                sb.append("\n");

                sb.append("\\#00FFFFCell Coordinates: \\#FFFFFF");
                sb.append("\\#00FF00 X: \\#FFFFFF");
                sb.append(containerLocation.x);
                sb.append("\\#00FF00 Y: \\#FFFFFF");
                sb.append(containerLocation.y);
                sb.append("\\#00FF00 Z: \\#FFFFFF");
                sb.append(containerLocation.z);
                sb.append("\n");
                
                sb.append("\\#00FFFFObject To Parent Transform: \\#FFFFFF");                
                sb.append("\n");
                sb.append(cellPos);
                sb.append("\n");

                AppendVectorInformation(sb, cellPos);

                break;

            default:
               break;
        }

    }

    private static int getLocationType(obj_id self) throws InterruptedException      
    {
        int locationType = LOCATION_UNKNOWN;
        location localLocation = null;

        localLocation = getLocation(self);

        if (isValidId(localLocation.cell))
        {
            return LOCATION_CELL;
        }

        if (isValidId(space_transition.getContainingShip(self)))    
        {
            return LOCATION_SPACE;
        }

        return LOCATION_GROUND;
    }

}

