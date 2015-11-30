package script.test;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.combat;
import script.library.sui;
import script.library.quests;
import script.library.ai_lib;
import script.library.money;
import script.library.chat;
import script.library.pclib;
import script.library.vehicle;

public class jwatson_sui_test extends script.base_script
{
    public jwatson_sui_test()
    {
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(text);
        if (tok.hasMoreTokens())
        {
            String command = tok.nextToken();
            debugConsoleMsg(self, "command is: " + command);
            if (command.equals("jw_suiTargetInfo"))
            {
                spawnSuiTargetInfo(self);
            }
            if (command.equals("jw_suiTargetViewer"))
            {
                spawnSuiTargetViewer(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public static String OBJVAR_SUI_TARGET_INFO_PID = "suiTargetInfoPid";
    public static String OBJVAR_SUI_TARGET_INFO_TARGET_OBJ_ID = "suiTargetInfoTargetObjId";
    public void spawnSuiTargetInfo(obj_id player) throws InterruptedException
    {
        int pId = createSUIPage("Script.ColorPicker", player, player, "suiTargetInfoCallbackClosedCancel");
        if (pId < 0)
        {
            return;
        }
        obj_id target = getLookAtTarget(player);
        if (target == null)
        {
            target = player;
        }
        setSUIProperty(pId, "ColorPicker", "TargetNetworkId", target.toString());
        setSUIProperty(pId, "ColorPicker", "TargetVariable", "/shared_owner/index_color_skin");
        setSUIProperty(pId, "ColorPicker", "TargetRangeMin", "0");
        setSUIProperty(pId, "ColorPicker", "TargetRangeMax", "500");
        setSUIProperty(pId, "bg.caption.lblTitle", "text", "(test) SuiTargetInfo");
        subscribeToSUIProperty(pId, "ColorPicker", "SelectedIndex");
        setSUIAssociatedObject(pId, target);
        showSUIPage(pId);
        setObjVar(player, OBJVAR_SUI_TARGET_INFO_PID, pId);
        setObjVar(player, OBJVAR_SUI_TARGET_INFO_TARGET_OBJ_ID, target);
        messageTo(player, "updateSuiTargetInfo", null, 1.0f, true);
    }
    public int updateSuiTargetInfo(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (target == null)
        {
            target = self;
        }
        obj_id oldTarget = getObjIdObjVar(self, OBJVAR_SUI_TARGET_INFO_TARGET_OBJ_ID);
        if (!oldTarget.equals(target))
        {
            setObjVar(self, OBJVAR_SUI_TARGET_INFO_TARGET_OBJ_ID, target);
            int pId = getIntObjVar(self, OBJVAR_SUI_TARGET_INFO_PID);
            setSUIProperty(pId, "ColorPicker", "TargetNetworkId", target.toString());
            setSUIProperty(pId, "ColorPicker", "TargetRangeMin", "0");
            setSUIProperty(pId, "ColorPicker", "TargetRangeMax", "500");
            flushSUIPage(pId);
        }
        messageTo(self, "updateSuiTargetInfo", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int suiTargetInfoCallbackClosedCancel(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, OBJVAR_SUI_TARGET_INFO_PID);
        removeObjVar(self, OBJVAR_SUI_TARGET_INFO_TARGET_OBJ_ID);
        return SCRIPT_CONTINUE;
    }
    public static String OBJVAR_SUI_TARGET_VIEWER_PID = "suiTargetViewerPid";
    public static String OBJVAR_SUI_TARGET_VIEWER_TARGET_OBJ_ID = "suiTargetViewerTargetObjId";
    public void spawnSuiTargetViewer(obj_id player) throws InterruptedException
    {
        int pId = createSUIPage("Script.ObjectViewer", player, player, "suiTargetViewerCallbackClosedCancel");
        if (pId < 0)
        {
            return;
        }
        obj_id target = getLookAtTarget(player);
        if (target == null)
        {
            target = player;
        }
        setSUIProperty(pId, "v", "SetObject", target.toString());
        setSUIProperty(pId, "bg.caption.lblTitle", "text", "(test) SuiTargetViewer");
        showSUIPage(pId);
        setObjVar(player, OBJVAR_SUI_TARGET_VIEWER_PID, pId);
        setObjVar(player, OBJVAR_SUI_TARGET_VIEWER_TARGET_OBJ_ID, target);
        messageTo(player, "updateSuiTargetViewer", null, 1.0f, true);
    }
    public int updateSuiTargetViewer(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id target = getLookAtTarget(self);
        if (target == null)
        {
            target = self;
        }
        obj_id oldTarget = getObjIdObjVar(self, OBJVAR_SUI_TARGET_VIEWER_TARGET_OBJ_ID);
        if (!oldTarget.equals(target))
        {
            setObjVar(self, OBJVAR_SUI_TARGET_VIEWER_TARGET_OBJ_ID, target);
            int pId = getIntObjVar(self, OBJVAR_SUI_TARGET_VIEWER_PID);
            setSUIProperty(pId, "v", "SetObject", target.toString());
            flushSUIPage(pId);
        }
        messageTo(self, "updateSuiTargetViewer", null, 1.0f, true);
        return SCRIPT_CONTINUE;
    }
    public int suiTargetViewerCallbackClosedCancel(obj_id self, dictionary params) throws InterruptedException
    {
        removeObjVar(self, OBJVAR_SUI_TARGET_VIEWER_TARGET_OBJ_ID);
        removeObjVar(self, OBJVAR_SUI_TARGET_VIEWER_PID);
        return SCRIPT_CONTINUE;
    }
}
