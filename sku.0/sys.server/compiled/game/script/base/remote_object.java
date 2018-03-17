package script.base;

public class remote_object extends script.base_script
{
    public remote_object()
    {
    }
    public static final String REMOTE_OBJECT_MANAGER = "remote_object_manager";
    public static final String BASE_NAME = "remote_object";
    public static final String ATTACH_SCRIPTS = BASE_NAME + ".attachScripts";
    public static final String CREATE_PARAMS = BASE_NAME + ".createParams";
    public static final String CREATOR = BASE_NAME + ".creator";
    public static final String IN_SPACE = BASE_NAME + ".inSpace";
    public static final String OBJECT_NAME = BASE_NAME + ".objectName";
    public static final String REMOTE_OBJECT = BASE_NAME + ".remoteObject";
    public static final String REQUESTER = BASE_NAME + ".requester";
    public static final String SCENE_NAME = BASE_NAME + ".sceneName";
    public static final String USE_RANDOM_LOCATION = BASE_NAME + ".useRandomLocation";
    public static final String X = BASE_NAME + ".x";
    public static final String Y = BASE_NAME + ".y";
    public static final String Z = BASE_NAME + ".z";
    public static final String CREATE_REMOTE_OBJECT = "createRemoteObject";
    public static final String CREATE_REMOTE_OBJECT_RESPONSE = "createRemoteObjectResponse";
    public static final String RO = "RemoteObject";
    public static final boolean LOGS_ENABLED = false;
    public void log(String text) throws InterruptedException
    {
        log(text, LOGS_ENABLED);
    }
    public void logError(String text) throws InterruptedException
    {
        log("Error:" + text, true);
    }
    public void log(String text, boolean force) throws InterruptedException
    {
        if (LOGS_ENABLED || force)
        {
            LOG(RO, text);
        }
    }
    public boolean logsEnabled() throws InterruptedException
    {
        return LOGS_ENABLED;
    }
}
