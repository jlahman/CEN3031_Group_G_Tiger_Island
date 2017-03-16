/**
 * Created by kevin on 3/15/2017.
 */
public enum Terrain {
        JUNGLE("Jungle"),
        LAKE("Lake"),
        GRASSLANDS("Grass"),
        ROCKY("Rocky"),
        VOLCANO("Volcano");
        private String terrainText;
        //Constructor
        private Terrain(String terrainText){
            this.terrainText = terrainText;
        }
        //Getter
        public String getTerrainText(){
            return terrainText;
        }
        //Setter
        public void setTerrainText(String Terrain){
            terrainText = Terrain;
        }
}

