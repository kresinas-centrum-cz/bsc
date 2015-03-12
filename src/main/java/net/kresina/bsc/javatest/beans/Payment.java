package net.kresina.bsc.javatest.beans;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import net.kresina.bsc.javatest.components.Conversions;
import net.kresina.bsc.javatest.components.Keeper;

import org.apache.log4j.Logger;

public class Payment {

	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	
	String currency;
	BigDecimal amount;
	Logger log = Logger.getLogger(this.getClass());
	
	public Payment(String currencyCode, String amount) throws Exception {
		log.info("code: " + currencyCode + "  amount: " + amount);
		Payment.validateCode(currencyCode); 
		this.currency = currencyCode;
		// amount is validated by BigDecimal constructor
		this.amount = new BigDecimal(amount).setScale(ROUNDING_MODE);
	}


	private static void validateCode(String currencyCode) throws Exception {
		if (null == currencyCode){
			throw new Exception("Invalid input");
		} 
		Pattern pattern = Pattern.compile("^[A-Z]{3}$");
		if (!pattern.matcher(currencyCode).matches()){
			throw new Exception("Invalid input");
		}
	}

	@Override
	public String toString() {
		if (amount.compareTo(BigDecimal.ZERO) == 0){
			return null;
		}
		return "" + currency + " " + Keeper.getDecimalformat().format(amount) + "\n";
	}

	public String toStringInfo() {
		return "Payment [currency=" + currency + ", amount=" + amount + "]";
	}

	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public static void main(String[] args) {
	}


	public String toStringFormated(String masterCurrency) {
		if (amount.compareTo(BigDecimal.ZERO) == 0){
			return null;
		}
		return toStringNoNull(masterCurrency);
	}


	public String toStringNoNull(String masterCurrency) {
		if (null != Conversions.getString(currency)){
			return "" + currency + " " + Keeper.getDecimalformat().format(amount) +" ("+masterCurrency+ " " + Keeper.getDecimalformat().format(amount.multiply(new BigDecimal(Conversions.getString(currency)).setScale(ROUNDING_MODE))) + ")\n";
		} else {
			return "" + currency + " " + Keeper.getDecimalformat().format(amount) + "\n";
		}
	}

}
