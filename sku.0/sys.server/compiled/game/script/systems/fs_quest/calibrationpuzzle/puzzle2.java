package script.systems.fs_quest.calibrationpuzzle;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class puzzle2 extends script.base_script
{
    public puzzle2()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        launchCalibPuzzle2(self);
        return SCRIPT_CONTINUE;
    }
    public boolean launchCalibPuzzle2(obj_id self) throws InterruptedException
    {
        int pid = createSUIPage("/Script.calibration.game2", self, self, "calibpuzzle2Callback");
        utils.setScriptVar(self, "calibpuzzle2_pid", pid);
        debugSpeakMsg(self, "calibpuzzle2 FOO pid = " + pid);
        if (pid < 0)
        {
            return false;
        }
        int bar0 = rand(0, 2);
        int bar1 = rand(0, 1);
        if (bar1 == 0)
        {
            bar1 = -1;
        }
        bar1 += bar0;
        if (bar1 == -1)
        {
            bar1 = 2;
        }
        if (bar1 == 3)
        {
            bar1 = 0;
        }
        int i = 0;
        int bar2 = 0;
        for (i = 0; i < 3; i++)
        {
            if ((i != bar0) && (i != bar1))
            {
                bar2 = i;
            }
        }
        utils.setScriptVar(self, "calibpuzzle2_bar0", bar0);
        utils.setScriptVar(self, "calibpuzzle2_bar1", bar1);
        utils.setScriptVar(self, "calibpuzzle2_bar2", bar2);
        float coeff0 = rand();
        float coeff1 = rand();
        float coeff2 = rand();
        utils.setScriptVar(self, "calibpuzzle2_coeff0", coeff0);
        utils.setScriptVar(self, "calibpuzzle2_coeff1", coeff1);
        utils.setScriptVar(self, "calibpuzzle2_coeff2", coeff2);
        float sliderPos0 = rand();
        float sliderPos1 = rand();
        float sliderPos2 = rand();
        utils.setScriptVar(self, "calibpuzzle2_barPos0", sliderPos0);
        utils.setScriptVar(self, "calibpuzzle2_barPos1", sliderPos1);
        utils.setScriptVar(self, "calibpuzzle2_barPos2", sliderPos2);
        setSUIProperty(pid, "%slider1%", "Value", "" + (100 * sliderPos0));
        setSUIProperty(pid, "%slider2%", "Value", "" + (100 * sliderPos1));
        setSUIProperty(pid, "%slider3%", "Value", "" + (100 * sliderPos2));
        utils.setScriptVar(self, "calibpuzzle2_moves", 25);
        setSUIProperty(pid, "%attemptsRemaining%", "Text", "" + 25);
        float n;
        float c0 = ((bar0 == 0) ? sliderPos0 : ((bar0 == 1) ? sliderPos1 : sliderPos2));
        float c1 = ((bar1 == 0) ? sliderPos0 : ((bar1 == 1) ? sliderPos1 : sliderPos2));
        float c2 = ((bar2 == 0) ? sliderPos0 : ((bar2 == 1) ? sliderPos1 : sliderPos2));
        for (i = 0; i < 7; i++)
        {
            n = i / 7.0f;
            float value = c0 + (c1 * n) + (c2 * n * n);
            if (value < 0.0f)
            {
                value = 0.0f;
            }
            if (value > 1.0f)
            {
                value = 1.0f;
            }
            setSUIProperty(pid, "%barTop" + i + "%", "Value", "" + (100 * value));
            setSUIProperty(pid, "%barTop" + i + "%", "RunScript", "");
        }
        for (i = 0; i < 7; i++)
        {
            n = i / 7.0f;
            float value = coeff0 + (coeff1 * n) + (coeff2 * n * n);
            if (value < 0.0f)
            {
                value = 0.0f;
            }
            if (value > 1.0f)
            {
                value = 1.0f;
            }
            setSUIProperty(pid, "%barBottom" + i + "%", "Value", "" + (100 * value));
            setSUIProperty(pid, "%barBottom" + i + "%", "RunScript", "");
        }
        setSUIProperty(pid, "%okButton%", "IsDefaultButton", "true");
        subscribeToSUIEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "calibpuzzle2Callback");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider1%", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider2%", "Value");
        subscribeToSUIPropertyForEvent(pid, sui_event_type.SET_onButton, "%buttonOk%", "%slider3%", "Value");
        setSUIAssociatedObject(pid, self);
        showSUIPage(pid);
        return true;
    }
    public int calibpuzzle2Callback(obj_id self, dictionary params) throws InterruptedException
    {
        debugSpeakMsg(self, "calibpuzzle2Callback");
        String widgetName = params.getString("eventWidgetName");
        int pid = utils.getIntScriptVar(self, "calibpuzzle2_pid");
        debugSpeakMsg(self, "calibpuzzle2 id = " + pid);
        if (widgetName.equalsIgnoreCase("%buttonCancel%") || widgetName.equalsIgnoreCase(""))
        {
            debugSpeakMsg(self, "You lost.");
            detachScript(self, "systems.fs_quest.calibrationpuzzle.puzzle2");
            forceCloseSUIPage(pid);
        }
        else if (widgetName.equalsIgnoreCase("%buttonOk%"))
        {
            debugSpeakMsg(self, "Inside buttonOK");
            float sliderPos0 = utils.stringToInt(params.getString("%slider1%.Value"));
            float sliderPos1 = utils.stringToInt(params.getString("%slider2%.Value"));
            float sliderPos2 = utils.stringToInt(params.getString("%slider3%.Value"));
            int bar0 = utils.getIntScriptVar(self, "calibpuzzle2_bar0");
            int bar1 = utils.getIntScriptVar(self, "calibpuzzle2_bar1");
            int bar2 = utils.getIntScriptVar(self, "calibpuzzle2_bar2");
            float c0 = ((bar0 == 0) ? sliderPos0 : ((bar0 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float c1 = ((bar1 == 0) ? sliderPos0 : ((bar1 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float c2 = ((bar2 == 0) ? sliderPos0 : ((bar2 == 1) ? sliderPos1 : sliderPos2)) / 100.0f;
            float coeff0 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff0");
            float coeff1 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff1");
            float coeff2 = utils.getFloatScriptVar(self, "calibpuzzle2_coeff2");
            debugSpeakMsg(self, "Inside buttonOK 2");
            debugSpeakMsg(self, "right answer: " + coeff0 + " , " + coeff1 + " , " + coeff2);
            if (Math.abs(coeff0 - c0) + Math.abs(coeff1 - c1) + Math.abs(coeff2 - c2) < 0.1)
            {
                debugSpeakMsg(self, "You won!");
                detachScript(self, "systems.fs_quest.calibrationpuzzle.puzzle2");
                forceCloseSUIPage(pid);
            }
            int currentMoves = utils.getIntScriptVar(self, "calibpuzzle2_moves");
            currentMoves--;
            utils.setScriptVar(self, "calibpuzzle2_moves", currentMoves);
            debugSpeakMsg(self, "Inside buttonOK 3, currentMoves = " + currentMoves);
            if (currentMoves == 0)
            {
                debugSpeakMsg(self, "You lost.");
                detachScript(self, "systems.fs_quest.calibrationpuzzle.puzzle2");
                forceCloseSUIPage(pid);
            }
            setSUIProperty(pid, "%attemptsRemaining%", "Text", "" + currentMoves);
            debugSpeakMsg(self, "bar0 = " + bar0 + " bar1 = " + bar1 + " bar2 = " + bar2);
            debugSpeakMsg(self, "c0 = " + c0 + " c1 = " + c1 + " c2 = " + c2);
            float n;
            for (int i = 0; i < 7; i++)
            {
                n = i / 7.0f;
                float value = c0 + (c1 * n) + (c2 * n * n);
                if (value < 0.0f)
                {
                    value = 0.0f;
                }
                if (value > 1.0f)
                {
                    value = 1.0f;
                }
                setSUIProperty(pid, "%barTop" + i + "%", "Value", "" + (100 * value));
                setSUIProperty(pid, "%barTop" + i + "%", "RunScript", "");
            }
            debugSpeakMsg(self, "Inside buttonOK 4, currentMoves = " + currentMoves);
            flushSUIPage(pid);
            return SCRIPT_CONTINUE;
        }
        else 
        {
            debugSpeakMsg(self, "calibpuzzle2 got invalid widget, widget = '" + widgetName + "'");
        }
        return SCRIPT_CONTINUE;
    }
}
