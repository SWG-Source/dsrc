package script.quest.crowd_pleaser;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

public class dance_reward extends script.base_script
{
    public dance_reward()
    {
    }
    public static final String DANCE_NAME = "theatrical";
    public static final String ADVANCED_DANCE_NAME = "theatrical2";
    public static final String ADVANCEMENT_OBJVAR = "dance_advancement";
    public static final String REMAIN_OBJVAR = ADVANCEMENT_OBJVAR + "." + DANCE_NAME + ".remaining";
    public static final String GRANT_OBJVAR = ADVANCEMENT_OBJVAR + "." + DANCE_NAME + ".grants";
    public static final String INDEX_OBJVAR = ADVANCEMENT_OBJVAR + "." + DANCE_NAME + ".index";
    public static final String COST_OBJVAR = ADVANCEMENT_OBJVAR + "." + DANCE_NAME + ".cost";
    public static final String DATATABLE_PERFORMANCE = "datatables/performance/performance.iff";
    public static final String ADVANCEMENT_SCRIPT = "systems.skills.performance.dance_advancement";
    public static final int PRACTICE_XP_COST = 100000;
    public static final int ADVANCEMENT_XP_COST = 400000;
    public int handleLearnSuccess(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id player = params.getObjId("player");
        int idx = dataTableSearchColumnForString(DANCE_NAME, 0, DATATABLE_PERFORMANCE);
        if (idx < 0)
        {
            return SCRIPT_CONTINUE;
        }
        idx++;
        setObjVar(player, REMAIN_OBJVAR, PRACTICE_XP_COST);
        setObjVar(player, COST_OBJVAR, ADVANCEMENT_XP_COST);
        setObjVar(player, GRANT_OBJVAR, ADVANCED_DANCE_NAME);
        setObjVar(player, INDEX_OBJVAR, idx);
        if (!hasScript(player, ADVANCEMENT_SCRIPT))
        {
            attachScript(player, ADVANCEMENT_SCRIPT);
        }
        destroyObject(self);
        return SCRIPT_CONTINUE;
    }
}
