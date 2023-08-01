package DEV130_4_1_Tekiev;

public class Main {

    public static void main(String[] args)  {
       new Main().start();
    }
    public void start(){
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true){
                    Provider provider = new Provider();
                    provider.putProduct();
                }
            }).start();
        }
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    while (true) {
                        Consumer consumer = new Consumer();
                        consumer.getProduct();
                    }
                }).start();
            }
    }
}
