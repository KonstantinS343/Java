import java.util.Locale;

class Seven {
    public static SeptenaryStringFunction fun = (q,w,e,r,t,y,u)->{return (q+w+e+r+t+y+u).toUpperCase();};
}

@FunctionalInterface
interface SeptenaryStringFunction {
    String apply(String s1, String s2, String s3, String s4, String s5, String s6, String s7);
}