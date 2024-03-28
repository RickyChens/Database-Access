import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            try {
                //Creates connection to mySQL
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/infocom", "root", "password");
                Statement statement = connection.createStatement();

                //Create file writer
                FileWriter writer = new FileWriter("AuditLog.txt", true);

                String[] tables = {"Login_Credential", "SE_Data", "Emp_SE", "HR_data", "Emp_HR", "Emp_PR", "PR_Data"};
                //Table reading permission arrays
                boolean[] tables_accessible = new boolean[7];
                Arrays.fill(tables_accessible, false);

                //R/W permission arrays
                boolean[] tables_permissions = new boolean[7];
                Arrays.fill(tables_permissions, false);


                //Login and role access
                Scanner input = new Scanner(System.in);
                String role = "";
                System.out.print("Enter username: ");
                String user = input.nextLine();
                System.out.print("Enter password: ");
                String pass = input.nextLine();
                String query = "SELECT role FROM login_credential WHERE username = \'" + user + "\' AND password = \'" + pass + "\'";
                ResultSet resultSet = statement.executeQuery(query);


                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                final int columnCount = resultSetMetaData.getColumnCount();
                //Tells use their role
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print("Welcome your role is ");
                        System.out.print(role = resultSet.getString(i));
                        System.out.print(" ");
                    }
                    System.out.println();
                }
                if (role.isEmpty()){
                    System.out.print("Invalid login");
                    System.exit(0);
                }


                //Checking for role and giving corresponding options
                if (role.equalsIgnoreCase("admin")) {
                    System.out.print("Read data or Change data: ");
                    String readOrChange = input.nextLine();

                    //Checks for user input and gives corresponding options
                    if (readOrChange.equalsIgnoreCase("Read")) {
                        returnPossibleTables(role, tables_accessible);
                        returnTableSelected(tables, tables_accessible, statement);


                    } else if (readOrChange.equalsIgnoreCase("Change")) {

                        System.out.println("You can Write and Update on Login_Credential, SE_Data, Emp_SE, HR_data, Emp_HR, Emp_PR, PR_Data");
                        Arrays.fill(tables_permissions, true);

                        //Allows user to change table
                        System.out.print("Enter table to change: ");
                        String tableUpdate = input.nextLine();
                        System.out.print("Select Insert, Update, or Delete: ");
                        String writeOrUpdate = input.nextLine();
                        if (writeOrUpdate.equalsIgnoreCase("Insert")) {
                            for (int i = 0; i < tables.length; i++){
                                insertQuery(writer, tableUpdate, tables_permissions, tables, statement, "Insert into");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Update")) {
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Update");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Delete")){
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Delete from");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else {
                            System.out.print("Invalid input");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Invalid input");
                        System.exit(0);
                    }
                } else if (role.equalsIgnoreCase("se")) {
                    System.out.print("Read data or Change data: ");
                    String readOrChange = input.nextLine();
                    if (readOrChange.equalsIgnoreCase("Read")) {
                        returnPossibleTables(role, tables_accessible);
                        returnTableSelected(tables, tables_accessible, statement);


                    } else if (readOrChange.equalsIgnoreCase("Change")) {
                        System.out.println("You can Write and Update on SE_Data, Emp_SE");
                        for (int i = 1; i <= 2; i++) {
                            tables_permissions[i] = true;
                        }

                        System.out.print("Enter table to change: ");
                        String tableUpdate = input.nextLine();
                        System.out.print("Select Insert, Update, or Delete: ");
                        String writeOrUpdate = input.nextLine();
                        if (writeOrUpdate.equalsIgnoreCase("Insert")) {
                            for (int i = 0; i < tables.length; i++){
                                insertQuery(writer, tableUpdate, tables_permissions, tables, statement, "Insert into");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Update")) {
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Update");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Delete")){
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Delete from");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else {
                            System.out.print("Invalid input");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Invalid input");
                        System.exit(0);
                    }



                } else if (role.equalsIgnoreCase("hr")) {
                    System.out.print("Read data or Change data: ");
                    String readOrChange = input.nextLine();
                    if (readOrChange.equalsIgnoreCase("Read")) {
                        returnPossibleTables(role, tables_accessible);
                        returnTableSelected(tables, tables_accessible, statement);


                    } else if (readOrChange.equalsIgnoreCase("Change")) {
                        System.out.println("You can Write and Update on HR_data, Emp_HR, Emp_PR");
                        for (int i = 3; i <= 5; i++) {
                            tables_permissions[i] = true;
                        }

                        System.out.print("Enter table to change: ");
                        String tableUpdate = input.nextLine();
                        System.out.print("Select Insert, Update, or Delete: ");
                        String writeOrUpdate = input.nextLine();
                        if (writeOrUpdate.equalsIgnoreCase("Insert")) {
                            for (int i = 0; i < tables.length; i++){
                                insertQuery(writer, tableUpdate, tables_permissions, tables, statement, "Insert into");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Update")) {
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Update");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Delete")){
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Delete from");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else {
                            System.out.print("Invalid input");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Invalid input");
                        System.exit(0);
                    }



                } else if (role.equalsIgnoreCase("pr")) {
                    System.out.print("Read data or Change data: ");
                    String readOrChange = input.nextLine();
                    if (readOrChange.equalsIgnoreCase("Read")) {
                        returnPossibleTables(role, tables_accessible);
                        returnTableSelected(tables, tables_accessible, statement);


                    } else if (readOrChange.equalsIgnoreCase("Change")) {
                        System.out.println("You can Write and Update on Emp_PR, PR_Data");
                        for (int i = 5; i <= 6; i++) {
                            tables_permissions[i] = true;
                        }

                        System.out.print("Enter table to change: ");
                        String tableUpdate = input.nextLine();
                        System.out.print("Select Insert, Update, or Delete: ");
                        String writeOrUpdate = input.nextLine();
                        if (writeOrUpdate.equalsIgnoreCase("Insert")) {
                            for (int i = 0; i < tables.length; i++){
                                insertQuery(writer, tableUpdate, tables_permissions, tables, statement, "Insert into");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Update")) {
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Update");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else if (writeOrUpdate.equalsIgnoreCase("Delete")){
                            for (int i = 0; i < tables.length; i++){
                                askForQuery(writer, tableUpdate, tables_permissions, tables, statement, "Delete from");
                            }
                            System.out.println("Table cannot be accessed");
                            System.exit(-1);
                        } else {
                            System.out.print("Invalid input");
                            System.exit(0);
                        }
                    } else {
                        System.out.println("Invalid input");
                        System.exit(0);
                    }



                } else if (role.equalsIgnoreCase("general")) {
                    returnPossibleTables(role, tables_accessible);
                    returnTableSelected(tables, tables_accessible, statement);
                } else {
                    System.exit(0);
                }

                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        public static void insertQuery(FileWriter writer, String tableUpdate, boolean[] tables_permissions, String[] tables, Statement statement, String identifier) throws SQLException, IOException {
            // Goes through tables and checks permissions and allows the user to input a query
            for (int i = 0; i < tables.length; i++) {
                if (tables_permissions[i] && tables[i].equalsIgnoreCase(tableUpdate)) {
                    Scanner input = new Scanner(System.in);

                    // Retrieve table attributes
                    ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM " + tableUpdate).getMetaData();
                    int columnCount = metaData.getColumnCount();
                    String[] attributes = new String[columnCount];
                    StringBuilder updateQuery = new StringBuilder(identifier + " " + tableUpdate + " (");
                    StringBuilder valuesPart = new StringBuilder("VALUES (");

                    System.out.println("Enter values for the following attributes:");

                    // Ask user for values
                    for (int j = 1; j <= columnCount; j++) {
                        attributes[j - 1] = metaData.getColumnName(j);
                        System.out.print(attributes[j - 1] + ": ");
                        String attributeValue = input.nextLine();

                        // Make sure it has single quotes for the right things
                        if (metaData.getColumnType(j) == Types.VARCHAR || metaData.getColumnType(j) == Types.CHAR || metaData.getColumnType(j) == Types.DATE) {
                            valuesPart.append("'").append(attributeValue).append("'");
                        } else {
                            valuesPart.append(attributeValue);
                        }

                        updateQuery.append(attributes[j - 1]);
                        if (j < columnCount) {
                            updateQuery.append(", ");
                            valuesPart.append(", ");
                        }
                    }

                    updateQuery.append(") ").append(valuesPart).append(")");

                    // Execute the query
                    try {
                        statement.executeUpdate(updateQuery.toString());
                        System.out.println("Successfully executed");
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        writer.write(dtf.format(now) + "\t");
                        writer.write(updateQuery.toString());
                        writer.write("\n");
                        writer.close();
                        System.exit(0);
                    } catch (SQLException e) {
                        System.out.println("Error executing the query: " + e.getMessage());
                        System.exit(-1);
                    }
                }
            }
        }



        public static void askForQuery(FileWriter writer, String tableUpdate, boolean[] tables_permissions, String[] tables, Statement statement, String identifier) throws SQLException, IOException {
            //Goes through tables and checks permissions and allows user to input a query
            for (int i = 0; i < tables.length; i++) {
                if (tables_permissions[i] && tables[i].equalsIgnoreCase(tableUpdate)) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("Query header is: " + identifier + " " + tableUpdate + " ");
                    System.out.print("Enter the rest of the query: ");
                    String updateQuery = input.nextLine();
                    String writeQuery = identifier + " " + tableUpdate + " " + updateQuery;
                    try {
                        statement.executeUpdate(writeQuery);
                        System.out.println("Successfully executed");
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        writer.write(dtf.format(now) + "\t");
                        writer.write(writeQuery);
                        writer.write("\n");
                        writer.close();
                        System.exit(0);
                    } catch (SQLException e) {
                        System.out.println("Error executing the query: " + e.getMessage());
                        System.exit(-1);
                    }
                }
            }
        }

        public static void returnTableSelected(String[] tables, boolean[] tables_accessible, Statement statement) throws SQLException {
            //Allows user to enter a table to be displayed
            Scanner input = new Scanner(System.in);
            System.out.print("Enter table: ");
            String tableSelected = input.nextLine();
            StringBuilder tableQuery = new StringBuilder("select * from ");
            boolean tableFound = false;
            for (int i = 0; i < tables_accessible.length; i++){
                if (tables_accessible[i] && (tables[i].compareToIgnoreCase(tableSelected) == 0)){
                    tableQuery.append(tables[i].toLowerCase());
                    tableFound = true;
                }
            }
            if (!tableFound) {
                System.out.println("Table cannot be accessed");
                System.exit(-1);
            }
            String temp = tableQuery.toString();
            ResultSet printTable = statement.executeQuery(temp);
            ResultSetMetaData printTableMetaData = printTable.getMetaData();
            final int col = printTableMetaData.getColumnCount();

            while (printTable.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(printTable.getString(i));
                    System.out.print(" ");
                }
                System.out.println();
            }
        }


        public static void returnPossibleTables(String role, boolean[] tables_accessible){
            //Checks user roles and returns tables that can be accessed
            if (role.equals("admin")){
                System.out.println("You can access Login_Credential, SE_Data, Emp_SE, HR_data, Emp_HR, Emp_PR, PR_Data");
                Arrays.fill(tables_accessible, true);
            }
            else if (role.equals("se")) {
                System.out.println("You can access SE_Data, Emp_SE");
                for (int i = 1; i <= 2; i++) {
                    tables_accessible[i] = true;
                }
            }
            else if(role.equals("hr")) {
                System.out.println("You can access Emp_SE, HR_data, Emp_HR, Emp_PR");
                for (int i = 2; i <= 5; i++) {
                    tables_accessible[i] = true;
                }
            }
            else if(role.equals("pr")) {
                System.out.println("You can access Emp_SE, Emp_HR, Emp_PR, PR_Data");
                for (int i = 3; i <= 6; i++) {
                    tables_accessible[i] = true;
                }
                tables_accessible[2] = true;
            }
            else if (role.equals("general")){
                System.out.println("You can access Emp_HR, Emp_PR");
                for (int i = 4; i <= 5; i++) {
                    tables_accessible[i] = true;
                }
                tables_accessible[2] = true;
            }
            else {
                System.exit(0);
            }
        }
    }