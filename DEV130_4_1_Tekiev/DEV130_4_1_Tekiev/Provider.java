package DEV130_4_1_Tekiev;

public class Provider {
    public void putProduct() {
        int productOnProvider = (int) (Math.random() * 5 + 1);
        WareHouse.getInstance().putProductToWareHouse(productOnProvider);
    }
}
