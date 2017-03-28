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
        else
            this.meepleCount = 999; //This is just a placeholder that will be changed when we update later
    }

    public void setTotoroCount(int totoroCount) {
        if(totoroCount>=0)
            this.totoroCount = totoroCount;
        else
            this.totoroCount = 999; //Placeholder
    }

    public void setTigerCount(int tigerCount) {
        if(tigerCount>=0)
            this.tigerCount = tigerCount;
        else
            this.tigerCount = 999; //Placeholder
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
