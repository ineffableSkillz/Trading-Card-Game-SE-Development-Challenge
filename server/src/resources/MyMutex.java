package resources;


/* Make shift Mutex for listOfUnits
 * getPermission() must be called before any access to listOfUnits
 * relinquishPermission must be called after any access to listOfunits
 */
public class MyMutex {

    /* Singleton Setup */
    private MyMutex() {}

    private static MyMutex instance;

    public static MyMutex getInstance() {

        if(instance == null)
            instance = new MyMutex();

        return instance;


    }


    /* Mutex Methods */
    private static final Object lock = new Object();
    private static boolean listLocked = false;

    public static void getPermission() {

        while(listLocked);
        synchronized(lock) {
            while(listLocked);
            listLocked = true;
        }
    }

    public static void relinquishPermission() {
        listLocked = false;
    }

    public static boolean isListLocked() {
        return listLocked;
    }


}