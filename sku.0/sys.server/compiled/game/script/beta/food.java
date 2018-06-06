package script.beta;

import script.obj_id;

public class food extends script.base_script
{
    public food()
    {
    }
    public static final String SCRIPT_BETA_FOOD = "beta.food";
    public int OnAttach(obj_id self) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
}
