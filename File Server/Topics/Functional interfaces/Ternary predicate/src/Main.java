class Predicate {
    public static final TernaryIntPredicate ALL_DIFFERENT = (a, b, c)->{
      if(a==b || a==c || b==c){
          return false;
      }else{
          return true;
      }
    };

    @FunctionalInterface
    public interface TernaryIntPredicate {
       boolean test(int a, int b, int c);
    }
}