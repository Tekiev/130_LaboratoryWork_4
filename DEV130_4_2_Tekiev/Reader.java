package DEV130_4_2_Tekiev;

public class Reader {

    public void readFromDB() throws InterruptedException {
        int ID = (int) (Math.random()*10 + 1);
        DataBase.getInstance().readFromDataBase(ID);
    }

}
