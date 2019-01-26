package script.poi.template.scene.camp.jawa;

import script.dictionary;
import script.library.locations;
import script.library.theater;
import script.library.utils;
import script.location;
import script.obj_id;

import java.util.Arrays;
import java.util.Vector;

public class camp_lrg extends script.poi.template.scene.camp.jawa.base
{
    public camp_lrg()
    {
    }
    public static final int COL_MAX = 3;
    public int handleTheaterSetup(obj_id self, dictionary params) throws InterruptedException
    {
        if (dataTableOpen(TBL))
        {
            Vector children = new Vector();
            children.setSize(0);
            for (int n = 0; n < COL_MAX; n++)
            {
                String[] tpfArray = dataTableGetStringColumn(TBL, n);
                Vector tpf = new Vector(Arrays.asList(tpfArray));
                if ((tpf == null) || (tpf.size() == 0))
                {
                    destroyObject(self);
                    return SCRIPT_CONTINUE;
                }
                if (n == COL_MAX - 1)
                {
                    tpf = utils.removeElementAt(tpf, 0);
                }
                int cnt = rand(4, 7) - n;
                for (int i = 0; i < cnt; i++)
                {
                    int idx = rand(0, tpf.size() - 1);
                    String template = ((String)tpf.get(idx));
                    if ((template != null) && (!template.equals("")))
                    {
                        location myLoc = getLocation(self);
                        float min = 6.0f * (n + 1);
                        float max = 12.0f * (n + 1);
                        location c = utils.getRandomLocationInRing(myLoc, min, max);
                        float iSize = n * n;
                        float aSize = 3 * iSize;
                        location here = locations.getGoodLocationAroundLocation(c, iSize, iSize, aSize, aSize);
                        if (here == null)
                        {
                            here = c;
                        }
                        here.y = getHeightAtLocation(here.x, here.z);
                        obj_id child = createObject(template, here);
                        if ((child == null) || (child == obj_id.NULL_ID))
                        {
                        }
                        else 
                        {
                            setYaw(child, rand(-180, 180));
                            children = utils.addElement(children, child);
                        }
                    }
                }
            }
            if ((children == null) || (children.size() == 0))
            {
            }
            else 
            {
                setObjVar(self, theater.VAR_CHILDREN, children);
            }
        }
        return SCRIPT_CONTINUE;
    }
}
