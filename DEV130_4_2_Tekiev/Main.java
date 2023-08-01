package DEV130_4_2_Tekiev;

public class Main {

    public static void main(String[] args) {
        DataBase.getInstance().getAllOrders();
        new  Main().start();
    }
    public void start(){
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    Reader reader = new Reader();
                    try {
                        reader.readFromDB();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    Writer writer = new Writer();
                    try {
                        writer.writeToDB();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

}
