import java.util.Arrays;

/**
 * Created by madashi on 4/16/17.
 */
public class myIntArr{
    public int[] array;

    public myIntArr(int size){
        array = new int[size];
    }

    public myIntArr(int[] size){
        array = size;
    }

    @Override
    public boolean equals(Object a2){
        if(a2 instanceof myIntArr){
            myIntArr tt = (myIntArr)a2;
            return Arrays.equals(array, tt.array);
        }
        else return false;

    }

}
