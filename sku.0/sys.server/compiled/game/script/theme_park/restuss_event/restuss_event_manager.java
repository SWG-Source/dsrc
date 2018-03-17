package script.theme_park.restuss_event;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;
import script.library.restuss_event;
import script.library.trial;

public class restuss_event_manager extends script.base_script
{
    public restuss_event_manager()
    {
    }
    public int stepIncrease(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id[] controllers = getAllObjectsWithScript(getLocation(self), 200.0f, "theme_park.restuss_event.restuss_event_watcher");
        queryData(self, controllers);
        return SCRIPT_CONTINUE;
    }
    public void queryData(obj_id self, obj_id[] dungeon_data) throws InterruptedException
    {
        final int IMP_BAR = 0;
        final int IMP_HQ = 1;
        final int IMP_COMM = 2;
        final int IMP_LOG = 3;
        final int IMP_MED = 4;
        final int IMP_WALL = 5;
        final int REB_BAR = 6;
        final int REB_HQ = 7;
        final int REB_COMM = 8;
        final int REB_LOG = 9;
        final int REB_MED = 10;
        final int REB_WALL = 11;
        final String[] elementName = 
        {
            "imp_bar",
            "imp_head",
            "imp_com",
            "imp_log",
            "imp_med",
            "imp_wall",
            "reb_bar",
            "reb_head",
            "reb_com",
            "reb_log",
            "reb_med",
            "reb_wall"
        };
        obj_id[] elementList = new obj_id[12];
        for (int i = 0; i < dungeon_data.length; i++)
        {
            String name = getStringObjVar(dungeon_data[i], "element");
            if (name.indexOf(elementName[IMP_BAR]) > -1)
            {
                elementList[IMP_BAR] = dungeon_data[i];
            }
            if (name.indexOf(elementName[IMP_HQ]) > -1)
            {
                elementList[IMP_HQ] = dungeon_data[i];
            }
            if (name.indexOf(elementName[IMP_COMM]) > -1)
            {
                elementList[IMP_COMM] = dungeon_data[i];
            }
            if (name.indexOf(elementName[IMP_LOG]) > -1)
            {
                elementList[IMP_LOG] = dungeon_data[i];
            }
            if (name.indexOf(elementName[IMP_MED]) > -1)
            {
                elementList[IMP_MED] = dungeon_data[i];
            }
            if (name.indexOf(elementName[IMP_WALL]) > -1)
            {
                elementList[IMP_WALL] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_BAR]) > -1)
            {
                elementList[REB_BAR] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_HQ]) > -1)
            {
                elementList[REB_HQ] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_COMM]) > -1)
            {
                elementList[REB_COMM] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_LOG]) > -1)
            {
                elementList[REB_LOG] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_MED]) > -1)
            {
                elementList[REB_MED] = dungeon_data[i];
            }
            if (name.indexOf(elementName[REB_WALL]) > -1)
            {
                elementList[REB_WALL] = dungeon_data[i];
            }
        }
        int sum_imp_phase = 0;
        int sum_reb_phase = 0;
        if ((getStringObjVar(self, "element")).indexOf("imp") > -1)
        {
            int imp_bar_phase = restuss_event.getPhase(elementList[IMP_BAR]);
            int imp_hq_phase = restuss_event.getPhase(elementList[IMP_HQ]);
            int imp_com_phase = restuss_event.getPhase(elementList[IMP_COMM]);
            int imp_log_phase = restuss_event.getPhase(elementList[IMP_LOG]);
            int imp_med_phase = restuss_event.getPhase(elementList[IMP_MED]);
            int imp_wall_phase = restuss_event.getPhase(elementList[IMP_WALL]);
            sum_imp_phase = imp_bar_phase + imp_hq_phase + imp_com_phase + imp_log_phase;
            setObjVar(self, "event_controller.imperial_complete", sum_imp_phase);
            switch (sum_imp_phase)
            {
                case 2:
                if (imp_med_phase == 0)
                {
                    restuss_event.incrimentPhase(elementList[IMP_MED]);
                }
                break;
                case 4:
                if (imp_wall_phase == 0)
                {
                    restuss_event.incrimentPhase(elementList[IMP_WALL]);
                }
                break;
                case 6:
                if (imp_med_phase == 1)
                {
                    restuss_event.incrimentPhase(elementList[IMP_MED]);
                }
                break;
                case 8:
                if (imp_wall_phase == 1)
                {
                    restuss_event.incrimentPhase(elementList[IMP_WALL]);
                }
                break;
                case 10:
                if (imp_med_phase == 2)
                {
                    restuss_event.incrimentPhase(elementList[IMP_MED]);
                }
                break;
                case 12:
                if (imp_wall_phase == 2)
                {
                    restuss_event.incrimentPhase(elementList[IMP_WALL]);
                }
                break;
            }
        }
        else 
        {
            int reb_bar_phase = restuss_event.getPhase(elementList[REB_BAR]);
            int reb_hq_phase = restuss_event.getPhase(elementList[REB_HQ]);
            int reb_com_phase = restuss_event.getPhase(elementList[REB_COMM]);
            int reb_log_phase = restuss_event.getPhase(elementList[REB_LOG]);
            int reb_med_phase = restuss_event.getPhase(elementList[REB_MED]);
            int reb_wall_phase = restuss_event.getPhase(elementList[REB_WALL]);
            sum_reb_phase = reb_bar_phase + reb_hq_phase + reb_com_phase + reb_log_phase;
            setObjVar(self, "event_controller.rebel_complete", sum_reb_phase);
            switch (sum_reb_phase)
            {
                case 2:
                if (reb_med_phase == 0)
                {
                    restuss_event.incrimentPhase(elementList[REB_MED]);
                }
                break;
                case 4:
                if (reb_wall_phase == 0)
                {
                    restuss_event.incrimentPhase(elementList[REB_WALL]);
                }
                break;
                case 6:
                if (reb_med_phase == 1)
                {
                    restuss_event.incrimentPhase(elementList[REB_MED]);
                }
                break;
                case 8:
                if (reb_wall_phase == 1)
                {
                    restuss_event.incrimentPhase(elementList[REB_WALL]);
                }
                break;
                case 10:
                if (reb_med_phase == 2)
                {
                    restuss_event.incrimentPhase(elementList[REB_MED]);
                }
                break;
                case 12:
                if (reb_wall_phase == 2)
                {
                    restuss_event.incrimentPhase(elementList[REB_WALL]);
                }
                break;
            }
        }
        obj_id restuss_controller = getFirstObjectWithScript(getLocation(self), 1000.0f, "theme_park.restuss_event.stage_one_watcher");
        if (restuss_controller == null)
        {
            return;
        }
        dictionary dict = new dictionary();
        String type = "none";
        if (sum_imp_phase == 12)
        {
            type = "imperial";
        }
        if (sum_reb_phase == 12)
        {
            type = "rebel";
        }
        if (type.equals("none"))
        {
            return;
        }
        dict.put("faction", type);
        messageTo(restuss_controller, "updateStageData", dict, 1.0f, false);
    }
}
