package net.kresina.bsc.javatest.components;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.kresina.bsc.javatest.beans.Payment;

public class Keeper {

	private Map<String, Payment> record = new HashMap<String, Payment>(); 
	Logger log = Logger.getLogger(this.getClass());
	
	private String masterCurrency;
	
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");;
	
	public void addRecord(Payment pa){
		log.info("received payment: " + pa.toStringInfo());
		Payment payment = record.get(pa.getCurrency());
		if (payment != null){
			log.info("current payment: " + payment.toStringInfo());
			payment.setAmount(payment.getAmount().add(pa.getAmount()));
		} else {
			payment = pa;
		}
		log.info("result payment: " + payment.toStringInfo());
		record.put(payment.getCurrency(), payment);
		System.out.println(Messages.getString("Keeper.string3") + payment.toStringNoNull(masterCurrency));
	}
	
	@Override
	public String toString() {
		String message = "";
		for (Payment p : record.values()){
			String stringFormated = p.toStringFormated(masterCurrency);
			if (stringFormated != null){
				message += stringFormated;
			}
		}
		return message;
	}

	// for scheduler
	public void printMe(){
		log.info("print me");
		System.out.println(this.toString());
	}

	public String getMasterCurrency() {
		return masterCurrency;
	}

	public void setMasterCurrency(String masterCurrency) {
		this.masterCurrency = masterCurrency;
	}

	public static void setDecimalFormat(DecimalFormat decimalFormat) {
		Keeper.decimalFormat = decimalFormat;
	}

	public static DecimalFormat getDecimalformat() {
		return decimalFormat;
	}

	public String printCurrency(String code) {
		return record.get(code).toStringFormated(masterCurrency);
	}
}
