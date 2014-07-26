package app;

public class Range{
	
	public Range(int start, int end){
		this.start = start;
		this.end = end;
	}
	private int start;
	private int end;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	
	@Override
	public int hashCode(){
		return (start << 32) + end;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Range other = (Range) obj;
        if (end != other.end)
            return false;
        if (start != other.start)
            return false;
        return true;
	}
	
}
