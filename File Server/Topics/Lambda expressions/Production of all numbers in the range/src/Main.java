import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (x, y) -> {
        long rez=1;
        for(long i = x; i <= y; i++){
            rez*=i;
        }
        return rez;
    };
}