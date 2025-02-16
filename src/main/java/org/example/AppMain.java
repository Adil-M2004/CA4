package org.example;

//Your java program should allow you to do the following:
//      1.	List all expenses incurred and calculate the total spend
//      2.	Add a new expense
//      3.	Delete an expense (by id)
//      4.	List all income earned and calculate total income
//      5.	Add a new income
//      6.	Delete an income (by id)
//     7.	List all income and expenses for a particular month and display the total income,
//     expenditure, and how much money they should have left over.


import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        AppMain appMain = new AppMain();
        //OUTPUTTING BOTH DATABASES

        Scanner sc = new Scanner(System.in);
        System.out.println("Select Option");
        System.out.println("1. Get Expenses Amount");
        System.out.println("2. Get Income Amount");
        System.out.println("3. Add an expense");
        System.out.println("4. Add an income");
        System.out.println("5. List all income and expenses for a particular month and display the total income,\n" +
                "//     expenditure, and how much money they should have left over.");
        System.out.println("6. Delete Income");
        System.out.println("7. Delete Expense");
        int option = sc.nextInt();

        if (option == 1) {
            appMain.expense();
        } else if (option == 2) {
            appMain.income();
        } else if (option == 3) {
            appMain.AddExpense();
        } else if (option == 4) {
            appMain.AddIncome();
        } else if  (option == 5) {
            appMain.listMonth();
        } else if (option == 6) {
            System.out.println("Enter income ID");
            int incomeID = sc.nextInt();
            appMain.deleteIncome(incomeID);
        } else if (option == 7) {
            System.out.println("Enter expense ID");
            int expenseID = sc.nextInt();
            appMain.deleteExpense(expenseID);
        }


    }

    public void income() {
        System.out.println("Income Table");

        String url = "jdbc:mysql://localhost/";
        String dbName = "income";
        String userName = "root";
        String password = "";


        // try with resources
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            System.out.println("\nConnected to the database.");


            Statement statement = conn.createStatement();

            // ResultSet stores the result from the SQL query
            String sqlQuery = "select * from income";
            ResultSet resultSet = statement.executeQuery(sqlQuery);


            double incomeAmount = 0;
            // Iterate over the resultSet to process every row
            while (resultSet.next()) {
                // Columns can be identified by column name OR by number
                // The first column is number 1   e.g. resultSet.getString(1);

                int income_ID = resultSet.getInt(1);


                String title = resultSet.getString(2);

                double amount = resultSet.getDouble(3);  // get third value using index, i.e lastName
                Date dateEarned = resultSet.getDate(4);

                System.out.print("Income ID = " + income_ID + ", ");
                System.out.print("Title = " + title + ", ");
                System.out.print("Amount = " + amount + ", ");
                System.out.println("Date Earned : " + dateEarned);

                //TOTAL INCOME OCCURED
                incomeAmount += amount;
            }
            System.out.println("\nTotal Income Amount : " + incomeAmount);
            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    public void expense() {
        System.out.println("Expense Table");
        String url = "jdbc:mysql://localhost/";
        String dbName = "expense";
        String userName = "root";
        String password = "";

        // try with resources
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            System.out.println("\nConnected to the database.");

            // Statements allow us to issue SQL queries to the database
            Statement statement = conn.createStatement();

            // ResultSet stores the result from the SQL query
            String sqlQuery = "select * from expense";
            ResultSet resultSet = statement.executeQuery(sqlQuery);


            double count = 0;
            // Iterate over the resultSet to process every row
            while (resultSet.next()) {
                // Columns can be identified by column name OR by number
                // The first column is number 1   e.g. resultSet.getString(1);

                int expense_ID = resultSet.getInt(1);

                String title = resultSet.getString(2);

                String category = resultSet.getString(3);

                double amount = resultSet.getDouble(4);  // get third value using index, i.e lastName
                Date dateIncurred = resultSet.getDate(5);

                System.out.print("Customer ID = " + expense_ID + ", ");
                System.out.print("First Name = " + title + ", ");
                System.out.print("First Name = " + category + ", ");
                System.out.print("Last Name = " + amount + ", ");
                System.out.println("Date of Birth : " + dateIncurred);

                count += amount;

            }
            System.out.println("\nTotal Expense Amount : " + count);
            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    //TODO ADD EXPENSE!!!!!!!!!!!!!!!!!!!

    public void AddExpense() {
        System.out.println("Adding Expense");

        String url = "jdbc:mysql://localhost/";
        String dbName = "expense";
        String fullURL = url + dbName;  // join together
        String userName = "root";
        String password = "";

        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter title");
        String title1 = sc.nextLine();
        System.out.println("Please enter category");
        String category1 = sc.nextLine();
        System.out.println("Please enter amount");
        double amount1 = sc.nextDouble();

        String date = "2025-07-15";

        // Prepare the Query String using "?" to indicate field parameters.
        ///
        String query1 = "INSERT INTO expense VALUES (null, ?, ?, ?, ?)";


        // Try-with-Resources style
        try (Connection connection = DriverManager.getConnection(fullURL, userName, password);
             PreparedStatement preparedStatement1 = connection.prepareStatement( query1 );
        )
        {
            System.out.println("Connected to the database");
            System.out.println("Building a PreparedStatement to insert a new row in database.");


            preparedStatement1.setString(1, title1);
            preparedStatement1.setString(2, category1);
            preparedStatement1.setDouble(3, amount1);
            preparedStatement1.setDate(4, Date.valueOf(date));


            preparedStatement1.executeUpdate();  // will INSERT a new row



            // Statements allow us to issue SQL queries to the database
            Statement statement = connection.createStatement();

            // Execute the Prepared Statement and get a result set
            ResultSet resultSet = statement.executeQuery("select * from expense");


            while (resultSet.next()) {

                int expense_ID = resultSet.getInt(1);

                String title = resultSet.getString(2);

                String category = resultSet.getString(3);

                double amount = resultSet.getDouble(4);  // get third value using index, i.e lastName
                Date dateIncurred = resultSet.getDate(5);

                LocalDate date1 = dateIncurred.toLocalDate();

                System.out.print("Expense ID = " + expense_ID + ", ");
                System.out.print("Title = " + title + ", ");
                System.out.print("category = " + category + ", ");
                System.out.print("amount = " + amount + ", ");
                System.out.println(date1);
            }
            System.out.println("Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    //TODO ADD INCOME!!!!!!!!!!!!!!!!!!!

    public void AddIncome() {
        System.out.println("Adding Income");

        String url = "jdbc:mysql://localhost/";
        String dbName = "income";
        String fullURL = url + dbName;  // join together
        String userName = "root";
        String password = "";

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter title");
        String title1 = sc.nextLine();
        System.out.println("Please enter amount");
        double amount1 = sc.nextDouble();

        String date = "2025-07-15";

        // Prepare the Query String using "?" to indicate field parameters.
        ///
        String query1 = "INSERT INTO income VALUES (null, ?, ?, ?)";


        // Try-with-Resources style
        try (Connection connection = DriverManager.getConnection(fullURL, userName, password);
             PreparedStatement preparedStatement1 = connection.prepareStatement( query1 );
        )
        {
            System.out.println("Connected to the database");
            System.out.println("Building a PreparedStatement to insert a new row in database.");


            // bind 'firstName" with the first "?" parameter
            preparedStatement1.setString(1, title1);
            preparedStatement1.setDouble(2, amount1);
            preparedStatement1.setDate(3, Date.valueOf(date));


            preparedStatement1.executeUpdate();  // will INSERT a new row



            // Statements allow us to issue SQL queries to the database
            Statement statement = connection.createStatement();

            // Execute the Prepared Statement and get a result set
            ResultSet resultSet = statement.executeQuery("select * from income");


            while (resultSet.next()) {
                // It is possible to get the columns via name (as shown)
                // or via the column number.  First column is indexed as 1 (and not 0 as in arrays)
                // e.g. resultSet.getString(2); gets the second column.
                int income_ID = resultSet.getInt(1);

                String title = resultSet.getString(2);

                double amount = resultSet.getDouble(3);  // get third value using index, i.e lastName
                Date dateEarned = resultSet.getDate(4);
                LocalDate date1 = dateEarned.toLocalDate();

                System.out.print("Income ID = " + income_ID + ", ");
                System.out.print("Title = " + title + ", ");
                System.out.print("Amount = " + amount + ", ");
                System.out.println(date1);
            }
            System.out.println("Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    double incomeAmount = 0;
    public void listMonth() {
        System.out.println("List all income and expenses for a particular month and display the total income,\\n\" +\n" +
                "                \"//     expenditure, and how much money they should have left over");

        String url = "jdbc:mysql://localhost/";
        String dbName = "income";
        String userName = "root";
        String password = "";

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Input Valid Month");
//        int month = sc.nextInt();

        // try with resources
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            System.out.println("\nConnected to the database.");

            // Statements allow us to issue SQL queries to the database
            Statement statement = conn.createStatement();

            // ResultSet stores the result from the SQL query
            String sqlQuery = "select * from income WHERE MONTH(dateEarned) = 01 ";
            ResultSet resultSet = statement.executeQuery(sqlQuery);



            // Iterate over the resultSet to process every row
            while (resultSet.next()) {
                // Columns can be identified by column name OR by number
                // The first column is number 1   e.g. resultSet.getString(1);

                int income_ID = resultSet.getInt(1);


                String title = resultSet.getString(2);

                double amount = resultSet.getDouble(3);  // get third value using index, i.e lastName
                Date dateEarned = resultSet.getDate(4);

                System.out.print("Income ID = " + income_ID + ", ");
                System.out.print("Title = " + title + ", ");
                System.out.print("Amount = " + amount + ", ");
                System.out.println("Date Earned : " + dateEarned);

                //TOTAL INCOME OCCURED
                incomeAmount += amount;
            }
            System.out.println("\nTotal Income Amount : " + incomeAmount);
            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }

        //TODO INCOME MONTH////////////////////////////////////////////////////////////////////
        //TODO//////////////////////////////////////////////////////////////////////////////////
        //TODO///////////////////////////////////////////////////////////////////////////////////

        System.out.println("Expense Table");
        String url1 = "jdbc:mysql://localhost/";
        String dbName1 = "expense";
        String userName1 = "root";
        String password1 = "";

        // try with resources
        try (Connection conn = DriverManager.getConnection(url1 + dbName1, userName1, password1)) {
            System.out.println("\nConnected to the database.");

            // Statements allow us to issue SQL queries to the database
            Statement statement = conn.createStatement();

            // ResultSet stores the result from the SQL query
            String sqlQuery = "select * from expense WHERE MONTH(dateIncurred) = 01 ";
            ResultSet resultSet = statement.executeQuery(sqlQuery);


            double count = 0;
            // Iterate over the resultSet to process every row
            while (resultSet.next()) {
                // Columns can be identified by column name OR by number
                // The first column is number 1   e.g. resultSet.getString(1);

                int expense_ID = resultSet.getInt(1);

                String title = resultSet.getString(2);

                String category = resultSet.getString(3);

                double amount = resultSet.getDouble(4);  // get third value using index, i.e lastName
                Date dateIncurred = resultSet.getDate(5);

                System.out.print("Customer ID = " + expense_ID + ", ");
                System.out.print("First Name = " + title + ", ");
                System.out.print("First Name = " + category + ", ");
                System.out.print("Last Name = " + amount + ", ");
                System.out.println("Date of Birth : " + dateIncurred);

                count += amount;

            }
            System.out.println("\nTotal Expense Amount : " + count);
            double leftover = incomeAmount - count;
            System.out.println("\nHow much you should have left over : " + leftover);
            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }

    }

    public void deleteIncome(int incomeID) {
        String url = "jdbc:mysql://localhost/";
        String dbName = "income";
        String userName = "root";
        String password = "";

        // SQL DELETE statement
        String sqlDelete = "DELETE FROM income WHERE income_ID = " + incomeID;

        // try with resources
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password);
             Statement statement = conn.createStatement()) {

            System.out.println("\nConnected to the database.");

            // Execute the delete statement
            int rowsDeleted = statement.executeUpdate(sqlDelete);

            if (rowsDeleted > 0) {
                System.out.println("Task with Income ID = " + incomeID + " was deleted successfully.");
            } else {
                System.out.println("No task found with Income ID = " + incomeID);
            }

            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    public void deleteExpense(int expenseID) {
        String url = "jdbc:mysql://localhost/";
        String dbName = "income";
        String userName = "root";
        String password = "";

        // SQL DELETE statement
        String sqlDelete = "DELETE FROM expense WHERE expense_ID = " + expenseID;

        // try with resources
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password);
             Statement statement = conn.createStatement()) {

            System.out.println("\nConnected to the database.");

            // Execute the delete statement
            int rowsDeleted = statement.executeUpdate(sqlDelete);

            if (rowsDeleted > 0) {
                System.out.println("Task with Income ID = " + expenseID + " was deleted successfully.");
            } else {
                System.out.println("No task found with Income ID = " + expenseID);
            }

            System.out.println("\nFinished - Disconnected from database");
        } catch (SQLException ex) {
            System.out.println("SQL Failed - check MySQL Server is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }



}//LB//////////////////////////////////////////


