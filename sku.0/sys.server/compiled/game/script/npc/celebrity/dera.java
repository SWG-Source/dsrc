package script.npc.celebrity;

import script.obj_id;

public class dera extends script.base_script
{
    public dera()
    {
    }
    public static final String CONVO = "celebrity/dera_darklighter";
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        setName(self, "Dera Darklighter");
        return SCRIPT_CONTINUE;
    }
}
