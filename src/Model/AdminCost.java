package Model;

public class AdminCost {
	private String Dong;
	private String Ho;
	private String electric;
	private String water;
	private String gas;
	private String heat;
	private String joint;
	private String receive;
	private String tatalCost;
	private String reciveDay;
	private String info;
	public AdminCost(String info,String dong, String ho, String electric, String water, String gas, String heat, String joint,
			String receive, String tatalCost, String reciveDay) {
		super();
		Dong = dong;
		Ho = ho;
		this.electric = electric;
		this.water = water;
		this.gas = gas;
		this.heat = heat;
		this.joint = joint;
		this.receive = receive;
		this.tatalCost = tatalCost;
		this.reciveDay = reciveDay;
		this.info=info;
	}
	public String getDong() {
		return Dong;
	}
	public void setDong(String dong) {
		Dong = dong;
	}
	public String getHo() {
		return Ho;
	}
	public void setHo(String ho) {
		Ho = ho;
	}
	public String getElectric() {
		return electric;
	}
	public void setElectric(String electric) {
		this.electric = electric;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getHeat() {
		return heat;
	}
	public void setHeat(String heat) {
		this.heat = heat;
	}
	public String getJoint() {
		return joint;
	}
	public void setJoint(String joint) {
		this.joint = joint;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getTatalCost() {
		return tatalCost;
	}
	public void setTatalCost(String tatalCost) {
		this.tatalCost = tatalCost;
	}
	public String getReciveDay() {
		return reciveDay;
	}
	public void setReciveDay(String reciveDay) {
		this.reciveDay = reciveDay;
	}
	public String getinfo() {
		return reciveDay;
	}
	public void setinfo(String info) {
		this.info = info;
	}
	
	
	
}
