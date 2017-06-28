package konradotwinowskiapp.service;

/**
 * Created by Konrad on 2017-06-28.
 */

public class DataProvider {

    private String kasa;

    public String getKasa() {
        return kasa;
    }

    public void setKasa(String kasa) {
        this.kasa = kasa;
    }

    public DataProvider(String kasa){
        this.kasa = kasa;
    }
}
