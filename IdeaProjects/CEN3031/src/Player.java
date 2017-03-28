public class Player {
    private int meepleCount;
    private int totoroCount;
    private int tigerCount;

    public Player() {
        setMeepleCount(20);
        setTotoroCount(3);
        setTigerCount(2);
    }

    public void setMeepleCount(int meepleCount) {
        if(meepleCount>=0)
            this.meepleCount = meepleCount;
    }

    public void setTotoroCount(int totoroCount) {
        if(totoroCount>=0)
            this.totoroCount = totoroCount;
    }

    public void setTigerCount(int tigerCount) {
        if(tigerCount>=0)
            this.tigerCount = tigerCount;
    }

    public int getMeepleCount() {
        return meepleCount;
    }

    public int getTotoroCount() {
        return totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }
}
