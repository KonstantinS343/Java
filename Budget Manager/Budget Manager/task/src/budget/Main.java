package budget;
import java.text.DecimalFormat;
import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        double total = 0;
        DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
        int  key = 0;
        boolean again = false, exit = false;
        Operation MyList = new Operation();
        while(!again){
            System.out.print("\nChoose your action:\n1) Add income\n2) Add purchase\n3) Show list of purchases\n4) Balance\n5) Save\n6) Load\n7) Analyze (Sort)\n0) Exit\n");
            key = scanner.nextInt();
            switch (key) {
                case 1:
                    System.out.println("\nEnter income:");
                    MyList.InOperation(scanner.nextDouble());
                    System.out.println("Income was added!");
                    break;
                case 2:
                    while (exit == false){
                        System.out.println("\nChoose the type of purchases\n1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) Back");
                        key = scanner.nextInt();
                        if(key == 5){
                            break;
                        }
                        String buffer = scanner.nextLine();
                        System.out.println("\nEnter purchase name:");
                        String str = scanner.nextLine();
                        System.out.println("Enter its price:");
                        double cost = scanner.nextDouble();
                        StringBuilder NewPurchase = new StringBuilder(str);
                        String costline = " $" + cost;
                        if (costline.substring(costline.lastIndexOf(".") + 1).length() == 1){
                            costline = " $" + cost+"0";
                        }
                        costline.replace(',','.');
                        NewPurchase.append(costline);
                        String Purchase = String.valueOf(NewPurchase);
                        MyList.AddPurchase(Purchase, key);
                        System.out.println("Purchase was added!");
                    }
                    break;
                case 3:
                    if(MyList.GetPurchase().get(0).size() == 0 && MyList.GetPurchase().get(1).size() == 0 && MyList.GetPurchase().get(2).size() == 0 && MyList.GetPurchase().get(3).size() == 0){
                        System.out.println("\nThe purchase list is empty!");
                        break;
                    }
                    while(exit == false){
                        System.out.println("\nChoose the type of purchases\n1) Food\n2) Clothes\n3) Entertainment\n4) Other\n5) All\n6) Back");
                        switch (scanner.nextInt()){
                            case 1:
                                if(MyList.GetPurchase().get(0).size() == 0){
                                    System.out.println("\nFood:");
                                    System.out.println("The purchase list is empty\n");
                                    break;
                                }
                                System.out.println("\nFood:");
                                for(Object i: MyList.GetPurchase().get(0).keySet()) {
                                    System.out.println(i);
                                }
                                System.out.printf("Total sum: $" + "%.2f",MyList.GetCategories(0));
                                System.out.print("\n");
                                break;
                            case 2:
                                if(MyList.GetPurchase().size() < 2){
                                    System.out.println("\nClothes:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                if(MyList.GetPurchase().get(1).size() == 0){
                                    System.out.println("\nClothes:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                System.out.println("\nClothes:");
                                for(Object i: MyList.GetPurchase().get(1).keySet()) {
                                    System.out.println(i);
                                }
                                System.out.printf("Total sum: $" + "%.2f",MyList.GetCategories(1));
                                System.out.print("\n");
                                break;
                            case 3:
                                if(MyList.GetPurchase().size() < 3){
                                    System.out.println("\nEntertainment:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                if(MyList.GetPurchase().get(2).size() == 0){
                                    System.out.println("\nEntertainment:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                System.out.println("\nEntertainment:");
                                for(Object i: MyList.GetPurchase().get(2).keySet()) {
                                    System.out.println(i);
                                }
                                System.out.printf("Total sum: $" + "%.2f",MyList.GetCategories(2));
                                System.out.print("\n");
                                break;
                            case 4:
                                if(MyList.GetPurchase().size() < 4){
                                    System.out.println("\nOther:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                if(MyList.GetPurchase().get(3).size() == 0){
                                    System.out.println("\nOther:");
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                System.out.println("\nOther:");
                                for(Object i: MyList.GetPurchase().get(3).keySet()) {
                                    System.out.println(i);
                                }
                                System.out.printf("Total sum: $" + "%.2f",MyList.GetCategories(3));
                                System.out.print("\n");
                                break;
                            case 5:
                                if(MyList.GetPurchase().isEmpty()){
                                    System.out.println("The purchase list is empty");
                                    break;
                                }
                                System.out.println("\nAll:");
                                for(int i = 0; i<MyList.GetPurchase().size(); i++) {
                                    for(Object j: MyList.GetPurchase().get(i).keySet()) {
                                        System.out.println(j);
                                    }
                                }
                                System.out.printf("Total sum: $" + "%.2f",MyList.GetTotal());
                                System.out.print("\n");
                                break;
                            case 6:
                                exit = true;
                                break;
                        }
                    }
                    exit = false;
                    break;
                case 4:
                    if (MyList == null) {
                        System.out.println("\nBalance: $0.00");
                        break;
                    }
                    System.out.println("\nBalance: $" + MyList.GetBalance());
                    break;
                case 5:
                    SaveTheFile(MyList);
                    break;
                case 6:
                    MyList = LoadTheFile(MyList);
                    break;
                case 7:
                    while (exit == false){
                        System.out.println("\nHow do you want to sort?\n1) Sort all purchases\n2) Sort by type\n3) Sort certsin type\n4) Back");
                        switch (scanner.nextInt()) {
                            case 1:
                                if (MyList.GetPurchase().get(0).size() == 0 && MyList.GetPurchase().get(1).size() == 0 && MyList.GetPurchase().get(2).size() == 0 && MyList.GetPurchase().get(3).size() == 0) {
                                    System.out.println("\nThe purchase list is empty!");
                                    break;
                                }
                                System.out.print("\n");
                                Map<Object, Double> map = new HashMap<>();
                                ArrayList list = new ArrayList<>();
                                Map<Object, Object> newmap = new TreeMap<>();
                                for (int j = 0; j < 4; j++) {
                                    for (var i : MyList.GetPurchase().get(j).entrySet()) {
                                        map.put(i.getKey(), i.getValue());
                                    }
                                }
                                for (Map.Entry entry : map.entrySet()) {
                                    list.add(entry.getValue());
                                }
                                Set<Object> set = new HashSet<>(list);
                                list.clear();
                                list.addAll(set);
                                Collections.sort(list);
                                Collections.reverse(list);
                                for (Object str : list) {
                                    for (var entry : map.entrySet()) {
                                        if (entry.getValue().equals(str)) {
                                            System.out.println(entry.getKey());
                                        }
                                    }
                                }
                                System.out.print("\n");
                                break;
                            case 2:
                                System.out.println("\nTypes:");
                                String buffer;
                                String[] Types = {"Food - $" , "Clothes - $" , "Entertainment - $" , "Other - $"};
                                String test = decimalFormat.format(MyList.GetCategories(0));
                                test = test.replace(',','.');
                                Types[0] = "Food - $" + test;
                                test = decimalFormat.format(MyList.GetCategories(1));
                                test = test.replace(',','.');
                                Types[1] = "Clothes - $" + test;
                                test = decimalFormat.format(MyList.GetCategories(2));
                                test = test.replace(',','.');
                                Types[2] = "Entertainment - $" + test;
                                test = decimalFormat.format(MyList.GetCategories(3));
                                test = test.replace(',','.');
                                Types[3] = "Other - $" + test;
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 3; j++){
                                        if(Double.parseDouble(Types[j].substring(Types[j].lastIndexOf("$") + 1))<Double.parseDouble(Types[j+1].substring(Types[j+1].lastIndexOf("$") + 1))){
                                            buffer = Types[j];
                                            Types[j]=Types[j+1];
                                            Types[j+1]=buffer;
                                        }
                                    }
                                }
                                for(int i =0;i< 4;i++){
                                    System.out.print(Types[i]);
                                    System.out.print("\n");
                                }
                                break;
                            case 3:
                                System.out.println("\nChoose the type of purchases\n1) Food\n2) Clothes\n3) Entertainment\n4) Other");
                                switch (scanner.nextInt()){
                                    case 1:
                                        if(MyList.GetCategories(0) == 0){
                                            System.out.println("\nThe purchase list is empty!");
                                            break;
                                        }
                                        System.out.println("\nFood:");
                                        ArrayList foodlist = new ArrayList<>();
                                        for (var entry : MyList.GetPurchase().get(0).entrySet()) {
                                            foodlist.add(entry.getValue());
                                        }
                                        Set<Object> sset = new HashSet<>(foodlist);
                                        foodlist.clear();
                                        foodlist.addAll(sset);
                                        Collections.sort(foodlist);
                                        Collections.reverse(foodlist);
                                        for (Object str : foodlist) {
                                            for (var entry : MyList.GetPurchase().get(0).entrySet()) {
                                                if (entry.getValue().equals(str)) {
                                                    System.out.println(entry.getKey());
                                                }
                                            }
                                        }
                                        System.out.printf("Total sum: $" + "%.2f", MyList.GetCategories(0));
                                        System.out.print("\n");
                                        break;
                                    case 2:
                                        if(MyList.GetCategories(1) == 0){
                                            System.out.println("\nThe purchase list is empty!");
                                            break;
                                        }
                                        System.out.println("\nClothes:");
                                        ArrayList clotheslist = new ArrayList<>();
                                        for (var entry : MyList.GetPurchase().get(1).entrySet()) {
                                            clotheslist.add(entry.getValue());
                                        }
                                        Set<Object> set1 = new HashSet<>(clotheslist);
                                        clotheslist.clear();
                                        clotheslist.addAll(set1);
                                        Collections.sort(clotheslist);
                                        Collections.reverse(clotheslist);
                                        for (Object str : clotheslist) {
                                            for (var entry : MyList.GetPurchase().get(1).entrySet()) {
                                                if (entry.getValue().equals(str)) {
                                                    System.out.println(entry.getKey());
                                                }
                                            }
                                        }
                                        System.out.printf("Total sum: $" + "%.2f", MyList.GetCategories(1));
                                        System.out.print("\n");
                                        break;
                                    case 3:
                                        if(MyList.GetCategories(2) == 0){
                                            System.out.println("\nThe purchase list is empty!");
                                            break;
                                        }
                                        System.out.println("\nEntertainment:");
                                        ArrayList enterlist = new ArrayList<>();
                                        for (var entry : MyList.GetPurchase().get(2).entrySet()) {
                                            enterlist.add(entry.getValue());
                                        }
                                        Set<Object> set2 = new HashSet<>(enterlist);
                                        enterlist.clear();
                                        enterlist.addAll(set2);
                                        Collections.sort(enterlist);
                                        Collections.reverse(enterlist);
                                        for (Object str : enterlist) {
                                            for (var entry : MyList.GetPurchase().get(2).entrySet()) {
                                                if (entry.getValue().equals(str)) {
                                                    System.out.println(entry.getKey());
                                                }
                                            }
                                        }
                                        System.out.printf("Total sum: $" + "%.2f", MyList.GetCategories(2));
                                        System.out.print("\n");
                                        break;
                                    case 4:
                                        if(MyList.GetCategories(3) == 0){
                                            System.out.println("\nThe purchase list is empty!");
                                            break;
                                        }
                                        System.out.println("\nOther:");
                                        ArrayList otherlist = new ArrayList<>();
                                        for (var entry : MyList.GetPurchase().get(3).entrySet()) {
                                            otherlist.add(entry.getValue());
                                        }
                                        Set<Object> set3 = new HashSet<>(otherlist);
                                        otherlist.clear();
                                        otherlist.addAll(set3);
                                        Collections.sort(otherlist);
                                        Collections.reverse(otherlist);
                                        for (Object str : otherlist) {
                                            for (var entry : MyList.GetPurchase().get(3).entrySet()) {
                                                if (entry.getValue().equals(str)) {
                                                    System.out.println(entry.getKey());
                                                }
                                            }
                                        }
                                        System.out.printf("Total sum: $" + "%.2f", MyList.GetCategories(3));
                                        System.out.print("\n");
                                        break;
                                }
                                break;
                            case 4:
                                exit = true;
                                break;
                        }
                    }
                    exit=false;
                    break;
                case 0:
                    System.out.print("\nBye!\n");
                    again = true;
                    break;
            }
        }
    }
    public static void SaveTheFile(Operation Mylist){
        try{
            File fileinput = new File("purchases.txt");
            if(!fileinput.exists()){
                fileinput.createNewFile();
            }
            PrintWriter write = new PrintWriter(fileinput);
            for(int i = 0; i<Mylist.GetPurchase().size(); i++) {
                for (var j: Mylist.GetPurchase().get(i).entrySet()) {
                    write.print(i);
                    write.println(j.getKey());
                }
            }
            write.println("Total: $" + Mylist.GetTotal());
            write.println("Balance: $" + Mylist.GetBalance());
            write.close();
        }catch (IOException e){
            System.out.println("Error!");
        }
        System.out.println("\nPurchases were saved!");
    }
    public static Operation LoadTheFile(Operation Mylist){
        BufferedReader fileout=null;
        try{
            fileout = new BufferedReader(new FileReader("purchases.txt"));
            String line;
            while((line = fileout.readLine())!=null){
                String index = String.valueOf(line.charAt(0));
                if(index.equals("T")) {
                    double money = Double.parseDouble(line.substring(line.lastIndexOf("$") + 1));
                    Mylist.GetTotal(money);
                    break;
                }
                Mylist.AddPurchase(line.substring(1, line.length()) , Integer.parseInt(index)+1);
            }
            line = fileout.readLine();
            Mylist.InOperation(Double.parseDouble(line.substring(line.lastIndexOf("$") + 1)));
            fileout.close();
        }catch (IOException e){
            System.out.println("Error!");
        }
        System.out.println("\nPurchases were loaded!");
        return Mylist;
    }
}
class Operation {
    private static double balance = 0;
    private List<Map<Object,Double>> MyPurchase= Arrays.asList(new HashMap<>() , new HashMap<>() ,new HashMap<>() ,new HashMap<>() );
    private double total = 0;
    private double [] totalcategories = new double[4];

    public void InOperation(double addmoney) {
        this.balance = balance + addmoney;
    }

    public double GetBalance() {
        return balance;
    }

    public void GetTotal(Double total){
        this.total = total;
    }

    public List<Map<Object, Double>> GetPurchase() {
        return MyPurchase;
    }
    public void AddPurchase(String str, int i) {
        MyPurchase.get(i-1).put(str,Double.parseDouble(str.substring(str.lastIndexOf("$") + 1)));
        total += Double.parseDouble(str.substring(str.lastIndexOf("$") + 1));
        totalcategories[i-1] += Double.parseDouble(str.substring(str.lastIndexOf("$") + 1));
        this.balance = balance - Double.parseDouble(str.substring(str.lastIndexOf("$") + 1));;
        if (this.balance < 0) {
            this.balance = 0;
        }
    }

    public double GetTotal() {
        return total;
    }
    public double GetCategories(int i){
        return totalcategories[i];
    }
    public double [] GetCategor(){
        return totalcategories;
    }
}