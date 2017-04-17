public class Player {
    private int meepleCount;
    private int totoroCount;
    private int tigerCount;
    private int score;
    private String name;


    public Player(String name) {
        meepleCount = 20;
        totoroCount = 3;
        tigerCount = 2;
        score = 0;
        this.name = name;
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

    public void increaseScore(int delta){
        score = score + delta;
    }

    public void decreaseMeepleCount(int i){
        this.meepleCount = this.meepleCount - i;
    }

    public void decreaseTotoroCount(int i){
        this.totoroCount = this.totoroCount - i;
    }

    public void decreaseTigerCount(int i){
        this.tigerCount = this.tigerCount - i;
    }

    public int getScore(){
        return score;
    }
}
