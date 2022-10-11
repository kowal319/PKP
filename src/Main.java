import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean mainLoop = true;
        String choice;

        while (true) {
            System.out.println("Type 'next' to get next super quote, type 'exit' to exit program");
            choice = input.nextLine();
            switch (choice) {
                case "next":
                    try {
                        String url = "https://api.kanye.rest/";
                        URL obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection)
                                obj.openConnection();
                        int responseCode = con.getResponseCode();

                        System.out.println("\nSending 'GET' request to url : " + url);
                        System.out.println("Response Code: " + responseCode);
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }

                        System.out.println(response.toString());

                        try{
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection a=DriverManager.getConnection(
                                    "jdbc:mysql://localhost:3306/pkp?characterEncoding=latin1&useConfigs=maxPerformance","root","root");
                            Statement stmt=a.createStatement();
                            String query = " insert into quotes (quote)"
                                    + " values (?)";
                            PreparedStatement preparedStmt = a.prepareStatement(query);
                            preparedStmt.setString (1, String.valueOf(response));
                            preparedStmt.execute();

                            System.out.println("Saved quotes:");
                            ResultSet rs=stmt.executeQuery("select * from quotes");
                            while(rs.next())
                                System.out.println(rs.getInt(1)+"  "+rs.getString(2));
                            a.close();
                        }catch(Exception ex){ System.out.println(ex);
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    break;

                case "exit":
                    System.out.println("Exciting program");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not walid option");
                    break;
            }
        }
    }
}


