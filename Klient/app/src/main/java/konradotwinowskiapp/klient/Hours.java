package konradotwinowskiapp.klient;

/**
 * Created by Konrad on 2017-07-17.
 */

public class Hours {
    private int id;
    private String free;
    private String hour;

    public Hours(int id,String free, String hour) {
        this.id = id;
        this.free = free;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

}
