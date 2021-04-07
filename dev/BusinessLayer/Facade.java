package BusinessLayer;

public class Facade {
    private InventoryController invCnt;
    private ReportController repCnt;

    public Facade() {
        this.repCnt = new ReportController();
        this.invCnt = new InventoryController();
    }
}
