package app;

public class EachHour {
	private int hour;
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public boolean isChoosen() {
		return choosen;
	}

	public void setChoosen(boolean choosen) {
		this.choosen = choosen;
	}
	
	public boolean getChoose(){
		return choosen;
	}

	private boolean choosen;
	
	public EachHour (int hour, boolean choosen){
		this.hour = hour;
		this.choosen = choosen;
	}
	
	
}
