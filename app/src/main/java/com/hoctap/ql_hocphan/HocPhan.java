package com.hoctap.ql_hocphan;

public class HocPhan {
    private String mahp,tenhp,stc,hp_tq,hp_ss,kql;

    public HocPhan() {
    }

    public HocPhan(String mahp, String tenhp, String stc, String hp_tq, String hp_ss, String kql) {
        this.mahp = mahp;
        this.tenhp = tenhp;
        this.stc = stc;
        this.hp_tq = hp_tq;
        this.hp_ss = hp_ss;
        this.kql = kql;
    }

    public HocPhan(String tenhp, String stc, String hp_tq, String hp_ss, String kql) {
        this.tenhp = tenhp;
        this.stc = stc;
        this.hp_tq = hp_tq;
        this.hp_ss = hp_ss;
        this.kql = kql;
    }

    public String getMahp() {
        return mahp;
    }

    public void setMahp(String mahp) {
        this.mahp = mahp;
    }

    public String getTenhp() {
        return tenhp;
    }

    public void setTenhp(String tenhp) {
        this.tenhp = tenhp;
    }

    public String getStc() {
        return stc;
    }

    public void setStc(String stc) {
        this.stc = stc;
    }

    public String getHp_tq() {
        return hp_tq;
    }

    public void setHp_tq(String hp_tq) {
        this.hp_tq = hp_tq;
    }

    public String getHp_ss() {
        return hp_ss;
    }

    public void setHp_ss(String hp_ss) {
        this.hp_ss = hp_ss;
    }

    public String getKql() {
        return kql;
    }

    public void setKql(String kql) {
        this.kql = kql;
    }

    @Override
    public String toString() {
        return "HocPhan{" +
                "mahp='" + mahp + '\'' +
                ", tenhp='" + tenhp + '\'' +
                ", stc='" + stc + '\'' +
                ", hp_tq='" + hp_tq + '\'' +
                ", hp_ss='" + hp_ss + '\'' +
                ", kql='" + kql + '\'' +
                '}';
    }
}
