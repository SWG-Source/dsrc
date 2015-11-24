package script.item.loot_kits.simple_kit_scripts;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class viewscreen_printer extends script.base_script
{
    public viewscreen_printer()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        messageTo(self, "setUp", null, 2, false);
        return SCRIPT_CONTINUE;
    }
    public int setUp(obj_id self, dictionary params) throws InterruptedException
    {
        String[] needs = new String[5];
        needs[0] = "object/tangible/loot/simple_kit/paint_cartridge.iff";
        needs[1] = "object/tangible/loot/simple_kit/paint_disperser.iff";
        needs[2] = "object/tangible/loot/simple_kit/flat_canvas.iff";
        needs[3] = "object/tangible/loot/simple_kit/viewscreen_reader.iff";
        needs[4] = "object/tangible/loot/tool/viewscreen_broken_s2.iff";
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
            painting = "object/tangible/painting/painting_cargoport.iff";
            break;
            case 2:
            painting = "object/tangible/painting/painting_skyscraper.iff";
            break;
            case 3:
            painting = "object/tangible/painting/painting_freedom.iff";
            break;
        }
        return painting;
    }
}
