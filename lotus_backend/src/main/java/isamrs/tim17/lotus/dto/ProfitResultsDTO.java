package isamrs.tim17.lotus.dto;

public class ProfitResultsDTO {
	private double averagePrice;
	private double totalProfit;
	private double totalCount;
	
	public ProfitResultsDTO() {}
	
	public ProfitResultsDTO(double averagePrice, double totalProfit, double totalCount) {
		this.averagePrice = averagePrice;
		this.totalCount = totalCount;
		this.totalProfit = totalProfit;
	}

	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public double getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}
	public double getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
