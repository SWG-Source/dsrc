package script.developer;

import script.dictionary;
import script.library.sui;
import script.library.utils;
import script.location;
import script.obj_id;
import script.transform;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * SWG Buildout Utility
 * For World Building in the Release Game Client
 * To Use: attach developer.buildout_utility to your player and say "info" for commands
 *
 * Most usage will be creating an object either with this script or by some other means,
 * targeting it, and saying "getBuildoutInfo" in spatial which will give you an SUI window
 * of everything you need to type in to add that object to a buildout.
 *
 * *************************************************
 * This is a WORK IN PROGRESS
 * I'm adding it now upon request to help a few people get the gist of buildouts.
 * *************************************************
 *
 * Authors: Aconite
 */
public class buildout_utility extends script.base_script {

    public buildout_utility()
    {
    }

    public int OnAttach(obj_id self) throws InterruptedException {
        sendSystemMessageTestingOnly(self, "Buildout Utility Attached... say INFO for help.");
        return SCRIPT_CONTINUE;
    }

    public int OnSpeaking(obj_id self, String text) throws InterruptedException, IOException {

        if (!isGod(self)) {
            return SCRIPT_CONTINUE;
        }

        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        String command1 = tok.nextToken();
        String command2 = "";
        if (tok.hasMoreTokens()) {
            if (tok.hasMoreTokens()) {
                command2 = tok.nextToken();
            }
        }

        // ===========================================================================
        // ===== info
        // ===========================================================================
        if (command1.equalsIgnoreCase("info")) {
            sendSystemMessageTestingOnly(self, "Instructions for buildout utility sent to your console.");
            sendConsoleMessage(self, "\\#ffff00 ============ buildout_utility spatial chat/speak commands ============ \\#.");
            sendConsoleMessage(self, "\\#00ffff clearObjects \\#.");
            sendConsoleMessage(self, "destroys all objects within 500m with buildout_utility.write objvar");
            sendConsoleMessage(self, "\\#00ffff createObject \\#bfff00 <template> \\#.");
            sendConsoleMessage(self, "spawns at your location the specified object adding buildout_utility.write objvar automatically");
            sendConsoleMessage(self, "\\#00ffff getBuildoutArea \\#.");
            sendConsoleMessage(self, "returns the current buildout area you are in");
            sendConsoleMessage(self, "\\#00ffff getBuildoutInfo \\#bfff00 [target OR <oid>] \\#.");
            sendConsoleMessage(self, "returns all buildout information for the specified object");
            sendConsoleMessage(self, "\\#00ffff getObjects \\#.");
            sendConsoleMessage(self, "returns all objects within 500m with buildout_utility.write objvar");
            //sendConsoleMessage(self, "\\#00ffff writeBuildout \\#bfff00 <file name> \\#.");
            //sendConsoleMessage(self, "writes all objects within 500m range with the buildout_utility.write objvar to a buildout formatted .csv file swg-main/linux/exe");
            sendConsoleMessage(self, "\\#ffff00 ============ ============ ============ ============ \\#.");
            return SCRIPT_CONTINUE;

            // ===========================================================================
            // ===== getBuildoutArea
            // ===========================================================================
        } else if (command1.equalsIgnoreCase("getBuildoutArea")) {
            location here = getLocation(self);
            obj_id containingBuilding = getTopMostContainer(self);
            if (isIdValid(containingBuilding)) {
                here = getLocation(containingBuilding);
            }
            String buildoutAreaName = getBuildoutAreaName(here.x, here.z, getCurrentSceneName());
            sendSystemMessageTestingOnly(self, "You are in buildout area: " + buildoutAreaName);


            // ===========================================================================
            // ===== getBuildoutInfo
            // ===========================================================================
        } else if (command1.equalsIgnoreCase("getBuildoutInfo")) {

            obj_id oid = utils.stringToObjId(command2);

            if (command2.isEmpty())
            {
                oid = getTarget(self);
            }

            if (!isIdValid(oid))
            {
                sendSystemMessageTestingOnly(self, "OID specified for getBuildoutInfo was not valid.");
                return SCRIPT_CONTINUE;
            }

            StringBuilder sb = new StringBuilder();

            sb.append("Buildout Information for Object: ").append(oid).append("\n\n");

            if(hasObjVar(oid, "buildoutObjectId"))
            {
                sb.append(getHeaderFormat("Object ID in Buildout File"));
                sb.append("\t").append(getIntObjVar(oid, "buildoutObjectId"));
                sb.append("\n\n");
            }

            sb.append(getHeaderFormat("Server Template")).append("\t").append(getTemplateName(oid)).append("\n\n");
            sb.append(getHeaderFormat("Shared Template")).append("\t").append(getSharedObjectTemplateName(oid)).append("\n\n");

            final location objectLoc = getLocation(oid);
            sb.append(getHeaderFormat("Buildout Area"));
            final String buildoutAreaName = getBuildoutAreaName(objectLoc.x, objectLoc.z, objectLoc.area);
            sb.append("\t").append(buildoutAreaName).append("\n\n");

            sb.append(getHeaderFormat("Container"));
            if(isIdValid(objectLoc.cell))
            {
               sb.append("\t").append(objectLoc.cell);
            }
            else
            {
                sb.append("\t").append(0);
            }
            sb.append("\n\n");

            // Cell Index
            sb.append(getHeaderFormat("Cell Index"));
            sb.append("\t").append(getCellIndex(objectLoc.cell));
            sb.append("\n\n");

            // X Y Z coordinates
            final float[] buildout = getBuildoutAreaSizeAndCenter(objectLoc.x, objectLoc.z, objectLoc.area, false, false);
            sb.append(getHeaderFormat("Object to Parent Coordinates (for Buildout) [pX, pY, pZ]"));
            if(isIdValid(objectLoc.cell))
            {
                sb.append("\t").append(objectLoc.x).append(",\n");
                sb.append("\t").append(objectLoc.y).append(",\n");
                sb.append("\t").append(objectLoc.z).append(",\n");
            }
            else
            {
                sb.append("\t").append((objectLoc.x - (buildout[2] - (buildout[0] / 2)))).append(",\n");
                sb.append("\t").append(objectLoc.y).append(",\n");
                sb.append("\t").append((objectLoc.z - (buildout[3] - (buildout[1] / 2))));
            }
            sb.append("\n\n");

            final float[] quaternion = getQuaternion(oid);
            sb.append(getHeaderFormat("Quaternion [qX, qY, qZ, qW]"));
            sb.append("\t").append(quaternion[0]).append(",\n");
            sb.append("\t").append(quaternion[1]).append(",\n");
            sb.append("\t").append(quaternion[2]).append(",\n");
            sb.append("\t").append(quaternion[3]);
            sb.append("\n\n");

            sb.append(getHeaderFormat("Scripts"));
            sb.append(Arrays.toString(getScriptList(oid)));
            sb.append("\n\n");

            sb.append(getHeaderFormat("ObjVars"));
            sb.append(getPackedObjvars(oid));
            sb.append("\n\n");

            final int pid = sui.msgbox(self, self, sb.toString(), sui.OK_ONLY, "getBuildoutInfo", sui.MSG_INFORMATION, "noHandler");
            setSUIProperty(pid, "Prompt.lblPrompt", "Editable", "true");
            setSUIProperty(pid, "Prompt.lblPrompt", "GetsInput", "true");
            flushSUIPage(pid);

            // ===========================================================================
            // ===== writeBuildout
            // ===========================================================================
        } else if (command1.equalsIgnoreCase("writeBuildout")) {

            sendSystemMessageTestingOnly(self, "This feature is currently disabled as it needs repair.");

            /*
            if (command2.isEmpty()) {
                sendSystemMessageTestingOnly(self, "[SYNTAX] writeBuildout <file name>");
                return SCRIPT_CONTINUE;
            }

            List<String> list = Arrays.asList(SCENES);
            if(!list.contains(getCurrentSceneName())) {
                sendSystemMessageTestingOnly(self, "You cannot use this feature in your current scene (likely due to world size mismatch).");
                return SCRIPT_CONTINUE;
            }

            location me = getLocation(self);
            obj_id[] objects = getAllObjectsWithObjVar(me, 500f, "buildout_utility.write");

            PrintWriter writer = new PrintWriter(command2+".csv", StandardCharsets.UTF_8);
            writer.println("objid,container,server_template_crc,cell_index,px,py,pz,qw,qx,qy,qz,scripts,objvars");
            for (obj_id i : objects) {
                String template = getTemplateName(i);
                location wp = getLocation(i);
                obj_id cell = wp.cell;
                boolean isInBuilding = isIdValid(cell);
                obj_id container = null;
                if (isInBuilding) {
                    container = getContainedBy(cell);
                }
                String scripts = Arrays.toString(getScriptList(i));
                String objvars = getPackedObjvars(i);
                float[] q = getQuaternion(i);
                String buildout = getBuildoutAreaName(wp.x, wp.z);
                float coord_x = wp.x - getBuildoutRootCoords(buildout).x;
                float coord_z = wp.z - getBuildoutRootCoords(buildout).z;
                writer.println(i+","+container+","+template+","+cell+","+coord_x+","+wp.y+","+coord_z+","+q[0]+","+q[1]+","+q[2]+","+q[3]+","+scripts+","+objvars);
            }
            writer.close();
            sendSystemMessageTestingOnly(self, "Successfully wrote file "+command2+".csv to swg-main/exe/linux...");
             */

            // ===========================================================================
            // ===== getObjects
            // ===========================================================================\
        } else if (command1.equalsIgnoreCase("getObjects")) {

            location me = getLocation(self);
            obj_id[] objects = getAllObjectsWithObjVar(me, 500f, "buildout_utility.write");

            int count = 0;
            for (obj_id i : objects) {
                count++;
            }

            String header = "Listing all objects within 500m with buildout_utility.write objvar \n Total object count: "+count+"\n\n";
            sui.msgbox(self, self, header + Arrays.toString(objects), sui.OK_ONLY, "getObjects", "noHandler");

            // ===========================================================================
            // ===== clearObjects
            // ===========================================================================
        } else if (command1.equalsIgnoreCase("clearObjects")) {

            location me = getLocation(self);
            obj_id[] objects = getAllObjectsWithObjVar(me, 500f, "buildout_utility.write");
            int count = 0;

            sendSystemMessageTestingOnly(self, "clearObjects: Destroying all objects in range with buildout_utility.write ObjVar...");

            for (obj_id i : objects) {
                sendConsoleMessage(self, "buildout_utility clearObjects: Destroying object " + getName(i) + " (" + i + ")");
                destroyObject(i);
                count++;
            }

            sendSystemMessageTestingOnly(self, "clearObjects: Finished - Deleted " + count + " objects");
            return SCRIPT_CONTINUE;

            // ===========================================================================
            // ===== createObjects
            // ===========================================================================
        } else if (command1.equalsIgnoreCase("createObject")) {

            if (command2.isEmpty()) {
                sendSystemMessageTestingOnly(self, "[SYNTAX] createObject <template>");
                return SCRIPT_CONTINUE;
            }

            location me = getLocation(self);
            obj_id object = createObject(command2, me);
            setObjVar(object, "buildout_utility.write", 1);

            if(isValidId(object)) {
                sendSystemMessageTestingOnly(self, "createObject: Success, new object OID is " + object);
            } else {
                sendSystemMessageTestingOnly(self, "createObject: ERROR creating object. Check your template spelling.");
            }

            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }

    public static int getCellIndex(obj_id cell)
    {
        if(isIdValid(cell))
        {
            return Arrays.asList(getCellIds(getTopMostContainer(cell))).indexOf(cell);
        }
        return 0;
    }

    public static boolean isWorldCoordinateOffset(String scene)
    {
        dictionary d = dataTableGetRow("datatables/buildout/buildout_scenes.iff", scene);
        return d != null && d.getInt("adjust_map_coordinates") > 0;
    }

    public static String getHeaderFormat(String header)
    {
        return "\\#00FFFF"+header+":\\#.\n";
    }



}