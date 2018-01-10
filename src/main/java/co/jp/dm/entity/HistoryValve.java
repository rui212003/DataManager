package co.jp.dm.entity;
 /**
 * Created by rui on 2018/1/9.
 * 操作履歴
 */
public class HistoryValve {

    public int hId;
    public String hUsername;
    public String hTablename;
    public String hTargetId;
    public String hDetail;
    public String hIp;
    public String hHost;
    public String hTrkDate;

    public int gethId() {
        return hId;
    }

    public void sethId(int hId) {
        this.hId = hId;
    }

    public String gethUsername() {
        return hUsername;
    }

    public void sethUsername(String hUsername) {
        this.hUsername = hUsername;
    }

    public String gethTablename() { return hTablename; }

    public void sethTablename(String hTablename) { this.hTablename = hTablename; }

    public String gethTargetId() {
        return hTargetId;
    }

    public void sethTargetId(String hTargetId) {
        this.hTargetId = hTargetId;
    }

    public String gethDetail() {
        return hDetail;
    }

    public void sethDetail(String hDetail) {
        this.hDetail = hDetail;
    }

    public String gethIp() {
        return hIp;
    }

    public void sethIp(String hIp) {
        this.hIp = hIp;
    }

    public String gethHost() {
        return hHost;
    }

    public void sethHost(String hHost) {
        this.hHost = hHost;
    }

    public String gethTrkDate() {
        return hTrkDate;
    }

    public void sethTrkDate(String hTrkDate) {
        this.hTrkDate = hTrkDate;
    }
}
