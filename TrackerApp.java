import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class InvalidExpenseException extends Exception{
    InvalidExpenseException(String msg){
        super(msg);
    }
}


class Expense{
    private double amount;
    private String category;

    Expense(double amount, String category){
        this.amount = amount;
        this.category = category;
    }

    public double getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }
    
}
class ExpenseTracker{
    private static final String FILE_NAME = "expenses.csv";

    private ArrayList<Expense> expenses;

    ExpenseTracker(){
        expenses = new ArrayList<>();
    }

    public void addExp(Expense e) throws InvalidExpenseException{
        if(e.getAmount()<=0){
            throw new InvalidExpenseException("Amount must be greater than 0");
        }
        if(e.getCategory()==null || e.getCategory().trim().isEmpty()){
            throw new InvalidExpenseException("Category cannot be empty");
        }
        expenses.add(e);
    }

    public double calcCategoryExp(String category){
        double exp = 0.0;
        for(Expense e : expenses){
            if(e.getCategory().equals(category)){
                exp += e.getAmount();
            }
        }
        return exp;
    }

    public void dispCatExp(String category){
        if(expenses.isEmpty()){
            System.out.println("No expenses added yet");
            return;
        }
        double total = calcCategoryExp(category);
        System.out.println("Expense for category "+category+" is: "+total);
    }
    public double calcTotalExp(){
        double total=0.0;
        for(Expense e : expenses){
            total+=e.getAmount();
        }
        return total;
    }
    public void displayTotal(){
        if(expenses.isEmpty()){
            System.out.println("No expenses added yet");
            return;
        }
        double total = calcTotalExp();
        System.out.println("Total expense: "+total);
    }

    public void display(){
        if(expenses.isEmpty()){
            System.out.println("No expenses added yet");
            return;
        }
        System.out.println("\nEXPENSE DETAILS");
        int i=1;
        for(Expense e: expenses){
            System.out.println(i+") Amount: "+e.getAmount()+"  Category: "+e.getCategory());
            i++;
        }
    }
    public void deleteExp(int index){
        if(expenses.isEmpty()){
            System.out.println("No expenses to delete");
        }
        else if(index<=0 || index>expenses.size()){
            System.out.println("Invalid index");
        }else{
            expenses.remove(index-1);
            System.out.println("Expense deleted");
        }
    }


    // to save entries to file
    public void saveToFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))){
            for(Expense e : expenses){
                bw.write(e.getAmount()+","+e.getCategory());
                bw.newLine();
            }
        }catch(IOException ex){
            System.out.println("Error saving file\n"+ex.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous file found. Starting fresh.");
            return;
        }
        // filereader->opens file and reads it character by character
        //bufferreader->used for reading it line by line
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length != 2) continue;

                double amt = Double.parseDouble(parts[0]); //to convert the string to double
                String cat = parts[1];

                if (amt > 0 && cat != null && !cat.trim().isEmpty()) {
                    expenses.add(new Expense(amt, cat));
                }
            }

            System.out.println("Expenses loaded from file!");

        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error loading file: " + ex.getMessage());
        }
    }
}

public class TrackerApp{
    public static void main(String[] args) {
        
            Scanner sc = new Scanner(System.in);
            int x;
            ExpenseTracker e = new ExpenseTracker();
            e.loadFromFile();
            while(true){
                System.out.println("Choose\n1.add expense\n2.display total\n3.display category total\n4.view expenses\n5.Delete expense\n6.Save and Exit");
                if(!sc.hasNextInt()){
                   System.out.println("Enter valid numbers to choose from options");
                   sc.next(); //to remove wrong input
                   continue;
                }
                x = sc.nextInt();
                switch(x){
                    case 1:
                        try{
                            System.out.println("Enter amount: ");
                            double amt;
                            if(sc.hasNextDouble()){
                                amt = sc.nextDouble();
                            }else {
                                System.out.println("Enter a valid amount!");
                                sc.next(); 
                                break;
                            }
                            sc.nextLine();
                            System.out.println("Enter category: ");
                            String category = sc.nextLine();
                            Expense exp = new Expense(amt, category);
                            e.addExp(exp);
                            e.saveToFile();
                        }catch(InvalidExpenseException ex){
                            System.out.println("Error: "+ex.getMessage());
                        }
                        break;
                    case 2:
                        e.displayTotal();
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.println("Enter category: ");
                        String cat = sc.nextLine();
                        e.dispCatExp(cat);
                        break;
                    case 4:
                        e.display();
                        break;
                    case 5:
                        System.out.println("Enter index to be deleted: ");
                        if(!sc.hasNextInt()){
                            System.out.println("Enter valid index");
                            sc.next(); 
                            continue;
                        }
                        int index = sc.nextInt();
                        e.deleteExp(index);
                        e.saveToFile();
                        break;
                    case 6:
                        e.saveToFile();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
    }
}