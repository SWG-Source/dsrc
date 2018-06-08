package script.item.firework;

import script.obj_id;

public class premade_show extends script.base_script
{
    public premade_show()
    {
    }
    public static final int AWESOMENESS_FACTOR = 44;
    public int OnAttach(obj_id self) throws InterruptedException
    {
        String[] fireworksFx = new String[AWESOMENESS_FACTOR];
        float[] theSameTimeEveryTime = new float[AWESOMENESS_FACTOR];
        for (int i = 0; i < fireworksFx.length; i++)
        {
            int randomFx = rand(1, 5);
            fireworksFx[i] = "fx_0" + randomFx;
            theSameTimeEveryTime[i] = 1.0f;
        }
        setCount(self, AWESOMENESS_FACTOR);
        setObjVar(self, "firework.show.fx", fireworksFx);
        setObjVar(self, "firework.show.delay", theSameTimeEveryTime);
        attachScript(self, "item.firework.show");
        return SCRIPT_CONTINUE;
    }
}
