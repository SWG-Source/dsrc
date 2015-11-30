package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class broken_viewscreen extends script.base_script
{
    public broken_viewscreen()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[4];
        needs[0] = "object/tangible/loot/simple_kit/flat_canvas.iff";
        needs[1] = "object/tangible/loot/simple_kit/paint_cartridge.iff";
        needs[2] = "object/tangible/loot/simple_kit/paint_disperser.iff";
        needs[3] = "object/tangible/loot/tool/viewscreen_broken_s1.iff";
        setObjVar(self, "needs", needs);
        setObjVar(self, "overview", needs);
        String painting = pickPaintingToMake();
        setObjVar(self, "reward", painting);
        return SCRIPT_CONTINUE;
    }
    public String pickPaintingToMake() throws InterruptedException
    {
        String painting = "object/tangible/painting/painting_tree.iff";
        int which = rand(1, 3);
        switch (which)
        {
            case 1:
            painting = "object/tangible/painting/painting_bioengineer_orange.iff";
            break;
            case 2:
            painting = "object/tangible/painting/painting_double_helix.iff";
            break;
            case 3:
            painting = "object/tangible/painting/painting_droid_bright.iff";
            break;
        }
        return painting;
    }
}
