package konradotwinowskiapp.service;

/**
 * Created by Konrad on 2017-07-03.
 */

public class Cash {

    private int id;
    private String cash;
    private String number;

    public Cash(int id, String cash, String number) {
        this.id = id;
        this.cash = cash;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
