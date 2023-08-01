package DEV130_4_2_Tekiev;

public class Writer {

    public void writeToDB() throws InterruptedException {
        DataBase.getInstance().writeToDataBase();
    }


}
