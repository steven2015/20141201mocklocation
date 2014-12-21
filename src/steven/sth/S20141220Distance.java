/**
 *
 */
package steven.sth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven
 *
 */
public class S20141220Distance{
	private static final List<double[]> list = new ArrayList<>();

	public static final void main(final String[] args){
		S20141220Distance.create(22.339617,114.206073);
		S20141220Distance.create(22.339278,114.205771);
		S20141220Distance.create(22.339166,114.205555);
		//S20141220Distance.create(22.338921,114.205873);
		//create(22.339166, 114.205555);
		//create(22.339278, 114.205771);
		//create(22.339617, 114.206073);
		S20141220Distance.mid();
	}
	public static final void create(final double latitude, final double longitude){
		S20141220Distance.list.add(new double[]{latitude, longitude});
	}
	public static final void mid(){
		int index1 = 0;
		int index2 = 0;
		float d = 0;
		for(int i = 0; i < S20141220Distance.list.size(); i++){
			for(int j = i + 1; j < S20141220Distance.list.size(); j++){
				final float dd = S20141220Distance.distance(S20141220Distance.list.get(i)[0], S20141220Distance.list.get(i)[1], S20141220Distance.list.get(j)[0], S20141220Distance.list.get(j)[1]);
				if(dd > d){
					d = dd;
					index1 = i;
					index2 = j;
				}
				if(dd >= 80){
					System.out.println("radius >= 40");
				}
			}
		}
		System.out.println("max diameter " + d);
		final double latitude = (S20141220Distance.list.get(index1)[0] + S20141220Distance.list.get(index2)[0]) / 2;
		final double longitude = (S20141220Distance.list.get(index1)[1] + S20141220Distance.list.get(index2)[1]) / 2;
		if(S20141220Distance.check40(latitude, longitude)){
			System.out.println(latitude + "," + longitude);
		}else{
			final double radius = 0.0001;
			final double step = 0.00001;
			for(double i = latitude - radius; i <= latitude + radius; i += step){
				for(double j = longitude - radius; j <= longitude + radius; j += step){
					if(S20141220Distance.check40(i, j)){
						System.out.println(i + "," + j);
						return;
					}
				}
			}
			System.out.println("not possible");
		}
	}
	public static final boolean check40(final double latitude, final double longitude){
		for(final double[] p : S20141220Distance.list){
			if(S20141220Distance.distance(latitude, longitude, p[0], p[1]) >= 40){
				return false;
			}
		}
		return true;
	}
	public static final float distance(final double fromLatitude, final double fromLongitude, final double toLatitude, final double toLongitude){
		final float[] results = new float[1];
		Location.distanceBetween(fromLatitude, fromLongitude, toLatitude, toLongitude, results);
		return results[0];
	}
}
