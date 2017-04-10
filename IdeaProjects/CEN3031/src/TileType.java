

public enum TileType{
    JJ("Jungle-Jungle"), JL("Jungle-Lake"), JG("Jungle-Grasslands"), JR("Jungle-Rocky"),
    LJ("Lake-Jungle"), LL("Lake-Lake"), LG("Lake-Grasslands"), LR("Lake-Rocky"),
    GJ("Grasslands-Jungle"), GL("Grasslands-Lake"), GG("Grasslands-Grasslands"), GR("Grasslands-Rocky"),
    RJ("Rocky-Jungle"), RL("Rocky-Lake"), RG("Rocky-Grasslands"), RR("Rocky-Rocky");

    private String tileTypeText;

    TileType(String tileTypeText) {
        this.tileTypeText = tileTypeText;
    }

    public String getTileTypeText(){
        return this.tileTypeText;
    }
}
