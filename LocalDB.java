//import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class LocalDB {
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";		
	Statement st;
	String url = "jdbc:derby:C:\\Users\\Admin\\MyDB;create=true";
	String user = "user";
	String pass = "pass";
	Connection conn;
	PreparedStatement psUpdate = null;
	
	public LocalDB()
	{
		loadDriver();
		
	}
public static void main(String[] args){
    }

//загрузка драйвера базы
private void loadDriver() {
try {
            Class.forName(driver).newInstance();
           // System.out.println("Loaded the appropriate driver");
            //System.out.println("sdfvsdfv");
            conn= DriverManager.getConnection(url, user, pass);
           
        } catch (ClassNotFoundException cnfe) {
            System.err.println("\nUnable to load the JDBC driver " + driver);
            System.err.println("Please check your CLASSPATH.");
            JOptionPane.showMessageDialog(null, "Unable to load the JDBC driver " + driver+"Please check your CLASSPATH.", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            cnfe.printStackTrace(System.err);
        } catch (InstantiationException ie) {
            System.err.println(
                        "\nUnable to instantiate the JDBC driver " + driver);
            JOptionPane.showMessageDialog(null, "Unable to load the JDBC driver " + driver, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            
            ie.printStackTrace(System.err);
        } catch (IllegalAccessException iae) {
            System.err.println(
                        "\nNot allowed to access the JDBC driver " + driver);
            JOptionPane.showMessageDialog(null, "Not allowed to access the JDBC driver " + driver, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            
            iae.printStackTrace(System.err);
        }
catch(SQLException xe){
    System.out.println("xe"+xe);
    JOptionPane.showMessageDialog(null, xe, "Ошибка!", JOptionPane.ERROR_MESSAGE);
    xe.printStackTrace();
   System.exit(-1);
}

}

public void saveESFList(ArrayList<ESFList> list)
{
 try{
    int i;
    //System.out.print(list.size());
    //System.out.println();
    for(i=0;i<list.size();i++){
    	psUpdate = conn.prepareStatement("insert into test.esf_list values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		//System.out.println(list.get(i).InvoiceDate);
				psUpdate.setInt(1,  list.get(i).InvoiceID );
				psUpdate.setString(2,  list.get(i).InvoiceNumber);
				psUpdate.setString(3,  list.get(i).InvoiceDate);
				psUpdate.setString(4,  list.get(i).ProviderIINBIN); 
				psUpdate.setString(5,  list.get(i).BeneficiaryIINBIN);
				psUpdate.setString(6,  list.get(i).TurnoverSum);
				psUpdate.setString(7,  list.get(i).NDSSum); 
				psUpdate.setString(8,  list.get(i).TotalSum); 
				psUpdate.setInt(9,  list.get(i).InvoiceState);
				psUpdate.setString(10, list.get(i).EmpIIN);
				psUpdate.setString(11, list.get(i).EmpName);
				psUpdate.setString(12, list.get(i).CanActions); 
				psUpdate.setInt(13, list.get(i).Signed);
				psUpdate.setInt(14, list.get(i).Changed);
				 psUpdate.executeUpdate();
    }       
}catch(SQLException xe){
   // System.out.println("xe"+xe);
    System.err.println(xe);
}
}
public ArrayList<ESFList> loadESFList(){
	ArrayList<ESFList> list = new ArrayList<ESFList>();
	ESFList item;
	
	int invoiceid,invoicestate,changed;
	Integer i;
	String invoicenumber,invoicedate,provideriinbin,beneficiaryiinbin,turnoversum,ndssum,totalsum,empiin,empname;
	try{
	st = conn.createStatement();
	//for(i=1;i<7;i++){//System.out.print(i);
	ResultSet rs = st.executeQuery("SELECT * FROM test.esf_list");// where invoicestate="+i.toString());
	while (rs.next()) {
		invoiceid=rs.getInt(1);
		invoicenumber=rs.getString(2);
		invoicedate=rs.getString(3);
		provideriinbin=rs.getString(4);
		beneficiaryiinbin=rs.getString(5);
		turnoversum=rs.getString(6);
		ndssum=rs.getString(7);
		totalsum=rs.getString(8);
		invoicestate=rs.getInt(9);
		empiin=rs.getString(10);
		empname=rs.getString(11);
		changed=rs.getInt(14);
		item=new ESFList(invoiceid,invoicenumber,invoicedate,provideriinbin,beneficiaryiinbin,turnoversum,ndssum,totalsum,invoicestate,empiin,empname,changed);
		item.CanActions=rs.getString(12);
		item.Signed=rs.getInt(13);
		list.add(item);
		//System.out.print(list.size());	
		//item.print();
		
	}rs.close();
	//}
	}
	catch(SQLException xe){
	    System.out.println("xe"+xe);
	}
	return list;
}

public ArrayList<ESFList> loadESFList(Integer state){
	ArrayList<ESFList> list = new ArrayList<ESFList>();
	ESFList item;
	int invoiceid,invoicestate,changed;
	String invoicenumber,invoicedate,provideriinbin,beneficiaryiinbin,turnoversum,ndssum,totalsum,empiin,empname;
	try{
	st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM test.esf_list where invoicestate="+state.toString());
	while (rs.next()) {
		invoiceid=rs.getInt(1);
		invoicenumber=rs.getString(2);
		invoicedate=rs.getString(3);
		provideriinbin=rs.getString(4);
		beneficiaryiinbin=rs.getString(5);
		turnoversum=rs.getString(6);
		ndssum=rs.getString(7);
		totalsum=rs.getString(8);
		invoicestate=rs.getInt(9);
		empiin=rs.getString(10);
		empname=rs.getString(11);
		changed=rs.getInt(14);
		item=new ESFList(invoiceid,invoicenumber,invoicedate,provideriinbin,beneficiaryiinbin,turnoversum,ndssum,totalsum,invoicestate,empiin,empname,changed);
		item.CanActions=rs.getString(12);
		item.Signed=rs.getInt(13);
		list.add(item);

		//System.out.print(list.size());	
		//item.print();
	}
	rs.close();
	}catch(SQLException xe){
	    System.out.println("xe"+xe);
	}
	return list;
}

public void clearTable(String table)
 {
	 try{
		 st = conn.createStatement();
		 st.executeUpdate("TRUNCATE TABLE test."+table);
		// System.out.println(table+" cleared");
	 }catch(SQLException xe){
		    System.out.println("xe"+xe);
		}
 }

public void saveInvoice(Invoice Invoice){
	 try{
		// System.out.println("1");
		 psUpdate = conn.prepareStatement("insert into test.invoice (INVOICEID, INVOICENUMBER,INVOICEDATE,TURNOVERDATE,IINBIN1  ,CORRECTEDINVOICE  ,"
       +"CORRECTEDINVOICENUMBER  ,CORRECTEDDATE  ,CORRECTEDNUMBER  ,ADDITIONALINVOICENUMBER  ,PROVIDER  ,"
       +"PROVIDERSTATUS  ,IINBIN2  ,ADDRESS1  ,SERIESCERTIFICATE  ,CERTIFICATENUMBER  ,DOCUMENTNUMBER  ,"
       +"DOCUMENTDATE  ,INFORMATION1  ,KBE  ,BIK1  ,IIK1  ,NAMEBANK  ,CONTRACTNUMBER  ,CONTRACTDATE  ,"
       +"PAYMENTTERMS  ,SUPPLIEDDESTINATION  ,PROXYNUMBER  ,PROXYDATE  ,ADMINISTRATIONMETHOD  ,"
	   +"DELIVERYDOCDATE,DELIVERYDOCNUMBER,CONSIGNOR  ,CONSIGNORIINBIN  ,ADDRESSCONSIGNOR  ,CONSIGNEE  ,"
       +"CONSIGNEEIINBIN  ,CONSIGNEEADDRESS  ,PECIPIENT  ,RECIPIENTSTATUS  ,IINBIN3  ,ADDRESS2  ,"
       +"INFORMATION2  ,CODEGU  ,CODE  ,TERRITORIALDIVISION  ,BIK2  ,IIK2  ,FINANCINGSOURCE  ,BUDGETTYPE  ,"
       +"KBKEXPENSES  ,TRUCODE  ,PAYMENTSTATE  ,PAYMENTPURPOSE  ,KNP  ,RECEIPTSKBK ,ADDITIONALDATA ,"
       +"SENDDATE ,TOTALSUM,PROVIDERSIGNATURE)   values(?,?,?,?,?,?,?,?,?,?,"
		 		+ "															?,?,?,?,?,?,?,?,?,?,"
		 		+ "															?,?,?,?,?,?,?,?,?,?,"
		 		+ "															?,?,?,?,?,?,?,?,?,?,"
		 		+ "															?,?,?,?,?,?,?,?,?,?,"
		 		+ "															?,?,?,?,?,?,?,?,?,?)");
			//System.out.println(list.get(i).InvoiceDate);
					psUpdate.setInt(1,  Invoice.InvoiceID );
					psUpdate.setString(2,  Invoice.InvoiceNumber);
					psUpdate.setString(3,  Invoice.InvoiceDate);
					psUpdate.setString(4,  Invoice.GeneralInformation.TurnoverDate);
					psUpdate.setString(5,  Invoice.GeneralInformation.IINBIN);
					psUpdate.setString(6,  Invoice.GeneralInformation.CorrectedInvoice);
					psUpdate.setString(7,  Invoice.GeneralInformation.CorrectedInvoiceNumber);
					psUpdate.setString(8,  Invoice.GeneralInformation.CorrectedDate);
					psUpdate.setString(9,  Invoice.GeneralInformation.CorrectedNumber);
					psUpdate.setString(10,  Invoice.GeneralInformation.AdditionalInvoiceNumber);
					psUpdate.setString(11,  Invoice.ProviderDetails.Provider);
					psUpdate.setString(12,  Invoice.ProviderDetails.ProviderStatus);
					psUpdate.setString(13,  Invoice.ProviderDetails.IINBIN);
					psUpdate.setString(14,  Invoice.ProviderDetails.Address);
					psUpdate.setString(15,  Invoice.ProviderDetails.SeriesCertificate);
					psUpdate.setString(16,  Invoice.ProviderDetails.CertificateNumber);
					psUpdate.setString(17,  Invoice.ProviderDetails.DocumentNumber);
					psUpdate.setString(18,  Invoice.ProviderDetails.DocumentDate);
					psUpdate.setString(19,  Invoice.ProviderDetails.Information);
					psUpdate.setString(20,  Invoice.PaymentDetails.KBe);
					psUpdate.setString(21,  Invoice.PaymentDetails.BIK);
					psUpdate.setString(22,  Invoice.PaymentDetails.IIK);
					psUpdate.setString(23,  Invoice.PaymentDetails.NameBank);
					psUpdate.setString(24,  Invoice.DeliveryTerms.ContractNumber);
					psUpdate.setString(25,  Invoice.DeliveryTerms.ContractDate);
					psUpdate.setString(26,  Invoice.DeliveryTerms.PaymentTerms);
					psUpdate.setString(27,  Invoice.DeliveryTerms.SuppliedDestination);
					psUpdate.setString(28,  Invoice.DeliveryTerms.ProxyNumber);
					psUpdate.setString(29,  Invoice.DeliveryTerms.ProxyDate);
					psUpdate.setString(30,  Invoice.DeliveryTerms.AdministrationMethod);
					psUpdate.setString(31,  Invoice.DeliveryTerms.DeliveryDocDate);
					psUpdate.setString(32,  Invoice.DeliveryTerms.DeliveryDocNumber);
					psUpdate.setString(33,  Invoice.ConsignorConsigneeDetails.Consignor);
					psUpdate.setString(34,  Invoice.ConsignorConsigneeDetails.ConsignorIINBIN);
					psUpdate.setString(35,  Invoice.ConsignorConsigneeDetails.AddressConsignor);
					psUpdate.setString(36,  Invoice.ConsignorConsigneeDetails.Consignee);
					psUpdate.setString(37,  Invoice.ConsignorConsigneeDetails.ConsigneeIINBIN);
					psUpdate.setString(38,  Invoice.ConsignorConsigneeDetails.ConsigneeAddress);
					psUpdate.setString(39,  Invoice.BeneficiaryDetails.Pecipient);
					psUpdate.setString(40,  Invoice.BeneficiaryDetails.RecipientStatus);
					psUpdate.setString(41,  Invoice.BeneficiaryDetails.IINBIN);
					psUpdate.setString(42,  Invoice.BeneficiaryDetails.Address);
					psUpdate.setString(43,  Invoice.BeneficiaryDetails.Information);
					psUpdate.setString(44,  Invoice.DetailsAgency.CodeGU);
					psUpdate.setString(45,  Invoice.DetailsAgency.Code);
					psUpdate.setString(46,  Invoice.DetailsAgency.TerritorialDivision);
					psUpdate.setString(47,  Invoice.DetailsAgency.BIK);
					psUpdate.setString(48,  Invoice.DetailsAgency.IIK);
					psUpdate.setString(49,  Invoice.DetailsAgency.FinancingSource);
					psUpdate.setString(50,  Invoice.DetailsAgency.BudgetType);
					psUpdate.setString(51,  Invoice.DetailsAgency.KBKExpenses);
					psUpdate.setString(52,  Invoice.DetailsAgency.TRUCode);
					psUpdate.setString(53,  Invoice.DetailsAgency.PaymentState);
					psUpdate.setString(54,  Invoice.DetailsAgency.PaymentPurpose);
					psUpdate.setString(55,  Invoice.DetailsAgency.KNP);
					psUpdate.setString(56,  Invoice.DetailsAgency.ReceiptsKBK);
					psUpdate.setString(57,  Invoice.AdditionalData);
					psUpdate.setString(58,  Invoice.SendDate);
					psUpdate.setString(59,  Invoice.TotalSum);
					psUpdate.setString(60,  Invoice.ProviderSignature);
					
					psUpdate.executeUpdate();
		    int i;
		    //System.out.print(list.size());
		    //System.out.println();
		   // System.out.println("1");
			saveTRU(Invoice.tru);
			//System.out.println("1");
			savePD(Invoice.pd);
			//System.out.println("1");
		            
		}catch(SQLException xe){
		   // System.out.println("xe"+xe);
		    System.err.println(xe);
		}
	
	
}

public Invoice loadInvoice(int id){
	Invoice Invoice=new Invoice();
	try{
	st = conn.createStatement();
	Integer ID=id;
	String str="SELECT * FROM test.invoice where InvoiceID="+ID.toString();
	ResultSet rs = st.executeQuery(str);
	while (rs.next()) {
		Invoice.InvoiceID=rs.getInt(1);
	    Invoice.InvoiceNumber=rs.getString(2);
	    Invoice.InvoiceDate=rs.getString(3);
		Invoice.GeneralInformation.TurnoverDate=rs.getString(4);
	    Invoice.GeneralInformation.IINBIN=rs.getString(5);
	    Invoice.GeneralInformation.CorrectedInvoice=rs.getString(6);
	    Invoice.GeneralInformation.CorrectedInvoiceNumber=rs.getString(7);
	    Invoice.GeneralInformation.CorrectedDate=rs.getString(8);
	    Invoice.GeneralInformation.CorrectedNumber=rs.getString(9);
		Invoice.GeneralInformation.AdditionalInvoiceNumber=rs.getString(10);
	    Invoice.ProviderDetails.Provider=rs.getString(11);
	    Invoice.ProviderDetails.ProviderStatus=rs.getString(12);
	    Invoice.ProviderDetails.IINBIN=rs.getString(13);
	    Invoice.ProviderDetails.Address=rs.getString(14);
	    Invoice.ProviderDetails.SeriesCertificate=rs.getString(15);
	    Invoice.ProviderDetails.CertificateNumber=rs.getString(16);
	    Invoice.ProviderDetails.DocumentNumber=rs.getString(17);
	    Invoice.ProviderDetails.DocumentDate=rs.getString(18);
	    Invoice.ProviderDetails.Information=rs.getString(19);
	    Invoice.PaymentDetails.KBe=rs.getString(20);
	    Invoice.PaymentDetails.BIK=rs.getString(21);
	    Invoice.PaymentDetails.IIK=rs.getString(22);
	    Invoice.PaymentDetails.NameBank=rs.getString(23);
	    Invoice.DeliveryTerms.ContractNumber=rs.getString(24);
	    Invoice.DeliveryTerms.ContractDate=rs.getString(25);
	    Invoice.DeliveryTerms.PaymentTerms=rs.getString(26);
	    Invoice.DeliveryTerms.SuppliedDestination=rs.getString(27);
	    Invoice.DeliveryTerms.ProxyNumber=rs.getString(28);
	    Invoice.DeliveryTerms.ProxyDate=rs.getString(29);
	    Invoice.DeliveryTerms.AdministrationMethod=rs.getString(30);
	    Invoice.DeliveryTerms.DeliveryDocDate=rs.getString(31);
	    Invoice.DeliveryTerms.DeliveryDocNumber=rs.getString(32);
	    Invoice.ConsignorConsigneeDetails.Consignor=rs.getString(33);
	    Invoice.ConsignorConsigneeDetails.ConsignorIINBIN=rs.getString(34);
	    Invoice.ConsignorConsigneeDetails.AddressConsignor=rs.getString(35);
	    Invoice.ConsignorConsigneeDetails.Consignee=rs.getString(36);
	    Invoice.ConsignorConsigneeDetails.ConsigneeIINBIN=rs.getString(37);
	    Invoice.ConsignorConsigneeDetails.ConsigneeAddress=rs.getString(38);
	    Invoice.BeneficiaryDetails.Pecipient=rs.getString(39);
	    Invoice.BeneficiaryDetails.RecipientStatus=rs.getString(40);
	    Invoice.BeneficiaryDetails.IINBIN=rs.getString(41);
	    Invoice.BeneficiaryDetails.Address=rs.getString(42);
	    Invoice.BeneficiaryDetails.Information=rs.getString(43);
	    Invoice.DetailsAgency.CodeGU=rs.getString(44);
	    Invoice.DetailsAgency.Code=rs.getString(45);
	    Invoice.DetailsAgency.TerritorialDivision=rs.getString(46);
	    Invoice.DetailsAgency.BIK=rs.getString(47);
	    Invoice.DetailsAgency.IIK=rs.getString(48);
	    Invoice.DetailsAgency.FinancingSource=rs.getString(49);
	    Invoice.DetailsAgency.BudgetType=rs.getString(50);
	    Invoice.DetailsAgency.KBKExpenses=rs.getString(51);
	    Invoice.DetailsAgency.TRUCode=rs.getString(52);
	    Invoice.DetailsAgency.PaymentState=rs.getString(53);
	    Invoice.DetailsAgency.PaymentPurpose=rs.getString(54);
	    Invoice.DetailsAgency.KNP=rs.getString(55);
	    Invoice.DetailsAgency.ReceiptsKBK=rs.getString(56);
	    Invoice.AdditionalData=rs.getString(57);
	    Invoice.SendDate=rs.getString(58);
	    Invoice.TotalSum=rs.getString(59);
		Invoice.ProviderSignature=rs.getString(60);
		Invoice.InnerId=rs.getInt(61);
		Invoice.tru=loadTRU(Invoice.InvoiceID);
		Invoice.pd=loadPD(Invoice.InvoiceID);
		
	}
	
}catch(SQLException xe){
   // System.out.println("xe"+xe);
    System.err.println(xe);
}
	return Invoice;
	
}

public void saveTRU(ArrayList<TRUItem> list)
{

	 try{
	    int i;
	    //System.out.print(list.size());
	    //System.out.println();
	    for(i=0;i<list.size();i++){
	    	psUpdate = conn.prepareStatement("insert into test.tru values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//list.get(i).print();
					psUpdate.setInt(1,  list.get(i).InvoiceID );
					psUpdate.setString(2,  list.get(i).CurrencyCode);
					psUpdate.setString(3,  list.get(i).TRUName);
					psUpdate.setString(4,  list.get(i).ProductCode); 
					psUpdate.setString(5,  list.get(i).MeasureUnit);
					psUpdate.setString(6,  list.get(i).Amount);
					psUpdate.setString(7,  list.get(i).Price); 
					psUpdate.setString(8,  list.get(i).Cost); 
					psUpdate.setString(9,  list.get(i).ExciseRate);
					psUpdate.setString(10, list.get(i).ExciseSum);
					psUpdate.setString(11, list.get(i).TurnoverSum);
					psUpdate.setString(12, list.get(i).NDSRate);
					psUpdate.setString(13, list.get(i).NDSSum); 
					psUpdate.setString(14, list.get(i).CostNDS);
					psUpdate.setString(15, list.get(i).Information);
					 psUpdate.executeUpdate();
	    }       
	}catch(SQLException xe){
	   // System.out.println("xe"+xe);
	    System.err.println(xe);
	}

}

public ArrayList<TRUItem> loadTRU(Integer id){
	ArrayList<TRUItem> list = new ArrayList<TRUItem>();
	TRUItem item=new TRUItem();//item.print();
	try{
	st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM test.TRU where invoiceid="+id.toString());
	while (rs.next()) {item=new TRUItem();
		item.InvoiceID=rs.getInt(1);
		item.CurrencyCode=rs.getString(2);
		item.TRUName=rs.getString(3);
		item.ProductCode=rs.getString(4); 
		item.MeasureUnit=rs.getString(5);
		item.Amount=rs.getString(6);
		item.Price=rs.getString(7); 
		item.Cost=rs.getString(8); 
		item.ExciseRate=rs.getString(9);
		item.ExciseSum=rs.getString(10);
		item.NDSRate=rs.getString(11);
		item.NDSSum=rs.getString(12); 
		item.CostNDS=rs.getString(13);
		item.Information=rs.getString(14);
		list.add(item);
		//System.out.print();	
		//item.print();
	}
	}catch(SQLException xe){
	    System.out.println("xe"+xe);
	}
	return list;
}

public void savePD(ArrayList<ParticipantsData> list)
{

	 try{
	    int i;
	    //System.out.print(list.size());
	    //System.out.println();
		//System.out.println("2");

		psUpdate = conn.prepareStatement("insert into test.ADDITIONAL_PARTICIPANTS values(?,?,?,?,?,?,?,?,?,?,?,?)");
		//System.out.println("2");
	    for(i=0;i<list.size();i++){
	    	//System.out.println(list.get(i).InvoiceDate);
					psUpdate.setInt(1,  list.get(i).InvoiceID );
					psUpdate.setString(2,  list.get(i).Number);
					psUpdate.setString(3,  list.get(i).Participant);
					psUpdate.setString(4,  list.get(i).Share); 
					psUpdate.setString(5,  list.get(i).Cost);
					psUpdate.setString(6,  list.get(i).ExciseRate);
					psUpdate.setString(7,  list.get(i).ExciseSum); 
					psUpdate.setString(8,  list.get(i).TurnoverSum); 
					psUpdate.setString(9,  list.get(i).NDSRate);
					psUpdate.setString(10, list.get(i).NDSSum);
					psUpdate.setString(11, list.get(i).CostNDS);
					psUpdate.setString(12, list.get(i).ParticipantTotal); 
					
					 psUpdate.executeUpdate();
					
	    }       
	}catch(SQLException xe){
	   // System.out.println("xe"+xe);
	    System.err.println(xe);
	}

}

public ArrayList<ParticipantsData> loadPD(Integer id){
	ArrayList<ParticipantsData> list = new ArrayList<ParticipantsData>();
	ParticipantsData item=new ParticipantsData();;
	try{
	st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM test.ADDITIONAL_PARTICIPANTS where invoiceid="+id.toString());
	while (rs.next()) {item=new ParticipantsData();
		item.InvoiceID=rs.getInt(1);
		item.Number=rs.getString(2);
		item.Participant=rs.getString(3);
		item.Share=rs.getString(4); 
		item.Cost=rs.getString(5);
		item.ExciseRate=rs.getString(6);
		item.ExciseSum=rs.getString(7); 
		item.TurnoverSum=rs.getString(8); 
		item.NDSRate=rs.getString(9);
		item.NDSSum=rs.getString(10);
		item.CostNDS=rs.getString(11);
		item.ParticipantTotal=rs.getString(12); 
		list.add(item);
		//System.out.print(list.size());	
		//item.print();
	}
	}catch(SQLException xe){
	    System.out.println("xe"+xe);
	}
	return list;
}


public void execute(String statement)
{
 try{
    //System.out.print(list.size());
    //System.out.println();
	psUpdate = conn.prepareStatement(statement);
	 psUpdate.executeUpdate();           
}catch(SQLException xe){
   // System.out.println("xe"+xe);
    System.err.println(xe);
}
}

public int synchronize(String bin,String iin)
{
	SOAPMessenger msg=new SOAPMessenger();
	Invoice invoice = new Invoice();
	ArrayList<ESFList> locallist= new ArrayList<ESFList>();
	ArrayList<ESFList> loadedlist= new ArrayList<ESFList>();
	ArrayList<ESFList> list= new ArrayList<ESFList>();
	ArrayList<Invoice> invoicelist= new ArrayList<Invoice>();
	int i;
	ArrayList<Integer> Id1=new ArrayList<Integer>();
	ArrayList<Integer> Id2=new ArrayList<Integer>();
	ArrayList<Integer> Id=new ArrayList<Integer>();
	loadedlist=msg.getEsfList("1","1","1",bin,iin);
	/*for(i=0;i<loadedlist.size();i++)
		loadedlist.get(i).print();*/
	for(i=0;i<loadedlist.size();i++)
		Id1.add(loadedlist.get(i).InvoiceID);
	locallist=loadESFList();
	//if(locallist.size()!=0)
	for(i=0;i<locallist.size();i++)
		if(locallist.get(i).Changed!=0)
		Id2.add(locallist.get(i).InvoiceID);
	for(i=0;i<Id2.size();i++)
		if(Id1.contains(Id2.get(i)))
			Id.add(Id2.get(i));
	clearTable("invoice");
	clearTable("tru");
	clearTable("ADDITIONAL_PARTICIPANTS");
	clearTable("ESF_LIST");
	saveESFList(loadedlist);
	//System.out.print(loadedlist.size());
	//msg.getEsf("1","1","1",bin,iin,"3434");
	System.out.print(Id1.size());
	System.out.println();
	for(i=0;i<Id1.size();i++)
			{System.out.println(Id1.get(i).toString());
			invoice=msg.getEsf("1","1","1",bin,iin,Id1.get(i).toString());
			//invoice.print();
			 //saveInvoice(invoice);
			}
	//System.out.println();
	/*tester.invoice=tester.msg.getEsf("1","1","1","010140000007","811000000161","101");
	tester.MyDB.saveInvoice(tester.invoice);
	tester.invoice=tester.MyDB.loadInvoice(tester.invoice.InvoiceID);
	tester.invoice.print();*/
	//tester.invoice.print();



 return 0;
}
public void changeState(Integer id,Integer state)
{
	try{st = conn.createStatement();
	
		st.executeUpdate("UPDATE test.esf_list SET invoicestate ="+state.toString()+",ISCHANGED=1 WHERE Invoiceid="+id.toString());
		
		st.close();
	}
	catch(SQLException xe){
		   // System.out.println("xe"+xe);
		    System.err.println(xe);
		}
}
public void setLogPas(String login,String pass)
{ try{
    int i;
    st = conn.createStatement();
	st.executeUpdate("UPDATE test.LOGIN SET ISLAST=0 WHERE ISLAST=1");
	st.close();
   	psUpdate = conn.prepareStatement("insert into test.LOGIN values(?,?,1)");
				psUpdate.setString(1,  login );
				psUpdate.setString(2,  pass);
				psUpdate.executeUpdate();
				
    }       
catch(SQLException xe){
   // System.out.println("xe"+xe);
    System.err.println(xe);
}
}
public int loadLogPass(String login,String pass)
{
	try{
	st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM test.LOGIN where LOGIN='"+login+"' AND PASS='"+pass+"'");
	if(!rs.next())return -1;
	}catch(SQLException xe){
	    System.out.println(""+xe);
	}
return 0;

}
public String loadLastLogin()
{String login="";
	try{
	st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM test.LOGIN where ISLAST=1");
	if(!rs.next())
	login=rs.getString(1);
	System.out.print(login);
	st.close();
	}catch(SQLException xe){
	    System.out.println("xe "+xe);
	}
	return login;
}
public void setSign(Integer id,String sign)
{
	try{
	    int i;
	    st = conn.createStatement();
	 	psUpdate = conn.prepareStatement("UPDATE test.invoice SET PROVIDERSIGNATURE=? WHERE INNERINVOICEID=?");
		psUpdate.setString(1,sign);
		psUpdate.setInt(2,id);
		psUpdate.executeUpdate();
	    }       
	catch(SQLException xe){
	   // System.out.println("xe"+xe);
	    System.err.println(xe);
	}

}
}

