package DEV130_4_1_Tekiev;

public class Consumer {
    public void getProduct(){
        int productOnConsumer = (int) (Math.random()*10 + 1);
        WareHouse.getInstance().getProductFromWareHouse(productOnConsumer);
    }
}
