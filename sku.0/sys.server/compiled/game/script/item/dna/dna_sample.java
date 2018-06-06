package script.item.dna;

import script.obj_id;

public class dna_sample extends script.base_script
{
    public dna_sample()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!isCrafted(self))
        {
            setCraftedId(self, self);
        }
        return SCRIPT_CONTINUE;
    }
}
