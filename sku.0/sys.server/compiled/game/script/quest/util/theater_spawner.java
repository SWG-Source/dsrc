package script.quest.util;

import script.base_class;
import script.dictionary;
import script.obj_id;

public class theater_spawner extends script.base_script
{
    public theater_spawner()
    {
    }
    public static final String VAR_SPAWNER_CURRENT_POPULATION = "quest_spawner.current_pop";
    public static final String VAR_SPAWNED_BY = "quest_spawner.spawned_by";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        String spawner = base_class.getStringObjVar(self, "quest_spawner.type");
        if (spawner == null || spawner.length() < 1)
        {
            base_class.LOG("quest", "theater_spawner.OnInitialize -- spawner is null or empty");
            return base_class.SCRIPT_CONTINUE;
        }
        String datatable = base_class.getStringObjVar(self, "quest_spawner.datatable");
        if (datatable == null || datatable.length() < 1)
        {
            base_class.LOG("quest", "theater_spawner.OnInitialize -- datatable is null or empty");
            return base_class.SCRIPT_CONTINUE;
        }
        int index = base_class.dataTableSearchColumnForString(spawner, "type", datatable);
        if (index == -1)
        {
            base_class.LOG("quest", "theater_spawner.OnInitialize -- can't find spawner " + spawner + " within datatable " + datatable);
            return base_class.SCRIPT_CONTINUE;
        }
        dictionary row = base_class.dataTableGetRow(datatable, index);
        if (row == null)
        {
            base_class.LOG("quest", "theater_spawner.OnInitialize -- can't find data in row " + index + " for datatable " + datatable);
            return base_class.SCRIPT_CONTINUE;
        }
        int pulse = row.getInt("pulse");
        int max_spawn = row.getInt("max_spawn");
        int max_population = row.getInt("max_population");
        int expire = row.getInt("expire");
        base_class.setObjVar(self, "quest_spawner.pulse", pulse);
        base_class.setObjVar(self, "quest_spawner.max_spawn", max_spawn);
        base_class.setObjVar(self, "quest_spawner.max_pop", max_population);
        if (expire > 1)
        {
            base_class.setObjVar(self, "quest_spawner.time_expired", expire + base_class.getGameTime());
        }
        else 
        {
            base_class.setObjVar(self, "quest_spawner.time_expired", 0);
        }
        base_class.detachScript(self, "quest.util.theater_spawner");
        base_class.attachScript(self, "quest.util.quest_spawner");
        base_class.messageTo(self, "msgQuestSpawnerPulse", null, 5.0f, false);
        return base_class.SCRIPT_CONTINUE;
    }
}
