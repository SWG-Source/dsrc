package script.developer;

import script.library.sui;
import script.library.utils;
import script.location;
import script.obj_id;

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

    // Only list planets in the SCENES array that are full size (16000x16000)
    // Only worlds at full size using -8192 to 8192 coordinates should be used otherwise the positioning will be off because the buildout names won't match to the correct positioning boxes.
    public static final String[] SCENES = {
            "tatooine",
            "yavin4",
            "mustafar",
            "endor",
            "talus",
            "corellia",
            "rori",
            "dathomir",
            "dantooine",
            "naboo",
            "lok"
    };

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

            if (command2.isEmpty()) {
                oid = getTarget(self);
            }

            if (!isIdValid(oid)) {
                sendSystemMessageTestingOnly(self, "OID specified for getBuildoutInfo was not valid.");
                return SCRIPT_CONTINUE;
            }

            List<String> list = Arrays.asList(SCENES);
            if(!list.contains(getCurrentSceneName())) {
                sendSystemMessageTestingOnly(self, "You cannot use this feature in your current scene (likely due to world size mismatch).");
                return SCRIPT_CONTINUE;
            }

            location wp = getLocation(oid);
            String buildout = getBuildoutAreaName(wp.x, wp.z);
            float coord_x = wp.x - getBuildoutRootCoords(buildout).x;
            float coord_z = wp.z - getBuildoutRootCoords(buildout).z;
            obj_id cell = wp.cell;
            float[] q = getQuaternion(oid);
            boolean isInBuilding = isIdValid(cell);
            obj_id container = null;
            if (isInBuilding) {
                container = getContainedBy(cell);
            }
            String message = "Buildout Information for Object: " + oid + "\n\n" +
                    "Reminder, this currently supports EXTERIOR objects only. Interiors should probably be using a dungeon_spawner or interior spawn egg anyways." + "\n\n" +
                    "template: " + getTemplateName(oid) + "\n\n" +
                    "buildout: " + buildout + "\n\n" +
                    "cell ID: " + cell + "\n\n" +
                    "container: " + container + "\n\n" +
                    "world coordinates (X, Y, Z): "+wp.x+", "+wp.y+", "+wp.z + "\n\n" +
                    "buildout coordinates (X, Y, Z): " + coord_x+", "+wp.y+", "+coord_z+"\n\n" +
                    "quaternion (qW, qX, qY, qZ): " + q[0] + ", " + q[1] + ", " + q[2] + ", " + q[3] + " \n\n" +
                    "scripts: " + Arrays.toString(getScriptList(oid)) + "\n\n" +
                    "objvars: " + getPackedObjvars(oid);

            sui.msgbox(self, self, message, sui.OK_ONLY, "getBuildoutCoords", "noHanlder");

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

    // ===========================================================================
    // ===== getBuildoutRootCoords
    // ===========================================================================
    public static location getBuildoutRootCoords(String buildoutAreaName) throws InterruptedException {
        String buildoutAreaNumber = buildoutAreaName.substring(buildoutAreaName.length() - 3);

        switch (buildoutAreaNumber) {
            case "1_1":
                return new location(-8196f, 0f, -8196f);
            case "1_2":
                return new location(-8196f, 0f, -6144f);
            case "1_3":
                return new location(-8196f, 0f, -4096f);
            case "1_4":
                return new location(-8196f, 0f, -2048f);
            case "1_5":
                return new location(-8196f, 0f, 0f);
            case "1_6":
                return new location(-8196f, 0f, 2048f);
            case "1_7":
                return new location(-8196f, 0f, 4096f);
            case "1_8":
                return new location(-8196f, 0f, 6144f);
            case "2_1":
                return new location(-6144f, 0f, -8196f);
            case "2_2":
                return new location(-6144f, 0f, -6144f);
            case "2_3":
                return new location(-6144f, 0f, -4096f);
            case "2_4":
                return new location(-6144f, 0f, -2048f);
            case "2_5":
                return new location(-6144f, 0f, 0f);
            case "2_6":
                return new location(-6144f, 0f, 2048f);
            case "2_7":
                return new location(-6144f, 0f, 4096f);
            case "2_8":
                return new location(-6144f, 0f, 6144f);
            case "3_1":
                return new location(-4096f, 0f, -8196f);
            case "3_2":
                return new location(-4096f, 0f, -6144f);
            case "3_3":
                return new location(-4096f, 0f, -4096f);
            case "3_4":
                return new location(-4096f, 0f, -2048f);
            case "3_5":
                return new location(-4096f, 0f, 0f);
            case "3_6":
                return new location(-4096f, 0f, 2048f);
            case "3_7":
                return new location(-4096f, 0f, 4096f);
            case "3_8":
                return new location(-4096f, 0f, 6144f);
            case "4_1":
                return new location(-2048f, 0f, -8196f);
            case "4_2":
                return new location(-2048f, 0f, -6144f);
            case "4_3":
                return new location(-2048f, 0f, -4096f);
            case "4_4":
                return new location(-2048f, 0f, -2048f);
            case "4_5":
                return new location(-2048f, 0f, 0f);
            case "4_6":
                return new location(-2048f, 0f, 2048f);
            case "4_7":
                return new location(-2048f, 0f, 4096f);
            case "4_8":
                return new location(-2048f, 0f, 6144f);
            case "5_1":
                return new location(0f, 0f, -8196f);
            case "5_2":
                return new location(0f, 0f, -6144f);
            case "5_3":
                return new location(0f, 0f, -4096f);
            case "5_4":
                return new location(0f, 0f, -2048f);
            case "5_5":
                return new location(0f, 0f, 0f);
            case "5_6":
                return new location(0f, 0f, 2048f);
            case "5_7":
                return new location(0f, 0f, 4096f);
            case "5_8":
                return new location(0f, 0f, 6144f);
            case "6_1":
                return new location(2048f, 0f, -8196f);
            case "6_2":
                return new location(2048f, 0f, -6144f);
            case "6_3":
                return new location(2048f, 0f, -4096f);
            case "6_4":
                return new location(2048f, 0f, -2048f);
            case "6_5":
                return new location(2048f, 0f, 0f);
            case "6_6":
                return new location(2048f, 0f, 2048f);
            case "6_7":
                return new location(2048f, 0f, 4096f);
            case "6_8":
                return new location(2048f, 0f, 6144f);
            case "7_1":
                return new location(4096f, 0f, -8196f);
            case "7_2":
                return new location(4096f, 0f, -6144f);
            case "7_3":
                return new location(4096f, 0f, -4096f);
            case "7_4":
                return new location(4096f, 0f, -2048f);
            case "7_5":
                return new location(4096f, 0f, 0f);
            case "7_6":
                return new location(4096f, 0f, 2048f);
            case "7_7":
                return new location(4096f, 0f, 4096f);
            case "7_8":
                return new location(4096f, 0f, 6144f);
            case "8_1":
                return new location(6144f, 0f, -8196f);
            case "8_2":
                return new location(6144f, 0f, -6144f);
            case "8_3":
                return new location(6144f, 0f, -4096f);
            case "8_4":
                return new location(6144f, 0f, -2048f);
            case "8_5":
                return new location(6144f, 0f, 0f);
            case "8_6":
                return new location(6144f, 0f, 2048f);
            case "8_7":
                return new location(6144f, 0f, 4096f);
            case "8_8":
                return new location(6144f, 0f, 6144f);
            default:
                return new location(0f, 0f, 0f);
        }
    }

}