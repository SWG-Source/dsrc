package script.faction_perk.hq;

import script.dictionary;
import script.library.gcw;
import script.library.utils;
import script.obj_id;

public class planetary_base_register extends script.base_script
{
    public planetary_base_register()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setControlData(self);
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setControlData(self);
        messageTo(self, "performQuery", null, 30.0f, false);
        return SCRIPT_CONTINUE;
    }
    public int OnPreloadComplete(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public int performQuery(obj_id self, dictionary params) throws InterruptedException
    {
        queryPlanetaryBaseData(self);
        return SCRIPT_CONTINUE;
    }
    private void setControlData(obj_id self) throws InterruptedException
    {
        utils.setScriptVar(getPlanetByName(getLocation(self).area), gcw.GCW_BASE_MANAGER, self);
    }
    public int OnClusterWideDataResponse(obj_id self, String manage_name, String dungeon_name, int request_id, String[] element_name_list, dictionary[] dungeon_datas, int lock_key) throws InterruptedException
    {
        if (dungeon_name.startsWith("base_cwdata"))
        {
            if (dungeon_datas == null || dungeon_datas.length == 0)
            {
                releaseClusterWideDataLock(manage_name, lock_key);
                addToBaseCount(self, 0, 0);
                return SCRIPT_CONTINUE;
            }
            int imperial = 0;
            int rebel = 0;
            for (dictionary dataItem : dungeon_datas) {
                if (dataItem.getInt("faction") == gcw.FACTION_REBEL && (dataItem.getString("scene")).equals(getLocation(self).area)) {
                    rebel++;
                }
                if (dataItem.getInt("faction") == gcw.FACTION_IMPERIAL && (dataItem.getString("scene")).equals(getLocation(self).area)) {
                    imperial++;
                }
            }
            addToBaseCount(self, rebel, imperial);
        }
        return SCRIPT_CONTINUE;
    }
    private void queryPlanetaryBaseData(obj_id self) throws InterruptedException
    {
        getClusterWideData("gcw_player_base", "base_cwdata_manager*", true, self);
    }
    private void addToBaseCount(obj_id self, int rebel, int imperial) throws InterruptedException
    {
        obj_id planet = getPlanetByName(getLocation(self).area);
        int curReb = gcw.getRebelBaseCount(planet);
        int curImp = gcw.getImperialBaseCount(planet);
        utils.setScriptVar(planet, gcw.GCW_BASE_COUNT_REBEL, ((curReb + rebel) >= 0 ? (curReb + rebel) : 0));
        utils.setScriptVar(planet, gcw.GCW_BASE_COUNT_IMPERIAL, ((curImp + imperial) >= 0 ? (curImp + imperial) : 0));
    }
    public int alterBaseCount(obj_id self, dictionary params) throws InterruptedException
    {
        int faction = params.getInt("faction");
        int delta = params.getInt("delta");
        if (faction == gcw.FACTION_REBEL)
        {
            addToBaseCount(self, delta, 0);
        }
        else 
        {
            addToBaseCount(self, 0, delta);
        }
        return SCRIPT_CONTINUE;
    }
}
