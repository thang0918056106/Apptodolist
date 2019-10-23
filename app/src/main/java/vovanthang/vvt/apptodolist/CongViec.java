package vovanthang.vvt.apptodolist;

public class CongViec {
    private String tencongviec;
    private int idCV;

    public CongViec(String tencongviec, int idCV) {
        this.tencongviec = tencongviec;
        this.idCV = idCV;
    }

    public String getTencongviec() {
        return tencongviec;
    }

    public void setTencongviec(String tencongviec) {
        this.tencongviec = tencongviec;
    }

    public int getIdCV() {
        return idCV;
    }

    public void setIdCV(int idCV) {
        this.idCV = idCV;
    }
}
