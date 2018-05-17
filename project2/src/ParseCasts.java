
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ParseCasts {
	Document dom;
    public void runExample() {

        parseXmlFile();

        parseDocument();

    }

    private void parseXmlFile() {
       
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("casts124.xml");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocument() {
    	String loginUser = "root";
        String loginPasswd = "TIAN950130";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        Element docEle = dom.getDocumentElement();

        try {
        	Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        	NodeList el = docEle.getElementsByTagName("m");
        	System.out.println(el);
            if (el != null && el.getLength() > 0) {
                for (int i = 0; i < el.getLength(); i++) {
                    Element cast = (Element) el.item(i);
                    String mid = getTextValue(cast, "f");
                    String mname = getTextValue(cast, "t");
                    String aname = getTextValue(cast, "a");
                    String aid = "";
                    
                    System.out.println(mid);
                    System.out.println(mname); 
                    System.out.println(aname);

	                String query = "SELECT * FROM movies where title=?";
	                PreparedStatement statement = connection.prepareStatement(query);
	                statement.setString(1, mname);                
	                ResultSet rs11 = statement.executeQuery();
	                if(rs11.next()) {
	                	mid = rs11.getString("id");}
	                else {
	                	String querymax = "SELECT max(id) as m from movies";
	                   	Statement statm = connection.createStatement();
	                    ResultSet maxid = statm.executeQuery(querymax);
	                   	maxid.next();
	                	String oldid = maxid.getString("m");
	                	System.out.println(Integer.parseInt(oldid.substring(2, oldid.length()-1))+1);
	                   	mid = "tt" + Integer.toString((Integer.parseInt(oldid.substring(2, oldid.length()))+1));
	                   	String queryIn = "INSERT INTO movies VALUES(?,?,?,?)";
	                   	PreparedStatement statement1 = connection.prepareStatement(queryIn);
	                   	statement1.setString(1, mid);
	                   	statement1.setString(2, "unknown");
	                   	statement1.setString(3, "0000");
	                   	statement1.setString(4, "unknown");
	                   	statement1.executeUpdate();
	                }
	                
	                String querys = "SELECT * FROM stars where name=?";
	                PreparedStatement statements = connection.prepareStatement(querys);
	                statements.setString(1, aname);                
	                ResultSet rss = statements.executeQuery();
	                if(rss.next()) {
	                	aid = rss.getString("id");}
	                else {
	                	String querymax = "SELECT max(id) as m from stars";
	                   	Statement statm = connection.createStatement();
	                   	ResultSet maxid = statm.executeQuery(querymax);
	                   	maxid.next();
	                   	System.out.println(maxid.getString("m"));
	                   	String oldid = maxid.getString("m");
	                	System.out.println(Integer.parseInt(oldid.substring(2, oldid.length()-1))+1);
	                   	aid = "nm" + Integer.toString((Integer.parseInt(oldid.substring(2, oldid.length()))+1));
	                   	System.out.println(aid);
	                   	String queryIn = "INSERT INTO stars VALUES(?,?,?)";
	                    PreparedStatement statement0 = connection.prepareStatement(queryIn);
	                    if(aname == null)
	                    	aname = "unknown";
	                    statement0.setString(1, aid);                    
	                    statement0.setString(2, aname);
	                    statement0.setNull(3, java.sql.Types.INTEGER);
		                statement0.executeUpdate();
	                }
	                
	                
	                String query1 = "SELECT * FROM stars_in_movies as sim where movieId=? and starId=?";
	                PreparedStatement statement1 = connection.prepareStatement(query1);
	                statement1.setString(1, mid); 
	                statement1.setString(2, aid);
	                ResultSet rscheck = statement1.executeQuery();
	                
	                if(rscheck.next()) {
	                	System.out.println("This cast already exists in the database!");;
	                }
	                else{
	                	System.out.println("Adding to the db now...");	                	
	                	String queryIn = "INSERT INTO stars_in_movies VALUES(?,?)";
	                    PreparedStatement statementIn = connection.prepareStatement(queryIn);
	                    statementIn.setString(1, aid);                    
	                    statementIn.setString(2, mid);
	                    statementIn.executeUpdate();
	                	}       
                	}
                }
            }catch (SQLException e) {
			e.printStackTrace();}              
    }
 
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            if(el.getFirstChild() == null) {
            	return textVal;
            }
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }


    private int getIntValue(Element ele, String tagName) {
        if(getTextValue(ele, tagName) != null) {
        	return Integer.parseInt(getTextValue(ele, tagName));}      	
        else
        	return 0;
    }
 

    public static void main(String[] args) {
      
        ParseCasts dpe = new ParseCasts();

        dpe.runExample();
    }

}


