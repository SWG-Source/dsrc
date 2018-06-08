package script.player;

import script.dictionary;
import script.library.slicing;
import script.library.sui;
import script.obj_id;

public class player_slicing extends script.base_script
{
    public player_slicing()
    {
    }
    public int handleSlicingCategory(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            slicing.clearSlicing(self);
            return SCRIPT_CONTINUE;
        }
        slicing.handleSlicingCategory(self, idx);
        return SCRIPT_CONTINUE;
    }
    public int handleSlicingSelect(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            slicing.clearSlicing(self);
            return SCRIPT_CONTINUE;
        }
        slicing.handleSlicingSelect(self, idx);
        return SCRIPT_CONTINUE;
    }
    public int handleApplySlice(obj_id self, dictionary params) throws InterruptedException
    {
        int idx = sui.getListboxSelectedRow(params);
        if (idx < 0)
        {
            idx = 0;
        }
        obj_id player = sui.getPlayerId(params);
        int btn = sui.getIntButtonPressed(params);
        if (btn == sui.BP_CANCEL)
        {
            slicing.clearSlicing(self);
            return SCRIPT_CONTINUE;
        }
        slicing.handleApplySlice(self, idx);
        return SCRIPT_CONTINUE;
    }
}
