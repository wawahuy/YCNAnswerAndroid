package ml.huytools.ycnanswer.Models.Entities;


import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class CauHoi extends Entity {

    @JsonName
    private int id;

    @JsonName
    private String cauhoi;

    @JsonName
    private String paA;

    @JsonName
    private String paB;

    @JsonName
    private String paC;

    @JsonName
    private String paD;


    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public String getPaA() {
        return paA;
    }

    public void setPaA(String paA) {
        this.paA = paA;
    }

    public String getPaB() {
        return paB;
    }

    public void setPaB(String paB) {
        this.paB = paB;
    }

    public String getPaC() {
        return paC;
    }

    public void setPaC(String paC) {
        this.paC = paC;
    }

    public String getPaD() {
        return paD;
    }

    public void setPaD(String paD) {
        this.paD = paD;
    }

}