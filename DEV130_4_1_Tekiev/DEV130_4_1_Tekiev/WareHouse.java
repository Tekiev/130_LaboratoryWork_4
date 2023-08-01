package DEV130_4_1_Tekiev;

public class WareHouse {
    private int productOnWareHouse;
    private static WareHouse wareHouse;
    private WareHouse() {
    }
    public synchronized static WareHouse getInstance() {
        if (wareHouse == null) wareHouse = new WareHouse();
        return wareHouse;
    }
    public synchronized void putProductToWareHouse(int product) {
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Поставщик " + Thread.currentThread().getName() + " приехал на склад и доставил товар в количестве " + product + " шт.");
        productOnWareHouse += product;
        System.out.println("Поставщик  " + Thread.currentThread().getName() + " покинул склад. На складе осталось товара в количестве " + productOnWareHouse + " шт.");
        try {
            wait((int) (Math.random() * 1000));
        } catch (InterruptedException e) {}
        notify();
    }
    public synchronized void getProductFromWareHouse(int product) {
        try {
            Thread.sleep((int) (Math.random() * 2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Потребитель " + Thread.currentThread().getName() + " пришел на склад за товаром в количестве " +  product + " шт.");
        while (productOnWareHouse < product) {
            try {
                System.out.println("Потребитель " + Thread.currentThread().getName() + " ожитает товар на складе в количестве " + product + " шт.");
                wait();
            } catch (InterruptedException e) {}
        }
        productOnWareHouse -= product;
        System.out.println("Потребитель " + Thread.currentThread().getName() + " получил со склада товар в количестве " +  product + " шт. и покинул склад. На складе осталось товара в количестве " + productOnWareHouse + " шт.");
        notify();
    }
}
