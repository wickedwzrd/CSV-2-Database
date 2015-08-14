package csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tan.JdbcHelper;

public class Csv2Database {
    // constants
    private static final String DB_URL = "jdbc:mysql://localhost/ejd";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    // member vars
    private final ArrayList<Person> parsedPersons;
    private final ArrayList<Person> addedPersons;
    private final JdbcHelper jdbc;
    private Person person;
    
    // ctor
    public Csv2Database() {
        parsedPersons = new ArrayList<>();
        addedPersons = new ArrayList<>();
        jdbc = new JdbcHelper();
    }
    
    public ArrayList<Person> readCsv(InputStream is) {
        parsedPersons.clear();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        try {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmp = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                
                for (int i = 0; i < tmp.length; ++ i) 
                    tmp[i] = tmp[i].replaceAll("^\"|\"$", "");
               
                person.setFirstName(tmp[0]);
                person.setLastName(tmp[1]);
                person.setCompanyName(tmp[2]);
                person.setAddress(tmp[3]);
                person.setCity(tmp[4]);
                person.setProvince(tmp[5]);
                person.setPostal(tmp[6]);
                person.setPhone1(tmp[7]);
                person.setPhone2(tmp[8]);
                person.setEmail(tmp[9]);
                person.setWeb(tmp[10]);
                    
                parsedPersons.add(person);
            }
            
            // done with file stream, clean up
            if(reader != null) reader.close();
            if(is != null) is.close();
        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println(parsedPersons.size());
        return parsedPersons;
    }
    
    public ArrayList<Person> addPersons(ArrayList<Person> persons) {
        // reset
        addedPersons.clear();
        
        // get all exsisting data from DB
        ArrayList<Person> exsistingPersons = getPersonsFromDatabase();
        
        // test if it is new or not
        for (int i = 0; i < persons.size(); ++i) {
            person = persons.get(i);
            // skip if already exist
            if (isPersonExsist(exsistingPersons, person))
                continue;
            
            jdbc.connect(DB_URL, DB_USER, DB_PASS);
            
            String sql = "insert into Person(firstName, lastName, companyName, address, city, province, postal, phone1, phone2, email, web)"
                    + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ArrayList<Object> params = new ArrayList<>();
            
            // add params
            params.add(person.getFirstName());
            params.add(person.getLastName());
            params.add(person.getCompanyName());
            params.add(person.getAddress());
            params.add(person.getCity());
            params.add(person.getProvince());
            params.add(person.getPostal());
            params.add(person.getPhone1());
            params.add(person.getPhone2());
            params.add(person.getEmail());
            params.add(person.getWeb());
            
            int result = jdbc.update(sql, params);
            if (result == 0) 
                addedPersons.add(person); // add only if success
        }
        
        jdbc.disconnect();
        return addedPersons;
    }
    
    private ArrayList<Person> getPersonsFromDatabase() {
        ArrayList<Person> persons = new ArrayList<>();
        
        // connect to DB
        jdbc.connect(DB_URL, DB_USER, DB_PASS);
        
        // sql string
        String sql = "select * from Person";
        
        try {
            ResultSet result = jdbc.query(sql);
            
            while (result.next()) {
                person.setFirstName(result.getString(2));
                person.setLastName(result.getString(3));
                person.setCompanyName(result.getString(4));
                person.setAddress(result.getString(5));
                person.setCity(result.getString(6));
                person.setProvince(result.getString(7));
                person.setPostal(result.getString(8));
                person.setPhone1(result.getString(9));
                person.setPhone2(result.getString(10));
                person.setEmail(result.getString(11));
                person.setWeb(result.getString(12));
                
                persons.add(person);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        }
        
        jdbc.disconnect();
        return persons;
    }

    private boolean isPersonExsist(ArrayList<Person> persons, Person person) {
        for (int i = 0; i < persons.size(); ++i) {
            Person p2 = persons.get(i);
            if (person.equals(p2))
                return true;
        }
        
        return false;
    }
    
    
}